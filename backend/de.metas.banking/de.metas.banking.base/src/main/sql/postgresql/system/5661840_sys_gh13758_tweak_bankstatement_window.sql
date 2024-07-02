-- Value: C_BankStatement_Import_File_Camt53_ImportAttachment
-- Classname: de.metas.banking.camt53.process.C_BankStatement_Import_File_Camt53_ImportAttachment
-- 2022-10-25T09:54:55.180Z
UPDATE AD_Process SET Classname='de.metas.banking.camt53.process.C_BankStatement_Import_File_Camt53_ImportAttachment', Value='C_BankStatement_Import_File_Camt53_ImportAttachment',Updated=TO_TIMESTAMP('2022-10-25 11:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585129
;

-- 2022-10-25T09:58:55.551Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581605,0,'IsUpdateAmountsFromInvoice',TO_TIMESTAMP('2022-10-25 11:58:55','YYYY-MM-DD HH24:MI:SS'),100,'If Y and an invoice is assigned, then the satement amount and transaction amount are set to the invoice''s respective open amount','D','Y','Update Amounts from invoice','Update Amounts from invoice',TO_TIMESTAMP('2022-10-25 11:58:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T09:58:55.558Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581605 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:00:01.756Z
UPDATE AD_Element_Trl SET Name='Beträge von Rechnung übernehmen', PrintName='Beträge von Rechnung übernehmen',Updated=TO_TIMESTAMP('2022-10-25 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='de_CH'
;

-- 2022-10-25T10:00:01.796Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'de_CH') 
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:00:07.942Z
UPDATE AD_Element_Trl SET Description='If Y and an invoice is assigned, then the satement amount and transaction amount are set to be the invoice''s respective open amount',Updated=TO_TIMESTAMP('2022-10-25 12:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='en_US'
;

-- 2022-10-25T10:00:07.947Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'en_US') 
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:00:11.206Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 12:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='en_US'
;

-- 2022-10-25T10:00:11.211Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'en_US') 
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:01:37.643Z
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und eine Rechnung wird zugeordnet, dann werden der Kontoauszug- und Bewegungsbetrag auf den offenen Betrag der Rechnung gesetzt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-10-25 12:01:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='de_CH'
;

-- 2022-10-25T10:01:37.648Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'de_CH') 
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:01:41.208Z
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und eine Rechnung wird zugeordnet, dann werden der Kontoauszug- und Bewegungsbetrag auf den offenen Betrag der Rechnung gesetzt.',Updated=TO_TIMESTAMP('2022-10-25 12:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='de_DE'
;

-- 2022-10-25T10:01:41.216Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581605,'de_DE') 
;

-- 2022-10-25T10:01:41.219Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'de_DE') 
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:02:16.323Z
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und eine Rechnung wird zugeordnet, dann werden der Kontoauszug- und Bewegungsbetrag sowie die Währung vom offenen Betrag der Rechnung übernommen.', IsTranslated='Y', Name='Beträge von Rechnung übernehmen', PrintName='Beträge von Rechnung übernehmen',Updated=TO_TIMESTAMP('2022-10-25 12:02:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='de_DE'
;

-- 2022-10-25T10:02:16.334Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581605,'de_DE') 
;

-- 2022-10-25T10:02:16.339Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'de_DE') 
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:02:19.066Z
UPDATE AD_Element_Trl SET Description='Wenn gesetzt und eine Rechnung wird zugeordnet, dann werden der Kontoauszug- und Bewegungsbetrag sowie die Währung vom offenen Betrag der Rechnung übernommen.',Updated=TO_TIMESTAMP('2022-10-25 12:02:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='de_CH'
;

-- 2022-10-25T10:02:19.070Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'de_CH') 
;

-- Element: IsUpdateAmountsFromInvoice
-- 2022-10-25T10:02:41.072Z
UPDATE AD_Element_Trl SET Description='If set and an invoice is assigned, then the bank statement and transaction amount as well as the currency will be taken from the open amount of the invoice.',Updated=TO_TIMESTAMP('2022-10-25 12:02:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581605 AND AD_Language='en_US'
;

-- 2022-10-25T10:02:41.076Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581605,'en_US') 
;

