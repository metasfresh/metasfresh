--
-- Script: /tmp/webui_migration_scripts_2025-05-14_1741970523032075331/5754640_migration_2025-05-14_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI



-- 2025-06-02T06:01:31.347Z
UPDATE AD_Element_Trl trl SET Name='Quarantine Warehouse' WHERE AD_Element_ID=543932 AND AD_Language='en_US'
;

-- 2025-06-02T06:01:26.541Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'en_US')
;


-- 2025-06-02T06:01:26.519Z
UPDATE AD_Element SET Name='Quarantänelager',Updated=TO_TIMESTAMP('2025-06-02 06:01:26.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932
;

-- 2025-06-02T06:01:26.521Z
UPDATE AD_Element_Trl trl SET Name='Quarantänelager' WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:01:26.541Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- 2025-06-02T06:01:29.130Z
UPDATE AD_Element SET PrintName='Quarantänelager',Updated=TO_TIMESTAMP('2025-06-02 06:01:29.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932
;

-- 2025-06-02T06:01:29.131Z
UPDATE AD_Element_Trl trl SET PrintName='Quarantänelager' WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:01:29.132Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- 2025-06-02T06:01:31.346Z
UPDATE AD_Element SET Name='Quarantänelager',Updated=TO_TIMESTAMP('2025-06-02 06:01:31.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932
;

-- 2025-06-02T06:01:31.347Z
UPDATE AD_Element_Trl trl SET Name='Quarantänelager' WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:01:31.348Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:01:43.467Z
UPDATE AD_Element_Trl SET Name='Quarantänelager',Updated=TO_TIMESTAMP('2025-06-02 06:01:43.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2025-06-02T06:01:43.468Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:01:43.628Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:01:44.625Z
UPDATE AD_Element_Trl SET PrintName='Quarantänelager',Updated=TO_TIMESTAMP('2025-06-02 06:01:44.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2025-06-02T06:01:44.626Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:01:44.787Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:01:44.796Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-02 06:01:44.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2025-06-02T06:01:44.797Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:01:46.924Z
UPDATE AD_Element_Trl SET Name='Quarantänelager',Updated=TO_TIMESTAMP('2025-06-02 06:01:46.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2025-06-02T06:01:46.924Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:01:47.104Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:01:52.024Z
UPDATE AD_Element_Trl SET PrintName='Quarantänelager',Updated=TO_TIMESTAMP('2025-06-02 06:01:52.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2025-06-02T06:01:52.024Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:01:52.180Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:03:13.207Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-02 06:03:13.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:03:13.208Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543932,'de_DE')
;

-- 2025-06-02T06:03:13.209Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:03:21.152Z
UPDATE AD_Element_Trl SET Name='IsQuarantineWarehouse',Updated=TO_TIMESTAMP('2025-06-02 06:03:21.151000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='fr_CH'
;

-- 2025-06-02T06:03:21.152Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:03:21.324Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'fr_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:03:29.215Z
UPDATE AD_Element_Trl SET Name='Quarantine Warehouse',Updated=TO_TIMESTAMP('2025-06-02 06:03:29.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='fr_CH'
;

-- 2025-06-02T06:03:29.216Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:03:29.364Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'fr_CH')
;

-- 2025-06-02T06:03:39.960Z
UPDATE AD_Element SET Description='Flag indicating whether the warehouse is used as a quarantine warehouse.',Updated=TO_TIMESTAMP('2025-06-02 06:03:39.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932
;

-- 2025-06-02T06:03:39.961Z
UPDATE AD_Element_Trl trl SET Description='Flag indicating whether the warehouse is used as a quarantine warehouse.' WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:03:39.962Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- 2025-06-02T06:03:51.381Z
UPDATE AD_Element SET Help='A quarantine warehouse is intended for storing products that require additional quality checks or cannot be released immediately into regular inventory. When this flag is enabled (''Y''), the system treats the warehouse as a special location for isolating goods - such as received items pending inspection or returned products - until a final decision is made (e.g., acceptance, rejection, or restocking).',Updated=TO_TIMESTAMP('2025-06-02 06:03:51.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932
;

-- 2025-06-02T06:03:51.387Z
UPDATE AD_Element_Trl trl SET Help='A quarantine warehouse is intended for storing products that require additional quality checks or cannot be released immediately into regular inventory. When this flag is enabled (''Y''), the system treats the warehouse as a special location for isolating goods - such as received items pending inspection or returned products - until a final decision is made (e.g., acceptance, rejection, or restocking).' WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:03:51.388Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:04:12.242Z
UPDATE AD_Element_Trl SET Help='A quarantine warehouse is intended for storing products that require additional quality checks or cannot be released immediately into regular inventory. When this flag is enabled (''Y''), the system treats the warehouse as a special location for isolating goods - such as received items pending inspection or returned products - until a final decision is made (e.g., acceptance, rejection, or restocking).',Updated=TO_TIMESTAMP('2025-06-02 06:04:12.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='en_US'
;

-- 2025-06-02T06:04:12.242Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:04:12.395Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'en_US')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:04:20.090Z
UPDATE AD_Element_Trl SET Description='Flag indicating whether the warehouse is used as a quarantine warehouse.',Updated=TO_TIMESTAMP('2025-06-02 06:04:20.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='en_US'
;

-- 2025-06-02T06:04:20.090Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:04:20.258Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'en_US')
;


-- 2025-06-02T06:08:51.884Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:09:57.615Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-02 06:09:57.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_DE'
;

-- 2025-06-02T06:09:57.616Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543295,'de_DE')
;

-- 2025-06-02T06:09:57.617Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_DE')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:10:01.875Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-02 06:10:01.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_CH'
;

-- 2025-06-02T06:10:01.876Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_CH')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:10:03.085Z
UPDATE AD_Element_Trl SET Name='Sperrlager',Updated=TO_TIMESTAMP('2025-06-02 06:10:03.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_CH'
;

-- 2025-06-02T06:10:03.086Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:10:03.278Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_CH')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:10:04.498Z
UPDATE AD_Element_Trl SET PrintName='Sperrlager',Updated=TO_TIMESTAMP('2025-06-02 06:10:04.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_CH'
;

-- 2025-06-02T06:10:04.498Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:10:04.657Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_CH')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:10:13.807Z
UPDATE AD_Element_Trl SET Description='Indicates whether the warehouse is designated for quality-related returns.',Updated=TO_TIMESTAMP('2025-06-02 06:10:13.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='en_US'
;

-- 2025-06-02T06:10:13.808Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:10:13.982Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'en_US')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:10:29.670Z
UPDATE AD_Element_Trl SET Help='When this flag is enabled (''Y''), the warehouse is used specifically to store returned products that are under quality inspection or review. It is typically used for goods returned by customers or from internal processes that need to be assessed before being restocked, discarded, or processed further. This setting helps isolate potentially non-conforming or damaged items from the main inventory.',Updated=TO_TIMESTAMP('2025-06-02 06:10:29.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='en_US'
;

-- 2025-06-02T06:10:29.670Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:10:29.830Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'en_US')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:12:46.570Z
UPDATE AD_Element_Trl SET Description='Kennzeichnet, ob das Lager für qualitätsbezogene Rücksendungen vorgesehen ist.',Updated=TO_TIMESTAMP('2025-06-02 06:12:46.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_CH'
;

-- 2025-06-02T06:12:46.570Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:12:46.733Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_CH')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:12:56.129Z
UPDATE AD_Element_Trl SET Help='Wenn dieses Kennzeichen aktiviert ist (''Y''), wird das Lager speziell für Rücksendungen verwendet, die einer Qualitätsprüfung oder -bewertung unterliegen. Es dient in der Regel zur Zwischenlagerung von Waren, die von Kunden zurückgesendet wurden oder aus internen Prozessen stammen und noch nicht wieder dem regulären Bestand zugeführt, entsorgt oder weiterverarbeitet wurden. Diese Einstellung hilft, potenziell mangelhafte oder beschädigte Artikel vom restlichen Lagerbestand zu isolieren.',Updated=TO_TIMESTAMP('2025-06-02 06:12:56.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_CH'
;

-- 2025-06-02T06:12:56.130Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:12:56.299Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_CH')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:13:01.726Z
UPDATE AD_Element_Trl SET Help='Wenn dieses Kennzeichen aktiviert ist (''Y''), wird das Lager speziell für Rücksendungen verwendet, die einer Qualitätsprüfung oder -bewertung unterliegen. Es dient in der Regel zur Zwischenlagerung von Waren, die von Kunden zurückgesendet wurden oder aus internen Prozessen stammen und noch nicht wieder dem regulären Bestand zugeführt, entsorgt oder weiterverarbeitet wurden. Diese Einstellung hilft, potenziell mangelhafte oder beschädigte Artikel vom restlichen Lagerbestand zu isolieren.',Updated=TO_TIMESTAMP('2025-06-02 06:13:01.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_DE'
;

-- 2025-06-02T06:13:01.727Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:13:02.032Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543295,'de_DE')
;

-- 2025-06-02T06:13:02.033Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_DE')
;

-- Element: IsQualityReturnWarehouse
-- 2025-06-02T06:13:04.830Z
UPDATE AD_Element_Trl SET Description='Kennzeichnet, ob das Lager für qualitätsbezogene Rücksendungen vorgesehen ist.',Updated=TO_TIMESTAMP('2025-06-02 06:13:04.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543295 AND AD_Language='de_DE'
;

-- 2025-06-02T06:13:04.830Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:13:05.153Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543295,'de_DE')
;

-- 2025-06-02T06:13:05.154Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543295,'de_DE')
;


---


-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:23:22.850Z
UPDATE AD_Element_Trl SET Description='Kennzeichen, ob das Lager als Quarantänelager verwendet wird.',Updated=TO_TIMESTAMP('2025-06-02 06:23:22.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2025-06-02T06:23:22.851Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:23:23.073Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:23:30.779Z
UPDATE AD_Element_Trl SET Help='Ein Quarantänelager dient zur Lagerung von Produkten, die zusätzlichen Qualitätsprüfungen unterzogen werden müssen oder nicht sofort in den regulären Bestand überführt werden dürfen. Wenn dieses Kennzeichen aktiviert ist ('Y'), behandelt das System das Lager als speziellen Ort zur Isolierung von Waren – beispielsweise für eingehende Artikel, die geprüft werden müssen, oder Rücksendungen – bis eine endgültige Entscheidung getroffen wurde (z. B. Annahme, Ablehnung oder Wiedereinlagerung).',Updated=TO_TIMESTAMP('2025-06-02 06:23:30.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_CH'
;

-- 2025-06-02T06:23:30.784Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:23:30.980Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_CH')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:23:36.386Z
UPDATE AD_Element_Trl SET Help='Ein Quarantänelager dient zur Lagerung von Produkten, die zusätzlichen Qualitätsprüfungen unterzogen werden müssen oder nicht sofort in den regulären Bestand überführt werden dürfen. Wenn dieses Kennzeichen aktiviert ist ('Y'), behandelt das System das Lager als speziellen Ort zur Isolierung von Waren – beispielsweise für eingehende Artikel, die geprüft werden müssen, oder Rücksendungen – bis eine endgültige Entscheidung getroffen wurde (z. B. Annahme, Ablehnung oder Wiedereinlagerung).',Updated=TO_TIMESTAMP('2025-06-02 06:23:36.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:23:36.387Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:23:36.709Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543932,'de_DE')
;

-- 2025-06-02T06:23:36.709Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;

-- Element: IsQuarantineWarehouse
-- 2025-06-02T06:23:42.313Z
UPDATE AD_Element_Trl SET Description='Kennzeichen, ob das Lager als Quarantänelager verwendet wird.',Updated=TO_TIMESTAMP('2025-06-02 06:23:42.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543932 AND AD_Language='de_DE'
;

-- 2025-06-02T06:23:42.314Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T06:23:42.649Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543932,'de_DE')
;

-- 2025-06-02T06:23:42.649Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543932,'de_DE')
;
