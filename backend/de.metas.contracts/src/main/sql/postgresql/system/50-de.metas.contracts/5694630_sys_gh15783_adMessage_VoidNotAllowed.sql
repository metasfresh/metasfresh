-- Value: de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed
-- 2023-07-05T10:07:47.204649300Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545284,0,TO_TIMESTAMP('2023-07-05 13:07:47.0','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Cannot void purchase order because the following goods receipts have already been created for it: {0}.','E',TO_TIMESTAMP('2023-07-05 13:07:47.0','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed')
;

-- 2023-07-05T10:07:47.214649600Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545284 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed
-- 2023-07-05T10:07:57.894675Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung kann nicht storniert werden, da bereits folgende Wareneingänge für sie erstellt wurden: {0}',Updated=TO_TIMESTAMP('2023-07-05 13:07:57.894','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545284
;

-- Value: de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed
-- 2023-07-05T10:08:03.987375200Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung kann nicht storniert werden, da bereits folgende Wareneingänge für sie erstellt wurden: {0}',Updated=TO_TIMESTAMP('2023-07-05 13:08:03.986','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545284
;

-- 2023-07-05T10:08:03.990932Z
UPDATE AD_Message SET MsgText='Die Bestellung kann nicht storniert werden, da bereits folgende Wareneingänge für sie erstellt wurden: {0}' WHERE AD_Message_ID=545284
;

-- Value: de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed
-- 2023-07-05T10:08:06.855672400Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung kann nicht storniert werden, da bereits folgende Wareneingänge für sie erstellt wurden: {0}',Updated=TO_TIMESTAMP('2023-07-05 13:08:06.854','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545284
;

-- Value: de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed
-- 2023-07-05T10:08:10.439444500Z
UPDATE AD_Message_Trl SET MsgText='Die Bestellung kann nicht storniert werden, da bereits folgende Wareneingänge für sie erstellt wurden: {0}',Updated=TO_TIMESTAMP('2023-07-05 13:08:10.439','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545284
;

