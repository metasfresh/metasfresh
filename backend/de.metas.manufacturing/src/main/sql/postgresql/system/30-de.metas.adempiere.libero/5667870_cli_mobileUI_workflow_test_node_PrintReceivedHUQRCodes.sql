-- -- 2022-12-09T08:35:12.076Z
-- UPDATE T_WEBUI_ViewSelection_ToDelete SET Executor_UUID=? WHERE Executor_UUID IS NULL
-- ;

-- 2022-12-09T08:39:32.302Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,IsMilestone,IsSubcontracting,JoinElement,Name,PP_Activity_Type,SplitElement,UnitsCycles,Updated,UpdatedBy,Value,WaitingTime,XPosition,Yield,YPosition) VALUES ('Z',1000000,1000000,540294,540114,0,TO_TIMESTAMP('2022-12-09 10:39:32','YYYY-MM-DD HH24:MI:SS'),540116,'CO',0,0,'U','Y','Y','N','N','X','Print Received HUs','PrintReceivedHUQRCodes','X',0,TO_TIMESTAMP('2022-12-09 10:39:32','YYYY-MM-DD HH24:MI:SS'),540116,'45',0,0,100,0)
;

-- 2022-12-09T08:39:32.316Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540294 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-12-09T09:14:07.735Z
UPDATE AD_WF_Node SET Action='Z', S_Resource_ID=540011,Updated=TO_TIMESTAMP('2022-12-09 11:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540294
;

-- 2022-12-13T07:41:18.947Z
UPDATE AD_WF_Node SET Action='Z', Name='QR-Druck empfangener HUs',Updated=TO_TIMESTAMP('2022-12-13 09:41:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540294
;

-- 2022-12-13T07:41:18.972Z
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='QR-Druck empfangener HUs'  WHERE AD_WF_Node_ID=540294 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-12-13T07:47:14.729Z
UPDATE AD_WF_Node SET Action='Z', PP_AlwaysAvailableToUser='Y',Updated=TO_TIMESTAMP('2022-12-13 09:47:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540294
;

