-- Run mode: SWING_CLIENT

-- Name: PostFinance_ExportStatus
-- 2024-03-22T13:29:36.601Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541860,TO_TIMESTAMP('2024-03-22 14:29:36.424','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','N','PostFinance_ExportStatus',TO_TIMESTAMP('2024-03-22 14:29:36.424','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2024-03-22T13:29:36.607Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541860 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PostFinance_ExportStatus
-- Value: N
-- ValueName: NotSent
-- 2024-03-22T13:39:37.299Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541860,543649,TO_TIMESTAMP('2024-03-22 14:39:37.17','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Nicht gesendet',TO_TIMESTAMP('2024-03-22 14:39:37.17','YYYY-MM-DD HH24:MI:SS.US'),100,'N','NotSent')
;

-- 2024-03-22T13:39:37.301Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543649 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PostFinance_ExportStatus -> N_NotSent
-- 2024-03-22T13:39:41.654Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:39:41.654','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543649
;

-- Reference Item: PostFinance_ExportStatus -> N_NotSent
-- 2024-03-22T13:39:43.307Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:39:43.307','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543649
;

-- Reference Item: PostFinance_ExportStatus -> N_NotSent
-- 2024-03-22T13:39:51.433Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Not send',Updated=TO_TIMESTAMP('2024-03-22 14:39:51.433','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543649
;

-- Reference: PostFinance_ExportStatus
-- Value: E
-- ValueName: Error
-- 2024-03-22T13:40:58.944Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541860,543650,TO_TIMESTAMP('2024-03-22 14:40:58.815','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Fehler',TO_TIMESTAMP('2024-03-22 14:40:58.815','YYYY-MM-DD HH24:MI:SS.US'),100,'E','Error')
;

-- 2024-03-22T13:40:58.949Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543650 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PostFinance_ExportStatus -> E_Error
-- 2024-03-22T13:41:04.291Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:41:04.291','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543650
;

-- Reference Item: PostFinance_ExportStatus -> E_Error
-- 2024-03-22T13:41:06.690Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:41:06.69','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543650
;

-- Reference Item: PostFinance_ExportStatus -> E_Error
-- 2024-03-22T13:41:13.571Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Error',Updated=TO_TIMESTAMP('2024-03-22 14:41:13.571','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543650
;

-- Reference: PostFinance_ExportStatus
-- Value: O
-- ValueName: OK
-- 2024-03-22T13:43:28.554Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541860,543651,TO_TIMESTAMP('2024-03-22 14:43:28.429','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','OK',TO_TIMESTAMP('2024-03-22 14:43:28.429','YYYY-MM-DD HH24:MI:SS.US'),100,'O','OK')
;

-- 2024-03-22T13:43:28.555Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543651 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PostFinance_ExportStatus -> O_OK
-- 2024-03-22T13:43:34.392Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:43:34.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543651
;

-- Reference Item: PostFinance_ExportStatus -> O_OK
-- 2024-03-22T13:43:36.263Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:43:36.263','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543651
;

-- Reference Item: PostFinance_ExportStatus -> O_OK
-- 2024-03-22T13:43:38.224Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:43:38.224','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543651
;

-- 2024-03-22T13:46:18.735Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583052,0,'PostFinance_Export_Status',TO_TIMESTAMP('2024-03-22 14:46:18.617','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','PostFinance Status','PostFinance Status',TO_TIMESTAMP('2024-03-22 14:46:18.617','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-22T13:46:18.739Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583052 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PostFinance_Export_Status
-- 2024-03-22T13:46:27.132Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:46:27.132','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583052 AND AD_Language='de_CH'
;

-- 2024-03-22T13:46:27.151Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583052,'de_CH')
;

-- Element: PostFinance_Export_Status
-- 2024-03-22T13:46:27.956Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:46:27.956','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583052 AND AD_Language='de_DE'
;

-- 2024-03-22T13:46:27.958Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583052,'de_DE')
;

-- 2024-03-22T13:46:27.960Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583052,'de_DE')
;

-- Element: PostFinance_Export_Status
-- 2024-03-22T13:46:33.311Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 14:46:33.311','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583052 AND AD_Language='en_US'
;

-- 2024-03-22T13:46:33.312Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583052,'en_US')
;

