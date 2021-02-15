-- 2021-02-11T14:39:27.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' & @IsAmountValid/''Y''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-11 16:39:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572622
;

-- 2021-02-11T14:40:30.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-02-11 16:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=628824
;

-- 2021-02-12T08:42:40.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572803,1047,0,20,541573,'Processed',TO_TIMESTAMP('2021-02-12 10:42:40','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2021-02-12 10:42:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-12T08:42:40.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572803 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-12T08:42:40.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2021-02-12T08:42:42.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice','ALTER TABLE public.C_RemittanceAdvice ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- 2021-02-12T08:43:03.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572804,1047,0,20,541574,'Processed',TO_TIMESTAMP('2021-02-12 10:43:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2021-02-12 10:43:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-12T08:43:03.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572804 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-12T08:43:03.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2021-02-12T08:44:10.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-12 10:44:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572790
;

-- 2021-02-12T08:45:12.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=75,Updated=TO_TIMESTAMP('2021-02-12 10:45:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=577073
;

-- 2021-02-12T08:45:57.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice_Line','ALTER TABLE public.C_RemittanceAdvice_Line ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- 2021-02-12T08:46:08.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice','Processed','CHAR(1)',null,'N')
;

-- 2021-02-12T08:46:08.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice SET Processed='N' WHERE Processed IS NULL
;

-- 2021-02-12T08:52:23.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-02-12 10:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572623
;

-- 2021-02-12T09:01:26.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-02-12 11:01:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572622
;

-- 2021-02-12T09:01:27.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsLineAcknowledged','CHAR(1)',null,null)
;

-- 2021-02-12T09:03:15.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:03:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572622
;

-- 2021-02-12T09:03:18.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsLineAcknowledged','CHAR(1)',null,'N')
;

-- 2021-02-12T09:03:18.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsLineAcknowledged='N' WHERE IsLineAcknowledged IS NULL
;

-- 2021-02-12T09:03:18.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsLineAcknowledged',null,'NOT NULL',null)
;

-- 2021-02-12T09:13:36.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572621
;

-- 2021-02-12T09:13:37.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsServiceColumnsResolved','CHAR(1)',null,'N')
;

-- 2021-02-12T09:13:37.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsServiceColumnsResolved='N' WHERE IsServiceColumnsResolved IS NULL
;

-- 2021-02-12T09:13:37.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsServiceColumnsResolved',null,'NOT NULL',null)
;

-- 2021-02-12T09:13:46.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572618
;

-- 2021-02-12T09:13:47.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsServiceFeeVatRateValid','CHAR(1)',null,'N')
;

-- 2021-02-12T09:13:47.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsServiceFeeVatRateValid='N' WHERE IsServiceFeeVatRateValid IS NULL
;

-- 2021-02-12T09:13:47.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsServiceFeeVatRateValid',null,'NOT NULL',null)
;

-- 2021-02-12T09:13:58.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:13:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572619
;

-- 2021-02-12T09:14:00.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceResolved','CHAR(1)',null,'N')
;

-- 2021-02-12T09:14:00.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsInvoiceResolved='N' WHERE IsInvoiceResolved IS NULL
;

-- 2021-02-12T09:14:00.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceResolved',null,'NOT NULL',null)
;

-- 2021-02-12T09:14:11.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:14:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572616
;

-- 2021-02-12T09:14:12.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceDocTypeValid','CHAR(1)',null,'N')
;

-- 2021-02-12T09:14:12.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsInvoiceDocTypeValid='N' WHERE IsInvoiceDocTypeValid IS NULL
;

-- 2021-02-12T09:14:12.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceDocTypeValid',null,'NOT NULL',null)
;

-- 2021-02-12T09:14:22.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:14:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572615
;

-- 2021-02-12T09:14:24.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceDateValid','CHAR(1)',null,'N')
;

-- 2021-02-12T09:14:24.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsInvoiceDateValid='N' WHERE IsInvoiceDateValid IS NULL
;

-- 2021-02-12T09:14:24.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsInvoiceDateValid',null,'NOT NULL',null)
;

-- 2021-02-12T09:14:40.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572617
;

-- 2021-02-12T09:14:42.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsBPartnerValid','CHAR(1)',null,'N')
;

-- 2021-02-12T09:14:42.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsBPartnerValid='N' WHERE IsBPartnerValid IS NULL
;

-- 2021-02-12T09:14:42.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsBPartnerValid',null,'NOT NULL',null)
;

-- 2021-02-12T09:14:52.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-12 11:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572620
;

-- 2021-02-12T09:14:53.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsAmountValid','CHAR(1)',null,'N')
;

-- 2021-02-12T09:14:53.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_RemittanceAdvice_Line SET IsAmountValid='N' WHERE IsAmountValid IS NULL
;

-- 2021-02-12T09:14:53.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_remittanceadvice_line','IsAmountValid',null,'NOT NULL',null)
;

-- 2021-02-12T09:23:51.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @RemittanceAmt/-1@>0',Updated=TO_TIMESTAMP('2021-02-12 11:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572570
;

-- 2021-02-12T09:27:02.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @RemittanceAmt/-1@!=0',Updated=TO_TIMESTAMP('2021-02-12 11:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572570
;

-- 2021-02-12T09:28:41.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @C_RemittanceAdviceLine.C_RemittanceAdvice_ID/-1@=@C_RemittanceAdvice_ID@',Updated=TO_TIMESTAMP('2021-02-12 11:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572570
;

-- 2021-02-12T09:31:30.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @C_Invoice_ID/-1@>0',Updated=TO_TIMESTAMP('2021-02-12 11:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572570
;

-- 2021-02-12T09:32:15.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @C_Invoice_IDm/-1@>0',Updated=TO_TIMESTAMP('2021-02-12 11:32:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572570
;

-- 2021-02-12T09:37:21.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @C_Invoice_ID/-1@>0 ',Updated=TO_TIMESTAMP('2021-02-12 11:37:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572570
;

-- 2021-02-12T09:40:54.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578758,0,'CurrenciesReadOnlyFlag',TO_TIMESTAMP('2021-02-12 11:40:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CurrencyReadOnlyFlag','CurrencyReadOnlyFlag',TO_TIMESTAMP('2021-02-12 11:40:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-12T09:40:54.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578758 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-12T09:41:18.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572805,578758,0,20,541573,'CurrenciesReadOnlyFlag',TO_TIMESTAMP('2021-02-12 11:41:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'CurrencyReadOnlyFlag',0,0,TO_TIMESTAMP('2021-02-12 11:41:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-12T09:41:18.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572805 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-12T09:41:18.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578758) 
;

-- 2021-02-12T09:41:20.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice','ALTER TABLE public.C_RemittanceAdvice ADD COLUMN CurrenciesReadOnlyFlag CHAR(1) DEFAULT ''N'' CHECK (CurrenciesReadOnlyFlag IN (''Y'',''N'')) NOT NULL')
;

-- 2021-02-12T09:42:07.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@ServiceFeeAmount/0@!=0', ReadOnlyLogic='@I_IsImported/''N''@ = ''Y''|@CurrenciesReadOnlyFlag/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-12 11:42:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572577
;

-- 2021-02-12T09:42:22.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @CurrenciesReadOnlyFlag/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-12 11:42:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572570
;

-- 2021-02-12T09:42:35.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@I_IsImported/''N''@ = ''Y'' | @CurrenciesReadOnlyFlag/''N''@ = ''Y''',Updated=TO_TIMESTAMP('2021-02-12 11:42:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572577
;

