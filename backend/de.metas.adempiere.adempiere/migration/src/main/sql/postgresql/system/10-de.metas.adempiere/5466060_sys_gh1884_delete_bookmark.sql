-- deletes bookmark menu for user metasfresh allowing fallback to full menu
delete from ad_treebar where ad_user_id = 100 and node_id = 144;
