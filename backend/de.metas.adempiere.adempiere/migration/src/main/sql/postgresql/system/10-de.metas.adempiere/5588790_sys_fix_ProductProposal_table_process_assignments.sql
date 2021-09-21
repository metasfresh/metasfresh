UPDATE ad_table_process tp
SET ad_window_id=tt.ad_window_id, updatedby=0, updated='2021-05-17'
FROM ad_tab tt
WHERE tt.ad_tab_id = tp.ad_tab_id
  AND tp.ad_window_id != tt.ad_window_id
;


update ad_table_process tp set ad_tab_id=null, updatedby=0, updated='2021-05-17' where ad_table_process_id=540665 and ad_tab_id=187;
update ad_table_process tp set ad_tab_id=null, updatedby=0, updated='2021-05-17' where ad_table_process_id=540667 and ad_tab_id=293;
