-- Run mode: SWING_CLIENT

-- Table: SEPA_Export_Line_Ref
-- 2025-12-15T16:09:09.472Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,Help,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542564,'A','N',TO_TIMESTAMP('2025-12-15 16:09:09.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','de.metas.payment.sepa','','N','Y','N','Y','Y','N','N','N','N','N',0,'SEPA  Export Line Reference','NP','L','SEPA_Export_Line_Ref','DTI',TO_TIMESTAMP('2025-12-15 16:09:09.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2025-12-15T16:09:09.477Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542564 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-12-15T16:09:09.616Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556572,TO_TIMESTAMP('2025-12-15 16:09:09.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table SEPA_Export_Line_Ref',1,'Y','N','Y','Y','SEPA_Export_Line_Ref',1000000,TO_TIMESTAMP('2025-12-15 16:09:09.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-15T16:09:09.633Z
CREATE SEQUENCE SEPA_EXPORT_LINE_REF_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: SEPA_Export_Line_Ref.Updated
-- 2025-12-15T16:10:22.664Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591721,607,0,16,542564,'Updated',TO_TIMESTAMP('2025-12-15 16:10:22.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.payment.sepa',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2025-12-15 16:10:22.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:10:22.668Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591721 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:10:22.676Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: SEPA_Export_Line_Ref.UpdatedBy
-- 2025-12-15T16:10:23.461Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591722,608,0,18,110,542564,'UpdatedBy',TO_TIMESTAMP('2025-12-15 16:10:23.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.payment.sepa',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2025-12-15 16:10:23.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:10:23.462Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591722 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:10:23.464Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-12-15T16:10:24.328Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584371,0,'SEPA_Export_Line_Ref_ID',TO_TIMESTAMP('2025-12-15 16:10:24.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.payment.sepa','Y','SEPA  Export Line Reference','SEPA  Export Line Reference',TO_TIMESTAMP('2025-12-15 16:10:24.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-15T16:10:24.331Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584371 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SEPA_Export_Line_Ref.SEPA_Export_Line_Ref_ID
-- 2025-12-15T16:10:25.049Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591723,584371,0,13,542564,'SEPA_Export_Line_Ref_ID',TO_TIMESTAMP('2025-12-15 16:10:24.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.payment.sepa',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','SEPA  Export Line Reference',0,0,TO_TIMESTAMP('2025-12-15 16:10:24.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:10:25.052Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591723 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:10:25.055Z
/* DDL */  select update_Column_Translation_From_AD_Element(584371)
;

-- Column: SEPA_Export_Line_Ref.AD_Client_ID
-- 2025-12-15T16:11:08.144Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591724,102,0,19,542564,'AD_Client_ID',TO_TIMESTAMP('2025-12-15 16:11:07.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','de.metas.payment.sepa',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2025-12-15 16:11:07.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:11:08.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591724 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:11:08.153Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: SEPA_Export_Line_Ref.AD_Org_ID
-- 2025-12-15T16:11:08.973Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591725,113,0,30,542564,'AD_Org_ID',TO_TIMESTAMP('2025-12-15 16:11:08.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','de.metas.payment.sepa',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2025-12-15 16:11:08.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:11:08.974Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591725 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:11:08.976Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: SEPA_Export_Line_Ref.Created
-- 2025-12-15T16:11:09.721Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591726,245,0,16,542564,'Created',TO_TIMESTAMP('2025-12-15 16:11:09.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.payment.sepa',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2025-12-15 16:11:09.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:11:09.722Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591726 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:11:09.724Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: SEPA_Export_Line_Ref.CreatedBy
-- 2025-12-15T16:11:10.483Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591727,246,0,18,110,542564,'CreatedBy',TO_TIMESTAMP('2025-12-15 16:11:10.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.payment.sepa',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2025-12-15 16:11:10.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:11:10.484Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591727 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:11:10.486Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: SEPA_Export_Line_Ref.IsActive
-- 2025-12-15T16:11:11.266Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591728,348,0,20,542564,'IsActive',TO_TIMESTAMP('2025-12-15 16:11:11.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','de.metas.payment.sepa',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2025-12-15 16:11:11.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:11:11.267Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591728 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:11:11.269Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: SEPA_Export_Line.SEPA_Export_Line_ID
-- 2025-12-15T16:13:54.462Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-12-15 16:13:54.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550918
;

-- Column: SEPA_Export_Line_Ref.AD_Table_ID
-- 2025-12-15T16:14:26.144Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591741,126,0,19,542564,'AD_Table_ID',TO_TIMESTAMP('2025-12-15 16:14:25.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Database Table information','de.metas.payment.sepa',0,10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','DB-Tabelle',0,0,TO_TIMESTAMP('2025-12-15 16:14:25.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:14:26.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:14:26.152Z
/* DDL */  select update_Column_Translation_From_AD_Element(126)
;

-- Column: SEPA_Export_Line_Ref.Amt
-- 2025-12-15T16:14:26.961Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591742,160,0,12,542564,'Amt',TO_TIMESTAMP('2025-12-15 16:14:26.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Betrag','de.metas.payment.sepa',0,20,'Betrag','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Betrag',0,0,TO_TIMESTAMP('2025-12-15 16:14:26.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:14:26.964Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:14:26.967Z
/* DDL */  select update_Column_Translation_From_AD_Element(160)
;

-- Column: SEPA_Export_Line_Ref.C_Currency_ID
-- 2025-12-15T16:14:29.348Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591745,193,0,19,542564,'C_Currency_ID',TO_TIMESTAMP('2025-12-15 16:14:29.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Die Währung für diesen Eintrag','de.metas.payment.sepa',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Währung',0,0,TO_TIMESTAMP('2025-12-15 16:14:29.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:14:29.350Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:14:29.352Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- Column: SEPA_Export_Line_Ref.Description
-- 2025-12-15T16:14:30.065Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591746,275,0,10,542564,'Description',TO_TIMESTAMP('2025-12-15 16:14:29.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.payment.sepa',0,256,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,0,TO_TIMESTAMP('2025-12-15 16:14:29.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:14:30.067Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:14:30.071Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;


-- Column: SEPA_Export_Line_Ref.Record_ID
-- 2025-12-15T16:14:33.898Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591751,538,0,28,542564,'Record_ID',TO_TIMESTAMP('2025-12-15 16:14:33.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Direct internal record ID','de.metas.payment.sepa',0,20,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Datensatz-ID',0,0,TO_TIMESTAMP('2025-12-15 16:14:33.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:14:33.901Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591751 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:14:33.904Z
/* DDL */  select update_Column_Translation_From_AD_Element(538)
;

-- Column: SEPA_Export_Line_Ref.SEPA_Export_ID
-- 2025-12-15T16:14:34.806Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591752,542477,0,30,540510,542564,'SEPA_Export_ID',TO_TIMESTAMP('2025-12-15 16:14:34.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.payment.sepa',0,10,'Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','SEPA Export',0,0,TO_TIMESTAMP('2025-12-15 16:14:34.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:14:34.808Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:14:34.810Z
/* DDL */  select update_Column_Translation_From_AD_Element(542477)
;

-- Column: SEPA_Export_Line_Ref.StructuredRemittanceInfo
-- 2025-12-15T16:14:37.419Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591755,542618,0,10,542564,'StructuredRemittanceInfo',TO_TIMESTAMP('2025-12-15 16:14:37.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Structured Remittance Information','de.metas.payment.sepa',0,35,'As of task 07789 this field is used for the ESR reference string.
IMPORTANT: at least for pain.001.001.03, the max length of this field is 35','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','StructuredRemittanceInfo',0,0,TO_TIMESTAMP('2025-12-15 16:14:37.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:14:37.420Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591755 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:14:37.422Z
/* DDL */  select update_Column_Translation_From_AD_Element(542618)
;

-- Column: SEPA_Export_Line_Ref.SEPA_Export_Line_ID
-- 2025-12-15T16:22:03.195Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591757,542479,0,19,542564,'XX','SEPA_Export_Line_ID',TO_TIMESTAMP('2025-12-15 16:22:03.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.payment.sepa',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'SEPA_Export_Line_ID',0,0,TO_TIMESTAMP('2025-12-15 16:22:03.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-15T16:22:03.197Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591757 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-15T16:22:03.203Z
/* DDL */  select update_Column_Translation_From_AD_Element(542479)
;

-- 2025-12-15T16:22:04.160Z
/* DDL */ CREATE TABLE public.SEPA_Export_Line_Ref
(
    AD_Client_ID               NUMERIC(10)                            NOT NULL,
    AD_Org_ID                  NUMERIC(10)                            NOT NULL,
    AD_Table_ID                NUMERIC(10)                            NOT NULL,
    Amt                        NUMERIC                                NOT NULL,
    Created                    TIMESTAMP WITH TIME ZONE               NOT NULL,
    CreatedBy                  NUMERIC(10)                            NOT NULL,
    Description                VARCHAR(256),
    IsActive                   CHAR(1) CHECK (IsActive IN ('Y', 'N')) NOT NULL,
    Record_ID                  NUMERIC(10)                            NOT NULL,
    SEPA_Export_ID             NUMERIC(10)                            NOT NULL,
    SEPA_Export_Line_ID        NUMERIC(10)                            NOT NULL,
    SEPA_Export_Line_Ref_ID    NUMERIC(10)                            NOT NULL,
    SEPA_Export_Line_Retry_ID  NUMERIC(10),
    Updated                    TIMESTAMP WITH TIME ZONE               NOT NULL,
    UpdatedBy                  NUMERIC(10)                            NOT NULL,
    CONSTRAINT ADTable_SEPAExportLineRef FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT SEPAExport_SEPAExportLineRef FOREIGN KEY (SEPA_Export_ID) REFERENCES public.SEPA_Export DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT SEPAExportLine_SEPAExportLineRef FOREIGN KEY (SEPA_Export_Line_ID) REFERENCES public.SEPA_Export_Line DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT SEPA_Export_Line_Ref_Key PRIMARY KEY (SEPA_Export_Line_Ref_ID),
    CONSTRAINT SEPAExportLineRetry_SEPAExportLineRef FOREIGN KEY (SEPA_Export_Line_Retry_ID) REFERENCES public.SEPA_Export_Line DEFERRABLE INITIALLY DEFERRED
)
;

-- Column: SEPA_Export_Line_Ref.SEPA_Export_Line_ID
-- 2025-12-15T16:22:49.342Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-12-15 16:22:49.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591757
;

-- 2025-12-15T16:22:50.488Z
INSERT INTO t_alter_column values('sepa_export_line_ref','SEPA_Export_Line_ID','NUMERIC(10)',null,null)
;

-- Run mode: SWING_CLIENT

-- Column: SEPA_Export_Line.Record_ID
-- 2025-12-16T11:36:03.115Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-12-16 11:36:03.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550925
;

-- 2025-12-16T11:36:04.827Z
INSERT INTO t_alter_column values('sepa_export_line','Record_ID','NUMERIC(10)',null,null)
;

-- 2025-12-16T11:36:04.837Z
INSERT INTO t_alter_column values('sepa_export_line','Record_ID',null,'NULL',null)
;

-- Column: SEPA_Export_Line.AD_Table_ID
-- 2025-12-16T11:36:30.381Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-12-16 11:36:30.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=550919
;

-- 2025-12-16T11:36:31.351Z
INSERT INTO t_alter_column values('sepa_export_line','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2025-12-16T11:36:31.356Z
INSERT INTO t_alter_column values('sepa_export_line','AD_Table_ID',null,'NULL',null)
;

-- 2025-12-16T13:05:06.010Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584372,0,'IsGroupLine',TO_TIMESTAMP('2025-12-16 13:05:05.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppentransaktionen pro Geschäftspartner','D','Y','Gruppenlinie','Gruppenlinie',TO_TIMESTAMP('2025-12-16 13:05:05.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T13:05:06.019Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584372 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsGroupLine
-- 2025-12-16T13:05:19.440Z
UPDATE AD_Element_Trl SET Description='Group Transactions per Business Partner', Name='Group Line', PrintName='Group Line',Updated=TO_TIMESTAMP('2025-12-16 13:05:19.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584372 AND AD_Language='en_US'
;

-- 2025-12-16T13:05:19.440Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-12-16T13:05:20.152Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584372,'en_US')
;

-- Column: SEPA_Export_Line.IsGroupLine
-- 2025-12-16T13:06:13.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591759,584372,0,20,540604,'XX','IsGroupLine',TO_TIMESTAMP('2025-12-16 13:06:13.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','Gruppentransaktionen pro Geschäftspartner','de.metas.payment.sepa',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Gruppenlinie',0,0,TO_TIMESTAMP('2025-12-16 13:06:13.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-16T13:06:13.868Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-16T13:06:13.885Z
/* DDL */  select update_Column_Translation_From_AD_Element(584372)
;

-- 2025-12-16T13:06:14.731Z
/* DDL */ SELECT public.db_alter_table('SEPA_Export_Line','ALTER TABLE public.SEPA_Export_Line ADD COLUMN IsGroupLine CHAR(1) CHECK (IsGroupLine IN (''Y'',''N''))')
;

-- Run mode: SWING_CLIENT

-- Process: C_PaySelection_SEPA_XmlExport(de.metas.payment.sepa.process.C_PaySelection_SEPA_XmlExport)
-- ParameterName: IsGroupTransactions
-- 2025-12-16T15:23:46.085Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540461,543086,20,'IsGroupTransactions',TO_TIMESTAMP('2025-12-16 15:23:45.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Ermöglicht die Gruppierung mehrerer Rechnungen pro Lieferant zu einer einzigen aggregierten SEPA-Gutschiftung. Wenn aktiviert (Standard: Ja), erstellt der SEPA-Export eine Transaktion pro Partner statt pro Rechnung, wodurch Bankgebühren und Unübersichtlichkeit in Kontoauszügen reduziert werden. Die interne Nachverfolgbarkeit bleibt in metasfresh vollständig erhalten, ergänzt durch einen detaillierten Excel-Prüfbericht mit rechnungsgenauen Aufschlüsselungen pro Zahlung.','de.metas.payment.sepa',0,'Y','N','Y','N','N','N','Transaktionen pro Partner gruppieren',20,TO_TIMESTAMP('2025-12-16 15:23:45.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-16T15:23:46.102Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543086 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2025-12-16T15:24:04.159Z
UPDATE AD_Process_Para_Trl SET Description='Enables grouping of multiple invoices per vendor into a single aggregated SEPA credit transfer. When checked (default: Yes), the SEPA export creates one transaction per partner instead of one per invoice, reducing bank fees and statement clutter. Internal traceability remains fully preserved in metasfresh, with a detailed Excel audit report providing invoice-level breakdowns per payment.', Name='Group Transactions per Partner',Updated=TO_TIMESTAMP('2025-12-16 15:24:04.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_Para_ID=543086
;

-- 2025-12-16T15:24:04.160Z
UPDATE AD_Process_Para base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Process_Para_Trl trl  WHERE trl.AD_Process_Para_ID=base.AD_Process_Para_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Process: C_PaySelection_SEPA_XmlExport(de.metas.payment.sepa.process.C_PaySelection_SEPA_XmlExport)
-- ParameterName: IsGroupTransactions
-- 2025-12-16T15:31:04.598Z
UPDATE AD_Process_Para SET DisplayLogic='@PaySelectionTrxType/X@=''CT''',Updated=TO_TIMESTAMP('2025-12-16 15:31:04.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543086
;

-- Run mode: SWING_CLIENT

-- Column: SEPA_Export_Line.IsGroupLine
-- 2025-12-17T10:48:17.845Z
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2025-12-17 10:48:17.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591759
;

-- 2025-12-17T10:48:19.396Z
INSERT INTO t_alter_column values('sepa_export_line','IsGroupLine','CHAR(1)',null,'N')
;
