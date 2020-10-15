-- 2020-10-13T20:02:27.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541345,'O',TO_TIMESTAMP('2020-10-13 22:02:26','YYYY-MM-DD HH24:MI:SS'),100,'If Y and the QtyToDeliver increases on an already exported shipment schedule (a.k.a. shipment candidate), then its status shall be set back to "pending", to be exported again.
If setting it to Y, note that this might happen when you don''t expect it.','de.metas.inoutcandidate','Y','de.metas.inoutcandidate.M_ShipmentSchedule.canBeExportedIfQtyToDeliverIsIncreased',TO_TIMESTAMP('2020-10-13 22:02:26','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

