-- Run mode: SWING_CLIENT

-- Column: C_PaySelectionLine.C_Order_ID
-- 2025-10-16T13:07:13.391Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591353,558,0,19,427,'XX','C_Order_ID',TO_TIMESTAMP('2025-10-16 13:07:13.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Auftrag','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag',0,0,TO_TIMESTAMP('2025-10-16 13:07:13.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-16T13:07:13.403Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591353 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-16T13:07:13.437Z
/* DDL */  select update_Column_Translation_From_AD_Element(558)
;

-- 2025-10-16T13:07:24.950Z
/* DDL */ SELECT public.db_alter_table('C_PaySelectionLine','ALTER TABLE public.C_PaySelectionLine ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2025-10-16T13:07:25.233Z
ALTER TABLE C_PaySelectionLine ADD CONSTRAINT COrder_CPaySelectionLine FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_PaySelectionLine.C_Invoice_ID
-- 2025-10-16T13:07:31.946Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-10-16 13:07:31.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=5639
;

-- Field: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> Auszugsposition
-- Column: C_PaySelectionLine.C_BankStatementLine_ID
-- 2025-10-16T13:09:33.423Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551690,754990,0,353,TO_TIMESTAMP('2025-10-16 13:09:33.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position auf einem Bankauszug zu dieser Bank',10,'D','Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.','Y','N','N','N','N','N','N','N','Auszugsposition',TO_TIMESTAMP('2025-10-16 13:09:33.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T13:09:33.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T13:09:33.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1382)
;

-- 2025-10-16T13:09:33.452Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754990
;

-- 2025-10-16T13:09:33.462Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754990)
;

-- Field: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> Bankauszugszeile Referenz
-- Column: C_PaySelectionLine.C_BankStatementLine_Ref_ID
-- 2025-10-16T13:09:33.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552601,754991,0,353,TO_TIMESTAMP('2025-10-16 13:09:33.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Bankauszugszeile Referenz',TO_TIMESTAMP('2025-10-16 13:09:33.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T13:09:33.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T13:09:33.577Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53355)
;

-- 2025-10-16T13:09:33.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754991
;

-- 2025-10-16T13:09:33.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754991)
;

-- Field: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> Has Open Outgoing payments
-- Column: C_PaySelectionLine.HasOpenOutgoingPayments
-- 2025-10-16T13:09:33.691Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552758,754992,0,353,TO_TIMESTAMP('2025-10-16 13:09:33.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Has Open Outgoing payments',TO_TIMESTAMP('2025-10-16 13:09:33.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T13:09:33.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T13:09:33.698Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542894)
;

-- 2025-10-16T13:09:33.703Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754992
;

-- 2025-10-16T13:09:33.705Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754992)
;

-- Field: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> Bankauszug
-- Column: C_PaySelectionLine.C_BankStatement_ID
-- 2025-10-16T13:09:33.811Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570111,754993,0,353,TO_TIMESTAMP('2025-10-16 13:09:33.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bank Statement of account',10,'D','The Bank Statement identifies a unique Bank Statement for a defined time period.  The statement defines all transactions that occurred','Y','N','N','N','N','N','N','N','Bankauszug',TO_TIMESTAMP('2025-10-16 13:09:33.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T13:09:33.814Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T13:09:33.817Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1381)
;

-- 2025-10-16T13:09:33.826Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754993
;

-- 2025-10-16T13:09:33.828Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754993)
;

-- Field: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> Auftrag
-- Column: C_PaySelectionLine.C_Order_ID
-- 2025-10-16T13:09:33.933Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591353,754994,0,353,TO_TIMESTAMP('2025-10-16 13:09:33.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2025-10-16 13:09:33.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-16T13:09:33.935Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-16T13:09:33.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558)
;

-- 2025-10-16T13:09:34.001Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754994
;

-- 2025-10-16T13:09:34.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754994)
;

-- UI Element: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> main -> 10 -> default.Auftrag
-- Column: C_PaySelectionLine.C_Order_ID
-- 2025-10-16T13:10:10.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754994,0,353,541024,637869,'F',TO_TIMESTAMP('2025-10-16 13:10:10.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','Y','N','N','Auftrag',20,20,0,TO_TIMESTAMP('2025-10-16 13:10:10.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> main -> 10 -> default.Auftrag
-- Column: C_PaySelectionLine.C_Order_ID
-- 2025-10-16T13:10:17.169Z
UPDATE AD_UI_Element SET SeqNo=22,Updated=TO_TIMESTAMP('2025-10-16 13:10:17.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637869
;

-- Name: PaySelectionModeByPaySelectionTrxTypeValRule
-- 2025-10-16T13:12:30.363Z
UPDATE AD_Val_Rule SET Classname='de.metas.banking.payment.PaySelectionModeByPaySelectionTrxTypeValRule', Name='PaySelectionModeByPaySelectionTrxTypeValRule',Updated=TO_TIMESTAMP('2025-10-16 13:12:30.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540484
;





-- Name: C_PaymentTerm_CheckComplexPaymentTermBreaks
-- 2025-10-17T07:02:47.365Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540752,'IsComplex=''N'' OR (IsComplex=''Y'' AND exists (select 1 from C_PaymentTerm_Break ptb where ptb.C_PaymentTerm_ID=C_PaymentTerm.C_PaymentTerm_ID ))',TO_TIMESTAMP('2025-10-17 07:02:47.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Complex payment terms must include at least one payment break. Non-complex payment terms are always allowed.','D','Y','C_PaymentTerm_CheckComplexPaymentTermBreaks','S',TO_TIMESTAMP('2025-10-17 07:02:47.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order.C_PaymentTerm_ID
-- 2025-10-17T07:02:56.769Z
UPDATE AD_Column SET AD_Val_Rule_ID=540752,Updated=TO_TIMESTAMP('2025-10-17 07:02:56.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=2187
;


-- Run mode: SWING_CLIENT

-- 2025-10-17T12:03:49.817Z
INSERT INTO t_alter_column values('c_payselectionline','C_Invoice_ID','NUMERIC(10)',null,null)
;

-- 2025-10-17T12:03:49.828Z
INSERT INTO t_alter_column values('c_payselectionline','C_Invoice_ID',null,'NULL',null)
;


-- Column: C_OrderPaySchedule.C_PaymentTerm_Break_ID
-- 2025-10-17T12:29:49.580Z
UPDATE AD_Column SET FilterOperator='E', IsMandatory='Y', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-17 12:29:49.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591214
;

-- 2025-10-17T12:29:53.209Z
INSERT INTO t_alter_column values('c_orderpayschedule','C_PaymentTerm_Break_ID','NUMERIC(10)',null,null)
;

-- 2025-10-17T12:29:53.214Z
INSERT INTO t_alter_column values('c_orderpayschedule','C_PaymentTerm_Break_ID',null,'NOT NULL',null)
;

-- Column: C_OrderPaySchedule.C_PaymentTerm_Break_ID
-- 2025-10-17T12:31:20.987Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2025-10-17 12:31:20.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591214
;

-- Column: C_PaySelectionLine.C_OrderPaySchedule_ID
-- 2025-10-17T12:31:30.629Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591370,584056,0,19,427,'XX','C_OrderPaySchedule_ID',TO_TIMESTAMP('2025-10-17 12:31:30.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsplan',0,0,TO_TIMESTAMP('2025-10-17 12:31:30.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-17T12:31:30.636Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591370 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-17T12:31:30.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(584056)
;

-- Column: C_PaySelectionLine.C_OrderPaySchedule_ID
-- 2025-10-17T12:31:38.148Z
UPDATE AD_Column SET MandatoryLogic='@C_Order_ID@ > 0',Updated=TO_TIMESTAMP('2025-10-17 12:31:38.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591370
;

-- 2025-10-17T12:31:39.741Z
/* DDL */ SELECT public.db_alter_table('C_PaySelectionLine','ALTER TABLE public.C_PaySelectionLine ADD COLUMN C_OrderPaySchedule_ID NUMERIC(10)')
;

-- 2025-10-17T12:31:39.946Z
ALTER TABLE C_PaySelectionLine ADD CONSTRAINT COrderPaySchedule_CPaySelectionLine FOREIGN KEY (C_OrderPaySchedule_ID) REFERENCES public.C_OrderPaySchedule DEFERRABLE INITIALLY DEFERRED
;

