-- Value: de.metas.shipper.gateway.dhl.MissingBillingNumber
-- DHL_Shipper_Config has no billing number for the DHL product of a parcel; {0}=DHL_Shipper_Config_ID, {1}=shipper name, {2}=DHL product code, {3}=field label
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545992,0,TO_TIMESTAMP('2026-11-07 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die DHL-Konfiguration (DHL_Shipper_Config_ID={0}) des Spediteurs "{1}" enthält keine Abrechnungsnummer für das DHL-Produkt {2}. Bitte das Feld "{3}" ausfüllen.','E',TO_TIMESTAMP('2026-11-07 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.shipper.gateway.dhl.MissingBillingNumber')
;

UPDATE AD_Message SET ErrorCode='MISSING_BILLING_NUMBER', Updated=TO_TIMESTAMP('2026-11-07 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545992
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545992
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='The DHL configuration (DHL_Shipper_Config_ID={0}) of shipper "{1}" has no billing number for the DHL product {2}. Please fill the field "{3}".',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-07 09:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545992
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-07 09:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language IN ('de_DE','de_CH') AND AD_Message_ID=545992
;
