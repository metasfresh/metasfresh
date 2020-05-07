
-- Mar 8, 2016 11:20 AM
-- URL zum Konzept
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,5383,0,TO_TIMESTAMP('2016-03-08 11:20:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.jms','Y','Y','de.metas.jms.model','de.metas.jms','N',TO_TIMESTAMP('2016-03-08 11:20:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Mar 8, 2016 11:21 AM
-- URL zum Konzept
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540108,0,TO_TIMESTAMP('2016-03-08 11:21:13','YYYY-MM-DD HH24:MI:SS'),100,'This model interceptor is responsibt of starting an embedded broker, if the system is configured that way','de.metas.jms','Y','de.metas.jms.JmsInterceptor','de.metas.jms.JmsInterceptor',0,TO_TIMESTAMP('2016-03-08 11:21:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Mar 8, 2016 11:21 AM
-- URL zum Konzept
UPDATE AD_ModelValidator SET Description='This model interceptor is responsible of starting an embedded broker, if the system is configured that way',Updated=TO_TIMESTAMP('2016-03-08 11:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ModelValidator_ID=540108
;

