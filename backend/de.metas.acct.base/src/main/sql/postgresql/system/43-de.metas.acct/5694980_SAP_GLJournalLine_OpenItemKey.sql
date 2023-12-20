-- Column: SAP_GLJournalLine.OpenItemKey
-- 2023-07-06T14:33:21.387Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587043,582532,0,10,542276,'XX','OpenItemKey',TO_TIMESTAMP('2023-07-06 17:33:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Open Item Key',0,0,TO_TIMESTAMP('2023-07-06 17:33:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-06T14:33:21.799Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587043 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-06T14:33:22.009Z
/* DDL */  select update_Column_Translation_From_AD_Element(582532) 
;

-- 2023-07-06T14:33:23.493Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournalLine','ALTER TABLE public.SAP_GLJournalLine ADD COLUMN OpenItemKey VARCHAR(255)')
;

-- Column: SAP_GLJournalLine.OpenItemKey
-- 2023-07-06T14:36:48.660Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2023-07-06 17:36:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587043
;

-- Column: SAP_GLJournalLine.OpenItemKey
-- 2023-07-06T14:37:44.272Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='N',Updated=TO_TIMESTAMP('2023-07-06 17:37:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587043
;

