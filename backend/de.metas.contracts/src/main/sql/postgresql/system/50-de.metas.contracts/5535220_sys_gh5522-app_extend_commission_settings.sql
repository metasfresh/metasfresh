-- 2019-10-28T09:53:36.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569283,566,0,11,541429,'SeqNo',TO_TIMESTAMP('2019-10-28 10:53:36','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_CommissionSettingsLine WHERE C_HierarchyCommissionSettings=@C_HierarchyCommissionSettings@','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.contracts.commission',14,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Reihenfolge',0,0,TO_TIMESTAMP('2019-10-28 10:53:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-28T09:53:36.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-28T09:53:37.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2019-10-28T09:53:45.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_CommissionSettingsLine','ALTER TABLE public.C_CommissionSettingsLine ADD COLUMN SeqNo NUMERIC(10) NOT NULL')
;

-- 2019-10-28T09:54:43.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=569214
;

-- 2019-10-28T09:54:43.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=569214
;

-- 2019-10-28T09:56:48.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577252,0,'PointsPrecision',TO_TIMESTAMP('2019-10-28 10:56:48','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Nachkommastellen auf die beim Berechnen der Provisionspounkte gerundet wird.','de.metas.contracts.commission','Y','Nachkommapräzision','Nachkommapräzision',TO_TIMESTAMP('2019-10-28 10:56:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T09:56:48.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577252 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-28T09:56:56.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-28 10:56:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577252 AND AD_Language='de_CH'
;

-- 2019-10-28T09:56:56.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577252,'de_CH') 
;

-- 2019-10-28T09:56:58.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-28 10:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577252 AND AD_Language='de_DE'
;

-- 2019-10-28T09:56:58.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577252,'de_DE') 
;

-- 2019-10-28T09:56:58.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577252,'de_DE') 
;

-- 2019-10-28T09:57:56.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Number of digits after the decimal point to which the system rounds when computing commission points.', IsTranslated='Y', Name='Points precision', PrintName='Points precision',Updated=TO_TIMESTAMP('2019-10-28 10:57:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577252 AND AD_Language='en_US'
;

-- 2019-10-28T09:57:56.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577252,'en_US') 
;

-- 2019-10-28T09:58:32.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin,Version) VALUES (0,569284,577252,0,11,541425,'PointsPrecision',TO_TIMESTAMP('2019-10-28 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Anzahl der Nachkommastellen auf die beim Berechnen der Provisionspounkte gerundet wird.','de.metas.contracts.commission',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','Nachkommapräzision',0,0,TO_TIMESTAMP('2019-10-28 10:58:32','YYYY-MM-DD HH24:MI:SS'),100,'','0',0)
;

-- 2019-10-28T09:58:32.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569284 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-28T09:58:32.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577252) 
;

-- 2019-10-28T09:58:40.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='2',Updated=TO_TIMESTAMP('2019-10-28 10:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569284
;

-- 2019-10-28T09:58:43.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_HierarchyCommissionSettings','ALTER TABLE public.C_HierarchyCommissionSettings ADD COLUMN PointsPrecision NUMERIC(10) DEFAULT 2 NOT NULL')
;

-- 2019-10-28T10:08:15.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577253,0,'M_Product_Order_ID',TO_TIMESTAMP('2019-10-28 11:08:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Beauftragtes Produkt','Beauftragtes Produkt',TO_TIMESTAMP('2019-10-28 11:08:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:08:15.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577253 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-28T10:08:19.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-28 11:08:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577253 AND AD_Language='de_CH'
;

-- 2019-10-28T10:08:19.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577253,'de_CH') 
;

-- 2019-10-28T10:08:21.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-28 11:08:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577253 AND AD_Language='de_DE'
;

-- 2019-10-28T10:08:21.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577253,'de_DE') 
;

-- 2019-10-28T10:08:21.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577253,'de_DE') 
;

-- 2019-10-28T10:08:36.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ordered product', PrintName='Ordered product',Updated=TO_TIMESTAMP('2019-10-28 11:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577253 AND AD_Language='en_US'
;

-- 2019-10-28T10:08:36.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577253,'en_US') 
;

