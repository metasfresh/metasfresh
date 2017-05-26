-- 2017-05-26T16:22:19.452
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, IsAutocomplete='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-05-26 16:22:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=60346
;

-- 2017-05-26T16:22:21.387
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_table_process','AD_Process_ID','NUMERIC(10)',null,null)
;

-- 2017-05-26T16:24:01.867
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,346,208,TO_TIMESTAMP('2017-05-26 16:24:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y',TO_TIMESTAMP('2017-05-26 16:24:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;

