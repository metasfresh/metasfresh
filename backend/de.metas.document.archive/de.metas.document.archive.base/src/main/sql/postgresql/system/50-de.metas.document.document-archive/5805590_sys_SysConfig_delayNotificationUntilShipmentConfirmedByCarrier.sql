-- on-switch for delaying the shipment notification email until the
-- shipment carrier has returned its tracking URL(s). Default 'N' (vanilla unchanged);
-- set 'Y' per customer instance. Read by InOutNotificationDelayHandler.
INSERT INTO AD_SysConfig (
  AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,Name,Value,ConfigurationLevel,
  EntityType,IsActive,Created,CreatedBy,Updated,UpdatedBy
) VALUES (
  0,0,541810 /*From ID Server*/,'delayNotificationUntilShipmentConfirmedByCarrier','N','S',
  'D','Y',TO_TIMESTAMP('2026-06-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-01 12:00:00','YYYY-MM-DD HH24:MI:SS'),100
);
