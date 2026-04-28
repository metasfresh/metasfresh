-- 2019-10-12T14:51:12.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569178,0,TO_TIMESTAMP('2019-10-12 16:51:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550251,'N','N','LU Verpackungscode',290,'R',TO_TIMESTAMP('2019-10-12 16:51:12','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_LU_ID')
;

-- 2019-10-12T14:51:13.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569177,0,TO_TIMESTAMP('2019-10-12 16:51:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550252,'N','N','TU Verpackungscode',300,'R',TO_TIMESTAMP('2019-10-12 16:51:12','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_TU_ID')
;

-- 2019-10-12T18:19:24.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577201,0,'M_HU_PackagingCode_LU_Text',TO_TIMESTAMP('2019-10-12 20:19:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','M_HU_PackagingCode_LU_Text','M_HU_PackagingCode_LU_Text',TO_TIMESTAMP('2019-10-12 20:19:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-12T18:19:24.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577201 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-12T18:19:45.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577202,0,'M_HU_PackagingCode_TU_Text',TO_TIMESTAMP('2019-10-12 20:19:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','','Y','M_HU_PackagingCode_TU_Text','M_HU_PackagingCode_TU_Text',TO_TIMESTAMP('2019-10-12 20:19:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-12T18:19:45.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577202 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-12T18:43:31.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,569179,577201,0,10,540645,'M_HU_PackagingCode_LU_Text',TO_TIMESTAMP('2019-10-12 20:43:31','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',4,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','M_HU_PackagingCode_LU_Text',0,0,'The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export',TO_TIMESTAMP('2019-10-12 20:43:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-12T18:43:31.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569179 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-12T18:43:31.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577201) 
;

-- 2019-10-12T18:43:31.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN M_HU_PackagingCode_LU_Text VARCHAR(4)')
;

-- 2019-10-12T18:46:48.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export',Updated=TO_TIMESTAMP('2019-10-12 20:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='de_CH'
;

-- 2019-10-12T18:46:48.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'de_CH') 
;

-- 2019-10-12T18:46:54.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.',Updated=TO_TIMESTAMP('2019-10-12 20:46:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='de_DE'
;

-- 2019-10-12T18:46:54.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'de_DE') 
;

-- 2019-10-12T18:46:54.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577201,'de_DE') 
;

-- 2019-10-12T18:46:54.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_HU_PackagingCode_LU_Text', Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.' WHERE AD_Element_ID=577201
;

-- 2019-10-12T18:46:54.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_PackagingCode_LU_Text', Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.', AD_Element_ID=577201 WHERE UPPER(ColumnName)='M_HU_PACKAGINGCODE_LU_TEXT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-12T18:46:54.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_PackagingCode_LU_Text', Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.' WHERE AD_Element_ID=577201 AND IsCentrallyMaintained='Y'
;

-- 2019-10-12T18:46:54.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577201) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577201)
;

-- 2019-10-12T18:46:54.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.', CommitWarning = NULL WHERE AD_Element_ID = 577201
;

-- 2019-10-12T18:46:54.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.' WHERE AD_Element_ID = 577201
;

-- 2019-10-12T18:46:54.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'M_HU_PackagingCode_LU_Text', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577201
;

-- 2019-10-12T18:46:58.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.',Updated=TO_TIMESTAMP('2019-10-12 20:46:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='de_CH'
;

-- 2019-10-12T18:46:58.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'de_CH') 
;

-- 2019-10-12T18:47:07.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_LU_ID. 
Not for display, just for DESADV-export.',Updated=TO_TIMESTAMP('2019-10-12 20:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='en_US'
;

-- 2019-10-12T18:47:07.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'en_US') 
;

-- 2019-10-12T18:49:36.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for DESADV-export.',Updated=TO_TIMESTAMP('2019-10-12 20:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577202 AND AD_Language='en_US'
;

-- 2019-10-12T18:49:36.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577202,'en_US') 
;

-- 2019-10-12T18:49:51.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577202 AND AD_Language='nl_NL'
;

-- 2019-10-12T18:49:51.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577202,'nl_NL') 
;

-- 2019-10-12T18:50:01.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577202 AND AD_Language='en_US'
;

-- 2019-10-12T18:50:01.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577202,'en_US') 
;

-- 2019-10-12T18:50:07.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:50:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577202 AND AD_Language='de_DE'
;

-- 2019-10-12T18:50:07.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577202,'de_DE') 
;

-- 2019-10-12T18:50:07.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577202,'de_DE') 
;

