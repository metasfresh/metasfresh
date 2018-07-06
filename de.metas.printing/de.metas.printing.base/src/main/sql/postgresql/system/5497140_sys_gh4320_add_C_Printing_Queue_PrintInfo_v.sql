DROP VIEW IF EXISTS C_Printing_Queue_PrintInfo_v;

CREATE OR REPLACE VIEW C_Printing_Queue_PrintInfo_v AS
SELECT
	pq.C_Printing_Queue_ID,
	pp.AD_Session_ID AS AD_Session_PrintPackage_ID,
	pq.AD_Archive_ID,
	pji.C_Print_Job_Instructions_ID,
	pji.Status AS Status_Print_Job_Instructions,
	pji.Created AS Created_Print_Job_Instructions,
	pji.CreatedBy AS CreatedBy_Print_Job_Instructions,
	pji.AD_Org_ID AS AD_Org_Print_Job_Instructions_ID,
	pji.Updated AS Updated_Print_Job_Instructions,
	pji.UpdatedBy AS UpdatedBy_Print_Job_Instructions,
	pp.C_Print_Package_ID,
	ppi.C_Print_PackageInfo_ID,
	ppi.AD_PrinterHW_ID,
	pwh.Name AS PrintServiceName,
	ppi.AD_PrinterHW_MediaTray_ID,
	phwt.TrayNumber AS TrayNumber,
	phwt.Name AS PrintServiceTray,
	pq.Created, 
	pq.AD_Client_ID

FROM C_Printing_Queue pq
	LEFT JOIN C_Print_Job_Line pjl ON pjl.C_Printing_Queue_ID=pq.C_Printing_Queue_ID
		LEFT JOIN C_Print_Job_Instructions pji ON pji.C_Print_Job_ID=pjl.C_Print_Job_ID
			LEFT JOIN C_Print_Package pp ON pp.C_Print_Job_Instructions_ID=pji.C_Print_Job_Instructions_ID
				LEFT JOIN C_Print_PackageInfo ppi ON ppi.C_Print_Package_ID=pp.C_Print_Package_ID
					LEFT JOIN AD_PrinterHW pwh ON pwh.AD_PrinterHW_ID=ppi.AD_PrinterHW_ID
					LEFT JOIN AD_PrinterHW_MediaTray phwt ON phwt.AD_PrinterHW_MediaTray_ID=ppi.AD_PrinterHW_MediaTray_ID
WHERE true;
