DROP FUNCTION IF EXISTS report.Salesgroups (IN DateFrom timestamp without time zone, In DateTo timestamp without time zone);

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
	asi_inausland character varying
	
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.Salesgroups(IN DateFrom timestamp without time zone, In DateTo timestamp without time zone) RETURNS SETOF report.Salesgroups_Report_Sub AS
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
	asi_inausland
	
FROM report.RV_Salesgroups rv
JOIN C_UOM uom on uom.C_UOM_ID = rv.uom
WHERE rv.DateInvoiced >= $1 AND rv.DateInvoiced <= $2
GROUP BY 	
	asi_inausland, productsalesgroup, uom.name
	
	
	
	
	
ORDER BY
	asi_inausland,productsalesgroup ASC$BODY$
LANGUAGE sql STABLE;

