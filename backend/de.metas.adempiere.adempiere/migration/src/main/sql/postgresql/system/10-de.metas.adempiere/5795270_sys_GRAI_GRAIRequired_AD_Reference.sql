-- 2026-03-23
-- AD_Reference (List) and AD_Ref_List entries for GRAIRequired

-- AD_Reference: GRAIRequired
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, ValidationType, EntityType, AD_Reference_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        'GRAIRequired', 'L', 'de.metas.handlingunits', 542081 /*From ID Server*/);

-- AD_Ref_List: N = Nein / No
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, AD_Reference_ID, EntityType, AD_Ref_List_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        'N', 'Nein', 542081 /*From ID Server*/, 'de.metas.handlingunits', 544172 /*From ID Server*/);

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Ref_List_ID, Name, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        544172 /*From ID Server*/, 'No', NULL, 'Y');

-- AD_Ref_List: Y = Ja / Yes
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, AD_Reference_ID, EntityType, AD_Ref_List_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        'Y', 'Ja', 542081 /*From ID Server*/, 'de.metas.handlingunits', 544173 /*From ID Server*/);

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Ref_List_ID, Name, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        544173 /*From ID Server*/, 'Yes', NULL, 'Y');

-- AD_Ref_List: D = Ja, mit Dummy-GRAIs / Yes, with dummy GRAIs
INSERT INTO AD_Ref_List (AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, Name, AD_Reference_ID, EntityType, AD_Ref_List_ID)
VALUES (0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        'D', 'Ja, mit Dummy-GRAIs', 542081 /*From ID Server*/, 'de.metas.handlingunits', 544174 /*From ID Server*/);

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Ref_List_ID, Name, Description, IsTranslated)
VALUES ('en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0, TO_TIMESTAMP('2026-03-23 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        544174 /*From ID Server*/, 'Yes, with dummy GRAIs', NULL, 'Y');
