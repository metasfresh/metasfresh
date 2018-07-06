
DROP VIEW IF EXISTS C_Order_MFGWarehouse_Report_PrintInfo_v;

CREATE OR REPLACE VIEW C_Order_MFGWarehouse_Report_PrintInfo_v AS
SELECT
	o_wh.C_Order_ID,
	o_wh.C_Order_MFGWarehouse_Report_ID,
	o_wh.AD_User_responsible_ID,
	o_wh.AD_Org_ID,
	o_wh.AD_Client_ID,
	pp.AD_Session_ID AS AD_Session_PrintPackage_ID,
	p.S_Resource_ID,
	wh.M_Warehouse_ID,
	wpe.C_Queue_Element_ID,
	wpe.C_Queue_WorkPackage_ID,
	wp.Processed AS wp_IsProcessed,
	wp.IsError AS wp_IsError,
	a.ad_archive_ID,
	pq.C_Printing_Queue_ID,
	pji.C_Print_Job_Instructions_ID,
	pji.Status AS Status_Print_Job_Instructions,
	pp.C_Print_Package_ID,
	ppi.C_Print_PackageInfo_ID,
	ppi.AD_PrinterHW_ID,
	ppi.AD_PrinterHW_MediaTray_ID,
	pwh.Name AS PrintServiceName,
	phwt.TrayNumber AS TrayNumber,
	phwt.Name AS PrintServiceTray,
	o_wh.created,
	o_wh.createdby,
	o_wh.updated,
	o_wh.updatedby

FROM C_Order_MFGWarehouse_Report o_wh
	LEFT JOIN C_Queue_Element wpe ON wpe.Record_ID=o_wh.C_Order_MFGWarehouse_Report_ID and wpe.AD_Table_ID=get_table_id('C_Order_MFGWarehouse_Report')
		LEFT JOIN C_Queue_WorkPackage wp ON wp.C_Queue_WorkPackage_ID=wpe.C_Queue_WorkPackage_ID
	LEFT JOIN AD_Archive a ON a.Record_ID=o_wh.C_Order_MFGWarehouse_Report_ID and a.AD_Table_ID=get_table_id('C_Order_MFGWarehouse_Report')
		LEFT JOIN C_Printing_Queue pq ON pq.AD_Archive_ID=a.AD_Archive_ID
			LEFT JOIN C_Print_Job_Line pjl ON pjl.C_Printing_Queue_ID=pq.C_Printing_Queue_ID
				LEFT JOIN C_Print_Job_Instructions pji ON pji.C_Print_Job_ID=pjl.C_Print_Job_ID
					LEFT JOIN C_Print_Package pp ON pp.C_Print_Job_Instructions_ID=pji.C_Print_Job_Instructions_ID
						LEFT JOIN C_Print_PackageInfo ppi ON ppi.C_Print_Package_ID=pp.C_Print_Package_ID
							LEFT JOIN AD_PrinterHW pwh ON pwh.AD_PrinterHW_ID=ppi.AD_PrinterHW_ID
							LEFT JOIN AD_PrinterHW_MediaTray phwt ON phwt.AD_PrinterHW_MediaTray_ID=ppi.AD_PrinterHW_MediaTray_ID
	LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID=o_wh.M_Warehouse_ID
	LEFT JOIN S_Resource p ON p.S_Resource_ID=o_wh.pp_plant_id
WHERE true;
