UPDATE modcntr_settings
SET M_Raw_Product_ID = M_Product_ID
;

COMMIT
;

-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-04-01T12:24:29.896Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-01 15:24:29.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588085
;

-- 2024-04-01T12:24:46.186Z
INSERT INTO t_alter_column values('modcntr_settings','M_Raw_Product_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T12:24:46.190Z
INSERT INTO t_alter_column values('modcntr_settings','M_Raw_Product_ID',null,'NOT NULL',null)
;

-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-04-01T12:24:29.896Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-01 15:24:29.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588085
;

-- 2024-04-01T12:24:46.186Z
INSERT INTO t_alter_column values('modcntr_settings','M_Raw_Product_ID','NUMERIC(10)',null,null)
;

-- 2024-04-01T12:24:46.190Z
INSERT INTO t_alter_column values('modcntr_settings','M_Raw_Product_ID',null,'NOT NULL',null)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: ModCntr_Settings.M_Product_ID
-- 2024-04-01T12:26:19.586Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617985
;

-- 2024-04-01T12:26:19.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716341
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Produkt
-- Column: ModCntr_Settings.M_Product_ID
-- 2024-04-01T12:26:19.601Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716341
;

-- 2024-04-01T12:26:19.603Z
DELETE FROM AD_Field WHERE AD_Field_ID=716341
;

-- 2024-04-01T12:26:19.605Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE ModCntr_Settings DROP COLUMN IF EXISTS M_Product_ID')
;

-- Column: ModCntr_Settings.M_Product_ID
-- 2024-04-01T12:26:19.623Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586790
;

-- 2024-04-01T12:26:19.626Z
DELETE FROM AD_Column WHERE AD_Column_ID=586790
;
