-- #3860 Delete legacy Production DocType that references removed MMP DocBaseType
-- The MMP (Material Production) DocBaseType was removed in metasfresh
-- migration 5486280_sys_gh3537-app_cleanup_legacy_production_stuff.sql (2018)
-- but the C_DocType record was not cleaned up, causing customer repo builds to fail
-- when loading DocTypes during startup.

-- Delete document action access records first (FK constraint)
DELETE FROM AD_Document_Action_Access
WHERE C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType = 'MMP');

-- Delete the DocType translations (FK constraint)
DELETE FROM C_DocType_Trl
WHERE C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType = 'MMP');

DELETE FROM c_doctype_sequence
WHERE C_DocType_ID IN (SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType = 'MMP');

-- Delete the DocType record(s)
DELETE FROM C_DocType WHERE DocBaseType = 'MMP';

-- Delete counter DocBaseType mappings that reference MMP
DELETE FROM C_DocBaseType_Counter WHERE DocBaseType = 'MMP' OR Counter_DocBaseType = 'MMP';

-- Delete period controls for MMP DocBaseType
DELETE FROM C_PeriodControl WHERE DocBaseType = 'MMP';
