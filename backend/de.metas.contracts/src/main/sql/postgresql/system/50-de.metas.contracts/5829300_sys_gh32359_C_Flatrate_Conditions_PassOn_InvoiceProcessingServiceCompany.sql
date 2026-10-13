-- Contract conditions: read-only virtual column "Servicegebühr übernehmen von", the service company of the linked
-- compensation group contract settings. Reuses element 585538 and the table reference 542148 (company name) of the settings column.
-- Self reference is the raw table name (C_Flatrate_Conditions has a PO model class, see virtual column rules).
-- Source dependency: when the settings change, the conditions that use them are refreshed.
-- ============================================================================
-- 1) AD_Column (virtual, read-only)
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
VALUES (0, 593737 /*From ID Server*/, 585538, 0, 30, 542148, 540311, 'PassOn_InvoiceProcessingServiceCompany_ID',
        '(select s.passon_invoiceprocessingservicecompany_id from c_compensationgroup_contractsettings s where s.c_compensationgroup_contractsettings_id=C_Flatrate_Conditions.C_CompensationGroup_ContractSettings_ID)',
        TO_TIMESTAMP('2026-10-13 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 10, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Servicegebühr übernehmen von', 'NP', TO_TIMESTAMP('2026-10-13 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593737
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585538)
;
-- ============================================================================
-- 2) Cache invalidation: a change of the settings' field refreshes the conditions linked to them
-- ============================================================================
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID, AD_Column_ID, AD_Org_ID, AD_SQLColumn_SourceTableColumn_ID, AD_Table_ID, Created, CreatedBy, FetchTargetRecordsMethod, IsActive, Source_Column_ID, Source_Table_ID, SQL_GetTargetRecordIdBySourceRecordId, Updated, UpdatedBy)
VALUES (0, 593737, 0, 540246 /*From ID Server*/, 540311, TO_TIMESTAMP('2026-10-13 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'S', 'Y', 593735, 542650,
        'select c.c_flatrate_conditions_id from c_flatrate_conditions c where c.c_compensationgroup_contractsettings_id=@Record_ID/-1@',
        TO_TIMESTAMP('2026-10-13 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
