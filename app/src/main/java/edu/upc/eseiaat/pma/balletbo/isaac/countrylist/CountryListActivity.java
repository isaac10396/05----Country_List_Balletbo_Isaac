package edu.upc.eseiaat.pma.balletbo.isaac.countrylist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {

    //Objecte Adaptador llista
    private ArrayAdapter<String> adapter;
    //Objecte ArrayList en Java (millor que la taula normal)
    private ArrayList<String> country_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        //Creem la taula pelada de Java
        String[] countries = getResources().getStringArray(R.array.countries);
        //Convertim countries en country_list
        country_list = new ArrayList<>(Arrays.asList(countries));

        //Referència a la llista del Layout
        ListView list = (ListView) findViewById(R.id.country_list);

        //Creem l'Adaptador (Context, Layout de cada pastilla de la llista, dades)
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country_list);
        //Tots els ListViews tenen un Adaptador per mostrar be la llista
        list.setAdapter(adapter);

        //Al fer un click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {
                Toast.makeText(
                        CountryListActivity.this,
                        String.format("Has escogido '%s'", country_list.get(pos)),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        //Al fer un click mes llarg
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id) {
                //Creador cuadros de dialogo
                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                //Titol Builder
                builder.setTitle(R.string.confirm);
                //Missatge Builder
                String msg = getResources().getString(R.string.confirm_message);
                builder.setMessage(msg + " " + country_list.get(pos) + "?");
                builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Se elimina el pais de la lista
                        country_list.remove(pos);
                        //Notificacion de que han cambiado los datos
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();
                return true;
            }
        });
    }
}