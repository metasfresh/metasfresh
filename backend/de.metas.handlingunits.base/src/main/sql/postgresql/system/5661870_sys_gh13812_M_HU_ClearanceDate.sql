-- 2022-10-25T09:31:41.309Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581602,0,'ClearanceDate',TO_TIMESTAMP('2022-10-25 12:31:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Freigabedatum','Freigabedatum',TO_TIMESTAMP('2022-10-25 12:31:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T09:31:41.434Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581602 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-10-25T09:32:01.943Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Clearance Date', PrintName='Clearance Date',Updated=TO_TIMESTAMP('2022-10-25 12:32:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581602 AND AD_Language='en_US'
;

-- 2022-10-25T09:32:02.017Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581602,'en_US') 
;

-- 2022-10-25T09:32:49.357Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584801,581602,0,15,540516,'ClearanceDate',TO_TIMESTAMP('2022-10-25 12:32:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Freigabedatum',0,0,TO_TIMESTAMP('2022-10-25 12:32:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T09:32:49.400Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584801 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T09:32:49.485Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581602) 
;

-- 2022-10-25T09:40:53.834Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_HU','ALTER TABLE public.M_HU ADD COLUMN ClearanceDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-10-25T09:53:58.202Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584801,707858,0,540508,0,TO_TIMESTAMP('2022-10-25 12:53:57','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Freigabedatum',0,160,0,1,1,TO_TIMESTAMP('2022-10-25 12:53:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T09:53:58.246Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T09:53:58.291Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581602) 
;

-- 2022-10-25T09:53:58.353Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707858
;

-- 2022-10-25T09:53:58.399Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(707858)
;

-- 2022-10-25T09:54:14.096Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@ClearanceNote@!=null', EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-10-25 12:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707858
;

-- 2022-10-25T09:56:02.287Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707858,0,540508,548051,613300,'F',TO_TIMESTAMP('2022-10-25 12:56:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Freigabedatum',30,0,0,TO_TIMESTAMP('2022-10-25 12:56:01','YYYY-MM-DD HH24:MI:SS'),100)
;

