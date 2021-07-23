DROP VIEW IF EXISTS report.RV_TaxDeclarationLine;
CREATE OR REPLACE VIEW report.RV_TaxDeclarationLine AS
SELECT
	td.C_TaxDeclaration_ID,
	tdl.Line,
	tdl.DateAcct,
	t.name as C_Tax_Name,
	cur.cursymbol as CurSymbol,
	tdl.DocumentNo,
	dt.name as C_DocType_Name,
	ev.Value as Acct_Value,
	ev.Name as Acct_Name,
	--
	-- VST - Tax payed to vendors
	-- UST - Tax collected from customers
	COALESCE(fa.AmtAcctDr,0) as VST,
	COALESCE(fa.AmtAcctCr,0) as UST,
	CASE WHEN fa.AmtAcctDr <> 0 THEN tdl.TaxBaseAmt ELSE 0 END AS VST_Basis,
	CASE WHEN fa.AmtAcctCr <> 0 THEN tdl.TaxBaseAmt ELSE 0 END AS UST_Basis
FROM
	C_TaxDeclaration td
	INNER JOIN c_taxdeclarationline tdl	ON (td.c_TaxDeclaration_ID = tdl.C_TaxDeclaration_ID) AND tdl.isActive = 'Y'
	INNER JOIN c_tax t ON (tdl.C_Tax_ID = t.C_Tax_ID) AND t.isActive = 'Y'
	INNER JOIN c_currency cur ON (tdl.c_currency_id = cur.c_currency_id) AND cur.isActive = 'Y'
	LEFT OUTER JOIN C_DocType dt ON (dt.C_DocType_ID=tdl.C_DocType_ID) AND dt.isActive = 'Y'
	--
	LEFT OUTER JOIN C_TaxDeclarationAcct tda ON (
		tda.C_TaxDeclaration_ID=tdl.C_TaxDeclaration_ID
		AND tda.C_TaxDeclarationLine_ID=tdl.C_TaxDeclarationLine_ID
	) AND tda.isActive = 'Y'
	LEFT OUTER JOIN Fact_Acct fa ON (fa.Fact_Acct_ID=tda.Fact_Acct_ID) AND fa.isActive = 'Y'
	LEFT OUTER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID=fa.Account_ID) AND ev.isActive = 'Y'
	WHERE td.isActive = 'Y'
;


