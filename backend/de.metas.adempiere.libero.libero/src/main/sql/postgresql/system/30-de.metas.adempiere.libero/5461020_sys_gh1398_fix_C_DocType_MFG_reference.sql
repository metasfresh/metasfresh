-- 2017-04-29T15:50:12.972
-- i.e. removed the AD_Client_ID=@#AD_Client_ID@ part
UPDATE AD_Ref_Table SET WhereClause='C_DocType.DocBaseType IN  (''MOP'',''MOF'',''MQO'') ',Updated=TO_TIMESTAMP('2017-04-29 15:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=53233
;

