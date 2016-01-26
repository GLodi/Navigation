package com.giuliolodi.navigation;

import android.app.Notification;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentNotification extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_notification, container, false);

        final Button button1 = (Button) v.findViewById(R.id.button1);
        final Button button2 = (Button) v.findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.Builder notiBuilder = new Notification.Builder(getActivity())
                        .setSmallIcon(R.drawable.notification_template_icon_bg)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentTitle("Sample notification")
                        .setContentText("Questa e' una notifica di prova");
            }
        });

        return v;
    }
}
