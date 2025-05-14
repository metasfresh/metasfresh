-- 2024-11-21T17:44:04.389Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583376,0,'ValidUntil',TO_TIMESTAMP('2024-11-21 18:44:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gültig bis','Gültig bis',TO_TIMESTAMP('2024-11-21 18:44:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-21T17:44:04.444Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583376 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-11-21T17:44:30.439Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Valid Until', PrintName='Valid Until',Updated=TO_TIMESTAMP('2024-11-21 18:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583376 AND AD_Language='en_US'
;

-- 2024-11-21T17:44:30.576Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583376,'en_US') 
;

-- Column: C_Order.ValidUntil
-- 2024-11-21T18:56:12.347Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589447,583376,0,15,259,'ValidUntil',TO_TIMESTAMP('2024-11-21 19:56:11','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gültig bis',0,0,TO_TIMESTAMP('2024-11-21 19:56:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-21T18:56:12.401Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589447 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-21T18:56:12.511Z
/* DDL */  select update_Column_Translation_From_AD_Element(583376) 
;

-- 2024-11-21T18:56:43.200Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN ValidUntil TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: OLD-Auftrag -> Auftrag -> Gültig bis
-- Column: C_Order.ValidUntil
-- 2024-11-21T18:59:36.652Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589447,734036,0,186,0,TO_TIMESTAMP('2024-11-21 19:59:35','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Gültig bis',0,760,0,1,1,TO_TIMESTAMP('2024-11-21 19:59:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-21T18:59:36.704Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=734036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-21T18:59:36.759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583376) 
;

-- 2024-11-21T18:59:36.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734036
;

-- 2024-11-21T18:59:36.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734036)
;

-- Field: OLD-Auftrag -> Auftrag -> E-Mail senden
-- Column: C_Order.SendEMail
-- 2024-11-21T19:02:27.047Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-21 20:02:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6227
;

-- Field: OLD-Auftrag -> Auftrag -> Gültig bis
-- Column: C_Order.ValidUntil
-- 2024-11-21T19:02:36.045Z
UPDATE AD_Field SET DisplayLogic='@OrderType/XX@=ON',Updated=TO_TIMESTAMP('2024-11-21 20:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=734036
;

-- UI Element: OLD-Auftrag -> Auftrag.Gültig bis
-- Column: C_Order.ValidUntil
-- 2024-11-21T19:03:33.568Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734036,0,186,540920,627358,'F',TO_TIMESTAMP('2024-11-21 20:03:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Gültig bis',50,0,0,TO_TIMESTAMP('2024-11-21 20:03:32','YYYY-MM-DD HH24:MI:SS'),100)
;

