-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.workpackage.impl.SOLineForSOLogHandler.OnComplete.Description
-- 2023-09-19T15:29:03.850Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545340,0,TO_TIMESTAMP('2023-09-19 18:29:03.639','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Ein Auftrag, der das Produkt {0} mit der Menge {1} enthält, wurde fertiggestellt, was die Erstellung eines modularen Vertrags auslöste.','I',TO_TIMESTAMP('2023-09-19 18:29:03.639','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.workpackage.impl.SOLineForSOLogHandler.OnComplete.Description')
;

-- 2023-09-19T15:29:03.870Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545340 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.workpackage.impl.SOLineForSOLogHandler.OnComplete.Description
-- 2023-09-19T15:29:14.350Z
UPDATE AD_Message_Trl SET MsgText='A sales order including the product {0} with the quantity {1} was completed, triggering the creation of a modular contract.',Updated=TO_TIMESTAMP('2023-09-19 18:29:14.35','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545340
;

