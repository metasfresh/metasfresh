-- WEBUI_PP_Order_Receipt (AD_Process 540773): new parameters 'Menge' / 'Menge CU' / helper for unlimited capacity; 'Menge pro TU' becomes hidden.
-- Own elements (distinct ColumnNames) are created so the existing I_Product.QtyCU element (583543) is not relabelled.

INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,EntityType,Name,PrintName)
VALUES (585562 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-10-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'QtyTotalReceived','de.metas.ui.web','Menge','Menge');

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,IsUseCustomization)
SELECT l.AD_Language,t.AD_Element_ID,t.Name,t.PrintName,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y','N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585562
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Menge',PrintName='Menge',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585562 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Quantity',PrintName='Quantity',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585562 AND AD_Language='en_US';

-- guarded: a release-branch migration may have created this element (same ID and values) before this script runs
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,EntityType,Name,PrintName)
SELECT 585563 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-23 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-10-23 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsReceived','de.metas.ui.web','Menge CU','Menge CU'
WHERE NOT EXISTS (SELECT 1 FROM AD_Element e WHERE e.AD_Element_ID=585563);

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,IsUseCustomization)
SELECT l.AD_Language,t.AD_Element_ID,t.Name,t.PrintName,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y','N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585563
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Menge CU',PrintName='Menge CU',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585563 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Quantity CU',PrintName='Quantity CU',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585563 AND AD_Language='en_US';

INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,EntityType,Name,PrintName)
VALUES (585564 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-23 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-10-23 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'IsPIPUnlimitedCapacity','de.metas.ui.web','Unbegrenzte Kapazität','Unbegrenzte Kapazität');

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,IsUseCustomization)
SELECT l.AD_Language,t.AD_Element_ID,t.Name,t.PrintName,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y','N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585564
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Unbegrenzte Kapazität',PrintName='Unbegrenzte Kapazität',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585564 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl SET Name='Unlimited capacity',PrintName='Unlimited capacity',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585564 AND AD_Language='en_US';

INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,AD_Process_ID,SeqNo,AD_Reference_ID,ColumnName,IsCentrallyMaintained,FieldLength,IsMandatory,AD_Element_ID,EntityType,DisplayLogic,ReadOnlyLogic)
VALUES (543358 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-23 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-10-23 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Menge',540773,55,29,'QtyTotalReceived','Y',0,'Y',585562,'de.metas.ui.web','@M_HU_PI_Item_Product_ID/-1@>0 & @M_HU_PI_Item_Product_ID/-1@!101 & @IsReceiveIndividualCUs/''N''@=''N''','@IsPIPUnlimitedCapacity/''N''@=''N''');

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Process_Para_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_Para_ID=543358
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585562);

INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,AD_Process_ID,SeqNo,AD_Reference_ID,ColumnName,IsCentrallyMaintained,FieldLength,IsMandatory,AD_Element_ID,EntityType,DisplayLogic,ReadOnlyLogic)
VALUES (543359 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-23 10:02:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-10-23 10:02:01','YYYY-MM-DD HH24:MI:SS'),100,'Menge CU',540773,56,29,'QtyCUsReceived','Y',0,'Y',585563,'de.metas.ui.web','@M_HU_PI_Item_Product_ID/-1@=101 & @IsReceiveIndividualCUs/''N''@=''N''',NULL);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Process_Para_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_Para_ID=543359
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585563);

INSERT INTO AD_Process_Para (AD_Process_Para_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,AD_Process_ID,SeqNo,AD_Reference_ID,ColumnName,IsCentrallyMaintained,FieldLength,IsMandatory,AD_Element_ID,EntityType,DisplayLogic,ReadOnlyLogic)
VALUES (543360 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-23 10:02:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-10-23 10:02:02','YYYY-MM-DD HH24:MI:SS'),100,'Unbegrenzte Kapazität',540773,65,20,'IsPIPUnlimitedCapacity','Y',0,'N',585564,'de.metas.ui.web','1=2',NULL);

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Process_Para_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_Para_ID=543360
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585564);

-- 'Menge pro TU' (QtyCUsPerTU): hidden, no longer mandatory
UPDATE AD_Process_Para SET DisplayLogic='1=2', IsMandatory='N', Updated=TO_TIMESTAMP('2026-10-23 10:03:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_Para_ID=541173;

-- 'TU Anzahl' (QtyTU) and 'LU Anzahl' (QtyLU): read-only when the packing instruction has unlimited capacity
UPDATE AD_Process_Para SET ReadOnlyLogic='@IsPIPUnlimitedCapacity/''N''@=''Y''', Updated=TO_TIMESTAMP('2026-10-23 10:03:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_Para_ID=541174;

UPDATE AD_Process_Para SET ReadOnlyLogic='@IsPIPUnlimitedCapacity/''N''@=''Y''', Updated=TO_TIMESTAMP('2026-10-23 10:03:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_Para_ID=541175;
