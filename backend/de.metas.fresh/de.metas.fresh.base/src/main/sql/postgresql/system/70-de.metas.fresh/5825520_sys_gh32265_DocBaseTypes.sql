-- Run mode: SWING_CLIENT

-- Bestellkontrolle — add two document base types for the order-checkup kinds
-- Reference 183: C_DocType.DocBaseType
-- IDs allocated: 544369 (Produktion base type), 544370 (Büro base type)

-- Value: BKP
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
SELECT 0,0,183,544369 /*From ID Server*/,TO_TIMESTAMP('2026-09-21 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','Y','Bestellkontrolle Produktion',TO_TIMESTAMP('2026-09-21 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BKP','Order Checkup Production'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List x WHERE x.AD_Reference_ID=183 AND x.Value='BKP')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=183 AND t.Value='BKP' AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Value: BKB
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
SELECT 0,0,183,544370 /*From ID Server*/,TO_TIMESTAMP('2026-09-21 12:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','Y','Bestellkontrolle Büro',TO_TIMESTAMP('2026-09-21 12:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'BKB','Order Checkup Office'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List x WHERE x.AD_Reference_ID=183 AND x.Value='BKB')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=183 AND t.Value='BKB' AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- English override (base column carries German; both English locales get the English label)
UPDATE AD_Ref_List_Trl SET Name='Order Checkup Production', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-21 12:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544369 AND AD_Language='en_US'
;

UPDATE AD_Ref_List_Trl SET Name='Order Checkup Office', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-21 12:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544370 AND AD_Language='en_US'
;

UPDATE AD_Ref_List_Trl SET Name='Order Checkup Production', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-21 12:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544369 AND AD_Language='en_GB'
;

UPDATE AD_Ref_List_Trl SET Name='Order Checkup Office', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-21 12:00:05.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Ref_List_ID=544370 AND AD_Language='en_GB'
;
