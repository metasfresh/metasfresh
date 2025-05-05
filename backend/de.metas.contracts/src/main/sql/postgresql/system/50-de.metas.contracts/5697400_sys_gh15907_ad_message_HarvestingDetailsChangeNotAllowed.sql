-- Value: de.metas.contracts.modular.interceptor.C_Order.HarvestingDetailsChangeNotAllowed
-- 2023-07-28T08:10:09.224204500Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545310,0,TO_TIMESTAMP('2023-07-28 11:10:09.017','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Erntedetails können nicht geändert werden, da für einige der enthaltenen Auftragszeilen bereits Protokolleinträge zum modularen Vertrag vorliegen.','E',TO_TIMESTAMP('2023-07-28 11:10:09.017','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.interceptor.C_Order.HarvestingDetailsChangeNotAllowed')
;

-- 2023-07-28T08:10:09.234227Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545310 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.interceptor.C_Order.HarvestingDetailsChangeNotAllowed
-- 2023-07-28T08:10:20.938319300Z
UPDATE AD_Message_Trl SET MsgText='Cannot change harvest details because some of the included order lines already have modular contract log entries.',Updated=TO_TIMESTAMP('2023-07-28 11:10:20.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545310
;

