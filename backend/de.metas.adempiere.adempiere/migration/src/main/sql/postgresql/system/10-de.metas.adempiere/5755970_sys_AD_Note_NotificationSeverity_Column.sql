-- Run mode: SWING_CLIENT

-- 2025-05-26T14:28:47.521Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583672,0,'NotificationSeverity',TO_TIMESTAMP('2025-05-26 14:28:47.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Notification Severity','Notification Severity',TO_TIMESTAMP('2025-05-26 14:28:47.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-26T14:28:47.540Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583672 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: NotificationSeverity
-- 2025-05-26T14:29:31.101Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541947,TO_TIMESTAMP('2025-05-26 14:29:30.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','NotificationSeverity',TO_TIMESTAMP('2025-05-26 14:29:30.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-05-26T14:29:31.104Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541947 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: NotificationSeverity
-- Value: Notice
-- ValueName: Notice
-- 2025-05-26T14:29:45.485Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541947,543918,TO_TIMESTAMP('2025-05-26 14:29:45.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Notice',TO_TIMESTAMP('2025-05-26 14:29:45.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Notice','Notice')
;

-- 2025-05-26T14:29:45.493Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543918 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: NotificationSeverity
-- Value: Warning
-- ValueName: Warning
-- 2025-05-26T14:29:56.853Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541947,543919,TO_TIMESTAMP('2025-05-26 14:29:56.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Warning',TO_TIMESTAMP('2025-05-26 14:29:56.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Warning','Warning')
;

-- 2025-05-26T14:29:56.855Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543919 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: NotificationSeverity
-- Value: Error
-- ValueName: Error
-- 2025-05-26T14:30:06.107Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541947,543920,TO_TIMESTAMP('2025-05-26 14:30:05.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Error',TO_TIMESTAMP('2025-05-26 14:30:05.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Error','Error')
;

-- 2025-05-26T14:30:06.109Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543920 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: AD_Note.NotificationSeverity
-- 2025-05-26T14:30:39.087Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590134,583672,0,17,541947,389,'XX','NotificationSeverity',TO_TIMESTAMP('2025-05-26 14:30:38.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Notification Severity',0,0,TO_TIMESTAMP('2025-05-26 14:30:38.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-05-26T14:30:39.097Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590134 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-26T14:30:40.038Z
/* DDL */  select update_Column_Translation_From_AD_Element(583672)
;

-- 2025-05-26T14:30:43.108Z
/* DDL */ SELECT public.db_alter_table('AD_Note','ALTER TABLE public.AD_Note ADD COLUMN NotificationSeverity VARCHAR(255)')
;

-- Column: AD_Note.NotificationSeverity
-- 2025-05-27T11:16:36.606Z
UPDATE AD_Column SET DefaultValue='Notice', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-05-27 11:16:36.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590134
;

-- 2025-05-27T11:16:37.758Z
INSERT INTO t_alter_column values('ad_note','NotificationSeverity','VARCHAR(255)',null,'Notice')
;

-- 2025-05-27T11:16:37.775Z
INSERT INTO t_alter_column values('ad_note','NotificationSeverity',null,'NOT NULL',null)
;






-- Reference: NotificationSeverity
-- Value: Warning
-- ValueName: Warning
-- 2025-05-28T18:16:36.872Z
UPDATE AD_Ref_List SET Name='WARNING',Updated=TO_TIMESTAMP('2025-05-28 18:16:36.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543919
;

-- 2025-05-28T18:16:36.876Z
UPDATE AD_Ref_List_Trl trl SET Name='WARNING' WHERE AD_Ref_List_ID=543919 AND AD_Language='de_DE'
;

-- Reference: NotificationSeverity
-- Value: Notice
-- ValueName: Notice
-- 2025-05-28T18:16:44.457Z
UPDATE AD_Ref_List SET Name='NOTICE',Updated=TO_TIMESTAMP('2025-05-28 18:16:44.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543918
;

-- 2025-05-28T18:16:44.458Z
UPDATE AD_Ref_List_Trl trl SET Name='NOTICE' WHERE AD_Ref_List_ID=543918 AND AD_Language='de_DE'
;

-- Reference: NotificationSeverity
-- Value: Error
-- ValueName: Error
-- 2025-05-28T18:16:52.686Z
UPDATE AD_Ref_List SET Name='ERROR',Updated=TO_TIMESTAMP('2025-05-28 18:16:52.685000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543920
;

-- 2025-05-28T18:16:52.686Z
UPDATE AD_Ref_List_Trl trl SET Name='ERROR' WHERE AD_Ref_List_ID=543920 AND AD_Language='de_DE'
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:34.658Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='FEHLER',Updated=TO_TIMESTAMP('2025-05-28 18:17:34.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:34.659Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:45.074Z
UPDATE AD_Ref_List_Trl SET Name='FEHLER',Updated=TO_TIMESTAMP('2025-05-28 18:17:45.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:45.075Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:49.701Z
UPDATE AD_Ref_List_Trl SET Name='ERROR',Updated=TO_TIMESTAMP('2025-05-28 18:17:49.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:49.703Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:17:59.777Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='FEHLER',Updated=TO_TIMESTAMP('2025-05-28 18:17:59.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543920
;

-- 2025-05-28T18:17:59.778Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Error_Error
-- 2025-05-28T18:18:02.039Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-28 18:18:02.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543920
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:19:46.130Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='WARNHINWEIS',Updated=TO_TIMESTAMP('2025-05-28 18:19:46.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:19:46.132Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:19:50.835Z
UPDATE AD_Ref_List_Trl SET Name='WARNHINWEIS',Updated=TO_TIMESTAMP('2025-05-28 18:19:50.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:19:50.836Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:19:57.171Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='WARNING',Updated=TO_TIMESTAMP('2025-05-28 18:19:57.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:19:57.172Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Warning_Warning
-- 2025-05-28T18:20:00.965Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='WARNHINWEIS',Updated=TO_TIMESTAMP('2025-05-28 18:20:00.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543919
;

-- 2025-05-28T18:20:00.966Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: NotificationSeverity
-- Value: Notice
-- ValueName: Notice
-- 2025-05-28T18:21:18.027Z
UPDATE AD_Ref_List SET Name='NACHRICHT',Updated=TO_TIMESTAMP('2025-05-28 18:21:18.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:18.029Z
UPDATE AD_Ref_List_Trl trl SET Name='NACHRICHT' WHERE AD_Ref_List_ID=543918 AND AD_Language='de_DE'
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:26.916Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-28 18:21:26.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543918
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:33.028Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='NOTICE',Updated=TO_TIMESTAMP('2025-05-28 18:21:33.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:33.029Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:37.737Z
UPDATE AD_Ref_List_Trl SET Name='NACHRICHT',Updated=TO_TIMESTAMP('2025-05-28 18:21:37.737000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:37.738Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: NotificationSeverity -> Notice_Notice
-- 2025-05-28T18:21:41.557Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='NACHRICHT',Updated=TO_TIMESTAMP('2025-05-28 18:21:41.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543918
;

-- 2025-05-28T18:21:41.559Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

