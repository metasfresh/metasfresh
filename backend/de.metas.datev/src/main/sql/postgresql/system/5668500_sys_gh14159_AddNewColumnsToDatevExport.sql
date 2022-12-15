-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- 2022-12-15T08:21:39.151Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,585324,1133,0,12,540936,'TaxAmt',TO_TIMESTAMP('2022-12-15 10:21:38.93','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Steuerbetrag für diesen Beleg','de.metas.datev',10,'Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Steuerbetrag',0,TO_TIMESTAMP('2022-12-15 10:21:38.93','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-12-15T08:21:39.155Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585324 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T08:21:39.194Z
/* DDL */  select update_Column_Translation_From_AD_Element(1133) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- 2022-12-15T08:22:13.431Z
UPDATE AD_Column SET FieldLength=131089,Updated=TO_TIMESTAMP('2022-12-15 10:22:13.43','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585324
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- 2022-12-15T08:22:46.777Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,585325,316,0,12,540936,'GrandTotal',TO_TIMESTAMP('2022-12-15 10:22:46.665','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Summe über Alles zu diesem Beleg','de.metas.datev',131089,'Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Summe Gesamt',0,TO_TIMESTAMP('2022-12-15 10:22:46.665','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-12-15T08:22:46.778Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585325 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T08:22:46.781Z
/* DDL */  select update_Column_Translation_From_AD_Element(316) 
;

