-- 2021-05-07T13:13:38.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579143,0,'OrganisationInCharge',TO_TIMESTAMP('2021-05-07 16:13:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisation which is in charge of this postal number.','D','Y','Organisation in charge','Organisation in charge',TO_TIMESTAMP('2021-05-07 16:13:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:13:38.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579143 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-07T13:14:28.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Organisation, die für diese Postleitzahl zuständig ist.', IsTranslated='Y', Name='Zuständige Organisation', PrintName='Zuständige Organisation',Updated=TO_TIMESTAMP('2021-05-07 16:14:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579143 AND AD_Language='de_CH'
;

-- 2021-05-07T13:14:28.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579143,'de_CH') 
;

-- 2021-05-07T13:14:37.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Organisation, die für diese Postleitzahl zuständig ist.', IsTranslated='Y', Name='Zuständige Organisation', PrintName='Zuständige Organisation',Updated=TO_TIMESTAMP('2021-05-07 16:14:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579143 AND AD_Language='de_DE'
;

-- 2021-05-07T13:14:37.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579143,'de_DE') 
;

-- 2021-05-07T13:14:37.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579143,'de_DE') 
;

-- 2021-05-07T13:14:37.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='OrganisationInCharge', Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL WHERE AD_Element_ID=579143
;

-- 2021-05-07T13:14:37.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrganisationInCharge', Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL, AD_Element_ID=579143 WHERE UPPER(ColumnName)='ORGANISATIONINCHARGE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T13:14:37.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='OrganisationInCharge', Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL WHERE AD_Element_ID=579143 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T13:14:37.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579143) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579143)
;

-- 2021-05-07T13:14:37.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zuständige Organisation', Name='Zuständige Organisation' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579143)
;

-- 2021-05-07T13:14:37.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579143
;

-- 2021-05-07T13:14:37.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL WHERE AD_Element_ID = 579143
;

-- 2021-05-07T13:14:37.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zuständige Organisation', Description = 'Organisation, die für diese Postleitzahl zuständig ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579143
;

-- 2021-05-07T13:14:56.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='AD_Org_InCharge_ID',Updated=TO_TIMESTAMP('2021-05-07 16:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579143
;

-- 2021-05-07T13:14:56.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AD_Org_InCharge_ID', Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL WHERE AD_Element_ID=579143
;

-- 2021-05-07T13:14:56.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_InCharge_ID', Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL, AD_Element_ID=579143 WHERE UPPER(ColumnName)='AD_ORG_INCHARGE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T13:14:56.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AD_Org_InCharge_ID', Name='Zuständige Organisation', Description='Organisation, die für diese Postleitzahl zuständig ist.', Help=NULL WHERE AD_Element_ID=579143 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T13:15:47.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573823,579143,0,18,322,540317,'AD_Org_InCharge_ID',TO_TIMESTAMP('2021-05-07 16:15:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisation, die für diese Postleitzahl zuständig ist.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zuständige Organisation',0,0,TO_TIMESTAMP('2021-05-07 16:15:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-07T13:15:47.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573823 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-07T13:15:47.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579143) 
;

-- 2021-05-07T13:15:59.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Postal','ALTER TABLE public.C_Postal ADD COLUMN AD_Org_InCharge_ID NUMERIC(10)')
;

-- 2021-05-07T13:15:59.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Postal ADD CONSTRAINT ADOrgInCharge_CPostal FOREIGN KEY (AD_Org_InCharge_ID) REFERENCES public.AD_Org DEFERRABLE INITIALLY DEFERRED
;

-- 2021-05-07T13:16:14.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573823,645422,0,540870,TO_TIMESTAMP('2021-05-07 16:16:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisation, die für diese Postleitzahl zuständig ist.',10,'D','Y','N','N','N','N','N','N','N','Zuständige Organisation',TO_TIMESTAMP('2021-05-07 16:16:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T13:16:14.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645422 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-07T13:16:14.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579143) 
;

-- 2021-05-07T13:16:14.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645422
;

-- 2021-05-07T13:16:14.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645422)
;

-- 2021-05-07T13:17:31.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645422,0,540870,584684,541142,'F',TO_TIMESTAMP('2021-05-07 16:17:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisation, die für diese Postleitzahl zuständig ist.','Y','N','N','Y','N','N','N',0,'Zuständige Organisation',30,0,0,TO_TIMESTAMP('2021-05-07 16:17:31','YYYY-MM-DD HH24:MI:SS'),100)
;

