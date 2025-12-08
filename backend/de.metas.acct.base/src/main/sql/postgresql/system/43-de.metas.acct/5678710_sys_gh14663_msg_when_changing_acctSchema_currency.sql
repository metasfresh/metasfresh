-- 2023-02-21T13:06:27.394Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545240,0,TO_TIMESTAMP('2023-02-21 15:06:26.794','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Währung kann nicht geändert werden, solange dieses Buchführungsschema noch aktuelle Produktkosten in der vorherigen Währung beinhaltet.','I',TO_TIMESTAMP('2023-02-21 15:06:26.794','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct.AcctSchema.hasCosts')
;

-- 2023-02-21T13:06:27.446Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545240 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-02-21T13:06:44.990Z
UPDATE AD_Message_Trl SET MsgText='Cannot change currency while current product costs associated with this accounting schema still exist in the previous currency.',Updated=TO_TIMESTAMP('2023-02-21 15:06:44.849','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545240
;

