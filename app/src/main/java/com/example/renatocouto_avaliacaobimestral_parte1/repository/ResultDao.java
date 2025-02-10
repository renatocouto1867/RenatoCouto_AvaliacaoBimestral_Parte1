package com.example.renatocouto_avaliacaobimestral_parte1.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.renatocouto_avaliacaobimestral_parte1.entity.Result;

import java.util.List;

@Dao
public interface ResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Query("SELECT * FROM pokemon_table ORDER BY name ASC")
    List<Result> getAllResults();

    @Query("DELETE FROM pokemon_table")
    void deleteAll();

    /**
     * para salvar em lote.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Result> results);
}
