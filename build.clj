(ns build
  "lt.jocas/lucene-query-parsing's build script.
  clojure -T:build deploy
  For more information, run:
  clojure -A:deps -T:build help/doc"
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.build.api :as b]
            [org.corfield.build :as bb]))

(def lib 'lt.jocas/lucene-query-parsing)
(defn- the-version [patch] (format "1.0.%s" patch))
(def version (the-version (b/git-count-revs nil)))
(def snapshot (the-version "999-SNAPSHOT"))

(defn deploy "Deploy the JAR to Clojars." [opts]
  (-> opts
      (assoc :lib lib :version (if (:snapshot opts) snapshot version))
      (bb/clean)
      (assoc :src-pom "pom.xml.template")
      (bb/jar)
      (bb/deploy)))

(defn trigger-release [opts]
  (let [tag (str "v" (if (:snapshot opts) snapshot version))]
    (println "Initiating release for git tag:" tag)
    (b/git-process {:git-args ["tag" tag]})
    (b/git-process {:git-args ["push" "origin" tag]})))
