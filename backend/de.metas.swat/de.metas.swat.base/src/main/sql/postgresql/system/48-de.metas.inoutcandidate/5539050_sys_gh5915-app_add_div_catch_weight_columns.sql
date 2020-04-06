-- 2019-12-15T14:58:13.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569774,576949,0,29,540530,'QtyDeliveredInCatchUOM',TO_TIMESTAMP('2019-12-15 15:58:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Tatsächlich gelieferte Menge','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geliefert Catch',0,0,TO_TIMESTAMP('2019-12-15 15:58:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-15T14:58:13.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569774 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-15T14:58:14.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576949) 
;

-- 2019-12-15T14:59:27.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569775,576953,0,30,114,540530,'Catch_UOM_ID',TO_TIMESTAMP('2019-12-15 15:59:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Aus dem Produktstamm übenommene Catch Weight Einheit.','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Catch Einheit',0,0,TO_TIMESTAMP('2019-12-15 15:59:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-12-15T14:59:27.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569775 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-15T14:59:27.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2019-12-15T14:59:29.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule_Alloc','ALTER TABLE public.M_ReceiptSchedule_Alloc ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2019-12-15T14:59:29.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_ReceiptSchedule_Alloc ADD CONSTRAINT CatchUOM_MReceiptScheduleAlloc FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2019-12-15T15:10:40.562Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes', IsTranslated='Y', Name='Zugewiesene HU-Menge', PrintName='Zugewiesene HU-Menge',Updated=TO_TIMESTAMP('2019-12-15 16:10:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='fr_CH'
;

-- 2019-12-15T15:10:40.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'fr_CH') 
;

-- 2019-12-15T15:11:19.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Allocated HU quantity', PrintName='Allocated HU quantity',Updated=TO_TIMESTAMP('2019-12-15 16:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='en_US'
;

-- 2019-12-15T15:11:19.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'en_US') 
;

-- 2019-12-15T15:11:52.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zugewiesene HU-Menge', PrintName='Zugewiesene HU-Menge',Updated=TO_TIMESTAMP('2019-12-15 16:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='de_DE'
;

-- 2019-12-15T15:11:52.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'de_DE') 
;

-- 2019-12-15T15:11:52.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542324,'de_DE') 
;

-- 2019-12-15T15:11:52.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=542324
;

-- 2019-12-15T15:11:52.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description=NULL, Help=NULL, AD_Element_ID=542324 WHERE UPPER(ColumnName)='HU_QTYALLOCATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-15T15:11:52.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=542324 AND IsCentrallyMaintained='Y'
;

-- 2019-12-15T15:11:52.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugewiesene HU-Menge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542324) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542324)
;

-- 2019-12-15T15:11:52.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Zugewiesene HU-Menge', Name='Zugewiesene HU-Menge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542324)
;

-- 2019-12-15T15:11:52.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zugewiesene HU-Menge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:11:52.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zugewiesene HU-Menge', Description=NULL, Help=NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:11:52.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zugewiesene HU-Menge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:12:08.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.',Updated=TO_TIMESTAMP('2019-12-15 16:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='fr_CH'
;

-- 2019-12-15T15:12:08.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'fr_CH') 
;

-- 2019-12-15T15:12:24.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.',Updated=TO_TIMESTAMP('2019-12-15 16:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='de_DE'
;

-- 2019-12-15T15:12:24.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'de_DE') 
;

-- 2019-12-15T15:12:24.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542324,'de_DE') 
;

-- 2019-12-15T15:12:24.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE AD_Element_ID=542324
;

-- 2019-12-15T15:12:24.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL, AD_Element_ID=542324 WHERE UPPER(ColumnName)='HU_QTYALLOCATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-15T15:12:24.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE AD_Element_ID=542324 AND IsCentrallyMaintained='Y'
;

-- 2019-12-15T15:12:24.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugewiesene HU-Menge', Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542324) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542324)
;

-- 2019-12-15T15:12:24.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zugewiesene HU-Menge', Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:12:24.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zugewiesene HU-Menge', Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:12:24.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zugewiesene HU-Menge', Description = 'Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:12:37.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordnete Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 16:12:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='de_CH'
;

-- 2019-12-15T15:12:37.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'de_CH') 
;

-- 2019-12-15T15:12:52.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Zugewiesene HU-Menge', PrintName='Zugewiesene HU-Menge',Updated=TO_TIMESTAMP('2019-12-15 16:12:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='de_CH'
;

