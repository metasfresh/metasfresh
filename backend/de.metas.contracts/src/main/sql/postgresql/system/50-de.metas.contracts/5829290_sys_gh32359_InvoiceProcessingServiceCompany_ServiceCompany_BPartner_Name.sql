-- Dropdown of "Servicegebühr übernehmen von" (reference 542148): show the company NAME only.
-- The business partner lookup of ServiceCompany_BPartner_ID renders "<value>_<name>", so the reference displays this
-- virtual column (name of the service company business partner) instead. Used as display column only: it is in no window,
-- hence no AD_SQLColumn_SourceTableColumn (nothing is displayed per record that would need a cache refresh).
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, ColumnSQL, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
VALUES (0, 593736 /*From ID Server*/, 469, 0, 10, 541493, 'ServiceCompany_BPartner_Name', '(select bp.name from c_bpartner bp where bp.c_bpartner_id=servicecompany_bpartner_id)', TO_TIMESTAMP('2026-10-13 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'de.metas.contracts', 100, 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Name', 'P', TO_TIMESTAMP('2026-10-13 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593736
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
UPDATE AD_Column_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-13 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593736 AND AD_Language IN ('de_DE', 'de_CH', 'en_US')
;
UPDATE AD_Ref_Table
SET AD_Display = 593736 /* ServiceCompany_BPartner_Name */, Updated = TO_TIMESTAMP('2026-10-13 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Reference_ID = 542148
;
