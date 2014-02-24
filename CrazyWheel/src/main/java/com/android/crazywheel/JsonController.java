package com.android.crazywheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonController {

    public static void readElements(String json, ArrayList<Element> items)
            throws JSONException {

        synchronized (items) {
            ArrayList<Element> heap = new ArrayList<Element>();

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Element element = new Element();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                element.setId(jsonObject.getString("id"));
                element.setTitle(jsonObject.getString("title"));
                element.setText(jsonObject.getString("text"));
                addElement(items, element);
                heap.add(element);
            }

            for (Element element : (ArrayList<Element>) items.clone())
                if (!heap.contains(element))
                    items.remove(element);
        }

    }

    private static void addElement(ArrayList<Element> items, Element element) {

        if (items.contains(element)) {
            items.get(items.indexOf(element)).setText(element.getText());
        } else {
            items.add(element);
        }

    }

}
