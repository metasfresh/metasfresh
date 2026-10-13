-- Migration: Register scanGRAI action for HU Manager mobile app
-- Purpose: Enables the "Scan GRAI" button in the HU Manager mobile UI
-- Issue: me03#28474

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_tables WHERE tablename = 'mobile_application_action') THEN
        INSERT INTO Mobile_Application_Action
        (AD_Client_ID, AD_Org_ID,
         Mobile_Application_Action_ID, Mobile_Application_ID,
         InternalName, IsActive,
         Created, CreatedBy, Updated, UpdatedBy)
        VALUES
        (0, 0,
         540007, 540005,
         'scanGRAI', 'Y',
         TO_TIMESTAMP('2026-03-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
         TO_TIMESTAMP('2026-03-03 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100);
    ELSE
        RAISE NOTICE 'Mobile_Application_Action table does not exist yet, skipping scanGRAI action registration';
    END IF;
END $$;
