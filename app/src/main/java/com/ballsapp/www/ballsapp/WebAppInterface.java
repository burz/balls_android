package com.ballsapp.www.ballsapp;

import android.content.Context;
import android.telephony.SmsManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by burz on 4/13/15.
 */

class WebAppInterface {
    private Context context;
    private WebView web_view;

    public WebAppInterface(Context new_context, WebView new_web_view) {
        context = new_context;
        web_view = new_web_view;
    }

    @JavascriptInterface
    public void sendInvite(String phone_number, String league_name, String invite_path) {
        String message = "Hey! Come play beer pong with me in my league \"" + league_name +
                "\" in BallsApp! Join here: " + invite_path;
        sendSMS(phone_number, message);
    }

    @JavascriptInterface
    public void loadContacts() {
        web_view.post(new Runnable() {
            @Override
            public void run() {
                String order_by = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
                Cursor contact_cursor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, order_by);
                while(contact_cursor.moveToNext()) {
                    String name = contact_cursor.getString(
                            contact_cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone_number = contact_cursor.getString(
                            contact_cursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if(name.charAt(0) != '#') {
                        String js = "add_contact('" + name + "','" + phone_number + "')";
                        web_view.loadUrl("javascript:" + js);
                    }
                }
                contact_cursor.close();
            }
        });
    }

    private void sendSMS(String phone_number, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone_number, null, message, null, null);
    }
}
