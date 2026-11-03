-- Element: Imported
-- 2022-10-20T07:42:18.575Z
UPDATE AD_Element_Trl SET Name='Import-Zeitpunkt', PrintName='Import-Zeitpunkt',Updated=TO_TIMESTAMP('2022-10-20 10:42:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581560 AND AD_Language='de_CH'
;

-- 2022-10-20T07:42:18.624Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581560,'de_CH') 
;

-- Element: Imported
-- 2022-10-20T07:42:20.994Z
UPDATE AD_Element_Trl SET Name='Import-Zeitpunkt', PrintName='Import-Zeitpunkt',Updated=TO_TIMESTAMP('2022-10-20 10:42:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581560 AND AD_Language='de_DE'
;

-- 2022-10-20T07:42:20.995Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581560,'de_DE') 
;

-- 2022-10-20T07:42:20.999Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581560,'de_DE') 
;

-- Element: Imported
-- 2022-10-20T07:42:24.275Z
UPDATE AD_Element_Trl SET Name='Import-Zeitpunkt', PrintName='Import-Zeitpunkt',Updated=TO_TIMESTAMP('2022-10-20 10:42:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581560 AND AD_Language='nl_NL'
;

-- 2022-10-20T07:42:24.277Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581560,'nl_NL') 
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed
-- 2022-10-20T07:43:21.745Z
UPDATE AD_Message_Trl SET MsgText='Ausgewählter Datensatz ist bereits bearbeitet!',Updated=TO_TIMESTAMP('2022-10-20 10:43:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545168
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed
-- 2022-10-20T07:43:23.723Z
UPDATE AD_Message_Trl SET MsgText='Ausgewählter Datensatz ist bereits bearbeitet!',Updated=TO_TIMESTAMP('2022-10-20 10:43:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545168
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed
-- 2022-10-20T07:43:26.725Z
UPDATE AD_Message_Trl SET MsgText='Ausgewählter Datensatz ist bereits bearbeitet!',Updated=TO_TIMESTAMP('2022-10-20 10:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545168
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoStatementImported
-- 2022-10-20T07:43:58.121Z
UPDATE AD_Message_Trl SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Positionen!',Updated=TO_TIMESTAMP('2022-10-20 10:43:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545163
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoStatementImported
-- 2022-10-20T07:43:59.705Z
UPDATE AD_Message_Trl SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Positionen!',Updated=TO_TIMESTAMP('2022-10-20 10:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545163
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoStatementImported
-- 2022-10-20T07:44:01.673Z
UPDATE AD_Message_Trl SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Positionen!',Updated=TO_TIMESTAMP('2022-10-20 10:44:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545163
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.NoStatementImported
-- 2022-10-20T07:44:06.748Z
UPDATE AD_Message SET MsgText='Keine Anweisung importiert! Die Datei enthält keine gültigen Positionen!',Updated=TO_TIMESTAMP('2022-10-20 10:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545163
;

-- Value: de.metas.banking.camt53.process.C_BankStatement_Camt53_ImportAttachment.SelectedRecordIsProcessed
-- 2022-10-20T07:44:28.747Z
UPDATE AD_Message SET MsgText='Ausgewählter Datensatz ist bereits bearbeitet!',Updated=TO_TIMESTAMP('2022-10-20 10:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545168
;

-- 2022-10-20T10:28:14.055Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581588,0,'IsMatchAmounts',TO_TIMESTAMP('2022-10-20 13:28:13','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option gesetzt ist, berücksichtigt der Importprozess bei der Suche nach entsprechenden Rechnungen auch den offenen Rechnungsbetrag.','D','Y','Beträge abgleichen','Beträge abgleichen',TO_TIMESTAMP('2022-10-20 13:28:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-20T10:28:14.064Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581588 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsMatchAmounts
-- 2022-10-20T10:28:37.201Z
UPDATE AD_Element_Trl SET Description='If set, the import process will also take into consideration the invoice open amount when looking for corresponding invoices.', Name='Match Amounts', PrintName='Match Amounts',Updated=TO_TIMESTAMP('2022-10-20 13:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581588 AND AD_Language='en_US'
;

-- 2022-10-20T10:28:37.202Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581588,'en_US') 
;

-- Column: C_BankStatement_Import_File.IsMatchAmounts
-- 2022-10-20T10:29:38.849Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584775,581588,0,20,542246,'IsMatchAmounts',TO_TIMESTAMP('2022-10-20 13:29:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Wenn diese Option gesetzt ist, berücksichtigt der Importprozess bei der Suche nach entsprechenden Rechnungen auch den offenen Rechnungsbetrag.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Beträge abgleichen',0,0,TO_TIMESTAMP('2022-10-20 13:29:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-20T10:29:38.851Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584775 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-20T10:29:38.854Z
/* DDL */  select update_Column_Translation_From_AD_Element(581588) 
;

-- 2022-10-20T10:29:41.049Z
/* DDL */ SELECT public.db_alter_table('C_BankStatement_Import_File','ALTER TABLE public.C_BankStatement_Import_File ADD COLUMN IsMatchAmounts CHAR(1) DEFAULT ''Y'' CHECK (IsMatchAmounts IN (''Y'',''N'')) NOT NULL')
;

-- Field: Kontoauszug Import-Datei(541623,D) -> Kontoauszug Import-Datei(546658,D) -> Beträge abgleichen
-- Column: C_BankStatement_Import_File.IsMatchAmounts
-- 2022-10-20T10:30:14.868Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584775,707812,0,546658,TO_TIMESTAMP('2022-10-20 13:30:14','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option gesetzt ist, berücksichtigt der Importprozess bei der Suche nach entsprechenden Rechnungen auch den offenen Rechnungsbetrag.',1,'D','Y','N','N','N','N','N','N','N','Beträge abgleichen',TO_TIMESTAMP('2022-10-20 13:30:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-20T10:30:14.870Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-20T10:30:14.873Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581588) 
;

-- 2022-10-20T10:30:14.887Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707812
;

-- 2022-10-20T10:30:14.891Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707812)
;

-- UI Element: Kontoauszug Import-Datei(541623,D) -> Kontoauszug Import-Datei(546658,D) -> main -> 10 -> main.Beträge abgleichen
-- Column: C_BankStatement_Import_File.IsMatchAmounts
-- 2022-10-20T10:31:01.590Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707812,0,546658,613291,549978,'F',TO_TIMESTAMP('2022-10-20 13:31:01','YYYY-MM-DD HH24:MI:SS'),100,'Wenn diese Option gesetzt ist, berücksichtigt der Importprozess bei der Suche nach entsprechenden Rechnungen auch den offenen Rechnungsbetrag.','Y','N','N','Y','N','N','N',0,'Beträge abgleichen',12,0,0,TO_TIMESTAMP('2022-10-20 13:31:01','YYYY-MM-DD HH24:MI:SS'),100)
;
