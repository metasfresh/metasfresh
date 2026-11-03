-- Run mode: SWING_CLIENT

-- User-facing, localized error shown when a ZUGFeRD e-invoice fails EN16931 validation and the
-- invoice is therefore not completed (de.metas.einvoice.interceptor.C_Invoice). {0} = failed rule ids.

-- IDs allocated from idserver.metas.de on 2026-06-23:
--   AD_Message 545763 (EInvoice_ZUGFeRDInvalid)

-- Value: EInvoice_ZUGFeRDInvalid
-- 2026-06-23T00:00:00Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value,ErrorCode) VALUES (0,545763 /*From ID Server*/,0,TIMESTAMP '2026-06-23 00:00:00',100,'D','Y','Das ZUGFeRD (E-Rechnung) für diese Rechnung ist ungültig; die Rechnung wurde nicht fertiggestellt. Bitte korrigieren Sie die beanstandeten Daten (z. B. Lieferanten-USt-IdNr., Zahlungsangaben, Steuerkennzeichen) und stellen Sie die Rechnung erneut fertig. Nicht erfüllte Regeln: {0}','E',TIMESTAMP '2026-06-23 00:00:00',100,'EInvoice_ZUGFeRDInvalid','EINVOICE_ZUGFERD_INVALID')
;

-- 2026-06-23T00:00:00Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545763 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: EInvoice_ZUGFeRDInvalid
-- 2026-06-23T00:00:00Z
UPDATE AD_Message_Trl SET MsgText='The ZUGFeRD (e-invoice) for this invoice is invalid; the invoice was not completed. Please correct the reported data (e.g. seller VAT ID, payment details, tax category) and complete the invoice again. Failed rules: {0}',Updated=TIMESTAMP '2026-06-23 00:00:00',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545763
;

-- 2026-06-23T00:00:00Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
