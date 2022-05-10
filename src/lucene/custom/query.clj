(ns lucene.custom.query
  (:require [lucene.custom.query.impl :as impl]
            [lucene.custom.query-parser :as query-parser])
  (:import (org.apache.lucene.analysis Analyzer)
           (org.apache.lucene.analysis.standard StandardAnalyzer)
           (org.apache.lucene.search Query)))

(defn parse
  "Constructs query parser and parses the query.
  Params:
  - query: Lucene query string
  - query-parser-name: Lucene query parser id, one of #{:classic :complex-phrase :surround :simple :standard}, default: :classic
  - query-parser-conf: map with query parser configuration
  - field-name: default field for terms query, defaults \"\"
  - analyzer: Lucene analyzer to apply on query terms, defaults StandardAnalyzer"
  (^Query [^String query]
   (parse query :classic {} "" (StandardAnalyzer.)))
  (^Query [^String query query-parser-name]
   (parse query query-parser-name {} "" (StandardAnalyzer.)))
  (^Query [^String query query-parser-name query-parser-conf]
   (parse query query-parser-name query-parser-conf "" (StandardAnalyzer.)))
  (^Query [^String query query-parser-name query-parser-conf ^String field-name]
   (parse query query-parser-name query-parser-conf field-name (StandardAnalyzer.)))
  (^Query [^String query query-parser-name query-parser-conf ^String field-name ^Analyzer analyzer]
   (let [query-parser (query-parser/create query-parser-name query-parser-conf field-name analyzer)]
     (impl/parse* query-parser query field-name))))

(comment
  (lucene.custom.query/parse "foo bar baz")
  (lucene.custom.query/parse "foo bar baz" :classic {} "field-name" (StandardAnalyzer.)))
