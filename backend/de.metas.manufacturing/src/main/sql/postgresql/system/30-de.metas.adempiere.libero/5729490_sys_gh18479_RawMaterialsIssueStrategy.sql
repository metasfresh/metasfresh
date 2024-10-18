-- 2024-07-17T12:58:53.786Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583189,0,'RawMaterialsIssueStrategy',TO_TIMESTAMP('2024-07-17 15:58:53','YYYY-MM-DD HH24:MI:SS'),100,'Issue strategy for raw materials','U','Y','Issue strategy','Issue strategy',TO_TIMESTAMP('2024-07-17 15:58:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-17T12:58:53.799Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583189 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: RawMaterialsIssueStrategy
-- 2024-07-17T12:59:01.805Z
UPDATE AD_Element_Trl SET Description='Verbrauchsstrategie', Name='Verbrauchsstrategie', PrintName='Verbrauchsstrategie',Updated=TO_TIMESTAMP('2024-07-17 15:59:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583189 AND AD_Language='de_CH'
;

-- 2024-07-17T12:59:01.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583189,'de_CH') 
;

-- Element: RawMaterialsIssueStrategy
-- 2024-07-17T12:59:06.421Z
UPDATE AD_Element_Trl SET Description='Verbrauchsstrategie', Name='Verbrauchsstrategie', PrintName='Verbrauchsstrategie',Updated=TO_TIMESTAMP('2024-07-17 15:59:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583189 AND AD_Language='de_DE'
;

-- 2024-07-17T12:59:06.422Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583189,'de_DE') 
;

-- 2024-07-17T12:59:06.435Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583189,'de_DE') 
;

-- 2024-07-17T12:59:21.869Z
UPDATE AD_Element SET EntityType='EE01',Updated=TO_TIMESTAMP('2024-07-17 15:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583189
;

-- 2024-07-17T12:59:21.870Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583189,'de_DE') 
;

-- Element: RawMaterialsIssueStrategy
-- 2024-07-17T12:59:33.422Z
UPDATE AD_Element_Trl SET Description='Verbrauchsstrategie', Name='Verbrauchsstrategie', PrintName='Verbrauchsstrategie',Updated=TO_TIMESTAMP('2024-07-17 15:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583189 AND AD_Language='fr_CH'
;

-- 2024-07-17T12:59:33.423Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583189,'fr_CH') 
;

