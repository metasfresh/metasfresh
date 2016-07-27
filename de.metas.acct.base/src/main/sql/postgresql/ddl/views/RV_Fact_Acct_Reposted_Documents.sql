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
to_number (regexp_matches(p_msg, '\d+')::text, '99999999999999999') AS AD_Table_ID ,
to_number (regexp_matches(substr(p_msg, position('Record_ID =' IN p_msg) + length('Record_ID =')), '\d+')::text, '99999999999999999' ) AS Record_ID 
	
FROM AD_PInstance_Log pilog

JOIN AD_PInstance pinst ON pilog.AD_PInstance_ID = pinst.AD_PInstance_ID
JOIN AD_Process process ON pinst.AD_Process_ID = process.AD_Process_ID 

WHERE process.value = 'Documents_FactAcct_Creation_For_Posted'
;
