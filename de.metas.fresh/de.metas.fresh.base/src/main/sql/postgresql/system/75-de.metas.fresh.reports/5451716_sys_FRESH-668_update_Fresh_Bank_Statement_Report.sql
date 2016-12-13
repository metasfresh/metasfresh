DROP FUNCTION IF EXISTS report.Fresh_Bank_Statement_Report ( IN DateFrom date, IN DateTo date);
CREATE FUNCTION report.Fresh_Bank_Statement_Report ( IN DateFrom date, IN DateTo date) 
	RETURNS TABLE ( 
		BankstatementNo text, 
		BPartnerName text, 
		BPartnerValue text, 
		Referenznummer text,
		Valutadate timestamp,
		LineNo numeric, 
		StatementAmount numeric,
		SummaryBooking character
	) AS 
$$
	SELECT 
	bst.DocumentNO AS BankstatementNo, 
	bp.Name AS BPartnerName,
	bp.Value AS BPartnerValue, 
	bstl.ReferenceNo AS Referenznummer, 
	bstl.Valutadate AS Valutadate, 
	bstl.Line AS LineNo, 
	bstl.StmtAmt AS StatementAmount,
	bstl.isMultiplePaymentOrInvoice AS SummaryBooking

FROM C_BankStatementLine bstl

INNER JOIN C_BankStatement bst 
	ON bst.C_BankStatement_ID =  bstl.C_BankStatement_ID AND bst.isActive = 'Y'
INNER JOIN C_BP_BankAccount bpbacc 
	ON bpbacc.C_BP_BankAccount_ID = bst.C_BP_BankAccount_ID AND bpbacc.isActive = 'Y'
INNER JOIN C_BPartner bp 
	ON bp.C_BPartner_ID = bpbacc.C_BPartner_ID AND bp.isActive = 'Y'

WHERE bst.StatementDate >=$1 AND bst.StatementDate <=$2 AND bstl.isActive = 'Y'

ORDER BY BankstatementNo, LineNo
$$ 
LANGUAGE sql STABLE;
