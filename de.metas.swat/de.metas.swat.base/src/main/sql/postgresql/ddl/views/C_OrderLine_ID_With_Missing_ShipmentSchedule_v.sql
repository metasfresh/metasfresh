

drop VIEW IF EXISTS C_OrderLine_ID_With_Missing_ShipmentSchedule; -- ad-hoc view, wrong name
CREATE OR REPLACE VIEW C_OrderLine_ID_With_Missing_ShipmentSchedule_v AS
SELECT ol.C_OrderLine_id
from C_OrderLine ol
	join C_Order o ON ol.C_Order_id=o.C_Order_ID
	join C_DocType dt on dt.C_DocType_ID=o.c_doctype_id
where true
	AND ol.QtyOrdered <> ol.QtyDelivered 
	AND NOT EXISTS ( select * from M_ShipmentSchedule s where s.C_OrderLine_ID=ol.C_OrderLine_ID ) 
	and dt.DocBaseType='SOO' AND dt.DocSubType NOT IN ('ON','OB','WR') 
	and o.IsSOTrx='Y' 
	AND o.docstatus='CO' 
	AND NOT EXISTS (      select 1 from M_IolCandHandler_Log log       where log.M_IolCandHandler_ID=1000000        and log.AD_Table_ID=260        and log.Record_ID=ol.C_OrderLine_ID        and log.IsActive='Y'   )
;	
COMMENT ON VIEW C_OrderLine_ID_With_Missing_ShipmentSchedule_v IS 'task 08896';
