-- 2019-11-18T08:15:39.093Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-18 10:15:38','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2019-11-18 10:15:38','YYYY-MM-DD HH24:MI:SS'),100,577358,'ShipperProduct','Shipper Product','Shipper Product','D')
;

-- 2019-11-18T08:15:39.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577358 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-18T08:15:53.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,EntityType) VALUES (10,16,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-11-18 10:15:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-11-18 10:15:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541433,'N',569593,'N','N','N','N','N','N','N','N',0,577358,'N','N','ShipperProduct','N','Shipper Product',0,'de.metas.shipper.gateway.dpd')
;

-- 2019-11-18T08:15:53.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569593 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-11-18T08:15:53.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577358) 
;

-- 2019-11-18T08:16:01.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2019-11-18 10:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569593
;

-- 2019-11-18T08:16:04.916Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DPD_Shipper_Config','ALTER TABLE public.DPD_Shipper_Config ADD COLUMN ShipperProduct VARCHAR(255)')
;

-- 2019-11-18T08:17:46.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,IncludedTabHeight,ColumnDisplayLength,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (542101,'Y',255,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-11-18 10:17:46','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-11-18 10:17:46','YYYY-MM-DD HH24:MI:SS'),100,591579,'Y',60,60,1,1,569593,'Shipper Product',0,'D')
;

-- 2019-11-18T08:17:46.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-18T08:17:46.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577358) 
;

-- 2019-11-18T08:17:46.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591579
;

-- 2019-11-18T08:17:46.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591579)
;

-- 2019-11-18T08:25:04.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-18 10:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591579
;

-- 2019-11-18T08:25:29.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,563958,0,100,60,0,'N',0,0,543141,'F','N',0,'N','Shipper Product',591579,542101,TO_TIMESTAMP('2019-11-18 10:25:29','YYYY-MM-DD HH24:MI:SS'),'Y','N','Y','N',TO_TIMESTAMP('2019-11-18 10:25:29','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-11-18T09:23:04.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Lieferweg Produkt', IsTranslated='Y', Name='Lieferweg Produkt',Updated=TO_TIMESTAMP('2019-11-18 11:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577358
;

-- 2019-11-18T09:23:04.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577358,'de_CH') 
;

-- 2019-11-18T09:23:08.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Lieferweg Produkt', IsTranslated='Y', Name='Lieferweg Produkt',Updated=TO_TIMESTAMP('2019-11-18 11:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577358
;

-- 2019-11-18T09:23:08.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577358,'de_DE') 
;

-- 2019-11-18T09:23:08.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577358,'de_DE') 
;

-- 2019-11-18T09:23:08.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ShipperProduct', Name='Lieferweg Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577358
;

-- 2019-11-18T09:23:08.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipperProduct', Name='Lieferweg Produkt', Description=NULL, Help=NULL, AD_Element_ID=577358 WHERE UPPER(ColumnName)='SHIPPERPRODUCT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-18T09:23:08.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ShipperProduct', Name='Lieferweg Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID=577358 AND IsCentrallyMaintained='Y'
;

-- 2019-11-18T09:23:08.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferweg Produkt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577358) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577358)
;

-- 2019-11-18T09:23:08.257Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferweg Produkt', Name='Lieferweg Produkt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577358)
;

-- 2019-11-18T09:23:08.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferweg Produkt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577358
;

-- 2019-11-18T09:23:08.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferweg Produkt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577358
;

-- 2019-11-18T09:23:08.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferweg Produkt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577358
;

-- 2019-11-18T09:23:10.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-18 11:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577358
;

-- 2019-11-18T09:23:10.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577358,'en_US') 
;

