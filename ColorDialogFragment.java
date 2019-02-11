package com.example.user.doodlz2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

public class ColorDialogFragment extends DialogFragment {
    private SeekBar alphaSeekBar, redSeekBar, greenSeekBar, blueSeekBar;
    private View colorView;
    private int color;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        MainActivityFragment fragment = getDoodleFragment();
        if (fragment != null) fragment.setDialogOnScreen(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MainActivityFragment fragment = getDoodleFragment();
        if (fragment != null) fragment.setDialogOnScreen(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_color, null);
        builder.setView(colorDialogView);
        builder.setTitle(R.string.title_color_dialog);

        alphaSeekBar = colorDialogView.findViewById(R.id.alphaSeekBar);
        redSeekBar = colorDialogView.findViewById(R.id.redSeekBar);
        greenSeekBar = colorDialogView.findViewById(R.id.greenSeekBar);
        blueSeekBar = colorDialogView.findViewById(R.id.blueSeekBar);
        colorView = colorDialogView.findViewById(R.id.colorView);

        SeekBar[] bars = {alphaSeekBar, redSeekBar, greenSeekBar, blueSeekBar};
        for (SeekBar bar : bars) bar.setOnSeekBarChangeListener(colorChangedListener);

        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        color = doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.green(color));
        blueSeekBar.setProgress(Color.blue(color));
        colorView.setBackgroundColor(color);

        builder.setPositiveButton(R.string.button_set_color, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                doodleView.setDrawingColor(color);
            }
        });
        return builder.create();
    }

    private final SeekBar.OnSeekBarChangeListener colorChangedListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
            if (fromUser) {
                color = Color.argb(alphaSeekBar.getProgress(), redSeekBar.getProgress(),
                        greenSeekBar.getProgress(), blueSeekBar.getProgress());
                colorView.setBackgroundColor(color);
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    protected MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager().findFragmentById(R.id.doodleFragment);
    }
}
