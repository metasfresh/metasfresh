DROP TABLE IF EXISTS backup.MobileUI_UserProfile_Picking_BKP20250910
;

DROP TABLE IF EXISTS backup.MobileUI_UserProfile_Picking_Job_BKP20250910
;

DELETE
FROM backup.backup_tables
WHERE backup_table_name IN (
                            'backup.MobileUI_UserProfile_Picking_BKP20250910',
                            'backup.MobileUI_UserProfile_Picking_Job_BKP20250910'
    )
;

DO
$$
    BEGIN
        PERFORM backup_table(
                p_TableName => 'MobileUI_UserProfile_Picking',
                p_BackupTableName => 'MobileUI_UserProfile_Picking_BKP20250910'
                );

        PERFORM backup_table(
                p_TableName => 'MobileUI_UserProfile_Picking_Job',
                p_BackupTableName => 'MobileUI_UserProfile_Picking_Job_BKP20250910'
                );
    END;
$$
;


UPDATE MobileUI_UserProfile_Picking
SET allowpicktostructure_lu_tu = (CASE WHEN ispickingwithnewlu = 'Y' THEN 'Y' ELSE 'N' END),
    allowpicktostructure_tu    = (CASE WHEN ispickingwithnewlu = 'N' AND isallownewtu = 'Y' THEN 'Y' ELSE 'N' END)
;

UPDATE MobileUI_UserProfile_Picking_Job j
SET allowpicktostructure_lu_tu = (CASE WHEN ispickingwithnewlu = 'Y' THEN 'Y' ELSE 'N' END),
    allowpicktostructure_tu    = (CASE WHEN ispickingwithnewlu = 'N' AND isallownewtu = 'Y' THEN 'Y' ELSE 'N' END)
WHERE NOT EXISTS (SELECT 1
                  FROM mobileui_userprofile_picking p
                  WHERE p.isactive = 'Y'
                    AND p.ispickingwithnewlu = j.ispickingwithnewlu
                    AND p.isallownewtu = j.isallownewtu)
;


--
-- Check
/*
SELECT name, allowpicktostructure_lu_tu, allowpicktostructure_tu, ispickingwithnewlu, isallownewtu, allowpicktostructure_lu_cu, allowpicktostructure_cu
FROM MobileUI_UserProfile_Picking
;

SELECT name, allowpicktostructure_lu_tu, allowpicktostructure_tu, ispickingwithnewlu, isallownewtu, allowpicktostructure_lu_cu, allowpicktostructure_cu
FROM mobileui_userprofile_picking_job
;
*/