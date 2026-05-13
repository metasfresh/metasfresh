-- Run mode: SWING_CLIENT

-- IDs fetched from ID server (http://idserver.metas.de):
-- AD_MigrationScript  -> 5802360  (×10 = 58023600, this file)
-- AD_Reference        -> 542089   /*From ID Server*/
-- AD_Ref_List (NRC)   -> 544229   /*From ID Server*/
-- AD_Ref_List (RC)    -> 544230   /*From ID Server*/
-- AD_Element          -> 584866   /*From ID Server*/
-- AD_Column           -> 592521   /*From ID Server*/
-- AD_Field            -> 779547   /*From ID Server*/
-- AD_UI_Element       -> 651358   /*From ID Server*/

-- DB lookups (swift_eagle_uat, port 4432):
-- AD_Tab_ID of Product window (AD_Window_ID=140) main tab (TabLevel=0) -> 180   /*From DB lookup*/
-- GTIN AD_Field_ID=589539, SeqNo=430, SeqNoGrid=460                             /*From DB lookup*/
-- GTIN AD_UI_ElementGroup_ID=1000015, max SeqNo in group=90                     /*From DB lookup*/
-- M_Product AD_Table_ID=208                                                     /*From DB lookup*/

-- ===========================================================================
-- 1. AD_Reference: list reference "Deposit Type"
-- ===========================================================================
-- 2026-05-13T17:00:00.000Z
INSERT INTO AD_Reference
    (AD_Reference_ID, AD_Client_ID, AD_Org_ID, Name, ValidationType, EntityType,
     IsActive,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (542089 /*From ID Server*/, 0, 0, 'Deposit Type', 'L', 'D',
     'Y',
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100)
;

-- AD_Reference_Trl
INSERT INTO AD_Reference_Trl
    (AD_Language, AD_Reference_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, 'Pfandart', 'Y',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y'
  AND l.AD_Language IN ('de_DE','de_CH')
  AND t.AD_Reference_ID=542089 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
                  WHERE tt.AD_Language=l.AD_Language
                    AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- ===========================================================================
-- 2. AD_Ref_List: NRC (Einwegpfand)
-- ===========================================================================
-- 2026-05-13T17:00:00.000Z
INSERT INTO AD_Ref_List
    (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, AD_Reference_ID,
     Value, Name, EntityType, IsActive, ValidFromVersion,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (544229 /*From ID Server*/, 0, 0, 542089 /*From ID Server*/,
     'NRC', 'Einwegpfand', 'D', 'Y', 0,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100)
;

-- AD_Ref_List_Trl for NRC
INSERT INTO AD_Ref_List_Trl
    (AD_Language, AD_Ref_List_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, 'Einwegpfand', 'Y',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y'
  AND l.AD_Language IN ('de_DE','de_CH')
  AND t.AD_Ref_List_ID=544229 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language=l.AD_Language
                    AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- ===========================================================================
-- 3. AD_Ref_List: RC (Mehrwegpfand)
-- ===========================================================================
-- 2026-05-13T17:00:00.000Z
INSERT INTO AD_Ref_List
    (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, AD_Reference_ID,
     Value, Name, EntityType, IsActive, ValidFromVersion,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (544230 /*From ID Server*/, 0, 0, 542089 /*From ID Server*/,
     'RC', 'Mehrwegpfand', 'D', 'Y', 0,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100)
;

-- AD_Ref_List_Trl for RC
INSERT INTO AD_Ref_List_Trl
    (AD_Language, AD_Ref_List_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, 'Mehrwegpfand', 'Y',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y'
  AND l.AD_Language IN ('de_DE','de_CH')
  AND t.AD_Ref_List_ID=544230 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language=l.AD_Language
                    AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- ===========================================================================
-- 4. AD_Element: DepositType
-- ===========================================================================
-- 2026-05-13T17:00:00.000Z
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, ColumnName,
     Name, PrintName, EntityType, IsActive, PersonalDataCategory,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (584866 /*From ID Server*/, 0, 0, 'DepositType',
     'Pfandart', 'Pfandart', 'D', 'Y', 'NP',
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100)
;

-- AD_Element_Trl (all active system/base languages, then update DE overrides)
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID,
     CommitWarning, Description, Help, Name,
     PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
     WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
     IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
       t.CommitWarning, t.Description, t.Help, t.Name,
       t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584866 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language=l.AD_Language
                    AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Set German translations (de_DE and de_CH)
UPDATE AD_Element_Trl
SET IsTranslated='Y',
    Name='Pfandart',
    PrintName='Pfandart',
    Updated=TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'),
    UpdatedBy=100
WHERE AD_Element_ID=584866 /*From ID Server*/
  AND AD_Language IN ('de_DE','de_CH')
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584866 /*From ID Server*/, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584866 /*From ID Server*/, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584866 /*From ID Server*/, 'de_DE');

-- ===========================================================================
-- 5. AD_Column: M_Product.DepositType
-- ===========================================================================
-- 2026-05-13T17:00:00.000Z
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID,
     AD_Table_ID, AD_Element_ID,
     AD_Reference_ID, AD_Reference_Value_ID,
     ColumnName, Name,
     Description, Help,
     FieldLength, IsMandatory, IsActive, IsUpdateable, IsTranslated,
     IsIdentifier, IsKey, IsParent, IsEncrypted, IsSelectionColumn,
     IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
     IsCalculated, DDL_NoForeignKey, IsSyncDatabase,
     FacetFilterSeqNo, IsFacetFilter,
     IsShowFilterIncrementButtons, IsShowFilterInline,
     SelectionColumnSeqNo, SeqNo,
     EntityType, PersonalDataCategory, Version,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (592521 /*From ID Server*/, 0, 0,
     208 /*M_Product AD_Table_ID, From DB lookup*/, 584866 /*From ID Server*/,
     17 /*List reference type*/, 542089 /*AD_Reference_Value_ID = our new ref, From ID Server*/,
     'DepositType', 'Pfandart',
     'Klassifizierung des Pfandtyps: Einwegpfand (NRC) oder Mehrwegpfand (RC).',
     'Pfandtyp-Klassifizierung des Produkts für den EDIFACT IMD+C Pfandsegment (Einwegpfand = NRC, Mehrwegpfand = RC).',
     3, 'N', 'Y', 'Y', 'N',
     'N', 'N', 'N', 'N', 'N',
     'Y', 'N', 'N',
     'N', 'N', 'N',
     0, 'N',
     'N', 'N',
     0, 0,
     'D', 'NP', 0,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100)
;

-- AD_Column_Trl
INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Column_ID=592521 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language=l.AD_Language
                    AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584866 /*From ID Server*/);

-- ===========================================================================
-- 6. Physical column DDL
-- ===========================================================================
-- New column → use ALTER TABLE ADD COLUMN (t_alter_column requires column to already exist)
ALTER TABLE M_Product ADD COLUMN IF NOT EXISTS DepositType CHAR(3) DEFAULT NULL;

ALTER TABLE M_Product ADD CONSTRAINT DepositType_Check
    CHECK (DepositType IS NULL OR DepositType IN ('NRC', 'RC'));

-- ===========================================================================
-- 7. AD_Field: place in Product window main tab, next to GTIN (SeqNo 430+5=435)
-- ===========================================================================
-- 2026-05-13T17:00:00.000Z
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID,
     AD_Tab_ID, AD_Column_ID,
     Name, EntityType,
     IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted,
     IsFieldOnly, IsHeading, IsReadOnly, IsSameLine,
     SeqNo, SeqNoGrid,
     ColumnDisplayLength, DisplayLength,
     IncludedTabHeight, SortNo, SpanX, SpanY,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (779547 /*From ID Server*/, 0, 0,
     180 /*AD_Tab_ID Product main tab, From DB lookup*/, 592521 /*From ID Server*/,
     'Pfandart', 'D',
     'Y', 'Y', 'N', 'N',
     'N', 'N', 'N', 'N',
     435 /*SeqNo: GTIN=430 + 5; From DB lookup*/, 465 /*SeqNoGrid: GTIN grid=460+5*/,
     0, 0,
     0, 0, 1, 1,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100)
;

-- AD_Field_Trl
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N')
  AND t.AD_Field_ID=779547 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language=l.AD_Language
                    AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584866 /*From ID Server*/);

/* DDL */ DELETE FROM AD_Element_Link WHERE AD_Field_ID=779547 /*From ID Server*/;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(779547 /*From ID Server*/);

-- ===========================================================================
-- 8. AD_UI_Element: place in the GTIN element group (AD_UI_ElementGroup_ID=1000015)
--    SeqNo = max(90) + 10 = 100
-- ===========================================================================
-- 2026-05-13T17:00:00.000Z
INSERT INTO AD_UI_Element
    (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID,
     AD_UI_ElementType,
     Name, SeqNo, SeqNo_SideList, SeqNoGrid,
     IsActive, IsAdvancedField, IsAllowFiltering,
     IsDisplayed, IsDisplayed_SideList, IsDisplayedGrid, IsMultiLine, MultiLine_LinesCount,
     Created, CreatedBy, Updated, UpdatedBy)
VALUES
    (651358 /*From ID Server*/, 0, 0,
     180 /*AD_Tab_ID, From DB lookup*/, 779547 /*From ID Server*/, 1000015 /*GTIN group, From DB lookup*/,
     'F',
     'Pfandart', 100 /*SeqNo: max in group 90+10, From DB lookup*/, 0, 0,
     'Y', 'N', 'N',
     'Y', 'N', 'N', 'N', 0,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
     TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100)
;

-- ===========================================================================
-- 9. en_US translations (explicit — add_missing_translations would back-fill
--    German placeholder text for en_US; we set the correct English values here)
-- ===========================================================================

-- AD_Reference_Trl: en_US
INSERT INTO AD_Reference_Trl
    (AD_Language, AD_Reference_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'en_US', t.AD_Reference_ID, 'Deposit Type', 'Y',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Reference t
WHERE t.AD_Reference_ID=542089 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
                  WHERE tt.AD_Language='en_US' AND tt.AD_Reference_ID=t.AD_Reference_ID);

-- AD_Ref_List_Trl: en_US for NRC (Disposable Deposit)
INSERT INTO AD_Ref_List_Trl
    (AD_Language, AD_Ref_List_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'en_US', t.AD_Ref_List_ID, 'Disposable Deposit', 'Y',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Ref_List t
WHERE t.AD_Ref_List_ID=544229 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language='en_US' AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- AD_Ref_List_Trl: en_US for RC (Reusable Deposit)
INSERT INTO AD_Ref_List_Trl
    (AD_Language, AD_Ref_List_ID, Name, IsTranslated,
     AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT 'en_US', t.AD_Ref_List_ID, 'Reusable Deposit', 'Y',
       t.AD_Client_ID, t.AD_Org_ID,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), 100, 'Y'
FROM AD_Ref_List t
WHERE t.AD_Ref_List_ID=544230 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language='en_US' AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- AD_Element_Trl: update en_US row (created by the skeleton INSERT above with German placeholder)
UPDATE AD_Element_Trl
SET Name='Deposit Type', PrintName='Deposit Type', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-13 17:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584866 /*From ID Server*/ AND AD_Language='en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584866 /*From ID Server*/, 'en_US');

-- ===========================================================================
-- 10. Safety net: fill any missing _Trl rows for all translatable tables
-- ===========================================================================
SELECT add_missing_translations();
