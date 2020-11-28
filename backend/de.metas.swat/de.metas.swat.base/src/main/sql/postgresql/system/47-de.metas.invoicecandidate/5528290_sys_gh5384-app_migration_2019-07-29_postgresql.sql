-- 2019-07-29T11:32:04.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568491,576953,0,18,114,500221,'Catch_UOM_ID',TO_TIMESTAMP('2019-07-29 13:32:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Catch Weight Einheit',0,0,TO_TIMESTAMP('2019-07-29 13:32:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T11:32:04.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568491 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T11:32:04.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2019-07-29T12:18:15.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576962,0,'QtyToDeliverCatch_Override',TO_TIMESTAMP('2019-07-29 14:18:15','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Abw. Catch Weight Menge','Abw. Catch Weight Menge',TO_TIMESTAMP('2019-07-29 14:18:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:18:15.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576962 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T12:18:27.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568497,576962,0,29,500221,'QtyToDeliverCatch_Override',TO_TIMESTAMP('2019-07-29 14:18:27','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Abw. Catch Weight Menge',0,0,TO_TIMESTAMP('2019-07-29 14:18:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T12:18:27.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568497 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T12:18:27.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576962) 
;

-- 2019-07-29T12:18:28.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN QtyToDeliverCatch_Override NUMERIC')
;

-- 2019-07-29T12:18:42.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2019-07-29T12:18:42.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT CatchUOM_MShipmentSchedule FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

ALTER TABLE C_Invoice_Candidate RENAME COLUMN QtyToInvoiceInPriceUOM_CatchWeight TO QtyToInvoiceInPriceUOM_Catch;
-- 2019-07-29T12:21:51.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyToInvoiceInPriceUOM_Catch',Updated=TO_TIMESTAMP('2019-07-29 14:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941
;

-- 2019-07-29T12:21:52.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941
;

-- 2019-07-29T12:21:52.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=576941 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_CATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T12:21:52.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Catch Weight Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T12:22:04.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch Menge in Preiseinheit', PrintName='Catch Menge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-29 14:22:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_CH'
;

-- 2019-07-29T12:22:04.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_CH') 
;

-- 2019-07-29T12:22:12.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch Menge in Preiseinheit', PrintName='Catch Menge in Preiseinheit',Updated=TO_TIMESTAMP('2019-07-29 14:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_DE'
;

-- 2019-07-29T12:22:12.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_DE') 
;

-- 2019-07-29T12:22:12.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576941,'de_DE') 
;

-- 2019-07-29T12:22:12.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Catch Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941
;

-- 2019-07-29T12:22:12.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Catch Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=576941 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_CATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T12:22:12.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Catch Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T12:22:12.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Catch Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576941) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576941)
;

-- 2019-07-29T12:22:12.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Catch Menge in Preiseinheit', Name='Catch Menge in Preiseinheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576941)
;

-- 2019-07-29T12:22:12.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Catch Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T12:22:12.957Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Catch Menge in Preiseinheit', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T12:22:12.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Catch Menge in Preiseinheit', Description = 'Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T12:22:20.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch quantity in price-UOM', PrintName='Catch quantity in price-UOM',Updated=TO_TIMESTAMP('2019-07-29 14:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='en_US'
;

-- 2019-07-29T12:22:20.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'en_US') 
;

-- 2019-07-29T12:23:00.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576963,0,'QtyPickedCatch',TO_TIMESTAMP('2019-07-29 14:23:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kommissionierte Catch Menge','Kommissionierte Catch Menge',TO_TIMESTAMP('2019-07-29 14:23:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:23:00.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576963 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T12:24:24.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 14:24:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576963 AND AD_Language='de_CH'
;

-- 2019-07-29T12:24:24.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576963,'de_CH') 
;

-- 2019-07-29T12:24:30.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 14:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576963 AND AD_Language='de_DE'
;

-- 2019-07-29T12:24:30.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576963,'de_DE') 
;

-- 2019-07-29T12:24:30.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576963,'de_DE') 
;

-- 2019-07-29T12:24:30.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyPickedCatch', Name='Kommissionierte Catch Menge', Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576963
;

-- 2019-07-29T12:24:30.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedCatch', Name='Kommissionierte Catch Menge', Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', Help=NULL, AD_Element_ID=576963 WHERE UPPER(ColumnName)='QTYPICKEDCATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T12:24:30.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyPickedCatch', Name='Kommissionierte Catch Menge', Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576963 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T12:24:30.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kommissionierte Catch Menge', Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576963) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576963)
;

-- 2019-07-29T12:24:30.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kommissionierte Catch Menge', Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576963
;

-- 2019-07-29T12:24:30.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kommissionierte Catch Menge', Description='Tatsächliche kommisionierte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID = 576963
;

-- 2019-07-29T12:24:30.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kommissionierte Catch Menge', Description = 'Tatsächliche kommisionierte Menge in Catch Weight Einheit.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576963
;

-- 2019-07-29T12:24:37.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 14:24:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576963 AND AD_Language='en_US'
;

-- 2019-07-29T12:24:37.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576963,'en_US') 
;

-- 2019-07-29T12:25:01.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Actually picked quantity in catch weight UOM', Name='Picked catch quantity', PrintName='Picked catch quantity',Updated=TO_TIMESTAMP('2019-07-29 14:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576963 AND AD_Language='en_US'
;

-- 2019-07-29T12:25:01.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576963,'en_US') 
;

-- 2019-07-29T12:25:31.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568498,576963,0,29,500221,'QtyPickedCatch',TO_TIMESTAMP('2019-07-29 14:25:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Tatsächliche kommisionierte Menge in Catch Weight Einheit.','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Kommissionierte Catch Menge',0,0,TO_TIMESTAMP('2019-07-29 14:25:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T12:25:31.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568498 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T12:25:31.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576963) 
;

-- 2019-07-29T12:25:33.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN QtyPickedCatch NUMERIC')
;

-- 2019-07-29T12:29:14.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568491,582461,0,500221,0,TO_TIMESTAMP('2019-07-29 14:29:14','YYYY-MM-DD HH24:MI:SS'),100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',0,'de.metas.inoutcandidate',0,'Y','Y','Y','N','N','N','N','N','Catch Weight Einheit',580,650,0,1,1,TO_TIMESTAMP('2019-07-29 14:29:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:29:14.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582461 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:29:14.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953) 
;

-- 2019-07-29T12:29:14.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582461
;

-- 2019-07-29T12:29:14.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582461)
;

-- 2019-07-29T12:29:52.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568497,582462,0,500221,0,TO_TIMESTAMP('2019-07-29 14:29:52','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.inoutcandidate',0,'Y','Y','Y','N','N','N','N','N','Abw. Catch Weight Menge',590,660,0,1,1,TO_TIMESTAMP('2019-07-29 14:29:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T12:29:52.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T12:29:52.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576962) 
;

-- 2019-07-29T12:29:52.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582462
;

-- 2019-07-29T12:29:52.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582462)
;

-- 2019-07-29T12:34:31.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 14:34:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574151 AND AD_Language='de_CH'
;

-- 2019-07-29T12:34:31.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574151,'de_CH') 
;

-- 2019-07-29T12:34:39.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2019-07-29 14:34:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574151 AND AD_Language='en_US'
;

-- 2019-07-29T12:34:39.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574151,'en_US') 
;

-- 2019-07-29T12:34:49.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 14:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574151 AND AD_Language='de_DE'
;

-- 2019-07-29T12:34:49.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574151,'de_DE') 
;

-- 2019-07-29T12:34:49.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(574151,'de_DE') 
;

-- 2019-07-29T12:34:49.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Lieferdisposition', Description='Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', Help=NULL WHERE AD_Element_ID=574151
;

-- 2019-07-29T12:34:49.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Lieferdisposition', Description='Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', Help=NULL WHERE AD_Element_ID=574151 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T12:34:49.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferdisposition', Description='Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574151) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574151)
;

-- 2019-07-29T12:34:49.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Lieferdisposition', Description='Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574151
;

-- 2019-07-29T12:34:49.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Lieferdisposition', Description='Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', Help=NULL WHERE AD_Element_ID = 574151
;

-- 2019-07-29T12:34:49.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Lieferdisposition', Description = 'Zeigt alle zu Auslieferung anstehenden Auftragspositionen an und erlaubt Änderungen bzgl. Priorität und Stückzahl.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574151
;

-- 2019-07-29T13:38:40.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553212
;

-- 2019-07-29T13:38:44.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554061
;

-- 2019-07-29T13:38:46.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:38:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554060
;

-- 2019-07-29T13:38:53.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553211
;

-- 2019-07-29T13:38:55.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:38:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554062
;

-- 2019-07-29T13:39:03.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:39:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555544
;

-- 2019-07-29T13:39:14.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:39:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555542
;

-- 2019-07-29T13:39:19.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:39:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554132
;

-- 2019-07-29T13:39:24.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N',Updated=TO_TIMESTAMP('2019-07-29 15:39:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553926
;

-- 2019-07-29T13:40:33.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563270,582479,0,540279,TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','External ID',TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:40:33.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582479 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T13:40:33.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2019-07-29T13:40:33.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582479
;

-- 2019-07-29T13:40:33.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582479)
;

-- 2019-07-29T13:40:33.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568427,582480,0,540279,TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','IsFreightCost',TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:40:33.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582480 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T13:40:33.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576917) 
;

-- 2019-07-29T13:40:33.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582480
;

-- 2019-07-29T13:40:33.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582480)
;

-- 2019-07-29T13:40:33.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568474,582481,0,540279,TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,'Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.',10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Catch Menge in Preiseinheit',TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:40:33.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582481 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T13:40:33.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576941) 
;

-- 2019-07-29T13:40:33.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582481
;

-- 2019-07-29T13:40:33.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582481)
;

-- 2019-07-29T13:40:33.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568475,582482,0,540279,TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,'Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.',10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Zu ber. Menge in Preiseinheit eff.',TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:40:33.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582482 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T13:40:33.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576945) 
;

-- 2019-07-29T13:40:33.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582482
;

-- 2019-07-29T13:40:33.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582482)
;

-- 2019-07-29T13:40:33.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568478,582483,0,540279,TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.',11,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Abr. Menge basiert auf',TO_TIMESTAMP('2019-07-29 15:40:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:40:34.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582483 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T13:40:34.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576948) 
;

-- 2019-07-29T13:40:34.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582483
;

-- 2019-07-29T13:40:34.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582483)
;

