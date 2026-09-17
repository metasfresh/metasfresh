-- Run mode: SWING_CLIENT

-- me03#30198: Fix Limit and Offset descriptions for Available_For_Sales_JSON (AD_Process_ID=585498).
-- Migration 5806240 set descriptions directly on AD_Process_Para (wrong for IsCentrallyMaintained='Y').
-- This migration corrects them via AD_Element so they propagate to all linked records.

-- ============================================================
-- Limit (AD_Element_ID=543188)
-- ============================================================

-- Element: Limit
-- 2026-06-04T19:02:47.215Z
UPDATE AD_Element_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000',Updated=TO_TIMESTAMP('2026-06-04 19:02:47.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543188 AND AD_Language='de_DE'
;

-- 2026-06-04T19:02:47.217Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-04T19:02:47.596Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543188,'de_DE')
;

-- 2026-06-04T19:02:47.597Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543188,'de_DE')
;

-- Element: Limit
-- 2026-06-04T19:02:48.000Z
UPDATE AD_Element_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000',Updated=TO_TIMESTAMP('2026-06-04 19:02:48.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543188 AND AD_Language='de_CH'
;

-- 2026-06-04T19:02:48.001Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-04T19:02:48.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543188,'de_CH')
;

-- Element: Limit
-- 2026-06-04T19:02:49.000Z
UPDATE AD_Element_Trl SET Description='Maximum number of records to return. Default and maximum: 2000',Updated=TO_TIMESTAMP('2026-06-04 19:02:49.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543188 AND AD_Language='en_US'
;

-- 2026-06-04T19:02:49.001Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-04T19:02:49.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543188,'en_US')
;


-- ============================================================
-- Offset (AD_Element_ID=576802)
-- ============================================================

-- Element: Offset
-- 2026-06-04T19:03:00.000Z
UPDATE AD_Element_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0',Updated=TO_TIMESTAMP('2026-06-04 19:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=576802 AND AD_Language='de_DE'
;

-- 2026-06-04T19:03:00.002Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-04T19:03:00.381Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(576802,'de_DE')
;

-- 2026-06-04T19:03:00.382Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576802,'de_DE')
;

-- Element: Offset
-- 2026-06-04T19:03:01.000Z
UPDATE AD_Element_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0',Updated=TO_TIMESTAMP('2026-06-04 19:03:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=576802 AND AD_Language='de_CH'
;

-- 2026-06-04T19:03:01.001Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-04T19:03:01.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576802,'de_CH')
;

-- Element: Offset
-- 2026-06-04T19:03:02.000Z
UPDATE AD_Element_Trl SET Description='Number of records to skip for pagination. Default: 0',Updated=TO_TIMESTAMP('2026-06-04 19:03:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=576802 AND AD_Language='en_US'
;

-- 2026-06-04T19:03:02.001Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-04T19:03:02.166Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576802,'en_US')
;
