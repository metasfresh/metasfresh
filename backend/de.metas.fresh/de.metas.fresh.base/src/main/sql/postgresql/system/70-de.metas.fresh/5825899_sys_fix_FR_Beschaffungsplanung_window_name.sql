-- Fixes failure of: 5825900_sys_gh32176_AD_Element_Trl_FR_Update.sql
--
-- 5825900 assigns the French name 'Planification des achats' to AD_Element 574315
-- (window "Bestelldisposition Neu", C_PurchaseCandidate). That French name is already held by
-- AD_Element 574280 ("Procurement Planning" / Beschaffungsplanung, window 540285), set by the
-- earlier FR import. On a DB where window 540285 carries it (e.g. soft_panda_uat), applying 5825900
-- makes a second window take the same name, and the unique index ad_window_trl_name_uc
-- (Name, AD_Language) rejects two windows with the same French name at COMMIT, aborting the run.
--
-- This sub-slot runs BEFORE 5825900 and re-translates 574280 to 'Planification de l'approvisionnement'
-- (the faithful French for "Procurement Planning"), freeing 'Planification des achats' for 574315.

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Planification de l''approvisionnement', Updated=TO_TIMESTAMP('2026-10-14 00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=574280 AND AD_Language='fr_FR';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(574280,'fr_FR');
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Planification de l''approvisionnement', Updated=TO_TIMESTAMP('2026-10-14 00:00:00.000001','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=574280 AND AD_Language='fr_CH';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(574280,'fr_CH');
