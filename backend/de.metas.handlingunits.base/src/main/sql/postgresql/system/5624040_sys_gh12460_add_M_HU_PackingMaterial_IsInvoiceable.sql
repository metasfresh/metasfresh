-- 2022-02-01T17:44:08.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580536,0,'IsInvoiceable',TO_TIMESTAMP('2022-02-01 19:44:08','YYYY-MM-DD HH24:MI:SS'),100,'If ticked, this packing material will also be invoiced when used for a shipment','D','Y','Include in Invoice','Include in Invoice',TO_TIMESTAMP('2022-02-01 19:44:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-01T17:44:08.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580536 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-02-01T17:45:13.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579181,580536,0,20,540519,'IsInvoiceable',TO_TIMESTAMP('2022-02-01 19:45:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','If ticked, this packing material will also be invoiced when used for a shipment','de.metas.handlingunits',0,1,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Y','N',0,'Include in Invoice',0,0,TO_TIMESTAMP('2022-02-01 19:45:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-01T17:45:13.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579181 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-01T17:45:13.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580536) 
;

-- 2022-02-01T17:45:17.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_PackingMaterial','ALTER TABLE public.M_HU_PackingMaterial ADD COLUMN IsInvoiceable CHAR(1) DEFAULT ''Y'' CHECK (IsInvoiceable IN (''Y'',''N'')) NOT NULL')
;

-- 2022-02-01T17:47:02.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579181,678378,0,540521,0,TO_TIMESTAMP('2022-02-01 19:47:02','YYYY-MM-DD HH24:MI:SS'),100,'If ticked, this packing material will also be invoiced when used for a shipment',0,'D',0,'Y','Y','Y','N','N','N','N','N','Include in Invoice',0,200,0,1,1,TO_TIMESTAMP('2022-02-01 19:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-01T17:47:02.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=678378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-01T17:47:02.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580536) 
;

-- 2022-02-01T17:47:02.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678378
;

-- 2022-02-01T17:47:02.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(678378)
;

-- 2022-02-01T17:47:52.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,678378,0,540521,540535,600360,'F',TO_TIMESTAMP('2022-02-01 19:47:52','YYYY-MM-DD HH24:MI:SS'),100,'If ticked, this packing material will also be invoiced when used for a shipment','Y','N','N','Y','N','N','N',0,'Include in Invoice',30,0,0,TO_TIMESTAMP('2022-02-01 19:47:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-02T14:51:17.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='In Rechnung stellen',Updated=TO_TIMESTAMP('2022-02-02 16:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580536 AND AD_Language='de_CH'
;

-- 2022-02-02T14:51:17.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580536,'de_CH') 
;

-- 2022-02-02T14:51:22.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='In Rechnung stellen', PrintName='In Rechnung stellen',Updated=TO_TIMESTAMP('2022-02-02 16:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580536 AND AD_Language='de_DE'
;

-- 2022-02-02T14:51:22.561Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580536,'de_DE') 
;

-- 2022-02-02T14:51:22.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580536,'de_DE') 
;

-- 2022-02-02T14:51:22.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvoiceable', Name='In Rechnung stellen', Description='If ticked, this packing material will also be invoiced when used for a shipment', Help=NULL WHERE AD_Element_ID=580536
;

-- 2022-02-02T14:51:22.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceable', Name='In Rechnung stellen', Description='If ticked, this packing material will also be invoiced when used for a shipment', Help=NULL, AD_Element_ID=580536 WHERE UPPER(ColumnName)='ISINVOICEABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-02T14:51:22.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceable', Name='In Rechnung stellen', Description='If ticked, this packing material will also be invoiced when used for a shipment', Help=NULL WHERE AD_Element_ID=580536 AND IsCentrallyMaintained='Y'
;

-- 2022-02-02T14:51:22.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='In Rechnung stellen', Description='If ticked, this packing material will also be invoiced when used for a shipment', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580536) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580536)
;

-- 2022-02-02T14:51:22.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='In Rechnung stellen', Name='In Rechnung stellen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580536)
;

-- 2022-02-02T14:51:22.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='In Rechnung stellen', Description='If ticked, this packing material will also be invoiced when used for a shipment', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580536
;

-- 2022-02-02T14:51:22.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='In Rechnung stellen', Description='If ticked, this packing material will also be invoiced when used for a shipment', Help=NULL WHERE AD_Element_ID = 580536
;

-- 2022-02-02T14:51:22.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'In Rechnung stellen', Description = 'If ticked, this packing material will also be invoiced when used for a shipment', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580536
;

-- 2022-02-02T14:51:24.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='In Rechnung stellen',Updated=TO_TIMESTAMP('2022-02-02 16:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580536 AND AD_Language='de_CH'
;

-- 2022-02-02T14:51:24.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580536,'de_CH') 
;

-- 2022-02-02T14:51:32.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='In Rechnung stellen', PrintName='In Rechnung stellen',Updated=TO_TIMESTAMP('2022-02-02 16:51:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580536 AND AD_Language='nl_NL'
;

-- 2022-02-02T14:51:32.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580536,'nl_NL') 
;

-- 2022-02-02T14:51:34.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.',Updated=TO_TIMESTAMP('2022-02-02 16:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580536 AND AD_Language='de_CH'
;

-- 2022-02-02T14:51:34.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580536,'de_CH') 
;

-- 2022-02-02T14:51:35.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.',Updated=TO_TIMESTAMP('2022-02-02 16:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580536 AND AD_Language='de_DE'
;

-- 2022-02-02T14:51:35.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580536,'de_DE') 
;

-- 2022-02-02T14:51:35.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580536,'de_DE') 
;

-- 2022-02-02T14:51:35.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsInvoiceable', Name='In Rechnung stellen', Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.', Help=NULL WHERE AD_Element_ID=580536
;

-- 2022-02-02T14:51:35.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceable', Name='In Rechnung stellen', Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.', Help=NULL, AD_Element_ID=580536 WHERE UPPER(ColumnName)='ISINVOICEABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-02-02T14:51:35.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsInvoiceable', Name='In Rechnung stellen', Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.', Help=NULL WHERE AD_Element_ID=580536 AND IsCentrallyMaintained='Y'
;

-- 2022-02-02T14:51:35.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='In Rechnung stellen', Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580536) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580536)
;

-- 2022-02-02T14:51:35.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='In Rechnung stellen', Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580536
;

-- 2022-02-02T14:51:35.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='In Rechnung stellen', Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.', Help=NULL WHERE AD_Element_ID = 580536
;

-- 2022-02-02T14:51:35.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'In Rechnung stellen', Description = 'Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580536
;

-- 2022-02-02T14:51:37.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, wird dieses Packmittel bei Verwendung für eine Sendung auch in Rechnung gestellt.',Updated=TO_TIMESTAMP('2022-02-02 16:51:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580536 AND AD_Language='nl_NL'
;

-- 2022-02-02T14:51:37.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580536,'nl_NL') 
;
