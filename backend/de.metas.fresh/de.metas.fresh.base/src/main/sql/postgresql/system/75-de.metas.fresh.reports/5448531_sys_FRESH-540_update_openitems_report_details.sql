DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report_Details(IN AD_Org_ID numeric, IN C_BPartner_ID numeric, IN IsSOTrx character varying, IN DaysDue numeric, IN InvoiceCollectionType character varying, IN StartDate date, In EndDate date, IN Reference_Date date, IN PassForPayment character varying, IN switchDate character varying);

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.OpenItems_Report_Details(IN AD_Org_ID numeric, IN C_BPartner_ID numeric, IN IsSOTrx character varying, IN DaysDue numeric, IN InvoiceCollectionType character varying, IN StartDate date, In EndDate date, IN Reference_Date date, IN PassForPayment character varying, IN switchDate character varying)
	RETURNS TABLE ( 
		iso_code character(3), 
		DocumentNo character varying (30), 
		DateInvoiced date, 
		DateAcct date,
		NetDays numeric,
		DiscountDays numeric(10,0),
		DueDate date,
		DaysDue integer,
		DiscountDate date,
		GrandTotal numeric,
		PaidAmt numeric,
		OpenAmt numeric,
		Value character varying (40),
		Name character varying (60),
		PassForPayment boolean,
		GrandTotalConvert numeric,
		OpenAmtConvert numeric,
		main_currency character(3)
	) AS 
$$

SELECT
	oi.CurrencyCode as iso_code,
	oi.DocumentNo,
	oi.DateInvoiced::date,
	oi.DateAcct::date,
	oi.NetDays,
	oi.DiscountDays,
	oi.DueDate::date,
	oi.DaysDue,
 	oi.DiscountDate::date,
	oi.GrandTotal,
	oi.PaidAmt,
	oi.OpenAmt,
	oi.BPValue as Value,
	oi.BPName as Name,
	oi.IsInPaySelection as PassForPayment,
	(CASE WHEN (cc.C_Currency_ID != c.C_Currency_ID) THEN currencyConvert(oi.GrandTotal
			, cc.C_Currency_ID -- p_curfrom_id
			, c.C_Currency_ID -- p_curto_id
			, $8 -- p_convdate (Reference_Date)
			, (SELECT C_ConversionType_ID FROM C_ConversionType where Value='P') -- p_conversiontype_id
			, oi.AD_Client_ID
			, oi.ad_org_id
			)
		ELSE NULL
	END)	AS GrandTotalConvert,
	(CASE WHEN (cc.C_Currency_ID != c.C_Currency_ID) THEN currencyConvert(oi.OpenAmt
			, cc.C_Currency_ID -- p_curfrom_id
			, c.C_Currency_ID -- p_curto_id
			, $8 -- p_convdate (Reference_Date)
			, (SELECT C_ConversionType_ID FROM C_ConversionType where Value='P') -- p_conversiontype_id
			, oi.AD_Client_ID
			, oi.ad_org_id
			)
		ELSE NULL
	END)	AS OpenAmtConvert,
	(CASE WHEN (cc.C_Currency_ID != c.C_Currency_ID) THEN c.iso_code
	ELSE NULL 
	END) AS main_currency
	
FROM de_metas_endcustomer_fresh_reports.OpenItems_Report($8, $10) oi

LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID=oi.ad_client_id 
LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID=ci.C_AcctSchema1_ID
LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID=c.C_Currency_ID
INNER JOIN C_Currency cc ON cc.iso_code=oi.CurrencyCode

WHERE
	oi.AD_Org_ID = (CASE WHEN $1 IS NULL THEN oi.AD_Org_ID ELSE $1 END)
	AND oi.IsSOTrx = (CASE WHEN $3 IS NULL THEN oi.IsSOTrx ELSE $3 END)
	AND oi.C_BPartner_ID = (CASE WHEN $2 IS NULL THEN oi.C_BPartner_ID ELSE $2 END)
	AND ($4 IS NULL OR $4 <= 0 OR oi.daysdue >= $4)
	AND COALESCE( oi.InvoiceCollectionType, '') =
		(CASE WHEN COALESCE($5, '') = '' THEN COALESCE( oi.InvoiceCollectionType, '' ) ELSE $5 END)
	AND (
	--09738 if flag is set then switches the given date to dateacct instead of dateinvoiced
	CASE WHEN $10 = 'Y' THEN
		(oi.DateAcct >= ( CASE WHEN $6::date IS NULL THEN oi.DateAcct ELSE $6::date END )
		AND oi.DateAcct <= ( CASE WHEN $7::date IS NULL THEN oi.DateAcct ELSE $7::date END ))
	ELSE
		(oi.DateInvoiced >= ( CASE WHEN $6::date IS NULL THEN oi.DateInvoiced ELSE $6::date END )
		AND oi.DateInvoiced <= ( CASE WHEN $7::date IS NULL THEN oi.DateInvoiced ELSE $7::date END ))
	END
	)
	-- 08394: If Flag is not set, only display invoices that are not part of a processed PaySelection
	AND ( $9 = 'Y' OR NOT oi.IsInPaySelection )
$$ 
LANGUAGE sql STABLE;
/*ORDER BY
	oi.CurrencyCode,
	oi.BPName,
	oi.BPValue,
	oi.DateInvoiced,
	oi.DocumentNo
;*/
