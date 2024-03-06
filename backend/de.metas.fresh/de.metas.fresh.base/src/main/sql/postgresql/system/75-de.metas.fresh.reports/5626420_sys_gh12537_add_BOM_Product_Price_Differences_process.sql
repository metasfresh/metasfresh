-- 2022-02-16T19:40:36.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584994,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2022-02-16 21:40:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'Rohwarenpreis vs. BOM kosten','json','N','N','xls','SELECT ProductName as "Suchschlüssel",
       ProductValue as "Produktname",
       UOM as "Maßeinheit",
       ValidFrom as "Gültig ab",
       ProductPriceDifference as "Produkt Preisdifferenz",
       ProductsListPriceDifferences as "Komponente Preisdifferenzen"
FROM de_metas_endcustomer_fresh_reports.get_BOM_Product_Differences(@PP_Product_BOM_ID/NULL@, @M_PriceList_ID@, ''@Date@''::date )','Excel',TO_TIMESTAMP('2022-02-16 21:40:36','YYYY-MM-DD HH24:MI:SS'),100,'get_BOM_Product_Differences')
;

-- 2022-02-16T19:40:36.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584994 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-02-16T19:41:02.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Raw material price vs. BOM cost',Updated=TO_TIMESTAMP('2022-02-16 21:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584994
;

-- 2022-02-16T19:42:49.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53245,0,584994,542206,30,'PP_Product_BOM_ID',TO_TIMESTAMP('2022-02-16 21:42:49','YYYY-MM-DD HH24:MI:SS'),100,'Stücklistenversion','EE01',0,'Y','N','Y','N','N','N','Stücklistenversion',10,TO_TIMESTAMP('2022-02-16 21:42:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-16T19:42:49.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542206 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-02-16T19:43:33.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,449,0,584994,542207,30,'M_PriceList_ID',TO_TIMESTAMP('2022-02-16 21:43:33','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste','U',0,'Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','Y','N','Y','N','Preisliste',20,TO_TIMESTAMP('2022-02-16 21:43:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-16T19:43:33.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542207 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-02-16T19:46:04.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577762,0,584994,542208,15,'Date',TO_TIMESTAMP('2022-02-16 21:46:04','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','U',0,'Y','N','Y','N','N','N','Datum',30,TO_TIMESTAMP('2022-02-16 21:46:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-16T19:46:04.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542208 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-02-16T19:49:51.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580596,0,TO_TIMESTAMP('2022-02-16 21:49:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rohwarenpreis vs. BOM kosten','Rohwarenpreis vs. BOM kosten',TO_TIMESTAMP('2022-02-16 21:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-16T19:49:51.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580596 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-02-16T19:50:03.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Raw material price vs. BOM cost', PrintName='Raw material price vs. BOM cost',Updated=TO_TIMESTAMP('2022-02-16 21:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='en_US'
;

-- 2022-02-16T19:50:03.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'en_US') 
;

-- 2022-02-16T19:50:40.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,580596,541907,0,584994,TO_TIMESTAMP('2022-02-16 21:50:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','get_BOM_Product_Differences','Y','N','N','N','N','Rohwarenpreis vs. BOM kosten',TO_TIMESTAMP('2022-02-16 21:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-16T19:50:40.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541907 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-02-16T19:50:40.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541907, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541907)
;

-- 2022-02-16T19:50:40.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(580596) 
;

-- 2022-02-16T19:50:40.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000060, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541907 AND AD_Tree_ID=10
;

-- 2022-02-16T19:50:55.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000063, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541907 AND AD_Tree_ID=10
;

-- 2022-02-16T19:54:23.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-02-16 21:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542208
;

-- 2022-02-16T20:01:50.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Report that provided an overview of how a manufactured product component''s current purchase price compares against their historic average purchase price.
The differences are displayed as a percentage of the average purchase price (a positive number meaning components are becoming more expensive while a negative number means they''re becoming cheaper).',Updated=TO_TIMESTAMP('2022-02-16 22:01:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584994
;

-- 2022-02-16T20:01:50.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Report that provided an overview of how a manufactured product component''s current purchase price compares against their historic average purchase price.
The differences are displayed as a percentage of the average purchase price (a positive number meaning components are becoming more expensive while a negative number means they''re becoming cheaper).', IsActive='Y', Name='Rohwarenpreis vs. BOM kosten',Updated=TO_TIMESTAMP('2022-02-16 22:01:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541907
;

-- 2022-02-17T12:32:58.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rohwarenpreis vs. Stücklistenkosten', PrintName='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='de_CH'
;

-- 2022-02-17T12:32:58.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'de_CH') 
;

-- 2022-02-17T12:33:01.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rohwarenpreis vs. Stücklistenkosten', PrintName='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:33:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='de_DE'
;

-- 2022-02-17T12:33:01.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'de_DE') 
;

-- 2022-02-17T12:33:01.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580596,'de_DE') 
;

-- 2022-02-17T12:33:01.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Rohwarenpreis vs. Stücklistenkosten', Description=NULL, Help=NULL WHERE AD_Element_ID=580596
;

-- 2022-02-17T12:33:01.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rohwarenpreis vs. Stücklistenkosten', Description=NULL, Help=NULL WHERE AD_Element_ID=580596 AND IsCentrallyMaintained='Y'
;

-- 2022-02-17T12:33:01.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rohwarenpreis vs. Stücklistenkosten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580596) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580596)
;

