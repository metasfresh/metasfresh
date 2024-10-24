-- Table: AD_Tag
-- Table: AD_Tag
-- 2024-10-23T14:13:31.570Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542449,'N',TO_TIMESTAMP('2024-10-23 14:13:31.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Tag','NP','L','AD_Tag','DTI',TO_TIMESTAMP('2024-10-23 14:13:31.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:13:31.572Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542449 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-10-23T14:13:31.690Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556381,TO_TIMESTAMP('2024-10-23 14:13:31.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table AD_Tag',1,'Y','N','Y','Y','AD_Tag','N',1000000,TO_TIMESTAMP('2024-10-23 14:13:31.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:13:31.702Z
CREATE SEQUENCE AD_Tag_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_Tag.AD_Client_ID
-- Column: AD_Tag.AD_Client_ID
-- 2024-10-23T14:14:25.327Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589343,102,0,19,542449,'AD_Client_ID',TO_TIMESTAMP('2024-10-23 14:14:25.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-10-23 14:14:25.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:25.329Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589343 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:25.359Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_Tag.AD_Org_ID
-- Column: AD_Tag.AD_Org_ID
-- 2024-10-23T14:14:26.306Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589344,113,0,30,542449,'AD_Org_ID',TO_TIMESTAMP('2024-10-23 14:14:26.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-10-23 14:14:26.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:26.308Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589344 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:26.311Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_Tag.Created
-- Column: AD_Tag.Created
-- 2024-10-23T14:14:26.799Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589345,245,0,16,542449,'Created',TO_TIMESTAMP('2024-10-23 14:14:26.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-10-23 14:14:26.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:26.800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589345 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:26.804Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_Tag.CreatedBy
-- Column: AD_Tag.CreatedBy
-- 2024-10-23T14:14:27.325Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589346,246,0,18,110,542449,'CreatedBy',TO_TIMESTAMP('2024-10-23 14:14:27.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-10-23 14:14:27.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:27.326Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589346 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:27.329Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_Tag.IsActive
-- Column: AD_Tag.IsActive
-- 2024-10-23T14:14:27.778Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589347,348,0,20,542449,'IsActive',TO_TIMESTAMP('2024-10-23 14:14:27.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-10-23 14:14:27.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:27.779Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589347 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:27.782Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_Tag.Updated
-- Column: AD_Tag.Updated
-- 2024-10-23T14:14:28.232Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589348,607,0,16,542449,'Updated',TO_TIMESTAMP('2024-10-23 14:14:28.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-10-23 14:14:28.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:28.233Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589348 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:28.235Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_Tag.UpdatedBy
-- Column: AD_Tag.UpdatedBy
-- 2024-10-23T14:14:28.686Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589349,608,0,18,110,542449,'UpdatedBy',TO_TIMESTAMP('2024-10-23 14:14:28.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-10-23 14:14:28.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:28.687Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589349 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:28.689Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-10-23T14:14:29.126Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583341,0,'AD_Tag_ID',TO_TIMESTAMP('2024-10-23 14:14:29.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Tag','Tag',TO_TIMESTAMP('2024-10-23 14:14:29.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:14:29.129Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583341 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Tag.AD_Tag_ID
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-23T14:14:29.588Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589350,583341,0,13,542449,'AD_Tag_ID',TO_TIMESTAMP('2024-10-23 14:14:29.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Tag',0,0,TO_TIMESTAMP('2024-10-23 14:14:29.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:14:29.589Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589350 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:14:29.591Z
/* DDL */  select update_Column_Translation_From_AD_Element(583341) 
;

-- Name: AD_UI_Element labels
-- 2024-10-23T14:21:29.680Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540692,'AD_UI_Element.AD_UI_ElementType = ''L''',TO_TIMESTAMP('2024-10-23 14:21:29.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','AD_UI_Element labels','S',TO_TIMESTAMP('2024-10-23 14:21:29.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: AD_Tag.AD_UI_Element_ID
-- Column: AD_Tag.AD_UI_Element_ID
-- 2024-10-23T14:24:12.822Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589351,543156,0,30,542449,540692,'AD_UI_Element_ID',TO_TIMESTAMP('2024-10-23 14:24:12.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'UI Element',0,0,TO_TIMESTAMP('2024-10-23 14:24:12.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:24:12.824Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589351 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:24:12.827Z
/* DDL */  select update_Column_Translation_From_AD_Element(543156) 
;

-- Column: AD_Tag.Value
-- Column: AD_Tag.Value
-- 2024-10-23T14:25:03.262Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589352,620,0,10,542449,'Value',TO_TIMESTAMP('2024-10-23 14:25:03.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',0,255,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2024-10-23 14:25:03.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:25:03.264Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589352 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:25:03.268Z
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- Column: AD_Tag.Name
-- Column: AD_Tag.Name
-- 2024-10-23T14:25:26.178Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589353,469,0,10,542449,'Name',TO_TIMESTAMP('2024-10-23 14:25:26.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,255,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2024-10-23 14:25:26.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-23T14:25:26.180Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589353 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-23T14:25:26.182Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2024-10-23T14:25:35.495Z
/* DDL */ CREATE TABLE public.AD_Tag (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_UI_Element_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, AD_Tag_ID NUMERIC(10) NOT NULL, Name VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(255), CONSTRAINT ADUIElement_MTag FOREIGN KEY (AD_UI_Element_ID) REFERENCES public.AD_UI_Element DEFERRABLE INITIALLY DEFERRED, CONSTRAINT AD_Tag_Key PRIMARY KEY (AD_Tag_ID))
;

-- 2024-10-23T14:27:31.153Z
INSERT INTO t_alter_column values('AD_Tag','Name','VARCHAR(255)',null,null)
;

-- Window: Tag, InternalName=null
-- Window: Tag, InternalName=null
-- 2024-10-23T14:28:17.730Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583341,0,541829,TO_TIMESTAMP('2024-10-23 14:28:17.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Tag','N',TO_TIMESTAMP('2024-10-23 14:28:17.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2024-10-23T14:28:17.732Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541829 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-10-23T14:28:17.735Z
/* DDL */  select update_window_translation_from_ad_element(583341) 
;

-- 2024-10-23T14:28:17.745Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541829
;

-- 2024-10-23T14:28:17.749Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541829)
;

-- Tab: Tag -> Tag
-- Table: AD_Tag
-- Tab: Tag(541829,D) -> Tag
-- Table: AD_Tag
-- 2024-10-23T14:28:49.114Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583341,0,547625,542449,541829,'Y',TO_TIMESTAMP('2024-10-23 14:28:48.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_Tag','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Tag','N',10,0,TO_TIMESTAMP('2024-10-23 14:28:48.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:28:49.116Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547625 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-23T14:28:49.119Z
/* DDL */  select update_tab_translation_from_ad_element(583341) 
;

-- 2024-10-23T14:28:49.123Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547625)
;

-- Field: Tag -> Tag -> Mandant
-- Column: AD_Tag.AD_Client_ID
-- Field: Tag(541829,D) -> Tag(547625,D) -> Mandant
-- Column: AD_Tag.AD_Client_ID
-- 2024-10-23T14:29:11.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589343,731982,0,547625,TO_TIMESTAMP('2024-10-23 14:29:11.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-23 14:29:11.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:11.432Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:11.434Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-23T14:29:12.403Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731982
;

-- 2024-10-23T14:29:12.404Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731982)
;

-- Field: Tag -> Tag -> Sektion
-- Column: AD_Tag.AD_Org_ID
-- Field: Tag(541829,D) -> Tag(547625,D) -> Sektion
-- Column: AD_Tag.AD_Org_ID
-- 2024-10-23T14:29:12.498Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589344,731983,0,547625,TO_TIMESTAMP('2024-10-23 14:29:12.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-10-23 14:29:12.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:12.499Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:12.500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-23T14:29:12.667Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731983
;

-- 2024-10-23T14:29:12.667Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731983)
;

-- Field: Tag -> Tag -> Erstellt
-- Column: AD_Tag.Created
-- Field: Tag(541829,D) -> Tag(547625,D) -> Erstellt
-- Column: AD_Tag.Created
-- 2024-10-23T14:29:12.756Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589345,731984,0,547625,TO_TIMESTAMP('2024-10-23 14:29:12.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-10-23 14:29:12.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:12.757Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:12.759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2024-10-23T14:29:12.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731984
;

-- 2024-10-23T14:29:12.884Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731984)
;

-- Field: Tag -> Tag -> Erstellt durch
-- Column: AD_Tag.CreatedBy
-- Field: Tag(541829,D) -> Tag(547625,D) -> Erstellt durch
-- Column: AD_Tag.CreatedBy
-- 2024-10-23T14:29:12.976Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589346,731985,0,547625,TO_TIMESTAMP('2024-10-23 14:29:12.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2024-10-23 14:29:12.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:12.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:12.978Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2024-10-23T14:29:13.023Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731985
;

-- 2024-10-23T14:29:13.024Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731985)
;

-- Field: Tag -> Tag -> Aktiv
-- Column: AD_Tag.IsActive
-- Field: Tag(541829,D) -> Tag(547625,D) -> Aktiv
-- Column: AD_Tag.IsActive
-- 2024-10-23T14:29:13.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589347,731986,0,547625,TO_TIMESTAMP('2024-10-23 14:29:13.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-23 14:29:13.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:13.116Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:13.118Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-23T14:29:13.257Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731986
;

-- 2024-10-23T14:29:13.258Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731986)
;

-- Field: Tag -> Tag -> Aktualisiert
-- Column: AD_Tag.Updated
-- Field: Tag(541829,D) -> Tag(547625,D) -> Aktualisiert
-- Column: AD_Tag.Updated
-- 2024-10-23T14:29:13.352Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589348,731987,0,547625,TO_TIMESTAMP('2024-10-23 14:29:13.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2024-10-23 14:29:13.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:13.353Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:13.354Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2024-10-23T14:29:13.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731987
;

-- 2024-10-23T14:29:13.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731987)
;

-- Field: Tag -> Tag -> Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- Field: Tag(541829,D) -> Tag(547625,D) -> Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- 2024-10-23T14:29:13.505Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589349,731988,0,547625,TO_TIMESTAMP('2024-10-23 14:29:13.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2024-10-23 14:29:13.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:13.506Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:13.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2024-10-23T14:29:13.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731988
;

-- 2024-10-23T14:29:13.538Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731988)
;

-- Field: Tag -> Tag -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- Field: Tag(541829,D) -> Tag(547625,D) -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-23T14:29:13.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589350,731989,0,547625,TO_TIMESTAMP('2024-10-23 14:29:13.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Tag',TO_TIMESTAMP('2024-10-23 14:29:13.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:13.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:13.643Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583341) 
;

-- 2024-10-23T14:29:13.646Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731989
;

-- 2024-10-23T14:29:13.647Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731989)
;

-- Field: Tag -> Tag -> UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- Field: Tag(541829,D) -> Tag(547625,D) -> UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- 2024-10-23T14:29:13.738Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589351,731990,0,547625,TO_TIMESTAMP('2024-10-23 14:29:13.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','Y','N','N','N','N','N','UI Element',TO_TIMESTAMP('2024-10-23 14:29:13.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:13.739Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:13.740Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543156) 
;

-- 2024-10-23T14:29:13.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731990
;

-- 2024-10-23T14:29:13.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731990)
;

-- Field: Tag -> Tag -> Suchschlüssel
-- Column: AD_Tag.Value
-- Field: Tag(541829,D) -> Tag(547625,D) -> Suchschlüssel
-- Column: AD_Tag.Value
-- 2024-10-23T14:29:13.829Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589352,731991,0,547625,TO_TIMESTAMP('2024-10-23 14:29:13.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',255,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2024-10-23 14:29:13.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:13.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:13.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2024-10-23T14:29:13.862Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731991
;

-- 2024-10-23T14:29:13.863Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731991)
;

-- Field: Tag -> Tag -> Name
-- Column: AD_Tag.Name
-- Field: Tag(541829,D) -> Tag(547625,D) -> Name
-- Column: AD_Tag.Name
-- 2024-10-23T14:29:13.953Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589353,731992,0,547625,TO_TIMESTAMP('2024-10-23 14:29:13.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','Y','N','N','N','N','N','Name',TO_TIMESTAMP('2024-10-23 14:29:13.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:29:13.954Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T14:29:13.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2024-10-23T14:29:14.095Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731992
;

-- 2024-10-23T14:29:14.096Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731992)
;

-- Tab: Tag(541829,D) -> Tag(547625,D)
-- UI Section: main
-- 2024-10-23T14:29:29.087Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547625,546210,TO_TIMESTAMP('2024-10-23 14:29:28.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-10-23 14:29:28.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-10-23T14:29:29.089Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546210 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Tag(541829,D) -> Tag(547625,D) -> main
-- UI Column: 10
-- 2024-10-23T14:29:29.225Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547597,546210,TO_TIMESTAMP('2024-10-23 14:29:29.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-10-23 14:29:29.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Tag(541829,D) -> Tag(547625,D) -> main
-- UI Column: 20
-- 2024-10-23T14:29:29.310Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547598,546210,TO_TIMESTAMP('2024-10-23 14:29:29.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-10-23 14:29:29.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Tag(541829,D) -> Tag(547625,D) -> main -> 10
-- UI Element Group: default
-- 2024-10-23T14:29:29.443Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547597,552048,TO_TIMESTAMP('2024-10-23 14:29:29.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-10-23 14:29:29.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Mandant
-- Column: AD_Tag.AD_Client_ID
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Mandant
-- Column: AD_Tag.AD_Client_ID
-- 2024-10-23T14:29:29.597Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731982,0,547625,552048,626220,'F',TO_TIMESTAMP('2024-10-23 14:29:29.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2024-10-23 14:29:29.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Sektion
-- Column: AD_Tag.AD_Org_ID
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Sektion
-- Column: AD_Tag.AD_Org_ID
-- 2024-10-23T14:29:29.765Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731983,0,547625,552048,626221,'F',TO_TIMESTAMP('2024-10-23 14:29:29.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2024-10-23 14:29:29.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Erstellt
-- Column: AD_Tag.Created
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Erstellt
-- Column: AD_Tag.Created
-- 2024-10-23T14:29:29.859Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731984,0,547625,552048,626222,'F',TO_TIMESTAMP('2024-10-23 14:29:29.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','Y','N','Erstellt',30,30,0,TO_TIMESTAMP('2024-10-23 14:29:29.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Erstellt durch
-- Column: AD_Tag.CreatedBy
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_Tag.CreatedBy
-- 2024-10-23T14:29:29.959Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731985,0,547625,552048,626223,'F',TO_TIMESTAMP('2024-10-23 14:29:29.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','Y','Y','N','Erstellt durch',40,40,0,TO_TIMESTAMP('2024-10-23 14:29:29.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Aktiv
-- Column: AD_Tag.IsActive
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Aktiv
-- Column: AD_Tag.IsActive
-- 2024-10-23T14:29:30.048Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731986,0,547625,552048,626224,'F',TO_TIMESTAMP('2024-10-23 14:29:29.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2024-10-23 14:29:29.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Aktualisiert
-- Column: AD_Tag.Updated
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Aktualisiert
-- Column: AD_Tag.Updated
-- 2024-10-23T14:29:30.133Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731987,0,547625,552048,626225,'F',TO_TIMESTAMP('2024-10-23 14:29:30.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','Y','Y','N','Aktualisiert',60,60,0,TO_TIMESTAMP('2024-10-23 14:29:30.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- 2024-10-23T14:29:30.222Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731988,0,547625,552048,626226,'F',TO_TIMESTAMP('2024-10-23 14:29:30.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','Y','Y','N','Aktualisiert durch',70,70,0,TO_TIMESTAMP('2024-10-23 14:29:30.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- 2024-10-23T14:29:30.309Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731990,0,547625,552048,626227,'F',TO_TIMESTAMP('2024-10-23 14:29:30.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','UI Element',80,80,0,TO_TIMESTAMP('2024-10-23 14:29:30.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Suchschlüssel
-- Column: AD_Tag.Value
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Suchschlüssel
-- Column: AD_Tag.Value
-- 2024-10-23T14:29:30.405Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731991,0,547625,552048,626228,'F',TO_TIMESTAMP('2024-10-23 14:29:30.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',90,90,0,TO_TIMESTAMP('2024-10-23 14:29:30.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Tag -> Tag.Name
-- Column: AD_Tag.Name
-- UI Element: Tag(541829,D) -> Tag(547625,D) -> main -> 10 -> default.Name
-- Column: AD_Tag.Name
-- 2024-10-23T14:29:30.491Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731992,0,547625,552048,626229,'F',TO_TIMESTAMP('2024-10-23 14:29:30.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','Y','N','Name',100,100,0,TO_TIMESTAMP('2024-10-23 14:29:30.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: Tag
-- Action Type: W
-- Window: Tag(541829,D)
-- 2024-10-23T14:31:45.432Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583341,542183,0,541829,TO_TIMESTAMP('2024-10-23 14:31:45.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','AD_Tag','Y','N','N','N','N','Tag',TO_TIMESTAMP('2024-10-23 14:31:45.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T14:31:45.437Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542183 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-10-23T14:31:45.439Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542183, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542183)
;

-- 2024-10-23T14:31:45.449Z
/* DDL */  select update_menu_translation_from_ad_element(583341) 
;

-- Reordering children of `CRM`
-- Node name: `Request (R_Request)`
-- 2024-10-23T14:31:53.649Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- Node name: `Request (all) (R_Request)`
-- 2024-10-23T14:31:53.650Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=308 AND AD_Tree_ID=10
;

-- Node name: `Company Phone Book (AD_User)`
-- 2024-10-23T14:31:53.651Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2024-10-23T14:31:53.652Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- Node name: `Partner Export (C_BPartner_Export)`
-- 2024-10-23T14:31:53.653Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents (C_Doc_Outbound_Log)`
-- 2024-10-23T14:31:53.654Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-10-23T14:31:53.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-10-23T14:31:53.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-10-23T14:31:53.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- Node name: `Businesspartner Global ID (I_BPartner_GlobalID)`
-- 2024-10-23T14:31:53.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- Node name: `Import User (I_User)`
-- 2024-10-23T14:31:53.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- Node name: `Phone call (R_Request)`
-- 2024-10-23T14:31:53.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541896 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema (C_Phonecall_Schema)`
-- 2024-10-23T14:31:53.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema Version (C_Phonecall_Schema_Version)`
-- 2024-10-23T14:31:53.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schedule (C_Phonecall_Schedule)`
-- 2024-10-23T14:31:53.661Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- Node name: `Tag`
-- 2024-10-23T14:31:53.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542183 AND AD_Tree_ID=10
;


-- Tab: Geschäftspartner -> Tag
-- Table: AD_Tag
-- Tab: Geschäftspartner(123,D) -> Tag
-- Table: AD_Tag
-- 2024-10-23T17:27:40.099Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583341,0,547626,542449,123,'Y',TO_TIMESTAMP('2024-10-23 17:27:39.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_Tag','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Tag','N',290,0,TO_TIMESTAMP('2024-10-23 17:27:39.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:27:40.103Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547626 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-23T17:27:40.105Z
/* DDL */  select update_tab_translation_from_ad_element(583341) 
;

-- 2024-10-23T17:27:40.109Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547626)
;

-- Tab: Geschäftspartner -> Tag
-- Table: AD_Tag
-- Tab: Geschäftspartner(123,D) -> Tag
-- Table: AD_Tag
-- 2024-10-23T17:28:07.889Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2024-10-23 17:28:07.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547626
;

-- Field: Geschäftspartner -> Tag -> Mandant
-- Column: AD_Tag.AD_Client_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Mandant
-- Column: AD_Tag.AD_Client_ID
-- 2024-10-23T17:28:23.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589343,731994,0,547626,TO_TIMESTAMP('2024-10-23 17:28:23.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-23 17:28:23.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:23.410Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:23.412Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-23T17:28:23.494Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731994
;

-- 2024-10-23T17:28:23.495Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731994)
;

-- Field: Geschäftspartner -> Tag -> Sektion
-- Column: AD_Tag.AD_Org_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Sektion
-- Column: AD_Tag.AD_Org_ID
-- 2024-10-23T17:28:23.584Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589344,731995,0,547626,TO_TIMESTAMP('2024-10-23 17:28:23.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-10-23 17:28:23.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:23.585Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:23.587Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-23T17:28:23.638Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731995
;

-- 2024-10-23T17:28:23.639Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731995)
;

-- Field: Geschäftspartner -> Tag -> Erstellt
-- Column: AD_Tag.Created
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Erstellt
-- Column: AD_Tag.Created
-- 2024-10-23T17:28:23.726Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589345,731996,0,547626,TO_TIMESTAMP('2024-10-23 17:28:23.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-10-23 17:28:23.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:23.728Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:23.729Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2024-10-23T17:28:23.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731996
;

-- 2024-10-23T17:28:23.754Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731996)
;

-- Field: Geschäftspartner -> Tag -> Erstellt durch
-- Column: AD_Tag.CreatedBy
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Erstellt durch
-- Column: AD_Tag.CreatedBy
-- 2024-10-23T17:28:23.841Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589346,731997,0,547626,TO_TIMESTAMP('2024-10-23 17:28:23.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2024-10-23 17:28:23.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:23.842Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:23.844Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2024-10-23T17:28:23.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731997
;

-- 2024-10-23T17:28:23.865Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731997)
;

-- Field: Geschäftspartner -> Tag -> Aktiv
-- Column: AD_Tag.IsActive
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Aktiv
-- Column: AD_Tag.IsActive
-- 2024-10-23T17:28:23.962Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589347,731998,0,547626,TO_TIMESTAMP('2024-10-23 17:28:23.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-23 17:28:23.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:23.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:23.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-23T17:28:24.019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731998
;

-- 2024-10-23T17:28:24.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731998)
;

-- Field: Geschäftspartner -> Tag -> Aktualisiert
-- Column: AD_Tag.Updated
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Aktualisiert
-- Column: AD_Tag.Updated
-- 2024-10-23T17:28:24.109Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589348,731999,0,547626,TO_TIMESTAMP('2024-10-23 17:28:24.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2024-10-23 17:28:24.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:24.110Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:24.111Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2024-10-23T17:28:24.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731999
;

-- 2024-10-23T17:28:24.134Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731999)
;

-- Field: Geschäftspartner -> Tag -> Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- 2024-10-23T17:28:24.221Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589349,732000,0,547626,TO_TIMESTAMP('2024-10-23 17:28:24.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2024-10-23 17:28:24.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:24.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:24.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2024-10-23T17:28:24.245Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732000
;

-- 2024-10-23T17:28:24.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732000)
;

-- Field: Geschäftspartner -> Tag -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-23T17:28:24.333Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589350,732001,0,547626,TO_TIMESTAMP('2024-10-23 17:28:24.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Tag',TO_TIMESTAMP('2024-10-23 17:28:24.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:24.334Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:24.336Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583341) 
;

-- 2024-10-23T17:28:24.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732001
;

-- 2024-10-23T17:28:24.339Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732001)
;

-- Field: Geschäftspartner -> Tag -> UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- 2024-10-23T17:28:24.425Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589351,732002,0,547626,TO_TIMESTAMP('2024-10-23 17:28:24.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','Y','N','N','N','N','N','UI Element',TO_TIMESTAMP('2024-10-23 17:28:24.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:24.426Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:24.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543156) 
;

-- 2024-10-23T17:28:24.430Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732002
;

-- 2024-10-23T17:28:24.431Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732002)
;

-- Field: Geschäftspartner -> Tag -> Suchschlüssel
-- Column: AD_Tag.Value
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Suchschlüssel
-- Column: AD_Tag.Value
-- 2024-10-23T17:28:24.519Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589352,732003,0,547626,TO_TIMESTAMP('2024-10-23 17:28:24.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',255,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2024-10-23 17:28:24.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:24.520Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732003 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:24.521Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2024-10-23T17:28:24.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732003
;

-- 2024-10-23T17:28:24.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732003)
;

-- Field: Geschäftspartner -> Tag -> Name
-- Column: AD_Tag.Name
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Name
-- Column: AD_Tag.Name
-- 2024-10-23T17:28:24.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589353,732004,0,547626,TO_TIMESTAMP('2024-10-23 17:28:24.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','Y','N','N','N','N','N','Name',TO_TIMESTAMP('2024-10-23 17:28:24.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-23T17:28:24.623Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-23T17:28:24.625Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2024-10-23T17:28:24.652Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732004
;

-- 2024-10-23T17:28:24.654Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732004)
;

-- Tab: Geschäftspartner(123,D) -> Tag(547626,D)
-- UI Section: tag
-- 2024-10-23T17:29:56.687Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547626,546211,TO_TIMESTAMP('2024-10-23 17:29:56.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','tag',10,TO_TIMESTAMP('2024-10-23 17:29:56.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'tag')
;

-- 2024-10-23T17:29:56.688Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546211 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner(123,D) -> Tag(547626,D) -> tag
-- UI Column: 10
-- 2024-10-23T17:30:03.910Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547599,546211,TO_TIMESTAMP('2024-10-23 17:30:03.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-10-23 17:30:03.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Geschäftspartner(123,D) -> Tag(547626,D) -> tag -> 10
-- UI Element Group: tag
-- 2024-10-23T17:30:11.405Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547599,552049,TO_TIMESTAMP('2024-10-23 17:30:11.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','tag',10,TO_TIMESTAMP('2024-10-23 17:30:11.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner -> Tag.Tag
-- Column: AD_Tag.AD_Tag_ID
-- UI Element: Geschäftspartner(123,D) -> Tag(547626,D) -> tag -> 10 -> tag.Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-23T17:30:49.566Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732001,0,547626,552049,626232,'F',TO_TIMESTAMP('2024-10-23 17:30:49.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Tag',10,0,0,TO_TIMESTAMP('2024-10-23 17:30:49.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Tag
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Tag
-- 2024-10-23T17:31:53.534Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,220,1000013,626233,'L',TO_TIMESTAMP('2024-10-23 17:31:53.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',732001,547626,0,'Tag',41,0,0,TO_TIMESTAMP('2024-10-23 17:31:53.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: AD_Tag.Name
-- Column: AD_Tag.Name
-- 2024-10-23T17:33:13.204Z
UPDATE AD_Column SET SeqNo=2,Updated=TO_TIMESTAMP('2024-10-23 17:33:13.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589353
;

-- Column: AD_Tag.Value
-- Column: AD_Tag.Value
-- 2024-10-23T17:33:20.040Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2024-10-23 17:33:20.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589352
;

-- Tab: Geschäftspartner(123,D) -> Tag(547626,D)
-- UI Section: main
-- 2024-10-23T17:37:13.921Z
UPDATE AD_UI_Section SET Name='main', Value='main',Updated=TO_TIMESTAMP('2024-10-23 17:37:13.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Section_ID=546211
;

-- UI Column: Geschäftspartner(123,D) -> Tag(547626,D) -> main -> 10
-- UI Element Group: default
-- 2024-10-23T17:37:26.965Z
UPDATE AD_UI_ElementGroup SET Name='default', UIStyle='main',Updated=TO_TIMESTAMP('2024-10-23 17:37:26.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552049
;

-- UI Column: Geschäftspartner(123,D) -> Tag(547626,D) -> main -> 10
-- UI Element Group: default
-- 2024-10-23T17:37:51.040Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2024-10-23 17:37:51.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552049
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Tag
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Tag
-- 2024-10-23T17:39:01.920Z
UPDATE AD_UI_Element SET IsAllowFiltering='Y',Updated=TO_TIMESTAMP('2024-10-23 17:39:01.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=626233
;

-- Field: Geschäftspartner -> Tag -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-23T17:40:00.091Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-10-23 17:40:00.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=732001
;

-- UI Element: Geschäftspartner -> Tag.Tag
-- Column: AD_Tag.AD_Tag_ID
-- UI Element: Geschäftspartner(123,D) -> Tag(547626,D) -> main -> 10 -> default.Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-23T17:41:39.090Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=626232
;

-- UI Element: Geschäftspartner -> Tag.Tag
-- Column: AD_Tag.AD_Tag_ID
-- UI Element: Geschäftspartner(123,D) -> Tag(547626,D) -> main -> 10 -> default.Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-23T17:41:56.959Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732001,0,547626,552049,626234,'F',TO_TIMESTAMP('2024-10-23 17:41:56.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Tag',10,0,0,TO_TIMESTAMP('2024-10-23 17:41:56.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Geschäftspartner(123,D) -> Tag(547626,D)
-- UI Section: main
-- UI Section: Geschäftspartner(123,D) -> Tag(547626,D) -> main
-- UI Column: 10
-- UI Column: Geschäftspartner(123,D) -> Tag(547626,D) -> main -> 10
-- UI Element Group: default
-- UI Element: Geschäftspartner -> Tag.Tag
-- Column: AD_Tag.AD_Tag_ID
-- UI Element: Geschäftspartner(123,D) -> Tag(547626,D) -> main -> 10 -> default.Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-24T05:46:28.667Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=626234
;

-- 2024-10-24T05:46:28.671Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=552049
;

-- 2024-10-24T05:46:28.675Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=547599
;

-- 2024-10-24T05:46:28.676Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=546211
;

-- 2024-10-24T05:46:28.680Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=546211
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Tag
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Tag
-- 2024-10-24T05:46:52.118Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=626233
;

-- Column: C_BPartner_Attribute.AD_Tag_ID
-- Column: C_BPartner_Attribute.AD_Tag_ID
-- 2024-10-24T05:48:50.800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589355,583341,0,30,540839,'AD_Tag_ID',TO_TIMESTAMP('2024-10-24 05:48:50.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tag',0,0,TO_TIMESTAMP('2024-10-24 05:48:50.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-10-24T05:48:50.802Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589355 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-24T05:48:50.805Z
/* DDL */  select update_Column_Translation_From_AD_Element(583341) 
;

-- 2024-10-24T05:48:58.379Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Attribute','ALTER TABLE public.C_BPartner_Attribute ADD COLUMN AD_Tag_ID NUMERIC(10)')
;

-- 2024-10-24T05:48:58.388Z
ALTER TABLE C_BPartner_Attribute ADD CONSTRAINT MTag_CBPartnerAttribute FOREIGN KEY (AD_Tag_ID) REFERENCES public.AD_Tag DEFERRABLE INITIALLY DEFERRED
;

-- Tab: Geschäftspartner -> Tag
-- Table: AD_Tag
-- Tab: Geschäftspartner(123,D) -> Tag
-- Table: AD_Tag
-- 2024-10-24T05:49:23.333Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731994
;

-- Field: Geschäftspartner -> Tag -> Mandant
-- Column: AD_Tag.AD_Client_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Mandant
-- Column: AD_Tag.AD_Client_ID
-- 2024-10-24T05:49:23.336Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=731994
;

-- 2024-10-24T05:49:23.339Z
DELETE FROM AD_Field WHERE AD_Field_ID=731994
;

-- 2024-10-24T05:49:23.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731995
;

-- Field: Geschäftspartner -> Tag -> Sektion
-- Column: AD_Tag.AD_Org_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Sektion
-- Column: AD_Tag.AD_Org_ID
-- 2024-10-24T05:49:23.347Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=731995
;

-- 2024-10-24T05:49:23.350Z
DELETE FROM AD_Field WHERE AD_Field_ID=731995
;

-- 2024-10-24T05:49:23.354Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731996
;

-- Field: Geschäftspartner -> Tag -> Erstellt
-- Column: AD_Tag.Created
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Erstellt
-- Column: AD_Tag.Created
-- 2024-10-24T05:49:23.357Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=731996
;

-- 2024-10-24T05:49:23.360Z
DELETE FROM AD_Field WHERE AD_Field_ID=731996
;

-- 2024-10-24T05:49:23.365Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731997
;

-- Field: Geschäftspartner -> Tag -> Erstellt durch
-- Column: AD_Tag.CreatedBy
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Erstellt durch
-- Column: AD_Tag.CreatedBy
-- 2024-10-24T05:49:23.368Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=731997
;

-- 2024-10-24T05:49:23.371Z
DELETE FROM AD_Field WHERE AD_Field_ID=731997
;

-- 2024-10-24T05:49:23.376Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731998
;

-- Field: Geschäftspartner -> Tag -> Aktiv
-- Column: AD_Tag.IsActive
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Aktiv
-- Column: AD_Tag.IsActive
-- 2024-10-24T05:49:23.379Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=731998
;

-- 2024-10-24T05:49:23.383Z
DELETE FROM AD_Field WHERE AD_Field_ID=731998
;

-- 2024-10-24T05:49:23.388Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731999
;

-- Field: Geschäftspartner -> Tag -> Aktualisiert
-- Column: AD_Tag.Updated
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Aktualisiert
-- Column: AD_Tag.Updated
-- 2024-10-24T05:49:23.391Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=731999
;

-- 2024-10-24T05:49:23.394Z
DELETE FROM AD_Field WHERE AD_Field_ID=731999
;

-- 2024-10-24T05:49:23.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732000
;

-- Field: Geschäftspartner -> Tag -> Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Aktualisiert durch
-- Column: AD_Tag.UpdatedBy
-- 2024-10-24T05:49:23.401Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=732000
;

-- 2024-10-24T05:49:23.405Z
DELETE FROM AD_Field WHERE AD_Field_ID=732000
;

-- 2024-10-24T05:49:23.409Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732001
;

-- Field: Geschäftspartner -> Tag -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Tag
-- Column: AD_Tag.AD_Tag_ID
-- 2024-10-24T05:49:23.412Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=732001
;

-- 2024-10-24T05:49:23.416Z
DELETE FROM AD_Field WHERE AD_Field_ID=732001
;

-- 2024-10-24T05:49:23.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732002
;

-- Field: Geschäftspartner -> Tag -> UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> UI Element
-- Column: AD_Tag.AD_UI_Element_ID
-- 2024-10-24T05:49:23.424Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=732002
;

-- 2024-10-24T05:49:23.427Z
DELETE FROM AD_Field WHERE AD_Field_ID=732002
;

-- 2024-10-24T05:49:23.432Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732003
;

-- Field: Geschäftspartner -> Tag -> Suchschlüssel
-- Column: AD_Tag.Value
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Suchschlüssel
-- Column: AD_Tag.Value
-- 2024-10-24T05:49:23.435Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=732003
;

-- 2024-10-24T05:49:23.438Z
DELETE FROM AD_Field WHERE AD_Field_ID=732003
;

-- 2024-10-24T05:49:23.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732004
;

-- Field: Geschäftspartner -> Tag -> Name
-- Column: AD_Tag.Name
-- Field: Geschäftspartner(123,D) -> Tag(547626,D) -> Name
-- Column: AD_Tag.Name
-- 2024-10-24T05:49:23.446Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=732004
;

-- 2024-10-24T05:49:23.449Z
DELETE FROM AD_Field WHERE AD_Field_ID=732004
;

-- 2024-10-24T05:49:23.452Z
DELETE FROM  AD_Tab_Trl WHERE AD_Tab_ID=547626
;

-- 2024-10-24T05:49:23.455Z
DELETE FROM AD_Tab WHERE AD_Tab_ID=547626
;

-- Field: Geschäftspartner -> Partner Merkmale -> Tag
-- Column: C_BPartner_Attribute.AD_Tag_ID
-- Field: Geschäftspartner(123,D) -> Partner Merkmale(540877,D) -> Tag
-- Column: C_BPartner_Attribute.AD_Tag_ID
-- 2024-10-24T05:49:46.842Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589355,732005,0,540877,TO_TIMESTAMP('2024-10-24 05:49:46.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Tag',TO_TIMESTAMP('2024-10-24 05:49:46.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-10-24T05:49:46.843Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=732005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-24T05:49:46.845Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583341) 
;

-- 2024-10-24T05:49:46.849Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=732005
;

-- 2024-10-24T05:49:46.850Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(732005)

;

-- UI Element: Geschäftspartner -> Partner Merkmale.Tag
-- Column: C_BPartner_Attribute.AD_Tag_ID
-- UI Element: Geschäftspartner(123,D) -> Partner Merkmale(540877,D) -> main -> 10 -> default.Tag
-- Column: C_BPartner_Attribute.AD_Tag_ID
-- 2024-10-24T05:50:10.633Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,732005,0,540877,541549,626235,'F',TO_TIMESTAMP('2024-10-24 05:50:10.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Tag',10,0,0,TO_TIMESTAMP('2024-10-24 05:50:10.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Attribute
-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Attribute
-- 2024-10-24T05:50:42.169Z
UPDATE AD_UI_Element SET Labels_Selector_Field_ID=732005,Updated=TO_TIMESTAMP('2024-10-24 05:50:42.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=548674
;

-- Column: C_BPartner_Attribute.Attribute
-- Column: C_BPartner_Attribute.Attribute
-- 2024-10-24T05:53:42.279Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-10-24 05:53:42.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=557190
;

-- 2024-10-24T05:53:44.903Z
INSERT INTO t_alter_column values('c_bpartner_attribute','Attribute','VARCHAR(40)',null,null)
;

-- 2024-10-24T05:53:44.905Z
INSERT INTO t_alter_column values('c_bpartner_attribute','Attribute',null,'NULL',null)
;

