DELETE FROM ExternalSystem_Config_ProCareManagement_LocalFile;

-- UI Element: Externe System Konfiguration PCM -> Lokale Datei.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-29T14:25:57.454Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=623785
;

-- 2024-03-29T14:25:57.478Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726617
;

-- Field: Externe System Konfiguration PCM -> Lokale Datei -> Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-29T14:25:57.498Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=726617
;

-- 2024-03-29T14:25:57.505Z
DELETE FROM AD_Field WHERE AD_Field_ID=726617
;

-- 2024-03-29T14:25:57.583Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE ExternalSystem_Config_ProCareManagement_LocalFile DROP COLUMN IF EXISTS ProcessedDirectory')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ProcessedDirectory
-- 2024-03-29T14:25:57.672Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588034
;

-- 2024-03-29T14:25:57.678Z
DELETE FROM AD_Column WHERE AD_Column_ID=588034
;

-- UI Element: Externe System Konfiguration PCM -> Lokale Datei.Fehler-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-29T14:26:12.898Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=623786
;

-- 2024-03-29T14:26:12.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726618
;

-- Field: Externe System Konfiguration PCM -> Lokale Datei -> Fehler-Verzeichnis
-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-29T14:26:12.903Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=726618
;

-- 2024-03-29T14:26:12.907Z
DELETE FROM AD_Field WHERE AD_Field_ID=726618
;

-- 2024-03-29T14:26:12.909Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ProCareManagement_LocalFile','ALTER TABLE ExternalSystem_Config_ProCareManagement_LocalFile DROP COLUMN IF EXISTS ErroredDirectory')
;

-- Column: ExternalSystem_Config_ProCareManagement_LocalFile.ErroredDirectory
-- 2024-03-29T14:26:12.920Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588035
;

-- 2024-03-29T14:26:12.923Z
DELETE FROM AD_Column WHERE AD_Column_ID=588035
;

-- 2024-03-29T14:30:02.848Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=583039
;

-- 2024-03-29T14:30:02.867Z
DELETE FROM AD_Element WHERE AD_Element_ID=583039
;

-- 2024-03-29T14:30:27.884Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=583040
;

-- 2024-03-29T14:30:27.886Z
DELETE FROM AD_Element WHERE AD_Element_ID=583040
;
