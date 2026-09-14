-- Column: C_Invoice_Candidate.ActualLoadingDate
-- 2023-02-17T12:14:07.459Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586144,581689,0,15,540270,'ActualLoadingDate',TO_TIMESTAMP('2023-02-17 14:14:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Verladedatum',0,0,TO_TIMESTAMP('2023-02-17 14:14:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-17T12:14:07.463Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586144 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-17T12:14:07.492Z
/* DDL */  select update_Column_Translation_From_AD_Element(581689) 
;

-- 2023-02-17T12:14:12.633Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN ActualLoadingDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: C_Invoice_Candidate.ActualLoadingDate
-- 2023-02-17T13:17:03.678Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-02-17 15:17:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586144
;


-- Field: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> Act Load Date
-- Column: C_Invoice_Candidate.ActualLoadingDate
-- 2023-02-17T13:26:43.416Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586144,712633,0,540279,0,TO_TIMESTAMP('2023-02-17 15:26:42','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','Act Load Date',0,570,0,1,1,TO_TIMESTAMP('2023-02-17 15:26:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-17T13:26:43.448Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712633 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-17T13:26:43.480Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581689)
;

-- 2023-02-17T13:26:43.514Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712633
;

-- 2023-02-17T13:26:43.546Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712633)
;

-- UI Element: Sales Invoice Candidates_OLD(540092,de.metas.invoicecandidate) -> Invoice Candidates(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Act Load Date
-- Column: C_Invoice_Candidate.ActualLoadingDate
-- 2023-02-17T13:27:46.248Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712633,0,540279,540056,615855,'F',TO_TIMESTAMP('2023-02-17 15:27:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Act Load Date',195,0,0,TO_TIMESTAMP('2023-02-17 15:27:45','YYYY-MM-DD HH24:MI:SS'),100)
;
