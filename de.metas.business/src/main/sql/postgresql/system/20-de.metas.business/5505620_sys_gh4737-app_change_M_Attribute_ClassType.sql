-- 2018-11-05T15:04:43.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_JavaClass_Type SET Classname='org.adempiere.mm.attributes.spi.IAttributeValueHandler', InternalName='org.adempiere.mm.attributes.spi.IAttributeValueHandler', Name='Attribute Value Handler',Updated=TO_TIMESTAMP('2018-11-05 15:04:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_JavaClass_Type_ID=540026
;

-- 2018-11-05T15:05:55.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy) VALUES (0,540050,540026,0,'de.metas.materialtracking.attributes.MaterialTrackingAttributeValuesProviderFactory',TO_TIMESTAMP('2018-11-05 15:05:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.materialtracking','Y','N','MaterialTrackingAttributeValuesProviderFactory',TO_TIMESTAMP('2018-11-05 15:05:55','YYYY-MM-DD HH24:MI:SS'),100)
;

