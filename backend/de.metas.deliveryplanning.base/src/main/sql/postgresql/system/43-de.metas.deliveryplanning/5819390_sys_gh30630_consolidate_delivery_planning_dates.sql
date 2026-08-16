-- gh30630 Phase D - consolidate delivery-planning date columns onto the base ETD/ATD/ETA/ATA columns.
--
-- Loading  = departure -> T-D (ETD/ATD);  Delivery = arrival -> T-A (ETA/ATA).
-- Planned = E (estimated); Actual = A.
--
-- Reuses the base ETD/ETA/ATD/ATA AD_Elements introduced for M_ShipperTransportation:
--   ETD=584066, ETA=584067, ATD=584068, ATA=584069.
--
-- Mapping (per table):
--   PlannedLoadingDate  -> ETD
--   ActualLoadingDate   -> ATD
--   PlannedDeliveryDate -> ETA
--   ActualDeliveryDate  -> ATA
--
-- Tables consolidated:
--   M_Delivery_Planning (AD_Table_ID=542259): new ETD/ATD/ETA/ATA columns created here.
--   I_DeliveryPlanning  (AD_Table_ID=542292): new ETD/ATD/ETA/ATA columns created here.
--   M_ShipperTransportation (AD_Table_ID=540030): ETD/ATD/ETA/ATA already exist (base script 5772160);
--     only the field re-point and the drop of LoadingDate/DeliveryDate happen here.
--
-- NOTE: no AD_Element is deleted. Element 581689 (ActualLoadingDate) is SHARED with
--       C_Invoice_Candidate; deleting it would break IC. LoadingDate (581900) and
--       DeliveryDate (541376) elements are likewise kept.
--
-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/views/M_Delivery_Planning_Delivery_Instructions_V.sql
-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/views/M_ShipperTransportation_Delivery_Instructions_V.sql


-- =====================================================================================
-- 1) New AD_Columns ETD/ATD/ETA/ATA on M_Delivery_Planning (reuse base date elements)
-- =====================================================================================

-- Column: M_Delivery_Planning.ETD  (was PlannedLoadingDate, element 584066)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory,FilterOperator) VALUES (0,593314 /*From ID Server*/,584066,0,15,542259,'ETD',TO_TIMESTAMP('2026-08-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'ETD',0,0,TO_TIMESTAMP('2026-08-16 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0,'NP','B')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593314 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584066)
;
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN ETD TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_Delivery_Planning.ATD  (was ActualLoadingDate, element 584068)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory,FilterOperator) VALUES (0,593315 /*From ID Server*/,584068,0,15,542259,'ATD',TO_TIMESTAMP('2026-08-16 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'ATD',0,0,TO_TIMESTAMP('2026-08-16 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,0,'NP','B')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593315 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584068)
;
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN ATD TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_Delivery_Planning.ETA  (was PlannedDeliveryDate, element 584067)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory,FilterOperator) VALUES (0,593316 /*From ID Server*/,584067,0,15,542259,'ETA',TO_TIMESTAMP('2026-08-16 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'ETA',0,0,TO_TIMESTAMP('2026-08-16 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,0,'NP','B')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593316 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584067)
;
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN ETA TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_Delivery_Planning.ATA  (was ActualDeliveryDate, element 584069)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory,FilterOperator) VALUES (0,593317 /*From ID Server*/,584069,0,15,542259,'ATA',TO_TIMESTAMP('2026-08-16 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'ATA',0,0,TO_TIMESTAMP('2026-08-16 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,0,'NP','B')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593317 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584069)
;
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN ATA TIMESTAMP WITHOUT TIME ZONE')
;


-- =====================================================================================
-- 2) New AD_Columns ETD/ATD/ETA/ATA on I_DeliveryPlanning (reuse base date elements)
--    (import staging table - not a selection column, mirrors 585794..585799)
-- =====================================================================================

