--
-- Script: /tmp/webui_migration_scripts_2025-06-20_411820529535079317/5758260_migration_2025-06-20_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- Value: de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU
-- 2025-06-20T13:56:25.454Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545564,0,TO_TIMESTAMP('2025-06-20 13:56:25.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','The HU is not active!','I',TO_TIMESTAMP('2025-06-20 13:56:25.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU')
;

-- 2025-06-20T13:56:25.455Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545564 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU
-- 2025-06-20T13:56:50.599Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-20 13:56:50.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545564
;

-- Value: de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU
-- 2025-06-20T13:56:53.019Z
UPDATE AD_Message_Trl SET MsgText='Die HU ist nicht aktiv!',Updated=TO_TIMESTAMP('2025-06-20 13:56:53.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545564
;

-- 2025-06-20T13:56:53.019Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU
-- 2025-06-20T13:57:02.103Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-20 13:57:02.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545564
;

-- Value: de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU
-- 2025-06-20T13:57:03.164Z
UPDATE AD_Message_Trl SET MsgText='Die HU ist nicht aktiv!',Updated=TO_TIMESTAMP('2025-06-20 13:57:03.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545564
;

-- 2025-06-20T13:57:03.164Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU
-- 2025-06-20T13:57:06.499Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-20 13:57:06.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545564
;
