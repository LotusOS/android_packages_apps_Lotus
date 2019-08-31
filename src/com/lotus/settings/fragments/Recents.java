/*
 * Copyright (C) 2019 LotusOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lotus.settings.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;

import com.android.settings.R;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.util.lotus.LotusUtils;
import com.android.settings.SettingsPreferenceFragment;

import java.util.Arrays;
import java.util.HashSet;

public class Recents extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String KEY_CATEGORY_SLIM = "slim_recents_category";
    private static final String RECENTS_LAYOUT_STYLE_PREF = "recents_layout_style";

    private PreferenceCategory mSlimCat;
    private ListPreference mRecentsLayoutStylePref;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.lotus_settings_recents);

        ContentResolver resolver = getActivity().getContentResolver();

        // recents layout style
        mRecentsLayoutStylePref = (ListPreference) findPreference(RECENTS_LAYOUT_STYLE_PREF);
        int type = Settings.System.getInt(resolver,
                Settings.System.RECENTS_LAYOUT_STYLE, 0);
        mRecentsLayoutStylePref.setValue(String.valueOf(type));
        mRecentsLayoutStylePref.setSummary(mRecentsLayoutStylePref.getEntry());
        mRecentsLayoutStylePref.setOnPreferenceChangeListener(this);

        mSlimCat = (PreferenceCategory) findPreference(KEY_CATEGORY_SLIM);
        updateRecentsState(type);
    }

    public void updateRecentsState(int type) {
        switch(type) {
            case 0:
                mSlimCat.setEnabled(false);
                Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.USE_SLIM_RECENTS, 0);
                break;
            case 1:
                mSlimCat.setEnabled(false);
                Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.USE_SLIM_RECENTS, 0);
                break;
            case 2:
                mSlimCat.setEnabled(false);
                Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.USE_SLIM_RECENTS, 0);
                break;
            case 3:
                mSlimCat.setEnabled(false);
                Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.USE_SLIM_RECENTS, 0);
                break;
            case 4:
                mSlimCat.setEnabled(true);
                Settings.System.putInt(getActivity().getContentResolver(),
                Settings.System.USE_SLIM_RECENTS, 1);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mRecentsLayoutStylePref) {
            int type = Integer.valueOf((String) objValue);
            int index = mRecentsLayoutStylePref.findIndexOfValue((String) objValue);
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.RECENTS_LAYOUT_STYLE, type);
            mRecentsLayoutStylePref.setSummary(mRecentsLayoutStylePref.getEntries()[index]);
            updateRecentsState(type);
            LotusUtils.showSystemUiRestartDialog(getContext());
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.LOTUS_SETTINGS;
    }
}