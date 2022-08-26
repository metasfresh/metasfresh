-- Table: I_Invoice_Candidate
-- 2022-08-22T06:29:19.523Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542207,'N',TO_TIMESTAMP('2022-08-22 09:29:19','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Import - Invoice candiate','NP','L','I_Invoice_Candidate','DTI',TO_TIMESTAMP('2022-08-22 09:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T06:29:19.531Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542207 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-08-22T06:29:19.646Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556004,TO_TIMESTAMP('2022-08-22 09:29:19','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table I_Invoice_Candidate',1,'Y','N','Y','Y','I_Invoice_Candidate','N',1000000,TO_TIMESTAMP('2022-08-22 09:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T06:29:19.658Z
CREATE SEQUENCE I_INVOICE_CANDIDATE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: I_Invoice_Candidate.AD_Client_ID
-- 2022-08-22T06:29:31.229Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584158,102,0,19,542207,'AD_Client_ID',TO_TIMESTAMP('2022-08-22 09:29:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-08-22 09:29:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:31.232Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584158 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:31.260Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: I_Invoice_Candidate.AD_Org_ID
-- 2022-08-22T06:29:32.014Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584159,113,0,30,542207,'AD_Org_ID',TO_TIMESTAMP('2022-08-22 09:29:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-08-22 09:29:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:32.015Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584159 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:32.018Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: I_Invoice_Candidate.Created
-- 2022-08-22T06:29:32.516Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584160,245,0,16,542207,'Created',TO_TIMESTAMP('2022-08-22 09:29:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-08-22 09:29:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:32.518Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584160 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:32.521Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: I_Invoice_Candidate.CreatedBy
-- 2022-08-22T06:29:32.990Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584161,246,0,18,110,542207,'CreatedBy',TO_TIMESTAMP('2022-08-22 09:29:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-08-22 09:29:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:32.992Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584161 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:32.995Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: I_Invoice_Candidate.IsActive
-- 2022-08-22T06:29:33.468Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584162,348,0,20,542207,'IsActive',TO_TIMESTAMP('2022-08-22 09:29:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-08-22 09:29:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:33.470Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584162 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:33.473Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: I_Invoice_Candidate.Updated
-- 2022-08-22T06:29:33.906Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584163,607,0,16,542207,'Updated',TO_TIMESTAMP('2022-08-22 09:29:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-08-22 09:29:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:33.908Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584163 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:33.911Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: I_Invoice_Candidate.UpdatedBy
-- 2022-08-22T06:29:34.366Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584164,608,0,18,110,542207,'UpdatedBy',TO_TIMESTAMP('2022-08-22 09:29:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-08-22 09:29:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:34.367Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:34.370Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2022-08-22T06:29:34.811Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581357,0,'I_Invoice_Candidate_ID',TO_TIMESTAMP('2022-08-22 09:29:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Import - Invoice candiate','Import - Invoice candiate',TO_TIMESTAMP('2022-08-22 09:29:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T06:29:34.813Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581357 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_Invoice_Candidate.I_Invoice_Candidate_ID
-- 2022-08-22T06:29:35.172Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584165,581357,0,13,542207,'I_Invoice_Candidate_ID',TO_TIMESTAMP('2022-08-22 09:29:34','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Import - Invoice candiate',0,0,TO_TIMESTAMP('2022-08-22 09:29:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:29:35.173Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:29:35.176Z
/* DDL */  select update_Column_Translation_From_AD_Element(581357)
;

-- 2022-08-22T06:29:35.535Z
/* DDL */ CREATE TABLE public.I_Invoice_Candidate (
                                                      AD_Client_ID NUMERIC(10) NOT NULL
    , AD_Org_ID NUMERIC(10) NOT NULL
    , Created TIMESTAMP WITH TIME ZONE NOT NULL
    , CreatedBy NUMERIC(10) NOT NULL
    , I_Invoice_Candidate_ID NUMERIC(10) NOT NULL
    , IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL
    , Updated TIMESTAMP WITH TIME ZONE NOT NULL
    , UpdatedBy NUMERIC(10) NOT NULL
    , CONSTRAINT I_Invoice_Candidate_Key PRIMARY KEY (I_Invoice_Candidate_ID)
          )
;

-- 2022-08-22T06:29:35.570Z
INSERT INTO t_alter_column values('i_invoice_candidate','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2022-08-22T06:29:35.582Z
INSERT INTO t_alter_column values('i_invoice_candidate','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-08-22T06:29:35.596Z
INSERT INTO t_alter_column values('i_invoice_candidate','CreatedBy','NUMERIC(10)',null,null)
;

-- 2022-08-22T06:29:35.605Z
INSERT INTO t_alter_column values('i_invoice_candidate','IsActive','CHAR(1)',null,null)
;

-- 2022-08-22T06:29:35.626Z
INSERT INTO t_alter_column values('i_invoice_candidate','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-08-22T06:29:35.635Z
INSERT INTO t_alter_column values('i_invoice_candidate','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2022-08-22T06:29:35.643Z
INSERT INTO t_alter_column values('i_invoice_candidate','I_Invoice_Candidate_ID','NUMERIC(10)',null,null)
;

-- Column: I_Invoice_Candidate.POReference
-- 2022-08-22T06:30:00.461Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584166,952,0,10,542207,'POReference',TO_TIMESTAMP('2022-08-22 09:30:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Referenz-Nummer des Kunden','D',0,255,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Referenz',0,0,TO_TIMESTAMP('2022-08-22 09:30:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:30:00.467Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:30:00.473Z
/* DDL */  select update_Column_Translation_From_AD_Element(952)
;

-- 2022-08-22T06:30:00.972Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN POReference VARCHAR(255)')
;

-- Column: I_Invoice_Candidate.Bill_Location_ID
-- 2022-08-22T06:32:28.693Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584168,2040,0,19,542207,'Bill_Location_ID',TO_TIMESTAMP('2022-08-22 09:32:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Standort des Geschäftspartners für die Rechnungsstellung','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsstandort',0,0,TO_TIMESTAMP('2022-08-22 09:32:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:32:28.696Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584168 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:32:28.704Z
/* DDL */  select update_Column_Translation_From_AD_Element(2040)
;

-- Column: I_Invoice_Candidate.Bill_Location_ID
-- 2022-08-22T06:33:55.486Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=159, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-08-22 09:33:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584168
;

-- 2022-08-22T06:33:55.925Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Bill_Location_ID NUMERIC(10) NOT NULL')
;

-- 2022-08-22T06:33:55.940Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT BillLocation_IInvoiceCandidate FOREIGN KEY (Bill_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_Invoice_Candidate.Bill_User_ID
-- 2022-08-22T06:34:31.796Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584169,2041,0,18,110,542207,'Bill_User_ID',TO_TIMESTAMP('2022-08-22 09:34:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Ansprechpartner des Geschäftspartners für die Rechnungsstellung','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungskontakt',0,0,TO_TIMESTAMP('2022-08-22 09:34:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:34:31.797Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584169 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:34:31.799Z
/* DDL */  select update_Column_Translation_From_AD_Element(2041)
;

-- 2022-08-22T06:34:32.277Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Bill_User_ID NUMERIC(10) NOT NULL')
;

-- 2022-08-22T06:34:32.282Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT BillUser_IInvoiceCandidate FOREIGN KEY (Bill_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_Invoice_Candidate.M_Product_Value
-- 2022-08-22T06:34:52.878Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584170,577363,0,10,542207,'M_Product_Value',TO_TIMESTAMP('2022-08-22 09:34:52','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Product Value',0,0,TO_TIMESTAMP('2022-08-22 09:34:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:34:52.884Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584170 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:34:52.890Z
/* DDL */  select update_Column_Translation_From_AD_Element(577363)
;

-- 2022-08-22T06:34:53.406Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN M_Product_Value VARCHAR(255) NOT NULL')
;

-- Column: I_Invoice_Candidate.DateOrdered
-- 2022-08-22T06:35:08.285Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584171,268,0,15,542207,'DateOrdered',TO_TIMESTAMP('2022-08-22 09:35:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum des Auftrags','D',0,7,'Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsdatum',0,0,TO_TIMESTAMP('2022-08-22 09:35:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:35:08.286Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584171 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:35:08.288Z
/* DDL */  select update_Column_Translation_From_AD_Element(268)
;

-- 2022-08-22T06:35:08.783Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN DateOrdered TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: I_Invoice_Candidate.QtyOrdered
-- 2022-08-22T06:35:28.129Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584172,531,0,29,542207,'QtyOrdered',TO_TIMESTAMP('2022-08-22 09:35:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Bestellt/ Beauftragt','D',0,10,'Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bestellt/ Beauftragt',0,0,TO_TIMESTAMP('2022-08-22 09:35:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:35:28.131Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584172 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:35:28.135Z
/* DDL */  select update_Column_Translation_From_AD_Element(531)
;

-- 2022-08-22T06:35:28.687Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN QtyOrdered NUMERIC NOT NULL')
;

-- Column: I_Invoice_Candidate.QtyDelivered
-- 2022-08-22T06:35:48.993Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584173,528,0,29,542207,'QtyDelivered',TO_TIMESTAMP('2022-08-22 09:35:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Gelieferte Menge','D',0,10,'Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gelieferte Menge',0,0,TO_TIMESTAMP('2022-08-22 09:35:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:35:48.996Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584173 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:35:48.999Z
/* DDL */  select update_Column_Translation_From_AD_Element(528)
;

-- 2022-08-22T06:35:49.493Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN QtyDelivered NUMERIC')
;

-- Column: I_Invoice_Candidate.C_UOM_ID
-- 2022-08-22T06:37:08.388Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584174,215,0,18,114,542207,'C_UOM_ID',TO_TIMESTAMP('2022-08-22 09:37:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','D',0,22,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2022-08-22 09:37:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:37:08.389Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584174 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:37:08.392Z
/* DDL */  select update_Column_Translation_From_AD_Element(215)
;

-- 2022-08-22T06:37:08.928Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2022-08-22T06:37:08.933Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT CUOM_IInvoiceCandidate FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_Invoice_Candidate.IsSOTrx
-- 2022-08-22T06:37:29.397Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584175,1106,0,20,542207,'IsSOTrx',TO_TIMESTAMP('2022-08-22 09:37:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Dies ist eine Verkaufstransaktion','D',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verkaufstransaktion',0,0,TO_TIMESTAMP('2022-08-22 09:37:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:37:29.399Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584175 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:37:29.403Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106)
;

-- 2022-08-22T06:37:29.877Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''Y'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;


-- Column: I_Invoice_Candidate.InvoiceRule
-- 2022-08-22T06:40:09.642Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584177,559,0,10,542207,'InvoiceRule',TO_TIMESTAMP('2022-08-22 09:40:09','YYYY-MM-DD HH24:MI:SS'),100,'N','"Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsstellung',0,0,TO_TIMESTAMP('2022-08-22 09:40:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:40:09.643Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584177 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:40:09.647Z
/* DDL */  select update_Column_Translation_From_AD_Element(559)
;

-- 2022-08-22T06:40:10.117Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN InvoiceRule VARCHAR(255)')
;

-- Column: I_Invoice_Candidate.PresetDateInvoiced
-- 2022-08-22T06:40:29.946Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584178,577060,0,15,542207,'PresetDateInvoiced',TO_TIMESTAMP('2022-08-22 09:40:29','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vorbelegtes Rechnungsdatum',0,0,TO_TIMESTAMP('2022-08-22 09:40:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:40:29.949Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584178 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:40:29.955Z
/* DDL */  select update_Column_Translation_From_AD_Element(577060)
;

-- 2022-08-22T06:40:30.433Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN PresetDateInvoiced TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: I_Invoice_Candidate.Description
-- 2022-08-22T06:40:46.185Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584179,275,0,10,542207,'Description',TO_TIMESTAMP('2022-08-22 09:40:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2022-08-22 09:40:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T06:40:46.187Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584179 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T06:40:46.189Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2022-08-22T06:40:46.733Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Description VARCHAR(255)')
;

-- Column: I_Invoice_Candidate.InvoiceRule
-- 2022-08-22T07:23:50.363Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=150,Updated=TO_TIMESTAMP('2022-08-22 10:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584177
;

-- 2022-08-22T07:23:50.867Z
INSERT INTO t_alter_column values('i_invoice_candidate','InvoiceRule','VARCHAR(255)',null,null)
;

-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-22T10:34:19.089Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584186,865,0,17,183,542207,'DocBaseType',TO_TIMESTAMP('2022-08-22 13:34:18','YYYY-MM-DD HH24:MI:SS'),100,'N','ARI','','D',0,3,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Dokument Basis Typ',0,0,TO_TIMESTAMP('2022-08-22 13:34:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:34:19.092Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584186 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:34:19.114Z
/* DDL */  select update_Column_Translation_From_AD_Element(865)
;

-- 2022-08-22T10:34:19.867Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN DocBaseType VARCHAR(3) DEFAULT ''ARI'' NOT NULL')
;

-- Column: I_Invoice_Candidate.DocSubType
-- 2022-08-22T10:35:38.464Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584187,1018,0,17,148,542207,540219,'DocSubType',TO_TIMESTAMP('2022-08-22 13:35:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Document Sub Type','D',0,3,'The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Doc Sub Type',0,0,TO_TIMESTAMP('2022-08-22 13:35:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:35:38.468Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584187 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:35:38.474Z
/* DDL */  select update_Column_Translation_From_AD_Element(1018)
;

-- 2022-08-22T10:35:38.949Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN DocSubType VARCHAR(3)')
;

-- Column: I_Invoice_Candidate.I_ErrorMsg
-- 2022-08-22T10:37:24.439Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584188,912,0,10,542207,'I_ErrorMsg',TO_TIMESTAMP('2022-08-22 13:37:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Meldungen, die durch den Importprozess generiert wurden','D',0,2000,'"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Import-Fehlermeldung',0,0,TO_TIMESTAMP('2022-08-22 13:37:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:37:24.441Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584188 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:37:24.443Z
/* DDL */  select update_Column_Translation_From_AD_Element(912)
;

-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-22T10:37:59.702Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584189,913,0,20,542207,'I_IsImported',TO_TIMESTAMP('2022-08-22 13:37:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Ist dieser Import verarbeitet worden?','D',0,1,'DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Importiert',0,0,TO_TIMESTAMP('2022-08-22 13:37:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:37:59.704Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584189 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:37:59.710Z
/* DDL */  select update_Column_Translation_From_AD_Element(913)
;

-- 2022-08-22T10:38:02.843Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN I_IsImported CHAR(1) DEFAULT ''N'' CHECK (I_IsImported IN (''Y'',''N'')) NOT NULL')
;

-- 2022-08-22T10:38:09.074Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN I_ErrorMsg VARCHAR(2000)')
;

-- Column: I_Invoice_Candidate.Processed
-- 2022-08-22T10:39:30.976Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584190,1047,0,20,542207,'Processed',TO_TIMESTAMP('2022-08-22 13:39:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2022-08-22 13:39:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:39:30.979Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584190 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:39:30.985Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047)
;

-- 2022-08-22T10:39:31.467Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- Column: I_Invoice_Candidate.C_DataImport_Run_ID
-- 2022-08-22T10:47:56.278Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584191,577114,0,30,542207,'C_DataImport_Run_ID',TO_TIMESTAMP('2022-08-22 13:47:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Data Import Run',0,0,TO_TIMESTAMP('2022-08-22 13:47:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:47:56.286Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584191 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:47:56.290Z
/* DDL */  select update_Column_Translation_From_AD_Element(577114)
;

-- 2022-08-22T10:47:56.851Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_DataImport_Run_ID NUMERIC(10)')
;

-- 2022-08-22T10:47:56.858Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT CDataImportRun_IInvoiceCandidate FOREIGN KEY (C_DataImport_Run_ID) REFERENCES public.C_DataImport_Run DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-22T11:02:38.202Z
INSERT INTO t_alter_column values('i_invoice_candidate','DocBaseType','VARCHAR(3)',null,'ARI')
;

-- 2022-08-22T11:02:38.207Z
UPDATE I_Invoice_Candidate SET DocBaseType='ARI' WHERE DocBaseType IS NULL
;
-- Column: I_Invoice_Candidate.Processing
-- 2022-08-22T13:05:00.156Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584193,524,0,28,542207,'Processing',TO_TIMESTAMP('2022-08-22 16:04:59','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Process Now',0,0,TO_TIMESTAMP('2022-08-22 16:04:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T13:05:00.163Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T13:05:00.169Z
/* DDL */  select update_Column_Translation_From_AD_Element(524)
;

-- 2022-08-23T05:23:57.166Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Processing CHAR(1)')
;

-- Column: I_Invoice_Candidate.I_LineContent
-- 2022-08-22T13:05:32.747Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584194,577115,0,14,542207,'I_LineContent',TO_TIMESTAMP('2022-08-22 16:05:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,4000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Import Line Content',0,0,TO_TIMESTAMP('2022-08-22 16:05:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T13:05:32.751Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T13:05:32.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(577115)
;

-- 2022-08-22T13:05:33.277Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN I_LineContent TEXT')
;

-- Column: I_Invoice_Candidate.I_LineNo
-- 2022-08-22T13:06:04.851Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584195,577116,0,11,542207,'I_LineNo',TO_TIMESTAMP('2022-08-22 16:06:04','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Import Line No',0,0,TO_TIMESTAMP('2022-08-22 16:06:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T13:06:04.854Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584195 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T13:06:04.861Z
/* DDL */  select update_Column_Translation_From_AD_Element(577116)
;

-- 2022-08-22T13:06:17.128Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN I_LineNo NUMERIC(10)')
;

-- Column: I_Invoice_Candidate.M_Product_ID
-- 2022-08-22T13:09:30.609Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584197,454,0,19,542207,'M_Product_ID',TO_TIMESTAMP('2022-08-22 16:09:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2022-08-22 16:09:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T13:09:30.612Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T13:09:30.618Z
/* DDL */  select update_Column_Translation_From_AD_Element(454)
;

-- 2022-08-22T13:09:31.159Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2022-08-22T13:09:31.170Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT MProduct_IInvoiceCandidate FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-22T13:28:21.254Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584198,2039,0,19,542207,'Bill_BPartner_ID',TO_TIMESTAMP('2022-08-22 16:28:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Geschäftspartner für die Rechnungsstellung','D',0,10,'Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungspartner',0,0,TO_TIMESTAMP('2022-08-22 16:28:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T13:28:21.257Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584198 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T13:28:21.298Z
/* DDL */  select update_Column_Translation_From_AD_Element(2039)
;

-- Column: I_Invoice_Candidate.Bill_BPartner_ID
-- 2022-08-22T13:28:43.164Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541252, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-08-22 16:28:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584198
;

-- 2022-08-22T13:28:43.672Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Bill_BPartner_ID NUMERIC(10)')
;

-- 2022-08-22T13:28:43.689Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT BillBPartner_IInvoiceCandidate FOREIGN KEY (Bill_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2022-08-22T13:29:32.681Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581361,0,TO_TIMESTAMP('2022-08-22 16:29:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bill_BPartner_Value','Bill_BPartner_Value',TO_TIMESTAMP('2022-08-22 16:29:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:29:32.685Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581361 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-08-22T13:29:37.841Z
UPDATE AD_Element SET ColumnName='Bill_BPartner_Value',Updated=TO_TIMESTAMP('2022-08-22 16:29:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581361
;

-- 2022-08-22T13:29:37.847Z
UPDATE AD_Column SET ColumnName='Bill_BPartner_Value', Name='Bill_BPartner_Value', Description=NULL, Help=NULL WHERE AD_Element_ID=581361
;

-- 2022-08-22T13:29:37.848Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Bill_BPartner_Value', Description=NULL, Help=NULL, AD_Element_ID=581361 WHERE UPPER(ColumnName)='BILL_BPARTNER_VALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-08-22T13:29:37.852Z
UPDATE AD_Process_Para SET ColumnName='Bill_BPartner_Value', Name='Bill_BPartner_Value', Description=NULL, Help=NULL WHERE AD_Element_ID=581361 AND IsCentrallyMaintained='Y'
;

-- Column: I_Invoice_Candidate.Bill_BPartner_Value
-- 2022-08-22T13:30:09.953Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584199,581361,0,10,542207,'Bill_BPartner_Value',TO_TIMESTAMP('2022-08-22 16:30:09','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bill_BPartner_Value',0,0,TO_TIMESTAMP('2022-08-22 16:30:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T13:30:09.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584199 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T13:30:09.962Z
/* DDL */  select update_Column_Translation_From_AD_Element(581361)
;

-- 2022-08-22T13:30:10.574Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Bill_BPartner_Value VARCHAR(255) NOT NULL')
;

-- 2022-08-22T13:30:59.270Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581362,0,'C_UOM_X12DE355',TO_TIMESTAMP('2022-08-22 16:30:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_UOM_X12DE355','C_UOM_X12DE355',TO_TIMESTAMP('2022-08-22 16:30:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T13:30:59.271Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581362 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_Invoice_Candidate.C_UOM_X12DE355
-- 2022-08-22T13:31:27.382Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584200,581362,0,10,542207,'C_UOM_X12DE355',TO_TIMESTAMP('2022-08-22 16:31:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'C_UOM_X12DE355',0,0,TO_TIMESTAMP('2022-08-22 16:31:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T13:31:27.384Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584200 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T13:31:27.389Z
/* DDL */  select update_Column_Translation_From_AD_Element(581362)
;

-- 2022-08-22T13:31:28.025Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_UOM_X12DE355 VARCHAR(255)')
;

-- Column: I_Invoice_Candidate.C_DataImport_ID
-- 2022-08-23T06:45:55.774Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584201,543913,0,30,542207,'C_DataImport_ID',TO_TIMESTAMP('2022-08-23 09:45:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Daten Import',0,0,TO_TIMESTAMP('2022-08-23 09:45:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-23T06:45:55.779Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584201 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-23T06:45:55.806Z
/* DDL */  select update_Column_Translation_From_AD_Element(543913)
;

-- 2022-08-23T06:46:10.272Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN C_DataImport_ID NUMERIC(10)')
;

-- 2022-08-23T06:46:10.286Z
ALTER TABLE I_Invoice_Candidate ADD CONSTRAINT CDataImport_IInvoiceCandidate FOREIGN KEY (C_DataImport_ID) REFERENCES public.C_DataImport DEFERRABLE INITIALLY DEFERRED
;


-- Column: I_Invoice_Candidate.ExternalHeaderId
-- 2022-08-23T06:47:54.223Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584202,575915,0,10,542207,'ExternalHeaderId',TO_TIMESTAMP('2022-08-23 09:47:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe Datensatz-Kopf-ID',0,0,TO_TIMESTAMP('2022-08-23 09:47:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-23T06:47:54.225Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584202 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-23T06:47:54.228Z
/* DDL */  select update_Column_Translation_From_AD_Element(575915)
;

-- 2022-08-23T06:47:54.700Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN ExternalHeaderId VARCHAR(255)')
;

-- Column: I_Invoice_Candidate.ExternalLineId
-- 2022-08-23T06:48:17.487Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584203,575914,0,10,542207,'ExternalLineId',TO_TIMESTAMP('2022-08-23 09:48:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe Datensatz-Zeilen-ID',0,0,TO_TIMESTAMP('2022-08-23 09:48:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-23T06:48:17.488Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584203 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-23T06:48:17.490Z
/* DDL */  select update_Column_Translation_From_AD_Element(575914)
;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2022-08-23T06:48:17.947Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN ExternalLineId VARCHAR(255)')
;

-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-23T13:09:54.854Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-08-23 16:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584186
;

-- 2022-08-23T13:09:55.801Z
INSERT INTO t_alter_column values('i_invoice_candidate','DocBaseType','VARCHAR(3)',null,'ARI')
;

-- 2022-08-23T13:09:55.813Z
INSERT INTO t_alter_column values('i_invoice_candidate','DocBaseType',null,'NULL',null)
;

-- Column: I_Invoice_Candidate.DocBaseType
-- 2022-08-23T13:10:03.354Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2022-08-23 16:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584186
;

-- 2022-08-23T13:10:03.812Z
INSERT INTO t_alter_column values('i_invoice_candidate','DocBaseType','VARCHAR(3)',null,null)
;

-- Column: I_Invoice_Candidate.I_IsImported
-- 2022-08-23T13:30:22.582Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2022-08-23 16:30:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584189
;

-- 2022-08-23T13:30:23.177Z
INSERT INTO t_alter_column values('i_invoice_candidate','I_IsImported','CHAR(1)',null,null)
;