-- Column: I_DeliveryPlanning.ETD  (was PlannedLoadingDate, element 584066)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory) VALUES (0,593318 /*From ID Server*/,584066,0,15,542292,'ETD',TO_TIMESTAMP('2026-08-16 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ETD',0,0,TO_TIMESTAMP('2026-08-16 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,0,'NP')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593318 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584066)
;
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning ADD COLUMN ETD TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: I_DeliveryPlanning.ATD  (was ActualLoadingDate, element 584068)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory) VALUES (0,593319 /*From ID Server*/,584068,0,15,542292,'ATD',TO_TIMESTAMP('2026-08-16 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ATD',0,0,TO_TIMESTAMP('2026-08-16 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,0,'NP')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593319 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584068)
;
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning ADD COLUMN ATD TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: I_DeliveryPlanning.ETA  (was PlannedDeliveryDate, element 584067)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory) VALUES (0,593320 /*From ID Server*/,584067,0,15,542292,'ETA',TO_TIMESTAMP('2026-08-16 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ETA',0,0,TO_TIMESTAMP('2026-08-16 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,0,'NP')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593320 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584067)
;
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning ADD COLUMN ETA TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: I_DeliveryPlanning.ATA  (was ActualDeliveryDate, element 584069)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version,PersonalDataCategory) VALUES (0,593321 /*From ID Server*/,584069,0,15,542292,'ATA',TO_TIMESTAMP('2026-08-16 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ATA',0,0,TO_TIMESTAMP('2026-08-16 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,0,'NP')
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593321 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584069)
;
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning ADD COLUMN ATA TIMESTAMP WITHOUT TIME ZONE')
;


-- =====================================================================================
-- 3) Re-point AD_Fields to the new / existing consolidated columns (placement preserved)
-- =====================================================================================

-- M_Delivery_Planning window 541632, tab 546674
UPDATE AD_Field SET AD_Column_ID=593314, Updated=TO_TIMESTAMP('2026-08-16 10:01:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=708098; -- was PlannedLoadingDate  -> ETD
UPDATE AD_Field SET AD_Column_ID=593315, Updated=TO_TIMESTAMP('2026-08-16 10:01:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=708099; -- was ActualLoadingDate   -> ATD
UPDATE AD_Field SET AD_Column_ID=593316, Updated=TO_TIMESTAMP('2026-08-16 10:01:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=708095; -- was PlannedDeliveryDate -> ETA
UPDATE AD_Field SET AD_Column_ID=593317, Updated=TO_TIMESTAMP('2026-08-16 10:01:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=708096; -- was ActualDeliveryDate  -> ATA

-- Lieferanweisungen window 541657, tab 546732 (M_ShipperTransportation) - ETD/ETA already exist (591245/591246)
UPDATE AD_Field SET AD_Column_ID=591245, Updated=TO_TIMESTAMP('2026-08-16 10:01:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=710113; -- was LoadingDate  -> ETD
UPDATE AD_Field SET AD_Column_ID=591246, Updated=TO_TIMESTAMP('2026-08-16 10:01:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=710116; -- was DeliveryDate -> ETA (keeps its AD_Name_ID=581902 'Liefertermin' override)

-- I_DeliveryPlanning window (import), tab 546801 - re-point to the new I_DeliveryPlanning columns
UPDATE AD_Field SET AD_Column_ID=593318, Updated=TO_TIMESTAMP('2026-08-16 10:01:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=712080; -- was PlannedLoadingDate  -> ETD
UPDATE AD_Field SET AD_Column_ID=593319, Updated=TO_TIMESTAMP('2026-08-16 10:01:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=712081; -- was ActualLoadingDate   -> ATD
UPDATE AD_Field SET AD_Column_ID=593320, Updated=TO_TIMESTAMP('2026-08-16 10:01:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=712084; -- was PlannedDeliveryDate -> ETA
UPDATE AD_Field SET AD_Column_ID=593321, Updated=TO_TIMESTAMP('2026-08-16 10:01:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=712085; -- was ActualDeliveryDate  -> ATA

-- Delivery Planning Import format (AD_ImpFormat 540078): its rows still reference the old
-- I_DeliveryPlanning date AD_Columns, which are dropped below -> repoint them to the new
-- columns first (mirror of the AD_Field repoint above) so the import keeps working and the
-- adcolumn_adimpformatrow FK does not block the AD_Column delete.
UPDATE AD_ImpFormat_Row SET AD_Column_ID=593318, Updated=TO_TIMESTAMP('2026-08-16 10:01:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541815; -- PlannedLoadingDate  -> ETD
UPDATE AD_ImpFormat_Row SET AD_Column_ID=593319, Updated=TO_TIMESTAMP('2026-08-16 10:01:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541816; -- ActualLoadingDate   -> ATD
UPDATE AD_ImpFormat_Row SET AD_Column_ID=593320, Updated=TO_TIMESTAMP('2026-08-16 10:01:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541819; -- PlannedDeliveryDate -> ETA
UPDATE AD_ImpFormat_Row SET AD_Column_ID=593321, Updated=TO_TIMESTAMP('2026-08-16 10:01:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541820; -- ActualDeliveryDate  -> ATA


-- =====================================================================================
-- 4) Re-point the two delivery-instruction views onto the consolidated ETD/ETA source.
--    Output column names loadingdate / deliverydate are preserved so the view AD_Columns
--    (585633/585634) and the fields on tab 546754 stay valid; only the underlying source
--    column changes (di.loadingdate->di.etd, di.deliverydate->di.eta).
-- =====================================================================================

-- View: M_ShipperTransportation_Delivery_Instructions_V
DROP VIEW IF EXISTS M_ShipperTransportation_Delivery_Instructions_V$new
;
CREATE OR REPLACE VIEW M_ShipperTransportation_Delivery_Instructions_V$new
AS
SELECT di.documentno,
       di.m_shippertransportation_id,
       dp.m_delivery_planning_id,
       di.docstatus,
       di.datedoc,
       di.c_bpartner_location_loading_id,
       di.etd AS loadingdate,
       di.c_bpartner_location_delivery_id,
       di.eta AS deliverydate,
       di.c_incoterms_id,
       di.incotermlocation,
       di.m_meansoftransportation_id,
       sp.M_Product_ID,
       sp.m_locator_id,
       sp.actualloadqty as plannedloadedquantity,
       sp.actualdischargequantity as planneddischargequantity,
       di.created,
       di.createdby,
       sp.m_shippertransportation_id AS M_Delivery_Planning_Delivery_Instructions_V_ID,
       di.updated,
       di.updatedby,
       di.isactive,
       di.ad_org_id,
       di.ad_client_id
FROM M_ShipperTransportation di
         JOIN M_ShippingPackage sp
              ON di.m_shippertransportation_id = sp.m_shippertransportation_id
         JOIN M_Delivery_Planning dp ON di.M_Delivery_Planning_id = dp.M_Delivery_Planning_id
;
SELECT db_alter_view(
               'm_shippertransportation_delivery_instructions_v',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('m_shippertransportation_delivery_instructions_v$new'))
           )
;
DROP VIEW IF EXISTS m_shippertransportation_delivery_instructions_v$new
;

-- View: M_Delivery_Planning_Delivery_Instructions_V
DROP VIEW IF EXISTS M_Delivery_Planning_Delivery_Instructions_V$new
;
CREATE OR REPLACE VIEW M_Delivery_Planning_Delivery_Instructions_V$new
AS
SELECT di.documentno,
       di.m_shippertransportation_id,
       dp.m_delivery_planning_id,
       di.docstatus,
       di.datedoc,
       di.c_bpartner_location_loading_id,
       di.etd AS loadingdate,
       di.c_bpartner_location_delivery_id,
       di.eta AS deliverydate,
       di.c_incoterms_id,
       di.incotermlocation,
       di.m_meansoftransportation_id,
       sp.M_Product_ID,
       sp.m_locator_id,
       sp.actualloadqty,
       sp.actualdischargequantity,
       sp.M_ShippingPackage_ID,
       di.created,
       di.createdby,
       sp.M_ShippingPackage_ID AS M_Delivery_Planning_Delivery_Instructions_V_ID,
       di.updated,
       di.updatedby,
       di.isactive,
       di.ad_org_id,
       di.ad_client_id
FROM M_ShipperTransportation di
         INNER JOIN M_ShippingPackage sp ON di.m_shippertransportation_id = sp.m_shippertransportation_id
         INNER JOIN M_Delivery_Planning dp ON dp.m_shippertransportation_id = di.m_shippertransportation_id
WHERE di.docstatus NOT IN ('VO', 'RE')
;
SELECT db_alter_view(
               'm_delivery_planning_delivery_instructions_v',
               (SELECT view_definition
                FROM information_schema.views
                WHERE lower(views.table_name) = lower('m_delivery_planning_delivery_instructions_v$new'))
           )
;
DROP VIEW IF EXISTS m_delivery_planning_delivery_instructions_v$new
;


-- =====================================================================================
-- 5) Re-point the "Export Delivery Planning Lines (Jasper)" process SQL (AD_Process 585207)
--    from the old M_Delivery_Planning date columns onto the consolidated ETD/ATD/ETA/ATA.
-- =====================================================================================
UPDATE AD_Process
SET SQLStatement =
'SELECT div.documentno,
       COALESCE(deliveryL.bpartnername, deliveryBP.name) || E''\n'' || deliveryL.address AS shipToLocation_name,
       p.name                                                                          AS productName,
       p.value                                                                         AS productCode,
       w.name                                                                          AS warehouseName,
       fromC.name                                                                      AS originCountry,
       div.deliverydate                                                                AS deliveryDate,
       m_delivery_planning.batch,
       m_delivery_planning.releaseno,
       m_delivery_planning.etd                                                         AS plannedloadingdate,
       m_delivery_planning.atd                                                         AS actualloadingdate,
       m_delivery_planning.plannedloadedquantity,
       m_delivery_planning.actualloadqty,
       m_delivery_planning.eta                                                         AS planneddeliverydate,
       m_delivery_planning.ata                                                         AS actualdeliverydate,
       m_delivery_planning.planneddischargequantity,
       m_delivery_planning.actualdischargequantity
FROM m_delivery_planning_delivery_instructions_v div
         INNER JOIN m_delivery_planning ON m_delivery_planning.m_delivery_planning_id = div.m_delivery_planning_id
         INNER JOIN c_bpartner_location deliveryL ON div.c_bpartner_location_delivery_id = deliveryL.c_bpartner_location_id
         INNER JOIN c_bpartner_location loadingL ON div.c_bpartner_location_loading_id = loadingL.c_bpartner_location_id
         INNER JOIN c_bpartner deliveryBP ON deliveryL.c_bpartner_id = deliveryBP.c_bpartner_id
         LEFT JOIN m_product p ON m_delivery_planning.m_product_id = p.m_product_id
         LEFT JOIN m_warehouse W ON m_delivery_planning.m_warehouse_id = W.m_warehouse_id
         LEFT JOIN ad_language l ON l.isbaselanguage = ''Y''
         LEFT JOIN c_country_trl fromC ON m_delivery_planning.c_origincountry_id = fromC.c_country_id AND fromC.ad_language = l.ad_language WHERE @SELECTION_WHERECLAUSE/false@',
    Updated = TO_TIMESTAMP('2026-08-16 10:02:00','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Process_ID = 585207
;


-- =====================================================================================
-- 6) Re-point the delivery-instructions Jasper SQL function onto ETD/ETA.
--    Output column names loadingdate / deliverydate are preserved so the JRXML field
--    bindings stay valid; only the underlying source column changes (st.loadingdate->st.etd,
--    st.deliverydate->st.eta).
-- =====================================================================================
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_deliveryinstructions_description(p_m_shippertransportation_id numeric)
 RETURNS TABLE(forwarderaddress text, transportdetails text, deliveryaddress text, deliverycontactname character varying, deliverycontactphone character varying, loadingaddress text, loadingdate timestamp without time zone, loadingtime character varying, deliverydate timestamp without time zone, documentno character varying, creator character varying, creatorphone character varying, creatorfax character varying, creatoremail character varying, orderno character varying, referenceno character varying, incoterms character varying, incotermlocation character varying, meansoftransport text)
 LANGUAGE sql
 STABLE
AS $function$
SELECT f.*,
       d.*,
       l.*,
       st.etd        AS loadingdate,
       st.loadingtime,
       st.eta        AS deliverydate,
       st.documentno,
       u.name        AS Creator,
       u.phone       AS CreatorPhone,
       u.fax         AS CreatorFax,
       u.email       AS CreatorEmail,
       o.documentno  AS orderno,
       o.poreference AS referenceno,
       ic.name       AS incoterms,
       st.incotermlocation,
       mt.name       AS meansoftransport

FROM de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_LoadingAddress(p_m_shippertransportation_id) AS l,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_Forwarder(p_m_shippertransportation_id) AS f,
     de_metas_endcustomer_fresh_reports.Docs_DeliveryInstructions_DeliveryAddress(p_m_shippertransportation_id) AS d,
     M_ShipperTransportation st
         JOIN ad_user u ON st.createdby = u.ad_user_id
         JOIN m_delivery_planning dp ON dp.m_delivery_planning_id = st.m_delivery_planning_id
         JOIN C_order o ON o.c_order_id = dp.c_order_id
         JOIN c_incoterms ic ON ic.c_incoterms_id = st.c_incoterms_id
         LEFT JOIN m_meansoftransportation mt ON mt.m_meansoftransportation_id = st.m_meansoftransportation_id
WHERE st.m_shippertransportation_id = p_m_shippertransportation_id
$function$
;


-- =====================================================================================
-- 7) Drop the old date AD_Columns + physical columns.
--    NOTE: no AD_Element deleted (581686/581687/581688/581689 shared with C_Invoice_Candidate;
--          581900/541376 kept as well).
-- =====================================================================================

-- --- M_Delivery_Planning: PlannedDeliveryDate 585023, ActualDeliveryDate 585024, PlannedLoadingDate 585026, ActualLoadingDate 585027
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (585023,585024,585026,585027);
DELETE FROM AD_Column     WHERE AD_Column_ID IN (585023,585024,585026,585027);
SELECT backup_table('m_delivery_planning','_gh30630_dates');
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning DROP COLUMN IF EXISTS PlannedDeliveryDate');
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning DROP COLUMN IF EXISTS ActualDeliveryDate');
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning DROP COLUMN IF EXISTS PlannedLoadingDate');
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning DROP COLUMN IF EXISTS ActualLoadingDate');

-- --- I_DeliveryPlanning: PlannedLoadingDate 585794, ActualLoadingDate 585795, PlannedDeliveryDate 585798, ActualDeliveryDate 585799
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (585794,585795,585798,585799);
DELETE FROM AD_Column     WHERE AD_Column_ID IN (585794,585795,585798,585799);
SELECT backup_table('i_deliveryplanning','_gh30630_dates');
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning DROP COLUMN IF EXISTS PlannedLoadingDate');
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning DROP COLUMN IF EXISTS ActualLoadingDate');
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning DROP COLUMN IF EXISTS PlannedDeliveryDate');
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning','ALTER TABLE public.I_DeliveryPlanning DROP COLUMN IF EXISTS ActualDeliveryDate');

-- --- M_ShipperTransportation: LoadingDate 585436, DeliveryDate 585439 (their ETD/ETA already exist)
DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (585436,585439);
DELETE FROM AD_Column     WHERE AD_Column_ID IN (585436,585439);
SELECT backup_table('m_shippertransportation','_gh30630_dates');
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation DROP COLUMN IF EXISTS LoadingDate');
/* DDL */ SELECT public.db_alter_table('M_ShipperTransportation','ALTER TABLE public.M_ShipperTransportation DROP COLUMN IF EXISTS DeliveryDate');

-- =====================================================================================
-- 8) Remove the now-orphaned port-introduced date AD_Elements. The columns above were
--    consolidated onto the base ETD/ETA/ATD/ATA elements (584066-069), leaving these three
--    port elements with 0 remaining references (verified: AD_Column/AD_Field.Name/
--    AD_Process_Para/AD_InfoColumn all 0). KEEP: 581689 ActualLoadingDate (shared with
--    C_Invoice_Candidate), 581900 LoadingDate (used by the two *_Delivery_Instructions_V
--    view columns), 541376 DeliveryDate (widely shared, 15 columns).
-- =====================================================================================
DELETE FROM AD_Element_Trl WHERE AD_Element_ID IN (581686,581687,581688);
DELETE FROM AD_Element     WHERE AD_Element_ID IN (581686,581687,581688);
