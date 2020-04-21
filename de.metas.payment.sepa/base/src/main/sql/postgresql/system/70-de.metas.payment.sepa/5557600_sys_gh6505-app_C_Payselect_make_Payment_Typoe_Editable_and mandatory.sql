-- 2020-04-21T11:11:43.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-04-21 13:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550908
;

-- 2020-04-21T11:11:45.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorIdentifier','VARCHAR(35)',null,null)
;

-- 2020-04-21T11:11:45.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('sepa_export','SEPA_CreditorIdentifier',null,'NULL',null)
;

-- 2020-04-21T11:11:51.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='Setting not mandatory, because users might only want to do credit transfers (not direct debits). Those may not even have a CreditorIdentifier.
So we need to check this in the code.',Updated=TO_TIMESTAMP('2020-04-21 13:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550908
;

UPDATE C_PaySelection SET PaySelectionTrxType='CT' WHERE PaySelectionTrxType IS NULL;


DROP VIEW IF EXISTS C_Invoice_PaySelectionTrxType_V;
CREATE OR REPLACE VIEW C_Invoice_PaySelectionTrxType_V AS
SELECT C_Invoice_ID,
	CASE 
		WHEN /* CREDIT_TRANSFER_TO_VENDOR - "OUT" */ i.IsSOTrx='N' AND i.PaymentRule IN ('T'/*DirectDeposit*/, 'P'/*OnCredit*/) AND i.DocStatus IN ('CO', 'CL') THEN 'CT'
		WHEN /* DIRECT_DEBIT_FROM_CUSTOMER - "CDD" */ i.IsSOTrx='Y' AND dt.DocBaseType = 'ARC' AND i.PaymentRule IN ('D'/*DirectDebit*/) AND i.DocStatus IN ('CO', 'CL') THEN 'DD'
		WHEN /* CREDIT_TRANSFER_TO_CUSTOMER - "CRE" */ i.IsSOTrx='Y' AND dt.DocBaseType = 'ARC' AND i.PaymentRule IN ('T'/*DirectDeposit*/) AND i.DocStatus IN ('CO', 'CL') THEN 'CT'
		ELSE /* not compatible to any type */ 'XX'
	END AS PaySelectionTrxType,
	i.IsSOTrx,
	i.PaymentRule,
	i.DocStatus,
	i.DocumentNo,
	dt.DocBaseType
FROM C_Invoice i
	JOIN C_DocType dt ON dt.C_DocType_ID=i.C_DocType_ID;
COMMENT ON VIEW C_Invoice_PaySelectionTrxType_V IS 
'Selects C_Invoice_IDs together with their respective PaySelectionTrxTypes. XX means "none". 
The other columns are just for diagnostics/troubleshooting.
Please keep in sync with the code at de.metas.banking.payment.impl.PaySelectionUpdater.buildSelectSQL_MatchRequirement()';


-- 2020-04-21T11:55:26.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-04-21 13:55:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570073
;

-- 2020-04-21T11:55:28.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_payselection','PaySelectionTrxType','VARCHAR(2)',null,null)
;

-- 2020-04-21T11:55:28.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_payselection','PaySelectionTrxType',null,'NOT NULL',null)
;

-- 2020-04-21T11:56:04.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Zahlungsauswahl-Position', PersonalDataCategory='P',Updated=TO_TIMESTAMP('2020-04-21 13:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=427
;

-- 2020-04-21T12:06:04.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540498,'@PaySelectionTrxType/''X''@ = ( select PaySelectionTrxType from C_Invoice_PaySelectionTrxType_V v where v.C_Invoice_ID=C_Invoice.C_Invoice_ID )',TO_TIMESTAMP('2020-04-21 14:06:04','YYYY-MM-DD HH24:MI:SS'),100,'Allows invoices that are compatible with the current pay selection type','de.metas.payment.sepa','Y','Invoice matches PaySelectionTrxType','S',TO_TIMESTAMP('2020-04-21 14:06:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-21T12:35:27.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Description='Allows invoices that are compatible with the current pay selection type; includes that invoice needs to be CO or CL', Name='C_Invoice matches PaySelectionTrxType',Updated=TO_TIMESTAMP('2020-04-21 14:35:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540498
;

-- 2020-04-21T12:35:38.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule_Included SET Included_Val_Rule_ID=540498,Updated=TO_TIMESTAMP('2020-04-21 14:35:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_Included_ID=540025
;

-- 2020-04-21T12:40:15.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2020-04-21 14:40:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598435
;

-- 2020-04-21T12:44:33.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='''@PaySelectionTrxType/''X''@'' = ( select PaySelectionTrxType from C_Invoice_PaySelectionTrxType_V v where v.C_Invoice_ID=C_Invoice.C_Invoice_ID )',Updated=TO_TIMESTAMP('2020-04-21 14:44:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540498
;

-- 2020-04-21T13:27:29.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule_Included SET SeqNo=5,Updated=TO_TIMESTAMP('2020-04-21 15:27:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_Included_ID=540025
;