-- Column: C_BankStatementLine.IsUpdateAmountsFromInvoice
-- 2022-10-25T10:03:35.016Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,584803,581605,0,20,393,'IsUpdateAmountsFromInvoice',TO_TIMESTAMP('2022-10-25 12:03:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Wenn gesetzt und eine Rechnung wird zugeordnet, dann werden der Kontoauszug- und Bewegungsbetrag sowie die Währung vom offenen Betrag der Rechnung übernommen.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Beträge von Rechnung übernehmen','',0,0,'Default=Y for backwards-compatibility.',TO_TIMESTAMP('2022-10-25 12:03:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T10:03:35.021Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584803 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T10:03:35.032Z
/* DDL */  select update_Column_Translation_From_AD_Element(581605) 
;

-- 2022-10-25T10:03:37.693Z
/* DDL */ SELECT public.db_alter_table('C_BankStatementLine','ALTER TABLE public.C_BankStatementLine ADD COLUMN IsUpdateAmountsFromInvoice CHAR(1) DEFAULT ''Y'' CHECK (IsUpdateAmountsFromInvoice IN (''Y'',''N'')) NOT NULL')
;

-- Table: C_BankStatementLine
-- 2022-10-25T10:04:17.725Z
UPDATE AD_Table SET Name='C_BankStatementLine',Updated=TO_TIMESTAMP('2022-10-25 12:04:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=393
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Referenznummer
-- Column: C_BankStatementLine.ReferenceNo
-- 2022-10-25T10:23:08.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-10-25 12:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543112
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Beschreibung
-- Column: C_BankStatementLine.Description
-- 2022-10-25T10:23:08.796Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-10-25 12:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543093
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Memo
-- Column: C_BankStatementLine.Memo
-- 2022-10-25T10:23:08.808Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-10-25 12:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543113
;

-- Field: Bankauszug(194,D) -> Auszugs-Position(329,D) -> Beträge von Rechnung übernehmen
-- Column: C_BankStatementLine.IsUpdateAmountsFromInvoice
-- 2022-10-25T10:23:38.255Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584803,707859,0,329,0,TO_TIMESTAMP('2022-10-25 12:23:38','YYYY-MM-DD HH24:MI:SS'),100,'Wenn gesetzt und eine Rechnung wird zugeordnet, dann werden der Kontoauszug- und Bewegungsbetrag sowie die Währung vom offenen Betrag der Rechnung übernommen.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Beträge von Rechnung übernehmen',0,360,0,1,1,TO_TIMESTAMP('2022-10-25 12:23:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T10:23:38.257Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T10:23:38.265Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581605) 
;

-- 2022-10-25T10:23:38.279Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707859
;

-- 2022-10-25T10:23:38.286Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707859)
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Beträge von Rechnung übernehmen
-- Column: C_BankStatementLine.IsUpdateAmountsFromInvoice
-- 2022-10-25T10:24:29.774Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707859,0,329,540272,613301,'F',TO_TIMESTAMP('2022-10-25 12:24:29','YYYY-MM-DD HH24:MI:SS'),100,'Wenn gesetzt und eine Rechnung wird zugeordnet, dann werden der Kontoauszug- und Bewegungsbetrag sowie die Währung vom offenen Betrag der Rechnung übernommen.','Y','N','N','Y','N','N','N',0,'Beträge von Rechnung übernehmen',102,0,0,TO_TIMESTAMP('2022-10-25 12:24:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Bankauszug(194,D) -> Auszugs-Position(329,D) -> main -> 10 -> default.Rechnung
-- Column: C_BankStatementLine.C_Invoice_ID
-- 2022-10-25T10:25:09.392Z
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2022-10-25 12:25:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543116
;

-- Table: C_BankStatement_Import_File
-- 2022-10-25T10:26:09.564Z
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2022-10-25 12:26:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- Table: C_BankStatement_Import_File
-- 2022-10-25T10:26:14.979Z
UPDATE AD_Table SET Name='C_BankStatement',Updated=TO_TIMESTAMP('2022-10-25 12:26:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- Table: C_BankStatement_Import_File
-- 2022-10-25T10:26:17.892Z
UPDATE AD_Table SET Name='C_BankStatement_Import_File',Updated=TO_TIMESTAMP('2022-10-25 12:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

