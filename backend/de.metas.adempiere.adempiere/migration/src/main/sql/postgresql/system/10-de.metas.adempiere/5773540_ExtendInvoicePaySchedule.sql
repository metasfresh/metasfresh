
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

