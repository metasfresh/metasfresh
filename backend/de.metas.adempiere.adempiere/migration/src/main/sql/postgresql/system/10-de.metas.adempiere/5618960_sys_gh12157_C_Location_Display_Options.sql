-- 2021-12-15T08:23:13.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541434,'C',TO_TIMESTAMP('2021-12-15 10:23:13','YYYY-MM-DD HH24:MI:SS'),100,'Decides if the Address1 field shall be shown in the address dialog','D','Y','de.metas.ui.web.address.AddressDescriptorFactory.Address1.IsDisplay',TO_TIMESTAMP('2021-12-15 10:23:13','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-12-15T08:23:55.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541435,'C',TO_TIMESTAMP('2021-12-15 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Decides if the Address1 field shall be shown in the address dialog','D','Y','de.metas.ui.web.address.AddressDescriptorFactory.Address2.IsDisplay',TO_TIMESTAMP('2021-12-15 10:23:55','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2021-12-15T08:24:06.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='C', Description='Decides if the Address3 field shall be shown in the address dialog', Name='de.metas.ui.web.address.AddressDescriptorFactory.Address3.IsDisplay',Updated=TO_TIMESTAMP('2021-12-15 10:24:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541435
;

-- 2021-12-15T08:24:20.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541436,'C',TO_TIMESTAMP('2021-12-15 10:24:20','YYYY-MM-DD HH24:MI:SS'),100,'Decides if the Address4 field shall be shown in the address dialog','D','Y','de.metas.ui.web.address.AddressDescriptorFactory.Address4.IsDisplay',TO_TIMESTAMP('2021-12-15 10:24:20','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;







CREATE table backup_BKP_AD_SysConfig_gh12157_15122021
AS
    SELECT * FROM ad_sysconfig;
	
	
	
UPDATE ad_sysconfig s
SET Value = x.Value
FROM
(SELECT Value from AD_SysConfig WHERE name = 'de.metas.ui.web.address.ShowAddress4' ) x
WHERE s.name = 'de.metas.ui.web.address.AddressDescriptorFactory.Address4.IsDisplay';



UPDATE ad_sysconfig s
SET Value = x.Value
FROM
    (SELECT Value from AD_SysConfig WHERE name = 'de.metas.ui.web.address.ShowAddress3' ) x
WHERE s.name = 'de.metas.ui.web.address.AddressDescriptorFactory.Address3.IsDisplay';


-- 2021-12-15T11:50:44.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541427
;

-- 2021-12-15T11:50:47.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_SysConfig WHERE AD_SysConfig_ID=541426
;

