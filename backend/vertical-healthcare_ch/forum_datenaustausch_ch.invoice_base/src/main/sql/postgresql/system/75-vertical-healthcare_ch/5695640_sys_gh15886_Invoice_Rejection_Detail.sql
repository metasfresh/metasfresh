-- Column: C_Invoice_Rejection_Detail.DateReceived
-- 2023-07-13T15:50:07.027Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587114,1324,0,15,541365,'DateReceived',TO_TIMESTAMP('2023-07-13 16:50:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, zu dem ein Produkt empfangen wurde','D',0,7,'"Eingangsdatum" bezeichnet das Datum, zu dem das Produkt empfangen wurde','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingangsdatum',0,0,TO_TIMESTAMP('2023-07-13 16:50:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-13T15:50:07.030Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587114 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-13T15:50:07.051Z
/* DDL */  select update_Column_Translation_From_AD_Element(1324) 
;

-- 2023-07-13T15:50:24.991Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Rejection_Detail','ALTER TABLE public.C_Invoice_Rejection_Detail ADD COLUMN DateReceived TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: Zurückweisungsdetail -> Zurückweisungsdetail -> Eingangsdatum
-- Column: C_Invoice_Rejection_Detail.DateReceived
-- 2023-07-13T15:50:51.920Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587114,716661,0,541791,0,TO_TIMESTAMP('2023-07-13 16:50:51','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem ein Produkt empfangen wurde',0,'D','"Eingangsdatum" bezeichnet das Datum, zu dem das Produkt empfangen wurde',0,'Y','Y','Y','N','N','N','N','N','Eingangsdatum',0,180,0,1,1,TO_TIMESTAMP('2023-07-13 16:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-13T15:50:51.921Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716661 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-13T15:50:51.923Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1324) 
;

-- 2023-07-13T15:50:51.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716661
;

-- 2023-07-13T15:50:51.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716661)
;

-- UI Element: Zurückweisungsdetail -> Zurückweisungsdetail.Eingangsdatum
-- Column: C_Invoice_Rejection_Detail.DateReceived
-- 2023-07-13T15:51:25.228Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716661,0,541791,542602,618248,'F',TO_TIMESTAMP('2023-07-13 16:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem ein Produkt empfangen wurde','"Eingangsdatum" bezeichnet das Datum, zu dem das Produkt empfangen wurde','Y','N','N','Y','N','N','N',0,'Eingangsdatum',15,0,0,TO_TIMESTAMP('2023-07-13 16:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

