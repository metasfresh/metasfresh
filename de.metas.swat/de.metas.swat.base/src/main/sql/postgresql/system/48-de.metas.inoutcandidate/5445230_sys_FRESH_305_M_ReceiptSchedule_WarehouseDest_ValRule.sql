-- 12.05.2016 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540331,'EXISTS(select 1 from DD_NetworkDistributionLine dd WHERE M_WarehouseSource_ID = @M_Warehouse_ID@ AND M_Warehouse.M_Warehouse+ID = dd.M_Warehouse_ID)',TO_TIMESTAMP('2016-05-12 11:33:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','M_Warehouse_Target','S',TO_TIMESTAMP('2016-05-12 11:33:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 12.05.2016 11:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540331,Updated=TO_TIMESTAMP('2016-05-12 11:33:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550277
;

-- 12.05.2016 11:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS(select 1 from DD_NetworkDistributionLine dd WHERE M_WarehouseSource_ID = @M_Warehouse_ID@ AND M_Warehouse.M_Warehouse_ID = dd.M_Warehouse_ID)',Updated=TO_TIMESTAMP('2016-05-12 11:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

-- 12.05.2016 11:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS(select 1 from DD_NetworkDistributionLine dd WHERE dd.M_WarehouseSource_ID = @M_Warehouse_ID@ AND M_Warehouse.M_Warehouse_ID = dd.M_Warehouse_ID)',Updated=TO_TIMESTAMP('2016-05-12 11:38:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

-- 12.05.2016 11:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS(select 1 from DD_NetworkDistributionLine dd where dd.M_WarehouseSource_ID = @M_Warehouse_ID@ and M_Warehouse.M_Warehouse_ID = dd.M_Warehouse_ID)',Updated=TO_TIMESTAMP('2016-05-12 11:48:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

-- 12.05.2016 11:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS(select * from DD_NetworkDistributionLine dd where dd.M_WarehouseSource_ID = @M_Warehouse_ID@ and M_Warehouse.M_Warehouse_ID = dd.M_Warehouse_ID)',Updated=TO_TIMESTAMP('2016-05-12 11:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

-- 12.05.2016 14:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS(select * from DD_NetworkDistributionLine dd where dd.M_WarehouseSource_ID = @#M_Warehouse_ID@ and M_Warehouse.M_Warehouse_ID = dd.M_Warehouse_ID)',Updated=TO_TIMESTAMP('2016-05-12 14:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

-- 12.05.2016 15:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS(select * from DD_NetworkDistributionLine dd where dd.M_WarehouseSource_ID = @M_Warehouse_ID@ and M_Warehouse.M_Warehouse_ID = dd.M_Warehouse_ID)',Updated=TO_TIMESTAMP('2016-05-12 15:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

-- 12.05.2016 17:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS( select * from DD_NetworkDistributionLine dd where dd.M_WarehouseSource_ID = @M_Warehouse_ID@ and M_Warehouse.M_Warehouse_ID = dd.M_Warehouse_ID and dd.isActive = ''Y'' )',Updated=TO_TIMESTAMP('2016-05-12 17:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

-- 12.05.2016 17:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS( 
select *
 from DD_NetworkDistributionLine ddl
join DD_NetworkDistribution dd on ddl.DD_NetworkDistribution_ID = dd.DD_NetworkDistribution_ID 
where ddl.M_WarehouseSource_ID = @M_Warehouse_ID@ 
and M_Warehouse.M_Warehouse_ID = ddl.M_Warehouse_ID
 and ddl.isActive = ''Y'' 
and dd.isActive = ''Y'')',Updated=TO_TIMESTAMP('2016-05-12 17:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540331
;

