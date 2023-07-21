DELETE
FROM ad_table
WHERE tablename = 'X_MRP_ProductInfo_Detail_MV'
;

DELETE
FROM ad_user_sortpref_line
WHERE ad_user_sortpref_hdr_id in (SELECT ad_user_sortpref_hdr_id FROM ad_user_sortpref_hdr WHERE ad_infowindow_id in (SELECT ad_infowindow_id FROM ad_infowindow WHERE ad_table_id = get_table_id('X_MRP_ProductInfo_V')))
;

DELETE
FROM ad_user_sortpref_hdr
WHERE ad_infowindow_id in (SELECT ad_infowindow_id FROM ad_infowindow WHERE ad_table_id = get_table_id('X_MRP_ProductInfo_V'))
;

DELETE
FROM ad_infowindow
WHERE ad_table_id = get_table_id('X_MRP_ProductInfo_V')
;

DELETE
FROM ad_table
WHERE tablename = 'X_MRP_ProductInfo_V'
;

DELETE
FROM ad_process
WHERE value = 'Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref'
;
