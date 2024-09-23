
-- 2024-09-23T07:50:00.875Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583279,0,'PP_Order_Candidate_Parent_ID',TO_TIMESTAMP('2024-09-23 09:50:00','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Produktionskandidat automatisch erstellt wurde, um eine Komponente für einen anderen Produktionskandidaten herzustellen, dann enthält diese Feld eine Referenz auf den anderen Produktionskandidaten.','EE01','Y','Eltern-Kandidat','Eltern-Kandidat',TO_TIMESTAMP('2024-09-23 09:50:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-23T07:50:00.885Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583279 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PP_Order_Candidate_Parent_ID
-- 2024-09-23T07:50:06.532Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-23 09:50:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583279 AND AD_Language='de_CH'
;

-- 2024-09-23T07:50:06.566Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583279,'de_CH') 
;

-- Element: PP_Order_Candidate_Parent_ID
-- 2024-09-23T07:50:08.732Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-23 09:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583279 AND AD_Language='de_DE'
;

-- 2024-09-23T07:50:08.737Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583279,'de_DE') 
;

-- 2024-09-23T07:50:08.742Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583279,'de_DE') 
;

-- Element: PP_Order_Candidate_Parent_ID
-- 2024-09-23T07:51:25.553Z
UPDATE AD_Element_Trl SET Description='If this production candidate was automatically created to produce a component for another production candidate, then this field contains a reference to the other production candidate.', IsTranslated='Y', Name='Parent-Candidate', PrintName='Parent-Candidate',Updated=TO_TIMESTAMP('2024-09-23 09:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583279 AND AD_Language='en_US'
;

-- 2024-09-23T07:51:25.559Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583279,'en_US') 
;

-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- 2024-09-23T07:52:35.791Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589156,583279,0,18,541494,541913,'XX','PP_Order_Candidate_Parent_ID',TO_TIMESTAMP('2024-09-23 09:52:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Wenn dieser Produktionskandidat automatisch erstellt wurde, um eine Komponente für einen anderen Produktionskandidaten herzustellen, dann enthält diese Feld eine Referenz auf den anderen Produktionskandidaten.','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eltern-Kandidat',0,0,TO_TIMESTAMP('2024-09-23 09:52:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-23T07:52:35.795Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589156 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-23T07:52:35.803Z
/* DDL */  select update_Column_Translation_From_AD_Element(583279) 
;

-- Field: Produktionsdisposition -> Produktionsdisposition -> Eltern-Kandidat
-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- Field: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> Eltern-Kandidat
-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- 2024-09-23T07:53:53.169Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589156,730941,0,544794,0,TO_TIMESTAMP('2024-09-23 09:53:53','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Produktionskandidat automatisch erstellt wurde, um eine Komponente für einen anderen Produktionskandidaten herzustellen, dann enthält diese Feld eine Referenz auf den anderen Produktionskandidaten.',0,'EE01',0,'Y','Y','Y','N','N','N','Y','N','Eltern-Kandidat',0,20,0,1,1,TO_TIMESTAMP('2024-09-23 09:53:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-23T07:53:53.174Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-23T07:53:53.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583279) 
;

-- 2024-09-23T07:53:53.194Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730941
;

-- 2024-09-23T07:53:53.204Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730941)
;

-- UI Element: Produktionsdisposition -> Produktionsdisposition.PP_Order_Candidate_Parent_ID
-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 20 -> bp.PP_Order_Candidate_Parent_ID
-- Column: PP_Order_Candidate.PP_Order_Candidate_Parent_ID
-- 2024-09-23T07:55:28.791Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730941,0,544794,547108,625809,'F',TO_TIMESTAMP('2024-09-23 09:55:28','YYYY-MM-DD HH24:MI:SS'),100,'Wenn dieser Produktionskandidat automatisch erstellt wurde, um eine Komponente für einen anderen Produktionskandidaten herzustellen, dann enthält diese Feld eine Referenz auf den anderen Produktionskandidaten.','Y','N','N','Y','N','N','N',0,'PP_Order_Candidate_Parent_ID',10,0,0,TO_TIMESTAMP('2024-09-23 09:55:28','YYYY-MM-DD HH24:MI:SS'),100)
;

