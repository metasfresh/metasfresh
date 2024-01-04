-- Name: AD_Process_StoreProcessResultFileOn
-- 2023-11-20T09:09:20.897210100Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541841,TO_TIMESTAMP('2023-11-20 10:09:20.573','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','AD_Process_StoreProcessResultFileOn',TO_TIMESTAMP('2023-11-20 10:09:20.573','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-11-20T09:09:20.902390700Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541841 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_Process_StoreProcessResultFileOn
-- Value: S
-- ValueName: Server
-- 2023-11-20T09:09:46.197280900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541841,543594,TO_TIMESTAMP('2023-11-20 10:09:45.985','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Server',TO_TIMESTAMP('2023-11-20 10:09:45.985','YYYY-MM-DD HH24:MI:SS.US'),100,'S','Server')
;

-- 2023-11-20T09:09:46.198853200Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543594 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Process_StoreProcessResultFileOn
-- Value: D
-- ValueName: Browser
-- 2023-11-20T09:10:04.050700500Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541841,543595,TO_TIMESTAMP('2023-11-20 10:10:03.85','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Browser',TO_TIMESTAMP('2023-11-20 10:10:03.85','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Browser')
;

-- 2023-11-20T09:10:04.052292Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543595 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AD_Process_StoreProcessResultFileOn
-- Value: B
-- ValueName: Both
-- 2023-11-20T09:10:18.110863300Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541841,543596,TO_TIMESTAMP('2023-11-20 10:10:17.907','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Both',TO_TIMESTAMP('2023-11-20 10:10:17.907','YYYY-MM-DD HH24:MI:SS.US'),100,'B','Both')
;

-- 2023-11-20T09:10:18.112477Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543596 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2023-11-20T09:10:40.470737700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582811,0,'StoreProcessResultFileOn',TO_TIMESTAMP('2023-11-20 10:10:40.236','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Process Result Storing File Default Path','Process Result Storing File Default Path',TO_TIMESTAMP('2023-11-20 10:10:40.236','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-20T09:10:40.472821300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582811 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-20T09:11:21.978353100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587657,582811,0,17,541841,284,'StoreProcessResultFileOn',TO_TIMESTAMP('2023-11-20 10:11:21.743','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,12,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Process Result Storing File Default Path',0,0,TO_TIMESTAMP('2023-11-20 10:11:21.743','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-20T09:11:21.981582300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587657 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-20T09:11:22.519812400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582811) 
;

-- 2023-11-16T16:53:43.121787100Z
/* DDL */ SELECT public.db_alter_table('AD_Process','ALTER TABLE public.AD_Process ADD COLUMN StoreProcessResultFileOn CHAR')
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Default Path
-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-20T09:15:24.878027900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587657,721885,0,245,0,TO_TIMESTAMP('2023-11-20 10:15:24.661','YYYY-MM-DD HH24:MI:SS.US'),100,0,'IsReport=''Y''','D',0,'Y','Y','Y','N','N','N','N','N','N','Process Result Storing File Default Path',236,360,0,999,1,TO_TIMESTAMP('2023-11-20 10:15:24.661','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-20T09:15:24.880196700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-20T09:15:24.882274900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582811) 
;

-- 2023-11-20T09:15:24.892688500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721885
;

-- 2023-11-20T09:15:24.894888500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721885)
;

-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-20T09:19:07.232462100Z
UPDATE AD_Column SET FieldLength=22,Updated=TO_TIMESTAMP('2023-11-20 10:19:07.232','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587657
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Default Path
-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-20T09:20:16.697309500Z
UPDATE AD_Field SET DisplayLength=22,Updated=TO_TIMESTAMP('2023-11-20 10:20:16.697','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721885
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Default Path
-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-20T09:20:46.057318700Z
UPDATE AD_Field SET DisplayLogic='@IsReport@=''Y''',Updated=TO_TIMESTAMP('2023-11-20 10:20:46.057','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721885
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Default Path
-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-20T09:24:20.197274300Z
UPDATE AD_Field SET SpanX=1,Updated=TO_TIMESTAMP('2023-11-20 10:24:20.196','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721885
;

-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-22T10:34:13.704338500Z
UPDATE AD_Column SET DefaultValue='D', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-11-22 11:34:13.704','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587657
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Default Path
-- Column: AD_Process.StoreProcessResultFileOn
-- 2023-11-22T10:22:01.565581500Z
UPDATE AD_Field SET SeqNo=232,Updated=TO_TIMESTAMP('2023-11-22 11:22:01.565','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721885
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> Process Result Storing File Path
-- Column: AD_Process.StoreProcessResultFilePath
-- 2023-11-22T10:25:21.222796100Z
UPDATE AD_Field SET DisplayLogic='@IsReport@=''Y'' & (@StoreProcessResultFileOn@=''S''  | @StoreProcessResultFileOn@=''B'')',Updated=TO_TIMESTAMP('2023-11-22 11:25:21.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=721875
;

UPDATE ad_process
SET StoreProcessResultFileOn = 'D'
WHERE TRUE
;

