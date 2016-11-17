(ns lesson.core
  (:require [reagent.core :as r]))

(enable-console-print!)

;; Defonce
(defonce state* (r/atom {:contacts #{}
                         :new-contact ""}))

(def contacts* (r/cursor state* [:contacts]))

(def contact-count* (r/track (fn [contacts*] (count contacts*))
                      contacts*))

(def new-contact* (r/cursor state* [:new-contact]))

(def search-text* (r/cursor state* [:search-text]))

(defn update-new-contact [e]
  (reset! new-contact* (.-value (.-target e))))

(defn add-contact [& _]
  (swap! contacts* conj @new-contact*))

(defn input []
  [:input {:value    @new-contact*
           :onChange update-new-contact}])

(defn main-view []
  ;; Here only happens on mount
  (fn []
    [:div
     [:pre (pr-str @state*)]
     [input]
     [:button {:onClick add-contact} "Add Contact"]]))

(r/render-component [main-view] (.-body js/document))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
