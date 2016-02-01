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