-- 2019-10-12T18:50:07.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_HU_PackagingCode_TU_Text', Name='M_HU_PackagingCode_TU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE AD_Element_ID=577202
;

-- 2019-10-12T18:50:07.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_PackagingCode_TU_Text', Name='M_HU_PackagingCode_TU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.', AD_Element_ID=577202 WHERE UPPER(ColumnName)='M_HU_PACKAGINGCODE_TU_TEXT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-12T18:50:07.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_PackagingCode_TU_Text', Name='M_HU_PackagingCode_TU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE AD_Element_ID=577202 AND IsCentrallyMaintained='Y'
;

-- 2019-10-12T18:50:07.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='M_HU_PackagingCode_TU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577202) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577202)
;

-- 2019-10-12T18:50:07.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='M_HU_PackagingCode_TU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.', CommitWarning = NULL WHERE AD_Element_ID = 577202
;

-- 2019-10-12T18:50:07.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='M_HU_PackagingCode_TU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE AD_Element_ID = 577202
;

-- 2019-10-12T18:50:07.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'M_HU_PackagingCode_TU_Text', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577202
;

-- 2019-10-12T18:50:11.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577202 AND AD_Language='de_CH'
;

-- 2019-10-12T18:50:11.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577202,'de_CH') 
;

-- 2019-10-12T18:50:36.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:50:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='de_CH'
;

-- 2019-10-12T18:50:36.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'de_CH') 
;

-- 2019-10-12T18:50:38.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='de_DE'
;

-- 2019-10-12T18:50:38.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'de_DE') 
;

-- 2019-10-12T18:50:38.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577201,'de_DE') 
;

-- 2019-10-12T18:50:38.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_HU_PackagingCode_LU_Text', Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE AD_Element_ID=577201
;

-- 2019-10-12T18:50:38.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_PackagingCode_LU_Text', Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.', AD_Element_ID=577201 WHERE UPPER(ColumnName)='M_HU_PACKAGINGCODE_LU_TEXT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-12T18:50:38.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_HU_PackagingCode_LU_Text', Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE AD_Element_ID=577201 AND IsCentrallyMaintained='Y'
;

-- 2019-10-12T18:50:38.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577201) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577201)
;

-- 2019-10-12T18:50:38.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.', CommitWarning = NULL WHERE AD_Element_ID = 577201
;

-- 2019-10-12T18:50:38.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='M_HU_PackagingCode_LU_Text', Description=NULL, Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.' WHERE AD_Element_ID = 577201
;

-- 2019-10-12T18:50:38.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'M_HU_PackagingCode_LU_Text', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577201
;

-- 2019-10-12T18:50:42.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='en_US'
;

-- 2019-10-12T18:50:42.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'en_US') 
;

-- 2019-10-12T18:50:45.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.',Updated=TO_TIMESTAMP('2019-10-12 20:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577201 AND AD_Language='nl_NL'
;

-- 2019-10-12T18:50:45.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577201,'nl_NL') 
;

-- 2019-10-12T18:50:58.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline','M_HU_PackagingCode_LU_Text','VARCHAR(4)',null,null)
;

-- 2019-10-12T18:53:11.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569180,577202,0,10,540645,'M_HU_PackagingCode_TU_Text',TO_TIMESTAMP('2019-10-12 20:53:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',4,'The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','M_HU_PackagingCode_TU_Text',0,0,TO_TIMESTAMP('2019-10-12 20:53:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-12T18:53:11.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569180 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-12T18:53:11.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577202) 
;

-- 2019-10-12T18:53:13.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN M_HU_PackagingCode_TU_Text VARCHAR(4)')
;

-- 2019-10-12T19:02:41.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569179,0,TO_TIMESTAMP('2019-10-12 21:02:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550253,'The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.','N','N','M_HU_PackagingCode_LU_Text',310,'E',TO_TIMESTAMP('2019-10-12 21:02:41','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_LU_Text')
;

-- 2019-10-12T19:02:41.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569180,0,TO_TIMESTAMP('2019-10-12 21:02:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550254,'The current PackagingCode string from the current M_HU_PackagingCode_TU_ID. 
Not for display, just for EDI-export.','N','N','M_HU_PackagingCode_TU_Text',320,'E',TO_TIMESTAMP('2019-10-12 21:02:41','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_TU_Text')
;

-- 2019-10-12T19:02:56.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-12 21:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550253
;

-- 2019-10-12T19:03:01.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-10-12 21:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550254
;

