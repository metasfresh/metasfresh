-- nShift: new mapping attribute source values ReceiverBPartnerAttention / SenderBPartnerAttention

-- AD_Ref_List: ReceiverBPartnerAttention
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544238 /*From ID Server*/,542001,
        TO_TIMESTAMP('2026-05-28 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Empfänger z. Hd.',
        TO_TIMESTAMP('2026-05-28 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'ReceiverBPartnerAttention','ReceiverBPartnerAttention');

-- AD_Ref_List_Trl skeleton for ReceiverBPartnerAttention
INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544238 /*From ID Server*/,
       TO_TIMESTAMP('2026-05-28 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Empfänger z. Hd.',
       TO_TIMESTAMP('2026-05-28 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544238 AND tt.AD_Language=l.AD_Language);

-- English translation for ReceiverBPartnerAttention
UPDATE AD_Ref_List_Trl SET Name='Receiver Attention', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-05-28 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544238 AND AD_Language='en_US';

-- AD_Ref_List: SenderBPartnerAttention
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544239 /*From ID Server*/,542001,
        TO_TIMESTAMP('2026-05-28 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Lieferant z. Hd.',
        TO_TIMESTAMP('2026-05-28 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'SenderBPartnerAttention','SenderBPartnerAttention');

-- AD_Ref_List_Trl skeleton for SenderBPartnerAttention
INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544239 /*From ID Server*/,
       TO_TIMESTAMP('2026-05-28 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Lieferant z. Hd.',
       TO_TIMESTAMP('2026-05-28 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544239 AND tt.AD_Language=l.AD_Language);

-- English translation for SenderBPartnerAttention
UPDATE AD_Ref_List_Trl SET Name='Sender Attention', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-05-28 10:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544239 AND AD_Language='en_US';

-- M_Shipper_Mapping_Config: ReceiverAttention source for nShift base shipper
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,M_Shipper_Mapping_Config_ID,
                                      Created,CreatedBy,IsActive,
                                      M_Shipper_ID,MappingAttributeType,MappingAttributeValue,
                                      SeqNo,Updated,UpdatedBy)
VALUES (1000000,0,540021 /*From ID Server*/,
        TO_TIMESTAMP('2026-05-28 10:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Y',
        540019,'ReceiverAttention','ReceiverBPartnerAttention',
        10,
        TO_TIMESTAMP('2026-05-28 10:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100);
