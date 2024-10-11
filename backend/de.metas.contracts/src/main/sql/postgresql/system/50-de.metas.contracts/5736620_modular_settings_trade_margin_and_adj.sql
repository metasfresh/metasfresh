-- Run mode: SWING_CLIENT

-- Reference: ModCntr_Type
-- Table: ModCntr_Type
-- Key: ModCntr_Type.ModCntr_Type_ID
-- 2024-10-10T08:59:21.279Z
UPDATE AD_Ref_Table SET WhereClause='ModCntr_Type_ID not in (540010, 540009, 540008, 540026)',Updated=TO_TIMESTAMP('2024-10-10 10:59:21.279','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541861
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:10:54.296Z
UPDATE AD_Message SET MsgText='Es kann nur ein Verkaufs Vertragsbaustein-Typ innerhalb der gleichen Modularen Einstellungen verwendet werden!',Updated=TO_TIMESTAMP('2024-10-10 11:10:54.295','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545393
;

-- 2024-10-10T09:10:54.299Z
UPDATE AD_Message_Trl trl SET MsgText='Es kann nur ein Verkaufs Vertragsbaustein-Typ innerhalb der gleichen Modularen Einstellungen verwendet werden!' WHERE AD_Message_ID=545393 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:18.837Z
UPDATE AD_Message_Trl SET MsgText='Es kann nur ein Verkaufs Vertragsbaustein-Typ innerhalb der gleichen Modularen Einstellungen verwendet werden!',Updated=TO_TIMESTAMP('2024-10-10 11:11:18.837','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:54.367Z
UPDATE AD_Message_Trl SET MsgText='Only one Sales Computing Method can be used within the same Modular Settings!',Updated=TO_TIMESTAMP('2024-10-10 11:11:54.367','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:55.293Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-10 11:11:55.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:56.019Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-10 11:11:56.019','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:56.962Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-10 11:11:56.962','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545393
;

