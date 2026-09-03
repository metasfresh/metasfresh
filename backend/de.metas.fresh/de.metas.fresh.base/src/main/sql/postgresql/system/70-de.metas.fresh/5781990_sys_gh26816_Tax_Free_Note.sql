--
-- Script: /tmp/webui_migration_scripts_2025-12-18_7613510093622480088/5781990_migration_2025-12-18_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- 2025-12-18T12:25:22.454Z
INSERT INTO AD_BoilerPlate (AD_BoilerPlate_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Name,Subject,TextSnippet,Updated,UpdatedBy) VALUES (540034,1000000,1000000,TO_TIMESTAMP('2025-12-18 12:25:22.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Tax-free','Steuerfreie innergemeinschaftliche Lieferung','Steuerfreie innergemeinschaftliche Lieferung',TO_TIMESTAMP('2025-12-18 12:25:22.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T12:25:22.455Z
INSERT INTO AD_BoilerPlate_Trl (AD_Language,AD_BoilerPlate_ID, TextSnippet, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_BoilerPlate_ID, t.TextSnippet, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_BoilerPlate t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_BoilerPlate_ID=540034 AND NOT EXISTS (SELECT 1 FROM AD_BoilerPlate_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_BoilerPlate_ID=t.AD_BoilerPlate_ID)
;

-- 2025-12-18T12:25:22.455Z
DELETE FROM AD_BoilerPlate_Ref WHERE AD_BoilerPlate_ID=540034
;

-- 2025-12-18T12:25:43.649Z
UPDATE AD_BoilerPlate_Trl SET TextSnippet='tax-free intra-Community supply',Updated=TO_TIMESTAMP('2025-12-18 12:25:43.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BoilerPlate_ID=540034 AND AD_Language='en_US'
;

-- 2025-12-18T12:25:43.650Z
UPDATE AD_BoilerPlate base SET TextSnippet=trl.TextSnippet, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_BoilerPlate_Trl trl  WHERE trl.AD_BoilerPlate_ID=base.AD_BoilerPlate_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-18T13:05:57.691Z
UPDATE AD_BoilerPlate_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-12-18 13:05:57.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BoilerPlate_ID=540034 AND AD_Language='en_US'
;
