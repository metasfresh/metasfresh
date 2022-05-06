-- Column: M_ShipmentSchedule.AD_InputDataSource_ID
-- 2022-05-06T07:48:22.983Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582928,541291,0,30,500221,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:48:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:48:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:48:22.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:48:23.006Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:48:23.829Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:48:23.976Z
ALTER TABLE M_ShipmentSchedule ADD CONSTRAINT ADInputDataSource_MShipmentSchedule FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.AD_InputDataSource_ID
-- 2022-05-06T07:49:02.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582929,541291,0,30,318,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:49:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:49:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:49:02.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582929 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:49:02.959Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:49:03.514Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:49:04.307Z
ALTER TABLE C_Invoice ADD CONSTRAINT ADInputDataSource_CInvoice FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice_Candidate.AD_InputDataSource_ID
-- 2022-05-06T07:49:56.480Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582930,541291,0,30,540270,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:49:56','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:49:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:49:56.484Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582930 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:49:56.489Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:49:57.001Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:49:57.322Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT ADInputDataSource_CInvoiceCandidate FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InOut.AD_InputDataSource_ID
-- 2022-05-06T07:50:54.944Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582931,541291,0,30,319,'AD_InputDataSource_ID',TO_TIMESTAMP('2022-05-06 09:50:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Eingabequelle',0,0,TO_TIMESTAMP('2022-05-06 09:50:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-06T07:50:54.948Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582931 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-06T07:50:54.952Z
/* DDL */  select update_Column_Translation_From_AD_Element(541291) 
;

-- 2022-05-06T07:50:55.454Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN AD_InputDataSource_ID NUMERIC(10)')
;

-- 2022-05-06T07:50:55.959Z
ALTER TABLE M_InOut ADD CONSTRAINT ADInputDataSource_MInOut FOREIGN KEY (AD_InputDataSource_ID) REFERENCES public.AD_InputDataSource DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Invoice.AD_InputDataSource_ID
-- 2022-05-06T09:12:48.243Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-06 11:12:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582929
;

-- Column: M_InOut.AD_InputDataSource_ID
-- 2022-05-06T09:15:50.510Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-06 11:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582931
;

