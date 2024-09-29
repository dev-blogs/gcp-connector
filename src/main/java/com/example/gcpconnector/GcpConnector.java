package com.example.gcpconnector;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class GcpConnector {
    private static final String BUCKET_NAME = "gcp-bucker-spark-learning-374112";
    private static final String KEY_PATH = "/user/data/key.json";
    private static final String FILE_PATH = "/opt/app/data.txt";

    public void checkGcp() throws IOException {
        String SERVICE_ACCOUNT_JSON_PATH = KEY_PATH;

        Storage storage =
                StorageOptions.newBuilder()
                        //.setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
                        .build()
                        .getService();

        Bucket bucket = storage.get(BUCKET_NAME);

        Page<Blob> blobs = bucket.list();
        for (Blob blob : blobs.getValues()) {
            System.out.println(blob.getName());
        }

        String objectName = "test-1";

        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        // Optional: set a generation-match precondition to avoid potential race
        // conditions and data corruptions. The request returns a 412 error if the
        // preconditions are not met.
        Storage.BlobWriteOption precondition;
        if (storage.get(BUCKET_NAME, objectName) == null) {
            // For a target object that does not yet exist, set the DoesNotExist precondition.
            // This will cause the request to fail if the object is created before the request runs.
            precondition = Storage.BlobWriteOption.doesNotExist();
        } else {
            // If the destination already exists in your bucket, instead set a generation-match
            // precondition. This will cause the request to fail if the existing object's generation
            // changes before the request runs.
            precondition =
                    Storage.BlobWriteOption.generationMatch(
                            storage.get(BUCKET_NAME, objectName).getGeneration());
        }
        storage.createFrom(blobInfo, Paths.get(FILE_PATH), precondition);

        System.out.println(
                "File " + FILE_PATH + " uploaded to bucket " + BUCKET_NAME + " as " + objectName);
    }

    public void checkGcp2() throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(KEY_PATH));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        Bucket bucket = storage.get(BUCKET_NAME);
        Page<Blob> blobs = bucket.list();
        for (Blob blob : blobs.getValues()) {
            System.out.println(blob.getName());
        }

        BlobId blobId = BlobId.of(BUCKET_NAME, FILE_PATH);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        Blob blob = storage.create(blobInfo, "a simple blob".getBytes(UTF_8));
    }
}
