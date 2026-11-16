-- Follow-up to 5807750: DD_Order.LocatorPriorityNo virtual column
-- 1) Lowercase ColumnSQL keywords + table names (Convert_PostgreSQL case-sensitive on uppercase keywords).
-- 2) Add AD_SQLColumn_SourceTableColumn entries so the WebUI invalidates DD_Order rows
--    when DD_OrderLine or M_Locator rows change.
-- See https://github.com/metasfresh/me03/issues/29941

-- IDs from idserver.metas.de:
--   AD_SQLColumn_SourceTableColumn 540204 (DD_OrderLine)
--   AD_SQLColumn_SourceTableColumn 540205 (M_Locator)

-- 1. Lowercase the ColumnSQL on AD_Column 592811 (DD_Order.LocatorPriorityNo)
UPDATE AD_Column
SET ColumnSQL = '(select case when count(distinct ol.M_Locator_ID) = 1 then max(loc.PriorityNo) else null end from dd_orderline ol left join m_locator loc on loc.M_Locator_ID = ol.M_Locator_ID where ol.DD_Order_ID = @JoinTableNameOrAliasIncludingDot@DD_Order_ID)',
    Updated = TO_TIMESTAMP('2026-06-15 23:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 592811
;

-- 2. AD_SQLColumn_SourceTableColumn: DD_OrderLine drives invalidation of DD_Order rows
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID,
	 Source_Table_ID, Link_Column_ID,
	 FetchTargetRecordsMethod)
SELECT
	540204 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-15 23:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-06-15 23:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
	53037, 592811,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='DD_OrderLine'),
	(SELECT c.AD_Column_ID FROM AD_Column c JOIN AD_Table t ON t.AD_Table_ID=c.AD_Table_ID
	  WHERE t.TableName='DD_OrderLine' AND c.ColumnName='DD_Order_ID'),
	'L'
WHERE NOT EXISTS (
	SELECT 1 FROM AD_SQLColumn_SourceTableColumn
	WHERE AD_Column_ID=592811
	  AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='DD_OrderLine')
)
;

-- 3. AD_SQLColumn_SourceTableColumn: M_Locator changes drive invalidation of DD_Order rows
--    Link via DD_OrderLine.M_Locator_ID; FetchTargetRecordsMethod 'S' (custom SQL) since the
--    relationship is indirect: a locator change affects every DD_Order whose lines reference it.
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID,
	 Source_Table_ID,
	 Sql_GetTargetRecordIdBySourceRecordId,
	 FetchTargetRecordsMethod)
SELECT
	540205 /*From ID Server*/, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-15 23:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
	TO_TIMESTAMP('2026-06-15 23:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
	53037, 592811,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Locator'),
	'select distinct DD_Order_ID from dd_orderline where M_Locator_ID = @Record_ID@',
	'S'
WHERE NOT EXISTS (
	SELECT 1 FROM AD_SQLColumn_SourceTableColumn
	WHERE AD_Column_ID=592811
	  AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Locator')
)
;
