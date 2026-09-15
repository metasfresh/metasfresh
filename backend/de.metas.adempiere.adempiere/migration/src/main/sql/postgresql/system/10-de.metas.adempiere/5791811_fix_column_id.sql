BEGIN;
SET CONSTRAINTS ALL DEFERRED;
update ad_field set ad_column_id=579379 where ad_column_id=(select ad_column_id /*, columnname, tablename*/ from ad_column c, ad_table t where c.ad_column_id=592131 and t.ad_table_id=c.ad_table_id and t.tablename='M_QtyReservation' and c.columnname='Processed');
update ad_column_trl set ad_column_id=579379 where ad_column_id=(select ad_column_id /*, columnname, tablename*/ from ad_column c, ad_table t where c.ad_column_id=592131 and t.ad_table_id=c.ad_table_id and t.tablename='M_QtyReservation' and c.columnname='Processed');
update ad_column set ad_column_id=579379 where ad_column_id=(select ad_column_id /*, columnname, tablename*/ from ad_column c, ad_table t where c.ad_column_id=592131 and t.ad_table_id=c.ad_table_id and t.tablename='M_QtyReservation' and c.columnname='Processed');
commit;
