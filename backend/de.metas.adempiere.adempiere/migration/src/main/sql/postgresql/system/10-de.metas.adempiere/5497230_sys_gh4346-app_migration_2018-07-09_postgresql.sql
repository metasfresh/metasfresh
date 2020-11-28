
-- 2018-07-09T11:53:48.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544174,0,'AD_Sequence_ProjectValue_ID',TO_TIMESTAMP('2018-07-09 11:53:48','YYYY-MM-DD HH24:MI:SS'),100,'Nummerfolge für Projekt-Suchschlüssel','D','Y','Projekt-Nummerfolge','Projekt-Nummerfolge',TO_TIMESTAMP('2018-07-09 11:53:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-09T11:53:48.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544174 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-09T11:53:53.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-09 11:53:53','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544174 AND AD_Language='de_CH'
;

-- 2018-07-09T11:53:53.247
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544174,'de_CH') 
;

-- 2018-07-09T11:54:20.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-09 11:54:20','YYYY-MM-DD HH24:MI:SS'),Name='Project sequence',PrintName='Project sequence',Description='Sequence for projekt values' WHERE AD_Element_ID=544174 AND AD_Language='en_US'
;

-- 2018-07-09T11:54:20.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544174,'en_US') 
;

-- 2018-07-09T11:54:26.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-09 11:54:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544174 AND AD_Language='en_US'
;

-- 2018-07-09T11:54:26.036
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544174,'en_US') 
;

-- 2018-07-09T11:54:53.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560665,544174,0,30,128,575,'N','AD_Sequence_ProjectValue_ID',TO_TIMESTAMP('2018-07-09 11:54:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Nummerfolge für Projekt-Suchschlüssel','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Projekt-Nummerfolge',0,0,TO_TIMESTAMP('2018-07-09 11:54:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-09T11:54:53.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560665 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-09T11:54:58.999
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_ProjectType','ALTER TABLE public.C_ProjectType ADD COLUMN AD_Sequence_ProjectValue_ID NUMERIC(10)')
;

-- 2018-07-09T11:54:59.088
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_ProjectType ADD CONSTRAINT ADSequenceProjectValue_CProjectType FOREIGN KEY (AD_Sequence_ProjectValue_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- 2018-07-09T11:55:15.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560665,565096,0,476,TO_TIMESTAMP('2018-07-09 11:55:15','YYYY-MM-DD HH24:MI:SS'),100,'Nummerfolge für Projekt-Suchschlüssel',10,'D','Y','Y','N','N','N','N','N','Projekt-Nummerfolge',TO_TIMESTAMP('2018-07-09 11:55:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-09T11:55:15.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- 2018-07-09T11:50:09.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544173,0,'AD_Sequence_ProductValue_ID',TO_TIMESTAMP('2018-07-09 11:50:09','YYYY-MM-DD HH24:MI:SS'),100,'Nummerfolge für Produkt-Suchschlüssel','D','Y','Produkt-Nummerfolge','Produkt-Nummerfolge',TO_TIMESTAMP('2018-07-09 11:50:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-09T11:50:09.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544173 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-07-09T11:50:13.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-09 11:50:13','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544173 AND AD_Language='de_CH'
;

-- 2018-07-09T11:50:13.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544173,'de_CH') 
;

-- 2018-07-09T11:50:43.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-07-09 11:50:43','YYYY-MM-DD HH24:MI:SS'),Name='Product sequence',PrintName='Product sequence',Description='Sequence for product value' WHERE AD_Element_ID=544173 AND AD_Language='en_US'
;

-- 2018-07-09T11:50:43.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544173,'en_US') 
;

-- 2018-07-09T11:51:44.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='AD_Sequence for DocumentNo and Value',Updated=TO_TIMESTAMP('2018-07-09 11:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=128
;

-- 2018-07-09T11:51:48.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560664,544173,0,30,128,209,'N','AD_Sequence_ProductValue_ID',TO_TIMESTAMP('2018-07-09 11:51:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Nummerfolge für Produkt-Suchschlüssel','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Produkt-Nummerfolge',0,0,TO_TIMESTAMP('2018-07-09 11:51:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-07-09T11:51:48.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560664 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-07-09T11:51:52.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product_Category','ALTER TABLE public.M_Product_Category ADD COLUMN AD_Sequence_ProductValue_ID NUMERIC(10)')
;

-- 2018-07-09T11:51:53.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_Category ADD CONSTRAINT ADSequenceProductValue_MProductCategory FOREIGN KEY (AD_Sequence_ProductValue_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;
-- 2018-07-09T11:57:34.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560664,565101,0,189,TO_TIMESTAMP('2018-07-09 11:57:34','YYYY-MM-DD HH24:MI:SS'),100,'Nummerfolge für Produkt-Suchschlüssel',10,'D','Y','Y','N','N','N','N','N','Produkt-Nummerfolge',TO_TIMESTAMP('2018-07-09 11:57:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-09T11:57:34.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- fix ValueNames for ProjectCategory
UPDATE AD_Ref_List SET ValueName='AssetProject' WHERE AD_Ref_List_ID=572;   -- Asset-Projekt
UPDATE AD_Ref_List SET ValueName='General' WHERE AD_Ref_List_ID=571; -- Allgemein
UPDATE AD_Ref_List SET ValueName='ServiceChargeProject' WHERE AD_Ref_List_ID=574; -- Service (Charge) Project
UPDATE AD_Ref_List SET ValueName='WorkOrderJob' WHERE AD_Ref_List_ID=573; -- Arbeitsauftrag (Job)

-- fix ValueNames for ProjInvoiceRule
UPDATE AD_Ref_List SET ValueName='None' WHERE AD_Ref_List_ID=892; --Nichts

-- fix ValueNames for C_Project LineLevel
UPDATE AD_Ref_List SET ValueName='Phase' WHERE AD_Ref_List_ID=898; -- Aktual-Phase
UPDATE AD_Ref_List SET ValueName='Project' WHERE AD_Ref_List_ID=897; -- Projekt
UPDATE AD_Ref_List SET ValueName='Task' WHERE AD_Ref_List_ID=899; -- Aufgabe
