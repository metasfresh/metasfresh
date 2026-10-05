-- Contract compensation groups: SO->PO take-over -- nullable reference on C_OrderLine to the take-over record.
-- Set on an appended own compensation line so that its product category survives a reload / invoice candidate rebuild.
-- System column: not placed on any window / tab / field.
-- ============================================================================
-- 1) Physical column + FK (nullable)
-- ============================================================================
/* DDL */ SELECT public.db_alter_table('C_OrderLine', 'ALTER TABLE public.C_OrderLine ADD COLUMN C_CompensationGroup_ContractSettings_TakeOver_ID NUMERIC(10)')
;
/* DDL */ SELECT public.db_alter_table('C_OrderLine', 'ALTER TABLE public.C_OrderLine ADD CONSTRAINT CCompGroupContractSettingsTakeOver_COrderLine FOREIGN KEY (C_CompensationGroup_ContractSettings_TakeOver_ID) REFERENCES public.C_CompensationGroup_ContractSettings_TakeOver DEFERRABLE INITIALLY DEFERRED')
;
-- ============================================================================
-- 2) AD_Column
-- ============================================================================
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID,
                       FieldLength, Name, Description, MandatoryLogic,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593695 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-01 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'D', 'C_CompensationGroup_ContractSettings_TakeOver_ID', 260, 585505, 19,
        10, 'Kompensationsgruppen-Vertragseinstellung Übernahme', 'Übernahme-Einstellung je Produktkategorie: Rabatt-Produkt der eigenen Zeile eines Kompensationsgruppen-Vertrags.', NULL,
        'N', 'Y', 'N', NULL,
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 593695
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
UPDATE AD_Column_Trl
SET Name = 'Compensation group contract settings take-over',
    Description = 'Take-over setting per product category: discount product of the own line of a compensation group contract.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593695 AND AD_Language = 'en_US'
;
UPDATE AD_Column_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-01 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593695 AND AD_Language IN ('de_DE', 'de_CH')
;
