-- Table: ModCntr_InvoicingGroup
-- 2023-08-16T13:25:58.738188400Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542359,'N',TO_TIMESTAMP('2023-08-16 16:25:58.52','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoice Group','NP','L','ModCntr_InvoicingGroup','DTI',TO_TIMESTAMP('2023-08-16 16:25:58.52','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:25:59.185582500Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542359 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-08-16T13:25:59.292792500Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556295,TO_TIMESTAMP('2023-08-16 16:25:59.217','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table ModCntr_InvoicingGroup',1,'Y','N','Y','Y','ModCntr_InvoicingGroup','N',1000000,TO_TIMESTAMP('2023-08-16 16:25:59.217','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:25:59.310683400Z
CREATE SEQUENCE MODCNTR_INVOICINGGROUP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2023-08-16T13:26:18.935671400Z
UPDATE AD_Table_Trl SET Name='Rechnungsgruppe',Updated=TO_TIMESTAMP('2023-08-16 16:26:18.934','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542359
;

-- 2023-08-16T13:26:21.891657100Z
UPDATE AD_Table_Trl SET Name='Rechnungsgruppe',Updated=TO_TIMESTAMP('2023-08-16 16:26:21.889','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542359
;

-- 2023-08-16T13:26:21.895662400Z
UPDATE AD_Table SET Name='Rechnungsgruppe' WHERE AD_Table_ID=542359
;

-- 2023-08-16T13:26:26.633973300Z
UPDATE AD_Table_Trl SET Name='Rechnungsgruppe',Updated=TO_TIMESTAMP('2023-08-16 16:26:26.632','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542359
;

-- 2023-08-16T13:26:28.521907900Z
UPDATE AD_Table_Trl SET Name='Rechnungsgruppe',Updated=TO_TIMESTAMP('2023-08-16 16:26:28.52','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Table_ID=542359
;

-- Column: ModCntr_InvoicingGroup.AD_Client_ID
-- 2023-08-16T13:26:45.998863800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587279,102,0,19,542359,'AD_Client_ID',TO_TIMESTAMP('2023-08-16 16:26:45.853','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-08-16 16:26:45.853','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:46.002861400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587279 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:46.960863600Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ModCntr_InvoicingGroup.AD_Org_ID
-- 2023-08-16T13:26:48.279364700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587280,113,0,30,542359,'AD_Org_ID',TO_TIMESTAMP('2023-08-16 16:26:47.762','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-08-16 16:26:47.762','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:48.280366900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587280 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:48.949873Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ModCntr_InvoicingGroup.Created
-- 2023-08-16T13:26:49.645309400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587281,245,0,16,542359,'Created',TO_TIMESTAMP('2023-08-16 16:26:49.41','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-08-16 16:26:49.41','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:49.647355100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587281 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:50.297926700Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ModCntr_InvoicingGroup.CreatedBy
-- 2023-08-16T13:26:51.233343100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587282,246,0,18,110,542359,'CreatedBy',TO_TIMESTAMP('2023-08-16 16:26:50.948','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-08-16 16:26:50.948','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:51.235381300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587282 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:51.796503700Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ModCntr_InvoicingGroup.IsActive
-- 2023-08-16T13:26:52.505461300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587283,348,0,20,542359,'IsActive',TO_TIMESTAMP('2023-08-16 16:26:52.243','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-08-16 16:26:52.243','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:52.507461800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:53.098509900Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ModCntr_InvoicingGroup.Updated
-- 2023-08-16T13:26:53.797464100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587284,607,0,16,542359,'Updated',TO_TIMESTAMP('2023-08-16 16:26:53.554','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-08-16 16:26:53.554','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:53.799491900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587284 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:54.357464400Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ModCntr_InvoicingGroup.UpdatedBy
-- 2023-08-16T13:26:55.229558400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587285,608,0,18,110,542359,'UpdatedBy',TO_TIMESTAMP('2023-08-16 16:26:54.98','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-08-16 16:26:54.98','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:55.230590400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587285 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:55.796760200Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-08-16T13:26:56.365659700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582647,0,'ModCntr_InvoicingGroup_ID',TO_TIMESTAMP('2023-08-16 16:26:56.282','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rechnungsgruppe','Rechnungsgruppe',TO_TIMESTAMP('2023-08-16 16:26:56.282','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:26:56.374662900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582647 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_InvoicingGroup.ModCntr_InvoicingGroup_ID
-- 2023-08-16T13:26:57.026467100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587286,582647,0,13,542359,'ModCntr_InvoicingGroup_ID',TO_TIMESTAMP('2023-08-16 16:26:56.276','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Rechnungsgruppe',0,0,TO_TIMESTAMP('2023-08-16 16:26:56.276','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:26:57.029503700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587286 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:26:57.598518800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582647) 
;

-- 2023-08-16T13:26:58.106513300Z
/* DDL */ CREATE TABLE public.ModCntr_InvoicingGroup (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, ModCntr_InvoicingGroup_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ModCntr_InvoicingGroup_Key PRIMARY KEY (ModCntr_InvoicingGroup_ID))
;

-- 2023-08-16T13:26:58.142515Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-08-16T13:26:58.158514Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-08-16T13:26:58.172513800Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-08-16T13:26:58.186517100Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','IsActive','CHAR(1)',null,null)
;

-- 2023-08-16T13:26:58.219512Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-08-16T13:26:58.228512200Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-08-16T13:26:58.237512400Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup','ModCntr_InvoicingGroup_ID','NUMERIC(10)',null,null)
;

-- Column: ModCntr_InvoicingGroup.Name
-- 2023-08-16T13:28:23.250553700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587287,469,0,10,542359,'Name',TO_TIMESTAMP('2023-08-16 16:28:23.116','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,60,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-08-16 16:28:23.116','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:28:23.253016800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587287 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:28:24.125208700Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2023-08-16T13:28:27.552388900Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN Name VARCHAR(60) NOT NULL')
;

-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2023-08-16T13:29:39.917250200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587288,617,0,16,542359,'ValidFrom',TO_TIMESTAMP('2023-08-16 16:29:39.803','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Gültig ab inklusiv (erster Tag)','D',0,7,'"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gültig ab',0,0,TO_TIMESTAMP('2023-08-16 16:29:39.803','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:29:39.919246400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587288 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:29:41.005250Z
/* DDL */  select update_Column_Translation_From_AD_Element(617) 
;

-- 2023-08-16T13:29:43.598528900Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN ValidFrom TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2023-08-16T13:31:35.994303800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587289,618,0,16,542359,'ValidTo',TO_TIMESTAMP('2023-08-16 16:31:35.877','YYYY-MM-DD HH24:MI:SS.US'),100,'N','9999-12-31 23:59:59.0','Gültig bis inklusiv (letzter Tag)','D',0,7,'"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gültig bis',0,0,TO_TIMESTAMP('2023-08-16 16:31:35.877','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:31:35.998029200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587289 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:31:36.515016200Z
/* DDL */  select update_Column_Translation_From_AD_Element(618) 
;

-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2023-08-16T13:34:59.058914800Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2023-08-16 16:34:59.058','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587289
;

-- 2023-08-16T13:35:00.757096900Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN ValidTo TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2023-08-16T13:36:55.330306500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582648,0,'Group_Product_ID',TO_TIMESTAMP('2023-08-16 16:36:55.215','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Gruppierungsprodukt','Gruppierungsprodukt',TO_TIMESTAMP('2023-08-16 16:36:55.215','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:36:55.334343600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582648 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Group_Product_ID
-- 2023-08-16T13:37:17.175740300Z
UPDATE AD_Element_Trl SET Name='Grouping Product', PrintName='Grouping Product',Updated=TO_TIMESTAMP('2023-08-16 16:37:17.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582648 AND AD_Language='en_US'
;

-- 2023-08-16T13:37:17.180740800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582648,'en_US') 
;

-- Column: ModCntr_InvoicingGroup.Group_Product_ID
-- 2023-08-16T13:38:35.575624400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587290,582648,0,30,540272,542359,'Group_Product_ID',TO_TIMESTAMP('2023-08-16 16:38:35.461','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gruppierungsprodukt',0,0,TO_TIMESTAMP('2023-08-16 16:38:35.461','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T13:38:35.579657600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587290 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T13:38:36.092499800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582648) 
;

-- 2023-08-16T13:38:37.354507600Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE public.ModCntr_InvoicingGroup ADD COLUMN Group_Product_ID NUMERIC(10)')
;

-- 2023-08-16T13:38:37.367226500Z
ALTER TABLE ModCntr_InvoicingGroup ADD CONSTRAINT GroupProduct_ModCntrInvoicingGroup FOREIGN KEY (Group_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2023-08-16T13:41:23.095126Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582649,0,TO_TIMESTAMP('2023-08-16 16:41:22.943','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rechnungsgruppe','Rechnungsgruppe',TO_TIMESTAMP('2023-08-16 16:41:22.943','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:41:23.097125300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582649 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-08-16T13:41:31.041251300Z
UPDATE AD_Element_Trl SET Name='Invoice Group', PrintName='Invoice Group',Updated=TO_TIMESTAMP('2023-08-16 16:41:31.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582649 AND AD_Language='en_US'
;

-- 2023-08-16T13:41:31.043256300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582649,'en_US') 
;

-- Window: Rechnungsgruppe, InternalName=null
-- 2023-08-16T13:42:04.293557400Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582649,0,541728,TO_TIMESTAMP('2023-08-16 16:42:04.171','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Rechnungsgruppe','N',TO_TIMESTAMP('2023-08-16 16:42:04.171','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-08-16T13:42:04.296555200Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541728 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-08-16T13:42:04.302515900Z
/* DDL */  select update_window_translation_from_ad_element(582649) 
;

-- 2023-08-16T13:42:04.323552200Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541728
;

-- 2023-08-16T13:42:04.330129700Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541728)
;

-- Tab: Rechnungsgruppe(541728,D) -> Rechnungsgruppe
-- Table: ModCntr_InvoicingGroup
-- 2023-08-16T13:42:32.825807100Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582647,0,547205,542359,541728,'Y',TO_TIMESTAMP('2023-08-16 16:42:32.683','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','ModCntr_InvoicingGroup','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Rechnungsgruppe','N',10,0,TO_TIMESTAMP('2023-08-16 16:42:32.683','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:32.828801100Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547205 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-08-16T13:42:32.832170700Z
/* DDL */  select update_tab_translation_from_ad_element(582647) 
;

-- 2023-08-16T13:42:32.835146800Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547205)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Mandant
-- Column: ModCntr_InvoicingGroup.AD_Client_ID
-- 2023-08-16T13:42:37.583551300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587279,720153,0,547205,TO_TIMESTAMP('2023-08-16 16:42:37.481','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-08-16 16:42:37.481','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:37.586423600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:37.589459200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-08-16T13:42:37.749418400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720153
;

-- 2023-08-16T13:42:37.749418400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720153)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Organisation
-- Column: ModCntr_InvoicingGroup.AD_Org_ID
-- 2023-08-16T13:42:37.844348Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587280,720154,0,547205,TO_TIMESTAMP('2023-08-16 16:42:37.753','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-08-16 16:42:37.753','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:37.846397Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:37.848385800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-08-16T13:42:37.960383100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720154
;

-- 2023-08-16T13:42:37.960383100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720154)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Aktiv
-- Column: ModCntr_InvoicingGroup.IsActive
-- 2023-08-16T13:42:38.038835800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587283,720155,0,547205,TO_TIMESTAMP('2023-08-16 16:42:37.966','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-08-16 16:42:37.966','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:38.039886200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:38.040869900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-08-16T13:42:38.155020800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720155
;

-- 2023-08-16T13:42:38.156010900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720155)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Rechnungsgruppe
-- Column: ModCntr_InvoicingGroup.ModCntr_InvoicingGroup_ID
-- 2023-08-16T13:42:38.250694700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587286,720156,0,547205,TO_TIMESTAMP('2023-08-16 16:42:38.162','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnungsgruppe',TO_TIMESTAMP('2023-08-16 16:42:38.162','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:38.251733500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:38.253691200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582647) 
;

-- 2023-08-16T13:42:38.258732800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720156
;

-- 2023-08-16T13:42:38.259574200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720156)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Name
-- Column: ModCntr_InvoicingGroup.Name
-- 2023-08-16T13:42:38.352422200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587287,720157,0,547205,TO_TIMESTAMP('2023-08-16 16:42:38.262','YYYY-MM-DD HH24:MI:SS.US'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-08-16 16:42:38.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:38.353461300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:38.355422500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-08-16T13:42:38.421455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720157
;

-- 2023-08-16T13:42:38.422416700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720157)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Gültig ab
-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2023-08-16T13:42:38.514424500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587288,720158,0,547205,TO_TIMESTAMP('2023-08-16 16:42:38.428','YYYY-MM-DD HH24:MI:SS.US'),100,'Gültig ab inklusiv (erster Tag)',7,'D','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','N','N','N','N','N','Gültig ab',TO_TIMESTAMP('2023-08-16 16:42:38.428','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:38.516445500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:38.518445700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(617) 
;

-- 2023-08-16T13:42:38.536440400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720158
;

-- 2023-08-16T13:42:38.537444200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720158)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Gültig bis
-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2023-08-16T13:42:38.632309100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587289,720159,0,547205,TO_TIMESTAMP('2023-08-16 16:42:38.549','YYYY-MM-DD HH24:MI:SS.US'),100,'Gültig bis inklusiv (letzter Tag)',7,'D','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','N','N','N','N','N','Gültig bis',TO_TIMESTAMP('2023-08-16 16:42:38.549','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:38.633349400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720159 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:38.635349600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(618) 
;

-- 2023-08-16T13:42:38.647348500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720159
;

-- 2023-08-16T13:42:38.648317400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720159)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Rechnungsgruppe
-- Column: ModCntr_InvoicingGroup.Group_Product_ID
-- 2023-08-16T13:42:38.735520100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587290,720160,0,547205,TO_TIMESTAMP('2023-08-16 16:42:38.651','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Gruppierungsprodukt',TO_TIMESTAMP('2023-08-16 16:42:38.651','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:42:38.736520700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T13:42:38.738518900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582648) 
;

-- 2023-08-16T13:42:38.742519100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720160
;

-- 2023-08-16T13:42:38.742519100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720160)
;

-- Tab: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D)
-- UI Section: main
-- 2023-08-16T13:43:42.283667500Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547205,545800,TO_TIMESTAMP('2023-08-16 16:43:42.164','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-16 16:43:42.164','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-08-16T13:43:42.291746Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545800 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main
-- UI Column: 10
-- 2023-08-16T13:43:50.750675500Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547062,545800,TO_TIMESTAMP('2023-08-16 16:43:50.607','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-16 16:43:50.607','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main
-- UI Column: 20
-- 2023-08-16T13:43:52.187454Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547063,545800,TO_TIMESTAMP('2023-08-16 16:43:52.092','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-08-16 16:43:52.092','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20
-- UI Element Group: main
-- 2023-08-16T13:44:11.375258800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547063,551111,TO_TIMESTAMP('2023-08-16 16:44:11.246','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-08-16 16:44:11.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> main.Name
-- Column: ModCntr_InvoicingGroup.Name
-- 2023-08-16T13:44:37.425435400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720157,0,547205,620339,551111,'F',TO_TIMESTAMP('2023-08-16 16:44:37.284','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-08-16 16:44:37.284','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> main.Gruppierungsprodukt
-- Column: ModCntr_InvoicingGroup.Group_Product_ID
-- 2023-08-16T13:45:03.678992600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720160,0,547205,620340,551111,'F',TO_TIMESTAMP('2023-08-16 16:45:03.54','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Gruppierungsprodukt',20,0,0,TO_TIMESTAMP('2023-08-16 16:45:03.54','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> main.Gültig ab
-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2023-08-16T13:45:28.942043300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720158,0,547205,620341,551111,'F',TO_TIMESTAMP('2023-08-16 16:45:28.806','YYYY-MM-DD HH24:MI:SS.US'),100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','N','N',0,'Gültig ab',30,0,0,TO_TIMESTAMP('2023-08-16 16:45:28.806','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> main.Gültig bis
-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2023-08-16T13:45:48.746699600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720159,0,547205,620342,551111,'F',TO_TIMESTAMP('2023-08-16 16:45:48.634','YYYY-MM-DD HH24:MI:SS.US'),100,'Gültig bis inklusiv (letzter Tag)','"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','N','N',0,'Gültig bis',40,0,0,TO_TIMESTAMP('2023-08-16 16:45:48.634','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10
-- UI Element Group: main
-- 2023-08-16T13:46:19.087552200Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547062,551112,TO_TIMESTAMP('2023-08-16 16:46:18.963','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-08-16 16:46:18.963','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Name
-- Column: ModCntr_InvoicingGroup.Name
-- 2023-08-16T13:46:45.392836100Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551112, SeqNo=10,Updated=TO_TIMESTAMP('2023-08-16 16:46:45.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620339
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Gruppierungsprodukt
-- Column: ModCntr_InvoicingGroup.Group_Product_ID
-- 2023-08-16T13:47:00.225059400Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551112, SeqNo=20,Updated=TO_TIMESTAMP('2023-08-16 16:47:00.224','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620340
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Gültig ab
-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2023-08-16T13:47:11.299826800Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551112, SeqNo=30,Updated=TO_TIMESTAMP('2023-08-16 16:47:11.299','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620341
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Gültig bis
-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2023-08-16T13:47:17.425536300Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551112, SeqNo=40,Updated=TO_TIMESTAMP('2023-08-16 16:47:17.425','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620342
;

-- UI Column: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20
-- UI Element Group: main
-- 2023-08-16T13:47:34.281258500Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=551111
;

-- UI Column: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20
-- UI Element Group: active
-- 2023-08-16T13:47:41.616605600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547063,551113,TO_TIMESTAMP('2023-08-16 16:47:41.499','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','active',10,TO_TIMESTAMP('2023-08-16 16:47:41.499','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> active.Aktiv
-- Column: ModCntr_InvoicingGroup.IsActive
-- 2023-08-16T13:48:01.784048700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720155,0,547205,620343,551113,'F',TO_TIMESTAMP('2023-08-16 16:48:01.667','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-08-16 16:48:01.667','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20
-- UI Element Group: org
-- 2023-08-16T13:48:09.203565300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547063,551114,TO_TIMESTAMP('2023-08-16 16:48:09.069','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',20,TO_TIMESTAMP('2023-08-16 16:48:09.069','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> org.Organisation
-- Column: ModCntr_InvoicingGroup.AD_Org_ID
-- 2023-08-16T13:48:40.172008500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720154,0,547205,620344,551114,'F',TO_TIMESTAMP('2023-08-16 16:48:40.058','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-08-16 16:48:40.058','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> org.Mandant
-- Column: ModCntr_InvoicingGroup.AD_Client_ID
-- 2023-08-16T13:49:00.634550100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720153,0,547205,620345,551114,'F',TO_TIMESTAMP('2023-08-16 16:49:00.51','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-08-16 16:49:00.51','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: Rechnungsgruppe
-- Action Type: W
-- Window: Rechnungsgruppe(541728,D)
-- 2023-08-16T13:52:49.353103300Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582649,542106,0,541728,TO_TIMESTAMP('2023-08-16 16:52:49.228','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Rechnungsgruppe','Y','N','N','N','N','Rechnungsgruppe',TO_TIMESTAMP('2023-08-16 16:52:49.228','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T13:52:49.355104700Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542106 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-08-16T13:52:49.359101400Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542106, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542106)
;

-- 2023-08-16T13:52:49.370100900Z
/* DDL */  select update_menu_translation_from_ad_element(582649) 
;

-- Reordering children of `Contract Management`
-- Node name: `Contractpartner (C_Flatrate_Data)`
-- 2023-08-16T13:52:57.618651200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000080 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Term)`
-- 2023-08-16T13:52:57.624688500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540951 AND AD_Tree_ID=10
;

-- Node name: `Contract Invoicecandidates (C_Invoice_Clearing_Alloc)`
-- 2023-08-16T13:52:57.625735900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540952 AND AD_Tree_ID=10
;

-- Node name: `Subscription History (C_SubscriptionProgress)`
-- 2023-08-16T13:52:57.626700200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540953 AND AD_Tree_ID=10
;

-- Node name: `Contract Terms (C_Flatrate_Conditions)`
-- 2023-08-16T13:52:57.626700200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540883 AND AD_Tree_ID=10
;

-- Node name: `Contract Period (C_Flatrate_Transition)`
-- 2023-08-16T13:52:57.627781100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540884 AND AD_Tree_ID=10
;

-- Node name: `Contract Module Type (ModCntr_Type)`
-- 2023-08-16T13:52:57.627781100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542086 AND AD_Tree_ID=10
;

-- Node name: `Subscriptions import (I_Flatrate_Term)`
-- 2023-08-16T13:52:57.628808100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540920 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Log (ModCntr_Log)`
-- 2023-08-16T13:52:57.628808100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542087 AND AD_Tree_ID=10
;

-- Node name: `Membership Month (C_MembershipMonth)`
-- 2023-08-16T13:52:57.629788500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541740 AND AD_Tree_ID=10
;

-- Node name: `Subscription Discount (C_SubscrDiscount)`
-- 2023-08-16T13:52:57.629788500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541766 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Flatrate Term (C_InterimInvoice_FlatrateTerm)`
-- 2023-08-16T13:52:57.629788500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541979 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-08-16T13:52:57.630788400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000054 AND AD_Tree_ID=10
;

-- Node name: `Interim Invoice Settings (C_Interim_Invoice_Settings)`
-- 2023-08-16T13:52:57.631789300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541974 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-08-16T13:52:57.631789300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000062 AND AD_Tree_ID=10
;

-- Node name: `Type specific settings`
-- 2023-08-16T13:52:57.632818400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000070 AND AD_Tree_ID=10
;

-- Node name: `Create/Update Customer Retentions (de.metas.contracts.process.C_Customer_Retention_CreateUpdate)`
-- 2023-08-16T13:52:57.632818400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541170 AND AD_Tree_ID=10
;

-- Node name: `Call-off order overview (C_CallOrderSummary)`
-- 2023-08-16T13:52:57.633793700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541909 AND AD_Tree_ID=10
;

-- Node name: `Modular Contract Settings (ModCntr_Settings)`
-- 2023-08-16T13:52:57.633793700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542088 AND AD_Tree_ID=10
;

-- Node name: `Rechnungsgruppe`
-- 2023-08-16T13:52:57.634814500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000013, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542106 AND AD_Tree_ID=10
;

-- Table: ModCntr_InvoicingGroup_Product
-- 2023-08-16T14:34:26.308097700Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542360,'N',TO_TIMESTAMP('2023-08-16 17:34:26.136','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Rechnungsgruppe Produkt','NP','L','ModCntr_InvoicingGroup_Product','DTI',TO_TIMESTAMP('2023-08-16 17:34:26.136','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:26.313189Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542360 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-08-16T14:34:26.877988800Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556296,TO_TIMESTAMP('2023-08-16 17:34:26.797','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table ModCntr_InvoicingGroup_Product',1,'Y','N','Y','Y','ModCntr_InvoicingGroup_Product','N',1000000,TO_TIMESTAMP('2023-08-16 17:34:26.797','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:34:26.889024700Z
CREATE SEQUENCE MODCNTR_INVOICINGGROUP_PRODUCT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ModCntr_InvoicingGroup_Product.AD_Client_ID
-- 2023-08-16T14:34:34.309601900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587291,102,0,19,542360,'AD_Client_ID',TO_TIMESTAMP('2023-08-16 17:34:34.171','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-08-16 17:34:34.171','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:34.311604100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587291 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:35.142673200Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: ModCntr_InvoicingGroup_Product.AD_Org_ID
-- 2023-08-16T14:34:36.083707300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587292,113,0,30,542360,'AD_Org_ID',TO_TIMESTAMP('2023-08-16 17:34:35.745','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-08-16 17:34:35.745','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:36.084710400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587292 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:36.702305800Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: ModCntr_InvoicingGroup_Product.Created
-- 2023-08-16T14:34:37.521304400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587293,245,0,16,542360,'Created',TO_TIMESTAMP('2023-08-16 17:34:37.278','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-08-16 17:34:37.278','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:37.523306100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587293 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:38.134304600Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: ModCntr_InvoicingGroup_Product.CreatedBy
-- 2023-08-16T14:34:38.895307400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587294,246,0,18,110,542360,'CreatedBy',TO_TIMESTAMP('2023-08-16 17:34:38.608','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-08-16 17:34:38.608','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:38.896307400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587294 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:39.714150300Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: ModCntr_InvoicingGroup_Product.IsActive
-- 2023-08-16T14:34:40.609899500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587295,348,0,20,542360,'IsActive',TO_TIMESTAMP('2023-08-16 17:34:40.285','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-08-16 17:34:40.285','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:40.610899900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587295 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:41.476716900Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: ModCntr_InvoicingGroup_Product.Updated
-- 2023-08-16T14:34:42.938915300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587296,607,0,16,542360,'Updated',TO_TIMESTAMP('2023-08-16 17:34:42.415','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-08-16 17:34:42.415','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:42.940915100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587296 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:44.284133400Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: ModCntr_InvoicingGroup_Product.UpdatedBy
-- 2023-08-16T14:34:45.673657800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587297,608,0,18,110,542360,'UpdatedBy',TO_TIMESTAMP('2023-08-16 17:34:45.209','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-08-16 17:34:45.209','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:45.675657900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587297 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:46.938860900Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-08-16T14:34:47.968848300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582650,0,'ModCntr_InvoicingGroup_Product_ID',TO_TIMESTAMP('2023-08-16 17:34:47.874','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rechnungsgruppe Produkt','Rechnungsgruppe Produkt',TO_TIMESTAMP('2023-08-16 17:34:47.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:34:47.971849400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582650 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_InvoicingGroup_Product.ModCntr_InvoicingGroup_Product_ID
-- 2023-08-16T14:34:49.295430700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587298,582650,0,13,542360,'ModCntr_InvoicingGroup_Product_ID',TO_TIMESTAMP('2023-08-16 17:34:47.872','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Rechnungsgruppe Produkt',0,0,TO_TIMESTAMP('2023-08-16 17:34:47.872','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:34:49.298433800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587298 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:34:50.706594Z
/* DDL */  select update_Column_Translation_From_AD_Element(582650) 
;

-- 2023-08-16T14:34:51.583984900Z
/* DDL */ CREATE TABLE public.ModCntr_InvoicingGroup_Product (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, ModCntr_InvoicingGroup_Product_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ModCntr_InvoicingGroup_Product_Key PRIMARY KEY (ModCntr_InvoicingGroup_Product_ID))
;

-- 2023-08-16T14:34:51.609519700Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-08-16T14:34:51.625521900Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-08-16T14:34:51.638522500Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-08-16T14:34:51.649521Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','IsActive','CHAR(1)',null,null)
;

-- 2023-08-16T14:34:51.682525100Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-08-16T14:34:51.697519100Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-08-16T14:34:51.708521100Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','ModCntr_InvoicingGroup_Product_ID','NUMERIC(10)',null,null)
;

-- 2023-08-16T14:35:20.618172100Z
UPDATE AD_Table_Trl SET Name='Invoice Group Product',Updated=TO_TIMESTAMP('2023-08-16 17:35:20.617','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542360
;

-- Column: ModCntr_InvoicingGroup_Product.ModCntr_InvoicingGroup_ID
-- 2023-08-16T14:36:34.250269200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587299,582647,0,30,542360,'ModCntr_InvoicingGroup_ID',TO_TIMESTAMP('2023-08-16 17:36:34.124','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Gruppe Fakturierung',0,0,TO_TIMESTAMP('2023-08-16 17:36:34.124','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:36:34.252268800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587299 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:36:35.240966700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582647) 
;

-- 2023-08-16T14:36:37.076371600Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup_Product','ALTER TABLE public.ModCntr_InvoicingGroup_Product ADD COLUMN ModCntr_InvoicingGroup_ID NUMERIC(10) NOT NULL')
;

-- 2023-08-16T14:36:37.085374200Z
ALTER TABLE ModCntr_InvoicingGroup_Product ADD CONSTRAINT ModCntrInvoicingGroup_ModCntrInvoicingGroupProduct FOREIGN KEY (ModCntr_InvoicingGroup_ID) REFERENCES public.ModCntr_InvoicingGroup DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_InvoicingGroup_Product.M_Product_ID
-- 2023-08-16T14:37:07.840671400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587300,454,0,30,540272,542360,'M_Product_ID',TO_TIMESTAMP('2023-08-16 17:37:07.69','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-08-16 17:37:07.69','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-16T14:37:07.842671100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587300 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-16T14:37:08.842597900Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2023-08-16T14:37:10.612292600Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup_Product','ALTER TABLE public.ModCntr_InvoicingGroup_Product ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2023-08-16T14:37:10.630292500Z
ALTER TABLE ModCntr_InvoicingGroup_Product ADD CONSTRAINT MProduct_ModCntrInvoicingGroupProduct FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Tab: Rechnungsgruppe(541728,D) -> Rechnungsgruppe Produkt
-- Table: ModCntr_InvoicingGroup_Product
-- 2023-08-16T14:38:59.478030600Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582650,0,547206,542360,541728,'Y',TO_TIMESTAMP('2023-08-16 17:38:59.348','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','ModCntr_InvoicingGroup_Product','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Rechnungsgruppe Produkt','N',20,1,TO_TIMESTAMP('2023-08-16 17:38:59.348','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:38:59.481429300Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547206 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-08-16T14:38:59.484406700Z
/* DDL */  select update_tab_translation_from_ad_element(582650) 
;

-- 2023-08-16T14:38:59.491397900Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547206)
;

-- 2023-08-16T14:39:19.430843400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582651,0,TO_TIMESTAMP('2023-08-16 17:39:19.328','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Quellprodukte','Quellprodukte',TO_TIMESTAMP('2023-08-16 17:39:19.328','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:39:19.432835200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582651 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-08-16T14:39:27.611237300Z
UPDATE AD_Element_Trl SET Name='Source Products', PrintName='Source Products',Updated=TO_TIMESTAMP('2023-08-16 17:39:27.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582651 AND AD_Language='en_US'
;

-- 2023-08-16T14:39:27.613237100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582651,'en_US') 
;

-- Tab: Rechnungsgruppe(541728,D) -> Quellprodukte
-- Table: ModCntr_InvoicingGroup_Product
-- 2023-08-16T14:39:40.663212500Z
UPDATE AD_Tab SET AD_Element_ID=582651, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Quellprodukte',Updated=TO_TIMESTAMP('2023-08-16 17:39:40.663','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547206
;

-- 2023-08-16T14:39:40.668175700Z
UPDATE AD_Tab_Trl trl SET Name='Quellprodukte' WHERE AD_Tab_ID=547206 AND AD_Language='de_DE'
;

-- 2023-08-16T14:39:40.669229Z
/* DDL */  select update_tab_translation_from_ad_element(582651) 
;

-- 2023-08-16T14:39:40.674031700Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547206)
;

-- Field: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> Mandant
-- Column: ModCntr_InvoicingGroup_Product.AD_Client_ID
-- 2023-08-16T14:39:44.879062800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587291,720161,0,547206,TO_TIMESTAMP('2023-08-16 17:39:44.741','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-08-16 17:39:44.741','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:39:44.883062400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T14:39:44.888062100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-08-16T14:39:45.004095500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720161
;

-- 2023-08-16T14:39:45.005057300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720161)
;

-- Field: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> Organisation
-- Column: ModCntr_InvoicingGroup_Product.AD_Org_ID
-- 2023-08-16T14:39:45.101697700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587292,720162,0,547206,TO_TIMESTAMP('2023-08-16 17:39:45.007','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-08-16 17:39:45.007','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:39:45.103720700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T14:39:45.104722300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-08-16T14:39:45.210753500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720162
;

-- 2023-08-16T14:39:45.210753500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720162)
;

-- Field: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> Aktiv
-- Column: ModCntr_InvoicingGroup_Product.IsActive
-- 2023-08-16T14:39:45.306284800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587295,720163,0,547206,TO_TIMESTAMP('2023-08-16 17:39:45.212','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-08-16 17:39:45.212','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:39:45.308280500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T14:39:45.310284900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-08-16T14:39:45.417274700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720163
;

-- 2023-08-16T14:39:45.417274700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720163)
;

-- Field: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> Rechnungsgruppe Produkt
-- Column: ModCntr_InvoicingGroup_Product.ModCntr_InvoicingGroup_Product_ID
-- 2023-08-16T14:39:45.508560Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587298,720164,0,547206,TO_TIMESTAMP('2023-08-16 17:39:45.419','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnungsgruppe Produkt',TO_TIMESTAMP('2023-08-16 17:39:45.419','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:39:45.510588200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T14:39:45.511552800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582650) 
;

-- 2023-08-16T14:39:45.517555100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720164
;

-- 2023-08-16T14:39:45.517555100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720164)
;

-- Field: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> Rechnungsgruppe
-- Column: ModCntr_InvoicingGroup_Product.ModCntr_InvoicingGroup_ID
-- 2023-08-16T14:39:45.606999800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587299,720165,0,547206,TO_TIMESTAMP('2023-08-16 17:39:45.519','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Rechnungsgruppe',TO_TIMESTAMP('2023-08-16 17:39:45.519','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:39:45.609058700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T14:39:45.610018900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582647) 
;

-- 2023-08-16T14:39:45.616018Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720165
;

-- 2023-08-16T14:39:45.616018Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720165)
;

-- Field: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> Produkt
-- Column: ModCntr_InvoicingGroup_Product.M_Product_ID
-- 2023-08-16T14:39:45.715039100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587300,720166,0,547206,TO_TIMESTAMP('2023-08-16 17:39:45.619','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-08-16 17:39:45.619','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-16T14:39:45.716997700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-16T14:39:45.718995400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-08-16T14:39:45.766031200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720166
;

-- 2023-08-16T14:39:45.766031200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720166)
;

-- Tab: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D)
-- UI Section: main
-- 2023-08-16T14:39:57.249082100Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547206,545801,TO_TIMESTAMP('2023-08-16 17:39:57.13','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-16 17:39:57.13','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-08-16T14:39:57.251008400Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545801 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Column: ModCntr_InvoicingGroup_Product.ModCntr_InvoicingGroup_ID
-- 2023-08-16T14:42:11.454131600Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-08-16 17:42:11.453','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587299
;

-- 2023-08-16T14:42:13.359519900Z
INSERT INTO t_alter_column values('modcntr_invoicinggroup_product','ModCntr_InvoicingGroup_ID','NUMERIC(10)',null,null)
;

-- Tab: Rechnungsgruppe(541728,D) -> Quellprodukte
-- Table: ModCntr_InvoicingGroup_Product
-- 2023-08-16T14:42:31.782465800Z
UPDATE AD_Tab SET Parent_Column_ID=587286,Updated=TO_TIMESTAMP('2023-08-16 17:42:31.781','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547206
;

-- UI Section: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> main
-- UI Column: 10
-- 2023-08-16T14:43:14.056710100Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547064,545801,TO_TIMESTAMP('2023-08-16 17:43:13.941','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-16 17:43:13.941','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> main -> 10
-- UI Element Group: default
-- 2023-08-16T14:43:22.877259300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547064,551115,TO_TIMESTAMP('2023-08-16 17:43:22.755','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-08-16 17:43:22.755','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> main -> 10 -> default.Produkt
-- Column: ModCntr_InvoicingGroup_Product.M_Product_ID
-- 2023-08-16T14:43:54.393525500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720166,0,547206,620346,551115,'F',TO_TIMESTAMP('2023-08-16 17:43:54.269','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2023-08-16 17:43:54.269','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> main -> 10 -> default.Aktiv
-- Column: ModCntr_InvoicingGroup_Product.IsActive
-- 2023-08-16T14:46:01.624722400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720163,0,547206,620347,551115,'F',TO_TIMESTAMP('2023-08-16 17:46:01.501','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2023-08-16 17:46:01.501','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> main -> 10 -> default.Organisation
-- Column: ModCntr_InvoicingGroup_Product.AD_Org_ID
-- 2023-08-16T14:46:44.515193300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720162,0,547206,620348,551115,'F',TO_TIMESTAMP('2023-08-16 17:46:44.388','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',30,0,0,TO_TIMESTAMP('2023-08-16 17:46:44.388','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Quellprodukte(547206,D) -> main -> 10 -> default.Mandant
-- Column: ModCntr_InvoicingGroup_Product.AD_Client_ID
-- 2023-08-16T14:47:11.391642800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720161,0,547206,620349,551115,'F',TO_TIMESTAMP('2023-08-16 17:47:11.272','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',40,0,0,TO_TIMESTAMP('2023-08-16 17:47:11.272','YYYY-MM-DD HH24:MI:SS.US'),100)
;

