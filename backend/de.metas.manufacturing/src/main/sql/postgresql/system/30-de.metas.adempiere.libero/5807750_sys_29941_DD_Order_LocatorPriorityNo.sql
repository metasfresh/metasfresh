-- Virtual column DD_Order.LocatorPriorityNo
-- Returns M_Locator.PriorityNo when every DD_OrderLine on this DD_Order shares the same M_Locator_ID; NULL otherwise.
-- Used as the SQL backing for DDOrderQuery.OrderByField.LocatorPriority (mobile distribution sort).
-- See https://github.com/metasfresh/me03/issues/29941

-- IDs from idserver.metas.de:
--   AD_Element 584994
--   AD_Column  592811

-- 1. AD_Element
INSERT INTO AD_Element
	(AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 ColumnName, Name, PrintName, EntityType)
VALUES
	(584994 /*From ID Server*/, 0, 0, 'Y',
	 TO_TIMESTAMP('2026-06-15 22:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	 TO_TIMESTAMP('2026-06-15 22:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	 'LocatorPriorityNo', 'Lagerort Priorität', 'Lagerort Priorität', 'EE01')
;

-- 2. AD_Element_Trl skeleton (inherit parent's Updated)
INSERT INTO AD_Element_Trl
	(AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 Name, PrintName, Description, Help, CommitWarning, PO_Description, PO_Help, PO_Name, PO_PrintName, IsTranslated)
SELECT
	t.AD_Element_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
	t.Name, t.PrintName, t.Description, t.Help, t.CommitWarning, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, 'N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Element_ID=584994
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 3. English translation (strictly later than the AD_Element INSERT)
UPDATE AD_Element_Trl
SET Name='Locator Priority',
    PrintName='Locator Priority',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-15 22:01:30', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID=584994 AND AD_Language='en_US'
;

-- 4. AD_Column (virtual; backed by ColumnSQL — no DDL sync)
INSERT INTO AD_Column
	(AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Element_ID, ColumnName, Name, EntityType,
	 AD_Reference_ID, FieldLength,
	 ColumnSQL, IsCalculated, IsSyncDatabase, IsUpdateable, IsAlwaysUpdateable,
	 IsAllowLogging, IsAutoApplyValidationRule, IsAdvancedText,
	 IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets,
	 IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey,
	 IsLazyLoading, IsMandatory, IsParent, IsRangeFilter, IsSelectionColumn,
	 IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsTranslated, IsUseDocSequence,
	 SeqNo, Version, PersonalDataCategory)
VALUES
	(592811 /*From ID Server*/, 0, 0, 'Y',
	 TO_TIMESTAMP('2026-06-15 22:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	 TO_TIMESTAMP('2026-06-15 22:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
	 53037, 584994, 'LocatorPriorityNo', 'Lagerort Priorität', 'EE01',
	 11, 22,
	 '(SELECT CASE WHEN COUNT(DISTINCT ol.M_Locator_ID) = 1 THEN MAX(loc.PriorityNo) ELSE NULL END FROM DD_OrderLine ol LEFT JOIN M_Locator loc ON loc.M_Locator_ID = ol.M_Locator_ID WHERE ol.DD_Order_ID = @JoinTableNameOrAliasIncludingDot@DD_Order_ID)',
	 'Y', 'N', 'N', 'N',
	 'Y', 'N', 'N',
	 'N', 'N', 'Y',
	 'N', 'N', 'N', 'N', 'N',
	 'Y', 'N', 'N', 'N', 'N',
	 'N', 'N', 'N', 'N', 'N',
	 0, 0, 'NP')
;

-- 5. AD_Column_Trl skeleton (inherit parent's Updated)
INSERT INTO AD_Column_Trl
	(AD_Column_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 Name, Description, IsTranslated)
SELECT
	t.AD_Column_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy,
	t.Name, t.Description, 'N'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y'
  AND t.AD_Column_ID=592811
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 6. Propagate element translations to AD_Column_Trl
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584994);
