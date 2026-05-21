-- Tax Declaration Iter 5 — add C_DocType_ID column to C_TaxDeclaration + FK + backfill to 'TXD' DocType.
-- Element reuse: AD_Element_ID=196 (existing 'C_DocType_ID' element).
-- AD_Table_ID=818 (C_TaxDeclaration).

-- AD_Column row for C_TaxDeclaration.C_DocType_ID
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsEncrypted, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592580, 196, 0, 19, 818, 'C_DocType_ID', TIMESTAMP '2026-05-21 00:00:00', 100, 'N', 'Belegart oder Verarbeitungsvorgaben', 'D', 10, 'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'Belegart', 0, 0, TIMESTAMP '2026-05-21 00:00:00', 100, 0)
;

-- Populate AD_Column_Trl for system languages from base
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592580
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Pull translations from the AD_Element row
SELECT update_Column_Translation_From_AD_Element(196)
;

-- DDL: add the column (nullable for now — backfill below, then SET NOT NULL)
/* DDL */ SELECT public.db_alter_table('C_TaxDeclaration','ALTER TABLE public.C_TaxDeclaration ADD COLUMN C_DocType_ID NUMERIC(10)')
;

-- FK constraint to C_DocType
ALTER TABLE C_TaxDeclaration
    ADD CONSTRAINT CDocType_CTaxDeclaration FOREIGN KEY (C_DocType_ID)
    REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- Backfill: every existing row gets the 'TXD' DocType
UPDATE C_TaxDeclaration td
SET C_DocType_ID = (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType='TXD' LIMIT 1)
WHERE td.C_DocType_ID IS NULL
;

-- Now enforce NOT NULL at the DB level
INSERT INTO t_alter_column VALUES ('c_taxdeclaration', 'C_DocType_ID', 'NUMERIC(10)', null, 'NULL')
;
INSERT INTO t_alter_column VALUES ('c_taxdeclaration', 'C_DocType_ID', null, 'NOT NULL', null)
;

-- Match the AD_Column metadata to the DB reality
UPDATE AD_Column SET IsMandatory='Y', Updated=TIMESTAMP '2026-05-21 00:00:00', UpdatedBy=100
WHERE AD_Column_ID=592580
;
