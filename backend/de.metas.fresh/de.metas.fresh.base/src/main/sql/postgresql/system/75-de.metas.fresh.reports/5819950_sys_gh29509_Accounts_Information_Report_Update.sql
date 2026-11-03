-- Run mode: SWING_CLIENT

-- 2026-08-24T09:09:24.737Z
UPDATE AD_Process_Para SET SeqNo=90,Updated=TO_TIMESTAMP('2026-08-24 09:09:24.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540954
;

-- 2026-08-24T09:09:34.397Z
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2026-08-24 09:09:34.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540697
;

-- 2026-08-24T09:09:41.694Z
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2026-08-24 09:09:41.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=541004
;

-- 2026-08-24T09:23:50.248Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585379,0,'Account_From_ID',TO_TIMESTAMP('2026-08-24 09:23:49.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto von','Konto von',TO_TIMESTAMP('2026-08-24 09:23:49.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-24T09:23:50.667Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585379 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-08-24T09:24:12.734Z
UPDATE AD_Element_Trl SET Description='', Help='', IsTranslated='Y', Name='Account From', PrintName='Account From',Updated=TO_TIMESTAMP('2026-08-24 09:24:12.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585379 AND AD_Language='en_US'
;

-- 2026-08-24T09:24:12.793Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-08-24T09:24:55.544Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585380,0,'Account_To_ID',TO_TIMESTAMP('2026-08-24 09:24:55.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwendetes Konto','D','Das verwendete (Standard-) Konto','Y','Konto bis','Konto bis',TO_TIMESTAMP('2026-08-24 09:24:55.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-08-24T09:24:55.730Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585380 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-08-24T09:25:15.073Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Account To', PrintName='Account To',Updated=TO_TIMESTAMP('2026-08-24 09:25:14.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585380 AND AD_Language='en_US'
;

-- 2026-08-24T09:25:15.130Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-08-24T09:26:13.207Z
UPDATE AD_Process_Para SET AD_Element_ID=585380, Name='Konto bis',Updated=TO_TIMESTAMP('2026-08-24 09:26:13.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=541004
;

-- 2026-08-24T09:26:13.327Z
UPDATE AD_Process_Para_Trl trl SET Name='Konto bis' WHERE AD_Process_Para_ID=541004 AND AD_Language='de_DE'
;

-- 2026-08-24T09:26:20.859Z
UPDATE AD_Process_Para SET AD_Element_ID=585379, Name='Konto von',Updated=TO_TIMESTAMP('2026-08-24 09:26:20.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540697
;

-- 2026-08-24T09:26:20.977Z
UPDATE AD_Process_Para_Trl trl SET Name='Konto von' WHERE AD_Process_Para_ID=540697 AND AD_Language='de_DE'
;

-- 2026-08-24T09:28:40.383Z
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2026-08-24 09:28:40.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=541004
;

