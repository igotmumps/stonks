(defproject stonks "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [compojure "1.7.1"]
                 [hiccup "1.0.5"]
                 [ring-server "0.5.0"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.xerial/sqlite-jdbc "3.47.0.0"]]
  :plugins [[lein-ring "0.12.6"]]
  :ring {:handler stonks.handler/app
         :init stonks.handler/init
         :destroy stonks.handler/destroy}
  :profiles
  {:uberjar {:aot :all}
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring/ring-mock "0.4.0"] [ring/ring-devel "1.7.1"]]
    :ring
    {:auto-reload? true}}})
