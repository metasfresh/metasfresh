update M_Material_Tracking
set QtyIssued = 
(
	select coalesce(sum(cc.MovementQty), 0)
	from M_Material_Tracking mt
	join M_Material_Tracking_ref ref on (mt.M_Material_Tracking_ID = ref.M_Material_Tracking_ID)
	join PP_Cost_Collector cc on (ref.AD_Table_ID = 53027 and ref.Record_id = cc.PP_Order_ID)
	where mt.M_Material_Tracking_ID=M_Material_Tracking.M_Material_Tracking_ID 
	and mt.M_product_ID = cc.M_Product_ID 
	and cc.COSTCOLLECTORTYPE = '110'
	and cc.DocStatus in ('CO', 'CL')
);



update M_Material_Tracking_Ref
set QtyIssued = 
(
	select coalesce(sum(cc.MovementQty), 0)
	from M_Material_Tracking_ref ref 
	join M_Material_Tracking mt on (mt.M_Material_Tracking_ID = ref.M_Material_Tracking_ID)
	join PP_Cost_Collector cc on (ref.AD_Table_ID = 53027 and ref.Record_id = cc.PP_Order_ID)
	where ref.M_Material_Tracking_Ref_ID=M_Material_Tracking_Ref.M_Material_Tracking_Ref_ID 
	and mt.M_product_ID = cc.M_Product_ID 
	and cc.COSTCOLLECTORTYPE = '110'
	and cc.DocStatus in ('CO', 'CL')
)
;