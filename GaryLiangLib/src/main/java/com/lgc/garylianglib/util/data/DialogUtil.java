package com.lgc.garylianglib.util.data;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/12/20 16:24
 *     desc   : 单一显示的对话框
 *     version: 1.0
 * </pre>
 */
public class DialogUtil {
    public static void showSingleDialog(AppCompatActivity activity, String title, String msg, String positive,
                                        DialogInterface.OnClickListener listener) {
        createSingleDialog(title, msg, positive, listener)
                .show(activity.getSupportFragmentManager(), activity.getClass().getSimpleName() + "Dialog");
    }

    public static void showDoubleDialog(AppCompatActivity activity, String title, String msg, String positive,
                                        String negative, DialogInterface.OnClickListener listener) {
        createDoubleDialog(title, msg, positive, negative, listener)
                .show(activity.getSupportFragmentManager(), activity.getClass().getSimpleName() + "Dialog");
    }


    public static DialogFragment createSingleDialog(String title, String msg, String positive,
                                                    DialogInterface.OnClickListener listener) {
        return AlertDialogFragment.newSingleInstance(title, msg, positive, listener);
    }

    public static DialogFragment createDoubleDialog(String title, String msg, String positive,
                                                    String negative, DialogInterface.OnClickListener listener) {
        return AlertDialogFragment.newDoubleInstance(title, msg, positive, negative, listener);
    }

    public static class AlertDialogFragment extends DialogFragment {

        private static DialogInterface.OnClickListener mListener;

        public static AlertDialogFragment newSingleInstance(String title, String msg, String positive,
                                                            DialogInterface.OnClickListener listener) {
            return newDoubleInstance(title, msg, positive, null, listener);
        }

        public static AlertDialogFragment newDoubleInstance(String title, String msg, String positive,
                                                            String negative, DialogInterface.OnClickListener listener) {
            AlertDialogFragment frag = new AlertDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            args.putString("msg", msg);
            args.putString("negative", negative);
            args.putString("positive", positive);
            mListener = listener;
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = getArguments().getString("title");
            String msg = getArguments().getString("msg");
            String positive = getArguments().getString("positive");
            String negative = getArguments().getString("negative");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton(positive, mListener);

            if (negative != null) {
                builder.setNegativeButton(negative, mListener);
            }
            return builder.create();
        }
    }
}
