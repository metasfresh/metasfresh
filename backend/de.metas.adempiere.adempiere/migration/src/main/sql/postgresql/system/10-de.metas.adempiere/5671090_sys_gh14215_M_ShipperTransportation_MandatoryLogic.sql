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

