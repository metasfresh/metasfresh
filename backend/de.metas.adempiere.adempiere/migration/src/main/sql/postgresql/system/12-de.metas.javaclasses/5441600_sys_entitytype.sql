
-- 04.03.2016 10:22
-- URL zum Konzept
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,5382,0,TO_TIMESTAMP('2016-03-04 10:22:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.javaclasses','Y','Y','de.metas.javaclasses.model','de.metas.javaclasses','N',TO_TIMESTAMP('2016-03-04 10:22:44','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 04.03.2016 11:16
-- URL zum Konzept
UPDATE AD_Table SET EntityType='de.metas.javaclasses',Updated=TO_TIMESTAMP('2016-03-04 11:16:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540520
;

-- 04.03.2016 11:16
-- URL zum Konzept
UPDATE AD_Table SET EntityType='de.metas.javaclasses',Updated=TO_TIMESTAMP('2016-03-04 11:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540521
;

-- 04.03.2016 11:16
-- URL zum Konzept
UPDATE AD_Window SET EntityType='de.metas.javaclasses',Updated=TO_TIMESTAMP('2016-03-04 11:16:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540193
;



-- 04.03.2016 11:33
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.javaclasses',Updated=TO_TIMESTAMP('2016-03-04 11:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554194
;



-- 04.03.2016 11:34
-- URL zum Konzept
UPDATE AD_Column SET Description='Systementitäts-Art', Name='Entitäts-Art',Updated=TO_TIMESTAMP('2016-03-04 11:34:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549455
;

-- 04.03.2016 11:34
-- URL zum Konzept
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=549455
;

-- 04.03.2016 11:34
-- URL zum Konzept
UPDATE AD_Field SET Name='Entitäts-Art', Description='Systementitäts-Art', Help='The entity type determines the ownership of Application Dictionary entries.  The types "Dictionary" and "Adempiere" should not be used and are maintainted by Adempiere (i.e. all changes are reversed during migration to the current definition).' WHERE AD_Column_ID=549455 AND IsCentrallyMaintained='Y'
;
