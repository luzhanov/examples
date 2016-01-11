package com.luzhanov.gamebook.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DriveConnector {

    public static final String APPLICATION_NAME = "GDrive-Luzhanov-Test";

    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final List<String> SCOPES = Arrays.asList(
            "email",
            "profile",
            "https://www.googleapis.com/auth/drive",
            "https://www.googleapis.com/auth/drive.file");

    private static DriveConnector instance;
    private Drive googleDriveClient;

    private DriveConnector() {
        try {
            initGoogleDriveServices();
        } catch (Exception e) {
            System.out.println("Failed to create the Google drive client. Please check your Internet connection and your Google credentials.");
            e.printStackTrace();
        }
    }

    public static DriveConnector getInstance() {
        if (instance == null) {
            instance = new DriveConnector();
        }
        return instance;
    }

    private GoogleCredential getCredential() throws Exception {
        File file = new File( this.getClass().getResource("/service-account.p12").toURI() );

        return new GoogleCredential.Builder()
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId("service@directed-potion-118213.iam.gserviceaccount.com")
                .setServiceAccountPrivateKeyFromP12File(file)     //notasecret
                .setServiceAccountScopes(SCOPES)
            //    .setServiceAccountUser("luzhanov@gmail.com")
                .build();
    }

    public void initGoogleDriveServices() throws Exception {
        GoogleCredential credential = getCredential();
        //Create a new authorized API client
        googleDriveClient = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Drive getGoogleDriveClient() {
        return googleDriveClient;
    }

}
