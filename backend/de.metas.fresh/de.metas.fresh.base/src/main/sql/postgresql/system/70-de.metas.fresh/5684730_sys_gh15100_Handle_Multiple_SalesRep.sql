DO $$
BEGIN
    IF exists(select 1 from ad_column where ad_column_id = 572811 and columnname = 'SalesRep_ID') THEN
        update ad_column SET ColumnSQL = '(select o.SalesRep_ID from C_Order o where o.C_Order_ID = C_Invoice_Candidate.C_Order_ID)', updated = now() where ad_column_id = 572811;
    ELSE
        INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,572811,1063,0,18,190,540270,'SalesRep_ID','(select o.SalesRep_ID from C_Order o where o.C_Order_ID = C_Invoice_Candidate.C_Order_ID)',TO_TIMESTAMP('2021-02-15 14:54:06','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.invoicecandidate',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Kundenbetreuer',0,0,TO_TIMESTAMP('2021-02-15 14:54:06','YYYY-MM-DD HH24:MI:SS'),100,0)
        ;

        INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=572811 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
        ;

        /* DDL */  perform update_Column_Translation_From_AD_Element(1063)
        ;
    END IF;
END $$;