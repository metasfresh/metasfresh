-- 2021-02-25T13:48:38.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578789,0,'IsCreateShareForOwnRevenue',TO_TIMESTAMP('2021-02-25 14:48:38','YYYY-MM-DD HH24:MI:SS'),100,'Wenn gesetzt wird bei einem Eigenumsatz des Vertriebspartners ein 0% Buchauszug-Datensatz angelegt.','de.metas.contracts.commission','Y','Buchauszug für Eigenumsatz','Buchauszug für Eigenumsatz',TO_TIMESTAMP('2021-02-25 14:48:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-25T13:48:39.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578789 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-25T14:02:34.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-25 15:02:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578789 AND AD_Language='de_CH'
;

-- 2021-02-25T14:02:34.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578789,'de_CH') 
;

-- 2021-02-25T14:02:36.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-25 15:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578789 AND AD_Language='de_DE'
;

-- 2021-02-25T14:02:36.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578789,'de_DE') 
;

-- 2021-02-25T14:02:36.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578789,'de_DE') 
;

-- 2021-02-25T14:03:58.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If set and the sales-rep makes a purchase of their own, then a 0% commission deed record is create for this.', IsTranslated='Y', Name='Commission deed for own revenue', PrintName='Commission deed for own revenue',Updated=TO_TIMESTAMP('2021-02-25 15:03:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578789 AND AD_Language='en_US'
;

-- 2021-02-25T14:03:58.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578789,'en_US') 
;

-- 2021-02-25T14:04:13.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573006,578789,0,20,541425,'IsCreateShareForOwnRevenue',TO_TIMESTAMP('2021-02-25 15:04:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn gesetzt wird bei einem Eigenumsatz des Vertriebspartners ein 0% Buchauszug-Datensatz angelegt.','de.metas.contracts.commission',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N',0,'Buchauszug für Eigenumsatz',0,0,TO_TIMESTAMP('2021-02-25 15:04:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-25T14:04:13.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573006 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-25T14:04:13.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578789) 
;

-- 2021-02-25T14:04:22.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_HierarchyCommissionSettings','ALTER TABLE public.C_HierarchyCommissionSettings ADD COLUMN IsCreateShareForOwnRevenue CHAR(1) DEFAULT ''N'' CHECK (IsCreateShareForOwnRevenue IN (''Y'',''N'')) NOT NULL')
;

-- 2021-02-25T14:04:48.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (542066,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-25 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-02-25 15:04:48','YYYY-MM-DD HH24:MI:SS'),100,632800,573006,'de.metas.contracts.commission','Buchauszug für Eigenumsatz','Wenn gesetzt wird bei einem Eigenumsatz des Vertriebspartners ein 0% Buchauszug-Datensatz angelegt.',0)
;

-- 2021-02-25T14:04:48.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=632800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-25T14:04:48.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578789) 
;

-- 2021-02-25T14:04:48.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=632800
;

-- 2021-02-25T14:04:48.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(632800)
;

-- 2021-02-25T14:05:55.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,632800,0,542066,543076,578397,'F',TO_TIMESTAMP('2021-02-25 15:05:55','YYYY-MM-DD HH24:MI:SS'),100,'Wenn gesetzt wird bei einem Eigenumsatz des Vertriebspartners ein 0% Buchauszug-Datensatz angelegt.','Y','N','N','Y','N','N','N',0,'IsCreateShareForOwnRevenue',100,0,0,TO_TIMESTAMP('2021-02-25 15:05:55','YYYY-MM-DD HH24:MI:SS'),100)
;

