(ns himmel.core
  (:import
    (gcloud_wrap GCloudWrapper)
    (java.util Date)))

(def datastore GCloudWrapper/datastore)

(defmulti set-kv (fn [builder key value & options]
                   (cond
                     (nil? value) :nil
                     :else (type value))))

(defn entity-key
  ([datastore kind]
   (GCloudWrapper/entityKey datastore kind))
  ([datastore kind name]
   (GCloudWrapper/entityKey datastore kind name)))

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
(defn make-entity [ds kind name values]
  (let [e-key     (entity-key ds kind name)
        e-builder (entity-with-values (entity-builder e-key) values)]
    (build e-builder)))

(defn put-entity [datastore entity]
  (println "upserting" (.toString entity))
  (GCloudWrapper/putEntity datastore entity))

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

(defmethod set-kv :default [builder key value &  options]
  (let [{:keys [indexed]} (value-map (apply hash-map options))]
    (GCloudWrapper/setKV builder (name key) value indexed)))

(defn get-entity [datastore key]
  (GCloudWrapper/getEntity datastore key))






(defn wox []
  (let [ds        datastore
        thing-key (entity-key ds "Thing" "thing-1")
        thing     (-> (entity-builder thing-key)
                      (set-kv "string1" "value1" :indexed false)
                      (set-kv "boolean1" true)
                      (set-kv "int1" 5)
                      (set-kv "null1" nil :indexed false)
                      (set-kv "date1" (Date.))
                      (set-kv "array1" ["this" "is" "an" "array"])
                      (set-kv "array2" [1 2 3 4 5])
                      (build))]
    (put-entity ds thing)))

(defn wor []
  (put-entity datastore
    (make-entity datastore "Thing" "thing-2" {:str-1  "aaaaaaaaaaaaaa LONG string!"
                                              :abc         {:val ["a" "b" "c" "d" "e" "f"] :indexed false}
                                              :one         [1 2 3 4 5]
                                              :another-map (make-entity datastore "smaller-thing" "small-thing" {:a 1
                                                                                                                 :b      2})})))

(defn x []
  (put-entity datastore
    (make-entity datastore "Task" "task-1" {:task-name "Eat a worm."
                                            :done false})))

(defn gox [type name]
  (get-entity datastore (entity-key datastore type name)))

