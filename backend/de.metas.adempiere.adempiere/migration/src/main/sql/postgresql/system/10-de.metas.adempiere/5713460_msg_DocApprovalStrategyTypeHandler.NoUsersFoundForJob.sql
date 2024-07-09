-- Value: DocApprovalStrategyTypeHandler.NoUsersFoundForJob
-- 2023-12-13T18:27:47.934246600Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545364,0,TO_TIMESTAMP('2023-12-13 20:27:47.461','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Für die Position {1} wurden keine Benutzer gefunden','E',TO_TIMESTAMP('2023-12-13 20:27:47.461','YYYY-MM-DD HH24:MI:SS.US'),100,'DocApprovalStrategyTypeHandler.NoUsersFoundForJob')
;

-- 2023-12-13T18:27:47.966714200Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545364 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: DocApprovalStrategyTypeHandler.NoUsersFoundForJob
-- 2023-12-13T18:28:03.252705600Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No users were found for the {1} position',Updated=TO_TIMESTAMP('2023-12-13 20:28:03.251','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545364
;

