-- Run mode: SWING_CLIENT

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:44:06.996Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587544,1297,0,16,542372,'DateTrx',TO_TIMESTAMP('2023-10-11 11:44:06.69','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Vorgangsdatum','de.metas.contracts',0,29,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vorgangsdatum',0,0,TO_TIMESTAMP('2023-10-11 11:44:06.69','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-10-11T10:44:07.004Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587544 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-11T10:44:07.026Z
/* DDL */  select update_Column_Translation_From_AD_Element(1297)
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:44:19.593Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-11 11:44:19.593','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587544
;

-- Column: I_ModCntr_Log.DateTrx
-- 2023-10-11T10:44:31.625Z
UPDATE AD_Column SET IsShowFilterInline='Y',Updated=TO_TIMESTAMP('2023-10-11 11:44:31.625','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587544
;

-- 2023-10-11T10:44:38.502Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN DateTrx TIMESTAMP WITH TIME ZONE')
;

-- 2023-10-11T10:46:14.864Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataFormat,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,587544,540092,541960,0,TO_TIMESTAMP('2023-10-11 11:46:14.486','YYYY-MM-DD HH24:MI:SS.US'),100,'yyyy-mm-dd','D','.','N',0,'Y','TransactionDate',220,200,TO_TIMESTAMP('2023-10-11 11:46:14.486','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-10-11T10:47:01.943Z
UPDATE AD_ImpFormat SET IsManualImport='Y',Updated=TO_TIMESTAMP('2023-10-11 11:47:01.943','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540092
;


-- 2023-10-11T10:59:57.572Z
UPDATE AD_ImpFormat_Row SET StartNo=20,Updated=TO_TIMESTAMP('2023-10-11 11:59:57.572','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541960
;



