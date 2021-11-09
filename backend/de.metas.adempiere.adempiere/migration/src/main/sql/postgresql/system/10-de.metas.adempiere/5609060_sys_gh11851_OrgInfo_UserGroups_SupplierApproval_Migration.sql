CREATE TABLE backup.BKP_AD_SYS_Config_12102021
AS
SELECT *
FROM ad_sysconfig
;

CREATE TABLE backup.BKP_AD_OrgInfo_12102021
AS
SELECT *
FROM ad_orginfo
;



UPDATE ad_orginfo oi
SET c_bp_supplierapproval_expiration_notify_usergroup_id = x.c_bp_supplierapproval_expiration_notify_usergroup_id :: numeric
FROM (
         SELECT c.ad_org_id, c.value as c_bp_supplierapproval_expiration_notify_usergroup_id
         FROM ad_sysconfig c
         WHERE NAME = 'C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID'
           AND isactive = 'Y'
     ) x

WHERE (
              (oi.ad_org_id = x.ad_org_id)
              OR
              (x.ad_org_id = 0 AND NOT EXISTS(SELECT 1
                                              FROM ad_sysconfig c
                                              WHERE NAME = 'C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID'
                                                AND c.ad_org_id = oi.ad_org_id)
                  )
          );
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  

DELETE FROM ad_sysconfig
WHERE NAME = 'C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID';