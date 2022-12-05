-- Table: S_Resource_Group
-- 2022-05-23T13:15:55.568Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542153,'N',TO_TIMESTAMP('2022-05-23 16:15:55','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','Y','Y','N','Y','N','N','N',0,'Resource Group','NP','L','S_Resource_Group','DTI',TO_TIMESTAMP('2022-05-23 16:15:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-23T13:15:55.577Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542153 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-05-23T13:15:55.710Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555893,TO_TIMESTAMP('2022-05-23 16:15:55','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table S_Resource_Group',1,'Y','N','Y','Y','S_Resource_Group','N',1000000,TO_TIMESTAMP('2022-05-23 16:15:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-23T13:15:55.724Z
CREATE SEQUENCE S_RESOURCE_GROUP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: S_Resource_Group.AD_Client_ID
-- 2022-05-23T13:16:05.807Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583163,102,0,19,542153,'AD_Client_ID',TO_TIMESTAMP('2022-05-23 16:16:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-05-23 16:16:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:05.813Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583163 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:05.853Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: S_Resource_Group.AD_Org_ID
-- 2022-05-23T13:16:06.684Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583164,113,0,30,542153,'AD_Org_ID',TO_TIMESTAMP('2022-05-23 16:16:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2022-05-23 16:16:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:06.686Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:06.688Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: S_Resource_Group.Created
-- 2022-05-23T13:16:07.288Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583165,245,0,16,542153,'Created',TO_TIMESTAMP('2022-05-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-05-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:07.289Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:07.291Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: S_Resource_Group.CreatedBy
-- 2022-05-23T13:16:07.839Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583166,246,0,18,110,542153,'CreatedBy',TO_TIMESTAMP('2022-05-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-05-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:07.841Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:07.843Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: S_Resource_Group.IsActive
-- 2022-05-23T13:16:08.354Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583167,348,0,20,542153,'IsActive',TO_TIMESTAMP('2022-05-23 16:16:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-05-23 16:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:08.356Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583167 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:08.357Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: S_Resource_Group.Updated
-- 2022-05-23T13:16:08.845Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583168,607,0,16,542153,'Updated',TO_TIMESTAMP('2022-05-23 16:16:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-05-23 16:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:08.847Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583168 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:08.849Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: S_Resource_Group.UpdatedBy
-- 2022-05-23T13:16:09.362Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583169,608,0,18,110,542153,'UpdatedBy',TO_TIMESTAMP('2022-05-23 16:16:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-05-23 16:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:09.365Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583169 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:09.368Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-05-23T13:16:09.865Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580932,0,'S_Resource_Group_ID',TO_TIMESTAMP('2022-05-23 16:16:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Resource Group','Resource Group',TO_TIMESTAMP('2022-05-23 16:16:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-23T13:16:09.870Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580932 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_Resource_Group.S_Resource_Group_ID
-- 2022-05-23T13:16:10.243Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583170,580932,0,13,542153,'S_Resource_Group_ID',TO_TIMESTAMP('2022-05-23 16:16:09','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Resource Group',0,0,TO_TIMESTAMP('2022-05-23 16:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T13:16:10.245Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583170 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T13:16:10.248Z
/* DDL */  select update_Column_Translation_From_AD_Element(580932) 
;

-- Column: S_Resource_Group.Name
-- 2022-05-23T14:02:15.689Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583172,469,0,10,542153,'Name',TO_TIMESTAMP('2022-05-23 17:02:15','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2022-05-23 17:02:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T14:02:15.693Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583172 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T14:02:15.699Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: S_Resource_Group.Description
-- 2022-05-23T14:02:42.091Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583173,275,0,10,542153,'Description',TO_TIMESTAMP('2022-05-23 17:02:41','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2022-05-23 17:02:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-23T14:02:42.095Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583173 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-23T14:02:42.100Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2022-05-23T14:04:45.618Z
/* DDL */ CREATE TABLE public.S_Resource_Group (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(255) NOT NULL, S_Resource_Group_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT S_Resource_Group_Key PRIMARY KEY (S_Resource_Group_ID))
;

-- Window: Resource Group, InternalName=null
-- 2022-05-23T14:05:19.813Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,580932,0,541501,TO_TIMESTAMP('2022-05-23 17:05:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Resource Group','N',TO_TIMESTAMP('2022-05-23 17:05:19','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-05-23T14:05:19.816Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541501 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-05-23T14:05:19.820Z
/* DDL */  select update_window_translation_from_ad_element(580932) 
;

-- 2022-05-23T14:05:19.832Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541501
;

-- 2022-05-23T14:05:19.836Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541501)
;

-- Tab: Resource Group -> Resource Group
-- Table: S_Resource_Group
-- 2022-05-23T14:06:17.035Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,580932,0,546255,542153,541501,'Y',TO_TIMESTAMP('2022-05-23 17:06:16','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','S_Resource_Group','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Resource Group','N',10,0,TO_TIMESTAMP('2022-05-23 17:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-23T14:06:17.038Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546255 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-23T14:06:17.040Z
/* DDL */  select update_tab_translation_from_ad_element(580932) 
;

-- 2022-05-23T14:06:17.043Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546255)
;

-- Column: S_Resource_Group.Name
-- 2022-05-23T14:06:33.785Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-05-23 17:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583172
;

-- 2022-05-23T14:08:28.687Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,580932,541949,0,541501,TO_TIMESTAMP('2022-05-23 17:08:28','YYYY-MM-DD HH24:MI:SS'),100,'D','resourceGroup','Y','N','N','N','N','Resource Group',TO_TIMESTAMP('2022-05-23 17:08:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-23T14:08:28.689Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541949 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-05-23T14:08:28.692Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541949, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541949)
;

-- 2022-05-23T14:08:28.703Z
/* DDL */  select update_menu_translation_from_ad_element(580932) 
;

-- Reordering children of `Settings`
-- Node name: `Product Planning (PP_Product_Planning)`
-- 2022-05-23T14:08:36.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- Node name: `Product Planning Schema (M_Product_PlanningSchema)`
-- 2022-05-23T14:08:36.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- Node name: `Product Planning (M_Product)`
-- 2022-05-23T14:08:37.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- Node name: `Create Default Product Planning Data (de.metas.product.process.M_ProductPlanning_Create_Default_ProductPlanningData)`
-- 2022-05-23T14:08:37.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2022-05-23T14:08:37.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Standard Activity`
-- 2022-05-23T14:08:37.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Transitions (AD_WF_NodeNext)`
-- 2022-05-23T14:08:37.016Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- Node name: `Resource (S_Resource)`
-- 2022-05-23T14:08:37.017Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- Node name: `Resource Type (S_ResourceType)`
-- 2022-05-23T14:08:37.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- Node name: `Component Generator (PP_ComponentGenerator)`
-- 2022-05-23T14:08:37.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Activity (AD_WF_Node)`
-- 2022-05-23T14:08:37.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

-- Node name: `Resource Group`
-- 2022-05-23T14:08:37.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541949 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `Product Planning (PP_Product_Planning)`
-- 2022-05-23T14:08:47.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- Node name: `Product Planning Schema (M_Product_PlanningSchema)`
-- 2022-05-23T14:08:47.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- Node name: `Product Planning (M_Product)`
-- 2022-05-23T14:08:47.521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- Node name: `Create Default Product Planning Data (de.metas.product.process.M_ProductPlanning_Create_Default_ProductPlanningData)`
-- 2022-05-23T14:08:47.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2022-05-23T14:08:47.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Standard Activity`
-- 2022-05-23T14:08:47.523Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Transitions (AD_WF_NodeNext)`
-- 2022-05-23T14:08:47.523Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- Node name: `Resource (S_Resource)`
-- 2022-05-23T14:08:47.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- Node name: `Resource Type (S_ResourceType)`
-- 2022-05-23T14:08:47.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- Node name: `Resource Group`
-- 2022-05-23T14:08:47.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541949 AND AD_Tree_ID=10
;

-- Node name: `Component Generator (PP_ComponentGenerator)`
-- 2022-05-23T14:08:47.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Activity (AD_WF_Node)`
-- 2022-05-23T14:08:47.526Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

