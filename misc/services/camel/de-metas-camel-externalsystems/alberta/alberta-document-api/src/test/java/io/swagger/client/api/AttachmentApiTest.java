/*
 * Dokumente - Warenwirtschaft (Basis)
 * Synchronisation der Dokumente aus Alberta
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.ApiException;
import io.swagger.client.model.ArrayOfAttachments;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;

/**
 * API tests for AttachmentApi
 */
@Ignore
public class AttachmentApiTest {

    private final AttachmentApi api = new AttachmentApi();

    /**
     * Anlagen abrufen
     *
     * Szenario - das WaWi fragt in einem bestimmten Intervall bei Alberta nach, ob es neue hochgeladene Anlagen gibt
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAllAttachmentsTest() throws ApiException {
        String apiKey = null;
        String createdAfter = null;
        BigDecimal type = null;
        ArrayOfAttachments response = api.getAllAttachments(apiKey, createdAfter, type);

        // TODO: test validations
    }
    /**
     * Einzelne Anlage abrufen
     *
     * Szenario - das WaWi ruft ein bestimmte Anlage als PDF ab
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getSingleAttachmentTest() throws ApiException {
        String apiKey = null;
        String id = null;
        File response = api.getSingleAttachment(apiKey, id);

        // TODO: test validations
    }
}
