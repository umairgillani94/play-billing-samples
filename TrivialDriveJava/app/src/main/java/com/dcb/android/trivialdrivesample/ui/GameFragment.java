/*
 * Copyright (C) 2021 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dcb.android.trivialdrivesample.ui;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.dcb.android.trivialdrivesample.GameViewModel;
import com.dcb.android.trivialdrivesample.R;
import com.dcb.android.trivialdrivesample.TrivialDriveApplication;
import com.dcb.android.trivialdrivesample.databinding.FragmentGameBinding;

/**
 * This Fragment represents the game world, but it really just exists to bind the variables used
 * in the views that use DataBinding to observe the ViewModel.
 * <p>
 * There's nothing about billing here; billing informationis abstracted into the BillingRepository.
 */
public class GameFragment extends androidx.fragment.app.Fragment {
    final private String TAG = GameFragment.class.getSimpleName();

    private GameViewModel gameViewModel;
    private FragmentGameBinding binding;

    /*
        We use data binding to bind the game view with this fragment, and this allows us to
        automatically observe changes in our TrivialDriveViewModel from our layout. The ViewModel
        handles most of the UI and game-related business logic.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false);
        // This allows data binding to automatically observe any LiveData we pass in
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG, "onViewCreated");

        GameViewModel.GameViewModelFactory gameViewModelFactory =
                new GameViewModel.GameViewModelFactory(
                        ((TrivialDriveApplication) getActivity().getApplication()).appContainer
                                .trivialDriveRepository);

        gameViewModel = new ViewModelProvider(this, gameViewModelFactory)
                .get(GameViewModel.class);

        TypedArray gasTankResourceIds = getResources().obtainTypedArray(R.array.gas_tank_images);

        // Set the variables up that we'll be using in data binding
        binding.setGasTankImages(gasTankResourceIds);
        binding.setGvm(gameViewModel);
        binding.setGameFragment(this);
    }

    public void drive() {
        Log.d(TAG, "Drive");
        gameViewModel.drive();
    }

    public void purchase(View view) {
        Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_makePurchaseFragment);
    }
}