-- 2019-12-15T15:12:52.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'de_CH') 
;

-- 2019-12-15T15:13:31.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Allocate quantity from the respective HU, in the receipt schedule''s uom.',Updated=TO_TIMESTAMP('2019-12-15 16:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='en_US'
;

-- 2019-12-15T15:13:31.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'en_US') 
;

-- 2019-12-15T15:40:55.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.',Updated=TO_TIMESTAMP('2019-12-15 16:40:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='fr_CH'
;

-- 2019-12-15T15:40:55.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'fr_CH') 
;

-- 2019-12-15T15:41:08.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.',Updated=TO_TIMESTAMP('2019-12-15 16:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='de_CH'
;

-- 2019-12-15T15:41:08.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'de_CH') 
;

-- 2019-12-15T15:41:18.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.',Updated=TO_TIMESTAMP('2019-12-15 16:41:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='de_DE'
;

-- 2019-12-15T15:41:18.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'de_DE') 
;

-- 2019-12-15T15:41:18.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(542324,'de_DE') 
;

-- 2019-12-15T15:41:18.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE AD_Element_ID=542324
;

-- 2019-12-15T15:41:18.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL, AD_Element_ID=542324 WHERE UPPER(ColumnName)='HU_QTYALLOCATED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-15T15:41:18.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='HU_QtyAllocated', Name='Zugewiesene HU-Menge', Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE AD_Element_ID=542324 AND IsCentrallyMaintained='Y'
;

-- 2019-12-15T15:41:18.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugewiesene HU-Menge', Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542324) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542324)
;

-- 2019-12-15T15:41:18.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Zugewiesene HU-Menge', Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:41:18.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Zugewiesene HU-Menge', Description='Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', Help=NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:41:18.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Zugewiesene HU-Menge', Description = 'Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542324
;

-- 2019-12-15T15:41:33.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Allocated (stock-)quantity from the respective HU, in the receipt schedule''s uom.',Updated=TO_TIMESTAMP('2019-12-15 16:41:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542324 AND AD_Language='en_US'
;

-- 2019-12-15T15:41:33.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542324,'en_US') 
;

-- 2019-12-15T17:48:28.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2019-12-15 18:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552792
;

-- 2019-12-15T19:51:48.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577429,0,'QtyAllocatedInCatchUOM',TO_TIMESTAMP('2019-12-15 20:51:48','YYYY-MM-DD HH24:MI:SS'),100,'Tatsächlich zugeordnete Menge','D','Y','Zugeordnet Catch','Zugeordnet Catch',TO_TIMESTAMP('2019-12-15 20:51:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-15T19:51:48.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577429 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-15T19:51:54.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 20:51:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577429 AND AD_Language='de_CH'
;

-- 2019-12-15T19:51:54.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577429,'de_CH') 
;

-- 2019-12-15T19:51:57.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 20:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577429 AND AD_Language='de_DE'
;

-- 2019-12-15T19:51:57.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577429,'de_DE') 
;

-- 2019-12-15T19:51:57.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577429,'de_DE') 
;

-- 2019-12-15T19:52:07.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Allocated catch', PrintName='Allocated catch',Updated=TO_TIMESTAMP('2019-12-15 20:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577429 AND AD_Language='en_US'
;

-- 2019-12-15T19:52:07.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577429,'en_US') 
;

-- 2019-12-15T19:52:22.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help=NULL, AD_Element_ID=577429, ColumnName='QtyAllocatedInCatchUOM', Name='Zugeordnet Catch', Description='Tatsächlich zugeordnete Menge',Updated=TO_TIMESTAMP('2019-12-15 20:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569774
;

-- 2019-12-15T19:52:22.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Zugeordnet Catch', Description='Tatsächlich zugeordnete Menge', Help=NULL WHERE AD_Column_ID=569774
;

-- 2019-12-15T19:52:22.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577429) 
;

-- 2019-12-15T19:53:37.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule_Alloc','ALTER TABLE public.M_ReceiptSchedule_Alloc ADD COLUMN QtyAllocatedInCatchUOM NUMERIC')
;

