-- gh#28565 Promotion Code / Aktionskennzeichen
-- Step 1.1: Create C_PromotionCode table + AD metadata

-- AD_Table
INSERT INTO AD_Table (AccessLevel, ACTriggerLength, AD_Client_ID, AD_Org_ID, AD_Table_ID,
                      CopyColumnsFromTable, Created, CreatedBy, EntityType, ImportTable, IsActive, IsAutocomplete,
                      IsChangeLog, IsDeleteable, IsHighVolume, IsSecurityEnabled, IsView, LoadSeq, Name,
                      ReplicationType, TableName, Updated, UpdatedBy)
VALUES ('3', 0, 0, 0, 542586,
        'N', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'N', 'Y', 'N',
        'Y', 'Y', 'N', 'N', 'N', 0, 'Aktionskennzeichen',
        'L', 'C_PromotionCode', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Table_ID = 542586
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID);

-- DB Sequence
CREATE SEQUENCE C_PROMOTIONCODE_SEQ INCREMENT 1 MINVALUE 0 MAXVALUE 2147483647 START 1000000;

-- AD_Sequence
INSERT INTO AD_Sequence (AD_Client_ID, AD_Org_ID, AD_Sequence_ID, Created, CreatedBy, CurrentNext, CurrentNextSys,
                         Description, IncrementNo, IsActive, IsAudited, IsAutoSequence, IsTableID, Name,
                         StartNo, Updated, UpdatedBy)
VALUES (0, 0, 556588, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 1000000, 50000,
        'Table C_PromotionCode', 1, 'Y', 'N', 'Y', 'Y', 'C_PromotionCode',
        1000000, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100);

-- ============================================================
-- Standard columns: AD_Client_ID, AD_Org_ID, Created, CreatedBy, IsActive, Updated, UpdatedBy
-- ============================================================

-- AD_Client_ID (AD_Element_ID=102, AD_Reference_ID=19)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592172, 102, 0, 19, 542586,
        'AD_Client_ID', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 10,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Mandant',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592172
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- AD_Org_ID (AD_Element_ID=113, AD_Reference_ID=19)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592173, 113, 0, 19, 542586,
        'AD_Org_ID', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 10,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Sektion',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592173
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Created (AD_Element_ID=245, AD_Reference_ID=16)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592174, 245, 0, 16, 542586,
        'Created', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 29,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Erstellt',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592174
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- CreatedBy (AD_Element_ID=246, AD_Reference_ID=18, AD_Reference_Value_ID=110)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo,
                       FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel,
                       IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory,
                       IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592175, 246, 0, 18, 110,
        542586, 'CreatedBy', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0,
        10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Erstellt durch',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592175
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- IsActive (AD_Element_ID=348, AD_Reference_ID=20)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592176, 348, 0, 20, 542586,
        'IsActive', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 1,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Aktiv',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592176
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Updated (AD_Element_ID=607, AD_Reference_ID=16)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592177, 607, 0, 16, 542586,
        'Updated', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 29,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Aktualisiert',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592177
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- UpdatedBy (AD_Element_ID=608, AD_Reference_ID=18, AD_Reference_Value_ID=110)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo,
                       FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                       IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel,
                       IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory,
                       IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592178, 608, 0, 18, 110,
        542586, 'UpdatedBy', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0,
        10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Aktualisiert durch',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592178
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- ============================================================
-- AD_Element for C_PromotionCode_ID
-- ============================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy,
                        EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584619, 0, 'C_PromotionCode_ID', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y', 'Aktionskennzeichen', 'Aktionskennzeichen', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Element_ID = 584619
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- English translation for element
UPDATE AD_Element_Trl SET Name = 'Promotion Code', PrintName = 'Promotion Code', IsTranslated = 'Y',
                          Updated = TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584619 AND AD_Language = 'en_US';

-- ============================================================
-- PK column: C_PromotionCode_ID (AD_Reference_ID=13, IsKey='Y')
-- ============================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592171, 584619, 0, 13, 542586,
        'C_PromotionCode_ID', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 10,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Aktionskennzeichen',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592171
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(584619);

