DROP FUNCTION IF EXISTS report.fresh_payselection_report (IN record_id numeric, IN c_bpartner_id numeric, isallowesrpayments character varying, printSinglePayment character varying);

DROP TABLE IF EXISTS report.fresh_payselection_report;

CREATE TABLE report.fresh_payselection_report
(
	c_bpartner_id numeric (10,0),
	ad_org_id numeric (10,0),
	issotrx text,
	c_bpartner_location_id numeric (10,0),
	isdraft boolean,
	linecount bigint
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.fresh_payselection_report(IN record_id numeric, IN c_bpartner_id numeric, IN isallowesrpayments character varying, printSinglePayment character varying ) RETURNS SETOF report.fresh_payselection_report AS
$BODY$SELECT
	i.C_BPartner_ID,
	ps.AD_Org_ID,
	CASE
		WHEN bp.IsCustomer = 'Y' THEN 'Y'
		WHEN bp.IsVendor = 'Y' THEN 'N'
		ELSE null
	END AS IsSOTrx,
	bpl.C_BPartner_Location_ID,
	ps.processed != 'Y' AS isDraft,
	count( psl.C_PaySelectionLine_ID ) AS LineCount

FROM
	-- Payselection records
	C_PaySelection ps
	LEFT OUTER JOIN C_PaySelectionLine psl ON ps.C_PaySelection_ID = psl.C_PaySelection_ID AND psl.IsActive = 'Y'
	-- Get BPartner
	-- I'm using C_Invoice because I want the report to executable before the lines were processed
	LEFT OUTER JOIN C_Invoice i ON psl.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
	LEFT OUTER JOIN C_BPartner bp ON i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
	-- Get BPartner Location
	LEFT OUTER JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID =
	(
		SELECT First_Agg (C_BPartner_Location_ID::Text ORDER BY IsBillToDefault DESC, IsBillTo DESC)
		FROM C_BPartner_Location sub_bpl WHERE sub_bpl.C_BPartner_ID = i.C_BPartner_ID AND sub_bpl.isActive = 'Y'
	)::Numeric AND bpl.isActive = 'Y'
	LEFT OUTER JOIN C_BP_BankAccount ba ON ba.C_BP_BankAccount_ID=psl.C_BP_BankAccount_ID AND ba.isActive = 'Y'
	
WHERE
	ps.C_PaySelection_ID = $1 AND ps.isActive = 'Y'
	-- only filter for BPartner if the parameter was set
	AND i.C_BPartner_ID = CASE WHEN $2 IS NULL THEN i.C_BPartner_ID ELSE $2 END
	-- We don't want to display ESR payments (if they are not allowed). See Task 08383
	AND (
 		( $3 = 'N' AND ba.IsESRAccount='N' )
		OR $3 = 'Y'
	)

GROUP BY
	i.C_BPartner_ID, ps.AD_Org_ID,
	bp.IsCustomer, bp.IsVendor,
	bpl.C_BPartner_Location_ID,
	ps.processed
HAVING
	-- The following filter shall not be applied if printSinglePayment is set
	-- We only want to display documents if there is more than one payment. see Task 08383
	($4 = 'Y' OR ($4 = 'N' AND count( psl.C_PaySelectionLine_ID ) > 1))
ORDER BY
	i.C_BPartner_ID
$BODY$
LANGUAGE sql STABLE;

