-- 2017-09-18T10:48:22.634
-- URL zum Konzept
INSERT INTO C_Async_Batch_Type 
(AD_Client_ID,AD_Org_ID,C_Async_Batch_Type_ID,Created,CreatedBy,InternalName,IsActive,IsSendMail,IsSendNotification,KeepAliveTimeHours,SkipTimeoutMillis,Updated,UpdatedBy) 
SELECT 1000000,1000000,540008,TO_TIMESTAMP('2017-09-18 10:48:22','YYYY-MM-DD HH24:MI:SS'),100,'DunningDoc','Y','N','N','24',0,TO_TIMESTAMP('2017-09-18 10:48:22','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from C_Async_Batch_Type where InternalName='DunningDoc')
;

