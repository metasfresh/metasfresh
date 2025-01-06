-- Column: C_POS_Order.Processed
-- Column: C_POS_Order.Processed
-- 2024-10-03T08:15:49.443Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589180,1047,0,20,542434,'XX','Processed',TO_TIMESTAMP('2024-10-03 11:15:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','de.metas.pos',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2024-10-03 11:15:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T08:15:49.450Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589180 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T08:15:49.455Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2024-10-03T08:15:50.367Z
/* DDL */ SELECT public.db_alter_table('C_POS_Order','ALTER TABLE public.C_POS_Order ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

