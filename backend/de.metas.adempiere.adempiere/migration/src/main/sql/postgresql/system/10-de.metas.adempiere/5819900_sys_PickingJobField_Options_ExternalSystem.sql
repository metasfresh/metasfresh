-- Run mode: SWING_CLIENT

-- Adds ExternalSystem as a DISPLAY field for the MobileUI picking profile — a new value on
-- AD_Reference 541850 ("PickingJobField_Options"), the value set behind
-- PickingProfile_PickingJobConfig.PickingJobField.
--
-- Why: customers who receive sales orders from several upstream systems have no way to see, on the
-- handheld, which system an order came from. The order's external system is
-- already carried down to the shipment schedule (M_ShipmentSchedule.ExternalSystem_ID, copied from
-- C_Order by OrderLineShipmentScheduleHandler), so this only makes it selectable for display.
--
-- Additive: no existing profile changes behaviour, and the built-in default field list
-- (PickingJobField.DEFAULTS) deliberately does NOT gain it. No window change is needed either —
-- the PickingJobField field already exists on the profile's config tab; this adds a value to its
-- reference list.
--
-- Naming reuses AD_Element 583968 ("Externes System" / en_US "External System"), the element behind
-- every ExternalSystem_ID column, so the picking profile reads the same as the documents do.
--
-- IDs allocated from idserver.metas.de on 2026-08-23:
--   AD_Ref_List 544354 (PickingJobField_Options -> ExternalSystem)

-- Reference: PickingJobField_Options
-- Value: ExternalSystem
-- ValueName: ExternalSystem
-- 2026-08-23T10:00:00.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544354 /*From ID Server*/,541850,TO_TIMESTAMP('2026-08-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Externes System',TO_TIMESTAMP('2026-08-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ExternalSystem','ExternalSystem')
;

-- Seed the translation rows for every system language, copying the base (German) name.
-- 2026-08-23T10:00:00.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544354 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- en_US: 'External System' — the wording AD_Element 583968 already carries for this same concept.
-- IsTranslated='Y' for the three languages whose text is final, per the documented convention:
-- seed every system language with the base text and 'N', then flip only the finalised ones
-- (metasfresh-application-dictionary, the CRITICAL Review-rule on seeding *_Trl tables).
-- 2026-08-23T10:00:01.000Z
UPDATE AD_Ref_List_Trl SET Name='External System', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544354 AND AD_Language='en_US'
;

-- de_DE / de_CH already carry the correct German from the base name. 'Externes System' contains no
-- 'ss'/'ß', so the Swiss form is identical.
-- 2026-08-23T10:00:01.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544354 AND AD_Language IN ('de_DE','de_CH')
;
