update ad_window 
set name = 'Fenster Verwaltung'
where ad_window_id = 102;
update ad_window_trl 
set name = 'Window Management'
where ad_window_id = 102 and ad_language in ('en_GB','en_US');