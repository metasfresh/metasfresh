
delete from ad_table where tablename='X_MRP_ProductInfo_Detail_MV';

delete from ad_user_sortpref_line_product where ad_user_sortpref_line_id IN (select ad_user_sortpref_line_id from ad_user_sortpref_line where ad_user_sortpref_hdr_id IN (select ad_user_sortpref_hdr_id from ad_user_sortpref_hdr where ad_infowindow_id=(select ad_infowindow_id from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V'))));
delete from ad_user_sortpref_line where ad_user_sortpref_hdr_id IN (select ad_user_sortpref_hdr_id from ad_user_sortpref_hdr where ad_infowindow_id=(select ad_infowindow_id from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V')));
delete from ad_user_sortpref_hdr where ad_infowindow_id IN (select ad_infowindow_id from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V'));
delete from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V');
delete from ad_table where tablename='X_MRP_ProductInfo_V';

delete from ad_table_process where ad_process_id in (select ad_process_id from ad_process where value='Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref');
delete from ad_process where value='Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref'; 
