-- Run mode: SWING_CLIENT

-- 2024-10-16T14:55:52.127Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583331,0,'Override_DocType_ID',TO_TIMESTAMP('2024-10-16 17:55:51.958','YYYY-MM-DD HH24:MI:SS.US'),100,'Dokumenttypnamen überschreiben','de.metas.document','','Y','Dokumenttypnamen überschreiben','Dokumenttypnamen überschreiben',TO_TIMESTAMP('2024-10-16 17:55:51.958','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-16T14:55:52.138Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583331 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Override_DocType_ID
-- 2024-10-16T14:56:18.682Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:56:18.681','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583331 AND AD_Language='de_CH'
;

-- 2024-10-16T14:56:18.710Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583331,'de_CH')
;

-- Element: Override_DocType_ID
-- 2024-10-16T14:56:32.450Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 17:56:32.45','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583331 AND AD_Language='de_DE'
;

-- 2024-10-16T14:56:32.454Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583331,'de_DE')
;

-- 2024-10-16T14:56:32.455Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583331,'de_DE')
;

-- Element: Override_DocType_ID
-- 2024-10-16T14:56:51.059Z
UPDATE AD_Element_Trl SET Description='Override Doc Type Name', IsTranslated='Y', Name='Override Doc Type Name', PrintName='Override Doc Type Name',Updated=TO_TIMESTAMP('2024-10-16 17:56:51.059','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583331 AND AD_Language='en_US'
;

-- 2024-10-16T14:56:51.062Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583331,'en_US')
;

-- Column: C_Doc_Outbound_Config_CC.Override_DocType_ID
-- 2024-10-16T14:57:07.805Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589308,583331,0,30,170,542430,'Override_DocType_ID',TO_TIMESTAMP('2024-10-16 17:57:07.679','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Dokumenttypnamen überschreiben','de.metas.document',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Dokumenttypnamen überschreiben',0,0,TO_TIMESTAMP('2024-10-16 17:57:07.679','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-16T14:57:07.807Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589308 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-16T14:57:07.810Z
/* DDL */  select update_Column_Translation_From_AD_Element(583331)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Dokumenttypnamen überschreiben
-- Column: C_Doc_Outbound_Config_CC.Override_DocType_ID
-- 2024-10-16T14:57:24.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589308,731910,0,547571,TO_TIMESTAMP('2024-10-16 17:57:24.608','YYYY-MM-DD HH24:MI:SS.US'),100,'Dokumenttypnamen überschreiben',10,'de.metas.document.archive','','Y','N','N','N','N','N','N','N','Dokumenttypnamen überschreiben',TO_TIMESTAMP('2024-10-16 17:57:24.608','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-16T14:57:24.773Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-16T14:57:24.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583331)
;

-- 2024-10-16T14:57:24.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731910
;

-- 2024-10-16T14:57:24.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731910)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main -> 10 -> main.Override Doc Type Name
-- Column: C_Doc_Outbound_Config_CC.AD_PrintFormat_ID
-- 2024-10-16T14:58:41.035Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731910,0,547571,551896,626194,'F',TO_TIMESTAMP('2024-10-16 17:58:40.897','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','N','Y','N','N','N','Override Doc Type Name',25,0,0,TO_TIMESTAMP('2024-10-16 17:58:40.897','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-16T15:08:13.420Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Config_CC','ALTER TABLE public.C_Doc_Outbound_Config_CC ADD COLUMN Override_DocType_ID NUMERIC(10)')
;

-- 2024-10-16T15:08:13.429Z
ALTER TABLE C_Doc_Outbound_Config_CC ADD CONSTRAINT OverrideDocType_CDocOutboundConfigCC FOREIGN KEY (Override_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Doc_Outbound_Config_CC.Override_DocType_ID
-- 2024-10-16T15:13:36.905Z
UPDATE AD_Column SET AD_Reference_ID=18,Updated=TO_TIMESTAMP('2024-10-16 18:13:36.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589308
;

