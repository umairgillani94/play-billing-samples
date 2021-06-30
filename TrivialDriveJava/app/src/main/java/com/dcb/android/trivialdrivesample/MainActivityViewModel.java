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
package com.dcb.android.trivialdrivesample;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/*
    This is used for any business logic, as well as to echo LiveData from the BillingRepository.
 */
public class MainActivityViewModel extends ViewModel {
    static final String TAG = GameViewModel.class.getSimpleName();
    private final TrivialDriveRepository tdr;

    public MainActivityViewModel(TrivialDriveRepository trivialDriveRepository) {
        tdr = trivialDriveRepository;
    }

    public LiveData<Integer> getMessages() {
        return tdr.getMessages();
    }

    public void debugConsumePremium() {
        tdr.debugConsumePremium();
    }

    public LifecycleObserver getBillingLifecycleObserver() {
        return tdr.getBillingLifecycleObserver();
    }

    public static class MainActivityViewModelFactory implements
            ViewModelProvider.Factory {
        private final TrivialDriveRepository trivialDriveRepository;

        public MainActivityViewModelFactory(TrivialDriveRepository tdr) {
            trivialDriveRepository = tdr;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MainActivityViewModel.class)) {
                return (T) new MainActivityViewModel(trivialDriveRepository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
