-- Run mode: SWING_CLIENT

-- 2024-03-25T18:14:26.417Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583057,0,'Reference_Position',TO_TIMESTAMP('2024-03-25 19:14:26.216','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Reference Position','Reference Position',TO_TIMESTAMP('2024-03-25 19:14:26.216','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:14:26.425Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583057 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-25T18:15:04.754Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583058,0,'Reference_Type',TO_TIMESTAMP('2024-03-25 19:15:04.635','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Reference Type','Reference Type',TO_TIMESTAMP('2024-03-25 19:15:04.635','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:15:04.756Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583058 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-25T18:15:31.524Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583059,0,'Reference_Value',TO_TIMESTAMP('2024-03-25 19:15:31.413','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Reference Value','Reference Value',TO_TIMESTAMP('2024-03-25 19:15:31.413','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:15:31.525Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583059 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Table: AD_Org_PostFinance_PaperBill_References
-- 2024-03-25T18:18:00.568Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('1',0,0,0,542403,'N',TO_TIMESTAMP('2024-03-25 19:18:00.443','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','N','Y','N','N','N','N','N','N','N','N',0,'Paper Bill Konfiguration','NP','L','AD_Org_PostFinance_PaperBill_References','DTI',TO_TIMESTAMP('2024-03-25 19:18:00.443','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:00.570Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542403 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-03-25T18:18:00.674Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556339,TO_TIMESTAMP('2024-03-25 19:18:00.588','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table AD_Org_PostFinance_PaperBill_References',1,'Y','N','Y','Y','AD_Org_PostFinance_PaperBill_References','N',1000000,TO_TIMESTAMP('2024-03-25 19:18:00.588','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:18:00.688Z
CREATE SEQUENCE AD_ORG_POSTFINANCE_PAPERBILL_REFERENCES_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2024-03-25T18:18:24.606Z
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-25 19:18:24.604','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542403
;

-- 2024-03-25T18:18:25.333Z
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-25 19:18:25.329','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542403
;

-- 2024-03-25T18:18:37.334Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Paper Bill Configuration',Updated=TO_TIMESTAMP('2024-03-25 19:18:37.332','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542403
;

-- Column: AD_Org_PostFinance_PaperBill_References.AD_Client_ID
-- 2024-03-25T18:18:53.190Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588060,102,0,19,542403,'AD_Client_ID',TO_TIMESTAMP('2024-03-25 19:18:53.012','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.postfinance',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-03-25 19:18:53.012','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:53.193Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588060 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:53.212Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: AD_Org_PostFinance_PaperBill_References.AD_Org_ID
-- 2024-03-25T18:18:53.876Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588061,113,0,30,542403,'AD_Org_ID',TO_TIMESTAMP('2024-03-25 19:18:53.576','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.postfinance',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-03-25 19:18:53.576','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:53.877Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588061 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:53.879Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: AD_Org_PostFinance_PaperBill_References.Created
-- 2024-03-25T18:18:54.404Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588062,245,0,16,542403,'Created',TO_TIMESTAMP('2024-03-25 19:18:54.193','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.postfinance',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-03-25 19:18:54.193','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:54.406Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588062 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:54.409Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: AD_Org_PostFinance_PaperBill_References.CreatedBy
-- 2024-03-25T18:18:54.982Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588063,246,0,18,110,542403,'CreatedBy',TO_TIMESTAMP('2024-03-25 19:18:54.755','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.postfinance',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-03-25 19:18:54.755','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:54.984Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588063 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:54.987Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: AD_Org_PostFinance_PaperBill_References.IsActive
-- 2024-03-25T18:18:55.587Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588064,348,0,20,542403,'IsActive',TO_TIMESTAMP('2024-03-25 19:18:55.34','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.postfinance',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-03-25 19:18:55.34','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:55.590Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588064 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:55.593Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: AD_Org_PostFinance_PaperBill_References.Updated
-- 2024-03-25T18:18:56.404Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588065,607,0,16,542403,'Updated',TO_TIMESTAMP('2024-03-25 19:18:56.02','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.postfinance',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-03-25 19:18:56.02','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:56.407Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588065 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:56.410Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: AD_Org_PostFinance_PaperBill_References.UpdatedBy
-- 2024-03-25T18:18:57.142Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588066,608,0,18,110,542403,'UpdatedBy',TO_TIMESTAMP('2024-03-25 19:18:56.82','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.postfinance',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-03-25 19:18:56.82','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:57.143Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588066 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:57.147Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2024-03-25T18:18:57.833Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583060,0,'AD_Org_PostFinance_PaperBill_References_ID',TO_TIMESTAMP('2024-03-25 19:18:57.7','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.postfinance','Y','Paper Bill Konfiguration','Paper Bill Konfiguration',TO_TIMESTAMP('2024-03-25 19:18:57.7','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:18:57.835Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583060 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_Org_PostFinance_PaperBill_References.AD_Org_PostFinance_PaperBill_References_ID
-- 2024-03-25T18:18:58.499Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588067,583060,0,13,542403,'AD_Org_PostFinance_PaperBill_References_ID',TO_TIMESTAMP('2024-03-25 19:18:57.697','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.postfinance',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Paper Bill Konfiguration',0,0,TO_TIMESTAMP('2024-03-25 19:18:57.697','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:18:58.502Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588067 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:18:58.506Z
/* DDL */  select update_Column_Translation_From_AD_Element(583060)
;

-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Position
-- 2024-03-25T18:20:25.213Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588068,583057,0,10,542403,'Reference_Position',TO_TIMESTAMP('2024-03-25 19:20:24.987','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.postfinance',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Reference Position',0,0,TO_TIMESTAMP('2024-03-25 19:20:24.987','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:20:25.221Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588068 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:20:25.244Z
/* DDL */  select update_Column_Translation_From_AD_Element(583057)
;

-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Type
-- 2024-03-25T18:21:08.739Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588069,583058,0,10,542403,'Reference_Type',TO_TIMESTAMP('2024-03-25 19:21:08.608','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.postfinance',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Reference Type',0,0,TO_TIMESTAMP('2024-03-25 19:21:08.608','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:21:08.743Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588069 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:21:08.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(583058)
;

-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Value
-- 2024-03-25T18:21:46.255Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588070,583059,0,10,542403,'Reference_Value',TO_TIMESTAMP('2024-03-25 19:21:46.145','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.postfinance',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reference Value',0,0,TO_TIMESTAMP('2024-03-25 19:21:46.145','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-03-25T18:21:46.256Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588070 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-25T18:21:46.259Z
/* DDL */  select update_Column_Translation_From_AD_Element(583059)
;

-- Tab: Organisation(110,D) -> Paper Bill Konfiguration
-- Table: AD_Org_PostFinance_PaperBill_References
-- 2024-03-25T18:24:06.101Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583060,0,547491,542403,110,'Y',TO_TIMESTAMP('2024-03-25 19:24:05.65','YYYY-MM-DD HH24:MI:SS.US'),100,'U','N','N','A','AD_Org_PostFinance_PaperBill_References','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Paper Bill Konfiguration','N',70,1,TO_TIMESTAMP('2024-03-25 19:24:05.65','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:06.104Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547491 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-03-25T18:24:06.155Z
/* DDL */  select update_tab_translation_from_ad_element(583060)
;

-- 2024-03-25T18:24:06.169Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547491)
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Mandant
-- Column: AD_Org_PostFinance_PaperBill_References.AD_Client_ID
-- 2024-03-25T18:24:15.139Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588060,726649,0,547491,TO_TIMESTAMP('2024-03-25 19:24:14.995','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-03-25 19:24:14.995','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:15.144Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726649 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-25T18:24:15.151Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-03-25T18:24:15.399Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726649
;

-- 2024-03-25T18:24:15.401Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726649)
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Organisation
-- Column: AD_Org_PostFinance_PaperBill_References.AD_Org_ID
-- 2024-03-25T18:24:15.503Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588061,726650,0,547491,TO_TIMESTAMP('2024-03-25 19:24:15.41','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-03-25 19:24:15.41','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:15.505Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726650 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-25T18:24:15.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-03-25T18:24:15.671Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726650
;

-- 2024-03-25T18:24:15.673Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726650)
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Aktiv
-- Column: AD_Org_PostFinance_PaperBill_References.IsActive
-- 2024-03-25T18:24:15.757Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588064,726651,0,547491,TO_TIMESTAMP('2024-03-25 19:24:15.676','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-03-25 19:24:15.676','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:15.759Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726651 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-25T18:24:15.761Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-03-25T18:24:15.833Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726651
;

-- 2024-03-25T18:24:15.834Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726651)
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Paper Bill Konfiguration
-- Column: AD_Org_PostFinance_PaperBill_References.AD_Org_PostFinance_PaperBill_References_ID
-- 2024-03-25T18:24:15.920Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588067,726652,0,547491,TO_TIMESTAMP('2024-03-25 19:24:15.837','YYYY-MM-DD HH24:MI:SS.US'),100,10,'U','Y','N','N','N','N','N','N','N','Paper Bill Konfiguration',TO_TIMESTAMP('2024-03-25 19:24:15.837','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:15.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726652 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-25T18:24:15.926Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583060)
;

-- 2024-03-25T18:24:15.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726652
;

-- 2024-03-25T18:24:15.931Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726652)
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Reference Position
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Position
-- 2024-03-25T18:24:16.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588068,726653,0,547491,TO_TIMESTAMP('2024-03-25 19:24:15.934','YYYY-MM-DD HH24:MI:SS.US'),100,10,'U','Y','N','N','N','N','N','N','N','Reference Position',TO_TIMESTAMP('2024-03-25 19:24:15.934','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:16.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726653 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-25T18:24:16.032Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583057)
;

-- 2024-03-25T18:24:16.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726653
;

-- 2024-03-25T18:24:16.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726653)
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Reference Type
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Type
-- 2024-03-25T18:24:16.125Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588069,726654,0,547491,TO_TIMESTAMP('2024-03-25 19:24:16.039','YYYY-MM-DD HH24:MI:SS.US'),100,10,'U','Y','N','N','N','N','N','N','N','Reference Type',TO_TIMESTAMP('2024-03-25 19:24:16.039','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:16.129Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-25T18:24:16.131Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583058)
;

-- 2024-03-25T18:24:16.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726654
;

-- 2024-03-25T18:24:16.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726654)
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Reference Value
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Value
-- 2024-03-25T18:24:16.238Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588070,726655,0,547491,TO_TIMESTAMP('2024-03-25 19:24:16.14','YYYY-MM-DD HH24:MI:SS.US'),100,10,'U','Y','N','N','N','N','N','N','N','Reference Value',TO_TIMESTAMP('2024-03-25 19:24:16.14','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-03-25T18:24:16.241Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=726655 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-25T18:24:16.244Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583059)
;

-- 2024-03-25T18:24:16.247Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726655
;

-- 2024-03-25T18:24:16.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726655)
;

-- Tab: Organisation(110,D) -> Paper Bill Konfiguration(547491,U)
-- UI Section: main
-- 2024-03-25T18:24:30.105Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547491,546070,TO_TIMESTAMP('2024-03-25 19:24:29.994','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-03-25 19:24:29.994','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-03-25T18:24:30.106Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546070 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main
-- UI Column: 10
-- 2024-03-25T18:24:38.423Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547417,546070,TO_TIMESTAMP('2024-03-25 19:24:38.25','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-03-25 19:24:38.25','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10
-- UI Element Group: main
-- 2024-03-25T18:24:58.540Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547417,551715,TO_TIMESTAMP('2024-03-25 19:24:58.429','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2024-03-25 19:24:58.429','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Reference Position
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Position
-- 2024-03-25T18:25:21.020Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726653,0,547491,551715,623813,'F',TO_TIMESTAMP('2024-03-25 19:25:20.899','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Reference Position',10,0,0,TO_TIMESTAMP('2024-03-25 19:25:20.899','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Reference Type
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Type
-- 2024-03-25T18:25:30.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726654,0,547491,551715,623814,'F',TO_TIMESTAMP('2024-03-25 19:25:30.307','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Reference Type',20,0,0,TO_TIMESTAMP('2024-03-25 19:25:30.307','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Reference Value
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Value
-- 2024-03-25T18:25:44.941Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726655,0,547491,551715,623815,'F',TO_TIMESTAMP('2024-03-25 19:25:44.832','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Reference Value',30,0,0,TO_TIMESTAMP('2024-03-25 19:25:44.832','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Aktiv
-- Column: AD_Org_PostFinance_PaperBill_References.IsActive
-- 2024-03-25T18:26:05.644Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,726651,0,547491,551715,623816,'F',TO_TIMESTAMP('2024-03-25 19:26:05.529','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',40,0,0,TO_TIMESTAMP('2024-03-25 19:26:05.529','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Reference Position
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Position
-- 2024-03-25T18:26:19.305Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-03-25 19:26:19.305','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623813
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Reference Type
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Type
-- 2024-03-25T18:26:19.313Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-03-25 19:26:19.313','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623814
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Reference Value
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Value
-- 2024-03-25T18:26:19.319Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-03-25 19:26:19.319','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623815
;

-- UI Element: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> main -> 10 -> main.Aktiv
-- Column: AD_Org_PostFinance_PaperBill_References.IsActive
-- 2024-03-25T18:26:19.324Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-03-25 19:26:19.324','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=623816
;

-- Column: AD_Org_PostFinance_PaperBill_References.AD_Org_ID
-- 2024-03-25T18:27:47.113Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-03-25 19:27:47.113','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588061
;

-- 2024-03-25T18:29:33.830Z
/* DDL */ CREATE TABLE public.AD_Org_PostFinance_PaperBill_References (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Org_PostFinance_PaperBill_References_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Reference_Position VARCHAR(10) NOT NULL, Reference_Type VARCHAR(10) NOT NULL, Reference_Value VARCHAR(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AD_Org_PostFinance_PaperBill_References_Key PRIMARY KEY (AD_Org_PostFinance_PaperBill_References_ID))
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Reference Type
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Type
-- 2024-03-25T18:46:32.321Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-03-25 19:46:32.321','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726654
;

-- Field: Organisation(110,D) -> Paper Bill Konfiguration(547491,U) -> Reference Position
-- Column: AD_Org_PostFinance_PaperBill_References.Reference_Position
-- 2024-03-25T18:46:35.678Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-03-25 19:46:35.678','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=726653
;

-- Tab: Organisation(110,D) -> Paper Bill Konfiguration
-- Table: AD_Org_PostFinance_PaperBill_References
-- 2024-03-25T18:46:38.744Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2024-03-25 19:46:38.744','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547491
;

-- Element: AD_Org_PostFinance_PaperBill_References_ID
-- 2024-03-25T18:58:58.489Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-25 19:58:58.489','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583060 AND AD_Language='de_CH'
;

-- 2024-03-25T18:58:58.491Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583060,'de_CH')
;

-- Element: AD_Org_PostFinance_PaperBill_References_ID
-- 2024-03-25T18:59:00.274Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-25 19:59:00.274','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583060 AND AD_Language='de_DE'
;

-- 2024-03-25T18:59:00.276Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583060,'de_DE')
;

-- 2024-03-25T18:59:00.277Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583060,'de_DE')
;

-- Element: AD_Org_PostFinance_PaperBill_References_ID
-- 2024-03-25T18:59:28.684Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Paper Bill Configuration', PrintName='Paper Bill Configuration',Updated=TO_TIMESTAMP('2024-03-25 19:59:28.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583060 AND AD_Language='en_US'
;

-- 2024-03-25T18:59:28.686Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583060,'en_US')
;

