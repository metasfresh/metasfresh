CREATE INDEX cs_transaction_result_partner_payment_time
  ON public.cs_transaction_result
  USING btree
  (C_BPartner_ID, PaymentRule, RequestStartTime DESC)
;

UPDATE AD_Message SET MsgText='Das CreditPass Ergebnis wurde von "manuell" auf die metasfresh-Voreinstellung gesetzt. Bitte überprüfe das Ergebnis.',Updated=TO_TIMESTAMP('2019-03-25 19:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544884
;

UPDATE AD_Process SET Name='CreditPass Überprüfung',Updated=TO_TIMESTAMP('2019-03-26 22:07:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541081
;


