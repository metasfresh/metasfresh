-- 2023-08-17T13:49:00.769763900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582652,0,'IsInterimInvoiceable',TO_TIMESTAMP('2023-08-17 16:49:00.593','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Für Zwischenabrechnung freigegeben','Für Zwischenabrechnung freigegeben',TO_TIMESTAMP('2023-08-17 16:49:00.593','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T13:49:00.790149100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582652 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsInterimInvoiceable
-- 2023-08-17T13:49:10.257793Z
UPDATE AD_Element_Trl SET Name='Approved for interim invoicing', PrintName='Approved for interim invoicing',Updated=TO_TIMESTAMP('2023-08-17 16:49:10.257','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582652 AND AD_Language='en_US'
;

-- 2023-08-17T13:49:10.286144600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582652,'en_US') 
;

-- Column: M_InOut.IsInterimInvoiceable
-- 2023-08-17T13:52:36.756625200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587301,582652,0,20,319,'IsInterimInvoiceable',TO_TIMESTAMP('2023-08-17 16:52:36.599','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Für Zwischenabrechnung freigegeben','1=1',0,0,TO_TIMESTAMP('2023-08-17 16:52:36.599','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-17T13:52:36.760657700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587301 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-17T13:52:37.297845200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582652) 
;

-- 2023-08-17T13:52:41.966595800Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN IsInterimInvoiceable CHAR(1) DEFAULT ''Y'' CHECK (IsInterimInvoiceable IN (''Y'',''N''))')
;

-- Field: Wareneingang(541532,D) -> Wareneingang(546374,D) -> Für Zwischenabrechnung freigegeben
-- Column: M_InOut.IsInterimInvoiceable
-- 2023-08-17T13:54:00.056457400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587301,720167,0,546374,TO_TIMESTAMP('2023-08-17 16:53:59.849','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Für Zwischenabrechnung freigegeben',TO_TIMESTAMP('2023-08-17 16:53:59.849','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T13:54:00.059466500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-17T13:54:00.064992500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582652) 
;

-- 2023-08-17T13:54:00.081943500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720167
;

-- 2023-08-17T13:54:00.086099200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720167)
;

-- UI Element: Wareneingang(541532,D) -> Wareneingang(546374,D) -> main -> 10 -> default.Für Zwischenabrechnung freigegeben
-- Column: M_InOut.IsInterimInvoiceable
-- 2023-08-17T13:54:22.648672200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720167,0,546374,620350,549355,'F',TO_TIMESTAMP('2023-08-17 16:54:22.472','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Für Zwischenabrechnung freigegeben',50,0,0,TO_TIMESTAMP('2023-08-17 16:54:22.472','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T14:52:43.172295100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582653,0,'IsBillable',TO_TIMESTAMP('2023-08-17 17:52:42.982','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Abrechenbar','Abrechenbar',TO_TIMESTAMP('2023-08-17 17:52:42.982','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T14:52:43.175450600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582653 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsBillable
-- 2023-08-17T14:52:53.849144600Z
UPDATE AD_Element_Trl SET Name='Invoiceable', PrintName='Invoiceable',Updated=TO_TIMESTAMP('2023-08-17 17:52:53.849','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582653 AND AD_Language='en_US'
;

-- 2023-08-17T14:52:53.851143900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582653,'en_US') 
;

-- Column: ModCntr_Log.IsBillable
-- 2023-08-17T14:53:54.914634600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587302,582653,0,20,542338,'IsBillable',TO_TIMESTAMP('2023-08-17 17:53:54.784','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','de.metas.contracts',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abrechenbar',0,0,TO_TIMESTAMP('2023-08-17 17:53:54.784','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-17T14:53:54.917633Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587302 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-17T14:53:55.688459400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582653) 
;

-- 2023-08-17T14:53:58.939786Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN IsBillable CHAR(1) DEFAULT ''Y'' CHECK (IsBillable IN (''Y'',''N''))')
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Abrechenbar
-- Column: ModCntr_Log.IsBillable
-- 2023-08-17T14:54:28.815900300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587302,720168,0,547012,TO_TIMESTAMP('2023-08-17 17:54:28.682','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.contracts','Y','N','N','N','N','N','N','N','Abrechenbar',TO_TIMESTAMP('2023-08-17 17:54:28.682','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T14:54:28.819648600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720168 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-17T14:54:28.822115500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582653) 
;

-- 2023-08-17T14:54:28.832468500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720168
;

-- 2023-08-17T14:54:28.838747500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720168)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> flags.Abrechenbar
-- Column: ModCntr_Log.IsBillable
-- 2023-08-17T14:54:58.018450300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720168,0,547012,620351,550778,'F',TO_TIMESTAMP('2023-08-17 17:54:57.883','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Abrechenbar',40,0,0,TO_TIMESTAMP('2023-08-17 17:54:57.883','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Value: M_InOut_ReadyForInterimInvoicing
-- Classname: de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing
-- 2023-08-17T15:20:50.002054400Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585306,'Y','de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing','N',TO_TIMESTAMP('2023-08-17 18:20:49.854','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Für Zwischenabrechnung freigeben','json','N','N','xls','Java',TO_TIMESTAMP('2023-08-17 18:20:49.854','YYYY-MM-DD HH24:MI:SS.US'),100,'M_InOut_ReadyForInterimInvoicing')
;

-- 2023-08-17T15:20:50.010047800Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585306 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: M_InOut_ReadyForInterimInvoicing(de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing)
-- ParameterName: IsInterimInvoiceable
-- 2023-08-17T15:22:35.952772300Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582652,0,585306,542680,20,'IsInterimInvoiceable',TO_TIMESTAMP('2023-08-17 18:22:35.809','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','D',0,'Y','N','Y','N','N','N','Für Zwischenabrechnung freigegeben',10,TO_TIMESTAMP('2023-08-17 18:22:35.809','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-17T15:22:35.957030900Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542680 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-08-17T15:22:35.960029100Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582652) 
;

-- Process: M_InOut_ReadyForInterimInvoicing(de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing)
-- Table: M_InOut
-- Window: Wareneingang(541532,D)
-- EntityType: D
-- 2023-08-17T15:25:25.424222800Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585306,319,541408,541532,TO_TIMESTAMP('2023-08-17 18:25:25.295','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2023-08-17 18:25:25.295','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','Y','N')
;

-- Process: M_InOut_ReadyForInterimInvoicing(de.metas.contracts.process.M_InOut_ReadyForInterimInvoicing)
-- 2023-08-18T09:40:43.369614500Z
UPDATE AD_Process_Trl SET Name='Approve for interim invoicing',Updated=TO_TIMESTAMP('2023-08-18 12:40:43.369','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585306
;

