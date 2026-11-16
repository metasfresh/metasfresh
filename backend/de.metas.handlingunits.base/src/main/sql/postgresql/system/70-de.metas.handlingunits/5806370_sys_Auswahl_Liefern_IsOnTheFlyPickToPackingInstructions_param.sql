-- Auswahl Liefern (AD_Process 540458 = M_ShipmentSchedule_EnqueueSelection):
-- expose a HIDDEN process parameter IsOnTheFlyPickToPackingInstructions (Yes-No, default 'N').
-- When on, on-the-fly picking packs CUs into TUs (PackingInstructions) instead of creating bare CUs
-- ("No Packing Item"), which is required for TU-based re-reservation after a shipment reversal.
-- Global default stays 'N' (no behaviour change); a customer flips DefaultValue on their instance only.
--
-- HIDDEN via DisplayLogic='1=0' (AD_Process_Para has no IsDisplayed column; '1=0' is the standard
-- never-true expression used to hide process params). IsMandatory='N', DefaultValue='N'.
-- The @Param resolves to false when absent, so the param is safe even on instances that never set it.
--
-- IDs from idserver.metas.de on 2026-06-04:
--   AD_Element        584950 (IsOnTheFlyPickToPackingInstructions label)
--   AD_Process_Para   543244 (on AD_Process 540458)
-- Yes-No reference: AD_Reference_ID=17 (List) + AD_Reference_Value_ID=319 (_YesNo) — same as the
-- canonical boolean process-param pattern (e.g. de.metas.fresh IsSOTrx in 5439480_..._ADR_Auswertung_Process.sql).

-- 2026-06-04T10:00:00.000Z
-- AD_Element (German base name; English via AD_Element_Trl)
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,ColumnName,EntityType,Name,PrintName,Description,Help,Created,CreatedBy,Updated,UpdatedBy,IsActive)
VALUES (584950 /*From ID Server*/,0,0,'IsOnTheFlyPickToPackingInstructions','de.metas.handlingunits','Im Lauf in Packvorschrift kommissionieren','Im Lauf in Packvorschrift kommissionieren','Wenn aktiviert, kommissioniert die fliegende Kommissionierung in Transporteinheiten (gemäß Packvorschrift) statt loser CUs.','Wenn aktiviert, kommissioniert die fliegende Kommissionierung in Transporteinheiten (gemäß Packvorschrift) statt loser CUs.',TO_TIMESTAMP('2026-06-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-04 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2026-06-04T10:00:01.000Z
-- AD_Element_Trl skeleton rows for all system languages (copies German base text)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Name,PrintName,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name,t.PrintName,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=584950
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-04T10:00:12.000Z
-- English override
UPDATE AD_Element_Trl SET Name='Pick to packing instructions on the fly',PrintName='Pick to packing instructions on the fly',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-04 10:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=584950
;

-- 2026-06-04T10:00:13.000Z
-- German is the base language → de_DE/de_CH rows are NOT translations: IsTranslated='N'
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2026-06-04 10:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=584950
;

-- 2026-06-04T10:01:00.000Z
-- AD_Process_Para: hidden Yes-No param, default 'N', on AD_Process 540458, SeqNo 40 (after the existing 10/20/30)
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,584950,0,540458,543244 /*From ID Server*/,17,319,'IsOnTheFlyPickToPackingInstructions',TO_TIMESTAMP('2026-06-04 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','1=0','de.metas.handlingunits',0,'Y','N','Y','N','N','N','Im Lauf in Packvorschrift kommissionieren',40,TO_TIMESTAMP('2026-06-04 10:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-04T10:01:01.000Z
-- AD_Process_Para_Trl skeleton rows for all system languages
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Name,Description,Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Name,t.Description,t.Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Process_Para_ID=543244
AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-06-04T10:02:00.000Z
-- Propagate Name/Description/Help from AD_Element to the new AD_Process_Para_Trl rows
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584950)
;
