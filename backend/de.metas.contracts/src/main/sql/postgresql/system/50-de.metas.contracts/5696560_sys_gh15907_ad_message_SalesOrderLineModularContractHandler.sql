-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description
-- 2023-07-21T11:06:25.634338600Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545295,0,TO_TIMESTAMP('2023-07-21 14:06:25.217','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','A sales order for product {0} with the quantity {1} was completed.','I',TO_TIMESTAMP('2023-07-21 14:06:25.217','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description')
;

-- 2023-07-21T11:06:25.642598400Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545295 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;



-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnReverse.Description
-- 2023-07-21T12:30:39.519872100Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545296,0,TO_TIMESTAMP('2023-07-21 15:30:39.229','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales order for product {0} with the quantity {1} was reversed.','I',TO_TIMESTAMP('2023-07-21 15:30:39.229','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnReverse.Description')
;

-- 2023-07-21T12:30:39.525867500Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545296 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;



-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnReverse.Description
-- 2023-07-21T13:04:15.449359Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-07-21 16:04:15.449','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545296
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnReverse.Description
-- 2023-07-21T13:04:19.509854800Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-07-21 16:04:19.509','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545296
;

-- 2023-07-21T13:04:19.513774400Z
UPDATE AD_Message SET MsgText='Auftrag für Produkt {0} mit Menge {1} wurde storniert.' WHERE AD_Message_ID=545296
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnReverse.Description
-- 2023-07-21T13:04:21.625792100Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-07-21 16:04:21.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545296
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnReverse.Description
-- 2023-07-21T13:04:24.027034400Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit Menge {1} wurde storniert.',Updated=TO_TIMESTAMP('2023-07-21 16:04:24.027','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545296
;



-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description
-- 2023-07-21T13:05:24.960284Z
UPDATE AD_Message SET MsgText='Sales order for product {0} with the quantity {1} was completed.',Updated=TO_TIMESTAMP('2023-07-21 16:05:24.959','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545295
;

-- 2023-07-21T13:05:24.964280200Z
UPDATE AD_Message_Trl trl SET MsgText='Sales order for product {0} with the quantity {1} was completed.' WHERE AD_Message_ID=545295 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description
-- 2023-07-21T13:05:54.079727200Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-07-21 16:05:54.079','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545295
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description
-- 2023-07-21T13:05:57.830073600Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-07-21 16:05:57.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545295
;

-- 2023-07-21T13:05:57.831074500Z
UPDATE AD_Message SET MsgText='Auftrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.' WHERE AD_Message_ID=545295
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description
-- 2023-07-21T13:05:59.993753500Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-07-21 16:05:59.993','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545295
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.OnComplete.Description
-- 2023-07-21T13:06:01.782631400Z
UPDATE AD_Message_Trl SET MsgText='Auftrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.',Updated=TO_TIMESTAMP('2023-07-21 16:06:01.782','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545295
;



-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed
-- 2023-07-25T09:44:58.190829700Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545298,0,TO_TIMESTAMP('2023-07-25 12:44:58.0','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Sales order line cannot be deleted since it has modular contract logs!','I',TO_TIMESTAMP('2023-07-25 12:44:58.0','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed')
;

-- 2023-07-25T09:44:58.203827600Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545298 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed
-- 2023-07-25T10:13:13.574481600Z
UPDATE AD_Message SET MsgText='Sales order line cannot be deleted as it has modular contract logs.',Updated=TO_TIMESTAMP('2023-07-25 13:13:13.573','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545298
;

-- 2023-07-25T10:13:13.581589500Z
UPDATE AD_Message_Trl trl SET MsgText='Sales order line cannot be deleted as it has modular contract logs.' WHERE AD_Message_ID=545298 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed
-- 2023-07-25T10:13:52.201130900Z
UPDATE AD_Message_Trl SET MsgText='Die Auftragszeile kann nicht gelöscht werden, da sie über Protokolle zum modularen Vertrag verfügt.',Updated=TO_TIMESTAMP('2023-07-25 13:13:52.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545298
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed
-- 2023-07-25T10:13:59.533079900Z
UPDATE AD_Message_Trl SET MsgText='Die Auftragszeile kann nicht gelöscht werden, da sie über Protokolle zum modularen Vertrag verfügt.',Updated=TO_TIMESTAMP('2023-07-25 13:13:59.533','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545298
;

-- 2023-07-25T10:13:59.534094Z
UPDATE AD_Message SET MsgText='Die Auftragszeile kann nicht gelöscht werden, da sie über Protokolle zum modularen Vertrag verfügt.' WHERE AD_Message_ID=545298
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed
-- 2023-07-25T10:14:01.995377500Z
UPDATE AD_Message_Trl SET MsgText='Die Auftragszeile kann nicht gelöscht werden, da sie über Protokolle zum modularen Vertrag verfügt.',Updated=TO_TIMESTAMP('2023-07-25 13:14:01.995','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545298
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed
-- 2023-07-25T10:14:04.897430600Z
UPDATE AD_Message_Trl SET MsgText='Die Auftragszeile kann nicht gelöscht werden, da sie über Protokolle zum modularen Vertrag verfügt.',Updated=TO_TIMESTAMP('2023-07-25 13:14:04.897','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545298
;

-- Value: de.metas.contracts.modular.impl.SalesOrderLineModularContractHandler.DeletionNotAllowed
-- 2023-07-25T10:14:12.098887800Z
UPDATE AD_Message_Trl SET MsgText='Sales order line cannot be deleted as it has modular contract logs.',Updated=TO_TIMESTAMP('2023-07-25 13:14:12.098','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545298
;


