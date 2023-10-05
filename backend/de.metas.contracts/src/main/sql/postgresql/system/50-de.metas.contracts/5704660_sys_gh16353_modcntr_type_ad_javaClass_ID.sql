-- Run mode: SWING_CLIENT

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Java-Klasse
-- Column: ModCntr_Type.Classname
-- 2023-09-29T16:29:30.703Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617951
;

-- 2023-09-29T16:29:30.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716275
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Java-Klasse
-- Column: ModCntr_Type.Classname
-- 2023-09-29T16:29:30.720Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716275
;

-- 2023-09-29T16:29:30.723Z
DELETE FROM AD_Field WHERE AD_Field_ID=716275
;

-- 2023-09-29T16:29:30.750Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Type','ALTER TABLE ModCntr_Type DROP COLUMN IF EXISTS Classname')
;

-- Column: ModCntr_Type.Classname
-- 2023-09-29T16:29:30.778Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586751
;

-- 2023-09-29T16:29:30.781Z
DELETE FROM AD_Column WHERE AD_Column_ID=586751
;

-- Run mode: SWING_CLIENT

-- Name: AD_JavaClass_For_ModularContracts
-- 2023-09-29T16:38:58.706Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540657,'AD_JavaClass.ad_javaclass_type_id=(SELECT ad_javaclass_type_id from ad_javaclass_type where classname=''de.metas.contracts.modular.IModularContractTypeHandler'')',TO_TIMESTAMP('2023-09-29 19:38:58.57','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','AD_JavaClass_For_ModularContracts','S',TO_TIMESTAMP('2023-09-29 19:38:58.57','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- Column: ModCntr_Type.AD_JavaClass_ID
-- 2023-09-29T16:40:13.234Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587486,542195,0,30,542337,540657,'AD_JavaClass_ID',TO_TIMESTAMP('2023-09-29 19:40:12.954','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Java Klasse',0,0,TO_TIMESTAMP('2023-09-29 19:40:12.954','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-29T16:40:13.238Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587486 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-29T16:40:13.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(542195)
;

-- 2023-09-29T16:40:14.811Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Type','ALTER TABLE public.ModCntr_Type ADD COLUMN AD_JavaClass_ID NUMERIC(10)')
;

-- 2023-09-29T16:40:14.819Z
ALTER TABLE ModCntr_Type ADD CONSTRAINT ADJavaClass_ModCntrType FOREIGN KEY (AD_JavaClass_ID) REFERENCES public.AD_JavaClass DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Java Klasse
-- Column: ModCntr_Type.AD_JavaClass_ID
-- 2023-09-29T16:41:55.665Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587486,720684,0,547011,TO_TIMESTAMP('2023-09-29 19:41:55.558','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Java Klasse',TO_TIMESTAMP('2023-09-29 19:41:55.558','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T16:41:55.669Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T16:41:55.675Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542195)
;

-- 2023-09-29T16:41:55.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720684
;

-- 2023-09-29T16:41:55.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720684)
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Java Klasse
-- Column: ModCntr_Type.AD_JavaClass_ID
-- 2023-09-29T16:42:16.782Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720684,0,547011,620601,550769,'F',TO_TIMESTAMP('2023-09-29 19:42:16.679','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Java Klasse',20,0,0,TO_TIMESTAMP('2023-09-29 19:42:16.679','YYYY-MM-DD HH24:MI:SS.US'),100)
;

