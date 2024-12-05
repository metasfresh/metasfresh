
-- 2024-12-03T12:19:18.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734051
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Automatische Bausteine(547697,de.metas.contracts) -> Verarbeitet
-- Column: ModCntr_Module.Processed
-- 2024-12-03T12:19:18.411Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=734051
;

-- 2024-12-03T12:19:18.414Z
DELETE FROM AD_Field WHERE AD_Field_ID=734051
;

-- 2024-12-03T12:19:43.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728702
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547014,de.metas.contracts) -> Verarbeitet
-- Column: ModCntr_Module.Processed
-- 2024-12-03T12:19:43.726Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728702
;

-- 2024-12-03T12:19:43.729Z
DELETE FROM AD_Field WHERE AD_Field_ID=728702
;

-- Column: ModCntr_Module.Processed
-- 2024-12-03T12:20:05.368Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588257
;

-- 2024-12-03T12:20:05.372Z
DELETE FROM AD_Column WHERE AD_Column_ID=588257
;

ALTER TABLE modcntr_module DROP COLUMN processed
;
