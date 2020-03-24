-- 2020-03-23T09:03:54.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2020-03-23 11:03:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2901
;
-- 2020-03-23T09:17:24.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2020-03-23 11:17:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2902
;

update ad_ref_table
set isvaluedisplayed = 'N'
where ad_table_id = 291
and ad_window_id = 232;