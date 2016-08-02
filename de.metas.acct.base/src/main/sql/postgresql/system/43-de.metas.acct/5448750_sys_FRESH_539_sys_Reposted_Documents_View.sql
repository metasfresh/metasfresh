DROP VIEW IF EXISTS de_metas_acct.RV_Reposted_Documents ;


CREATE OR REPLACE VIEW de_metas_acct.RV_Reposted_Documents 
AS 
SELECT
	pinst.AD_Client_ID,
	pinst.AD_Org_ID,
	pinst.Created,
	pinst.CreatedBy,
	pinst.Updated,
	pinst.UpdatedBy,
	pinst.IsActive,

	pinst.AD_PInstance_ID,

	substring(p_msg FROM 'AD_Table_ID[ =]*([0-9]*)')::numeric AS AS_AD_Table_ID,
	substring(p_msg FROM 'Record_ID[ =]*([0-9]*)')::numeric AS Record_ID,
	substring(p_msg FROM 'TableName[ =]*([a-zA-Z0-9_]*)')::character varying AS TableName,
	substring(p_msg FROM 'DocumentNo[ =]*([a-zA-Z0-9_]*)')::character varying AS DocumentNo
 
FROM AD_PInstance_Log pilog

JOIN AD_PInstance pinst ON pilog.AD_PInstance_ID = pinst.AD_PInstance_ID
JOIN AD_Process process ON pinst.AD_Process_ID = process.AD_Process_ID 

WHERE process.value = 'Documents_FactAcct_Creation_For_Posted' and substring(p_msg FROM 'AD_Table_ID[ =]*([0-9]*)')!='' AND substring(p_msg FROM 'Record_ID[ =]*([0-9]*)')!=''

;
