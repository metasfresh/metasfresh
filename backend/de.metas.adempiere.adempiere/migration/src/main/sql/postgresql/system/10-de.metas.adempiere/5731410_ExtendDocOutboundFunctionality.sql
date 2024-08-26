-- Table: C_Doc_Outbound_Config_CC
-- 2024-08-23T12:31:52.399Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542430,540173,'N',TO_TIMESTAMP('2024-08-23 15:31:52.167','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','Y','Y','N','Y','N','N','N',0,'Ausgehende Belege Konfig CC','NP','L','C_Doc_Outbound_Config_CC','DTI',TO_TIMESTAMP('2024-08-23 15:31:52.167','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T12:31:52.412Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542430 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-08-23T12:31:52.568Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556362,TO_TIMESTAMP('2024-08-23 15:31:52.476','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_Doc_Outbound_Config_CC',1,'Y','N','Y','Y','C_Doc_Outbound_Config_CC','N',1000000,TO_TIMESTAMP('2024-08-23 15:31:52.476','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T12:31:52.581Z
CREATE SEQUENCE C_DOC_OUTBOUND_CONFIG_CC_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Doc_Outbound_Config_CC.AD_Client_ID
-- 2024-08-23T12:32:06.853Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588914,102,0,19,542430,129,'AD_Client_ID',TO_TIMESTAMP('2024-08-23 15:32:06.721','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-08-23 15:32:06.721','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:06.860Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588914 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:06.914Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: C_Doc_Outbound_Config_CC.AD_Org_ID
-- 2024-08-23T12:32:08.744Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588915,113,0,30,542430,'AD_Org_ID',TO_TIMESTAMP('2024-08-23 15:32:08.463','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Organisation',0,0,TO_TIMESTAMP('2024-08-23 15:32:08.463','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:08.754Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588915 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:08.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: C_Doc_Outbound_Config_CC.AD_PrintFormat_ID
-- 2024-08-23T12:32:09.330Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588916,1790,0,30,542430,'AD_PrintFormat_ID',TO_TIMESTAMP('2024-08-23 15:32:09.072','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Data Print Format','D',0,10,'E','Das Druckformat legt fest, wie die Daten für den Druck aufbereitet werden.','Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','Druck - Format',20,0,TO_TIMESTAMP('2024-08-23 15:32:09.072','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:09.332Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588916 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:09.334Z
/* DDL */  select update_Column_Translation_From_AD_Element(1790)
;

-- Column: C_Doc_Outbound_Config_CC.AD_Table_ID
-- 2024-08-23T12:32:09.803Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588917,126,0,30,542430,'AD_Table_ID',TO_TIMESTAMP('2024-08-23 15:32:09.58','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Database Table information','D',0,10,'E','The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','DB-Tabelle',10,10,TO_TIMESTAMP('2024-08-23 15:32:09.58','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:09.805Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588917 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:09.808Z
/* DDL */  select update_Column_Translation_From_AD_Element(126)
;

-- Column: C_Doc_Outbound_Config_CC.CCPath
-- 2024-08-23T12:32:10.240Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588918,542348,0,10,542430,'CCPath',TO_TIMESTAMP('2024-08-23 15:32:10.062','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','CC Pfad',0,0,TO_TIMESTAMP('2024-08-23 15:32:10.062','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:10.242Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588918 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:10.245Z
/* DDL */  select update_Column_Translation_From_AD_Element(542348)
;

-- 2024-08-23T12:32:10.560Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583227,0,'C_Doc_Outbound_Config_CC_ID',TO_TIMESTAMP('2024-08-23 15:32:10.484','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Ausgehende Belege Konfig CC','Ausgehende Belege Konfig CC',TO_TIMESTAMP('2024-08-23 15:32:10.484','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T12:32:10.563Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583227 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Doc_Outbound_Config_CC.C_Doc_Outbound_Config_CC_ID
-- 2024-08-23T12:32:10.946Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588919,583227,0,13,542430,'C_Doc_Outbound_Config_CC_ID',TO_TIMESTAMP('2024-08-23 15:32:10.481','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Ausgehende Belege Konfig CC',0,0,TO_TIMESTAMP('2024-08-23 15:32:10.481','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:10.948Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588919 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:10.951Z
/* DDL */  select update_Column_Translation_From_AD_Element(583227)
;

-- Column: C_Doc_Outbound_Config_CC.Created
-- 2024-08-23T12:32:11.572Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588920,245,0,16,542430,'Created',TO_TIMESTAMP('2024-08-23 15:32:11.382','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-08-23 15:32:11.382','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:11.574Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588920 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:11.577Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: C_Doc_Outbound_Config_CC.CreatedBy
-- 2024-08-23T12:32:12.203Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588921,246,0,18,110,542430,'CreatedBy',TO_TIMESTAMP('2024-08-23 15:32:12.009','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-08-23 15:32:12.009','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:12.205Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588921 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:12.207Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: C_Doc_Outbound_Config_CC.DocBaseType
-- 2024-08-23T12:32:12.834Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588922,865,0,17,183,542430,'DocBaseType',TO_TIMESTAMP('2024-08-23 15:32:12.575','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,3,'E','','Y','Y','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','Dokument Basis Typ',30,20,TO_TIMESTAMP('2024-08-23 15:32:12.575','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:12.835Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588922 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:12.838Z
/* DDL */  select update_Column_Translation_From_AD_Element(865)
;

-- Column: C_Doc_Outbound_Config_CC.IsActive
-- 2024-08-23T12:32:13.294Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588923,348,0,20,542430,'IsActive',TO_TIMESTAMP('2024-08-23 15:32:13.096','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-08-23 15:32:13.096','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:13.296Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588923 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:13.299Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: C_Doc_Outbound_Config_CC.IsDirectEnqueue
-- 2024-08-23T12:32:13.829Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588924,542117,0,20,542430,'IsDirectEnqueue',TO_TIMESTAMP('2024-08-23 15:32:13.647','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Y','Entscheidet, ob beim erstellen des Druckstücks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','In Druck-Warteschlange',0,0,TO_TIMESTAMP('2024-08-23 15:32:13.647','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:13.831Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588924 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:13.833Z
/* DDL */  select update_Column_Translation_From_AD_Element(542117)
;

-- Column: C_Doc_Outbound_Config_CC.IsDirectProcessQueueItem
-- 2024-08-23T12:32:14.291Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588925,542655,0,20,542430,'IsDirectProcessQueueItem',TO_TIMESTAMP('2024-08-23 15:32:14.096','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Entscheidet, ob je nach Konfiguration sofort ein Druckjob erstellt oder das PDF lokal gespeichert wird.','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Warteschlangen-Eintrag sofort verarbeiten',0,0,TO_TIMESTAMP('2024-08-23 15:32:14.096','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:14.293Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588925 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:14.296Z
/* DDL */  select update_Column_Translation_From_AD_Element(542655)
;

-- Column: C_Doc_Outbound_Config_CC.Updated
-- 2024-08-23T12:32:14.735Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588926,607,0,16,542430,'Updated',TO_TIMESTAMP('2024-08-23 15:32:14.553','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-08-23 15:32:14.553','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:14.739Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588926 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:14.745Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: C_Doc_Outbound_Config_CC.UpdatedBy
-- 2024-08-23T12:32:15.291Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588927,608,0,18,110,542430,'UpdatedBy',TO_TIMESTAMP('2024-08-23 15:32:15.094','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-08-23 15:32:15.094','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T12:32:15.293Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588927 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T12:32:15.295Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- Column: C_Doc_Outbound_Config_CC.CCPath
-- 2024-08-23T12:32:30.649Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588918
;

-- 2024-08-23T12:32:30.660Z
DELETE FROM AD_Column WHERE AD_Column_ID=588918
;

-- Column: C_Doc_Outbound_Config_CC.AD_PrintFormat_ID
-- 2024-08-23T12:33:00.162Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-23 15:33:00.162','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588916
;

-- Column: C_Doc_Outbound_Config_CC.C_Doc_Outbound_Config_ID
-- 2024-08-23T12:34:39.265Z
UPDATE AD_Column SET AD_Element_ID=541926, AD_Reference_ID=19, ColumnName='C_Doc_Outbound_Config_ID', Description=NULL, EntityType='de.metas.document.archive', Help=NULL, IsExcludeFromZoomTargets='N', Name='Ausgehende Belege Konfig',Updated=TO_TIMESTAMP('2024-08-23 15:34:39.265','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588917
;

-- 2024-08-23T12:34:39.269Z
UPDATE AD_Column_Trl trl SET Name='Ausgehende Belege Konfig' WHERE AD_Column_ID=588917 AND AD_Language='de_DE'
;

-- 2024-08-23T12:34:39.269Z
UPDATE AD_Field SET Name='Ausgehende Belege Konfig', Description=NULL, Help=NULL WHERE AD_Column_ID=588917
;

-- 2024-08-23T12:34:39.272Z
/* DDL */  select update_Column_Translation_From_AD_Element(541926)
;

-- Column: C_Doc_Outbound_Config_CC.ColumnName
-- 2024-08-23T12:36:18.860Z
UPDATE AD_Column SET AD_Element_ID=228, AD_Reference_ID=10, AD_Reference_Value_ID=NULL, ColumnName='ColumnName', Description='Name der Spalte in der Datenbank', FieldLength=60, Help='"Spaltenname" bezeichnet den Namen einer Spalte einer Tabelle wie in der Datenbank definiert.', IsMandatory='Y', Name='Spaltenname',Updated=TO_TIMESTAMP('2024-08-23 15:36:18.86','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588922
;

-- 2024-08-23T12:36:18.862Z
UPDATE AD_Column_Trl trl SET Name='Spaltenname' WHERE AD_Column_ID=588922 AND AD_Language='de_DE'
;

-- 2024-08-23T12:36:18.863Z
UPDATE AD_Field SET Name='Spaltenname', Description='Name der Spalte in der Datenbank', Help='"Spaltenname" bezeichnet den Namen einer Spalte einer Tabelle wie in der Datenbank definiert.' WHERE AD_Column_ID=588922
;

-- 2024-08-23T12:36:18.865Z
/* DDL */  select update_Column_Translation_From_AD_Element(228)
;

-- Column: C_Doc_Outbound_Config_CC.IsDirectEnqueue
-- 2024-08-23T12:36:54.685Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588924
;

-- 2024-08-23T12:36:54.692Z
DELETE FROM AD_Column WHERE AD_Column_ID=588924
;

-- Column: C_Doc_Outbound_Config_CC.IsDirectProcessQueueItem
-- 2024-08-23T12:36:58.101Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588925
;

-- 2024-08-23T12:36:58.110Z
DELETE FROM AD_Column WHERE AD_Column_ID=588925
;

-- 2024-08-23T12:37:42.560Z
/* DDL */ CREATE TABLE public.C_Doc_Outbound_Config_CC (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_PrintFormat_ID NUMERIC(10) NOT NULL, C_Doc_Outbound_Config_CC_ID NUMERIC(10) NOT NULL, C_Doc_Outbound_Config_ID NUMERIC(10) NOT NULL, ColumnName VARCHAR(60) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADPrintFormat_CDocOutboundConfigCC FOREIGN KEY (AD_PrintFormat_ID) REFERENCES public.AD_PrintFormat DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Doc_Outbound_Config_CC_Key PRIMARY KEY (C_Doc_Outbound_Config_CC_ID), CONSTRAINT CDocOutboundConfig_CDocOutboundConfigCC FOREIGN KEY (C_Doc_Outbound_Config_ID) REFERENCES public.C_Doc_Outbound_Config DEFERRABLE INITIALLY DEFERRED)
;

-- Table: C_Doc_Outbound_Config_CC
-- 2024-08-23T12:38:38.296Z
UPDATE AD_Table SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:38:38.294','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542430
;

-- Column: C_Doc_Outbound_Config_CC.Updated
-- 2024-08-23T12:38:50.502Z
UPDATE AD_Column SET EntityType='de.metas.document.archive', IsAllowLogging='Y',Updated=TO_TIMESTAMP('2024-08-23 15:38:50.502','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588926
;

-- Column: C_Doc_Outbound_Config_CC.IsActive
-- 2024-08-23T12:38:54.399Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:38:54.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588923
;

-- Column: C_Doc_Outbound_Config_CC.AD_Client_ID
-- 2024-08-23T12:38:59.799Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:38:59.799','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588914
;

-- Column: C_Doc_Outbound_Config_CC.AD_Org_ID
-- 2024-08-23T12:39:02.998Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:39:02.998','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588915
;

-- Column: C_Doc_Outbound_Config_CC.AD_PrintFormat_ID
-- 2024-08-23T12:39:05.360Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:39:05.36','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588916
;

-- Column: C_Doc_Outbound_Config_CC.C_Doc_Outbound_Config_CC_ID
-- 2024-08-23T12:39:08.428Z
UPDATE AD_Column SET EntityType='de.metas.document.archive', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-08-23 15:39:08.428','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588919
;

-- Column: C_Doc_Outbound_Config_CC.ColumnName
-- 2024-08-23T12:39:24.967Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:39:24.967','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588922
;

-- Column: C_Doc_Outbound_Config_CC.Created
-- 2024-08-23T12:39:27.711Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:39:27.711','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588920
;

-- Column: C_Doc_Outbound_Config_CC.CreatedBy
-- 2024-08-23T12:39:30.437Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:39:30.436','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588921
;

-- Column: C_Doc_Outbound_Config_CC.UpdatedBy
-- 2024-08-23T12:39:36.684Z
UPDATE AD_Column SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:39:36.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588927
;

-- 2024-08-23T12:49:59.450Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583228,0,'BPartner_ColumnName',TO_TIMESTAMP('2024-08-23 15:49:59.262','YYYY-MM-DD HH24:MI:SS.US'),100,'Spaltenname des Partners','U','This will be a list with the column based on the table that is set in C_Doc_Outbound_Config.
If in C_Doc_Outbound_Config we have set table C_Order, then in C_Doc_Outbound_Config_CC.BPartner_ColumnName we shall be able to see the fields from C_Order, like: C_Order.C_BPartner_ID, C_Order.BillPartner_ID, etc','Y','Spaltenname','Spaltenname',TO_TIMESTAMP('2024-08-23 15:49:59.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T12:49:59.453Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583228 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: BPartner_ColumnName
-- 2024-08-23T12:50:30.316Z
UPDATE AD_Element_Trl SET Description='BPartner Column name', Name='BPartner Column name', PrintName='BPartner Column name',Updated=TO_TIMESTAMP('2024-08-23 15:50:30.316','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583228 AND AD_Language='en_US'
;

-- 2024-08-23T12:50:30.321Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583228,'en_US')
;

-- Element: BPartner_ColumnName
-- 2024-08-23T12:50:32.878Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-23 15:50:32.878','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583228 AND AD_Language='en_US'
;

-- 2024-08-23T12:50:32.881Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583228,'en_US')
;

-- Element: BPartner_ColumnName
-- 2024-08-23T12:50:48.991Z
UPDATE AD_Element_Trl SET Help='Dies ist eine Liste mit der Spalte basierend auf der Tabelle, die in C_Doc_Outbound_Config festgelegt ist.
Wenn wir in C_Doc_Outbound_Config die Tabelle C_Order festgelegt haben, können wir in C_Doc_Outbound_Config_CC.BPartner_ColumnName die Felder aus C_Order sehen, wie: C_Order.C_BPartner_ID, C_Order.BillPartner_ID usw.', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-23 15:50:48.991','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583228 AND AD_Language='de_DE'
;

-- 2024-08-23T12:50:48.993Z
UPDATE AD_Element SET Help='Dies ist eine Liste mit der Spalte basierend auf der Tabelle, die in C_Doc_Outbound_Config festgelegt ist.
Wenn wir in C_Doc_Outbound_Config die Tabelle C_Order festgelegt haben, können wir in C_Doc_Outbound_Config_CC.BPartner_ColumnName die Felder aus C_Order sehen, wie: C_Order.C_BPartner_ID, C_Order.BillPartner_ID usw.' WHERE AD_Element_ID=583228
;

-- 2024-08-23T12:50:49.211Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583228,'de_DE')
;

-- 2024-08-23T12:50:49.213Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583228,'de_DE')
;

-- Element: BPartner_ColumnName
-- 2024-08-23T12:50:53.943Z
UPDATE AD_Element_Trl SET Help='Dies ist eine Liste mit der Spalte basierend auf der Tabelle, die in C_Doc_Outbound_Config festgelegt ist.
Wenn wir in C_Doc_Outbound_Config die Tabelle C_Order festgelegt haben, können wir in C_Doc_Outbound_Config_CC.BPartner_ColumnName die Felder aus C_Order sehen, wie: C_Order.C_BPartner_ID, C_Order.BillPartner_ID usw.', IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-23 15:50:53.943','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583228 AND AD_Language='de_CH'
;

-- 2024-08-23T12:50:53.946Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583228,'de_CH')
;

-- 2024-08-23T12:51:22.438Z
UPDATE AD_Element SET EntityType='de.metas.document.archive',Updated=TO_TIMESTAMP('2024-08-23 15:51:22.437','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583228
;

-- 2024-08-23T12:51:22.441Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583228,'de_DE')
;

-- Column: C_Doc_Outbound_Config_CC.BPartner_ColumnName
-- 2024-08-23T12:51:32.180Z
UPDATE AD_Column SET AD_Element_ID=583228, ColumnName='BPartner_ColumnName', Description='Spaltenname des Partners', Help='Dies ist eine Liste mit der Spalte basierend auf der Tabelle, die in C_Doc_Outbound_Config festgelegt ist.
Wenn wir in C_Doc_Outbound_Config die Tabelle C_Order festgelegt haben, können wir in C_Doc_Outbound_Config_CC.BPartner_ColumnName die Felder aus C_Order sehen, wie: C_Order.C_BPartner_ID, C_Order.BillPartner_ID usw.', Name='Spaltenname',Updated=TO_TIMESTAMP('2024-08-23 15:51:32.18','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588922
;

-- 2024-08-23T12:51:32.182Z
UPDATE AD_Field SET Name='Spaltenname', Description='Spaltenname des Partners', Help='Dies ist eine Liste mit der Spalte basierend auf der Tabelle, die in C_Doc_Outbound_Config festgelegt ist.
Wenn wir in C_Doc_Outbound_Config die Tabelle C_Order festgelegt haben, können wir in C_Doc_Outbound_Config_CC.BPartner_ColumnName die Felder aus C_Order sehen, wie: C_Order.C_BPartner_ID, C_Order.BillPartner_ID usw.' WHERE AD_Column_ID=588922
;

-- 2024-08-23T12:51:32.183Z
/* DDL */  select update_Column_Translation_From_AD_Element(583228)
;

-- 2024-08-23T12:51:33.612Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Config_CC','ALTER TABLE public.C_Doc_Outbound_Config_CC ADD COLUMN BPartner_ColumnName VARCHAR(60) NOT NULL')
;

ALTER TABLE C_Doc_Outbound_Config_CC DROP COLUMN ColumnName;

-- Name: BPartner_ColumnName
-- 2024-08-23T13:26:32.367Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541882,TO_TIMESTAMP('2024-08-23 16:26:32.217','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.document.archive','Y','N','BPartner_ColumnName',TO_TIMESTAMP('2024-08-23 16:26:32.217','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2024-08-23T13:26:32.369Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541882 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: BPartner_ColumnName
-- 2024-08-23T13:57:12.702Z
UPDATE AD_Reference SET ValidationType='L',Updated=TO_TIMESTAMP('2024-08-23 16:57:12.7','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541882
;

-- Column: C_Doc_Outbound_Config_CC.BPartner_ColumnName
-- 2024-08-23T13:57:19.191Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541882,Updated=TO_TIMESTAMP('2024-08-23 16:57:19.191','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588922
;

-- Name: BPartner_ColumnName
-- 2024-08-23T13:57:37.016Z
UPDATE AD_Reference SET ValidationType='T',Updated=TO_TIMESTAMP('2024-08-23 16:57:37.013','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541882
;

-- 2024-08-23T14:02:19.281Z
UPDATE AD_Element SET ColumnName='BPartner_ColumnName_ID',Updated=TO_TIMESTAMP('2024-08-23 17:02:19.281','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583228
;

-- 2024-08-23T14:02:19.283Z
UPDATE AD_Column SET ColumnName='BPartner_ColumnName_ID' WHERE AD_Element_ID=583228
;

-- 2024-08-23T14:02:19.284Z
UPDATE AD_Process_Para SET ColumnName='BPartner_ColumnName_ID' WHERE AD_Element_ID=583228
;

-- 2024-08-23T14:02:19.287Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583228,'de_DE')
;

-- Column: C_Doc_Outbound_Config_CC.BPartner_ColumnName_ID
-- 2024-08-23T14:02:34.704Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540748,Updated=TO_TIMESTAMP('2024-08-23 17:02:34.704','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588922
;

-- Name: BPartner_ColumnName
-- 2024-08-23T14:02:38.982Z
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541882
;

-- 2024-08-23T14:02:38.986Z
DELETE FROM AD_Reference WHERE AD_Reference_ID=541882
;

-- Name: BPartner_ColumnName_by_AD_Table_ID
-- 2024-08-23T14:04:21.515Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540687,'AD_Column.AD_Table_ID=@AD_Table_ID/-1@ AND ColumName ilike ''%partner%''',TO_TIMESTAMP('2024-08-23 17:04:21.368','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.document.archive','Y','BPartner_ColumnName_by_AD_Table_ID','S',TO_TIMESTAMP('2024-08-23 17:04:21.368','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: BPartner_ColumnName_by_AD_Table_ID
-- 2024-08-23T14:04:31.419Z
UPDATE AD_Val_Rule SET Code='AD_Column.AD_Table_ID=@AD_Table_ID@ AND ColumName ilike ''%partner%''',Updated=TO_TIMESTAMP('2024-08-23 17:04:31.417','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540687
;

-- Column: C_Doc_Outbound_Config_CC.BPartner_ColumnName_ID
-- 2024-08-23T14:04:42.822Z
UPDATE AD_Column SET AD_Val_Rule_ID=540687,Updated=TO_TIMESTAMP('2024-08-23 17:04:42.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588922
;

-- 2024-08-23T14:04:47.305Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Config_CC','ALTER TABLE public.C_Doc_Outbound_Config_CC ADD COLUMN BPartner_ColumnName_ID NUMERIC(10) NOT NULL')
;

-- 2024-08-23T14:04:47.314Z
ALTER TABLE C_Doc_Outbound_Config_CC ADD CONSTRAINT BPartnerColumnName_CDocOutboundConfigCC FOREIGN KEY (BPartner_ColumnName_ID) REFERENCES public.AD_Column DEFERRABLE INITIALLY DEFERRED
;

ALTER TABLE C_Doc_Outbound_Config_CC DROP COLUMN BPartner_ColumnName;

-- Tab: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC
-- Table: C_Doc_Outbound_Config_CC
-- 2024-08-23T14:08:11.815Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583227,0,547571,542430,540173,'Y',TO_TIMESTAMP('2024-08-23 17:08:11.681','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.document.archive','N','N','A','C_Doc_Outbound_Config_CC','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Ausgehende Belege Konfig CC','N',20,0,TO_TIMESTAMP('2024-08-23 17:08:11.681','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:11.819Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547571 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-08-23T14:08:11.823Z
/* DDL */  select update_tab_translation_from_ad_element(583227)
;

-- 2024-08-23T14:08:11.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547571)
;

-- Tab: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC
-- Table: C_Doc_Outbound_Config_CC
-- 2024-08-23T14:08:23.286Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2024-08-23 17:08:23.286','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547571
;

-- Tab: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC
-- Table: C_Doc_Outbound_Config_CC
-- 2024-08-23T14:08:37.723Z
UPDATE AD_Tab SET Parent_Column_ID=548040,Updated=TO_TIMESTAMP('2024-08-23 17:08:37.723','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547571
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Mandant
-- Column: C_Doc_Outbound_Config_CC.AD_Client_ID
-- 2024-08-23T14:08:49.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588914,729833,0,547571,TO_TIMESTAMP('2024-08-23 17:08:49.175','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.document.archive','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-08-23 17:08:49.175','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:49.319Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-23T14:08:49.321Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-08-23T14:08:50.086Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729833
;

-- 2024-08-23T14:08:50.088Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729833)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Organisation
-- Column: C_Doc_Outbound_Config_CC.AD_Org_ID
-- 2024-08-23T14:08:50.181Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588915,729834,0,547571,TO_TIMESTAMP('2024-08-23 17:08:50.092','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.document.archive','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-08-23 17:08:50.092','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:50.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-23T14:08:50.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-08-23T14:08:50.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729834
;

-- 2024-08-23T14:08:50.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729834)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Druck - Format
-- Column: C_Doc_Outbound_Config_CC.AD_PrintFormat_ID
-- 2024-08-23T14:08:50.528Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588916,729835,0,547571,TO_TIMESTAMP('2024-08-23 17:08:50.435','YYYY-MM-DD HH24:MI:SS.US'),100,'Data Print Format',10,'de.metas.document.archive','Das Druckformat legt fest, wie die Daten für den Druck aufbereitet werden.','Y','N','N','N','N','N','N','N','Druck - Format',TO_TIMESTAMP('2024-08-23 17:08:50.435','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:50.530Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-23T14:08:50.531Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1790)
;

-- 2024-08-23T14:08:50.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729835
;

-- 2024-08-23T14:08:50.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729835)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Ausgehende Belege Konfig
-- Column: C_Doc_Outbound_Config_CC.C_Doc_Outbound_Config_ID
-- 2024-08-23T14:08:50.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588917,729836,0,547571,TO_TIMESTAMP('2024-08-23 17:08:50.542','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.document.archive','Y','N','N','N','N','N','N','N','Ausgehende Belege Konfig',TO_TIMESTAMP('2024-08-23 17:08:50.542','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:50.635Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-23T14:08:50.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541926)
;

-- 2024-08-23T14:08:50.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729836
;

-- 2024-08-23T14:08:50.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729836)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Ausgehende Belege Konfig CC
-- Column: C_Doc_Outbound_Config_CC.C_Doc_Outbound_Config_CC_ID
-- 2024-08-23T14:08:50.752Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588919,729837,0,547571,TO_TIMESTAMP('2024-08-23 17:08:50.643','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.document.archive','Y','N','N','N','N','N','N','N','Ausgehende Belege Konfig CC',TO_TIMESTAMP('2024-08-23 17:08:50.643','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:50.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-23T14:08:50.755Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583227)
;

-- 2024-08-23T14:08:50.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729837
;

-- 2024-08-23T14:08:50.757Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729837)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Spaltenname
-- Column: C_Doc_Outbound_Config_CC.BPartner_ColumnName_ID
-- 2024-08-23T14:08:50.850Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588922,729838,0,547571,TO_TIMESTAMP('2024-08-23 17:08:50.76','YYYY-MM-DD HH24:MI:SS.US'),100,'Spaltenname des Partners',60,'de.metas.document.archive','Dies ist eine Liste mit der Spalte basierend auf der Tabelle, die in C_Doc_Outbound_Config festgelegt ist.
Wenn wir in C_Doc_Outbound_Config die Tabelle C_Order festgelegt haben, können wir in C_Doc_Outbound_Config_CC.BPartner_ColumnName die Felder aus C_Order sehen, wie: C_Order.C_BPartner_ID, C_Order.BillPartner_ID usw.','Y','N','N','N','N','N','N','N','Spaltenname',TO_TIMESTAMP('2024-08-23 17:08:50.76','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:50.852Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-23T14:08:50.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583228)
;

-- 2024-08-23T14:08:50.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729838
;

-- 2024-08-23T14:08:50.858Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729838)
;

-- Field: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> Aktiv
-- Column: C_Doc_Outbound_Config_CC.IsActive
-- 2024-08-23T14:08:50.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588923,729839,0,547571,TO_TIMESTAMP('2024-08-23 17:08:50.862','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.document.archive','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-08-23 17:08:50.862','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:08:50.961Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-23T14:08:50.962Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-08-23T14:08:51.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729839
;

-- 2024-08-23T14:08:51.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729839)
;

-- Tab: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive)
-- UI Section: main
-- 2024-08-23T14:09:02.876Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547571,546155,TO_TIMESTAMP('2024-08-23 17:09:02.741','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2024-08-23 17:09:02.741','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-08-23T14:09:02.878Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546155 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main
-- UI Column: 10
-- 2024-08-23T14:09:11.489Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547521,546155,TO_TIMESTAMP('2024-08-23 17:09:11.347','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-08-23 17:09:11.347','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main -> 10
-- UI Element Group: main
-- 2024-08-23T14:09:19.394Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547521,551896,TO_TIMESTAMP('2024-08-23 17:09:19.251','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2024-08-23 17:09:19.251','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main -> 10 -> main.Spaltenname
-- Column: C_Doc_Outbound_Config_CC.BPartner_ColumnName_ID
-- 2024-08-23T14:09:38.756Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729838,0,547571,551896,625291,'F',TO_TIMESTAMP('2024-08-23 17:09:38.642','YYYY-MM-DD HH24:MI:SS.US'),100,'Spaltenname des Partners','Dies ist eine Liste mit der Spalte basierend auf der Tabelle, die in C_Doc_Outbound_Config festgelegt ist.
Wenn wir in C_Doc_Outbound_Config die Tabelle C_Order festgelegt haben, können wir in C_Doc_Outbound_Config_CC.BPartner_ColumnName die Felder aus C_Order sehen, wie: C_Order.C_BPartner_ID, C_Order.BillPartner_ID usw.','Y','N','Y','N','N','Spaltenname',10,0,0,TO_TIMESTAMP('2024-08-23 17:09:38.642','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main -> 10 -> main.Druck - Format
-- Column: C_Doc_Outbound_Config_CC.AD_PrintFormat_ID
-- 2024-08-23T14:09:46.110Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729835,0,547571,551896,625292,'F',TO_TIMESTAMP('2024-08-23 17:09:45.964','YYYY-MM-DD HH24:MI:SS.US'),100,'Data Print Format','Das Druckformat legt fest, wie die Daten für den Druck aufbereitet werden.','Y','N','Y','N','N','Druck - Format',20,0,0,TO_TIMESTAMP('2024-08-23 17:09:45.964','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main -> 10 -> main.Aktiv
-- Column: C_Doc_Outbound_Config_CC.IsActive
-- 2024-08-23T14:09:51.926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729839,0,547571,551896,625293,'F',TO_TIMESTAMP('2024-08-23 17:09:51.804','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',30,0,0,TO_TIMESTAMP('2024-08-23 17:09:51.804','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main -> 10 -> main.Organisation
-- Column: C_Doc_Outbound_Config_CC.AD_Org_ID
-- 2024-08-23T14:09:59.787Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729834,0,547571,551896,625294,'F',TO_TIMESTAMP('2024-08-23 17:09:59.649','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',40,0,0,TO_TIMESTAMP('2024-08-23 17:09:59.649','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC(547571,de.metas.document.archive) -> main -> 10 -> main.Mandant
-- Column: C_Doc_Outbound_Config_CC.AD_Client_ID
-- 2024-08-23T14:10:04.515Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729833,0,547571,551896,625295,'F',TO_TIMESTAMP('2024-08-23 17:10:04.412','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',50,0,0,TO_TIMESTAMP('2024-08-23 17:10:04.412','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: BPartner_ColumnName_by_AD_Table_ID
-- 2024-08-23T14:11:54.338Z
UPDATE AD_Val_Rule SET Code='AD_Column.AD_Table_ID=@AD_Table_ID@ AND ColumnName ilike ''%partner%''',Updated=TO_TIMESTAMP('2024-08-23 17:11:54.336','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540687
;

-- Tab: Ausgehende Belege Konfig(540173,de.metas.document.archive) -> Ausgehende Belege Konfig CC
-- Table: C_Doc_Outbound_Config_CC
-- 2024-08-23T14:21:13.302Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2024-08-23 17:21:13.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547571
;

-- Column: C_Doc_Outbound_Config_CC.C_Doc_Outbound_Config_ID
-- 2024-08-23T14:21:50.903Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-08-23 17:21:50.903','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588917
;

-- Name: BPartner_ColumnName_by_AD_Table_ID
-- 2024-08-23T14:26:21.472Z
UPDATE AD_Val_Rule SET Code='AD_Column.AD_Table_ID=@AD_Table_ID@ AND ColumnName ilike ''%partner_id%''',Updated=TO_TIMESTAMP('2024-08-23 17:26:21.47','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540687
;

-- 2024-08-23T14:28:26.550Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583229,0,'ShipFrom_Partner_ID',TO_TIMESTAMP('2024-08-23 17:28:26.386','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','Ship From Partner','Ship From Partner',TO_TIMESTAMP('2024-08-23 17:28:26.386','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-08-23T14:28:26.552Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583229 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ShipFrom_Partner_ID
-- 2024-08-23T14:28:35.240Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferung von', PrintName='Lieferung von',Updated=TO_TIMESTAMP('2024-08-23 17:28:35.24','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583229 AND AD_Language='de_DE'
;

-- 2024-08-23T14:28:35.242Z
UPDATE AD_Element SET Name='Lieferung von', PrintName='Lieferung von' WHERE AD_Element_ID=583229
;

-- 2024-08-23T14:28:35.487Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583229,'de_DE')
;

-- 2024-08-23T14:28:35.488Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583229,'de_DE')
;

-- Element: ShipFrom_Partner_ID
-- 2024-08-23T14:28:38.030Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-08-23 17:28:38.029','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583229 AND AD_Language='en_US'
;

-- 2024-08-23T14:28:38.035Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583229,'en_US')
;

-- Element: ShipFrom_Partner_ID
-- 2024-08-23T14:29:05.457Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferung von', PrintName='Lieferung von',Updated=TO_TIMESTAMP('2024-08-23 17:29:05.457','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583229 AND AD_Language='de_CH'
;

-- 2024-08-23T14:29:05.462Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583229,'de_CH')
;

-- Column: M_Shipping_Notification.ShipFrom_Partner_ID
-- 2024-08-23T14:29:49.404Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588928,583229,0,18,541252,542365,'ShipFrom_Partner_ID',TO_TIMESTAMP('2024-08-23 17:29:49.302','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferung von',0,0,TO_TIMESTAMP('2024-08-23 17:29:49.302','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-08-23T14:29:49.406Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-23T14:29:49.409Z
/* DDL */  select update_Column_Translation_From_AD_Element(583229)
;

-- 2024-08-23T14:29:53.210Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN ShipFrom_Partner_ID NUMERIC(10)')
;

-- 2024-08-23T14:29:53.418Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT ShipFromPartner_MShippingNotification FOREIGN KEY (ShipFrom_Partner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Value: Shipping Notification - Warehouse partner
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@de/metas/docs/sales/shipping_notification_warehouse/report.jasper
-- 2024-08-26T14:02:24.718Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,JasperReport,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585420,'Y','de.metas.report.jasper.client.process.JasperReportStarter','N',TO_TIMESTAMP('2024-08-26 17:02:24.506','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','N','N','N','Y','N','N','N','Y','Y','N','Y','@PREFIX@de/metas/docs/sales/shipping_notification_warehouse/report.jasper',0,'Shipping Notification - Warehouse partner(Jasper)','json','N','N','xls','JasperReportsSQL',TO_TIMESTAMP('2024-08-26 17:02:24.506','YYYY-MM-DD HH24:MI:SS.US'),100,'Shipping Notification - Warehouse partner')
;

-- 2024-08-26T14:02:24.725Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585420 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: C_Doc_Outbound_Config_CC
-- EntityType: D
-- 2024-08-26T14:02:35.414Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585420,542430,541518,TO_TIMESTAMP('2024-08-26 17:02:35.28','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2024-08-26 17:02:35.28','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: C_Doc_Outbound_Config_CC
-- EntityType: D
-- 2024-08-26T14:02:43.056Z
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=541518
;

-- Process: Shipping Notification - Warehouse partner(de.metas.report.jasper.client.process.JasperReportStarter)
-- Table: M_Shipping_Notification
-- EntityType: de.metas.shippingnotification
-- 2024-08-26T14:03:21.793Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585420,542365,541519,TO_TIMESTAMP('2024-08-26 17:03:21.641','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y',TO_TIMESTAMP('2024-08-26 17:03:21.641','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

