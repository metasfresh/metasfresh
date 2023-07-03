-- Column: GPLR_Report_Shipment.IsDropShip
-- 2023-07-03T07:29:17.112Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587021,2466,0,20,542343,'XX','IsDropShip',TO_TIMESTAMP('2023-07-03 10:29:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Drop Shipments are sent from the Vendor directly to the Customer','D',0,1,'Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Different shipping address',0,0,TO_TIMESTAMP('2023-07-03 10:29:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-03T07:29:17.115Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587021 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-03T07:29:17.733Z
/* DDL */  select update_Column_Translation_From_AD_Element(2466) 
;

-- 2023-07-03T07:29:19.758Z
/* DDL */ SELECT public.db_alter_table('GPLR_Report_Shipment','ALTER TABLE public.GPLR_Report_Shipment ADD COLUMN IsDropShip CHAR(1) DEFAULT ''N'' CHECK (IsDropShip IN (''Y'',''N'')) NOT NULL')
;

