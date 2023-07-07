-- Value: de.metas.contracts.modular.impl.InventoryLineModularContractHandler.ReactivateNotAllowed
-- 2023-07-05T16:39:34.587191200Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545286,0,TO_TIMESTAMP('2023-07-05 19:39:34.368','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Die Reaktivierung von Inventurbelegen mit modularen Verträgen ist nicht erlaubt!','E',TO_TIMESTAMP('2023-07-05 19:39:34.368','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.InventoryLineModularContractHandler.ReactivateNotAllowed')
;

-- 2023-07-05T16:39:34.589179200Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545286 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.InventoryLineModularContractHandler.ReactivateNotAllowed
-- 2023-07-05T16:39:42.972900Z
UPDATE AD_Message_Trl SET MsgText='Reactivating inventory documents with modular contracts is not allowed!',Updated=TO_TIMESTAMP('2023-07-05 19:39:42.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545286
;