-- 2019-10-28T10:08:48.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569285,577253,0,30,541405,'M_Product_Order_ID',TO_TIMESTAMP('2019-10-28 11:08:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Beauftragtes Produkt',0,0,TO_TIMESTAMP('2019-10-28 11:08:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-28T10:08:48.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569285 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-28T10:08:48.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577253) 
;

-- 2019-10-28T10:08:58.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540272,Updated=TO_TIMESTAMP('2019-10-28 11:08:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569285
;

-- 2019-10-28T10:09:00.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Instance','ALTER TABLE public.C_Commission_Instance ADD COLUMN M_Product_Order_ID NUMERIC(10)')
;

-- 2019-10-28T10:09:00.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Commission_Instance ADD CONSTRAINT MProductOrder_CCommissionInstance FOREIGN KEY (M_Product_Order_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2019-10-28T10:09:09.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-10-28 11:09:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569285
;

-- 2019-10-28T10:10:18.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','M_Product_Order_ID','NUMERIC(10)',null,null)
;

-- 2019-10-28T10:10:18.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','M_Product_Order_ID',null,'NOT NULL',null)
;

-- 2019-10-28T10:36:23.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569285,591204,0,541942,TO_TIMESTAMP('2019-10-28 11:36:23','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.contracts.commission','Y','Y','N','N','N','N','N','Beauftragtes Produkt',TO_TIMESTAMP('2019-10-28 11:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:36:23.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-28T10:36:23.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577253) 
;

-- 2019-10-28T10:36:23.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591204
;

-- 2019-10-28T10:36:23.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591204)
;

-- 2019-10-28T10:36:33.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-10-28 11:36:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591204
;

-- 2019-10-28T10:37:08.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591204,0,541942,542896,563730,'F',TO_TIMESTAMP('2019-10-28 11:37:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'M_Product_Order_ID',15,0,0,TO_TIMESTAMP('2019-10-28 11:37:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:37:16.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-10-28 11:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563730
;

-- 2019-10-28T10:37:16.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-10-28 11:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561843
;

-- 2019-10-28T10:37:16.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-10-28 11:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561844
;

-- 2019-10-28T10:37:16.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-10-28 11:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561845
;

-- 2019-10-28T10:37:16.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-10-28 11:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561846
;

-- 2019-10-28T10:37:16.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-10-28 11:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561847
;

-- 2019-10-28T10:37:16.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-10-28 11:37:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561840
;

-- 2019-10-28T10:37:36.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2019-10-28 11:37:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569285
;

-- 2019-10-28T10:37:36.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-10-28 11:37:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568754
;

-- 2019-10-28T10:37:36.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-10-28 11:37:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568790
;

-- 2019-10-28T10:38:41.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540508,0,541429,TO_TIMESTAMP('2019-10-28 11:38:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Y','C_CommissionSettings_Line_UC_SeqNo','N',TO_TIMESTAMP('2019-10-28 11:38:41','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-10-28T10:38:41.501Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540508 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-10-28T10:38:55.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569267,540971,540508,0,TO_TIMESTAMP('2019-10-28 11:38:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',10,TO_TIMESTAMP('2019-10-28 11:38:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:39:06.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569283,540972,540508,0,TO_TIMESTAMP('2019-10-28 11:39:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',20,TO_TIMESTAMP('2019-10-28 11:39:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:39:07.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_CommissionSettings_Line_UC_SeqNo ON C_CommissionSettingsLine (C_HierarchyCommissionSettings_ID,SeqNo) WHERE IsActive='Y'
;

-- 2019-10-28T10:39:25.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569284,591205,0,542066,TO_TIMESTAMP('2019-10-28 11:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Nachkommastellen auf die beim Berechnen der Provisionspounkte gerundet wird.',14,'de.metas.contracts.commission','Y','Y','N','N','N','N','N','Nachkommapräzision',TO_TIMESTAMP('2019-10-28 11:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:39:25.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-28T10:39:25.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577252) 
;

-- 2019-10-28T10:39:25.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591205
;

-- 2019-10-28T10:39:25.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591205)
;

-- 2019-10-28T10:40:05.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591205,0,542066,543076,563731,'F',TO_TIMESTAMP('2019-10-28 11:40:05','YYYY-MM-DD HH24:MI:SS'),100,'Anzahl der Nachkommastellen auf die beim Berechnen der Provisionspounkte gerundet wird.','Y','N','N','Y','N','N','N',0,'PointsPrecision',80,0,0,TO_TIMESTAMP('2019-10-28 11:40:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:40:22.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-10-28 11:40:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563248
;

-- 2019-10-28T10:40:22.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-10-28 11:40:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563731
;

-- 2019-10-28T10:40:40.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569283,591206,0,542093,TO_TIMESTAMP('2019-10-28 11:40:40','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',14,'de.metas.contracts','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','Y','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2019-10-28 11:40:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:40:40.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-28T10:40:40.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2019-10-28T10:40:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591206
;

-- 2019-10-28T10:40:40.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591206)
;

-- 2019-10-28T10:41:20.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542093,541627,TO_TIMESTAMP('2019-10-28 11:41:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-28 11:41:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2019-10-28T10:41:20.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541627 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-10-28T10:41:27.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542069,541627,TO_TIMESTAMP('2019-10-28 11:41:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-10-28 11:41:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:41:29.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542070,541627,TO_TIMESTAMP('2019-10-28 11:41:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-10-28 11:41:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:41:48.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542070,543135,TO_TIMESTAMP('2019-10-28 11:41:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2019-10-28 11:41:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:41:54.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542070,543136,TO_TIMESTAMP('2019-10-28 11:41:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2019-10-28 11:41:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:42:12.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590613,0,542093,543135,563732,'F',TO_TIMESTAMP('2019-10-28 11:42:11','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'IsActive',10,0,0,TO_TIMESTAMP('2019-10-28 11:42:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:42:27.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590612,0,542093,543136,563733,'F',TO_TIMESTAMP('2019-10-28 11:42:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'AD_Org_ID',10,0,0,TO_TIMESTAMP('2019-10-28 11:42:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:42:42.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590611,0,542093,543136,563734,'F',TO_TIMESTAMP('2019-10-28 11:42:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'AD_Client_ID',20,0,0,TO_TIMESTAMP('2019-10-28 11:42:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:43:15.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,542069,543137,TO_TIMESTAMP('2019-10-28 11:43:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2019-10-28 11:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:43:33.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591206,0,542093,543137,563735,'F',TO_TIMESTAMP('2019-10-28 11:43:33','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'SeqNo',10,0,0,TO_TIMESTAMP('2019-10-28 11:43:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:43:52.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590617,0,542093,543137,563736,'F',TO_TIMESTAMP('2019-10-28 11:43:52','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','N','Y','N','N','N',0,'C_BP_Group_ID',20,0,0,TO_TIMESTAMP('2019-10-28 11:43:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:44:05.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590616,0,542093,543137,563737,'F',TO_TIMESTAMP('2019-10-28 11:44:05','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','Y','N','N','N',0,'M_Product_Category_ID',30,0,0,TO_TIMESTAMP('2019-10-28 11:44:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:44:22.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='matching',Updated=TO_TIMESTAMP('2019-10-28 11:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543137
;

-- 2019-10-28T10:44:28.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542069,543138,TO_TIMESTAMP('2019-10-28 11:44:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','settings',20,TO_TIMESTAMP('2019-10-28 11:44:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:44:48.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,590618,0,542093,543138,563738,'F',TO_TIMESTAMP('2019-10-28 11:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PercentOfBasePoints',10,0,0,TO_TIMESTAMP('2019-10-28 11:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-28T10:45:09.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-10-28 11:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563735
;

-- 2019-10-28T10:45:09.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-10-28 11:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563736
;

-- 2019-10-28T10:45:09.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-10-28 11:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563737
;

-- 2019-10-28T10:45:09.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-10-28 11:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563738
;

-- 2019-10-28T12:31:36.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=1, IsQueryOnLoad='Y',Updated=TO_TIMESTAMP('2019-10-28 13:31:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542093
;

-- 2019-10-28T12:31:58.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-10-28 13:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=590612
;

-- 2019-10-28T12:34:49.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Einstellungsdetail', PrintName='Einstellungsdetail',Updated=TO_TIMESTAMP('2019-10-28 13:34:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577241 AND AD_Language='de_CH'
;

-- 2019-10-28T12:34:49.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577241,'de_CH') 
;

-- 2019-10-28T12:34:53.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Einstellungsdetail', PrintName='Einstellungsdetail',Updated=TO_TIMESTAMP('2019-10-28 13:34:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577241 AND AD_Language='de_DE'
;

-- 2019-10-28T12:34:53.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577241,'de_DE') 
;

-- 2019-10-28T12:34:53.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577241,'de_DE') 
;

-- 2019-10-28T12:34:53.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_CommissionSettingsLine_ID', Name='Einstellungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=577241
;

-- 2019-10-28T12:34:53.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CommissionSettingsLine_ID', Name='Einstellungsdetail', Description=NULL, Help=NULL, AD_Element_ID=577241 WHERE UPPER(ColumnName)='C_COMMISSIONSETTINGSLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-10-28T12:34:53.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_CommissionSettingsLine_ID', Name='Einstellungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=577241 AND IsCentrallyMaintained='Y'
;

-- 2019-10-28T12:34:53.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Einstellungsdetail', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577241) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577241)
;

-- 2019-10-28T12:34:53.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Einstellungsdetail', Name='Einstellungsdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577241)
;

-- 2019-10-28T12:34:53.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Einstellungsdetail', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577241
;

-- 2019-10-28T12:34:53.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Einstellungsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID = 577241
;

-- 2019-10-28T12:34:53.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Einstellungsdetail', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577241
;

-- 2019-10-28T12:35:01.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Settings detail', PrintName='Settings detail',Updated=TO_TIMESTAMP('2019-10-28 13:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577241 AND AD_Language='en_US'
;

-- 2019-10-28T12:35:01.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577241,'en_US') 
;

-- 2019-10-28T12:35:46.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-10-28 13:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569270
;

-- 2019-10-28T12:35:47.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commissionsettingsline','PercentOfBasePoints','NUMERIC',null,null)
;

-- 2019-10-28T12:35:47.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commissionsettingsline','PercentOfBasePoints',null,'NOT NULL',null)
;
