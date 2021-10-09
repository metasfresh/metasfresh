
UPDATE EDI_Desadv d_outer
SET SumOrderedInStockingUOM=data.SumOrderedInStockingUOM_new, UpdatedBy=99, Updated='2020-01-21 15:33:17.261192+01'
FROM (
select d.edi_desadv_id, sum(ol.qtyOrdered) AS SumOrderedInStockingUOM_new
from EDI_Desadv d
	join EDI_DesadvLine dl ON dl.EDI_Desadv_ID=d.EDI_Desadv_ID
		join C_OrderLine ol ON ol.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID
			join C_Order o ON o.C_Order_ID=ol.C_Order_ID
where o.DocStatus IN ('CO', 'CL')
	and o.IsActive='Y' and ol.IsActive='Y'
	and dl.IsActive='Y'
	and COALESCE(d.sumOrderedInStockingUOM,0) = 0
group by d.edi_desadv_id
) data
where data.edi_desadv_id=d_outer.edi_desadv_id;

UPDATE EDI_Desadv d_outer
SET sumDeliveredInStockingUOM=data.SumDeliveredInStockingUOM_new, UpdatedBy=99, Updated='2020-01-21 15:33:17.261192+01'
FROM (
select d.edi_desadv_id, sum(iol.MovementQty) AS SumDeliveredInStockingUOM_new
from EDI_Desadv d
	join EDI_DesadvLine dl ON dl.EDI_Desadv_ID=d.EDI_Desadv_ID
		join M_InoutLine iol ON iol.EDI_DesadvLine_ID=dl.EDI_DesadvLine_ID
			join M_Inout io ON io.M_Inout_ID=iol.M_Inout_ID
where io.DocStatus IN ('CO', 'CL')
	and io.IsActive='Y' and iol.IsActive='Y'
	and dl.IsActive='Y'
	and COALESCE(d.sumDeliveredInStockingUOM,0) = 0
group by d.edi_desadv_id
) data
where data.edi_desadv_id=d_outer.edi_desadv_id;

UPDATE EDI_Desadv d_outer
SET FulfillmentPercent=data.FulfillmentPercent_new, UpdatedBy=99, Updated='2020-01-21 15:33:17.261192+01'
FROM (
select d.edi_desadv_id, round((sumDeliveredInStockingUOM/SumOrderedInStockingUOM * 100),0) AS FulfillmentPercent_new
from edi_desadv d
where sumDeliveredInStockingUOM!=0 AND SumOrderedInStockingUOM!=0 and Coalesce(FulfillmentPercent,0)<=0
) data
where data.edi_desadv_id=d_outer.edi_desadv_id;
