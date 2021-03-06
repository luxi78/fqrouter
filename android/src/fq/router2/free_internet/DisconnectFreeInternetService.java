package fq.router2.free_internet;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import fq.router2.feedback.HandleFatalErrorIntent;
import fq.router2.utils.HttpUtils;
import fq.router2.utils.LogUtils;

public class DisconnectFreeInternetService extends IntentService {
    public DisconnectFreeInternetService() {
        super("DisconnectFreeInternet");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        disconnect(this);
    }

    public static void disconnect(Context context) {
        try {
            HttpUtils.post("http://127.0.0.1:2515/free-internet/disconnect");
            context.sendBroadcast(new FreeInternetChangedIntent(false));
        } catch (Exception e) {
            context.sendBroadcast(new HandleFatalErrorIntent(LogUtils.e("failed to disconnect from free internet", e)));
        }
    }

    public static void execute(Context context) {
        context.startService(new Intent(context, DisconnectFreeInternetService.class));
    }
}
