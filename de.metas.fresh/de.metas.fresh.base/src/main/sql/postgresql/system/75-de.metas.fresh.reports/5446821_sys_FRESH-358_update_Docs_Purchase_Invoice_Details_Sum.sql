DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details_Sum ( IN c_invoice_id numeric);


CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Purchase_Invoice_Details_Sum ( IN c_invoice_id numeric)
RETURNS TABLE 
(
	c_invoice_id numeric,
	iso_code text,
	taxbaseamt numeric,
	taxamt numeric,
	taxrate numeric,
	total numeric,
	huline character(1),
	isprinttax character(1),
	orderindex integer
)
AS
$$

SELECT
	*
FROM
(
	SELECT
		it.C_Invoice_ID,
		null as ISO_Code,
		SUM(TaxBaseAmt) AS TaxBaseAmt,
		null AS TaxAmt,
		CASE
			WHEN round(t.Rate,0) = t.Rate THEN floor(t.Rate)
			WHEN round(t.Rate,1) = t.Rate THEN round(t.Rate,1)
			WHEN round(t.Rate,2) = t.Rate THEN round(t.Rate,2)
		ELSE round(t.Rate,2)
		END AS TaxRate,
		null AS total,
		'N' AS HuLine,
		bpg.IsPrintTax,
		1 as orderindex
	FROM
		C_InvoiceTax it
		INNER JOIN C_Tax t ON it.C_Tax_ID = t.C_Tax_ID
		INNER JOIN C_Invoice i ON it.C_Invoice_ID = i.C_Invoice_ID
		INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
		INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
	WHERE
		it.IsPackagingTax = 'N'
	GROUP BY
		it.C_Invoice_ID,
		bpg.IsPrintTax,
		CASE
			WHEN round(t.Rate,0) = t.Rate THEN floor(t.Rate)
			WHEN round(t.Rate,1) = t.Rate THEN round(t.Rate,1)
			WHEN round(t.Rate,2) = t.Rate THEN round(t.Rate,2)
		ELSE round(t.Rate,2)
		END
UNION

	SELECT	it.C_Invoice_ID,
		null,
		null,
		SUM(TaxAmt),
		CASE
			WHEN round(t.Rate,0) = t.Rate THEN floor(t.Rate)
			WHEN round(t.Rate,1) = t.Rate THEN round(t.Rate,1)
			WHEN round(t.Rate,2) = t.Rate THEN round(t.Rate,2)
		ELSE round(t.Rate,2)
		END AS TaxRate,
		SUM(TaxBaseAmt + TaxAmt) AS total,
		it.IsPackagingTax AS HuLine,
		bpg.IsPrintTax,
		2 AS orderindex
	FROM
		C_InvoiceTax it
		INNER JOIN C_Tax t ON it.C_Tax_ID = t.C_Tax_ID
		INNER JOIN C_Invoice i ON it.C_Invoice_ID = i.C_Invoice_ID
		INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
		INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
	GROUP BY
		it.C_Invoice_ID, it.IsPackagingTax,
		bpg.IsPrintTax,
		CASE
			WHEN round(t.Rate,0) = t.Rate THEN floor(t.Rate)
			WHEN round(t.Rate,1) = t.Rate THEN round(t.Rate,1)
			WHEN round(t.Rate,2) = t.Rate THEN round(t.Rate,2)
		ELSE round(t.Rate,2)
		END
UNION
	SELECT	i.C_Invoice_ID,
		c.Iso_Code,
		null,
		null,
		null,
		Grandtotal AS total,
		null,
		bpg.IsPrintTax,
		3 AS orderindex
	FROM
		C_Invoice i
		INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID
		INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID
		INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
) a
WHERE
	C_Invoice_ID = $1
ORDER BY
	orderindex, taxrate desc
$$

LANGUAGE sql STABLE;