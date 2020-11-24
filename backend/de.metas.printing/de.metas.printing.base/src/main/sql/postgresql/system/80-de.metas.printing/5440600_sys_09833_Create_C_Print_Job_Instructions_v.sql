


DROP VIEW IF EXISTS C_Print_Job_Instructions_v;

CREATE VIEW C_Print_Job_Instructions_v AS

	SELECT

		o.C_Order_ID,
		o_wh.C_Order_MFGWarehouse_Report_ID,
		u.AD_User_ID,
		sess.AD_Session_ID,
		p.S_Resource_ID,
		wh.M_Warehouse_ID,
		a.ad_archive_ID,
		pji.C_Print_Job_Instructions_ID,
		pp.C_Print_Package_ID,
		pji.Created,
		pji.CreatedBy,
		pji.AD_Org_ID,
		pji.AD_Client_ID,
		pji.Updated,
		pji.UpdatedBy

	FROM C_Print_Job_Instructions pji
		LEFT JOIN C_Print_Package pp ON pp.C_Print_Job_Instructions_ID=pji.C_Print_Job_Instructions_ID
		LEFT JOIN C_Print_Job_Line pjl ON pjl.C_Print_Job_ID=pji.C_Print_Job_ID
		LEFT JOIN C_Printing_Queue pq ON pq.C_Printing_Queue_ID=pjl.C_Printing_Queue_ID
		LEFT JOIN AD_Archive a ON a.AD_Archive_ID=pq.AD_Archive_ID
		LEFT JOIN AD_Table t ON t.AD_Table_ID=a.AD_Table_ID
		LEFT JOIN C_Order_MFGWarehouse_Report o_wh ON o_wh.C_Order_MFGWarehouse_Report_ID=a.Record_ID and t.tablename='C_Order_MFGWarehouse_Report'
		LEFT JOIN AD_User u ON u.AD_User_ID=o_wh.AD_User_responsible_ID
		LEFT JOIN ( select distinct on (sess.Createdby) sess.* from AD_Session sess WHERE sess.Processed='N' ORDER BY sess.Createdby, sess.AD_Session_ID DESC) sess ON sess.Createdby=u.AD_User_ID
		LEFT JOIN M_Warehouse wh ON wh.M_Warehouse_ID=o_wh.M_Warehouse_ID
		LEFT JOIN S_Resource p ON p.S_Resource_ID=o_wh.pp_plant_id
		LEFT JOIN C_Order o ON o.C_Order_ID=o_wh.C_Order_ID
	WHERE true
			AND pji.Status!='D';