-- 2022-02-17T12:33:01.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rohwarenpreis vs. Stücklistenkosten', Name='Rohwarenpreis vs. Stücklistenkosten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580596)
;

-- 2022-02-17T12:33:01.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rohwarenpreis vs. Stücklistenkosten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580596
;

-- 2022-02-17T12:33:01.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rohwarenpreis vs. Stücklistenkosten', Description=NULL, Help=NULL WHERE AD_Element_ID = 580596
;

-- 2022-02-17T12:33:01.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rohwarenpreis vs. Stücklistenkosten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580596
;

-- 2022-02-17T12:33:04.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Rohwarenpreis vs. Stücklistenkosten', PrintName='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:33:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='nl_NL'
;

-- 2022-02-17T12:33:04.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'nl_NL') 
;

-- 2022-02-17T12:33:35.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Report providing an overview of the current purchase price trends of manufacturing product components compared to their historical average purchase price. The differences are displayed as a percentage of the average purchase price (a positive number means components are becoming more expensive while a negative number means that they are becoming cheaper).',Updated=TO_TIMESTAMP('2022-02-17 14:33:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='en_US'
;

-- 2022-02-17T12:33:35.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'en_US') 
;

-- 2022-02-17T12:33:36.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).',Updated=TO_TIMESTAMP('2022-02-17 14:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='de_CH'
;

-- 2022-02-17T12:33:36.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'de_CH') 
;

-- 2022-02-17T12:33:37.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).',Updated=TO_TIMESTAMP('2022-02-17 14:33:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='de_DE'
;

-- 2022-02-17T12:33:37.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'de_DE') 
;

-- 2022-02-17T12:33:37.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580596,'de_DE') 
;

-- 2022-02-17T12:33:37.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Rohwarenpreis vs. Stücklistenkosten', Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', Help=NULL WHERE AD_Element_ID=580596
;

-- 2022-02-17T12:33:37.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Rohwarenpreis vs. Stücklistenkosten', Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', Help=NULL WHERE AD_Element_ID=580596 AND IsCentrallyMaintained='Y'
;

-- 2022-02-17T12:33:37.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rohwarenpreis vs. Stücklistenkosten', Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580596) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580596)
;

-- 2022-02-17T12:33:37.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Rohwarenpreis vs. Stücklistenkosten', Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580596
;

-- 2022-02-17T12:33:37.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Rohwarenpreis vs. Stücklistenkosten', Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', Help=NULL WHERE AD_Element_ID = 580596
;

-- 2022-02-17T12:33:37.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Rohwarenpreis vs. Stücklistenkosten', Description = 'Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580596
;

-- 2022-02-17T12:33:38.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).',Updated=TO_TIMESTAMP('2022-02-17 14:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580596 AND AD_Language='nl_NL'
;

-- 2022-02-17T12:33:38.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580596,'nl_NL') 
;

-- 2022-02-17T12:34:28.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Rohwarenpreis vs. Stücklistenkosten', Value='BOM_Product_Price_Differences',Updated=TO_TIMESTAMP('2022-02-17 14:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584994
;

-- 2022-02-17T12:34:28.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Report that provided an overview of how a manufactured product component''s current purchase price compares against their historic average purchase price.
The differences are displayed as a percentage of the average purchase price (a positive number meaning components are becoming more expensive while a negative number means they''re becoming cheaper).', IsActive='Y', Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541907
;

-- 2022-02-17T12:34:42.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584994
;

-- 2022-02-17T12:34:43.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584994
;

-- 2022-02-17T12:34:43.632Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541907
;

-- 2022-02-17T12:34:43.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584994
;

-- 2022-02-17T12:34:50.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584994
;

-- 2022-02-17T12:34:55.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Report providing an overview of the current purchase price trends of manufacturing product components compared to their historical average purchase price. The differences are displayed as a percentage of the average purchase price (a positive number means components are becoming more expensive while a negative number means that they are becoming cheaper).',Updated=TO_TIMESTAMP('2022-02-17 14:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584994
;

-- 2022-02-17T12:34:56.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).',Updated=TO_TIMESTAMP('2022-02-17 14:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584994
;

-- 2022-02-17T12:34:56.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', Help=NULL, Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584994
;

-- 2022-02-17T12:34:56.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).', IsActive='Y', Name='Rohwarenpreis vs. Stücklistenkosten',Updated=TO_TIMESTAMP('2022-02-17 14:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541907
;

-- 2022-02-17T12:34:56.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).',Updated=TO_TIMESTAMP('2022-02-17 14:34:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584994
;

-- 2022-02-17T12:35:05.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Erstellt einen Bericht über die Entwicklung des aktuellen Einkaufspreises der Komponenten von Fertigungsprodukten im Vergleich zu deren historischem Durchschnittseinkaufspreis. Die Differenzen werden als Prozentsatz des durchschnittlichen Einkaufspreises angezeigt (eine positive Zahl bedeutet, dass die Komponenten teurer werden, eine negative Zahl, dass sie günstiger werden).',Updated=TO_TIMESTAMP('2022-02-17 14:35:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584994
;

-- 2022-02-17T13:37:29.678Z
-- URL zum Konzept
UPDATE AD_Menu SET EntityType='D',Updated=TO_TIMESTAMP('2022-02-17 15:37:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541907
;

-- 2022-02-17T13:37:50.225Z
-- URL zum Konzept
UPDATE AD_Menu SET Action='R',Updated=TO_TIMESTAMP('2022-02-17 15:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541907
;
