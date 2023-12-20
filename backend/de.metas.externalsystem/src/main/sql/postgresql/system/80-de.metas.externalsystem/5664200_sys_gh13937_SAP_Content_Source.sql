-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-14T12:54:00.025Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('4',0,0,0,542257,'N',TO_TIMESTAMP('2022-11-14 14:53:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'External System Config SAP SFTP','NP','L','ExternalSystem_Config_SAP_SFTP','DTI',TO_TIMESTAMP('2022-11-14 14:53:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T12:54:00.030Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542257 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-11-14T12:54:00.133Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556049,TO_TIMESTAMP('2022-11-14 14:54:00','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_SAP_SFTP',1,'Y','N','Y','Y','ExternalSystem_Config_SAP_SFTP','N',1000000,TO_TIMESTAMP('2022-11-14 14:54:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T12:54:00.151Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_SAP_SFTP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-14T12:54:55.954Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2022-11-14 14:54:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542257
;

-- 2022-11-14T12:58:42.227Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581669,0,'ExternalSystem_Config_SAP_SFTP_ID',TO_TIMESTAMP('2022-11-14 14:58:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','External System Config SAP SFTP','External System Config SAP SFTP',TO_TIMESTAMP('2022-11-14 14:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T12:58:42.235Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581669 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-14T12:58:42.724Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,584941,581669,0,13,542257,'ExternalSystem_Config_SAP_SFTP_ID',TO_TIMESTAMP('2022-11-14 14:58:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','External System Config SAP SFTP',TO_TIMESTAMP('2022-11-14 14:58:42','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-11-14T12:58:42.731Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584941 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T12:58:42.738Z
/* DDL */  select update_Column_Translation_From_AD_Element(581669)
;

-- 2022-11-14T12:58:43.157Z
ALTER SEQUENCE EXTERNALSYSTEM_CONFIG_SAP_SFTP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2022-11-14T12:59:18.485Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_SAP_SFTP (ExternalSystem_Config_SAP_SFTP_ID NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Config_SAP_SFTP_Key PRIMARY KEY (ExternalSystem_Config_SAP_SFTP_ID))
;

-- Column: ExternalSystem_Config_SAP_SFTP.AD_Client_ID
-- 2022-11-14T13:34:04.646Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584945,102,0,19,542257,'AD_Client_ID',TO_TIMESTAMP('2022-11-14 15:34:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-11-14 15:34:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:04.653Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584945 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:04.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: ExternalSystem_Config_SAP_SFTP.AD_Org_ID
-- 2022-11-14T13:34:05.925Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584946,113,0,30,542257,'AD_Org_ID',TO_TIMESTAMP('2022-11-14 15:34:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-11-14 15:34:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:05.927Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584946 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:05.930Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: ExternalSystem_Config_SAP_SFTP.Created
-- 2022-11-14T13:34:06.881Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584947,245,0,16,542257,'Created',TO_TIMESTAMP('2022-11-14 15:34:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-11-14 15:34:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:06.885Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584947 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:06.888Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: ExternalSystem_Config_SAP_SFTP.CreatedBy
-- 2022-11-14T13:34:07.723Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584948,246,0,18,110,542257,'CreatedBy',TO_TIMESTAMP('2022-11-14 15:34:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-11-14 15:34:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:07.725Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584948 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:07.727Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ErroredDirectory
-- 2022-11-14T13:34:08.452Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584949,581584,0,10,542257,'ErroredDirectory',TO_TIMESTAMP('2022-11-14 15:34:08','YYYY-MM-DD HH24:MI:SS'),100,'N','error','Legt fest, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen, relativ zum aktuellen sftp-Zielort.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Fehler-Verzeichnis',0,0,TO_TIMESTAMP('2022-11-14 15:34:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:08.456Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584949 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:08.460Z
/* DDL */  select update_Column_Translation_From_AD_Element(581584)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_ID
-- 2022-11-14T13:34:09.022Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584950,578728,0,19,542257,'ExternalSystem_Config_ID',TO_TIMESTAMP('2022-11-14 15:34:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','External System Config',0,0,TO_TIMESTAMP('2022-11-14 15:34:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:09.025Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584950 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:09.028Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystemValue
-- 2022-11-14T13:34:09.641Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584951,578788,0,10,542257,'ExternalSystemValue',TO_TIMESTAMP('2022-11-14 15:34:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Suchschlüssel',0,0,TO_TIMESTAMP('2022-11-14 15:34:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:09.644Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584951 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:09.647Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788)
;

-- Column: ExternalSystem_Config_SAP_SFTP.IsActive
-- 2022-11-14T13:34:10.380Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584952,348,0,20,542257,'IsActive',TO_TIMESTAMP('2022-11-14 15:34:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-11-14 15:34:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:10.382Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584952 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:10.385Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: ExternalSystem_Config_SAP_SFTP.PollingFrequencyInMs
-- 2022-11-14T13:34:11.308Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584953,581586,0,11,542257,'PollingFrequencyInMs',TO_TIMESTAMP('2022-11-14 15:34:11','YYYY-MM-DD HH24:MI:SS'),100,'N','1000','Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','de.metas.externalsystem',0,14,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Abfragefrequenz in Millisekunden',0,0,TO_TIMESTAMP('2022-11-14 15:34:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:11.310Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584953 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:11.311Z
/* DDL */  select update_Column_Translation_From_AD_Element(581586)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ProcessedDirectory
-- 2022-11-14T13:34:11.954Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584954,581583,0,10,542257,'ProcessedDirectory',TO_TIMESTAMP('2022-11-14 15:34:11','YYYY-MM-DD HH24:MI:SS'),100,'N','move','Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung relativ zum aktuellen sftp-Zielspeicherort verschoben werden sollen.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Verarbeitet-Verzeichnis',0,0,TO_TIMESTAMP('2022-11-14 15:34:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:11.957Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584954 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:11.960Z
/* DDL */  select update_Column_Translation_From_AD_Element(581583)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_HostName
-- 2022-11-14T13:34:12.498Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584955,581553,0,10,542257,'SFTP_HostName',TO_TIMESTAMP('2022-11-14 15:34:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Host des sftp-Servers.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Hostname',0,0,TO_TIMESTAMP('2022-11-14 15:34:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:12.500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584955 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:12.502Z
/* DDL */  select update_Column_Translation_From_AD_Element(581553)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Password
-- 2022-11-14T13:34:13.038Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584956,581554,0,10,542257,'SFTP_Password',TO_TIMESTAMP('2022-11-14 15:34:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Passwort, das zur Authentifizierung beim sftp-Server verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Passwort',0,0,TO_TIMESTAMP('2022-11-14 15:34:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:13.040Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584956 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:13.043Z
/* DDL */  select update_Column_Translation_From_AD_Element(581554)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Port
-- 2022-11-14T13:34:13.602Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584957,581555,0,10,542257,'SFTP_Port',TO_TIMESTAMP('2022-11-14 15:34:13','YYYY-MM-DD HH24:MI:SS'),100,'N','22','Der Port des sftp-Servers.','de.metas.externalsystem',0,14,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Port',0,0,TO_TIMESTAMP('2022-11-14 15:34:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:13.605Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584957 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:13.608Z
/* DDL */  select update_Column_Translation_From_AD_Element(581555)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_TargetDirectory
-- 2022-11-14T13:34:14.175Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584958,581557,0,10,542257,'SFTP_TargetDirectory',TO_TIMESTAMP('2022-11-14 15:34:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Verzeichnis, das zum Abrufen vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-14 15:34:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:14.176Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584958 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:14.178Z
/* DDL */  select update_Column_Translation_From_AD_Element(581557)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Username
-- 2022-11-14T13:34:14.740Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584959,581556,0,10,542257,'SFTP_Username',TO_TIMESTAMP('2022-11-14 15:34:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Benutzername, der für die Authentifizierung beim sftp-Server verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Benutzername',0,0,TO_TIMESTAMP('2022-11-14 15:34:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:14.742Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584959 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:14.743Z
/* DDL */  select update_Column_Translation_From_AD_Element(581556)
;

-- Column: ExternalSystem_Config_SAP_SFTP.Updated
-- 2022-11-14T13:34:15.357Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584960,607,0,16,542257,'Updated',TO_TIMESTAMP('2022-11-14 15:34:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-11-14 15:34:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:15.359Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584960 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:15.364Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: ExternalSystem_Config_SAP_SFTP.UpdatedBy
-- 2022-11-14T13:34:16.184Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584961,608,0,18,110,542257,'UpdatedBy',TO_TIMESTAMP('2022-11-14 15:34:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-11-14 15:34:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:34:16.186Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584961 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:34:16.188Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2022-11-14T13:35:55.373Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE ExternalSystem_Config_SAP_SFTP DROP COLUMN IF EXISTS ExternalSystem_Config_ID')
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_ID
-- 2022-11-14T13:35:55.443Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584950
;

-- 2022-11-14T13:35:55.455Z
DELETE FROM AD_Column WHERE AD_Column_ID=584950
;

-- 2022-11-14T13:36:31.513Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE ExternalSystem_Config_SAP_SFTP DROP COLUMN IF EXISTS ExternalSystemValue')
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystemValue
-- 2022-11-14T13:36:31.537Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584951
;

-- 2022-11-14T13:36:31.548Z
DELETE FROM AD_Column WHERE AD_Column_ID=584951
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Fehlerhaftes Verzeichnis
-- Column: ExternalSystem_Config_SAP.ErroredDirectory
-- 2022-11-14T13:38:33.829Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613279
;

-- 2022-11-14T13:38:33.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707804
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Fehler-Verzeichnis
-- Column: ExternalSystem_Config_SAP.ErroredDirectory
-- 2022-11-14T13:38:33.848Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707804
;

-- 2022-11-14T13:38:33.852Z
DELETE FROM AD_Field WHERE AD_Field_ID=707804
;

-- 2022-11-14T13:38:33.854Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS ErroredDirectory')
;

-- Column: ExternalSystem_Config_SAP.ErroredDirectory
-- 2022-11-14T13:38:33.861Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584769
;

-- 2022-11-14T13:38:33.863Z
DELETE FROM AD_Column WHERE AD_Column_ID=584769
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Abfragefrequenz
-- Column: ExternalSystem_Config_SAP.PollingFrequencyInMs
-- 2022-11-14T13:38:52.460Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613280
;

-- 2022-11-14T13:38:52.461Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707805
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_SAP.PollingFrequencyInMs
-- 2022-11-14T13:38:52.463Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707805
;

-- 2022-11-14T13:38:52.465Z
DELETE FROM AD_Field WHERE AD_Field_ID=707805
;

-- 2022-11-14T13:38:52.466Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS PollingFrequencyInMs')
;

-- Column: ExternalSystem_Config_SAP.PollingFrequencyInMs
-- 2022-11-14T13:38:52.476Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584770
;

-- 2022-11-14T13:38:52.478Z
DELETE FROM AD_Column WHERE AD_Column_ID=584770
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.Bearbeitetes Verzeichnis
-- Column: ExternalSystem_Config_SAP.ProcessedDirectory
-- 2022-11-14T13:39:03.257Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613278
;

-- 2022-11-14T13:39:03.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707803
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP.ProcessedDirectory
-- 2022-11-14T13:39:03.265Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707803
;

-- 2022-11-14T13:39:03.268Z
DELETE FROM AD_Field WHERE AD_Field_ID=707803
;

-- 2022-11-14T13:39:03.269Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS ProcessedDirectory')
;

-- Column: ExternalSystem_Config_SAP.ProcessedDirectory
-- 2022-11-14T13:39:03.278Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584768
;

-- 2022-11-14T13:39:03.280Z
DELETE FROM AD_Column WHERE AD_Column_ID=584768
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Hostname
-- Column: ExternalSystem_Config_SAP.SFTP_HostName
-- 2022-11-14T13:39:52.044Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613113
;

-- 2022-11-14T13:39:52.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707468
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Hostname
-- Column: ExternalSystem_Config_SAP.SFTP_HostName
-- 2022-11-14T13:39:52.050Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707468
;

-- 2022-11-14T13:39:52.052Z
DELETE FROM AD_Field WHERE AD_Field_ID=707468
;

-- 2022-11-14T13:39:52.055Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_HostName')
;

-- Column: ExternalSystem_Config_SAP.SFTP_HostName
-- 2022-11-14T13:39:52.072Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584648
;

-- 2022-11-14T13:39:52.074Z
DELETE FROM AD_Column WHERE AD_Column_ID=584648
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Passwort
-- Column: ExternalSystem_Config_SAP.SFTP_Password
-- 2022-11-14T13:40:18.107Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613116
;

-- 2022-11-14T13:40:18.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707471
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Passwort
-- Column: ExternalSystem_Config_SAP.SFTP_Password
-- 2022-11-14T13:40:18.109Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707471
;

-- 2022-11-14T13:40:18.111Z
DELETE FROM AD_Field WHERE AD_Field_ID=707471
;

-- 2022-11-14T13:40:18.112Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_Password')
;

-- Column: ExternalSystem_Config_SAP.SFTP_Password
-- 2022-11-14T13:40:18.119Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584651
;

-- 2022-11-14T13:40:18.120Z
DELETE FROM AD_Column WHERE AD_Column_ID=584651
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Port
-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-11-14T13:40:44.229Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613114
;

-- 2022-11-14T13:40:44.230Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707469
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Port
-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-11-14T13:40:44.232Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707469
;

-- 2022-11-14T13:40:44.233Z
DELETE FROM AD_Field WHERE AD_Field_ID=707469
;

-- 2022-11-14T13:40:44.234Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_Port')
;

-- Column: ExternalSystem_Config_SAP.SFTP_Port
-- 2022-11-14T13:40:44.241Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584649
;

-- 2022-11-14T13:40:44.242Z
DELETE FROM AD_Column WHERE AD_Column_ID=584649
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_TargetDirectory
-- 2022-11-14T13:41:02.392Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613230
;

-- 2022-11-14T13:41:02.393Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707760
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_TargetDirectory
-- 2022-11-14T13:41:02.394Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707760
;

-- 2022-11-14T13:41:02.395Z
DELETE FROM AD_Field WHERE AD_Field_ID=707760
;

-- 2022-11-14T13:41:02.396Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_TargetDirectory')
;

-- Column: ExternalSystem_Config_SAP.SFTP_TargetDirectory
-- 2022-11-14T13:41:02.404Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584682
;

-- 2022-11-14T13:41:02.405Z
DELETE FROM AD_Column WHERE AD_Column_ID=584682
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Benutzername
-- Column: ExternalSystem_Config_SAP.SFTP_Username
-- 2022-11-14T13:41:25.302Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613115
;

-- 2022-11-14T13:41:25.303Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707470
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Benutzername
-- Column: ExternalSystem_Config_SAP.SFTP_Username
-- 2022-11-14T13:41:25.305Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707470
;

-- 2022-11-14T13:41:25.306Z
DELETE FROM AD_Field WHERE AD_Field_ID=707470
;

-- 2022-11-14T13:41:25.308Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_Username')
;

-- Column: ExternalSystem_Config_SAP.SFTP_Username
-- 2022-11-14T13:41:25.316Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584650
;

-- 2022-11-14T13:41:25.318Z
DELETE FROM AD_Column WHERE AD_Column_ID=584650
;

-- 2022-11-14T13:43:11.485Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN AD_Client_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-14T13:43:15.912Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN AD_Org_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-14T13:43:20.524Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN Created TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-11-14T13:43:25.407Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN CreatedBy NUMERIC(10) NOT NULL')
;

-- 2022-11-14T13:43:30.259Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN ErroredDirectory VARCHAR(255) DEFAULT ''error'' NOT NULL')
;

-- 2022-11-14T13:43:35.695Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','ExternalSystem_Config_SAP_SFTP_ID','NUMERIC(10)',null,null)
;

-- 2022-11-14T13:43:40.140Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN IsActive CHAR(1) CHECK (IsActive IN (''Y'',''N'')) NOT NULL')
;

-- 2022-11-14T13:43:43.955Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN PollingFrequencyInMs NUMERIC(10) DEFAULT 1000 NOT NULL')
;

-- 2022-11-14T13:43:48.276Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN ProcessedDirectory VARCHAR(255) DEFAULT ''move'' NOT NULL')
;

-- 2022-11-14T13:43:52.769Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_HostName VARCHAR(255) NOT NULL')
;

-- 2022-11-14T13:43:57.634Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_Password VARCHAR(255) NOT NULL')
;

-- 2022-11-14T13:44:02.405Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_Port VARCHAR(14) DEFAULT ''22'' NOT NULL')
;

-- 2022-11-14T13:44:06.498Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_TargetDirectory VARCHAR(255)')
;

-- 2022-11-14T13:44:11.217Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_Username VARCHAR(255) NOT NULL')
;

-- 2022-11-14T13:44:15.398Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN Updated TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-11-14T13:44:19.672Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN UpdatedBy NUMERIC(10) NOT NULL')
;

-- Table: ExternalSystem_Config_SAP_LocalFile
-- 2022-11-14T13:47:47.301Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542258,'N',TO_TIMESTAMP('2022-11-14 15:47:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'ExternalSystem Config SAP Local File','NP','L','ExternalSystem_Config_SAP_LocalFile','DTI',TO_TIMESTAMP('2022-11-14 15:47:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T13:47:47.305Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542258 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-11-14T13:47:47.433Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556050,TO_TIMESTAMP('2022-11-14 15:47:47','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_SAP_LocalFile',1,'Y','N','Y','Y','ExternalSystem_Config_SAP_LocalFile','N',1000000,TO_TIMESTAMP('2022-11-14 15:47:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T13:47:47.445Z
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_SAP_LOCALFILE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2022-11-14T13:48:11.477Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581671,0,'ExternalSystem_Config_SAP_LocalFile_ID',TO_TIMESTAMP('2022-11-14 15:48:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem Config SAP Local File','ExternalSystem Config SAP Local File',TO_TIMESTAMP('2022-11-14 15:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T13:48:11.480Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581671 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T13:48:11.913Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,584962,581671,0,13,542258,'ExternalSystem_Config_SAP_LocalFile_ID',TO_TIMESTAMP('2022-11-14 15:48:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','ExternalSystem Config SAP Local File',TO_TIMESTAMP('2022-11-14 15:48:11','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-11-14T13:48:11.919Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584962 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:11.926Z
/* DDL */  select update_Column_Translation_From_AD_Element(581671)
;

-- 2022-11-14T13:48:12.346Z
ALTER SEQUENCE EXTERNALSYSTEM_CONFIG_SAP_LOCALFILE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2022-11-14T13:48:23.149Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_SAP_LocalFile (ExternalSystem_Config_SAP_LocalFile_ID NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Config_SAP_LocalFile_Key PRIMARY KEY (ExternalSystem_Config_SAP_LocalFile_ID))
;

-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Client_ID
-- 2022-11-14T13:48:52.679Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584963,102,0,19,542258,'AD_Client_ID',TO_TIMESTAMP('2022-11-14 15:48:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-11-14 15:48:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:52.682Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584963 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:52.686Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Org_ID
-- 2022-11-14T13:48:53.785Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584964,113,0,30,542258,'AD_Org_ID',TO_TIMESTAMP('2022-11-14 15:48:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-11-14 15:48:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:53.787Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584964 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:53.790Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.Created
-- 2022-11-14T13:48:54.635Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584965,245,0,16,542258,'Created',TO_TIMESTAMP('2022-11-14 15:48:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-11-14 15:48:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:54.638Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584965 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:54.641Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.CreatedBy
-- 2022-11-14T13:48:55.445Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584966,246,0,18,110,542258,'CreatedBy',TO_TIMESTAMP('2022-11-14 15:48:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-11-14 15:48:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:55.447Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584966 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:55.449Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.ErroredDirectory
-- 2022-11-14T13:48:56.204Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584967,581584,0,10,542258,'ErroredDirectory',TO_TIMESTAMP('2022-11-14 15:48:55','YYYY-MM-DD HH24:MI:SS'),100,'N','error','Legt fest, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen, relativ zum aktuellen sftp-Zielort.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Fehler-Verzeichnis',0,0,TO_TIMESTAMP('2022-11-14 15:48:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:56.205Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584967 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:56.207Z
/* DDL */  select update_Column_Translation_From_AD_Element(581584)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.IsActive
-- 2022-11-14T13:48:56.811Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584968,348,0,20,542258,'IsActive',TO_TIMESTAMP('2022-11-14 15:48:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-11-14 15:48:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:56.813Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584968 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:56.815Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.PollingFrequencyInMs
-- 2022-11-14T13:48:57.702Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584969,581586,0,11,542258,'PollingFrequencyInMs',TO_TIMESTAMP('2022-11-14 15:48:57','YYYY-MM-DD HH24:MI:SS'),100,'N','1000','Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','de.metas.externalsystem',0,14,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Abfragefrequenz in Millisekunden',0,0,TO_TIMESTAMP('2022-11-14 15:48:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:57.704Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584969 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:57.706Z
/* DDL */  select update_Column_Translation_From_AD_Element(581586)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.ProcessedDirectory
-- 2022-11-14T13:48:58.333Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584970,581583,0,10,542258,'ProcessedDirectory',TO_TIMESTAMP('2022-11-14 15:48:58','YYYY-MM-DD HH24:MI:SS'),100,'N','move','Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung relativ zum aktuellen sftp-Zielspeicherort verschoben werden sollen.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Verarbeitet-Verzeichnis',0,0,TO_TIMESTAMP('2022-11-14 15:48:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:58.334Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584970 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:58.335Z
/* DDL */  select update_Column_Translation_From_AD_Element(581583)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_HostName
-- 2022-11-14T13:48:58.924Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584971,581553,0,10,542258,'SFTP_HostName',TO_TIMESTAMP('2022-11-14 15:48:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Host des sftp-Servers.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Hostname',0,0,TO_TIMESTAMP('2022-11-14 15:48:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:58.926Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584971 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:58.928Z
/* DDL */  select update_Column_Translation_From_AD_Element(581553)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Password
-- 2022-11-14T13:48:59.489Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584972,581554,0,10,542258,'SFTP_Password',TO_TIMESTAMP('2022-11-14 15:48:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Passwort, das zur Authentifizierung beim sftp-Server verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Passwort',0,0,TO_TIMESTAMP('2022-11-14 15:48:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:48:59.491Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584972 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:48:59.493Z
/* DDL */  select update_Column_Translation_From_AD_Element(581554)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Port
-- 2022-11-14T13:49:00.088Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584973,581555,0,10,542258,'SFTP_Port',TO_TIMESTAMP('2022-11-14 15:48:59','YYYY-MM-DD HH24:MI:SS'),100,'N','22','Der Port des sftp-Servers.','de.metas.externalsystem',0,14,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Port',0,0,TO_TIMESTAMP('2022-11-14 15:48:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:49:00.090Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584973 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:49:00.092Z
/* DDL */  select update_Column_Translation_From_AD_Element(581555)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_TargetDirectory
-- 2022-11-14T13:49:00.692Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584974,581557,0,10,542258,'SFTP_TargetDirectory',TO_TIMESTAMP('2022-11-14 15:49:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Das Verzeichnis, das zum Abrufen vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-14 15:49:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:49:00.694Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584974 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:49:00.697Z
/* DDL */  select update_Column_Translation_From_AD_Element(581557)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Username
-- 2022-11-14T13:49:01.335Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584975,581556,0,10,542258,'SFTP_Username',TO_TIMESTAMP('2022-11-14 15:49:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Benutzername, der für die Authentifizierung beim sftp-Server verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','SFTP-Benutzername',0,0,TO_TIMESTAMP('2022-11-14 15:49:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:49:01.336Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584975 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:49:01.337Z
/* DDL */  select update_Column_Translation_From_AD_Element(581556)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.Updated
-- 2022-11-14T13:49:01.939Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584976,607,0,16,542258,'Updated',TO_TIMESTAMP('2022-11-14 15:49:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-11-14 15:49:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:49:01.941Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584976 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:49:01.944Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.UpdatedBy
-- 2022-11-14T13:49:02.752Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584977,608,0,18,110,542258,'UpdatedBy',TO_TIMESTAMP('2022-11-14 15:49:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-11-14 15:49:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T13:49:02.754Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584977 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T13:49:02.757Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2022-11-14T13:49:31.270Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_Username')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Username
-- 2022-11-14T13:49:31.280Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584975
;

-- 2022-11-14T13:49:31.281Z
DELETE FROM AD_Column WHERE AD_Column_ID=584975
;

-- 2022-11-14T13:49:39.798Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_TargetDirectory')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_TargetDirectory
-- 2022-11-14T13:49:39.808Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584974
;

-- 2022-11-14T13:49:39.812Z
DELETE FROM AD_Column WHERE AD_Column_ID=584974
;

-- 2022-11-14T13:49:49.976Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_Port')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Port
-- 2022-11-14T13:49:49.986Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584973
;

-- 2022-11-14T13:49:49.990Z
DELETE FROM AD_Column WHERE AD_Column_ID=584973
;

-- 2022-11-14T13:50:00.565Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_Password')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Password
-- 2022-11-14T13:50:00.574Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584972
;

-- 2022-11-14T13:50:00.575Z
DELETE FROM AD_Column WHERE AD_Column_ID=584972
;

-- 2022-11-14T13:50:16.911Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_HostName')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_HostName
-- 2022-11-14T13:50:16.919Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584971
;

-- 2022-11-14T13:50:16.922Z
DELETE FROM AD_Column WHERE AD_Column_ID=584971
;

-- 2022-11-14T13:50:29.676Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS PollingFrequencyInMs')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.PollingFrequencyInMs
-- 2022-11-14T13:50:29.683Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584969
;

-- 2022-11-14T13:50:29.686Z
DELETE FROM AD_Column WHERE AD_Column_ID=584969
;

-- 2022-11-14T13:51:20.432Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN AD_Client_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-14T13:51:24.928Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN AD_Org_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-14T13:51:28.990Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN Created TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-11-14T13:51:32.804Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN CreatedBy NUMERIC(10) NOT NULL')
;

-- 2022-11-14T13:51:36.728Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN ErroredDirectory VARCHAR(255) DEFAULT ''error'' NOT NULL')
;

-- 2022-11-14T13:51:41.410Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','ExternalSystem_Config_SAP_LocalFile_ID','NUMERIC(10)',null,null)
;

-- 2022-11-14T13:51:45.471Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN IsActive CHAR(1) CHECK (IsActive IN (''Y'',''N'')) NOT NULL')
;

-- 2022-11-14T13:51:50.438Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN ProcessedDirectory VARCHAR(255) DEFAULT ''move'' NOT NULL')
;

-- 2022-11-14T13:51:54.858Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN Updated TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-11-14T13:51:59.525Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN UpdatedBy NUMERIC(10) NOT NULL')
;

-- 2022-11-14T13:52:03.926Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-11-14T14:00:21.344Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581672,0,TO_TIMESTAMP('2022-11-14 16:00:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','External System SAP','External System SAP',TO_TIMESTAMP('2022-11-14 16:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:00:21.354Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581672 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-14T14:00:39.746Z
UPDATE AD_Element_Trl SET Name='Externes System SAP', PrintName='Externes System SAP',Updated=TO_TIMESTAMP('2022-11-14 16:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581672 AND AD_Language='de_CH'
;

-- 2022-11-14T14:00:39.747Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581672,'de_CH')
;

-- Element: null
-- 2022-11-14T14:00:50.868Z
UPDATE AD_Element_Trl SET Name='Externes System SAP', PrintName='Externes System SAP',Updated=TO_TIMESTAMP('2022-11-14 16:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581672 AND AD_Language='de_DE'
;

-- 2022-11-14T14:00:50.870Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581672,'de_DE')
;

-- 2022-11-14T14:00:50.872Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581672,'de_DE')
;

-- Element: null
-- 2022-11-14T14:01:03.501Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-14 16:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581672 AND AD_Language='en_US'
;

-- 2022-11-14T14:01:03.502Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581672,'en_US')
;

-- Window: Externes System SAP, InternalName=null
-- 2022-11-14T14:02:10.899Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581672,0,541631,TO_TIMESTAMP('2022-11-14 16:02:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','N','N','N','Y','Externes System SAP','N',TO_TIMESTAMP('2022-11-14 16:02:10','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-11-14T14:02:10.905Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541631 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-11-14T14:02:10.911Z
/* DDL */  select update_window_translation_from_ad_element(581672)
;

-- 2022-11-14T14:02:10.915Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541631
;

-- 2022-11-14T14:02:10.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541631)
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-11-14T14:03:53.299Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581532,0,546671,542238,541631,'Y',TO_TIMESTAMP('2022-11-14 16:03:53','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','A','ExternalSystem_Config_SAP','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_SAP','N',10,0,TO_TIMESTAMP('2022-11-14 16:03:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:03:53.307Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546671 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-14T14:03:53.313Z
/* DDL */  select update_tab_translation_from_ad_element(581532)
;

-- 2022-11-14T14:03:53.318Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546671)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Aktiv
-- Column: ExternalSystem_Config_SAP.IsActive
-- 2022-11-14T14:04:19.872Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584668,708031,0,546671,0,TO_TIMESTAMP('2022-11-14 16:04:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',0,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',0,10,0,1,1,TO_TIMESTAMP('2022-11-14 16:04:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:04:19.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T14:04:19.882Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-11-14T14:04:20.885Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708031
;

-- 2022-11-14T14:04:20.886Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708031)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Aktiv
-- Column: ExternalSystem_Config_SAP.IsActive
-- 2022-11-14T14:05:11.384Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem', IsAlwaysUpdateable='', SeqNo=10,Updated=TO_TIMESTAMP('2022-11-14 16:05:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708031
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T14:06:16.447Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584664,708032,0,546671,0,TO_TIMESTAMP('2022-11-14 16:06:16','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','Y','N','Mandant',0,0,1,1,TO_TIMESTAMP('2022-11-14 16:06:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:06:16.449Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T14:06:16.453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-11-14T14:06:16.918Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708032
;

-- 2022-11-14T14:06:16.919Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708032)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-11-14T14:07:07.590Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584665,708033,0,546671,0,TO_TIMESTAMP('2022-11-14 16:07:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',0,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','N','N','Sektion',1,1,TO_TIMESTAMP('2022-11-14 16:07:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:07:07.595Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T14:07:07.604Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-11-14T14:07:08.046Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708033
;

-- 2022-11-14T14:07:08.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708033)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T14:07:34.124Z
UPDATE AD_Field SET SeqNo=NULL, SortNo=NULL,Updated=TO_TIMESTAMP('2022-11-14 16:07:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708032
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-14T14:08:43.235Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584652,708034,0,546671,0,TO_TIMESTAMP('2022-11-14 16:08:43','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','ExternalSystem_Config_SAP',1,1,TO_TIMESTAMP('2022-11-14 16:08:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:08:43.241Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T14:08:43.247Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581532)
;

-- 2022-11-14T14:08:43.250Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708034
;

-- 2022-11-14T14:08:43.251Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708034)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-14T14:10:16.829Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584644,708035,0,546671,0,TO_TIMESTAMP('2022-11-14 16:10:16','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Suchschlüssel',20,20,0,1,1,TO_TIMESTAMP('2022-11-14 16:10:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:10:16.834Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T14:10:16.841Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788)
;

-- 2022-11-14T14:10:16.845Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708035
;

-- 2022-11-14T14:10:16.846Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708035)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-14T14:10:23.476Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2022-11-14 16:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708035
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> External System Config
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-11-14T14:12:14.057Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584643,708036,0,546671,0,TO_TIMESTAMP('2022-11-14 16:12:13','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','External System Config',1,1,TO_TIMESTAMP('2022-11-14 16:12:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T14:12:14.059Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T14:12:14.061Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728)
;

-- 2022-11-14T14:12:14.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708036
;

-- 2022-11-14T14:12:14.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708036)
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U)
-- UI Section: main
-- 2022-11-14T14:12:43.367Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546671,545296,TO_TIMESTAMP('2022-11-14 16:12:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-14 16:12:43','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-14T14:12:43.370Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545296 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main
-- UI Column: 10
-- 2022-11-14T14:13:09.600Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546446,545296,TO_TIMESTAMP('2022-11-14 16:13:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-14 16:13:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main
-- UI Column: 20
-- 2022-11-14T14:13:12.347Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546447,545296,TO_TIMESTAMP('2022-11-14 16:13:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-14 16:13:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20
-- UI Element Group: default
-- 2022-11-14T14:14:29.040Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546447,550015,TO_TIMESTAMP('2022-11-14 16:14:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-14 16:14:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-14T14:16:11.523Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708035,0,546671,613445,550015,'F',TO_TIMESTAMP('2022-11-14 16:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2022-11-14 16:16:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main
-- UI Column: 20
-- 2022-11-14T14:16:54.473Z
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2022-11-14 16:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546446
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main
-- UI Column: 10
-- 2022-11-14T14:17:07.900Z
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2022-11-14 16:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546447
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main
-- UI Column: 10
-- 2022-11-14T14:17:21.934Z
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2022-11-14 16:17:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546446
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main
-- UI Column: 20
-- 2022-11-14T14:17:29.661Z
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2022-11-14 16:17:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=546447
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 10
-- UI Element Group: default
-- 2022-11-14T14:17:58.317Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546446,550016,TO_TIMESTAMP('2022-11-14 16:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-11-14 16:17:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 10 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-14T14:18:24.963Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708035,0,546671,613446,550016,'F',TO_TIMESTAMP('2022-11-14 16:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2022-11-14 16:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20
-- UI Element Group: default
-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20 -> default.Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-14T14:20:07.534Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613445
;

-- 2022-11-14T14:20:07.535Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=550015
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20
-- UI Element Group: flag
-- 2022-11-14T14:20:25.065Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546447,550017,TO_TIMESTAMP('2022-11-14 16:20:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2022-11-14 16:20:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20 -> flag.Aktiv
-- Column: ExternalSystem_Config_SAP.IsActive
-- 2022-11-14T14:20:53.647Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708031,0,546671,613447,550017,'F',TO_TIMESTAMP('2022-11-14 16:20:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-11-14 16:20:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20
-- UI Element Group: org
-- 2022-11-14T14:21:27.068Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546447,550018,TO_TIMESTAMP('2022-11-14 16:21:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-11-14 16:21:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-11-14T14:22:17.362Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708033,0,546671,613448,550018,'F',TO_TIMESTAMP('2022-11-14 16:22:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2022-11-14 16:22:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-11-14T14:28:32.330Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-11-14 16:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613448
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T14:38:05.761Z
UPDATE AD_Field SET DisplayLength=22, IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2022-11-14 16:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708032
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-11-14T14:39:00.461Z
UPDATE AD_Field SET DisplayLength=22,Updated=TO_TIMESTAMP('2022-11-14 16:39:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708033
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> External System Config
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_ID
-- 2022-11-14T14:39:33.992Z
UPDATE AD_Field SET DisplayLength=22,Updated=TO_TIMESTAMP('2022-11-14 16:39:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708036
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-14T14:39:44.592Z
UPDATE AD_Field SET DisplayLength=22,Updated=TO_TIMESTAMP('2022-11-14 16:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708034
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Suchschlüssel
-- Column: ExternalSystem_Config_SAP.ExternalSystemValue
-- 2022-11-14T14:40:05.477Z
UPDATE AD_Field SET DisplayLength=255,Updated=TO_TIMESTAMP('2022-11-14 16:40:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708035
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_SAP.AD_Org_ID
-- 2022-11-14T14:45:09.848Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-11-14 16:45:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613448
;

-- 2022-11-14T14:50:12.551Z
INSERT INTO t_alter_column values('externalsystem_config_sap','AD_Client_ID','NUMERIC(10)',null,null)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T14:51:47.661Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2022-11-14 16:51:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708032
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T14:51:57.380Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2022-11-14 16:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708032
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T14:52:18.858Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2022-11-14 16:52:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708032
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T14:54:33.611Z
UPDATE AD_Field SET IsAlwaysUpdateable='',Updated=TO_TIMESTAMP('2022-11-14 16:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708032
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,U) -> main -> 20 -> org.Mandant
-- Column: ExternalSystem_Config_SAP.AD_Client_ID
-- 2022-11-14T16:48:21.518Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708032,0,546671,613449,550018,'F',TO_TIMESTAMP('2022-11-14 18:48:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2022-11-14 18:48:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP
-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-14T16:50:41.970Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581669,0,546672,542257,541631,'Y',TO_TIMESTAMP('2022-11-14 18:50:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_SAP_SFTP','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'External System Config SAP SFTP','N',20,1,TO_TIMESTAMP('2022-11-14 18:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T16:50:41.977Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546672 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-14T16:50:42.006Z
/* DDL */  select update_tab_translation_from_ad_element(581669)
;

-- 2022-11-14T16:50:42.021Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546672)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-14T17:03:01.312Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-14 19:03:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584941
;

-- 2022-11-14T17:05:22.692Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','ExternalSystem_Config_SAP_SFTP_ID','NUMERIC(10)',null,null)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T17:06:49.648Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-14 19:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584962
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T17:06:53.832Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584978,581671,0,19,542257,'ExternalSystem_Config_SAP_LocalFile_ID',TO_TIMESTAMP('2022-11-14 19:06:53','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'ExternalSystem Config SAP Local File',0,0,TO_TIMESTAMP('2022-11-14 19:06:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T17:06:53.839Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584978 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T17:06:53.851Z
/* DDL */  select update_Column_Translation_From_AD_Element(581671)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_SAP_SFTP.IsActive
-- 2022-11-14T17:09:43.481Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584952,708037,0,546672,0,TO_TIMESTAMP('2022-11-14 19:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',0,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','N','N','N','N','N','N','Aktiv',0,0,1,1,TO_TIMESTAMP('2022-11-14 19:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:09:43.488Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:09:43.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-11-14T17:09:44.033Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708037
;

-- 2022-11-14T17:09:44.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708037)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_SAP_SFTP.IsActive
-- 2022-11-14T17:09:56.355Z
UPDATE AD_Field SET SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-14 19:09:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708037
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_SAP_SFTP.AD_Client_ID
-- 2022-11-14T17:10:23.352Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584945,708038,0,546672,0,TO_TIMESTAMP('2022-11-14 19:10:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','Y','N','Mandant',0,20,0,1,1,TO_TIMESTAMP('2022-11-14 19:10:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:10:23.358Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:10:23.363Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-11-14T17:10:23.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708038
;

-- 2022-11-14T17:10:23.642Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708038)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_SAP_SFTP.AD_Org_ID
-- 2022-11-14T17:10:59.303Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584946,708039,0,546672,0,TO_TIMESTAMP('2022-11-14 19:10:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',0,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','N','N','Sektion',0,30,0,1,1,TO_TIMESTAMP('2022-11-14 19:10:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:10:59.305Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:10:59.314Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-11-14T17:10:59.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708039
;

-- 2022-11-14T17:10:59.553Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708039)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> SFTP-Hostname
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_HostName
-- 2022-11-14T17:12:28.202Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584955,708040,0,546672,0,TO_TIMESTAMP('2022-11-14 19:12:28','YYYY-MM-DD HH24:MI:SS'),100,'Der Host des sftp-Servers.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','SFTP-Hostname',0,40,0,1,1,TO_TIMESTAMP('2022-11-14 19:12:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:12:28.209Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:12:28.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581553)
;

-- 2022-11-14T17:12:28.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708040
;

-- 2022-11-14T17:12:28.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708040)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_SAP_SFTP.AD_Client_ID
-- 2022-11-14T17:12:49.124Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-14 19:12:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708038
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_HostName
-- 2022-11-14T17:15:20.602Z
UPDATE AD_Column SET DefaultValue='FTP-Hostname',Updated=TO_TIMESTAMP('2022-11-14 19:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584955
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_HostName
-- 2022-11-14T17:15:29.638Z
UPDATE AD_Column SET DefaultValue='SFTP-Hostname',Updated=TO_TIMESTAMP('2022-11-14 19:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584955
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Port
-- 2022-11-14T17:15:52.036Z
UPDATE AD_Column SET DefaultValue='SFTP-Port',Updated=TO_TIMESTAMP('2022-11-14 19:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584957
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Username
-- 2022-11-14T17:16:30.665Z
UPDATE AD_Column SET DefaultValue='SFTP-Benutzername',Updated=TO_TIMESTAMP('2022-11-14 19:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584959
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Password
-- 2022-11-14T17:17:13.605Z
UPDATE AD_Column SET DefaultValue='SFTP-Passwort',Updated=TO_TIMESTAMP('2022-11-14 19:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584956
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> SFTP-Port
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Port
-- 2022-11-14T17:19:08.176Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584957,708041,0,546672,0,TO_TIMESTAMP('2022-11-14 19:19:08','YYYY-MM-DD HH24:MI:SS'),100,'Der Port des sftp-Servers.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','SFTP-Port',0,50,0,1,1,TO_TIMESTAMP('2022-11-14 19:19:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:19:08.179Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:19:08.182Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581555)
;

-- 2022-11-14T17:19:08.186Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708041
;

-- 2022-11-14T17:19:08.187Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708041)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> SFTP-Benutzername
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Username
-- 2022-11-14T17:19:31.848Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584959,708042,0,546672,0,TO_TIMESTAMP('2022-11-14 19:19:31','YYYY-MM-DD HH24:MI:SS'),100,'Der Benutzername, der für die Authentifizierung beim sftp-Server verwendet wird.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','SFTP-Benutzername',0,60,0,1,1,TO_TIMESTAMP('2022-11-14 19:19:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:19:31.850Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:19:31.853Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581556)
;

-- 2022-11-14T17:19:31.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708042
;

-- 2022-11-14T17:19:31.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708042)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> SFTP-Passwort
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Password
-- 2022-11-14T17:19:55.691Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584956,708043,0,546672,0,TO_TIMESTAMP('2022-11-14 19:19:55','YYYY-MM-DD HH24:MI:SS'),100,'Das Passwort, das zur Authentifizierung beim sftp-Server verwendet wird.',0,'de.metas.externalsystem',0,'Y','Y','N','Y','N','N','N','N','SFTP-Passwort',0,70,0,1,1,TO_TIMESTAMP('2022-11-14 19:19:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:19:55.696Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:19:55.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581554)
;

-- 2022-11-14T17:19:55.708Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708043
;

-- 2022-11-14T17:19:55.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708043)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> SFTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_TargetDirectory
-- 2022-11-14T17:21:13.628Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584958,708044,0,546672,0,TO_TIMESTAMP('2022-11-14 19:21:13','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','SFTP-Zielverzeichnis',0,80,0,1,1,TO_TIMESTAMP('2022-11-14 19:21:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:21:13.630Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:21:13.632Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581557)
;

-- 2022-11-14T17:21:13.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708044
;

-- 2022-11-14T17:21:13.638Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708044)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.ProcessedDirectory
-- 2022-11-14T17:21:34.630Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584954,708045,0,546672,0,TO_TIMESTAMP('2022-11-14 19:21:34','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung relativ zum aktuellen sftp-Zielspeicherort verschoben werden sollen.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Verarbeitet-Verzeichnis',0,90,0,1,1,TO_TIMESTAMP('2022-11-14 19:21:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:21:34.633Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:21:34.640Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581583)
;

-- 2022-11-14T17:21:34.645Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708045
;

-- 2022-11-14T17:21:34.646Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708045)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Fehler-Verzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.ErroredDirectory
-- 2022-11-14T17:21:50.449Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584949,708046,0,546672,0,TO_TIMESTAMP('2022-11-14 19:21:50','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen, relativ zum aktuellen sftp-Zielort.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Fehler-Verzeichnis',0,100,0,1,1,TO_TIMESTAMP('2022-11-14 19:21:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:21:50.451Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:21:50.453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581584)
;

-- 2022-11-14T17:21:50.458Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708046
;

-- 2022-11-14T17:21:50.458Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708046)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_SAP_SFTP.PollingFrequencyInMs
-- 2022-11-14T17:22:18.072Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584953,708047,0,546672,0,TO_TIMESTAMP('2022-11-14 19:22:17','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Abfragefrequenz in Millisekunden',0,110,0,1,1,TO_TIMESTAMP('2022-11-14 19:22:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:22:18.073Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:22:18.079Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581586)
;

-- 2022-11-14T17:22:18.085Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708047
;

-- 2022-11-14T17:22:18.085Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708047)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_SAP_SFTP.PollingFrequencyInMs
-- 2022-11-14T17:22:23.523Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-11-14 19:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708047
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem)
-- UI Section: main
-- 2022-11-14T17:23:14.142Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546672,545297,TO_TIMESTAMP('2022-11-14 19:23:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-14 19:23:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-14T17:23:14.146Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545297 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2022-11-14T17:23:29.455Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,545297,TO_TIMESTAMP('2022-11-14 19:23:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-14 19:23:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2022-11-14T17:23:31.212Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546449,545297,TO_TIMESTAMP('2022-11-14 19:23:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-14 19:23:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2022-11-14T17:23:48.991Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546448,550021,TO_TIMESTAMP('2022-11-14 19:23:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-11-14 19:23:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Hostname
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_HostName
-- 2022-11-14T17:31:38.157Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708040,0,546672,613450,550021,'F',TO_TIMESTAMP('2022-11-14 19:31:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Host des sftp-Servers.','Y','N','N','Y','N','N','N',0,'SFTP-Hostname',10,0,0,TO_TIMESTAMP('2022-11-14 19:31:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Port
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Port
-- 2022-11-14T17:31:51.657Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708041,0,546672,613451,550021,'F',TO_TIMESTAMP('2022-11-14 19:31:51','YYYY-MM-DD HH24:MI:SS'),100,'Der Port des sftp-Servers.','Y','N','N','Y','N','N','N',0,'SFTP-Port',20,0,0,TO_TIMESTAMP('2022-11-14 19:31:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Benutzername
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Username
-- 2022-11-14T17:32:34.443Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708042,0,546672,613452,550021,'F',TO_TIMESTAMP('2022-11-14 19:32:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Benutzername, der für die Authentifizierung beim sftp-Server verwendet wird.','Y','N','N','Y','N','N','N',0,'SFTP-Benutzername',30,0,0,TO_TIMESTAMP('2022-11-14 19:32:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Passwort
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Password
-- 2022-11-14T17:32:46.811Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708043,0,546672,613453,550021,'F',TO_TIMESTAMP('2022-11-14 19:32:46','YYYY-MM-DD HH24:MI:SS'),100,'Das Passwort, das zur Authentifizierung beim sftp-Server verwendet wird.','Y','N','N','Y','N','N','N',0,'SFTP-Passwort',40,0,0,TO_TIMESTAMP('2022-11-14 19:32:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_TargetDirectory
-- 2022-11-14T17:33:02.885Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708044,0,546672,613454,550021,'F',TO_TIMESTAMP('2022-11-14 19:33:02','YYYY-MM-DD HH24:MI:SS'),100,'Das Verzeichnis, das zum Abrufen vom sftp-Server verwendet wird. (Wird hier kein Wert angegeben, werden die Dateien aus dem Stammverzeichnis des sftp-Servers gezogen.)','Y','N','N','Y','N','N','N',0,'SFTP-Zielverzeichnis',50,0,0,TO_TIMESTAMP('2022-11-14 19:33:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.ProcessedDirectory
-- 2022-11-14T17:34:21.036Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708045,0,546672,613455,550021,'F',TO_TIMESTAMP('2022-11-14 19:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung relativ zum aktuellen sftp-Zielspeicherort verschoben werden sollen.','Y','N','N','Y','N','N','N',0,'Verarbeitet-Verzeichnis',60,0,0,TO_TIMESTAMP('2022-11-14 19:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.Fehler-Verzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.ErroredDirectory
-- 2022-11-14T17:34:46.876Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708046,0,546672,613456,550021,'F',TO_TIMESTAMP('2022-11-14 19:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen, relativ zum aktuellen sftp-Zielort.','Y','N','N','Y','N','N','N',0,'Fehler-Verzeichnis',70,0,0,TO_TIMESTAMP('2022-11-14 19:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_SAP_SFTP.PollingFrequencyInMs
-- 2022-11-14T17:35:11.239Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708047,0,546672,613457,550021,'F',TO_TIMESTAMP('2022-11-14 19:35:11','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','Y','N','N','Y','N','N','N',0,'Abfragefrequenz in Millisekunden',80,0,0,TO_TIMESTAMP('2022-11-14 19:35:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Hostname
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_HostName
-- 2022-11-14T17:36:42.865Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-14 19:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613450
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Port
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Port
-- 2022-11-14T17:36:42.870Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-14 19:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613451
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Benutzername
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Username
-- 2022-11-14T17:36:42.874Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-14 19:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613452
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File
-- Table: ExternalSystem_Config_SAP_LocalFile
-- 2022-11-14T17:37:43.862Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581671,0,546673,542258,541631,'Y',TO_TIMESTAMP('2022-11-14 19:37:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_SAP_LocalFile','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem Config SAP Local File','N',30,1,TO_TIMESTAMP('2022-11-14 19:37:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:37:43.866Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546673 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-14T17:37:43.872Z
/* DDL */  select update_tab_translation_from_ad_element(581671)
;

-- 2022-11-14T17:37:43.877Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546673)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Client_ID
-- 2022-11-14T17:38:02.858Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584963,708048,0,546673,0,TO_TIMESTAMP('2022-11-14 19:38:02','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','Y','N','Mandant',0,10,0,1,1,TO_TIMESTAMP('2022-11-14 19:38:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:38:02.863Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:38:02.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2022-11-14T17:38:03.721Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708048
;

-- 2022-11-14T17:38:03.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708048)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Org_ID
-- 2022-11-14T17:38:10.933Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584964,708049,0,546673,0,TO_TIMESTAMP('2022-11-14 19:38:10','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',0,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','N','N','Sektion',0,20,0,1,1,TO_TIMESTAMP('2022-11-14 19:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:38:10.934Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:38:10.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2022-11-14T17:38:11.314Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708049
;

-- 2022-11-14T17:38:11.315Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708049)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Org_ID
-- 2022-11-14T17:38:21.811Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-14 19:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708049
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Client_ID
-- 2022-11-14T17:38:34.125Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-14 19:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708048
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_SAP_LocalFile.IsActive
-- 2022-11-14T17:39:24.082Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584968,708050,0,546673,0,TO_TIMESTAMP('2022-11-14 19:39:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',0,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','N','N','N','N','N','N','Aktiv',0,30,0,1,1,TO_TIMESTAMP('2022-11-14 19:39:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:39:24.088Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:39:24.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2022-11-14T17:39:24.794Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708050
;

-- 2022-11-14T17:39:24.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708050)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ProcessedDirectory
-- 2022-11-14T17:39:44.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584970,708051,0,546673,0,TO_TIMESTAMP('2022-11-14 19:39:44','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung relativ zum aktuellen sftp-Zielspeicherort verschoben werden sollen.',0,'U',0,'Y','Y','Y','N','N','N','N','N','Verarbeitet-Verzeichnis',0,40,0,1,1,TO_TIMESTAMP('2022-11-14 19:39:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:39:45.004Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:39:45.010Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581583)
;

-- 2022-11-14T17:39:45.015Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708051
;

-- 2022-11-14T17:39:45.016Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708051)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ProcessedDirectory
-- 2022-11-14T17:39:58.536Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-14 19:39:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708051
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> Fehler-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ErroredDirectory
-- 2022-11-14T17:40:38.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584967,708052,0,546673,0,TO_TIMESTAMP('2022-11-14 19:40:38','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen, relativ zum aktuellen sftp-Zielort.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Fehler-Verzeichnis',0,50,0,1,1,TO_TIMESTAMP('2022-11-14 19:40:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:40:38.611Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:40:38.619Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581584)
;

-- 2022-11-14T17:40:38.624Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708052
;

-- 2022-11-14T17:40:38.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708052)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> ExternalSystem Config SAP Local File
-- Column: ExternalSystem_Config_SAP_LocalFile.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T17:41:05.669Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584962,708053,0,546673,0,TO_TIMESTAMP('2022-11-14 19:41:05','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','N','N','N','N','N','N','ExternalSystem Config SAP Local File',0,60,0,1,1,TO_TIMESTAMP('2022-11-14 19:41:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T17:41:05.671Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T17:41:05.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581671)
;

-- 2022-11-14T17:41:05.680Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708053
;

-- 2022-11-14T17:41:05.681Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708053)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> ExternalSystem Config SAP Local File
-- Column: ExternalSystem_Config_SAP_LocalFile.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T17:41:14.136Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-14 19:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708053
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem)
-- UI Section: main
-- 2022-11-14T17:41:29.227Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546673,545298,TO_TIMESTAMP('2022-11-14 19:41:29','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-14 19:41:29','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-14T17:41:29.228Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545298 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flag
-- 2022-11-14T17:43:17.339Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546449,550022,TO_TIMESTAMP('2022-11-14 19:43:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2022-11-14 19:43:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2022-11-14T17:43:24.892Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546449,550023,TO_TIMESTAMP('2022-11-14 19:43:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-11-14 19:43:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 20 -> flag.Aktiv
-- Column: ExternalSystem_Config_SAP_SFTP.IsActive
-- 2022-11-14T17:45:20.221Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708037,0,546672,613458,550022,'F',TO_TIMESTAMP('2022-11-14 19:45:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-11-14 19:45:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 20 -> org.Mandant
-- Column: ExternalSystem_Config_SAP_SFTP.AD_Client_ID
-- 2022-11-14T17:46:30.777Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708038,0,546672,613459,550023,'F',TO_TIMESTAMP('2022-11-14 19:46:30','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-11-14 19:46:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_SAP_SFTP.AD_Org_ID
-- 2022-11-14T17:47:22.702Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708039,0,546672,613460,550023,'F',TO_TIMESTAMP('2022-11-14 19:47:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-11-14 19:47:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2022-11-14T17:48:16.113Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,545298,TO_TIMESTAMP('2022-11-14 19:48:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-14 19:48:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2022-11-14T17:48:17.568Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546451,545298,TO_TIMESTAMP('2022-11-14 19:48:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-14 19:48:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 10
-- UI Element Group: main
-- 2022-11-14T17:48:26.442Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546450,550024,TO_TIMESTAMP('2022-11-14 19:48:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-11-14 19:48:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 10 -> main.Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ProcessedDirectory
-- 2022-11-14T17:49:32.353Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708051,0,546673,613461,550024,'F',TO_TIMESTAMP('2022-11-14 19:49:32','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung relativ zum aktuellen sftp-Zielspeicherort verschoben werden sollen.','Y','N','N','Y','N','N','N',0,'Verarbeitet-Verzeichnis',10,0,0,TO_TIMESTAMP('2022-11-14 19:49:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 10 -> main.Fehler-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ErroredDirectory
-- 2022-11-14T17:49:55.687Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708052,0,546673,613462,550024,'F',TO_TIMESTAMP('2022-11-14 19:49:55','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen, relativ zum aktuellen sftp-Zielort.','Y','N','N','Y','N','N','N',0,'Fehler-Verzeichnis',20,0,0,TO_TIMESTAMP('2022-11-14 19:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flag
-- 2022-11-14T17:50:12.907Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546451,550025,TO_TIMESTAMP('2022-11-14 19:50:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2022-11-14 19:50:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 20 -> flag.Aktiv
-- Column: ExternalSystem_Config_SAP_LocalFile.IsActive
-- 2022-11-14T17:50:41.819Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708050,0,546673,613463,550025,'F',TO_TIMESTAMP('2022-11-14 19:50:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-11-14 19:50:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2022-11-14T17:50:54.027Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546451,550026,TO_TIMESTAMP('2022-11-14 19:50:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-11-14 19:50:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 20 -> org.Mandant
-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Client_ID
-- 2022-11-14T17:51:20.987Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708048,0,546673,613464,550026,'F',TO_TIMESTAMP('2022-11-14 19:51:20','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-11-14 19:51:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem Config SAP Local File(546673,de.metas.externalsystem) -> main -> 20 -> org.Sektion
-- Column: ExternalSystem_Config_SAP_LocalFile.AD_Org_ID
-- 2022-11-14T17:51:44Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708049,0,546673,613465,550026,'F',TO_TIMESTAMP('2022-11-14 19:51:43','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-11-14 19:51:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-14T18:06:19.756Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584979,581669,0,19,542238,'ExternalSystem_Config_SAP_SFTP_ID',TO_TIMESTAMP('2022-11-14 20:06:19','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External System Config SAP SFTP',0,0,TO_TIMESTAMP('2022-11-14 20:06:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T18:06:19.771Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584979 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T18:06:19.777Z
/* DDL */  select update_Column_Translation_From_AD_Element(581669)
;

-- 2022-11-14T18:06:26.270Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN ExternalSystem_Config_SAP_SFTP_ID NUMERIC(10)')
;

-- 2022-11-14T18:06:26.285Z
ALTER TABLE ExternalSystem_Config_SAP ADD CONSTRAINT ExternalSystemConfigSAPSFTP_ExternalSystemConfigSAP FOREIGN KEY (ExternalSystem_Config_SAP_SFTP_ID) REFERENCES public.ExternalSystem_Config_SAP_SFTP DEFERRABLE INITIALLY DEFERRED
;

-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:06:45.109Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584980,581671,0,19,542238,'ExternalSystem_Config_SAP_LocalFile_ID',TO_TIMESTAMP('2022-11-14 20:06:44','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ExternalSystem Config SAP Local File',0,0,TO_TIMESTAMP('2022-11-14 20:06:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-14T18:06:45.111Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=584980 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-14T18:06:45.114Z
/* DDL */  select update_Column_Translation_From_AD_Element(581671)
;

-- 2022-11-14T18:06:46.963Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE public.ExternalSystem_Config_SAP ADD COLUMN ExternalSystem_Config_SAP_LocalFile_ID NUMERIC(10)')
;

-- 2022-11-14T18:06:46.972Z
ALTER TABLE ExternalSystem_Config_SAP ADD CONSTRAINT ExternalSystemConfigSAPLocalFile_ExternalSystemConfigSAP FOREIGN KEY (ExternalSystem_Config_SAP_LocalFile_ID) REFERENCES public.ExternalSystem_Config_SAP_LocalFile DEFERRABLE INITIALLY DEFERRED
;

-- 2022-11-14T18:12:23.102Z
INSERT INTO t_alter_column values('externalsystem_config_sap','ExternalSystem_Config_SAP_LocalFile_ID','NUMERIC(10)',null,null)
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Table: ExternalSystem_Config_SAP
-- 2022-11-14T18:20:24.369Z
UPDATE AD_Tab SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-14 20:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546671
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> External System Config SAP SFTP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-14T18:20:56.195Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584979,708054,0,546671,0,TO_TIMESTAMP('2022-11-14 20:20:56','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','External System Config SAP SFTP',0,30,0,1,1,TO_TIMESTAMP('2022-11-14 20:20:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T18:20:56.197Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T18:20:56.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581669)
;

-- 2022-11-14T18:20:56.206Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708054
;

-- 2022-11-14T18:20:56.207Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708054)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> ExternalSystem Config SAP Local File
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:21:16.687Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584980,708055,0,546671,0,TO_TIMESTAMP('2022-11-14 20:21:16','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','ExternalSystem Config SAP Local File',0,40,0,1,1,TO_TIMESTAMP('2022-11-14 20:21:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T18:21:16.692Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T18:21:16.696Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581671)
;

-- 2022-11-14T18:21:16.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708055
;

-- 2022-11-14T18:21:16.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708055)
;

-- Table: External_System_Config_SAP_LocalFile
-- 2022-11-14T18:24:50.214Z
UPDATE AD_Table SET Name='External System Config SAP Local File', TableName='External_System_Config_SAP_LocalFile',Updated=TO_TIMESTAMP('2022-11-14 20:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542258
;

-- 2022-11-14T18:24:50.220Z
UPDATE AD_Sequence SET Name='External_System_Config_SAP_LocalFile',Updated=TO_TIMESTAMP('2022-11-14 20:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=556050
;

-- 2022-11-14T18:24:50.228Z
ALTER SEQUENCE ExternalSystem_Config_SAP_LocalFile_SEQ RENAME TO External_System_Config_SAP_LocalFile_SEQ
;

-- Table: ExternalSystem_Config_SAP_LocalFile
-- 2022-11-14T18:25:13.766Z
UPDATE AD_Table SET TableName='ExternalSystem_Config_SAP_LocalFile',Updated=TO_TIMESTAMP('2022-11-14 20:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542258
;

-- 2022-11-14T18:25:13.768Z
UPDATE AD_Sequence SET Name='ExternalSystem_Config_SAP_LocalFile',Updated=TO_TIMESTAMP('2022-11-14 20:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=556050
;

-- 2022-11-14T18:25:13.774Z
ALTER SEQUENCE External_System_Config_SAP_LocalFile_SEQ RENAME TO ExternalSystem_Config_SAP_LocalFile_SEQ
;

-- Element: ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:28:44.951Z
UPDATE AD_Element_Trl SET Name='External System Config SAP Local File', PrintName='External System Config SAP Local File',Updated=TO_TIMESTAMP('2022-11-14 20:28:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581671 AND AD_Language='de_CH'
;

-- 2022-11-14T18:28:44.953Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581671,'de_CH')
;

-- Element: ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:29:00.708Z
UPDATE AD_Element_Trl SET Name='External System Config SAP Local File', PrintName='External System Config SAP Local File',Updated=TO_TIMESTAMP('2022-11-14 20:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581671 AND AD_Language='de_DE'
;

-- 2022-11-14T18:29:00.709Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581671,'de_DE')
;

-- 2022-11-14T18:29:00.715Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581671,'de_DE')
;

-- Element: ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:29:04.105Z
UPDATE AD_Element_Trl SET Name='External System Config SAP Local File', PrintName='External System Config SAP Local File',Updated=TO_TIMESTAMP('2022-11-14 20:29:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581671 AND AD_Language='en_US'
;

-- 2022-11-14T18:29:04.106Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581671,'en_US')
;

-- Element: ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:29:07.945Z
UPDATE AD_Element_Trl SET Name='External System Config SAP Local File', PrintName='External System Config SAP Local File',Updated=TO_TIMESTAMP('2022-11-14 20:29:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581671 AND AD_Language='nl_NL'
;

-- 2022-11-14T18:29:07.947Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581671,'nl_NL')
;

-- 2022-11-14T18:29:23.170Z
INSERT INTO t_alter_column values('externalsystem_config_sap','ExternalSystem_Config_SAP_LocalFile_ID','NUMERIC(10)',null,null)
;

-- 2022-11-14T18:38:22.109Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708054
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> External System Config SAP SFTP
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-14T18:38:22.116Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708054
;

-- 2022-11-14T18:38:22.125Z
DELETE FROM AD_Field WHERE AD_Field_ID=708054
;

-- 2022-11-14T18:38:24.941Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708055
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> External System Config SAP Local File
-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:38:24.942Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708055
;

-- 2022-11-14T18:38:24.944Z
DELETE FROM AD_Field WHERE AD_Field_ID=708055
;

-- 2022-11-14T18:48:45.909Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE ExternalSystem_Config_SAP_SFTP DROP COLUMN IF EXISTS ExternalSystem_Config_SAP_LocalFile_ID')
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-14T18:48:45.938Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584978
;

-- 2022-11-14T18:48:45.955Z
DELETE FROM AD_Column WHERE AD_Column_ID=584978
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> External System Config SAP SFTP
-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-14T18:50:06.144Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584941,708056,0,546672,0,TO_TIMESTAMP('2022-11-14 20:50:06','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','N','N','N','N','N','N','External System Config SAP SFTP',0,120,0,1,1,TO_TIMESTAMP('2022-11-14 20:50:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T18:50:06.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-14T18:50:06.163Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581669)
;

-- 2022-11-14T18:50:06.172Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708056
;

-- 2022-11-14T18:50:06.178Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708056)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP(546672,de.metas.externalsystem) -> External System Config SAP SFTP
-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-14T18:50:17.288Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-14 20:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708056
;

-- 2022-11-15T14:52:03.641Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581675,0,TO_TIMESTAMP('2022-11-15 16:52:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','SFTP','SFTP',TO_TIMESTAMP('2022-11-15 16:52:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T14:52:03.656Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581675 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> SFTP
-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-15T14:52:20.703Z
UPDATE AD_Tab SET AD_Element_ID=581675, CommitWarning=NULL, Description=NULL, Help=NULL, Name='SFTP',Updated=TO_TIMESTAMP('2022-11-15 16:52:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546672
;

-- 2022-11-15T14:52:20.706Z
/* DDL */  select update_tab_translation_from_ad_element(581675)
;

-- 2022-11-15T14:52:20.739Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546672)
;

-- 2022-11-15T14:53:37.194Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581676,0,TO_TIMESTAMP('2022-11-15 16:53:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Local File','Local File',TO_TIMESTAMP('2022-11-15 16:53:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-15T14:53:37.199Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581676 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-15T14:54:17.479Z
UPDATE AD_Element_Trl SET Name='Lokale Datei', PrintName='Lokale Datei',Updated=TO_TIMESTAMP('2022-11-15 16:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581676 AND AD_Language='de_CH'
;

-- 2022-11-15T14:54:17.482Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581676,'de_CH')
;

-- Element: null
-- 2022-11-15T14:54:21.622Z
UPDATE AD_Element_Trl SET Name='Lokale Datei', PrintName='Lokale Datei',Updated=TO_TIMESTAMP('2022-11-15 16:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581676 AND AD_Language='de_DE'
;

-- 2022-11-15T14:54:21.625Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581676,'de_DE')
;

-- 2022-11-15T14:54:21.627Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581676,'de_DE')
;

-- Element: null
-- 2022-11-15T14:54:24.389Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-15 16:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581676 AND AD_Language='en_US'
;

-- 2022-11-15T14:54:24.392Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581676,'en_US')
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> Lokale Datei
-- Table: ExternalSystem_Config_SAP_LocalFile
-- 2022-11-15T14:54:57.386Z
UPDATE AD_Tab SET AD_Element_ID=581676, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Lokale Datei',Updated=TO_TIMESTAMP('2022-11-15 16:54:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546673
;

-- 2022-11-15T14:54:57.388Z
/* DDL */  select update_tab_translation_from_ad_element(581676)
;

-- 2022-11-15T14:54:57.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546673)
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> SFTP
-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-16T17:27:34.208Z
UPDATE AD_Tab SET AD_Column_ID=584941,Updated=TO_TIMESTAMP('2022-11-16 19:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546672
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> Lokale Datei
-- Table: ExternalSystem_Config_SAP_LocalFile
-- 2022-11-16T17:27:45.508Z
UPDATE AD_Tab SET AD_Column_ID=584962,Updated=TO_TIMESTAMP('2022-11-16 19:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546673
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> SFTP
-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-16T17:14:56.583Z
UPDATE AD_Tab SET Parent_Column_ID=584652,Updated=TO_TIMESTAMP('2022-11-16 19:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546672
;

-- Tab: Externes System SAP(541631,de.metas.externalsystem) -> Lokale Datei
-- Table: ExternalSystem_Config_SAP_LocalFile
-- 2022-11-16T17:15:23.525Z
UPDATE AD_Tab SET Parent_Column_ID=584652,Updated=TO_TIMESTAMP('2022-11-16 19:15:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546673
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Product_TargetDirectory
-- 2022-11-16T11:42:50.943Z
UPDATE AD_Column SET AD_Element_ID=581603, ColumnName='SFTP_Product_TargetDirectory', Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.', Help=NULL, Name='SFTP-Produkt-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-16 13:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584958
;

-- 2022-11-16T11:42:50.943Z
UPDATE AD_Field SET Name='SFTP-Produkt-Zielverzeichnis', Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.', Help=NULL WHERE AD_Column_ID=584958
;

-- 2022-11-16T11:42:50.990Z
/* DDL */  select update_Column_Translation_From_AD_Element(581603)
;

/* DDL */ select db_alter_table('ExternalSystem_Config_SAP_SFTP', 'ALTER TABLE ExternalSystem_Config_SAP_SFTP RENAME COLUMN SFTP_TargetDirectory to SFTP_Product_TargetDirectory;');
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_ID
-- 2022-11-16T11:46:22.052Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585039,578728,0,19,542257,'ExternalSystem_Config_ID',TO_TIMESTAMP('2022-11-16 13:46:21','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','External System Config',0,0,TO_TIMESTAMP('2022-11-16 13:46:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T11:46:22.059Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585039 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T11:46:22.069Z
/* DDL */  select update_Column_Translation_From_AD_Element(578728)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-16T11:46:22.971Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585040,581671,0,19,542257,'ExternalSystem_Config_SAP_LocalFile_ID',TO_TIMESTAMP('2022-11-16 13:46:22','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','External System Config SAP Local File',0,0,TO_TIMESTAMP('2022-11-16 13:46:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T11:46:22.974Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585040 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T11:46:22.980Z
/* DDL */  select update_Column_Translation_From_AD_Element(581671)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystemValue
-- 2022-11-16T11:46:23.847Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585041,578788,0,10,542257,'ExternalSystemValue',TO_TIMESTAMP('2022-11-16 13:46:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Suchschlüssel',0,0,TO_TIMESTAMP('2022-11-16 13:46:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T11:46:23.851Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585041 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T11:46:23.854Z
/* DDL */  select update_Column_Translation_From_AD_Element(578788)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_FileName_Pattern
-- 2022-11-16T11:46:24.734Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585042,581668,0,10,542257,'SFTP_BPartner_FileName_Pattern',TO_TIMESTAMP('2022-11-16 13:46:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP-Geschäftspartner Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-16 13:46:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T11:46:24.737Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585042 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T11:46:24.743Z
/* DDL */  select update_Column_Translation_From_AD_Element(581668)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_TargetDirectory
-- 2022-11-16T11:46:25.632Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585043,581604,0,10,542257,'SFTP_BPartner_TargetDirectory',TO_TIMESTAMP('2022-11-16 13:46:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP-Geschäftspartner-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-16 13:46:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T11:46:25.635Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585043 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T11:46:25.640Z
/* DDL */  select update_Column_Translation_From_AD_Element(581604)
;

-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Product_FileName_Pattern
-- 2022-11-16T11:46:26.614Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585044,581667,0,10,542257,'SFTP_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-16 13:46:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SFTP-Produkt Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-16 13:46:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T11:46:26.617Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585044 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T11:46:26.621Z
/* DDL */  select update_Column_Translation_From_AD_Element(581667)
;

-- 2022-11-16T11:47:07.472Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_BPartner_TargetDirectory VARCHAR(255)')
;

-- 2022-11-16T11:47:19.076Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_BPartner_FileName_Pattern VARCHAR(255)')
;

-- 2022-11-16T11:47:29.331Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN SFTP_Product_FileName_Pattern VARCHAR(255)')
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> partner.SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_FileName_Pattern
-- 2022-11-16T11:50:21.883Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613443
;

-- 2022-11-16T11:50:21.897Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708029
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_FileName_Pattern
-- 2022-11-16T11:50:21.916Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708029
;

-- 2022-11-16T11:50:21.924Z
DELETE FROM AD_Field WHERE AD_Field_ID=708029
;

-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_FileName_Pattern
-- 2022-11-16T11:50:21.939Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584936
;

-- 2022-11-16T11:50:21.944Z
DELETE FROM AD_Column WHERE AD_Column_ID=584936
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> partner.SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_TargetDirectory
-- 2022-11-16T11:50:43.823Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613302
;

-- 2022-11-16T11:50:43.827Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707860
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_TargetDirectory
-- 2022-11-16T11:50:43.833Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=707860
;

-- 2022-11-16T11:50:43.840Z
DELETE FROM AD_Field WHERE AD_Field_ID=707860
;

-- Column: ExternalSystem_Config_SAP.SFTP_BPartner_TargetDirectory
-- 2022-11-16T11:50:43.871Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584802
;

-- 2022-11-16T11:50:43.875Z
DELETE FROM AD_Column WHERE AD_Column_ID=584802
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> main -> 10 -> product.SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-16T11:50:51.621Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613442
;

-- 2022-11-16T11:50:51.623Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708028
;

-- Field: Externes System(541024,de.metas.externalsystem) -> SAP(546647,de.metas.externalsystem) -> SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-16T11:50:51.628Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708028
;

-- 2022-11-16T11:50:51.633Z
DELETE FROM AD_Field WHERE AD_Field_ID=708028
;

-- Column: ExternalSystem_Config_SAP.SFTP_Product_FileName_Pattern
-- 2022-11-16T11:50:51.673Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584935
;

-- 2022-11-16T11:50:51.676Z
DELETE FROM AD_Column WHERE AD_Column_ID=584935
;

-- 2022-11-16T11:51:31.951Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE ExternalSystem_Config_SAP_SFTP DROP COLUMN IF EXISTS ExternalSystem_Config_SAP_LocalFile_ID')
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-16T11:51:32.005Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585040
;

-- 2022-11-16T11:51:32.011Z
DELETE FROM AD_Column WHERE AD_Column_ID=585040
;

-- 2022-11-16T11:51:46.751Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE ExternalSystem_Config_SAP_SFTP DROP COLUMN IF EXISTS ExternalSystem_Config_ID')
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_ID
-- 2022-11-16T11:51:46.784Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585039
;

-- 2022-11-16T11:51:46.806Z
DELETE FROM AD_Column WHERE AD_Column_ID=585039
;

-- 2022-11-16T11:52:05.060Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE ExternalSystem_Config_SAP_SFTP DROP COLUMN IF EXISTS ExternalSystemValue')
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystemValue
-- 2022-11-16T11:52:05.080Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585041
;

-- 2022-11-16T11:52:05.086Z
DELETE FROM AD_Column WHERE AD_Column_ID=585041
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-11-16T12:03:34.755Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners synchronization with SAP external system. The business partner files are fetched from the configured sftp server.', Name='Start Business Partners Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543316
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-11-16T12:03:42.280Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners synchronization with SAP external system. The business partner files are fetched from the configured sftp server.', Name='Start Business Partners Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:03:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543316
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-11-16T12:04:11.163Z
UPDATE AD_Ref_List_Trl SET Name='Start der Geschäftspartner-Synchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:04:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543316
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-11-16T12:04:46.447Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von dem konfigurierten sftp-Server geholt.',Updated=TO_TIMESTAMP('2022-11-16 14:04:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543316
;

-- Reference Item: External_Request SAP -> startBPartnerSync_import
-- 2022-11-16T12:04:55.263Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von dem konfigurierten sftp-Server geholt.', Name='Start der Geschäftspartner-Synchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:04:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543316
;

-- Reference: External_Request SAP
-- Value: startBPartnerSyncSFTP
-- ValueName: import
-- 2022-11-16T12:05:30.205Z
UPDATE AD_Ref_List SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von dem konfigurierten sftp-Server geholt.', Name='Start der Geschäftspartner-Synchronisation SFTP', Value='startBPartnerSyncSFTP',Updated=TO_TIMESTAMP('2022-11-16 14:05:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543316
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-11-16T12:06:38.352Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system. The product files are fetched from the configured sftp server.', Name='Start Products Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-11-16T12:07:06.446Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system. The product files are fetched from the configured sftp server.', Name='Start Products Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-11-16T12:07:33.087Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden von dem konfigurierten sftp-Server geholt.', Name='Start der Produktsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSync_import
-- 2022-11-16T12:07:43.492Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden von dem konfigurierten sftp-Server geholt.', Name='Start der Produktsynchronisation SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:07:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543300
;

-- Reference: External_Request SAP
-- Value: startProductsSyncSFTP
-- ValueName: import
-- 2022-11-16T12:07:58.682Z
UPDATE AD_Ref_List SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden von dem konfigurierten sftp-Server geholt.', Name='Start der Produktsynchronisation SFTP', Value='startProductsSyncSFTP',Updated=TO_TIMESTAMP('2022-11-16 14:07:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-11-16T12:10:41.513Z
UPDATE AD_Ref_List_Trl SET Description='Stops the business partner synchronization with SAP external system from the configured SFTP server.', Name='Stop Business Partner Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:10:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543317
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-11-16T12:10:53.347Z
UPDATE AD_Ref_List_Trl SET Description='Stops the business partner synchronization with SAP external system from the configured SFTP server.', Name='Stop Business Partner Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543317
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-11-16T12:11:56.302Z
UPDATE AD_Ref_List_Trl SET Name='Geschäftspartner-Synchronisation stoppen SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543317
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-11-16T12:12:29.228Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System vom konfigurierten SFTP-Server.',Updated=TO_TIMESTAMP('2022-11-16 14:12:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543317
;

-- Reference Item: External_Request SAP -> stopBPartnerSync_import
-- 2022-11-16T12:12:44.030Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System vom konfigurierten SFTP-Server.', Name='Geschäftspartner-Synchronisation stoppen SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543317
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSyncSFTP
-- ValueName: import
-- 2022-11-16T12:13:23.934Z
UPDATE AD_Ref_List SET Description='Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System vom konfigurierten SFTP-Server.', Name='Geschäftspartner-Synchronisation stoppen SFTP', Value='stopBPartnerSyncSFTP',Updated=TO_TIMESTAMP('2022-11-16 14:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543317
;

-- Reference: External_Request SAP
-- Value: stopProductsSyncSFTP
-- ValueName: import
-- 2022-11-16T12:14:18.442Z
UPDATE AD_Ref_List SET Description='Stoppt die Produktsynchronisation mit dem externen SAP-System vom konfigurierten SFTP-Server.', Name='Produktsynchronisation stoppen SFTP', Value='stopProductsSyncSFTP',Updated=TO_TIMESTAMP('2022-11-16 14:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductsSyncSFTP_import
-- 2022-11-16T12:14:34.286Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Produktsynchronisation mit dem externen SAP-System vom konfigurierten SFTP-Server.', Name='Produktsynchronisation stoppen SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:14:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductsSyncSFTP_import
-- 2022-11-16T12:14:39.997Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Produktsynchronisation mit dem externen SAP-System vom konfigurierten SFTP-Server.', Name='Produktsynchronisation stoppen SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductsSyncSFTP_import
-- 2022-11-16T12:14:52.872Z
UPDATE AD_Ref_List_Trl SET Description='Stops the product synchronization with SAP external system from the configured SFTP server.', Name='Stop Product Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:14:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> stopProductsSyncSFTP_import
-- 2022-11-16T12:14:58.269Z
UPDATE AD_Ref_List_Trl SET Description='Stops the product synchronization with SAP external system from the configured SFTP server.', Name='Stop Product Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543301
;

-- Reference Item: External_Request SAP -> startProductsSyncSFTP_import
-- 2022-11-16T12:15:29.644Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system. The product files are fetched from the configured SFTP server.',Updated=TO_TIMESTAMP('2022-11-16 14:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSyncSFTP_import
-- 2022-11-16T12:15:34.494Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system. The product files are fetched from the configured SFTP server.',Updated=TO_TIMESTAMP('2022-11-16 14:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSyncSFTP_import
-- 2022-11-16T12:15:44.245Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden von dem konfigurierten SFTP-Server geholt.',Updated=TO_TIMESTAMP('2022-11-16 14:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductsSyncSFTP_import
-- 2022-11-16T12:15:50.501Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden von dem konfigurierten SFTP-Server geholt.',Updated=TO_TIMESTAMP('2022-11-16 14:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543300
;

-- Reference: External_Request SAP
-- Value: startBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:16:30.909Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543344,541661,TO_TIMESTAMP('2022-11-16 14:16:30','YYYY-MM-DD HH24:MI:SS'),100,'Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von dem konfigurierten sftp-Server geholt.','U','Y','Start der Geschäftspartner-Synchronisation Local File',TO_TIMESTAMP('2022-11-16 14:16:30','YYYY-MM-DD HH24:MI:SS'),100,'startBPartnerSyncLocalFile','import')
;

-- 2022-11-16T12:16:30.914Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543344 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:17:27.560Z
UPDATE AD_Ref_List_Trl SET Name='Start Business Partners Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-16 14:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543344
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:18:40.264Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners synchronization with SAP external system. The business partner files are fetched from the local machine.',Updated=TO_TIMESTAMP('2022-11-16 14:18:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543344
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:18:48.837Z
UPDATE AD_Ref_List_Trl SET Description='Starts the business partners synchronization with SAP external system. The business partner files are fetched from the local machine.',Updated=TO_TIMESTAMP('2022-11-16 14:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543344
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:19:06.445Z
UPDATE AD_Ref_List_Trl SET Name='Start Business Partners Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-16 14:19:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543344
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:19:41.980Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von vom lokalen Rechner geholt.',Updated=TO_TIMESTAMP('2022-11-16 14:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543344
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:19:54.963Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von vom lokalen Rechner geholt.',Updated=TO_TIMESTAMP('2022-11-16 14:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543344
;

-- Reference: External_Request SAP
-- Value: startBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:20:02.829Z
UPDATE AD_Ref_List SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von vom lokalen Rechner geholt.',Updated=TO_TIMESTAMP('2022-11-16 14:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543344
;

-- Reference: External_Request SAP
-- Value: startBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:20:18.730Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-16 14:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543344
;

-- Reference: External_Request SAP
-- Value: startProductSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:20:47.391Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543345,541661,TO_TIMESTAMP('2022-11-16 14:20:47','YYYY-MM-DD HH24:MI:SS'),100,'Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden von dem konfigurierten sftp-Server geholt.','U','Y','Start der Produktsynchronisation SFTP',TO_TIMESTAMP('2022-11-16 14:20:47','YYYY-MM-DD HH24:MI:SS'),100,'startProductSyncLocalFile','import')
;

-- 2022-11-16T12:20:47.393Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543345 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: External_Request SAP
-- Value: startProductsSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:21:02.534Z
UPDATE AD_Ref_List SET Value='startProductsSyncLocalFile',Updated=TO_TIMESTAMP('2022-11-16 14:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543345
;

-- Reference: External_Request SAP
-- Value: startProductsSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:21:52.350Z
UPDATE AD_Ref_List SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden vom lokalen Rechner geholt.', Name='Start der Produktsynchronisation Local File',Updated=TO_TIMESTAMP('2022-11-16 14:21:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:22:07.869Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden vom lokalen Rechner geholt.', Name='Start der Produktsynchronisation Local File',Updated=TO_TIMESTAMP('2022-11-16 14:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:22:23.030Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden vom lokalen Rechner geholt.', Name='Start der Produktsynchronisation Local File',Updated=TO_TIMESTAMP('2022-11-16 14:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:23:33.295Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system. The product files are fetched from the local machine.',Updated=TO_TIMESTAMP('2022-11-16 14:23:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:23:45.261Z
UPDATE AD_Ref_List_Trl SET Name='Start Products Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:24:08.535Z
UPDATE AD_Ref_List_Trl SET Description='Starts the products sychronization with SAP external system. The product files are fetched from the local machine.', Name='Start Products Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543345
;

-- Reference: External_Request SAP
-- Value: startProductsSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:24:23.767Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-16 14:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543345
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:25:31.110Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543346,541661,TO_TIMESTAMP('2022-11-16 14:25:30','YYYY-MM-DD HH24:MI:SS'),100,'Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System vom lokalen Rechner.','U','Y','Geschäftspartner-Synchronisation stoppen Local File',TO_TIMESTAMP('2022-11-16 14:25:30','YYYY-MM-DD HH24:MI:SS'),100,'stopBPartnerSyncLocalFile','import')
;

-- 2022-11-16T12:25:31.113Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543346 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncLocalFile_import
-- 2022-11-16T12:26:46.333Z
UPDATE AD_Ref_List_Trl SET Description='Stops the business partner synchronization with SAP external system from the local machine.', Name='Stop Business Partner Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-16 14:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543346
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncLocalFile_import
-- 2022-11-16T12:27:01.100Z
UPDATE AD_Ref_List_Trl SET Description='Stops the business partner synchronization with SAP external system from the local machine.', Name='Stop Business Partner Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-16 14:27:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543346
;

-- Reference: External_Request SAP
-- Value: stopProductSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:28:43.443Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543347,541661,TO_TIMESTAMP('2022-11-16 14:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Stoppt die Produktsynchronisation mit dem externen SAP-System vom lokalen Rechner.','U','Y','Produktsynchronisation stoppen lokale Datei',TO_TIMESTAMP('2022-11-16 14:28:42','YYYY-MM-DD HH24:MI:SS'),100,'stopProductSyncLocalFile','import')
;

-- 2022-11-16T12:28:43.445Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543347 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:29:27.201Z
UPDATE AD_Ref_List SET Name='Geschäftspartner-Synchronisation stoppen lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543346
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncLocalFile_import
-- 2022-11-16T12:29:44.200Z
UPDATE AD_Ref_List_Trl SET Name='Geschäftspartner-Synchronisation stoppen lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:29:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543346
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncLocalFile_import
-- 2022-11-16T12:29:50.342Z
UPDATE AD_Ref_List_Trl SET Name='Geschäftspartner-Synchronisation stoppen lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543346
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncLocalFile_import
-- 2022-11-16T12:30:27.252Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System vom lokalen Rechner aus.',Updated=TO_TIMESTAMP('2022-11-16 14:30:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543346
;

-- Reference Item: External_Request SAP -> stopBPartnerSyncLocalFile_import
-- 2022-11-16T12:30:31.929Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System vom lokalen Rechner aus.',Updated=TO_TIMESTAMP('2022-11-16 14:30:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543346
;

-- Reference: External_Request SAP
-- Value: startProductsSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:31:01.916Z
UPDATE AD_Ref_List SET Name='Start der Produktsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:31:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:31:15.626Z
UPDATE AD_Ref_List_Trl SET Name='Start der Produktsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:31:24.465Z
UPDATE AD_Ref_List_Trl SET Name='Start der Produktsynchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:32:09.891Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden vom lokalen Rechner abgerufen.',Updated=TO_TIMESTAMP('2022-11-16 14:32:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-16T12:32:14.072Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden vom lokalen Rechner abgerufen.',Updated=TO_TIMESTAMP('2022-11-16 14:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543345
;

-- Reference: External_Request SAP
-- Value: startBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:32:39.846Z
UPDATE AD_Ref_List SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von vom lokalen Rechner abgerufen.', Name='Start der Geschäftspartner-Synchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:32:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543344
;

-- Reference: External_Request SAP
-- Value: startProductsSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:32:49.604Z
UPDATE AD_Ref_List SET Description='Startet die Produktsynchronisation mit dem externen SAP-System. Die Produktdateien werden vom lokalen Rechner abgerufen.',Updated=TO_TIMESTAMP('2022-11-16 14:32:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:33:16.215Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von vom lokalen Rechner abgerufen.', Name='Start der Geschäftspartner-Synchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:33:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543344
;

-- Reference Item: External_Request SAP -> startBPartnerSyncLocalFile_import
-- 2022-11-16T12:33:27.092Z
UPDATE AD_Ref_List_Trl SET Description='Startet die Geschäftspartner-Synchronisation mit dem SAP-Außensystem. Die Geschäftspartnerdateien werden von vom lokalen Rechner abgerufen.', Name='Start der Geschäftspartner-Synchronisation lokale Datei',Updated=TO_TIMESTAMP('2022-11-16 14:33:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543344
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:34:01.104Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-16 14:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543346
;

-- Reference: External_Request SAP
-- Value: stopBPartnerSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:34:25.148Z
UPDATE AD_Ref_List SET Description='Stoppt die Geschäftspartner-Synchronisation mit dem externen SAP-System vom lokalen Rechner aus.',Updated=TO_TIMESTAMP('2022-11-16 14:34:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543346
;

-- Reference: External_Request SAP
-- Value: stopProductSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:34:48.527Z
UPDATE AD_Ref_List SET Description='Stoppt die Produktsynchronisation mit dem externen SAP-System vom lokalen Rechner aus.',Updated=TO_TIMESTAMP('2022-11-16 14:34:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543347
;

-- Reference: External_Request SAP
-- Value: stopProductSyncLocalFile
-- ValueName: import
-- 2022-11-16T12:34:55.609Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-11-16 14:34:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543347
;

-- Reference Item: External_Request SAP -> stopProductSyncLocalFile_import
-- 2022-11-16T12:36:16.131Z
UPDATE AD_Ref_List_Trl SET Description='Stop Product Synchronization SFTP', Name='Stop Product Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-16 14:36:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543347
;

-- Reference Item: External_Request SAP -> stopProductSyncLocalFile_import
-- 2022-11-16T12:36:36.087Z
UPDATE AD_Ref_List_Trl SET Description='Stops the product synchronization with SAP external system from the local machine.',Updated=TO_TIMESTAMP('2022-11-16 14:36:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543347
;

-- Reference Item: External_Request SAP -> stopProductSyncLocalFile_import
-- 2022-11-16T12:36:53.068Z
UPDATE AD_Ref_List_Trl SET Description='Stops the product synchronization with SAP external system from the local machine.', Name='Stop Product Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-16 14:36:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543347
;

-- Reference Item: External_Request SAP -> stopProductSyncLocalFile_import
-- 2022-11-16T12:37:01.968Z
UPDATE AD_Ref_List_Trl SET Name='Stop Product Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-16 14:37:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543347
;

-- Reference Item: External_Request SAP -> stopProductSyncLocalFile_import
-- 2022-11-16T12:37:11.159Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Produktsynchronisation mit dem externen SAP-System vom lokalen Rechner aus.',Updated=TO_TIMESTAMP('2022-11-16 14:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543347
;

-- Reference Item: External_Request SAP -> stopProductSyncLocalFile_import
-- 2022-11-16T12:37:17.156Z
UPDATE AD_Ref_List_Trl SET Description='Stoppt die Produktsynchronisation mit dem externen SAP-System vom lokalen Rechner aus.',Updated=TO_TIMESTAMP('2022-11-16 14:37:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543347
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_TargetDirectory
-- 2022-11-16T15:08:58.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585043,708063,0,546672,0,TO_TIMESTAMP('2022-11-16 17:08:58','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','SFTP-Geschäftspartner-Zielverzeichnis',0,130,0,1,1,TO_TIMESTAMP('2022-11-16 17:08:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:08:58.528Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:08:58.565Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581604)
;

-- 2022-11-16T15:08:58.585Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708063
;

-- 2022-11-16T15:08:58.592Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708063)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Product_FileName_Pattern
-- 2022-11-16T15:11:32.663Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585044,708089,0,546672,0,TO_TIMESTAMP('2022-11-16 17:11:32','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','SFTP-Produkt Dateinamen-Muster',0,140,0,1,1,TO_TIMESTAMP('2022-11-16 17:11:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:11:32.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:11:32.683Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581667)
;

-- 2022-11-16T15:11:32.692Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708089
;

-- 2022-11-16T15:11:32.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708089)
;

-- Field: Externes System SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_FileName_Pattern
-- 2022-11-16T15:12:37.243Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585042,708114,0,546672,0,TO_TIMESTAMP('2022-11-16 17:12:37','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','SFTP-Geschäftspartner Dateinamen-Muster',0,150,0,1,1,TO_TIMESTAMP('2022-11-16 17:12:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T15:12:37.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-16T15:12:37.248Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581668)
;

-- 2022-11-16T15:12:37.254Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708114
;

-- 2022-11-16T15:12:37.255Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708114)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_TargetDirectory
-- 2022-11-16T15:13:43.951Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708063,0,546672,613471,550021,'F',TO_TIMESTAMP('2022-11-16 17:13:43','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)','Y','N','N','Y','N','N','N',0,'SFTP-Geschäftspartner-Zielverzeichnis',90,0,0,TO_TIMESTAMP('2022-11-16 17:13:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Product_TargetDirectory
-- 2022-11-16T15:14:28.860Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708044,0,546672,613472,550021,'F',TO_TIMESTAMP('2022-11-16 17:14:28','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.','Y','N','N','Y','N','N','N',0,'SFTP-Produkt-Zielverzeichnis',100,0,0,TO_TIMESTAMP('2022-11-16 17:14:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_BPartner_FileName_Pattern
-- 2022-11-16T15:15:05.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708114,0,546672,613473,550021,'F',TO_TIMESTAMP('2022-11-16 17:15:05','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','Y','N','N','Y','N','N','N',0,'SFTP-Geschäftspartner Dateinamen-Muster',110,0,0,TO_TIMESTAMP('2022-11-16 17:15:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externes System SAP(541631,de.metas.externalsystem) -> SFTP(546672,de.metas.externalsystem) -> main -> 10 -> main.SFTP-Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_SFTP.SFTP_Product_FileName_Pattern
-- 2022-11-16T15:15:20.546Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708089,0,546672,613474,550021,'F',TO_TIMESTAMP('2022-11-16 17:15:20','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','Y','N','N','Y','N','N','N',0,'SFTP-Produkt Dateinamen-Muster',120,0,0,TO_TIMESTAMP('2022-11-16 17:15:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: null
-- 2022-11-16T17:46:11.177Z
UPDATE AD_Element_Trl SET Name='External system config SAP', PrintName='External system config SAP',Updated=TO_TIMESTAMP('2022-11-16 19:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581672 AND AD_Language='nl_NL'
;

-- 2022-11-16T17:46:11.199Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581672,'nl_NL')
;

-- Element: null
-- 2022-11-16T17:46:15.195Z
UPDATE AD_Element_Trl SET Name='External system config SAP', PrintName='External system config SAP',Updated=TO_TIMESTAMP('2022-11-16 19:46:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581672 AND AD_Language='en_US'
;

-- 2022-11-16T17:46:15.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581672,'en_US')
;

-- Element: null
-- 2022-11-16T17:46:19.803Z
UPDATE AD_Element_Trl SET Name='External system config SAP', PrintName='External system config SAP',Updated=TO_TIMESTAMP('2022-11-16 19:46:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581672 AND AD_Language='de_DE'
;

-- 2022-11-16T17:46:19.805Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581672,'de_DE')
;

-- 2022-11-16T17:46:19.808Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581672,'de_DE')
;

-- Element: null
-- 2022-11-16T17:46:24.402Z
UPDATE AD_Element_Trl SET Name='External system config SAP', PrintName='External system config SAP',Updated=TO_TIMESTAMP('2022-11-16 19:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581672 AND AD_Language='de_CH'
;

-- 2022-11-16T17:46:24.405Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581672,'de_CH')
;

-- Table: ExternalSystem_Config_SAP
-- 2022-11-16T17:54:49.577Z
UPDATE AD_Table SET Name='ExternalSystem Config SAP',Updated=TO_TIMESTAMP('2022-11-16 19:54:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542238
;

-- Table: ExternalSystem_Config_SAP
-- 2022-11-16T17:55:24.232Z
UPDATE AD_Table SET AD_Window_ID=541631,Updated=TO_TIMESTAMP('2022-11-16 19:55:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542238
;

-- Name: External system config SAP
-- Action Type: null
-- 2022-11-16T17:56:41.878Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,581672,542022,0,TO_TIMESTAMP('2022-11-16 19:56:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','541631','Y','N','N','N','N','External system config SAP',TO_TIMESTAMP('2022-11-16 19:56:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-16T17:56:41.882Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542022 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-11-16T17:56:41.887Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542022, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542022)
;

-- 2022-11-16T17:56:41.895Z
/* DDL */  select update_menu_translation_from_ad_element(581672)
;

-- Reordering children of `System`
-- Node name: `API Audit`
-- 2022-11-16T17:56:42.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2022-11-16T17:56:42.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2022-11-16T17:56:42.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2022-11-16T17:56:42.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2022-11-16T17:56:42.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2022-11-16T17:56:42.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2022-11-16T17:56:42.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2022-11-16T17:56:42.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2022-11-16T17:56:42.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2022-11-16T17:56:42.503Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2022-11-16T17:56:42.504Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2022-11-16T17:56:42.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2022-11-16T17:56:42.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2022-11-16T17:56:42.506Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2022-11-16T17:56:42.507Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2022-11-16T17:56:42.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2022-11-16T17:56:42.508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2022-11-16T17:56:42.509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2022-11-16T17:56:42.509Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2022-11-16T17:56:42.510Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2022-11-16T17:56:42.511Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2022-11-16T17:56:42.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2022-11-16T17:56:42.512Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2022-11-16T17:56:42.513Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2022-11-16T17:56:42.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2022-11-16T17:56:42.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2022-11-16T17:56:42.515Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2022-11-16T17:56:42.516Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2022-11-16T17:56:42.516Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2022-11-16T17:56:42.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Config (AD_Zebra_Config)`
-- 2022-11-16T17:56:42.517Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2022-11-16T17:56:42.518Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2022-11-16T17:56:42.519Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2022-11-16T17:56:42.520Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2022-11-16T17:56:42.521Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2022-11-16T17:56:42.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2022-11-16T17:56:42.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2022-11-16T17:56:42.523Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2022-11-16T17:56:42.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV_Missing_Counter_Documents (RV_Missing_Counter_Documents)`
-- 2022-11-16T17:56:42.524Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2022-11-16T17:56:42.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2022-11-16T17:56:42.525Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2022-11-16T17:56:42.526Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2022-11-16T17:56:42.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2022-11-16T17:56:42.527Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2022-11-16T17:56:42.528Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2022-11-16T17:56:42.529Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2022-11-16T17:56:42.529Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2022-11-16T17:56:42.530Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2022-11-16T17:56:42.530Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2022-11-16T17:56:42.531Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2022-11-16T17:56:42.532Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2022-11-16T17:56:42.532Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2022-11-16T17:56:42.533Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2022-11-16T17:56:42.533Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2022-11-16T17:56:42.534Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2022-11-16T17:56:42.535Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2022-11-16T17:56:42.535Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2022-11-16T17:56:42.536Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2022-11-16T17:56:42.536Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2022-11-16T17:56:42.537Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2022-11-16T17:56:42.538Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2022-11-16T17:56:42.538Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-16T17:56:42.539Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-16T17:56:42.539Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-16T17:56:42.540Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2022-11-16T17:56:42.540Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2022-11-16T17:56:42.541Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2022-11-16T17:56:42.541Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2022-11-16T17:56:42.542Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2022-11-16T17:56:42.543Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2022-11-16T17:56:42.543Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Change System Base Language (de.metas.process.ExecuteUpdateSQL)`
-- 2022-11-16T17:56:42.544Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541973 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Config (GeocodingConfig)`
-- 2022-11-16T17:56:42.544Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2022-11-16T17:56:42.545Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2022-11-16T17:56:42.546Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2022-11-16T17:56:42.547Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2022-11-16T17:56:42.547Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2022-11-16T17:56:42.548Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `External system config SAP`
-- 2022-11-16T17:56:42.548Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542022 AND AD_Tree_ID=10
;

-- Name: External system config SAP
-- Action Type: W
-- Window: External system config SAP(541631,de.metas.externalsystem)
-- 2022-11-16T17:57:54.991Z
UPDATE AD_Menu SET Action='W', AD_Window_ID=541631,Updated=TO_TIMESTAMP('2022-11-16 19:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542022
;

-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_LocalFile_ID
-- 2022-11-16T18:20:26.668Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584980
;

-- 2022-11-16T18:20:26.674Z
DELETE FROM AD_Column WHERE AD_Column_ID=584980
;

-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_SFTP_ID
-- 2022-11-16T18:20:51.320Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584979
;

-- 2022-11-16T18:20:51.326Z
DELETE FROM AD_Column WHERE AD_Column_ID=584979
;

-- Column: ExternalSystem_Config_SAP_LocalFile.ExternalSystem_Config_SAP_ID
-- 2022-11-16T18:30:02.827Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585051,581532,0,19,542258,'ExternalSystem_Config_SAP_ID',TO_TIMESTAMP('2022-11-16 20:30:02','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'ExternalSystem_Config_SAP',0,0,TO_TIMESTAMP('2022-11-16 20:30:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T18:30:02.834Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585051 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T18:30:02.869Z
/* DDL */  select update_Column_Translation_From_AD_Element(581532)
;

-- Column: ExternalSystem_Config_SAP_SFTP.ExternalSystem_Config_SAP_ID
-- 2022-11-16T18:30:40.662Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585052,581532,0,19,542257,'ExternalSystem_Config_SAP_ID',TO_TIMESTAMP('2022-11-16 20:30:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'ExternalSystem_Config_SAP',0,0,TO_TIMESTAMP('2022-11-16 20:30:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-16T18:30:40.666Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585052 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-16T18:30:40.672Z
/* DDL */  select update_Column_Translation_From_AD_Element(581532)
;

-- 2022-11-17T11:34:08.191Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN ExternalSystem_Config_SAP_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-17T11:34:08.234Z
ALTER TABLE ExternalSystem_Config_SAP_LocalFile ADD CONSTRAINT ExternalSystemConfigSAP_ExternalSystemConfigSAPLocalFile FOREIGN KEY (ExternalSystem_Config_SAP_ID) REFERENCES public.ExternalSystem_Config_SAP DEFERRABLE INITIALLY DEFERRED
;

-- 2022-11-17T11:34:39.237Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_SFTP','ALTER TABLE public.ExternalSystem_Config_SAP_SFTP ADD COLUMN ExternalSystem_Config_SAP_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-17T11:34:39.266Z
ALTER TABLE ExternalSystem_Config_SAP_SFTP ADD CONSTRAINT ExternalSystemConfigSAP_ExternalSystemConfigSAPSFTP FOREIGN KEY (ExternalSystem_Config_SAP_ID) REFERENCES public.ExternalSystem_Config_SAP DEFERRABLE INITIALLY DEFERRED
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-17T11:36:41.188Z
UPDATE AD_Element_Trl SET Name='Geschäftspartner Dateinamen-Muster', PrintName='Geschäftspartner Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-17 13:36:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='de_CH'
;

-- 2022-11-17T11:36:41.192Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'de_CH')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-17T11:36:47.490Z
UPDATE AD_Element_Trl SET Name='Geschäftspartner Dateinamen-Muster', PrintName='Geschäftspartner Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-17 13:36:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='de_DE'
;

-- 2022-11-17T11:36:47.494Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581668,'de_DE')
;

-- 2022-11-17T11:36:47.507Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'de_DE')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-17T11:36:52.020Z
UPDATE AD_Element_Trl SET Name='Business Partner File Name Pattern', PrintName='Business Partner File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 13:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='en_US'
;

-- 2022-11-17T11:36:52.024Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'en_US')
;

-- Element: SFTP_BPartner_FileName_Pattern
-- 2022-11-17T11:36:59.808Z
UPDATE AD_Element_Trl SET Name='Business Partner File Name Pattern', PrintName='Business Partner File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 13:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581668 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:36:59.811Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581668,'nl_NL')
;

-- 2022-11-17T11:37:37.503Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_BPartner_FileName_Pattern','VARCHAR(255)',null,null)
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-17T11:38:05.192Z
UPDATE AD_Element_Trl SET Name='Geschäftspartner-Zielverzeichnis', PrintName='Geschäftspartner-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 13:38:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_CH'
;

-- 2022-11-17T11:38:05.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_CH')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-17T11:38:09.705Z
UPDATE AD_Element_Trl SET Name='Geschäftspartner-Zielverzeichnis', PrintName='Geschäftspartner-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 13:38:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='de_DE'
;

-- 2022-11-17T11:38:09.708Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581604,'de_DE')
;

-- 2022-11-17T11:38:09.710Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'de_DE')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-17T11:38:28.271Z
UPDATE AD_Element_Trl SET Name='Business Partner Target Directory', PrintName='Business Partner Target Directory',Updated=TO_TIMESTAMP('2022-11-17 13:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='en_US'
;

-- 2022-11-17T11:38:28.275Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'en_US')
;

-- Element: SFTP_BPartner_TargetDirectory
-- 2022-11-17T11:38:34.951Z
UPDATE AD_Element_Trl SET Name='Business Partner Target Directory', PrintName='Business Partner Target Directory',Updated=TO_TIMESTAMP('2022-11-17 13:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581604 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:38:34.954Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581604,'nl_NL')
;

-- 2022-11-17T11:38:44.091Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_BPartner_TargetDirectory','VARCHAR(255)',null,null)
;

-- Element: SFTP_HostName
-- 2022-11-17T11:39:45.436Z
UPDATE AD_Element_Trl SET Name='Hostname', PrintName='Hostname',Updated=TO_TIMESTAMP('2022-11-17 13:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='de_CH'
;

-- 2022-11-17T11:39:45.440Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'de_CH')
;

-- Element: SFTP_HostName
-- 2022-11-17T11:39:50.021Z
UPDATE AD_Element_Trl SET Name='Hostname', PrintName='Hostname',Updated=TO_TIMESTAMP('2022-11-17 13:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='de_DE'
;

-- 2022-11-17T11:39:50.024Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581553,'de_DE')
;

-- 2022-11-17T11:39:50.035Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'de_DE')
;

-- Element: SFTP_HostName
-- 2022-11-17T11:39:54.924Z
UPDATE AD_Element_Trl SET Name='Hostname', PrintName='Hostname',Updated=TO_TIMESTAMP('2022-11-17 13:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='en_US'
;

-- 2022-11-17T11:39:54.929Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'en_US')
;

-- Element: SFTP_HostName
-- 2022-11-17T11:39:59.875Z
UPDATE AD_Element_Trl SET Name='Hostname', PrintName='Hostname',Updated=TO_TIMESTAMP('2022-11-17 13:39:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581553 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:39:59.879Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581553,'nl_NL')
;

-- 2022-11-17T11:40:05.504Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_HostName','VARCHAR(255)',null,'SFTP-Hostname')
;

-- 2022-11-17T11:40:05.513Z
UPDATE ExternalSystem_Config_SAP_SFTP SET SFTP_HostName='SFTP-Hostname' WHERE SFTP_HostName IS NULL
;

-- Element: SFTP_Password
-- 2022-11-17T11:40:33.776Z
UPDATE AD_Element_Trl SET Name='Passwort', PrintName='Passwort',Updated=TO_TIMESTAMP('2022-11-17 13:40:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='de_CH'
;

-- 2022-11-17T11:40:33.779Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'de_CH')
;

-- Element: SFTP_Password
-- 2022-11-17T11:40:38.857Z
UPDATE AD_Element_Trl SET Name='Passwort', PrintName='Passwort',Updated=TO_TIMESTAMP('2022-11-17 13:40:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='de_DE'
;

-- 2022-11-17T11:40:38.860Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581554,'de_DE')
;

-- 2022-11-17T11:40:38.869Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'de_DE')
;

-- Element: SFTP_Password
-- 2022-11-17T11:40:43.977Z
UPDATE AD_Element_Trl SET Name='Password', PrintName='Password',Updated=TO_TIMESTAMP('2022-11-17 13:40:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='en_US'
;

-- 2022-11-17T11:40:43.980Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'en_US')
;

-- Element: SFTP_Password
-- 2022-11-17T11:40:50.218Z
UPDATE AD_Element_Trl SET Name='Password', PrintName='Password',Updated=TO_TIMESTAMP('2022-11-17 13:40:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581554 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:40:50.222Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581554,'nl_NL')
;

-- Element: SFTP_Port
-- 2022-11-17T11:42:36.692Z
UPDATE AD_Element_Trl SET Name='Port', PrintName='Port',Updated=TO_TIMESTAMP('2022-11-17 13:42:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='de_CH'
;

-- 2022-11-17T11:42:36.695Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'de_CH')
;

-- Element: SFTP_Port
-- 2022-11-17T11:42:41.900Z
UPDATE AD_Element_Trl SET Name='Port', PrintName='Port',Updated=TO_TIMESTAMP('2022-11-17 13:42:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='de_DE'
;

-- 2022-11-17T11:42:41.903Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581555,'de_DE')
;

-- 2022-11-17T11:42:41.906Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'de_DE')
;

-- Element: SFTP_Port
-- 2022-11-17T11:42:46.445Z
UPDATE AD_Element_Trl SET Name='Port', PrintName='Port',Updated=TO_TIMESTAMP('2022-11-17 13:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='en_US'
;

-- 2022-11-17T11:42:46.448Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'en_US')
;

-- Element: SFTP_Port
-- 2022-11-17T11:42:50.829Z
UPDATE AD_Element_Trl SET Name='Port', PrintName='Port',Updated=TO_TIMESTAMP('2022-11-17 13:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581555 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:42:50.833Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581555,'nl_NL')
;

-- 2022-11-17T11:43:01.363Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_Port','VARCHAR(14)',null,'SFTP-Port')
;

-- 2022-11-17T11:43:01.376Z
UPDATE ExternalSystem_Config_SAP_SFTP SET SFTP_Port='SFTP-Port' WHERE SFTP_Port IS NULL
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-17T11:43:23.573Z
UPDATE AD_Element_Trl SET Name='Produkt Dateinamen-Muster', PrintName='Produkt Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-17 13:43:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_CH'
;

-- 2022-11-17T11:43:23.576Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_CH')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-17T11:43:27.780Z
UPDATE AD_Element_Trl SET Name='Produkt Dateinamen-Muster', PrintName='Produkt Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-17 13:43:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='de_DE'
;

-- 2022-11-17T11:43:27.783Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581667,'de_DE')
;

-- 2022-11-17T11:43:27.792Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'de_DE')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-17T11:43:34.271Z
UPDATE AD_Element_Trl SET Name='Product File Name Pattern', PrintName='Product File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 13:43:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='en_US'
;

-- 2022-11-17T11:43:34.273Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'en_US')
;

-- Element: SFTP_Product_FileName_Pattern
-- 2022-11-17T11:43:40.676Z
UPDATE AD_Element_Trl SET Name='Product File Name Pattern', PrintName='Product File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 13:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581667 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:43:40.679Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581667,'nl_NL')
;

-- 2022-11-17T11:43:56.360Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_Product_FileName_Pattern','VARCHAR(255)',null,null)
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-17T11:44:17.009Z
UPDATE AD_Element_Trl SET Name='Produkt-Zielverzeichnis', PrintName='Produkt-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 13:44:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_CH'
;

-- 2022-11-17T11:44:17.013Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_CH')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-17T11:44:22.681Z
UPDATE AD_Element_Trl SET Name='Produkt-Zielverzeichnis', PrintName='Produkt-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 13:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_DE'
;

-- 2022-11-17T11:44:22.684Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581603,'de_DE')
;

-- 2022-11-17T11:44:22.686Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_DE')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-17T11:44:27.633Z
UPDATE AD_Element_Trl SET Name='Product Target Directory', PrintName='Product Target Directory',Updated=TO_TIMESTAMP('2022-11-17 13:44:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='en_US'
;

-- 2022-11-17T11:44:27.636Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'en_US')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-17T11:44:32.664Z
UPDATE AD_Element_Trl SET Name='Product Target Directory', PrintName='Product Target Directory',Updated=TO_TIMESTAMP('2022-11-17 13:44:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:44:32.666Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'nl_NL')
;

-- 2022-11-17T11:44:43.765Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_Product_TargetDirectory','VARCHAR(255)',null,null)
;

-- Element: SFTP_Username
-- 2022-11-17T11:45:00.823Z
UPDATE AD_Element_Trl SET Name='Benutzername', PrintName='Benutzername',Updated=TO_TIMESTAMP('2022-11-17 13:45:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='de_CH'
;

-- 2022-11-17T11:45:00.827Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'de_CH')
;

-- Element: SFTP_Username
-- 2022-11-17T11:45:04.818Z
UPDATE AD_Element_Trl SET Name='Benutzername', PrintName='Benutzername',Updated=TO_TIMESTAMP('2022-11-17 13:45:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='de_DE'
;

-- 2022-11-17T11:45:04.821Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581556,'de_DE')
;

-- 2022-11-17T11:45:04.830Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'de_DE')
;

-- Element: SFTP_Username
-- 2022-11-17T11:45:10.569Z
UPDATE AD_Element_Trl SET Name='Username', PrintName='Username',Updated=TO_TIMESTAMP('2022-11-17 13:45:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='en_US'
;

-- 2022-11-17T11:45:10.573Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'en_US')
;

-- Element: SFTP_Username
-- 2022-11-17T11:45:15.678Z
UPDATE AD_Element_Trl SET Name='Username', PrintName='Username',Updated=TO_TIMESTAMP('2022-11-17 13:45:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581556 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:45:15.681Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581556,'nl_NL')
;

-- 2022-11-17T11:45:30.434Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_Username','VARCHAR(255)',null,'SFTP-Benutzername')
;

-- 2022-11-17T11:45:30.442Z
UPDATE ExternalSystem_Config_SAP_SFTP SET SFTP_Username='SFTP-Benutzername' WHERE SFTP_Username IS NULL
;

-- Column: ExternalSystem_Config_SAP_LocalFile.PollingFrequencyInMs
-- 2022-11-17T11:47:16.797Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585065,581586,0,11,542258,'PollingFrequencyInMs',TO_TIMESTAMP('2022-11-17 13:47:16','YYYY-MM-DD HH24:MI:SS'),100,'N','1000','Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','de.metas.externalsystem',0,14,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Abfragefrequenz in Millisekunden',0,0,TO_TIMESTAMP('2022-11-17 13:47:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:16.804Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585065 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:16.811Z
/* DDL */  select update_Column_Translation_From_AD_Element(581586)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_BPartner_FileName_Pattern
-- 2022-11-17T11:47:17.763Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585066,581668,0,10,542258,'SFTP_BPartner_FileName_Pattern',TO_TIMESTAMP('2022-11-17 13:47:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Geschäftspartner Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-17 13:47:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:17.767Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585066 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:17.773Z
/* DDL */  select update_Column_Translation_From_AD_Element(581668)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_BPartner_TargetDirectory
-- 2022-11-17T11:47:18.761Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585067,581604,0,10,542258,'SFTP_BPartner_TargetDirectory',TO_TIMESTAMP('2022-11-17 13:47:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Geschäftspartner-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-17 13:47:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:18.763Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585067 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:18.768Z
/* DDL */  select update_Column_Translation_From_AD_Element(581604)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_HostName
-- 2022-11-17T11:47:19.653Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585068,581553,0,10,542258,'SFTP_HostName',TO_TIMESTAMP('2022-11-17 13:47:19','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Hostname','SFTP-Server-Hostname','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Hostname',0,0,TO_TIMESTAMP('2022-11-17 13:47:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:19.656Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585068 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:19.661Z
/* DDL */  select update_Column_Translation_From_AD_Element(581553)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Password
-- 2022-11-17T11:47:20.561Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585069,581554,0,10,542258,'SFTP_Password',TO_TIMESTAMP('2022-11-17 13:47:20','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Passwort','Das Passwort, das für die Authentifizierung des sftp-Servers verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Passwort',0,0,TO_TIMESTAMP('2022-11-17 13:47:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:20.564Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585069 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:20.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(581554)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Port
-- 2022-11-17T11:47:21.465Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585070,581555,0,10,542258,'SFTP_Port',TO_TIMESTAMP('2022-11-17 13:47:21','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Port','SFTP-Port','de.metas.externalsystem',0,14,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Port',0,0,TO_TIMESTAMP('2022-11-17 13:47:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:21.468Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585070 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:21.472Z
/* DDL */  select update_Column_Translation_From_AD_Element(581555)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Product_FileName_Pattern
-- 2022-11-17T11:47:22.414Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585071,581667,0,10,542258,'SFTP_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-17 13:47:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Produktdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Produkt Dateinamen-Muster',0,0,TO_TIMESTAMP('2022-11-17 13:47:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:22.417Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585071 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:22.423Z
/* DDL */  select update_Column_Translation_From_AD_Element(581667)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Product_TargetDirectory
-- 2022-11-17T11:47:23.443Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585072,581603,0,10,542258,'SFTP_Product_TargetDirectory',TO_TIMESTAMP('2022-11-17 13:47:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Produkt-Zielverzeichnis',0,0,TO_TIMESTAMP('2022-11-17 13:47:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:23.445Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585072 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:23.449Z
/* DDL */  select update_Column_Translation_From_AD_Element(581603)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Username
-- 2022-11-17T11:47:24.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585073,581556,0,10,542258,'SFTP_Username',TO_TIMESTAMP('2022-11-17 13:47:24','YYYY-MM-DD HH24:MI:SS'),100,'N','SFTP-Benutzername','Benutzername, der für die Authentifizierung am sftp-Server verwendet wird.','de.metas.externalsystem',0,255,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Benutzername',0,0,TO_TIMESTAMP('2022-11-17 13:47:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T11:47:24.404Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585073 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T11:47:24.408Z
/* DDL */  select update_Column_Translation_From_AD_Element(581556)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Username
-- 2022-11-17T11:48:07.518Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585073
;

-- 2022-11-17T11:48:07.536Z
DELETE FROM AD_Column WHERE AD_Column_ID=585073
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Port
-- 2022-11-17T11:48:31.325Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585070
;

-- 2022-11-17T11:48:31.331Z
DELETE FROM AD_Column WHERE AD_Column_ID=585070
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_Password
-- 2022-11-17T11:48:40.594Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585069
;

-- 2022-11-17T11:48:40.616Z
DELETE FROM AD_Column WHERE AD_Column_ID=585069
;

-- Column: ExternalSystem_Config_SAP_LocalFile.SFTP_HostName
-- 2022-11-17T11:48:49.846Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585068
;

-- 2022-11-17T11:48:49.852Z
DELETE FROM AD_Column WHERE AD_Column_ID=585068
;

-- Element: ErroredDirectory
-- 2022-11-17T11:54:40.870Z
UPDATE AD_Element_Trl SET Description='Defines where files should be moved after being processed with error. (The path should be relative to the current target location)',Updated=TO_TIMESTAMP('2022-11-17 13:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='en_US'
;

-- 2022-11-17T11:54:40.873Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'en_US')
;

-- Element: ErroredDirectory
-- 2022-11-17T11:55:25.541Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. ( Der Pfad sollte relativ zum aktuellen Zielort sein )',Updated=TO_TIMESTAMP('2022-11-17 13:55:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:55:25.543Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'nl_NL')
;

-- Element: ErroredDirectory
-- 2022-11-17T11:55:34.471Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. ( Der Pfad sollte relativ zum aktuellen Zielort sein )',Updated=TO_TIMESTAMP('2022-11-17 13:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_DE'
;

-- 2022-11-17T11:55:34.474Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581584,'de_DE')
;

-- 2022-11-17T11:55:34.475Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_DE')
;

-- Element: ErroredDirectory
-- 2022-11-17T11:55:42.375Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien verschoben werden sollen, nachdem der Versuch, sie zu verarbeiten, fehlgeschlagen ist. ( Der Pfad sollte relativ zum aktuellen Zielort sein )',Updated=TO_TIMESTAMP('2022-11-17 13:55:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_CH'
;

-- 2022-11-17T11:55:42.378Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_CH')
;

-- 2022-11-17T11:56:04.405Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','ErroredDirectory','VARCHAR(255)',null,'error')
;

-- 2022-11-17T11:56:04.409Z
UPDATE ExternalSystem_Config_SAP_LocalFile SET ErroredDirectory='error' WHERE ErroredDirectory IS NULL
;

-- Element: ProcessedDirectory
-- 2022-11-17T11:56:24.118Z
UPDATE AD_Element_Trl SET Description='Defines where files should be moved after being successfully processed. (The path should be relative to the current target location)',Updated=TO_TIMESTAMP('2022-11-17 13:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='en_US'
;

-- 2022-11-17T11:56:24.120Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'en_US')
;

-- Element: ProcessedDirectory
-- 2022-11-17T11:56:29.287Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen Zielort sein)',Updated=TO_TIMESTAMP('2022-11-17 13:56:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='nl_NL'
;

-- 2022-11-17T11:56:29.289Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'nl_NL')
;

-- Element: ProcessedDirectory
-- 2022-11-17T11:56:34.199Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen Zielort sein)',Updated=TO_TIMESTAMP('2022-11-17 13:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_DE'
;

-- 2022-11-17T11:56:34.201Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581583,'de_DE')
;

-- 2022-11-17T11:56:34.202Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_DE')
;

-- Element: ProcessedDirectory
-- 2022-11-17T11:56:38.094Z
UPDATE AD_Element_Trl SET Description='Legt fest, wohin die Dateien nach erfolgreicher Verarbeitung verschoben werden sollen. (Der Pfad sollte relativ zum aktuellen Zielort sein)',Updated=TO_TIMESTAMP('2022-11-17 13:56:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_CH'
;

-- 2022-11-17T11:56:38.097Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_CH')
;

-- 2022-11-17T11:57:06.160Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','ProcessedDirectory','VARCHAR(255)',null,'move')
;

-- 2022-11-17T11:57:06.166Z
UPDATE ExternalSystem_Config_SAP_LocalFile SET ProcessedDirectory='move' WHERE ProcessedDirectory IS NULL
;

-- 2022-11-17T12:00:30.658Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581701,0,'LocalFile_BPartner_FileName_Pattern',TO_TIMESTAMP('2022-11-17 14:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem SFTP-Server verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','U','Y','Geschäftspartner Dateinamen-Muster','Geschäftspartner Dateinamen-Muster',TO_TIMESTAMP('2022-11-17 14:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:00:30.665Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581701 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-17T12:00:40.178Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-17 14:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581701
;

-- 2022-11-17T12:00:40.182Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581701,'de_DE')
;

-- Element: LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:03:14.866Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify business partner files on the local machine. (If not set, all files are considered)', Name='Business Partner File Name Pattern', PrintName='Business Partner File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 14:03:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581701 AND AD_Language='en_US'
;

-- 2022-11-17T12:03:14.869Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581701,'en_US')
;

-- Element: LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:03:34.246Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-17 14:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581701 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:03:34.249Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581701,'nl_NL')
;

-- Element: LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:03:44.479Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-17 14:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581701 AND AD_Language='de_DE'
;

-- 2022-11-17T12:03:44.480Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581701,'de_DE')
;

-- 2022-11-17T12:03:44.482Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581701,'de_DE')
;

-- Element: LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:03:48.635Z
UPDATE AD_Element_Trl SET Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',Updated=TO_TIMESTAMP('2022-11-17 14:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581701 AND AD_Language='de_CH'
;

-- 2022-11-17T12:03:48.637Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581701,'de_CH')
;

-- Element: LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:04:02.754Z
UPDATE AD_Element_Trl SET Name='Business Partner File Name Pattern', PrintName='Business Partner File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 14:04:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581701 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:04:02.757Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581701,'nl_NL')
;

-- Element: LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:04:06.330Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-17 14:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581701 AND AD_Language='en_US'
;

-- 2022-11-17T12:04:06.333Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581701,'en_US')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:04:41.541Z
UPDATE AD_Column SET AD_Element_ID=581701, ColumnName='LocalFile_BPartner_FileName_Pattern', Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)', Help=NULL, Name='Geschäftspartner Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-17 14:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585066
;

-- 2022-11-17T12:04:41.543Z
UPDATE AD_Field SET Name='Geschäftspartner Dateinamen-Muster', Description='Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)', Help=NULL WHERE AD_Column_ID=585066
;

-- 2022-11-17T12:04:41.544Z
/* DDL */  select update_Column_Translation_From_AD_Element(581701)
;

-- 2022-11-17T12:05:07.956Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581702,0,'LocalFile_BPartner_TargetDirectory',TO_TIMESTAMP('2022-11-17 14:05:07','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)','U','Y','Geschäftspartner-Zielverzeichnis','Geschäftspartner-Zielverzeichnis',TO_TIMESTAMP('2022-11-17 14:05:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:05:07.958Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581702 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-17T12:05:25.923Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-17 14:05:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702
;

-- 2022-11-17T12:05:25.926Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'de_DE')
;

-- 2022-11-17T12:12:05.332Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581704,0,'Local_Root_Location',TO_TIMESTAMP('2022-11-17 14:12:05','YYYY-MM-DD HH24:MI:SS'),100,'Local machine root location.','D','Y','Local Root Location','Local Root Location',TO_TIMESTAMP('2022-11-17 14:12:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:12:05.336Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581704 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Local_Root_Location
-- 2022-11-17T12:13:41.286Z
UPDATE AD_Element_Trl SET Description='Stammverzeichnis des lokalen Rechners.',Updated=TO_TIMESTAMP('2022-11-17 14:13:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581704 AND AD_Language='de_CH'
;

-- 2022-11-17T12:13:41.289Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581704,'de_CH')
;

-- Element: Local_Root_Location
-- 2022-11-17T12:17:12.601Z
UPDATE AD_Element_Trl SET Name='Lokales Stammverzeichnis', PrintName='Lokales Stammverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 14:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581704 AND AD_Language='de_CH'
;

-- 2022-11-17T12:17:12.604Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581704,'de_CH')
;

-- Element: Local_Root_Location
-- 2022-11-17T12:17:17.240Z
UPDATE AD_Element_Trl SET Name='Lokales Stammverzeichnis', PrintName='Lokales Stammverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 14:17:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581704 AND AD_Language='de_DE'
;

-- 2022-11-17T12:17:17.242Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581704,'de_DE')
;

-- 2022-11-17T12:17:17.245Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581704,'de_DE')
;

-- Element: Local_Root_Location
-- 2022-11-17T12:17:23.236Z
UPDATE AD_Element_Trl SET Description='Stammverzeichnis des lokalen Rechners.',Updated=TO_TIMESTAMP('2022-11-17 14:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581704 AND AD_Language='de_DE'
;

-- 2022-11-17T12:17:23.239Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581704,'de_DE')
;

-- 2022-11-17T12:17:23.240Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581704,'de_DE')
;

-- Element: Local_Root_Location
-- 2022-11-17T12:17:27.020Z
UPDATE AD_Element_Trl SET Description='Stammverzeichnis des lokalen Rechners.',Updated=TO_TIMESTAMP('2022-11-17 14:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581704 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:17:27.023Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581704,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.Local_Root_Location
-- 2022-11-17T12:17:55.743Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585084,581704,0,10,542258,'Local_Root_Location',TO_TIMESTAMP('2022-11-17 14:17:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Stammverzeichnis des lokalen Rechners.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lokales Stammverzeichnis',0,0,TO_TIMESTAMP('2022-11-17 14:17:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-17T12:17:55.750Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585084 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-17T12:17:55.753Z
/* DDL */  select update_Column_Translation_From_AD_Element(581704)
;

-- 2022-11-17T12:17:59.992Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN Local_Root_Location VARCHAR(255)')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:19:33.052Z
UPDATE AD_Element_Trl SET Description='Directory used to pull business partners from the sftp server. (If not set, the files will be pulled from the configured local root location)', Name='Business Partner Target Directory',Updated=TO_TIMESTAMP('2022-11-17 14:19:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='en_US'
;

-- 2022-11-17T12:19:33.056Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'en_US')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:19:50.254Z
UPDATE AD_Element_Trl SET PrintName='Business Partner Target Directory',Updated=TO_TIMESTAMP('2022-11-17 14:19:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='en_US'
;

-- 2022-11-17T12:19:50.256Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'en_US')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:20:18.826Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='de_DE'
;

-- 2022-11-17T12:20:18.829Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581702,'de_DE')
;

-- 2022-11-17T12:20:18.830Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'de_DE')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:20:45.908Z
UPDATE AD_Element_Trl SET Description='Directory used to pull business partners from the local machine. (If not set, the files will be pulled from the configured local root location)',Updated=TO_TIMESTAMP('2022-11-17 14:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='en_US'
;

-- 2022-11-17T12:20:45.911Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'en_US')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:21:20.001Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner',Updated=TO_TIMESTAMP('2022-11-17 14:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='de_DE'
;

-- 2022-11-17T12:21:20.003Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581702,'de_DE')
;

-- 2022-11-17T12:21:20.005Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'de_DE')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:21:57.827Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='de_DE'
;

-- 2022-11-17T12:21:57.830Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581702,'de_DE')
;

-- 2022-11-17T12:21:57.831Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'de_DE')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:22:06.975Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='de_CH'
;

-- 2022-11-17T12:22:06.978Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'de_CH')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:22:16.340Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:22:16.342Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'nl_NL')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:22:53.938Z
UPDATE AD_Column SET AD_Element_ID=581702, ColumnName='LocalFile_BPartner_TargetDirectory', Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)', Help=NULL, Name='Geschäftspartner-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 14:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585067
;

-- 2022-11-17T12:22:53.941Z
UPDATE AD_Field SET Name='Geschäftspartner-Zielverzeichnis', Description='Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)', Help=NULL WHERE AD_Column_ID=585067
;

-- 2022-11-17T12:22:53.942Z
/* DDL */  select update_Column_Translation_From_AD_Element(581702)
;

-- 2022-11-17T12:23:53.713Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581705,0,'LocalFile_Product_FileName_Pattern',TO_TIMESTAMP('2022-11-17 14:23:53','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Produktdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','U','Y','Produkt Dateinamen-Muster','Produkt Dateinamen-Muster',TO_TIMESTAMP('2022-11-17 14:23:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:23:53.718Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581705 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-17T12:24:01.468Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-17 14:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581705
;

-- 2022-11-17T12:24:01.470Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581705,'de_DE')
;

-- Element: LocalFile_Product_FileName_Pattern
-- 2022-11-17T12:24:53.352Z
UPDATE AD_Element_Trl SET Name='Product File Name Pattern', PrintName='Product File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 14:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581705 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:24:53.355Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581705,'nl_NL')
;

-- Element: LocalFile_Product_FileName_Pattern
-- 2022-11-17T12:25:39.796Z
UPDATE AD_Element_Trl SET Description='Ant-style pattern used to identify product files on the local machine. (If not set, all files are considered)', Name='Product File Name Pattern', PrintName='Product File Name Pattern',Updated=TO_TIMESTAMP('2022-11-17 14:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581705 AND AD_Language='en_US'
;

-- 2022-11-17T12:25:39.799Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581705,'en_US')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_FileName_Pattern
-- 2022-11-17T12:26:00.584Z
UPDATE AD_Column SET AD_Element_ID=581705, ColumnName='LocalFile_Product_FileName_Pattern', Description='Muster, das zur Identifizierung von Produktdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)', Help=NULL, Name='Produkt Dateinamen-Muster',Updated=TO_TIMESTAMP('2022-11-17 14:26:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585071
;

-- 2022-11-17T12:26:00.586Z
UPDATE AD_Field SET Name='Produkt Dateinamen-Muster', Description='Muster, das zur Identifizierung von Produktdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)', Help=NULL WHERE AD_Column_ID=585071
;

-- 2022-11-17T12:26:00.587Z
/* DDL */  select update_Column_Translation_From_AD_Element(581705)
;

-- 2022-11-17T12:26:37.263Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581706,0,'LocalFile_Product_TargetDirectory',TO_TIMESTAMP('2022-11-17 14:26:37','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem gemeinsamen sftp-Server verwendet wird.','U','Y','Produkt-Zielverzeichnis','Produkt-Zielverzeichnis',TO_TIMESTAMP('2022-11-17 14:26:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:26:37.265Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581706 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-17T12:26:46.175Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-17 14:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706
;

-- 2022-11-17T12:26:46.178Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'de_DE')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-17T12:30:34.758Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:30:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_CH'
;

-- 2022-11-17T12:30:34.761Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_CH')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-17T12:30:39.345Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:30:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='de_DE'
;

-- 2022-11-17T12:30:39.347Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581603,'de_DE')
;

-- 2022-11-17T12:30:39.348Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'de_DE')
;

-- Element: SFTP_Product_TargetDirectory
-- 2022-11-17T12:30:45.196Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom sftp-Server verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem Stammverzeichnis der sftp-Verbindung gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:30:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581603 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:30:45.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581603,'nl_NL')
;

-- 2022-11-17T12:30:56.896Z
INSERT INTO t_alter_column values('externalsystem_config_sap_sftp','SFTP_Product_TargetDirectory','VARCHAR(255)',null,null)
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-17T12:33:56.916Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='en_US'
;

-- 2022-11-17T12:33:56.919Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'en_US')
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-17T12:34:01.408Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='de_DE'
;

-- 2022-11-17T12:34:01.411Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581706,'de_DE')
;

-- 2022-11-17T12:34:01.412Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'de_DE')
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-17T12:34:05.110Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='de_CH'
;

-- 2022-11-17T12:34:05.113Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'de_CH')
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-17T12:34:13.471Z
UPDATE AD_Element_Trl SET Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',Updated=TO_TIMESTAMP('2022-11-17 14:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:34:13.474Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'nl_NL')
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-17T12:35:04.380Z
UPDATE AD_Element_Trl SET Description='Directory used to pull products from the sftp server. (If not set, the files will be pulled from the root location of the sftp server)', Name='Product Target Directory', PrintName='Product Target Directory',Updated=TO_TIMESTAMP('2022-11-17 14:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='en_US'
;

-- 2022-11-17T12:35:04.383Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'en_US')
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-17T12:35:18.171Z
UPDATE AD_Element_Trl SET Name='Product Target Directory', PrintName='Product Target Directory',Updated=TO_TIMESTAMP('2022-11-17 14:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='nl_NL'
;

-- 2022-11-17T12:35:18.173Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'nl_NL')
;

-- Element: LocalFile_Product_TargetDirectory
-- 2022-11-17T12:35:20.342Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-17 14:35:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581706 AND AD_Language='en_US'
;

-- 2022-11-17T12:35:20.345Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581706,'en_US')
;

-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_TargetDirectory
-- 2022-11-17T12:35:39.844Z
UPDATE AD_Column SET AD_Element_ID=581706, ColumnName='LocalFile_Product_TargetDirectory', Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)', Help=NULL, Name='Produkt-Zielverzeichnis',Updated=TO_TIMESTAMP('2022-11-17 14:35:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585072
;

-- 2022-11-17T12:35:39.846Z
UPDATE AD_Field SET Name='Produkt-Zielverzeichnis', Description='Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)', Help=NULL WHERE AD_Column_ID=585072
;

-- 2022-11-17T12:35:39.848Z
/* DDL */  select update_Column_Translation_From_AD_Element(581706)
;

-- Element: LocalFile_Product_FileName_Pattern
-- 2022-11-17T12:36:24.651Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-17 14:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581705 AND AD_Language='en_US'
;

-- 2022-11-17T12:36:24.654Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581705,'en_US')
;

-- Element: LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:36:42.835Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-17 14:36:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581702 AND AD_Language='en_US'
;

-- 2022-11-17T12:36:42.838Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581702,'en_US')
;

-- 2022-11-17T12:37:29.977Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_BPartner_FileName_Pattern VARCHAR(255)')
;

-- 2022-11-17T12:37:37.661Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_BPartner_TargetDirectory VARCHAR(255)')
;

-- 2022-11-17T12:37:44.153Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_Product_FileName_Pattern VARCHAR(255)')
;

-- 2022-11-17T12:37:49.888Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN LocalFile_Product_TargetDirectory VARCHAR(255)')
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> External System Config SAP SFTP
-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-17T12:45:36.733Z
UPDATE AD_Tab SET AD_Column_ID=585052, AD_Element_ID=581669, CommitWarning=NULL, Description=NULL, Help=NULL, Name='External System Config SAP SFTP', Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2022-11-17 14:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546672
;

-- 2022-11-17T12:45:36.733Z
/* DDL */  select update_tab_translation_from_ad_element(581669)
;

-- 2022-11-17T12:45:36.749Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546672)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> SFTP
-- Table: ExternalSystem_Config_SAP_SFTP
-- 2022-11-17T12:46:40.435Z
UPDATE AD_Tab SET AD_Element_ID=581675, CommitWarning=NULL, Description=NULL, Help=NULL, Name='SFTP',Updated=TO_TIMESTAMP('2022-11-17 14:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546672
;

-- 2022-11-17T12:46:40.435Z
/* DDL */  select update_tab_translation_from_ad_element(581675)
;

-- 2022-11-17T12:46:40.450Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546672)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei
-- Table: ExternalSystem_Config_SAP_LocalFile
-- 2022-11-17T12:47:25.834Z
UPDATE AD_Tab SET AD_Column_ID=585051, Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2022-11-17 14:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546673
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Lokales Stammverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.Local_Root_Location
-- 2022-11-17T12:48:19.075Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585084,708125,0,546673,0,TO_TIMESTAMP('2022-11-17 14:48:18','YYYY-MM-DD HH24:MI:SS'),100,'Stammverzeichnis des lokalen Rechners.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Lokales Stammverzeichnis',0,70,0,1,1,TO_TIMESTAMP('2022-11-17 14:48:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:48:19.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:48:19.075Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581704)
;

-- 2022-11-17T12:48:19.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708125
;

-- 2022-11-17T12:48:19.091Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708125)
;

-- Column: ExternalSystem_Config_SAP_LocalFile.Local_Root_Location
-- 2022-11-17T12:49:28.431Z
UPDATE AD_Column SET DefaultValue='Lokales-Stammverzeichnis', IsMandatory='Y',Updated=TO_TIMESTAMP('2022-11-17 14:49:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585084
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:50:30.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585066,708126,0,546673,0,TO_TIMESTAMP('2022-11-17 14:50:30','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Geschäftspartner Dateinamen-Muster',0,80,0,1,1,TO_TIMESTAMP('2022-11-17 14:50:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:50:30.492Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708126 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:50:30.499Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581701)
;

-- 2022-11-17T12:50:30.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708126
;

-- 2022-11-17T12:50:30.499Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708126)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:51:01.940Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585067,708127,0,546673,0,TO_TIMESTAMP('2022-11-17 14:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Zielverzeichnis',0,90,0,1,1,TO_TIMESTAMP('2022-11-17 14:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:51:01.940Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:51:01.940Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581702)
;

-- 2022-11-17T12:51:01.948Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708127
;

-- 2022-11-17T12:51:01.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708127)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_FileName_Pattern
-- 2022-11-17T12:51:24.337Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585071,708128,0,546673,0,TO_TIMESTAMP('2022-11-17 14:51:24','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Produktdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Produkt Dateinamen-Muster',0,100,0,1,1,TO_TIMESTAMP('2022-11-17 14:51:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:51:24.340Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:51:24.340Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581705)
;

-- 2022-11-17T12:51:24.340Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708128
;

-- 2022-11-17T12:51:24.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708128)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_TargetDirectory
-- 2022-11-17T12:51:39.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585072,708129,0,546673,0,TO_TIMESTAMP('2022-11-17 14:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Produkt-Zielverzeichnis',0,110,0,1,1,TO_TIMESTAMP('2022-11-17 14:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:51:39.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:51:39.605Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581706)
;

-- 2022-11-17T12:51:39.616Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708129
;

-- 2022-11-17T12:51:39.616Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708129)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Lokales Stammverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.Local_Root_Location
-- 2022-11-17T12:51:58.142Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-17 14:51:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708125
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Fehler-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ErroredDirectory
-- 2022-11-17T12:52:22.190Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-11-17 14:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708052
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Verarbeitet-Verzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.ProcessedDirectory
-- 2022-11-17T12:52:28.095Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-11-17 14:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708051
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_SAP_LocalFile.PollingFrequencyInMs
-- 2022-11-17T12:53:27.200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585065,708130,0,546673,0,TO_TIMESTAMP('2022-11-17 14:53:27','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.',0,'de.metas.externalsystem',0,'Y','Y','N','N','N','N','N','N','Abfragefrequenz in Millisekunden',0,120,0,1,1,TO_TIMESTAMP('2022-11-17 14:53:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T12:53:27.200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-17T12:53:27.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581586)
;

-- 2022-11-17T12:53:27.207Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708130
;

-- 2022-11-17T12:53:27.207Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708130)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Geschäftspartner Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_FileName_Pattern
-- 2022-11-17T12:54:16.517Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708126,0,546673,613523,550024,'F',TO_TIMESTAMP('2022-11-17 14:54:16','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Geschäftspartnerdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','Y','N','N','Y','N','N','N',0,'Geschäftspartner Dateinamen-Muster',30,0,0,TO_TIMESTAMP('2022-11-17 14:54:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:54:47.360Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708127,0,546673,613524,550024,'F',TO_TIMESTAMP('2022-11-17 14:54:47','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Zielverzeichnis',40,0,0,TO_TIMESTAMP('2022-11-17 14:54:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_TargetDirectory
-- 2022-11-17T12:54:58.786Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708129,0,546673,613525,550024,'F',TO_TIMESTAMP('2022-11-17 14:54:58','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Produkten vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)','Y','N','N','Y','N','N','N',0,'Produkt-Zielverzeichnis',50,0,0,TO_TIMESTAMP('2022-11-17 14:54:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:55:19.105Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708127,0,546673,613526,550024,'F',TO_TIMESTAMP('2022-11-17 14:55:18','YYYY-MM-DD HH24:MI:SS'),100,'Verzeichnis, das zum Abrufen von Geschäftspartnern vom lokalen Rechner verwendet wird. (Falls nicht festgelegt, werden die Dateien aus dem konfigurierten lokalen Stammverzeichnis gezogen)','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Zielverzeichnis',60,0,0,TO_TIMESTAMP('2022-11-17 14:55:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Produkt Dateinamen-Muster
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_FileName_Pattern
-- 2022-11-17T12:55:43.754Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708128,0,546673,613527,550024,'F',TO_TIMESTAMP('2022-11-17 14:55:43','YYYY-MM-DD HH24:MI:SS'),100,'Muster, das zur Identifizierung von Produktdateien auf dem lokalen Rechner verwendet wird. (Wenn nicht festgelegt, werden alle Dateien berücksichtigt)','Y','N','N','Y','N','N','N',0,'Produkt Dateinamen-Muster',70,0,0,TO_TIMESTAMP('2022-11-17 14:55:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-17T12:56:18.865Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613526
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Abfragefrequenz in Millisekunden
-- Column: ExternalSystem_Config_SAP_LocalFile.PollingFrequencyInMs
-- 2022-11-17T12:57:04.081Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708130,0,546673,613528,550024,'F',TO_TIMESTAMP('2022-11-17 14:57:03','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie häufig der Prozess nach neuen Dateien suchen soll.','Y','N','N','Y','N','N','N',0,'Abfragefrequenz in Millisekunden',80,0,0,TO_TIMESTAMP('2022-11-17 14:57:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Lokales Stammverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.Local_Root_Location
-- 2022-11-17T12:57:35.941Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708125,0,546673,613529,550024,'F',TO_TIMESTAMP('2022-11-17 14:57:35','YYYY-MM-DD HH24:MI:SS'),100,'Stammverzeichnis des lokalen Rechners.','Y','N','N','Y','N','N','N',0,'Lokales Stammverzeichnis',90,0,0,TO_TIMESTAMP('2022-11-17 14:57:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T14:23:47.631Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE public.ExternalSystem_Config_SAP_LocalFile ADD COLUMN PollingFrequencyInMs NUMERIC(10) DEFAULT 1000 NOT NULL')
;

-- 2022-11-17T14:27:18.138Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','LocalFile_BPartner_FileName_Pattern','VARCHAR(255)',null,null)
;

-- 2022-11-17T14:27:18.154Z
INSERT INTO t_alter_column values('externalsystem_config_sap_localfile','LocalFile_BPartner_FileName_Pattern',null,'NULL',null)
;

-- Column: ExternalSystem_Config_SAP.ExternalSystem_Config_SAP_ID
-- 2022-11-17T14:39:32.649Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-17 16:39:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584652
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Lokales Stammverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.Local_Root_Location
-- 2022-11-17T14:42:33.733Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-17 16:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613529
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Produkt-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_Product_TargetDirectory
-- 2022-11-17T14:42:33.733Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-17 16:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613525
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> Lokale Datei(546673,de.metas.externalsystem) -> main -> 10 -> main.Geschäftspartner-Zielverzeichnis
-- Column: ExternalSystem_Config_SAP_LocalFile.LocalFile_BPartner_TargetDirectory
-- 2022-11-17T14:42:33.733Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-17 16:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613524
;

-- 2022-11-17T15:04:06.101Z
UPDATE ExternalSystem_Config SET IsActive='Y',Updated=TO_TIMESTAMP('2022-11-17 17:04:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=540013
;

-- 2022-11-17T15:04:16.278Z
DELETE FROM ExternalSystem_Config_SAP WHERE ExternalSystem_Config_SAP_ID=540009
;

-- 2022-11-17T15:04:25.573Z
INSERT INTO ExternalSystem_Config_SAP (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ExternalSystem_Config_ID,ExternalSystem_Config_SAP_ID,ExternalSystemValue,IsActive,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2022-11-17 17:04:25','YYYY-MM-DD HH24:MI:SS'),100,540013,540016,'SAP-Konfig','Y',TO_TIMESTAMP('2022-11-17 17:04:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T15:04:39.971Z
INSERT INTO ExternalSystem_Config_SAP_SFTP (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ErroredDirectory,ExternalSystem_Config_SAP_ID,ExternalSystem_Config_SAP_SFTP_ID,IsActive,PollingFrequencyInMs,ProcessedDirectory,SFTP_HostName,SFTP_Password,SFTP_Port,SFTP_Username,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2022-11-17 17:04:39','YYYY-MM-DD HH24:MI:SS'),100,'error',540016,540009,'Y',1000,'move','SFTP-Hostname','SFTP-Passwort','SFTP-Port','SFTP-Benutzername',TO_TIMESTAMP('2022-11-17 17:04:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T15:04:48.449Z
INSERT INTO ExternalSystem_Config_SAP_LocalFile (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ErroredDirectory,ExternalSystem_Config_SAP_ID,ExternalSystem_Config_SAP_LocalFile_ID,IsActive,Local_Root_Location,PollingFrequencyInMs,ProcessedDirectory,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2022-11-17 17:04:48','YYYY-MM-DD HH24:MI:SS'),100,'error',540016,540013,'Y','Lokales-Stammverzeichnis',1000,'move',TO_TIMESTAMP('2022-11-17 17:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-17T15:06:03.873Z
UPDATE ExternalSystem_Config SET IsActive='N',Updated=TO_TIMESTAMP('2022-11-17 17:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ExternalSystem_Config_ID=540013
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-17T15:41:04.436Z
UPDATE AD_Ref_List_Trl SET Name='Start Products Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-17 17:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductsSyncLocalFile_import
-- 2022-11-17T15:41:12.068Z
UPDATE AD_Ref_List_Trl SET Name='Start Products Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-17 17:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543345
;

/*
 * #%L
 * de.metas.externalsystem
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

-- Reference: External_Request SAP
-- Value: startProductSyncLocalFile
-- ValueName: import
-- 2022-11-18T12:52:52.480Z
UPDATE AD_Ref_List SET Value='startProductSyncLocalFile',Updated=TO_TIMESTAMP('2022-11-18 14:52:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductSyncLocalFile_import
-- 2022-11-18T12:53:12.632Z
UPDATE AD_Ref_List_Trl SET Description='Starts the product sychronization with SAP external system. The product files are fetched from the local machine.',Updated=TO_TIMESTAMP('2022-11-18 14:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductSyncLocalFile_import
-- 2022-11-18T12:53:21.002Z
UPDATE AD_Ref_List_Trl SET Description='Starts the product sychronization with SAP external system. The product files are fetched from the local machine.', Name='Start Product Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-18 14:53:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543345
;

-- Reference Item: External_Request SAP -> startProductSyncLocalFile_import
-- 2022-11-18T12:53:24.711Z
UPDATE AD_Ref_List_Trl SET Name='Start Product Synchronization Local File',Updated=TO_TIMESTAMP('2022-11-18 14:53:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543345
;

-- Reference: External_Request SAP
-- Value: startProductSyncSFTP
-- ValueName: import
-- 2022-11-18T12:54:12.739Z
UPDATE AD_Ref_List SET Value='startProductSyncSFTP',Updated=TO_TIMESTAMP('2022-11-18 14:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductSyncSFTP_import
-- 2022-11-18T12:54:24.995Z
UPDATE AD_Ref_List_Trl SET Description='Starts the product sychronization with SAP external system. The product files are fetched from the configured SFTP server.', Name='Start Product Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 14:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543300
;

-- Reference Item: External_Request SAP -> startProductSyncSFTP_import
-- 2022-11-18T12:54:34.796Z
UPDATE AD_Ref_List_Trl SET Description='Starts the product sychronization with SAP external system. The product files are fetched from the configured SFTP server.', Name='Start Product Synchronization SFTP',Updated=TO_TIMESTAMP('2022-11-18 14:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543300
;

-- Reference: External_Request SAP
-- Value: stopProductSyncSFTP
-- ValueName: import
-- 2022-11-18T12:55:13.438Z
UPDATE AD_Ref_List SET Value='stopProductSyncSFTP',Updated=TO_TIMESTAMP('2022-11-18 14:55:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543301
;