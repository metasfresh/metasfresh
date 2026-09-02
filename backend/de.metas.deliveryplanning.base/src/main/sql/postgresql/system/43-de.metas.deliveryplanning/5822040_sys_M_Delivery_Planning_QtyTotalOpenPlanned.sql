-- Add M_Delivery_Planning.QtyTotalOpenPlanned: "how much of the order line nobody has planned yet"
-- (QtyOrdered - the planned quantities of all plannings of the line), shown directly below the existing
-- QtyTotalOpen ("what has not physically arrived yet"). AD metadata only -- no maintenance/interceptor is
-- added here, so the column starts and stays NULL until a later change makes the figure live. That is why
-- it is nullable with no default, unlike its stored, already-mandatory siblings QtyOrdered/QtyTotalOpen.
--
-- IDs allocated from idserver.metas.de on 2026-09-02:
--   AD_Element     585419 (QtyTotalOpenPlanned)
--   AD_Column      593466 (M_Delivery_Planning.QtyTotalOpenPlanned)
--   AD_Field       784913 (Delivery Planning tab)
--   AD_UI_Element  654683 (qtys element group, placed right after QtyTotalOpen)

-- ---------------------------------------------------------------------------------------------
-- 1) Element: QtyTotalOpenPlanned. German in the base column (AD base-language convention); English
--    override via _Trl[en_US]. fr_CH carries the en_US text with IsTranslated='N' -- see the
--    fr_CH CONVENTION stated once in 5820520_sys_M_Delivery_Planning_GenerateDeliveryInstruction_IsComplete.sql
--    (no French wording exists or is being commissioned for this change set).
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Description, Updated, UpdatedBy)
VALUES (0, 585419 /*From ID Server*/, 0, 'QtyTotalOpenPlanned',
        TO_TIMESTAMP('2026-09-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Offene Menge (geplant)', 'Offene Menge (geplant)',
        'Menge der Auftragsposition, für die noch keine Lieferplanung besteht: Positionsmenge abzüglich der geplanten Mengen aller Lieferplanungen dieser Position.',
        TO_TIMESTAMP('2026-09-02 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- seed AD_Element_Trl for every active system or base language, copying the German base text
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585419
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US override
UPDATE AD_Element_Trl SET
  Name='Open Quantity (planned)', PrintName='Open Quantity (planned)',
  Description='Quantity of the order line not yet covered by any delivery planning: ordered quantity less the planned quantities of all plannings for that line.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-09-02 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585419 AND AD_Language='en_US'
;

-- de_DE / de_CH already match the base text seeded above -- mark them actively translated
UPDATE AD_Element_Trl SET
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-09-02 11:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585419 AND AD_Language IN ('de_DE', 'de_CH')
;

-- fr_CH per the fr_CH CONVENTION: the en_US text, IsTranslated='N'
UPDATE AD_Element_Trl trl
   SET Name         = en.Name,
       PrintName    = en.PrintName,
       Description  = en.Description,
       IsTranslated = 'N',
       Updated      = TO_TIMESTAMP('2026-09-02 11:00:14', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
  FROM AD_Element_Trl en
 WHERE en.AD_Element_ID = trl.AD_Element_ID
   AND en.AD_Language = 'en_US'
   AND trl.AD_Language = 'fr_CH'
   AND trl.AD_Element_ID = 585419
;

-- ---------------------------------------------------------------------------------------------
-- 2) Column: M_Delivery_Planning.QtyTotalOpenPlanned -- stored (ColumnSQL NULL), same shape as its
--    sibling QtyTotalOpen (Reference 29 Quantity, FieldLength 10) except nullable/no default: no
--    maintenance exists yet, so there is no value to backfill with, and NOT NULL would force one.
--    PersonalDataCategory='NP' -- a quantity, no personal data. IsSelectionColumn='Y' per this task;
--    IsRangeFilter left 'N' (family default -- unchanged here, that decision is the receipt-logistics
--    window's, not this one's).
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593466 /*From ID Server*/, 585419, 0, 29, 542259, 'QtyTotalOpenPlanned',
        TO_TIMESTAMP('2026-09-02 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'N', 'D', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0,
        'Offene Menge (geplant)', 'NP', 0, 0,
        TO_TIMESTAMP('2026-09-02 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593466
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(585419);

-- Physical DDL for the new column -- nullable, no default (see comment at the top of this script).
SELECT public.db_alter_table('M_Delivery_Planning', 'ALTER TABLE public.M_Delivery_Planning ADD COLUMN IF NOT EXISTS QtyTotalOpenPlanned NUMERIC')
;

-- ---------------------------------------------------------------------------------------------
-- 3) Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Offene Menge (geplant)
--    Read-only display field, same shape as the QtyTotalOpen field (708091) it sits below --
--    no maintenance/interceptor is added here (that is a later change), so the field is read-only.
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, Created, CreatedBy, DisplayLength, EntityType, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, Updated, UpdatedBy)
VALUES (0, 593466, 784913 /*From ID Server*/, 0, 546674,
        TO_TIMESTAMP('2026-09-02 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 10, 'D', 'Y', 'Y', 'Y', 'N', 'N', 'N', 'Y', 'N',
        'Offene Menge (geplant)',
        TO_TIMESTAMP('2026-09-02 11:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784913
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(585419);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=784913;
SELECT AD_Element_Link_Create_Missing_Field(784913);

-- ---------------------------------------------------------------------------------------------
-- 4) UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> qtys ->
--    Offene Menge (geplant), placed directly below QtyTotalOpen (SeqNo 20 -> new 22 -> next used
--    SeqNo is 25 for PlannedLoadedQuantity) and immediately after it in the grid too
--    (SeqNoGrid 160 -> new 165 -> next used SeqNoGrid is 270 for ActualLoadQty).
-- ---------------------------------------------------------------------------------------------
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 784913, 0, 546674, 550032, 654683 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-09-02 11:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'Y', 'N', 'N', 0,
        'Offene Menge (geplant)', 22, 165, 0,
        TO_TIMESTAMP('2026-09-02 11:03:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- ---------------------------------------------------------------------------------------------
-- 5) Both open-quantity figures become selection columns (this task's requirement); IsRangeFilter
--    stays at the family default ('N', unchanged) for both. QtyTotalOpenPlanned (593466) already
--    got IsSelectionColumn='Y' in its INSERT above -- this UPDATE only flips its existing sibling.
-- ---------------------------------------------------------------------------------------------
UPDATE AD_Column SET IsSelectionColumn='Y', Updated=TO_TIMESTAMP('2026-09-02 11:04:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=585019 -- M_Delivery_Planning.QtyTotalOpen
;
