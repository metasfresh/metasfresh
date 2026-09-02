SELECT backup_table('c_doctype')
;

-- Fix wrongly generated C_DocType(s)
-- e.g. "CallOrder (001)" which was generated with AD_Client_ID=0 

UPDATE c_doctype
SET ad_client_id=1000000
WHERE ad_client_id = 0
  AND isactive = 'Y'
  AND ad_org_id > 0
;