-- Column: C_Doc_Outbound_Log.PostFinance_Export_Status
-- 2024-03-22T13:49:09.767Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588046,583052,0,17,541860,540453,'PostFinance_Export_Status',TO_TIMESTAMP('2024-03-22 14:49:09.557','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.document.archive',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'PostFinance Status',0,0,TO_TIMESTAMP('2024-03-22 14:49:09.557','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-22T13:49:09.776Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588046 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-22T13:49:09.787Z
/* DDL */  select update_Column_Translation_From_AD_Element(583052)
;

-- 2024-03-22T13:49:10.590Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN PostFinance_Export_Status CHAR(1) DEFAULT ''N'' NOT NULL')
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> PostFinance Status
-- Column: C_Doc_Outbound_Log.PostFinance_Export_Status
-- 2024-03-22T13:50:59.725Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588046,726635,0,540474,0,TO_TIMESTAMP('2024-03-22 14:50:59.584','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.postfinance',0,'Y','Y','Y','N','N','N','N','N','N','PostFinance Status',0,220,0,1,1,TO_TIMESTAMP('2024-03-22 14:50:59.584','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-22T13:50:59.727Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726635 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-22T13:50:59.730Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583052)
;

-- 2024-03-22T13:50:59.738Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726635
;

-- 2024-03-22T13:50:59.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726635)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.PostFinance Status
-- Column: C_Doc_Outbound_Log.PostFinance_Export_Status
-- 2024-03-22T13:51:51.329Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726635,0,540474,540351,623807,'F',TO_TIMESTAMP('2024-03-22 14:51:51.191','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'PostFinance Status',50,0,0,TO_TIMESTAMP('2024-03-22 14:51:51.191','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> PostFinance Status
-- Column: C_Doc_Outbound_Log.PostFinance_Export_Status
-- 2024-03-22T14:28:03.326Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-03-22 15:28:03.326','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726635
;

-- Table: C_Doc_Outbound_Log_PostFinance_Log
-- 2024-03-23T09:50:00.889Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542402,'N',TO_TIMESTAMP('2024-03-23 10:50:00.698','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','N','Y','N','N','N','N','N','N','N','N',0,'PostFinance Log','NP','L','C_Doc_Outbound_Log_PostFinance_Log','DTI',TO_TIMESTAMP('2024-03-23 10:50:00.698','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:00.893Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542402 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-03-23T09:50:01.001Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556338,TO_TIMESTAMP('2024-03-23 10:50:00.915','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_Doc_Outbound_Log_PostFinance_Log',1,'Y','N','Y','Y','C_Doc_Outbound_Log_PostFinance_Log','N',1000000,TO_TIMESTAMP('2024-03-23 10:50:00.915','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T09:50:01.012Z
CREATE SEQUENCE C_DOC_OUTBOUND_LOG_POSTFINANCE_LOG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Client_ID
-- 2024-03-23T09:50:33.722Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588047,102,0,19,542402,'AD_Client_ID',TO_TIMESTAMP('2024-03-23 10:50:33.595','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.postfinance',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-03-23 10:50:33.595','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:33.726Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588047 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:33.730Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Org_ID
-- 2024-03-23T09:50:34.727Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588048,113,0,30,542402,'AD_Org_ID',TO_TIMESTAMP('2024-03-23 10:50:34.393','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.postfinance',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-03-23 10:50:34.393','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:34.729Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588048 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:34.735Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.Created
-- 2024-03-23T09:50:35.486Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588049,245,0,16,542402,'Created',TO_TIMESTAMP('2024-03-23 10:50:35.209','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.postfinance',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-03-23 10:50:35.209','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:35.488Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588049 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:35.493Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.CreatedBy
-- 2024-03-23T09:50:37.489Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588050,246,0,18,110,542402,'CreatedBy',TO_TIMESTAMP('2024-03-23 10:50:35.918','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.postfinance',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-03-23 10:50:35.918','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:37.490Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588050 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:37.492Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.IsActive
-- 2024-03-23T09:50:38.153Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588051,348,0,20,542402,'IsActive',TO_TIMESTAMP('2024-03-23 10:50:37.888','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.postfinance',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-03-23 10:50:37.888','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:38.156Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588051 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:38.159Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.Updated
-- 2024-03-23T09:50:38.850Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588052,607,0,16,542402,'Updated',TO_TIMESTAMP('2024-03-23 10:50:38.604','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.postfinance',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-03-23 10:50:38.604','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:38.853Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588052 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:38.855Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.UpdatedBy
-- 2024-03-23T09:50:39.660Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588053,608,0,18,110,542402,'UpdatedBy',TO_TIMESTAMP('2024-03-23 10:50:39.325','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.postfinance',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-03-23 10:50:39.325','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:39.662Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588053 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:39.666Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2024-03-23T09:50:40.260Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583053,0,'C_Doc_Outbound_Log_PostFinance_Log_ID',TO_TIMESTAMP('2024-03-23 10:50:40.177','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','PostFinance Log','PostFinance Log',TO_TIMESTAMP('2024-03-23 10:50:40.177','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T09:50:40.262Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583053 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.C_Doc_Outbound_Log_PostFinance_Log_ID
-- 2024-03-23T09:50:41.122Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588054,583053,0,13,542402,'C_Doc_Outbound_Log_PostFinance_Log_ID',TO_TIMESTAMP('2024-03-23 10:50:40.174','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.postfinance',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','PostFinance Log',0,0,TO_TIMESTAMP('2024-03-23 10:50:40.174','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:50:41.126Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588054 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:50:41.130Z
/* DDL */  select update_Column_Translation_From_AD_Element(583053)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.IsError
-- 2024-03-23T09:52:20.556Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588055,2395,0,20,542402,'IsError',TO_TIMESTAMP('2024-03-23 10:52:20.434','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Ein Fehler ist bei der Durchführung aufgetreten','de.metas.postfinance',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Fehler',0,0,TO_TIMESTAMP('2024-03-23 10:52:20.434','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:52:20.560Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588055 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:52:20.564Z
/* DDL */  select update_Column_Translation_From_AD_Element(2395)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.MsgText
-- 2024-03-23T09:54:24.685Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588056,463,0,14,542402,'MsgText',TO_TIMESTAMP('2024-03-23 10:54:24.57','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Textual Informational, Menu or Error Message','de.metas.postfinance',0,2000,'The Message Text indicates the message that will display ','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Message Text',0,0,TO_TIMESTAMP('2024-03-23 10:54:24.57','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:54:24.687Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588056 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:54:24.689Z
/* DDL */  select update_Column_Translation_From_AD_Element(463)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Issue_ID
-- 2024-03-23T09:57:47.163Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588057,2887,0,30,542402,'AD_Issue_ID',TO_TIMESTAMP('2024-03-23 10:57:47.003','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.postfinance',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Probleme',0,0,TO_TIMESTAMP('2024-03-23 10:57:47.003','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T09:57:47.166Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588057 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T09:57:47.170Z
/* DDL */  select update_Column_Translation_From_AD_Element(2887)
;

-- 2024-03-23T09:59:02.650Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583054,0,'PostFinance_Transaction_Id',TO_TIMESTAMP('2024-03-23 10:59:02.536','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','PostFinance Transaktions Id','PostFinance Transaktions Id',TO_TIMESTAMP('2024-03-23 10:59:02.536','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T09:59:02.652Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583054 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PostFinance_Transaction_Id
-- 2024-03-23T09:59:11.016Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-23 10:59:11.016','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583054 AND AD_Language='de_CH'
;

-- 2024-03-23T09:59:11.018Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583054,'de_CH')
;

-- Element: PostFinance_Transaction_Id
-- 2024-03-23T09:59:14.359Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-23 10:59:14.359','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583054 AND AD_Language='de_DE'
;

-- 2024-03-23T09:59:14.361Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583054,'de_DE')
;

-- 2024-03-23T09:59:14.362Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583054,'de_DE')
;

-- Element: PostFinance_Transaction_Id
-- 2024-03-23T09:59:50.031Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PostFinance Transaction Id',Updated=TO_TIMESTAMP('2024-03-23 10:59:50.03','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583054 AND AD_Language='en_US'
;

-- 2024-03-23T09:59:50.033Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583054,'en_US')
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.PostFinance_Transaction_Id
-- 2024-03-23T10:02:36.064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588058,583054,0,10,542402,'PostFinance_Transaction_Id',TO_TIMESTAMP('2024-03-23 11:02:35.948','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.postfinance',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'PostFinance Transaktions Id',0,0,TO_TIMESTAMP('2024-03-23 11:02:35.948','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T10:02:36.066Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588058 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T10:02:36.068Z
/* DDL */  select update_Column_Translation_From_AD_Element(583054)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Issue_ID
-- 2024-03-23T10:47:54.481Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-03-23 11:47:54.481','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588057
;

-- 2024-03-23T10:02:48.979Z
/* DDL */ CREATE TABLE public.C_Doc_Outbound_Log_PostFinance_Log (AD_Client_ID NUMERIC(10) NOT NULL, AD_Issue_ID NUMERIC(10), AD_Org_ID NUMERIC(10) NOT NULL, C_Doc_Outbound_Log_PostFinance_Log_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsError CHAR(1) DEFAULT 'N' CHECK (IsError IN ('Y','N')) NOT NULL, MsgText VARCHAR(2000), PostFinance_Transaction_Id VARCHAR(50), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Doc_Outbound_Log_PostFinance_Log_Key PRIMARY KEY (C_Doc_Outbound_Log_PostFinance_Log_ID))
;

-- 2024-03-23T10:03:10.055Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583055,0,TO_TIMESTAMP('2024-03-23 11:03:09.858','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','PostFinance Log','PostFinance Log',TO_TIMESTAMP('2024-03-23 11:03:09.858','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:03:10.066Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583055 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.C_Doc_Outbound_Log_ID
-- 2024-03-23T10:10:24.272Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588059,541944,0,19,542402,'C_Doc_Outbound_Log_ID',TO_TIMESTAMP('2024-03-23 11:10:24.105','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.postfinance',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'C_Doc_Outbound_Log',0,0,TO_TIMESTAMP('2024-03-23 11:10:24.105','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-23T10:10:24.274Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588059 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-23T10:10:24.276Z
/* DDL */  select update_Column_Translation_From_AD_Element(541944)
;

-- 2024-03-23T10:10:25.713Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log_PostFinance_Log','ALTER TABLE public.C_Doc_Outbound_Log_PostFinance_Log ADD COLUMN C_Doc_Outbound_Log_ID NUMERIC(10)')
;

-- 2024-03-23T10:10:25.720Z
ALTER TABLE C_Doc_Outbound_Log_PostFinance_Log ADD CONSTRAINT CDocOutboundLog_CDocOutboundLogPostFinanceLog FOREIGN KEY (C_Doc_Outbound_Log_ID) REFERENCES public.C_Doc_Outbound_Log DEFERRABLE INITIALLY DEFERRED
;

-- Tab: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log
-- Table: C_Doc_Outbound_Log_PostFinance_Log
-- 2024-03-23T10:12:12.144Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583053,0,547490,542402,540170,'Y',TO_TIMESTAMP('2024-03-23 11:12:11.968','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','N','N','A','C_Doc_Outbound_Log_PostFinance_Log','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'PostFinance Log',548157,'N',30,1,TO_TIMESTAMP('2024-03-23 11:12:11.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:12:12.148Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547490 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-03-23T10:12:12.151Z
/* DDL */  select update_tab_translation_from_ad_element(583053)
;

-- 2024-03-23T10:12:12.155Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547490)
;

-- Column: C_Doc_Outbound_Log_PostFinance_Log.C_Doc_Outbound_Log_ID
-- 2024-03-23T10:14:39.040Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-03-23 11:14:39.04','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588059
;

-- Tab: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log
-- Table: C_Doc_Outbound_Log_PostFinance_Log
-- 2024-03-23T10:15:52.060Z
UPDATE AD_Tab SET AD_Column_ID=588059,Updated=TO_TIMESTAMP('2024-03-23 11:15:52.06','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547490
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Mandant
-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Client_ID
-- 2024-03-23T10:16:54.649Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588047,726636,0,547490,TO_TIMESTAMP('2024-03-23 11:16:54.541','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.postfinance','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-03-23 11:16:54.541','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:16:54.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726636 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:16:54.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-03-23T10:16:55.011Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726636
;

-- 2024-03-23T10:16:55.012Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726636)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Organisation
-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Org_ID
-- 2024-03-23T10:16:57.056Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588048,726637,0,547490,TO_TIMESTAMP('2024-03-23 11:16:55.015','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.postfinance','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-03-23 11:16:55.015','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:16:57.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726637 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:16:57.064Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-03-23T10:16:57.299Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726637
;

-- 2024-03-23T10:16:57.300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726637)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Erstellt
-- Column: C_Doc_Outbound_Log_PostFinance_Log.Created
-- 2024-03-23T10:16:59.056Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588049,726638,0,547490,TO_TIMESTAMP('2024-03-23 11:16:57.302','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.postfinance','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-03-23 11:16:57.302','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:16:59.059Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726638 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:16:59.063Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2024-03-23T10:16:59.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726638
;

-- 2024-03-23T10:16:59.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726638)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Erstellt durch
-- Column: C_Doc_Outbound_Log_PostFinance_Log.CreatedBy
-- 2024-03-23T10:16:59.201Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588050,726639,0,547490,TO_TIMESTAMP('2024-03-23 11:16:59.12','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.postfinance','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2024-03-23 11:16:59.12','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:16:59.202Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726639 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:16:59.203Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2024-03-23T10:16:59.238Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726639
;

-- 2024-03-23T10:16:59.239Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726639)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Aktiv
-- Column: C_Doc_Outbound_Log_PostFinance_Log.IsActive
-- 2024-03-23T10:16:59.327Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588051,726640,0,547490,TO_TIMESTAMP('2024-03-23 11:16:59.242','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.postfinance','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-03-23 11:16:59.242','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:16:59.328Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726640 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:16:59.329Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-03-23T10:16:59.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726640
;

-- 2024-03-23T10:16:59.424Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726640)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Aktualisiert
-- Column: C_Doc_Outbound_Log_PostFinance_Log.Updated
-- 2024-03-23T10:17:01.058Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588052,726641,0,547490,TO_TIMESTAMP('2024-03-23 11:16:59.427','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.postfinance','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2024-03-23 11:16:59.427','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726641 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.065Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2024-03-23T10:17:01.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726641
;

-- 2024-03-23T10:17:01.108Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726641)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Aktualisiert durch
-- Column: C_Doc_Outbound_Log_PostFinance_Log.UpdatedBy
-- 2024-03-23T10:17:01.199Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588053,726642,0,547490,TO_TIMESTAMP('2024-03-23 11:17:01.111','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.postfinance','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2024-03-23 11:17:01.111','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.201Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.202Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2024-03-23T10:17:01.239Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726642
;

-- 2024-03-23T10:17:01.240Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726642)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> PostFinance Log
-- Column: C_Doc_Outbound_Log_PostFinance_Log.C_Doc_Outbound_Log_PostFinance_Log_ID
-- 2024-03-23T10:17:01.353Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588054,726643,0,547490,TO_TIMESTAMP('2024-03-23 11:17:01.242','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.postfinance','Y','N','N','N','N','N','N','N','PostFinance Log',TO_TIMESTAMP('2024-03-23 11:17:01.242','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.355Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.358Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583053)
;

-- 2024-03-23T10:17:01.361Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726643
;

-- 2024-03-23T10:17:01.362Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726643)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Fehler
-- Column: C_Doc_Outbound_Log_PostFinance_Log.IsError
-- 2024-03-23T10:17:01.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588055,726644,0,547490,TO_TIMESTAMP('2024-03-23 11:17:01.365','YYYY-MM-DD HH24:MI:SS.US'),100,'Ein Fehler ist bei der Durchführung aufgetreten',1,'de.metas.postfinance','Y','Y','N','N','N','N','N','Fehler',TO_TIMESTAMP('2024-03-23 11:17:01.365','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.466Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.469Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395)
;

-- 2024-03-23T10:17:01.473Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726644
;

-- 2024-03-23T10:17:01.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726644)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Message Text
-- Column: C_Doc_Outbound_Log_PostFinance_Log.MsgText
-- 2024-03-23T10:17:01.563Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588056,726645,0,547490,TO_TIMESTAMP('2024-03-23 11:17:01.476','YYYY-MM-DD HH24:MI:SS.US'),100,'Textual Informational, Menu or Error Message',2000,'de.metas.postfinance','The Message Text indicates the message that will display ','Y','Y','N','N','N','N','N','Message Text',TO_TIMESTAMP('2024-03-23 11:17:01.476','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.565Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726645 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.567Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(463)
;

-- 2024-03-23T10:17:01.571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726645
;

-- 2024-03-23T10:17:01.572Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726645)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Probleme
-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Issue_ID
-- 2024-03-23T10:17:01.662Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588057,726646,0,547490,TO_TIMESTAMP('2024-03-23 11:17:01.574','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.postfinance','','Y','Y','N','N','N','N','N','Probleme',TO_TIMESTAMP('2024-03-23 11:17:01.574','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726646 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.667Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887)
;

-- 2024-03-23T10:17:01.673Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726646
;

-- 2024-03-23T10:17:01.674Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726646)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> PostFinance Transaktions Id
-- Column: C_Doc_Outbound_Log_PostFinance_Log.PostFinance_Transaction_Id
-- 2024-03-23T10:17:01.764Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588058,726647,0,547490,TO_TIMESTAMP('2024-03-23 11:17:01.676','YYYY-MM-DD HH24:MI:SS.US'),100,50,'de.metas.postfinance','Y','Y','N','N','N','N','N','PostFinance Transaktions Id',TO_TIMESTAMP('2024-03-23 11:17:01.676','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.766Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726647 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.768Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583054)
;

-- 2024-03-23T10:17:01.771Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726647
;

-- 2024-03-23T10:17:01.772Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726647)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> C_Doc_Outbound_Log
-- Column: C_Doc_Outbound_Log_PostFinance_Log.C_Doc_Outbound_Log_ID
-- 2024-03-23T10:17:01.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588059,726648,0,547490,TO_TIMESTAMP('2024-03-23 11:17:01.774','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.postfinance','Y','Y','N','N','N','N','N','C_Doc_Outbound_Log',TO_TIMESTAMP('2024-03-23 11:17:01.774','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-23T10:17:01.883Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726648 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-23T10:17:01.885Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541944)
;

-- 2024-03-23T10:17:01.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726648
;

-- 2024-03-23T10:17:01.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726648)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> C_Doc_Outbound_Log
-- Column: C_Doc_Outbound_Log_PostFinance_Log.C_Doc_Outbound_Log_ID
-- 2024-03-23T10:17:35.932Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-03-23 11:17:35.932','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726648
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Mandant
-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Client_ID
-- 2024-03-23T10:17:36.819Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-03-23 11:17:36.819','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726636
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Organisation
-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Org_ID
-- 2024-03-23T10:17:40.406Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-03-23 11:17:40.406','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726637
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Erstellt durch
-- Column: C_Doc_Outbound_Log_PostFinance_Log.CreatedBy
-- 2024-03-23T10:17:44.700Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-03-23 11:17:44.7','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726639
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Aktiv
-- Column: C_Doc_Outbound_Log_PostFinance_Log.IsActive
-- 2024-03-23T10:17:46.554Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-03-23 11:17:46.553','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726640
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Aktualisiert
-- Column: C_Doc_Outbound_Log_PostFinance_Log.Updated
-- 2024-03-23T10:17:47.357Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-03-23 11:17:47.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726641
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> Aktualisiert durch
-- Column: C_Doc_Outbound_Log_PostFinance_Log.UpdatedBy
-- 2024-03-23T10:17:55.329Z
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-03-23 11:17:55.329','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726642
;

-- Tab: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance)
-- UI Section: main
-- 2024-03-23T10:18:33.579Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547490,546069,TO_TIMESTAMP('2024-03-23 11:18:33.417','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-03-23 11:18:33.417','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-03-23T10:18:33.585Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546069 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main
-- UI Column: 10
-- 2024-03-23T10:18:57.257Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547416,546069,TO_TIMESTAMP('2024-03-23 11:18:57.136','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-03-23 11:18:57.136','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10
-- UI Element Group: default
-- 2024-03-23T10:19:09.063Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547416,551714,TO_TIMESTAMP('2024-03-23 11:19:08.956','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,TO_TIMESTAMP('2024-03-23 11:19:08.956','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Erstellt
-- Column: C_Doc_Outbound_Log_PostFinance_Log.Created
-- 2024-03-23T10:20:07.370Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726638,0,547490,551714,623808,'F',TO_TIMESTAMP('2024-03-23 11:20:07.232','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','N','N',0,'Erstellt',10,0,0,TO_TIMESTAMP('2024-03-23 11:20:07.232','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Message Text
-- Column: C_Doc_Outbound_Log_PostFinance_Log.MsgText
-- 2024-03-23T10:20:32.241Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726645,0,547490,551714,623809,'F',TO_TIMESTAMP('2024-03-23 11:20:32.147','YYYY-MM-DD HH24:MI:SS.US'),100,'Textual Informational, Menu or Error Message','The Message Text indicates the message that will display ','Y','N','N','Y','N','N','N',0,'Message Text',20,0,0,TO_TIMESTAMP('2024-03-23 11:20:32.147','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Fehler
-- Column: C_Doc_Outbound_Log_PostFinance_Log.IsError
-- 2024-03-23T10:20:45.801Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726644,0,547490,551714,623810,'F',TO_TIMESTAMP('2024-03-23 11:20:45.65','YYYY-MM-DD HH24:MI:SS.US'),100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','N','Y','N','N','N',0,'Fehler',30,0,0,TO_TIMESTAMP('2024-03-23 11:20:45.65','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Probleme
-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Issue_ID
-- 2024-03-23T10:21:37.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726646,0,547490,551714,623811,'F',TO_TIMESTAMP('2024-03-23 11:21:36.743','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Probleme',40,0,0,TO_TIMESTAMP('2024-03-23 11:21:36.743','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.PostFinance Transaktions Id
-- Column: C_Doc_Outbound_Log_PostFinance_Log.PostFinance_Transaction_Id
-- 2024-03-23T10:23:05.901Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726647,0,547490,551714,623812,'F',TO_TIMESTAMP('2024-03-23 11:23:05.705','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'PostFinance Transaktions Id',50,0,0,TO_TIMESTAMP('2024-03-23 11:23:05.705','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Erstellt
-- Column: C_Doc_Outbound_Log_PostFinance_Log.Created
-- 2024-03-23T10:23:28.297Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-03-23 11:23:28.297','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623808
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Message Text
-- Column: C_Doc_Outbound_Log_PostFinance_Log.MsgText
-- 2024-03-23T10:23:28.303Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-03-23 11:23:28.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623809
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Fehler
-- Column: C_Doc_Outbound_Log_PostFinance_Log.IsError
-- 2024-03-23T10:23:28.310Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-03-23 11:23:28.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623810
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.Probleme
-- Column: C_Doc_Outbound_Log_PostFinance_Log.AD_Issue_ID
-- 2024-03-23T10:23:28.316Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-03-23 11:23:28.316','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623811
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> PostFinance Log(547490,de.metas.postfinance) -> main -> 10 -> default.PostFinance Transaktions Id
-- Column: C_Doc_Outbound_Log_PostFinance_Log.PostFinance_Transaction_Id
-- 2024-03-23T10:23:28.321Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-03-23 11:23:28.321','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623812
;

