package com.example.renatocouto_avaliacaobimestral_parte1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.renatocouto_avaliacaobimestral_parte1.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

/*
Crie um aplicativo com os seguintes requisitos:
Usando o NavigationDrawer (pode ser tanto o template da IDE, ou a versão simplificada)
faça um app que consuma os dados da PokéAPI. Trata-se de uma API gratuita e regida pela
BSD-3-Clause license e autoria de Paul Hallett.
A API disponibiliza dados referentes aos personagens do Pokémon.
A API está disponível no seguinte endereço: https://pokeapi.co/.
Seu app deve mostrar uma lista com 50 personagens apresentando: o ID, o nome e
a URL do personagem (valor: 1.0).
Salve os dados em um banco local (valor: 2.0).
OBS: seu app deve usar Executor e conter apenas 1 Activity e as demais telas deverão ser Fragments.
 */

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.renatocouto_avaliacaobimestral_parte1.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_litar_pokemon)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sair) {
            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}