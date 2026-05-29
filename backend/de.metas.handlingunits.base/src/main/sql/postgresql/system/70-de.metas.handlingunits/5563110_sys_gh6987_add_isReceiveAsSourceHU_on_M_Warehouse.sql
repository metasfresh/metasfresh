-- 2020-07-08T15:11:00.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577789,0,'IsReceiveAsSourceHU',TO_TIMESTAMP('2020-07-08 18:11:00','YYYY-MM-DD HH24:MI:SS'),100,'Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.','de.metas.handlingunits','Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.','Y','Als Quell-HU annehmen','Als Quell-HU annehmen',TO_TIMESTAMP('2020-07-08 18:11:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-08T15:11:01.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577789 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-07-08T15:11:41.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Automatically marks all received HUs carrying BOM components as ''Source HUs''.', Help='Automatically marks all received HUs carrying BOM components as ''Source HUs''.', Name='Receive as Source HU', PrintName='Receive as Source HU',Updated=TO_TIMESTAMP('2020-07-08 18:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577789 AND AD_Language='en_US'
;

-- 2020-07-08T15:11:41.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577789,'en_US') 
;

-- 2020-07-08T15:12:24.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,FacetFilterSeqNo,MaxFacetsToFetch,IsFacetFilter,AD_Element_ID,EntityType) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-07-08 18:12:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-07-08 18:12:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N',190,'N','Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.',570909,'N','Y','N','N','N','N','N','N',0,'N','N','IsReceiveAsSourceHU','N','Als Quell-HU annehmen',0,'Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.',0,0,'N',577789,'de.metas.handlingunits')
;

-- 2020-07-08T15:12:24.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570909 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-08T15:12:24.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577789) 
;

-- 2020-07-08T15:14:11.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Warehouse','ALTER TABLE public.M_Warehouse ADD COLUMN IsReceiveAsSourceHU CHAR(1) DEFAULT ''N'' CHECK (IsReceiveAsSourceHU IN (''Y'',''N'')) NOT NULL')
;

-- 2020-07-08T15:16:10.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570909,615224,0,177,0,TO_TIMESTAMP('2020-07-08 18:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.',0,'de.metas.handlingunits','Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.',0,'Y','Y','Y','N','N','N','N','N','Als Quell-HU annehmen',220,190,0,1,1,TO_TIMESTAMP('2020-07-08 18:16:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-08T15:16:10.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=615224 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-07-08T15:16:10.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577789) 
;

-- 2020-07-08T15:16:10.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=615224
;

-- 2020-07-08T15:16:10.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(615224)
;

-- 2020-07-08T15:16:54.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,615224,0,177,570252,540174,'F',TO_TIMESTAMP('2020-07-08 18:16:54','YYYY-MM-DD HH24:MI:SS'),100,'Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.','Alle empfangenen HUs mit Stücklistenbestandteilen werden automatisch als ''Quell-HUs'' gekennzeichnet.','Y','N','N','Y','N','N','N',0,'Als Quell-HU annehmen',90,0,0,TO_TIMESTAMP('2020-07-08 18:16:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-08T15:21:17.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2020-07-08 18:21:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570909
;

