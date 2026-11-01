-- de_DE / de_CH translations of the take-over elements and tables carry the German base text and are therefore not "translated";
-- de_CH rows with Swiss spelling (ss instead of ß) differ from the base text and stay translated.

-- ============================================================================
-- 1) AD_Element_Trl
-- ============================================================================
UPDATE AD_Element_Trl
SET IsTranslated = 'N', Updated = TO_TIMESTAMP('2026-10-06 15:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID IN (585505, 585506, 585507, 585509)
  AND AD_Language IN ('de_DE', 'de_CH')
;

UPDATE AD_Element_Trl
SET IsTranslated = 'N', Updated = TO_TIMESTAMP('2026-10-06 15:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID IN (585508, 585510)
  AND AD_Language = 'de_DE'
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585505, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585505, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585506, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585506, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585507, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585507, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585508, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585509, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585509, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585510, 'de_DE');

-- ============================================================================
-- 2) AD_Table_Trl
-- ============================================================================
UPDATE AD_Table_Trl
SET IsTranslated = 'N', Updated = TO_TIMESTAMP('2026-10-06 15:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Table_ID IN (542652, 542653)
  AND AD_Language IN ('de_DE', 'de_CH')
;
