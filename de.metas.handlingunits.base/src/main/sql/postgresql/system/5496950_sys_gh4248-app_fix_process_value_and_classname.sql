
UPDATE AD_Process SET 
	Value='WEBUI_C_OrderLineSO_Delete_HUReservation',
	ClassName=replace(ClassName, 'WEBUI_C_OrderLineSO_Delete_HuReservation', 'WEBUI_C_OrderLineSO_Delete_HUReservation')
WHERE Value='WEBUI_C_OrderLineSO_Delete_HuReservation';

UPDATE AD_Process SET 
	Value='WEBUI_C_OrderLineSO_Make_HUReservation',
	ClassName=replace(ClassName, 'WEBUI_C_OrderLineSO_Make_HuReservation', 'WEBUI_C_OrderLineSO_Make_HUReservation')
WHERE Value='WEBUI_C_OrderLineSO_Make_HuReservation';

UPDATE AD_Process SET 
	Value='WEBUI_C_OrderLineSO_Launch_HUEditor',
	ClassName=replace(ClassName, 'WEBUI_C_OrderLineSO_Launch_HuEditor', 'WEBUI_C_OrderLineSO_Launch_HUEditor')
WHERE Value='WEBUI_C_OrderLineSO_Launch_HuEditor';
