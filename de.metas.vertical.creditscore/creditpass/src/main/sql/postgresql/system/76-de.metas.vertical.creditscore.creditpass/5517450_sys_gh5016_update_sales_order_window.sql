-- 2019-03-26T18:29:17.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576301,0,'CreditpassStatus',TO_TIMESTAMP('2019-03-26 18:29:17','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob das Creditpass-Status erfolgreich ist oder nicht und ob er benötigt wird oder nicht, für die ausgewählte Zahlungsweise ','de.metas.vertical.creditscore.creditpass','Y','Creditpass Status','Creditpass Status',TO_TIMESTAMP('2019-03-26 18:29:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T18:29:17.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576301 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-26T18:31:03.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Shows if Creditpass-Status successful is or not and if it''s needed or not', IsTranslated='Y', Name='Creditpass status', PrintName='Creditpass status',Updated=TO_TIMESTAMP('2019-03-26 18:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576301 AND AD_Language='en_US'
;

-- 2019-03-26T18:31:03.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576301,'en_US') 
;

-- 2019-03-26T18:33:01.602
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576302,0,'CreditpassFlag',TO_TIMESTAMP('2019-03-26 18:33:01','YYYY-MM-DD HH24:MI:SS'),100,'Creditpass Prüfung nicht abgeschlossen','de.metas.vertical.creditscore.creditpass','Y','Creditpass Prüfung nicht abgeschlossen','Creditpass Prüfung nicht abgeschlossen',TO_TIMESTAMP('2019-03-26 18:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T18:33:01.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576302 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-03-26T18:34:05.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Creditpass check not completed', IsTranslated='Y', Name='Creditpass check not completed', PrintName='Creditpass check not completed',Updated=TO_TIMESTAMP('2019-03-26 18:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576302 AND AD_Language='en_US'
;

-- 2019-03-26T18:34:05.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576302,'en_US') 
;

-- 2019-03-26T18:36:04.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,ReadOnlyLogic,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,564639,576301,0,10,259,52033,'CreditpassStatus',TO_TIMESTAMP('2019-03-26 18:36:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Zeigt an, ob das Creditpass-Status erfolgreich ist oder nicht und ob er benötigt wird oder nicht, für die ausgewählte Zahlungsweise ','de.metas.vertical.creditscore.creditpass',1000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Creditpass Status','',0,TO_TIMESTAMP('2019-03-26 18:36:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-26T18:36:04.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564639 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-26T18:36:54.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,ReadOnlyLogic,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,564640,576302,0,23,259,52033,'CreditpassFlag',TO_TIMESTAMP('2019-03-26 18:36:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Creditpass Prüfung nicht abgeschlossen','de.metas.vertical.creditscore.creditpass',0,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Creditpass Prüfung nicht abgeschlossen','',0,TO_TIMESTAMP('2019-03-26 18:36:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-03-26T18:36:54.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564640 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-03-26T18:37:48.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,564639,578096,0,186,95,TO_TIMESTAMP('2019-03-26 18:37:48','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob das Creditpass-Status erfolgreich ist oder nicht und ob er benötigt wird oder nicht, für die ausgewählte Zahlungsweise ',23,'de.metas.vertical.creditscore.creditpass',0,'Y','Y','N','N','N','N','N','Y','Creditpass Status',380,340,1,1,TO_TIMESTAMP('2019-03-26 18:37:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T18:37:48.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-26T18:38:25.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,564640,578097,0,186,95,TO_TIMESTAMP('2019-03-26 18:38:24','YYYY-MM-DD HH24:MI:SS'),100,'Creditpass Prüfung nicht abgeschlossen',23,'de.metas.vertical.creditscore.creditpass',0,'Y','Y','N','N','N','N','Y','Y','Creditpass Prüfung nicht abgeschlossen',380,340,1,1,TO_TIMESTAMP('2019-03-26 18:38:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T18:38:25.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=578097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-03-26T18:38:32.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-03-26 18:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578096
;

-- 2019-03-26T18:39:45.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578096,0,186,558081,1000026,'F',TO_TIMESTAMP('2019-03-26 18:39:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','CreditpassStatus',60,0,0,TO_TIMESTAMP('2019-03-26 18:39:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T18:40:28.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,578097,0,186,558082,1000026,'F',TO_TIMESTAMP('2019-03-26 18:40:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N','CreditpassPrüfung',70,0,0,TO_TIMESTAMP('2019-03-26 18:40:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-03-26T18:40:41.077
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542394, SeqNo=10,Updated=TO_TIMESTAMP('2019-03-26 18:40:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558082
;

-- 2019-03-26T18:40:48.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542394, SeqNo=20,Updated=TO_TIMESTAMP('2019-03-26 18:40:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558081
;
-- 2019-03-26T19:40:57.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN CreditpassStatus VARCHAR(1000)')
;

-- 2019-03-26T22:07:20.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541087,'Y','de.metas.vertical.creditscore.creditpass.process.CS_Creditpass_TransactionFrom_C_Order','N',TO_TIMESTAMP('2019-03-26 22:07:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.creditscore.creditpass','Y','N','N','N','N','N','N','Y','Y',0,'CreditPass überprüfen','N','N','Java',TO_TIMESTAMP('2019-03-26 22:07:20','YYYY-MM-DD HH24:MI:SS'),100,'CS_Creditpass_TransactionFrom_C_Order')
;

-- 2019-03-26T22:07:20.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541087 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-03-26T22:07:36.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='CS_Creditpass_TransactionFrom_C_Order',Updated=TO_TIMESTAMP('2019-03-26 22:07:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541087
;

-- 2019-03-26T22:08:46.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541087,186,259,540693,143,TO_TIMESTAMP('2019-03-26 22:08:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.creditscore.creditpass','Y',TO_TIMESTAMP('2019-03-26 22:08:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2019-03-26T22:09:17.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET AD_Tab_ID=NULL, AD_Window_ID=NULL, WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2019-03-26 22:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540693
;

-- 2019-03-26T22:16:55.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N', FieldLength=1, IsMandatory='Y',Updated=TO_TIMESTAMP('2019-03-26 22:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564640
;

-- 2019-03-26T22:19:02.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y', IsMandatory='N',Updated=TO_TIMESTAMP('2019-03-26 22:19:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564640
;

-- 2019-03-26T19:40:46.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN CreditpassFlag CHAR(1) DEFAULT ''Y''')
;

-- 2019-03-27T14:23:35.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=540011, SeqNo=60,Updated=TO_TIMESTAMP('2019-03-27 14:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542394
;

-- 2019-03-27T14:24:19.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=45,Updated=TO_TIMESTAMP('2019-03-27 14:24:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542394
;

