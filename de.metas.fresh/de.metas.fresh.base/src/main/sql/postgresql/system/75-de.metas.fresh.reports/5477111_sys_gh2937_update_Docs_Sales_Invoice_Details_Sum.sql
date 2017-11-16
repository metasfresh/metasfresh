DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_Sum ( IN c_invoice_id numeric);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_Details_Sum ( IN c_invoice_id numeric)
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
	cursymbol character varying(10),
	vataxid character varying(60),
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
		bpg.IsPrintTaxSales,
		c.cursymbol,
		null as vataxid,
		1 as orderindex
	FROM
		C_InvoiceTax it
		INNER JOIN C_Tax t ON it.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
		INNER JOIN C_Invoice i ON it.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
		INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
		INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
	WHERE
		it.IsPackagingTax = 'N' AND it.isActive = 'Y'
	GROUP BY
		it.C_Invoice_ID,
		bpg.IsPrintTaxSales,
		c.cursymbol,
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
		bpg.IsPrintTaxSales,
		c.cursymbol,
		null as vataxid,
		2 AS orderindex
	FROM
		C_InvoiceTax it
		INNER JOIN C_Tax t ON it.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
		INNER JOIN C_Invoice i ON it.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
		INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
		INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
	WHERE it.isActive = 'Y'
	GROUP BY
		it.C_Invoice_ID, it.IsPackagingTax,
		bpg.IsPrintTaxSales,
		c.cursymbol,
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
		bpg.IsPrintTaxSales,
		c.cursymbol,
		null as vataxid,
		3 AS orderindex
	FROM
		C_Invoice i
		INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
		INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
	WHERE i.isActive = 'Y'

UNION
	SELECT
		i.C_Invoice_ID,
		c.Iso_Code,
		SUM(TaxBaseAmt) AS TaxBaseAmt,
		SUM(TaxAmt) AS TaxAmt,
		CASE
			WHEN round(t.Rate,0) = t.Rate THEN floor(t.Rate)
			WHEN round(t.Rate,1) = t.Rate THEN round(t.Rate,1)
			WHEN round(t.Rate,2) = t.Rate THEN round(t.Rate,2)
		ELSE round(t.Rate,2)
		END AS TaxRate,
		Grandtotal AS total,
		null AS HuLine,
		bpg.IsPrintTaxSales,
		c.cursymbol,
		COALESCE(bp.vataxid, '') as vataxid,
		4 as orderindex
	FROM
		C_InvoiceTax it
		INNER JOIN C_Tax t ON it.C_Tax_ID = t.C_Tax_ID AND t.isActive = 'Y'
		INNER JOIN C_Invoice i ON it.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
		INNER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
		INNER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID AND bpg.isActive = 'Y'
		INNER JOIN C_Currency c ON i.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
	WHERE
		it.isActive = 'Y'
	GROUP BY
		i.C_Invoice_ID,
		c.Iso_Code,
		bpg.IsPrintTaxSales,
		c.cursymbol,
		Grandtotal,
		COALESCE(bp.vataxid, ''),
		CASE
			WHEN round(t.Rate,0) = t.Rate THEN floor(t.Rate)
			WHEN round(t.Rate,1) = t.Rate THEN round(t.Rate,1)
			WHEN round(t.Rate,2) = t.Rate THEN round(t.Rate,2)
		ELSE round(t.Rate,2)
		END	
		
) a
WHERE
	C_Invoice_ID = $1
ORDER BY
	orderindex, taxrate desc
$$

LANGUAGE sql STABLE;