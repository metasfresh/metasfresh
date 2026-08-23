-- Run mode: SWING_CLIENT

-- Adds ExternalSystem as a FILTER option for the MobileUI picking profile — a new value on
-- AD_Reference 541849 ("PickingFilter_Options"), the value set behind PickingProfile_Filter.FilterType.
--
-- Why: the companion script 5819900 lets the packing launcher DISPLAY the external system. Showing
-- it without being able to narrow by it repeats the mismatch PR 25526 had to close for the
-- Bereitstellungsdatum — the operator reads a value on the job card that the filter bar cannot act
-- on. The filter is therefore added alongside the display.
--
-- Additive: every existing filter group stays available and no existing profile changes behaviour.
-- Progressive disclosure is untouched — the bar still reveals one group at a time in SeqNo order.
--
-- Naming reuses AD_Element 583968 ("Externes System" / en_US "External System"), matching 5819900
-- so the display field and the filter group read identically.
--
-- IDs allocated from idserver.metas.de on 2026-08-23:
--   AD_Ref_List 544355 (PickingFilter_Options -> ExternalSystem)

-- Reference: PickingFilter_Options
-- Value: ExternalSystem
-- ValueName: ExternalSystem
-- 2026-08-23T10:00:02.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544355 /*From ID Server*/,541849,TO_TIMESTAMP('2026-08-23 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Externes System',TO_TIMESTAMP('2026-08-23 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'ExternalSystem','ExternalSystem')
;

-- Seed the translation rows for every system language, copying the base (German) name.
-- 2026-08-23T10:00:02.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544355 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-08-23T10:00:03.000Z
UPDATE AD_Ref_List_Trl SET Name='External System', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544355 AND AD_Language='en_US'
;

-- 2026-08-23T10:00:03.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544355 AND AD_Language IN ('de_DE','de_CH')
;
