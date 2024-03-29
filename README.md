[![Clojars Project](https://img.shields.io/clojars/v/lt.jocas/lucene-query-parsing.svg)](https://clojars.org/lt.jocas/lucene-query-parsing)
[![cljdoc badge](https://cljdoc.org/badge/lt.jocas/lucene-query-parsing)](https://cljdoc.org/d/lt.jocas/lucene-query-parsing/CURRENT)
[![Tests](https://github.com/dainiusjocas/lucene-query-parsing/actions/workflows/test.yml/badge.svg)](https://github.com/dainiusjocas/lucene-query-parsing/actions/workflows/test.yml)

# lucene-query-parsing

Library to parse [Lucene](https://lucene.apache.org) queries and build query parsers in the data-driven fashion.

## Quickstart

Dependencies:
```clojure
{:deps
 {lt.jocas/lucene-query-parsing {:mvn/version "RELEASE"}}}
```
Code:
```clojure
(require '[lucene.custom.query :as q])

;; Parse queries
(q/parse "foo bar baz")
;; => #object[org.apache.lucene.search.BooleanQuery 0x650526d1 "foo bar baz"]

(q/parse "*foo bar baz" :classic {:allow-leading-wildcard true} "field-name")
;; => #object[org.apache.lucene.search.BooleanQuery 0x3218294 "field-name:foo field-name:bar field-name:baz"]


(require '[lucene.custom.query-parser :as query-parser])

;; Create query parser
(query-parser/create :classic {:allow-leading-wildcard true})
;; =>
;; #object[org.apache.lucene.queryparser.classic.QueryParser
;;         0x36df3ba2
;;         "org.apache.lucene.queryparser.classic.QueryParser@36df3ba2"]
```

## Available Query Parsers

Currently, 5 [Lucene query parsers](https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html) are supported:

- classic: [docs](https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html)
- complex-phrase: [docs](https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html)
- simple: [docs](https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html)
- standard: [docs](https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html)
- surround: [docs](https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html)

These query parsers can be further configured with these parameters that were extracted from the Lucene source code.
The configuration options compatibility table for each supported query parser:

|                                         :option | :classic | :complex-phrase | :simple | :standard | :surround |
|-------------------------------------------------|----------|-----------------|---------|-----------|-----------|
|                         :allow-leading-wildcard |     true |            true |   false |      true |     false |
| :auto-generate-multi-term-synonyms-phrase-query |     true |            true |    true |     false |     false |
|                   :auto-generate-phrase-queries |     true |            true |   false |     false |     false |
|                                :date-resolution |     true |            true |   false |      true |     false |
|                               :default-operator |     true |            true |    true |      true |     false |
|                               :default-operator |     true |            true |    true |      true |     false |
|                               :default-operator |     true |            true |    true |      true |     false |
|                         :determinize-work-limit |     true |            true |   false |     false |     false |
|                           :enable-graph-queries |     true |            true |    true |     false |     false |
|                     :enable-position-increments |     true |            true |    true |      true |     false |
|                     :enable-position-increments |     true |            true |    true |      true |     false |
|                                  :fuzzy-min-sim |     true |            true |   false |      true |     false |
|                            :fuzzy-prefix-length |     true |            true |   false |      true |     false |
|                                       :in-order |    false |            true |   false |     false |     false |
|                                         :locale |     true |            true |   false |      true |     false |
|                      :multi-term-rewrite-method |     true |            true |   false |      true |     false |
|                                    :phrase-slop |     true |            true |   false |      true |     false |
|                            :split-on-whitespace |     true |            true |   false |     false |     false |
|                                      :time-zone |     true |            true |   false |      true |     false |

## Defaults

Default values for every Lucene query parser configuration option:

|                                         :option |               :classic |        :complex-phrase | :simple |              :standard | :surround |
|-------------------------------------------------|------------------------|------------------------|---------|------------------------|-----------|
|                         :allow-leading-wildcard |                  false |                  false |         |                  false |           |
| :auto-generate-multi-term-synonyms-phrase-query |                  false |                  false |   false |                        |           |
|                   :auto-generate-phrase-queries |                  false |                  false |         |                        |           |
|                                :date-resolution |                        |                        |         |                        |           |
|                               :default-operator |                     OR |                     OR |  should |                     OR |           |
|                               :default-operator |                     OR |                     OR |  should |                     OR |           |
|                               :default-operator |                     OR |                     OR |  should |                     OR |           |
|                         :determinize-work-limit |                  10000 |                  10000 |         |                        |           |
|                           :enable-graph-queries |                   true |                   true |    true |                        |           |
|                     :enable-position-increments |                  false |                  false |    true |                  false |           |
|                     :enable-position-increments |                  false |                  false |    true |                  false |           |
|                                  :fuzzy-min-sim |                    2.0 |                    2.0 |         |                    2.0 |           |
|                            :fuzzy-prefix-length |                      0 |                      0 |         |                      0 |           |
|                                       :in-order |                        |                   true |         |                        |           |
|                                         :locale |                     en |                     en |         |                     en |           |
|                      :multi-term-rewrite-method | CONSTANT_SCORE_REWRITE | CONSTANT_SCORE_REWRITE |         | CONSTANT_SCORE_REWRITE |           |
|                                    :phrase-slop |                      0 |                      0 |         |                      0 |           |
|                            :split-on-whitespace |                   true |                   true |         |                        |           |
|                                      :time-zone |                        |                        |         |                        |           |

In case you're lost: those cells that in the compatibility table states true but in the default values is empty
mean that the default value is nil.

For further details consult the [Lucene docs](https://javadoc.io/doc/org.apache.lucene/lucene-queryparser/latest/index.html).

## License

Copyright &copy; 2022 [Dainius Jocas](https://www.jocas.lt).

Distributed under The Apache License, Version 2.0.
