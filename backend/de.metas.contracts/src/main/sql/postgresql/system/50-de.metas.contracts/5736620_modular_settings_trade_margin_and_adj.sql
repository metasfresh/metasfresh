-- Run mode: SWING_CLIENT

-- Reference: ModCntr_Type
-- Table: ModCntr_Type
-- Key: ModCntr_Type.ModCntr_Type_ID
-- 2024-10-10T08:59:21.279Z
UPDATE AD_Ref_Table SET WhereClause='ModCntr_Type_ID not in (540010, 540009, 540008, 540026)',Updated=TO_TIMESTAMP('2024-10-10 10:59:21.279','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541861
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:10:54.296Z
UPDATE AD_Message SET MsgText='Es kann nur ein Verkaufs Vertragsbaustein-Typ innerhalb der gleichen Modularen Einstellungen verwendet werden!',Updated=TO_TIMESTAMP('2024-10-10 11:10:54.295','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545393
;

-- 2024-10-10T09:10:54.299Z
UPDATE AD_Message_Trl trl SET MsgText='Es kann nur ein Verkaufs Vertragsbaustein-Typ innerhalb der gleichen Modularen Einstellungen verwendet werden!' WHERE AD_Message_ID=545393 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:18.837Z
UPDATE AD_Message_Trl SET MsgText='Es kann nur ein Verkaufs Vertragsbaustein-Typ innerhalb der gleichen Modularen Einstellungen verwendet werden!',Updated=TO_TIMESTAMP('2024-10-10 11:11:18.837','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:54.367Z
UPDATE AD_Message_Trl SET MsgText='Only one Sales Computing Method can be used within the same Modular Settings!',Updated=TO_TIMESTAMP('2024-10-10 11:11:54.367','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:55.293Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-10 11:11:55.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:56.019Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-10 11:11:56.019','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545393
;

-- Value: de.metas.contracts.modular.settings.interceptor.SalesOnRawProductAndSalesOnProcessedProductError
-- 2024-10-10T09:11:56.962Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-10 11:11:56.962','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545393
;

-- 2024-10-14T11:15:12.931Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583330,0,'TradeMargin',TO_TIMESTAMP('2024-10-14 13:15:12.799','YYYY-MM-DD HH24:MI:SS.US'),100,'Durchschnittspreise werden um angegebenem Wert reduziert.','de.metas.contracts','Y','Handelsfranken','Handelsfranken',TO_TIMESTAMP('2024-10-14 13:15:12.799','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-14T11:15:12.937Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583330 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TradeMargin
-- 2024-10-14T11:15:48.930Z
UPDATE AD_Element_Trl SET Description='Average Prices will be reduced by given value.', IsTranslated='Y', Name='Trade Margin', PrintName='Trade Margin',Updated=TO_TIMESTAMP('2024-10-14 13:15:48.93','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583330 AND AD_Language='en_US'
;

-- 2024-10-14T11:15:48.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583330,'en_US')
;

-- Element: TradeMargin
-- 2024-10-14T11:15:49.518Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-14 13:15:49.518','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583330 AND AD_Language='de_CH'
;

-- 2024-10-14T11:15:49.521Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583330,'de_CH')
;

-- Element: TradeMargin
-- 2024-10-14T11:15:54.365Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-14 13:15:54.365','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583330 AND AD_Language='de_DE'
;

-- 2024-10-14T11:15:54.367Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583330,'de_DE')
;

-- 2024-10-14T11:15:54.368Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583330,'de_DE')
;

-- Column: ModCntr_Settings.TradeMargin
-- 2024-10-14T11:30:47.121Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589307,583330,0,37,542339,'TradeMargin',TO_TIMESTAMP('2024-10-14 13:30:46.99','YYYY-MM-DD HH24:MI:SS.US'),100,'N','1','Durchschnittspreise werden um angegebenem Wert reduziert.','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Handelsfranken',0,0,TO_TIMESTAMP('2024-10-14 13:30:46.99','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-14T11:30:47.124Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589307 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-14T11:30:47.127Z
/* DDL */  select update_Column_Translation_From_AD_Element(583330)
;

-- 2024-10-14T11:30:49.399Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN TradeMargin NUMERIC DEFAULT 1 NOT NULL')
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Handelsfranken
-- Column: ModCntr_Settings.TradeMargin
-- 2024-10-14T11:33:44.487Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589307,731908,0,547013,TO_TIMESTAMP('2024-10-14 13:33:44.043','YYYY-MM-DD HH24:MI:SS.US'),100,'Durchschnittspreise werden um angegebenem Wert reduziert.',10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Handelsfranken',TO_TIMESTAMP('2024-10-14 13:33:44.043','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-14T11:33:44.491Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-14T11:33:44.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583330)
;

-- 2024-10-14T11:33:44.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731908
;

-- 2024-10-14T11:33:44.501Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731908)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Handelsfranken
-- Column: ModCntr_Settings.TradeMargin
-- 2024-10-14T11:34:27.792Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731908,0,547013,551809,626192,'F',TO_TIMESTAMP('2024-10-14 13:34:27.505','YYYY-MM-DD HH24:MI:SS.US'),100,'Durchschnittspreise werden um angegebenem Wert reduziert.','Y','N','N','Y','N','N','N',0,'Handelsfranken',60,0,0,TO_TIMESTAMP('2024-10-14 13:34:27.505','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Handelsfranken
-- Column: ModCntr_Settings.TradeMargin
-- 2024-10-14T11:37:19.971Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''N''', IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-10-14 13:37:19.971','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731908
;

-- Column: ModCntr_Settings.IsSOTrx
-- 2024-10-14T11:39:37.810Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-14 13:39:37.81','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587164
;

