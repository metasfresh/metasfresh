-- Compensation group contract settings: new optional reference "Servicegebühr übernehmen von" to the invoice processing
-- service company whose payment-service fee is passed on (drop-ship purchase orders only). Column + element + dropdown
-- (reference + validation rule: only the newest active version of each service company, labelled with the company name;
-- the label is a virtual display column of the service company table that returns the name of its business partner,
-- because the business partner lookup itself would render "<value>_<name>").
-- No data change: the column is empty on all existing settings.
-- ============================================================================
-- 1) AD_Element
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585538 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-13 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'PassOn_InvoiceProcessingServiceCompany_ID', 'de.metas.contracts', 'Servicegebühr übernehmen von', 'Servicegebühr übernehmen von',
        'Gilt nur für Einkaufsaufträge mit Streckengeschäft (Direktlieferung). Auf Verkaufsaufträge hat dieses Feld keine Wirkung.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 585538, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585538
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Pass on service fee of', PrintName = 'Pass on service fee of',
    Description = 'Applies to purchase orders with drop shipment only. It has no effect on sales orders.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585538 AND AD_Language = 'en_US'
;
-- ============================================================================
-- 2) Validation rule: active records that are the newest active version (ValidFrom, ties: higher ID) of their service company
-- ============================================================================
INSERT INTO AD_Val_Rule (AD_Val_Rule_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, Type, Code, EntityType)
VALUES (540806 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-13 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'InvoiceProcessingServiceCompany newest active version',
        'Only the newest active version (latest ValidFrom) of each service company',
        'S',
        'InvoiceProcessingServiceCompany.IsActive=''Y'' AND NOT EXISTS (SELECT 1 FROM InvoiceProcessingServiceCompany newer WHERE newer.ServiceCompany_BPartner_ID=InvoiceProcessingServiceCompany.ServiceCompany_BPartner_ID AND newer.IsActive=''Y'' AND (newer.ValidFrom>InvoiceProcessingServiceCompany.ValidFrom OR (newer.ValidFrom=InvoiceProcessingServiceCompany.ValidFrom AND newer.InvoiceProcessingServiceCompany_ID>InvoiceProcessingServiceCompany.InvoiceProcessingServiceCompany_ID)))',
        'de.metas.contracts')
;
-- ============================================================================
-- 3) Virtual display column of the service company table: name of its business partner.
--    Raw table name as self reference: the table has a PO model class, and the lookup reads this SQL verbatim
--    from the table without an alias (the master alias token is not substituted on those paths).
--    Source dependency: a rename of the business partner refreshes the service company records.
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
VALUES (0, 593736 /*From ID Server*/, 469, 0, 10, 541493, 'ServiceCompany_BPartner_Name', '(select bp.name from c_bpartner bp where bp.c_bpartner_id=InvoiceProcessingServiceCompany.ServiceCompany_BPartner_ID)', TO_TIMESTAMP('2026-10-13 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 100, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Name', 'P', TO_TIMESTAMP('2026-10-13 10:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593736
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_Column_ID, AD_Org_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Table_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, Source_Column_ID, Source_Table_ID, SQL_GetTargetRecordIdBySourceRecordId, Updated, UpdatedBy)
VALUES (0, 593736, 0, 540247 /*From ID Server*/, 541493, TO_TIMESTAMP('2026-10-13 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'S', 'Y', 2902 /* C_BPartner.Name */, 291 /* C_BPartner */,
        'select InvoiceProcessingServiceCompany_ID from InvoiceProcessingServiceCompany where ServiceCompany_BPartner_ID=@Record_ID/-1@',
        TO_TIMESTAMP('2026-10-13 10:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
-- ============================================================================
-- 4) Table reference: key = InvoiceProcessingServiceCompany_ID, displayed = ServiceCompany_BPartner_Name (the company name)
-- ============================================================================
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542148 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-13 10:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Rechnungsserviceunternehmen (Name)', 'Invoice processing service company, shown by the name of its business partner', 'T', 'de.metas.contracts', 'N')
;
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Reference_ID = 542148
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;
UPDATE AD_Reference_Trl
SET Name = 'Invoice processing service company (name)', IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 10:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Reference_ID = 542148 AND AD_Language = 'en_US'
;
INSERT INTO AD_Ref_Table (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Key, AD_Display, IsValueDisplayed, EntityType)
VALUES (542148, 0, 0, 'Y', TO_TIMESTAMP('2026-10-13 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-13 10:00:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
        541493 /* InvoiceProcessingServiceCompany */, 570662 /* InvoiceProcessingServiceCompany_ID */, 593736 /* ServiceCompany_BPartner_Name */, 'N', 'de.metas.contracts')
;
-- ============================================================================
-- 5) AD_Column + DDL
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, AD_Val_Rule_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
VALUES (0, 593735 /*From ID Server*/, 585538, 0, 30, 542148, 542650, 540806, 'PassOn_InvoiceProcessingServiceCompany_ID', TO_TIMESTAMP('2026-10-13 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 10, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'Servicegebühr übernehmen von', 'NP', TO_TIMESTAMP('2026-10-13 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593735
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585538)
;
ALTER TABLE C_CompensationGroup_ContractSettings ADD COLUMN PassOn_InvoiceProcessingServiceCompany_ID NUMERIC(10)
;
ALTER TABLE C_CompensationGroup_ContractSettings ADD CONSTRAINT PassOnIPSC_CCompGroupContractSettings FOREIGN KEY (PassOn_InvoiceProcessingServiceCompany_ID) REFERENCES InvoiceProcessingServiceCompany (InvoiceProcessingServiceCompany_ID) DEFERRABLE INITIALLY DEFERRED
;
