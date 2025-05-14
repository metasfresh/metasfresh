-- 2024-12-10T06:46:31.793Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583388,0,'PluFileDestination',TO_TIMESTAMP('2024-12-10 06:46:31.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','PLU-Datei senden an','PLU-Datei senden an',TO_TIMESTAMP('2024-12-10 06:46:31.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-10T06:46:31.800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583388 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PluFileDestination
-- 2024-12-10T06:46:40.327Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 06:46:40.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583388 AND AD_Language='de_CH'
;

-- 2024-12-10T06:46:40.363Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583388,'de_CH') 
;

-- Element: PluFileDestination
-- 2024-12-10T06:46:43.449Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 06:46:43.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583388 AND AD_Language='de_DE'
;

-- 2024-12-10T06:46:43.451Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583388,'de_DE') 
;

-- 2024-12-10T06:46:43.452Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583388,'de_DE') 
;

-- Element: PluFileDestination
-- 2024-12-10T06:47:57.922Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Send PLU file to', PrintName='Send PLU file to',Updated=TO_TIMESTAMP('2024-12-10 06:47:57.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583388 AND AD_Language='en_US'
;

-- 2024-12-10T06:47:57.924Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583388,'en_US') 
;

-- Name: PluFileDestination
-- 2024-12-10T06:49:06.141Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541911,TO_TIMESTAMP('2024-12-10 06:49:06.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','N','PluFileDestination',TO_TIMESTAMP('2024-12-10 06:49:06.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2024-12-10T06:49:06.143Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541911 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PluFileDestination
-- Value: Disk
-- ValueName: Disk
-- 2024-12-10T06:51:04.937Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541911,543780,TO_TIMESTAMP('2024-12-10 06:51:04.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Resultierende PLU-Datei wird in einem Verzeichnis gespeichert.','de.metas.externalsystem','Y','Server-Verzeichnis',TO_TIMESTAMP('2024-12-10 06:51:04.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Disk','Disk')
;

-- 2024-12-10T06:51:04.940Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543780 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PluFileDestination -> Disk_Disk
-- 2024-12-10T06:51:10.018Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 06:51:10.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543780
;

-- Reference Item: PluFileDestination -> Disk_Disk
-- 2024-12-10T06:51:15.562Z
UPDATE AD_Ref_List_Trl SET Description='Die resultierende PLU-Datei wird in einem Verzeichnis gespeichert.', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 06:51:15.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543780
;

-- 2024-12-10T06:51:15.564Z
UPDATE AD_Ref_List SET Description='Die resultierende PLU-Datei wird in einem Verzeichnis gespeichert.', Updated=TO_TIMESTAMP('2024-12-10 06:51:15.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Ref_List_ID=543780
;

-- Reference Item: PluFileDestination -> Disk_Disk
-- 2024-12-10T06:51:22.549Z
UPDATE AD_Ref_List_Trl SET Description='Die resultierende PLU-Datei wird in einem Verzeichnis gespeichert.',Updated=TO_TIMESTAMP('2024-12-10 06:51:22.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543780
;

-- Reference Item: PluFileDestination -> Disk_Disk
-- 2024-12-10T06:52:00.457Z
UPDATE AD_Ref_List_Trl SET Description='The resultierende PLU file is stored in a folder', IsTranslated='Y', Name='Server folder',Updated=TO_TIMESTAMP('2024-12-10 06:52:00.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543780
;

-- Reference: PluFileDestination
-- Value: 1TCP
-- ValueName: TCP
-- 2024-12-10T06:53:56.570Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541911,543781,TO_TIMESTAMP('2024-12-10 06:53:56.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die resultierende PLU-Datei wird via TCP an eine Leich+Melhl Maschiene gesendet','de.metas.externalsystem','Y','Leich+Mehl Maschiene',TO_TIMESTAMP('2024-12-10 06:53:56.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'1TCP','TCP')
;

-- 2024-12-10T06:53:56.571Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543781 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PluFileDestination
-- Value: 2Disk
-- ValueName: Disk
-- 2024-12-10T06:54:05.464Z
UPDATE AD_Ref_List SET Value='2Disk',Updated=TO_TIMESTAMP('2024-12-10 06:54:05.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543780
;

-- Reference: PluFileDestination
-- Value: 1TCP
-- ValueName: TCP
-- 2024-12-10T06:54:33.526Z
UPDATE AD_Ref_List SET Description='Die resultierende PLU-Datei wird via TCP an eine Leich+Mehl Maschine gesendet', Name='Leich+Mehl Maschine',Updated=TO_TIMESTAMP('2024-12-10 06:54:33.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543781
;

-- 2024-12-10T06:54:33.527Z
UPDATE AD_Ref_List_Trl trl SET Description='Die resultierende PLU-Datei wird via TCP an eine Leich+Mehl Maschine gesendet',Name='Leich+Mehl Maschine' WHERE AD_Ref_List_ID=543781 AND AD_Language='de_DE'
;

-- Reference Item: PluFileDestination -> 1TCP_TCP
-- 2024-12-10T06:54:44.266Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 06:54:44.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543781
;

-- Reference Item: PluFileDestination -> 1TCP_TCP
-- 2024-12-10T06:54:47.946Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Leich+Mehl Maschine',Updated=TO_TIMESTAMP('2024-12-10 06:54:47.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543781
;

-- Reference Item: PluFileDestination -> 1TCP_TCP
-- 2024-12-10T06:55:33.375Z
UPDATE AD_Ref_List_Trl SET Description='Die resulting PLU file is send to a Leich+Mehl machine via TCP', IsTranslated='Y', Name='Leich+Mehl Machine',Updated=TO_TIMESTAMP('2024-12-10 06:55:33.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543781
;

-- Reference Item: PluFileDestination -> 1TCP_TCP
-- 2024-12-10T06:55:52.959Z
UPDATE AD_Ref_List_Trl SET Description='Die resulting PLU file is send to a Leich+Mehl machine via TCP.',Updated=TO_TIMESTAMP('2024-12-10 06:55:52.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543781
;

-- Reference Item: PluFileDestination -> 1TCP_TCP
-- 2024-12-10T06:55:58.236Z
UPDATE AD_Ref_List_Trl SET Description='Die resulting PLU file is send to a Leich+Mehl machine via TCP',Updated=TO_TIMESTAMP('2024-12-10 06:55:58.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543781
;

-- Reference Item: PluFileDestination -> 2Disk_Disk
-- 2024-12-10T06:56:20.132Z
UPDATE AD_Ref_List_Trl SET Description='The resultierende PLU file is stored to a folder',Updated=TO_TIMESTAMP('2024-12-10 06:56:20.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543780
;

-- Reference Item: PluFileDestination -> 2Disk_Disk
-- 2024-12-10T06:56:30.842Z
UPDATE AD_Ref_List_Trl SET Description='The resulting PLU file is stored to a folder',Updated=TO_TIMESTAMP('2024-12-10 06:56:30.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543780
;

-- Reference Item: PluFileDestination -> 2Disk_Disk
-- 2024-12-10T06:56:34.491Z
UPDATE AD_Ref_List_Trl SET Description='Die resultierende PLU-Datei wird in einem Verzeichnis gespeichert',Updated=TO_TIMESTAMP('2024-12-10 06:56:34.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543780
;

-- 2024-12-10T06:56:34.492Z
UPDATE AD_Ref_List SET Description='Die resultierende PLU-Datei wird in einem Verzeichnis gespeichert', Updated=TO_TIMESTAMP('2024-12-10 06:56:34.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Ref_List_ID=543780
;

-- Reference Item: PluFileDestination -> 2Disk_Disk
-- 2024-12-10T06:56:55.111Z
UPDATE AD_Ref_List_Trl SET Description='Die resultierende PLU-Datei wird in einem Verzeichnis gespeichert',Updated=TO_TIMESTAMP('2024-12-10 06:56:55.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543780
;

-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- 2024-12-10T06:57:34.439Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589479,583388,0,17,541911,542129,'XX','PluFileDestination',TO_TIMESTAMP('2024-12-10 06:57:34.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','1TCP','de.metas.externalsystem',0,4,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'PLU-Datei senden an',0,0,TO_TIMESTAMP('2024-12-10 06:57:34.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-10T06:57:34.442Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-10T06:57:34.447Z
/* DDL */  select update_Column_Translation_From_AD_Element(583388) 
;

-- 2024-12-10T06:57:39.819Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl','ALTER TABLE public.ExternalSystem_Config_LeichMehl ADD COLUMN PluFileDestination VARCHAR(4) DEFAULT ''1TCP'' NOT NULL')
;

-- 2024-12-10T07:02:57.186Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583389,0,'PluFileLocalFolder',TO_TIMESTAMP('2024-12-10 07:02:57.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespreichert wird','de.metas.externalsystem','Y','Server-Verzeichnis','Server-Verzeichnis',TO_TIMESTAMP('2024-12-10 07:02:57.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-10T07:02:57.188Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583389 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PluFileLocalFolder
-- 2024-12-10T07:03:00.483Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 07:03:00.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583389 AND AD_Language='de_CH'
;

-- 2024-12-10T07:03:00.485Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583389,'de_CH') 
;

-- Element: PluFileLocalFolder
-- 2024-12-10T07:03:03.296Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 07:03:03.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583389 AND AD_Language='de_DE'
;

-- 2024-12-10T07:03:03.298Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583389,'de_DE') 
;

-- 2024-12-10T07:03:03.299Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583389,'de_DE') 
;

-- Element: PluFileLocalFolder
-- 2024-12-10T07:03:12.710Z
UPDATE AD_Element_Trl SET Description='Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird',Updated=TO_TIMESTAMP('2024-12-10 07:03:12.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583389 AND AD_Language='de_DE'
;

-- 2024-12-10T07:03:12.711Z
UPDATE AD_Element SET Description='Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird', Updated=TO_TIMESTAMP('2024-12-10 07:03:12.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=583389
;

-- 2024-12-10T07:03:13.075Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583389,'de_DE') 
;

-- 2024-12-10T07:03:13.076Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583389,'de_DE') 
;

-- Element: PluFileLocalFolder
-- 2024-12-10T07:03:21.385Z
UPDATE AD_Element_Trl SET Description='Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird',Updated=TO_TIMESTAMP('2024-12-10 07:03:21.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583389 AND AD_Language='en_US'
;

-- 2024-12-10T07:03:21.388Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583389,'en_US') 
;

-- Element: PluFileLocalFolder
-- 2024-12-10T07:03:29.589Z
UPDATE AD_Element_Trl SET Description='Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird',Updated=TO_TIMESTAMP('2024-12-10 07:03:29.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583389 AND AD_Language='de_CH'
;

-- 2024-12-10T07:03:29.591Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583389,'de_CH') 
;

-- Element: PluFileLocalFolder
-- 2024-12-10T07:03:51.524Z
UPDATE AD_Element_Trl SET Description='Directory on the external systems server in which a resulting PLU file is saved', IsTranslated='Y', Name='Server-Folder', PrintName='Server-Folder',Updated=TO_TIMESTAMP('2024-12-10 07:03:51.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583389 AND AD_Language='en_US'
;

-- 2024-12-10T07:03:51.526Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583389,'en_US') 
;

-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-10T07:04:20.445Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589480,583389,0,10,542129,'XX','PluFileLocalFolder',TO_TIMESTAMP('2024-12-10 07:04:20.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird','de.metas.externalsystem',0,4096,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Server-Verzeichnis',0,0,TO_TIMESTAMP('2024-12-10 07:04:20.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-12-10T07:04:20.447Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-10T07:04:20.452Z
/* DDL */  select update_Column_Translation_From_AD_Element(583389) 
;

-- 2024-12-10T07:04:21.741Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl','ALTER TABLE public.ExternalSystem_Config_LeichMehl ADD COLUMN PluFileLocalFolder VARCHAR(4096)')
;

-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-10T07:04:53.712Z
UPDATE AD_Column SET MandatoryLogic='@PluFileDestination/1TCP@=2DISK',Updated=TO_TIMESTAMP('2024-12-10 07:04:53.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589480
;

-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2024-12-10T07:05:10.899Z
UPDATE AD_Column SET MandatoryLogic='@PluFileDestination/1TCP@=1TCP',Updated=TO_TIMESTAMP('2024-12-10 07:05:10.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583485
;

-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- 2024-12-10T07:05:17.514Z
UPDATE AD_Column SET MandatoryLogic='@PluFileDestination/1TCP@=1TCP',Updated=TO_TIMESTAMP('2024-12-10 07:05:17.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583484
;

-- 2024-12-10T07:05:31.129Z
INSERT INTO t_alter_column values('externalsystem_config_leichmehl','PluFileLocalFolder','VARCHAR(4096)',null,null)
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:22:00.098Z
UPDATE AD_Element_Trl SET Description='Wurzelverzeichnis, in dem alle Template-PLU-Dateien enthalten sind', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 07:22:00.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_CH'
;

-- 2024-12-10T07:22:00.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_CH') 
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:22:15.181Z
UPDATE AD_Element_Trl SET Description='Wurzelverzeichnis, in dem alle PLU-Template-Dateien enthalten sind',Updated=TO_TIMESTAMP('2024-12-10 07:22:15.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_DE'
;

-- 2024-12-10T07:22:15.183Z
UPDATE AD_Element SET Description='Wurzelverzeichnis, in dem alle PLU-Template-Dateien enthalten sind', Updated=TO_TIMESTAMP('2024-12-10 07:22:15.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=581059
;

-- 2024-12-10T07:22:15.517Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581059,'de_DE') 
;

-- 2024-12-10T07:22:15.519Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_DE') 
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:22:17.294Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 07:22:17.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_DE'
;

-- 2024-12-10T07:22:17.297Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581059,'de_DE') 
;

-- 2024-12-10T07:22:17.298Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_DE') 
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:22:27.947Z
UPDATE AD_Element_Trl SET Description='Wurzelverzeichnis, in dem alle PLU-Template-Dateien enthalten sind',Updated=TO_TIMESTAMP('2024-12-10 07:22:27.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_CH'
;

-- 2024-12-10T07:22:27.949Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_CH') 
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:22:40.009Z
UPDATE AD_Element_Trl SET Description='Root directly that contains all PLU template files', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-10 07:22:40.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='en_US'
;

-- 2024-12-10T07:22:40.011Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'en_US') 
;

-- Table: ExternalSystem_Config_LeichMehl
-- Table: ExternalSystem_Config_LeichMehl
-- 2024-12-10T07:23:35.322Z
UPDATE AD_Table SET AD_Window_ID=541540,Updated=TO_TIMESTAMP('2024-12-10 07:23:35.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542129
;

-- Field: External system config Leich + Mehl -> External system config Leich + Mehl -> PLU-Datei senden an
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- Field: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> PLU-Datei senden an
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- 2024-12-10T07:23:49.875Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589479,734077,0,546388,TO_TIMESTAMP('2024-12-10 07:23:49.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,4,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU-Datei senden an',TO_TIMESTAMP('2024-12-10 07:23:49.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-10T07:23:49.878Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-10T07:23:49.882Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583388) 
;

-- 2024-12-10T07:23:49.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734077
;

-- 2024-12-10T07:23:49.896Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734077)
;

-- Field: External system config Leich + Mehl -> External system config Leich + Mehl -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Field: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-10T07:23:49.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589480,734078,0,546388,TO_TIMESTAMP('2024-12-10 07:23:49.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird',4096,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Server-Verzeichnis',TO_TIMESTAMP('2024-12-10 07:23:49.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-12-10T07:23:49.992Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-10T07:23:49.994Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583389) 
;

-- 2024-12-10T07:23:49.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734078
;

-- 2024-12-10T07:23:49.998Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734078)
;

-- UI Column: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10
-- UI Element Group: template_directory
-- 2024-12-10T07:29:16.380Z
UPDATE AD_UI_ElementGroup SET Name='template_directory',Updated=TO_TIMESTAMP('2024-12-10 07:29:16.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549387
;

-- UI Column: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10
-- UI Element Group: plu_destincation
-- 2024-12-10T07:29:38.448Z
UPDATE AD_UI_ElementGroup SET Name='plu_destincation',Updated=TO_TIMESTAMP('2024-12-10 07:29:38.448000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549386
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_Host
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2024-12-10T07:30:33.503Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-12-10 07:30:33.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609632
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.TCP_PortNumber
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- 2024-12-10T07:30:36.636Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-12-10 07:30:36.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=609631
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.PluFileDestination
-- Column: ExternalSystem_Config_LeichMehl.PluFileDestination
-- 2024-12-10T07:31:07.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734077,0,546388,549386,627388,'F',TO_TIMESTAMP('2024-12-10 07:31:07.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'PluFileDestination',10,0,0,TO_TIMESTAMP('2024-12-10 07:31:07.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: External system config Leich + Mehl -> External system config Leich + Mehl.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- UI Element: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> main -> 10 -> plu_destincation.PluFileLocalFolder
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-10T07:31:28.840Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734078,0,546388,549386,627389,'F',TO_TIMESTAMP('2024-12-10 07:31:28.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verzeichnis auf dem Externe-Systeme-Server, in das eine resultierende PLU-Datei gespeichert wird','Y','N','N','Y','N','N','N',0,'PluFileLocalFolder',40,0,0,TO_TIMESTAMP('2024-12-10 07:31:28.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: External system config Leich + Mehl -> External system config Leich + Mehl -> LANScale Adresse
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- Field: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> LANScale Adresse
-- Column: ExternalSystem_Config_LeichMehl.TCP_Host
-- 2024-12-10T07:32:05.895Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=1TCP',Updated=TO_TIMESTAMP('2024-12-10 07:32:05.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=700755
;

-- Field: External system config Leich + Mehl -> External system config Leich + Mehl -> LANScale Port
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- Field: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> LANScale Port
-- Column: ExternalSystem_Config_LeichMehl.TCP_PortNumber
-- 2024-12-10T07:32:08.450Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=1TCP',Updated=TO_TIMESTAMP('2024-12-10 07:32:08.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=700754
;

-- Field: External system config Leich + Mehl -> External system config Leich + Mehl -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- Field: External system config Leich + Mehl(541540,de.metas.externalsystem) -> External system config Leich + Mehl(546388,de.metas.externalsystem) -> Server-Verzeichnis
-- Column: ExternalSystem_Config_LeichMehl.PluFileLocalFolder
-- 2024-12-10T07:32:31.937Z
UPDATE AD_Field SET DisplayLogic='@PluFileDestination/1TCP@=2DISK',Updated=TO_TIMESTAMP('2024-12-10 07:32:31.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=734078
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:33:12.124Z
UPDATE AD_Element_Trl SET Name='PLU-Template-Verzeichnis', PrintName='PLU-Template-Verzeichnis',Updated=TO_TIMESTAMP('2024-12-10 07:33:12.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_CH'
;

-- 2024-12-10T07:33:12.126Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_CH') 
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:33:16.256Z
UPDATE AD_Element_Trl SET Name='PLU-Template-Verzeichnis', PrintName='PLU-Template-Verzeichnis',Updated=TO_TIMESTAMP('2024-12-10 07:33:16.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='de_DE'
;

-- 2024-12-10T07:33:16.257Z
UPDATE AD_Element SET Name='PLU-Template-Verzeichnis', PrintName='PLU-Template-Verzeichnis', Updated=TO_TIMESTAMP('2024-12-10 07:33:16.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=581059
;

-- 2024-12-10T07:33:16.562Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581059,'de_DE') 
;

-- 2024-12-10T07:33:16.563Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'de_DE') 
;

-- Element: Product_BaseFolderName
-- 2024-12-10T07:33:31.689Z
UPDATE AD_Element_Trl SET Name='PLU template directory', PrintName='PLU template directory',Updated=TO_TIMESTAMP('2024-12-10 07:33:31.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581059 AND AD_Language='en_US'
;

-- 2024-12-10T07:33:31.690Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581059,'en_US') 
;

-- Column: ExternalSystem_Config_LeichMehl.Product_BaseFolderName
-- Column: ExternalSystem_Config_LeichMehl.Product_BaseFolderName
-- 2024-12-10T07:33:43.177Z
UPDATE AD_Column SET FieldLength=4096,Updated=TO_TIMESTAMP('2024-12-10 07:33:43.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=583483
;

-- 2024-12-10T07:33:44.407Z
INSERT INTO t_alter_column values('externalsystem_config_leichmehl','Product_BaseFolderName','VARCHAR(4096)',null,'N/A')
;

-- 2024-12-10T07:33:44.421Z
UPDATE ExternalSystem_Config_LeichMehl SET Product_BaseFolderName='N/A' WHERE Product_BaseFolderName IS NULL
;

-- Element: IsPluFileExportAuditEnabled
-- 2024-12-10T07:35:01.578Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLU-Datei Export-Revision', PrintName='PLU-Datei Export-Revision',Updated=TO_TIMESTAMP('2024-12-10 07:35:01.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581154 AND AD_Language='de_CH'
;

-- 2024-12-10T07:35:01.580Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581154,'de_CH') 
;

-- Element: IsPluFileExportAuditEnabled
-- 2024-12-10T07:35:06.676Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLU-Datei Export-Revision', PrintName='PLU-Datei Export-Revision',Updated=TO_TIMESTAMP('2024-12-10 07:35:06.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581154 AND AD_Language='de_DE'
;

-- 2024-12-10T07:35:06.677Z
UPDATE AD_Element SET Name='PLU-Datei Export-Revision', PrintName='PLU-Datei Export-Revision', Updated=TO_TIMESTAMP('2024-12-10 07:35:06.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC' WHERE AD_Element_ID=581154
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2024-12-10T07:35:06.963Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581154,'de_DE') 
;

-- 2024-12-10T07:35:06.964Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581154,'de_DE') 
;

-- Element: IsPluFileExportAuditEnabled
-- 2024-12-10T07:35:19.060Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLU-file export audit', PrintName='PLU-file export audit',Updated=TO_TIMESTAMP('2024-12-10 07:35:19.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=581154 AND AD_Language='en_US'
;

-- 2024-12-10T07:35:19.062Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581154,'en_US') 
;
