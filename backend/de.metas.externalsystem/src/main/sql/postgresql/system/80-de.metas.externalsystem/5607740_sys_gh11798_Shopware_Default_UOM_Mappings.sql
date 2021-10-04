UPDATE c_uom
SET isactive = 'Y'
WHERE c_uom_id = 540019;

UPDATE c_uom
SET isactive = 'Y'
WHERE c_uom_id = 540015;

-- 2021-10-04T09:17:25.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Config_Shopware6_UOM (AD_Client_ID,AD_Org_ID,C_UOM_ID,Created,CreatedBy,ExternalSystem_Config_Shopware6_ID,ExternalSystem_Config_Shopware6_UOM_ID,IsActive,ShopwareCode,Updated,UpdatedBy) VALUES (1000000,1000000,540019,TO_TIMESTAMP('2021-10-04 12:17:25','YYYY-MM-DD HH24:MI:SS'),100,540000,540000,'Y','g',TO_TIMESTAMP('2021-10-04 12:17:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-04T09:17:44.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Config_Shopware6_UOM (AD_Client_ID,AD_Org_ID,C_UOM_ID,Created,CreatedBy,ExternalSystem_Config_Shopware6_ID,ExternalSystem_Config_Shopware6_UOM_ID,IsActive,ShopwareCode,Updated,UpdatedBy) VALUES (1000000,1000000,540017,TO_TIMESTAMP('2021-10-04 12:17:44','YYYY-MM-DD HH24:MI:SS'),100,540000,540001,'Y','kg',TO_TIMESTAMP('2021-10-04 12:17:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-04T09:18:05.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ExternalSystem_Config_Shopware6_UOM (AD_Client_ID,AD_Org_ID,C_UOM_ID,Created,CreatedBy,ExternalSystem_Config_Shopware6_ID,ExternalSystem_Config_Shopware6_UOM_ID,IsActive,ShopwareCode,Updated,UpdatedBy) VALUES (1000000,1000000,540015,TO_TIMESTAMP('2021-10-04 12:18:05','YYYY-MM-DD HH24:MI:SS'),100,540000,540002,'Y','l',TO_TIMESTAMP('2021-10-04 12:18:05','YYYY-MM-DD HH24:MI:SS'),100)
;