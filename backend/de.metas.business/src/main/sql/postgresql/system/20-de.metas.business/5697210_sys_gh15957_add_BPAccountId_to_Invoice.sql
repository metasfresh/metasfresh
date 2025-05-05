-- Column: C_Invoice.C_BP_BankAccount_ID
-- 2023-07-27T10:24:58.160Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587201,837,0,30,540069,318,'XX','C_BP_BankAccount_ID',TO_TIMESTAMP('2023-07-27 13:24:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Bank Account of the Business Partner','D',0,10,'The Partner Bank Account identifies the bank account to be used for this Business Partner','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Partner Bank Account',0,0,TO_TIMESTAMP('2023-07-27 13:24:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-27T10:24:58.167Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587201 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-27T10:24:58.745Z
/* DDL */  select update_Column_Translation_From_AD_Element(837) 
;

-- 2023-07-27T10:28:35.728Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN C_BP_BankAccount_ID NUMERIC(10)')
;

-- 2023-07-27T10:28:37.979Z
ALTER TABLE C_Invoice ADD CONSTRAINT CBPBankAccount_CInvoice FOREIGN KEY (C_BP_BankAccount_ID) REFERENCES public.C_BP_BankAccount DEFERRABLE INITIALLY DEFERRED
;

