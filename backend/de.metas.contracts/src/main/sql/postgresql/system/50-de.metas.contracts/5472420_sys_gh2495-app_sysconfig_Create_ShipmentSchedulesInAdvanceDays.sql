
-- 2017-09-22T17:40:11.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541162,'O',TO_TIMESTAMP('2017-09-22 17:40:11','YYYY-MM-DD HH24:MI:SS'),100,'Tells the system how many days before a C_SubscriptionProgress''s EventDate the respective M_ShipmentSchedule shall be created.
Zero and negative integets are also legal. However, a negative value means that the shipment schedule is created *after* the EventDate, which probably doesn''t make sense.','de.metas.contracts.subscription','Y','C_SubscriptionProgress.Create_ShipmentSchedulesInAdvanceDays',TO_TIMESTAMP('2017-09-22 17:40:11','YYYY-MM-DD HH24:MI:SS'),100,'1')
;
