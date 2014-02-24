package com.android.crazywheel;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ItemsListFragment extends ListFragment {

    private TaskCallbacks mCallbacks;
    private UpdateTask mTask;
    public static final String TAG = "items";
    private ElementsArrayAdapter adapter;
    private ArrayList<Element> items;

    public interface TaskCallbacks {
        void onPreExecute();
        void onProgressUpdate(int percent);
        void onCancelled();
        void onPostExecute();
        void onListItemClick(Element element);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mCallbacks = null;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.setRetainInstance(true);
        this.items = new ArrayList<Element>();
        this.adapter = new ElementsArrayAdapter(getActivity(), R.layout.item, items);
        setListAdapter(adapter);
        this.getActivity().supportInvalidateOptionsMenu();

        this.refresh();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    public void refresh() {

        mTask = new UpdateTask();
        mTask.execute();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.mCallbacks.onListItemClick(((ElementsArrayAdapter) l.getAdapter()).getItem(position));
    }

    public class UpdateTask extends AsyncTask<String, Integer, Void> {

        private Exception e;

        @Override
        protected void onPreExecute() {
            e = null;
            if (mCallbacks != null) {
                mCallbacks.onPreExecute();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        protected Void doInBackground(String... params) {

            try {
                JsonController.readElements(HttpController.get(), items);
            } catch (IOException e) {
                this.e = e;
                e.printStackTrace();
            } catch (JSONException e) {
                this.e = e;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... i) {

            if (mCallbacks != null) {
                mCallbacks.onProgressUpdate(i[0]);
            }
        }

        @Override
        protected void onCancelled() {

            if (mCallbacks != null) {
                mCallbacks.onCancelled();
            }

        }

        @Override
        protected void onPostExecute(Void res) {

            if (e instanceof IOException) {
                this.showError("Ошибка соединения с сервером.");
            }
            if (e instanceof JSONException) {
                this.showError("Ошибка формата ввода информации.");
            }

            if (mCallbacks != null) {
                mCallbacks.onPostExecute();
            }

        }

        private void showError(String message) {

            Element element = new Element();
            element.setId("ID1001");
            element.setTitle("Ошибка");
            element.setText(message + " Да прибудет с вами великая сила!");

            items.clear();
            items.add(element);

        }
    }

}
