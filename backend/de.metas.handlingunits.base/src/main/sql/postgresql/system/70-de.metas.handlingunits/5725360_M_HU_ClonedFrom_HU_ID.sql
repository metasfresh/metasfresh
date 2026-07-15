-- 2024-06-04T06:40:26.982Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583132,0,'ClonedFrom_HU_ID',TO_TIMESTAMP('2024-06-04 09:40:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Cloned From','Cloned From',TO_TIMESTAMP('2024-06-04 09:40:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-04T06:40:26.993Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583132 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_HU.ClonedFrom_HU_ID
-- Column: M_HU.ClonedFrom_HU_ID
-- 2024-06-04T06:41:25.540Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588329,583132,0,30,540499,540516,'XX','ClonedFrom_HU_ID',TO_TIMESTAMP('2024-06-04 09:41:25','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cloned From',0,0,TO_TIMESTAMP('2024-06-04 09:41:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-04T06:41:25.542Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588329 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-04T06:41:25.574Z
/* DDL */  select update_Column_Translation_From_AD_Element(583132) 
;

-- 2024-06-04T06:41:26.385Z
/* DDL */ SELECT public.db_alter_table('M_HU','ALTER TABLE public.M_HU ADD COLUMN ClonedFrom_HU_ID NUMERIC(10)')
;

-- 2024-06-04T06:41:26.815Z
ALTER TABLE M_HU ADD CONSTRAINT ClonedFromHU_MHU FOREIGN KEY (ClonedFrom_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED
;

-- Field: Handling Unit -> Handling Unit -> Cloned From
-- Column: M_HU.ClonedFrom_HU_ID
-- Field: Handling Unit(540189,de.metas.handlingunits) -> Handling Unit(540508,de.metas.handlingunits) -> Cloned From
-- Column: M_HU.ClonedFrom_HU_ID
-- 2024-06-04T06:41:56.458Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588329,728775,0,540508,TO_TIMESTAMP('2024-06-04 09:41:56','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Cloned From',TO_TIMESTAMP('2024-06-04 09:41:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-04T06:41:56.462Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=728775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-04T06:41:56.467Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583132) 
;

-- 2024-06-04T06:41:56.484Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728775
;

-- 2024-06-04T06:41:56.487Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728775)
;

-- Field: Handling Unit -> Handling Unit -> Cloned From
-- Column: M_HU.ClonedFrom_HU_ID
-- Field: Handling Unit(540189,de.metas.handlingunits) -> Handling Unit(540508,de.metas.handlingunits) -> Cloned From
-- Column: M_HU.ClonedFrom_HU_ID
-- 2024-06-04T06:42:27.135Z
UPDATE AD_Field SET DisplayLogic='@ClonedFrom_HU_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-06-04 09:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=728775
;

-- UI Column: Handling Unit(540189,de.metas.handlingunits) -> Handling Unit(540508,de.metas.handlingunits) -> main -> 10
-- UI Element Group: cloned from
-- 2024-06-04T06:43:32.692Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540531,551838,TO_TIMESTAMP('2024-06-04 09:43:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','cloned from',30,TO_TIMESTAMP('2024-06-04 09:43:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Handling Unit -> Handling Unit.Cloned From
-- Column: M_HU.ClonedFrom_HU_ID
-- UI Element: Handling Unit(540189,de.metas.handlingunits) -> Handling Unit(540508,de.metas.handlingunits) -> main -> 10 -> cloned from.Cloned From
-- Column: M_HU.ClonedFrom_HU_ID
-- 2024-06-04T06:43:48.913Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728775,0,540508,551838,624789,'F',TO_TIMESTAMP('2024-06-04 09:43:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cloned From',10,0,0,TO_TIMESTAMP('2024-06-04 09:43:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: ClonedFrom_HU_ID
-- 2024-06-04T07:50:11.036Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Basiert auf', PrintName='Basiert auf',Updated=TO_TIMESTAMP('2024-06-04 10:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583132 AND AD_Language='de_CH'
;

-- 2024-06-04T07:50:11.041Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583132,'de_CH') 
;

-- Element: ClonedFrom_HU_ID
-- 2024-06-04T07:50:14.290Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-04 10:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583132 AND AD_Language='en_US'
;

-- 2024-06-04T07:50:14.292Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583132,'en_US') 
;

-- Element: ClonedFrom_HU_ID
-- 2024-06-04T07:50:17.599Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Basiert auf', PrintName='Basiert auf',Updated=TO_TIMESTAMP('2024-06-04 10:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583132 AND AD_Language='de_DE'
;

-- 2024-06-04T07:50:17.601Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583132,'de_DE') 
;

-- 2024-06-04T07:50:17.613Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583132,'de_DE') 
;

