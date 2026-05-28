-- Set IsForceIncludeInGeneratedModel=Y for the 4 correction-lifecycle columns on C_TaxDeclaration.
-- These columns have EntityType='de.metas.acct', while the table itself has EntityType='D'.
-- GenerateModel's OnlySystemColumns mode only includes columns whose EntityType is in the
-- hard-coded SYSTEM_MAINTAINED_ENTITY_TYPES set (D, C, U, CUST, A, EXT, XX, EE01..., de.metas.order).
-- 'de.metas.acct' is NOT in that set, so without IsForceIncludeInGeneratedModel=Y the generator
-- silently omits these columns from I_C_TaxDeclaration.java and X_C_TaxDeclaration.java.
-- See: EntityTypesCache.SYSTEM_MAINTAINED_ENTITY_TYPES (de.metas.adempiere.adempiere/base)
--      TableAndColumnInfoRepository.getColumnsEntityTypeWhereClause (same module)
--
-- Also creates an AD_Reference (Table type) for C_TaxDeclaration so that the GenerateModel tool
-- can resolve C_TaxDeclaration_Original_ID's referenced table.
-- GenerateModel's getReferenceClassName() logic: for Search columns with AD_Reference_Value_ID=0,
-- it strips '_ID' from the column name to derive the referenced table name. This works for
-- 'C_TaxDeclaration_ID' -> 'C_TaxDeclaration', but NOT for 'C_TaxDeclaration_Original_ID'
-- -> 'C_TaxDeclaration_Original' (no such table). Setting AD_Reference_Value_ID to a proper
-- AD_Reference record (ValidationType='T', Table=C_TaxDeclaration) makes GenerateModel use the
-- correct resolution path.
--
-- IDs allocated from idserver.metas.de on 2026-05-28:
--   AD_Reference 542099 (C_TaxDeclaration Search reference; also serves as AD_Ref_Table PK)
-- https://github.com/metasfresh/me03/issues/29631

-- ====================================================================================
-- Section 1: AD_Reference for C_TaxDeclaration (Table-based Search reference)
-- ====================================================================================
INSERT INTO AD_Reference
    (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, ValidationType, EntityType)
VALUES
    (542099 /*From ID Server*/, 0, 0, 'Y', TIMESTAMP '2026-05-28 00:00:00', 100, TIMESTAMP '2026-05-28 00:00:00', 100,
     'C_TaxDeclaration', 'T', 'de.metas.acct');

-- ====================================================================================
-- Section 2: AD_Ref_Table — points the reference to C_TaxDeclaration.C_TaxDeclaration_ID
-- Note: AD_Ref_Table uses AD_Reference_ID as its primary key (no separate AD_Ref_Table_ID).
-- The ID server allocation for AD_Ref_Table (543698) is not used; the PK is AD_Reference_ID.
-- ====================================================================================
INSERT INTO AD_Ref_Table
    (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Key, AD_Display,
     WhereClause, OrderByClause, EntityType, IsValueDisplayed)
VALUES
    (542099 /*AD_Reference_ID: C_TaxDeclaration*/,
     0, 0, 'Y', TIMESTAMP '2026-05-28 00:00:00', 100, TIMESTAMP '2026-05-28 00:00:00', 100,
     818   /*AD_Table_ID: C_TaxDeclaration*/,
     (SELECT AD_Column_ID FROM AD_Column WHERE AD_Table_ID = 818 AND ColumnName = 'C_TaxDeclaration_ID') /*AD_Key*/,
     (SELECT AD_Column_ID FROM AD_Column
      WHERE AD_Table_ID = 818 AND ColumnName = 'DocumentNo') /*AD_Display*/,
     NULL  /*WhereClause*/,
     NULL  /*OrderByClause*/,
     'de.metas.acct',
     'N');

-- ====================================================================================
-- Section 3: Set IsForceIncludeInGeneratedModel=Y + AD_Reference_Value_ID on the columns
-- ====================================================================================
UPDATE AD_Column
SET IsForceIncludeInGeneratedModel = 'Y',
    Updated = TO_TIMESTAMP('2026-05-28 17:30:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_TaxDeclaration')
  AND ColumnName IN ('IsCorrection', 'C_TaxDeclaration_Original_ID', 'IsCorrectionNeeded', 'CorrectionNeededReason');

-- Set AD_Reference_Value_ID on C_TaxDeclaration_Original_ID so GenerateModel can resolve the
-- referenced table (C_TaxDeclaration) via the AD_Ref_Table path instead of column-name stripping.
UPDATE AD_Column
SET AD_Reference_Value_ID = 542099 /*AD_Reference_ID: C_TaxDeclaration*/,
    Updated = TO_TIMESTAMP('2026-05-28 17:30:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_TaxDeclaration')
  AND ColumnName = 'C_TaxDeclaration_Original_ID';
