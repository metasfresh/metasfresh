UPDATE ad_column
SET columnname = 'Price_UOM_back_up_ID', Updated=TO_TIMESTAMP('2025-06-10 15:39:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE ad_column_id = 582988
;

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2025-01-09T17:58:13.095Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589574,542464,0,18,114,540861,540472,'XX','Price_UOM_ID',TO_TIMESTAMP('2025-01-09 17:58:12.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.purchasecandidate',0,10,'Y','N','Y','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preiseinheit',0,0,TO_TIMESTAMP('2025-01-09 17:58:12.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-09T17:58:13.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2025-01-09T18:16:10.974Z
UPDATE AD_Column SET ReadOnlyLogic='@IsManualPrice@=N',Updated=TO_TIMESTAMP('2025-01-09 18:16:10.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589574
;

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2022-05-18T11:26:25.932Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2022-05-18 14:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589574
;

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2022-05-18T11:30:40.790Z
UPDATE AD_Column SET IsAutoApplyValidationRule='N',Updated=TO_TIMESTAMP('2022-05-18 14:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589574
;

-- 2022-05-18T11:26:29.298Z
INSERT INTO t_alter_column values('c_purchasecandidate','Price_UOM_ID','NUMERIC(10)',null,null)
;

UPDATE ad_field
SET ad_column_id = 589574, Updated=TO_TIMESTAMP('2025-06-10 15:39:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE ad_column_id = 582988
;

-- 2025-06-10T13:26:38.873Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=582988
;

-- 2025-06-10T13:26:38.881Z
DELETE FROM AD_Column WHERE AD_Column_ID=582988
;
