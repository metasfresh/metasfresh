-- 2022-09-27T08:10:19.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540602,'AD_WF_Node.AD_Workflow_ID=@AD_Workflow_ID@',TO_TIMESTAMP('2022-09-27 11:10:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AD_WF_Node_by_AD_Workflow_ID','S',TO_TIMESTAMP('2022-09-27 11:10:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-27T08:10:30.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540602, IsUpdateable='N',Updated=TO_TIMESTAMP('2022-09-27 11:10:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53280
;

