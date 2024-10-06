-- Table: SUMUP_Transaction
-- Table: SUMUP_Transaction
-- 2024-10-04T06:29:44.343Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542443,'A','N',TO_TIMESTAMP('2024-10-04 09:29:44','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.payment.sumup','N','Y','N','N','Y','N','N','Y','N','N',0,'SumUp Transaction','NP','L','SUMUP_Transaction','DTI',TO_TIMESTAMP('2024-10-04 09:29:44','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-10-04T06:29:44.355Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542443 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-10-04T06:29:44.469Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,
                         StartNo,Updated,UpdatedBy) 
VALUES (0,0,556374,TO_TIMESTAMP('2024-10-04 09:29:44','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table SUMUP_Transaction',1,'Y','N','Y','Y','SUMUP_Transaction',
        1000000,TO_TIMESTAMP('2024-10-04 09:29:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:29:44.477Z
CREATE SEQUENCE SUMUP_TRANSACTION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: SUMUP_Transaction.AD_Client_ID
-- Column: SUMUP_Transaction.AD_Client_ID
-- 2024-10-04T06:29:47.547Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589219,102,0,19,542443,'AD_Client_ID',TO_TIMESTAMP('2024-10-04 09:29:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.payment.sumup',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-10-04 09:29:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:47.551Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589219 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:47.561Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: SUMUP_Transaction.AD_Org_ID
-- Column: SUMUP_Transaction.AD_Org_ID
-- 2024-10-04T06:29:48.394Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589220,113,0,30,542443,'AD_Org_ID',TO_TIMESTAMP('2024-10-04 09:29:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.payment.sumup',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-10-04 09:29:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:48.397Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589220 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:48.400Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: SUMUP_Transaction.Created
-- Column: SUMUP_Transaction.Created
-- 2024-10-04T06:29:48.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589221,245,0,16,542443,'Created',TO_TIMESTAMP('2024-10-04 09:29:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.payment.sumup',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-10-04 09:29:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:48.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589221 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:48.959Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: SUMUP_Transaction.CreatedBy
-- Column: SUMUP_Transaction.CreatedBy
-- 2024-10-04T06:29:49.562Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589222,246,0,18,110,542443,'CreatedBy',TO_TIMESTAMP('2024-10-04 09:29:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.payment.sumup',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-10-04 09:29:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:49.563Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589222 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:49.565Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: SUMUP_Transaction.IsActive
-- Column: SUMUP_Transaction.IsActive
-- 2024-10-04T06:29:50.142Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589223,348,0,20,542443,'IsActive',TO_TIMESTAMP('2024-10-04 09:29:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.payment.sumup',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-10-04 09:29:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:50.145Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589223 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:50.147Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: SUMUP_Transaction.Updated
-- Column: SUMUP_Transaction.Updated
-- 2024-10-04T06:29:50.709Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589224,607,0,16,542443,'Updated',TO_TIMESTAMP('2024-10-04 09:29:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.payment.sumup',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-10-04 09:29:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:50.711Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589224 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:50.713Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: SUMUP_Transaction.UpdatedBy
-- Column: SUMUP_Transaction.UpdatedBy
-- 2024-10-04T06:29:51.323Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589225,608,0,18,110,542443,'UpdatedBy',TO_TIMESTAMP('2024-10-04 09:29:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.payment.sumup',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-10-04 09:29:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:51.325Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589225 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:51.327Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-10-04T06:29:51.803Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583303,0,'SUMUP_Transaction_ID',TO_TIMESTAMP('2024-10-04 09:29:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','SumUp Transaction','SumUp Transaction',TO_TIMESTAMP('2024-10-04 09:29:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:29:51.806Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583303 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- 2024-10-04T06:29:52.327Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589226,583303,0,13,542443,'SUMUP_Transaction_ID',TO_TIMESTAMP('2024-10-04 09:29:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','SumUp Transaction',0,0,TO_TIMESTAMP('2024-10-04 09:29:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:29:52.328Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589226 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:29:52.331Z
/* DDL */  select update_Column_Translation_From_AD_Element(583303) 
;

-- Column: SUMUP_Transaction.ExternalId
-- Column: SUMUP_Transaction.ExternalId
-- 2024-10-04T06:33:14.457Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589227,543939,0,10,542443,'XX','ExternalId',TO_TIMESTAMP('2024-10-04 09:33:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2024-10-04 09:33:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:33:14.459Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589227 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:33:14.462Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- 2024-10-04T06:38:47.318Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583304,0,'SUMUP_ClientTransactionId',TO_TIMESTAMP('2024-10-04 09:38:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Client Transaction Id','Client Transaction Id',TO_TIMESTAMP('2024-10-04 09:38:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:38:47.325Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583304 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- 2024-10-04T06:39:15.092Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589228,583304,0,10,542443,'XX','SUMUP_ClientTransactionId',TO_TIMESTAMP('2024-10-04 09:39:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Client Transaction Id',0,0,TO_TIMESTAMP('2024-10-04 09:39:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:39:15.094Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589228 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:39:15.097Z
/* DDL */  select update_Column_Translation_From_AD_Element(583304) 
;

-- Column: SUMUP_Transaction.ExternalId
-- Column: SUMUP_Transaction.ExternalId
-- 2024-10-04T06:39:19.490Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2024-10-04 09:39:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589227
;

-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- 2024-10-04T06:39:38.869Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589229,583298,0,10,542443,'XX','SUMUP_merchant_code',TO_TIMESTAMP('2024-10-04 09:39:38','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Merchant Code',0,0,TO_TIMESTAMP('2024-10-04 09:39:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:39:38.870Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589229 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:39:38.873Z
/* DDL */  select update_Column_Translation_From_AD_Element(583298) 
;

-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- 2024-10-04T06:39:45.912Z
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-10-04 09:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589229
;

-- Column: SUMUP_Transaction.Timestamp
-- Column: SUMUP_Transaction.Timestamp
-- 2024-10-04T06:40:21.906Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589230,579160,0,16,542443,'XX','Timestamp',TO_TIMESTAMP('2024-10-04 09:40:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Zeitstempel',0,0,TO_TIMESTAMP('2024-10-04 09:40:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:40:21.908Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589230 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:40:21.910Z
/* DDL */  select update_Column_Translation_From_AD_Element(579160) 
;

-- Column: SUMUP_Transaction.Status
-- Column: SUMUP_Transaction.Status
-- 2024-10-04T06:40:51.743Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589231,3020,0,10,542443,'XX','Status',TO_TIMESTAMP('2024-10-04 09:40:50','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.payment.sumup',0,255,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Status',0,0,TO_TIMESTAMP('2024-10-04 09:40:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:40:51.745Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589231 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:40:51.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(3020) 
;

-- Column: SUMUP_Transaction.CurrencyCode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:41:17.829Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589232,577559,0,10,542443,'XX','CurrencyCode',TO_TIMESTAMP('2024-10-04 09:41:17','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währungscode',0,0,TO_TIMESTAMP('2024-10-04 09:41:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:41:17.832Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589232 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:41:17.835Z
/* DDL */  select update_Column_Translation_From_AD_Element(577559) 
;

-- Column: SUMUP_Transaction.Amount
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:41:34.154Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589233,1367,0,12,542443,'XX','Amount',TO_TIMESTAMP('2024-10-04 09:41:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Betrag in einer definierten Währung','de.metas.payment.sumup',0,10,'"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Betrag',0,0,TO_TIMESTAMP('2024-10-04 09:41:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:41:34.156Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589233 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:41:34.160Z
/* DDL */  select update_Column_Translation_From_AD_Element(1367) 
;

-- Column: SUMUP_Transaction.Amount
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:41:38.712Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-10-04 09:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589233
;

-- Column: SUMUP_Transaction.CurrencyCode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:41:44.479Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-10-04 09:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589232
;

-- Column: SUMUP_Transaction.JsonResponse
-- Column: SUMUP_Transaction.JsonResponse
-- 2024-10-04T06:42:33.437Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589234,578123,0,36,542443,'XX','JsonResponse',TO_TIMESTAMP('2024-10-04 09:42:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'JSON Response',0,0,TO_TIMESTAMP('2024-10-04 09:42:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:42:33.440Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589234 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:42:33.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(578123) 
;

-- Column: SUMUP_Transaction.C_POS_Order_ID
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- 2024-10-04T06:43:02.348Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589235,583266,0,30,542443,'XX','C_POS_Order_ID',TO_TIMESTAMP('2024-10-04 09:43:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'POS Order',0,0,TO_TIMESTAMP('2024-10-04 09:43:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:43:02.350Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589235 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:43:02.352Z
/* DDL */  select update_Column_Translation_From_AD_Element(583266) 
;

-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- 2024-10-04T06:43:19.411Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589236,583269,0,30,542443,'XX','C_POS_Payment_ID',TO_TIMESTAMP('2024-10-04 09:43:19','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'POS Payment',0,0,TO_TIMESTAMP('2024-10-04 09:43:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T06:43:19.414Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589236 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T06:43:19.417Z
/* DDL */  select update_Column_Translation_From_AD_Element(583269) 
;

-- Window: SumUp Transaction, InternalName=sumUpTransaction
-- Window: SumUp Transaction, InternalName=sumUpTransaction
-- 2024-10-04T06:45:07.492Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583303,0,541825,TO_TIMESTAMP('2024-10-04 09:45:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','sumUpTransaction','Y','N','N','N','N','N','N','Y','SumUp Transaction','N',TO_TIMESTAMP('2024-10-04 09:45:07','YYYY-MM-DD HH24:MI:SS'),100,'Q',0,0,100)
;

-- 2024-10-04T06:45:07.496Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541825 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-10-04T06:45:07.498Z
/* DDL */  select update_window_translation_from_ad_element(583303) 
;

-- 2024-10-04T06:45:07.501Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541825
;

-- 2024-10-04T06:45:07.505Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541825)
;

-- Tab: SumUp Transaction -> SumUp Transaction
-- Table: SUMUP_Transaction
-- Tab: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction
-- Table: SUMUP_Transaction
-- 2024-10-04T06:47:34.461Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583303,0,547617,542443,541825,'Y',TO_TIMESTAMP('2024-10-04 09:47:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','N','N','A','SUMUP_Transaction','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'SumUp Transaction','N',10,0,TO_TIMESTAMP('2024-10-04 09:47:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:34.465Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547617 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-04T06:47:34.467Z
/* DDL */  select update_tab_translation_from_ad_element(583303) 
;

-- 2024-10-04T06:47:34.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547617)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Mandant
-- Column: SUMUP_Transaction.AD_Client_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Mandant
-- Column: SUMUP_Transaction.AD_Client_ID
-- 2024-10-04T06:47:38.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589219,731826,0,547617,TO_TIMESTAMP('2024-10-04 09:47:38','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.payment.sumup','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-04 09:47:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:38.380Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:38.384Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-04T06:47:39.168Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731826
;

-- 2024-10-04T06:47:39.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731826)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Organisation
-- Column: SUMUP_Transaction.AD_Org_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Organisation
-- Column: SUMUP_Transaction.AD_Org_ID
-- 2024-10-04T06:47:39.283Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589220,731827,0,547617,TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.payment.sumup','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:39.284Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:39.286Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-04T06:47:39.424Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731827
;

-- 2024-10-04T06:47:39.425Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731827)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Aktiv
-- Column: SUMUP_Transaction.IsActive
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Aktiv
-- Column: SUMUP_Transaction.IsActive
-- 2024-10-04T06:47:39.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589223,731828,0,547617,TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.payment.sumup','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:39.548Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:39.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-04T06:47:39.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731828
;

-- 2024-10-04T06:47:39.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731828)
;

-- Field: SumUp Transaction -> SumUp Transaction -> SumUp Transaction
-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> SumUp Transaction
-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- 2024-10-04T06:47:39.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589226,731829,0,547617,TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','SumUp Transaction',TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:39.828Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:39.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583303) 
;

-- 2024-10-04T06:47:39.834Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731829
;

-- 2024-10-04T06:47:39.834Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731829)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Externe ID
-- Column: SUMUP_Transaction.ExternalId
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Externe ID
-- Column: SUMUP_Transaction.ExternalId
-- 2024-10-04T06:47:39.956Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589227,731830,0,547617,TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:39.958Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:39.959Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2024-10-04T06:47:39.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731830
;

-- 2024-10-04T06:47:39.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731830)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Client Transaction Id
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Client Transaction Id
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- 2024-10-04T06:47:40.079Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589228,731831,0,547617,TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Client Transaction Id',TO_TIMESTAMP('2024-10-04 09:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.083Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583304) 
;

-- 2024-10-04T06:47:40.087Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731831
;

-- 2024-10-04T06:47:40.088Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731831)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Merchant Code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Merchant Code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- 2024-10-04T06:47:40.201Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589229,731832,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Merchant Code',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.203Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.204Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583298) 
;

-- 2024-10-04T06:47:40.207Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731832
;

-- 2024-10-04T06:47:40.208Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731832)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- 2024-10-04T06:47:40.336Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589230,731833,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Zeitstempel',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.338Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.339Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579160) 
;

-- 2024-10-04T06:47:40.342Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731833
;

-- 2024-10-04T06:47:40.342Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731833)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Status
-- Column: SUMUP_Transaction.Status
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Status
-- Column: SUMUP_Transaction.Status
-- 2024-10-04T06:47:40.459Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589231,731834,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.payment.sumup','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.461Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.463Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2024-10-04T06:47:40.468Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731834
;

-- 2024-10-04T06:47:40.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731834)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:47:40.594Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589232,731835,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,3,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Währungscode',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.595Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.596Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577559) 
;

-- 2024-10-04T06:47:40.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731835
;

-- 2024-10-04T06:47:40.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731835)
;

-- Field: SumUp Transaction -> SumUp Transaction -> Betrag
-- Column: SUMUP_Transaction.Amount
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> Betrag
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:47:40.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589233,731836,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung',10,'de.metas.payment.sumup','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','N','N','N','N','N','Betrag',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.710Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.711Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2024-10-04T06:47:40.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731836
;

-- 2024-10-04T06:47:40.723Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731836)
;

-- Field: SumUp Transaction -> SumUp Transaction -> JSON Response
-- Column: SUMUP_Transaction.JsonResponse
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> JSON Response
-- Column: SUMUP_Transaction.JsonResponse
-- 2024-10-04T06:47:40.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589234,731837,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','JSON Response',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.831Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578123) 
;

-- 2024-10-04T06:47:40.836Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731837
;

-- 2024-10-04T06:47:40.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731837)
;

-- Field: SumUp Transaction -> SumUp Transaction -> POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- 2024-10-04T06:47:40.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589235,731838,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','POS Order',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:40.945Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:40.946Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583266) 
;

-- 2024-10-04T06:47:40.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731838
;

-- 2024-10-04T06:47:40.951Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731838)
;

-- Field: SumUp Transaction -> SumUp Transaction -> POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- 2024-10-04T06:47:41.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589236,731839,0,547617,TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','POS Payment',TO_TIMESTAMP('2024-10-04 09:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:47:41.071Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T06:47:41.073Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583269) 
;

-- 2024-10-04T06:47:41.077Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731839
;

-- 2024-10-04T06:47:41.077Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731839)
;

-- Tab: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup)
-- UI Section: main
-- 2024-10-04T06:47:48.644Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547617,546205,TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-10-04T06:47:48.648Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546205 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main
-- UI Column: 10
-- 2024-10-04T06:47:48.757Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547589,546205,TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main
-- UI Column: 20
-- 2024-10-04T06:47:48.877Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547590,546205,TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: default
-- 2024-10-04T06:47:48.998Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547589,552030,TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-10-04 09:47:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- 2024-10-04T06:49:13.689Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731838,0,547617,552030,626132,'F',TO_TIMESTAMP('2024-10-04 09:49:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Order',10,0,0,TO_TIMESTAMP('2024-10-04 09:49:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- 2024-10-04T06:49:20.076Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731839,0,547617,552030,626133,'F',TO_TIMESTAMP('2024-10-04 09:49:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','POS Payment',20,0,0,TO_TIMESTAMP('2024-10-04 09:49:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- 2024-10-04T06:49:31.942Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731833,0,547617,552030,626134,'F',TO_TIMESTAMP('2024-10-04 09:49:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zeitstempel',30,0,0,TO_TIMESTAMP('2024-10-04 09:49:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Client Transaction Id
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.Client Transaction Id
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- 2024-10-04T06:49:38.615Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731831,0,547617,552030,626135,'F',TO_TIMESTAMP('2024-10-04 09:49:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Client Transaction Id',40,0,0,TO_TIMESTAMP('2024-10-04 09:49:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Externe ID
-- Column: SUMUP_Transaction.ExternalId
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.Externe ID
-- Column: SUMUP_Transaction.ExternalId
-- 2024-10-04T06:49:45.624Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731830,0,547617,552030,626136,'F',TO_TIMESTAMP('2024-10-04 09:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Externe ID',50,0,0,TO_TIMESTAMP('2024-10-04 09:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Merchant Code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.Merchant Code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- 2024-10-04T06:49:56.667Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731832,0,547617,552030,626137,'F',TO_TIMESTAMP('2024-10-04 09:49:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Merchant Code',60,0,0,TO_TIMESTAMP('2024-10-04 09:49:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: amount
-- 2024-10-04T06:50:06.067Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547589,552031,TO_TIMESTAMP('2024-10-04 09:50:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','amount',20,TO_TIMESTAMP('2024-10-04 09:50:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:50:20.208Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731835,0,547617,552031,626138,'F',TO_TIMESTAMP('2024-10-04 09:50:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Währungscode',10,0,0,TO_TIMESTAMP('2024-10-04 09:50:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Betrag
-- Column: SUMUP_Transaction.Amount
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Betrag
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:50:28.077Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731836,0,547617,552031,626139,'F',TO_TIMESTAMP('2024-10-04 09:50:27','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','Y','N','N','Betrag',20,0,0,TO_TIMESTAMP('2024-10-04 09:50:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: status
-- 2024-10-04T06:50:38.917Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547590,552032,TO_TIMESTAMP('2024-10-04 09:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','status',10,TO_TIMESTAMP('2024-10-04 09:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Status
-- Column: SUMUP_Transaction.Status
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> status.Status
-- Column: SUMUP_Transaction.Status
-- 2024-10-04T06:50:52.040Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731834,0,547617,552032,626140,'F',TO_TIMESTAMP('2024-10-04 09:50:51','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Status',10,0,0,TO_TIMESTAMP('2024-10-04 09:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup)
-- UI Section: advanced
-- 2024-10-04T06:51:13.476Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547617,546206,TO_TIMESTAMP('2024-10-04 09:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-10-04 09:51:13','YYYY-MM-DD HH24:MI:SS'),100,'advanced')
;

-- 2024-10-04T06:51:13.477Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546206 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> advanced
-- UI Column: 10
-- 2024-10-04T06:51:18.118Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547591,546206,TO_TIMESTAMP('2024-10-04 09:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-04 09:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> advanced -> 10
-- UI Element Group: advanced
-- 2024-10-04T06:51:28.099Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547591,552033,TO_TIMESTAMP('2024-10-04 09:51:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced',10,TO_TIMESTAMP('2024-10-04 09:51:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.JSON Response
-- Column: SUMUP_Transaction.JsonResponse
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> advanced -> 10 -> advanced.JSON Response
-- Column: SUMUP_Transaction.JsonResponse
-- 2024-10-04T06:51:39.554Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731837,0,547617,552033,626141,'F',TO_TIMESTAMP('2024-10-04 09:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','JSON Response',10,0,0,TO_TIMESTAMP('2024-10-04 09:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.JSON Response
-- Column: SUMUP_Transaction.JsonResponse
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> advanced -> 10 -> advanced.JSON Response
-- Column: SUMUP_Transaction.JsonResponse
-- 2024-10-04T06:51:46.554Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-10-04 09:51:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626141
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.Zeitstempel
-- Column: SUMUP_Transaction.Timestamp
-- 2024-10-04T06:52:22.654Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-04 09:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626134
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Status
-- Column: SUMUP_Transaction.Status
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 20 -> status.Status
-- Column: SUMUP_Transaction.Status
-- 2024-10-04T06:52:22.663Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-04 09:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626140
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- 2024-10-04T06:52:22.671Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-04 09:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626132
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- 2024-10-04T06:52:22.677Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-04 09:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626133
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:52:22.685Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-10-04 09:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626138
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Betrag
-- Column: SUMUP_Transaction.Amount
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Betrag
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:52:22.693Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-10-04 09:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626139
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Währungscode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:52:39.287Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-04 09:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626138
;

-- UI Element: SumUp Transaction -> SumUp Transaction.Betrag
-- Column: SUMUP_Transaction.Amount
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> amount.Betrag
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:52:39.294Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-10-04 09:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626139
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Order
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- 2024-10-04T06:52:39.299Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-10-04 09:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626132
;

-- UI Element: SumUp Transaction -> SumUp Transaction.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.POS Payment
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- 2024-10-04T06:52:39.305Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-10-04 09:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626133
;

-- Column: SUMUP_Transaction.CurrencyCode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:53:06.048Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-10-04 09:53:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589232
;

-- Column: SUMUP_Transaction.Amount
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:53:16.017Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-10-04 09:53:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589233
;

-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- 2024-10-04T06:53:36.208Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2024-10-04 09:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589228
;

-- Column: SUMUP_Transaction.Amount
-- Column: SUMUP_Transaction.Amount
-- 2024-10-04T06:53:47.658Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:53:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589233
;

-- Column: SUMUP_Transaction.C_POS_Order_ID
-- Column: SUMUP_Transaction.C_POS_Order_ID
-- 2024-10-04T06:53:52.783Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:53:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589235
;

-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- Column: SUMUP_Transaction.C_POS_Payment_ID
-- 2024-10-04T06:53:57.868Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589236
;

-- Column: SUMUP_Transaction.CurrencyCode
-- Column: SUMUP_Transaction.CurrencyCode
-- 2024-10-04T06:54:04.802Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:54:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589232
;

-- Column: SUMUP_Transaction.ExternalId
-- Column: SUMUP_Transaction.ExternalId
-- 2024-10-04T06:54:08.288Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:54:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589227
;

-- Column: SUMUP_Transaction.JsonResponse
-- Column: SUMUP_Transaction.JsonResponse
-- 2024-10-04T06:54:15.122Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589234
;

-- Column: SUMUP_Transaction.Status
-- Column: SUMUP_Transaction.Status
-- 2024-10-04T06:54:18.572Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:54:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589231
;

-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- Column: SUMUP_Transaction.SUMUP_ClientTransactionId
-- 2024-10-04T06:54:22.716Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589228
;

-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- Column: SUMUP_Transaction.SUMUP_merchant_code
-- 2024-10-04T06:54:27.201Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589229
;

-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- Column: SUMUP_Transaction.SUMUP_Transaction_ID
-- 2024-10-04T06:54:31.187Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-10-04 09:54:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589226
;

-- Column: SUMUP_Transaction.Timestamp
-- Column: SUMUP_Transaction.Timestamp
-- 2024-10-04T06:54:37.028Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-04 09:54:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589230
;

-- 2024-10-04T06:55:06.329Z
/* DDL */ CREATE TABLE public.SUMUP_Transaction (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, C_POS_Order_ID NUMERIC(10), C_POS_Payment_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, CurrencyCode VARCHAR(3) NOT NULL, ExternalId VARCHAR(255) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, JsonResponse TEXT, Status VARCHAR(255) NOT NULL, SUMUP_ClientTransactionId VARCHAR(255) NOT NULL, SUMUP_merchant_code VARCHAR(255) NOT NULL, SUMUP_Transaction_ID NUMERIC(10) NOT NULL, Timestamp TIMESTAMP WITH TIME ZONE NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CPOSOrder_SUMUPTransaction FOREIGN KEY (C_POS_Order_ID) REFERENCES public.C_POS_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CPOSPayment_SUMUPTransaction FOREIGN KEY (C_POS_Payment_ID) REFERENCES public.C_POS_Payment DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SUMUP_Transaction_Key PRIMARY KEY (SUMUP_Transaction_ID))
;

-- Name: SumUp Transaction
-- Action Type: W
-- Window: SumUp Transaction(541825,de.metas.payment.sumup)
-- 2024-10-04T06:56:09.486Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583303,542178,0,541825,TO_TIMESTAMP('2024-10-04 09:56:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','sumUpTransaction','Y','N','N','N','N','SumUp Transaction',TO_TIMESTAMP('2024-10-04 09:56:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T06:56:09.489Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542178 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-10-04T06:56:09.492Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542178, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542178)
;

-- 2024-10-04T06:56:09.496Z
/* DDL */  select update_menu_translation_from_ad_element(583303) 
;

-- Reordering children of `SumUp`
-- Node name: `SumUp Configuration (SUMUP_Config)`
-- 2024-10-04T06:56:17.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=542175, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542176 AND AD_Tree_ID=10
;

-- Node name: `SumUp API Log (SUMUP_API_Log)`
-- 2024-10-04T06:56:17.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=542175, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542177 AND AD_Tree_ID=10
;

-- Node name: `SumUp Transaction`
-- 2024-10-04T06:56:17.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=542175, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542178 AND AD_Tree_ID=10
;

-- Column: SUMUP_Transaction.SUMUP_Config_ID
-- Column: SUMUP_Transaction.SUMUP_Config_ID
-- 2024-10-04T08:01:50.230Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589237,583297,0,30,542443,'XX','SUMUP_Config_ID',TO_TIMESTAMP('2024-10-04 11:01:49','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'SumUp Configuration',0,0,TO_TIMESTAMP('2024-10-04 11:01:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T08:01:50.236Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589237 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T08:01:50.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(583297) 
;

-- 2024-10-04T08:01:51.124Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Transaction','ALTER TABLE public.SUMUP_Transaction ADD COLUMN SUMUP_Config_ID NUMERIC(10) NOT NULL')
;

-- 2024-10-04T08:01:51.138Z
ALTER TABLE SUMUP_Transaction ADD CONSTRAINT SUMUPConfig_SUMUPTransaction FOREIGN KEY (SUMUP_Config_ID) REFERENCES public.SUMUP_Config DEFERRABLE INITIALLY DEFERRED
;

-- Field: SumUp Transaction -> SumUp Transaction -> SumUp Configuration
-- Column: SUMUP_Transaction.SUMUP_Config_ID
-- Field: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> SumUp Configuration
-- Column: SUMUP_Transaction.SUMUP_Config_ID
-- 2024-10-04T08:02:15.620Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589237,731840,0,547617,TO_TIMESTAMP('2024-10-04 11:02:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','SumUp Configuration',TO_TIMESTAMP('2024-10-04 11:02:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T08:02:15.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T08:02:15.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583297) 
;

-- 2024-10-04T08:02:15.634Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731840
;

-- 2024-10-04T08:02:15.636Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731840)
;

-- UI Element: SumUp Transaction -> SumUp Transaction.SumUp Configuration
-- Column: SUMUP_Transaction.SUMUP_Config_ID
-- UI Element: SumUp Transaction(541825,de.metas.payment.sumup) -> SumUp Transaction(547617,de.metas.payment.sumup) -> main -> 10 -> default.SumUp Configuration
-- Column: SUMUP_Transaction.SUMUP_Config_ID
-- 2024-10-04T08:02:50.030Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731840,0,547617,552030,626142,'F',TO_TIMESTAMP('2024-10-04 11:02:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','SumUp Configuration',70,0,0,TO_TIMESTAMP('2024-10-04 11:02:49','YYYY-MM-DD HH24:MI:SS'),100)
;

