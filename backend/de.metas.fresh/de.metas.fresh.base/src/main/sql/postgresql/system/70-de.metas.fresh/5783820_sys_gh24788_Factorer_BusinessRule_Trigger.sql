--
-- Script: /tmp/webui_migration_scripts_2026-01-13_7088119074252610245/5783820_migration_2026-01-14_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- Name: C_BPartner_IsFactorer_BankAccount
-- 2026-01-14T18:10:26.420Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540763,'C_BPartner.IsFactorer =''N'' OR ( C_BPartner.IsFactorer =''Y'' AND EXISTS (SELECT 1 FROM C_BP_BankAccount ba WHERE ba.C_BPartner_ID = C_BPartner.C_BPartner_ID) ) ',TO_TIMESTAMP('2026-01-14 18:10:26.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','C_BPartner_IsFactorer_BankAccount','S',TO_TIMESTAMP('2026-01-14 18:10:26.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: C_BPartner_Factorer_BankAccount
-- 2026-01-14T18:12:27.504Z
UPDATE AD_Val_Rule SET Name='C_BPartner_Factorer_BankAccount',Updated=TO_TIMESTAMP('2026-01-14 18:12:27.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540763
;

-- Value: C_BPartner_Factorer_BankAccount
-- 2026-01-14T18:12:48.146Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545622,0,TO_TIMESTAMP('2026-01-14 18:12:48.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','The Factorer Business Partner should have a bank account. {}','I',TO_TIMESTAMP('2026-01-14 18:12:48.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner_Factorer_BankAccount')
;

-- 2026-01-14T18:12:48.147Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545622 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_BPartner_Factorer_BankAccount
-- 2026-01-14T18:13:30.056Z
UPDATE AD_Message SET MsgText='Der Factoring-Geschäftspartner sollte über ein Bankkonto verfügen. {}',Updated=TO_TIMESTAMP('2026-01-14 18:13:30.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545622
;

-- 2026-01-14T18:13:30.057Z
UPDATE AD_Message_Trl trl SET MsgText='Der Factoring-Geschäftspartner sollte über ein Bankkonto verfügen. {}' WHERE AD_Message_ID=545622 AND AD_Language='de_DE'
;

-- 2026-01-14T18:29:45.218Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsCreateWarningOnTarget,IsDebug,Name,Severity,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540043,0,0,291,TO_TIMESTAMP('2026-01-14 18:29:45.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','C_BPartner_Factorer_BankAccount','N',TO_TIMESTAMP('2026-01-14 18:29:45.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540763,545622)
;

-- 2026-01-14T18:30:11.960Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540043,540041,0,0,TO_TIMESTAMP('2026-01-14 18:30:11.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','N',291,'IsFactorer',TO_TIMESTAMP('2026-01-14 18:30:11.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-14T18:30:14.013Z
UPDATE AD_BusinessRule_Trigger SET OnNew='Y',Updated=TO_TIMESTAMP('2026-01-14 18:30:14.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BusinessRule_Trigger_ID=540041
;

-- 2026-01-14T18:30:15.340Z
UPDATE AD_BusinessRule_Trigger SET OnUpdate='Y',Updated=TO_TIMESTAMP('2026-01-14 18:30:15.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BusinessRule_Trigger_ID=540041
;

-- Name: C_BPartner_Factorer_BankAccount
-- 2026-01-14T18:37:59.951Z
UPDATE AD_Val_Rule SET Code='C_BPartner.IsFactorer =''N'' OR ( C_BPartner.IsFactorer =''Y'' AND EXISTS (SELECT 1 FROM C_BP_BankAccount ba WHERE ba.C_BPartner_ID = C_BPartner.C_BPartner_ID) ) ',Updated=TO_TIMESTAMP('2026-01-14 18:37:59.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540763
;

-- 2026-01-14T18:40:09.062Z
UPDATE AD_BusinessRule_Trigger SET TargetRecordMappingSQL='C_BPartner_ID',Updated=TO_TIMESTAMP('2026-01-14 18:40:09.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BusinessRule_Trigger_ID=540041
;
