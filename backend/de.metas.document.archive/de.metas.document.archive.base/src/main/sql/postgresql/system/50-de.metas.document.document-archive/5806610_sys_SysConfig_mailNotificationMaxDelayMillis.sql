-- Integer "max delay millis" for held mail notifications: 0 = never delay (vanilla behaviour);
-- > 0 = wait at most that many milliseconds for readiness (e.g. the carrier tracking link of a
-- shipment) before sending the notification anyway. Read by the mail workpackage processor.
-- Default 0; set per instance (e.g. 60000) to enable.
INSERT INTO AD_SysConfig (
  AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,Name,Value,ConfigurationLevel,
  EntityType,IsActive,Created,CreatedBy,Updated,UpdatedBy
) VALUES (
  0,0,541812 /*From ID Server*/,'mailNotificationMaxDelayMillis','0','S',
  'D','Y',TO_TIMESTAMP('2026-06-06 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-06 10:00:00','YYYY-MM-DD HH24:MI:SS'),100
);

-- Drop the obsolete predecessor switch (system row + any client/org overrides).
DELETE FROM AD_SysConfig WHERE Name='delayNotificationUntilShipmentConfirmedByCarrier';
