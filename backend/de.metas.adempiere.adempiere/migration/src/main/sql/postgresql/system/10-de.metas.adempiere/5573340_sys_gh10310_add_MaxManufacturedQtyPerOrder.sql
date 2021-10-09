-- 2020-11-26T05:01:51.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578575,0,'MaxManufacturedQtyPerOrder',TO_TIMESTAMP('2020-11-26 06:01:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Max. Menge pro Produktionsauftrag','Max. Menge pro Produktionsauftrag',TO_TIMESTAMP('2020-11-26 06:01:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T05:01:51.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578575 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-26T05:01:55.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-26 06:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='de_CH'
;

-- 2020-11-26T05:01:55.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'de_CH') 
;

-- 2020-11-26T05:02:01.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-26 06:02:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='de_DE'
;

-- 2020-11-26T05:02:01.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'de_DE') 
;

-- 2020-11-26T05:02:01.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578575,'de_DE') 
;

-- 2020-11-26T05:02:29.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Max. quantity per manufactoring order', PrintName='Max. quantity per manufactoring order',Updated=TO_TIMESTAMP('2020-11-26 06:02:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='en_US'
;

-- 2020-11-26T05:02:29.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'en_US') 
;

-- 2020-11-26T05:07:16.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.',Updated=TO_TIMESTAMP('2020-11-26 06:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='de_CH'
;

-- 2020-11-26T05:07:16.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'de_CH') 
;

-- 2020-11-26T05:07:23.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.',Updated=TO_TIMESTAMP('2020-11-26 06:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='de_DE'
;

-- 2020-11-26T05:07:23.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'de_DE') 
;

-- 2020-11-26T05:07:23.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578575,'de_DE') 
;

-- 2020-11-26T05:07:23.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MaxManufacturedQtyPerOrder', Name='Max. Menge pro Produktionsauftrag', Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.', Help=NULL WHERE AD_Element_ID=578575
;

-- 2020-11-26T05:07:23.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxManufacturedQtyPerOrder', Name='Max. Menge pro Produktionsauftrag', Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.', Help=NULL, AD_Element_ID=578575 WHERE UPPER(ColumnName)='MAXMANUFACTUREDQTYPERORDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-26T05:07:23.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxManufacturedQtyPerOrder', Name='Max. Menge pro Produktionsauftrag', Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.', Help=NULL WHERE AD_Element_ID=578575 AND IsCentrallyMaintained='Y'
;

-- 2020-11-26T05:07:23.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Max. Menge pro Produktionsauftrag', Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578575) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578575)
;

-- 2020-11-26T05:07:23.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Max. Menge pro Produktionsauftrag', Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578575
;

-- 2020-11-26T05:07:23.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Max. Menge pro Produktionsauftrag', Description='Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.', Help=NULL WHERE AD_Element_ID = 578575
;

-- 2020-11-26T05:07:23.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Max. Menge pro Produktionsauftrag', Description = 'Steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578575
;

-- 2020-11-26T05:08:38.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder <=0 bedeuten "kein Maximum".',Updated=TO_TIMESTAMP('2020-11-26 06:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='de_CH'
;

-- 2020-11-26T05:08:38.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'de_CH') 
;

-- 2020-11-26T05:08:53.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".',Updated=TO_TIMESTAMP('2020-11-26 06:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='de_CH'
;

-- 2020-11-26T05:08:53.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'de_CH') 
;

-- 2020-11-26T05:08:58.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".',Updated=TO_TIMESTAMP('2020-11-26 06:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578575 AND AD_Language='de_DE'
;

-- 2020-11-26T05:08:58.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578575,'de_DE') 
;

-- 2020-11-26T05:08:58.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578575,'de_DE') 
;

-- 2020-11-26T05:08:58.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MaxManufacturedQtyPerOrder', Name='Max. Menge pro Produktionsauftrag', Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".', Help=NULL WHERE AD_Element_ID=578575
;

