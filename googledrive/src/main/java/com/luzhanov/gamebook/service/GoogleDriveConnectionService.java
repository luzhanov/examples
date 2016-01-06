package com.luzhanov.gamebook.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleDriveConnectionService {

    public List<String> listAllFiles() {
        try {
            List<String> allNames = new ArrayList<>();

            Drive driveClient = DriveConnector.getInstance().getGoogleDriveClient();
            Drive.Files.List request = driveClient.files().list();

            FileList files = request.execute();
            for(File file : files.getItems()) {
                allNames.add(file.getTitle());
            }

            return allNames;
        } catch (IOException e) {
            //todo: use logger
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
