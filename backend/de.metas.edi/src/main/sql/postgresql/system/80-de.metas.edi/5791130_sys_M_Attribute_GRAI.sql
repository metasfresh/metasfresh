-- Create M_Attribute for GRAI (Global Returnable Asset Identifier)
-- Used on TU-level HUs to identify crates for EPCIS traceability events.
-- GRAI values are captured via RFID scanning or mobile UI (separate workstream).
-- The EPCIS export reads whatever GRAI value is stored at shipment time.

INSERT INTO M_Attribute (M_Attribute_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         Value, Name, Description,
                         AttributeValueType, IsInstanceAttribute, IsMandatory, IsAttrDocumentRelevant)
VALUES (540189, 0, 0, 'Y', TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100, TO_TIMESTAMP('2026-03-02 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'GRAI', 'GRAI', 'Global Returnable Asset Identifier — identifies returnable transport items (crates, pallets) per GS1 standard',
        'S', 'Y', 'N', 'N');
