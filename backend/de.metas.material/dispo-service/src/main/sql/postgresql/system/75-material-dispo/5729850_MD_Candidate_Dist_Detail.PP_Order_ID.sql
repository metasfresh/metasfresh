-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- 2024-07-22T05:41:06.951Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588865,53276,0,30,540821,'XX','PP_Order_ID',TO_TIMESTAMP('2024-07-22 08:41:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktionsauftrag',0,0,TO_TIMESTAMP('2024-07-22 08:41:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-22T05:41:06.955Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588865 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-22T05:41:07.002Z
/* DDL */  select update_Column_Translation_From_AD_Element(53276) 
;

-- 2024-07-22T05:41:12.637Z
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Dist_Detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN PP_Order_ID NUMERIC(10)')
;

-- 2024-07-22T05:41:12.666Z
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT PPOrder_MDCandidateDistDetail FOREIGN KEY (PP_Order_ID) REFERENCES public.PP_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- 2024-07-22T05:41:42.492Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588866,53275,0,30,540503,540821,'XX','PP_Order_BOMLine_ID',TO_TIMESTAMP('2024-07-22 08:41:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Manufacturing Order BOM Line',0,0,TO_TIMESTAMP('2024-07-22 08:41:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-22T05:41:42.497Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588866 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-22T05:41:42.504Z
/* DDL */  select update_Column_Translation_From_AD_Element(53275) 
;

-- 2024-07-22T05:41:43.530Z
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Dist_Detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN PP_Order_BOMLine_ID NUMERIC(10)')
;

-- 2024-07-22T05:41:43.538Z
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT PPOrderBOMLine_MDCandidateDistDetail FOREIGN KEY (PP_Order_BOMLine_ID) REFERENCES public.PP_Order DEFERRABLE INITIALLY DEFERRED
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Network Distribution
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistribution_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Network Distribution
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistribution_ID
-- 2024-07-22T05:42:16.575Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588840,729137,0,540821,TO_TIMESTAMP('2024-07-22 08:42:16','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Network Distribution',TO_TIMESTAMP('2024-07-22 08:42:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T05:42:16.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729137 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-22T05:42:16.588Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53340) 
;

-- 2024-07-22T05:42:16.611Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729137
;

-- 2024-07-22T05:42:16.620Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729137)
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- 2024-07-22T05:42:27.789Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588865,729138,0,540821,TO_TIMESTAMP('2024-07-22 08:42:27','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Produktionsauftrag',TO_TIMESTAMP('2024-07-22 08:42:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T05:42:27.793Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729138 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-22T05:42:27.796Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53276) 
;

-- 2024-07-22T05:42:27.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729138
;

-- 2024-07-22T05:42:27.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729138)
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- 2024-07-22T05:42:28.022Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588866,729139,0,540821,TO_TIMESTAMP('2024-07-22 08:42:27','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Manufacturing Order BOM Line',TO_TIMESTAMP('2024-07-22 08:42:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T05:42:28.024Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-22T05:42:28.027Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53275) 
;

-- 2024-07-22T05:42:28.039Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729139
;

-- 2024-07-22T05:42:28.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729139)
;

-- UI Column: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10
-- UI Element Group: org&client
-- 2024-07-22T05:43:28.210Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540674,551869,TO_TIMESTAMP('2024-07-22 08:43:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',20,TO_TIMESTAMP('2024-07-22 08:43:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> org&client.Sektion
-- Column: MD_Candidate_Dist_Detail.AD_Org_ID
-- 2024-07-22T05:43:55.240Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551869, SeqNo=10,Updated=TO_TIMESTAMP('2024-07-22 08:43:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548843
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Mandant
-- Column: MD_Candidate_Dist_Detail.AD_Client_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> org&client.Mandant
-- Column: MD_Candidate_Dist_Detail.AD_Client_ID
-- 2024-07-22T05:44:05.817Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551869, SeqNo=20,Updated=TO_TIMESTAMP('2024-07-22 08:44:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548842
;

-- UI Column: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10
-- UI Element Group: manufacturing
-- 2024-07-22T05:44:51.734Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540674,551870,TO_TIMESTAMP('2024-07-22 08:44:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','manufacturing',30,TO_TIMESTAMP('2024-07-22 08:44:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- 2024-07-22T05:45:04.464Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729138,0,540821,551870,625026,'F',TO_TIMESTAMP('2024-07-22 08:45:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produktionsauftrag',10,0,0,TO_TIMESTAMP('2024-07-22 08:45:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- 2024-07-22T05:45:12.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729139,0,540821,551870,625027,'F',TO_TIMESTAMP('2024-07-22 08:45:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Manufacturing Order BOM Line',20,0,0,TO_TIMESTAMP('2024-07-22 08:45:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- 2024-07-22T05:45:41.179Z
UPDATE AD_Field SET DisplayLogic='@PP_Order_ID/0@>0',Updated=TO_TIMESTAMP('2024-07-22 08:45:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729138
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- 2024-07-22T05:45:53.295Z
UPDATE AD_Field SET DisplayLogic='@PP_Order_BOMLine_ID/0@>0',Updated=TO_TIMESTAMP('2024-07-22 08:45:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729139
;

