-- Column: DD_Order_Candidate.AD_InputDataSource_ID
-- Adds AD_InputDataSource_ID (Eingabequelle) to DD_Order_Candidate, mirroring C_OLCand.AD_InputDataSource_ID.
-- Reuses existing AD_Element 541291 (ColumnName=AD_InputDataSource_ID, "Eingabequelle", de.metas.swat).
-- Reference: AD_Reference_ID=19 (Table Direct) — resolves to AD_InputDataSource automatically, no AD_Reference_Value_ID needed.
-- Window 541807 (Distributionsdisposition), tab 547559 (main, tablevel=0).
-- Placed in the planning group (551864, right column) alongside the other replenishment drivers
-- (PP_Product_Planning_ID, DD_NetworkDistribution_ID) — the input data source identifies the producer
-- of the candidate. The org&client cornerstone group is reserved for AD_Org_ID / AD_Client_ID only,
-- per metasfresh-window-design-rules.
--
-- Also corrects the en_US label of the reused shared AD_Element 541291: the seed shipped a German
-- PrintName ('Eingabequelle') and a non-idiomatic Name ('Inputsource') for en_US. Both are set to
-- 'Input Source' (a strict improvement for every window that reuses this element, incl. C_OLCand —
-- with which the labels stay consistent), satisfying AC1's "correct en_US labels" requirement.
--
-- IDs allocated from idserver.metas.de on 2026-06-14:
--   AD_MigrationScript prefix : 5807670
--   AD_Column_ID              : 592807
--   AD_Field_ID               : 781115
--   AD_UI_Element_ID          : 652261

-- ============================================================
-- 1. DDL: add nullable column + FK constraint
-- ============================================================

ALTER TABLE DD_Order_Candidate ADD COLUMN IF NOT EXISTS AD_InputDataSource_ID NUMERIC(10);

ALTER TABLE DD_Order_Candidate
    ADD CONSTRAINT DDOrderCandidate_ADInputDataSource
    FOREIGN KEY (AD_InputDataSource_ID)
    REFERENCES AD_InputDataSource(AD_InputDataSource_ID)
    DEFERRABLE INITIALLY DEFERRED;

-- ============================================================
-- 1b. Correct the en_US label on the reused shared AD_Element 541291
--     (satisfies AC1 "correct en_US labels"). Seed had German PrintName
--     'Eingabequelle' and non-idiomatic Name 'Inputsource' for en_US.
--     Runs BEFORE the column/field translation propagation below so the
--     corrected value flows into AD_Column_Trl / AD_Field_Trl. The
--     propagation functions also push it to every other consumer of the
--     element (incl. C_OLCand) — labels stay consistent across windows.
-- ============================================================

UPDATE AD_Element_Trl
   SET Name='Input Source', PrintName='Input Source', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-06-14 13:59:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=541291 AND AD_Language='en_US'
;

-- ============================================================
-- 2. AD_Column INSERT
-- ============================================================

-- Column: DD_Order_Candidate -> Eingabequelle
-- 2026-06-14 14:00:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version,PersonalDataCategory)
VALUES (0,592807 /*From ID Server*/,541291,0,19,NULL,542424,'AD_InputDataSource_ID',TO_TIMESTAMP('2026-06-14 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','','EE01',10,'','Y','Y','N','N','N','N','N','N','N','N','N','Y','Eingabequelle',TO_TIMESTAMP('2026-06-14 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,0,'NP')
;

-- Allow zoom from the field to the AD_InputDataSource record — mirror C_OLCand.AD_InputDataSource_ID,
-- whose column has IsExcludeFromZoomTargets='N'. The DB default for a new column is 'Y' (no zoom arrow).
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',
       Updated=TO_TIMESTAMP('2026-06-14 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Column_ID=592807
;

-- AD_Column_Trl skeleton rows for all active system languages
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592807
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Propagate element translations to column
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(541291)
;

-- ============================================================
-- 3. AD_Field INSERT (tab 547559, main tab of window 541807)
-- ============================================================

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Eingabequelle
-- Column: DD_Order_Candidate.AD_InputDataSource_ID
-- 2026-06-14 14:02:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592807,781115 /*From ID Server*/,0,547559,TO_TIMESTAMP('2026-06-14 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,'',10,'EE01','','Y','Y','Y','N','N','N','N','N','Eingabequelle',TO_TIMESTAMP('2026-06-14 14:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_Field_Trl skeleton rows
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781115
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate element translations to field (pass element ID, not field ID)
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(541291)
;

-- Rebuild element links
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781115
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781115)
;

-- ============================================================
-- 4. AD_UI_Element INSERT
--    Group 551864 (planning, right column under section 546143) — groups with the
--    replenishment drivers PP_Product_Planning_ID(10), DD_NetworkDistribution_ID(20),
--    DD_NetworkDistributionLine_ID(30). SeqNo=5 puts the input data source first
--    (it identifies the producer of the candidate). SeqNoGrid=90 (max existing grid
--    seq is 80) so it appears as the rightmost grid column. IsAllowFiltering='Y'
--    per requirement. NOT placed in org&client (551866) — that cornerstone group is
--    reserved for AD_Org_ID / AD_Client_ID only.
-- ============================================================

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Eingabequelle
-- Column: DD_Order_Candidate.AD_InputDataSource_ID
-- Group: planning (551864)
-- 2026-06-14 14:02:30
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781115,0,547559,551864,652261 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-14 14:02:30','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','Y','Y','N','Eingabequelle',5,90,0,TO_TIMESTAMP('2026-06-14 14:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sync missing translations after all inserts
SELECT add_missing_translations()
;
