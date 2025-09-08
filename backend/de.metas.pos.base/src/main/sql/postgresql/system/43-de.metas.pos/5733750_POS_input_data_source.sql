-- 2024-09-19T11:10:00.573Z
INSERT INTO AD_InputDataSource (AD_Client_ID,AD_InputDataSource_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDestination,IsEdiEnabled,Name,Updated,UpdatedBy,Value) VALUES (1000000,540235,1000000,TO_TIMESTAMP('2024-09-19 14:10:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','SOURCE.POS',TO_TIMESTAMP('2024-09-19 14:10:00','YYYY-MM-DD HH24:MI:SS'),100,'SOURCE.POS')
;

-- 2024-09-19T11:10:32.842Z
UPDATE AD_InputDataSource SET Description='from Point of Sales',Updated=TO_TIMESTAMP('2024-09-19 14:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_InputDataSource_ID=540235
;

update ad_inputdatasource set internalname='SOURCE.POS', ad_client_id=0, ad_org_id=0 where ad_inputdatasource_id=540235
;

