DROP VIEW IF EXISTS report.RV_Salesgroups;

Create VIEW report.RV_Salesgroups AS
select x.DateInvoiced, x.ProductSalesgroup,  
Case When 
	x.PartnerSalesGroupValue = '0010' 
	THEN x.qtyInvoiced
	ELSE 0
	END
	AS QtyInvoicedDiscounter,

Case When 
	x.PartnerSalesGroupValue = '0020' 
	THEN x.qtyInvoiced
	ELSE 0
	END
	AS QtyInvoicedGastro,
Case When 
	x.PartnerSalesGroupValue = '0030' 
	THEN x.qtyInvoiced
	ELSE 0
	END
	AS QtyInvoicedDetailhandel,


	
Case When 
	x.PartnerSalesGroupValue = '0010' 
	THEN x.revenue
	ELSE 0
	END
	AS RevenueDiscounter,

Case When 
	x.PartnerSalesGroupValue = '0020' 
	THEN x.revenue
	ELSE 0
	END
	AS RevenueGastro,
Case When 
	x.PartnerSalesGroupValue = '0030' 
	THEN x.revenue
	ELSE 0
	END
	AS RevenueDetailhandel


from
(

SELECT 
	i.DateInvoiced,
	list.name as ProductSalesgroup,
	bp.salesgroup as PartnerSalesGroupValue,

	il.QtyInvoiced,

	case when (i.isTaxIncluded = 'Y')
	then
	
	currencyconvert ( il.linenetamt - il.taxamtinfo :: numeric, i.C_Currency_ID :: numeric, (Select C_Currency_ID from c_Currency where iso_code = 'CHF') :: numeric, i.DateInvoiced ::  timestamp with time zone, 0 :: numeric , i.AD_Client_ID :: numeric, i.AD_Org_ID :: numeric)


	else
	currencyconvert ( il.linenetamt :: numeric, i.C_Currency_ID :: numeric, (Select C_Currency_ID from c_Currency where iso_code = 'CHF') :: numeric, i.DateInvoiced ::  timestamp with time zone, 0 :: numeric , i.AD_Client_ID :: numeric, i.AD_Org_ID :: numeric)

	end
	
	as Revenue
	

FROM C_InvoiceLine il
JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID
JOIN M_Product p on il.M_Product_ID = p.M_Product_ID
JOIN AD_Ref_List list on list.AD_Reference_id in (select AD_Reference_id from AD_Reference where name = 'M_Product_Salesgroup') and list.value = p.Salesgroup
join C_BPartner bp on i.C_BPartner_ID = bp.C_BPartner_ID
JOIN AD_Ref_List list2 on list2.AD_Reference_id in (select AD_Reference_id from AD_Reference where name = 'C_BPartner_Salesgroup') and list2.value = bp.Salesgroup
where i.isSotrx = 'Y'and
	p.Salesgroup is not null and
	bp.Salesgroup is not null


) x



;











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