-- 2019-12-15T19:55:48.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577430,0,'QtyWithIssuesInCatchUOM',TO_TIMESTAMP('2019-12-15 20:55:47','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','Minderwertige Catch-Menge','Minderwertige Catch-Menge',TO_TIMESTAMP('2019-12-15 20:55:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-15T19:55:48.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577430 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-15T19:55:51.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 20:55:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577430 AND AD_Language='de_CH'
;

-- 2019-12-15T19:55:51.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577430,'de_CH') 
;

-- 2019-12-15T19:55:53.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 20:55:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577430 AND AD_Language='de_DE'
;

-- 2019-12-15T19:55:53.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577430,'de_DE') 
;

-- 2019-12-15T19:55:53.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577430,'de_DE') 
;

-- 2019-12-15T19:56:08.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Catch quantity with issues', PrintName='Catch quantity with issues',Updated=TO_TIMESTAMP('2019-12-15 20:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577430 AND AD_Language='en_US'
;

-- 2019-12-15T19:56:08.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577430,'en_US') 
;

-- 2019-12-15T19:56:56.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-12-15 20:56:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-12-15 20:56:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540542,'N',569776,'N','N','N','N','N','N','N','N',0,577430,'de.metas.inoutcandidate','N','N','QtyWithIssuesInCatchUOM','N','Minderwertige Catch-Menge',0,'')
--;

-- 2019-12-15T19:56:56.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569776 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
--;

-- 2019-12-15T19:56:56.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */  select update_Column_Translation_From_AD_Element(577430) 
--;

-- 2019-12-15T19:56:57.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule_QtyPicked','ALTER TABLE public.M_ShipmentSchedule_QtyPicked ADD COLUMN QtyWithIssuesInCatchUOM NUMERIC')
--;

-- 2019-12-15T19:59:26.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569776
--;

-- 2019-12-15T19:59:26.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--DELETE FROM AD_Column WHERE AD_Column_ID=569776
--;

-- 2019-12-15T20:00:39.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-12-15 21:00:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-12-15 21:00:39','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540530,'N',569777,'N','N','N','N','N','N','N','N',0,577430,'de.metas.inoutcandidate','N','N','QtyWithIssuesInCatchUOM','N','Minderwertige Catch-Menge',0,'')
;

-- 2019-12-15T20:00:39.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569777 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-15T20:00:39.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577430) 
;

-- 2019-12-15T20:00:40.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule_Alloc','ALTER TABLE public.M_ReceiptSchedule_Alloc ADD COLUMN QtyWithIssuesInCatchUOM NUMERIC')
;

-- 2019-12-15T20:05:01.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-12-15 21:05:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-12-15 21:05:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540524,'N',114,569778,'N','N','N','N','N','N','N','N',0,576953,'de.metas.inoutcandidate','N','N','Catch_UOM_ID','N','Catch Einheit',0,'Aus dem Produktstamm übenommene Catch Weight Einheit.')
;

-- 2019-12-15T20:05:01.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569778 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-15T20:05:01.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(576953) 
;

-- 2019-12-15T20:05:02.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN Catch_UOM_ID NUMERIC(10)')
;

-- 2019-12-15T20:05:02.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_ReceiptSchedule ADD CONSTRAINT CatchUOM_MReceiptSchedule FOREIGN KEY (Catch_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- 2019-12-15T20:06:45.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577431,0,'QtyMovedInCatchUOM',TO_TIMESTAMP('2019-12-15 21:06:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bewegte Catch-Menge','Bewegte Catch-Menge',TO_TIMESTAMP('2019-12-15 21:06:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-15T20:06:45.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577431 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-15T20:06:58.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyMovedInCatchUOM',Updated=TO_TIMESTAMP('2019-12-15 21:06:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577431
;

-- 2019-12-15T20:06:58.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyMovedInCatchUOM', Name='Bewegte Catch-Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=577431
;

-- 2019-12-15T20:06:58.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyMovedInCatchUOM', Name='Bewegte Catch-Menge', Description=NULL, Help=NULL, AD_Element_ID=577431 WHERE UPPER(ColumnName)='QTYMOVEDINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-15T20:06:58.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyMovedInCatchUOM', Name='Bewegte Catch-Menge', Description=NULL, Help=NULL WHERE AD_Element_ID=577431 AND IsCentrallyMaintained='Y'
;

-- 2019-12-15T20:07:04.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 21:07:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577431 AND AD_Language='de_CH'
;

-- 2019-12-15T20:07:04.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577431,'de_CH') 
;

-- 2019-12-15T20:07:06.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 21:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577431 AND AD_Language='de_DE'
;

-- 2019-12-15T20:07:06.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577431,'de_DE') 
;

-- 2019-12-15T20:07:06.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577431,'de_DE') 
;

-- 2019-12-15T20:07:22.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Moved catch quantity', PrintName='Moved catch quantity',Updated=TO_TIMESTAMP('2019-12-15 21:07:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577431 AND AD_Language='en_US'
;

-- 2019-12-15T20:07:22.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577431,'en_US') 
;

