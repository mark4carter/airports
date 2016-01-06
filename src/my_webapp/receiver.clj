(ns my-webapp.receiver
 (:require [langohr.core :as lc]
            [langohr.channel :as lch]
            [langohr.exchange :as le]
            [langohr.queue :as lq]
            [langohr.basic :as lb]
            [langohr.consumers :as lcons]))

(def ^{:const true} x "direct_logs")

(defn handle-delivery
	"Handles message delivery"
	[ch {:keys [routing-key]} payload]
	(println (format " [x] %s:%s" routing-key (String. payload "UTF-8"))))



(defn -main
	[& args]
	(with-open [conn (lc/connect)]
		(let [ch       (lch/open conn)
					{:keys [queue]} (lq/declare ch "" {:durable false :auto-delete false})]
			(le/direct ch x {:durable false :auto-delete false})
			(println args)
			(doseq [severity args]
				(lq/bind ch queue x {:routing-key severity}))
			(println " [x] Waiting for logs. To exit press CTRL+C")
			(lcons/blocking-subscribe ch queue handle-delivery))))