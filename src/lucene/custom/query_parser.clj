(ns lucene.custom.query-parser
  (:require [lucene.custom.query-parser.parsers :as parsers]
            [lucene.custom.query-parser.conf :as conf])
  (:import (org.apache.lucene.analysis Analyzer)
           (org.apache.lucene.analysis.standard StandardAnalyzer)))

(defn create
  "Constructs an Object that can be used for later query parsing.
   Defaults to the classic query parser.
   Params:
   - query-parser-name: Lucene query parser id, one of #{:classic :complex-phrase :surround :simple :standard}, default: :classic
   - conf: a map with query parser configuration
   - field-name: default field for terms query, defaults \"\"
   - analyzer: Lucene analyzer to apply on query terms, defaults StandardAnalyzer
   https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html"
  ([]
   (create :classic {} "" (StandardAnalyzer.)))
  ([query-parser-name]
   (create query-parser-name {} "" (StandardAnalyzer.)))
  ([query-parser-name conf]
   (create query-parser-name conf "" (StandardAnalyzer.)))
  ([query-parser-name conf ^String field-name]
   (create query-parser-name conf field-name (StandardAnalyzer.)))
  ([query-parser-name conf ^String field-name ^Analyzer analyzer]
   (case (keyword query-parser-name)
     :classic (parsers/classic conf field-name analyzer)
     :complex-phrase (parsers/complex-phrase conf field-name analyzer)
     :surround (parsers/surround conf)
     :simple (parsers/simple conf field-name analyzer)
     :standard (parsers/standard conf analyzer)
     (parsers/classic conf field-name analyzer))))

(defn default-config
  "Given the query parser key, returns a hashmap with the default options for that query parser."
  [query-parser-name]
  (let [query-parser (create query-parser-name)
        klass->defaults (reduce-kv
                          (fn [m k v]
                            (assoc m k (reduce (fn [acc [k v]]
                                                 (assoc acc k (:default v)))
                                               {} v)))
                          {} conf/query-parser-class->attrs)]
    (reduce (fn [acc [klass defaults]]
              (if (instance? ^Class klass query-parser)
                (merge acc defaults)
                acc)) {} klass->defaults)))

(comment
  (create)
  (create "classic" {} "field-name" (StandardAnalyzer.))
  ;; create all query parsers with their defaults
  (let [qp-kws #{:classic :complex-phrase :surround :simple :standard}]
    (map #(lucene.custom.query-parser/create
            %
            (lucene.custom.query-parser/default-config %)) qp-kws)))
