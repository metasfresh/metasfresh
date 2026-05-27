-- Register M_HU_Report_QRCode (AD_Process_ID=584980) in M_HU_Process so that
-- T_Selection is populated when the process runs and HU IDs can be retrieved.

INSERT INTO M_HU_Process (ad_client_id, ad_org_id, ad_process_id, created, createdby, isactive, isapplytolus, isapplytotus, isapplytocus, m_hu_pi_id, m_hu_process_id, updated, updatedby, isprovideasuseraction, isapplytotoplevelhusonly) VALUES (0, 0, 584980/*From ID Server*/, '2022-02-10 17:33:55.000000 +01:00', 100, 'Y', 'Y', 'Y', 'Y', null, 540019/*From ID Server*/, '2022-02-10 18:53:24.000000 +01:00', 100, 'Y', 'N');

-- delete the process from AD_Table_Process because is registered in M_HU_Process
delete from AD_Table_Process where ad_process_id = 584980/*From ID Server*/;

-- delete copy parameter from AD_Process_Para because is registered in M_HU_Process
delete from AD_Process_Para where ad_process_para_id = 543183/*From ID Server*/;

