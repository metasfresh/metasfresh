-- Sets AD_Message.ErrorCode on the error message "invalid reference" (AD_Message 545142), which the pain.001.001.09.ch.03 export raises.
-- Like the other new error messages of the ISO 20022 2019 port (script 5829860), it carries a stable short code (varchar(40)); the full key stays in AD_Message.Value.

UPDATE AD_Message SET ErrorCode='SEPA_INVALID_REFERENCE', Updated=TO_TIMESTAMP('2026-10-22 01:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE Value='de.metas.payment.sepa.SEPA_Export_InvalidReference' AND MsgType='E'
;
