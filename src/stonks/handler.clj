(ns stonks.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stonks.routes.home :refer [home-routes]]
             [stonks.models.db :as db]))

(defn init []
  (println "stonks is starting")
  (if-not (.exists (java.io.File. "./db.sq3"))
    (db/create-stonks-table)))

(defn destroy []
  (println "stonks is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes app-routes)
      (handler/site)
      (wrap-base-url)))
