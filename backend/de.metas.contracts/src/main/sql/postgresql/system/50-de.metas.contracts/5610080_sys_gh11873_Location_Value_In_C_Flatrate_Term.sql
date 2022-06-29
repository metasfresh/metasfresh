-- 2021-10-19T12:33:42.329Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577792,579024,0,19,540320,'Bill_Location_Value_ID',TO_TIMESTAMP('2021-10-19 15:33:41','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsstandort (Address)',0,0,TO_TIMESTAMP('2021-10-19 15:33:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-19T12:33:42.358Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-19T12:33:42.431Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579024) 
;



-- 2021-10-19T12:40:05.533Z
-- #298 changing anz. stellen
UPDATE AD_Column SET AD_Reference_ID=21, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-10-19 15:40:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577792
;

-- 2021-10-19T12:40:09.780Z
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN Bill_Location_Value_ID NUMERIC(10)')
;

-- 2021-10-19T12:40:10.439Z
-- #298 changing anz. stellen
ALTER TABLE C_Flatrate_Term ADD CONSTRAINT BillLocationValue_CFlatrateTerm FOREIGN KEY (Bill_Location_Value_ID) REFERENCES public.C_Location DEFERRABLE INITIALLY DEFERRED
;




-- 2021-10-20T08:14:01.494Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,577795,579025,0,21,540320,'DropShip_Location_Value_ID',TO_TIMESTAMP('2021-10-20 11:14:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Business Partner Location for shipping to','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferadresse (Address)',0,0,TO_TIMESTAMP('2021-10-20 11:14:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-10-20T08:14:01.541Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=577795 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-10-20T08:14:01.610Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(579025) 
;

-- 2021-10-20T08:14:08.641Z
-- #298 changing anz. stellen
/* DDL */ SELECT public.db_alter_table('C_Flatrate_Term','ALTER TABLE public.C_Flatrate_Term ADD COLUMN DropShip_Location_Value_ID NUMERIC(10)')
;

-- 2021-10-20T08:14:09.217Z
-- #298 changing anz. stellen
ALTER TABLE C_Flatrate_Term ADD CONSTRAINT DropShipLocationValue_CFlatrateTerm FOREIGN KEY (DropShip_Location_Value_ID) REFERENCES public.C_Location DEFERRABLE INITIALLY DEFERRED
;

