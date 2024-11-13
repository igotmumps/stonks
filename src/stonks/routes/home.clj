(ns stonks.routes.home
  (:require [compojure.core :refer :all]
            [stonks.views.layout :as layout]
            [hiccup.form :refer :all]
            [stonks.models.db :as db]))

(defn format-time [timestamp]
  (-> "dd/MM/yyyy"
      (java.text.SimpleDateFormat.)
      (.format timestamp)))

(defn show-stonks []
  [:ul.stonks
   (for [{:keys [ticker company timestamp]} (db/read-stonks)]
     [:li
      [:blockquote ticker]
      [:p "-" [:cite company]]
      [:time (format-time timestamp)]])])

(defn home [& [ticker company error]]
  (layout/common
   [:h1 "Stonks, am I right?"]
   [:p "Welcome to stonks"]
   [:p error]
   (show-stonks)
   [:hr]
   (form-to [:post "/"]
            [:p "Ticker:"]
            (text-field "ticker" ticker)
            [:p "Company Name:"]
            (text-field "company" company)
            [:br]
            (submit-button "submit"))
   ))

(defn save-stonks [ticker company]
  (cond
    (empty? ticker) (home ticker company "Some dummy forgot to include a ticker")
    (empty? company) (home ticker company "Missing required company name")
    :else (do
            (db/save-stonks ticker company)
            (home))))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [ticker company] (save-stonks ticker company)))
