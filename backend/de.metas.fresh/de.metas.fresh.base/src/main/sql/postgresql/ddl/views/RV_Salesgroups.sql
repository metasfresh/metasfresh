DROP VIEW IF EXISTS report.RV_Salesgroups;

Create VIEW report.RV_Salesgroups AS
select x.DateInvoiced, x.ProductSalesgroup,  x.uom, x.asi_inausland, x.asi_adr, x.asi_country,

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
	AS RevenueDetailhandel,
	
x.ad_org_id


from
(

SELECT 
	i.DateInvoiced,
	list.name as ProductSalesgroup,
	bp.salesgroup as PartnerSalesGroupValue,
	coalesce(p.Salesgroup_UOM_ID, p.C_UOM_ID) as uom,

	(
		case when p.Salesgroup_UOM_ID > 0 
		then uomconvert(p.M_Product_ID, il.C_UOM_ID, p.Salesgroup_UOM_ID, il.QtyInvoiced)
		else uomconvert(p.M_Product_ID, il.C_UOM_ID, p.C_UOM_ID, il.QtyInvoiced)
		end
	
	)QtyInvoiced,

	case when (i.isTaxIncluded = 'Y')
	then
	
	currencyconvert ( il.linenetamt - il.taxamtinfo :: numeric, i.C_Currency_ID :: numeric, (Select C_Currency_ID from c_Currency where iso_code = 'CHF') :: numeric, i.DateInvoiced ::  timestamp with time zone, 0 :: numeric , i.AD_Client_ID :: numeric, i.AD_Org_ID :: numeric)


	else
	currencyconvert ( il.linenetamt :: numeric, i.C_Currency_ID :: numeric, (Select C_Currency_ID from c_Currency where iso_code = 'CHF') :: numeric, i.DateInvoiced ::  timestamp with time zone, 0 :: numeric , i.AD_Client_ID :: numeric, i.AD_Org_ID :: numeric)

	end
	
	as Revenue
	,(SELECT ai_value FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID AND at_value='1000000') AS asi_inausland-- inausland
	,(SELECT ai_value FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID AND at_value='1000015') AS asi_adr-- adr
	,(SELECT ai_value FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = il.M_AttributeSetInstance_ID AND at_value='1000001') AS asi_country-- country
	
	, i.ad_org_id

FROM C_InvoiceLine il
JOIN C_Invoice i on il.C_Invoice_ID = i.C_Invoice_ID AND i.isActive = 'Y'
JOIN M_Product p on il.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
JOIN AD_Ref_List list on list.AD_Reference_id in (select AD_Reference_id from AD_Reference where name = 'M_Product_Salesgroup' AND isActive = 'Y') and list.value = p.Salesgroup AND list.isActive = 'Y'
join C_BPartner bp on i.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
JOIN AD_Ref_List list2 on list2.AD_Reference_id in (select AD_Reference_id from AD_Reference where name = 'C_BPartner_Salesgroup' AND isActive = 'Y') and list2.value = bp.Salesgroup AND list2.isActive = 'Y'
where i.isSotrx = 'Y'and
	p.Salesgroup is not null and
	bp.Salesgroup is not null
	AND il.isActive = 'Y'


) x
--we only want to show the ones from inland (inland, adr or ch) task 09837
where x.asi_inausland='Inland' OR x.asi_adr='AdR' OR asi_country='CH'

;