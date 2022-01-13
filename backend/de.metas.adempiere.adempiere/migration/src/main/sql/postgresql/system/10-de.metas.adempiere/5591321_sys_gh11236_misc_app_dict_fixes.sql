
update AD_table set isview='Y' where tablename='C_BPartner_Adv_Search_v';

delete from ad_ui_element where ad_field_id IN (select ad_field_id from ad_field where ad_column_id = (select ad_column.ad_column_id from ad_column WHERE columnname = 'TrackingNo'  AND ad_table_id = get_table_id('DD_Order')));
delete from ad_field where ad_column_id=(select ad_column_id from ad_column WHERE columnname = 'TrackingNo'  AND ad_table_id = get_table_id('DD_Order'));
delete from ad_column WHERE columnname = 'TrackingNo'  AND ad_table_id = get_table_id('DD_Order');

delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_column_id=(select ad_column.ad_column_id from ad_column WHERE columnname = 'TrackingNo' AND ad_table_id = get_table_id('M_InOut')));
delete from ad_field where ad_column_id=(select ad_column_id from ad_column WHERE columnname = 'TrackingNo'  AND ad_table_id = get_table_id('M_InOut'));
delete from ad_column WHERE columnname = 'TrackingNo'  AND ad_table_id = get_table_id('M_InOut');

delete from ad_ui_element where ad_field_id=(select ad_field_id from ad_field where ad_column_id=(select ad_column.ad_column_id from ad_column WHERE columnname = 'Name'  AND ad_table_id = get_table_id('PP_Order_Workflow_Trl')));
delete from ad_field where ad_column_id=(select ad_column_id from ad_column WHERE columnname = 'Name'  AND ad_table_id = get_table_id('PP_Order_Workflow_Trl'));
delete from ad_column WHERE columnname = 'Name'  AND ad_table_id = get_table_id('PP_Order_Workflow_Trl');



update ad_table set isview='Y' WHERE tablename ='MD_Available_For_Sales_QueryResult';

