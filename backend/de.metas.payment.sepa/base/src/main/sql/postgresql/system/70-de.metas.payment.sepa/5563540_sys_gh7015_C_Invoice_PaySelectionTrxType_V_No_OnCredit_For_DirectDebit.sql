
DROP VIEW IF EXISTS C_Invoice_PaySelectionTrxType_V;
CREATE OR REPLACE VIEW C_Invoice_PaySelectionTrxType_V AS
SELECT C_Invoice_ID,
	CASE 
		WHEN /* CREDIT_TRANSFER_TO_VENDOR - "OUT" */ i.IsSOTrx='N' AND i.PaymentRule IN ('T'/*DirectDeposit*/, 'P'/*OnCredit*/) AND i.DocStatus IN ('CO', 'CL') THEN 'CT'
		WHEN /* DIRECT_DEBIT_FROM_CUSTOMER - "CDD" */ i.IsSOTrx='Y' AND dt.DocBaseType != 'ARC' AND i.PaymentRule = 'D'/*DirectDebit*/ AND i.DocStatus IN ('CO', 'CL') THEN 'DD'
		WHEN /* CREDIT_TRANSFER_TO_CUSTOMER - "CRE" */ i.IsSOTrx='Y' AND dt.DocBaseType = 'ARC' AND i.PaymentRule IN ('T'/*DirectDeposit*/, 'P'/*OnCredit*/) AND i.DocStatus IN ('CO', 'CL') THEN 'CT'
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
