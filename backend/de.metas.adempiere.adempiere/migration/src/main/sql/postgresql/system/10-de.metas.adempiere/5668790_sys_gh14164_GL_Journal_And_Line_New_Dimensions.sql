-- Column: GL_Journal.C_Order_ID
-- 2022-12-15T11:33:59.760Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585328,558,0,30,224,'C_Order_ID',TO_TIMESTAMP('2022-12-15 13:33:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sales order',0,0,TO_TIMESTAMP('2022-12-15 13:33:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T11:33:59.792Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585328 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T11:33:59.882Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- Column: GL_Journal.M_Product_ID
-- 2022-12-15T11:34:50.818Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585329,454,0,30,224,'M_Product_ID',TO_TIMESTAMP('2022-12-15 13:34:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Product, Service, Item','D',0,10,'Identifies an item which is either purchased or sold in this organization.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Product',0,0,TO_TIMESTAMP('2022-12-15 13:34:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T11:34:50.850Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585329 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T11:34:50.911Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2022-12-15T11:34:55.905Z
/* DDL */ SELECT public.db_alter_table('GL_Journal','ALTER TABLE public.GL_Journal ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2022-12-15T11:34:56.062Z
ALTER TABLE GL_Journal ADD CONSTRAINT MProduct_GLJournal FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2022-12-15T11:49:56.493Z
/* DDL */ SELECT public.db_alter_table('GL_Journal','ALTER TABLE public.GL_Journal ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2022-12-15T11:49:56.586Z
ALTER TABLE GL_Journal ADD CONSTRAINT COrder_GLJournal FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;




-- 2022-12-15T13:46:22.116Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581871,0,'DR_M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 15:46:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Section Code','Section Code',TO_TIMESTAMP('2022-12-15 15:46:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:46:22.149Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581871 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DR_M_SectionCode_ID
-- 2022-12-15T13:46:46.891Z
UPDATE AD_Element_Trl SET Name='Section Code (Debit)', PrintName='Section Code (Debit)',Updated=TO_TIMESTAMP('2022-12-15 15:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581871 AND AD_Language='de_CH'
;

-- 2022-12-15T13:46:46.972Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581871,'de_CH') 
;

-- Element: DR_M_SectionCode_ID
-- 2022-12-15T13:46:59.127Z
UPDATE AD_Element_Trl SET Name='Section Code (Debit)', PrintName='Section Code (Debit)',Updated=TO_TIMESTAMP('2022-12-15 15:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581871 AND AD_Language='de_DE'
;

-- 2022-12-15T13:46:59.183Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581871,'de_DE') 
;

-- Element: DR_M_SectionCode_ID
-- 2022-12-15T13:47:02.983Z
UPDATE AD_Element_Trl SET Name='Section Code (Debit)', PrintName='Section Code (Debit)',Updated=TO_TIMESTAMP('2022-12-15 15:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581871 AND AD_Language='en_US'
;

-- 2022-12-15T13:47:03.040Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581871,'en_US') 
;

-- 2022-12-15T13:47:03.068Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581871,'en_US') 
;

-- Element: DR_M_SectionCode_ID
-- 2022-12-15T13:47:06.521Z
UPDATE AD_Element_Trl SET Name='Section Code (Debit)', PrintName='Section Code (Debit)',Updated=TO_TIMESTAMP('2022-12-15 15:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581871 AND AD_Language='fr_CH'
;

-- 2022-12-15T13:47:06.577Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581871,'fr_CH') 
;

-- 2022-12-15T13:47:34.617Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581872,0,'CR_M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 15:47:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Section Code (Credit)','Section Code (Credit)',TO_TIMESTAMP('2022-12-15 15:47:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:47:34.645Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581872 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-15T13:48:19.554Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581873,0,'DR_M_Product_ID',TO_TIMESTAMP('2022-12-15 15:48:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Material (Debit)','Material (Debit)',TO_TIMESTAMP('2022-12-15 15:48:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:48:19.582Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581873 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DR_M_Product_ID
-- 2022-12-15T13:49:25.426Z
UPDATE AD_Element_Trl SET Name='Produkt (Soll)', PrintName='Produkt (Soll)',Updated=TO_TIMESTAMP('2022-12-15 15:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581873 AND AD_Language='de_DE'
;

-- 2022-12-15T13:49:25.482Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581873,'de_DE') 
;

-- Element: DR_M_Product_ID
-- 2022-12-15T13:49:29.807Z
UPDATE AD_Element_Trl SET Name='Produkt (Soll)', PrintName='Produkt (Soll)',Updated=TO_TIMESTAMP('2022-12-15 15:49:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581873 AND AD_Language='de_CH'
;

-- 2022-12-15T13:49:29.865Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581873,'de_CH') 
;

-- Element: DR_M_SectionCode_ID
-- 2022-12-15T13:50:12.044Z
UPDATE AD_Element_Trl SET Name='Sektionskennung (Soll)', PrintName='Sektionskennung (Soll)',Updated=TO_TIMESTAMP('2022-12-15 15:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581871 AND AD_Language='de_CH'
;

-- 2022-12-15T13:50:12.101Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581871,'de_CH') 
;

-- Element: DR_M_SectionCode_ID
-- 2022-12-15T13:50:15.065Z
UPDATE AD_Element_Trl SET Name='Sektionskennung (Soll)', PrintName='Sektionskennung (Soll)',Updated=TO_TIMESTAMP('2022-12-15 15:50:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581871 AND AD_Language='de_DE'
;

-- 2022-12-15T13:50:15.121Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581871,'de_DE') 
;

-- Element: CR_M_SectionCode_ID
-- 2022-12-15T13:52:04.909Z
UPDATE AD_Element_Trl SET Name='Sektionskennung (Haben)', PrintName='Sektionskennung (Haben)',Updated=TO_TIMESTAMP('2022-12-15 15:52:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581872 AND AD_Language='de_CH'
;

-- 2022-12-15T13:52:04.965Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581872,'de_CH') 
;

-- Element: CR_M_SectionCode_ID
-- 2022-12-15T13:52:08.713Z
UPDATE AD_Element_Trl SET Name='Sektionskennung (Haben)', PrintName='Sektionskennung (Haben)',Updated=TO_TIMESTAMP('2022-12-15 15:52:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581872 AND AD_Language='de_DE'
;

-- 2022-12-15T13:52:08.813Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581872,'de_DE') 
;

-- 2022-12-15T13:52:40.257Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581874,0,'CR_M_Product_ID',TO_TIMESTAMP('2022-12-15 15:52:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Material (Credit)','Material (Credit)',TO_TIMESTAMP('2022-12-15 15:52:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:52:40.284Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581874 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CR_M_Product_ID
-- 2022-12-15T13:52:57.525Z
UPDATE AD_Element_Trl SET Name='Produkt (Haben)', PrintName='Produkt (Haben)',Updated=TO_TIMESTAMP('2022-12-15 15:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581874 AND AD_Language='de_DE'
;

-- 2022-12-15T13:52:57.581Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581874,'de_DE') 
;

-- Element: CR_M_Product_ID
-- 2022-12-15T13:53:01.140Z
UPDATE AD_Element_Trl SET Name='Produkt (Haben)', PrintName='Produkt (Haben)',Updated=TO_TIMESTAMP('2022-12-15 15:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581874 AND AD_Language='de_CH'
;

-- 2022-12-15T13:53:01.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581874,'de_CH') 
;

-- 2022-12-15T13:53:59.372Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581875,0,'CR_C_Order_ID',TO_TIMESTAMP('2022-12-15 15:53:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order (Credit)','Order (Credit)',TO_TIMESTAMP('2022-12-15 15:53:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:53:59.399Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581875 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CR_C_Order_ID
-- 2022-12-15T13:55:02.593Z
UPDATE AD_Element_Trl SET Name='Auftrag/Bestellung (Haben)', PrintName='Auftrag/Bestellung (Haben)',Updated=TO_TIMESTAMP('2022-12-15 15:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581875 AND AD_Language='de_CH'
;

-- 2022-12-15T13:55:02.651Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581875,'de_CH') 
;

-- Element: CR_C_Order_ID
-- 2022-12-15T13:55:08.942Z
UPDATE AD_Element_Trl SET Name='Auftrag/Bestellung (Haben)', PrintName='Auftrag/Bestellung (Haben)',Updated=TO_TIMESTAMP('2022-12-15 15:55:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581875 AND AD_Language='de_DE'
;

-- 2022-12-15T13:55:08.996Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581875,'de_DE') 
;

-- 2022-12-15T13:55:25.233Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581876,0,'DR_C_Order_ID',TO_TIMESTAMP('2022-12-15 15:55:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order (Debit)','Order (Debit)',TO_TIMESTAMP('2022-12-15 15:55:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:55:25.261Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581876 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DR_C_Order_ID
-- 2022-12-15T13:55:39.643Z
UPDATE AD_Element_Trl SET Name='Auftrag/Bestellung (Soll)', PrintName='Auftrag/Bestellung (Soll)',Updated=TO_TIMESTAMP('2022-12-15 15:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581876 AND AD_Language='de_DE'
;

-- 2022-12-15T13:55:39.698Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581876,'de_DE') 
;

-- Element: DR_C_Order_ID
-- 2022-12-15T13:55:42.564Z
UPDATE AD_Element_Trl SET Name='Auftrag/Bestellung (Soll)', PrintName='Auftrag/Bestellung (Soll)',Updated=TO_TIMESTAMP('2022-12-15 15:55:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581876 AND AD_Language='de_CH'
;

-- 2022-12-15T13:55:42.619Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581876,'de_CH') 
;





-- Column: GL_JournalLine.DR_M_Product_ID
-- 2022-12-15T14:04:14.689Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585366,581873,0,30,540272,226,'DR_M_Product_ID',TO_TIMESTAMP('2022-12-15 16:04:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Material (Debit)',0,0,TO_TIMESTAMP('2022-12-15 16:04:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:04:14.720Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585366 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:04:14.774Z
/* DDL */  select update_Column_Translation_From_AD_Element(581873) 
;

-- 2022-12-15T14:04:19.504Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN DR_M_Product_ID NUMERIC(10)')
;

-- 2022-12-15T14:04:19.585Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT DRMProduct_GLJournalLine FOREIGN KEY (DR_M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_JournalLine.CR_M_Product_ID
-- 2022-12-15T14:04:44.698Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585367,581874,0,30,540272,226,'CR_M_Product_ID',TO_TIMESTAMP('2022-12-15 16:04:44','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Material (Credit)',0,0,TO_TIMESTAMP('2022-12-15 16:04:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:04:44.728Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585367 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:04:44.785Z
/* DDL */  select update_Column_Translation_From_AD_Element(581874) 
;

-- 2022-12-15T14:04:48.992Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN CR_M_Product_ID NUMERIC(10)')
;

-- 2022-12-15T14:04:49.050Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT CRMProduct_GLJournalLine FOREIGN KEY (CR_M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_JournalLine.DR_C_Order_ID
-- 2022-12-15T14:05:11.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585368,581876,0,30,290,226,'DR_C_Order_ID',TO_TIMESTAMP('2022-12-15 16:05:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Order (Debit)',0,0,TO_TIMESTAMP('2022-12-15 16:05:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:05:11.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585368 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:05:11.767Z
/* DDL */  select update_Column_Translation_From_AD_Element(581876) 
;

-- 2022-12-15T14:05:15.884Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN DR_C_Order_ID NUMERIC(10)')
;

-- 2022-12-15T14:05:15.944Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT DRCOrder_GLJournalLine FOREIGN KEY (DR_C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_JournalLine.CR_C_Order_ID
-- 2022-12-15T14:05:41.402Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585369,581875,0,30,290,226,'CR_C_Order_ID',TO_TIMESTAMP('2022-12-15 16:05:40','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Order (Credit)',0,0,TO_TIMESTAMP('2022-12-15 16:05:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:05:41.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585369 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:05:41.487Z
/* DDL */  select update_Column_Translation_From_AD_Element(581875) 
;

-- Name: M_SectionCode
-- 2022-12-15T14:09:26.873Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541698,TO_TIMESTAMP('2022-12-15 16:09:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_SectionCode',TO_TIMESTAMP('2022-12-15 16:09:26','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-12-15T14:09:26.904Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541698 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_SectionCode
-- Table: M_SectionCode
-- Key: M_SectionCode.M_SectionCode_ID
-- 2022-12-15T14:16:05.096Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,583975,583973,0,541698,542200,541589,TO_TIMESTAMP('2022-12-15 16:16:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N',TO_TIMESTAMP('2022-12-15 16:16:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: GL_JournalLine.DR_M_SectionCode_ID
-- 2022-12-15T14:16:38.406Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585370,581871,0,30,541698,226,'DR_M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 16:16:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code (Debit)',0,0,TO_TIMESTAMP('2022-12-15 16:16:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:16:38.435Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585370 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:16:38.490Z
/* DDL */  select update_Column_Translation_From_AD_Element(581871) 
;

-- 2022-12-15T14:16:43.044Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN DR_M_SectionCode_ID NUMERIC(10)')
;

-- 2022-12-15T14:16:43.105Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT DRMSectionCode_GLJournalLine FOREIGN KEY (DR_M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_JournalLine.CR_M_SectionCode_ID
-- 2022-12-15T14:17:03.049Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585371,581872,0,30,541698,226,'CR_M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code (Credit)',0,0,TO_TIMESTAMP('2022-12-15 16:17:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T14:17:03.076Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585371 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T14:17:03.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(581872) 
;

-- 2022-12-15T14:17:07.735Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN CR_M_SectionCode_ID NUMERIC(10)')
;

-- 2022-12-15T14:17:07.811Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT CRMSectionCode_GLJournalLine FOREIGN KEY (CR_M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- -- Field: GL Journal(540356,D) -> Journal(540854,D) -> Reversal ID
-- -- Column: GL_Journal.Reversal_ID
-- -- 2022-12-15T14:21:53.439Z
-- INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,55306,710000,0,540854,TO_TIMESTAMP('2022-12-15 16:21:52','YYYY-MM-DD HH24:MI:SS'),100,'ID of document reversal',22,'D','Y','Y','N','N','N','N','N','Reversal ID',TO_TIMESTAMP('2022-12-15 16:21:52','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- -- 2022-12-15T14:21:53.467Z
-- INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
-- ;

-- -- 2022-12-15T14:21:53.497Z
-- /* DDL */  select update_FieldTranslation_From_AD_Name_Element(53457) 
-- ;

-- -- 2022-12-15T14:21:53.539Z
-- DELETE FROM AD_Element_Link WHERE AD_Field_ID=710000
-- ;

-- -- 2022-12-15T14:21:53.567Z
-- /* DDL */ select AD_Element_Link_Create_Missing_Field(710000)
;

-- Field: GL Journal(540356,D) -> Journal(540854,D) -> Posting Error
-- Column: GL_Journal.PostingError_Issue_ID
-- 2022-12-15T14:21:54.068Z
-- INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570869,710001,0,540854,TO_TIMESTAMP('2022-12-15 16:21:53','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Posting Error',TO_TIMESTAMP('2022-12-15 16:21:53','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- -- 2022-12-15T14:21:54.096Z
-- INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
-- ;

-- -- 2022-12-15T14:21:54.124Z
-- /* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
-- ;

-- -- 2022-12-15T14:21:54.155Z
-- DELETE FROM AD_Element_Link WHERE AD_Field_ID=710001
-- ;

-- -- 2022-12-15T14:21:54.183Z
-- /* DDL */ select AD_Element_Link_Create_Missing_Field(710001)
-- ;

-- Field: GL Journal(540356,D) -> Journal(540854,D) -> Sales order
-- Column: GL_Journal.C_Order_ID
-- 2022-12-15T14:21:54.643Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585328,710002,0,540854,TO_TIMESTAMP('2022-12-15 16:21:54','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','Y','N','N','N','N','N','Sales order',TO_TIMESTAMP('2022-12-15 16:21:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:21:54.671Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:21:54.701Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2022-12-15T14:21:54.745Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710002
;

-- 2022-12-15T14:21:54.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710002)
;

-- Field: GL Journal(540356,D) -> Journal(540854,D) -> Product
-- Column: GL_Journal.M_Product_ID
-- 2022-12-15T14:21:55.282Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585329,710003,0,540854,TO_TIMESTAMP('2022-12-15 16:21:54','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item',10,'D','Identifies an item which is either purchased or sold in this organization.','Y','Y','N','N','N','N','N','Product',TO_TIMESTAMP('2022-12-15 16:21:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:21:55.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:21:55.338Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-12-15T14:21:55.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710003
;

-- 2022-12-15T14:21:55.434Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710003)
;

-- Field: GL Journal(540356,D) -> Line(540855,D) -> Material (Debit)
-- Column: GL_JournalLine.DR_M_Product_ID
-- 2022-12-15T14:22:20.727Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585366,710004,0,540855,TO_TIMESTAMP('2022-12-15 16:22:20','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Material (Debit)',TO_TIMESTAMP('2022-12-15 16:22:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:22:20.754Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:22:20.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581873) 
;

-- 2022-12-15T14:22:20.814Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710004
;

-- 2022-12-15T14:22:20.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710004)
;

-- Field: GL Journal(540356,D) -> Line(540855,D) -> Material (Credit)
-- Column: GL_JournalLine.CR_M_Product_ID
-- 2022-12-15T14:22:21.344Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585367,710005,0,540855,TO_TIMESTAMP('2022-12-15 16:22:20','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Material (Credit)',TO_TIMESTAMP('2022-12-15 16:22:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:22:21.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:22:21.399Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581874) 
;

-- 2022-12-15T14:22:21.430Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710005
;

-- 2022-12-15T14:22:21.458Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710005)
;

-- Field: GL Journal(540356,D) -> Line(540855,D) -> Order (Debit)
-- Column: GL_JournalLine.DR_C_Order_ID
-- 2022-12-15T14:22:21.913Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585368,710006,0,540855,TO_TIMESTAMP('2022-12-15 16:22:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Order (Debit)',TO_TIMESTAMP('2022-12-15 16:22:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:22:21.941Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:22:21.969Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581876) 
;

-- 2022-12-15T14:22:22Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710006
;

-- 2022-12-15T14:22:22.028Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710006)
;

-- Field: GL Journal(540356,D) -> Line(540855,D) -> Order (Credit)
-- Column: GL_JournalLine.CR_C_Order_ID
-- 2022-12-15T14:22:22.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585369,710007,0,540855,TO_TIMESTAMP('2022-12-15 16:22:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Order (Credit)',TO_TIMESTAMP('2022-12-15 16:22:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:22:22.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:22:22.605Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581875) 
;

-- 2022-12-15T14:22:22.635Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710007
;

-- 2022-12-15T14:22:22.662Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710007)
;

-- Field: GL Journal(540356,D) -> Line(540855,D) -> Section Code (Debit)
-- Column: GL_JournalLine.DR_M_SectionCode_ID
-- 2022-12-15T14:22:23.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585370,710008,0,540855,TO_TIMESTAMP('2022-12-15 16:22:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Code (Debit)',TO_TIMESTAMP('2022-12-15 16:22:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:22:23.193Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710008 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:22:23.221Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581871) 
;

-- 2022-12-15T14:22:23.252Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710008
;

-- 2022-12-15T14:22:23.279Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710008)
;

-- Field: GL Journal(540356,D) -> Line(540855,D) -> Section Code (Credit)
-- Column: GL_JournalLine.CR_M_SectionCode_ID
-- 2022-12-15T14:22:23.738Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585371,710009,0,540855,TO_TIMESTAMP('2022-12-15 16:22:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Code (Credit)',TO_TIMESTAMP('2022-12-15 16:22:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:22:23.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T14:22:23.794Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581872) 
;

-- 2022-12-15T14:22:23.830Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710009
;

-- 2022-12-15T14:22:23.858Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710009)
;

-- UI Element: GL Journal(540356,D) -> Journal(540854,D) -> advanced edit -> 10 -> advanced edit.Section Code
-- Column: GL_Journal.M_SectionCode_ID
-- 2022-12-15T14:25:09.065Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,703931,0,540854,540994,614541,'F',TO_TIMESTAMP('2022-12-15 16:25:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',180,0,0,TO_TIMESTAMP('2022-12-15 16:25:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Journal(540854,D) -> advanced edit -> 10 -> advanced edit.Product
-- Column: GL_Journal.M_Product_ID
-- 2022-12-15T14:25:24.703Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710003,0,540854,540994,614542,'F',TO_TIMESTAMP('2022-12-15 16:25:24','YYYY-MM-DD HH24:MI:SS'),100,'Product, Service, Item','Identifies an item which is either purchased or sold in this organization.','Y','N','N','Y','N','N','N',0,'Product',190,0,0,TO_TIMESTAMP('2022-12-15 16:25:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Journal(540854,D) -> advanced edit -> 10 -> advanced edit.Sales order
-- Column: GL_Journal.C_Order_ID
-- 2022-12-15T14:25:42.061Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710002,0,540854,540994,614543,'F',TO_TIMESTAMP('2022-12-15 16:25:41','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','N','N','N',0,'Sales order',200,0,0,TO_TIMESTAMP('2022-12-15 16:25:41','YYYY-MM-DD HH24:MI:SS'),100)
;
-- UI Element: GL Journal(540356,D) -> Line(540855,D) -> accounts and amounts -> 10 -> DR.Section Code (Debit)
-- Column: GL_JournalLine.DR_M_SectionCode_ID
-- 2022-12-15T14:33:35.579Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710008,0,540855,542600,614545,'F',TO_TIMESTAMP('2022-12-15 16:33:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code (Debit)',40,0,0,TO_TIMESTAMP('2022-12-15 16:33:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Line(540855,D) -> accounts and amounts -> 10 -> DR.Material (Debit)
-- Column: GL_JournalLine.DR_M_Product_ID
-- 2022-12-15T14:33:47.193Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710004,0,540855,542600,614546,'F',TO_TIMESTAMP('2022-12-15 16:33:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Material (Debit)',50,0,0,TO_TIMESTAMP('2022-12-15 16:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Line(540855,D) -> accounts and amounts -> 10 -> DR.Order (Debit)
-- Column: GL_JournalLine.DR_C_Order_ID
-- 2022-12-15T14:34:00.041Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710006,0,540855,542600,614547,'F',TO_TIMESTAMP('2022-12-15 16:33:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Order (Debit)',60,0,0,TO_TIMESTAMP('2022-12-15 16:33:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Line(540855,D) -> accounts and amounts -> 20 -> CR.Section Code (Credit)
-- Column: GL_JournalLine.CR_M_SectionCode_ID
-- 2022-12-15T14:34:23.453Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710009,0,540855,542601,614548,'F',TO_TIMESTAMP('2022-12-15 16:34:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code (Credit)',40,0,0,TO_TIMESTAMP('2022-12-15 16:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Line(540855,D) -> accounts and amounts -> 20 -> CR.Material (Credit)
-- Column: GL_JournalLine.CR_M_Product_ID
-- 2022-12-15T14:34:35.030Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710005,0,540855,542601,614549,'F',TO_TIMESTAMP('2022-12-15 16:34:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Material (Credit)',50,0,0,TO_TIMESTAMP('2022-12-15 16:34:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Line(540855,D) -> accounts and amounts -> 20 -> CR.Order (Credit)
-- Column: GL_JournalLine.CR_C_Order_ID
-- 2022-12-15T14:34:52.636Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710007,0,540855,542601,614550,'F',TO_TIMESTAMP('2022-12-15 16:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Order (Credit)',60,0,0,TO_TIMESTAMP('2022-12-15 16:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal(540356,D) -> Line(540855,D) -> bottom -> 10 -> default.Section Code
-- Column: GL_JournalLine.M_SectionCode_ID
-- 2022-12-15T14:36:18.323Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-12-15 16:36:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611512
;



-- 2022-12-16T10:02:17.109Z
/* DDL */ SELECT public.db_alter_table('GL_JournalLine','ALTER TABLE public.GL_JournalLine ADD COLUMN CR_C_Order_ID NUMERIC(10)')
;

-- 2022-12-16T10:02:17.186Z
ALTER TABLE GL_JournalLine ADD CONSTRAINT CRCOrder_GLJournalLine FOREIGN KEY (CR_C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;




