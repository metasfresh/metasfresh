-- Run mode: SWING_CLIENT

-- Table: ExternalSystem_Outbound_Endpoint
-- 2025-11-03T14:37:38.915Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542551,'A','N',TO_TIMESTAMP('2025-11-03 14:37:38.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'A','de.metas.externalsystem','N','Y','N','Y','Y','N','N','N','N','N',0,'Externer System-Ausgangsendpunkt','NP','L','ExternalSystem_Outbound_Endpoint','DTI',TO_TIMESTAMP('2025-11-03 14:37:38.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'A')
;

-- 2025-11-03T14:37:38.915Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542551 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2025-11-03T14:37:39.041Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556558,TO_TIMESTAMP('2025-11-03 14:37:38.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,50000,'Table ExternalSystem_Outbound_Endpoint',1,'Y','N','Y','Y','ExternalSystem_Outbound_Endpoint',1000000,TO_TIMESTAMP('2025-11-03 14:37:38.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T14:37:39.065Z
CREATE SEQUENCE EXTERNALSYSTEM_OUTBOUND_ENDPOINT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: ExternalSystem_Outbound_Endpoint.AD_Client_ID
-- 2025-11-03T14:37:46.043Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591468,102,0,19,542551,'AD_Client_ID',TO_TIMESTAMP('2025-11-03 14:37:45.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2025-11-03 14:37:45.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:46.043Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591468 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:46.090Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: ExternalSystem_Outbound_Endpoint.AD_Org_ID
-- 2025-11-03T14:37:47.356Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591469,113,0,30,542551,'AD_Org_ID',TO_TIMESTAMP('2025-11-03 14:37:46.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2025-11-03 14:37:46.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:47.356Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591469 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:47.356Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: ExternalSystem_Outbound_Endpoint.Created
-- 2025-11-03T14:37:48.463Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591470,245,0,16,542551,'Created',TO_TIMESTAMP('2025-11-03 14:37:48.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2025-11-03 14:37:48.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:48.479Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591470 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:48.479Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: ExternalSystem_Outbound_Endpoint.CreatedBy
-- 2025-11-03T14:37:49.871Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591471,246,0,18,110,542551,'CreatedBy',TO_TIMESTAMP('2025-11-03 14:37:49.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2025-11-03 14:37:49.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:49.871Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591471 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:49.871Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: ExternalSystem_Outbound_Endpoint.IsActive
-- 2025-11-03T14:37:50.934Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591472,348,0,20,542551,'IsActive',TO_TIMESTAMP('2025-11-03 14:37:50.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2025-11-03 14:37:50.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:50.934Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591472 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:50.934Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: ExternalSystem_Outbound_Endpoint.Updated
-- 2025-11-03T14:37:52.043Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591473,607,0,16,542551,'Updated',TO_TIMESTAMP('2025-11-03 14:37:51.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2025-11-03 14:37:51.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:52.043Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:52.043Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: ExternalSystem_Outbound_Endpoint.UpdatedBy
-- 2025-11-03T14:37:53.485Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591474,608,0,18,110,542551,'UpdatedBy',TO_TIMESTAMP('2025-11-03 14:37:53.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2025-11-03 14:37:53.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:53.487Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:53.490Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2025-11-03T14:37:54.270Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584191,0,'ExternalSystem_Outbound_Endpoint_ID',TO_TIMESTAMP('2025-11-03 14:37:54.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','Externer System-Ausgangsendpunkt','Externer System-Ausgangsendpunkt',TO_TIMESTAMP('2025-11-03 14:37:54.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T14:37:54.270Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584191 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Outbound_Endpoint.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T14:37:55.194Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591475,584191,0,13,542551,'ExternalSystem_Outbound_Endpoint_ID',TO_TIMESTAMP('2025-11-03 14:37:54.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Externer System-Ausgangsendpunkt',0,0,TO_TIMESTAMP('2025-11-03 14:37:54.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:37:55.194Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591475 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:37:55.209Z
/* DDL */  select update_Column_Translation_From_AD_Element(584191)
;

-- 2025-11-03T14:38:31.809Z
UPDATE AD_Table_Trl SET Name='External System Outbound Endpoint',Updated=TO_TIMESTAMP('2025-11-03 14:38:31.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542551
;

-- 2025-11-03T14:38:31.825Z
UPDATE AD_Table base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Table_Trl trl  WHERE trl.AD_Table_ID=base.AD_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Column: ExternalSystem_Outbound_Endpoint.Description
-- 2025-11-03T14:38:50.353Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591476,275,0,10,542551,'XX','Description',TO_TIMESTAMP('2025-11-03 14:38:50.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2025-11-03 14:38:50.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:38:50.368Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591476 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:38:50.368Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2025-11-03T14:38:51.302Z
/* DDL */ CREATE TABLE public.ExternalSystem_Outbound_Endpoint (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), ExternalSystem_Outbound_Endpoint_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Outbound_Endpoint_Key PRIMARY KEY (ExternalSystem_Outbound_Endpoint_ID))
;

-- Name: EndpointType
-- 2025-11-03T14:46:33.263Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542016,TO_TIMESTAMP('2025-11-03 14:46:33.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','N','EndpointType',TO_TIMESTAMP('2025-11-03 14:46:33.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-11-03T14:46:33.278Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542016 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: EndpointType
-- Value: HTTP
-- ValueName: HTTP
-- 2025-11-03T14:47:37.309Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544050,542016,TO_TIMESTAMP('2025-11-03 14:47:37.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','HTTP',TO_TIMESTAMP('2025-11-03 14:47:37.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HTTP','HTTP')
;

-- 2025-11-03T14:47:37.309Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544050 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: EndpointType
-- Value: SFTP
-- ValueName: SFTP
-- 2025-11-03T14:48:07.294Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544051,542016,TO_TIMESTAMP('2025-11-03 14:48:07.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','SFTP',TO_TIMESTAMP('2025-11-03 14:48:07.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SFTP','SFTP')
;

-- 2025-11-03T14:48:07.309Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544051 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: EndpointType
-- Value: FILE
-- ValueName: FILE
-- 2025-11-03T14:48:21.413Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544052,542016,TO_TIMESTAMP('2025-11-03 14:48:21.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','FILE',TO_TIMESTAMP('2025-11-03 14:48:21.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'FILE','FILE')
;

-- 2025-11-03T14:48:21.413Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544052 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: EndpointType
-- Value: EMAIL
-- ValueName: EMAIL
-- 2025-11-03T14:48:37.887Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544053,542016,TO_TIMESTAMP('2025-11-03 14:48:37.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','EMAIL',TO_TIMESTAMP('2025-11-03 14:48:37.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMAIL','EMAIL')
;

-- 2025-11-03T14:48:37.903Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544053 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: EndpointType
-- Value: TCP
-- ValueName: TCP
-- 2025-11-03T14:48:46.403Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544054,542016,TO_TIMESTAMP('2025-11-03 14:48:46.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','TCP',TO_TIMESTAMP('2025-11-03 14:48:46.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'TCP','TCP')
;

-- 2025-11-03T14:48:46.403Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544054 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: ExternalSystem_Outbound_Endpoint.Type
-- 2025-11-03T14:49:30.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591477,600,0,17,542016,542551,'XX','Type',TO_TIMESTAMP('2025-11-03 14:49:29.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.externalsystem',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Art',0,0,TO_TIMESTAMP('2025-11-03 14:49:29.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:49:30.072Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591477 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:49:30.072Z
/* DDL */  select update_Column_Translation_From_AD_Element(600)
;

-- 2025-11-03T14:49:30.854Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN Type VARCHAR(10) NOT NULL')
;

-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpEP
-- 2025-11-03T14:51:04.745Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591478,584104,0,10,542551,'XX','OutboundHttpEP',TO_TIMESTAMP('2025-11-03 14:51:04.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','URL, an die die Daten gesendet werden','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ausgehender HTTP-Endpunkt',0,0,TO_TIMESTAMP('2025-11-03 14:51:04.579000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:51:04.748Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591478 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:51:04.748Z
/* DDL */  select update_Column_Translation_From_AD_Element(584104)
;

-- 2025-11-03T14:51:05.549Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN OutboundHttpEP VARCHAR(255) NOT NULL')
;

-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpMethod
-- 2025-11-03T14:51:22.248Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591479,584106,0,10,542551,'XX','OutboundHttpMethod',TO_TIMESTAMP('2025-11-03 14:51:22.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','HTTP-Methode, die beim Senden der Daten verwendet wird (z. B. POST, PUT)','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ausgehende HTTP-Methode',0,0,TO_TIMESTAMP('2025-11-03 14:51:22.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T14:51:22.248Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T14:51:22.431Z
/* DDL */  select update_Column_Translation_From_AD_Element(584106)
;

-- 2025-11-03T14:51:23.300Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN OutboundHttpMethod VARCHAR(255) NOT NULL')
;

-- 2025-11-03T16:54:21.377Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584192,0,'AuthType',TO_TIMESTAMP('2025-11-03 16:54:21.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','Authentifizierungstyp','Authentifizierungstyp',TO_TIMESTAMP('2025-11-03 16:54:21.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T16:54:21.377Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584192 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AuthType
-- 2025-11-03T16:54:30.832Z
UPDATE AD_Element_Trl SET Name='Authentication Type', PrintName='Authentication Type',Updated=TO_TIMESTAMP('2025-11-03 16:54:30.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584192 AND AD_Language='en_US'
;

-- 2025-11-03T16:54:30.832Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-03T16:54:31.524Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584192,'en_US')
;

-- Name: AuthType
-- 2025-11-03T16:56:01.791Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542017,TO_TIMESTAMP('2025-11-03 16:56:01.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','N','AuthType',TO_TIMESTAMP('2025-11-03 16:56:01.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-11-03T16:56:01.806Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542017 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AuthType
-- Value: Token
-- ValueName: Token-basiert
-- 2025-11-03T16:56:47.629Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544055,542017,TO_TIMESTAMP('2025-11-03 16:56:47.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','Token-basiert',TO_TIMESTAMP('2025-11-03 16:56:47.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Token','Token-basiert')
;

-- 2025-11-03T16:56:47.629Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544055 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: AuthType -> Token_Token-basiert
-- 2025-11-03T16:56:54.106Z
UPDATE AD_Ref_List_Trl SET Name='Token-Based',Updated=TO_TIMESTAMP('2025-11-03 16:56:54.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544055
;

-- 2025-11-03T16:56:54.106Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: AuthType
-- Value: Basic
-- ValueName: Basisauthentifizierung
-- 2025-11-03T16:57:23.994Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544056,542017,TO_TIMESTAMP('2025-11-03 16:57:23.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','Basisauthentifizierung',TO_TIMESTAMP('2025-11-03 16:57:23.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Basic','Basisauthentifizierung')
;

-- 2025-11-03T16:57:23.994Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544056 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: AuthType
-- Value: OAuth
-- ValueName: OAuth
-- 2025-11-03T16:57:48.197Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544057,542017,TO_TIMESTAMP('2025-11-03 16:57:48.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','OAuth',TO_TIMESTAMP('2025-11-03 16:57:48.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'OAuth','OAuth')
;

-- 2025-11-03T16:57:48.197Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544057 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: AuthType -> Basic_Basisauthentifizierung
-- 2025-11-03T16:58:01.533Z
UPDATE AD_Ref_List_Trl SET Name='Basic Auth',Updated=TO_TIMESTAMP('2025-11-03 16:58:01.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544056
;

-- 2025-11-03T16:58:01.533Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Column: ExternalSystem_Outbound_Endpoint.AuthType
-- 2025-11-03T16:58:42.612Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591481,584192,0,17,542017,542551,'XX','AuthType',TO_TIMESTAMP('2025-11-03 16:58:42.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Authentifizierungstyp',0,0,TO_TIMESTAMP('2025-11-03 16:58:42.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T16:58:42.612Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T16:58:42.612Z
/* DDL */  select update_Column_Translation_From_AD_Element(584192)
;

-- 2025-11-03T16:58:43.388Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN AuthType VARCHAR(10) NOT NULL')
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> main -> 10 -> endpoint.Ausgehender HTTP-Endpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpEP
-- 2025-11-03T16:59:50.008Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637783
;

-- 2025-11-03T16:59:50.039Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754852
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> Ausgehender HTTP-Endpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpEP
-- 2025-11-03T16:59:50.055Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754852
;

-- 2025-11-03T16:59:50.055Z
DELETE FROM AD_Field WHERE AD_Field_ID=754852
;

-- UI Element: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> main -> 10 -> endpoint.Ausgehender HTTP-Endpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpEP
-- 2025-11-03T16:59:50.067Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637857
;

-- 2025-11-03T16:59:50.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754960
;

-- Field: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> Ausgehender HTTP-Endpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpEP
-- 2025-11-03T16:59:50.067Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754960
;

-- 2025-11-03T16:59:50.067Z
DELETE FROM AD_Field WHERE AD_Field_ID=754960
;

-- 2025-11-03T16:59:50.067Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedExportConversion','ALTER TABLE ExternalSystem_Config_ScriptedExportConversion DROP COLUMN IF EXISTS OutboundHttpEP')
;

-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpEP
-- 2025-11-03T16:59:50.099Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591296
;

-- 2025-11-03T16:59:50.099Z
DELETE FROM AD_Column WHERE AD_Column_ID=591296
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> main -> 10 -> endpoint.Ausgehende HTTP-Methode
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpMethod
-- 2025-11-03T16:59:59.292Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637785
;

-- 2025-11-03T16:59:59.292Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754854
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> Ausgehende HTTP-Methode
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpMethod
-- 2025-11-03T16:59:59.308Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754854
;

-- 2025-11-03T16:59:59.308Z
DELETE FROM AD_Field WHERE AD_Field_ID=754854
;

-- UI Element: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> main -> 10 -> endpoint.Ausgehende HTTP-Methode
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpMethod
-- 2025-11-03T16:59:59.323Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637859
;

-- 2025-11-03T16:59:59.323Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754962
;

-- Field: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> Ausgehende HTTP-Methode
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpMethod
-- 2025-11-03T16:59:59.323Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754962
;

-- 2025-11-03T16:59:59.323Z
DELETE FROM AD_Field WHERE AD_Field_ID=754962
;

-- 2025-11-03T16:59:59.323Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedExportConversion','ALTER TABLE ExternalSystem_Config_ScriptedExportConversion DROP COLUMN IF EXISTS OutboundHttpMethod')
;

-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpMethod
-- 2025-11-03T16:59:59.339Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591298
;

-- 2025-11-03T16:59:59.339Z
DELETE FROM AD_Column WHERE AD_Column_ID=591298
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> main -> 10 -> endpoint.Ausgehendes HTTP-Token
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpToken
-- 2025-11-03T17:00:05.799Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637784
;

-- 2025-11-03T17:00:05.799Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754853
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> Ausgehendes HTTP-Token
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpToken
-- 2025-11-03T17:00:05.799Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754853
;

-- 2025-11-03T17:00:05.815Z
DELETE FROM AD_Field WHERE AD_Field_ID=754853
;

-- UI Element: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> main -> 10 -> endpoint.Ausgehendes HTTP-Token
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpToken
-- 2025-11-03T17:00:05.815Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637858
;

-- 2025-11-03T17:00:05.830Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754961
;

-- Field: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> Ausgehendes HTTP-Token
-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpToken
-- 2025-11-03T17:00:05.830Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754961
;

-- 2025-11-03T17:00:05.830Z
DELETE FROM AD_Field WHERE AD_Field_ID=754961
;

-- 2025-11-03T17:00:05.830Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedExportConversion','ALTER TABLE ExternalSystem_Config_ScriptedExportConversion DROP COLUMN IF EXISTS OutboundHttpToken')
;

-- Column: ExternalSystem_Config_ScriptedExportConversion.OutboundHttpToken
-- 2025-11-03T17:00:05.846Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591297
;

-- 2025-11-03T17:00:05.846Z
DELETE FROM AD_Column WHERE AD_Column_ID=591297
;

-- Column: ExternalSystem_Outbound_Endpoint.Value
-- 2025-11-03T17:01:31.609Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591482,620,0,10,542551,'XX','Value',TO_TIMESTAMP('2025-11-03 17:01:31.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','de.metas.externalsystem',0,255,'E','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','Y',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2025-11-03 17:01:31.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T17:01:31.614Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591482 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T17:01:31.748Z
/* DDL */  select update_Column_Translation_From_AD_Element(620)
;

-- 2025-11-03T17:01:32.779Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN Value VARCHAR(255) NOT NULL')
;

-- Column: ExternalSystem_Config_ScriptedExportConversion.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T17:02:28.460Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591483,584191,0,30,542541,'XX','ExternalSystem_Outbound_Endpoint_ID',TO_TIMESTAMP('2025-11-03 17:02:28.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Externer System-Ausgangsendpunkt',0,0,TO_TIMESTAMP('2025-11-03 17:02:28.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T17:02:28.460Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591483 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T17:02:28.460Z
/* DDL */  select update_Column_Translation_From_AD_Element(584191)
;

-- 2025-11-03T17:02:29.351Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedExportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedExportConversion ADD COLUMN ExternalSystem_Outbound_Endpoint_ID NUMERIC(10) NOT NULL')
;

-- 2025-11-03T17:02:29.351Z
ALTER TABLE ExternalSystem_Config_ScriptedExportConversion ADD CONSTRAINT ExternalSystemOutboundEndpoint_ExternalSystemConfigScriptedExportConve FOREIGN KEY (ExternalSystem_Outbound_Endpoint_ID) REFERENCES public.ExternalSystem_Outbound_Endpoint DEFERRABLE INITIALLY DEFERRED
;

-- Window: Externer System-Ausgangsendpunkt, InternalName=null
-- 2025-11-03T17:03:45.029Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584191,0,541967,TO_TIMESTAMP('2025-11-03 17:03:44.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','Y','N','N','N','N','N','N','Y','Externer System-Ausgangsendpunkt','N',TO_TIMESTAMP('2025-11-03 17:03:44.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-11-03T17:03:45.029Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541967 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-11-03T17:03:45.029Z
/* DDL */  select update_window_translation_from_ad_element(584191)
;

-- 2025-11-03T17:03:45.029Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541967
;

-- 2025-11-03T17:03:45.029Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541967)
;

-- Tab: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt
-- Table: ExternalSystem_Outbound_Endpoint
-- 2025-11-03T17:04:05.873Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584191,0,548506,542551,541967,'Y',TO_TIMESTAMP('2025-11-03 17:04:05.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','N','N','A','ExternalSystem_Outbound_Endpoint','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Externer System-Ausgangsendpunkt','N',10,0,TO_TIMESTAMP('2025-11-03 17:04:05.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:05.873Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548506 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-11-03T17:04:05.873Z
/* DDL */  select update_tab_translation_from_ad_element(584191)
;

-- 2025-11-03T17:04:05.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548506)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Outbound_Endpoint.AD_Client_ID
-- 2025-11-03T17:04:11.031Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591468,755934,0,548506,TO_TIMESTAMP('2025-11-03 17:04:10.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-11-03 17:04:10.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:11.047Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:11.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-11-03T17:04:11.231Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755934
;

-- 2025-11-03T17:04:11.231Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755934)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Outbound_Endpoint.AD_Org_ID
-- 2025-11-03T17:04:11.337Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591469,755935,0,548506,TO_TIMESTAMP('2025-11-03 17:04:11.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-11-03 17:04:11.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:11.337Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:11.337Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-11-03T17:04:11.521Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755935
;

-- 2025-11-03T17:04:11.521Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755935)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Outbound_Endpoint.IsActive
-- 2025-11-03T17:04:11.632Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591472,755936,0,548506,TO_TIMESTAMP('2025-11-03 17:04:11.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-11-03 17:04:11.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:11.632Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:11.632Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-11-03T17:04:11.832Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755936
;

-- 2025-11-03T17:04:11.832Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755936)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt
-- Column: ExternalSystem_Outbound_Endpoint.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T17:04:11.938Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591475,755937,0,548506,TO_TIMESTAMP('2025-11-03 17:04:11.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Externer System-Ausgangsendpunkt',TO_TIMESTAMP('2025-11-03 17:04:11.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:11.938Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:11.938Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584191)
;

-- 2025-11-03T17:04:11.938Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755937
;

-- 2025-11-03T17:04:11.953Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755937)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Beschreibung
-- Column: ExternalSystem_Outbound_Endpoint.Description
-- 2025-11-03T17:04:12.049Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591476,755938,0,548506,TO_TIMESTAMP('2025-11-03 17:04:11.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-11-03 17:04:11.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:12.049Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:12.060Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-11-03T17:04:12.170Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755938
;

-- 2025-11-03T17:04:12.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755938)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Art
-- Column: ExternalSystem_Outbound_Endpoint.Type
-- 2025-11-03T17:04:12.292Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591477,755939,0,548506,TO_TIMESTAMP('2025-11-03 17:04:12.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'de.metas.externalsystem','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2025-11-03 17:04:12.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:12.292Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:12.292Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600)
;

-- 2025-11-03T17:04:12.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755939
;

-- 2025-11-03T17:04:12.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755939)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Ausgehender HTTP-Endpunkt
-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpEP
-- 2025-11-03T17:04:12.433Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591478,755940,0,548506,TO_TIMESTAMP('2025-11-03 17:04:12.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL, an die die Daten gesendet werden',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Ausgehender HTTP-Endpunkt',TO_TIMESTAMP('2025-11-03 17:04:12.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:12.433Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:12.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584104)
;

-- 2025-11-03T17:04:12.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755940
;

-- 2025-11-03T17:04:12.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755940)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Ausgehende HTTP-Methode
-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpMethod
-- 2025-11-03T17:04:12.559Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591479,755941,0,548506,TO_TIMESTAMP('2025-11-03 17:04:12.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HTTP-Methode, die beim Senden der Daten verwendet wird (z. B. POST, PUT)',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Ausgehende HTTP-Methode',TO_TIMESTAMP('2025-11-03 17:04:12.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:12.562Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:12.564Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584106)
;

-- 2025-11-03T17:04:12.567Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755941
;

-- 2025-11-03T17:04:12.567Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755941)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Authentifizierungstyp
-- Column: ExternalSystem_Outbound_Endpoint.AuthType
-- 2025-11-03T17:04:12.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591481,755942,0,548506,TO_TIMESTAMP('2025-11-03 17:04:12.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Authentifizierungstyp',TO_TIMESTAMP('2025-11-03 17:04:12.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:12.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:12.677Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584192)
;

-- 2025-11-03T17:04:12.693Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755942
;

-- 2025-11-03T17:04:12.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755942)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Suchschlüssel
-- Column: ExternalSystem_Outbound_Endpoint.Value
-- 2025-11-03T17:04:12.787Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591482,755943,0,548506,TO_TIMESTAMP('2025-11-03 17:04:12.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',255,'de.metas.externalsystem','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2025-11-03 17:04:12.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:04:12.787Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:04:12.787Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2025-11-03T17:04:12.805Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755943
;

-- 2025-11-03T17:04:12.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755943)
;

-- Tab: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem)
-- UI Section: main
-- 2025-11-03T17:04:26.079Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548506,547038,TO_TIMESTAMP('2025-11-03 17:04:25.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-11-03 17:04:25.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-11-03T17:04:26.085Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547038 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2025-11-03T17:04:29.291Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548586,547038,TO_TIMESTAMP('2025-11-03 17:04:29.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-11-03 17:04:29.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2025-11-03T17:04:38.505Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548586,553738,TO_TIMESTAMP('2025-11-03 17:04:37.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,'primary',TO_TIMESTAMP('2025-11-03 17:04:37.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Suchschlüssel
-- Column: ExternalSystem_Outbound_Endpoint.Value
-- 2025-11-03T17:04:52.671Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755943,0,548506,638543,553738,'F',TO_TIMESTAMP('2025-11-03 17:04:52.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2025-11-03 17:04:52.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Art
-- Column: ExternalSystem_Outbound_Endpoint.Type
-- 2025-11-03T17:05:00.429Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755939,0,548506,638544,553738,'F',TO_TIMESTAMP('2025-11-03 17:05:00.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Art',20,0,0,TO_TIMESTAMP('2025-11-03 17:05:00.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Ausgehender HTTP-Endpunkt
-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpEP
-- 2025-11-03T17:05:16.641Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755940,0,548506,638545,553738,'F',TO_TIMESTAMP('2025-11-03 17:05:16.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL, an die die Daten gesendet werden','Y','N','N','Y','N','N','N',0,'Ausgehender HTTP-Endpunkt',30,0,0,TO_TIMESTAMP('2025-11-03 17:05:16.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Authentifizierungstyp
-- Column: ExternalSystem_Outbound_Endpoint.AuthType
-- 2025-11-03T17:05:34.785Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755942,0,548506,638546,553738,'F',TO_TIMESTAMP('2025-11-03 17:05:34.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Authentifizierungstyp',40,0,0,TO_TIMESTAMP('2025-11-03 17:05:34.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Ausgehende HTTP-Methode
-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpMethod
-- 2025-11-03T17:05:55.093Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755941,0,548506,638547,553738,'F',TO_TIMESTAMP('2025-11-03 17:05:54.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'HTTP-Methode, die beim Senden der Daten verwendet wird (z. B. POST, PUT)','Y','N','N','Y','N','N','N',0,'Ausgehende HTTP-Methode',50,0,0,TO_TIMESTAMP('2025-11-03 17:05:54.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Ausgehende HTTP-Methode
-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpMethod
-- 2025-11-03T17:06:01.208Z
UPDATE AD_UI_Element SET SeqNo=41,Updated=TO_TIMESTAMP('2025-11-03 17:06:01.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638547
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Authentifizierungstyp
-- Column: ExternalSystem_Outbound_Endpoint.AuthType
-- 2025-11-03T17:06:07.736Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2025-11-03 17:06:07.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638546
;

-- UI Section: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2025-11-03T17:06:23.670Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548587,547038,TO_TIMESTAMP('2025-11-03 17:06:23.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-11-03 17:06:23.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2025-11-03T17:06:30.559Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548587,553739,TO_TIMESTAMP('2025-11-03 17:06:30.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',10,TO_TIMESTAMP('2025-11-03 17:06:30.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2025-11-03T17:06:38.184Z
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2025-11-03 17:06:38.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553739
;

-- UI Column: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flags
-- 2025-11-03T17:06:45.053Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548587,553740,TO_TIMESTAMP('2025-11-03 17:06:44.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-11-03 17:06:44.897000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 20 -> flags.Aktiv
-- Column: ExternalSystem_Outbound_Endpoint.IsActive
-- 2025-11-03T17:06:52.820Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755936,0,548506,638548,553740,'F',TO_TIMESTAMP('2025-11-03 17:06:52.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-11-03 17:06:52.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Outbound_Endpoint.AD_Org_ID
-- 2025-11-03T17:07:24.086Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755935,0,548506,638549,553739,'F',TO_TIMESTAMP('2025-11-03 17:07:23.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2025-11-03 17:07:23.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 20 -> org.Mandant
-- Column: ExternalSystem_Outbound_Endpoint.AD_Client_ID
-- 2025-11-03T17:07:29.630Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755934,0,548506,638550,553739,'F',TO_TIMESTAMP('2025-11-03 17:07:29.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2025-11-03 17:07:29.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion
-- Table: ExternalSystem_Config_ScriptedExportConversion
-- 2025-11-03T17:10:05.283Z
UPDATE AD_Tab SET IsInsertRecord='Y',Updated=TO_TIMESTAMP('2025-11-03 17:10:05.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548471
;

-- Field: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T17:10:29.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591483,755944,0,548471,TO_TIMESTAMP('2025-11-03 17:10:29.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Externer System-Ausgangsendpunkt',TO_TIMESTAMP('2025-11-03 17:10:29.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:10:29.421Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:10:29.423Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584191)
;

-- 2025-11-03T17:10:29.427Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755944
;

-- 2025-11-03T17:10:29.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755944)
;

-- UI Element: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> main -> 10 -> default.Externer System-Ausgangsendpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T17:10:59.731Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755944,0,548471,638551,553634,'F',TO_TIMESTAMP('2025-11-03 17:10:59.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externer System-Ausgangsendpunkt',31,0,0,TO_TIMESTAMP('2025-11-03 17:10:59.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T17:12:03.807Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591483,755945,0,548464,TO_TIMESTAMP('2025-11-03 17:12:03.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Externer System-Ausgangsendpunkt',TO_TIMESTAMP('2025-11-03 17:12:03.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:12:03.809Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:12:03.811Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584191)
;

-- 2025-11-03T17:12:03.814Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755945
;

-- 2025-11-03T17:12:03.815Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755945)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> main -> 10 -> main.Externer System-Ausgangsendpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T17:12:30.954Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755945,0,548464,638552,553623,'F',TO_TIMESTAMP('2025-11-03 17:12:30.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externer System-Ausgangsendpunkt',11,0,0,TO_TIMESTAMP('2025-11-03 17:12:30.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> Ausgehender HTTP-Endpunkt
-- Column: ExternalSystem_Config_ScriptedExportConversion.ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T17:16:26.090Z
UPDATE AD_Field SET AD_Name_ID=584104, Description='URL, an die die Daten gesendet werden', Help=NULL, Name='Ausgehender HTTP-Endpunkt',Updated=TO_TIMESTAMP('2025-11-03 17:16:26.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755945
;

-- 2025-11-03T17:16:26.091Z
UPDATE AD_Field_Trl trl SET Description='URL, an die die Daten gesendet werden',Name='Ausgehender HTTP-Endpunkt' WHERE AD_Field_ID=755945 AND AD_Language='de_DE'
;

-- 2025-11-03T17:16:26.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584104)
;

-- 2025-11-03T17:16:26.101Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755945
;

-- 2025-11-03T17:16:26.102Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755945)
;

-- Column: ExternalSystem_Outbound_Endpoint.AuthToken
-- 2025-11-03T17:21:35.958Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591484,543920,0,10,542551,'XX','AuthToken',TO_TIMESTAMP('2025-11-03 17:21:35.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Authentifizierungs-Token',0,0,TO_TIMESTAMP('2025-11-03 17:21:35.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T17:21:35.959Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591484 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T17:21:35.963Z
/* DDL */  select update_Column_Translation_From_AD_Element(543920)
;

-- 2025-11-03T17:21:37.495Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN AuthToken VARCHAR(255)')
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Authentifizierungs-Token
-- Column: ExternalSystem_Outbound_Endpoint.AuthToken
-- 2025-11-03T17:21:49.981Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591484,755946,0,548506,TO_TIMESTAMP('2025-11-03 17:21:49.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Authentifizierungs-Token',TO_TIMESTAMP('2025-11-03 17:21:49.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:21:49.983Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:21:49.984Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543920)
;

-- 2025-11-03T17:21:49.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755946
;

-- 2025-11-03T17:21:49.988Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755946)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Authentifizierungstyp
-- Column: ExternalSystem_Outbound_Endpoint.AuthType
-- 2025-11-03T17:22:35.302Z
UPDATE AD_Field SET DisplayLogic='AuthType=''Token''',Updated=TO_TIMESTAMP('2025-11-03 17:22:35.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755942
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Authentifizierungs-Token
-- Column: ExternalSystem_Outbound_Endpoint.AuthToken
-- 2025-11-03T17:22:49.885Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755946,0,548506,638553,553738,'F',TO_TIMESTAMP('2025-11-03 17:22:49.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Authentifizierungs-Token',60,0,0,TO_TIMESTAMP('2025-11-03 17:22:49.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Ausgehende HTTP-Methode
-- Column: ExternalSystem_Outbound_Endpoint.OutboundHttpMethod
-- 2025-11-03T17:24:08.337Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2025-11-03 17:24:08.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638547
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Authentifizierungstyp
-- Column: ExternalSystem_Outbound_Endpoint.AuthType
-- 2025-11-03T17:25:35.590Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-11-03 17:25:35.590000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755942
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Authentifizierungs-Token
-- Column: ExternalSystem_Outbound_Endpoint.AuthToken
-- 2025-11-03T17:25:43.586Z
UPDATE AD_Field SET DisplayLogic='AuthType=''Token''',Updated=TO_TIMESTAMP('2025-11-03 17:25:43.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755946
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Authentifizierungs-Token
-- Column: ExternalSystem_Outbound_Endpoint.AuthToken
-- 2025-11-03T17:26:33.890Z
UPDATE AD_Field SET DisplayLogic='@AuthType@=''Token''',Updated=TO_TIMESTAMP('2025-11-03 17:26:33.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755946
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Authentifizierungs-Token
-- Column: ExternalSystem_Outbound_Endpoint.AuthToken
-- 2025-11-03T17:27:06.214Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''Token''',Updated=TO_TIMESTAMP('2025-11-03 17:27:06.214000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755946
;

-- 2025-11-03T17:29:44.905Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584193,0,'ClientSecret',TO_TIMESTAMP('2025-11-03 17:29:44.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Client Secret','Client Secret',TO_TIMESTAMP('2025-11-03 17:29:44.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:29:44.910Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584193 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Outbound_Endpoint.ClientSecret
-- 2025-11-03T17:30:29.529Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591485,584193,0,10,542551,'XX','ClientSecret',TO_TIMESTAMP('2025-11-03 17:30:29.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Client Secret',0,0,TO_TIMESTAMP('2025-11-03 17:30:29.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T17:30:29.529Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591485 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T17:30:29.544Z
/* DDL */  select update_Column_Translation_From_AD_Element(584193)
;

-- 2025-11-03T17:30:30.675Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN ClientSecret VARCHAR(255)')
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Client Secret
-- Column: ExternalSystem_Outbound_Endpoint.ClientSecret
-- 2025-11-03T17:30:38.407Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591485,755947,0,548506,TO_TIMESTAMP('2025-11-03 17:30:38.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Client Secret',TO_TIMESTAMP('2025-11-03 17:30:38.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:30:38.414Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755947 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:30:38.414Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584193)
;

-- 2025-11-03T17:30:38.414Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755947
;

-- 2025-11-03T17:30:38.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755947)
;

-- 2025-11-03T17:31:12.109Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584194,0,'ClientId',TO_TIMESTAMP('2025-11-03 17:31:11.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Client ID','Client ID',TO_TIMESTAMP('2025-11-03 17:31:11.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:31:12.109Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584194 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Outbound_Endpoint.ClientId
-- 2025-11-03T17:31:37.126Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591486,584194,0,10,542551,'XX','ClientId',TO_TIMESTAMP('2025-11-03 17:31:36.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Client ID',0,0,TO_TIMESTAMP('2025-11-03 17:31:36.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T17:31:37.126Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591486 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T17:31:37.126Z
/* DDL */  select update_Column_Translation_From_AD_Element(584194)
;

-- 2025-11-03T17:31:38.574Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN ClientId VARCHAR(255)')
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Client ID
-- Column: ExternalSystem_Outbound_Endpoint.ClientId
-- 2025-11-03T17:31:47.234Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591486,755948,0,548506,TO_TIMESTAMP('2025-11-03 17:31:47.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Client ID',TO_TIMESTAMP('2025-11-03 17:31:47.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:31:47.234Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:31:47.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584194)
;

-- 2025-11-03T17:31:47.250Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755948
;

-- 2025-11-03T17:31:47.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755948)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Client Secret
-- Column: ExternalSystem_Outbound_Endpoint.ClientSecret
-- 2025-11-03T17:32:12.440Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''OAuth''',Updated=TO_TIMESTAMP('2025-11-03 17:32:12.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755947
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Client ID
-- Column: ExternalSystem_Outbound_Endpoint.ClientId
-- 2025-11-03T17:32:22.977Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''OAuth''',Updated=TO_TIMESTAMP('2025-11-03 17:32:22.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755948
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Client ID
-- Column: ExternalSystem_Outbound_Endpoint.ClientId
-- 2025-11-03T17:32:36.136Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755948,0,548506,638554,553738,'F',TO_TIMESTAMP('2025-11-03 17:32:35.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Client ID',70,0,0,TO_TIMESTAMP('2025-11-03 17:32:35.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Client Secret
-- Column: ExternalSystem_Outbound_Endpoint.ClientSecret
-- 2025-11-03T17:32:40.217Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755947,0,548506,638555,553738,'F',TO_TIMESTAMP('2025-11-03 17:32:40.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Client Secret',80,0,0,TO_TIMESTAMP('2025-11-03 17:32:40.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: ExternalSystem_Outbound_Endpoint.EMail
-- 2025-11-03T17:33:43.525Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591487,881,0,10,542551,'XX','EMail',TO_TIMESTAMP('2025-11-03 17:33:43.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','EMail-Adresse','de.metas.externalsystem',0,60,'The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eMail',0,0,TO_TIMESTAMP('2025-11-03 17:33:43.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T17:33:43.528Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591487 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T17:33:43.532Z
/* DDL */  select update_Column_Translation_From_AD_Element(881)
;

-- 2025-11-03T17:33:44.500Z
--- we are changing this to UserLogin in the following script
---/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN EMail VARCHAR(60)')
;

-- Column: ExternalSystem_Outbound_Endpoint.Password
-- 2025-11-03T17:34:11.362Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591488,498,0,10,542551,'XX','Password',TO_TIMESTAMP('2025-11-03 17:34:11.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.externalsystem',0,255,'The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kennwort',0,0,TO_TIMESTAMP('2025-11-03 17:34:11.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-03T17:34:11.364Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591488 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-03T17:34:11.514Z
/* DDL */  select update_Column_Translation_From_AD_Element(498)
;

-- 2025-11-03T17:34:12.350Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Outbound_Endpoint','ALTER TABLE public.ExternalSystem_Outbound_Endpoint ADD COLUMN Password VARCHAR(255)')
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> eMail
-- Column: ExternalSystem_Outbound_Endpoint.EMail
-- 2025-11-03T17:34:26.596Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591487,755949,0,548506,TO_TIMESTAMP('2025-11-03 17:34:26.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse',60,'de.metas.externalsystem','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2025-11-03 17:34:26.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:34:26.597Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:34:26.599Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881)
;

-- 2025-11-03T17:34:26.608Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755949
;

-- 2025-11-03T17:34:26.608Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755949)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Kennwort
-- Column: ExternalSystem_Outbound_Endpoint.Password
-- 2025-11-03T17:34:35.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591488,755950,0,548506,TO_TIMESTAMP('2025-11-03 17:34:35.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'de.metas.externalsystem','The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','N','N','N','N','N','N','Kennwort',TO_TIMESTAMP('2025-11-03 17:34:35.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T17:34:35.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755950 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-03T17:34:35.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(498)
;

-- 2025-11-03T17:34:35.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755950
;

-- 2025-11-03T17:34:35.865Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755950)
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> eMail
-- Column: ExternalSystem_Outbound_Endpoint.EMail
-- 2025-11-03T17:34:55.529Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''Basic''',Updated=TO_TIMESTAMP('2025-11-03 17:34:55.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755949
;

-- Field: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> Kennwort
-- Column: ExternalSystem_Outbound_Endpoint.Password
-- 2025-11-03T17:35:01.095Z
UPDATE AD_Field SET DisplayLogic='@AuthType/X@=''Basic''',Updated=TO_TIMESTAMP('2025-11-03 17:35:01.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755950
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.eMail
-- Column: ExternalSystem_Outbound_Endpoint.EMail
-- 2025-11-03T17:35:10.690Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755949,0,548506,638556,553738,'F',TO_TIMESTAMP('2025-11-03 17:35:10.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','N','N','N',0,'eMail',90,0,0,TO_TIMESTAMP('2025-11-03 17:35:10.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem) -> Externer System-Ausgangsendpunkt(548506,de.metas.externalsystem) -> main -> 10 -> main.Kennwort
-- Column: ExternalSystem_Outbound_Endpoint.Password
-- 2025-11-03T17:35:16.754Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,755950,0,548506,638557,553738,'F',TO_TIMESTAMP('2025-11-03 17:35:16.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','N','Y','N','N','N',0,'Kennwort',100,0,0,TO_TIMESTAMP('2025-11-03 17:35:16.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: OutboundHttpMethod
-- 2025-11-03T17:36:30.350Z
UPDATE AD_Element_Trl SET Name='Outbound HTTP Method',Updated=TO_TIMESTAMP('2025-11-03 17:36:30.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584106 AND AD_Language='en_US'
;

-- 2025-11-03T17:36:30.352Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-03T17:36:30.996Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584106,'en_US')
;

-- Reference: AuthType
-- Value: Token
-- ValueName: Token-basiert
-- 2025-11-03T18:07:56.416Z
UPDATE AD_Ref_List SET Name='Token',ValueName='Token',Updated=TO_TIMESTAMP('2025-11-03 18:07:56.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544055
;

-- 2025-11-03T18:07:56.417Z
UPDATE AD_Ref_List_Trl trl SET Name='Token' WHERE AD_Ref_List_ID=544055 AND AD_Language='de_DE'
;

-- Reference Item: AuthType -> Token_Token-basiert
-- 2025-11-03T18:08:00.347Z
UPDATE AD_Ref_List_Trl SET Name='Token',Updated=TO_TIMESTAMP('2025-11-03 18:08:00.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544055
;

-- 2025-11-03T18:08:00.348Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: AuthType -> Token_Token-basiert
-- 2025-11-03T18:08:04.113Z
UPDATE AD_Ref_List_Trl SET Name='Token',Updated=TO_TIMESTAMP('2025-11-03 18:08:04.113000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544055
;

-- 2025-11-03T18:08:04.114Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: AuthType -> Token_Token-basiert
-- 2025-11-03T18:08:06.577Z
UPDATE AD_Ref_List_Trl SET Name='Token',Updated=TO_TIMESTAMP('2025-11-03 18:08:06.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=544055
;

-- 2025-11-03T18:08:06.577Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: AuthType
-- Value: Basic
-- ValueName: Basisauthentifizierung
-- 2025-11-03T18:08:25.420Z
UPDATE AD_Ref_List SET Name='Basic',ValueName='Basic',Updated=TO_TIMESTAMP('2025-11-03 18:08:25.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544056
;

-- 2025-11-03T18:08:25.421Z
UPDATE AD_Ref_List_Trl trl SET Name='Basic' WHERE AD_Ref_List_ID=544056 AND AD_Language='de_DE'
;

-- Element: ExternalSystem_Outbound_Endpoint_ID
-- 2025-11-03T18:40:22.913Z
UPDATE AD_Element_Trl SET Name='External System Outbound Endpoint', PrintName='External System Outbound Endpoint',Updated=TO_TIMESTAMP('2025-11-03 18:40:22.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584191 AND AD_Language='en_US'
;

-- 2025-11-03T18:40:22.913Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-03T18:40:24.144Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584191,'en_US')
;

-- Name: Externer System-Ausgangsendpunkt
-- Action Type: W
-- Window: Externer System-Ausgangsendpunkt(541967,de.metas.externalsystem)
-- 2025-11-03T18:42:11.805Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584191,542268,0,541967,TO_TIMESTAMP('2025-11-03 18:42:11.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.externalsystem','External System Outbound Endpoint','Y','N','N','N','N','Externer System-Ausgangsendpunkt',TO_TIMESTAMP('2025-11-03 18:42:11.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-03T18:42:11.808Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542268 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-11-03T18:42:11.812Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542268, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542268)
;

-- 2025-11-03T18:42:11.834Z
/* DDL */  select update_menu_translation_from_ad_element(584191)
;

-- Reordering children of `System`
-- Node name: `Change Log Config (AD_ChangeLog_Config)`
-- 2025-11-03T18:42:19.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542199 AND AD_Tree_ID=10
;

-- Node name: `Warnings (AD_Record_Warning)`
-- 2025-11-03T18:42:19.980Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542223 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2025-11-03T18:42:19.981Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config PCM (ExternalSystem_Config_ProCareManagement)`
-- 2025-11-03T18:42:19.981Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542142 AND AD_Tree_ID=10
;

-- Node name: `Costing (Freight etc)`
-- 2025-11-03T18:42:19.982Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2025-11-03T18:42:19.982Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2025-11-03T18:42:19.983Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2025-11-03T18:42:19.983Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System Configuration (ExternalSystem_Config)`
-- 2025-11-03T18:42:19.984Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2025-11-03T18:42:19.984Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `Scripted Export Conversion (ExternalSystem_Config_ScriptedExportConversion)`
-- 2025-11-03T18:42:19.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542262 AND AD_Tree_ID=10
;

-- Node name: `Scripted Import Conversion (ExternalSystem_Config_ScriptedImportConversion)`
-- 2025-11-03T18:42:19.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542263 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2025-11-03T18:42:19.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2025-11-03T18:42:19.987Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2025-11-03T18:42:19.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2025-11-03T18:42:19.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2025-11-03T18:42:19.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2025-11-03T18:42:19.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2025-11-03T18:42:19.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2025-11-03T18:42:19.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2025-11-03T18:42:19.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2025-11-03T18:42:19.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2025-11-03T18:42:19.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2025-11-03T18:42:19.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2025-11-03T18:42:19.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2025-11-03T18:42:19.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2025-11-03T18:42:19.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2025-11-03T18:42:19.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2025-11-03T18:42:19.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2025-11-03T18:42:19.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2025-11-03T18:42:19.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2025-11-03T18:42:19.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2025-11-03T18:42:20Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2025-11-03T18:42:20Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2025-11-03T18:42:20.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2025-11-03T18:42:20.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2025-11-03T18:42:20.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2025-11-03T18:42:20.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2025-11-03T18:42:20.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2025-11-03T18:42:20.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2025-11-03T18:42:20.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2025-11-03T18:42:20.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2025-11-03T18:42:20.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2025-11-03T18:42:20.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2025-11-03T18:42:20.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2025-11-03T18:42:20.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2025-11-03T18:42:20.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2025-11-03T18:42:20.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2025-11-03T18:42:20.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2025-11-03T18:42:20.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2025-11-03T18:42:20.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2025-11-03T18:42:20.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2025-11-03T18:42:20.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2025-11-03T18:42:20.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2025-11-03T18:42:20.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2025-11-03T18:42:20.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2025-11-03T18:42:20.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2025-11-03T18:42:20.013Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2025-11-03T18:42:20.013Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2025-11-03T18:42:20.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import GL Journal (I_GLJournal)`
-- 2025-11-03T18:42:20.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542247 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2025-11-03T18:42:20.016Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Import Forecast (I_Forecast)`
-- 2025-11-03T18:42:20.016Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542243 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2025-11-03T18:42:20.017Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2025-11-03T18:42:20.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2025-11-03T18:42:20.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2025-11-03T18:42:20.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2025-11-03T18:42:20.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2025-11-03T18:42:20.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2025-11-03T18:42:20.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2025-11-03T18:42:20.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2025-11-03T18:42:20.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2025-11-03T18:42:20.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-11-03T18:42:20.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-11-03T18:42:20.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-11-03T18:42:20.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2025-11-03T18:42:20.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2025-11-03T18:42:20.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2025-11-03T18:42:20.025Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2025-11-03T18:42:20.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2025-11-03T18:42:20.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2025-11-03T18:42:20.027Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2025-11-03T18:42:20.027Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2025-11-03T18:42:20.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `Analysis Report (QM_Analysis_Report)`
-- 2025-11-03T18:42:20.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542215 AND AD_Tree_ID=10
;

-- Node name: `Rebuild FactAcct Summary (de.metas.acct.process.Rebuild_FactAcctSummary)`
-- 2025-11-03T18:42:20.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542089 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2025-11-03T18:42:20.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2025-11-03T18:42:20.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Update purchase order highest price cache (de.metas.process.ExecuteUpdateSQL)`
-- 2025-11-03T18:42:20.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542158 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2025-11-03T18:42:20.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2025-11-03T18:42:20.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2025-11-03T18:42:20.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `Issues (AD_Issue)`
-- 2025-11-03T18:42:20.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542151 AND AD_Tree_ID=10
;

-- Node name: `Mobile`
-- 2025-11-03T18:42:20.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542179 AND AD_Tree_ID=10
;

-- Node name: `Business Rules`
-- 2025-11-03T18:42:20.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542187 AND AD_Tree_ID=10
;

-- Node name: `UI Tracing`
-- 2025-11-03T18:42:20.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542190 AND AD_Tree_ID=10
;

-- Node name: `Notification`
-- 2025-11-03T18:42:20.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542210 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem)`
-- 2025-11-03T18:42:20.035Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542246 AND AD_Tree_ID=10
;

-- Node name: `Externer System-Ausgangsendpunkt`
-- 2025-11-03T18:42:20.035Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542268 AND AD_Tree_ID=10
;

