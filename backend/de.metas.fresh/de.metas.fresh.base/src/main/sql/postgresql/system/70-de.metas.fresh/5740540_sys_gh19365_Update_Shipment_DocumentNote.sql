--
-- Script: /tmp/webui_migration_scripts_2024-11-26_3065392917516489212/5740540_migration_2024-11-26_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2024-11-26T10:19:22.323Z
UPDATE C_DocType SET DocumentNote='<b>Waren in einwandfreiem Zustand erhalten.</b>

<b>Unterschrift:</b>',Updated=TO_TIMESTAMP('2024-11-26 11:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000011
;

-- 2024-11-26T10:19:22.327Z
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote='<b>Waren in einwandfreiem Zustand erhalten.</b>

<b>Unterschrift:</b>', Name='Lieferschein', PrintName='Lieferschein'  WHERE C_DocType_ID=1000011 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2024-11-26T10:19:47.448Z
UPDATE C_DocType_Trl SET DocumentNote='<b>Goods received in perfect condition.</b>

<b>Signature:</b>',Updated=TO_TIMESTAMP('2024-11-26 11:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=1000011
;

