
UPDATE AD_Tab set IsInsertRecord='N', updatedby=100, updated='2026-03-17 12:01' where ad_table_id=get_table_id('C_Currency');
UPDATE AD_Table set technicalnote='Don''t allow inserting new currencies via UI, because their ISO-Codes must match the codes "hardcoded" in java.util.Currency.
'||COALESCE(technicalnote,''), updatedby=100, updated='2026-03-17 12:01' where ad_table_id=get_table_id('C_Currency');

UPDATE AD_Field
SET isreadonly='Y', updatedby=100, updated='2026-03-17 12:01'
WHERE ad_column_id IN (SELECT ad_column_id FROM ad_column WHERE ad_table_id = get_table_id('C_Currency') AND columnname = 'ISO_Code')
;
