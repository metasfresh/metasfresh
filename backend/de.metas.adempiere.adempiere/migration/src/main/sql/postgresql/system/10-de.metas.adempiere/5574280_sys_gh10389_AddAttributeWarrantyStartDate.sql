--
-- Script: C:\Users\crist\.metasfresh\migration_scripts\5574280_migration_2020-12-08_postgresql.sql
-- User: metasfresh
-- OS user: crist
--


-- 2020-12-08T09:57:23.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,IsActive,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,'S',TO_TIMESTAMP('2020-12-08 11:57:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','N','N',540093,'WarrantyStartDate',TO_TIMESTAMP('2020-12-08 11:57:23','YYYY-MM-DD HH24:MI:SS'),100,'WarrantyStartDate')
;

-- 2020-12-08T09:57:24.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Description='WarrantyStartDate',Updated=TO_TIMESTAMP('2020-12-08 11:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540093
;

-- 2020-12-08T09:57:25.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET AttributeValueType='D',Updated=TO_TIMESTAMP('2020-12-08 11:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540093
;

-- 2020-12-08T09:57:27.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Attribute SET Value='WarrantyStartDate',Updated=TO_TIMESTAMP('2020-12-08 11:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540093
;