-- ============================================================
-- Business columns: Value, Name, ValidTo
-- ============================================================

-- Value (AD_Element_ID=620, AD_Reference_ID=10, IsIdentifier=Y SeqNo=1)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592179, 620, 0, 10, 542586,
        'Value', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 40,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N',
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Suchschlüssel',
        'NP', 0, 10, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592179
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Name (AD_Element_ID=469, AD_Reference_ID=10, IsIdentifier=Y SeqNo=2)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592180, 469, 0, 10, 542586,
        'Name', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 255,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N',
        'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Name',
        'NP', 0, 20, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592180
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- ValidTo (AD_Element_ID=618, AD_Reference_ID=15)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength,
                       IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule,
                       IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                       IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn,
                       IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent,
                       IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable,
                       IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                       PersonalDataCategory, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592181, 618, 0, 15, 542586,
        'ValidTo', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'N', 'D', 0, 7,
        'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 0, 'Gültig bis',
        'NP', 0, 0, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592181
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

/* DDL */ SELECT update_Column_Translation_From_AD_Element(618);

-- ============================================================
-- CREATE TABLE
-- ============================================================
CREATE TABLE C_PromotionCode
(
    C_PromotionCode_ID NUMERIC(10)                NOT NULL,
    AD_Client_ID       NUMERIC(10)                NOT NULL,
    AD_Org_ID          NUMERIC(10)                NOT NULL,
    IsActive           CHAR(1) CHECK (IsActive IN ('Y', 'N')) NOT NULL,
    Created            TIMESTAMP WITH TIME ZONE   NOT NULL,
    CreatedBy          NUMERIC(10)                NOT NULL,
    Updated            TIMESTAMP WITH TIME ZONE   NOT NULL,
    UpdatedBy          NUMERIC(10)                NOT NULL,
    Value              VARCHAR(40)                NOT NULL,
    Name               VARCHAR(255)               NOT NULL,
    ValidTo            DATE                       DEFAULT NULL,
    CONSTRAINT C_PromotionCode_Key PRIMARY KEY (C_PromotionCode_ID)
);

-- ============================================================
-- Unique index: (AD_Client_ID, Value) WHERE IsActive='Y'
-- ============================================================
INSERT INTO AD_Index_Table (AD_Client_ID, AD_Index_Table_ID, AD_Org_ID, AD_Table_ID, Created, CreatedBy,
                            EntityType, IsActive, IsUnique, Name, Processing, Updated, UpdatedBy, WhereClause)
VALUES (0, 540863, 0, 542586, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y', 'Y', 'C_PromotionCode_ADClient_Value_UQ', 'N', TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100,
        'IsActive = ''Y''');

INSERT INTO AD_Index_Table_Trl (AD_Language, AD_Index_Table_ID, ErrorMsg, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N'
  AND t.AD_Index_Table_ID = 540863
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Index_Table_ID = t.AD_Index_Table_ID);

-- Error message for unique index violation (DE)
UPDATE AD_Index_Table SET ErrorMsg = 'Ein aktives Aktionskennzeichen mit diesem Suchschlüssel existiert bereits für diesen Mandanten.',
                          Updated = TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Index_Table_ID = 540863;

-- Error message EN
UPDATE AD_Index_Table_Trl SET ErrorMsg = 'An active promotion code with this search key already exists for this client.',
                              IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Index_Table_ID = 540863 AND AD_Language = 'en_US';

-- Index column: AD_Client_ID
INSERT INTO AD_Index_Column (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID,
                             Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 592172, 541524, 540863, 0,
        TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'Y', 10, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100);

-- Index column: Value
INSERT INTO AD_Index_Column (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID,
                             Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 592179, 541525, 540863, 0,
        TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'Y', 20, TO_TIMESTAMP('2026-03-05 12:00', 'YYYY-MM-DD HH24:MI'), 100);

CREATE UNIQUE INDEX C_PromotionCode_ADClient_Value_UQ ON C_PromotionCode (AD_Client_ID, Value) WHERE IsActive = 'Y';
