CREATE TABLE backup.DHL_ShipmentOrder_gh20631_20240417
AS
SELECT * FROM DHL_ShipmentOrder;

UPDATE DHL_ShipmentOrder
SET M_Package_ID = PackageId
WHERE PackageId > 0
;

-- Field: DHL Versandauftrag -> DHL_ShipmetnOrder -> Packstück
-- Column: DHL_ShipmentOrder.M_Package_ID
-- Field: DHL Versandauftrag(540743,D) -> DHL_ShipmetnOrder(542067,D) -> Packstück
-- Column: DHL_ShipmentOrder.M_Package_ID
-- 2025-04-16T13:23:21.280Z
UPDATE AD_Field SET AD_Column_ID=589907, Description='Shipment Package', Help='A Shipment can have one or more Packages.  A Package may be individually tracked.', Name='Packstück',Updated=TO_TIMESTAMP('2025-04-16 13:23:21.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=589633
;

-- 2025-04-16T13:23:21.331Z
UPDATE AD_Field_Trl trl SET Description='Shipment Package',Help='A Shipment can have one or more Packages.  A Package may be individually tracked.',Name='Packstück' WHERE AD_Field_ID=589633 AND AD_Language='de_DE'
;

-- 2025-04-16T13:23:28.222Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2410) 
;

-- 2025-04-16T13:23:28.286Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589633
;

-- 2025-04-16T13:23:28.339Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(589633)
;

-- Column: DHL_ShipmentOrder.PackageId
-- Column: DHL_ShipmentOrder.PackageId
-- 2025-04-16T13:26:05.543Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569089
;

-- 2025-04-16T13:26:05.853Z
DELETE FROM AD_Column WHERE AD_Column_ID=569089
;

ALTER TABLE DHL_ShipmentOrder
    DROP COLUMN PackageId
;
