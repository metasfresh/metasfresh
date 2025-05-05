-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-04-23T07:08:45.586Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545393,0,TO_TIMESTAMP('2024-04-23 10:08:44.526','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','SalesOnRawProduct & SalesOnProcessedProduct können nicht beide innerhalb der gleichen Modularen Einstellungen verwendet werden!','E',TO_TIMESTAMP('2024-04-23 10:08:44.526','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError')
;

-- 2024-04-23T07:08:45.605Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545393 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-04-23T07:09:03.292Z
UPDATE AD_Message_Trl SET MsgText='SalesOnRawProduct & SalesOnProcessedProduct cannot be both used within the same Modular Settings!',Updated=TO_TIMESTAMP('2024-04-23 10:09:03.292','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup
-- 2024-04-23T07:11:47.929Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545394,0,TO_TIMESTAMP('2024-04-23 10:11:47.785','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','SalesOnRawProduct kann nicht für eine andere Fakturierungsgruppe als ''{1}'' eingestellt werden!','E',TO_TIMESTAMP('2024-04-23 10:11:47.785','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup')
;

-- 2024-04-23T07:11:47.931Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545394 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup
-- 2024-04-23T07:11:58.868Z
UPDATE AD_Message_Trl SET MsgText='SalesOnRawProduct cannot be set for an invoicing group other than: ''{1}''!',Updated=TO_TIMESTAMP('2024-04-23 10:11:58.868','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545394
;


-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup
-- 2024-04-23T08:05:33.951Z
UPDATE AD_Message_Trl SET MsgText='SalesOnRawProduct cannot be set for an invoicing group other than: {}!',Updated=TO_TIMESTAMP('2024-04-23 11:05:33.951','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545394
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup
-- 2024-04-23T08:06:13.850Z
UPDATE AD_Message SET MsgText='SalesOnRawProduct kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!',Updated=TO_TIMESTAMP('2024-04-23 11:06:13.849','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545394
;

-- 2024-04-23T08:06:13.861Z
UPDATE AD_Message_Trl trl SET MsgText='SalesOnRawProduct kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!' WHERE AD_Message_ID=545394 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup
-- 2024-04-23T08:07:00.692Z
UPDATE AD_Message_Trl SET MsgText='SalesOnRawProduct kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!',Updated=TO_TIMESTAMP('2024-04-23 11:07:00.692','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Message_ID=545394
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup
-- 2024-04-23T08:07:04.925Z
UPDATE AD_Message_Trl SET MsgText='SalesOnRawProduct kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!',Updated=TO_TIMESTAMP('2024-04-23 11:07:04.925','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545394
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductRequiredInvoicingGroup
-- 2024-04-23T08:07:09.662Z
UPDATE AD_Message_Trl SET MsgText='SalesOnRawProduct kann nicht für eine andere Fakturierungsgruppe als {} eingestellt werden!',Updated=TO_TIMESTAMP('2024-04-23 11:07:09.662','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545394
;