-- #3860 Delete legacy Production DocType that references removed MMP DocBaseType
-- The MMP (Material Production) DocBaseType was removed in metasfresh
-- migration 5486280_sys_gh3537-app_cleanup_legacy_production_stuff.sql (2018)
-- but the C_DocType record was not cleaned up, causing customer repo builds to fail
-- when loading DocTypes during startup.

-- Delete the DocType translations first (FK constraint)
DELETE FROM C_DocType_Trl
WHERE C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType = 'MMP');

-- Delete the DocType record(s)
DELETE FROM C_DocType WHERE DocBaseType = 'MMP';
