-- gh26820: on-switch for delaying document notifications (invoice email) until the
-- shipment carrier has returned its tracking URL(s). Default 'N' (vanilla unchanged);
-- set 'Y' per customer instance. Read by InvoiceNotificationDelayHandler.
INSERT INTO AD_SysConfig (
  AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,Name,Value,ConfigurationLevel,
  EntityType,IsActive,Created,CreatedBy,Updated,UpdatedBy
) VALUES (
  0,0,541810,'delayNotificationUntilShipmentConfirmedByCarrier','N','S',
  'D','Y',now(),100,now(),100
);
