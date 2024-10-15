-- Column: DD_Order_Candidate.M_Shipper_ID
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2024-07-17T17:22:49.214Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588857,455,0,30,542424,'XX','M_Shipper_ID',TO_TIMESTAMP('2024-07-17 20:22:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Methode oder Art der Warenlieferung','EE01',0,10,'"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg',0,0,TO_TIMESTAMP('2024-07-17 20:22:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-17T17:22:49.216Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588857 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-17T17:22:49.268Z
/* DDL */  select update_Column_Translation_From_AD_Element(455) 
;

-- 2024-07-17T17:22:49.994Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate','ALTER TABLE public.DD_Order_Candidate ADD COLUMN M_Shipper_ID NUMERIC(10)')
;

-- 2024-07-17T17:22:50.001Z
ALTER TABLE DD_Order_Candidate ADD CONSTRAINT MShipper_DDOrderCandidate FOREIGN KEY (M_Shipper_ID) REFERENCES public.M_Shipper DEFERRABLE INITIALLY DEFERRED
;

-- Column: DD_Order_Candidate.M_Shipper_ID
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2024-07-17T17:24:02.362Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-07-17 20:24:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588857
;

-- 2024-07-17T17:24:02.908Z
INSERT INTO t_alter_column values('dd_order_candidate','M_Shipper_ID','NUMERIC(10)',null,null)
;

-- 2024-07-17T17:24:02.912Z
INSERT INTO t_alter_column values('dd_order_candidate','M_Shipper_ID',null,'NOT NULL',null)
;

