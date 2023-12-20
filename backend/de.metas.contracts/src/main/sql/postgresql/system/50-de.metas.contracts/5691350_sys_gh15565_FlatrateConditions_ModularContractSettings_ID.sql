-- Column: C_Flatrate_Conditions.C_ModularContractSettings_ID
-- 2023-06-13T09:47:12.401126300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586810,582425,0,19,540311,'C_ModularContractSettings_ID',TO_TIMESTAMP('2023-06-13 12:47:12.097','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Einstellungen Vertragsbausteine',0,0,TO_TIMESTAMP('2023-06-13 12:47:12.097','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-13T09:47:12.407172700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586810 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-13T09:47:13.798003900Z
/* DDL */  select update_Column_Translation_From_AD_Element(582425) 
;

-- Column: C_Flatrate_Conditions.C_ModularContractSettings_ID
-- 2023-06-13T09:47:56.748319900Z
UPDATE AD_Column SET MandatoryLogic='@Type_Conditions/''''@=''ModularContract''',Updated=TO_TIMESTAMP('2023-06-13 12:47:56.748','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586810
;

-- 2023-06-13T09:48:14.421336100Z
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Conditions','ALTER TABLE public.C_Flatrate_Conditions ADD COLUMN C_ModularContractSettings_ID NUMERIC(10)')
;

-- 2023-06-13T09:48:15.026656800Z
ALTER TABLE C_Flatrate_Conditions ADD CONSTRAINT CModularContractSettings_CFlatrateConditions FOREIGN KEY (C_ModularContractSettings_ID) REFERENCES public.C_ModularContractSettings DEFERRABLE INITIALLY DEFERRED
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Einstellungen Vertragsbausteine
-- Column: C_Flatrate_Conditions.C_ModularContractSettings_ID
-- 2023-06-13T09:48:33.215865300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586810,716357,0,540331,TO_TIMESTAMP('2023-06-13 12:48:33.039','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Einstellungen Vertragsbausteine',TO_TIMESTAMP('2023-06-13 12:48:33.039','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-13T09:48:33.218865300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716357 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-13T09:48:33.225396800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425) 
;

-- 2023-06-13T09:48:33.231391800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716357
;

-- 2023-06-13T09:48:33.237270300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716357)
;

-- Field: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> Einstellungen Vertragsbausteine
-- Column: C_Flatrate_Conditions.C_ModularContractSettings_ID
-- 2023-06-13T09:49:08.118154600Z
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''''@=''ModularContract''',Updated=TO_TIMESTAMP('2023-06-13 12:49:08.118','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716357
;

-- UI Element: Vertragsbedingungen (OLD)(540113,de.metas.contracts) -> Bedingungen(540331,de.metas.contracts) -> main -> 10 -> default.Einstellungen Vertragsbausteine
-- Column: C_Flatrate_Conditions.C_ModularContractSettings_ID
-- 2023-06-13T09:49:51.234496900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716357,0,540331,617997,540760,'F',TO_TIMESTAMP('2023-06-13 12:49:50.959','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Einstellungen Vertragsbausteine',100,0,0,TO_TIMESTAMP('2023-06-13 12:49:50.959','YYYY-MM-DD HH24:MI:SS.US'),100)
;

