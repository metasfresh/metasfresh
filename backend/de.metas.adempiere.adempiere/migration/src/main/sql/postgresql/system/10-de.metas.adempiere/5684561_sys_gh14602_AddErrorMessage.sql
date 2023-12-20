-- Value: InvoiceCand_Cannot_be_determined
-- 2023-04-07T12:49:27.722Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545259,0,TO_TIMESTAMP('2023-04-07 15:49:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y','Base Line date for the given payment term {0} cannot be determined!','E',TO_TIMESTAMP('2023-04-07 15:49:27','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceCand_Cannot_be_determined')
;

-- 2023-04-07T12:49:27.730Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545259 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: InvoiceCand_Cannot_be_determined
-- 2023-04-07T12:49:35.596Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-07 15:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545259
;

-- Value: InvoiceCand_Cannot_be_determined
-- 2023-04-07T12:49:56.283Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Basisdatum für die angegebene Zahlungsfrist {0} kann nicht ermittelt werden!',Updated=TO_TIMESTAMP('2023-04-07 15:49:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545259
;

-- Value: InvoiceCand_Cannot_be_determined
-- 2023-04-07T12:50:04.010Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Basisdatum für die angegebene Zahlungsfrist {0} kann nicht ermittelt werden!',Updated=TO_TIMESTAMP('2023-04-07 15:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545259
;

-- 2023-04-07T12:50:04.011Z
UPDATE AD_Message SET MsgText='Basisdatum für die angegebene Zahlungsfrist {0} kann nicht ermittelt werden!' WHERE AD_Message_ID=545259
;

