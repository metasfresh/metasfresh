-- 2023-02-13T16:02:17.901Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582064,0,'IsAllowOverrideDueDate',TO_TIMESTAMP('2023-02-13 18:02:17','YYYY-MM-DD HH24:MI:SS'),100,'When this flag is checked, then during invoicing the due date that would be computed using this payment term can be overridden','D','Y','Allow override due date','Allow override due date',TO_TIMESTAMP('2023-02-13 18:02:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T16:02:17.905Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582064 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_PaymentTerm.IsAllowOverrideDueDate
-- 2023-02-13T16:13:42.234Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586044,582064,0,20,113,'IsAllowOverrideDueDate',TO_TIMESTAMP('2023-02-13 18:13:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','When this flag is checked, then during invoicing the due date that would be computed using this payment term can be overridden','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow override due date',0,0,TO_TIMESTAMP('2023-02-13 18:13:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-13T16:13:42.235Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586044 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-13T16:13:42.261Z
/* DDL */  select update_Column_Translation_From_AD_Element(582064) 
;

-- 2023-02-13T16:13:45.412Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm ADD COLUMN IsAllowOverrideDueDate CHAR(1) DEFAULT ''N'' CHECK (IsAllowOverrideDueDate IN (''Y'',''N'')) NOT NULL')
;

-- Field: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> Allow override due date
-- Column: C_PaymentTerm.IsAllowOverrideDueDate
-- 2023-02-13T16:31:14.626Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586044,712376,0,184,0,TO_TIMESTAMP('2023-02-13 18:31:14','YYYY-MM-DD HH24:MI:SS'),100,'When this flag is checked, then during invoicing the due date that would be computed using this payment term can be overridden',0,'D',0,'Y','Y','Y','N','N','N','N','N','Allow override due date',0,270,0,1,1,TO_TIMESTAMP('2023-02-13 18:31:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T16:31:14.627Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712376 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-13T16:31:14.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582064) 
;

-- 2023-02-13T16:31:14.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712376
;

-- 2023-02-13T16:31:14.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712376)
;

-- UI Element: Zahlungsbedingung(141,D) -> Zahlungsbedingung(184,D) -> main -> 20 -> flags.Allow override due date
-- Column: C_PaymentTerm.IsAllowOverrideDueDate
-- 2023-02-13T16:31:36.089Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712376,0,184,540544,615725,'F',TO_TIMESTAMP('2023-02-13 18:31:35','YYYY-MM-DD HH24:MI:SS'),100,'When this flag is checked, then during invoicing the due date that would be computed using this payment term can be overridden','Y','N','N','Y','N','N','N',0,'Allow override due date',50,0,0,TO_TIMESTAMP('2023-02-13 18:31:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: OverrideDueDate
-- 2023-02-13T16:33:04.420Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582059,0,540304,542548,15,'OverrideDueDate',TO_TIMESTAMP('2023-02-13 18:33:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate',0,'Y','N','Y','N','N','N','Fälligkeitsdatum abw.',100,TO_TIMESTAMP('2023-02-13 18:33:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-13T16:33:04.422Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542548 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Element: IsAllowOverrideDueDate
-- 2023-02-14T11:07:03.516Z
UPDATE AD_Element_Trl SET Description='If ticked, the due date determined at the time of invoicing based on this payment term can be overridden.', IsTranslated='Y', Name='Allow overriding due date', PrintName='Allow overriding due date',Updated=TO_TIMESTAMP('2023-02-14 13:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582064 AND AD_Language='en_US'
;

-- 2023-02-14T11:07:03.519Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582064,'en_US') 
;

-- Element: IsAllowOverrideDueDate
-- 2023-02-14T11:07:12.248Z
UPDATE AD_Element_Trl SET Name='Allow overriding due date', PrintName='Allow overriding due date',Updated=TO_TIMESTAMP('2023-02-14 13:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582064 AND AD_Language='fr_CH'
;

-- 2023-02-14T11:07:12.250Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582064,'fr_CH') 
;

-- Element: IsAllowOverrideDueDate
-- 2023-02-14T11:07:40.450Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, kann das bei Rechnungsstellung auf Grundlage dieser Zahlungsbedingung ermittelte Fälligkeitsdatum überschrieben werden.', IsTranslated='Y', Name='Überschreiben des Fälligkeitsdatums zulassen', PrintName='Überschreiben des Fälligkeitsdatums zulassen',Updated=TO_TIMESTAMP('2023-02-14 13:07:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582064 AND AD_Language='de_DE'
;

-- 2023-02-14T11:07:40.451Z
UPDATE AD_Element SET Description='Wenn angehakt, kann das bei Rechnungsstellung auf Grundlage dieser Zahlungsbedingung ermittelte Fälligkeitsdatum überschrieben werden.', Name='Überschreiben des Fälligkeitsdatums zulassen', PrintName='Überschreiben des Fälligkeitsdatums zulassen' WHERE AD_Element_ID=582064
;

-- 2023-02-14T11:07:40.977Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582064,'de_DE') 
;

-- 2023-02-14T11:07:40.978Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582064,'de_DE') 
;

-- Element: IsAllowOverrideDueDate
-- 2023-02-14T11:07:54.226Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, kann das bei Rechnungsstellung auf Grundlage dieser Zahlungsbedingung ermittelte Fälligkeitsdatum überschrieben werden.', IsTranslated='Y', Name='Überschreiben des Fälligkeitsdatums zulassen', PrintName='Überschreiben des Fälligkeitsdatums zulassen',Updated=TO_TIMESTAMP('2023-02-14 13:07:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582064 AND AD_Language='de_CH'
;

-- 2023-02-14T11:07:54.229Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582064,'de_CH') 
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: OverrideDueDate
-- 2023-02-14T13:03:00.250Z
UPDATE AD_Process_Para SET DefaultValue='@DateInvoiced@',Updated=TO_TIMESTAMP('2023-02-14 15:03:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542548
;

-- -- seems to exists already
-- -- Field: Rechnung(167,D) -> Rechnung(263,D) -> Datum Fälligkeit
-- -- Column: C_Invoice.DueDate
-- -- 2023-02-14T16:06:34.504Z
-- INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584270,712526,0,263,0,TO_TIMESTAMP('2023-02-14 18:06:34','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem Zahlung fällig wird',0,'D','Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.',0,'Y','Y','Y','N','N','N','N','N','Datum Fälligkeit',0,520,0,1,1,TO_TIMESTAMP('2023-02-14 18:06:34','YYYY-MM-DD HH24:MI:SS'),100)
-- ;

-- -- 2023-02-14T16:06:34.508Z
-- INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712526 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
-- ;

-- -- 2023-02-14T16:06:34.509Z
-- /* DDL */  select update_FieldTranslation_From_AD_Name_Element(2000) 
-- ;

-- -- 2023-02-14T16:06:34.514Z
-- DELETE FROM AD_Element_Link WHERE AD_Field_ID=712526
-- ;

-- -- 2023-02-14T16:06:34.515Z
-- /* DDL */ select AD_Element_Link_Create_Missing_Field(712526)
-- ;

-- -- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Datum Fälligkeit
-- -- Column: C_Invoice.DueDate
-- -- 2023-02-14T16:07:22.674Z
-- INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712526,0,263,541214,615817,'F',TO_TIMESTAMP('2023-02-14 18:07:22','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem Zahlung fällig wird','Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','Y','N','Y','N','N','N','Datum Fälligkeit',15,0,0,TO_TIMESTAMP('2023-02-14 18:07:22','YYYY-MM-DD HH24:MI:SS'),100)
-- ;


-- Value: C_Invoice_Candidate_EnqueueSelectionForInvoicing
-- Classname: de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing
-- 2023-02-14T17:00:11.567Z
UPDATE AD_Process SET Help='Override due date field will be used only when the corresponding payment term has flag Allow override due date set on Yes.',Updated=TO_TIMESTAMP('2023-02-14 19:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540304
;

-- 2023-02-14T17:00:11.571Z
UPDATE AD_Process_Trl trl SET Help='Override due date field will be used only when the corresponding payment term has flag Allow override due date set on Yes.' WHERE AD_Process_ID=540304 AND AD_Language='de_DE'
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- 2023-02-14T17:00:28.031Z
UPDATE AD_Process_Trl SET Description='Override due date field will be used only when the corresponding payment term has flag Allow override due date set on Yes.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-14 19:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Process_ID=540304
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- 2023-02-14T17:00:35.781Z
UPDATE AD_Process_Trl SET Description='Override due date field will be used only when the corresponding payment term has flag Allow override due date set on Yes.',Updated=TO_TIMESTAMP('2023-02-14 19:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540304
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- 2023-02-14T17:01:10.583Z
UPDATE AD_Process_Trl SET Help='Das Feld „Überschriebenes Fälligkeitsdatum“ wird nur verwendet, wenn für die entsprechende Zahlungsbedingung das Flag „Überschreiben des Fälligkeitsdatums zulassen“ auf „Ja“ gesetzt ist.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-14 19:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540304
;

-- 2023-02-14T17:01:10.584Z
UPDATE AD_Process SET Help='Das Feld „Überschriebenes Fälligkeitsdatum“ wird nur verwendet, wenn für die entsprechende Zahlungsbedingung das Flag „Überschreiben des Fälligkeitsdatums zulassen“ auf „Ja“ gesetzt ist.' WHERE AD_Process_ID=540304
;

-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- 2023-02-14T17:01:17.391Z
UPDATE AD_Process_Trl SET Description='Das Feld „Überschriebenes Fälligkeitsdatum“ wird nur verwendet, wenn für die entsprechende Zahlungsbedingung das Flag „Überschreiben des Fälligkeitsdatums zulassen“ auf „Ja“ gesetzt ist.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-14 19:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540304
;


-- Process: C_Invoice_Candidate_EnqueueSelectionForInvoicing(de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing)
-- ParameterName: OverrideDueDate
-- 2023-02-14T17:29:08.624Z
UPDATE AD_Process_Para SET DefaultValue='',Updated=TO_TIMESTAMP('2023-02-14 19:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542548
;