-- 2019-07-29T13:41:40.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-07-29 15:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582480
;

-- 2019-07-29T13:45:21.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','QtyToInvoiceInPriceUOM_Catch','NUMERIC',null,null)
;

-- 2019-07-29T13:45:23.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','QtyToInvoiceInPriceUOM_Eff','NUMERIC',null,null)
;

-- 2019-07-29T13:45:25.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_invoice_candidate','QtyToInvoiceInPriceUOM_Nominal','NUMERIC',null,null)
;

-- moved to 5528289_sys_gh5384-app_migration_2019-07-29_postgresql_add_important_AD_stuff.sql
-- -- 2019-07-29T14:06:51.869Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568517,576948,0,17,541023,260,'InvoicableQtyBasedOn',TO_TIMESTAMP('2019-07-29 16:06:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Nominal','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','D',11,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Abr. Menge basiert auf',0,0,TO_TIMESTAMP('2019-07-29 16:06:51','YYYY-MM-DD HH24:MI:SS'),100,0)
-- ;
--
-- -- 2019-07-29T14:06:51.872Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568517 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
-- ;

-- 2019-07-29T14:06:51.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- moved to 5528289_sys_gh5384-app_migration_2019-07-29_postgresql_DDL
-- -- 2019-07-29T14:06:53.295Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- /* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN InvoicableQtyBasedOn VARCHAR(11) DEFAULT ''Nominal''')
-- ;

-- 2019-07-29T14:09:23.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568518,576948,0,17,541023,540244,'InvoicableQtyBasedOn',TO_TIMESTAMP('2019-07-29 16:09:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Nominal','Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.ordercandidate',11,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Abr. Menge basiert auf',0,0,TO_TIMESTAMP('2019-07-29 16:09:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T14:09:23.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568518 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T14:09:23.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576948) 
;

-- 2019-07-29T14:09:42.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OLCand','ALTER TABLE public.C_OLCand ADD COLUMN InvoicableQtyBasedOn VARCHAR(11) DEFAULT ''Nominal'' NOT NULL')
;

-- 2019-07-29T14:10:06.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-07-29 16:10:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568517
;

-- 2019-07-29T14:10:06.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_orderline','InvoicableQtyBasedOn','VARCHAR(11)',null,'Nominal')
;

-- 2019-07-29T14:10:06.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_OrderLine SET InvoicableQtyBasedOn='Nominal' WHERE InvoicableQtyBasedOn IS NULL
;

-- 2019-07-29T14:10:06.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_orderline','InvoicableQtyBasedOn',null,'NOT NULL',null)
;

-- 2019-07-29T15:00:39.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT COALESCE(SUM(ROUND((ic.QtyToInvoiceInPriceUOM_Eff * COALESCE(ic.PriceActual_Override, ic.PriceActual)), 2)), 0) from C_Invoice_Candidate ic where ic.C_Order_ID=C_Invoice_Candidate.C_Order_ID)',Updated=TO_TIMESTAMP('2019-07-29 17:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551284
;

-- 2019-07-29T15:00:51.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT COALESCE(SUM(ROUND((ic.QtyToInvoiceInPriceUOM_Eff * COALESCE(ic.PriceEntered_Override, ic.PriceEntered)), 2)), 0) from C_Invoice_Candidate ic where ic.C_Order_ID=C_Invoice_Candidate.C_Order_ID)',Updated=TO_TIMESTAMP('2019-07-29 17:00:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551763
;

-- 2019-07-29T15:06:42.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-07-29 17:06:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582481
;

-- 2019-07-29T15:06:50.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2019-07-29 17:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551671
;

-- 2019-07-29T15:07:16.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/N@=Y',Updated=TO_TIMESTAMP('2019-07-29 17:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582481
;

-- 2019-07-29T15:08:37.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/N@=Y',Updated=TO_TIMESTAMP('2019-07-29 17:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554677
;

-- 2019-07-29T15:12:34.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582483,0,540279,541087,560325,'F',TO_TIMESTAMP('2019-07-29 17:12:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'InvoicableQtyBasedOn',90,0,0,TO_TIMESTAMP('2019-07-29 17:12:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:12:59.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560325
;

-- 2019-07-29T15:13:16.941Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540046,542704,TO_TIMESTAMP('2019-07-29 17:13:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','qtyInPriceUOM',50,TO_TIMESTAMP('2019-07-29 17:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:13:35.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582483,0,540279,542704,560326,'F',TO_TIMESTAMP('2019-07-29 17:13:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'InvoicableQtyBasedOn',10,0,0,TO_TIMESTAMP('2019-07-29 17:13:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:14:09.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', DisplayLogic='@IsSOTrx/N@=Y', IsReadOnly='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-07-29 17:14:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582483
;

-- 2019-07-29T15:14:52.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554677,0,540279,542704,560327,'F',TO_TIMESTAMP('2019-07-29 17:14:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToInvoiceInPriceUOM_Nominal',11,0,0,TO_TIMESTAMP('2019-07-29 17:14:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:14:58.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-07-29 17:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560326
;

-- 2019-07-29T15:15:08.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-07-29 17:15:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560327
;

-- 2019-07-29T15:15:36.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582481,0,540279,542704,560328,'F',TO_TIMESTAMP('2019-07-29 17:15:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToInvoiceInPriceUOM_Catch',20,0,0,TO_TIMESTAMP('2019-07-29 17:15:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:16:13.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582482,0,540279,542704,560329,'F',TO_TIMESTAMP('2019-07-29 17:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToInvoiceInPriceUOM_Eff',40,0,0,TO_TIMESTAMP('2019-07-29 17:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:16:27.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsReadOnly='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-07-29 17:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582482
;

-- 2019-07-29T15:17:18.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-07-29 17:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582481
;

-- 2019-07-29T15:18:42.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554361,0,540279,542704,560330,'F',TO_TIMESTAMP('2019-07-29 17:18:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Price_UOM_ID',50,0,0,TO_TIMESTAMP('2019-07-29 17:18:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:18:51.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-07-29 17:18:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554361
;

-- 2019-07-29T15:20:33.825Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 17:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541053
;

-- 2019-07-29T15:20:44.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 17:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541052
;

-- 2019-07-29T15:49:40.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576969,0,'StockingUOM_ID',TO_TIMESTAMP('2019-07-29 17:49:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lagereinheit','Lagereinheit',TO_TIMESTAMP('2019-07-29 17:49:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T15:49:40.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576969 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T15:49:43.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 17:49:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576969 AND AD_Language='de_CH'
;

-- 2019-07-29T15:49:43.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576969,'de_CH') 
;

-- 2019-07-29T15:49:46.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 17:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576969 AND AD_Language='de_DE'
;

-- 2019-07-29T15:49:46.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576969,'de_DE') 
;

-- 2019-07-29T15:49:46.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576969,'de_DE') 
;

-- 2019-07-29T15:49:57.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Stocking UOM', PrintName='Stocking UOM',Updated=TO_TIMESTAMP('2019-07-29 17:49:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576969 AND AD_Language='en_US'
;

-- 2019-07-29T15:49:57.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576969,'en_US') 
;

-- 2019-07-29T15:50:59.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568519,576969,0,18,114,540270,'StockingUOM_ID','(select C_UOM_ID from M_Product p where p.M_Product_ID=C_Invoice_Candidate.M_Product_ID)',TO_TIMESTAMP('2019-07-29 17:50:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Lagereinheit',0,0,TO_TIMESTAMP('2019-07-29 17:50:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T15:50:59.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568519 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T15:50:59.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576969) 
;

-- 2019-07-29T19:06:25.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2019-07-29 21:06:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548108
;

-- 2019-07-29T19:06:27.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2019-07-29 21:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548109
;

-- 2019-07-29T19:06:34.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2019-07-29 21:06:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548110
;

-- 2019-07-29T19:06:36.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2019-07-29 21:06:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- 2019-07-29T19:06:39.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2019-07-29 21:06:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548112
;

-- 2019-07-29T19:07:04.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-07-29 21:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541079
;

-- 2019-07-29T19:07:18.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2019-07-29 21:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541083
;

-- 2019-07-29T19:09:43.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568519,582496,0,540279,0,TO_TIMESTAMP('2019-07-29 21:09:43','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Lagereinheit',1100,460,0,1,1,TO_TIMESTAMP('2019-07-29 21:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:09:43.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582496 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T19:09:43.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576969) 
;

-- 2019-07-29T19:09:43.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582496
;

-- 2019-07-29T19:09:43.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582496)
;

-- 2019-07-29T19:10:53.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582496,0,540279,541086,560331,'F',TO_TIMESTAMP('2019-07-29 21:10:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'StockingUOM_ID',100,0,0,TO_TIMESTAMP('2019-07-29 21:10:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:13:19.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Name='Beauftragt', PrintName='Beauftragt',Updated=TO_TIMESTAMP('2019-07-29 21:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576694 AND AD_Language='de_CH'
;

-- 2019-07-29T19:13:19.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576694,'de_CH') 
;

-- 2019-07-29T19:13:49.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Name='Beauftragt', PrintName='Beauftragt',Updated=TO_TIMESTAMP('2019-07-29 21:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576694 AND AD_Language='de_DE'
;

-- 2019-07-29T19:13:49.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576694,'de_DE') 
;

-- 2019-07-29T19:13:49.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576694,'de_DE') 
;

-- 2019-07-29T19:13:49.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyOrdered_In_Stocking_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Help='' WHERE AD_Element_ID=576694
;

-- 2019-07-29T19:13:49.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Stocking_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Help='', AD_Element_ID=576694 WHERE UPPER(ColumnName)='QTYORDERED_IN_STOCKING_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T19:13:49.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Stocking_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Help='' WHERE AD_Element_ID=576694 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T19:13:49.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beauftragt', Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576694) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576694)
;

-- 2019-07-29T19:13:49.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beauftragt', Name='Beauftragt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576694)
;

-- 2019-07-29T19:13:49.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beauftragt', Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Help='', CommitWarning = NULL WHERE AD_Element_ID = 576694
;

-- 2019-07-29T19:13:49.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beauftragt', Description='Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', Help='' WHERE AD_Element_ID = 576694
;

-- 2019-07-29T19:13:49.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beauftragt', Description = 'Beauftragte Menge in der Lager-Maßeinheit des jeweiligen Produktes.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576694
;

-- 2019-07-29T19:14:02.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Ordered quantity in the respective product''s stocking UOM.', Name='Ordered', PrintName='Ordered',Updated=TO_TIMESTAMP('2019-07-29 21:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576694 AND AD_Language='en_US'
;

-- 2019-07-29T19:14:02.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576694,'en_US') 
;

-- 2019-07-29T19:15:11.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540045,542705,TO_TIMESTAMP('2019-07-29 21:15:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','prices',30,TO_TIMESTAMP('2019-07-29 21:15:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:17:43.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548116
;

-- 2019-07-29T19:17:43.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548117
;

-- 2019-07-29T19:17:43.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548118
;

-- 2019-07-29T19:17:43.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548119
;

-- 2019-07-29T19:17:56.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=21,Updated=TO_TIMESTAMP('2019-07-29 21:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-07-29T19:18:06.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-07-29 21:18:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542705
;

-- 2019-07-29T19:18:12.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-07-29 21:18:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-07-29T19:18:57.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547029,0,540279,542705,560332,'F',TO_TIMESTAMP('2019-07-29 21:18:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PriceActual',10,0,0,TO_TIMESTAMP('2019-07-29 21:18:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:19:30.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,552879,0,540279,542705,560333,'F',TO_TIMESTAMP('2019-07-29 21:19:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PriceEntered_Override',20,0,0,TO_TIMESTAMP('2019-07-29 21:19:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:19:47.086Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547032,0,540279,542705,560334,'F',TO_TIMESTAMP('2019-07-29 21:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Discount',30,0,0,TO_TIMESTAMP('2019-07-29 21:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:20:05.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547033,0,540279,542705,560335,'F',TO_TIMESTAMP('2019-07-29 21:20:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Discount_Override',40,0,0,TO_TIMESTAMP('2019-07-29 21:20:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:21:34.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=60,Updated=TO_TIMESTAMP('2019-07-29 21:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540147
;

-- 2019-07-29T19:30:14.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Nominalmenge', PrintName='Gelieferte Nominalmenge',Updated=TO_TIMESTAMP('2019-07-29 21:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_CH'
;

-- 2019-07-29T19:30:14.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_CH') 
;

-- 2019-07-29T19:30:19.482Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Nominalmenge',Updated=TO_TIMESTAMP('2019-07-29 21:30:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_DE'
;

-- 2019-07-29T19:30:19.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_DE') 
;

-- 2019-07-29T19:30:19.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576941,'de_DE') 
;

-- 2019-07-29T19:30:19.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Gelieferte Nominalmenge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941
;

-- 2019-07-29T19:30:19.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Gelieferte Nominalmenge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=576941 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_CATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T19:30:19.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Gelieferte Nominalmenge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T19:30:19.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gelieferte Nominalmenge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576941) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576941)
;

-- 2019-07-29T19:30:19.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Catch Menge in Preiseinheit', Name='Gelieferte Nominalmenge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576941)
;

-- 2019-07-29T19:30:19.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gelieferte Nominalmenge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T19:30:19.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gelieferte Nominalmenge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T19:30:19.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gelieferte Nominalmenge', Description = 'Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T19:30:42.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Catchmenge', PrintName='Gelieferte Catchmenge',Updated=TO_TIMESTAMP('2019-07-29 21:30:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_CH'
;

-- 2019-07-29T19:30:42.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_CH') 
;

-- 2019-07-29T19:30:53.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Catch Menge', PrintName='Gelieferte Catch Menge',Updated=TO_TIMESTAMP('2019-07-29 21:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_DE'
;

-- 2019-07-29T19:30:53.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_DE') 
;

-- 2019-07-29T19:30:53.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576941,'de_DE') 
;

-- 2019-07-29T19:30:53.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Gelieferte Catch Menge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941
;

-- 2019-07-29T19:30:53.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Gelieferte Catch Menge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, AD_Element_ID=576941 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_CATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T19:30:53.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Catch', Name='Gelieferte Catch Menge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID=576941 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T19:30:53.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gelieferte Catch Menge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576941) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576941)
;

-- 2019-07-29T19:30:53.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gelieferte Catch Menge', Name='Gelieferte Catch Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576941)
;

-- 2019-07-29T19:30:53.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gelieferte Catch Menge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T19:30:53.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gelieferte Catch Menge', Description='Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', Help=NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T19:30:53.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gelieferte Catch Menge', Description = 'Zu berechnende tatsächliche gelieferte Menge in der Mengeneinheit des Preises.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576941
;

-- 2019-07-29T19:30:58.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Catch Menge', PrintName='Gelieferte Catch Menge',Updated=TO_TIMESTAMP('2019-07-29 21:30:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='de_CH'
;

-- 2019-07-29T19:30:58.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'de_CH') 
;

-- 2019-07-29T19:31:19.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Delivered catch quantity', PrintName='Delivered catch quantity',Updated=TO_TIMESTAMP('2019-07-29 21:31:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576941 AND AD_Language='en_US'
;

-- 2019-07-29T19:31:19.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576941,'en_US') 
;

-- 2019-07-29T19:32:26.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Nominalmenge', PrintName='Gelieferte Nominalmenge',Updated=TO_TIMESTAMP('2019-07-29 21:32:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_CH'
;

-- 2019-07-29T19:32:26.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_CH') 
;

-- 2019-07-29T19:32:41.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Delivered nominal quantity', PrintName='Delivered nominal quantity',Updated=TO_TIMESTAMP('2019-07-29 21:32:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='en_US'
;

-- 2019-07-29T19:32:41.398Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'en_US') 
;

-- 2019-07-29T19:32:56.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', Name='Gelieferte Nominalmenge', PrintName='Gelieferte Nominalmenge',Updated=TO_TIMESTAMP('2019-07-29 21:32:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_DE'
;

-- 2019-07-29T19:32:56.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_DE') 
;

-- 2019-07-29T19:32:56.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542529,'de_DE') 
;

-- 2019-07-29T19:32:56.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Gelieferte Nominalmenge', Description='', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-29T19:32:56.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Gelieferte Nominalmenge', Description='', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_NOMINAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T19:32:56.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Nominal', Name='Gelieferte Nominalmenge', Description='', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T19:32:56.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gelieferte Nominalmenge', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542529)
;

-- 2019-07-29T19:32:56.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gelieferte Nominalmenge', Name='Gelieferte Nominalmenge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542529)
;

-- 2019-07-29T19:32:56.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gelieferte Nominalmenge', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-29T19:32:56.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gelieferte Nominalmenge', Description='', Help=NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-29T19:32:56.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gelieferte Nominalmenge', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-29T19:33:45.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Menge eff.', PrintName='Gelieferte Menge eff.',Updated=TO_TIMESTAMP('2019-07-29 21:33:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-29T19:33:45.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-29T19:33:53.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Gelieferte Menge eff.', PrintName='Gelieferte Menge eff.',Updated=TO_TIMESTAMP('2019-07-29 21:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-29T19:33:53.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-29T19:33:53.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-29T19:33:53.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Gelieferte Menge eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-29T19:33:53.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Gelieferte Menge eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_EFF' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T19:33:53.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Gelieferte Menge eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T19:33:53.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gelieferte Menge eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576945) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576945)
;

-- 2019-07-29T19:33:53.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gelieferte Menge eff.', Name='Gelieferte Menge eff.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576945)
;

-- 2019-07-29T19:33:53.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Gelieferte Menge eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T19:33:53.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Gelieferte Menge eff.', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T19:33:53.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Gelieferte Menge eff.', Description = 'Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T19:34:28.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Delivered quantity eff.', PrintName='Delivered quantity eff.',Updated=TO_TIMESTAMP('2019-07-29 21:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='en_US'
;

-- 2019-07-29T19:34:28.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'en_US') 
;

-- 2019-07-29T19:35:36.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560330
;

-- 2019-07-29T19:39:41.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576970,0,'Invoicing_UOM_ID',TO_TIMESTAMP('2019-07-29 21:39:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abrechnungseinheit','Abrechnungseinheit',TO_TIMESTAMP('2019-07-29 21:39:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:39:41.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576970 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T19:39:51.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576970, Description=NULL, Help=NULL, Name='Abrechnungseinheit',Updated=TO_TIMESTAMP('2019-07-29 21:39:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554200
;

-- 2019-07-29T19:39:51.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576970) 
;

-- 2019-07-29T19:39:51.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554200
;

-- 2019-07-29T19:39:51.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(554200)
;

-- 2019-07-29T19:42:43.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542704, SeqNo=50,Updated=TO_TIMESTAMP('2019-07-29 21:42:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559074
;

-- 2019-07-29T19:42:56.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542704, SeqNo=60,Updated=TO_TIMESTAMP('2019-07-29 21:42:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559075
;

-- 2019-07-29T19:43:23.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=4,Updated=TO_TIMESTAMP('2019-07-29 21:43:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559075
;

-- 2019-07-29T19:43:29.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=7,Updated=TO_TIMESTAMP('2019-07-29 21:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559074
;

-- 2019-07-29T19:46:12.370Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 21:46:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560327
;

-- 2019-07-29T19:46:17.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 21:46:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560328
;

-- 2019-07-29T19:46:19.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 21:46:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560326
;

-- 2019-07-29T19:46:34.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='qtyInUOM',Updated=TO_TIMESTAMP('2019-07-29 21:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542704
;

-- 2019-07-29T19:46:48.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='qtyInStockingUOM',Updated=TO_TIMESTAMP('2019-07-29 21:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-07-29T19:48:05.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542704, SeqNo=50,Updated=TO_TIMESTAMP('2019-07-29 21:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- 2019-07-29T19:48:14.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542704, SeqNo=60,Updated=TO_TIMESTAMP('2019-07-29 21:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548112
;

-- 2019-07-29T19:48:35.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2019-07-29 21:48:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548112
;

-- 2019-07-29T19:54:37.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576971,0,'QtyInvoicedInUOM',TO_TIMESTAMP('2019-07-29 21:54:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abgerechnet (Abr.-Einh.)','Abgerechnet (Abr.-Einh.)',TO_TIMESTAMP('2019-07-29 21:54:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:54:37.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576971 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T19:55:09.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568520,576971,0,29,540270,'QtyInvoicedInUOM',TO_TIMESTAMP('2019-07-29 21:55:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Abgerechnet (Abr.-Einh.)',0,0,TO_TIMESTAMP('2019-07-29 21:55:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T19:55:09.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568520 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T19:55:09.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576971) 
;

-- 2019-07-29T19:55:19.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select 1)', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-07-29 21:55:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568520
;

-- 2019-07-29T19:56:27.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragt (Abr.-Einh.)', PrintName='Beauftragt (Abr.-Einh.)',Updated=TO_TIMESTAMP('2019-07-29 21:56:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576695 AND AD_Language='de_CH'
;

-- 2019-07-29T19:56:27.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_CH') 
;

-- 2019-07-29T19:56:37.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragt (Abr.-Einh.)', PrintName='Beauftragt (Abr.-Einh.)',Updated=TO_TIMESTAMP('2019-07-29 21:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576695 AND AD_Language='de_DE'
;

-- 2019-07-29T19:56:37.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_DE') 
;

-- 2019-07-29T19:56:37.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576695,'de_DE') 
;

-- 2019-07-29T19:56:37.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt (Abr.-Einh.)', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=576695
;

-- 2019-07-29T19:56:37.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt (Abr.-Einh.)', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL, AD_Element_ID=576695 WHERE UPPER(ColumnName)='QTYORDERED_IN_RECORD_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T19:56:37.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt (Abr.-Einh.)', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=576695 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T19:56:37.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beauftragt (Abr.-Einh.)', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576695) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576695)
;

-- 2019-07-29T19:56:37.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beauftragt (Abr.-Einh.)', Name='Beauftragt (Abr.-Einh.)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576695)
;

-- 2019-07-29T19:56:37.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beauftragt (Abr.-Einh.)', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576695
;

-- 2019-07-29T19:56:37.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beauftragt (Abr.-Einh.)', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID = 576695
;

-- 2019-07-29T19:56:37.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beauftragt (Abr.-Einh.)', Description = 'Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576695
;

-- 2019-07-29T19:58:07.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert (Abr.-Einh.)', PrintName='Geliefert (Abr.-Einh.)',Updated=TO_TIMESTAMP('2019-07-29 21:58:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-29T19:58:07.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-29T19:58:12.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert (Abr.-Einh.)', PrintName='Geliefert (Abr.-Einh.)',Updated=TO_TIMESTAMP('2019-07-29 21:58:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-29T19:58:12.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-29T19:58:12.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-29T19:58:12.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Geliefert (Abr.-Einh.)', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-29T19:58:12.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Geliefert (Abr.-Einh.)', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_EFF' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T19:58:12.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Geliefert (Abr.-Einh.)', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T19:58:12.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert (Abr.-Einh.)', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576945) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576945)
;

-- 2019-07-29T19:58:12.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert (Abr.-Einh.)', Name='Geliefert (Abr.-Einh.)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576945)
;

-- 2019-07-29T19:58:12.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert (Abr.-Einh.)', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T19:58:12.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert (Abr.-Einh.)', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T19:58:12.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert (Abr.-Einh.)', Description = 'Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T19:59:34.553Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568520,582497,0,540279,0,TO_TIMESTAMP('2019-07-29 21:59:34','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Abgerechnet (Abr.-Einh.)',1110,470,0,1,1,TO_TIMESTAMP('2019-07-29 21:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T19:59:34.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582497 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T19:59:34.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576971) 
;

-- 2019-07-29T19:59:34.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582497
;

-- 2019-07-29T19:59:34.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582497)
;

-- 2019-07-29T20:01:00.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576972,0,'QtyInvoicedInStockingUOM',TO_TIMESTAMP('2019-07-29 22:01:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abgerechnet (Lagereinheit)','Abgerechnet (Lagereinheit)',TO_TIMESTAMP('2019-07-29 22:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:01:00.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576972 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T20:01:20.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576972, Description=NULL, Name='Abgerechnet (Lagereinheit)',Updated=TO_TIMESTAMP('2019-07-29 22:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547554
;

-- 2019-07-29T20:01:20.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576972) 
;

-- 2019-07-29T20:01:20.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=547554
;

-- 2019-07-29T20:01:20.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(547554)
;

-- moved to 5528289_sys_gh5384-app_migration_2019-07-29_postgresql_add_important_AD_stuff.sql
-- -- 2019-07-29T20:03:37.068Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576973,0,'QtyDeliveredInStockingUOM',TO_TIMESTAMP('2019-07-29 22:03:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geliefert (Lagereinheit)','Geliefert (Lagereinheit)',TO_TIMESTAMP('2019-07-29 22:03:36','YYYY-MM-DD HH24:MI:SS'),100)
-- ;
--
-- -- 2019-07-29T20:03:37.079Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576973 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
-- ;
--
-- -- 2019-07-29T20:04:03.763Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576974,0,'QtyOrderedInStockingUOM',TO_TIMESTAMP('2019-07-29 22:04:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Beauftragt (Lagereinheit)','Beauftragt (Lagereinheit)',TO_TIMESTAMP('2019-07-29 22:04:03','YYYY-MM-DD HH24:MI:SS'),100)
-- ;
--
-- -- 2019-07-29T20:04:03.764Z
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576974 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
-- ;

-- 2019-07-29T20:04:34.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576973, Description=NULL, Help=NULL, Name='Geliefert (Lagereinheit)',Updated=TO_TIMESTAMP('2019-07-29 22:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547552
;

-- 2019-07-29T20:04:34.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576973) 
;

-- 2019-07-29T20:04:34.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=547552
;

-- 2019-07-29T20:04:34.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(547552)
;

-- 2019-07-29T20:04:51.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576974, Description=NULL, Name='Beauftragt (Lagereinheit)',Updated=TO_TIMESTAMP('2019-07-29 22:04:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=547551
;

-- 2019-07-29T20:04:51.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576974) 
;

-- 2019-07-29T20:04:51.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=547551
;

-- 2019-07-29T20:04:51.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(547551)
;

-- 2019-07-29T20:08:00.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582497,0,540279,542704,560336,'F',TO_TIMESTAMP('2019-07-29 22:07:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyInvoicedInUOM',43,0,0,TO_TIMESTAMP('2019-07-29 22:07:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:09:52.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=15,Updated=TO_TIMESTAMP('2019-07-29 22:09:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-07-29T20:09:57.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-07-29 22:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542705
;

-- 2019-07-29T20:12:00.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyToInvoice_Override',Updated=TO_TIMESTAMP('2019-07-29 22:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548112
;

-- 2019-07-29T20:12:38.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.',Updated=TO_TIMESTAMP('2019-07-29 22:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_DE'
;

-- 2019-07-29T20:12:38.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_DE') 
;

-- 2019-07-29T20:12:38.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1002360,'de_DE') 
;

-- 2019-07-29T20:12:38.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Zu ber. Menge abw. nach Qualitätsabzug', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360
;

-- 2019-07-29T20:12:38.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Zu ber. Menge abw. nach Qualitätsabzug', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:12:38.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zu ber. Menge abw. nach Qualitätsabzug', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1002360) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1002360)
;

-- 2019-07-29T20:12:38.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zu ber. Menge abw. nach Qualitätsabzug', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.', CommitWarning = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:12:38.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zu ber. Menge abw. nach Qualitätsabzug', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:12:38.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zu ber. Menge abw. nach Qualitätsabzug', Description = 'Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:12:42.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.',Updated=TO_TIMESTAMP('2019-07-29 22:12:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_CH'
;

-- 2019-07-29T20:12:42.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_CH') 
;

-- 2019-07-29T20:13:25.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rechnungsmenge abw.', PrintName='Rechnungsmenge abw.',Updated=TO_TIMESTAMP('2019-07-29 22:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_CH'
;

-- 2019-07-29T20:13:25.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_CH') 
;

-- 2019-07-29T20:13:47.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rechnungsmenge abw.', PrintName='Rechnungsmenge abw.',Updated=TO_TIMESTAMP('2019-07-29 22:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_DE'
;

-- 2019-07-29T20:13:47.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_DE') 
;

-- 2019-07-29T20:13:47.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1002360,'de_DE') 
;

-- 2019-07-29T20:13:47.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Rechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360
;

-- 2019-07-29T20:13:47.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:13:47.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1002360) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1002360)
;

-- 2019-07-29T20:13:47.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungsmenge abw.', Name='Rechnungsmenge abw.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1002360)
;

-- 2019-07-29T20:13:47.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.', CommitWarning = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:13:47.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:13:47.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rechnungsmenge abw.', Description = 'Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:13:51.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-07-29 22:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_DE'
;

-- 2019-07-29T20:13:51.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_DE') 
;

-- 2019-07-29T20:13:51.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1002360,'de_DE') 
;

-- 2019-07-29T20:14:13.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrechnungsmenge abw.', PrintName='Abrechnungsmenge abw.',Updated=TO_TIMESTAMP('2019-07-29 22:14:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_CH'
;

-- 2019-07-29T20:14:13.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_CH') 
;

-- 2019-07-29T20:14:26.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abrechnungsmenge abw.', PrintName='Abrechnungsmenge abw.',Updated=TO_TIMESTAMP('2019-07-29 22:14:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_DE'
;

-- 2019-07-29T20:14:26.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_DE') 
;

-- 2019-07-29T20:14:26.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1002360,'de_DE') 
;

-- 2019-07-29T20:14:26.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Abrechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360
;

-- 2019-07-29T20:14:26.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Abrechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:14:26.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abrechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1002360) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1002360)
;

-- 2019-07-29T20:14:26.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abrechnungsmenge abw.', Name='Abrechnungsmenge abw.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1002360)
;

-- 2019-07-29T20:14:26.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abrechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.', CommitWarning = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:14:26.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abrechnungsmenge abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:14:26.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abrechnungsmenge abw.', Description = 'Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:14:57.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abzurechnen abw.', PrintName='Abzurechnen abw.',Updated=TO_TIMESTAMP('2019-07-29 22:14:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_DE'
;

-- 2019-07-29T20:14:57.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_DE') 
;

-- 2019-07-29T20:14:57.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1002360,'de_DE') 
;

-- 2019-07-29T20:14:57.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Abzurechnen abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360
;

-- 2019-07-29T20:14:57.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Abzurechnen abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:14:57.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abzurechnen abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1002360) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1002360)
;

-- 2019-07-29T20:14:57.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abzurechnen abw.', Name='Abzurechnen abw.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1002360)
;

-- 2019-07-29T20:14:57.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abzurechnen abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.', CommitWarning = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:14:57.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abzurechnen abw.', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:14:57.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abzurechnen abw.', Description = 'Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-07-29T20:15:03.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abzurechnen abw.', PrintName='Abzurechnen abw.',Updated=TO_TIMESTAMP('2019-07-29 22:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_CH'
;

-- 2019-07-29T20:15:03.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_CH') 
;

-- 2019-07-29T20:17:23.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540045, SeqNo=40,Updated=TO_TIMESTAMP('2019-07-29 22:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541087
;

-- 2019-07-29T20:18:20.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=29,Updated=TO_TIMESTAMP('2019-07-29 22:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-07-29T20:19:09.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=41,Updated=TO_TIMESTAMP('2019-07-29 22:19:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-07-29T20:19:14.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-07-29 22:19:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542705
;

-- 2019-07-29T20:22:34.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576975,0,'QtyToInvoiceInUOM',TO_TIMESTAMP('2019-07-29 22:22:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abzurechnen','Abzurechnen',TO_TIMESTAMP('2019-07-29 22:22:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:22:34.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576975 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T20:22:53.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568521,576975,0,29,540270,'QtyToInvoiceInUOM','select 1',TO_TIMESTAMP('2019-07-29 22:22:52','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Abzurechnen',0,0,TO_TIMESTAMP('2019-07-29 22:22:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T20:22:53.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568521 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T20:22:53.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576975) 
;

-- 2019-07-29T20:23:16.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568521,582498,0,540279,0,TO_TIMESTAMP('2019-07-29 22:23:16','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D',0,'Y','Y','Y','N','N','N','N','N','Abzurechnen',1120,480,0,1,1,TO_TIMESTAMP('2019-07-29 22:23:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:23:16.687Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582498 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:23:16.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576975) 
;

-- 2019-07-29T20:23:16.702Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582498
;

-- 2019-07-29T20:23:16.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582498)
;

-- 2019-07-29T20:23:54.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582498,0,540279,542704,560337,'F',TO_TIMESTAMP('2019-07-29 22:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToInvoiceInUOM',44,0,0,TO_TIMESTAMP('2019-07-29 22:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:25:28.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyToInvoiceInUOM_Calc',Updated=TO_TIMESTAMP('2019-07-29 22:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576975
;

-- 2019-07-29T20:25:28.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInUOM_Calc', Name='Abzurechnen', Description=NULL, Help=NULL WHERE AD_Element_ID=576975
;

-- 2019-07-29T20:25:28.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInUOM_Calc', Name='Abzurechnen', Description=NULL, Help=NULL, AD_Element_ID=576975 WHERE UPPER(ColumnName)='QTYTOINVOICEINUOM_CALC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:25:28.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInUOM_Calc', Name='Abzurechnen', Description=NULL, Help=NULL WHERE AD_Element_ID=576975 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:26:11.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abzurechnen eff.', PrintName='Abzurechnen eff.',Updated=TO_TIMESTAMP('2019-07-29 22:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='de_CH'
;

-- 2019-07-29T20:26:11.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'de_CH') 
;

-- 2019-07-29T20:26:21.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Qty to invoice eff.',Updated=TO_TIMESTAMP('2019-07-29 22:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='en_US'
;

-- 2019-07-29T20:26:21.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'en_US') 
;

-- 2019-07-29T20:26:29.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abzurechnen eff.', PrintName='Abzurechnen eff.',Updated=TO_TIMESTAMP('2019-07-29 22:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='de_DE'
;

-- 2019-07-29T20:26:29.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'de_DE') 
;

-- 2019-07-29T20:26:29.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1251,'de_DE') 
;

-- 2019-07-29T20:26:29.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoice', Name='Abzurechnen eff.', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE AD_Element_ID=1251
;

-- 2019-07-29T20:26:29.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoice', Name='Abzurechnen eff.', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL, AD_Element_ID=1251 WHERE UPPER(ColumnName)='QTYTOINVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:26:29.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoice', Name='Abzurechnen eff.', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE AD_Element_ID=1251 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:26:29.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abzurechnen eff.', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1251) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1251)
;

-- 2019-07-29T20:26:29.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abzurechnen eff.', Name='Abzurechnen eff.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1251)
;

-- 2019-07-29T20:26:29.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abzurechnen eff.', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1251
;

-- 2019-07-29T20:26:29.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abzurechnen eff.', Description='Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', Help=NULL WHERE AD_Element_ID = 1251
;

-- 2019-07-29T20:26:29.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abzurechnen eff.', Description = 'Menge, die aktuell bei einem Rechnungslauf in Rechnung gestellt würde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1251
;

-- 2019-07-29T20:27:03.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select 1)',Updated=TO_TIMESTAMP('2019-07-29 22:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568521
;

-- 2019-07-29T20:30:14.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='dates & flags',Updated=TO_TIMESTAMP('2019-07-29 22:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540058
;

-- 2019-07-29T20:30:38.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548115,0,540279,540058,560338,'F',TO_TIMESTAMP('2019-07-29 22:30:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Processed',50,0,0,TO_TIMESTAMP('2019-07-29 22:30:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:31:13.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-07-29 22:31:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540146
;

-- 2019-07-29T20:31:16.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-07-29 22:31:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542704
;

-- 2019-07-29T20:31:28.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540046,542706,TO_TIMESTAMP('2019-07-29 22:31:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','totals',40,TO_TIMESTAMP('2019-07-29 22:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:33:00.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548199,0,540279,542706,560339,'F',TO_TIMESTAMP('2019-07-29 22:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'NetAmtInvoiced',10,0,0,TO_TIMESTAMP('2019-07-29 22:33:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:33:47.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abgerechneter Betrag', PrintName='Abgerechneter Betrag',Updated=TO_TIMESTAMP('2019-07-29 22:33:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541464 AND AD_Language='de_CH'
;

-- 2019-07-29T20:33:47.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541464,'de_CH') 
;

-- 2019-07-29T20:33:57.646Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Invoiced Amount',Updated=TO_TIMESTAMP('2019-07-29 22:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541464 AND AD_Language='en_US'
;

-- 2019-07-29T20:33:57.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541464,'en_US') 
;

-- 2019-07-29T20:34:09.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abgerechneter Betrag', PrintName='Abgerechneter Betrag',Updated=TO_TIMESTAMP('2019-07-29 22:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541464 AND AD_Language='de_DE'
;

-- 2019-07-29T20:34:09.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541464,'de_DE') 
;

-- 2019-07-29T20:34:09.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541464,'de_DE') 
;

-- 2019-07-29T20:34:09.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetAmtInvoiced', Name='Abgerechneter Betrag', Description='Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde', Help='Wenn eine Rechnung storniert wird, dann fließt auch der Storno-Betrag mit negativem Vorzeichen in den berechneten Betrag mit ein.' WHERE AD_Element_ID=541464
;

-- 2019-07-29T20:34:09.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetAmtInvoiced', Name='Abgerechneter Betrag', Description='Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde', Help='Wenn eine Rechnung storniert wird, dann fließt auch der Storno-Betrag mit negativem Vorzeichen in den berechneten Betrag mit ein.', AD_Element_ID=541464 WHERE UPPER(ColumnName)='NETAMTINVOICED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:34:09.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetAmtInvoiced', Name='Abgerechneter Betrag', Description='Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde', Help='Wenn eine Rechnung storniert wird, dann fließt auch der Storno-Betrag mit negativem Vorzeichen in den berechneten Betrag mit ein.' WHERE AD_Element_ID=541464 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:34:09.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgerechneter Betrag', Description='Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde', Help='Wenn eine Rechnung storniert wird, dann fließt auch der Storno-Betrag mit negativem Vorzeichen in den berechneten Betrag mit ein.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541464) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541464)
;

-- 2019-07-29T20:34:09.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abgerechneter Betrag', Name='Abgerechneter Betrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541464)
;

-- 2019-07-29T20:34:09.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abgerechneter Betrag', Description='Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde', Help='Wenn eine Rechnung storniert wird, dann fließt auch der Storno-Betrag mit negativem Vorzeichen in den berechneten Betrag mit ein.', CommitWarning = NULL WHERE AD_Element_ID = 541464
;

-- 2019-07-29T20:34:09.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abgerechneter Betrag', Description='Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde', Help='Wenn eine Rechnung storniert wird, dann fließt auch der Storno-Betrag mit negativem Vorzeichen in den berechneten Betrag mit ein.' WHERE AD_Element_ID = 541464
;

-- 2019-07-29T20:34:09.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abgerechneter Betrag', Description = 'Bezeichnet den Netto-Geldbetrag, der für diesen Rechnungskandidaten bereits in Rechnung gestellt wurde', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541464
;

-- 2019-07-29T20:35:18.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abzurechnender Betrag', PrintName='Abzurechnender Betrag',Updated=TO_TIMESTAMP('2019-07-29 22:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1249 AND AD_Language='de_CH'
;

-- 2019-07-29T20:35:18.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1249,'de_CH') 
;

-- 2019-07-29T20:35:29.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abzurechnender Betrag', PrintName='Abzurechnender Betrag',Updated=TO_TIMESTAMP('2019-07-29 22:35:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1249 AND AD_Language='de_DE'
;

-- 2019-07-29T20:35:29.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1249,'de_DE') 
;

-- 2019-07-29T20:35:29.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1249,'de_DE') 
;

-- 2019-07-29T20:35:29.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetAmtToInvoice', Name='Abzurechnender Betrag', Description='Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.', Help=NULL WHERE AD_Element_ID=1249
;

-- 2019-07-29T20:35:29.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetAmtToInvoice', Name='Abzurechnender Betrag', Description='Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.', Help=NULL, AD_Element_ID=1249 WHERE UPPER(ColumnName)='NETAMTTOINVOICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:35:29.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetAmtToInvoice', Name='Abzurechnender Betrag', Description='Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.', Help=NULL WHERE AD_Element_ID=1249 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:35:29.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abzurechnender Betrag', Description='Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1249) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1249)
;

-- 2019-07-29T20:35:29.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abzurechnender Betrag', Name='Abzurechnender Betrag' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1249)
;

-- 2019-07-29T20:35:29.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abzurechnender Betrag', Description='Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1249
;

-- 2019-07-29T20:35:29.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abzurechnender Betrag', Description='Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.', Help=NULL WHERE AD_Element_ID = 1249
;

-- 2019-07-29T20:35:29.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abzurechnender Betrag', Description = 'Bezeichnet den netto-Geldbetrag, der für den jeweiligen Rechnungskandidaten aktuell bei einem Rechnungslauf in Rechnung gestellt würde.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1249
;

-- 2019-07-29T20:36:22.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547553,0,540279,542706,560340,'F',TO_TIMESTAMP('2019-07-29 22:36:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'NetAmtToInvoice',20,0,0,TO_TIMESTAMP('2019-07-29 22:36:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:38:19.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 22:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541060
;

-- 2019-07-29T20:38:21.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 22:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541061
;

-- 2019-07-29T20:39:44.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=26,Updated=TO_TIMESTAMP('2019-07-29 22:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548115
;

-- 2019-07-29T20:40:22.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 22:40:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560337
;

-- 2019-07-29T20:41:28.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragt', PrintName='Beauftragt',Updated=TO_TIMESTAMP('2019-07-29 22:41:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576695 AND AD_Language='de_CH'
;

-- 2019-07-29T20:41:28.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_CH') 
;

-- 2019-07-29T20:41:33.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Beauftragt', PrintName='Beauftragt',Updated=TO_TIMESTAMP('2019-07-29 22:41:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576695 AND AD_Language='de_DE'
;

-- 2019-07-29T20:41:33.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576695,'de_DE') 
;

-- 2019-07-29T20:41:33.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576695,'de_DE') 
;

-- 2019-07-29T20:41:33.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=576695
;

-- 2019-07-29T20:41:33.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL, AD_Element_ID=576695 WHERE UPPER(ColumnName)='QTYORDERED_IN_RECORD_UOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:41:33.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyOrdered_In_Record_UOM', Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID=576695 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:41:33.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576695) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576695)
;

-- 2019-07-29T20:41:33.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Beauftragt', Name='Beauftragt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576695)
;

-- 2019-07-29T20:41:33.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576695
;

-- 2019-07-29T20:41:33.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Beauftragt', Description='Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', Help=NULL WHERE AD_Element_ID = 576695
;

-- 2019-07-29T20:41:33.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Beauftragt', Description = 'Beauftragte Menge in der Maßeinheit des jeweiligen Datensatzes', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576695
;

-- 2019-07-29T20:42:11.303Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert', PrintName='Geliefert',Updated=TO_TIMESTAMP('2019-07-29 22:42:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_CH'
;

-- 2019-07-29T20:42:11.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_CH') 
;

-- 2019-07-29T20:42:25.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert', PrintName='Geliefert',Updated=TO_TIMESTAMP('2019-07-29 22:42:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576945 AND AD_Language='de_DE'
;

-- 2019-07-29T20:42:25.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576945,'de_DE') 
;

-- 2019-07-29T20:42:25.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576945,'de_DE') 
;

-- 2019-07-29T20:42:25.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945
;

-- 2019-07-29T20:42:25.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, AD_Element_ID=576945 WHERE UPPER(ColumnName)='QTYTOINVOICEINPRICEUOM_EFF' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:42:25.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyToInvoiceInPriceUOM_Eff', Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID=576945 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:42:25.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576945) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576945)
;

-- 2019-07-29T20:42:25.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert', Name='Geliefert' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576945)
;

-- 2019-07-29T20:42:25.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T20:42:25.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert', Description='Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', Help=NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T20:42:25.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert', Description = 'Effektiv zu berechnende Menge in der Mengeneinheit des Preises; abhängig davon, ob ein Catchweight-Abrechnung vorgesehen ist.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576945
;

-- 2019-07-29T20:43:19.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abgerechnet', PrintName='Abgerechnet',Updated=TO_TIMESTAMP('2019-07-29 22:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576971 AND AD_Language='de_CH'
;

-- 2019-07-29T20:43:19.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576971,'de_CH') 
;

-- 2019-07-29T20:43:26.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abgerechnet', PrintName='Abgerechnet',Updated=TO_TIMESTAMP('2019-07-29 22:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576971 AND AD_Language='de_DE'
;

-- 2019-07-29T20:43:26.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576971,'de_DE') 
;

-- 2019-07-29T20:43:26.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576971,'de_DE') 
;

-- 2019-07-29T20:43:26.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyInvoicedInUOM', Name='Abgerechnet', Description=NULL, Help=NULL WHERE AD_Element_ID=576971
;

-- 2019-07-29T20:43:26.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInvoicedInUOM', Name='Abgerechnet', Description=NULL, Help=NULL, AD_Element_ID=576971 WHERE UPPER(ColumnName)='QTYINVOICEDINUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:43:26.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyInvoicedInUOM', Name='Abgerechnet', Description=NULL, Help=NULL WHERE AD_Element_ID=576971 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:43:26.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abgerechnet', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576971) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576971)
;

-- 2019-07-29T20:43:26.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abgerechnet', Name='Abgerechnet' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576971)
;

-- 2019-07-29T20:43:26.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abgerechnet', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576971
;

-- 2019-07-29T20:43:26.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abgerechnet', Description=NULL, Help=NULL WHERE AD_Element_ID = 576971
;

-- 2019-07-29T20:43:26.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abgerechnet', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576971
;

-- 2019-07-29T20:45:55.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Menge in Catch Einheit', PrintName='Menge in Catch Einheit',Updated=TO_TIMESTAMP('2019-07-29 22:45:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_CH'
;

-- 2019-07-29T20:45:55.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_CH') 
;

-- 2019-07-29T20:46:00.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Menge in Catch Einheit', PrintName='Menge in Catch Einheit',Updated=TO_TIMESTAMP('2019-07-29 22:46:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_DE'
;

-- 2019-07-29T20:46:00.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_DE') 
;

-- 2019-07-29T20:46:00.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576949,'de_DE') 
;

-- 2019-07-29T20:46:00.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949
;

-- 2019-07-29T20:46:00.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, AD_Element_ID=576949 WHERE UPPER(ColumnName)='QTYDELIVEREDINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:46:00.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Menge in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:46:00.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Menge in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576949) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576949)
;

-- 2019-07-29T20:46:00.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Menge in Catch Einheit', Name='Menge in Catch Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576949)
;

-- 2019-07-29T20:46:00.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Menge in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:46:00.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Menge in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:46:00.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Menge in Catch Einheit', Description = 'Tatsächlich gelieferte Menge in Catch Weight Einheit.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:46:07.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Quantity in catch UOM', PrintName='Quantity in catch UOM',Updated=TO_TIMESTAMP('2019-07-29 22:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='en_US'
;

-- 2019-07-29T20:46:07.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'en_US') 
;

-- 2019-07-29T20:46:16.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568522,576949,0,29,540579,'QtyDeliveredInCatchUOM','select 0',TO_TIMESTAMP('2019-07-29 22:46:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Tatsächlich gelieferte Menge in Catch Weight Einheit.','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Menge in Catch Einheit',0,0,TO_TIMESTAMP('2019-07-29 22:46:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T20:46:16.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568522 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T20:46:16.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576949) 
;

-- 2019-07-29T20:47:03.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568523,576953,0,18,114,540579,'Catch_UOM_ID','( select null )',TO_TIMESTAMP('2019-07-29 22:47:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Catch Weight Einheit',0,0,TO_TIMESTAMP('2019-07-29 22:47:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T20:47:03.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568523 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T20:47:03.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2019-07-29T20:47:12.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch Einheit', PrintName='Catch Einheit',Updated=TO_TIMESTAMP('2019-07-29 22:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953 AND AD_Language='de_CH'
;

-- 2019-07-29T20:47:12.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576953,'de_CH') 
;

-- 2019-07-29T20:47:18.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch Einheit', PrintName='Catch Einheit',Updated=TO_TIMESTAMP('2019-07-29 22:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953 AND AD_Language='de_DE'
;

-- 2019-07-29T20:47:18.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576953,'de_DE') 
;

-- 2019-07-29T20:47:18.058Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576953,'de_DE') 
;

-- 2019-07-29T20:47:18.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Catch_UOM_ID', Name='Catch Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953
;

-- 2019-07-29T20:47:18.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Catch_UOM_ID', Name='Catch Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL, AD_Element_ID=576953 WHERE UPPER(ColumnName)='CATCH_UOM_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:47:18.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Catch_UOM_ID', Name='Catch Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576953 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:47:18.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Catch Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576953) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576953)
;

-- 2019-07-29T20:47:18.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Catch Einheit', Name='Catch Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576953)
;

-- 2019-07-29T20:47:18.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Catch Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576953
;

-- 2019-07-29T20:47:18.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Catch Einheit', Description='Aus dem Produktstamm übenommene Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID = 576953
;

-- 2019-07-29T20:47:18.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Catch Einheit', Description = 'Aus dem Produktstamm übenommene Catch Weight Einheit.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576953
;

-- 2019-07-29T20:47:24.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Catch UOM', PrintName='Catch UOM',Updated=TO_TIMESTAMP('2019-07-29 22:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576953 AND AD_Language='en_US'
;

-- 2019-07-29T20:47:24.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576953,'en_US') 
;

-- 2019-07-29T20:48:59.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576976,0,'QtyDeliveredInCatchUOM_Override',TO_TIMESTAMP('2019-07-29 22:48:59','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich gelieferte Menge in Catch Weight Einheit.','U','Y','Geliefert in Catch Einheit abw.','Geliefert in Catch Einheit abw.',TO_TIMESTAMP('2019-07-29 22:48:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:48:59.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576976 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T20:49:03.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-07-29 22:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576976
;

-- 2019-07-29T20:49:31.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568524,576976,0,29,540579,'QtyDeliveredInCatchUOM_Override','( select 1 )',TO_TIMESTAMP('2019-07-29 22:49:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Tatsächlich gelieferte Menge in Catch Weight Einheit.','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Geliefert in Catch Einheit abw.',0,0,TO_TIMESTAMP('2019-07-29 22:49:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T20:49:31.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568524 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T20:49:31.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576976) 
;

-- 2019-07-29T20:49:49.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert in Catch Einheit', PrintName='Geliefert in Catch Einheit',Updated=TO_TIMESTAMP('2019-07-29 22:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_CH'
;

-- 2019-07-29T20:49:49.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_CH') 
;

-- 2019-07-29T20:49:58.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert in Catch Einheit', PrintName='Geliefert in Catch Einheit',Updated=TO_TIMESTAMP('2019-07-29 22:49:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_DE'
;

-- 2019-07-29T20:49:58.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_DE') 
;

-- 2019-07-29T20:49:58.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576949,'de_DE') 
;

-- 2019-07-29T20:49:58.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInCatchUOM', Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949
;

-- 2019-07-29T20:49:58.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, AD_Element_ID=576949 WHERE UPPER(ColumnName)='QTYDELIVEREDINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:49:58.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInCatchUOM', Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:49:58.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576949) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576949)
;

-- 2019-07-29T20:49:58.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert in Catch Einheit', Name='Geliefert in Catch Einheit' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576949)
;

-- 2019-07-29T20:49:58.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:49:58.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:49:58.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert in Catch Einheit', Description = 'Tatsächlich gelieferte Menge in Catch Weight Einheit.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:50:08.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Delivered in catch UOM', PrintName='Delivered in catch UOM',Updated=TO_TIMESTAMP('2019-07-29 22:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='en_US'
;

-- 2019-07-29T20:50:08.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'en_US') 
;

-- 2019-07-29T20:52:25.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( select 0 )',Updated=TO_TIMESTAMP('2019-07-29 22:52:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568522
;

-- 2019-07-29T20:53:30.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyDeliveredInUOM_Nominal',Updated=TO_TIMESTAMP('2019-07-29 22:53:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529
;

-- 2019-07-29T20:53:30.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInUOM_Nominal', Name='Gelieferte Nominalmenge', Description='', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-29T20:53:30.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Nominal', Name='Gelieferte Nominalmenge', Description='', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYDELIVEREDINUOM_NOMINAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:53:30.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Nominal', Name='Gelieferte Nominalmenge', Description='', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:53:39.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568525,542529,0,29,540579,'QtyDeliveredInUOM_Nominal',TO_TIMESTAMP('2019-07-29 22:53:39','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.invoicecandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Gelieferte Nominalmenge',0,0,TO_TIMESTAMP('2019-07-29 22:53:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-07-29T20:53:39.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568525 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-07-29T20:53:39.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542529) 
;

-- 2019-07-29T20:53:47.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( select 1 )', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-07-29 22:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568525
;

-- 2019-07-29T20:54:21.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyDeliveredInUOM_Catch',Updated=TO_TIMESTAMP('2019-07-29 22:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949
;

-- 2019-07-29T20:54:21.704Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInUOM_Catch', Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949
;

-- 2019-07-29T20:54:21.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Catch', Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, AD_Element_ID=576949 WHERE UPPER(ColumnName)='QTYDELIVEREDINUOM_CATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:54:21.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Catch', Name='Geliefert in Catch Einheit', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576949 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:54:40.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tatsächlich gelieferte Menge', Name='Geliefert Catch', PrintName='Geliefert Catch',Updated=TO_TIMESTAMP('2019-07-29 22:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_CH'
;

-- 2019-07-29T20:54:40.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_CH') 
;

-- 2019-07-29T20:54:55.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Tatsächlich gelieferte Menge', Name='Geliefert Catch', PrintName='Geliefert Catch',Updated=TO_TIMESTAMP('2019-07-29 22:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='de_DE'
;

-- 2019-07-29T20:54:55.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'de_DE') 
;

-- 2019-07-29T20:54:55.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576949,'de_DE') 
;

-- 2019-07-29T20:54:55.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInUOM_Catch', Name='Geliefert Catch', Description='Tatsächlich gelieferte Menge', Help=NULL WHERE AD_Element_ID=576949
;

-- 2019-07-29T20:54:55.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Catch', Name='Geliefert Catch', Description='Tatsächlich gelieferte Menge', Help=NULL, AD_Element_ID=576949 WHERE UPPER(ColumnName)='QTYDELIVEREDINUOM_CATCH' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:54:55.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Catch', Name='Geliefert Catch', Description='Tatsächlich gelieferte Menge', Help=NULL WHERE AD_Element_ID=576949 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:54:55.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert Catch', Description='Tatsächlich gelieferte Menge', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576949) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576949)
;

-- 2019-07-29T20:54:55.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert Catch', Name='Geliefert Catch' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576949)
;

-- 2019-07-29T20:54:55.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert Catch', Description='Tatsächlich gelieferte Menge', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:54:55.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert Catch', Description='Tatsächlich gelieferte Menge', Help=NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:54:55.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert Catch', Description = 'Tatsächlich gelieferte Menge', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576949
;

-- 2019-07-29T20:55:24.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Actually delivered catch quantity', Name='Delivered catch', PrintName='Delivered catch',Updated=TO_TIMESTAMP('2019-07-29 22:55:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576949 AND AD_Language='en_US'
;

-- 2019-07-29T20:55:24.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576949,'en_US') 
;

-- 2019-07-29T20:56:36.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyDeliveredInUOM_Override',Updated=TO_TIMESTAMP('2019-07-29 22:56:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576976
;

-- 2019-07-29T20:56:36.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInUOM_Override', Name='Geliefert in Catch Einheit abw.', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576976
;

-- 2019-07-29T20:56:36.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Override', Name='Geliefert in Catch Einheit abw.', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL, AD_Element_ID=576976 WHERE UPPER(ColumnName)='QTYDELIVEREDINUOM_OVERRIDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:56:36.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Override', Name='Geliefert in Catch Einheit abw.', Description='Tatsächlich gelieferte Menge in Catch Weight Einheit.', Help=NULL WHERE AD_Element_ID=576976 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:56:48.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Geliefert abw.', PrintName='Geliefert abw.',Updated=TO_TIMESTAMP('2019-07-29 22:56:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576976 AND AD_Language='de_CH'
;

-- 2019-07-29T20:56:48.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576976,'de_CH') 
;

-- 2019-07-29T20:56:58.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Geliefert abw.', PrintName='Geliefert abw.',Updated=TO_TIMESTAMP('2019-07-29 22:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576976 AND AD_Language='de_DE'
;

-- 2019-07-29T20:56:58.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576976,'de_DE') 
;

-- 2019-07-29T20:56:58.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576976,'de_DE') 
;

-- 2019-07-29T20:56:58.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInUOM_Override', Name='Geliefert abw.', Description='', Help=NULL WHERE AD_Element_ID=576976
;

-- 2019-07-29T20:56:58.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Override', Name='Geliefert abw.', Description='', Help=NULL, AD_Element_ID=576976 WHERE UPPER(ColumnName)='QTYDELIVEREDINUOM_OVERRIDE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T20:56:58.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Override', Name='Geliefert abw.', Description='', Help=NULL WHERE AD_Element_ID=576976 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T20:56:58.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert abw.', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576976) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576976)
;

-- 2019-07-29T20:56:58.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert abw.', Name='Geliefert abw.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576976)
;

-- 2019-07-29T20:56:58.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert abw.', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576976
;

-- 2019-07-29T20:56:58.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert abw.', Description='', Help=NULL WHERE AD_Element_ID = 576976
;

-- 2019-07-29T20:56:58.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert abw.', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576976
;

-- 2019-07-29T20:57:14.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Delivered override', PrintName='Delivered override',Updated=TO_TIMESTAMP('2019-07-29 22:57:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576976 AND AD_Language='en_US'
;

-- 2019-07-29T20:57:14.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576976,'en_US') 
;

-- 2019-07-29T20:57:50.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=215, AD_Reference_ID=19, ColumnName='C_UOM_ID', Description='Maßeinheit', Help='Eine eindeutige (nicht monetäre) Maßeinheit', Name='Maßeinheit',Updated=TO_TIMESTAMP('2019-07-29 22:57:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568523
;

-- 2019-07-29T20:57:50.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Maßeinheit', Description='Maßeinheit', Help='Eine eindeutige (nicht monetäre) Maßeinheit' WHERE AD_Column_ID=568523
;

-- 2019-07-29T20:57:50.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2019-07-29T20:58:32.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540092,Updated=TO_TIMESTAMP('2019-07-29 22:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540579
;

-- 2019-07-29T20:59:01.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550551,582499,0,540665,TO_TIMESTAMP('2019-07-29 22:59:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.invoicecandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-07-29 22:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:01.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582499 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:01.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-07-29T20:59:01.918Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582499
;

-- 2019-07-29T20:59:01.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582499)
;

-- 2019-07-29T20:59:02.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550554,582500,0,540665,TO_TIMESTAMP('2019-07-29 22:59:01','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','C_InvoiceCandidate_InOutLine',TO_TIMESTAMP('2019-07-29 22:59:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582500 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542423) 
;

-- 2019-07-29T20:59:02.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582500
;

-- 2019-07-29T20:59:02.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582500)
;

-- 2019-07-29T20:59:02.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550602,582501,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,'Menge in Produkt-Maßeinheit, die bereits in Rechnung gestellt wurde.',22,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Berechn. Menge',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582501 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.164Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(529) 
;

-- 2019-07-29T20:59:02.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582501
;

-- 2019-07-29T20:59:02.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582501)
;

-- 2019-07-29T20:59:02.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551874,582502,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','IsInvalidQty',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582502 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542724) 
;

-- 2019-07-29T20:59:02.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582502
;

-- 2019-07-29T20:59:02.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582502)
;

-- 2019-07-29T20:59:02.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551875,582503,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','InvalidQtyReason',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582503 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542725) 
;

-- 2019-07-29T20:59:02.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582503
;

-- 2019-07-29T20:59:02.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582503)
;

-- 2019-07-29T20:59:02.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551876,582504,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','IsMigrated',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582504 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542726) 
;

-- 2019-07-29T20:59:02.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582504
;

-- 2019-07-29T20:59:02.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582504)
;

-- 2019-07-29T20:59:02.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568522,582505,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich gelieferte Menge',10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Geliefert Catch',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582505 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576949) 
;

-- 2019-07-29T20:59:02.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582505
;

-- 2019-07-29T20:59:02.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582505)
;

-- 2019-07-29T20:59:02.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568523,582506,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'de.metas.invoicecandidate','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582506 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2019-07-29T20:59:02.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582506
;

-- 2019-07-29T20:59:02.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582506)
;

-- 2019-07-29T20:59:02.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568524,582507,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Geliefert abw.',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:02.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:02.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576976) 
;

-- 2019-07-29T20:59:02.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582507
;

-- 2019-07-29T20:59:02.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582507)
;

-- 2019-07-29T20:59:03.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568525,582508,0,540665,TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Gelieferte Nominalmenge',TO_TIMESTAMP('2019-07-29 22:59:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:59:03.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T20:59:03.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542529) 
;

-- 2019-07-29T20:59:03.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582508
;

-- 2019-07-29T20:59:03.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582508)
;

-- 2019-07-29T20:59:23.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 22:59:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000491
;

-- 2019-07-29T21:00:50.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582506,0,540665,1000045,560341,'F',TO_TIMESTAMP('2019-07-29 23:00:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_UOM_ID',60,0,0,TO_TIMESTAMP('2019-07-29 23:00:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T21:01:25.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582505,0,540665,1000045,560342,'F',TO_TIMESTAMP('2019-07-29 23:01:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyDeliveredInUOM_Catch',70,0,0,TO_TIMESTAMP('2019-07-29 23:01:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T21:01:41.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582508,0,540665,1000045,560343,'F',TO_TIMESTAMP('2019-07-29 23:01:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyDeliveredInUOM_Nominal',80,0,0,TO_TIMESTAMP('2019-07-29 23:01:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T21:02:08.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert Nominal', PrintName='Geliefert Nominal',Updated=TO_TIMESTAMP('2019-07-29 23:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_CH'
;

-- 2019-07-29T21:02:08.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_CH') 
;

-- 2019-07-29T21:02:20.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Geliefert Nominal', PrintName='Geliefert Nominal',Updated=TO_TIMESTAMP('2019-07-29 23:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542529 AND AD_Language='de_DE'
;

-- 2019-07-29T21:02:20.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542529,'de_DE') 
;

-- 2019-07-29T21:02:20.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542529,'de_DE') 
;

-- 2019-07-29T21:02:20.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyDeliveredInUOM_Nominal', Name='Geliefert Nominal', Description='', Help=NULL WHERE AD_Element_ID=542529
;

-- 2019-07-29T21:02:20.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Nominal', Name='Geliefert Nominal', Description='', Help=NULL, AD_Element_ID=542529 WHERE UPPER(ColumnName)='QTYDELIVEREDINUOM_NOMINAL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-29T21:02:20.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyDeliveredInUOM_Nominal', Name='Geliefert Nominal', Description='', Help=NULL WHERE AD_Element_ID=542529 AND IsCentrallyMaintained='Y'
;

-- 2019-07-29T21:02:20.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geliefert Nominal', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542529) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542529)
;

-- 2019-07-29T21:02:20.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geliefert Nominal', Name='Geliefert Nominal' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542529)
;

-- 2019-07-29T21:02:20.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geliefert Nominal', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-29T21:02:20.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geliefert Nominal', Description='', Help=NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-29T21:02:20.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geliefert Nominal', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542529
;

-- 2019-07-29T21:02:54.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582507,0,540665,1000045,560344,'F',TO_TIMESTAMP('2019-07-29 23:02:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyDeliveredInUOM_Override',90,0,0,TO_TIMESTAMP('2019-07-29 23:02:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T21:03:40.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576973, Description=NULL, Help=NULL, Name='Geliefert (Lagereinheit)',Updated=TO_TIMESTAMP('2019-07-29 23:03:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555488
;

-- 2019-07-29T21:03:40.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576973) 
;

-- 2019-07-29T21:03:40.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=555488
;

-- 2019-07-29T21:03:40.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(555488)
;

-- 2019-07-29T21:05:05.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( select 1)',Updated=TO_TIMESTAMP('2019-07-29 23:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551086
;

-- 2019-07-29T21:06:48.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-07-29 23:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000491
;

-- 2019-07-29T21:06:48.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-07-29 23:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560341
;

-- 2019-07-29T21:06:48.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-07-29 23:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560342
;

-- 2019-07-29T21:06:48.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-07-29 23:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560343
;

-- 2019-07-29T21:06:48.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-07-29 23:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560344
;

-- 2019-07-29T21:06:48.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-07-29 23:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000487
;

-- 2019-07-29T21:08:09.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-29 23:08:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541073
;

-- 2019-07-29T21:09:34.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582483,0,540279,540058,560345,'F',TO_TIMESTAMP('2019-07-29 23:09:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'InvoicableQtyBasedOn',60,0,0,TO_TIMESTAMP('2019-07-29 23:09:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T21:09:45.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2019-07-29 23:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560338
;

-- 2019-07-29T21:10:38.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=7,Updated=TO_TIMESTAMP('2019-07-29 23:10:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541086
;

-- 2019-07-29T21:10:43.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=9,Updated=TO_TIMESTAMP('2019-07-29 23:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541948
;

-- 2019-07-29T21:11:57.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=15,Updated=TO_TIMESTAMP('2019-07-29 23:11:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541086
;

-- 2019-07-29T21:14:27.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-07-29 23:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560341
;

-- 2019-07-29T21:14:30.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-07-29 23:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560342
;

-- 2019-07-29T21:14:35.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-07-29 23:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560343
;

-- 2019-07-29T21:14:42.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-07-29 23:14:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560344
;

-- 2019-07-29T21:16:52.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576977,0,TO_TIMESTAMP('2019-07-29 23:16:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Liefereinheit','Liefereinheit',TO_TIMESTAMP('2019-07-29 23:16:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T21:16:52.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576977 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-07-29T21:17:05.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=576977, Description=NULL, Help=NULL, Name='Liefereinheit',Updated=TO_TIMESTAMP('2019-07-29 23:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582506
;

-- 2019-07-29T21:17:05.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576977) 
;

-- 2019-07-29T21:17:05.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582506
;

-- 2019-07-29T21:17:05.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582506)
;

-- 2019-07-30T04:43:52.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540146, SeqNo=10,Updated=TO_TIMESTAMP('2019-07-30 06:43:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541949
;

-- 2019-07-30T04:44:02.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540146, SeqNo=20,Updated=TO_TIMESTAMP('2019-07-30 06:44:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541950
;

-- 2019-07-30T04:44:15.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540146, SeqNo=30,Updated=TO_TIMESTAMP('2019-07-30 06:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541951
;

-- 2019-07-30T04:44:24.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540146, SeqNo=40,Updated=TO_TIMESTAMP('2019-07-30 06:44:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541952
;

-- 2019-07-30T04:44:49.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540146, SeqNo=50,Updated=TO_TIMESTAMP('2019-07-30 06:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541948
;

-- 2019-07-30T04:45:14.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2019-07-30 06:45:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541948
;

-- 2019-07-30T04:46:26.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=31,Updated=TO_TIMESTAMP('2019-07-30 06:46:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541950
;

-- 2019-07-30T04:46:34.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-07-30 06:46:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541951
;

-- 2019-07-30T04:47:11.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Qualitätsabzug % eff.', PrintName='Qualitätsabzug % eff.',Updated=TO_TIMESTAMP('2019-07-30 06:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542264 AND AD_Language='de_CH'
;

-- 2019-07-30T04:47:11.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542264,'de_CH') 
;

-- 2019-07-30T04:47:23.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Quality Discount % eff.',Updated=TO_TIMESTAMP('2019-07-30 06:47:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542264 AND AD_Language='en_US'
;

-- 2019-07-30T04:47:23.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542264,'en_US') 
;

-- 2019-07-30T04:47:33.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Qualitätsabzug % eff.', PrintName='Qualitätsabzug % eff.',Updated=TO_TIMESTAMP('2019-07-30 06:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542264 AND AD_Language='de_DE'
;

-- 2019-07-30T04:47:33.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542264,'de_DE') 
;

-- 2019-07-30T04:47:33.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542264,'de_DE') 
;

-- 2019-07-30T04:47:33.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QualityDiscountPercent_Effective', Name='Qualitätsabzug % eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=542264
;

-- 2019-07-30T04:47:33.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QualityDiscountPercent_Effective', Name='Qualitätsabzug % eff.', Description=NULL, Help=NULL, AD_Element_ID=542264 WHERE UPPER(ColumnName)='QUALITYDISCOUNTPERCENT_EFFECTIVE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-07-30T04:47:33.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QualityDiscountPercent_Effective', Name='Qualitätsabzug % eff.', Description=NULL, Help=NULL WHERE AD_Element_ID=542264 AND IsCentrallyMaintained='Y'
;

-- 2019-07-30T04:47:33.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Qualitätsabzug % eff.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542264) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542264)
;

-- 2019-07-30T04:47:33.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Qualitätsabzug % eff.', Name='Qualitätsabzug % eff.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542264)
;

-- 2019-07-30T04:47:33.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Qualitätsabzug % eff.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542264
;

-- 2019-07-30T04:47:33.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Qualitätsabzug % eff.', Description=NULL, Help=NULL WHERE AD_Element_ID = 542264
;

-- 2019-07-30T04:47:33.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Qualitätsabzug % eff.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542264
;

-- 2019-07-30T04:49:29.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=41,Updated=TO_TIMESTAMP('2019-07-30 06:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541949
;

-- 2019-07-30T04:49:33.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-07-30 06:49:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541952
;

-- 2019-07-30T04:50:12.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-30 06:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541949
;

-- 2019-07-30T04:50:49.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554132,0,540279,540056,560346,'F',TO_TIMESTAMP('2019-07-30 06:50:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'ReasonDiscount',1020,0,0,TO_TIMESTAMP('2019-07-30 06:50:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-30T05:29:11.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=541052
;

-- 2019-07-30T05:29:11.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560327
;

-- 2019-07-30T05:29:11.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=554677
;

-- 2019-07-30T05:29:11.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=554677
;

-- 2019-07-30T05:29:11.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=554677
;

-- 2019-07-30T05:29:21.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=551086
;

-- 2019-07-30T05:29:21.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=551086
;

-- 2019-07-30T05:30:04.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2019-07-30 07:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568521
;

-- 2019-07-30T05:30:06.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyToInvoiceInUOM_Calc NUMERIC')
;

-- 2019-07-30T05:30:37.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2019-07-30 07:30:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568520
;

-- 2019-07-30T05:30:39.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN QtyInvoicedInUOM NUMERIC')
;

-- 2019-07-30T05:36:33.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560328
;

-- 2019-07-30T05:36:33.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582481
;

-- 2019-07-30T05:36:33.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=582481
;

-- 2019-07-30T05:36:33.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=582481
;

-- 2019-07-30T05:36:39.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568474
;

-- 2019-07-30T05:36:39.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568474
;

