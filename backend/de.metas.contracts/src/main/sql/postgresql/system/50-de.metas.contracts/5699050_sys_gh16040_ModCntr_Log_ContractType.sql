-- 2023-08-10T16:30:09.239085700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582640,0,'ContractType',TO_TIMESTAMP('2023-08-10 19:30:09.063','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Vertragsart','Vertragsart',TO_TIMESTAMP('2023-08-10 19:30:09.063','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-10T16:30:09.715427600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582640 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ContractType
-- 2023-08-10T16:30:34.648715800Z
UPDATE AD_Element_Trl SET Name='Contract type', PrintName='Contract type',Updated=TO_TIMESTAMP('2023-08-10 19:30:34.648','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582640 AND AD_Language='en_US'
;

-- 2023-08-10T16:30:34.694709700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582640,'en_US') 
;

-- Name: ModCntr_Log_ContractType
-- 2023-08-10T16:31:40.800585700Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541814,TO_TIMESTAMP('2023-08-10 19:31:40.673','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_ContractType',TO_TIMESTAMP('2023-08-10 19:31:40.673','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-08-10T16:31:40.803584500Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541814 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_ContractType
-- Value: Interim
-- ValueName: Interim
-- 2023-08-10T16:33:07.922440900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543535,541814,TO_TIMESTAMP('2023-08-10 19:33:07.772','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Interim',TO_TIMESTAMP('2023-08-10 19:33:07.772','YYYY-MM-DD HH24:MI:SS.US'),100,'Interim','Interim')
;

-- 2023-08-10T16:33:07.925479300Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543535 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: ModCntr_Log_ContractType
-- Value: ModularContract
-- ValueName: Modular Contract
-- 2023-08-10T16:33:32.085558200Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543536,541814,TO_TIMESTAMP('2023-08-10 19:33:31.976','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Modular Contract',TO_TIMESTAMP('2023-08-10 19:33:31.976','YYYY-MM-DD HH24:MI:SS.US'),100,'ModularContract','Modular Contract')
;

-- 2023-08-10T16:33:32.087592800Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543536 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: ModCntr_Log_ContractType -> ModularContract_Modular Contract
-- 2023-08-10T16:33:36.333165700Z
UPDATE AD_Ref_List_Trl SET Name='Modularer Vertrag',Updated=TO_TIMESTAMP('2023-08-10 19:33:36.332','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543536
;

-- Reference Item: ModCntr_Log_ContractType -> ModularContract_Modular Contract
-- 2023-08-10T16:33:39.728473700Z
UPDATE AD_Ref_List_Trl SET Name='Modularer Vertrag',Updated=TO_TIMESTAMP('2023-08-10 19:33:39.728','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543536
;

-- 2023-08-10T16:33:39.733474100Z
UPDATE AD_Ref_List SET Name='Modularer Vertrag' WHERE AD_Ref_List_ID=543536
;

-- Column: ModCntr_Log.ContractType
-- 2023-08-10T16:35:18.535893600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587273,582640,0,17,541814,542338,'ContractType',TO_TIMESTAMP('2023-08-10 19:35:18.397','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vertragsart',0,0,TO_TIMESTAMP('2023-08-10 19:35:18.397','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-10T16:35:18.537893600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587273 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-10T16:35:19.367897400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582640) 
;

-- 2023-08-10T16:35:22.229467900Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Log','ALTER TABLE public.ModCntr_Log ADD COLUMN ContractType VARCHAR(250)')
;

-- 2023-08-10T16:53:54.320432400Z
INSERT INTO t_alter_column values('modcntr_log','ContractType','VARCHAR(250)',null,null)
;

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Vertragsart
-- Column: ModCntr_Log.ContractType
-- 2023-08-10T16:38:20.196519800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587273,720148,0,547012,TO_TIMESTAMP('2023-08-10 19:38:20.045','YYYY-MM-DD HH24:MI:SS.US'),100,250,'de.metas.contracts','Y','N','N','N','N','N','N','N','Vertragsart',TO_TIMESTAMP('2023-08-10 19:38:20.045','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-10T16:38:20.200069Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-10T16:38:20.203067400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582640) 
;

-- 2023-08-10T16:38:20.226063900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720148
;

-- 2023-08-10T16:38:20.233221Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720148)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Vertragsart
-- Column: ModCntr_Log.ContractType
-- 2023-08-10T16:39:05.302043900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720148,0,547012,620334,550777,'F',TO_TIMESTAMP('2023-08-10 19:39:05.16','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Vertragsart',35,0,0,TO_TIMESTAMP('2023-08-10 19:39:05.16','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 10 -> details.Vertragsart
-- Column: ModCntr_Log.ContractType
-- 2023-08-10T16:40:18.397981600Z
UPDATE AD_UI_Element SET SeqNo=16,Updated=TO_TIMESTAMP('2023-08-10 19:40:18.397','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620334
;

-- Element: ContractType
-- 2023-08-10T16:41:10.083398800Z
UPDATE AD_Element_Trl SET Name='Contract Type', PrintName='Contract Type',Updated=TO_TIMESTAMP('2023-08-10 19:41:10.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582640 AND AD_Language='en_US'
;

-- 2023-08-10T16:41:10.085402600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582640,'en_US') 
;

-- 2023-08-10T16:53:14.711449700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582641,0,'t',TO_TIMESTAMP('2023-08-10 19:53:14.513','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','t','t',TO_TIMESTAMP('2023-08-10 19:53:14.513','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-10T16:53:14.713453500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582641 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_Log.ContractType
-- 2023-08-10T16:54:25.267097Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-08-10 19:54:25.266','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587273
;

-- Window: Log für modulare Verträge, InternalName=ModCntr_Log
-- 2023-08-10T17:20:12.343350500Z
UPDATE AD_Window SET WindowType='T',Updated=TO_TIMESTAMP('2023-08-10 20:20:12.342','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541711
;

-- Tab: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log
-- Table: ModCntr_Log
-- 2023-08-10T17:53:33.606476500Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-08-10 20:53:33.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547012
;