-- Name: RawMaterialsIssueStrategy
-- 2024-07-17T13:00:29.007Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541877,TO_TIMESTAMP('2024-07-17 16:00:28','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','RawMaterialsIssueStrategy',TO_TIMESTAMP('2024-07-17 16:00:28','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-07-17T13:00:29.012Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541877 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: RawMaterialsIssueStrategy
-- Value: OnlyAssignedHU
-- ValueName: Only assigned HUs
-- 2024-07-17T13:01:23.550Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543706,541877,TO_TIMESTAMP('2024-07-17 16:01:23','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Only assigned HUs',TO_TIMESTAMP('2024-07-17 16:01:23','YYYY-MM-DD HH24:MI:SS'),100,'OnlyAssignedHU','Only assigned HUs')
;

-- 2024-07-17T13:01:23.552Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543706 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: RawMaterialsIssueStrategy
-- Value: OnlyAssignedHU
-- ValueName: Only assigned HUs
-- 2024-07-17T13:03:31.787Z
UPDATE AD_Ref_List SET Description='Only assigned HUs (via PP_Order_SourceHU) can be issued',Updated=TO_TIMESTAMP('2024-07-17 16:03:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> OnlyAssignedHU_Only assigned HUs
-- 2024-07-17T13:04:03.272Z
UPDATE AD_Ref_List_Trl SET Name='Nur zugewiesene HUs',Updated=TO_TIMESTAMP('2024-07-17 16:04:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> OnlyAssignedHU_Only assigned HUs
-- 2024-07-17T13:04:05.457Z
UPDATE AD_Ref_List_Trl SET Name='Nur zugewiesene HUs',Updated=TO_TIMESTAMP('2024-07-17 16:04:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> OnlyAssignedHU_Only assigned HUs
-- 2024-07-17T13:04:09.369Z
UPDATE AD_Ref_List_Trl SET Name='Nur zugewiesene HUs',Updated=TO_TIMESTAMP('2024-07-17 16:04:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543706
;

-- Reference: RawMaterialsIssueStrategy
-- Value: AssignedHUsOnly
-- ValueName: Only assigned HUs
-- 2024-07-17T13:04:33.227Z
UPDATE AD_Ref_List SET Name='Assigned HUs only', Value='AssignedHUsOnly',Updated=TO_TIMESTAMP('2024-07-17 16:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543706
;

-- Reference: RawMaterialsIssueStrategy
-- Value: AssignedHUsOnly
-- ValueName: Only assigned HUs
-- 2024-07-17T13:05:03.784Z
UPDATE AD_Ref_List SET Description='Nur zugeordnete HUs (über PP_Order_SourceHU) können verbraucht werden',Updated=TO_TIMESTAMP('2024-07-17 16:05:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AssignedHUsOnly_Only assigned HUs
-- 2024-07-17T13:05:06.049Z
UPDATE AD_Ref_List_Trl SET Description='Nur zugeordnete HUs (über PP_Order_SourceHU) können verbraucht werden',Updated=TO_TIMESTAMP('2024-07-17 16:05:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AssignedHUsOnly_Only assigned HUs
-- 2024-07-17T13:05:09.394Z
UPDATE AD_Ref_List_Trl SET Description='Nur zugeordnete HUs (über PP_Order_SourceHU) können verbraucht werden',Updated=TO_TIMESTAMP('2024-07-17 16:05:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AssignedHUsOnly_Only assigned HUs
-- 2024-07-17T13:05:11.817Z
UPDATE AD_Ref_List_Trl SET Description='Nur zugeordnete HUs (über PP_Order_SourceHU) können verbraucht werden',Updated=TO_TIMESTAMP('2024-07-17 16:05:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543706
;

-- Reference: RawMaterialsIssueStrategy
-- Value: AssignedHUsOnly
-- ValueName: Only assigned HUs
-- 2024-07-17T13:05:18.101Z
UPDATE AD_Ref_List SET Description='Only allocated HUs (via PP_Order_SourceHU) can be consumed',Updated=TO_TIMESTAMP('2024-07-17 16:05:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543706
;

-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- 2024-07-17T13:07:44.469Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588855,583189,0,17,541877,129,'XX','RawMaterialsIssueStrategy',TO_TIMESTAMP('2024-07-17 16:07:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Verbrauchsstrategie','EE01',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verbrauchsstrategie',0,0,TO_TIMESTAMP('2024-07-17 16:07:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-17T13:07:44.472Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-17T13:07:44.478Z
/* DDL */  select update_Column_Translation_From_AD_Element(583189) 
;

-- 2024-07-17T13:07:47.897Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Node','ALTER TABLE public.AD_WF_Node ADD COLUMN RawMaterialsIssueStrategy VARCHAR(255)')
;

-- 2024-07-17T13:09:54.004Z
INSERT INTO t_alter_column values('ad_wf_node','RawMaterialsIssueStrategy','VARCHAR(255)',null,null)
;

-- Column: PP_Order_Node.RawMaterialsIssueStrategy
-- Column: PP_Order_Node.RawMaterialsIssueStrategy
-- 2024-07-17T13:13:39.889Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588856,583189,0,17,541877,53022,'XX','RawMaterialsIssueStrategy',TO_TIMESTAMP('2024-07-17 16:13:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Verbrauchsstrategie','EE01',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verbrauchsstrategie',0,0,TO_TIMESTAMP('2024-07-17 16:13:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-17T13:13:39.892Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-17T13:13:39.896Z
/* DDL */  select update_Column_Translation_From_AD_Element(583189) 
;

-- 2024-07-17T13:13:42.067Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Node','ALTER TABLE public.PP_Order_Node ADD COLUMN RawMaterialsIssueStrategy VARCHAR(255)')
;

-- Field: Produktion Arbeitsablauf -> Arbeitsschritt -> Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- Field: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- 2024-07-17T14:33:17.602Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588855,729098,0,53025,TO_TIMESTAMP('2024-07-17 17:33:17','YYYY-MM-DD HH24:MI:SS'),100,'Verbrauchsstrategie',255,'EE01','Y','N','N','N','N','N','N','N','Verbrauchsstrategie',TO_TIMESTAMP('2024-07-17 17:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-17T14:33:17.606Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-17T14:33:17.610Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583189) 
;

-- 2024-07-17T14:33:17.624Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729098
;

-- 2024-07-17T14:33:17.629Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729098)
;

-- UI Element: Produktion Arbeitsablauf -> Arbeitsschritt.Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- UI Element: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> main -> 10 -> default.Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- 2024-07-17T14:34:39.250Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729098,0,53025,625012,540370,'F',TO_TIMESTAMP('2024-07-17 17:34:39','YYYY-MM-DD HH24:MI:SS'),100,'Verbrauchsstrategie','Y','N','N','Y','N','N','N',0,'Verbrauchsstrategie',170,0,0,TO_TIMESTAMP('2024-07-17 17:34:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Produktion Arbeitsablauf -> Arbeitsschritt -> Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- Field: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- 2024-07-17T14:39:37.077Z
UPDATE AD_Field SET DisplayLogic='@PP_Activity_Type@ = ''IssueOnlyWhatWasReceived'' | @PP_Activity_Type@ = ''MI''',Updated=TO_TIMESTAMP('2024-07-17 17:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729098
;

-- UI Element: Produktion Arbeitsablauf -> Arbeitsschritt.Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- UI Element: Produktion Arbeitsablauf(53005,EE01) -> Arbeitsschritt(53025,EE01) -> main -> 10 -> default.Verbrauchsstrategie
-- Column: AD_WF_Node.RawMaterialsIssueStrategy
-- 2024-07-17T14:43:27.153Z
UPDATE AD_UI_Element SET SeqNo=145,Updated=TO_TIMESTAMP('2024-07-17 17:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625012
;

-- Reference: RawMaterialsIssueStrategy
-- Value: AllocatedSourceHUsOnly
-- ValueName: Only assigned HUs
-- 2024-07-17T14:46:30.516Z
UPDATE AD_Ref_List SET Description='Only allocated source HUs (via PP_Order_SourceHU) can be consumed for ''IssueOnlyForReceived'' method', Name='Only allocated source HUs', Value='AllocatedSourceHUsOnly',Updated=TO_TIMESTAMP('2024-07-17 17:46:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AllocatedSourceHUsOnly_Only assigned HUs
-- 2024-07-17T14:46:38.216Z
UPDATE AD_Ref_List_Trl SET Name='Only allocated source HUs',Updated=TO_TIMESTAMP('2024-07-17 17:46:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AllocatedSourceHUsOnly_Only assigned HUs
-- 2024-07-17T14:46:42.746Z
UPDATE AD_Ref_List_Trl SET Description='Only allocated source HUs (via PP_Order_SourceHU) can be consumed for ''IssueOnlyForReceived'' method',Updated=TO_TIMESTAMP('2024-07-17 17:46:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AllocatedSourceHUsOnly_Only assigned HUs
-- 2024-07-17T14:47:25.977Z
UPDATE AD_Ref_List_Trl SET Description='Nur zugeordnete Quell-HUs (über PP_Order_SourceHU) können für die Methode ''IssueOnlyForReceived'' verbraucht werden',Updated=TO_TIMESTAMP('2024-07-17 17:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AllocatedSourceHUsOnly_Only assigned HUs
-- 2024-07-17T14:47:29.162Z
UPDATE AD_Ref_List_Trl SET Description='Nur zugeordnete Quell-HUs (über PP_Order_SourceHU) können für die Methode ''IssueOnlyForReceived'' verbraucht werden',Updated=TO_TIMESTAMP('2024-07-17 17:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543706
;

-- Reference Item: RawMaterialsIssueStrategy -> AllocatedSourceHUsOnly_Only assigned HUs
-- 2024-07-17T14:47:34.525Z
UPDATE AD_Ref_List_Trl SET Description='Nur zugeordnete Quell-HUs (über PP_Order_SourceHU) können für die Methode ''IssueOnlyForReceived'' verbraucht werden',Updated=TO_TIMESTAMP('2024-07-17 17:47:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543706
;

