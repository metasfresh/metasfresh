DROP VIEW IF EXISTS dlm.AD_ChangeLog_counts_v;

CREATE OR REPLACE VIEW dlm.AD_ChangeLog_counts_v as
SELECT t.TableName, c.ColumnName, count(l.*) as count,
-- bring your own 
-- DELETE FROM public.AD_ChangeLog WHERE false
-- to use these clauses all in one SQL
	' OR (AD_Column_ID='||l.AD_Column_ID||' AND AD_Table_ID='||l.AD_Table_ID||')' as delete_where_clause,
	'UPDATE AD_Column SET IsAllowLogging=''N'' WHERE AD_Column_ID='||l.AD_Column_ID||';' as update_column_sql
FROM public.AD_ChangeLog l
	JOIN public.AD_Table t ON l.AD_Table_ID=t.AD_Table_ID
	JOIN public.AD_Column c ON c.AD_Column_ID=l.AD_Column_ID
GROUP BY l.AD_Table_ID, l.AD_Column_ID, t.TableName, c.ColumnName
;
COMMENT ON view dlm.AD_ChangeLog_counts_v IS 'Selects the number of AD_Changelog records grouped by table and column.
This view might take a *long time* to complete.

The delete_where_clause and update_column_sql columns are supposed to provide usefull snippets to clean up where needed.

Suggested use:

create table backup.AD_ChangeLog_counts_<current-date> AS
select * from AD_ChangeLog_counts_v;
';