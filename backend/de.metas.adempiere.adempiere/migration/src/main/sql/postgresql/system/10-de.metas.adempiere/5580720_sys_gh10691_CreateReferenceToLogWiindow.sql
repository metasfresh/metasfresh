-- 2021-02-26T10:11:42.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19, FieldLength=22,Updated=TO_TIMESTAMP('2021-02-26 12:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T10:22:27.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2021-02-26 12:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T10:23:32.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='N',Updated=TO_TIMESTAMP('2021-02-26 12:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T10:37:51.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541268,TO_TIMESTAMP('2021-02-26 12:37:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ExternaSystemConfig',TO_TIMESTAMP('2021-02-26 12:37:51','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-02-26T10:37:51.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541268 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-26T10:40:35.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,572724,0,541268,541576,TO_TIMESTAMP('2021-02-26 12:40:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-02-26 12:40:35','YYYY-MM-DD HH24:MI:SS'),100,'exists (select 1 from ExternalSystem_Config_PInstance_Log_v log where log.ExternalSystem_Config_ID = ExternalSystem_Config.ExternalSystem_Config_ID)')
;

-- 2021-02-26T10:46:56.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=541024,Updated=TO_TIMESTAMP('2021-02-26 12:46:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T10:47:22.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541269,TO_TIMESTAMP('2021-02-26 12:47:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ExternaSystemConfigLog',TO_TIMESTAMP('2021-02-26 12:47:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-02-26T10:47:22.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541269 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-26T10:47:44.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,572969,0,541269,541584,541040,TO_TIMESTAMP('2021-02-26 12:47:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-02-26 12:47:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-26T10:48:32.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541268,541269,540279,TO_TIMESTAMP('2021-02-26 12:48:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','ExternalConfig -> External Config Log',TO_TIMESTAMP('2021-02-26 12:48:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-26T11:05:33.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541269, AD_Reference_Target_ID=541268,Updated=TO_TIMESTAMP('2021-02-26 13:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T11:08:38.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL, WhereClause='exists (select 1 from ExternalSystem_Config esc where esc.ExternalSystem_Config_ID = ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_ID)',Updated=TO_TIMESTAMP('2021-02-26 13:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T11:08:52.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-02-26 13:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T11:25:37.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ExternalSystem_Config_ID = @ExternalSystem_Config_ID@',Updated=TO_TIMESTAMP('2021-02-26 13:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T11:30:00.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=541040,Updated=TO_TIMESTAMP('2021-02-26 13:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T11:32:54.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2021-02-26 13:32:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T11:33:58.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2021-02-26 13:33:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T11:35:47.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='N',Updated=TO_TIMESTAMP('2021-02-26 13:35:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T11:37:50.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=0,Updated=TO_TIMESTAMP('2021-02-26 13:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T11:39:27.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541268, AD_Reference_Target_ID=541269, IsActive='Y', IsDirected='Y',Updated=TO_TIMESTAMP('2021-02-26 13:39:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T11:40:37.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from ExternalSystem_Config esc where esc.ExternalSystem_Config_ID = ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_ID)',Updated=TO_TIMESTAMP('2021-02-26 13:40:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T11:40:59.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ExternalSystem_Config_ID = @ExternalSystem_Config_ID@',Updated=TO_TIMESTAMP('2021-02-26 13:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T11:44:50.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=541024,Updated=TO_TIMESTAMP('2021-02-26 13:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T11:46:13.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2021-02-26 13:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T11:51:16.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from ExternalSystem_Config_PInstance_Log_v log where log.ExternalSystem_Config_ID =ExternalSystem_Config.ExternalSystem_Config_ID)',Updated=TO_TIMESTAMP('2021-02-26 13:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T11:55:37.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=572714, WhereClause='',Updated=TO_TIMESTAMP('2021-02-26 13:55:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T11:59:25.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=572974, WhereClause='ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_ID =@ExternalSystem_Config_ID@ ',Updated=TO_TIMESTAMP('2021-02-26 13:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T11:59:33.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2021-02-26 13:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T12:03:46.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='EXISTS (  select 1 from ExternalSystem_Config_PInstance_Log_v log  INNER JOIN ExternalSystem_Config ec on log.ExternalSystem_Config_ID = ec.ExternalSystem_Config_ID  where ec.ExternalSystem_Config_ID = @ExternalSystem_Config_ID@   AND ec.ExternalSystem_Config_ID = ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_ID )',Updated=TO_TIMESTAMP('2021-02-26 14:03:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T12:10:01.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=572724, AD_Table_ID=541576, WhereClause='EXISTS (  select 1 from ExternalSystem_Config_PInstance_Log_v log  INNER JOIN ExternalSystem_Config ec on log.ExternalSystem_Config_ID = ec.ExternalSystem_Config_ID  where ec.ExternalSystem_Config_ID = @ExternalSystem_Config_ID@   AND ec.ExternalSystem_Config_ID = ExternalSystem_Config.ExternalSystem_Config_ID )',Updated=TO_TIMESTAMP('2021-02-26 14:10:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T12:10:15.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=572714,Updated=TO_TIMESTAMP('2021-02-26 14:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T12:15:54.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541269, AD_Reference_Target_ID=541268, IsDirected='N',Updated=TO_TIMESTAMP('2021-02-26 14:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T12:19:39.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from ExternalSystem_Config_PInstance_Log_v v  where ExternalSystem_Config_ID = @ExternalSystem_Config_ID@ and v.ExternalSystem_Config_ID = ExternalSystem_Config.ExternalSystem_Config_ID)',Updated=TO_TIMESTAMP('2021-02-26 14:19:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T12:20:40.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2021-02-26 14:20:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T12:23:47.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=572974, AD_Key=572974, AD_Table_ID=541584, AD_Window_ID=NULL, WhereClause='exists (select 1 from ExternalSystem_Config_PInstance_Log_v v where  v.ExternalSystem_Config_ID = ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_ID)',Updated=TO_TIMESTAMP('2021-02-26 14:23:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T12:26:09.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541268, AD_Reference_Target_ID=541269,Updated=TO_TIMESTAMP('2021-02-26 14:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T12:28:46.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from ExternalSystem_Config_PInstance_Log_v v  where v.ExternalSystem_Config_ID = @ExternalSystem_Config_ID@ and v.ExternalSystem_Config_ID = ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_ID)',Updated=TO_TIMESTAMP('2021-02-26 14:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T12:30:29.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists (select 1 from ExternalSystem_Config_PInstance_Log_v v where  v.ExternalSystem_Config_ID = ExternalSystem_Config.ExternalSystem_Config_ID)',Updated=TO_TIMESTAMP('2021-02-26 14:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T12:43:21.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2021-02-26 14:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T12:43:53.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=572969, WhereClause='',Updated=TO_TIMESTAMP('2021-02-26 14:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T12:44:06.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=541024,Updated=TO_TIMESTAMP('2021-02-26 14:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T12:44:45.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=572714, AD_Key=572724, AD_Table_ID=541576,Updated=TO_TIMESTAMP('2021-02-26 14:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T12:47:47.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=572974, AD_Key=572969, AD_Table_ID=541584, AD_Window_ID=541040, WhereClause='exists (select 1 from ExternalSystem_Config_PInstance_Log_v v where  v.ExternalSystem_Config_ID = @ExternalSystem_Config_ID@ and v.ExternalSystem_Config_PInstance_Log_v_ID = ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_PInstance_Log_v_ID)',Updated=TO_TIMESTAMP('2021-02-26 14:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T12:50:27.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ExternalSystem_Config_ID = @ExternalSystem_Config_ID@',Updated=TO_TIMESTAMP('2021-02-26 14:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T12:51:25.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ExternalSystem_Config_ID = @ExternalSystem_Config_ID@',Updated=TO_TIMESTAMP('2021-02-26 14:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T13:00:00.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=572974,Updated=TO_TIMESTAMP('2021-02-26 15:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T13:05:12.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541269, AD_Reference_Target_ID=541268,Updated=TO_TIMESTAMP('2021-02-26 15:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T13:06:18.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541268, AD_Reference_Target_ID=541269,Updated=TO_TIMESTAMP('2021-02-26 15:06:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;

-- 2021-02-26T13:06:22.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='N',Updated=TO_TIMESTAMP('2021-02-26 15:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540279
;


-- 2021-02-26T14:20:17.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541268,541269,540280,TO_TIMESTAMP('2021-02-26 16:20:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','ExternalSystemConfig -> External System Log',TO_TIMESTAMP('2021-02-26 16:20:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-26T14:39:20.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='ExternalSystem_Config_ID = @ExternalSystem_Config_ID@ and ExternalSystem_Config_PInstance_Log_v.ExternalSystem_Config_ID = @ExternalSystem_Config_ID@',Updated=TO_TIMESTAMP('2021-02-26 16:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541269
;

-- 2021-02-26T14:42:08.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2021-02-26 16:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541268
;

-- 2021-02-26T14:44:08.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2021-02-26 16:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T14:45:26.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='N',Updated=TO_TIMESTAMP('2021-02-26 16:45:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T14:56:06.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2021-02-26 16:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572974
;

-- 2021-02-26T14:56:18.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2021-02-26 16:56:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540280
;

-- 2021-02-26T14:57:17.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='Y',Updated=TO_TIMESTAMP('2021-02-26 16:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540280
;

-- 2021-02-26T14:57:36.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsActive='N',Updated=TO_TIMESTAMP('2021-02-26 16:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540280
;

-- 2021-02-26T14:58:33.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-02-26 16:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572724
;

