(ns my-webapp.handler
  (:require [my-webapp.views :as views]
  					[compojure.core :as cc]
            [compojure.route :as route]
            [compojure.handler :as handler]))

(cc/defroutes app-routes
  (cc/GET "/" 
  	[] 
  	(views/home-page))
  (cc/GET "/add-location" 
  	[] 
  	(views/add-location-page))
  (cc/GET "/add-location" 
  	{params :params} 
  	(views/add-location-results-page params))
  (cc/GET "/location/:loc-id" 
  	[loc-id] 
  	(views/location-page loc-id))
  (cc/GET "/all-locations" 
  	[] 
  	(views/all-locations-page))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
