-- Column: M_RequisitionLine.C_Currency_ID
-- 2023-05-22T18:24:10.536933700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586686,193,0,19,703,'C_Currency_ID',TO_TIMESTAMP('2023-05-22 19:24:10.116','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','D',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2023-05-22 19:24:10.116','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-22T18:24:10.542280400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586686 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-22T18:24:11.033788800Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- 2023-05-22T18:24:15.410558900Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN C_Currency_ID NUMERIC(10)')
;

-- 2023-05-22T18:24:15.487064700Z
ALTER TABLE M_RequisitionLine ADD CONSTRAINT CCurrency_MRequisitionLine FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Währung
-- Column: M_RequisitionLine.C_Currency_ID
-- 2023-05-22T18:24:51.943Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586686,715491,0,642,0,TO_TIMESTAMP('2023-05-22 19:24:51.679','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag',0,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','Y','Y','N','N','N','N','N','Währung',0,240,0,1,1,TO_TIMESTAMP('2023-05-22 19:24:51.679','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-22T18:24:51.944584300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715491 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-22T18:24:51.946701500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-05-22T18:24:52.047616100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715491
;

-- 2023-05-22T18:24:52.049796500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715491)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> line.Währung
-- Column: M_RequisitionLine.C_Currency_ID
-- 2023-05-22T18:25:51.420540600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715491,0,642,550705,617540,'F',TO_TIMESTAMP('2023-05-22 19:25:51.108','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',65,0,0,TO_TIMESTAMP('2023-05-22 19:25:51.108','YYYY-MM-DD HH24:MI:SS.US'),100)
;

