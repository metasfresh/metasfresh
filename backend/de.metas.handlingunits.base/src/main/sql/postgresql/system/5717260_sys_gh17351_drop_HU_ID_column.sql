-- 2024-02-14T14:14:21.101Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=679029
;

-- Field: HU QR Code -> HU QR Code -> Handling Unit
-- Column: M_HU_QRCode.M_HU_ID
-- Field: HU QR Code(541422,de.metas.handlingunits) -> HU QR Code(545387,de.metas.handlingunits) -> Handling Unit
-- Column: M_HU_QRCode.M_HU_ID
-- 2024-02-14T14:14:21.109Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=679029
;

-- 2024-02-14T14:14:21.131Z
DELETE FROM AD_Field WHERE AD_Field_ID=679029
;

-- 2024-02-14T14:14:21.136Z
/* DDL */ SELECT public.db_alter_table('M_HU_QRCode','ALTER TABLE M_HU_QRCode DROP COLUMN IF EXISTS M_HU_ID')
;

-- Column: M_HU_QRCode.M_HU_ID
-- Column: M_HU_QRCode.M_HU_ID
-- 2024-02-14T14:14:21.199Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=579140
;

-- 2024-02-14T14:14:21.202Z
DELETE FROM AD_Column WHERE AD_Column_ID=579140
;

