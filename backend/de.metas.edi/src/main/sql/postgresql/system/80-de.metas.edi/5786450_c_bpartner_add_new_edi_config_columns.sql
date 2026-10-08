-- Run mode: SWING_CLIENT

-- Name: EDISendingMode
-- 2026-02-03T07:25:01.135Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542047,TO_TIMESTAMP('2026-02-03 07:25:00.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','EDISendingMode',TO_TIMESTAMP('2026-02-03 07:25:00.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2026-02-03T07:25:01.150Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542047 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: EDISendingMode
-- Value: R
-- ValueName: ReplicationInterface
-- 2026-02-03T07:26:14.588Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542047,544115,TO_TIMESTAMP('2026-02-03 07:26:14.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI über Replikationsschnittstelle senden (Legacy)','D','Y','Repl. Schnittstelle',TO_TIMESTAMP('2026-02-03 07:26:14.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'R','ReplicationInterface')
;

-- 2026-02-03T07:26:14.601Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544115 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: EDISendingMode -> R_ReplicationInterface
-- 2026-02-03T07:26:57.416Z
UPDATE AD_Ref_List_Trl SET Description='Send EDI via replication interface (legacy)', IsTranslated='Y', Name='Repl. Interface',Updated=TO_TIMESTAMP('2026-02-03 07:26:57.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544115
;

-- 2026-02-03T07:26:57.418Z
UPDATE AD_Ref_List base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: EDISendingMode -> R_ReplicationInterface
-- 2026-02-03T07:27:03.822Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 07:27:03.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544115
;

-- Reference Item: EDISendingMode -> R_ReplicationInterface
-- 2026-02-03T07:27:09.716Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 07:27:09.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544115
;

-- Reference: EDISendingMode
-- Value: E
-- ValueName: ExternalSystem
-- 2026-02-03T07:27:54.815Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542047,544116,TO_TIMESTAMP('2026-02-03 07:27:54.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI über externes System senden. Die Konfiguration des externen Systems entscheidet, ob manuell über einen Prozess oder automatisch nach Abschluss.','D','Y','Externes System',TO_TIMESTAMP('2026-02-03 07:27:54.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'E','ExternalSystem')
;

-- 2026-02-03T07:27:54.820Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544116 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: EDISendingMode -> E_ExternalSystem
-- 2026-02-03T07:28:44.988Z
UPDATE AD_Ref_List_Trl SET Description='Send EDI via external system. The external system config decides if manually via process or automatically on complete', IsTranslated='Y', Name='External System',Updated=TO_TIMESTAMP('2026-02-03 07:28:44.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544116
;

-- 2026-02-03T07:28:44.989Z
UPDATE AD_Ref_List base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: EDISendingMode -> E_ExternalSystem
-- 2026-02-03T07:28:49.557Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 07:28:49.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544116
;

-- Reference Item: EDISendingMode -> E_ExternalSystem
-- 2026-02-03T07:28:50.629Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 07:28:50.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544116
;

-- 2026-02-03T08:08:56.983Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584485,0,'EdiDESADVSendingMode',TO_TIMESTAMP('2026-02-03 08:08:56.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','EDI-DESADV Sendemodus ','EDI-DESADV Sendemodus ',TO_TIMESTAMP('2026-02-03 08:08:56.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-03T08:08:56.998Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584485 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EdiDESADVSendingMode
-- 2026-02-03T08:09:32.316Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-DESADV Sending Mode', PrintName='EDI-DESADV Sending Mode',Updated=TO_TIMESTAMP('2026-02-03 08:09:32.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584485 AND AD_Language='en_US'
;

-- 2026-02-03T08:09:32.318Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-03T08:09:32.591Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584485,'en_US')
;

-- Element: EdiDESADVSendingMode
-- 2026-02-03T08:09:37.555Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-DESADV Sendemodus', PrintName='EDI-DESADV Sendemodus',Updated=TO_TIMESTAMP('2026-02-03 08:09:37.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584485 AND AD_Language='de_CH'
;

-- 2026-02-03T08:09:37.555Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-03T08:09:37.718Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584485,'de_CH')
;

-- Element: EdiDESADVSendingMode
-- 2026-02-03T08:09:45.866Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-DESADV Sendemodus', PrintName='EDI-DESADV Sendemodus',Updated=TO_TIMESTAMP('2026-02-03 08:09:45.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584485 AND AD_Language='de_DE'
;

-- 2026-02-03T08:09:45.867Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-03T08:09:46.950Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584485,'de_DE')
;

-- 2026-02-03T08:09:46.951Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584485,'de_DE')
;

-- 2026-02-03T08:10:59.832Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584486,0,'EdiINVOICSendingMode',TO_TIMESTAMP('2026-02-03 08:10:59.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','EDI-INVOIC Sendemodus','EDI-INVOIC Sendemodus',TO_TIMESTAMP('2026-02-03 08:10:59.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-03T08:10:59.838Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584486 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EdiINVOICSendingMode
-- 2026-02-03T08:11:26.397Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-INVOICSending Mode', PrintName='EDI-INVOIC Sending Mode',Updated=TO_TIMESTAMP('2026-02-03 08:11:26.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584486 AND AD_Language='en_US'
;

-- 2026-02-03T08:11:26.398Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-03T08:11:26.559Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584486,'en_US')
;

-- Element: EdiINVOICSendingMode
-- 2026-02-03T08:11:27.476Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 08:11:27.476000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584486 AND AD_Language='de_CH'
;

-- 2026-02-03T08:11:27.478Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584486,'de_CH')
;

-- Element: EdiINVOICSendingMode
-- 2026-02-03T08:11:29.224Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 08:11:29.224000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584486 AND AD_Language='de_DE'
;

-- 2026-02-03T08:11:29.227Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584486,'de_DE')
;

-- 2026-02-03T08:11:29.230Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584486,'de_DE')
;

-- 2026-02-03T08:13:50.535Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584487,0,'EdiINVOIC_ExternalSystem_Config_ID',TO_TIMESTAMP('2026-02-03 08:13:50.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','EDI-INVOIC Externes System Config','EDI-INVOIC Externes System Config',TO_TIMESTAMP('2026-02-03 08:13:50.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-03T08:13:50.542Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584487 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EdiINVOIC_ExternalSystem_Config_ID
-- 2026-02-03T08:14:16.088Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-INVOIC External System Config', PrintName='EDI-INVOIC External System Config',Updated=TO_TIMESTAMP('2026-02-03 08:14:16.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584487 AND AD_Language='en_US'
;

-- 2026-02-03T08:14:16.090Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-03T08:14:16.279Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584487,'en_US')
;

-- Element: EdiINVOIC_ExternalSystem_Config_ID
-- 2026-02-03T08:14:18.167Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 08:14:18.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584487 AND AD_Language='de_CH'
;

-- 2026-02-03T08:14:18.169Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584487,'de_CH')
;

-- Element: EdiINVOIC_ExternalSystem_Config_ID
-- 2026-02-03T08:14:19.682Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 08:14:19.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584487 AND AD_Language='de_DE'
;

-- 2026-02-03T08:14:19.686Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584487,'de_DE')
;

-- 2026-02-03T08:14:19.687Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584487,'de_DE')
;

-- 2026-02-03T08:15:46.279Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584488,0,'EdiDESADV_ExternalSystem_Config_ID',TO_TIMESTAMP('2026-02-03 08:15:46.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','EDI-DESADV Externes System Config','EDI-DESADV Externes System Config',TO_TIMESTAMP('2026-02-03 08:15:46.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-03T08:15:46.282Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584488 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-03T08:16:11.080Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI-DESADV External System Config', PrintName='EDI-DESADV External System Config',Updated=TO_TIMESTAMP('2026-02-03 08:16:11.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584488 AND AD_Language='en_US'
;

-- 2026-02-03T08:16:11.082Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-03T08:16:11.249Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584488,'en_US')
;

-- Element: EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-03T08:16:11.930Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 08:16:11.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584488 AND AD_Language='de_CH'
;

-- 2026-02-03T08:16:11.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584488,'de_CH')
;

-- Element: EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-03T08:16:13.446Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-03 08:16:13.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584488 AND AD_Language='de_DE'
;

-- 2026-02-03T08:16:13.448Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584488,'de_DE')
;

-- 2026-02-03T08:16:13.450Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584488,'de_DE')
;

-- Column: C_BPartner.EdiDESADVSendingMode
-- 2026-02-03T08:49:29.515Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591909,584485,0,17,542047,291,'XX','EdiDESADVSendingMode',TO_TIMESTAMP('2026-02-03 08:49:29.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','R','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'EDI-DESADV Sendemodus',0,0,TO_TIMESTAMP('2026-02-03 08:49:29.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-03T08:49:29.531Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591909 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-03T08:49:29.545Z
/* DDL */  select update_Column_Translation_From_AD_Element(584485)
;

-- 2026-02-03T08:49:37.718Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN EdiDESADVSendingMode CHAR(1) DEFAULT ''R'' NOT NULL')
;

-- Name: Externalsystem_Config Type ScriptedExportConversion
-- 2026-02-03T08:55:14.154Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540768,'ExternalSystem_Config_ID IN (SELECT ExternalSystem_Config_ID FROM ExternalSystem_Config WHERE ExternalSystem_ID=540056 /*ScriptedExportConversion*/)',TO_TIMESTAMP('2026-02-03 08:55:14.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Externalsystem_Config Type ScriptedExportConversion','S',TO_TIMESTAMP('2026-02-03 08:55:14.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_BPartner.EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-03T09:07:54.094Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591910,584488,0,18,541268,291,540768,'XX','EdiDESADV_ExternalSystem_Config_ID',TO_TIMESTAMP('2026-02-03 09:07:53.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@EdiDESADVSendingMode@=''E''',0,'EDI-DESADV Externes System Config',0,0,TO_TIMESTAMP('2026-02-03 09:07:53.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-03T09:07:54.101Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591910 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-03T09:07:54.108Z
/* DDL */  select update_Column_Translation_From_AD_Element(584488)
;

-- 2026-02-03T09:07:57.771Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN EdiDESADV_ExternalSystem_Config_ID NUMERIC(10)')
;

-- 2026-02-03T09:07:58.734Z
ALTER TABLE C_BPartner ADD CONSTRAINT EdiDESADVExternalSystemConfig_CBPartner FOREIGN KEY (EdiDESADV_ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BPartner.EdiINVOICSendingMode
-- 2026-02-03T09:29:30.952Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591911,584486,0,17,542047,291,'XX','EdiINVOICSendingMode',TO_TIMESTAMP('2026-02-03 09:29:30.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','R','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'EDI-INVOIC Sendemodus',0,0,TO_TIMESTAMP('2026-02-03 09:29:30.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-03T09:29:30.955Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591911 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-03T09:29:31.056Z
/* DDL */  select update_Column_Translation_From_AD_Element(584486)
;

-- 2026-02-03T09:29:34.664Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN EdiINVOICSendingMode CHAR(1) DEFAULT ''R'' NOT NULL')
;

-- Column: C_BPartner.EdiINVOIC_ExternalSystem_Config_ID
-- 2026-02-03T09:31:24.008Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591912,584487,0,18,541268,291,540768,'XX','EdiINVOIC_ExternalSystem_Config_ID',TO_TIMESTAMP('2026-02-03 09:31:23.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@EdiINVOICSendingMode@=''E''',0,'EDI-INVOIC Externes System Config',0,0,TO_TIMESTAMP('2026-02-03 09:31:23.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-03T09:31:24.019Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591912 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-03T09:31:24.232Z
/* DDL */  select update_Column_Translation_From_AD_Element(584487)
;

-- 2026-02-03T09:31:25.742Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN EdiINVOIC_ExternalSystem_Config_ID NUMERIC(10)')
;

-- 2026-02-03T09:31:28.786Z
ALTER TABLE C_BPartner ADD CONSTRAINT EdiINVOICExternalSystemConfig_CBPartner FOREIGN KEY (EdiINVOIC_ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- Name: EDISendingMode
-- 2026-02-03T09:32:09.555Z
UPDATE AD_Reference SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2026-02-03 09:32:09.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Reference_ID=542047
;

-- Column: C_BPartner.EdiDESADVSendingMode
-- 2026-02-03T09:32:23.552Z
UPDATE AD_Column SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2026-02-03 09:32:23.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591909
;

-- Column: C_BPartner.EdiINVOICSendingMode
-- 2026-02-03T09:33:51.623Z
UPDATE AD_Column SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2026-02-03 09:33:51.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591911
;

-- Column: C_BPartner.EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-03T09:34:31.626Z
UPDATE AD_Column SET EntityType='de.metas.esb.edi',Updated=TO_TIMESTAMP('2026-02-03 09:34:31.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591910
;

