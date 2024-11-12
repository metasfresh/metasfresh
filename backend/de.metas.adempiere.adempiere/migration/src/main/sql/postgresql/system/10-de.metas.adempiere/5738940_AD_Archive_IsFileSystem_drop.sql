create table backup.ad_archive_before_isfilesystem_drop as select ad_archive_id, isfilesystem from ad_archive where isfilesystem='Y';

-- Run mode: SWING_CLIENT

-- 2024-11-12T06:48:39.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=552887
;

-- Field: Archive(540176,de.metas.document.archive) -> Archiv(540483,de.metas.document.archive) -> IsFileSystem
-- Column: AD_Archive.IsFileSystem
-- 2024-11-12T06:48:39.686Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=552887
;

-- 2024-11-12T06:48:39.691Z
DELETE FROM AD_Field WHERE AD_Field_ID=552887
;

-- 2024-11-12T06:48:39.695Z
/* DDL */ SELECT public.db_alter_table('AD_Archive','ALTER TABLE AD_Archive DROP COLUMN IF EXISTS IsFileSystem')
;

-- Column: AD_Archive.IsFileSystem
-- 2024-11-12T06:48:40.004Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=548868
;

-- 2024-11-12T06:48:40.009Z
DELETE FROM AD_Column WHERE AD_Column_ID=548868
;

-- Column: AD_Archive.AD_Archive_Storage_ID
-- 2024-11-12T06:49:00.362Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-11-12 06:49:00.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589384
;

