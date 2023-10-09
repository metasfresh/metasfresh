-- Run mode: SWING_CLIENT

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Java-Klasse
-- Column: ModCntr_Type.Classname
-- 2023-10-06T14:36:51.319Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617951
;

-- 2023-10-06T14:36:51.327Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716275
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Java-Klasse
-- Column: ModCntr_Type.Classname
-- 2023-10-06T14:36:51.334Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=716275
;

-- 2023-10-06T14:36:51.336Z
DELETE FROM AD_Field WHERE AD_Field_ID=716275
;

-- 2023-10-06T14:36:51.363Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Type','ALTER TABLE ModCntr_Type DROP COLUMN IF EXISTS Classname')
;

-- Column: ModCntr_Type.Classname
-- 2023-10-06T14:36:51.398Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586751
;

-- 2023-10-06T14:36:51.401Z
DELETE FROM AD_Column WHERE AD_Column_ID=586751
;

-- Run mode: SWING_CLIENT

-- 2023-10-06T14:39:34.144Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582747,0,'ModularContractHandlerType',TO_TIMESTAMP('2023-10-06 17:39:34.02','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Modular Contract Handler Type','Modular Contract Handler Type',TO_TIMESTAMP('2023-10-06 17:39:34.02','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-06T14:39:34.155Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582747 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ModularContractHandlerType
-- 2023-10-06T14:40:40.750Z
UPDATE AD_Element_Trl SET PrintName='Modularer Auftragsabwickler Typ',Updated=TO_TIMESTAMP('2023-10-06 17:40:40.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582747 AND AD_Language='de_CH'
;

-- 2023-10-06T14:40:40.754Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582747,'de_CH')
;

-- Element: ModularContractHandlerType
-- 2023-10-06T14:40:44.270Z
UPDATE AD_Element_Trl SET Name='Modularer Auftragsabwickler Typ', PrintName='Modularer Auftragsabwickler Typ',Updated=TO_TIMESTAMP('2023-10-06 17:40:44.27','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582747 AND AD_Language='de_DE'
;

-- 2023-10-06T14:40:44.274Z
UPDATE AD_Element SET Name='Modularer Auftragsabwickler Typ', PrintName='Modularer Auftragsabwickler Typ' WHERE AD_Element_ID=582747
;

-- 2023-10-06T14:40:44.662Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582747,'de_DE')
;

-- 2023-10-06T14:40:44.666Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582747,'de_DE')
;

-- Element: ModularContractHandlerType
-- 2023-10-06T14:40:47.750Z
UPDATE AD_Element_Trl SET Name='Modularer Auftragsabwickler Typ', PrintName='Modularer Auftragsabwickler Typ',Updated=TO_TIMESTAMP('2023-10-06 17:40:47.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582747 AND AD_Language='fr_CH'
;

-- 2023-10-06T14:40:47.753Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582747,'fr_CH')
;

-- Element: ModularContractHandlerType
-- 2023-10-06T14:40:49.755Z
UPDATE AD_Element_Trl SET Name='Modularer Auftragsabwickler Typ', PrintName='Modularer Auftragsabwickler Typ',Updated=TO_TIMESTAMP('2023-10-06 17:40:49.755','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582747 AND AD_Language='it_IT'
;

-- 2023-10-06T14:40:49.757Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582747,'it_IT')
;

-- Run mode: SWING_CLIENT

-- Column: ModCntr_Type.ModularContractHandlerType
-- 2023-10-06T14:43:57.384Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587541,582747,0,17,541838,542337,'ModularContractHandlerType',TO_TIMESTAMP('2023-10-06 17:43:57.27','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Modularer Auftragsabwickler Typ',0,0,TO_TIMESTAMP('2023-10-06 17:43:57.27','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-06T14:43:57.386Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587541 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-06T14:43:57.389Z
/* DDL */  select update_Column_Translation_From_AD_Element(582747)
;

-- 2023-10-06T14:43:59.317Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Type','ALTER TABLE public.ModCntr_Type ADD COLUMN ModularContractHandlerType VARCHAR(255)')
;

-- Run mode: SWING_CLIENT

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Modularer Auftragsabwickler Typ
-- Column: ModCntr_Type.ModularContractHandlerType
-- 2023-10-06T14:45:20.564Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587541,720784,0,547011,TO_TIMESTAMP('2023-10-06 17:45:20.439','YYYY-MM-DD HH24:MI:SS.US'),100,255,'de.metas.contracts','Y','N','N','N','N','N','N','N','Modularer Auftragsabwickler Typ',TO_TIMESTAMP('2023-10-06 17:45:20.439','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-06T14:45:20.566Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-06T14:45:20.570Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582747)
;

-- 2023-10-06T14:45:20.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720784
;

-- 2023-10-06T14:45:20.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720784)
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Modularer Auftragsabwickler Typ
-- Column: ModCntr_Type.ModularContractHandlerType
-- 2023-10-06T14:45:40.306Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720784,0,547011,620641,550769,'F',TO_TIMESTAMP('2023-10-06 17:45:40.167','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Modularer Auftragsabwickler Typ',20,0,0,TO_TIMESTAMP('2023-10-06 17:45:40.167','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Suchschlüssel
-- Column: ModCntr_Type.Value
-- 2023-10-06T15:11:03.997Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-06 18:11:03.997','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617948
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Name
-- Column: ModCntr_Type.Name
-- 2023-10-06T15:11:04.008Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-06 18:11:04.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617949
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Beschreibung
-- Column: ModCntr_Type.Description
-- 2023-10-06T15:11:04.013Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-06 18:11:04.013','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617950
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 20 -> flags.Aktiv
-- Column: ModCntr_Type.IsActive
-- 2023-10-06T15:11:04.018Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-06 18:11:04.018','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617952
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Modularer Auftragsabwickler Typ
-- Column: ModCntr_Type.ModularContractHandlerType
-- 2023-10-06T15:11:04.024Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-06 18:11:04.024','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620641
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 20 -> org.Organisation
-- Column: ModCntr_Type.AD_Org_ID
-- 2023-10-06T15:11:04.029Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-06 18:11:04.029','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617953
;

