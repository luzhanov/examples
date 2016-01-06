package com.luzhanov.gamebook.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class DriveConnector {

    private final static String CLIENT_ID = "PASTE_YOUR_CLIENT_ID_HERE";
    private final static String CLIENT_SECRET = "PASTE_YOUR_CLIENT_SECRET_HERE";

    private final static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";

    private static DriveConnector instance;
    private Drive googleDriveClient;

    private DriveConnector() {
        try {
            initGoogleDriveServices();
        } catch (IOException e) {
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

    public void initGoogleDriveServices() throws IOException {
        HttpTransport httpTransport = new NetHttpTransport();
        com.google.api.client.json.JsonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
                .setAccessType("online")
                .setApprovalPrompt("auto").build();

        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();

        //todo: use logger
        System.out.println("Please open the following URL in your browser then type the authorization code:");
        System.out.println("  " + url);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();

        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);

        //Create a new authorized API client
        googleDriveClient = new Drive.Builder(httpTransport, jsonFactory, credential).build();
    }

    public Drive getGoogleDriveClient() {
        return googleDriveClient;
    }

}
