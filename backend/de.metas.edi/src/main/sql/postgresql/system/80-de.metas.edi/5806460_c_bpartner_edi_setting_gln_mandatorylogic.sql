-- MandatoryLogic on the C_BPartner_EDI_Setting GLN columns:
-- the recipient GLN is required when the matching IsEdi*Recipient flag is set.
-- (Declarative AD-level enforcement complementing the C_BPartner_EDI_Setting interceptor.)
-- C_BPartner_EDI_Setting AD_Table_ID = 542610.

UPDATE AD_Column SET MandatoryLogic='@IsEdiDesadvRecipient@=''Y''', Updated=now(), UpdatedBy=100
WHERE AD_Table_ID=542610 AND ColumnName='EdiDesadvRecipientGLN'
;

UPDATE AD_Column SET MandatoryLogic='@IsEdiInvoicRecipient@=''Y''', Updated=now(), UpdatedBy=100
WHERE AD_Table_ID=542610 AND ColumnName='EdiInvoicRecipientGLN'
;
