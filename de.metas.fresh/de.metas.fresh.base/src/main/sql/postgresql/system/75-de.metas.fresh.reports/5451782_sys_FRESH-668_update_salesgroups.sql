DROP FUNCTION IF EXISTS report.Salesgroups (IN DateFrom timestamp without time zone, In DateTo timestamp without time zone);

DROP FUNCTION IF EXISTS report.Salesgroups (IN DateFrom timestamp without time zone, In DateTo timestamp without time zone, IN ad_org_id numeric(10,0));

DROP TABLE IF EXISTS report.Salesgroups_Report_Sub;


CREATE TABLE report.Salesgroups_Report_Sub
(
	
	productsalesgroup character varying(60),
	uom character varying(60),
	qtyinvoiceddiscounter numeric,			
	qtyinvoicedgastro numeric,			
	qtyinvoiceddetailhandel	numeric,	
	revenuediscounter numeric,			
	revenuegastro numeric,			
	revenuedetailhandel numeric,
	
	ad_org_id numeric
	
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.Salesgroups(IN DateFrom timestamp without time zone, In DateTo timestamp without time zone, IN ad_org_id numeric(10,0)) RETURNS SETOF report.Salesgroups_Report_Sub AS
$BODY$
SELECT

	productsalesgroup,
	uom.name,
	
	sum(qtyinvoiceddiscounter),		
	sum(qtyinvoicedgastro),	
	sum(qtyinvoiceddetailhandel),	
	sum(revenuediscounter),		
	sum(revenuegastro),		
	sum(revenuedetailhandel),
	
	rv.ad_org_id
	
FROM report.RV_Salesgroups rv
JOIN C_UOM uom on uom.C_UOM_ID = rv.uom AND uom.isActive = 'Y'
WHERE rv.DateInvoiced >= $1 AND rv.DateInvoiced <= $2
	AND rv.ad_org_id = $3
GROUP BY 	
	productsalesgroup, uom.name,
	rv.ad_org_id
	
ORDER BY
	productsalesgroup ASC$BODY$
LANGUAGE sql STABLE;

