

UPDATE AD_Tab SET IsAdvancedTab='N', CreatedBy=99, Created='2021-01-08 11:28:58.401604' WHERE IsAdvancedTab='Y';

--select * from ad_column where columnname='IsAdvancedTab' and ad_Table_id=get_table_id('AD_Tab')
UPDATE ad_column set technicalnote='This column is deprecated. It shall always be N.
                                   Otherwise the tab might be skipped in org.compiere.model.GridTabVO.loadTabDetails depending in the current user, and that result is cached for all users.'
where ad_column_id=13995;

update ad_field set isreadonly='Y' where ad_column_id=13995;
