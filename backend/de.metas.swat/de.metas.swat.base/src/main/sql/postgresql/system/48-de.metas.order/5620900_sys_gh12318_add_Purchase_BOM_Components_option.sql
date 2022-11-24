-- 2022-01-10T14:14:18.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580471,0,TO_TIMESTAMP('2022-01-10 16:14:18','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann werden für alle Auftragszeilen mit Stücklistenprodukt die entsprechenden Komponenten bzw. Subkomponenten als Bestellzeilen hinzugefügt. Alle anderen Auftragszeilen werden ignoriert.','D','Y','Stücklisten-Komponenten bestellen','Stücklisten-Komponenten bestellen',TO_TIMESTAMP('2022-01-10 16:14:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-10T14:14:18.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580471 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-10T14:14:36.972Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If ticked, then for all sales order lines whose product has a BOM, the corresponding components are added as purchase order lines. All other sales order lines are ignored.', Name='Purchase BOM Components', PrintName='Purchase BOM Components',Updated=TO_TIMESTAMP('2022-01-10 16:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580471 AND AD_Language='en_US'
;

-- 2022-01-10T14:14:37.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580471,'en_US') 
;

-- 2022-01-10T14:24:50.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName=NULL,Updated=TO_TIMESTAMP('2022-01-10 16:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580471
;

-- 2022-01-10T14:24:50.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Stücklisten-Komponenten bestellen', Description='Wenn angehakt, dann werden für alle Auftragszeilen mit Stücklistenprodukt die entsprechenden Komponenten bzw. Subkomponenten als Bestellzeilen hinzugefügt. Alle anderen Auftragszeilen werden ignoriert.', Help=NULL WHERE AD_Element_ID=580471
;

-- 2022-01-10T14:24:50.351Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Stücklisten-Komponenten bestellen', Description='Wenn angehakt, dann werden für alle Auftragszeilen mit Stücklistenprodukt die entsprechenden Komponenten bzw. Subkomponenten als Bestellzeilen hinzugefügt. Alle anderen Auftragszeilen werden ignoriert.', Help=NULL WHERE AD_Element_ID=580471 AND IsCentrallyMaintained='Y'
;

-- 2022-01-10T14:56:11.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,580471,0,193,542168,20,'purchaseBOMComponents',TO_TIMESTAMP('2022-01-10 16:56:11','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann werden für alle Auftragszeilen mit Stücklistenprodukt die entsprechenden Komponenten bzw. Subkomponenten als Bestellzeilen hinzugefügt. Alle anderen Auftragszeilen werden ignoriert.','de.metas.order',1,'Y','N','Y','N','N','N','Stücklisten-Komponenten bestellen',100,TO_TIMESTAMP('2022-01-10 16:56:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-10T14:56:11.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542168 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-01-10T14:59:26.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='IsPurchaseBOMComponents',Updated=TO_TIMESTAMP('2022-01-10 16:59:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580471
;

-- 2022-01-10T14:59:26.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsPurchaseBOMComponents', Name='Stücklisten-Komponenten bestellen', Description='Wenn angehakt, dann werden für alle Auftragszeilen mit Stücklistenprodukt die entsprechenden Komponenten bzw. Subkomponenten als Bestellzeilen hinzugefügt. Alle anderen Auftragszeilen werden ignoriert.', Help=NULL WHERE AD_Element_ID=580471
;

-- 2022-01-10T14:59:26.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPurchaseBOMComponents', Name='Stücklisten-Komponenten bestellen', Description='Wenn angehakt, dann werden für alle Auftragszeilen mit Stücklistenprodukt die entsprechenden Komponenten bzw. Subkomponenten als Bestellzeilen hinzugefügt. Alle anderen Auftragszeilen werden ignoriert.', Help=NULL, AD_Element_ID=580471 WHERE UPPER(ColumnName)='ISPURCHASEBOMCOMPONENTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-01-10T14:59:26.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsPurchaseBOMComponents', Name='Stücklisten-Komponenten bestellen', Description='Wenn angehakt, dann werden für alle Auftragszeilen mit Stücklistenprodukt die entsprechenden Komponenten bzw. Subkomponenten als Bestellzeilen hinzugefügt. Alle anderen Auftragszeilen werden ignoriert.', Help=NULL WHERE AD_Element_ID=580471 AND IsCentrallyMaintained='Y'
;

