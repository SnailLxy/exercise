package com.lixueyang.exercise.activity.commonintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Toast;

import com.lixueyang.exercise.R;
import com.lixueyang.exercise.databinding.ActivityContactsBinding;
import com.lixueyang.exercise.utils.IntentUtils;

public class ContactsIntentActivity extends AppCompatActivity {

  static final int REQUEST_SELECT_CONTACT = 100;
  private ActivityContactsBinding binding;
  private Uri contactUri;
  private Long currentId;
  private String currentLookupKey;

  public static void startContactsIntentActivity(Activity activity) {
    Intent intent = new Intent(activity, ContactsIntentActivity.class);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityContactsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    initView();
  }

  private void initView() {
    selectPhone();
    showContacts();
    editContact();
    insertContact();
  }

  private void showContacts() {
    binding.btnShowContactsIntent.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });
  }

  /**
   * 获取电话联系人
   * 这种方法不需要 READ_CONTACTS 权限
   * 注：常用的CONTENT_TYPE
   * 1、ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE 对应有电话号码的联系人中
   * 2、ContactsContract.CommonDataKinds.Email.CONTENT_TYPE  对应有电子邮件地址的联系人
   */
  private void selectPhone() {
    binding.btnSelectedContactsIntent.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_PICK);
      intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivityForResult(intent, REQUEST_SELECT_CONTACT);
      }
    });
  }

  private void editContact() {
    binding.btnEditContactsIntent.setOnClickListener(view -> {
      if (contactUri == null) {
        Toast.makeText(this, "请选择对应的联系人！", Toast.LENGTH_LONG).show();
        return;
      }

      Intent intent = new Intent(Intent.ACTION_EDIT);
      intent.setDataAndType(ContactsContract.Contacts.getLookupUri(currentId, currentLookupKey), ContactsContract.Contacts.CONTENT_ITEM_TYPE);
      String phoneNumber = binding.etPhoneNumber.getText().toString();
      if (!TextUtils.isEmpty(phoneNumber)) {
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
      }
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });
  }

  private void insertContact() {
    binding.btnInsertContactsIntent.setOnClickListener(view -> {
      Intent intent = new Intent(Intent.ACTION_INSERT);
      intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
      String name = binding.etContactsName.getText().toString();
      String phoneNumber = binding.etPhoneNumber.getText().toString();
      if (!TextUtils.isEmpty(name)) {
        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
      }
      if (!TextUtils.isEmpty(phoneNumber)) {
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
      }
      if (IntentUtils.isActivityAlive(intent, getPackageManager())) {
        startActivity(intent);
      }
    });

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK) {
      return;
    }
    switch (requestCode) {
      case REQUEST_SELECT_CONTACT:
        if (data == null) {
          return;
        }
        contactUri = data.getData();
        String[] projection = new String[]{
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY
        };
        if (contactUri != null) {
          Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
          if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            currentId = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            currentLookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
            binding.etContactsName.setText(name);
            binding.etPhoneNumber.setText(phoneNumber);
            cursor.close();
          }
        }
        break;
    }
  }
}