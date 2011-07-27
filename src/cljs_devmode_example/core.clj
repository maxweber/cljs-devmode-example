(ns cljs-devmode-example.core
  (:use compojure.core
        hiccup.core
        hiccup.page-helpers
        ring.adapter.jetty
        cljs-devmode.ring-middleware))

(defroutes app
  (GET "/" []
       (html [:head
              [:title "Hello cljs-devmode"]
              (include-js "cljs/out/goog/base.js")
              (include-js "cljs/cljs-devmode-example.js")
              (javascript-tag
               "goog.require('hello.core');")
              (javascript-tag
               "alert(greet(\"ClojureScript\"));
                alert(\"The sum of [1,2,3,4,5,6,7,8,9] is: \"
                + hello.core.sum([1,2,3,4,5,6,7,8,9]));")]
             [:body
              [:h1 "Hello cljs-devmode!"]])))

(def the-app
     (-> app
         (wrap-cljs-forward "/cljs")))

(defn -main []
  (run-jetty (var the-app) {:port 8080}))
