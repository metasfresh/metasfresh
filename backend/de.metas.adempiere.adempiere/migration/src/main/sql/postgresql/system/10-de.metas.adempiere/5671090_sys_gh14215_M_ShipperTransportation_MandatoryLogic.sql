-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Forwarder
-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-09T17:14:05.840Z
UPDATE AD_Field SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-09 19:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710121
;

-- Column: M_ShipperTransportation.M_Shipper_ID
-- 2023-01-09T17:18:27.596Z
UPDATE AD_Column SET MandatoryLogic='@M_Forwarder_ID/0@!0',Updated=TO_TIMESTAMP('2023-01-09 19:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550685
;

-- 2023-01-09T17:18:32.036Z
INSERT INTO t_alter_column values('m_shippertransportation','M_Shipper_ID','NUMERIC(10)',null,null)
;

-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-10T09:32:14.079Z
UPDATE AD_Column SET MandatoryLogic='@M_Shipper_ID/0@!0',Updated=TO_TIMESTAMP('2023-01-10 11:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585444
;




-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-12T14:39:22.855Z
UPDATE AD_Column SET MandatoryLogic='@M_Shipper_ID/0@=0',Updated=TO_TIMESTAMP('2023-01-12 16:39:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585444
;

-- Column: M_ShipperTransportation.M_Shipper_ID
-- 2023-01-12T14:39:36.858Z
UPDATE AD_Column SET MandatoryLogic='@M_Forwarder_ID/0@=0',Updated=TO_TIMESTAMP('2023-01-12 16:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550685
;



-- Column: M_ShipperTransportation.M_Shipper_ID
-- 2023-01-12T14:47:43.072Z
UPDATE AD_Column SET TechnicalNote='M_Shipper_ID is mandatory if the M_Forwarder_ID was not set.',Updated=TO_TIMESTAMP('2023-01-12 16:47:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550685
;

-- 2023-01-12T14:47:51.383Z
INSERT INTO t_alter_column values('m_shippertransportation','M_Shipper_ID','NUMERIC(10)',null,null)
;

-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-12T14:48:26.345Z
UPDATE AD_Column SET IsMandatory='Y', TechnicalNote='M_Forwarder_ID is mandatory if the M_Shipper_ID was not set.',Updated=TO_TIMESTAMP('2023-01-12 16:48:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585444
;

-- Column: M_ShipperTransportation.M_Forwarder_ID
-- 2023-01-12T14:48:41.089Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-01-12 16:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585444
;

-- 2023-01-12T14:48:47.138Z
INSERT INTO t_alter_column values('m_shippertransportation','M_Forwarder_ID','NUMERIC(10)',null,null)
;

-- 2023-01-12T14:49:19.006Z
INSERT INTO t_alter_column values('m_shippertransportation','M_Shipper_ID','NUMERIC(10)',null,null)
;

-- Column: M_ShipperTransportation.M_Shipper_ID
-- 2023-01-12T14:49:25.931Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-01-12 16:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550685
;

-- 2023-01-12T14:49:29.871Z
INSERT INTO t_alter_column values('m_shippertransportation','M_Shipper_ID','NUMERIC(10)',null,null)
;

-- 2023-01-12T14:49:29.903Z
INSERT INTO t_alter_column values('m_shippertransportation','M_Shipper_ID',null,'NULL',null)
;


