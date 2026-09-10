-- gh#27699: Add AD_Message for SSCC label report when no HUs have SSCC18 attribute

-- AD_Message: HU_Labels_SSCC_LU.NoSSCC
INSERT INTO AD_Message (AD_Message_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Value, MsgText, MsgType, EntityType, ErrorCode)
VALUES (545638, 0, 0, 'Y', '2026-02-28 11:00', 100, '2026-02-28 11:00', 100,
        'HU_Labels_SSCC_LU.NoSSCC',
        'None of the selected Handling Units have an SSCC18 attribute. SSCC labels can only be printed for HUs that have been assigned an SSCC.',
        'E',
        'de.metas.handlingunits',
        'HU_LABELS_SSCC_LU_NO_SSCC')
;

-- Translation: de_DE
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545638, 'de_DE', 0, 0, 'Y', '2026-02-28 11:00', 100, '2026-02-28 11:00', 100, 'Y',
        'Keine der ausgewählten Handling Units hat ein SSCC18-Merkmal. SSCC-Etiketten können nur für HUs gedruckt werden, denen eine SSCC zugewiesen wurde.')
;

-- Translation: de_CH
INSERT INTO AD_Message_Trl (AD_Message_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, IsTranslated, MsgText)
VALUES (545638, 'de_CH', 0, 0, 'Y', '2026-02-28 11:00', 100, '2026-02-28 11:00', 100, 'Y',
        'Keine der ausgewählten Handling Units hat ein SSCC18-Merkmal. SSCC-Etiketten können nur für HUs gedruckt werden, denen eine SSCC zugewiesen wurde.')
;
