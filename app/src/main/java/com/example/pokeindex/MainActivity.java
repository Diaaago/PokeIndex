package com.example.pokeindex;


import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    private List<Pokemon> pokemonList;

    //private boolean isLoading = false; // 用于跟踪是否正在加载数据
    //private int visibleThreshold = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 RecyclerView 和其他成员变量
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        pokemonList = new ArrayList<>();
        adapter = new PokemonAdapter(this, pokemonList);
        recyclerView.setAdapter(adapter);

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                             @Override
                                             public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                                 super.onScrolled(recyclerView, dx, dy);
                                                 GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                                                 int totalItemCount = layoutManager.getItemCount();
                                                 int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                                                 if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                                     // 加载更多数据
                                                     isLoading = true;
                                                     // 这里调用加载更多数据的方法
                                                 }
                                             }
                                         });*/
        // 启动异步任务加载宝可梦数据
        new LoadPokemonTask().execute("https://raw.githubusercontent.com/Purukitto/pokemon-data.json/master/pokedex.json");
    }

    private class LoadPokemonTask extends AsyncTask<String, Void, List<Pokemon>> {

        @Override
        protected List<Pokemon> doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());

                // 使用 Gson 解析 JSON 数据
                List<Pokemon> pokemonList = new Gson().fromJson(reader, new TypeToken<List<Pokemon>>(){}.getType());
                urlConnection.disconnect();
                return pokemonList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Pokemon> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
                // 更新 RecyclerView 的数据
                pokemonList.clear();
                pokemonList.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }
    }


}