-- 2022-02-14T09:33:52.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,PP_Activity_Type,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540263,540114,0,TO_TIMESTAMP('2022-02-14 11:33:52','YYYY-MM-DD HH24:MI:SS'),540116,'CO',0,0,'U','Y','Y','N','N','X','Scan Scale Device','ScanScaleDevice','X',0,TO_TIMESTAMP('2022-02-14 11:33:52','YYYY-MM-DD HH24:MI:SS'),540116,'15',0,0,100,0)
;

-- 2022-02-14T09:33:52.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540263 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-02-14T09:34:03.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-02-14 11:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540263
;

