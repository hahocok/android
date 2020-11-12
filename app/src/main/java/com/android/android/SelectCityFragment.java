package com.android.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SelectCityFragment extends Fragment implements Constants {

    private Button btnSelectCity;
    private EditText etSelectCity;
    private CheckBox cbCloudiness;
    private CheckBox cbHumidity;
    private MainPresenter presenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_select_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        btnSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etSelectCity.getText().toString();
                boolean isCloudiness = cbCloudiness.isChecked();
                boolean isHumidity = cbHumidity.isChecked();

                presenter.setTemperature(getRandomTemp());

                final MainActivityFragment fragment = new MainActivityFragment();
                Bundle bundle = new Bundle();
                bundle.putString(CITY, city);
                bundle.putBoolean(CLOUDINESS, isCloudiness);
                bundle.putBoolean(HUMIDITY, isHumidity);
                fragment.setArguments(bundle);

                Snackbar.make(v, "Применить изменения?", Snackbar.LENGTH_LONG).setAction("ОК", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startMainFragment(fragment);
                    }
                }).show();

//                getFragmentManager().
//                        beginTransaction()
//                        .replace(R.id.fragment_container, fragment)
//                        .commit();
            }
        });

        readIntent();
    }

    private void startMainFragment(MainActivityFragment fragment) {
        getFragmentManager().
                beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private String getRandomTemp() {
        int min = -30;
        int max = 30;
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        i += min;
        return String.valueOf(i);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(CITY, etSelectCity.getText().toString());
        outState.putBoolean(CLOUDINESS, cbCloudiness.isChecked());
        outState.putBoolean(HUMIDITY, cbHumidity.isChecked());
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        presenter = MainPresenter.getInstance();
        btnSelectCity = view.findViewById(R.id.btn_select_city);
        cbCloudiness = view.findViewById(R.id.cb_cloudiness);
        cbHumidity = view.findViewById(R.id.cb_humidity);
        etSelectCity = view.findViewById(R.id.et_select_city);
    }

    private void readIntent() {
        Bundle args = getArguments();
        String city = null;
        boolean isCloudiness = false;
        boolean isHumidity = false;
        if (args != null) {
            city = args.getString(CITY);
            isCloudiness = args.getBoolean(CLOUDINESS, false);
            isHumidity = args.getBoolean(HUMIDITY, false);
        }

        if (city != null && city.equals(getResources().getString(R.string.city))) {
            etSelectCity.setHint(getResources().getString(R.string.enter_city));
        } else {
            etSelectCity.setText(city);
        }

        cbCloudiness.setChecked(isCloudiness);
        cbHumidity.setChecked(isHumidity);
    }
}
