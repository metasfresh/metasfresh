-- Run mode: SWING_CLIENT

-- Table: MobileUI_UserProfile_Picking_Job
-- 2025-02-12T14:40:18.008Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542464,'A','N',TO_TIMESTAMP('2025-02-12 14:40:17.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','D','N','Y','N','N','Y','N','N','N','N','N',0,'Mobile UI Picking Job Options','NP','L','MobileUI_UserProfile_Picking_Job','DTI',TO_TIMESTAMP('2025-02-12 14:40:17.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2025-02-12T14:40:18.011Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542464 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-02-12T14:40:18.157Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556398,TO_TIMESTAMP('2025-02-12 14:40:18.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table MobileUI_UserProfile_Picking_Job',1,'Y','N','Y','Y','MobileUI_UserProfile_Picking_Job',1000000,TO_TIMESTAMP('2025-02-12 14:40:18.043000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T14:40:18.174Z
CREATE SEQUENCE MOBILEUI_USERPROFILE_PICKING_JOB_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: MobileUI_UserProfile_Picking_Job
-- 2025-02-12T14:40:24.945Z
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2025-02-12 14:40:24.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542464
;

-- Column: MobileUI_UserProfile_Picking_Job.AD_Client_ID
-- 2025-02-12T14:40:38.974Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589644,102,0,19,542464,'AD_Client_ID',TO_TIMESTAMP('2025-02-12 14:40:38.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2025-02-12 14:40:38.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:38.976Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:39.134Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: MobileUI_UserProfile_Picking_Job.AD_Org_ID
-- 2025-02-12T14:40:39.912Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589645,113,0,30,542464,'AD_Org_ID',TO_TIMESTAMP('2025-02-12 14:40:39.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2025-02-12 14:40:39.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:39.916Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589645 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:39.919Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: MobileUI_UserProfile_Picking_Job.Created
-- 2025-02-12T14:40:40.543Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589646,245,0,16,542464,'Created',TO_TIMESTAMP('2025-02-12 14:40:40.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2025-02-12 14:40:40.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:40.545Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589646 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:40.548Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: MobileUI_UserProfile_Picking_Job.CreatedBy
-- 2025-02-12T14:40:41.171Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589647,246,0,18,110,542464,'CreatedBy',TO_TIMESTAMP('2025-02-12 14:40:40.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2025-02-12 14:40:40.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:41.173Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589647 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:41.175Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsActive
-- 2025-02-12T14:40:41.757Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589648,348,0,20,542464,'IsActive',TO_TIMESTAMP('2025-02-12 14:40:41.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2025-02-12 14:40:41.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:41.759Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:41.765Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: MobileUI_UserProfile_Picking_Job.Updated
-- 2025-02-12T14:40:42.331Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589649,607,0,16,542464,'Updated',TO_TIMESTAMP('2025-02-12 14:40:42.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2025-02-12 14:40:42.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:42.333Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:42.337Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: MobileUI_UserProfile_Picking_Job.UpdatedBy
-- 2025-02-12T14:40:42.924Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589650,608,0,18,110,542464,'UpdatedBy',TO_TIMESTAMP('2025-02-12 14:40:42.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2025-02-12 14:40:42.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:42.926Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589650 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:42.928Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-02-12T14:40:43.391Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583482,0,'MobileUI_UserProfile_Picking_Job_ID',TO_TIMESTAMP('2025-02-12 14:40:43.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mobile UI Picking Job Options','Mobile UI Picking Job Options',TO_TIMESTAMP('2025-02-12 14:40:43.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T14:40:43.394Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583482 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_UserProfile_Picking_Job.MobileUI_UserProfile_Picking_Job_ID
-- 2025-02-12T14:40:43.934Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589651,583482,0,13,542464,'MobileUI_UserProfile_Picking_Job_ID',TO_TIMESTAMP('2025-02-12 14:40:43.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Mobile UI Picking Job Options',0,0,TO_TIMESTAMP('2025-02-12 14:40:43.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:40:43.935Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589651 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:40:43.938Z
/* DDL */  select update_Column_Translation_From_AD_Element(583482)
;

-- Column: MobileUI_UserProfile_Picking_Job.CreateShipmentPolicy
-- 2025-02-12T14:41:52.949Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589652,582769,0,17,541839,542464,'CreateShipmentPolicy',TO_TIMESTAMP('2025-02-12 14:41:52.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','NO','Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen','D',0,2,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Erstellen von Lieferung Richtlinie',0,0,TO_TIMESTAMP('2025-02-12 14:41:52.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:52.953Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589652 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:52.958Z
/* DDL */  select update_Column_Translation_From_AD_Element(582769)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsAllowNewTU
-- 2025-02-12T14:41:53.517Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589653,583230,0,20,542464,'IsAllowNewTU',TO_TIMESTAMP('2025-02-12 14:41:53.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Allow new TU',0,0,TO_TIMESTAMP('2025-02-12 14:41:53.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:53.518Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589653 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:53.521Z
/* DDL */  select update_Column_Translation_From_AD_Element(583230)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsAllowPickingAnyHU
-- 2025-02-12T14:41:54.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589654,582776,0,20,542464,'IsAllowPickingAnyHU',TO_TIMESTAMP('2025-02-12 14:41:53.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Beliebige HU kommissionieren',0,0,TO_TIMESTAMP('2025-02-12 14:41:53.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:54.074Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589654 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:54.076Z
/* DDL */  select update_Column_Translation_From_AD_Element(582776)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsAllowSkippingRejectedReason
-- 2025-02-12T14:41:54.607Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589655,583236,0,20,542464,'IsAllowSkippingRejectedReason',TO_TIMESTAMP('2025-02-12 14:41:54.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn aktiviert darf eine geringere Menge kommissioniert werden. Es taucht dann eine weitere Option im Kommissionierdialog auf.','D',0,1,'Wenn aktiviert darf eine geringere Menge kommissioniert werden. Es taucht dann eine weitere Option im Kommissionierdialog auf.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Abweichende Menge erlauben',0,0,TO_TIMESTAMP('2025-02-12 14:41:54.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:54.609Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589655 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:54.612Z
/* DDL */  select update_Column_Translation_From_AD_Element(583236)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsAlwaysSplitHUsEnabled
-- 2025-02-12T14:41:55.173Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589656,583041,0,20,542464,'IsAlwaysSplitHUsEnabled',TO_TIMESTAMP('2025-02-12 14:41:54.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn diese Option aktiviert ist, wird die ausgewählte HU vor der Kommissionierung immer aufgeteilt.','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','HU immer splitten',0,0,TO_TIMESTAMP('2025-02-12 14:41:54.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:55.174Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589656 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:55.177Z
/* DDL */  select update_Column_Translation_From_AD_Element(583041)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsCatchWeightTUPickingEnabled
-- 2025-02-12T14:41:55.715Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589657,583234,0,20,542464,'IsCatchWeightTUPickingEnabled',TO_TIMESTAMP('2025-02-12 14:41:55.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn aktiviert und in der Auftragszeile eine Packvorschrift enthalten ist, wird beim kommissionieren von Catch Weight angenommen dass es sich um eine TU handelt. Andernfalls geht metasfresh von einer CU aus','D',0,1,'Wenn aktiviert und in der Auftragszeile eine Packvorschrift enthalten ist, wird beim kommissionieren von Catch Weight angenommen dass es sich um eine TU handelt. Andernfalls geht metasfresh von einer CU aus','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','TU Catch weight erlauben',0,0,TO_TIMESTAMP('2025-02-12 14:41:55.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:55.718Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589657 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:55.720Z
/* DDL */  select update_Column_Translation_From_AD_Element(583234)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsConsiderSalesOrderCapacity
-- 2025-02-12T14:41:56.277Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589658,583235,0,20,542464,'IsConsiderSalesOrderCapacity',TO_TIMESTAMP('2025-02-12 14:41:56.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn nicht aktiviert wird die Mengenzuordnung bei TU Catch Weight aus den Stammdaten des Produkts genommen','D',0,1,'Wenn nicht aktiviert wird die Mengenzuordnung bei TU Catch Weight aus den Stammdaten des Produkts genommen','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','CU-TU Menge aus Auftrag verwenden',0,0,TO_TIMESTAMP('2025-02-12 14:41:56.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:56.278Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589658 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:56.281Z
/* DDL */  select update_Column_Translation_From_AD_Element(583235)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsPickingWithNewLU
-- 2025-02-12T14:41:56.918Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589659,583239,0,20,542464,'IsPickingWithNewLU',TO_TIMESTAMP('2025-02-12 14:41:56.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Kommissionieren mit LU',0,0,TO_TIMESTAMP('2025-02-12 14:41:56.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:56.920Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589659 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:56.923Z
/* DDL */  select update_Column_Translation_From_AD_Element(583239)
;

-- Column: MobileUI_UserProfile_Picking_Job.IsShowConfirmationPromptWhenOverPick
-- 2025-02-12T14:41:57.489Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589660,583289,0,20,542464,'IsShowConfirmationPromptWhenOverPick',TO_TIMESTAMP('2025-02-12 14:41:57.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Rückfragen bei Überkommissionierung',0,0,TO_TIMESTAMP('2025-02-12 14:41:57.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:57.491Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589660 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:57.495Z
/* DDL */  select update_Column_Translation_From_AD_Element(583289)
;

-- Column: MobileUI_UserProfile_Picking_Job.Name
-- 2025-02-12T14:41:58.076Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589661,469,0,10,542464,'Name',TO_TIMESTAMP('2025-02-12 14:41:57.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,255,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',0,1,TO_TIMESTAMP('2025-02-12 14:41:57.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:58.078Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589661 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:58.081Z
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- Column: MobileUI_UserProfile_Picking_Job.PickingLineGroupBy
-- 2025-02-12T14:41:58.752Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589662,583314,0,17,541899,542464,'PickingLineGroupBy',TO_TIMESTAMP('2025-02-12 14:41:58.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Kommissionierung Liniengruppierung',0,0,TO_TIMESTAMP('2025-02-12 14:41:58.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:58.755Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589662 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:58.759Z
/* DDL */  select update_Column_Translation_From_AD_Element(583314)
;

-- Column: MobileUI_UserProfile_Picking_Job.PickingLineSortBy
-- 2025-02-12T14:41:59.349Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589663,583315,0,17,541900,542464,'PickingLineSortBy',TO_TIMESTAMP('2025-02-12 14:41:59.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Sortierung der Kommissionierlinie',0,0,TO_TIMESTAMP('2025-02-12 14:41:59.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T14:41:59.350Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589663 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T14:41:59.353Z
/* DDL */  select update_Column_Translation_From_AD_Element(583315)
;

-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_Job_ID
-- 2025-02-12T15:24:26.535Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589664,583482,0,30,542374,'XX','MobileUI_UserProfile_Picking_Job_ID',TO_TIMESTAMP('2025-02-12 15:24:26.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mobile UI Picking Job Options',0,0,TO_TIMESTAMP('2025-02-12 15:24:26.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T15:24:26.539Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589664 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T15:24:26.544Z
/* DDL */  select update_Column_Translation_From_AD_Element(583482)
;

-- Column: MobileUI_UserProfile_Picking_Job.Description
-- 2025-02-12T15:26:14.577Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589665,275,0,36,542464,'XX','Description',TO_TIMESTAMP('2025-02-12 15:26:14.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2025-02-12 15:26:14.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-12T15:26:14.580Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589665 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-12T15:26:14.702Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2025-02-12T15:26:26.719Z
/* DDL */ CREATE TABLE public.MobileUI_UserProfile_Picking_Job (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, CreateShipmentPolicy VARCHAR(2) DEFAULT 'NO' NOT NULL, Description TEXT, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsAllowNewTU CHAR(1) DEFAULT 'N' CHECK (IsAllowNewTU IN ('Y','N')) NOT NULL, IsAllowPickingAnyHU CHAR(1) DEFAULT 'N' CHECK (IsAllowPickingAnyHU IN ('Y','N')) NOT NULL, IsAllowSkippingRejectedReason CHAR(1) DEFAULT 'N' CHECK (IsAllowSkippingRejectedReason IN ('Y','N')) NOT NULL, IsAlwaysSplitHUsEnabled CHAR(1) DEFAULT 'N' CHECK (IsAlwaysSplitHUsEnabled IN ('Y','N')) NOT NULL, IsCatchWeightTUPickingEnabled CHAR(1) DEFAULT 'N' CHECK (IsCatchWeightTUPickingEnabled IN ('Y','N')) NOT NULL, IsConsiderSalesOrderCapacity CHAR(1) DEFAULT 'N' CHECK (IsConsiderSalesOrderCapacity IN ('Y','N')) NOT NULL, IsPickingWithNewLU CHAR(1) DEFAULT 'N' CHECK (IsPickingWithNewLU IN ('Y','N')) NOT NULL, IsShowConfirmationPromptWhenOverPick CHAR(1) DEFAULT 'N' CHECK (IsShowConfirmationPromptWhenOverPick IN ('Y','N')) NOT NULL, MobileUI_UserProfile_Picking_Job_ID NUMERIC(10) NOT NULL, Name VARCHAR(255) NOT NULL, PickingLineGroupBy VARCHAR(255), PickingLineSortBy VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MobileUI_UserProfile_Picking_Job_Key PRIMARY KEY (MobileUI_UserProfile_Picking_Job_ID))
;

-- 2025-02-12T15:26:32.538Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking_BPartner','ALTER TABLE public.MobileUI_UserProfile_Picking_BPartner ADD COLUMN MobileUI_UserProfile_Picking_Job_ID NUMERIC(10)')
;

-- 2025-02-12T15:26:32.552Z
ALTER TABLE MobileUI_UserProfile_Picking_BPartner ADD CONSTRAINT MobileUIUserProfilePickingJob_MobileUIUserProfilePickingBPartner FOREIGN KEY (MobileUI_UserProfile_Picking_Job_ID) REFERENCES public.MobileUI_UserProfile_Picking_Job DEFERRABLE INITIALLY DEFERRED
;

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> Mobile UI Picking Job Options
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_Job_ID
-- 2025-02-12T15:36:44.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589664,737661,0,547259,TO_TIMESTAMP('2025-02-12 15:36:44.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Mobile UI Picking Job Options',TO_TIMESTAMP('2025-02-12 15:36:44.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:36:44.956Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737661 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:36:44.964Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583482)
;

-- 2025-02-12T15:36:44.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737661
;

-- 2025-02-12T15:36:44.992Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737661)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 10 -> default.Mobile UI Picking Job Options
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_Job_ID
-- 2025-02-12T15:37:29.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737661,0,547259,551254,629417,'F',TO_TIMESTAMP('2025-02-12 15:37:29.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mobile UI Picking Job Options',40,0,0,TO_TIMESTAMP('2025-02-12 15:37:29.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main
-- UI Column: 20
-- 2025-02-12T15:37:58.987Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547833,545854,TO_TIMESTAMP('2025-02-12 15:37:58.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-02-12 15:37:58.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-02-12T15:38:10.342Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547833,552478,TO_TIMESTAMP('2025-02-12 15:38:10.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',10,TO_TIMESTAMP('2025-02-12 15:38:10.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 20 -> org&client.Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- 2025-02-12T15:38:55.063Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552478, SeqNo=10,Updated=TO_TIMESTAMP('2025-02-12 15:38:55.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621120
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 20 -> org&client.Mandant
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Client_ID
-- 2025-02-12T15:39:07.430Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552478, SeqNo=20,Updated=TO_TIMESTAMP('2025-02-12 15:39:07.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621121
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 20 -> org&client.Sektion
-- Column: MobileUI_UserProfile_Picking_BPartner.AD_Org_ID
-- 2025-02-12T15:39:22.099Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-02-12 15:39:22.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621120
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Kunden(547259,D) -> main -> 10 -> default.Mobile UI Picking Job Options
-- Column: MobileUI_UserProfile_Picking_BPartner.MobileUI_UserProfile_Picking_Job_ID
-- 2025-02-12T15:39:22.106Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-02-12 15:39:22.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=629417
;

-- Window: Mobile UI Picking Job Options, InternalName=null
-- 2025-02-12T15:39:54.356Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583482,0,541862,TO_TIMESTAMP('2025-02-12 15:39:54.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Mobile UI Picking Job Options','N',TO_TIMESTAMP('2025-02-12 15:39:54.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-02-12T15:39:54.360Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541862 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-02-12T15:39:54.362Z
/* DDL */  select update_window_translation_from_ad_element(583482)
;

-- 2025-02-12T15:39:54.365Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541862
;

-- 2025-02-12T15:39:54.366Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541862)
;

-- Tab: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options
-- Table: MobileUI_UserProfile_Picking_Job
-- 2025-02-12T15:40:39.491Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583482,0,547823,542464,541862,'Y',TO_TIMESTAMP('2025-02-12 15:40:39.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','MobileUI_UserProfile_Picking_Job','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Mobile UI Picking Job Options','N',10,0,TO_TIMESTAMP('2025-02-12 15:40:39.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:39.493Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547823 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-02-12T15:40:39.495Z
/* DDL */  select update_tab_translation_from_ad_element(583482)
;

-- 2025-02-12T15:40:39.505Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547823)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Mandant
-- Column: MobileUI_UserProfile_Picking_Job.AD_Client_ID
-- 2025-02-12T15:40:44.160Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589644,737662,0,547823,TO_TIMESTAMP('2025-02-12 15:40:44.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-02-12 15:40:44.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:44.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737662 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:44.165Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-02-12T15:40:45.652Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737662
;

-- 2025-02-12T15:40:45.653Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737662)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Sektion
-- Column: MobileUI_UserProfile_Picking_Job.AD_Org_ID
-- 2025-02-12T15:40:45.770Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589645,737663,0,547823,TO_TIMESTAMP('2025-02-12 15:40:45.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-02-12 15:40:45.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:45.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737663 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:45.773Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-02-12T15:40:46.310Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737663
;

-- 2025-02-12T15:40:46.312Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737663)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Aktiv
-- Column: MobileUI_UserProfile_Picking_Job.IsActive
-- 2025-02-12T15:40:46.421Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589648,737664,0,547823,TO_TIMESTAMP('2025-02-12 15:40:46.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-02-12 15:40:46.314000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:46.422Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737664 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:46.424Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-02-12T15:40:46.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737664
;

-- 2025-02-12T15:40:46.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737664)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Mobile UI Picking Job Options
-- Column: MobileUI_UserProfile_Picking_Job.MobileUI_UserProfile_Picking_Job_ID
-- 2025-02-12T15:40:46.658Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589651,737665,0,547823,TO_TIMESTAMP('2025-02-12 15:40:46.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Mobile UI Picking Job Options',TO_TIMESTAMP('2025-02-12 15:40:46.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:46.659Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737665 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:46.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583482)
;

-- 2025-02-12T15:40:46.663Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737665
;

-- 2025-02-12T15:40:46.664Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737665)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking_Job.CreateShipmentPolicy
-- 2025-02-12T15:40:46.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589652,737666,0,547823,TO_TIMESTAMP('2025-02-12 15:40:46.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen',2,'D','Y','N','N','N','N','N','N','N','Erstellen von Lieferung Richtlinie',TO_TIMESTAMP('2025-02-12 15:40:46.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:46.778Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737666 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:46.780Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582769)
;

-- 2025-02-12T15:40:46.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737666
;

-- 2025-02-12T15:40:46.785Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737666)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Allow new TU
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowNewTU
-- 2025-02-12T15:40:46.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589653,737667,0,547823,TO_TIMESTAMP('2025-02-12 15:40:46.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Allow new TU',TO_TIMESTAMP('2025-02-12 15:40:46.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:46.901Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737667 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:46.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583230)
;

-- 2025-02-12T15:40:46.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737667
;

-- 2025-02-12T15:40:46.907Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737667)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Beliebige HU kommissionieren
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowPickingAnyHU
-- 2025-02-12T15:40:47.027Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589654,737668,0,547823,TO_TIMESTAMP('2025-02-12 15:40:46.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Beliebige HU kommissionieren',TO_TIMESTAMP('2025-02-12 15:40:46.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.029Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737668 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.031Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582776)
;

-- 2025-02-12T15:40:47.034Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737668
;

-- 2025-02-12T15:40:47.035Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737668)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Abweichende Menge erlauben
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowSkippingRejectedReason
-- 2025-02-12T15:40:47.146Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589655,737669,0,547823,TO_TIMESTAMP('2025-02-12 15:40:47.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiviert darf eine geringere Menge kommissioniert werden. Es taucht dann eine weitere Option im Kommissionierdialog auf.',1,'D','Wenn aktiviert darf eine geringere Menge kommissioniert werden. Es taucht dann eine weitere Option im Kommissionierdialog auf.','Y','N','N','N','N','N','N','N','Abweichende Menge erlauben',TO_TIMESTAMP('2025-02-12 15:40:47.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.149Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737669 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.151Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583236)
;

-- 2025-02-12T15:40:47.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737669
;

-- 2025-02-12T15:40:47.157Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737669)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> HU immer splitten
-- Column: MobileUI_UserProfile_Picking_Job.IsAlwaysSplitHUsEnabled
-- 2025-02-12T15:40:47.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589656,737670,0,547823,TO_TIMESTAMP('2025-02-12 15:40:47.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn diese Option aktiviert ist, wird die ausgewählte HU vor der Kommissionierung immer aufgeteilt.',1,'D','Y','N','N','N','N','N','N','N','HU immer splitten',TO_TIMESTAMP('2025-02-12 15:40:47.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.271Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737670 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583041)
;

-- 2025-02-12T15:40:47.275Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737670
;

-- 2025-02-12T15:40:47.276Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737670)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> TU Catch weight erlauben
-- Column: MobileUI_UserProfile_Picking_Job.IsCatchWeightTUPickingEnabled
-- 2025-02-12T15:40:47.388Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589657,737671,0,547823,TO_TIMESTAMP('2025-02-12 15:40:47.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiviert und in der Auftragszeile eine Packvorschrift enthalten ist, wird beim kommissionieren von Catch Weight angenommen dass es sich um eine TU handelt. Andernfalls geht metasfresh von einer CU aus',1,'D','Wenn aktiviert und in der Auftragszeile eine Packvorschrift enthalten ist, wird beim kommissionieren von Catch Weight angenommen dass es sich um eine TU handelt. Andernfalls geht metasfresh von einer CU aus','Y','N','N','N','N','N','N','N','TU Catch weight erlauben',TO_TIMESTAMP('2025-02-12 15:40:47.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.390Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737671 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.392Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583234)
;

-- 2025-02-12T15:40:47.394Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737671
;

-- 2025-02-12T15:40:47.395Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737671)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> CU-TU Menge aus Auftrag verwenden
-- Column: MobileUI_UserProfile_Picking_Job.IsConsiderSalesOrderCapacity
-- 2025-02-12T15:40:47.511Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589658,737672,0,547823,TO_TIMESTAMP('2025-02-12 15:40:47.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn nicht aktiviert wird die Mengenzuordnung bei TU Catch Weight aus den Stammdaten des Produkts genommen',1,'D','Wenn nicht aktiviert wird die Mengenzuordnung bei TU Catch Weight aus den Stammdaten des Produkts genommen','Y','N','N','N','N','N','N','N','CU-TU Menge aus Auftrag verwenden',TO_TIMESTAMP('2025-02-12 15:40:47.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.512Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737672 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.514Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583235)
;

-- 2025-02-12T15:40:47.518Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737672
;

-- 2025-02-12T15:40:47.519Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737672)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Kommissionieren mit LU
-- Column: MobileUI_UserProfile_Picking_Job.IsPickingWithNewLU
-- 2025-02-12T15:40:47.633Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589659,737673,0,547823,TO_TIMESTAMP('2025-02-12 15:40:47.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Kommissionieren mit LU',TO_TIMESTAMP('2025-02-12 15:40:47.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.634Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737673 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.636Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583239)
;

-- 2025-02-12T15:40:47.639Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737673
;

-- 2025-02-12T15:40:47.640Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737673)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Rückfragen bei Überkommissionierung
-- Column: MobileUI_UserProfile_Picking_Job.IsShowConfirmationPromptWhenOverPick
-- 2025-02-12T15:40:47.751Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589660,737674,0,547823,TO_TIMESTAMP('2025-02-12 15:40:47.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Rückfragen bei Überkommissionierung',TO_TIMESTAMP('2025-02-12 15:40:47.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737674 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583289)
;

-- 2025-02-12T15:40:47.758Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737674
;

-- 2025-02-12T15:40:47.758Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737674)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Name
-- Column: MobileUI_UserProfile_Picking_Job.Name
-- 2025-02-12T15:40:47.880Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589661,737675,0,547823,TO_TIMESTAMP('2025-02-12 15:40:47.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2025-02-12 15:40:47.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:47.881Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737675 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:47.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-02-12T15:40:48.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737675
;

-- 2025-02-12T15:40:48.139Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737675)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Kommissionierung Liniengruppierung
-- Column: MobileUI_UserProfile_Picking_Job.PickingLineGroupBy
-- 2025-02-12T15:40:48.248Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589662,737676,0,547823,TO_TIMESTAMP('2025-02-12 15:40:48.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Kommissionierung Liniengruppierung',TO_TIMESTAMP('2025-02-12 15:40:48.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:48.249Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737676 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:48.251Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583314)
;

-- 2025-02-12T15:40:48.253Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737676
;

-- 2025-02-12T15:40:48.254Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737676)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Sortierung der Kommissionierlinie
-- Column: MobileUI_UserProfile_Picking_Job.PickingLineSortBy
-- 2025-02-12T15:40:48.379Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589663,737677,0,547823,TO_TIMESTAMP('2025-02-12 15:40:48.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Sortierung der Kommissionierlinie',TO_TIMESTAMP('2025-02-12 15:40:48.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:48.380Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737677 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:48.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583315)
;

-- 2025-02-12T15:40:48.384Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737677
;

-- 2025-02-12T15:40:48.385Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737677)
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Beschreibung
-- Column: MobileUI_UserProfile_Picking_Job.Description
-- 2025-02-12T15:40:48.508Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589665,737678,0,547823,TO_TIMESTAMP('2025-02-12 15:40:48.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-02-12 15:40:48.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:40:48.509Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=737678 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-12T15:40:48.511Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-02-12T15:40:48.721Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=737678
;

-- 2025-02-12T15:40:48.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(737678)
;

-- Window: Mobile UI Picking Job Options, InternalName=MobileUI_UserProfile_Picking_Job
-- 2025-02-12T15:40:54.526Z
UPDATE AD_Window SET InternalName='MobileUI_UserProfile_Picking_Job',Updated=TO_TIMESTAMP('2025-02-12 15:40:54.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541862
;

-- Tab: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D)
-- UI Section: main
-- 2025-02-12T15:42:16.989Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547823,546410,TO_TIMESTAMP('2025-02-12 15:42:16.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-02-12 15:42:16.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-02-12T15:42:16.991Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546410 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main
-- UI Column: 10
-- 2025-02-12T15:42:17.108Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547834,546410,TO_TIMESTAMP('2025-02-12 15:42:17.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-02-12 15:42:17.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main
-- UI Column: 20
-- 2025-02-12T15:42:17.224Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547835,546410,TO_TIMESTAMP('2025-02-12 15:42:17.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-02-12 15:42:17.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10
-- UI Element Group: default
-- 2025-02-12T15:42:17.345Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547834,552479,TO_TIMESTAMP('2025-02-12 15:42:17.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-02-12 15:42:17.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10 -> default.Name
-- Column: MobileUI_UserProfile_Picking_Job.Name
-- 2025-02-12T15:42:42.827Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737675,0,547823,552479,629418,'F',TO_TIMESTAMP('2025-02-12 15:42:42.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2025-02-12 15:42:42.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10 -> default.Beschreibung
-- Column: MobileUI_UserProfile_Picking_Job.Description
-- 2025-02-12T15:42:54.072Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737678,0,547823,552479,629419,'F',TO_TIMESTAMP('2025-02-12 15:42:53.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2025-02-12 15:42:53.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10
-- UI Element Group: shipment
-- 2025-02-12T15:43:18.655Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547834,552480,TO_TIMESTAMP('2025-02-12 15:43:18.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','shipment',20,TO_TIMESTAMP('2025-02-12 15:43:18.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10 -> shipment.Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking_Job.CreateShipmentPolicy
-- 2025-02-12T15:43:38.345Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737666,0,547823,552480,629420,'F',TO_TIMESTAMP('2025-02-12 15:43:38.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen','Y','N','Y','N','N','Erstellen von Lieferung Richtlinie',10,0,0,TO_TIMESTAMP('2025-02-12 15:43:38.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10
-- UI Element Group: lines
-- 2025-02-12T15:43:51.106Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547834,552481,TO_TIMESTAMP('2025-02-12 15:43:50.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','lines',30,TO_TIMESTAMP('2025-02-12 15:43:50.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10 -> lines.Kommissionierung Liniengruppierung
-- Column: MobileUI_UserProfile_Picking_Job.PickingLineGroupBy
-- 2025-02-12T15:44:04.727Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737676,0,547823,552481,629421,'F',TO_TIMESTAMP('2025-02-12 15:44:04.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Kommissionierung Liniengruppierung',10,0,0,TO_TIMESTAMP('2025-02-12 15:44:04.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10 -> lines.Sortierung der Kommissionierlinie
-- Column: MobileUI_UserProfile_Picking_Job.PickingLineSortBy
-- 2025-02-12T15:44:43.281Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737677,0,547823,552481,629422,'F',TO_TIMESTAMP('2025-02-12 15:44:43.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Sortierung der Kommissionierlinie',20,0,0,TO_TIMESTAMP('2025-02-12 15:44:43.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20
-- UI Element Group: flags
-- 2025-02-12T15:45:00.008Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547835,552482,TO_TIMESTAMP('2025-02-12 15:44:59.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-02-12 15:44:59.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.Aktiv
-- Column: MobileUI_UserProfile_Picking_Job.IsActive
-- 2025-02-12T15:45:50.538Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737664,0,547823,552482,629423,'F',TO_TIMESTAMP('2025-02-12 15:45:50.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2025-02-12 15:45:50.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.Beliebige HU kommissionieren
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowPickingAnyHU
-- 2025-02-12T15:46:33.573Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737668,0,547823,552482,629424,'F',TO_TIMESTAMP('2025-02-12 15:46:33.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Beliebige HU kommissionieren',20,0,0,TO_TIMESTAMP('2025-02-12 15:46:33.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.HU immer splitten
-- Column: MobileUI_UserProfile_Picking_Job.IsAlwaysSplitHUsEnabled
-- 2025-02-12T15:47:30.536Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737670,0,547823,552482,629425,'F',TO_TIMESTAMP('2025-02-12 15:47:30.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn diese Option aktiviert ist, wird die ausgewählte HU vor der Kommissionierung immer aufgeteilt.','Y','N','Y','N','N','HU immer splitten',30,0,0,TO_TIMESTAMP('2025-02-12 15:47:30.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.Kommissionieren mit LU
-- Column: MobileUI_UserProfile_Picking_Job.IsPickingWithNewLU
-- 2025-02-12T15:48:11.477Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737673,0,547823,552482,629426,'F',TO_TIMESTAMP('2025-02-12 15:48:11.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Kommissionieren mit LU',40,0,0,TO_TIMESTAMP('2025-02-12 15:48:11.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.Allow new TU
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowNewTU
-- 2025-02-12T15:48:21.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737667,0,547823,552482,629427,'F',TO_TIMESTAMP('2025-02-12 15:48:21.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Allow new TU',50,0,0,TO_TIMESTAMP('2025-02-12 15:48:21.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.TU Catch weight erlauben
-- Column: MobileUI_UserProfile_Picking_Job.IsCatchWeightTUPickingEnabled
-- 2025-02-12T15:48:48.466Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737671,0,547823,552482,629428,'F',TO_TIMESTAMP('2025-02-12 15:48:48.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiviert und in der Auftragszeile eine Packvorschrift enthalten ist, wird beim kommissionieren von Catch Weight angenommen dass es sich um eine TU handelt. Andernfalls geht metasfresh von einer CU aus','Wenn aktiviert und in der Auftragszeile eine Packvorschrift enthalten ist, wird beim kommissionieren von Catch Weight angenommen dass es sich um eine TU handelt. Andernfalls geht metasfresh von einer CU aus','Y','N','Y','N','N','TU Catch weight erlauben',60,0,0,TO_TIMESTAMP('2025-02-12 15:48:48.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.CU-TU Menge aus Auftrag verwenden
-- Column: MobileUI_UserProfile_Picking_Job.IsConsiderSalesOrderCapacity
-- 2025-02-12T15:49:03.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737672,0,547823,552482,629429,'F',TO_TIMESTAMP('2025-02-12 15:49:02.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn nicht aktiviert wird die Mengenzuordnung bei TU Catch Weight aus den Stammdaten des Produkts genommen','Wenn nicht aktiviert wird die Mengenzuordnung bei TU Catch Weight aus den Stammdaten des Produkts genommen','Y','N','Y','N','N','CU-TU Menge aus Auftrag verwenden',70,0,0,TO_TIMESTAMP('2025-02-12 15:49:02.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.Abweichende Menge erlauben
-- Column: MobileUI_UserProfile_Picking_Job.IsAllowSkippingRejectedReason
-- 2025-02-12T15:49:54.987Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737669,0,547823,552482,629430,'F',TO_TIMESTAMP('2025-02-12 15:49:54.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiviert darf eine geringere Menge kommissioniert werden. Es taucht dann eine weitere Option im Kommissionierdialog auf.','Wenn aktiviert darf eine geringere Menge kommissioniert werden. Es taucht dann eine weitere Option im Kommissionierdialog auf.','Y','N','Y','N','N','Abweichende Menge erlauben',80,0,0,TO_TIMESTAMP('2025-02-12 15:49:54.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> flags.Rückfragen bei Überkommissionierung
-- Column: MobileUI_UserProfile_Picking_Job.IsShowConfirmationPromptWhenOverPick
-- 2025-02-12T15:50:14.106Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737674,0,547823,552482,629431,'F',TO_TIMESTAMP('2025-02-12 15:50:13.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Rückfragen bei Überkommissionierung',90,0,0,TO_TIMESTAMP('2025-02-12 15:50:13.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-02-12T15:50:23.299Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547835,552483,TO_TIMESTAMP('2025-02-12 15:50:22.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',20,TO_TIMESTAMP('2025-02-12 15:50:22.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> org&client.Sektion
-- Column: MobileUI_UserProfile_Picking_Job.AD_Org_ID
-- 2025-02-12T15:50:31.125Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737663,0,547823,552483,629432,'F',TO_TIMESTAMP('2025-02-12 15:50:30.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2025-02-12 15:50:30.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 20 -> org&client.Mandant
-- Column: MobileUI_UserProfile_Picking_Job.AD_Client_ID
-- 2025-02-12T15:50:36.842Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,737662,0,547823,552483,629433,'F',TO_TIMESTAMP('2025-02-12 15:50:36.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2025-02-12 15:50:36.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10 -> default.Name
-- Column: MobileUI_UserProfile_Picking_Job.Name
-- 2025-02-12T15:51:28.019Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-02-12 15:51:28.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=629418
;

-- UI Element: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> main -> 10 -> default.Beschreibung
-- Column: MobileUI_UserProfile_Picking_Job.Description
-- 2025-02-12T15:51:28.024Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-02-12 15:51:28.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=629419
;

-- Field: Mobile UI Picking Job Options(541862,D) -> Mobile UI Picking Job Options(547823,D) -> Name
-- Column: MobileUI_UserProfile_Picking_Job.Name
-- 2025-02-12T15:51:43.399Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-02-12 15:51:43.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=737675
;

-- Name: Mobile UI Picking Job Options
-- Action Type: W
-- Window: Mobile UI Picking Job Options(541862,D)
-- 2025-02-12T15:52:33.399Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583482,542201,0,541862,TO_TIMESTAMP('2025-02-12 15:52:33.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','MobileUI_UserProfile_Picking_Job','Y','N','N','N','N','Mobile UI Picking Job Options',TO_TIMESTAMP('2025-02-12 15:52:33.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-12T15:52:33.401Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542201 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-02-12T15:52:33.404Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542201, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542201)
;

-- 2025-02-12T15:52:33.418Z
/* DDL */  select update_menu_translation_from_ad_element(583482)
;

-- Reordering children of `Settings`
-- Node name: `Product Planning (PP_Product_Planning)`
-- 2025-02-12T15:52:34.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541392 AND AD_Tree_ID=10
;

-- Node name: `Product Planning Schema (M_Product_PlanningSchema)`
-- 2025-02-12T15:52:34.015Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541035 AND AD_Tree_ID=10
;

-- Node name: `Product Planning (M_Product)`
-- 2025-02-12T15:52:34.017Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540820 AND AD_Tree_ID=10
;

-- Node name: `Create Default Product Planning Data (de.metas.product.process.M_ProductPlanning_Create_Default_ProductPlanningData)`
-- 2025-02-12T15:52:34.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541030 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflows (AD_Workflow)`
-- 2025-02-12T15:52:34.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540822 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Standard Activity`
-- 2025-02-12T15:52:34.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541409 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Workflow Transitions (AD_WF_NodeNext)`
-- 2025-02-12T15:52:34.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540823 AND AD_Tree_ID=10
;

-- Node name: `Resource (S_Resource)`
-- 2025-02-12T15:52:34.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540818 AND AD_Tree_ID=10
;

-- Node name: `Resource Type (S_ResourceType)`
-- 2025-02-12T15:52:34.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540819 AND AD_Tree_ID=10
;

-- Node name: `Component Generator (PP_ComponentGenerator)`
-- 2025-02-12T15:52:34.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541571 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Activity (AD_WF_Node)`
-- 2025-02-12T15:52:34.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541609 AND AD_Tree_ID=10
;

-- Node name: `Maturing Configuration (M_Maturing_Configuration)`
-- 2025-02-12T15:52:34.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542133 AND AD_Tree_ID=10
;

-- Node name: `MobileUI Manufacturing Configuration (MobileUI_MFG_Config)`
-- 2025-02-12T15:52:34.025Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542141 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI Picking Job Options`
-- 2025-02-12T15:52:34.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000071, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542201 AND AD_Tree_ID=10
;

-- Reordering children of `Picking`
-- Node name: `Mobile UI Picking Profile (MobileUI_UserProfile_Picking)`
-- 2025-02-12T15:52:47.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542119 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI Picking Job Options`
-- 2025-02-12T15:52:47.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542201 AND AD_Tree_ID=10
;

-- Node name: `Picking Job (M_Picking_Job)`
-- 2025-02-12T15:52:47.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541857 AND AD_Tree_ID=10
;

-- Node name: `Picking Job Step (M_Picking_Job_Step)`
-- 2025-02-12T15:52:47.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541858 AND AD_Tree_ID=10
;

-- Node name: `Print Picking Slot QR Codes (de.metas.picking.process.M_PickingSlot_PrintQRCodes)`
-- 2025-02-12T15:52:47.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541908 AND AD_Tree_ID=10
;

-- Reordering children of `Picking`
-- Node name: `Mobile UI Picking Job Options`
-- 2025-02-12T15:52:51.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542201 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI Picking Profile (MobileUI_UserProfile_Picking)`
-- 2025-02-12T15:52:51.826Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542119 AND AD_Tree_ID=10
;

-- Node name: `Picking Job (M_Picking_Job)`
-- 2025-02-12T15:52:51.827Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541857 AND AD_Tree_ID=10
;

-- Node name: `Picking Job Step (M_Picking_Job_Step)`
-- 2025-02-12T15:52:51.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541858 AND AD_Tree_ID=10
;

-- Node name: `Print Picking Slot QR Codes (de.metas.picking.process.M_PickingSlot_PrintQRCodes)`
-- 2025-02-12T15:52:51.829Z
UPDATE AD_TreeNodeMM SET Parent_ID=541856, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541908 AND AD_Tree_ID=10
;

