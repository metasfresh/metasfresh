-- 2026-09-07T14:22:00.000Z
-- Empty HU write-off — add reject reason E (empty, auto. inventory)
-- IDs allocated from idserver.metas.de on 2026-09-07:
--   AD_Ref_List 544360 (new reject reason E on AD_Reference 541422)
--   AD_Val_Rule 540800 (exclude reason E from M_Inventory_Candidate.DisposeReason window dropdown)

-- Step 1: Add reject reason E to AD_Reference 541422
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,541422,544360 /*From ID Server*/,TO_TIMESTAMP('2026-09-07 14:22:00','YYYY-MM-DD HH24:MI:SS'),100,'Reason code when a handling unit is emptied during raw-materials issue','D','Y','empty (auto. inventory)',TO_TIMESTAMP('2026-09-07 14:22:00','YYYY-MM-DD HH24:MI:SS'),100,'E','Emptied')
;

-- Step 2: Seed AD_Ref_List_Trl skeleton rows for all active system languages
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544360 /*From ID Server*/
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Step 3: Override en_US translation
UPDATE AD_Ref_List_Trl SET Name='empty (auto. inventory)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 14:22:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544360 /*From ID Server*/
;

-- Step 4: Mark de_DE and de_CH as translated (same text as base; both are German variants)
UPDATE AD_Ref_List_Trl SET Name='leer (autom. Inventur)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 14:22:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Ref_List_ID=544360 /*From ID Server*/
;

-- Step 5: Create AD_Val_Rule to exclude the E reason from the M_Inventory_Candidate.DisposeReason window dropdown
-- This val rule will be the second gating point (the first is Java code in QtyRejectedReasonCode.reasonsFor for the served list)
-- The rule is named so a future context (reading only AD) can understand the intent without reading Java
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy)
VALUES (0,0,540800 /*From ID Server*/,'AD_Ref_List.Value <> ''E''',TO_TIMESTAMP('2026-09-07 14:22:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dispose reasons excluding Emptied (manufacturing-only)','S',TO_TIMESTAMP('2026-09-07 14:22:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Step 6: Attach the val rule to M_Inventory_Candidate.DisposeReason column
UPDATE AD_Column SET AD_Val_Rule_ID=540800 /*From ID Server*/, Updated=TO_TIMESTAMP('2026-09-07 14:22:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=578885
;
