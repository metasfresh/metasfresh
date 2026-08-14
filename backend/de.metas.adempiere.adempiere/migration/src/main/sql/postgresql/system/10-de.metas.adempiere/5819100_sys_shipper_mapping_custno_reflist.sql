-- nShift shipper mapping: generic CustNo support.
-- New mapping ATTRIBUTE TYPES (AD_Reference 541999) SenderCustNo / ReceiverCustNo -> set the nShift address CustNo
-- of the sender / receiver address from a mapping rule.
-- New mapping ATTRIBUTE VALUE (AD_Reference 542001) CustomValueString1 -> a generic value read from the shipper
-- config additional property "CustomValueString1" (carrier-agnostic on purpose: e.g. for DHL Freight the consignee
-- id is stored there and a ReceiverCustNo rule routes it into the address CustNo).
-- Ref-list values only; the per-shipper M_Shipper_Mapping_Config rows and the config value are instance data.

-- AD_Ref_List: SenderCustNo (attribute type)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544339 /*From ID Server*/,541999,
        TO_TIMESTAMP('2026-08-14 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Lieferant Kundennr.',
        TO_TIMESTAMP('2026-08-14 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'SenderCustNo','SenderCustNo');

INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544339 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-14 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Lieferant Kundennr.',
       TO_TIMESTAMP('2026-08-14 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544339 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Ref_List_Trl SET Name='Sender Customer No.', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544339 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544339 AND AD_Language IN ('de_DE','de_CH');

-- AD_Ref_List: ReceiverCustNo (attribute type)
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544340 /*From ID Server*/,541999,
        TO_TIMESTAMP('2026-08-14 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Empfänger Kundennr.',
        TO_TIMESTAMP('2026-08-14 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'ReceiverCustNo','ReceiverCustNo');

INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544340 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-14 10:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Empfänger Kundennr.',
       TO_TIMESTAMP('2026-08-14 10:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544340 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Ref_List_Trl SET Name='Receiver Customer No.', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544340 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544340 AND AD_Language IN ('de_DE','de_CH');

-- AD_Ref_List: CustomValueString1 (attribute value; read from shipper-config additional property "CustomValueString1")
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544341 /*From ID Server*/,542001,
        TO_TIMESTAMP('2026-08-14 10:00:08','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Benutzerdef. Text 1',
        TO_TIMESTAMP('2026-08-14 10:00:08','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'CustomValueString1','CustomValueString1');

INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544341 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-14 10:00:09','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Benutzerdef. Text 1',
       TO_TIMESTAMP('2026-08-14 10:00:09','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544341 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Ref_List_Trl SET Name='Custom Text 1', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544341 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544341 AND AD_Language IN ('de_DE','de_CH');

-- AD_Ref_List: CustomValueString2 (attribute value; read from shipper-config additional property "CustomValueString2")
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544342 /*From ID Server*/,542001,
        TO_TIMESTAMP('2026-08-14 10:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Benutzerdef. Text 2',
        TO_TIMESTAMP('2026-08-14 10:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'CustomValueString2','CustomValueString2');

INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544342 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-14 10:00:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Benutzerdef. Text 2',
       TO_TIMESTAMP('2026-08-14 10:00:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544342 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Ref_List_Trl SET Name='Custom Text 2', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544342 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:15','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544342 AND AD_Language IN ('de_DE','de_CH');

-- AD_Ref_List: CustomValueString3 (attribute value; read from shipper-config additional property "CustomValueString3")
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,
                         Created,CreatedBy,Description,EntityType,IsActive,Name,
                         Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544343 /*From ID Server*/,542001,
        TO_TIMESTAMP('2026-08-14 10:00:16','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,'D','Y','Benutzerdef. Text 3',
        TO_TIMESTAMP('2026-08-14 10:00:16','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'CustomValueString3','CustomValueString3');

INSERT INTO AD_Ref_List_Trl (AD_Client_ID,AD_Org_ID,AD_Language,AD_Ref_List_ID,
                             Created,CreatedBy,Description,IsActive,IsTranslated,Name,
                             Updated,UpdatedBy)
SELECT 0,0,l.AD_Language,544343 /*From ID Server*/,
       TO_TIMESTAMP('2026-08-14 10:00:17','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       NULL,'Y','N','Benutzerdef. Text 3',
       TO_TIMESTAMP('2026-08-14 10:00:17','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Language l WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Ref_List_ID=544343 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Ref_List_Trl SET Name='Custom Text 3', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:18','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544343 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 10:00:19','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544343 AND AD_Language IN ('de_DE','de_CH');