-- 2019-12-15T20:07:42.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-12-15 21:07:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-12-15 21:07:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540524,'N',569779,'N','N','N','N','N','N','N','N',0,577431,'de.metas.inoutcandidate','N','N','QtyMovedInCatchUOM','N','Bewegte Catch-Menge',0)
;

-- 2019-12-15T20:07:42.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569779 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-15T20:07:42.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577431) 
;

-- 2019-12-15T20:07:46.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN QtyMovedInCatchUOM NUMERIC')
;

-- 2019-12-15T20:08:00.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_receiptschedule','Catch_UOM_ID','NUMERIC(10)',null,null)
;

-- 2019-12-15T20:08:24.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyAllocatedInCatchUOM',Updated=TO_TIMESTAMP('2019-12-15 21:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577429
;

-- 2019-12-15T20:08:24.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyAllocatedInCatchUOM', Name='Zugeordnet Catch', Description='Tatsächlich zugeordnete Menge', Help=NULL WHERE AD_Element_ID=577429
;

-- 2019-12-15T20:08:24.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAllocatedInCatchUOM', Name='Zugeordnet Catch', Description='Tatsächlich zugeordnete Menge', Help=NULL, AD_Element_ID=577429 WHERE UPPER(ColumnName)='QTYALLOCATEDINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-15T20:08:24.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyAllocatedInCatchUOM', Name='Zugeordnet Catch', Description='Tatsächlich zugeordnete Menge', Help=NULL WHERE AD_Element_ID=577429 AND IsCentrallyMaintained='Y'
;

-- 2019-12-15T20:11:02.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='QtyWithIssuesInCatchUOM',Updated=TO_TIMESTAMP('2019-12-15 21:11:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577430
;

-- 2019-12-15T20:11:02.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='QtyWithIssuesInCatchUOM', Name='Minderwertige Catch-Menge', Description='', Help=NULL WHERE AD_Element_ID=577430
;

-- 2019-12-15T20:11:02.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyWithIssuesInCatchUOM', Name='Minderwertige Catch-Menge', Description='', Help=NULL, AD_Element_ID=577430 WHERE UPPER(ColumnName)='QTYWITHISSUESINCATCHUOM' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-15T20:11:02.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='QtyWithIssuesInCatchUOM', Name='Minderwertige Catch-Menge', Description='', Help=NULL WHERE AD_Element_ID=577430 AND IsCentrallyMaintained='Y'
;

-- 2019-12-15T20:22:32.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577432,0,'QtyMovedWithIssuesInCatchUOM',TO_TIMESTAMP('2019-12-15 21:22:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bewegte minderwertige Catch-Menge','Bewegte minderwertige Catch-Menge',TO_TIMESTAMP('2019-12-15 21:22:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-15T20:22:32.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577432 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-12-15T20:22:35.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 21:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577432 AND AD_Language='de_CH'
;

-- 2019-12-15T20:22:35.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577432,'de_CH') 
;

-- 2019-12-15T20:22:39.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-15 21:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577432 AND AD_Language='de_DE'
;

-- 2019-12-15T20:22:39.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577432,'de_DE') 
;

-- 2019-12-15T20:22:39.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577432,'de_DE') 
;

-- 2019-12-15T20:23:14.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Moved catch quantity with issues', PrintName='Moved catch quantity with issues',Updated=TO_TIMESTAMP('2019-12-15 21:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577432 AND AD_Language='en_US'
;

-- 2019-12-15T20:23:14.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577432,'en_US') 
;

-- 2019-12-15T20:23:54.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-12-15 21:23:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-12-15 21:23:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N',540524,'N',569780,'N','N','N','N','N','N','N','N',0,577432,'de.metas.inoutcandidate','N','N','QtyMovedWithIssuesInCatchUOM','N','Bewegte minderwertige Catch-Menge',0)
;

-- 2019-12-15T20:23:54.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-12-15T20:23:54.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577432) 
;

-- 2019-12-15T20:24:00.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN QtyMovedWithIssuesInCatchUOM NUMERIC')
;

