
-- 01.03.2016 10:22
-- URL zum Konzept
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,5379,0,TO_TIMESTAMP('2016-03-01 10:22:33','YYYY-MM-DD HH24:MI:SS'),100,'Subscription contracts','de.metas.contracts.subscription','subscription contracts are contracts that come with a regular shipping and/or invoicing','Y','Y','de.metas.contracts.subscription','de.metas.contracts.subscription','N',TO_TIMESTAMP('2016-03-01 10:22:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- deactivated by default; actiavate it where you need it, e.g. in endcustomer.mf15
UPDATE AD_EntityType SET IsDisplayed='N' WHERE EntityType='de.metas.contracts.subscription';
