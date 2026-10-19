-- pain.001.001.09.ch.03 export: ErrorCode for the error messages (MsgType='E') added by 5829540; the code is the message key without the 'de.metas.payment.sepa.' prefix.

UPDATE AD_Message SET ErrorCode='SEPA_Export_NoLines', Updated=TO_TIMESTAMP('2026-10-19 09:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545937 AND Value='de.metas.payment.sepa.SEPA_Export_NoLines'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_DebtorNameMissing', Updated=TO_TIMESTAMP('2026-10-19 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545938 AND Value='de.metas.payment.sepa.SEPA_Export_DebtorNameMissing'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_DebtorBankUnidentified', Updated=TO_TIMESTAMP('2026-10-19 09:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545939 AND Value='de.metas.payment.sepa.SEPA_Export_DebtorBankUnidentified'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_InvalidAmount', Updated=TO_TIMESTAMP('2026-10-19 09:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545941 AND Value='de.metas.payment.sepa.SEPA_Export_InvalidAmount'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_AmountTooManyDecimals', Updated=TO_TIMESTAMP('2026-10-19 09:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545942 AND Value='de.metas.payment.sepa.SEPA_Export_AmountTooManyDecimals'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_CreditorBankUnidentified', Updated=TO_TIMESTAMP('2026-10-19 09:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545943 AND Value='de.metas.payment.sepa.SEPA_Export_CreditorBankUnidentified'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_CreditorNameMissing', Updated=TO_TIMESTAMP('2026-10-19 09:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545944 AND Value='de.metas.payment.sepa.SEPA_Export_CreditorNameMissing'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_QrIbanCurrencyNotSupported', Updated=TO_TIMESTAMP('2026-10-19 09:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545945 AND Value='de.metas.payment.sepa.SEPA_Export_QrIbanCurrencyNotSupported'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_IsrNotSupported', Updated=TO_TIMESTAMP('2026-10-19 09:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545946 AND Value='de.metas.payment.sepa.SEPA_Export_IsrNotSupported'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_BankAccountCountryMissing', Updated=TO_TIMESTAMP('2026-10-19 09:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545950 AND Value='de.metas.payment.sepa.SEPA_Export_BankAccountCountryMissing'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_BankAccountCountryInvalid', Updated=TO_TIMESTAMP('2026-10-19 09:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545951 AND Value='de.metas.payment.sepa.SEPA_Export_BankAccountCountryInvalid'
;

UPDATE AD_Message SET ErrorCode='SEPA_Export_IbanTooShort', Updated=TO_TIMESTAMP('2026-10-19 09:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545952 AND Value='de.metas.payment.sepa.SEPA_Export_IbanTooShort'
;
