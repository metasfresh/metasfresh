
-- 2017-12-01T07:56:53.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541178,'S',TO_TIMESTAMP('2017-12-01 07:56:53','YYYY-MM-DD HH24:MI:SS'),100,'If set to Y, then the system will skip all calls to MStorage.add (sync or async).
Background: updating M_Storage costs a lot of performance and we don''t  know of any remaining use cases..so we''ll disable it for a time and then maybe kick out the whole thing..
See https://github.com/metasfresh/metasfresh/issues/3117','D','Y','de.metas.product.M_Storage_Disabled',TO_TIMESTAMP('2017-12-01 07:56:53','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

