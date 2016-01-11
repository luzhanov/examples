package com.luzhanov.gamebook.service;

import org.junit.Test;

public class GoogleDriveConnectionServiceTest {

    private GoogleDriveConnectionService service = new GoogleDriveConnectionService();

    @Test
    public void testListAllFiles() throws Exception {
        service.listAllFiles();
    }
}