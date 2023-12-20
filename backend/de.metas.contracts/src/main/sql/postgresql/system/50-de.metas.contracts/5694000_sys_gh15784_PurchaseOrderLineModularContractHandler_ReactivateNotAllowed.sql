-- Value: de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.ReactivateNotAllowed
-- 2023-07-03T16:49:52.655925700Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545281,0,TO_TIMESTAMP('2023-07-03 19:49:52.432','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Die Reaktivierung von Aufträgen mit modularen Verträgen ist nicht erlaubt!','E',TO_TIMESTAMP('2023-07-03 19:49:52.432','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.ReactivateNotAllowed')
;

-- 2023-07-03T16:49:52.660926200Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545281 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.ReactivateNotAllowed
-- 2023-07-03T16:50:07.155910900Z
UPDATE AD_Message_Trl SET MsgText='Reactivating orders with modular contract(s) is not allowed!',Updated=TO_TIMESTAMP('2023-07-03 19:50:07.154','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545281
;

