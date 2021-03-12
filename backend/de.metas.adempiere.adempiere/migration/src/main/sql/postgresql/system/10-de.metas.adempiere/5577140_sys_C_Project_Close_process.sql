-- 2021-01-22T07:20:37.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.project.process.C_Project_Close',Updated=TO_TIMESTAMP('2021-01-22 09:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=227
;

-- 2021-01-22T09:00:03.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,227,203,540890,TO_TIMESTAMP('2021-01-22 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2021-01-22 11:00:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N')
;

-- 2021-01-22T09:00:56.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Process_ID=NULL, AD_Reference_ID=20, DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-01-22 11:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=9861
;

-- 2021-01-22T09:01:58.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project','Processing','CHAR(1)',null,'N')
;

-- 2021-01-22T09:01:59.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Project SET Processing='N' WHERE Processing IS NULL
;

-- 2021-01-22T09:01:59.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_project','Processing',null,'NOT NULL',null)
;

