-- Table: SUMUP_API_Log
-- Table: SUMUP_API_Log
-- 2024-10-03T20:58:22.143Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542442,'A','N',TO_TIMESTAMP('2024-10-03 23:58:21','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.payment.sumup','N','Y','N','N','Y','N','N','Y','N','N',0,'SumUp API Log','NP','L','SUMUP_API_Log','DTI',TO_TIMESTAMP('2024-10-03 23:58:21','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-10-03T20:58:22.149Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542442 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-10-03T20:58:22.280Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,
                         StartNo,Updated,UpdatedBy) 
VALUES (0,0,556373,TO_TIMESTAMP('2024-10-03 23:58:22','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table SUMUP_API_Log',1,'Y','N','Y','Y','SUMUP_API_Log',
        1000000,TO_TIMESTAMP('2024-10-03 23:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T20:58:22.286Z
CREATE SEQUENCE SUMUP_API_LOG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: SUMUP_API_Log.AD_Client_ID
-- Column: SUMUP_API_Log.AD_Client_ID
-- 2024-10-03T20:59:06.902Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589203,102,0,19,542442,'AD_Client_ID',TO_TIMESTAMP('2024-10-03 23:59:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.payment.sumup',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-10-03 23:59:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:06.906Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589203 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:06.909Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: SUMUP_API_Log.AD_Org_ID
-- Column: SUMUP_API_Log.AD_Org_ID
-- 2024-10-03T20:59:07.687Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589204,113,0,30,542442,'AD_Org_ID',TO_TIMESTAMP('2024-10-03 23:59:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.payment.sumup',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-10-03 23:59:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:07.689Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589204 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:07.693Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: SUMUP_API_Log.Created
-- Column: SUMUP_API_Log.Created
-- 2024-10-03T20:59:08.292Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589205,245,0,16,542442,'Created',TO_TIMESTAMP('2024-10-03 23:59:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.payment.sumup',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-10-03 23:59:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:08.294Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589205 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:08.298Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: SUMUP_API_Log.CreatedBy
-- Column: SUMUP_API_Log.CreatedBy
-- 2024-10-03T20:59:08.911Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589206,246,0,18,110,542442,'CreatedBy',TO_TIMESTAMP('2024-10-03 23:59:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.payment.sumup',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-10-03 23:59:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:08.913Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589206 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:08.915Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: SUMUP_API_Log.IsActive
-- Column: SUMUP_API_Log.IsActive
-- 2024-10-03T20:59:09.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589207,348,0,20,542442,'IsActive',TO_TIMESTAMP('2024-10-03 23:59:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.payment.sumup',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-10-03 23:59:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:09.475Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589207 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:09.480Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: SUMUP_API_Log.Updated
-- Column: SUMUP_API_Log.Updated
-- 2024-10-03T20:59:10.051Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589208,607,0,16,542442,'Updated',TO_TIMESTAMP('2024-10-03 23:59:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.payment.sumup',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-10-03 23:59:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:10.053Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589208 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:10.055Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: SUMUP_API_Log.UpdatedBy
-- Column: SUMUP_API_Log.UpdatedBy
-- 2024-10-03T20:59:10.643Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589209,608,0,18,110,542442,'UpdatedBy',TO_TIMESTAMP('2024-10-03 23:59:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.payment.sumup',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-10-03 23:59:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:10.645Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589209 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:10.648Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-10-03T20:59:11.105Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583302,0,'SUMUP_API_Log_ID',TO_TIMESTAMP('2024-10-03 23:59:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','SumUp API Log','SumUp API Log',TO_TIMESTAMP('2024-10-03 23:59:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T20:59:11.107Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583302 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_API_Log.SUMUP_API_Log_ID
-- Column: SUMUP_API_Log.SUMUP_API_Log_ID
-- 2024-10-03T20:59:11.636Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589210,583302,0,13,542442,'SUMUP_API_Log_ID',TO_TIMESTAMP('2024-10-03 23:59:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','SumUp API Log',0,0,TO_TIMESTAMP('2024-10-03 23:59:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T20:59:11.637Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589210 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T20:59:11.640Z
/* DDL */  select update_Column_Translation_From_AD_Element(583302) 
;

-- Column: SUMUP_API_Log.Method
-- Column: SUMUP_API_Log.Method
-- 2024-10-03T21:01:12.053Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589211,579102,0,17,541306,542442,'XX','Method',TO_TIMESTAMP('2024-10-04 00:01:11','YYYY-MM-DD HH24:MI:SS'),100,'N','HTTP-Methode, die von dieser Zeile gematcht wird. Ein leeres Feld matcht alle Methoden.','de.metas.payment.sumup',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Methode',0,0,TO_TIMESTAMP('2024-10-04 00:01:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:01:12.055Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589211 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:01:12.058Z
/* DDL */  select update_Column_Translation_From_AD_Element(579102) 
;

-- Column: SUMUP_API_Log.URL
-- Column: SUMUP_API_Log.URL
-- 2024-10-03T21:01:54.987Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589212,983,0,10,542442,'XX','URL',TO_TIMESTAMP('2024-10-04 00:01:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Z.B. http://www.metasfresh.com','de.metas.payment.sumup',0,2000,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'URL',0,0,TO_TIMESTAMP('2024-10-04 00:01:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:01:54.990Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589212 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:01:54.993Z
/* DDL */  select update_Column_Translation_From_AD_Element(983) 
;

-- Column: SUMUP_API_Log.RequestURI
-- Column: SUMUP_API_Log.RequestURI
-- 2024-10-03T21:02:15.666Z
UPDATE AD_Column SET AD_Element_ID=579274, ColumnName='RequestURI', Description=NULL, FieldLength=1024, Help=NULL, Name='URI',Updated=TO_TIMESTAMP('2024-10-04 00:02:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589212
;

-- 2024-10-03T21:02:15.667Z
UPDATE AD_Field SET Name='URI', Description=NULL, Help=NULL WHERE AD_Column_ID=589212
;

-- 2024-10-03T21:02:15.679Z
/* DDL */  select update_Column_Translation_From_AD_Element(579274) 
;

-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- 2024-10-03T21:02:39.759Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589213,583298,0,10,542442,'XX','SUMUP_merchant_code',TO_TIMESTAMP('2024-10-04 00:02:39','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Merchant Code',0,0,TO_TIMESTAMP('2024-10-04 00:02:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:02:39.761Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:02:39.764Z
/* DDL */  select update_Column_Translation_From_AD_Element(583298) 
;

-- Column: SUMUP_API_Log.RequestBody
-- Column: SUMUP_API_Log.RequestBody
-- 2024-10-03T21:02:51.135Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589214,576882,0,36,542442,'XX','RequestBody',TO_TIMESTAMP('2024-10-04 00:02:50','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Request Body',0,0,TO_TIMESTAMP('2024-10-04 00:02:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:02:51.137Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589214 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:02:51.139Z
/* DDL */  select update_Column_Translation_From_AD_Element(576882) 
;

-- Column: SUMUP_API_Log.ResponseBody
-- Column: SUMUP_API_Log.ResponseBody
-- 2024-10-03T21:03:08.027Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589215,576885,0,36,542442,'XX','ResponseBody',TO_TIMESTAMP('2024-10-04 00:03:07','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Response Body',0,0,TO_TIMESTAMP('2024-10-04 00:03:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:03:08.029Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589215 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:03:08.031Z
/* DDL */  select update_Column_Translation_From_AD_Element(576885) 
;

-- Column: SUMUP_API_Log.ResponseCode
-- Column: SUMUP_API_Log.ResponseCode
-- 2024-10-03T21:04:15.940Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589216,576236,0,11,542442,'XX','ResponseCode',TO_TIMESTAMP('2024-10-04 00:04:15','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.payment.sumup',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Antwort ',0,0,TO_TIMESTAMP('2024-10-04 00:04:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:04:15.943Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589216 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:04:15.946Z
/* DDL */  select update_Column_Translation_From_AD_Element(576236) 
;

-- Column: SUMUP_API_Log.ResponseCode
-- Column: SUMUP_API_Log.ResponseCode
-- 2024-10-03T21:04:18.429Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-10-04 00:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589216
;

-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- 2024-10-03T21:04:36.081Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589217,583297,0,30,542442,'XX','SUMUP_Config_ID',TO_TIMESTAMP('2024-10-04 00:04:35','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SumUp Configuration',0,0,TO_TIMESTAMP('2024-10-04 00:04:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:04:36.083Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589217 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:04:36.085Z
/* DDL */  select update_Column_Translation_From_AD_Element(583297) 
;

-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- 2024-10-03T21:04:46.641Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:04:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589217
;

-- Column: SUMUP_API_Log.RequestURI
-- Column: SUMUP_API_Log.RequestURI
-- 2024-10-03T21:04:56.879Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:04:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589212
;

-- Column: SUMUP_API_Log.ResponseCode
-- Column: SUMUP_API_Log.ResponseCode
-- 2024-10-03T21:05:05.504Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589216
;

-- Column: SUMUP_API_Log.Created
-- Column: SUMUP_API_Log.Created
-- 2024-10-03T21:05:15.087Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:05:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589205
;

-- Column: SUMUP_API_Log.Method
-- Column: SUMUP_API_Log.Method
-- 2024-10-03T21:05:27.011Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589211
;

-- Column: SUMUP_API_Log.ResponseBody
-- Column: SUMUP_API_Log.ResponseBody
-- 2024-10-03T21:05:33.459Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589215
;

-- Column: SUMUP_API_Log.RequestBody
-- Column: SUMUP_API_Log.RequestBody
-- 2024-10-03T21:05:45.056Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:05:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589214
;

-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- 2024-10-03T21:05:56.656Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:05:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589213
;

-- Column: SUMUP_API_Log.AD_Issue_ID
-- Column: SUMUP_API_Log.AD_Issue_ID
-- 2024-10-03T21:06:48.399Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589218,2887,0,30,542442,'XX','AD_Issue_ID',TO_TIMESTAMP('2024-10-04 00:06:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','','de.metas.payment.sumup',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Probleme',0,0,TO_TIMESTAMP('2024-10-04 00:06:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T21:06:48.401Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589218 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T21:06:48.403Z
/* DDL */  select update_Column_Translation_From_AD_Element(2887) 
;

-- Column: SUMUP_API_Log.AD_Issue_ID
-- Column: SUMUP_API_Log.AD_Issue_ID
-- 2024-10-03T21:06:53.636Z
UPDATE AD_Column SET FilterOperator='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 00:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589218
;

-- 2024-10-03T21:08:16.223Z
/* DDL */ CREATE TABLE public.SUMUP_API_Log (AD_Client_ID NUMERIC(10) NOT NULL, AD_Issue_ID NUMERIC(10), AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Method VARCHAR(255), RequestBody TEXT, RequestURI VARCHAR(1024), ResponseBody TEXT, ResponseCode NUMERIC(10), SUMUP_API_Log_ID NUMERIC(10) NOT NULL, SUMUP_Config_ID NUMERIC(10), SUMUP_merchant_code VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT SUMUP_API_Log_Key PRIMARY KEY (SUMUP_API_Log_ID), CONSTRAINT SUMUPConfig_SUMUPAPILog FOREIGN KEY (SUMUP_Config_ID) REFERENCES public.SUMUP_Config DEFERRABLE INITIALLY DEFERRED)
;

-- Window: SumUp API Log, InternalName=null
-- Window: SumUp API Log, InternalName=null
-- 2024-10-03T21:08:43.270Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583302,0,541824,TO_TIMESTAMP('2024-10-04 00:08:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','N','N','N','N','N','N','Y','SumUp API Log','N',TO_TIMESTAMP('2024-10-04 00:08:43','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-10-03T21:08:43.274Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541824 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-10-03T21:08:43.279Z
/* DDL */  select update_window_translation_from_ad_element(583302) 
;

-- 2024-10-03T21:08:43.282Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541824
;

-- 2024-10-03T21:08:43.288Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541824)
;

-- Window: SumUp API Log, InternalName=sumUpLog
-- Window: SumUp API Log, InternalName=sumUpLog
-- 2024-10-03T21:09:00.033Z
UPDATE AD_Window SET InternalName='sumUpLog',Updated=TO_TIMESTAMP('2024-10-04 00:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541824
;

-- Tab: SumUp API Log -> SumUp API Log
-- Table: SUMUP_API_Log
-- Tab: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log
-- Table: SUMUP_API_Log
-- 2024-10-03T21:09:20.793Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583302,0,547616,542442,541824,'Y',TO_TIMESTAMP('2024-10-04 00:09:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','N','N','A','SUMUP_API_Log','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'SumUp API Log','N',10,0,TO_TIMESTAMP('2024-10-04 00:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:20.796Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547616 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-03T21:09:20.798Z
/* DDL */  select update_tab_translation_from_ad_element(583302) 
;

-- 2024-10-03T21:09:20.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547616)
;

-- Field: SumUp API Log -> SumUp API Log -> Mandant
-- Column: SUMUP_API_Log.AD_Client_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Mandant
-- Column: SUMUP_API_Log.AD_Client_ID
-- 2024-10-03T21:09:28.885Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589203,731813,0,547616,TO_TIMESTAMP('2024-10-04 00:09:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.payment.sumup','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-04 00:09:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:28.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:28.891Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-03T21:09:29.720Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731813
;

-- 2024-10-03T21:09:29.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731813)
;

-- Field: SumUp API Log -> SumUp API Log -> Organisation
-- Column: SUMUP_API_Log.AD_Org_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Organisation
-- Column: SUMUP_API_Log.AD_Org_ID
-- 2024-10-03T21:09:29.838Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589204,731814,0,547616,TO_TIMESTAMP('2024-10-04 00:09:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.payment.sumup','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-10-04 00:09:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:29.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:29.844Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-03T21:09:29.963Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731814
;

-- 2024-10-03T21:09:29.965Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731814)
;

-- Field: SumUp API Log -> SumUp API Log -> Aktiv
-- Column: SUMUP_API_Log.IsActive
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Aktiv
-- Column: SUMUP_API_Log.IsActive
-- 2024-10-03T21:09:30.075Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589207,731815,0,547616,TO_TIMESTAMP('2024-10-04 00:09:29','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.payment.sumup','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-04 00:09:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:30.076Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:30.078Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-03T21:09:30.238Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731815
;

-- 2024-10-03T21:09:30.239Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731815)
;

-- Field: SumUp API Log -> SumUp API Log -> SumUp API Log
-- Column: SUMUP_API_Log.SUMUP_API_Log_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> SumUp API Log
-- Column: SUMUP_API_Log.SUMUP_API_Log_ID
-- 2024-10-03T21:09:30.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589210,731816,0,547616,TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','SumUp API Log',TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:30.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:30.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583302) 
;

-- 2024-10-03T21:09:30.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731816
;

-- 2024-10-03T21:09:30.364Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731816)
;

-- Field: SumUp API Log -> SumUp API Log -> Methode
-- Column: SUMUP_API_Log.Method
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Methode
-- Column: SUMUP_API_Log.Method
-- 2024-10-03T21:09:30.477Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589211,731817,0,547616,TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100,'HTTP-Methode, die von dieser Zeile gematcht wird. Ein leeres Feld matcht alle Methoden.',255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Methode',TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:30.479Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:30.481Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579102) 
;

-- 2024-10-03T21:09:30.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731817
;

-- 2024-10-03T21:09:30.489Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731817)
;

-- Field: SumUp API Log -> SumUp API Log -> URI
-- Column: SUMUP_API_Log.RequestURI
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> URI
-- Column: SUMUP_API_Log.RequestURI
-- 2024-10-03T21:09:30.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589212,731818,0,547616,TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100,1024,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','URI',TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:30.615Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:30.617Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579274) 
;

-- 2024-10-03T21:09:30.623Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731818
;

-- 2024-10-03T21:09:30.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731818)
;

-- Field: SumUp API Log -> SumUp API Log -> Merchant Code
-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Merchant Code
-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- 2024-10-03T21:09:30.735Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589213,731819,0,547616,TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Merchant Code',TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:30.737Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:30.738Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583298) 
;

-- 2024-10-03T21:09:30.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731819
;

-- 2024-10-03T21:09:30.742Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731819)
;

-- Field: SumUp API Log -> SumUp API Log -> Request Body
-- Column: SUMUP_API_Log.RequestBody
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Request Body
-- Column: SUMUP_API_Log.RequestBody
-- 2024-10-03T21:09:30.858Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589214,731820,0,547616,TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Request Body',TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:30.859Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:30.861Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576882) 
;

-- 2024-10-03T21:09:30.865Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731820
;

-- 2024-10-03T21:09:30.866Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731820)
;

-- Field: SumUp API Log -> SumUp API Log -> Response Body
-- Column: SUMUP_API_Log.ResponseBody
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Response Body
-- Column: SUMUP_API_Log.ResponseBody
-- 2024-10-03T21:09:30.987Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589215,731821,0,547616,TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Response Body',TO_TIMESTAMP('2024-10-04 00:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:30.989Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:30.992Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576885) 
;

-- 2024-10-03T21:09:30.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731821
;

-- 2024-10-03T21:09:30.997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731821)
;

-- Field: SumUp API Log -> SumUp API Log -> Antwort
-- Column: SUMUP_API_Log.ResponseCode
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Antwort
-- Column: SUMUP_API_Log.ResponseCode
-- 2024-10-03T21:09:31.120Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589216,731822,0,547616,TO_TIMESTAMP('2024-10-04 00:09:31','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Antwort ',TO_TIMESTAMP('2024-10-04 00:09:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:31.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:31.123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576236) 
;

-- 2024-10-03T21:09:31.130Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731822
;

-- 2024-10-03T21:09:31.131Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731822)
;

-- Field: SumUp API Log -> SumUp API Log -> SumUp Configuration
-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> SumUp Configuration
-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- 2024-10-03T21:09:31.247Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589217,731823,0,547616,TO_TIMESTAMP('2024-10-04 00:09:31','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','SumUp Configuration',TO_TIMESTAMP('2024-10-04 00:09:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:31.249Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:31.251Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583297) 
;

-- 2024-10-03T21:09:31.256Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731823
;

-- 2024-10-03T21:09:31.257Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731823)
;

-- Field: SumUp API Log -> SumUp API Log -> Probleme
-- Column: SUMUP_API_Log.AD_Issue_ID
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Probleme
-- Column: SUMUP_API_Log.AD_Issue_ID
-- 2024-10-03T21:09:31.371Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589218,731824,0,547616,TO_TIMESTAMP('2024-10-04 00:09:31','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.payment.sumup','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2024-10-04 00:09:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:09:31.372Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:09:31.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2024-10-03T21:09:31.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731824
;

-- 2024-10-03T21:09:31.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731824)
;

-- Tab: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup)
-- UI Section: main
-- 2024-10-03T21:09:42.278Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547616,546204,TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-10-03T21:09:42.281Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546204 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main
-- UI Column: 10
-- 2024-10-03T21:09:42.402Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547587,546204,TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main
-- UI Column: 20
-- 2024-10-03T21:09:42.515Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547588,546204,TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: default
-- 2024-10-03T21:09:42.628Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547587,552027,TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-10-04 00:09:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: request
-- 2024-10-03T21:10:06.243Z
UPDATE AD_UI_ElementGroup SET Name='request', UIStyle='',Updated=TO_TIMESTAMP('2024-10-04 00:10:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552027
;

-- UI Element: SumUp API Log -> SumUp API Log.URI
-- Column: SUMUP_API_Log.RequestURI
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> request.URI
-- Column: SUMUP_API_Log.RequestURI
-- 2024-10-03T21:10:34.912Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731818,0,547616,552027,626123,'F',TO_TIMESTAMP('2024-10-04 00:10:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','URI',10,0,0,TO_TIMESTAMP('2024-10-04 00:10:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.Methode
-- Column: SUMUP_API_Log.Method
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> request.Methode
-- Column: SUMUP_API_Log.Method
-- 2024-10-03T21:10:43.260Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731817,0,547616,552027,626124,'F',TO_TIMESTAMP('2024-10-04 00:10:42','YYYY-MM-DD HH24:MI:SS'),100,'HTTP-Methode, die von dieser Zeile gematcht wird. Ein leeres Feld matcht alle Methoden.','Y','N','Y','N','N','Methode',20,0,0,TO_TIMESTAMP('2024-10-04 00:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.Request Body
-- Column: SUMUP_API_Log.RequestBody
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> request.Request Body
-- Column: SUMUP_API_Log.RequestBody
-- 2024-10-03T21:10:58.389Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731820,0,547616,552027,626125,'F',TO_TIMESTAMP('2024-10-04 00:10:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Request Body',30,0,0,TO_TIMESTAMP('2024-10-04 00:10:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: response
-- 2024-10-03T21:11:11.039Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547588,552028,TO_TIMESTAMP('2024-10-04 00:11:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','response',10,TO_TIMESTAMP('2024-10-04 00:11:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.Antwort
-- Column: SUMUP_API_Log.ResponseCode
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 20 -> response.Antwort
-- Column: SUMUP_API_Log.ResponseCode
-- 2024-10-03T21:11:36.646Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731822,0,547616,552028,626126,'F',TO_TIMESTAMP('2024-10-04 00:11:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Antwort ',10,0,0,TO_TIMESTAMP('2024-10-04 00:11:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.Response Body
-- Column: SUMUP_API_Log.ResponseBody
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 20 -> response.Response Body
-- Column: SUMUP_API_Log.ResponseBody
-- 2024-10-03T21:11:50.718Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731821,0,547616,552028,626127,'F',TO_TIMESTAMP('2024-10-04 00:11:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Response Body',20,0,0,TO_TIMESTAMP('2024-10-04 00:11:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.Probleme
-- Column: SUMUP_API_Log.AD_Issue_ID
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 20 -> response.Probleme
-- Column: SUMUP_API_Log.AD_Issue_ID
-- 2024-10-03T21:12:01.939Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731824,0,547616,552028,626128,'F',TO_TIMESTAMP('2024-10-04 00:12:00','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Probleme',30,0,0,TO_TIMESTAMP('2024-10-04 00:12:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: context
-- 2024-10-03T21:12:17.144Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547587,552029,TO_TIMESTAMP('2024-10-04 00:12:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','context',20,TO_TIMESTAMP('2024-10-04 00:12:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.SumUp Configuration
-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> context.SumUp Configuration
-- Column: SUMUP_API_Log.SUMUP_Config_ID
-- 2024-10-03T21:12:29.496Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731823,0,547616,552029,626129,'F',TO_TIMESTAMP('2024-10-04 00:12:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','SumUp Configuration',10,0,0,TO_TIMESTAMP('2024-10-04 00:12:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.Merchant Code
-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> context.Merchant Code
-- Column: SUMUP_API_Log.SUMUP_merchant_code
-- 2024-10-03T21:12:38.333Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731819,0,547616,552029,626130,'F',TO_TIMESTAMP('2024-10-04 00:12:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Merchant Code',20,0,0,TO_TIMESTAMP('2024-10-04 00:12:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.URI
-- Column: SUMUP_API_Log.RequestURI
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> request.URI
-- Column: SUMUP_API_Log.RequestURI
-- 2024-10-03T21:13:09.862Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-04 00:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626123
;

-- UI Element: SumUp API Log -> SumUp API Log.Methode
-- Column: SUMUP_API_Log.Method
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> request.Methode
-- Column: SUMUP_API_Log.Method
-- 2024-10-03T21:13:09.869Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-04 00:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626124
;

-- UI Element: SumUp API Log -> SumUp API Log.Antwort
-- Column: SUMUP_API_Log.ResponseCode
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 20 -> response.Antwort
-- Column: SUMUP_API_Log.ResponseCode
-- 2024-10-03T21:13:09.876Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-04 00:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626126
;

-- Field: SumUp API Log -> SumUp API Log -> Erstellt
-- Column: SUMUP_API_Log.Created
-- Field: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> Erstellt
-- Column: SUMUP_API_Log.Created
-- 2024-10-03T21:13:23.034Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589205,731825,0,547616,TO_TIMESTAMP('2024-10-04 00:13:22','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.payment.sumup','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-10-04 00:13:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:13:23.037Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T21:13:23.039Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2024-10-03T21:13:23.125Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731825
;

-- 2024-10-03T21:13:23.126Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731825)
;

-- UI Element: SumUp API Log -> SumUp API Log.Erstellt
-- Column: SUMUP_API_Log.Created
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> request.Erstellt
-- Column: SUMUP_API_Log.Created
-- 2024-10-03T21:13:47.906Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731825,0,547616,552027,626131,'F',TO_TIMESTAMP('2024-10-04 00:13:47','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','N','Erstellt',40,0,0,TO_TIMESTAMP('2024-10-04 00:13:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp API Log -> SumUp API Log.Erstellt
-- Column: SUMUP_API_Log.Created
-- UI Element: SumUp API Log(541824,de.metas.payment.sumup) -> SumUp API Log(547616,de.metas.payment.sumup) -> main -> 10 -> request.Erstellt
-- Column: SUMUP_API_Log.Created
-- 2024-10-03T21:13:53.275Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-04 00:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626131
;

-- Name: SumUp API Log
-- Action Type: W
-- Window: SumUp API Log(541824,de.metas.payment.sumup)
-- 2024-10-03T21:14:25.091Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583302,542177,0,541824,TO_TIMESTAMP('2024-10-04 00:14:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','sumUpLog','Y','N','N','N','N','SumUp API Log',TO_TIMESTAMP('2024-10-04 00:14:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T21:14:25.093Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542177 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-10-03T21:14:25.096Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542177, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542177)
;

-- 2024-10-03T21:14:25.100Z
/* DDL */  select update_menu_translation_from_ad_element(583302) 
;

-- Reordering children of `SumUp`
-- Node name: `SumUp Configuration (SUMUP_Config)`
-- 2024-10-03T21:14:33.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=542175, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542176 AND AD_Tree_ID=10
;

-- Node name: `SumUp API Log`
-- 2024-10-03T21:14:33.236Z
UPDATE AD_TreeNodeMM SET Parent_ID=542175, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542177 AND AD_Tree_ID=10
;

