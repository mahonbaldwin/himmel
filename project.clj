(defproject himmel "1.0.0-SNAPSHOT"
  :description "Clojure wrapper for Google Datastore"
  :min-lein-version "2.0.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.google.cloud/google-cloud "0.8.0" :exclusions [io.netty/* io.grpc/*]]]
  :source-paths ["src/clj"]
  :java-source-paths ["src/java"]
  :javac-options     ["-target" "1.8" "-source" "1.8"])
