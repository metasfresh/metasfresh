-- Run mode: SWING_CLIENT

-- Adds PreparationDate as a filter option for the MobileUI picking profile — a new value on
-- AD_Reference 541849 ("PickingFilter_Options"), the value set behind PickingProfile_Filter.FilterType.
--
-- Why: the packing launcher can DISPLAY the Bereitstellungsdatum (PickingProfile_PickingJobConfig
-- value 'PreparationDate', on AD_Reference 541850) but could only FILTER by the Lieferdatum. An
-- operator therefore read one date on the job card and narrowed the list by a different one. Packing
-- is scheduled by the Bereitstellungsdatum, so that date has to be filterable too.
--
-- Additive: DeliveryDate stays available for profiles that use it, and no existing profile changes
-- behaviour. No window change is needed either — FilterType's field already exists on the profile's
-- Filter tab (547359); this only adds a value to its reference list.
--
-- Naming reuses AD_Reference 541850's existing pair for the very same concept, so the two references
-- read identically: de 'Bereitstellungsdatum', en_US 'Date Ready'.
--
-- IDs allocated from idserver.metas.de on 2026-08-13:
--   AD_Ref_List 544338 (PickingFilter_Options -> PreparationDate)

-- Reference: PickingFilter_Options
-- Value: PreparationDate
-- ValueName: PreparationDate
-- 2026-08-13T10:00:00.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544338 /*From ID Server*/,541849,TO_TIMESTAMP('2026-08-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Bereitstellungsdatum',TO_TIMESTAMP('2026-08-13 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'PreparationDate','PreparationDate')
;

-- Seed the translation rows for every system language, copying the base (German) name.
-- 2026-08-13T10:00:00.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=544338 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- en_US: 'Date Ready' — the wording AD_Reference 541850 (row 543623) already uses for this same
-- date, so the two references read identically. That row is the precedent for the WORDING only:
-- its own IsTranslated flag is 'N', as are all three pre-existing rows on THIS reference, 541849
-- (543617 Customer, 543618 DeliveryDate, 543619 HandoverLocation).
--
-- IsTranslated='Y' here is the documented convention, not a deviation: seed every system language
-- with the base text and 'N', "then UPDATE ... SET IsTranslated='Y' only for the languages whose
-- text is final (typically en_US, de_DE, de_CH)" — metasfresh-application-dictionary, the CRITICAL
-- Review-rule on seeding *_Trl tables. 'Bereitstellungsdatum' <-> 'Date Ready' is a faithful pair,
-- so those three languages are final. The siblings' 'N' is the un-finalised state, not a standard
-- to copy.
-- 2026-08-13T10:00:01.000Z
UPDATE AD_Ref_List_Trl SET Name='Date Ready', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-13 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544338 AND AD_Language='en_US'
;

-- de_DE / de_CH already carry the correct German from the base name. 'Bereitstellungsdatum' contains
-- no 'ß', so the Swiss form is identical.
-- 2026-08-13T10:00:01.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-13 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Ref_List_ID=544338 AND AD_Language IN ('de_DE','de_CH')
;
