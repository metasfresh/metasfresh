DO
$DO$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM ad_column WHERE AD_Column_ID=584270) THEN

            -- Column: C_Invoice.DueDate
            -- 2022-09-07T14:46:24.866Z
            INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,NAME,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,VERSION) VALUES (0,584270,2000,0,15,318,'DueDate',TO_TIMESTAMP('2022-09-07 15:46:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Date when the payment is due','D',0,7,'Date when the payment is due without deductions or discount','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Due Date',0,0,TO_TIMESTAMP('2022-09-07 15:46:23','YYYY-MM-DD HH24:MI:SS'),100,0)
            ;

            -- 2022-09-07T14:46:24.974Z
            INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, NAME, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584270 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
            ;

            -- 2022-09-07T14:46:25.205Z
            /* DDL */
            PERFORM update_Column_Translation_From_AD_Element(2000)
            ;

            -- 2022-09-07T14:47:37.877Z
            /* DDL */ PERFORM PUBLIC.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN DueDate TIMESTAMP WITHOUT TIME ZONE')
            ;

        END IF;
    END
$DO$