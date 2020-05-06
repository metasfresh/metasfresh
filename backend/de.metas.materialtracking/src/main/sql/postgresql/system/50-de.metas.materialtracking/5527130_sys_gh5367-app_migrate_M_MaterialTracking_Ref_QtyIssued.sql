create table backup.M_Material_Tracking_Ref_20190711_2 AS select * from M_Material_Tracking_Ref;

UPDATE M_Material_Tracking_Ref mtr_outer
SET Updated=now(),
	UpdatedBy=99,
	QtyIssued=data.MovementQty
FROM (
	select 
		mtr.M_Material_Tracking_Ref_ID, mtr.QtyIssued, cc.MovementQty
	from M_Material_Tracking_Ref mtr
		join PP_Cost_Collector cc ON mtr.AD_Table_ID = 53035 and mtr.Record_ID=cc.PP_Cost_Collector_ID
	where true
	--	and M_Material_Tracking_ID = 1003059
		and cc.CostCollectorType='110'
		and mtr.QtyIssued is null
) data
WHERE data.M_Material_Tracking_Ref_ID = mtr_outer.M_Material_Tracking_Ref_ID
;
