-- Column: S_HumanResourceTestGroup.IsDateSlot
-- 2024-01-12T07:13:30.044917700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587779,1764,0,20,542326,'IsDateSlot',TO_TIMESTAMP('2024-01-12 09:13:29.75','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Ressource hat eingeschränkte Tageverfügbarkeit','D',0,1,'Ressource ist nur an bestimmten Tagen verfügbar','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Tag',0,0,TO_TIMESTAMP('2024-01-12 09:13:29.75','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:13:30.052699900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587779 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:13:30.672124200Z
/* DDL */  select update_Column_Translation_From_AD_Element(1764) 
;

-- 2024-01-12T07:13:32.490179Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN IsDateSlot CHAR(1) DEFAULT ''N'' CHECK (IsDateSlot IN (''Y'',''N'')) NOT NULL')
;

-- Column: S_HumanResourceTestGroup.TimeSlotStart
-- 2024-01-12T07:14:02.990846300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587780,1760,0,24,542326,'TimeSlotStart',TO_TIMESTAMP('2024-01-12 09:14:02.853','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Start des Zeitabschnittes','D',0,7,'Startzeitpunkt für Zeitabschnitt','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Startzeitpunkt',0,0,TO_TIMESTAMP('2024-01-12 09:14:02.853','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:14:02.992950800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:14:03.519369100Z
/* DDL */  select update_Column_Translation_From_AD_Element(1760) 
;

-- 2024-01-12T07:14:04.936622Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN TimeSlotStart TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: S_HumanResourceTestGroup.TimeSlotEnd
-- 2024-01-12T07:14:15.099479500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587781,1759,0,24,542326,'TimeSlotEnd',TO_TIMESTAMP('2024-01-12 09:14:14.965','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Ende des Zeitabschnittes','D',0,7,'Endzeitpunkt für Zeitabschnitt','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Endzeitpunkt',0,0,TO_TIMESTAMP('2024-01-12 09:14:14.965','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:14:15.102104200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587781 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:14:15.623223100Z
/* DDL */  select update_Column_Translation_From_AD_Element(1759) 
;

-- 2024-01-12T07:14:17.006125700Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN TimeSlotEnd TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: S_HumanResourceTestGroup.IsTimeSlot
-- 2024-01-12T07:14:38.013656700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587782,1768,0,20,542326,'IsTimeSlot',TO_TIMESTAMP('2024-01-12 09:14:36.863','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Ressource hat eingeschränkte zeitliche Verfügbarkeit','D',0,1,'Ressource ist nur zu bestimmten Zeiten verfügbar','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zeitabschnitt',0,0,TO_TIMESTAMP('2024-01-12 09:14:36.863','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:14:38.015741300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:14:38.534913Z
/* DDL */  select update_Column_Translation_From_AD_Element(1768) 
;

-- 2024-01-12T07:14:39.961473100Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN IsTimeSlot CHAR(1) DEFAULT ''N'' CHECK (IsTimeSlot IN (''Y'',''N'')) NOT NULL')
;

-- Column: S_HumanResourceTestGroup.OnMonday
-- 2024-01-12T07:15:18.744615400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587783,1770,0,20,542326,'OnMonday',TO_TIMESTAMP('2024-01-12 09:15:18.597','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Montags verfügbar','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Montag',0,0,TO_TIMESTAMP('2024-01-12 09:15:18.597','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:15:18.747042800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:15:19.306379300Z
/* DDL */  select update_Column_Translation_From_AD_Element(1770) 
;

-- 2024-01-12T07:15:20.702352200Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN OnMonday CHAR(1) DEFAULT ''Y'' CHECK (OnMonday IN (''Y'',''N'')) NOT NULL')
;

-- Column: S_HumanResourceTestGroup.OnTuesday
-- 2024-01-12T07:15:32.796395500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587784,1774,0,20,542326,'OnTuesday',TO_TIMESTAMP('2024-01-12 09:15:32.654','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Dienstags verfügbar','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Dienstag',0,0,TO_TIMESTAMP('2024-01-12 09:15:32.654','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:15:32.799003300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:15:33.325669200Z
/* DDL */  select update_Column_Translation_From_AD_Element(1774) 
;

-- 2024-01-12T07:15:34.657539400Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN OnTuesday CHAR(1) DEFAULT ''Y'' CHECK (OnTuesday IN (''Y'',''N'')) NOT NULL')
;

-- Column: S_HumanResourceTestGroup.OnWednesday
-- 2024-01-12T07:15:45.982664500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587785,1775,0,20,542326,'OnWednesday',TO_TIMESTAMP('2024-01-12 09:15:45.843','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Mittwochs verfügbar','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Mittwoch',0,0,TO_TIMESTAMP('2024-01-12 09:15:45.843','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:15:45.984746900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:15:46.506227800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1775) 
;

-- 2024-01-12T07:15:47.884572800Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN OnWednesday CHAR(1) DEFAULT ''Y'' CHECK (OnWednesday IN (''Y'',''N'')) NOT NULL')
;

-- Column: S_HumanResourceTestGroup.OnThursday
-- 2024-01-12T07:15:58.077201300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587786,1773,0,20,542326,'OnThursday',TO_TIMESTAMP('2024-01-12 09:15:57.94','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Donnerstags verfügbar','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Donnerstag',0,0,TO_TIMESTAMP('2024-01-12 09:15:57.94','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:15:58.079784100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:15:58.588699100Z
/* DDL */  select update_Column_Translation_From_AD_Element(1773) 
;

-- Column: S_HumanResourceTestGroup.OnFriday
-- 2024-01-12T07:16:08.474273100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587787,1769,0,20,542326,'OnFriday',TO_TIMESTAMP('2024-01-12 09:16:07.327','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Freitags verfügbar','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Freitag',0,0,TO_TIMESTAMP('2024-01-12 09:16:07.327','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:16:08.476362500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:16:09.023064800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1769) 
;

-- Column: S_HumanResourceTestGroup.OnSaturday
-- 2024-01-12T07:16:21.007646300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587788,1771,0,20,542326,'OnSaturday',TO_TIMESTAMP('2024-01-12 09:16:19.859','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Samstags verfügbar','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Samstag',0,0,TO_TIMESTAMP('2024-01-12 09:16:19.859','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:16:21.009727600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:16:21.566369700Z
/* DDL */  select update_Column_Translation_From_AD_Element(1771) 
;

-- 2024-01-12T07:16:23.626252900Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN OnSaturday CHAR(1) DEFAULT ''Y'' CHECK (OnSaturday IN (''Y'',''N'')) NOT NULL')
;

-- Column: S_HumanResourceTestGroup.OnSunday
-- 2024-01-12T07:16:36.726834500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587789,1772,0,20,542326,'OnSunday',TO_TIMESTAMP('2024-01-12 09:16:36.6','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Sonntags verfügbar','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Sonntag',0,0,TO_TIMESTAMP('2024-01-12 09:16:36.6','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-12T07:16:36.728923500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-12T07:16:37.249082Z
/* DDL */  select update_Column_Translation_From_AD_Element(1772) 
;

-- 2024-01-12T07:16:39.593044300Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN OnSunday CHAR(1) DEFAULT ''Y'' CHECK (OnSunday IN (''Y'',''N'')) NOT NULL')
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Tag
-- Column: S_HumanResourceTestGroup.IsDateSlot
-- 2024-01-12T07:17:28.392148100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587779,723802,0,546958,TO_TIMESTAMP('2024-01-12 09:17:28.203','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource hat eingeschränkte Tageverfügbarkeit',1,'D','Ressource ist nur an bestimmten Tagen verfügbar','Y','N','N','N','N','N','N','N','Tag',TO_TIMESTAMP('2024-01-12 09:17:28.203','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:28.396338Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:28.399995700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1764) 
;

-- 2024-01-12T07:17:28.413958700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723802
;

-- 2024-01-12T07:17:28.420353200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723802)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Startzeitpunkt
-- Column: S_HumanResourceTestGroup.TimeSlotStart
-- 2024-01-12T07:17:28.542867400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587780,723803,0,546958,TO_TIMESTAMP('2024-01-12 09:17:28.427','YYYY-MM-DD HH24:MI:SS.US'),100,'Start des Zeitabschnittes',7,'D','Startzeitpunkt für Zeitabschnitt','Y','N','N','N','N','N','N','N','Startzeitpunkt',TO_TIMESTAMP('2024-01-12 09:17:28.427','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:28.544944900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:28.546535900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1760) 
;

-- 2024-01-12T07:17:28.549646300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723803
;

-- 2024-01-12T07:17:28.550693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723803)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Endzeitpunkt
-- Column: S_HumanResourceTestGroup.TimeSlotEnd
-- 2024-01-12T07:17:28.663942700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587781,723804,0,546958,TO_TIMESTAMP('2024-01-12 09:17:28.553','YYYY-MM-DD HH24:MI:SS.US'),100,'Ende des Zeitabschnittes',7,'D','Endzeitpunkt für Zeitabschnitt','Y','N','N','N','N','N','N','N','Endzeitpunkt',TO_TIMESTAMP('2024-01-12 09:17:28.553','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:28.666077400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:28.668159600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1759) 
;

-- 2024-01-12T07:17:28.671834100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723804
;

-- 2024-01-12T07:17:28.672875400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723804)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Zeitabschnitt
-- Column: S_HumanResourceTestGroup.IsTimeSlot
-- 2024-01-12T07:17:28.786795600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587782,723805,0,546958,TO_TIMESTAMP('2024-01-12 09:17:28.676','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource hat eingeschränkte zeitliche Verfügbarkeit',1,'D','Ressource ist nur zu bestimmten Zeiten verfügbar','Y','N','N','N','N','N','N','N','Zeitabschnitt',TO_TIMESTAMP('2024-01-12 09:17:28.676','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:28.788616100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:28.790185800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1768) 
;

-- 2024-01-12T07:17:28.793822200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723805
;

-- 2024-01-12T07:17:28.794869800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723805)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Montag
-- Column: S_HumanResourceTestGroup.OnMonday
-- 2024-01-12T07:17:28.910677600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587783,723806,0,546958,TO_TIMESTAMP('2024-01-12 09:17:28.797','YYYY-MM-DD HH24:MI:SS.US'),100,'Montags verfügbar',1,'D','Y','N','N','N','N','N','N','N','Montag',TO_TIMESTAMP('2024-01-12 09:17:28.797','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:28.912760Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:28.915381Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1770) 
;

-- 2024-01-12T07:17:28.922677600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723806
;

-- 2024-01-12T07:17:28.923710Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723806)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Dienstag
-- Column: S_HumanResourceTestGroup.OnTuesday
-- 2024-01-12T07:17:29.039835200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587784,723807,0,546958,TO_TIMESTAMP('2024-01-12 09:17:28.926','YYYY-MM-DD HH24:MI:SS.US'),100,'Dienstags verfügbar',1,'D','Y','N','N','N','N','N','N','N','Dienstag',TO_TIMESTAMP('2024-01-12 09:17:28.926','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:29.042511900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:29.044602700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1774) 
;

-- 2024-01-12T07:17:29.052924700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723807
;

-- 2024-01-12T07:17:29.053443Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723807)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Mittwoch
-- Column: S_HumanResourceTestGroup.OnWednesday
-- 2024-01-12T07:17:29.166143700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587785,723808,0,546958,TO_TIMESTAMP('2024-01-12 09:17:29.057','YYYY-MM-DD HH24:MI:SS.US'),100,'Mittwochs verfügbar',1,'D','Y','N','N','N','N','N','N','N','Mittwoch',TO_TIMESTAMP('2024-01-12 09:17:29.057','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:29.168238100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:29.170323Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1775) 
;

-- 2024-01-12T07:17:29.173969600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723808
;

-- 2024-01-12T07:17:29.174492300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723808)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Donnerstag
-- Column: S_HumanResourceTestGroup.OnThursday
-- 2024-01-12T07:17:29.287970300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587786,723809,0,546958,TO_TIMESTAMP('2024-01-12 09:17:29.177','YYYY-MM-DD HH24:MI:SS.US'),100,'Donnerstags verfügbar',1,'D','Y','N','N','N','N','N','N','N','Donnerstag',TO_TIMESTAMP('2024-01-12 09:17:29.177','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:29.290623800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:29.292186Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1773) 
;

-- 2024-01-12T07:17:29.296852200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723809
;

-- 2024-01-12T07:17:29.297372200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723809)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Freitag
-- Column: S_HumanResourceTestGroup.OnFriday
-- 2024-01-12T07:17:29.415050100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587787,723810,0,546958,TO_TIMESTAMP('2024-01-12 09:17:29.3','YYYY-MM-DD HH24:MI:SS.US'),100,'Freitags verfügbar',1,'D','Y','N','N','N','N','N','N','N','Freitag',TO_TIMESTAMP('2024-01-12 09:17:29.3','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:29.416904400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:29.418474600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1769) 
;

-- 2024-01-12T07:17:29.422134700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723810
;

-- 2024-01-12T07:17:29.423186400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723810)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Samstag
-- Column: S_HumanResourceTestGroup.OnSaturday
-- 2024-01-12T07:17:29.528200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587788,723811,0,546958,TO_TIMESTAMP('2024-01-12 09:17:29.425','YYYY-MM-DD HH24:MI:SS.US'),100,'Samstags verfügbar',1,'D','Y','N','N','N','N','N','N','N','Samstag',TO_TIMESTAMP('2024-01-12 09:17:29.425','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:29.530926200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:29.532491500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1771) 
;

-- 2024-01-12T07:17:29.536643400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723811
;

-- 2024-01-12T07:17:29.537164800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723811)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Sonntag
-- Column: S_HumanResourceTestGroup.OnSunday
-- 2024-01-12T07:17:29.656444Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587789,723812,0,546958,TO_TIMESTAMP('2024-01-12 09:17:29.539','YYYY-MM-DD HH24:MI:SS.US'),100,'Sonntags verfügbar',1,'D','Y','N','N','N','N','N','N','N','Sonntag',TO_TIMESTAMP('2024-01-12 09:17:29.539','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-12T07:17:29.658552200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-12T07:17:29.660126700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1772) 
;

-- 2024-01-12T07:17:29.664302300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723812
;

-- 2024-01-12T07:17:29.665354200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723812)
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Montag
-- Column: S_HumanResourceTestGroup.OnMonday
-- 2024-01-12T07:20:20.319634600Z
UPDATE AD_Field SET DisplayLogic='@IsDateSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:20:20.319','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723806
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Dienstag
-- Column: S_HumanResourceTestGroup.OnTuesday
-- 2024-01-12T07:20:22.427129800Z
UPDATE AD_Field SET DisplayLogic='@IsDateSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:20:22.426','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723807
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Mittwoch
-- Column: S_HumanResourceTestGroup.OnWednesday
-- 2024-01-12T07:20:23.971019100Z
UPDATE AD_Field SET DisplayLogic='@IsDateSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:20:23.97','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723808
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Donnerstag
-- Column: S_HumanResourceTestGroup.OnThursday
-- 2024-01-12T07:20:25.490943600Z
UPDATE AD_Field SET DisplayLogic='@IsDateSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:20:25.49','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723809
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Freitag
-- Column: S_HumanResourceTestGroup.OnFriday
-- 2024-01-12T07:20:26.999079800Z
UPDATE AD_Field SET DisplayLogic='@IsDateSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:20:26.999','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723810
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Samstag
-- Column: S_HumanResourceTestGroup.OnSaturday
-- 2024-01-12T07:20:28.593946Z
UPDATE AD_Field SET DisplayLogic='@IsDateSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:20:28.593','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723811
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Sonntag
-- Column: S_HumanResourceTestGroup.OnSunday
-- 2024-01-12T07:20:31.736575Z
UPDATE AD_Field SET DisplayLogic='@IsDateSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:20:31.736','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723812
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Startzeitpunkt
-- Column: S_HumanResourceTestGroup.TimeSlotStart
-- 2024-01-12T07:21:22.197958100Z
UPDATE AD_Field SET DisplayLogic='@IsTimeSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:21:22.197','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723803
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Endzeitpunkt
-- Column: S_HumanResourceTestGroup.TimeSlotEnd
-- 2024-01-12T07:21:24.129755800Z
UPDATE AD_Field SET DisplayLogic='@IsTimeSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:21:24.129','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723804
;

-- UI Column: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20
-- UI Element Group: org
-- 2024-01-12T07:21:47.015892400Z
UPDATE AD_UI_ElementGroup SET SeqNo=900,Updated=TO_TIMESTAMP('2024-01-12 09:21:47.015','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550650
;

-- UI Column: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20
-- UI Element Group: date slot
-- 2024-01-12T07:21:57.243716100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546797,551422,TO_TIMESTAMP('2024-01-12 09:21:57.108','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','date slot',20,TO_TIMESTAMP('2024-01-12 09:21:57.108','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20
-- UI Element Group: time slot
-- 2024-01-12T07:22:04.735561900Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546797,551423,TO_TIMESTAMP('2024-01-12 09:22:04.588','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','time slot',30,TO_TIMESTAMP('2024-01-12 09:22:04.588','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Tag
-- Column: S_HumanResourceTestGroup.IsDateSlot
-- 2024-01-12T07:22:26.957379600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723802,0,546958,551422,622102,'F',TO_TIMESTAMP('2024-01-12 09:22:26.797','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource hat eingeschränkte Tageverfügbarkeit','Ressource ist nur an bestimmten Tagen verfügbar','Y','N','Y','N','N','Tag',10,0,0,TO_TIMESTAMP('2024-01-12 09:22:26.797','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Montag
-- Column: S_HumanResourceTestGroup.OnMonday
-- 2024-01-12T07:22:45.617115100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723806,0,546958,551422,622103,'F',TO_TIMESTAMP('2024-01-12 09:22:45.468','YYYY-MM-DD HH24:MI:SS.US'),100,'Montags verfügbar','Y','N','Y','N','N','Montag',20,0,0,TO_TIMESTAMP('2024-01-12 09:22:45.468','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Dienstag
-- Column: S_HumanResourceTestGroup.OnTuesday
-- 2024-01-12T07:22:57.306599100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723807,0,546958,551422,622104,'F',TO_TIMESTAMP('2024-01-12 09:22:57.152','YYYY-MM-DD HH24:MI:SS.US'),100,'Dienstags verfügbar','Y','N','Y','N','N','Dienstag',30,0,0,TO_TIMESTAMP('2024-01-12 09:22:57.152','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Mittwoch
-- Column: S_HumanResourceTestGroup.OnWednesday
-- 2024-01-12T07:23:08.504949600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723808,0,546958,551422,622105,'F',TO_TIMESTAMP('2024-01-12 09:23:08.353','YYYY-MM-DD HH24:MI:SS.US'),100,'Mittwochs verfügbar','Y','N','Y','N','N','Mittwoch',40,0,0,TO_TIMESTAMP('2024-01-12 09:23:08.353','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Donnerstag
-- Column: S_HumanResourceTestGroup.OnThursday
-- 2024-01-12T07:23:17.740100300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723809,0,546958,551422,622106,'F',TO_TIMESTAMP('2024-01-12 09:23:17.59','YYYY-MM-DD HH24:MI:SS.US'),100,'Donnerstags verfügbar','Y','N','Y','N','N','Donnerstag',50,0,0,TO_TIMESTAMP('2024-01-12 09:23:17.59','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Freitag
-- Column: S_HumanResourceTestGroup.OnFriday
-- 2024-01-12T07:23:25.998683200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723810,0,546958,551422,622107,'F',TO_TIMESTAMP('2024-01-12 09:23:25.858','YYYY-MM-DD HH24:MI:SS.US'),100,'Freitags verfügbar','Y','N','Y','N','N','Freitag',60,0,0,TO_TIMESTAMP('2024-01-12 09:23:25.858','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Samstag
-- Column: S_HumanResourceTestGroup.OnSaturday
-- 2024-01-12T07:23:38.750465800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723811,0,546958,551422,622108,'F',TO_TIMESTAMP('2024-01-12 09:23:38.599','YYYY-MM-DD HH24:MI:SS.US'),100,'Samstags verfügbar','Y','N','Y','N','N','Samstag',70,0,0,TO_TIMESTAMP('2024-01-12 09:23:38.599','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> date slot.Sonntag
-- Column: S_HumanResourceTestGroup.OnSunday
-- 2024-01-12T07:23:46.659310300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723812,0,546958,551422,622109,'F',TO_TIMESTAMP('2024-01-12 09:23:46.51','YYYY-MM-DD HH24:MI:SS.US'),100,'Sonntags verfügbar','Y','N','Y','N','N','Sonntag',80,0,0,TO_TIMESTAMP('2024-01-12 09:23:46.51','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> time slot.Zeitabschnitt
-- Column: S_HumanResourceTestGroup.IsTimeSlot
-- 2024-01-12T07:24:07.536302800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723805,0,546958,551423,622110,'F',TO_TIMESTAMP('2024-01-12 09:24:06.384','YYYY-MM-DD HH24:MI:SS.US'),100,'Ressource hat eingeschränkte zeitliche Verfügbarkeit','Ressource ist nur zu bestimmten Zeiten verfügbar','Y','N','Y','N','N','Zeitabschnitt',10,0,0,TO_TIMESTAMP('2024-01-12 09:24:06.384','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> time slot.Startzeitpunkt
-- Column: S_HumanResourceTestGroup.TimeSlotStart
-- 2024-01-12T07:24:14.877811100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723803,0,546958,551423,622111,'F',TO_TIMESTAMP('2024-01-12 09:24:14.738','YYYY-MM-DD HH24:MI:SS.US'),100,'Start des Zeitabschnittes','Startzeitpunkt für Zeitabschnitt','Y','N','Y','N','N','Startzeitpunkt',20,0,0,TO_TIMESTAMP('2024-01-12 09:24:14.738','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> time slot.Endzeitpunkt
-- Column: S_HumanResourceTestGroup.TimeSlotEnd
-- 2024-01-12T07:24:22.388551800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723804,0,546958,551423,622112,'F',TO_TIMESTAMP('2024-01-12 09:24:22.232','YYYY-MM-DD HH24:MI:SS.US'),100,'Ende des Zeitabschnittes','Endzeitpunkt für Zeitabschnitt','Y','N','Y','N','N','Endzeitpunkt',30,0,0,TO_TIMESTAMP('2024-01-12 09:24:22.232','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 10 -> default.Kapazität in Stunden
-- Column: S_HumanResourceTestGroup.CapacityInHours
-- 2024-01-12T07:34:37.135035500Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617284
;

-- 2024-01-12T07:34:37.138151300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715053
;

-- Field: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> Kapazität in Stunden
-- Column: S_HumanResourceTestGroup.CapacityInHours
-- 2024-01-12T07:34:37.143368300Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=715053
;

-- 2024-01-12T07:34:37.150878500Z
DELETE FROM AD_Field WHERE AD_Field_ID=715053
;

-- 2024-01-12T07:34:37.153999100Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE S_HumanResourceTestGroup DROP COLUMN IF EXISTS CapacityInHours')
;

-- Column: S_HumanResourceTestGroup.CapacityInHours
-- 2024-01-12T07:34:37.182217700Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586509
;

-- 2024-01-12T07:34:37.186910900Z
DELETE FROM AD_Column WHERE AD_Column_ID=586509
;

-- 2024-01-12T07:37:30.355429800Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN OnThursday CHAR(1) DEFAULT ''Y'' CHECK (OnThursday IN (''Y'',''N'')) NOT NULL')
;

-- 2024-01-12T07:37:47.457941400Z
INSERT INTO t_alter_column values('s_humanresourcetestgroup','OnMonday','CHAR(1)',null,'Y')
;

-- 2024-01-12T07:37:47.469722500Z
UPDATE S_HumanResourceTestGroup SET OnMonday='Y' WHERE OnMonday IS NULL
;

-- 2024-01-12T07:38:11.020835600Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN OnFriday CHAR(1) DEFAULT ''Y'' CHECK (OnFriday IN (''Y'',''N'')) NOT NULL')
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 10 -> default.LPG
-- Column: S_HumanResourceTestGroup.GroupIdentifier
-- 2024-01-12T07:39:41.979663500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-01-12 09:39:41.979','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617282
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 10 -> default.Name
-- Column: S_HumanResourceTestGroup.Name
-- 2024-01-12T07:39:41.988566900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-01-12 09:39:41.988','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617280
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 10 -> default.Abteilung
-- Column: S_HumanResourceTestGroup.Department
-- 2024-01-12T07:39:42.001937600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-01-12 09:39:41.997','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617281
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> flags.Aktiv
-- Column: S_HumanResourceTestGroup.IsActive
-- 2024-01-12T07:39:42.014714Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-01-12 09:39:42.014','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617285
;

-- UI Element: Prüfanlagen Gruppe(541701,D) -> Prüfanlagen Gruppe(546958,D) -> main -> 20 -> org.Organisation
-- Column: S_HumanResourceTestGroup.AD_Org_ID
-- 2024-01-12T07:39:42.023111300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-01-12 09:39:42.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617286
;

-- Column: S_HumanResourceTestGroup.TimeSlotStart
-- 2024-01-12T07:50:50.624339300Z
UPDATE AD_Column SET MandatoryLogic='@IsTimeSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:50:50.623','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587780
;

-- Column: S_HumanResourceTestGroup.TimeSlotEnd
-- 2024-01-12T07:50:59.063642900Z
UPDATE AD_Column SET MandatoryLogic='@IsTimeSlot@=Y',Updated=TO_TIMESTAMP('2024-01-12 09:50:59.063','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587781
;

