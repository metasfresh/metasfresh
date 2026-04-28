
-- Column: C_InvoicePaySchedule.C_OrderPaySchedule_ID
-- 2025-10-17T14:17:13.395Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591372,584056,0,19,551,'XX','C_OrderPaySchedule_ID',TO_TIMESTAMP('2025-10-17 14:17:13.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsplan',0,0,TO_TIMESTAMP('2025-10-17 14:17:13.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-17T14:17:13.407Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591372 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-17T14:17:13.412Z
/* DDL */  select update_Column_Translation_From_AD_Element(584056)
;

-- 2025-10-17T14:17:16.725Z
/* DDL */ SELECT public.db_alter_table('C_InvoicePaySchedule','ALTER TABLE public.C_InvoicePaySchedule ADD COLUMN C_OrderPaySchedule_ID NUMERIC(10)')
;

-- 2025-10-17T14:17:16.984Z
ALTER TABLE C_InvoicePaySchedule ADD CONSTRAINT COrderPaySchedule_CInvoicePaySchedule FOREIGN KEY (C_OrderPaySchedule_ID) REFERENCES public.C_OrderPaySchedule DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_InvoicePaySchedule.C_Order_ID
-- 2025-10-17T14:17:30.486Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591373,558,0,19,551,'XX','C_Order_ID',TO_TIMESTAMP('2025-10-17 14:17:30.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Auftrag','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2025-10-17 14:17:30.239000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-17T14:17:30.492Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591373 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-17T14:17:30.496Z
/* DDL */  select update_Column_Translation_From_AD_Element(558)
;

-- 2025-10-17T14:17:31.706Z
/* DDL */ SELECT public.db_alter_table('C_InvoicePaySchedule','ALTER TABLE public.C_InvoicePaySchedule ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2025-10-17T14:17:31.832Z
ALTER TABLE C_InvoicePaySchedule ADD CONSTRAINT COrder_CInvoicePaySchedule FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_InvoicePaySchedule.C_Invoice_ID
-- 2025-10-17T14:17:38.077Z
UPDATE AD_Column SET IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-10-17 14:17:38.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8312
;

-- 2025-10-17T14:17:39.501Z
INSERT INTO t_alter_column values('c_invoicepayschedule','C_Invoice_ID','NUMERIC(10)',null,null)
;

-- 2025-10-17T14:17:39.505Z
INSERT INTO t_alter_column values('c_invoicepayschedule','C_Invoice_ID',null,'NULL',null)
;

-- Column: C_InvoicePaySchedule.C_Invoice_ID
-- 2025-10-17T14:18:22.912Z
UPDATE AD_Column SET IsUpdateable='N', MandatoryLogic='@C_Order_ID@ < 0',Updated=TO_TIMESTAMP('2025-10-17 14:18:22.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8312
;

-- Column: C_InvoicePaySchedule.C_Order_ID
-- 2025-10-17T14:18:39.790Z
UPDATE AD_Column SET MandatoryLogic='@C_Invoice_ID@ < 0',Updated=TO_TIMESTAMP('2025-10-17 14:18:39.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591373
;

-- Column: C_InvoicePaySchedule.C_OrderPaySchedule_ID
-- 2025-10-17T14:18:56.651Z
UPDATE AD_Column SET MandatoryLogic='@C_Invoice_ID@ < 0',Updated=TO_TIMESTAMP('2025-10-17 14:18:56.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591372
;

-- Column: C_Payment.C_OrderPaySchedule_ID
-- 2025-10-17T14:22:00.999Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591374,584056,0,19,335,'XX','C_OrderPaySchedule_ID',TO_TIMESTAMP('2025-10-17 14:22:00.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsplan',0,0,TO_TIMESTAMP('2025-10-17 14:22:00.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-17T14:22:01.003Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591374 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-17T14:22:01.111Z
/* DDL */  select update_Column_Translation_From_AD_Element(584056)
;

-- 2025-10-17T14:22:02.247Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN C_OrderPaySchedule_ID NUMERIC(10)')
;

-- 2025-10-17T14:22:02.479Z
ALTER TABLE C_Payment ADD CONSTRAINT COrderPaySchedule_CPayment FOREIGN KEY (C_OrderPaySchedule_ID) REFERENCES public.C_OrderPaySchedule DEFERRABLE INITIALLY DEFERRED
;


-- Name: C_PaySelectionLine.C_BP_BankAccount_ID
-- 2025-10-17T20:24:29.258Z
UPDATE AD_Val_Rule SET Code='C_BP_BankAccount.C_BPartner_ID=@C_BPartner_ID@ /* the invoice''s currency needs to match the account''s currency */ AND C_BP_BankAccount.C_Currency_ID=(select i.C_Currency_ID from C_invoice i where i.C_Invoice_ID=@C_Invoice_ID@ UNION select o.C_Currency_ID from C_Order o where o.C_Order_ID=@C_Order_ID@) AND (C_BP_BankAccount.BPBankAcctUse = ''B'' OR C_BP_BankAccount.BPBankAcctUse = ''@PaymentRule@'' /*Allow selection of NON-Standard types of usage like "Provision", because we can''t know if they make sense or not*/ OR C_BP_BankAccount.BPBankAcctUse NOT IN (''B'',''D'',''N'',''T'') )',Updated=TO_TIMESTAMP('2025-10-17 20:24:29.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540104
;

-- Column: C_InvoicePaySchedule.DiscountAmt
-- 2025-10-17T20:45:23.820Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-10-17 20:45:23.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8308
;

-- 2025-10-17T20:45:25.108Z
INSERT INTO t_alter_column values('c_invoicepayschedule','DiscountAmt','NUMERIC',null,null)
;

-- 2025-10-17T20:45:25.115Z
INSERT INTO t_alter_column values('c_invoicepayschedule','DiscountAmt',null,'NULL',null)
;

-- Column: C_InvoicePaySchedule.DiscountDate
-- 2025-10-17T20:45:31.731Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-10-17 20:45:31.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=8310
;

-- 2025-10-17T20:45:32.708Z
INSERT INTO t_alter_column values('c_invoicepayschedule','DiscountDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2025-10-17T20:45:32.710Z
INSERT INTO t_alter_column values('c_invoicepayschedule','DiscountDate',null,'NULL',null)
;



-- Tab: Eingangsrechnung(183,D) -> Zahlungsplan
-- Table: C_InvoicePaySchedule
-- 2025-10-19T13:48:52.020Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,8312,1995,0,548475,551,183,'Y',TO_TIMESTAMP('2025-10-19 13:48:51.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zahlungsplan','D','N','The Invoice Payment Schedule determines when partial payments are due.','N','A','C_InvoicePaySchedule','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Zahlungsplan',3484,'N',110,1,TO_TIMESTAMP('2025-10-19 13:48:51.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:48:52.073Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548475 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-19T13:48:52.097Z
/* DDL */  select update_tab_translation_from_ad_element(1995)
;

-- 2025-10-19T13:48:52.147Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548475)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Sektion
-- Column: C_InvoicePaySchedule.AD_Org_ID
-- 2025-10-19T13:48:57.400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8303,755038,0,548475,TO_TIMESTAMP('2025-10-19 13:48:57.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-19 13:48:57.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:48:57.433Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:48:57.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-19T13:48:58.964Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755038
;

-- 2025-10-19T13:48:58.968Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755038)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Gültig
-- Column: C_InvoicePaySchedule.IsValid
-- 2025-10-19T13:48:59.098Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8304,755039,0,548475,TO_TIMESTAMP('2025-10-19 13:48:58.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Element ist gültig',1,'D','Das Element hat die Überprüfung bestanden','Y','N','N','N','N','N','N','N','Gültig',TO_TIMESTAMP('2025-10-19 13:48:58.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:48:59.108Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:48:59.112Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2002)
;

-- 2025-10-19T13:48:59.143Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755039
;

-- 2025-10-19T13:48:59.144Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755039)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Fälliger Betrag
-- Column: C_InvoicePaySchedule.DueAmt
-- 2025-10-19T13:48:59.235Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8305,755040,0,548475,TO_TIMESTAMP('2025-10-19 13:48:59.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Betrag der fälligen Zahlung',22,'D','Voller Betrag der fälligen Zahlung.','Y','N','N','N','N','N','N','N','Fälliger Betrag',TO_TIMESTAMP('2025-10-19 13:48:59.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:48:59.239Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:48:59.242Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1999)
;

-- 2025-10-19T13:48:59.267Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755040
;

-- 2025-10-19T13:48:59.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755040)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Datum Fälligkeit
-- Column: C_InvoicePaySchedule.DueDate
-- 2025-10-19T13:48:59.373Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8306,755041,0,548475,TO_TIMESTAMP('2025-10-19 13:48:59.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem Zahlung fällig wird',7,'D','Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','N','N','N','N','N','N','N','Datum Fälligkeit',TO_TIMESTAMP('2025-10-19 13:48:59.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:48:59.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:48:59.380Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2000)
;

-- 2025-10-19T13:48:59.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755041
;

-- 2025-10-19T13:48:59.393Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755041)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Skonto
-- Column: C_InvoicePaySchedule.DiscountAmt
-- 2025-10-19T13:48:59.491Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8308,755042,0,548475,TO_TIMESTAMP('2025-10-19 13:48:59.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Calculated amount of discount',22,'D','The Discount Amount indicates the discount amount for a document or line.','Y','N','N','N','N','N','N','N','Skonto',TO_TIMESTAMP('2025-10-19 13:48:59.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:48:59.513Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:48:59.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1395)
;

-- 2025-10-19T13:48:59.531Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755042
;

-- 2025-10-19T13:48:59.534Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755042)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Mandant
-- Column: C_InvoicePaySchedule.AD_Client_ID
-- 2025-10-19T13:48:59.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8309,755043,0,548475,TO_TIMESTAMP('2025-10-19 13:48:59.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-19 13:48:59.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:48:59.664Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:48:59.667Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-19T13:49:00.483Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755043
;

-- 2025-10-19T13:49:00.485Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755043)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Skontodatum
-- Column: C_InvoicePaySchedule.DiscountDate
-- 2025-10-19T13:49:00.591Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8310,755044,0,548475,TO_TIMESTAMP('2025-10-19 13:49:00.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Last Date for payments with discount',7,'D','Last Date where a deduction of the payment discount is allowed','Y','N','N','N','N','N','N','N','Skontodatum',TO_TIMESTAMP('2025-10-19 13:49:00.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:00.603Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:00.605Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1998)
;

-- 2025-10-19T13:49:00.610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755044
;

-- 2025-10-19T13:49:00.611Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755044)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Rechnung
-- Column: C_InvoicePaySchedule.C_Invoice_ID
-- 2025-10-19T13:49:00.713Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8312,755045,0,548475,TO_TIMESTAMP('2025-10-19 13:49:00.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Invoice Identifier',22,'D','The Invoice Document.','Y','N','N','N','N','N','N','N','Rechnung',TO_TIMESTAMP('2025-10-19 13:49:00.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:00.732Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:00.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008)
;

-- 2025-10-19T13:49:00.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755045
;

-- 2025-10-19T13:49:00.785Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755045)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Aktiv
-- Column: C_InvoicePaySchedule.IsActive
-- 2025-10-19T13:49:00.875Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8313,755046,0,548475,TO_TIMESTAMP('2025-10-19 13:49:00.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-19 13:49:00.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:00.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:00.898Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-19T13:49:01.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755046
;

-- 2025-10-19T13:49:01.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755046)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Zahlungsplan
-- Column: C_InvoicePaySchedule.C_InvoicePaySchedule_ID
-- 2025-10-19T13:49:01.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8314,755047,0,548475,TO_TIMESTAMP('2025-10-19 13:49:01.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zahlungsplan',22,'D','The Invoice Payment Schedule determines when partial payments are due.','Y','N','N','N','N','N','N','N','Zahlungsplan',TO_TIMESTAMP('2025-10-19 13:49:01.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:01.653Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:01.655Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1995)
;

-- 2025-10-19T13:49:01.662Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755047
;

-- 2025-10-19T13:49:01.663Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755047)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Zahlungsplan
-- Column: C_InvoicePaySchedule.C_PaySchedule_ID
-- 2025-10-19T13:49:01.765Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,8315,755048,0,548475,TO_TIMESTAMP('2025-10-19 13:49:01.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Payment Schedule Template',22,'D','Information when parts of the payment are due','Y','N','N','N','N','N','N','N','Zahlungsplan',TO_TIMESTAMP('2025-10-19 13:49:01.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:01.769Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:01.771Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1996)
;

-- 2025-10-19T13:49:01.774Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755048
;

-- 2025-10-19T13:49:01.776Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755048)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Process Now
-- Column: C_InvoicePaySchedule.Processing
-- 2025-10-19T13:49:01.891Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,10324,755049,0,548475,TO_TIMESTAMP('2025-10-19 13:49:01.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2025-10-19 13:49:01.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:01.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:01.916Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524)
;

-- 2025-10-19T13:49:01.990Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755049
;

-- 2025-10-19T13:49:01.992Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755049)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Verarbeitet
-- Column: C_InvoicePaySchedule.Processed
-- 2025-10-19T13:49:02.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,10570,755050,0,548475,TO_TIMESTAMP('2025-10-19 13:49:01.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2025-10-19 13:49:01.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:02.097Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:02.100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2025-10-19T13:49:02.166Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755050
;

-- 2025-10-19T13:49:02.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755050)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Zahlungsplan
-- Column: C_InvoicePaySchedule.C_OrderPaySchedule_ID
-- 2025-10-19T13:49:02.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591372,755051,0,548475,TO_TIMESTAMP('2025-10-19 13:49:02.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Zahlungsplan',TO_TIMESTAMP('2025-10-19 13:49:02.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:02.270Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:02.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584056)
;

-- 2025-10-19T13:49:02.280Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755051
;

-- 2025-10-19T13:49:02.281Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755051)
;

-- Field: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> Auftrag
-- Column: C_InvoicePaySchedule.C_Order_ID
-- 2025-10-19T13:49:02.399Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591373,755052,0,548475,TO_TIMESTAMP('2025-10-19 13:49:02.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2025-10-19 13:49:02.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-19T13:49:02.422Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-19T13:49:02.434Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558)
;

-- 2025-10-19T13:49:02.495Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755052
;

-- 2025-10-19T13:49:02.497Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755052)
;

-- Tab: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D)
-- UI Section: MAIN
-- 2025-10-19T13:49:15.665Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548475,547002,TO_TIMESTAMP('2025-10-19 13:49:15.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-19 13:49:15.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MAIN')
;

-- 2025-10-19T13:49:15.690Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547002 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN
-- UI Column: 10
-- 2025-10-19T13:49:22.315Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548536,547002,TO_TIMESTAMP('2025-10-19 13:49:22.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-19 13:49:22.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10
-- UI Element Group: main
-- 2025-10-19T13:49:35.099Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548536,553648,TO_TIMESTAMP('2025-10-19 13:49:34.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-19 13:49:34.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Auftrag
-- Column: C_InvoicePaySchedule.C_Order_ID
-- 2025-10-19T13:50:07.158Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755052,0,548475,553648,637909,'F',TO_TIMESTAMP('2025-10-19 13:50:06.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Auftrag',10,0,0,TO_TIMESTAMP('2025-10-19 13:50:06.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Zahlungsplan
-- Column: C_InvoicePaySchedule.C_InvoicePaySchedule_ID
-- 2025-10-19T13:50:16.946Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755047,0,548475,553648,637910,'F',TO_TIMESTAMP('2025-10-19 13:50:16.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zahlungsplan','The Invoice Payment Schedule determines when partial payments are due.','Y','N','Y','N','N','Zahlungsplan',20,0,0,TO_TIMESTAMP('2025-10-19 13:50:16.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Datum Fälligkeit
-- Column: C_InvoicePaySchedule.DueDate
-- 2025-10-19T13:50:44.893Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755041,0,548475,553648,637911,'F',TO_TIMESTAMP('2025-10-19 13:50:44.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem Zahlung fällig wird','Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','N','Y','N','N','Datum Fälligkeit',30,0,0,TO_TIMESTAMP('2025-10-19 13:50:44.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Fälliger Betrag
-- Column: C_InvoicePaySchedule.DueAmt
-- 2025-10-19T13:50:54.488Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755040,0,548475,553648,637912,'F',TO_TIMESTAMP('2025-10-19 13:50:54.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Betrag der fälligen Zahlung','Voller Betrag der fälligen Zahlung.','Y','N','Y','N','N','Fälliger Betrag',40,0,0,TO_TIMESTAMP('2025-10-19 13:50:54.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Skontodatum
-- Column: C_InvoicePaySchedule.DiscountDate
-- 2025-10-19T13:51:03.228Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755044,0,548475,553648,637913,'F',TO_TIMESTAMP('2025-10-19 13:51:03.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Last Date for payments with discount','Last Date where a deduction of the payment discount is allowed','Y','N','Y','N','N','Skontodatum',50,0,0,TO_TIMESTAMP('2025-10-19 13:51:03.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Skonto
-- Column: C_InvoicePaySchedule.DiscountAmt
-- 2025-10-19T13:51:12.328Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755042,0,548475,553648,637914,'F',TO_TIMESTAMP('2025-10-19 13:51:12.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Calculated amount of discount','The Discount Amount indicates the discount amount for a document or line.','Y','N','Y','N','N','Skonto',60,0,0,TO_TIMESTAMP('2025-10-19 13:51:12.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN
-- UI Column: 20
-- 2025-10-19T13:51:40.327Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548537,547002,TO_TIMESTAMP('2025-10-19 13:51:40.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-19 13:51:40.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20
-- UI Element Group: main
-- 2025-10-19T13:51:52.707Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548537,553649,TO_TIMESTAMP('2025-10-19 13:51:52.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-19 13:51:52.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Aktiv
-- Column: C_InvoicePaySchedule.IsActive
-- 2025-10-19T13:52:04.141Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755046,0,548475,553649,637915,'F',TO_TIMESTAMP('2025-10-19 13:52:03.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2025-10-19 13:52:03.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Verarbeitet
-- Column: C_InvoicePaySchedule.Processed
-- 2025-10-19T13:52:15.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755050,0,548475,553649,637916,'F',TO_TIMESTAMP('2025-10-19 13:52:15.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',20,0,0,TO_TIMESTAMP('2025-10-19 13:52:15.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Sektion
-- Column: C_InvoicePaySchedule.AD_Org_ID
-- 2025-10-19T13:52:26.282Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755038,0,548475,553649,637917,'F',TO_TIMESTAMP('2025-10-19 13:52:26.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',30,0,0,TO_TIMESTAMP('2025-10-19 13:52:26.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Mandant
-- Column: C_InvoicePaySchedule.AD_Client_ID
-- 2025-10-19T13:52:34.996Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755043,0,548475,553649,637918,'F',TO_TIMESTAMP('2025-10-19 13:52:34.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',40,0,0,TO_TIMESTAMP('2025-10-19 13:52:34.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Auftrag
-- Column: C_InvoicePaySchedule.C_Order_ID
-- 2025-10-19T13:53:07.077Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-19 13:53:07.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637909
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Fälliger Betrag
-- Column: C_InvoicePaySchedule.DueAmt
-- 2025-10-19T13:53:07.087Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-19 13:53:07.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637912
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Datum Fälligkeit
-- Column: C_InvoicePaySchedule.DueDate
-- 2025-10-19T13:53:07.098Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-19 13:53:07.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637911
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Sektion
-- Column: C_InvoicePaySchedule.AD_Org_ID
-- 2025-10-19T13:53:07.108Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-19 13:53:07.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637917
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Mandant
-- Column: C_InvoicePaySchedule.AD_Client_ID
-- 2025-10-19T13:53:07.117Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-19 13:53:07.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637918
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Zahlungsplan
-- Column: C_InvoicePaySchedule.C_OrderPaySchedule_ID
-- 2025-10-19T13:53:33.109Z
UPDATE AD_UI_Element SET AD_Field_ID=755051, Description=NULL, Help=NULL,Updated=TO_TIMESTAMP('2025-10-19 13:53:33.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637910
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Zahlungsplan
-- Column: C_InvoicePaySchedule.C_OrderPaySchedule_ID
-- 2025-10-19T13:53:44.739Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-19 13:53:44.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637910
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Fälliger Betrag
-- Column: C_InvoicePaySchedule.DueAmt
-- 2025-10-19T13:53:44.757Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-19 13:53:44.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637912
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 10 -> main.Datum Fälligkeit
-- Column: C_InvoicePaySchedule.DueDate
-- 2025-10-19T13:53:44.777Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-19 13:53:44.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637911
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Sektion
-- Column: C_InvoicePaySchedule.AD_Org_ID
-- 2025-10-19T13:53:44.797Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-19 13:53:44.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637917
;

-- UI Element: Eingangsrechnung(183,D) -> Zahlungsplan(548475,D) -> MAIN -> 20 -> main.Mandant
-- Column: C_InvoicePaySchedule.AD_Client_ID
-- 2025-10-19T13:53:44.813Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-19 13:53:44.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637918
;

