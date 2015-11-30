
UPDATE PP_Order ppo
SET M_Material_Tracking_ID=mtr.M_Material_Tracking_ID
FROM M_Material_Tracking_Ref mtr
WHERE true
	AND mtr.AD_Table_ID=get_table_id('PP_Order')
	AND mtr.Record_ID=ppo.PP_Order_ID
	AND mtr.IsActive='Y'
	AND ppo.IsActive='Y'
;
