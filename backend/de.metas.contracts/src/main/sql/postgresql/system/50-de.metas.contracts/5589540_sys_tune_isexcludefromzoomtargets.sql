/*
SELECT c.isexcludefromzoomtargets, fk.*, c.ad_column_id
FROM db_columns_fk fk
         LEFT OUTER JOIN ad_column c ON c.ad_table_id = get_table_id(fk.tablename) AND c.columnname = fk.columnname
WHERE fk.tablename = 'C_Flatrate_Term'
  AND fk.ref_tablename = 'C_BPartner';
*/


-- C_Flatrate_Term.Bill_BPartner_ID
update ad_column c set isexcludefromzoomtargets='N'
where c.ad_column_id=546056 and c.isexcludefromzoomtargets='Y';

