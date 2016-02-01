DROP FUNCTION IF EXISTS report.Salesgroups (IN DateFrom timestamp without time zone, In DateTo timestamp without time zone);

DROP TABLE IF EXISTS report.Salesgroups_Report_Sub;


CREATE TABLE report.Salesgroups_Report_Sub
(
	
	productsalesgroup character varying(60),
	
	qtyinvoiceddiscounter numeric,			
	qtyinvoicedgastro numeric,			
	qtyinvoiceddetailhandel	numeric,	
	revenuediscounter numeric,			
	revenuegastro numeric,			
	revenuedetailhandel numeric
	
)
WITH (
	OIDS=FALSE
);


CREATE FUNCTION report.Salesgroups(IN DateFrom timestamp without time zone, In DateTo timestamp without time zone) RETURNS SETOF report.Salesgroups_Report_Sub AS
$BODY$
SELECT

	productsalesgroup,
	
	sum(qtyinvoiceddiscounter),		
	sum(qtyinvoicedgastro),	
	sum(qtyinvoiceddetailhandel),	
	sum(revenuediscounter),		
	sum(revenuegastro),		
	sum(revenuedetailhandel)
	
FROM report.RV_Salesgroups rv
WHERE rv.DateInvoiced >= $1 AND rv.DateInvoiced <= $2
GROUP BY 	
	productsalesgroup
	
	
	
	
	
ORDER BY
	productsalesgroup ASC$BODY$
LANGUAGE sql STABLE;

