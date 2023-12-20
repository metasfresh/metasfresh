-- Value: de.metas.contracts.modular.impl.InventoryLineModularContractHandler.Description
-- 2023-07-05T16:38:16.811637Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545285,0,TO_TIMESTAMP('2023-07-05 19:38:16.585','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Bei der Inventur wurde ein Fehl-/Mehrbestand von {0} gezählt.','I',TO_TIMESTAMP('2023-07-05 19:38:16.585','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.InventoryLineModularContractHandler.Description')
;

-- 2023-07-05T16:38:16.822636900Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545285 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.InventoryLineModularContractHandler.Description
-- 2023-07-05T16:38:27.269552500Z
UPDATE AD_Message_Trl SET MsgText='A shortage/excess stock of {0} was counted during inventory.',Updated=TO_TIMESTAMP('2023-07-05 19:38:27.269','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545285
;

