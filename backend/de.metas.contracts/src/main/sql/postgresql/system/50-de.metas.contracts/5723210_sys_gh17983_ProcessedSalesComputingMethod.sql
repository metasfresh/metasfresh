-- Run mode: SWING_CLIENT

-- Name: ModCntr_Type
-- 2024-05-08T08:51:14.402Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540674,'(CASE WHEN @M_Product_ID/-1@ != @M_Processed_Product_ID/-1@ AND modcntr_type.modularcontracthandlertype IN (''SalesOnProcessedProduct'', ''AddValueOnProcessedProduct'') THEN false
    ELSE true
    END)',TO_TIMESTAMP('2024-05-08 11:51:14.156','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','ModCntr_Type','S',TO_TIMESTAMP('2024-05-08 11:51:14.156','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Module.ModCntr_Type_ID
-- 2024-05-08T08:51:41.066Z
UPDATE AD_Column SET AD_Val_Rule_ID=540674,Updated=TO_TIMESTAMP('2024-05-08 11:51:41.066','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586809
;

-- 2024-05-08T08:51:44.294Z
INSERT INTO t_alter_column values('modcntr_module','ModCntr_Type_ID','NUMERIC(10)',null,null)
;

-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnProcessedProductRequiredInvoicingGroup
-- 2024-05-08T10:05:04.174Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545395,0,TO_TIMESTAMP('2024-05-08 13:05:03.954','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','SalesOnProcessedProduct kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!','E',TO_TIMESTAMP('2024-05-08 13:05:03.954','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.SalesOnProcessedProductRequiredInvoicingGroup')
;

-- 2024-05-08T10:05:04.189Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545395 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnProcessedProductRequiredInvoicingGroup
-- 2024-05-08T10:05:22.414Z
UPDATE AD_Message_Trl SET MsgText='SalesOnProcessedProduct cannot be set for an invoicing group other than: {}!',Updated=TO_TIMESTAMP('2024-05-08 13:05:22.414','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545395
;

-- Value: de.metas.contracts.modular.manufacturingOrderCompleteLogDescription
-- 2024-05-08T13:14:41.083Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545396,0,TO_TIMESTAMP('2024-05-08 16:14:40.886','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Produktionsauftrag für Produkt {0} mit der Menge {1} wurde fertiggestellt.','I',TO_TIMESTAMP('2024-05-08 16:14:40.886','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.manufacturingOrderCompleteLogDescription')
;

-- 2024-05-08T13:14:41.089Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545396 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.manufacturingOrderCompleteLogDescription
-- 2024-05-08T13:14:51.489Z
UPDATE AD_Message_Trl SET MsgText='Manufacturing Order for product {0} with the quantity {1} was completed.',Updated=TO_TIMESTAMP('2024-05-08 16:14:51.488','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545396
;
