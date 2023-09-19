-- Run mode: SWING_CLIENT

-- Name: M_Shipping_NotificationLine
-- 2023-09-18T11:31:25.242Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541832,TO_TIMESTAMP('2023-09-18 14:31:25.077','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','N','M_Shipping_NotificationLine',TO_TIMESTAMP('2023-09-18 14:31:25.077','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-09-18T11:31:25.245Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541832 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Shipping_NotificationLine
-- Table: M_Shipping_NotificationLine
-- Key: M_Shipping_NotificationLine.M_Shipping_NotificationLine_ID
-- 2023-09-18T11:31:39.123Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,587411,0,541832,542366,TO_TIMESTAMP('2023-09-18 14:31:39.112','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','N','N',TO_TIMESTAMP('2023-09-18 14:31:39.112','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Shipping_NotificationLine.Reversal_ID
-- 2023-09-18T11:31:52.870Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587480,53457,0,30,541832,542366,'Reversal_ID',TO_TIMESTAMP('2023-09-18 14:31:52.712','YYYY-MM-DD HH24:MI:SS.US'),100,'N','ID of document reversal','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Storno-Gegenbeleg',0,0,TO_TIMESTAMP('2023-09-18 14:31:52.712','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-18T11:31:52.873Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-18T11:31:52.877Z
/* DDL */  select update_Column_Translation_From_AD_Element(53457)
;

-- 2023-09-18T11:31:54.457Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_NotificationLine','ALTER TABLE public.M_Shipping_NotificationLine ADD COLUMN Reversal_ID NUMERIC(10)')
;

-- 2023-09-18T11:31:54.465Z
ALTER TABLE M_Shipping_NotificationLine ADD CONSTRAINT Reversal_MShippingNotificationLine FOREIGN KEY (Reversal_ID) REFERENCES public.M_Shipping_NotificationLine DEFERRABLE INITIALLY DEFERRED
;

