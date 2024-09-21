-- Table: C_POS_Journal
-- Table: C_POS_Journal
-- 2024-09-21T20:55:24.527Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('1',0,0,0,542438,'A','N',TO_TIMESTAMP('2024-09-21 23:55:24','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.pos','N','Y','N','N','Y','N','Y','Y','N','N',0,'POS Cash Journal','NP','L','C_POS_Journal','DTI',TO_TIMESTAMP('2024-09-21 23:55:24','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-09-21T20:55:24.535Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542438 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-09-21T20:55:24.706Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556369,TO_TIMESTAMP('2024-09-21 23:55:24','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_POS_Journal',1,'Y','N','Y','Y','C_POS_Journal','N',1000000,TO_TIMESTAMP('2024-09-21 23:55:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T20:55:24.717Z
CREATE SEQUENCE C_POS_JOURNAL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_POS_Journal.AD_Client_ID
-- Column: C_POS_Journal.AD_Client_ID
-- 2024-09-21T20:55:27.881Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589122,102,0,19,542438,'AD_Client_ID',TO_TIMESTAMP('2024-09-21 23:55:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.pos',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-09-21 23:55:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:27.884Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589122 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:27.891Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_POS_Journal.AD_Org_ID
-- Column: C_POS_Journal.AD_Org_ID
-- 2024-09-21T20:55:28.746Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589123,113,0,30,542438,'AD_Org_ID',TO_TIMESTAMP('2024-09-21 23:55:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.pos',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-09-21 23:55:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:28.748Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589123 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:28.752Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_POS_Journal.Created
-- Column: C_POS_Journal.Created
-- 2024-09-21T20:55:29.507Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589124,245,0,16,542438,'Created',TO_TIMESTAMP('2024-09-21 23:55:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.pos',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-09-21 23:55:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:29.510Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:29.516Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_POS_Journal.CreatedBy
-- Column: C_POS_Journal.CreatedBy
-- 2024-09-21T20:55:30.301Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589125,246,0,18,110,542438,'CreatedBy',TO_TIMESTAMP('2024-09-21 23:55:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.pos',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-09-21 23:55:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:30.303Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:30.307Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_POS_Journal.IsActive
-- Column: C_POS_Journal.IsActive
-- 2024-09-21T20:55:31.034Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589126,348,0,20,542438,'IsActive',TO_TIMESTAMP('2024-09-21 23:55:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.pos',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-09-21 23:55:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:31.037Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589126 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:31.040Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_POS_Journal.Updated
-- Column: C_POS_Journal.Updated
-- 2024-09-21T20:55:31.700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589127,607,0,16,542438,'Updated',TO_TIMESTAMP('2024-09-21 23:55:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.pos',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-09-21 23:55:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:31.703Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589127 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:31.706Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_POS_Journal.UpdatedBy
-- Column: C_POS_Journal.UpdatedBy
-- 2024-09-21T20:55:32.353Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589128,608,0,18,110,542438,'UpdatedBy',TO_TIMESTAMP('2024-09-21 23:55:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.pos',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-09-21 23:55:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:32.356Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589128 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:32.359Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-09-21T20:55:32.921Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583272,0,'C_POS_Journal_ID',TO_TIMESTAMP('2024-09-21 23:55:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','POS Cash Journal','POS Cash Journal',TO_TIMESTAMP('2024-09-21 23:55:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T20:55:32.926Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583272 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Journal.C_POS_Journal_ID
-- Column: C_POS_Journal.C_POS_Journal_ID
-- 2024-09-21T20:55:33.554Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589129,583272,0,13,542438,'C_POS_Journal_ID',TO_TIMESTAMP('2024-09-21 23:55:32','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','POS Cash Journal',0,0,TO_TIMESTAMP('2024-09-21 23:55:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:55:33.557Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589129 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:55:33.560Z
/* DDL */  select update_Column_Translation_From_AD_Element(583272) 
;

-- Column: C_POS_Journal.C_POS_ID
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T20:56:18.548Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589130,2581,0,30,542438,'XX','C_POS_ID',TO_TIMESTAMP('2024-09-21 23:56:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Point of Sales Terminal','de.metas.pos',0,10,'"POS-Terminal" definiert Standardwerte und Funktionen fürdas POS-Fenster.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'POS-Terminal',0,0,TO_TIMESTAMP('2024-09-21 23:56:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:56:18.551Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589130 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:56:18.554Z
/* DDL */  select update_Column_Translation_From_AD_Element(2581) 
;

-- 2024-09-21T20:57:01.123Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583273,0,'CashBeginningBalance',TO_TIMESTAMP('2024-09-21 23:57:00','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.pos','','Y','Anfangssaldo','Anfangssaldo',TO_TIMESTAMP('2024-09-21 23:57:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T20:57:01.125Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583273 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CashBeginningBalance
-- 2024-09-21T20:57:26.649Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Cash Beginning Balance', PrintName='Cash Beginning Balance',Updated=TO_TIMESTAMP('2024-09-21 23:57:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583273 AND AD_Language='en_US'
;

-- 2024-09-21T20:57:26.654Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583273,'en_US') 
;

-- Column: C_POS_Journal.CashBeginningBalance
-- Column: C_POS_Journal.CashBeginningBalance
-- 2024-09-21T20:57:50.318Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589131,583273,0,12,542438,'XX','CashBeginningBalance',TO_TIMESTAMP('2024-09-21 23:57:50','YYYY-MM-DD HH24:MI:SS'),100,'N','','','de.metas.pos',0,22,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Anfangssaldo',0,0,TO_TIMESTAMP('2024-09-21 23:57:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:57:50.321Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589131 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:57:50.324Z
/* DDL */  select update_Column_Translation_From_AD_Element(583273) 
;

-- 2024-09-21T20:58:22.548Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583274,0,'CashEndingBalance',TO_TIMESTAMP('2024-09-21 23:58:22','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.pos','','Y','Endsaldo','Endsaldo',TO_TIMESTAMP('2024-09-21 23:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T20:58:22.550Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583274 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CashEndingBalance
-- 2024-09-21T20:58:36.204Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Cash Ending Balance', PrintName='Cash Ending Balance',Updated=TO_TIMESTAMP('2024-09-21 23:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583274 AND AD_Language='en_US'
;

-- 2024-09-21T20:58:36.207Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583274,'en_US') 
;

-- Column: C_POS_Journal.CashEndingBalance
-- Column: C_POS_Journal.CashEndingBalance
-- 2024-09-21T20:58:48.587Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589132,583274,0,12,542438,'XX','CashEndingBalance',TO_TIMESTAMP('2024-09-21 23:58:48','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.pos',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Endsaldo',0,0,TO_TIMESTAMP('2024-09-21 23:58:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:58:48.589Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589132 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:58:48.592Z
/* DDL */  select update_Column_Translation_From_AD_Element(583274) 
;

-- Column: C_POS_Journal.CashBeginningBalance
-- Column: C_POS_Journal.CashBeginningBalance
-- 2024-09-21T20:58:51.729Z
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2024-09-21 23:58:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589131
;

-- Column: C_POS_Journal.C_Currency_ID
-- Column: C_POS_Journal.C_Currency_ID
-- 2024-09-21T20:59:10.386Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589133,193,0,30,542438,'XX','C_Currency_ID',TO_TIMESTAMP('2024-09-21 23:59:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Währung für diesen Eintrag','de.metas.pos',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2024-09-21 23:59:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:59:10.388Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589133 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:59:10.391Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: C_POS_Journal.IsClosed
-- Column: C_POS_Journal.IsClosed
-- 2024-09-21T20:59:24.494Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589134,2723,0,20,542438,'XX','IsClosed',TO_TIMESTAMP('2024-09-21 23:59:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','de.metas.pos',0,1,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Geschlossen',0,0,TO_TIMESTAMP('2024-09-21 23:59:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T20:59:24.497Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589134 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T20:59:24.501Z
/* DDL */  select update_Column_Translation_From_AD_Element(2723) 
;

-- 2024-09-21T20:59:52.900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583275,0,'OpeningNote',TO_TIMESTAMP('2024-09-21 23:59:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Opening Note','Opening Note',TO_TIMESTAMP('2024-09-21 23:59:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T20:59:52.902Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583275 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-09-21T21:00:08.609Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583276,0,'ClosingNote',TO_TIMESTAMP('2024-09-22 00:00:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Closing Note','Closing Note',TO_TIMESTAMP('2024-09-22 00:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:00:08.612Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583276 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Journal.OpeningNote
-- Column: C_POS_Journal.OpeningNote
-- 2024-09-21T21:00:25.620Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589135,583275,0,36,542438,'XX','OpeningNote',TO_TIMESTAMP('2024-09-22 00:00:25','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Opening Note',0,0,TO_TIMESTAMP('2024-09-22 00:00:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:00:25.623Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589135 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:00:25.625Z
/* DDL */  select update_Column_Translation_From_AD_Element(583275) 
;

-- Column: C_POS_Journal.ClosingNote
-- Column: C_POS_Journal.ClosingNote
-- 2024-09-21T21:00:37.519Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589136,583276,0,36,542438,'XX','ClosingNote',TO_TIMESTAMP('2024-09-22 00:00:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Closing Note',0,0,TO_TIMESTAMP('2024-09-22 00:00:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:00:37.521Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589136 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:00:37.524Z
/* DDL */  select update_Column_Translation_From_AD_Element(583276) 
;

-- Column: C_POS_Journal.DateTrx
-- Column: C_POS_Journal.DateTrx
-- 2024-09-21T21:01:19.101Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589137,1297,0,16,542438,'XX','DateTrx',TO_TIMESTAMP('2024-09-22 00:01:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Vorgangsdatum','de.metas.pos',0,7,'Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Vorgangsdatum',0,0,TO_TIMESTAMP('2024-09-22 00:01:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:01:19.104Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589137 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:01:19.109Z
/* DDL */  select update_Column_Translation_From_AD_Element(1297) 
;

-- 2024-09-21T21:03:32.430Z
/* DDL */ CREATE TABLE public.C_POS_Journal (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, CashBeginningBalance NUMERIC NOT NULL, CashEndingBalance NUMERIC NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, ClosingNote TEXT, C_POS_ID NUMERIC(10) NOT NULL, C_POS_Journal_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateTrx TIMESTAMP WITH TIME ZONE NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsClosed CHAR(1) DEFAULT 'N' CHECK (IsClosed IN ('Y','N')) NOT NULL, OpeningNote TEXT, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCurrency_CPOSJournal FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CPOS_CPOSJournal FOREIGN KEY (C_POS_ID) REFERENCES public.C_POS DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_POS_Journal_Key PRIMARY KEY (C_POS_Journal_ID))
;

-- Table: C_POS_JournalLine
-- Table: C_POS_JournalLine
-- 2024-09-21T21:03:58.785Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('1',0,0,0,542439,'A','N',TO_TIMESTAMP('2024-09-22 00:03:58','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.pos','N','Y','N','N','Y','N','Y','N','N','N',0,'POS Cash Journal Line','NP','L','C_POS_JournalLine','DTI',TO_TIMESTAMP('2024-09-22 00:03:58','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-09-21T21:03:58.786Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542439 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-09-21T21:03:58.893Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556370,TO_TIMESTAMP('2024-09-22 00:03:58','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_POS_JournalLine',1,'Y','N','Y','Y','C_POS_JournalLine','N',1000000,TO_TIMESTAMP('2024-09-22 00:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:03:58.898Z
CREATE SEQUENCE C_POS_JOURNALLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_POS_JournalLine.AD_Client_ID
-- Column: C_POS_JournalLine.AD_Client_ID
-- 2024-09-21T21:04:03.495Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589138,102,0,19,542439,'AD_Client_ID',TO_TIMESTAMP('2024-09-22 00:04:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.pos',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-09-22 00:04:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:03.498Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589138 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:03.502Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_POS_JournalLine.AD_Org_ID
-- Column: C_POS_JournalLine.AD_Org_ID
-- 2024-09-21T21:04:04.110Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589139,113,0,30,542439,'AD_Org_ID',TO_TIMESTAMP('2024-09-22 00:04:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.pos',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-09-22 00:04:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:04.112Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589139 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:04.114Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_POS_JournalLine.Created
-- Column: C_POS_JournalLine.Created
-- 2024-09-21T21:04:04.664Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589140,245,0,16,542439,'Created',TO_TIMESTAMP('2024-09-22 00:04:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.pos',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-09-22 00:04:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:04.666Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589140 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:04.668Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_POS_JournalLine.CreatedBy
-- Column: C_POS_JournalLine.CreatedBy
-- 2024-09-21T21:04:05.701Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589141,246,0,18,110,542439,'CreatedBy',TO_TIMESTAMP('2024-09-22 00:04:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.pos',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-09-22 00:04:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:05.703Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589141 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:05.705Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_POS_JournalLine.IsActive
-- Column: C_POS_JournalLine.IsActive
-- 2024-09-21T21:04:06.288Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589142,348,0,20,542439,'IsActive',TO_TIMESTAMP('2024-09-22 00:04:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.pos',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-09-22 00:04:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:06.289Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589142 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:06.292Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_POS_JournalLine.Updated
-- Column: C_POS_JournalLine.Updated
-- 2024-09-21T21:04:06.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589143,607,0,16,542439,'Updated',TO_TIMESTAMP('2024-09-22 00:04:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.pos',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-09-22 00:04:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:06.866Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589143 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:06.869Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_POS_JournalLine.UpdatedBy
-- Column: C_POS_JournalLine.UpdatedBy
-- 2024-09-21T21:04:07.451Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589144,608,0,18,110,542439,'UpdatedBy',TO_TIMESTAMP('2024-09-22 00:04:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.pos',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-09-22 00:04:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:07.455Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589144 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:07.461Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-09-21T21:04:07.946Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583277,0,'C_POS_JournalLine_ID',TO_TIMESTAMP('2024-09-22 00:04:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','POS Cash Journal Line','POS Cash Journal Line',TO_TIMESTAMP('2024-09-22 00:04:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:04:07.948Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583277 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_JournalLine.C_POS_JournalLine_ID
-- Column: C_POS_JournalLine.C_POS_JournalLine_ID
-- 2024-09-21T21:04:08.496Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589145,583277,0,13,542439,'C_POS_JournalLine_ID',TO_TIMESTAMP('2024-09-22 00:04:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','POS Cash Journal Line',0,0,TO_TIMESTAMP('2024-09-22 00:04:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:04:08.497Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589145 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:04:08.500Z
/* DDL */  select update_Column_Translation_From_AD_Element(583277) 
;

-- Name: C_POS_JournalLine_Type
-- 2024-09-21T21:05:01.002Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541892,TO_TIMESTAMP('2024-09-22 00:05:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','N','C_POS_JournalLine_Type',TO_TIMESTAMP('2024-09-22 00:05:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2024-09-21T21:05:01.004Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541892 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_POS_JournalLine_Type
-- Value: CASH_PAY
-- ValueName: CashPayment
-- 2024-09-21T21:06:53.747Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541892,543721,TO_TIMESTAMP('2024-09-22 00:06:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Cash Payment',TO_TIMESTAMP('2024-09-22 00:06:53','YYYY-MM-DD HH24:MI:SS'),100,'CASH_PAY','CashPayment')
;

-- 2024-09-21T21:06:53.749Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543721 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_JournalLine_Type
-- Value: CARD_PAY
-- ValueName: CardPayment
-- 2024-09-21T21:07:14.722Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541892,543722,TO_TIMESTAMP('2024-09-22 00:07:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Card Payment',TO_TIMESTAMP('2024-09-22 00:07:13','YYYY-MM-DD HH24:MI:SS'),100,'CARD_PAY','CardPayment')
;

-- 2024-09-21T21:07:14.723Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543722 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_JournalLine_Type
-- Value: CASH_INOUT
-- ValueName: CashInOut
-- 2024-09-21T21:07:43.827Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541892,543723,TO_TIMESTAMP('2024-09-22 00:07:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Cash In/Out',TO_TIMESTAMP('2024-09-22 00:07:43','YYYY-MM-DD HH24:MI:SS'),100,'CASH_INOUT','CashInOut')
;

-- 2024-09-21T21:07:43.828Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543723 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_POS_JournalLine_Type
-- Value: CASH_DIFF
-- ValueName: CashClosingDifference
-- 2024-09-21T21:08:23.935Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541892,543724,TO_TIMESTAMP('2024-09-22 00:08:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Cash Closing Difference',TO_TIMESTAMP('2024-09-22 00:08:23','YYYY-MM-DD HH24:MI:SS'),100,'CASH_DIFF','CashClosingDifference')
;

-- 2024-09-21T21:08:23.937Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543724 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_POS_JournalLine.Type
-- Column: C_POS_JournalLine.Type
-- 2024-09-21T21:08:46.017Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589146,600,0,17,541892,542439,'XX','Type',TO_TIMESTAMP('2024-09-22 00:08:45','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.pos',0,20,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Art',0,0,TO_TIMESTAMP('2024-09-22 00:08:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:08:46.019Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589146 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:08:46.023Z
/* DDL */  select update_Column_Translation_From_AD_Element(600) 
;

-- Column: C_POS_JournalLine.Amount
-- Column: C_POS_JournalLine.Amount
-- 2024-09-21T21:09:08.061Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589147,1367,0,12,542439,'XX','Amount',TO_TIMESTAMP('2024-09-22 00:09:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Betrag in einer definierten Währung','de.metas.pos',0,10,'"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Betrag',0,0,TO_TIMESTAMP('2024-09-22 00:09:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:09:08.062Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:09:08.064Z
/* DDL */  select update_Column_Translation_From_AD_Element(1367) 
;

-- Column: C_POS_JournalLine.Cashier_ID
-- Column: C_POS_JournalLine.Cashier_ID
-- 2024-09-21T21:09:26.272Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589148,583267,0,30,110,542439,'XX','Cashier_ID',TO_TIMESTAMP('2024-09-22 00:09:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cashier',0,0,TO_TIMESTAMP('2024-09-22 00:09:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:09:26.273Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:09:26.276Z
/* DDL */  select update_Column_Translation_From_AD_Element(583267) 
;

-- Column: C_POS_JournalLine.Description
-- Column: C_POS_JournalLine.Description
-- 2024-09-21T21:09:57.588Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589149,275,0,36,542439,'XX','Description',TO_TIMESTAMP('2024-09-22 00:09:57','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,4000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2024-09-22 00:09:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:09:57.589Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589149 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:09:57.591Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: C_POS_JournalLine.C_POS_Order_ID
-- Column: C_POS_JournalLine.C_POS_Order_ID
-- 2024-09-21T21:10:13.562Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589150,583266,0,30,542439,'XX','C_POS_Order_ID',TO_TIMESTAMP('2024-09-22 00:10:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'POS Order',0,0,TO_TIMESTAMP('2024-09-22 00:10:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:10:13.564Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589150 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:10:13.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(583266) 
;

-- Column: C_POS_Payment.Amount
-- Column: C_POS_Payment.Amount
-- 2024-09-21T21:11:30.611Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-22 00:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589117
;

-- Column: C_POS_JournalLine.C_POS_Payment_ID
-- Column: C_POS_JournalLine.C_POS_Payment_ID
-- 2024-09-21T21:11:59.147Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589151,583269,0,30,542439,'XX','C_POS_Payment_ID',TO_TIMESTAMP('2024-09-22 00:11:58','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'POS Payment',0,0,TO_TIMESTAMP('2024-09-22 00:11:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:11:59.149Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589151 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:11:59.152Z
/* DDL */  select update_Column_Translation_From_AD_Element(583269) 
;

-- 2024-09-21T21:12:20.111Z
/* DDL */ CREATE TABLE public.C_POS_JournalLine (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, Cashier_ID NUMERIC(10) NOT NULL, C_POS_JournalLine_ID NUMERIC(10) NOT NULL, C_POS_Order_ID NUMERIC(10), C_POS_Payment_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Type VARCHAR(20) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Cashier_CPOSJournalLine FOREIGN KEY (Cashier_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_POS_JournalLine_Key PRIMARY KEY (C_POS_JournalLine_ID), CONSTRAINT CPOSOrder_CPOSJournalLine FOREIGN KEY (C_POS_Order_ID) REFERENCES public.C_POS_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CPOSPayment_CPOSJournalLine FOREIGN KEY (C_POS_Payment_ID) REFERENCES public.C_POS_Payment DEFERRABLE INITIALLY DEFERRED)
;

-- Column: C_POS_Journal.C_POS_ID
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T21:13:13.643Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-22 00:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589130
;

-- Column: C_POS_Journal.DateTrx
-- Column: C_POS_Journal.DateTrx
-- 2024-09-21T21:13:20.017Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-22 00:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589137
;

-- Column: C_POS_Journal.IsClosed
-- Column: C_POS_Journal.IsClosed
-- 2024-09-21T21:13:24.452Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-22 00:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589134
;

-- Column: C_POS_Journal.OpeningNote
-- Column: C_POS_Journal.OpeningNote
-- 2024-09-21T21:13:29.340Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-22 00:13:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589135
;

-- Column: C_POS_Journal.ClosingNote
-- Column: C_POS_Journal.ClosingNote
-- 2024-09-21T21:13:33.378Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-09-22 00:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589136
;

-- Column: C_POS_Journal.C_POS_ID
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T21:14:10.675Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-22 00:14:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589130
;

-- Column: C_POS_Journal.DateTrx
-- Column: C_POS_Journal.DateTrx
-- 2024-09-21T21:14:21.105Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-09-22 00:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589137
;

-- Column: C_POS_JournalLine.C_POS_Journal_ID
-- Column: C_POS_JournalLine.C_POS_Journal_ID
-- 2024-09-21T21:14:48.271Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589152,583272,0,30,542439,'XX','C_POS_Journal_ID',TO_TIMESTAMP('2024-09-22 00:14:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'POS Cash Journal',0,0,TO_TIMESTAMP('2024-09-22 00:14:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T21:14:48.273Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589152 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T21:14:48.276Z
/* DDL */  select update_Column_Translation_From_AD_Element(583272) 
;

-- 2024-09-21T21:14:48.873Z
/* DDL */ SELECT public.db_alter_table('C_POS_JournalLine','ALTER TABLE public.C_POS_JournalLine ADD COLUMN C_POS_Journal_ID NUMERIC(10) NOT NULL')
;

-- 2024-09-21T21:14:48.883Z
ALTER TABLE C_POS_JournalLine ADD CONSTRAINT CPOSJournal_CPOSJournalLine FOREIGN KEY (C_POS_Journal_ID) REFERENCES public.C_POS_Journal DEFERRABLE INITIALLY DEFERRED
;

-- Window: POS Cash Journal, InternalName=null
-- Window: POS Cash Journal, InternalName=null
-- 2024-09-21T21:15:26.992Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583272,0,541819,TO_TIMESTAMP('2024-09-22 00:15:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','N','N','N','N','N','N','Y','POS Cash Journal','N',TO_TIMESTAMP('2024-09-22 00:15:26','YYYY-MM-DD HH24:MI:SS'),100,'T',0,0,100)
;

-- 2024-09-21T21:15:26.994Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541819 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-09-21T21:15:26.998Z
/* DDL */  select update_window_translation_from_ad_element(583272) 
;

-- 2024-09-21T21:15:26.999Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541819
;

-- 2024-09-21T21:15:27.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541819)
;

-- Window: POS Cash Journal, InternalName=posJournal
-- Window: POS Cash Journal, InternalName=posJournal
-- 2024-09-21T21:15:32.654Z
UPDATE AD_Window SET InternalName='posJournal',Updated=TO_TIMESTAMP('2024-09-22 00:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541819
;

-- Tab: POS Cash Journal -> POS Cash Journal
-- Table: C_POS_Journal
-- Tab: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal
-- Table: C_POS_Journal
-- 2024-09-21T21:15:50.318Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583272,0,547594,542438,541819,'Y',TO_TIMESTAMP('2024-09-22 00:15:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','N','N','A','C_POS_Journal','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'POS Cash Journal','N',10,0,TO_TIMESTAMP('2024-09-22 00:15:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:15:50.324Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547594 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-09-21T21:15:50.326Z
/* DDL */  select update_tab_translation_from_ad_element(583272) 
;

-- 2024-09-21T21:15:50.330Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547594)
;

-- Tab: POS Cash Journal -> POS Cash Journal
-- Table: C_POS_Journal
-- Tab: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal
-- Table: C_POS_Journal
-- 2024-09-21T21:15:55.330Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-09-22 00:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547594
;

-- Tab: POS Cash Journal -> POS Cash Journal Line
-- Table: C_POS_JournalLine
-- Tab: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line
-- Table: C_POS_JournalLine
-- 2024-09-21T21:16:21.149Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589152,583277,0,547595,542439,541819,'Y',TO_TIMESTAMP('2024-09-22 00:16:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','N','N','A','C_POS_JournalLine','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'POS Cash Journal Line',589129,'N',20,1,TO_TIMESTAMP('2024-09-22 00:16:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:21.152Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547595 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-09-21T21:16:21.154Z
/* DDL */  select update_tab_translation_from_ad_element(583277) 
;

-- 2024-09-21T21:16:21.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547595)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Mandant
-- Column: C_POS_Journal.AD_Client_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Mandant
-- Column: C_POS_Journal.AD_Client_ID
-- 2024-09-21T21:16:28.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589122,730915,0,547594,TO_TIMESTAMP('2024-09-22 00:16:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.pos','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-09-22 00:16:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:28.644Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:28.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-09-21T21:16:29.476Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730915
;

-- 2024-09-21T21:16:29.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730915)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Organisation
-- Column: C_POS_Journal.AD_Org_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Organisation
-- Column: C_POS_Journal.AD_Org_ID
-- 2024-09-21T21:16:29.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589123,730916,0,547594,TO_TIMESTAMP('2024-09-22 00:16:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.pos','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-09-22 00:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:29.598Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:29.599Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-09-21T21:16:29.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730916
;

-- 2024-09-21T21:16:29.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730916)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Aktiv
-- Column: C_POS_Journal.IsActive
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Aktiv
-- Column: C_POS_Journal.IsActive
-- 2024-09-21T21:16:29.829Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589126,730917,0,547594,TO_TIMESTAMP('2024-09-22 00:16:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.pos','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-09-22 00:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:29.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:29.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-09-21T21:16:29.962Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730917
;

-- 2024-09-21T21:16:29.963Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730917)
;

-- Field: POS Cash Journal -> POS Cash Journal -> POS Cash Journal
-- Column: C_POS_Journal.C_POS_Journal_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> POS Cash Journal
-- Column: C_POS_Journal.C_POS_Journal_ID
-- 2024-09-21T21:16:30.074Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589129,730918,0,547594,TO_TIMESTAMP('2024-09-22 00:16:29','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Cash Journal',TO_TIMESTAMP('2024-09-22 00:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.076Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583272) 
;

-- 2024-09-21T21:16:30.079Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730918
;

-- 2024-09-21T21:16:30.080Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730918)
;

-- Field: POS Cash Journal -> POS Cash Journal -> POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T21:16:30.187Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589130,730919,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,'Point of Sales Terminal',10,'de.metas.pos','"POS-Terminal" definiert Standardwerte und Funktionen fürdas POS-Fenster.','Y','N','N','N','N','N','N','N','POS-Terminal',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.189Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.190Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2581) 
;

-- 2024-09-21T21:16:30.196Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730919
;

-- 2024-09-21T21:16:30.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730919)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- 2024-09-21T21:16:30.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589131,730920,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.pos','','Y','N','N','N','N','N','N','N','Anfangssaldo',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583273) 
;

-- 2024-09-21T21:16:30.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730920
;

-- 2024-09-21T21:16:30.323Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730920)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- 2024-09-21T21:16:30.439Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589132,730921,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.pos','','Y','N','N','N','N','N','N','N','Endsaldo',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.440Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.442Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583274) 
;

-- 2024-09-21T21:16:30.446Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730921
;

-- 2024-09-21T21:16:30.446Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730921)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Währung
-- Column: C_POS_Journal.C_Currency_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Währung
-- Column: C_POS_Journal.C_Currency_ID
-- 2024-09-21T21:16:30.554Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589133,730922,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'de.metas.pos','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.555Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.556Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2024-09-21T21:16:30.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730922
;

-- 2024-09-21T21:16:30.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730922)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Geschlossen
-- Column: C_POS_Journal.IsClosed
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Geschlossen
-- Column: C_POS_Journal.IsClosed
-- 2024-09-21T21:16:30.686Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589134,730923,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.pos','','Y','N','N','N','N','N','N','N','Geschlossen',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.688Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.689Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2723) 
;

-- 2024-09-21T21:16:30.696Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730923
;

-- 2024-09-21T21:16:30.696Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730923)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Opening Note
-- Column: C_POS_Journal.OpeningNote
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Opening Note
-- Column: C_POS_Journal.OpeningNote
-- 2024-09-21T21:16:30.794Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589135,730924,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.pos','Y','N','N','N','N','N','N','N','Opening Note',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.795Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.797Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583275) 
;

-- 2024-09-21T21:16:30.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730924
;

-- 2024-09-21T21:16:30.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730924)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Closing Note
-- Column: C_POS_Journal.ClosingNote
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Closing Note
-- Column: C_POS_Journal.ClosingNote
-- 2024-09-21T21:16:30.918Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589136,730925,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.pos','Y','N','N','N','N','N','N','N','Closing Note',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:30.920Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:30.921Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583276) 
;

-- 2024-09-21T21:16:30.924Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730925
;

-- 2024-09-21T21:16:30.924Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730925)
;

-- Field: POS Cash Journal -> POS Cash Journal -> Vorgangsdatum
-- Column: C_POS_Journal.DateTrx
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Vorgangsdatum
-- Column: C_POS_Journal.DateTrx
-- 2024-09-21T21:16:31.040Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589137,730926,0,547594,TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum',7,'de.metas.pos','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','N','N','N','N','N','N','Vorgangsdatum',TO_TIMESTAMP('2024-09-22 00:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:31.041Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:31.043Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1297) 
;

-- 2024-09-21T21:16:31.052Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730926
;

-- 2024-09-21T21:16:31.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730926)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> Mandant
-- Column: C_POS_JournalLine.AD_Client_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> Mandant
-- Column: C_POS_JournalLine.AD_Client_ID
-- 2024-09-21T21:16:34.894Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589138,730927,0,547595,TO_TIMESTAMP('2024-09-22 00:16:34','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.pos','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-09-22 00:16:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:34.896Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:34.897Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-09-21T21:16:34.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730927
;

-- 2024-09-21T21:16:34.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730927)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> Organisation
-- Column: C_POS_JournalLine.AD_Org_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> Organisation
-- Column: C_POS_JournalLine.AD_Org_ID
-- 2024-09-21T21:16:35.086Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589139,730928,0,547595,TO_TIMESTAMP('2024-09-22 00:16:34','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.pos','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-09-22 00:16:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:35.088Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:35.090Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-09-21T21:16:35.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730928
;

-- 2024-09-21T21:16:35.138Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730928)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> Aktiv
-- Column: C_POS_JournalLine.IsActive
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> Aktiv
-- Column: C_POS_JournalLine.IsActive
-- 2024-09-21T21:16:35.246Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589142,730929,0,547595,TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.pos','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:35.248Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:35.249Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-09-21T21:16:35.298Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730929
;

-- 2024-09-21T21:16:35.300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730929)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> POS Cash Journal Line
-- Column: C_POS_JournalLine.C_POS_JournalLine_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> POS Cash Journal Line
-- Column: C_POS_JournalLine.C_POS_JournalLine_ID
-- 2024-09-21T21:16:35.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589145,730930,0,547595,TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Cash Journal Line',TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:35.415Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730930 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:35.418Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583277) 
;

-- 2024-09-21T21:16:35.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730930
;

-- 2024-09-21T21:16:35.422Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730930)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> Art
-- Column: C_POS_JournalLine.Type
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> Art
-- Column: C_POS_JournalLine.Type
-- 2024-09-21T21:16:35.538Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589146,730931,0,547595,TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100,'',20,'de.metas.pos','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:35.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:35.543Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2024-09-21T21:16:35.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730931
;

-- 2024-09-21T21:16:35.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730931)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> Betrag
-- Column: C_POS_JournalLine.Amount
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> Betrag
-- Column: C_POS_JournalLine.Amount
-- 2024-09-21T21:16:35.679Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589147,730932,0,547595,TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung',10,'de.metas.pos','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','N','N','N','N','N','Betrag',TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:35.680Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:35.682Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2024-09-21T21:16:35.692Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730932
;

-- 2024-09-21T21:16:35.692Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730932)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> Cashier
-- Column: C_POS_JournalLine.Cashier_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> Cashier
-- Column: C_POS_JournalLine.Cashier_ID
-- 2024-09-21T21:16:35.812Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589148,730933,0,547595,TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','Cashier',TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:35.814Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:35.815Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583267) 
;

-- 2024-09-21T21:16:35.818Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730933
;

-- 2024-09-21T21:16:35.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730933)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> Beschreibung
-- Column: C_POS_JournalLine.Description
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> Beschreibung
-- Column: C_POS_JournalLine.Description
-- 2024-09-21T21:16:35.924Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589149,730934,0,547595,TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.pos','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-09-22 00:16:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:35.925Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:35.927Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2024-09-21T21:16:36.028Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730934
;

-- 2024-09-21T21:16:36.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730934)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> POS Order
-- Column: C_POS_JournalLine.C_POS_Order_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> POS Order
-- Column: C_POS_JournalLine.C_POS_Order_ID
-- 2024-09-21T21:16:36.138Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589150,730935,0,547595,TO_TIMESTAMP('2024-09-22 00:16:36','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Order',TO_TIMESTAMP('2024-09-22 00:16:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:36.139Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:36.140Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583266) 
;

-- 2024-09-21T21:16:36.144Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730935
;

-- 2024-09-21T21:16:36.145Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730935)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> POS Payment
-- Column: C_POS_JournalLine.C_POS_Payment_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> POS Payment
-- Column: C_POS_JournalLine.C_POS_Payment_ID
-- 2024-09-21T21:16:36.253Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589151,730936,0,547595,TO_TIMESTAMP('2024-09-22 00:16:36','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Payment',TO_TIMESTAMP('2024-09-22 00:16:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:36.254Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:36.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583269) 
;

-- 2024-09-21T21:16:36.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730936
;

-- 2024-09-21T21:16:36.259Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730936)
;

-- Field: POS Cash Journal -> POS Cash Journal Line -> POS Cash Journal
-- Column: C_POS_JournalLine.C_POS_Journal_ID
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> POS Cash Journal
-- Column: C_POS_JournalLine.C_POS_Journal_ID
-- 2024-09-21T21:16:36.368Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589152,730937,0,547595,TO_TIMESTAMP('2024-09-22 00:16:36','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','POS Cash Journal',TO_TIMESTAMP('2024-09-22 00:16:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:16:36.369Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T21:16:36.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583272) 
;

-- 2024-09-21T21:16:36.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730937
;

-- 2024-09-21T21:16:36.374Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730937)
;

-- Tab: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos)
-- UI Section: main
-- 2024-09-21T21:16:43.560Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547594,546181,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-09-21T21:16:43.563Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546181 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main
-- UI Column: 10
-- 2024-09-21T21:16:43.679Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547557,546181,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main
-- UI Column: 20
-- 2024-09-21T21:16:43.795Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547558,546181,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10
-- UI Element Group: default
-- 2024-09-21T21:16:43.895Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547557,551972,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos)
-- UI Section: main
-- 2024-09-21T21:16:44.007Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547595,546182,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-22 00:16:43','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-09-21T21:16:44.008Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546182 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main
-- UI Column: 10
-- 2024-09-21T21:16:44.115Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547559,546182,TO_TIMESTAMP('2024-09-22 00:16:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-22 00:16:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10
-- UI Element Group: default
-- 2024-09-21T21:16:44.225Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547559,551973,TO_TIMESTAMP('2024-09-22 00:16:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-22 00:16:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T21:17:45.310Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730919,0,547594,551972,625790,'F',TO_TIMESTAMP('2024-09-22 00:17:45','YYYY-MM-DD HH24:MI:SS'),100,'Point of Sales Terminal','"POS-Terminal" definiert Standardwerte und Funktionen fürdas POS-Fenster.','Y','N','Y','N','N','POS-Terminal',10,0,0,TO_TIMESTAMP('2024-09-22 00:17:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Vorgangsdatum
-- Column: C_POS_Journal.DateTrx
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.Vorgangsdatum
-- Column: C_POS_Journal.DateTrx
-- 2024-09-21T21:17:58.331Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730926,0,547594,551972,625791,'F',TO_TIMESTAMP('2024-09-22 00:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Vorgangsdatum','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','Vorgangsdatum',20,0,0,TO_TIMESTAMP('2024-09-22 00:17:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10
-- UI Element Group: cash
-- 2024-09-21T21:18:11.097Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547557,551974,TO_TIMESTAMP('2024-09-22 00:18:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','cash',20,TO_TIMESTAMP('2024-09-22 00:18:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Währung
-- Column: C_POS_Journal.C_Currency_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Währung
-- Column: C_POS_Journal.C_Currency_ID
-- 2024-09-21T21:18:23.280Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730922,0,547594,551974,625792,'F',TO_TIMESTAMP('2024-09-22 00:18:22','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',10,0,0,TO_TIMESTAMP('2024-09-22 00:18:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Opening Note
-- Column: C_POS_Journal.OpeningNote
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Opening Note
-- Column: C_POS_Journal.OpeningNote
-- 2024-09-21T21:18:33.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730924,0,547594,551974,625793,'F',TO_TIMESTAMP('2024-09-22 00:18:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Opening Note',20,0,0,TO_TIMESTAMP('2024-09-22 00:18:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Closing Note
-- Column: C_POS_Journal.ClosingNote
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Closing Note
-- Column: C_POS_Journal.ClosingNote
-- 2024-09-21T21:18:39.672Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730925,0,547594,551974,625794,'F',TO_TIMESTAMP('2024-09-22 00:18:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Closing Note',30,0,0,TO_TIMESTAMP('2024-09-22 00:18:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 20
-- UI Element Group: flags & status
-- 2024-09-21T21:19:05.850Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547558,551975,TO_TIMESTAMP('2024-09-22 00:19:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags & status',10,TO_TIMESTAMP('2024-09-22 00:19:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Geschlossen
-- Column: C_POS_Journal.IsClosed
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 20 -> flags & status.Geschlossen
-- Column: C_POS_Journal.IsClosed
-- 2024-09-21T21:19:37.885Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730923,0,547594,551975,625795,'F',TO_TIMESTAMP('2024-09-22 00:19:37','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Geschlossen',10,0,0,TO_TIMESTAMP('2024-09-22 00:19:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 20
-- UI Element Group: org&client
-- 2024-09-21T21:19:46.820Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547558,551976,TO_TIMESTAMP('2024-09-22 00:19:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',20,TO_TIMESTAMP('2024-09-22 00:19:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Organisation
-- Column: C_POS_Journal.AD_Org_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 20 -> org&client.Organisation
-- Column: C_POS_Journal.AD_Org_ID
-- 2024-09-21T21:19:57.228Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730916,0,547594,551976,625796,'F',TO_TIMESTAMP('2024-09-22 00:19:57','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2024-09-22 00:19:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Mandant
-- Column: C_POS_Journal.AD_Client_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 20 -> org&client.Mandant
-- Column: C_POS_Journal.AD_Client_ID
-- 2024-09-21T21:20:04.491Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730915,0,547594,551976,625797,'F',TO_TIMESTAMP('2024-09-22 00:20:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-09-22 00:20:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos)
-- UI Section: notes
-- 2024-09-21T21:20:21.782Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547594,546183,TO_TIMESTAMP('2024-09-22 00:20:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-22 00:20:21','YYYY-MM-DD HH24:MI:SS'),100,'notes')
;

-- 2024-09-21T21:20:21.785Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546183 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> notes
-- UI Column: 10
-- 2024-09-21T21:20:26.290Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547560,546183,TO_TIMESTAMP('2024-09-22 00:20:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-09-22 00:20:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> notes -> 10
-- UI Element Group: notes
-- 2024-09-21T21:20:31.463Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547560,551977,TO_TIMESTAMP('2024-09-22 00:20:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','notes',10,TO_TIMESTAMP('2024-09-22 00:20:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Opening Note
-- Column: C_POS_Journal.OpeningNote
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> notes -> 10 -> notes.Opening Note
-- Column: C_POS_Journal.OpeningNote
-- 2024-09-21T21:20:39.798Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551977, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-09-22 00:20:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625793
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Closing Note
-- Column: C_POS_Journal.ClosingNote
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> notes -> 10 -> notes.Closing Note
-- Column: C_POS_Journal.ClosingNote
-- 2024-09-21T21:20:46.194Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551977, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-09-22 00:20:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625794
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- 2024-09-21T21:21:11.876Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730920,0,547594,551974,625798,'F',TO_TIMESTAMP('2024-09-22 00:21:11','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Anfangssaldo',20,0,0,TO_TIMESTAMP('2024-09-22 00:21:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- 2024-09-21T21:21:17.466Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730921,0,547594,551974,625799,'F',TO_TIMESTAMP('2024-09-22 00:21:17','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Endsaldo',30,0,0,TO_TIMESTAMP('2024-09-22 00:21:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T21:21:41.457Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-22 00:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625790
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Vorgangsdatum
-- Column: C_POS_Journal.DateTrx
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.Vorgangsdatum
-- Column: C_POS_Journal.DateTrx
-- 2024-09-21T21:21:41.465Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-22 00:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625791
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Geschlossen
-- Column: C_POS_Journal.IsClosed
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 20 -> flags & status.Geschlossen
-- Column: C_POS_Journal.IsClosed
-- 2024-09-21T21:21:41.472Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-22 00:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625795
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Währung
-- Column: C_POS_Journal.C_Currency_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Währung
-- Column: C_POS_Journal.C_Currency_ID
-- 2024-09-21T21:21:41.479Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-22 00:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625792
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- 2024-09-21T21:21:41.488Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-09-22 00:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625798
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- 2024-09-21T21:21:41.496Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-22 00:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625799
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Art
-- Column: C_POS_JournalLine.Type
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Art
-- Column: C_POS_JournalLine.Type
-- 2024-09-21T21:22:13.201Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730931,0,547595,551973,625800,'F',TO_TIMESTAMP('2024-09-22 00:22:13','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Art',10,0,0,TO_TIMESTAMP('2024-09-22 00:22:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Cashier
-- Column: C_POS_JournalLine.Cashier_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Cashier
-- Column: C_POS_JournalLine.Cashier_ID
-- 2024-09-21T21:22:24.105Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730933,0,547595,551973,625801,'F',TO_TIMESTAMP('2024-09-22 00:22:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cashier',20,0,0,TO_TIMESTAMP('2024-09-22 00:22:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Betrag
-- Column: C_POS_JournalLine.Amount
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Betrag
-- Column: C_POS_JournalLine.Amount
-- 2024-09-21T21:22:32.947Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730932,0,547595,551973,625802,'F',TO_TIMESTAMP('2024-09-22 00:22:32','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','Betrag',30,0,0,TO_TIMESTAMP('2024-09-22 00:22:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Beschreibung
-- Column: C_POS_JournalLine.Description
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Beschreibung
-- Column: C_POS_JournalLine.Description
-- 2024-09-21T21:22:44.407Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730934,0,547595,551973,625803,'F',TO_TIMESTAMP('2024-09-22 00:22:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beschreibung',40,0,0,TO_TIMESTAMP('2024-09-22 00:22:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10
-- UI Element Group: payment
-- 2024-09-21T21:23:01.636Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547559,551978,TO_TIMESTAMP('2024-09-22 00:23:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','payment',20,TO_TIMESTAMP('2024-09-22 00:23:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.POS Order
-- Column: C_POS_JournalLine.C_POS_Order_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> payment.POS Order
-- Column: C_POS_JournalLine.C_POS_Order_ID
-- 2024-09-21T21:23:10.948Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730935,0,547595,551978,625804,'F',TO_TIMESTAMP('2024-09-22 00:23:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Order',10,0,0,TO_TIMESTAMP('2024-09-22 00:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.POS Payment
-- Column: C_POS_JournalLine.C_POS_Payment_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> payment.POS Payment
-- Column: C_POS_JournalLine.C_POS_Payment_ID
-- 2024-09-21T21:23:23.415Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730936,0,547595,551978,625805,'F',TO_TIMESTAMP('2024-09-22 00:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Payment',20,0,0,TO_TIMESTAMP('2024-09-22 00:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main
-- UI Column: 20
-- 2024-09-21T21:23:33.069Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547561,546182,TO_TIMESTAMP('2024-09-22 00:23:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-22 00:23:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 20
-- UI Element Group: payment
-- 2024-09-21T21:23:53.905Z
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=547561, SeqNo=10,Updated=TO_TIMESTAMP('2024-09-22 00:23:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551978
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Art
-- Column: C_POS_JournalLine.Type
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Art
-- Column: C_POS_JournalLine.Type
-- 2024-09-21T21:24:22.644Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-22 00:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625800
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Betrag
-- Column: C_POS_JournalLine.Amount
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Betrag
-- Column: C_POS_JournalLine.Amount
-- 2024-09-21T21:24:22.652Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-22 00:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625802
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Cashier
-- Column: C_POS_JournalLine.Cashier_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Cashier
-- Column: C_POS_JournalLine.Cashier_ID
-- 2024-09-21T21:24:22.659Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-22 00:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625801
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.POS Order
-- Column: C_POS_JournalLine.C_POS_Order_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 20 -> payment.POS Order
-- Column: C_POS_JournalLine.C_POS_Order_ID
-- 2024-09-21T21:24:22.666Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-22 00:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625804
;

-- UI Element: POS Cash Journal -> POS Cash Journal Line.Beschreibung
-- Column: C_POS_JournalLine.Description
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal Line(547595,de.metas.pos) -> main -> 10 -> default.Beschreibung
-- Column: C_POS_JournalLine.Description
-- 2024-09-21T21:24:30.325Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-09-22 00:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625803
;

-- Name: POS Cash Journal
-- Action Type: W
-- Window: POS Cash Journal(541819,de.metas.pos)
-- 2024-09-21T21:25:17.370Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583272,542174,0,541819,TO_TIMESTAMP('2024-09-22 00:25:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','posJournal','Y','N','N','N','N','POS Cash Journal',TO_TIMESTAMP('2024-09-22 00:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T21:25:17.372Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542174 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-09-21T21:25:17.376Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542174, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542174)
;

-- 2024-09-21T21:25:17.379Z
/* DDL */  select update_menu_translation_from_ad_element(583272) 
;

-- Reordering children of `Point of Sale (POS)`
-- Node name: `POS Terminal (C_POS)`
-- 2024-09-21T21:25:25.542Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542173 AND AD_Tree_ID=10
;

-- Node name: `POS Order (C_POS_Order)`
-- 2024-09-21T21:25:25.543Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542172 AND AD_Tree_ID=10
;

-- Node name: `POS Cash Journal`
-- 2024-09-21T21:25:25.544Z
UPDATE AD_TreeNodeMM SET Parent_ID=542171, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542174 AND AD_Tree_ID=10
;

-- Table: C_POS_JournalLine
-- Table: C_POS_JournalLine
-- 2024-09-21T21:28:51.166Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2024-09-22 00:28:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542439
;

-- Table: C_POS_Journal
-- Table: C_POS_Journal
-- 2024-09-21T21:28:55.966Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2024-09-22 00:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542438
;

-- Table: C_POS_Journal
-- Table: C_POS_Journal
-- 2024-09-21T21:31:11.931Z
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2024-09-22 00:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542438
;

-- Table: C_POS_JournalLine
-- Table: C_POS_JournalLine
-- 2024-09-21T21:31:14.333Z
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2024-09-22 00:31:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542439
;

-- Column: C_POS.C_POS_Journal_ID
-- Column: C_POS.C_POS_Journal_ID
-- 2024-09-21T22:04:34.759Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589153,583272,0,30,748,'XX','C_POS_Journal_ID',TO_TIMESTAMP('2024-09-22 01:04:34','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'POS Cash Journal',0,0,TO_TIMESTAMP('2024-09-22 01:04:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T22:04:34.765Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589153 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T22:04:34.769Z
/* DDL */  select update_Column_Translation_From_AD_Element(583272) 
;

-- 2024-09-21T22:04:35.412Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE public.C_POS ADD COLUMN C_POS_Journal_ID NUMERIC(10)')
;

-- 2024-09-21T22:04:35.429Z
ALTER TABLE C_POS ADD CONSTRAINT CPOSJournal_CPOS FOREIGN KEY (C_POS_Journal_ID) REFERENCES public.C_POS_Journal DEFERRABLE INITIALLY DEFERRED
;

-- Field: POS-Terminal -> POS-Terminal -> POS Cash Journal
-- Column: C_POS.C_POS_Journal_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> POS Cash Journal
-- Column: C_POS.C_POS_Journal_ID
-- 2024-09-21T22:05:00.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589153,730938,0,676,TO_TIMESTAMP('2024-09-22 01:05:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','POS Cash Journal',TO_TIMESTAMP('2024-09-22 01:05:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T22:05:00.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T22:05:00.880Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583272) 
;

-- 2024-09-21T22:05:00.885Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730938
;

-- 2024-09-21T22:05:00.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730938)
;

-- 2024-09-21T22:06:37.408Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583278,0,'CashLastBalance',TO_TIMESTAMP('2024-09-22 01:06:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Cash Last Balance','Cash Last Balance',TO_TIMESTAMP('2024-09-22 01:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T22:06:37.411Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583278 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS.CashLastBalance
-- Column: C_POS.CashLastBalance
-- 2024-09-21T22:06:54.297Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589154,583278,0,12,748,'XX','CashLastBalance',TO_TIMESTAMP('2024-09-22 01:06:54','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.pos',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cash Last Balance',0,0,TO_TIMESTAMP('2024-09-22 01:06:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T22:06:54.299Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589154 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T22:06:54.303Z
/* DDL */  select update_Column_Translation_From_AD_Element(583278) 
;

-- 2024-09-21T22:06:54.900Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE public.C_POS ADD COLUMN CashLastBalance NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: POS-Terminal -> POS-Terminal -> Cash Last Balance
-- Column: C_POS.CashLastBalance
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> Cash Last Balance
-- Column: C_POS.CashLastBalance
-- 2024-09-21T22:07:09.389Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589154,730939,0,676,TO_TIMESTAMP('2024-09-22 01:07:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cash Last Balance',TO_TIMESTAMP('2024-09-22 01:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T22:07:09.392Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T22:07:09.393Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583278) 
;

-- 2024-09-21T22:07:09.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730939
;

-- 2024-09-21T22:07:09.399Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730939)
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20
-- UI Element Group: cash journal
-- 2024-09-21T22:07:34.477Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547556,551979,TO_TIMESTAMP('2024-09-22 01:07:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','cash journal',30,TO_TIMESTAMP('2024-09-22 01:07:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20
-- UI Element Group: org & client
-- 2024-09-21T22:07:54.891Z
UPDATE AD_UI_ElementGroup SET SeqNo=990,Updated=TO_TIMESTAMP('2024-09-22 01:07:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551966
;

-- UI Column: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20
-- UI Element Group: cash journal
-- 2024-09-21T22:07:56.756Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2024-09-22 01:07:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551979
;

-- UI Element: POS-Terminal -> POS-Terminal.Cash Last Balance
-- Column: C_POS.CashLastBalance
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> cash journal.Cash Last Balance
-- Column: C_POS.CashLastBalance
-- 2024-09-21T22:08:13.415Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730939,0,676,551979,625806,'F',TO_TIMESTAMP('2024-09-22 01:08:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cash Last Balance',10,0,0,TO_TIMESTAMP('2024-09-22 01:08:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.POS Cash Journal
-- Column: C_POS.C_POS_Journal_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 20 -> cash journal.POS Cash Journal
-- Column: C_POS.C_POS_Journal_ID
-- 2024-09-21T22:08:20.620Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730938,0,676,551979,625807,'F',TO_TIMESTAMP('2024-09-22 01:08:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Cash Journal',20,0,0,TO_TIMESTAMP('2024-09-22 01:08:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: POS-Terminal -> POS-Terminal -> Cash Last Balance
-- Column: C_POS.CashLastBalance
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> Cash Last Balance
-- Column: C_POS.CashLastBalance
-- 2024-09-21T22:08:34.006Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-09-22 01:08:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=730939
;

-- Field: POS-Terminal -> POS-Terminal -> POS Cash Journal
-- Column: C_POS.C_POS_Journal_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> POS Cash Journal
-- Column: C_POS.C_POS_Journal_ID
-- 2024-09-21T22:08:35.740Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-09-22 01:08:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=730938
;

-- Column: C_POS.CashLastBalance
-- Column: C_POS.CashLastBalance
-- 2024-09-21T22:10:20.758Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2024-09-22 01:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589154
;

-- Column: C_POS.C_BPartnerCashTrx_ID
-- Column: C_POS.C_BPartnerCashTrx_ID
-- 2024-09-21T22:10:24.119Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2024-09-22 01:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12785
;

-- Column: C_POS.C_BPartnerCashTrx_ID
-- Column: C_POS.C_BPartnerCashTrx_ID
-- 2024-09-21T22:10:27.742Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='N',Updated=TO_TIMESTAMP('2024-09-22 01:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12785
;

-- Column: C_POS.C_POS_Journal_ID
-- Column: C_POS.C_POS_Journal_ID
-- 2024-09-21T22:10:49.332Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2024-09-22 01:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589153
;


































create index c_pos_journal_terminal on c_pos_journal(c_pos_id);
create index c_pos_journalline_parent on c_pos_journalline(c_pos_journal_id);


















-- Column: C_POS_Journal.DocumentNo
-- Column: C_POS_Journal.DocumentNo
-- 2024-09-21T22:17:13.198Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589155,290,0,10,542438,'XX','DocumentNo',TO_TIMESTAMP('2024-09-22 01:17:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Document sequence number of the document','de.metas.pos',0,40,'E','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','Y',0,'Nr.',0,1,TO_TIMESTAMP('2024-09-22 01:17:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-21T22:17:13.204Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589155 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-21T22:17:13.207Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- 2024-09-21T22:17:13.827Z
/* DDL */ SELECT public.db_alter_table('C_POS_Journal','ALTER TABLE public.C_POS_Journal ADD COLUMN DocumentNo VARCHAR(40) NOT NULL')
;

-- Field: POS Cash Journal -> POS Cash Journal -> Nr.
-- Column: C_POS_Journal.DocumentNo
-- Field: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> Nr.
-- Column: C_POS_Journal.DocumentNo
-- 2024-09-21T22:17:52.351Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589155,730940,0,547594,TO_TIMESTAMP('2024-09-22 01:17:52','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',40,'de.metas.pos','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2024-09-22 01:17:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-21T22:17:52.354Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-21T22:17:52.358Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2024-09-21T22:17:52.400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730940
;

-- 2024-09-21T22:17:52.402Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730940)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Nr.
-- Column: C_POS_Journal.DocumentNo
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.Nr.
-- Column: C_POS_Journal.DocumentNo
-- 2024-09-21T22:18:13.786Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730940,0,547594,551972,625808,'F',TO_TIMESTAMP('2024-09-22 01:18:13','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Nr.',30,0,0,TO_TIMESTAMP('2024-09-22 01:18:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Nr.
-- Column: C_POS_Journal.DocumentNo
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.Nr.
-- Column: C_POS_Journal.DocumentNo
-- 2024-09-21T22:18:28.617Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2024-09-22 01:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625808
;

-- UI Element: POS Cash Journal -> POS Cash Journal.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T22:18:33.566Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-09-22 01:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625790
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Nr.
-- Column: C_POS_Journal.DocumentNo
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.Nr.
-- Column: C_POS_Journal.DocumentNo
-- 2024-09-21T22:20:29.462Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-22 01:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625808
;

-- UI Element: POS Cash Journal -> POS Cash Journal.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> default.POS-Terminal
-- Column: C_POS_Journal.C_POS_ID
-- 2024-09-21T22:20:29.472Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-22 01:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625790
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Geschlossen
-- Column: C_POS_Journal.IsClosed
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 20 -> flags & status.Geschlossen
-- Column: C_POS_Journal.IsClosed
-- 2024-09-21T22:20:29.480Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-22 01:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625795
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Währung
-- Column: C_POS_Journal.C_Currency_ID
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Währung
-- Column: C_POS_Journal.C_Currency_ID
-- 2024-09-21T22:20:29.489Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-09-22 01:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625792
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Anfangssaldo
-- Column: C_POS_Journal.CashBeginningBalance
-- 2024-09-21T22:20:29.507Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-22 01:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625798
;

-- UI Element: POS Cash Journal -> POS Cash Journal.Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- UI Element: POS Cash Journal(541819,de.metas.pos) -> POS Cash Journal(547594,de.metas.pos) -> main -> 10 -> cash.Endsaldo
-- Column: C_POS_Journal.CashEndingBalance
-- 2024-09-21T22:20:29.515Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-22 01:20:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625799
;

