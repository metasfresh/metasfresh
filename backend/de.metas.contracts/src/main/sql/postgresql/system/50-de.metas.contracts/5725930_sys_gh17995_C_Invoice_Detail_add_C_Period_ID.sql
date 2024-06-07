-- Column: C_Invoice_Detail.C_Period_ID
-- 2024-06-07T09:13:41.010Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588335,206,0,19,540614,'C_Period_ID',TO_TIMESTAMP('2024-06-07 11:13:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Periode des Kalenders','D',0,10,'"Periode" bezeichnet einen eklusiven Datumsbereich eines Kalenders.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Periode',0,0,TO_TIMESTAMP('2024-06-07 11:13:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-06-07T09:13:41.018Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588335 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-07T09:13:41.058Z
/* DDL */  select update_Column_Translation_From_AD_Element(206) 
;

-- 2024-06-07T09:13:58.322Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Detail','ALTER TABLE public.C_Invoice_Detail ADD COLUMN C_Period_ID NUMERIC(10)')
;

-- 2024-06-07T09:13:58.376Z
ALTER TABLE C_Invoice_Detail ADD CONSTRAINT CPeriod_CInvoiceDetail FOREIGN KEY (C_Period_ID) REFERENCES public.C_Period DEFERRABLE INITIALLY DEFERRED
;