-- 2020-11-26T05:08:58.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxManufacturedQtyPerOrder', Name='Max. Menge pro Produktionsauftrag', Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".', Help=NULL, AD_Element_ID=578575 WHERE UPPER(ColumnName)='MAXMANUFACTUREDQTYPERORDER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-26T05:08:58.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MaxManufacturedQtyPerOrder', Name='Max. Menge pro Produktionsauftrag', Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".', Help=NULL WHERE AD_Element_ID=578575 AND IsCentrallyMaintained='Y'
;

-- 2020-11-26T05:08:58.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Max. Menge pro Produktionsauftrag', Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578575) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578575)
;

-- 2020-11-26T05:08:58.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Max. Menge pro Produktionsauftrag', Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578575
;

-- 2020-11-26T05:08:58.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Max. Menge pro Produktionsauftrag', Description='Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".', Help=NULL WHERE AD_Element_ID = 578575
;

-- 2020-11-26T05:08:58.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Max. Menge pro Produktionsauftrag', Description = 'Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578575
;

-- 2020-11-26T05:09:07.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,FacetFilterSeqNo,MaxFacetsToFetch,IsFacetFilter,AD_Element_ID) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-11-26 06:09:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-11-26 06:09:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N',53020,'N',572239,'N','N','N','N','N','N','N','N',0,'EE01','N','N','MaxManufacturedQtyPerOrder','N','Max. Menge pro Produktionsauftrag',0,'Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".',0,0,'N',578575)
;

-- 2020-11-26T05:09:07.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572239 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-11-26T05:09:07.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578575) 
;

-- 2020-11-26T05:09:15.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN MaxManufacturedQtyPerOrder NUMERIC')
;

-- 2020-11-26T05:10:18.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578576,0,'MaxManufacturedQtyPerOrder_UOM_ID',TO_TIMESTAMP('2020-11-26 06:10:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Maßeinheit','Maßeinheit',TO_TIMESTAMP('2020-11-26 06:10:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T05:10:18.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578576 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-26T05:10:32.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-26 06:10:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578576 AND AD_Language='de_CH'
;

-- 2020-11-26T05:10:32.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578576,'de_CH') 
;

-- 2020-11-26T05:10:35.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-26 06:10:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578576 AND AD_Language='de_DE'
;

-- 2020-11-26T05:10:35.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578576,'de_DE') 
;

-- 2020-11-26T05:10:35.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578576,'de_DE') 
;

-- 2020-11-26T05:10:49.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Unit of measurement', PrintName='Unit of measurement',Updated=TO_TIMESTAMP('2020-11-26 06:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578576 AND AD_Language='en_US'
;

-- 2020-11-26T05:10:49.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578576,'en_US') 
;

