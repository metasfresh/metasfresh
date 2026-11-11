-- Application dictionary for the view C_Order_CompensationGroup_Line_V (script 5834200) and the order-line tab of the window
-- "Order Compensation Group" (AD_Window 542196), plus the Related Documents of a line row.
--
-- Problem: tab 549518 sits on C_OrderLine, so AD_Field_v adds a field for every active C_OrderLine column and the window health
-- check with context variables reports 6 missing context variables (order header fields). Fix: the tab reads a plain read-only
-- view that carries only the columns the tab shows plus technical ones. No AD_Val_Rule_ID, no mandatory/display/read-only logic,
-- no default on any new AD_Column (references only), so nothing depends on the order header.
--
-- 1) AD_Table (clone of the template X_TableTemplate 540290, IsView='Y'; EntityType 'D' as the window) + AD_Column rows
--    (the AD_Elements of the C_OrderLine columns are reused, no new element). Key column C_OrderLine_ID (IsKey, IsGenericZoomOrigin)
--    keeps the generic Related Documents of a line row.
-- 2) Related Documents: copy of the relation type 540499 (C_OrderLine -> MD_Stock_PerWeek_V, target reference 542100 reused) with
--    the view as source (own AD_Reference + AD_Ref_Table, key C_OrderLine_ID). Not copied: change log (window 270), event log
--    (window 540394) and issues (window 363) of a line row; they are matched by table + record id and cannot be kept on a view row.
-- 3) AD_Tab 549518 re-pointed to the view: AD_Table_ID and AD_Column_ID (the child link; its field is the hidden field 785681);
--    Parent_Column_ID stays NULL as before.
-- 4) AD_Field.AD_Column_ID of the 10 configured fields re-pointed to the view's columns (names, sort, AD_UI_Element rows stay).
--
-- IDs allocated from idserver.metas.de on 2026-11-11:
--   AD_MigrationScript 5834210 (this script), 5834200 (view)
--   AD_Table 542660; AD_Column 593830..593848; AD_Reference 542156; AD_RelationType 540513

