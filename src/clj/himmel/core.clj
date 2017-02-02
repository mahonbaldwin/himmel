(ns himmel.core
  (:import
    (gcloud_wrap GCloudWrapper)
    (java.util Date)
    (com.google.common.collect RegularImmutableList)
    (com.google.cloud.datastore Key)))

(def datastore GCloudWrapper/datastore)

(defmulti set-kv (fn [builder key value & options]
                   (cond
                     (nil? value) :nil
                     :else (type value))))

(defn entity-key
  ([datastore kind]
   (GCloudWrapper/entityKey datastore kind))
  ([datastore kind name]
   (let [t (type name)]
     (cond
       (= t java.lang.Long) (GCloudWrapper/entityKeyLong datastore kind name)
       (= t java.lang.String) (GCloudWrapper/entityKey datastore kind name)))))

(defn entity-builder [entity-key]
  (GCloudWrapper/entityBuilder entity-key))

(defn build [entity-builder]
  (GCloudWrapper/build entity-builder))

(defn- value-map [m]
  (merge {:indexed true} m))

(defn- entity-with-values [builder values]
  (reduce
    (fn [builder [k v]]
      (println "building " k " with " v)
      (if (map? v)
        (let [val-map (value-map v)]
          (set-kv builder k (:val val-map) :indexed (:indexed val-map)))
        (set-kv builder k v)))
    builder
    values))

;take a map and do all the steps for me
(defn make-entity
  ([ds kind values]
   (let [e-key     (entity-key ds kind)
         e-builder (entity-with-values (entity-builder e-key) values)]
     (build e-builder)))
  ([ds kind name values]
   (let [e-key     (entity-key ds kind name)
         e-builder (entity-with-values (entity-builder e-key) values)]
     (build e-builder))))

(defmethod set-kv clojure.lang.PersistentVector [builder key value & options]
  (if (= key :abc) (println "options for :abc" options))
  (let [{:keys [indexed]} (value-map (apply hash-map options))
        values (map #(GCloudWrapper/valueFor %) value)]
    (GCloudWrapper/setKV builder (name key) (java.util.ArrayList. values) indexed)))

(defmethod set-kv clojure.lang.PersistentArrayMap [builder key value & options]
  (throw (IllegalArgumentException. (str "Embeded entities must be build with `entity` fn. Ex: (entity datastore \"smaller-thing\" \"small-thing-1\" {:a 1 :b 2})"))))

(defmethod set-kv :nil [builder key value & options]
  (let [{:keys [indexed]} (value-map (apply hash-map options))]
    (GCloudWrapper/setKVnull builder (name key) indexed)))

(defmethod set-kv :default [builder key value & options]
  (let [{:keys [indexed]} (value-map (apply hash-map options))]
    (GCloudWrapper/setKV builder (name key) value indexed)))

(defn get-value [entity name]
  (let [v (.get (GCloudWrapper/getValue entity name))]
    (if (= (type v) RegularImmutableList)
      (map #(.get %) v)
      v)))

(defn get-names [entity]
  (GCloudWrapper/getNames entity))

(defn ent->clj [entity]
  (reduce (fn [em value-name]
            (let [val (get-value entity value-name)]
              (into em {(keyword value-name) val})))
    {}
    (get-names entity)))

(defn get-key [entity]
  (GCloudWrapper/getKey entity))

(defn get-entity
  ([datastore key]
   (GCloudWrapper/getEntity datastore key))
  ([datastore type id]
   (GCloudWrapper/getEntity datastore (entity-key datastore type id))))

(defn get-data
  ([datastore key]
   (ent->clj (get-entity datastore key)))
  ([datastore type id]
   (ent->clj (get-entity datastore type id))))


(defn add-entity [datastore entity]
  (println "adding" (.toString entity))
  (GCloudWrapper/addEntity datastore entity))

(defn put-entity [datastore entity]
  (println "upserting" (.toString entity))
  (GCloudWrapper/putEntity datastore entity))

(defn update-entity [datastore entity]
  (println "updating" (.toStirng entity))
  (GCloudWrapper/updateEntity datastore entity))

(defn delete-entity [datastore entity]
  (println "deleting" (.toString entity))
  (cond
    (= (type entity) Key) (GCloudWrapper/deleteEntity datastore entity)
    :else (GCloudWrapper/deleteEntity datastore (get-key entity))))
