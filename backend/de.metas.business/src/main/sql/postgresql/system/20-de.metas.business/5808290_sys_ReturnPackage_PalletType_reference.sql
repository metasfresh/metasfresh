-- Run mode: SWING_CLIENT
--
-- Ruecknahme Gebinde (return-package) feature, core (EntityType 'D').
--
-- This script creates the pallet-type reference list used by the new column
-- C_Order_ReturnPackage.PalletType (created in the next migration 5808300).
--
-- AD_Reference 542107 'Ruecknahme Palettentyp' (en_US 'Return Pallet Type'), ValidationType 'L' (list).
--   AD_Ref_List 544266: Value 'EUR' / Name 'EUR'
--   AD_Ref_List 544267: Value 'H1'  / Name 'H1'
-- Labels are identical in German and English, so the _Trl en_US rows keep the same text.

-- AD_Reference: Ruecknahme Palettentyp
-- 2026-06-17T08:00:00.000Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,IsActive,IsOrderByValue,Created,CreatedBy,Updated,UpdatedBy,ValidationType,Name,EntityType) VALUES (0,0,542107 /*From ID Server*/,'Y','N',TO_TIMESTAMP('2026-06-17 08:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-17 08:00:00','YYYY-MM-DD HH24:MI:SS'),100,'L','Rücknahme Palettentyp','D')
;

-- 2026-06-17T08:00:01.000Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542107 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2026-06-17T08:00:02.000Z
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Return Pallet Type', Updated=TO_TIMESTAMP('2026-06-17 08:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=542107
;

-- AD_Ref_List: EUR
-- 2026-06-17T08:00:03.000Z
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Value,ValueName,Name,EntityType) VALUES (542107,0,0,544266 /*From ID Server*/,'Y',TO_TIMESTAMP('2026-06-17 08:00:03','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-17 08:00:03','YYYY-MM-DD HH24:MI:SS'),100,'EUR','EUR','EUR','D')
;

-- 2026-06-17T08:00:04.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544266 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-17T08:00:05.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='EUR', Updated=TO_TIMESTAMP('2026-06-17 08:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544266
;

-- AD_Ref_List: H1
-- 2026-06-17T08:00:06.000Z
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Value,ValueName,Name,EntityType) VALUES (542107,0,0,544267 /*From ID Server*/,'Y',TO_TIMESTAMP('2026-06-17 08:00:06','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-17 08:00:06','YYYY-MM-DD HH24:MI:SS'),100,'H1','H1','H1','D')
;

-- 2026-06-17T08:00:07.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544267 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-17T08:00:08.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='H1', Updated=TO_TIMESTAMP('2026-06-17 08:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544267
;

-- Mark the German (de_DE, de_CH) translations as translated too (labels are identical to the base text).
-- 2026-06-17T08:00:09.000Z
UPDATE AD_Reference_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 08:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=542107
;
-- 2026-06-17T08:00:10.000Z
UPDATE AD_Reference_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 08:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=542107
;
-- 2026-06-17T08:00:11.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 08:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544266
;
-- 2026-06-17T08:00:12.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 08:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544266
;
-- 2026-06-17T08:00:13.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 08:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544267
;
-- 2026-06-17T08:00:14.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 08:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544267
;