-- 2020-11-26T05:25:18.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='AD_Val_Rule',Updated=TO_TIMESTAMP('2020-11-26 06:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=103
;

-- 2020-11-26T05:34:28.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET TechnicalNote='m:n Table between composite parent validation rules and their "included" child validation rules',Updated=TO_TIMESTAMP('2020-11-26 06:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=108
;

-- 2020-11-26T05:36:24.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Val_Rule_ID,AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,FacetFilterSeqNo,MaxFacetsToFetch,IsFacetFilter,AD_Element_ID) VALUES (210,18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-11-26 06:36:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-11-26 06:36:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N',53020,'N',114,572240,'N','N','N','N','N','N','N','N',0,'EE01','N','N','MaxManufacturedQtyPerOrder_UOM_ID','N','Maßeinheit',0,0,0,'N',578576)
;

-- 2020-11-26T05:36:24.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572240 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-11-26T05:36:24.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578576) 
;

-- 2020-11-26T05:39:00.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@MaxManufacturedQtyPerOrder/0@!0 & @MaxManufacturedQtyPerOrder/0@>0',Updated=TO_TIMESTAMP('2020-11-26 06:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572240
;

-- 2020-11-26T05:39:26.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN MaxManufacturedQtyPerOrder_UOM_ID NUMERIC(10)')
;

-- 2020-11-26T05:39:26.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Product_Planning ADD CONSTRAINT MaxManufacturedQtyPerOrderUOM_PPProductPlanning FOREIGN KEY (MaxManufacturedQtyPerOrder_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2020-11-26T05:39:36.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ValueMin='0',Updated=TO_TIMESTAMP('2020-11-26 06:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572239
;

-- 2020-11-26T05:39:39.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('pp_product_planning','MaxManufacturedQtyPerOrder','NUMERIC',null,null)
;

-- 2020-11-26T05:41:59.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572239,626447,0,542102,0,TO_TIMESTAMP('2020-11-26 06:41:59','YYYY-MM-DD HH24:MI:SS'),100,'Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".',0,'D',0,'Y','Y','Y','N','N','N','N','N','Max. Menge pro Produktionsauftrag',10,10,0,1,1,TO_TIMESTAMP('2020-11-26 06:41:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T05:41:59.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626447 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-26T05:41:59.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578575) 
;

-- 2020-11-26T05:41:59.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626447
;

-- 2020-11-26T05:41:59.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(626447)
;

-- 2020-11-26T05:42:58.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572240,626448,0,542102,0,TO_TIMESTAMP('2020-11-26 06:42:58','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','Maßeinheit',20,20,0,1,1,TO_TIMESTAMP('2020-11-26 06:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T05:42:58.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626448 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-26T05:42:58.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578576) 
;

-- 2020-11-26T05:42:58.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626448
;

-- 2020-11-26T05:42:58.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(626448)
;

-- 2020-11-26T05:48:39.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2020-11-26 06:48:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563785
;

-- 2020-11-26T05:48:43.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2020-11-26 06:48:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563780
;

-- 2020-11-26T05:48:58.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2020-11-26 06:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564012
;

-- 2020-11-26T05:49:48.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,626447,0,542102,543143,575353,'F',TO_TIMESTAMP('2020-11-26 06:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Optional; steuert, wie viele Produktionsaufträge bei einer bestimmten zu produzierenden Menge durch die Material-Dispo erzeugt werden. "Leer" oder kleiner-gleich Null bedeuten "kein Maximum".','Y','N','N','Y','N','N','N',0,'Max. Menge pro Produktionsauftrag',80,0,0,TO_TIMESTAMP('2020-11-26 06:49:48','YYYY-MM-DD HH24:MI:SS'),100,'')
;

-- 2020-11-26T05:53:11.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,626448,0,540817,575353,TO_TIMESTAMP('2020-11-26 06:53:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2020-11-26 06:53:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T05:54:45.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManufactured/''N''@=''Y''',Updated=TO_TIMESTAMP('2020-11-26 06:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=626447
;

-- 2020-11-26T05:54:49.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManufactured/''N''@=''Y''',Updated=TO_TIMESTAMP('2020-11-26 06:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=626448
;

-- 2020-11-26T05:56:44.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540817
;

-- 2020-11-26T05:57:16.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,626448,0,542102,543143,575354,'F',TO_TIMESTAMP('2020-11-26 06:57:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'MaxManufacturedQtyPerOrder_UOM_ID',90,0,0,TO_TIMESTAMP('2020-11-26 06:57:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T05:57:24.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='MaxManufacturedQtyPerOrder',Updated=TO_TIMESTAMP('2020-11-26 06:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575353
;

-- 2020-11-26T05:58:05.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManufactured/''N''@=''Y''',Updated=TO_TIMESTAMP('2020-11-26 06:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591855
;

-- 2020-11-26T05:58:43.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManufactured/''N''@=''Y'' & @IsPickingOrder/''Y''@=''N''',Updated=TO_TIMESTAMP('2020-11-26 06:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=626447
;

-- 2020-11-26T05:58:50.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManufactured/''N''@=''Y'' & @IsPickingOrder/''Y''@=''N''',Updated=TO_TIMESTAMP('2020-11-26 06:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=626448
;

-- 2020-11-26T07:25:10.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=75,Updated=TO_TIMESTAMP('2020-11-26 08:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=564012
;

-- 2020-11-26T07:26:16.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2020-11-26 08:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591254
;

