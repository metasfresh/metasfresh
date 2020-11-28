-- script added to make sure this function exists in the database


DROP FUNCTION IF EXISTS report.Fresh_Fact_Acct_Fibo_Report ( IN DateFrom date, IN DateTo date, IN AccountID numeric);
CREATE FUNCTION report.Fresh_Fact_Acct_Fibo_Report ( IN DateFrom date, IN DateTo date, IN AccountID numeric) 
	RETURNS TABLE ( 
		DocumentNumber text, 
		BPartnerName text, 
		BPartnerValue text, 
		ReferenceNumber text,
		dateAccount timestamp,
		LineNo text, 
		Amount numeric,
		AccountName text
	)AS 
$$
	SELECT 
	COALESCE(i.DocumentNo, io.DocumentNo, p.DocumentNo, cc.DocumentNo, inv.DocumentNo, gl.DocumentNo, minv.DocumentNo, mov.DocumentNo, hdr.DocumentNo, bstm.DocumentNo) AS DocumentNumber,
	bp.Name AS BPartnerName,
	bp.Value AS BPartnerValue, 
	COALESCE(i.POReference, io.POReference, mov.POReference) AS ReferenceNumber, 
	fa.dateAcct AS dateAccount, 
	
        CASE WHEN(fa.description like '%#%')	
					THEN 
						Substring ( fa.description, instr(fa.description, '#',1)+1, instr(fa.description||' ', ' ',1,2)-instr(fa.description, ' ',1)-2)
						ELSE '' 
					END  
					AS LineNo,
	SUM( (fa.AmtAcctCr - fa.AmtAcctDr) * acctBalance(fa.account_id, 0, 1 )) AS Amount,
	ev.name AS AccountName

FROM Fact_Acct fa

LEFT OUTER JOIN C_BPartner bp ON bp.C_BPartner_ID = fa.C_BPartner_ID
LEFT OUTER JOIN C_Invoice i ON fa.ad_table_id = 318 AND fa.record_id = i.C_Invoice_ID
LEFT OUTER JOIN M_InOut io ON fa.ad_table_id = 319 AND fa.record_id =  io.M_Inout_ID
LEFT OUTER JOIN C_Payment p ON fa.ad_table_id = 335 AND fa.record_id = p.C_Payment_ID
LEFT OUTER JOIN PP_Cost_Collector cc ON fa.ad_table_id = 53035 AND fa.record_id = cc.PP_Cost_Collector_ID
LEFT OUTER JOIN M_Inventory inv ON fa.ad_table_id = 321 AND fa.record_id = inv.M_Inventory_ID
LEFT OUTER JOIN GL_Journal gl ON fa.ad_table_id = 224 AND fa.record_id = gl.GL_Journal_ID
LEFT OUTER JOIN M_MatchInv minv ON fa.ad_table_id = 472 AND fa.record_id = minv.M_MatchInv_ID
LEFT OUTER JOIN M_Movement mov ON fa.ad_table_id = 323 AND fa.record_id = mov.M_Movement_ID
LEFT OUTER JOIN C_AllocationHdr hdr ON fa.ad_table_id =735 AND fa.record_id = hdr.C_AllocationHdr_ID
LEFT OUTER JOIN C_BankStatement bstm ON fa.ad_table_id =392 AND fa.record_id = bstm.C_BankStatement_ID

INNER JOIN C_ElementValue ev ON ev.C_ElementValue_ID = fa.Account_ID

WHERE fa.dateAcct >= $1 
	AND fa.dateAcct <= $2 
	AND fa.account_id = $3

GROUP BY LineNo,dateAccount,ReferenceNumber,BPartnerValue,BPartnerName,DocumentNumber, fa.description, ev.name

ORDER BY dateAccount, DocumentNumber, LineNo;
$$ 
LANGUAGE sql STABLE;
