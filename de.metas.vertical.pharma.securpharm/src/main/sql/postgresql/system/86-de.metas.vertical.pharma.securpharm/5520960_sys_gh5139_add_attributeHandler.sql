-- 2019-05-06T17:48:58.573
-- URL zum Konzept
INSERT INTO AD_JavaClass (Updated,UpdatedBy,Created,CreatedBy,IsActive,AD_Org_ID,AD_JavaClass_Type_ID,Classname,AD_JavaClass_ID,AD_Client_ID,IsInterface,EntityType,Description,Name) VALUES (TO_TIMESTAMP('2019-05-06 17:48:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-06 17:48:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,540026,'de.metas.vertical.pharma.securpharm.attribute.HUScannedAttributeHandler',540052,0,'N','de.metas.vertical.pharma.securpharm','','Scanned Attribute  Handler')
;

-- 2019-05-06T17:49:15.480
-- URL zum Konzept
UPDATE AD_JavaClass SET IsInterface='N', Name='Scanned Attribute Handler',Updated=TO_TIMESTAMP('2019-05-06 17:49:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JavaClass_ID=540052
;

-- 2019-05-06T17:49:39.117
-- URL zum Konzept
UPDATE M_Attribute SET AD_JavaClass_ID=540052,Updated=TO_TIMESTAMP('2019-05-06 17:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540040
;
