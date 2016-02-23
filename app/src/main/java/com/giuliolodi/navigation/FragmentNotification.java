package com.giuliolodi.navigation;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
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

        final Context context = getActivity().getApplicationContext();

        final Button button1 = (Button) v.findViewById(R.id.button1);
        final Button button2 = (Button) v.findViewById(R.id.button2);
        final NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.Builder mBuilder = new Notification.Builder(getActivity())
                        .setSmallIcon(R.drawable.weather_snowy)
                        .setContentTitle("Sample notification")
                        .setContentText("Questa e' una notifica di prova");

                int mNotificationId = 001;
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
            }
        });

        return v;
    }
}
