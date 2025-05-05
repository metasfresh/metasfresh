-- Column: C_Order.InternalDescription
-- 2023-08-09T10:08:21.619683100Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-09 13:08:21.618','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587257
;

-- Column: C_Order.M_Warehouse_ID
-- 2023-08-09T10:12:12.532978400Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-09 13:12:12.531','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=2202
;

-- Column: C_Order.M_Locator_ID
-- 2023-08-09T10:12:20.968080Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-08-09 13:12:20.968','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587254
;

-- Table: C_Auction
-- 2023-08-09T10:19:59.081873500Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542358,'N',TO_TIMESTAMP('2023-08-09 13:19:58.083','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Auction','NP','L','C_Auction','DTI',TO_TIMESTAMP('2023-08-09 13:19:58.083','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:19:59.083874900Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542358 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-08-09T10:20:00.042468Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556294,TO_TIMESTAMP('2023-08-09 13:19:59.952','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_Auction',1,'Y','N','Y','Y','C_Auction','N',1000000,TO_TIMESTAMP('2023-08-09 13:19:59.952','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:20:00.072761Z
CREATE SEQUENCE C_AUCTION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Auction.AD_Client_ID
-- 2023-08-09T10:20:16.681435900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587258,102,0,19,542358,'AD_Client_ID',TO_TIMESTAMP('2023-08-09 13:20:16.547','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-08-09 13:20:16.547','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:16.684159700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587258 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:17.248590200Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Auction.AD_Org_ID
-- 2023-08-09T10:20:18.126188Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587259,113,0,30,542358,'AD_Org_ID',TO_TIMESTAMP('2023-08-09 13:20:17.733','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-08-09 13:20:17.733','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:18.129226700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587259 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:18.698988Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Auction.Created
-- 2023-08-09T10:20:19.521678600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587260,245,0,16,542358,'Created',TO_TIMESTAMP('2023-08-09 13:20:19.222','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-08-09 13:20:19.222','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:19.523675800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587260 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:20.081575900Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Auction.CreatedBy
-- 2023-08-09T10:20:20.842976700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587261,246,0,18,110,542358,'CreatedBy',TO_TIMESTAMP('2023-08-09 13:20:20.571','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-08-09 13:20:20.571','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:20.843975Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587261 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:21.455190900Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Auction.IsActive
-- 2023-08-09T10:20:22.303922700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587262,348,0,20,542358,'IsActive',TO_TIMESTAMP('2023-08-09 13:20:21.952','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-08-09 13:20:21.952','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:22.305920400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587262 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:22.934101400Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Auction.Updated
-- 2023-08-09T10:20:23.682096800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587263,607,0,16,542358,'Updated',TO_TIMESTAMP('2023-08-09 13:20:23.449','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-08-09 13:20:23.449','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:23.684095400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587263 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:24.408607400Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Auction.UpdatedBy
-- 2023-08-09T10:20:25.345583500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587264,608,0,18,110,542358,'UpdatedBy',TO_TIMESTAMP('2023-08-09 13:20:25.037','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-08-09 13:20:25.037','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:25.346573600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587264 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:26.271632Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-08-09T10:20:27.058921800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582634,0,'C_Auction_ID',TO_TIMESTAMP('2023-08-09 13:20:26.963','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Auction','Auction',TO_TIMESTAMP('2023-08-09 13:20:26.963','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:20:27.061922900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582634 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Auction.C_Auction_ID
-- 2023-08-09T10:20:28.082081500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587265,582634,0,13,542358,'C_Auction_ID',TO_TIMESTAMP('2023-08-09 13:20:26.961','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Auction',0,0,TO_TIMESTAMP('2023-08-09 13:20:26.961','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:20:28.084080900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587265 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:20:29.284955700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582634) 
;

-- 2023-08-09T10:20:30.420782800Z
/* DDL */ CREATE TABLE public.C_Auction (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Auction_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Auction_Key PRIMARY KEY (C_Auction_ID))
;

-- 2023-08-09T10:20:30.484821700Z
INSERT INTO t_alter_column values('c_auction','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-08-09T10:20:30.511611200Z
INSERT INTO t_alter_column values('c_auction','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-08-09T10:20:30.538188300Z
INSERT INTO t_alter_column values('c_auction','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-08-09T10:20:30.559814700Z
INSERT INTO t_alter_column values('c_auction','IsActive','CHAR(1)',null,null)
;

-- 2023-08-09T10:20:30.610164300Z
INSERT INTO t_alter_column values('c_auction','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-08-09T10:20:30.632972300Z
INSERT INTO t_alter_column values('c_auction','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-08-09T10:20:30.651203600Z
INSERT INTO t_alter_column values('c_auction','C_Auction_ID','NUMERIC(10)',null,null)
;

-- 2023-08-09T10:20:41.061395Z
UPDATE AD_Table_Trl SET Name='Ausschreibung',Updated=TO_TIMESTAMP('2023-08-09 13:20:41.06','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542358
;

-- 2023-08-09T10:20:44.703189800Z
UPDATE AD_Table_Trl SET Name='Ausschreibung',Updated=TO_TIMESTAMP('2023-08-09 13:20:44.702','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542358
;

-- 2023-08-09T10:20:44.704190900Z
UPDATE AD_Table SET Name='Ausschreibung' WHERE AD_Table_ID=542358
;

-- 2023-08-09T10:20:48.215262Z
UPDATE AD_Table_Trl SET Name='Ausschreibung',Updated=TO_TIMESTAMP('2023-08-09 13:20:48.214','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542358
;

-- 2023-08-09T10:20:50.302426800Z
UPDATE AD_Table_Trl SET Name='Ausschreibung',Updated=TO_TIMESTAMP('2023-08-09 13:20:50.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Table_ID=542358
;

-- Column: C_Auction.Date
-- 2023-08-09T10:24:38.892763100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587266,577762,0,15,542358,'Date',TO_TIMESTAMP('2023-08-09 13:24:38.742','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Datum',0,0,TO_TIMESTAMP('2023-08-09 13:24:38.742','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:24:38.894757400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587266 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:24:39.521385900Z
/* DDL */  select update_Column_Translation_From_AD_Element(577762) 
;

-- 2023-08-09T10:24:41.592653400Z
/* DDL */ SELECT public.db_alter_table('C_Auction','ALTER TABLE public.C_Auction ADD COLUMN Date TIMESTAMP WITHOUT TIME ZONE NOT NULL')
;

-- Column: C_Auction.Name
-- 2023-08-09T10:25:51.147274Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587267,469,0,10,542358,'Name',TO_TIMESTAMP('2023-08-09 13:25:50.998','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,60,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-08-09 13:25:50.998','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:25:51.150309500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587267 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:25:51.696013800Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2023-08-09T10:25:53.616678300Z
/* DDL */ SELECT public.db_alter_table('C_Auction','ALTER TABLE public.C_Auction ADD COLUMN Name VARCHAR(60) NOT NULL')
;

-- Column: C_Auction.C_Harvesting_Calendar_ID
-- 2023-08-09T10:27:26.872574500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587268,581157,0,30,540260,542358,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-09 13:27:26.731','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-08-09 13:27:26.731','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:27:26.873417500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587268 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:27:27.643006800Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- 2023-08-09T10:27:29.344869200Z
/* DDL */ SELECT public.db_alter_table('C_Auction','ALTER TABLE public.C_Auction ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-08-09T10:27:29.351395100Z
ALTER TABLE C_Auction ADD CONSTRAINT CHarvestingCalendar_CAuction FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_Auction.Harvesting_Year_ID
-- 2023-08-09T10:28:36.460335100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587269,582471,0,30,540133,542358,540647,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-09 13:28:36.279','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@C_Harvesting_Calendar_ID/-1@!0',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-08-09 13:28:36.279','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T10:28:36.461860700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587269 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T10:28:37.146525600Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- 2023-08-09T10:28:39.257077900Z
/* DDL */ SELECT public.db_alter_table('C_Auction','ALTER TABLE public.C_Auction ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-08-09T10:28:39.265345100Z
ALTER TABLE C_Auction ADD CONSTRAINT HarvestingYear_CAuction FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Window: Auction, InternalName=null
-- 2023-08-09T10:34:23.245082700Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582634,0,541724,TO_TIMESTAMP('2023-08-09 13:34:23.118','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Auction','N',TO_TIMESTAMP('2023-08-09 13:34:23.118','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-08-09T10:34:23.248080400Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541724 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-08-09T10:34:23.252110300Z
/* DDL */  select update_window_translation_from_ad_element(582634) 
;

-- 2023-08-09T10:34:23.259579300Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541724
;

-- 2023-08-09T10:34:23.268129700Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541724)
;

-- Tab: Auction(541724,D) -> Auction
-- Table: C_Auction
-- 2023-08-09T10:35:14.930567700Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582634,0,547115,542358,541724,'Y',TO_TIMESTAMP('2023-08-09 13:35:14.751','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','C_Auction','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Auction','N',10,0,TO_TIMESTAMP('2023-08-09 13:35:14.751','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:14.933548Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547115 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-08-09T10:35:14.934897500Z
/* DDL */  select update_tab_translation_from_ad_element(582634) 
;

-- 2023-08-09T10:35:14.939896600Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547115)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Mandant
-- Column: C_Auction.AD_Client_ID
-- 2023-08-09T10:35:18.148275200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587258,718733,0,547115,TO_TIMESTAMP('2023-08-09 13:35:18.058','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-08-09 13:35:18.058','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:18.149305200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:18.151273700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-08-09T10:35:18.301851500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718733
;

-- 2023-08-09T10:35:18.301851500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718733)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Organisation
-- Column: C_Auction.AD_Org_ID
-- 2023-08-09T10:35:18.403271400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587259,718734,0,547115,TO_TIMESTAMP('2023-08-09 13:35:18.304','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-08-09 13:35:18.304','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:18.404270900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718734 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:18.405272900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-08-09T10:35:18.595018900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718734
;

-- 2023-08-09T10:35:18.595018900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718734)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Aktiv
-- Column: C_Auction.IsActive
-- 2023-08-09T10:35:18.697269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587262,718735,0,547115,TO_TIMESTAMP('2023-08-09 13:35:18.598','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-08-09 13:35:18.598','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:18.698268200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718735 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:18.699267Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-08-09T10:35:18.874155700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718735
;

-- 2023-08-09T10:35:18.874155700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718735)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Auction
-- Column: C_Auction.C_Auction_ID
-- 2023-08-09T10:35:18.968352800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587265,718736,0,547115,TO_TIMESTAMP('2023-08-09 13:35:18.877','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Auction',TO_TIMESTAMP('2023-08-09 13:35:18.877','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:18.969362800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718736 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:18.984669100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582634) 
;

-- 2023-08-09T10:35:18.989370400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718736
;

-- 2023-08-09T10:35:18.989370400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718736)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Datum
-- Column: C_Auction.Date
-- 2023-08-09T10:35:19.088272900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587266,718737,0,547115,TO_TIMESTAMP('2023-08-09 13:35:18.991','YYYY-MM-DD HH24:MI:SS.US'),100,7,'D','Y','N','N','N','N','N','N','N','Datum',TO_TIMESTAMP('2023-08-09 13:35:18.991','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:19.089272900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718737 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:19.091273400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577762) 
;

-- 2023-08-09T10:35:19.103275100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718737
;

-- 2023-08-09T10:35:19.103275100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718737)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Name
-- Column: C_Auction.Name
-- 2023-08-09T10:35:19.197531900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587267,718738,0,547115,TO_TIMESTAMP('2023-08-09 13:35:19.107','YYYY-MM-DD HH24:MI:SS.US'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-08-09 13:35:19.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:19.198533800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718738 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:19.199532900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-08-09T10:35:19.284686400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718738
;

-- 2023-08-09T10:35:19.285690900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718738)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Erntekalender
-- Column: C_Auction.C_Harvesting_Calendar_ID
-- 2023-08-09T10:35:19.373746400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587268,718739,0,547115,TO_TIMESTAMP('2023-08-09 13:35:19.288','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntekalender',TO_TIMESTAMP('2023-08-09 13:35:19.288','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:19.374747900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718739 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:19.376276200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-09T10:35:19.380276800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718739
;

-- 2023-08-09T10:35:19.380276800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718739)
;

-- Field: Auction(541724,D) -> Auction(547115,D) -> Erntejahr
-- Column: C_Auction.Harvesting_Year_ID
-- 2023-08-09T10:35:19.483216900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587269,718740,0,547115,TO_TIMESTAMP('2023-08-09 13:35:19.382','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntejahr',TO_TIMESTAMP('2023-08-09 13:35:19.382','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:35:19.484216700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=718740 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-09T10:35:19.485217600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-09T10:35:19.491247600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=718740
;

-- 2023-08-09T10:35:19.491247600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(718740)
;

-- 2023-08-09T10:36:10.230176700Z
INSERT INTO t_alter_column values('c_auction','TIMESTAMP','WITHOUT TIME ZONE TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2023-08-09T10:36:15.486662400Z
INSERT INTO t_alter_column values('c_auction','Name','VARCHAR(60)',null,null)
;

-- 2023-08-09T10:36:22.549198900Z
INSERT INTO t_alter_column values('c_auction','C_Harvesting_Calendar_ID','NUMERIC(10)',null,null)
;

-- 2023-08-09T10:36:27.389173100Z
INSERT INTO t_alter_column values('c_auction','Harvesting_Year_ID','NUMERIC(10)',null,null)
;

-- Tab: Auction(541724,D) -> Auction(547115,D)
-- UI Section: main view
-- 2023-08-09T10:36:58.886069700Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547115,545710,TO_TIMESTAMP('2023-08-09 13:36:58.76','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-09 13:36:58.76','YYYY-MM-DD HH24:MI:SS.US'),100,'main view')
;

-- 2023-08-09T10:36:58.888070300Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545710 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Auction(541724,D) -> Auction(547115,D) -> main view
-- UI Column: 10
-- 2023-08-09T10:37:06.342019700Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546968,545710,TO_TIMESTAMP('2023-08-09 13:37:06.232','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-08-09 13:37:06.232','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Auction(541724,D) -> Auction(547115,D) -> main view
-- UI Column: 20
-- 2023-08-09T10:37:08.316107100Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546969,545710,TO_TIMESTAMP('2023-08-09 13:37:08.24','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-08-09 13:37:08.24','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Auction(541724,D) -> Auction(547115,D) -> main view -> 10
-- UI Element Group: main
-- 2023-08-09T10:37:20.719430300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546968,550961,TO_TIMESTAMP('2023-08-09 13:37:20.608','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-08-09 13:37:20.608','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auction(541724,D) -> Auction(547115,D) -> main view -> 10 -> main.Name
-- Column: C_Auction.Name
-- 2023-08-09T10:37:50.382036Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,718738,0,547115,619354,550961,'F',TO_TIMESTAMP('2023-08-09 13:37:50.238','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2023-08-09 13:37:50.238','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auction(541724,D) -> Auction(547115,D) -> main view -> 10 -> main.Datum
-- Column: C_Auction.Date
-- 2023-08-09T10:38:24.312376700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,718737,0,547115,619355,550961,'F',TO_TIMESTAMP('2023-08-09 13:38:24.189','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Datum',20,0,0,TO_TIMESTAMP('2023-08-09 13:38:24.189','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auction(541724,D) -> Auction(547115,D) -> main view -> 10 -> main.Erntekalender
-- Column: C_Auction.C_Harvesting_Calendar_ID
-- 2023-08-09T10:38:37.131760200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,718739,0,547115,619356,550961,'F',TO_TIMESTAMP('2023-08-09 13:38:36.997','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntekalender',30,0,0,TO_TIMESTAMP('2023-08-09 13:38:36.997','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auction(541724,D) -> Auction(547115,D) -> main view -> 10 -> main.Erntejahr
-- Column: C_Auction.Harvesting_Year_ID
-- 2023-08-09T10:38:44.975014900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,718740,0,547115,619357,550961,'F',TO_TIMESTAMP('2023-08-09 13:38:44.846','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntejahr',40,0,0,TO_TIMESTAMP('2023-08-09 13:38:44.846','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Auction(541724,D) -> Auction(547115,D) -> main view -> 20
-- UI Element Group: active
-- 2023-08-09T10:39:27.964171800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546969,550962,TO_TIMESTAMP('2023-08-09 13:39:27.822','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','active',10,TO_TIMESTAMP('2023-08-09 13:39:27.822','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auction(541724,D) -> Auction(547115,D) -> main view -> 20 -> active.Aktiv
-- Column: C_Auction.IsActive
-- 2023-08-09T10:39:38.074542900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,718735,0,547115,619358,550962,'F',TO_TIMESTAMP('2023-08-09 13:39:37.963','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-08-09 13:39:37.963','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Auction(541724,D) -> Auction(547115,D) -> main view -> 20
-- UI Element Group: Org und Lager
-- 2023-08-09T10:39:49.575809Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546969,550963,TO_TIMESTAMP('2023-08-09 13:39:49.452','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Org und Lager',20,TO_TIMESTAMP('2023-08-09 13:39:49.452','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auction(541724,D) -> Auction(547115,D) -> main view -> 20 -> Org und Lager.Organisation
-- Column: C_Auction.AD_Org_ID
-- 2023-08-09T10:40:03.067129900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,718734,0,547115,619359,550963,'F',TO_TIMESTAMP('2023-08-09 13:40:02.949','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-08-09 13:40:02.949','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auction(541724,D) -> Auction(547115,D) -> main view -> 20 -> Org und Lager.Mandant
-- Column: C_Auction.AD_Client_ID
-- 2023-08-09T10:40:08.881691Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,718733,0,547115,619360,550963,'F',TO_TIMESTAMP('2023-08-09 13:40:08.756','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-08-09 13:40:08.756','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: Auction
-- Action Type: W
-- Window: Auction(541724,D)
-- 2023-08-09T10:44:31.984905800Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582634,542101,0,541724,TO_TIMESTAMP('2023-08-09 13:44:31.845','YYYY-MM-DD HH24:MI:SS.US'),100,'D','C_Auction','Y','N','N','N','N','Auction',TO_TIMESTAMP('2023-08-09 13:44:31.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-09T10:44:31.987948900Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542101 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-08-09T10:44:31.990950500Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542101, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542101)
;

-- 2023-08-09T10:44:32.002934700Z
/* DDL */  select update_menu_translation_from_ad_element(582634) 
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2023-08-09T10:44:32.147303200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2023-08-09T10:44:32.149298700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2023-08-09T10:44:32.150299900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2023-08-09T10:44:32.150299900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2023-08-09T10:44:32.151301100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2023-08-09T10:44:32.151301100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2023-08-09T10:44:32.151301100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2023-08-09T10:44:32.152298500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2023-08-09T10:44:32.152298500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2023-08-09T10:44:32.152298500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2023-08-09T10:44:32.153300900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2023-08-09T10:44:32.153300900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2023-08-09T10:44:32.155301300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-08-09T10:44:32.155301300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2023-08-09T10:44:32.156327600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2023-08-09T10:44:32.156327600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-08-09T10:44:32.157302600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2023-08-09T10:44:32.157302600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-08-09T10:44:32.157302600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-08-09T10:44:32.158301500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2023-08-09T10:44:32.158301500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Auction`
-- 2023-08-09T10:44:32.159300200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542101 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `Auction`
-- 2023-08-09T10:45:59.785076200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542101 AND AD_Tree_ID=10
;

-- Node name: `CRM`
-- 2023-08-09T10:45:59.785676500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2023-08-09T10:45:59.786200800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2023-08-09T10:45:59.786200800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2023-08-09T10:45:59.787209200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2023-08-09T10:45:59.787209200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2023-08-09T10:45:59.787209200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2023-08-09T10:45:59.788209800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2023-08-09T10:45:59.788209800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2023-08-09T10:45:59.789208700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2023-08-09T10:45:59.789208700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2023-08-09T10:45:59.789208700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2023-08-09T10:45:59.790209600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2023-08-09T10:45:59.790209600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2023-08-09T10:45:59.791215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2023-08-09T10:45:59.791215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2023-08-09T10:45:59.792208600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2023-08-09T10:45:59.792208600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2023-08-09T10:45:59.792208600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2023-08-09T10:45:59.793209200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2023-08-09T10:45:59.793209200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2023-08-09T10:45:59.794214100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Node name: `Calendar`
-- 2023-08-09T10:45:59.794214100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541955 AND AD_Tree_ID=10
;

-- Reordering children of `Menu`
-- Node name: `Auction`
-- 2023-08-09T10:46:07.127867800Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542101 AND AD_Tree_ID=10
;

-- Node name: `webUI`
-- 2023-08-09T10:46:07.128867600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2023-08-09T10:46:07.128867600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- Node name: `Übersetzung`
-- 2023-08-09T10:46:07.130043300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- Node name: `Handling Units`
-- 2023-08-09T10:46:07.131025700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2023-08-09T10:46:07.131025700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- Node name: `System Admin`
-- 2023-08-09T10:46:07.132032500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2023-08-09T10:46:07.133031Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- Node name: `Partner Relations`
-- 2023-08-09T10:46:07.134026Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- Node name: `Quote-to-Invoice`
-- 2023-08-09T10:46:07.135026200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Management`
-- 2023-08-09T10:46:07.135026200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- Node name: `Requisition-to-Invoice`
-- 2023-08-09T10:46:07.136028600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- Node name: `DPD`
-- 2023-08-09T10:46:07.137023700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- Node name: `Materialsaldo`
-- 2023-08-09T10:46:07.138027300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- Node name: `Returns`
-- 2023-08-09T10:46:07.138027300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- Node name: `Open Items`
-- 2023-08-09T10:46:07.139026300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- Node name: `Material Management`
-- 2023-08-09T10:46:07.140026300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2023-08-09T10:46:07.140026300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- Node name: `Performance Analysis`
-- 2023-08-09T10:46:07.141025500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- Node name: `Assets`
-- 2023-08-09T10:46:07.142027100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- Node name: `Call Center`
-- 2023-08-09T10:46:07.142027100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- Node name: `Berichte`
-- 2023-08-09T10:46:07.143026100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- Node name: `Human Resource & Payroll`
-- 2023-08-09T10:46:07.144028600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- Node name: `EDI Definition (C_BP_EDI)`
-- 2023-08-09T10:46:07.144028600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- Node name: `EDI Transaction`
-- 2023-08-09T10:46:07.145177700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- Node name: `Berichte Materialwirtschaft`
-- 2023-08-09T10:46:07.146027Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen Verkauf`
-- 2023-08-09T10:46:07.146027Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- Node name: `Berichte Verkauf`
-- 2023-08-09T10:46:07.147030700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- Node name: `Berichte Geschäftspartner`
-- 2023-08-09T10:46:07.148031900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- Node name: `Cockpit`
-- 2023-08-09T10:46:07.149031500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- Node name: `Packstück (M_Package)`
-- 2023-08-09T10:46:07.150032900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- Node name: `Lieferanten Abrufauftrag (C_OrderLine)`
-- 2023-08-09T10:46:07.151031800Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (@PREFIX@de/metas/reports/kassenbuch/report.jasper)`
-- 2023-08-09T10:46:07.152033200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- Node name: `Nachlieferung (M_SubsequentDelivery_V)`
-- 2023-08-09T10:46:07.152033200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- Node name: `Verpackung (M_PackagingContainer)`
-- 2023-08-09T10:46:07.153031100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- Node name: `Abolieferplan aktualisieren (de.metas.contracts.subscription.process.C_SubscriptionProgress_Evaluate)`
-- 2023-08-09T10:46:07.154030700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- Node name: `Umsatz pro Kunde (@PREFIX@de/metas/reports/umsatzprokunde/report.jasper)`
-- 2023-08-09T10:46:07.155033700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- Node name: `Sponsoren Anlegen (de.metas.commission.process.CreateSponsors)`
-- 2023-08-09T10:46:07.156031600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- Node name: `Arbeitszeit (@PREFIX@de/metas/reports/arbeitszeit/report.jasper)`
-- 2023-08-09T10:46:07.156031600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- Node name: `Check Tree and Reset Sponsor Depths (de.metas.commission.process.CheckTreeResetDepths)`
-- 2023-08-09T10:46:07.157024700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- Node name: `Bankeinzug (C_DirectDebit)`
-- 2023-08-09T10:46:07.157024700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- Node name: `Spezial`
-- 2023-08-09T10:46:07.158028100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- Node name: `Belege`
-- 2023-08-09T10:46:07.159026400Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- Node name: `Steuer`
-- 2023-08-09T10:46:07.159026400Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- Node name: `Währung`
-- 2023-08-09T10:46:07.160032Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- Node name: `Hauptbuch`
-- 2023-08-09T10:46:07.161090200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- Node name: `Speditionsauftrag (@PREFIX@de/metas/docs/sales/shippingorder/report.jasper)`
-- 2023-08-09T10:46:07.162026900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- Node name: `Steueranmeldung (@PREFIX@de/metas/reports/taxregistration/report.jasper)`
-- 2023-08-09T10:46:07.163025700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- Node name: `Wiederkehrende Zahlungen (C_RecurrentPayment)`
-- 2023-08-09T10:46:07.163025700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- Node name: `Verkaufte Artikel (@PREFIX@de/metas/reports/soldproducts/report.jasper)`
-- 2023-08-09T10:46:07.164025500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- Node name: `Versand (@PREFIX@de/metas/reports/versand/report.jasper)`
-- 2023-08-09T10:46:07.165026300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- Node name: `Provision_LEGACY`
-- 2023-08-09T10:46:07.165026300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- Node name: `Vertriebspartnerpunkte (@PREFIX@de/metas/reports/vertriebspartnerpunktzahl/report.jasper)`
-- 2023-08-09T10:46:07.166025600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- Node name: `C_BPartner Convert Memo (de.metas.adempiere.process.ConvertBPartnerMemo)`
-- 2023-08-09T10:46:07.166025600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- Node name: `Ladeliste (Jasper) (@PREFIX@de/metas/docs/sales/shippingorder/report.jasper)`
-- 2023-08-09T10:46:07.167028500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- Node name: `Wiederkenhrende Zahlungs-Rechnungen erzeugen (de.metas.banking.process.C_RecurrentPaymentCreateInvoice)`
-- 2023-08-09T10:46:07.168027200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- Node name: `Daten-Bereinigung (de.metas.adempiere.process.SweepTable)`
-- 2023-08-09T10:46:07.169026300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- Node name: `Geschäftspartner importieren (org.compiere.process.ImportBPartner)`
-- 2023-08-09T10:46:07.170025Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- Node name: `Update C_BPartner.IsSalesRep (de.metas.process.ExecuteUpdateSQL)`
-- 2023-08-09T10:46:07.170025Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- Node name: `E/A`
-- 2023-08-09T10:46:07.171025800Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- Node name: `Sponsor-Statistik aktualisieren (de.metas.commission.process.UpdateSponsorStats)`
-- 2023-08-09T10:46:07.172026100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- Node name: `Downline Navigator (de.metas.commision.form.zk.WSponsorBrowse)`
-- 2023-08-09T10:46:07.172026100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- Node name: `B2B Adressen und Bankverbindung ändern (de.metas.commision.form.zk.WB2BAddressAccount)`
-- 2023-08-09T10:46:07.173024800Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- Node name: `B2B Auftrag erfassen (de.metas.commision.form.zk.WB2BOrder)`
-- 2023-08-09T10:46:07.173024800Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- Node name: `UserAccountLock`
-- 2023-08-09T10:46:07.174025500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- Node name: `B2B Bestellübersicht (de.metas.commision.form.zk.WB2BOrderHistory)`
-- 2023-08-09T10:46:07.175027400Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- Node name: `VP-Ränge (@PREFIX@de/metas/reports/vertriebspartnerraenge/report.jasper)`
-- 2023-08-09T10:46:07.175027400Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- Node name: `User lock expire (de.metas.user.process.AD_User_ExpireLocks)`
-- 2023-08-09T10:46:07.176025600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- Node name: `Orders Overview (de.metas.adempiere.form.swing.OrderOverview)`
-- 2023-08-09T10:46:07.176025600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- Node name: `Kommissionier Terminal (de.metas.picking.terminal.form.swing.PickingTerminal)`
-- 2023-08-09T10:46:07.177224600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- Node name: `Tour (M_Tour)`
-- 2023-08-09T10:46:07.177224600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- Node name: `UI Trigger (AD_TriggerUI)`
-- 2023-08-09T10:46:07.178157800Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- Node name: `Auftragskandidaten verarbeiten (de.metas.ordercandidate.process.C_OLCandEnqueueForSalesOrderCreation)`
-- 2023-08-09T10:46:07.179159100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- Node name: `ESR Zahlungsimport (ESR_Import)`
-- 2023-08-09T10:46:07.179159100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- Node name: `Liefertage (M_DeliveryDay)`
-- 2023-08-09T10:46:07.180158500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- Node name: `Create product costs (de.metas.adempiere.process.CreateProductCosts)`
-- 2023-08-09T10:46:07.181160900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- Node name: `Document Management`
-- 2023-08-09T10:46:07.181160900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- Node name: `Update Addresses (de.metas.adempiere.process.UpdateAddresses)`
-- 2023-08-09T10:46:07.182159200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- Node name: `Massendruck`
-- 2023-08-09T10:46:07.182159200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- Node name: `Abrechnung MwSt.-Korrektur (C_Invoice_VAT_Corr_Candidates_v1)`
-- 2023-08-09T10:46:07.183160100Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot (M_PickingSlot)`
-- 2023-08-09T10:46:07.184160500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-08-09T10:46:07.184160500Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- Node name: `Picking Vorbereitung Liste (Jasper) (@PREFIX@de/metas/reports/pickingpreparation/report.jasper)`
-- 2023-08-09T10:46:07.185158600Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- Node name: `Parzelle (C_Allotment)`
-- 2023-08-09T10:46:07.186160300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- Node name: `Export Format (EXP_Format)`
-- 2023-08-09T10:46:07.186160300Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- Node name: `Transportdisposition (M_Tour_Instance)`
-- 2023-08-09T10:46:07.187156900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- Node name: `Belegzeile-Sortierung (C_DocLine_Sort)`
-- 2023-08-09T10:46:07.187156900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- Node name: `Zählbestand Einkauf (fresh) (Fresh_QtyOnHand)`
-- 2023-08-09T10:46:07.188159200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2023-08-09T10:46:07.188159200Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2023-08-09T10:46:07.189160700Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- Node name: `Transparenz zur Status ESR Import in Bankauszug (x_esr_import_in_c_bankstatement_v)`
-- 2023-08-09T10:46:07.190157900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- Node name: `Offene Zahlung - Skonto Zuordnung (de.metas.payment.process.C_Payment_MassWriteOff)`
-- 2023-08-09T10:46:07.190157900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- Node name: `Gebinde`
-- 2023-08-09T10:46:07.191158400Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- Node name: `Parzelle`
-- 2023-08-09T10:46:07.192227Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- Node name: `AD_Table_CreateFromInputFile (org.adempiere.ad.table.process.AD_Table_CreateFromInputFile)`
-- 2023-08-09T10:46:07.192227Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- Node name: `Shipment restrictions (M_Shipment_Constraint)`
-- 2023-08-09T10:46:07.193159400Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- Node name: `Board Configuration (WEBUI_Board)`
-- 2023-08-09T10:46:07.194166900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- Node name: `Test facility group (S_HumanResourceTestGroup)`
-- 2023-08-09T10:46:07.194166900Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542082 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2023-08-09T10:46:07.195158400Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- Node name: `Create Membership Contracts (de.metas.contracts.order.process.C_Order_CreateForAllMembers)`
-- 2023-08-09T10:46:07.196696800Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541832 AND AD_Tree_ID=10
;

-- Reordering children of `Sales`
-- Node name: `Auction`
-- 2023-08-09T10:47:06.485080400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542101 AND AD_Tree_ID=10
;

-- Node name: `CreditPass configuration (CS_Creditpass_Config)`
-- 2023-08-09T10:47:06.485080400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- Node name: `Sales Order (C_Order)`
-- 2023-08-09T10:47:06.486045500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- Node name: `Alberta Prescription Request (Alberta_PrescriptionRequest)`
-- 2023-08-09T10:47:06.486045500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- Node name: `Sales Order Disposition (C_OLCand)`
-- 2023-08-09T10:47:06.487022300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- Node name: `Order Control (C_Order_MFGWarehouse_Report)`
-- 2023-08-09T10:47:06.487022300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- Node name: `Sales Opportunity Board (Prototype)`
-- 2023-08-09T10:47:06.488053300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Type (C_CreditLimit_Type)`
-- 2023-08-09T10:47:06.488053300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-08-09T10:47:06.489045900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-08-09T10:47:06.489045900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-08-09T10:47:06.489045900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- Node name: `CreditPass transaction results (CS_Transaction_Result)`
-- 2023-08-09T10:47:06.490045800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- Node name: `Commission`
-- 2023-08-09T10:47:06.490045800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- Node name: `Incoterm (C_Incoterms)`
-- 2023-08-09T10:47:06.491054500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541784 AND AD_Tree_ID=10
;

-- Node name: `Available for sales (MD_Available_For_Sales)`
-- 2023-08-09T10:47:06.491054500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541962 AND AD_Tree_ID=10
;

-- Element: C_Auction_ID
-- 2023-08-09T11:27:47.690219Z
UPDATE AD_Element_Trl SET Name='Auktion', PrintName='Auktion',Updated=TO_TIMESTAMP('2023-08-09 14:27:47.69','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582634 AND AD_Language='de_CH'
;

-- 2023-08-09T11:27:47.701823400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582634,'de_CH') 
;

-- Element: C_Auction_ID
-- 2023-08-09T11:27:55.798797300Z
UPDATE AD_Element_Trl SET Name='Auktion', PrintName='Auktion',Updated=TO_TIMESTAMP('2023-08-09 14:27:55.797','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582634 AND AD_Language='de_DE'
;

-- 2023-08-09T11:27:55.799794700Z
UPDATE AD_Element SET Name='Auktion', PrintName='Auktion' WHERE AD_Element_ID=582634
;

-- 2023-08-09T11:27:57.205320800Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582634,'de_DE') 
;

-- 2023-08-09T11:27:57.215619200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582634,'de_DE') 
;

-- Element: C_Auction_ID
-- 2023-08-09T11:28:01.183453500Z
UPDATE AD_Element_Trl SET Name='Auktion', PrintName='Auktion',Updated=TO_TIMESTAMP('2023-08-09 14:28:01.183','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582634 AND AD_Language='fr_CH'
;

-- 2023-08-09T11:28:01.186166900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582634,'fr_CH') 
;

-- Element: C_Auction_ID
-- 2023-08-09T11:28:03.646438800Z
UPDATE AD_Element_Trl SET Name='Auktion', PrintName='Auktion',Updated=TO_TIMESTAMP('2023-08-09 14:28:03.646','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582634 AND AD_Language='it_IT'
;

-- 2023-08-09T11:28:03.648442Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582634,'it_IT') 
;

-- Column: C_Order.C_Auction_ID
-- 2023-08-09T13:45:18.712540800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587270,582634,0,19,259,'C_Auction_ID',TO_TIMESTAMP('2023-08-09 16:45:18.488','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auktion',0,0,TO_TIMESTAMP('2023-08-09 16:45:18.488','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-09T13:45:18.722884300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587270 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-09T13:45:19.410415500Z
/* DDL */  select update_Column_Translation_From_AD_Element(582634) 
;

-- 2023-08-09T13:45:21.676034Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_Auction_ID NUMERIC(10)')
;

-- 2023-08-09T13:45:23.494613300Z
ALTER TABLE C_Order ADD CONSTRAINT CAuction_COrder FOREIGN KEY (C_Auction_ID) REFERENCES public.C_Auction DEFERRABLE INITIALLY DEFERRED
;

-- 2023-08-09T13:46:41.088709900Z
INSERT INTO t_alter_column values('c_order','C_Auction_ID','NUMERIC(10)',null,null)
;

