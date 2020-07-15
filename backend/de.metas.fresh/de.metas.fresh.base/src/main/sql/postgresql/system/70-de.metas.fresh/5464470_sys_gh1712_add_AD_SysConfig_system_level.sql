-- 2017-06-07T11:50:29.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541149,'O',TO_TIMESTAMP('2017-06-07 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'If Y then the M_ShipmentSchedule_ID will be included in the grouping key for the items shown in the picking terminal.
Set this to true to get one line for each shipment schedule. 
This config can be overridden on the ord level and will be evaluated using the respective shipment schedule''s client and organisation.
See https://github.com/metasfresh/metasfresh/issues/1712','de.metas.fresh','Y','de.metas.fresh.picking.form.FreshPackingMd.groupByShipmentSchedule',TO_TIMESTAMP('2017-06-07 11:50:29','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2017-06-07T11:53:28.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='If Y then the respective M_ShipmentSchedule_IDs will be included in the grouping key for the items shown in the picking terminal. I.e. Set this to Y to get one line for each shipment schedule.
This config can be overridden on the org level and will be evaluated for each individual shipment schedule, using the respective shipment schedule''s AD_Client_ID and AD_Org_ID.
Needs to be Y for the SwingUI based picking terminal, if there can be multiple order lines which the same date, partner, product and if existing HUs need to be assigned to them; 
see https://github.com/metasfresh/metasfresh/issues/1712 .',Updated=TO_TIMESTAMP('2017-06-07 11:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541149
;

