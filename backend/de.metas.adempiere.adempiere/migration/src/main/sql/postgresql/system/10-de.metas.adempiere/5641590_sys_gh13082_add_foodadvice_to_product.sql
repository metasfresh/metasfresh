-- 2022-05-31T14:06:57.843Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541589,TO_TIMESTAMP('2022-05-31 16:06:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Product_FoodAdvice',TO_TIMESTAMP('2022-05-31 16:06:57','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-05-31T14:06:57.862Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541589 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-05-31T14:08:52.474Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543203,TO_TIMESTAMP('2022-05-31 16:08:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vakuum',TO_TIMESTAMP('2022-05-31 16:08:52','YYYY-MM-DD HH24:MI:SS'),100,'Vakuum','Vakuum')
;

-- 2022-05-31T14:08:52.477Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543203 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-31T14:09:36.540Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543204,TO_TIMESTAMP('2022-05-31 16:09:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geräuchert',TO_TIMESTAMP('2022-05-31 16:09:36','YYYY-MM-DD HH24:MI:SS'),100,'Geraeuchert','Geräuchert')
;

-- 2022-05-31T14:09:36.541Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543204 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-31T14:09:50.567Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543205,TO_TIMESTAMP('2022-05-31 16:09:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Getrocknet',TO_TIMESTAMP('2022-05-31 16:09:50','YYYY-MM-DD HH24:MI:SS'),100,'Getrocknet','Getrocknet')
;

-- 2022-05-31T14:09:50.568Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543205 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-31T14:10:03.922Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543206,TO_TIMESTAMP('2022-05-31 16:10:03','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Mariniert',TO_TIMESTAMP('2022-05-31 16:10:03','YYYY-MM-DD HH24:MI:SS'),100,'Mariniert','Mariniert')
;

-- 2022-05-31T14:10:03.924Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543206 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-31T14:10:14.739Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543207,TO_TIMESTAMP('2022-05-31 16:10:14','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Frittiert',TO_TIMESTAMP('2022-05-31 16:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Frittiert','Frittiert')
;

-- 2022-05-31T14:10:14.740Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543207 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-31T14:10:24.754Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543208,TO_TIMESTAMP('2022-05-31 16:10:24','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Geformt',TO_TIMESTAMP('2022-05-31 16:10:24','YYYY-MM-DD HH24:MI:SS'),100,'Geformt','Geformt')
;

-- 2022-05-31T14:10:24.756Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543208 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-31T14:10:33.665Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543209,TO_TIMESTAMP('2022-05-31 16:10:33','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Gekocht',TO_TIMESTAMP('2022-05-31 16:10:33','YYYY-MM-DD HH24:MI:SS'),100,'Gekocht','Gekocht')
;

-- 2022-05-31T14:10:33.666Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543209 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-05-31T14:11:06.673Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541589,543210,TO_TIMESTAMP('2022-05-31 16:11:06','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Gekühlt Haltbar',TO_TIMESTAMP('2022-05-31 16:11:06','YYYY-MM-DD HH24:MI:SS'),100,'Gekuehlt Haltbar','Gekühlt Haltbar')
;

-- 2022-05-31T14:11:06.674Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543210 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Table: M_Product_FoodAdvice
-- 2022-05-31T14:12:23.165Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542162,'N',TO_TIMESTAMP('2022-05-31 16:12:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Product Foodadvice','NP','L','M_Product_FoodAdvice','DTI',TO_TIMESTAMP('2022-05-31 16:12:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:12:23.167Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542162 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-05-31T14:12:23.282Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555898,TO_TIMESTAMP('2022-05-31 16:12:23','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_FoodAdvice',1,'Y','N','Y','Y','M_Product_FoodAdvice','N',1000000,TO_TIMESTAMP('2022-05-31 16:12:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:12:23.295Z
CREATE SEQUENCE M_PRODUCT_FOODADVICE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Product_FoodAdvice.AD_Client_ID
-- 2022-05-31T14:12:53.850Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583257,102,0,19,542162,'AD_Client_ID',TO_TIMESTAMP('2022-05-31 16:12:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Mandant',0,0,TO_TIMESTAMP('2022-05-31 16:12:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:12:53.851Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583257 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:12:53.901Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Product_FoodAdvice.AD_Org_ID
-- 2022-05-31T14:13:05.102Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583258,113,0,19,542162,'AD_Org_ID',TO_TIMESTAMP('2022-05-31 16:13:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Sektion',0,0,TO_TIMESTAMP('2022-05-31 16:13:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:13:05.104Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583258 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:13:05.107Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Product_FoodAdvice.Created
-- 2022-05-31T14:13:14.435Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583259,245,0,16,542162,'Created',TO_TIMESTAMP('2022-05-31 16:13:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Erstellt',0,0,TO_TIMESTAMP('2022-05-31 16:13:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:13:14.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583259 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:13:14.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Product_FoodAdvice.CreatedBy
-- 2022-05-31T14:13:21.484Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583260,246,0,18,110,542162,'CreatedBy',TO_TIMESTAMP('2022-05-31 16:13:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Erstellt durch',0,0,TO_TIMESTAMP('2022-05-31 16:13:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:13:21.486Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583260 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:13:21.489Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Product_FoodAdvice.IsActive
-- 2022-05-31T14:13:28.080Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583261,348,0,20,542162,'IsActive',TO_TIMESTAMP('2022-05-31 16:13:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Aktiv',0,0,TO_TIMESTAMP('2022-05-31 16:13:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:13:28.081Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583261 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:13:28.084Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Product_FoodAdvice.Updated
-- 2022-05-31T14:13:40.503Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583262,607,0,16,542162,'Updated',TO_TIMESTAMP('2022-05-31 16:13:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Aktualisiert',0,0,TO_TIMESTAMP('2022-05-31 16:13:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:13:40.506Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583262 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:13:40.509Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Product_FoodAdvice.UpdatedBy
-- 2022-05-31T14:13:56.184Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583263,608,0,18,110,542162,'UpdatedBy',TO_TIMESTAMP('2022-05-31 16:13:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Aktualisiert durch',0,0,TO_TIMESTAMP('2022-05-31 16:13:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:13:56.185Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583263 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:13:56.188Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: M_Product_FoodAdvice.M_Product_ID
-- 2022-05-31T14:14:36.989Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583264,454,0,19,542162,'M_Product_ID',TO_TIMESTAMP('2022-05-31 16:14:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2022-05-31 16:14:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:14:36.990Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583264 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:14:36.993Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2022-05-31T14:16:09.786Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580970,0,'M_Product_FoodAdvice_ID',TO_TIMESTAMP('2022-05-31 16:16:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product FoodAdvice','Product FoodAdvice',TO_TIMESTAMP('2022-05-31 16:16:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:16:09.790Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580970 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-31T14:17:26.123Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580971,0,'M_Product_FoodAdvice',TO_TIMESTAMP('2022-05-31 16:17:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Foodadvice','Foodadvice',TO_TIMESTAMP('2022-05-31 16:17:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:17:26.124Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580971 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-31T14:17:46.809Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lebensmittelhinweise', PrintName='Lebensmittelhinweise',Updated=TO_TIMESTAMP('2022-05-31 16:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580971 AND AD_Language='de_DE'
;

-- 2022-05-31T14:17:46.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580971,'de_DE') 
;

-- 2022-05-31T14:17:46.829Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580971,'de_DE') 
;

-- 2022-05-31T14:17:46.831Z
UPDATE AD_Column SET ColumnName='M_Product_FoodAdvice', Name='Lebensmittelhinweise', Description=NULL, Help=NULL WHERE AD_Element_ID=580971
;

-- 2022-05-31T14:17:46.832Z
UPDATE AD_Process_Para SET ColumnName='M_Product_FoodAdvice', Name='Lebensmittelhinweise', Description=NULL, Help=NULL, AD_Element_ID=580971 WHERE UPPER(ColumnName)='M_PRODUCT_FOODADVICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-31T14:17:46.833Z
UPDATE AD_Process_Para SET ColumnName='M_Product_FoodAdvice', Name='Lebensmittelhinweise', Description=NULL, Help=NULL WHERE AD_Element_ID=580971 AND IsCentrallyMaintained='Y'
;

-- 2022-05-31T14:17:46.834Z
UPDATE AD_Field SET Name='Lebensmittelhinweise', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580971) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580971)
;

-- 2022-05-31T14:17:46.842Z
UPDATE AD_PrintFormatItem pi SET PrintName='Lebensmittelhinweise', Name='Lebensmittelhinweise' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580971)
;

-- 2022-05-31T14:17:46.843Z
UPDATE AD_Tab SET Name='Lebensmittelhinweise', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 580971
;

-- 2022-05-31T14:17:46.844Z
UPDATE AD_WINDOW SET Name='Lebensmittelhinweise', Description=NULL, Help=NULL WHERE AD_Element_ID = 580971
;

-- 2022-05-31T14:17:46.846Z
UPDATE AD_Menu SET   Name = 'Lebensmittelhinweise', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580971
;

-- 2022-05-31T14:17:55.541Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lebensmittelhinweise', PrintName='Lebensmittelhinweise',Updated=TO_TIMESTAMP('2022-05-31 16:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580971 AND AD_Language='de_CH'
;

-- 2022-05-31T14:17:55.542Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580971,'de_CH') 
;

-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice_ID
-- 2022-05-31T14:18:27.663Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583265,580970,0,13,542162,'M_Product_FoodAdvice_ID',TO_TIMESTAMP('2022-05-31 16:18:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Y','N','N','N','N','N','N','N','N','N',0,'Product FoodAdvice',0,0,TO_TIMESTAMP('2022-05-31 16:18:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:18:27.665Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583265 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:18:27.668Z
/* DDL */  select update_Column_Translation_From_AD_Element(580970) 
;

-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice
-- 2022-05-31T14:19:18.854Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583266,580971,0,10,542162,'M_Product_FoodAdvice',TO_TIMESTAMP('2022-05-31 16:19:18','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,30,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lebensmittelhinweise',0,0,TO_TIMESTAMP('2022-05-31 16:19:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-31T14:19:18.856Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583266 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-31T14:19:18.858Z
/* DDL */  select update_Column_Translation_From_AD_Element(580971) 
;

-- 2022-05-31T14:19:20.452Z
/* DDL */ CREATE TABLE public.M_Product_FoodAdvice (AD_Client_ID NUMERIC(10), AD_Org_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE, CreatedBy NUMERIC(10), IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_FoodAdvice VARCHAR(30), M_Product_FoodAdvice_ID NUMERIC(10) NOT NULL, M_Product_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE, UpdatedBy NUMERIC(10), CONSTRAINT M_Product_FoodAdvice_Key PRIMARY KEY (M_Product_FoodAdvice_ID), CONSTRAINT MProduct_MProductFoodAdvice FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2022-05-31T14:19:55.908Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Produkt Lebensmittelhinweise',Updated=TO_TIMESTAMP('2022-05-31 16:19:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542162
;

-- 2022-05-31T14:20:07.824Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Produkt Lebensmittelhinweise',Updated=TO_TIMESTAMP('2022-05-31 16:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542162
;

-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice
-- 2022-05-31T14:20:50.148Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541589,Updated=TO_TIMESTAMP('2022-05-31 16:20:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583266
;

-- Tab: Produkt -> Product FoodAdvice
-- Table: M_Product_FoodAdvice
-- 2022-05-31T14:22:05.041Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,580970,0,546292,542162,140,'Y',TO_TIMESTAMP('2022-05-31 16:22:04','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_Product_FoodAdvice','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Product FoodAdvice','N',310,0,TO_TIMESTAMP('2022-05-31 16:22:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:22:05.043Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546292 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-31T14:22:05.045Z
/* DDL */  select update_tab_translation_from_ad_element(580970) 
;

-- 2022-05-31T14:22:05.058Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546292)
;

-- Field: Produkt -> Product FoodAdvice -> Lebensmittelhinweise
-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice
-- 2022-05-31T14:24:21.921Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583266,698028,0,546292,0,TO_TIMESTAMP('2022-05-31 16:24:21','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Lebensmittelhinweise',0,10,0,1,1,TO_TIMESTAMP('2022-05-31 16:24:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:24:21.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T14:24:21.926Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580971) 
;

-- 2022-05-31T14:24:21.928Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698028
;

-- 2022-05-31T14:24:21.929Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698028)
;

-- Field: Produkt -> Product FoodAdvice -> Product FoodAdvice
-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice_ID
-- 2022-05-31T14:26:35.305Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583265,698029,0,546292,0,TO_TIMESTAMP('2022-05-31 16:26:35','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Product FoodAdvice',0,20,0,1,1,TO_TIMESTAMP('2022-05-31 16:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:26:35.307Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T14:26:35.308Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580970) 
;

-- 2022-05-31T14:26:35.310Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698029
;

-- 2022-05-31T14:26:35.311Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698029)
;

-- Field: Produkt -> Product FoodAdvice -> Mandant
-- Column: M_Product_FoodAdvice.AD_Client_ID
-- 2022-05-31T14:26:44.237Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583257,698030,0,546292,0,TO_TIMESTAMP('2022-05-31 16:26:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',0,30,0,1,1,TO_TIMESTAMP('2022-05-31 16:26:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:26:44.238Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T14:26:44.240Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-31T14:26:44.789Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698030
;

-- 2022-05-31T14:26:44.790Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698030)
;

-- Field: Produkt -> Product FoodAdvice -> Sektion
-- Column: M_Product_FoodAdvice.AD_Org_ID
-- 2022-05-31T14:26:53.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583258,698031,0,546292,0,TO_TIMESTAMP('2022-05-31 16:26:53','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',0,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',0,40,0,1,1,TO_TIMESTAMP('2022-05-31 16:26:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:26:53.270Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T14:26:53.271Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-31T14:26:53.611Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698031
;

-- 2022-05-31T14:26:53.612Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698031)
;

-- Field: Produkt -> Product FoodAdvice -> Aktiv
-- Column: M_Product_FoodAdvice.IsActive
-- 2022-05-31T14:27:00.911Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583261,698032,0,546292,0,TO_TIMESTAMP('2022-05-31 16:27:00','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',0,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',0,50,0,1,1,TO_TIMESTAMP('2022-05-31 16:27:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:27:00.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T14:27:00.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-31T14:27:01.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698032
;

-- 2022-05-31T14:27:01.073Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698032)
;

-- Field: Produkt -> Product FoodAdvice -> Produkt
-- Column: M_Product_FoodAdvice.M_Product_ID
-- 2022-05-31T14:27:18.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,583264,698034,0,546292,0,TO_TIMESTAMP('2022-05-31 16:27:18','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',0,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',0,60,0,1,1,TO_TIMESTAMP('2022-05-31 16:27:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:27:18.712Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=698034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T14:27:18.713Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-05-31T14:27:18.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=698034
;

-- 2022-05-31T14:27:18.743Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(698034)
;

-- Field: Produkt -> Product FoodAdvice -> Lebensmittelhinweise
-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice
-- 2022-05-31T14:27:55.579Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-05-31 16:27:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698028
;

-- Field: Produkt -> Product FoodAdvice -> Product FoodAdvice
-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice_ID
-- 2022-05-31T14:28:00.182Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-05-31 16:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698029
;

-- Field: Produkt -> Product FoodAdvice -> Mandant
-- Column: M_Product_FoodAdvice.AD_Client_ID
-- 2022-05-31T14:28:04.939Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-05-31 16:28:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698030
;

-- Field: Produkt -> Product FoodAdvice -> Sektion
-- Column: M_Product_FoodAdvice.AD_Org_ID
-- 2022-05-31T14:28:07.338Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-05-31 16:28:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698031
;

-- Field: Produkt -> Product FoodAdvice -> Aktiv
-- Column: M_Product_FoodAdvice.IsActive
-- 2022-05-31T14:28:09.979Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-05-31 16:28:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698032
;

-- Field: Produkt -> Product FoodAdvice -> Produkt
-- Column: M_Product_FoodAdvice.M_Product_ID
-- 2022-05-31T14:28:13.020Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-05-31 16:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=698034
;

-- 2022-05-31T14:29:48.996Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546292,544941,TO_TIMESTAMP('2022-05-31 16:29:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 16:29:48','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-31T14:29:48.998Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544941 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-31T14:30:01.903Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545978,544941,TO_TIMESTAMP('2022-05-31 16:30:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-31 16:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:30:16.893Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545978,549209,TO_TIMESTAMP('2022-05-31 16:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-05-31 16:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product FoodAdvice.Lebensmittelhinweise
-- 2022-05-31T14:30:53.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,546292,549209,608337,'F',TO_TIMESTAMP('2022-05-31 16:30:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lebensmittelhinweise',10,0,0,TO_TIMESTAMP('2022-05-31 16:30:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> Product FoodAdvice.Lebensmittelhinweise
-- Column: M_Product_FoodAdvice.M_Product_FoodAdvice
-- 2022-05-31T14:32:04.801Z
UPDATE AD_UI_Element SET AD_Field_ID=698028,Updated=TO_TIMESTAMP('2022-05-31 16:32:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608337
;

-- UI Element: Produkt -> Produkt.Lebensmittelhinweise
-- 2022-05-31T14:34:24.589Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,180,547431,608338,'L',TO_TIMESTAMP('2022-05-31 16:34:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','N','N',698028,546292,0,'Lebensmittelhinweise',50,0,0,TO_TIMESTAMP('2022-05-31 16:34:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T14:40:52.377Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-31 16:40:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580971 AND AD_Language='en_US'
;

-- 2022-05-31T14:40:52.384Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580971,'en_US') 
;

-- 2022-05-31T14:43:14.911Z
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-05-31 16:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542162
;

-- 2022-06-09T12:23:10.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-06-09 14:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608338
;

-- 2022-06-09T12:23:10.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-06-09 14:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000090
;

-- 2022-06-09T12:23:10.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-06-09 14:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000091
;

-- 2022-06-09T12:23:10.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-06-09 14:23:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545297
;

