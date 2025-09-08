-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- 2024-07-22T06:13:51.066Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588867,580109,0,30,540821,'XX','PP_Order_Candidate_ID',TO_TIMESTAMP('2024-07-22 09:13:50','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produktionsdisposition',0,0,TO_TIMESTAMP('2024-07-22 09:13:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-22T06:13:51.069Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588867 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-22T06:13:51.098Z
/* DDL */  select update_Column_Translation_From_AD_Element(580109) 
;

-- 2024-07-22T06:13:52.949Z
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Dist_Detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN PP_Order_Candidate_ID NUMERIC(10)')
;

-- 2024-07-22T06:13:52.956Z
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT PPOrderCandidate_MDCandidateDistDetail FOREIGN KEY (PP_Order_Candidate_ID) REFERENCES public.PP_Order_Candidate DEFERRABLE INITIALLY DEFERRED
;

-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- 2024-07-22T06:14:18.394Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588868,580110,0,30,541494,540821,'XX','PP_OrderLine_Candidate_ID',TO_TIMESTAMP('2024-07-22 09:14:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Manufacturing Order Line Candidate',0,0,TO_TIMESTAMP('2024-07-22 09:14:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-22T06:14:18.395Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588868 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-22T06:14:18.399Z
/* DDL */  select update_Column_Translation_From_AD_Element(580110) 
;

-- 2024-07-22T06:14:18.963Z
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Dist_Detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN PP_OrderLine_Candidate_ID NUMERIC(10)')
;

-- 2024-07-22T06:14:18.971Z
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT PPOrderLineCandidate_MDCandidateDistDetail FOREIGN KEY (PP_OrderLine_Candidate_ID) REFERENCES public.PP_Order_Candidate DEFERRABLE INITIALLY DEFERRED
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- 2024-07-22T06:14:42.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588867,729140,0,540821,TO_TIMESTAMP('2024-07-22 09:14:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Produktionsdisposition',TO_TIMESTAMP('2024-07-22 09:14:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T06:14:42.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-22T06:14:42.250Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580109) 
;

-- 2024-07-22T06:14:42.270Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729140
;

-- 2024-07-22T06:14:42.273Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729140)
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- 2024-07-22T06:14:42.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588868,729141,0,540821,TO_TIMESTAMP('2024-07-22 09:14:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','N','N','N','N','N','N','N','Manufacturing Order Line Candidate',TO_TIMESTAMP('2024-07-22 09:14:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-22T06:14:42.374Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-22T06:14:42.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580110) 
;

-- 2024-07-22T06:14:42.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729141
;

-- 2024-07-22T06:14:42.380Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729141)
;

-- UI Column: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10
-- UI Element Group: org&client
-- 2024-07-22T06:15:01.206Z
UPDATE AD_UI_ElementGroup SET SeqNo=990,Updated=TO_TIMESTAMP('2024-07-22 09:15:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551869
;

-- UI Column: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10
-- UI Element Group: manufacturing
-- 2024-07-22T06:15:03.264Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2024-07-22 09:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551870
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- 2024-07-22T06:15:21.338Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729140,0,540821,551870,625028,'F',TO_TIMESTAMP('2024-07-22 09:15:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produktionsdisposition',30,0,0,TO_TIMESTAMP('2024-07-22 09:15:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- 2024-07-22T06:15:26.841Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729141,0,540821,551870,625029,'F',TO_TIMESTAMP('2024-07-22 09:15:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Manufacturing Order Line Candidate',40,0,0,TO_TIMESTAMP('2024-07-22 09:15:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- 2024-07-22T06:15:40.061Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2024-07-22 09:15:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625028
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- 2024-07-22T06:15:43.707Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-07-22 09:15:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625029
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Produktionsauftrag
-- Column: MD_Candidate_Dist_Detail.PP_Order_ID
-- 2024-07-22T06:15:46.835Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-07-22 09:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625026
;

-- UI Element: Material Disposition -> Bereitstellungsdetail.Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- UI Element: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> main -> 10 -> manufacturing.Manufacturing Order BOM Line
-- Column: MD_Candidate_Dist_Detail.PP_Order_BOMLine_ID
-- 2024-07-22T06:15:49.075Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-07-22 09:15:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625027
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Produktionsdisposition
-- Column: MD_Candidate_Dist_Detail.PP_Order_Candidate_ID
-- 2024-07-22T06:16:23.531Z
UPDATE AD_Field SET DisplayLogic='@PP_Order_Candidate_ID/0@>0',Updated=TO_TIMESTAMP('2024-07-22 09:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729140
;

-- Field: Material Disposition -> Bereitstellungsdetail -> Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- Field: Material Disposition(540334,de.metas.material.dispo) -> Bereitstellungsdetail(540821,de.metas.material.dispo) -> Manufacturing Order Line Candidate
-- Column: MD_Candidate_Dist_Detail.PP_OrderLine_Candidate_ID
-- 2024-07-22T06:16:34.734Z
UPDATE AD_Field SET DisplayLogic='@PP_OrderLine_Candidate_ID/0@>0',Updated=TO_TIMESTAMP('2024-07-22 09:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729141
;






alter table md_candidate_dist_detail drop CONSTRAINT if exists pporderlinecandidate_mdcandidatedistdetail;