-- ============================================================================
-- 1) AD_Table (template X_TableTemplate 540290: AccessLevel, ChangeLog etc.; IsView='Y')
-- ============================================================================
INSERT INTO AD_Table (AD_Client_ID, AD_Org_ID, AD_Table_ID, AccessLevel, ACTriggerLength, Created, CreatedBy, EntityType, ImportTable, IsActive, IsAutocomplete, IsChangeLog, IsDeleteable, IsDLM, IsEnableRemoteCacheInvalidation, IsHighVolume, IsSecurityEnabled, IsView, LoadSeq, Name, PersonalDataCategory, ReplicationType, TableName, TooltipType, Updated, UpdatedBy)
VALUES (0, 0, 542660 /*From ID Server*/, '3', 0, TO_TIMESTAMP('2026-11-11 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 0, 'C_Order_CompensationGroup_Line_V', 'NP', 'L', 'C_Order_CompensationGroup_Line_V', 'DTI', TO_TIMESTAMP('2026-11-11 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Table_ID = 542660
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID)
;
-- ============================================================================
-- 1b) AD_Column rows: references only; no AD_Val_Rule_ID, no logic, no default
-- ============================================================================
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593830 /*From ID Server*/, 561, 0, 13, NULL, 542660, 'C_OrderLine_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 'N', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 561
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593830
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(561)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593831 /*From ID Server*/, 102, 0, 19, NULL, 542660, 'AD_Client_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 102
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593831
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(102)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593832 /*From ID Server*/, 113, 0, 30, NULL, 542660, 'AD_Org_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 113
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593832
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(113)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593833 /*From ID Server*/, 348, 0, 20, NULL, 542660, 'IsActive', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 1, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 348
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593833
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(348)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593834 /*From ID Server*/, 245, 0, 16, NULL, 542660, 'Created', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 7, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 245
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593834
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(245)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593835 /*From ID Server*/, 246, 0, 18, 110, 542660, 'CreatedBy', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 246
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593835
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(246)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593836 /*From ID Server*/, 607, 0, 16, NULL, 542660, 'Updated', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 7, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 607
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593836
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(607)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593837 /*From ID Server*/, 608, 0, 18, 110, 542660, 'UpdatedBy', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 608
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593837
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(608)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593838 /*From ID Server*/, 558, 0, 30, NULL, 542660, 'C_Order_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 558
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593838
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(558)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593839 /*From ID Server*/, 543469, 0, 30, NULL, 542660, 'C_Order_CompensationGroup_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 543469
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593839
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543469)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593840 /*From ID Server*/, 439, 0, 11, NULL, 542660, 'Line', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 439
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593840
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(439)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593841 /*From ID Server*/, 454, 0, 30, 162, 542660, 'M_Product_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 454
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593841
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(454)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593842 /*From ID Server*/, 2589, 0, 29, NULL, 542660, 'QtyEntered', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 2589
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593842
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(2589)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593843 /*From ID Server*/, 215, 0, 18, 541960, 542660, 'C_UOM_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 215
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593843
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(215)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593844 /*From ID Server*/, 2588, 0, 37, NULL, 542660, 'PriceEntered', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 2588
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593844
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(2588)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593845 /*From ID Server*/, 542464, 0, 18, 541960, 542660, 'Price_UOM_ID', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 542464
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593845
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(542464)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593846 /*From ID Server*/, 441, 0, 12, NULL, 542660, 'LineNetAmt', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 22, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 441
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593846
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(441)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593847 /*From ID Server*/, 543461, 0, 17, 540758, 542660, 'GroupCompensationType', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 1, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 543461
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593847
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543461)
;
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsEncrypted, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, Updated, UpdatedBy, Version)
SELECT 0, 593848 /*From ID Server*/, 543462, 0, 17, 540759, 542660, 'GroupCompensationAmtType', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10, 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', e.Name, 'NP', TO_TIMESTAMP('2026-11-11 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0
FROM AD_Element e WHERE e.AD_Element_ID = 543462
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y') AND t.AD_Column_ID = 593848
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(543462)
;
-- ============================================================================
-- 2) Related Documents of a line row: copy of the relation type 540499 with the view as source
-- ============================================================================
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 542156 /*From ID Server*/, TO_TIMESTAMP('2026-11-11 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'RelType C_Order_CompensationGroup_Line_V (Quelle)', TO_TIMESTAMP('2026-11-11 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Key, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy)
VALUES (0, 0, 542156, 542660, 593830 /* C_Order_CompensationGroup_Line_V.C_OrderLine_ID */, TO_TIMESTAMP('2026-11-11 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2026-11-11 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_RelationType_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, Created, CreatedBy, EntityType, InternalName, IsActive, IsDirected, IsExplicit, IsTableRecordIDTarget, Name, Role_Source, Role_Target, Type, Updated, UpdatedBy)
SELECT 0, 0, 540513 /*From ID Server*/, 542156, rt.AD_Reference_Target_ID, TO_TIMESTAMP('2026-11-11 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, rt.EntityType, 'C_Order_CompensationGroup_Line_V_MD_Stock_PerWeek', 'Y', 'Y', rt.IsExplicit, rt.IsTableRecordIDTarget,
       'C_Order_CompensationGroup_Line_V -> MD_Stock_PerWeek_V', rt.Role_Source, rt.Role_Target, rt.Type, TO_TIMESTAMP('2026-11-11 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_RelationType rt WHERE rt.AD_RelationType_ID = 540499
;
-- ============================================================================
-- 3) AD_Tab 549518: tab on the view. The link column stays the group column (its field: hidden field 785681); Parent_Column_ID stays NULL.
-- ============================================================================
UPDATE AD_Tab
SET AD_Table_ID = 542660, AD_Column_ID = 593839 /* C_Order_CompensationGroup_Line_V.C_Order_CompensationGroup_ID */,
    Updated = TO_TIMESTAMP('2026-11-11 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Tab_ID = 549518
;
-- ============================================================================
-- 4) AD_Field.AD_Column_ID of the 10 configured fields -> the view's columns
-- ============================================================================
UPDATE AD_Field SET AD_Column_ID = 593840 /* C_Order_CompensationGroup_Line_V.Line */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785672 AND AD_Tab_ID = 549518 AND AD_Column_ID = 2214 /* C_OrderLine.Line */
;
UPDATE AD_Field SET AD_Column_ID = 593841 /* C_Order_CompensationGroup_Line_V.M_Product_ID */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785673 AND AD_Tab_ID = 549518 AND AD_Column_ID = 2221 /* C_OrderLine.M_Product_ID */
;
UPDATE AD_Field SET AD_Column_ID = 593842 /* C_Order_CompensationGroup_Line_V.QtyEntered */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785674 AND AD_Tab_ID = 549518 AND AD_Column_ID = 12876 /* C_OrderLine.QtyEntered */
;
UPDATE AD_Field SET AD_Column_ID = 593843 /* C_Order_CompensationGroup_Line_V.C_UOM_ID */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785675 AND AD_Tab_ID = 549518 AND AD_Column_ID = 2222 /* C_OrderLine.C_UOM_ID */
;
UPDATE AD_Field SET AD_Column_ID = 593844 /* C_Order_CompensationGroup_Line_V.PriceEntered */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785676 AND AD_Tab_ID = 549518 AND AD_Column_ID = 12875 /* C_OrderLine.PriceEntered */
;
UPDATE AD_Field SET AD_Column_ID = 593845 /* C_Order_CompensationGroup_Line_V.Price_UOM_ID */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785677 AND AD_Tab_ID = 549518 AND AD_Column_ID = 550846 /* C_OrderLine.Price_UOM_ID */
;
UPDATE AD_Field SET AD_Column_ID = 593846 /* C_Order_CompensationGroup_Line_V.LineNetAmt */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785678 AND AD_Tab_ID = 549518 AND AD_Column_ID = 3723 /* C_OrderLine.LineNetAmt */
;
UPDATE AD_Field SET AD_Column_ID = 593847 /* C_Order_CompensationGroup_Line_V.GroupCompensationType */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785679 AND AD_Tab_ID = 549518 AND AD_Column_ID = 557778 /* C_OrderLine.GroupCompensationType */
;
UPDATE AD_Field SET AD_Column_ID = 593848 /* C_Order_CompensationGroup_Line_V.GroupCompensationAmtType */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785680 AND AD_Tab_ID = 549518 AND AD_Column_ID = 557779 /* C_OrderLine.GroupCompensationAmtType */
;
UPDATE AD_Field SET AD_Column_ID = 593839 /* C_Order_CompensationGroup_Line_V.C_Order_CompensationGroup_ID */, Updated = TO_TIMESTAMP('2026-11-11 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID = 785681 AND AD_Tab_ID = 549518 AND AD_Column_ID = 557744 /* C_OrderLine.C_Order_CompensationGroup_ID */
;
