DROP VIEW IF EXISTS report.fresh_TaxDeclaration_Overview;
CREATE OR REPLACE VIEW report.fresh_TaxDeclaration_Overview AS
SELECT
	COALESCE(SUM(CASE WHEN t.rate IN (8, 2.5) AND tdl.IsSOTrx = 'Y' THEN tdl.TaxBaseAmt + tdl.TaxAmt ELSE NULL END), 0) AS pos200,
	COALESCE(SUM(CASE WHEN t.name ILIKE '0% EU%' AND tdl.IsSOTrx = 'Y' THEN tdl.TaxBaseAmt ELSE NULL END), 0) AS pos220,
	COALESCE(SUM(CASE WHEN t.rate = 8 AND tdl.IsSOTrx = 'Y' THEN tdl.TaxBaseAmt ELSE NULL END), 0) AS pos301base,
	COALESCE(SUM(CASE WHEN t.rate = 8 AND tdl.IsSOTrx = 'Y' THEN tdl.TaxAmt ELSE NULL END), 0) AS pos301tax,
	COALESCE(SUM(CASE WHEN t.rate = 2.5 AND tdl.IsSOTrx = 'Y' THEN tdl.TaxBaseAmt ELSE NULL END), 0) AS pos311base,
	COALESCE(SUM(CASE WHEN t.rate = 2.5 AND tdl.IsSOTrx = 'Y' THEN tdl.TaxAmt ELSE NULL END), 0) AS pos311tax,
	--381 - postponed,
	COALESCE(SUM(CASE WHEN tdl.IsSOTrx = 'N' AND ( t.name ILIKE '%Waren/ DL%' OR bp.fresh_urproduzent = 'Y' ) THEN tdl.TaxAmt ELSE NULL END), 0) AS pos400,
	COALESCE(SUM(CASE WHEN tdl.IsSOTrx = 'N' AND ( t.name ILIKE '%Beherbergung%' OR t.name Like '%Betriebsaufwand%' ) THEN tdl.TaxAmt ELSE NULL END), 0) AS pos405,
	COALESCE(SUM(CASE WHEN tdl.IsSOTrx = 'Y' AND t.name ILIKE '%Gebinde%' THEN tdl.TaxBaseAmt ELSE NULL END), 0) AS pos910,
	--
	td.name,
	td.Datefrom,
	td.Dateto,
	td.C_TaxDeclaration_ID
FROM
	C_TaxDeclaration td
	LEFT OUTER JOIN C_TaxDeclarationline tdl ON td.C_TaxDeclaration_ID = tdl.C_TaxDeclaration_ID AND tdl.isActive = 'Y'
	LEFT OUTER JOIN C_Tax t ON tdl.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp ON bp.C_BPartner_ID = tdl.C_BPartner_ID AND bp.isActive = 'Y'
WHERE td.isActive = 'Y'

GROUP BY
	td.C_TaxDeclaration_ID,
	td.name,
	td.Datefrom,
	td.Dateto
;

