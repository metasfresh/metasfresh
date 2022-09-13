-- 2022-09-13T18:18:10.822Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541481,'S',TO_TIMESTAMP('2022-09-13 19:18:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','org.adempiere.mm.attributes.spi.impl.HUVendorBPartnerAttributeValuesProvider.maxBusinessPartners',TO_TIMESTAMP('2022-09-13 19:18:10','YYYY-MM-DD HH24:MI:SS'),100,'20')
;

-- 2022-09-13T18:30:35.209Z
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540070,540026,0,'org.adempiere.mm.attributes.spi.impl.HUBusinessPartnerAttributeHandler',TO_TIMESTAMP('2022-09-13 19:30:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','Business Partner Attribute Value Handler',TO_TIMESTAMP('2022-09-13 19:30:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-13T18:30:41.812Z
UPDATE AD_JavaClass SET Description='Business Partner Attribute Value Handler', IsInterface='N',Updated=TO_TIMESTAMP('2022-09-13 19:30:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JavaClass_ID=540070
;

