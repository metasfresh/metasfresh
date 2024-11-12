select backup_table('ad_client');


-- Run mode: SWING_CLIENT

-- UI Element: Mandant(109,D) -> Mandant(145,D) -> main -> 20 -> flags.Archiv speichern Dateisystem
-- Column: AD_Client.StoreArchiveOnFileSystem
-- 2024-11-12T06:45:10.492Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544615
;

-- 2024-11-12T06:45:10.495Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=50184
;

-- Field: Mandant(109,D) -> Mandant(145,D) -> Store Archive On File System
-- Column: AD_Client.StoreArchiveOnFileSystem
-- 2024-11-12T06:45:10.502Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=50184
;

-- 2024-11-12T06:45:10.508Z
DELETE FROM AD_Field WHERE AD_Field_ID=50184
;

-- 2024-11-12T06:45:10.511Z
/* DDL */ SELECT public.db_alter_table('AD_Client','ALTER TABLE AD_Client DROP COLUMN IF EXISTS StoreArchiveOnFileSystem')
;

-- Column: AD_Client.StoreArchiveOnFileSystem
-- 2024-11-12T06:45:10.610Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=50214
;

-- 2024-11-12T06:45:10.617Z
DELETE FROM AD_Column WHERE AD_Column_ID=50214
;

-- UI Element: Mandant(109,D) -> Mandant(145,D) -> advanced edit -> 10 -> advanced edit.Unix Archiv Verzeichnis
-- Column: AD_Client.UnixArchivePath
-- 2024-11-12T06:45:26.010Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544617
;

-- 2024-11-12T06:45:26.013Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=50186
;

-- Field: Mandant(109,D) -> Mandant(145,D) -> Unix Archive Path
-- Column: AD_Client.UnixArchivePath
-- 2024-11-12T06:45:26.016Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=50186
;

-- 2024-11-12T06:45:26.021Z
DELETE FROM AD_Field WHERE AD_Field_ID=50186
;

-- 2024-11-12T06:45:26.025Z
/* DDL */ SELECT public.db_alter_table('AD_Client','ALTER TABLE AD_Client DROP COLUMN IF EXISTS UnixArchivePath')
;

-- Column: AD_Client.UnixArchivePath
-- 2024-11-12T06:45:26.035Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=50216
;

-- 2024-11-12T06:45:26.041Z
DELETE FROM AD_Column WHERE AD_Column_ID=50216
;

-- UI Element: Mandant(109,D) -> Mandant(145,D) -> advanced edit -> 10 -> advanced edit.Windows Archiv Verzeichnis
-- Column: AD_Client.WindowsArchivePath
-- 2024-11-12T06:45:39.161Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544616
;

-- 2024-11-12T06:45:39.164Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=50185
;

-- Field: Mandant(109,D) -> Mandant(145,D) -> Windows Archive Path
-- Column: AD_Client.WindowsArchivePath
-- 2024-11-12T06:45:39.168Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=50185
;

-- 2024-11-12T06:45:39.173Z
DELETE FROM AD_Field WHERE AD_Field_ID=50185
;

-- 2024-11-12T06:45:39.177Z
/* DDL */ SELECT public.db_alter_table('AD_Client','ALTER TABLE AD_Client DROP COLUMN IF EXISTS WindowsArchivePath')
;

-- Column: AD_Client.WindowsArchivePath
-- 2024-11-12T06:45:39.189Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=50215
;

-- 2024-11-12T06:45:39.194Z
DELETE FROM AD_Column WHERE AD_Column_ID=50215
;

