-- gh#28066: Fix AD_Process field descriptions (IsLogWarning, SQLStatement)
--
-- IsLogWarning (AD_Element_ID=581085):
--   The existing description incorrectly states that RAISE NOTICE messages are ignored.
--   In reality, PostgreSQL JDBC maps both RAISE NOTICE and RAISE WARNING to java.sql.SQLWarning,
--   and ExecuteUpdateSQL captures them all via SQLUtil.extractWarningMessages().
--
-- SQLStatement (AD_Element_ID=50028):
--   Has no description at all. Add one.
--   Note: also used on AD_MigrationStep, so keep description generic.

-- =====================================================
-- 1) Fix IsLogWarning (AD_Element_ID=581085)
-- =====================================================

-- 1a) de_DE
UPDATE AD_Element_Trl
SET Name = 'Warnungen protokollieren',
    Description = 'Wenn aktiv, werden PostgreSQL-Meldungen (RAISE NOTICE / RAISE WARNING) in AD_PInstance_Log gespeichert.',
    Help = 'Wenn aktiv, werden die von der SQL-Funktion erzeugten PostgreSQL-Meldungen (RAISE NOTICE und RAISE WARNING) als Warnungen in AD_PInstance_Log protokolliert.'
     || E'\nDie Meldungen werden über die JDBC-SQLWarning-Kette erfasst und in der Spalte "Warnings" gespeichert.'
     || E'\nEinzelne Warnmeldungen sollten maximal 5000 Zeichen lang sein.',
    Updated = '2026-02-11 18:30',
    UpdatedBy = 100
WHERE AD_Element_ID = 581085 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(581085, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581085, 'de_DE');

-- 1b) en_US
UPDATE AD_Element_Trl
SET Name = 'Log Warning',
    Description = 'If enabled, PostgreSQL messages (RAISE NOTICE / RAISE WARNING) are logged to AD_PInstance_Log.',
    Help = 'If enabled, PostgreSQL messages raised by the SQL function (both RAISE NOTICE and RAISE WARNING) are captured via the JDBC SQLWarning chain and stored in the AD_PInstance_Log.Warnings column.'
     || E'\nA single warning message should contain a maximum of 5000 characters.',
    Updated = '2026-02-11 18:30',
    UpdatedBy = 100
WHERE AD_Element_ID = 581085 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(581085, 'en_US');


-- =====================================================
-- 2) Add SQLStatement description (AD_Element_ID=50028)
-- =====================================================

-- 2a) de_DE
UPDATE AD_Element_Trl
SET Description = 'SQL-Anweisung die von diesem Prozess ausgeführt wird',
    Help = 'Die SQL-Anweisung wird von ExecuteUpdateSQL ausgeführt.'
     || E'\nSie kann Kontext-Variablen enthalten (z.B. @C_BPartner_ID@), die zur Laufzeit aufgelöst werden.'
     || E'\nBei Nicht-SELECT-Anweisungen werden RAISE NOTICE / RAISE WARNING Meldungen erfasst (siehe "Warnungen protokollieren").',
    Updated = '2026-02-11 18:30',
    UpdatedBy = 100
WHERE AD_Element_ID = 50028 AND AD_Language = 'de_DE';

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50028, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50028, 'de_DE');

-- 2b) en_US
UPDATE AD_Element_Trl
SET Description = 'SQL statement to be executed by this process',
    Help = 'The SQL statement is executed by ExecuteUpdateSQL.'
     || E'\nIt may contain context variables (e.g. @C_BPartner_ID@) which are resolved at runtime.'
     || E'\nFor non-SELECT statements, RAISE NOTICE / RAISE WARNING messages are captured (see "Log Warning" flag).',
    Updated = '2026-02-11 18:30',
    UpdatedBy = 100
WHERE AD_Element_ID = 50028 AND AD_Language = 'en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50028, 'en_US');
