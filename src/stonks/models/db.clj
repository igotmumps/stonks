(ns stonks.models.db
  (:require [clojure.java.jdbc :as jdbc])
  )

(def db {:classname "org.sqlite.JDBC",
         :subprotocol "sqlite",
         :subname "db.sq3"})



(defn read-stonks []
    (jdbc/query db
      ["SELECT * FROM stonks ORDER BY timestamp DESC"]
      ))

(defn save-stonks [ticker company]
    (jdbc/insert! db
                          :stonks
                          {:ticker ticker 
                           :company company 
                           :timestamp (new java.util.Date)}))


(defn create-stonks-table []
    (jdbc/db-do-commands db
     [(jdbc/create-table-ddl :stonks 
                             [[:id "INTEGER PRIMARY KEY AUTOINCREMENT"] 
                              [:timestamp "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"] 
                              [:ticker "TEXT"] 
                              [:company "TEXT"]]
                             "CREATE INDEX timestamp_index ON stonks (timestamp)")]))
  