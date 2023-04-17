-- Table: SAP_BPartnerImportSettings
-- 2023-04-11T11:53:14.973Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542325,'N',TO_TIMESTAMP('2023-04-11 14:53:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','Y','N','N','N','N','N',0,'SAP BPartner Import Settings','NP','L','SAP_BPartnerImportSettings','DTI',TO_TIMESTAMP('2023-04-11 14:53:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T11:53:14.977Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542325 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-04-11T11:53:16.066Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556259,TO_TIMESTAMP('2023-04-11 14:53:15','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table SAP_BPartnerImportSettings',1,'Y','N','Y','Y','SAP_BPartnerImportSettings','N',1000000,TO_TIMESTAMP('2023-04-11 14:53:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T11:53:16.100Z
CREATE SEQUENCE SAP_BPARTNERIMPORTSETTINGS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: SAP_BPartnerImportSettings
-- 2023-04-11T11:54:50.903Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-04-11 14:54:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542325
;

-- 2023-04-11T11:57:08.037Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582232,0,'PartnerCodePattern',TO_TIMESTAMP('2023-04-11 14:57:07','YYYY-MM-DD HH24:MI:SS'),100,'Regex pattern for the partner code','de.metas.externalsystem','Y','PartnerCodePattern','Partner Code Pattern',TO_TIMESTAMP('2023-04-11 14:57:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T11:57:08.041Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582232 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PartnerCodePattern
-- 2023-04-11T11:57:56.072Z
UPDATE AD_Element_Trl SET Description='Regex-Muster für den Partnercode', Name='Partner-Code-Muster', PrintName='Partner-Code-Muster',Updated=TO_TIMESTAMP('2023-04-11 14:57:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='de_CH'
;

-- 2023-04-11T11:57:56.118Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'de_CH') 
;

-- Element: PartnerCodePattern
-- 2023-04-11T11:58:01.421Z
UPDATE AD_Element_Trl SET Name='Partner-Code-Muster', PrintName='Partner-Code-Muster',Updated=TO_TIMESTAMP('2023-04-11 14:58:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='de_DE'
;

-- 2023-04-11T11:58:01.422Z
UPDATE AD_Element SET Name='Partner-Code-Muster', PrintName='Partner-Code-Muster' WHERE AD_Element_ID=582232
;

-- 2023-04-11T11:58:04.852Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582232,'de_DE') 
;

-- 2023-04-11T11:58:04.858Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'de_DE') 
;

-- Element: PartnerCodePattern
-- 2023-04-11T11:58:13.143Z
UPDATE AD_Element_Trl SET Name='Partner Code Pattern',Updated=TO_TIMESTAMP('2023-04-11 14:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='en_US'
;

-- 2023-04-11T11:58:13.145Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'en_US') 
;

-- Element: PartnerCodePattern
-- 2023-04-11T11:58:19.114Z
UPDATE AD_Element_Trl SET Name='Partner Code Pattern',Updated=TO_TIMESTAMP('2023-04-11 14:58:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='fr_CH'
;

-- 2023-04-11T11:58:19.116Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'fr_CH') 
;

-- Element: PartnerCodePattern
-- 2023-04-11T11:58:29.565Z
UPDATE AD_Element_Trl SET Description='Regex-Muster für den Partnercode',Updated=TO_TIMESTAMP('2023-04-11 14:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='de_DE'
;

-- 2023-04-11T11:58:29.567Z
UPDATE AD_Element SET Description='Regex-Muster für den Partnercode' WHERE AD_Element_ID=582232
;

-- 2023-04-11T11:58:32.986Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582232,'de_DE') 
;

-- 2023-04-11T11:58:32.987Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'de_DE') 
;

-- Column: SAP_BPartnerImportSettings.AD_Client_ID
-- 2023-04-11T12:01:07.852Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586469,102,0,19,542325,'AD_Client_ID',TO_TIMESTAMP('2023-04-11 15:01:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-04-11 15:01:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:07.856Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586469 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:09.187Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: SAP_BPartnerImportSettings.AD_Org_ID
-- 2023-04-11T12:01:11.395Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586470,113,0,30,542325,'AD_Org_ID',TO_TIMESTAMP('2023-04-11 15:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-04-11 15:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:11.406Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586470 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:12.675Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: SAP_BPartnerImportSettings.Created
-- 2023-04-11T12:01:14.386Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586471,245,0,16,542325,'Created',TO_TIMESTAMP('2023-04-11 15:01:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-04-11 15:01:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:14.388Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586471 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:15.627Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: SAP_BPartnerImportSettings.CreatedBy
-- 2023-04-11T12:01:17.225Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586472,246,0,18,110,542325,'CreatedBy',TO_TIMESTAMP('2023-04-11 15:01:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-04-11 15:01:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:17.225Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586472 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:18.464Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: SAP_BPartnerImportSettings.IsActive
-- 2023-04-11T12:01:20.112Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586473,348,0,20,542325,'IsActive',TO_TIMESTAMP('2023-04-11 15:01:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-04-11 15:01:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:20.114Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:21.400Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: SAP_BPartnerImportSettings.Updated
-- 2023-04-11T12:01:22.938Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586474,607,0,16,542325,'Updated',TO_TIMESTAMP('2023-04-11 15:01:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-04-11 15:01:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:22.940Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:24.174Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: SAP_BPartnerImportSettings.UpdatedBy
-- 2023-04-11T12:01:25.751Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586475,608,0,18,110,542325,'UpdatedBy',TO_TIMESTAMP('2023-04-11 15:01:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-04-11 15:01:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:25.756Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586475 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:27.029Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-04-11T12:01:28.233Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582233,0,'SAP_BPartnerImportSettings_ID',TO_TIMESTAMP('2023-04-11 15:01:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','SAP BPartner Import Settings','SAP BPartner Import Settings',TO_TIMESTAMP('2023-04-11 15:01:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T12:01:28.235Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582233 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SAP_BPartnerImportSettings.SAP_BPartnerImportSettings_ID
-- 2023-04-11T12:01:29.610Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586476,582233,0,13,542325,'SAP_BPartnerImportSettings_ID',TO_TIMESTAMP('2023-04-11 15:01:28','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','SAP BPartner Import Settings',0,0,TO_TIMESTAMP('2023-04-11 15:01:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:01:29.611Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586476 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:01:31.453Z
/* DDL */  select update_Column_Translation_From_AD_Element(582233) 
;

-- 2023-04-11T12:02:07.603Z
/* DDL */ CREATE TABLE public.SAP_BPartnerImportSettings (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, SAP_BPartnerImportSettings_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT SAP_BPartnerImportSettings_Key PRIMARY KEY (SAP_BPartnerImportSettings_ID))
;

-- 2023-04-11T12:02:21.364Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-04-11T12:02:35.626Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-04-11T12:02:45.326Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-04-11T12:03:03.987Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','IsActive','CHAR(1)',null,null)
;

-- 2023-04-11T12:03:18.430Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','SAP_BPartnerImportSettings_ID','NUMERIC(10)',null,null)
;

-- 2023-04-11T12:03:32.664Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-04-11T12:03:46.727Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','UpdatedBy','NUMERIC(10)',null,null)
;

-- Column: SAP_BPartnerImportSettings.PartnerCodePattern
-- 2023-04-11T12:05:57.913Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586477,582232,0,10,542325,'PartnerCodePattern',TO_TIMESTAMP('2023-04-11 15:05:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Regex-Muster für den Partnercode','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Partner-Code-Muster',0,0,TO_TIMESTAMP('2023-04-11 15:05:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:05:57.915Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586477 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:05:59.153Z
/* DDL */  select update_Column_Translation_From_AD_Element(582232) 
;

-- 2023-04-11T12:06:16.938Z
/* DDL */ SELECT public.db_alter_table('SAP_BPartnerImportSettings','ALTER TABLE public.SAP_BPartnerImportSettings ADD COLUMN PartnerCodePattern VARCHAR(255) NOT NULL')
;

-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T12:14:10.348Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586478,566,0,11,542325,'SeqNo',TO_TIMESTAMP('2023-04-11 15:14:10','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0) + 100 AS DefaultValue FROM C_PricingRule','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.externalsystem',0,14,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2023-04-11 15:14:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:14:10.352Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586478 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:14:11.606Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T12:14:30.306Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0) + 100 AS DefaultValue FROM SAP_BPartnerImportSettings',Updated=TO_TIMESTAMP('2023-04-11 15:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586478
;

-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T12:19:23.569Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM SAP_BPartnerImportSettings WHERE PartnerCodePattern=@PartnerCodePattern@',Updated=TO_TIMESTAMP('2023-04-11 15:19:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586478
;

-- Column: SAP_BPartnerImportSettings.C_BP_Group_ID
-- 2023-04-11T12:26:05.100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586479,1383,0,19,542325,'C_BP_Group_ID',TO_TIMESTAMP('2023-04-11 15:26:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Geschäftspartnergruppe','de.metas.externalsystem',0,10,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartnergruppe',0,0,TO_TIMESTAMP('2023-04-11 15:26:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:26:05.102Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:26:06.440Z
/* DDL */  select update_Column_Translation_From_AD_Element(1383) 
;

-- 2023-04-11T12:26:16.597Z
/* DDL */ SELECT public.db_alter_table('SAP_BPartnerImportSettings','ALTER TABLE public.SAP_BPartnerImportSettings ADD COLUMN C_BP_Group_ID NUMERIC(10)')
;

-- 2023-04-11T12:26:16.613Z
ALTER TABLE SAP_BPartnerImportSettings ADD CONSTRAINT CBPGroup_SAPBPartnerImportSettings FOREIGN KEY (C_BP_Group_ID) REFERENCES public.C_BP_Group DEFERRABLE INITIALLY DEFERRED
;

-- 2023-04-11T12:26:34.047Z
/* DDL */ SELECT public.db_alter_table('SAP_BPartnerImportSettings','ALTER TABLE public.SAP_BPartnerImportSettings ADD COLUMN SeqNo NUMERIC(10) NOT NULL')
;

-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T12:27:08.355Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM SAP_BPartnerImportSettings WHERE PartnerCodePattern ILIKE ''@PartnerCodePattern@''',Updated=TO_TIMESTAMP('2023-04-11 15:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586478
;

-- 2023-04-11T12:27:17.953Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','SeqNo','NUMERIC(10)',null,null)
;

-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T12:29:00.072Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM SAP_BPartnerImportSettings WHERE PartnerCodePattern=@PartnerCodePattern@',Updated=TO_TIMESTAMP('2023-04-11 15:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586478
;

-- 2023-04-11T12:29:16.440Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','SeqNo','NUMERIC(10)',null,null)
;

-- 2023-04-11T12:33:16.559Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582234,0,'isSingleBPartner',TO_TIMESTAMP('2023-04-11 15:33:16','YYYY-MM-DD HH24:MI:SS'),100,'If true, the business partners having the partner code falling under the regex pattern, will not be aggregated based on the code and no section group business partner is created','de.metas.externalsystem','Y','Single Business Partner','Single Business Partner',TO_TIMESTAMP('2023-04-11 15:33:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T12:33:16.563Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582234 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: isSingleBPartner
-- 2023-04-11T12:34:01.196Z
UPDATE AD_Element_Trl SET Description='Bei "true" werden die Geschäftspartner, deren Partnercode unter das Regex-Muster fällt, nicht auf der Grundlage des Codes aggregiert und es wird keine Abschnittsgruppe Geschäftspartner erstellt.', Name='Einzelner Geschäftspartner', PrintName='Einzelner Geschäftspartner',Updated=TO_TIMESTAMP('2023-04-11 15:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='de_CH'
;

-- 2023-04-11T12:34:01.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'de_CH') 
;

-- Element: isSingleBPartner
-- 2023-04-11T12:34:06.686Z
UPDATE AD_Element_Trl SET Name='Einzelner Geschäftspartner', PrintName='Einzelner Geschäftspartner',Updated=TO_TIMESTAMP('2023-04-11 15:34:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='de_DE'
;

-- 2023-04-11T12:34:06.687Z
UPDATE AD_Element SET Name='Einzelner Geschäftspartner', PrintName='Einzelner Geschäftspartner' WHERE AD_Element_ID=582234
;

-- 2023-04-11T12:34:09.976Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582234,'de_DE') 
;

-- 2023-04-11T12:34:09.988Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'de_DE') 
;

-- Element: isSingleBPartner
-- 2023-04-11T12:35:53.123Z
UPDATE AD_Element_Trl SET Description='Bei "true" werden die Geschäftspartner, deren Partnercode unter das Regex-Muster fällt, nicht auf der Grundlage des Codes aggregiert und es wird keine Abschnittsgruppe Geschäftspartner erstellt.',Updated=TO_TIMESTAMP('2023-04-11 15:35:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='de_DE'
;

-- 2023-04-11T12:35:53.124Z
UPDATE AD_Element SET Description='Bei "true" werden die Geschäftspartner, deren Partnercode unter das Regex-Muster fällt, nicht auf der Grundlage des Codes aggregiert und es wird keine Abschnittsgruppe Geschäftspartner erstellt.' WHERE AD_Element_ID=582234
;

-- 2023-04-11T12:35:56.977Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582234,'de_DE') 
;

-- 2023-04-11T12:35:56.978Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'de_DE') 
;

-- Column: SAP_BPartnerImportSettings.isSingleBPartner
-- 2023-04-11T12:36:32.733Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586480,582234,0,20,542325,'isSingleBPartner',TO_TIMESTAMP('2023-04-11 15:36:32','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Bei "true" werden die Geschäftspartner, deren Partnercode unter das Regex-Muster fällt, nicht auf der Grundlage des Codes aggregiert und es wird keine Abschnittsgruppe Geschäftspartner erstellt.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Einzelner Geschäftspartner',0,0,TO_TIMESTAMP('2023-04-11 15:36:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T12:36:32.743Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T12:36:33.988Z
/* DDL */  select update_Column_Translation_From_AD_Element(582234) 
;

-- 2023-04-11T12:36:41.512Z
/* DDL */ SELECT public.db_alter_table('SAP_BPartnerImportSettings','ALTER TABLE public.SAP_BPartnerImportSettings ADD COLUMN isSingleBPartner CHAR(1) DEFAULT ''N'' CHECK (isSingleBPartner IN (''Y'',''N'')) NOT NULL')
;

-- 2023-04-11T12:40:05.910Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582235,0,TO_TIMESTAMP('2023-04-11 15:40:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Business Partner Import Settings','Business Partner Import Settings',TO_TIMESTAMP('2023-04-11 15:40:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T12:40:05.912Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582235 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-04-11T12:40:38.635Z
UPDATE AD_Element_Trl SET Name='Geschäftspartner-Import-Einstellungen', PrintName='Geschäftspartner-Import-Einstellungen',Updated=TO_TIMESTAMP('2023-04-11 15:40:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582235 AND AD_Language='de_CH'
;

-- 2023-04-11T12:40:38.640Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582235,'de_CH') 
;

-- Element: null
-- 2023-04-11T12:40:44.704Z
UPDATE AD_Element_Trl SET Name='Geschäftspartner-Import-Einstellungen', PrintName='Geschäftspartner-Import-Einstellungen',Updated=TO_TIMESTAMP('2023-04-11 15:40:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582235 AND AD_Language='de_DE'
;

-- 2023-04-11T12:40:44.705Z
UPDATE AD_Element SET Name='Geschäftspartner-Import-Einstellungen', PrintName='Geschäftspartner-Import-Einstellungen' WHERE AD_Element_ID=582235
;

-- 2023-04-11T12:40:48.098Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582235,'de_DE') 
;

-- 2023-04-11T12:40:48.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582235,'de_DE') 
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> SAP BPartner Import Settings
-- Table: SAP_BPartnerImportSettings
-- 2023-04-11T12:41:50.347Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582233,0,546903,542325,541631,'Y',TO_TIMESTAMP('2023-04-11 15:41:50','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','A','SAP_BPartnerImportSettings','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'SAP BPartner Import Settings','N',80,0,TO_TIMESTAMP('2023-04-11 15:41:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T12:41:50.351Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546903 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-04-11T12:41:50.356Z
/* DDL */  select update_tab_translation_from_ad_element(582233) 
;

-- 2023-04-11T12:41:50.380Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546903)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen
-- Table: SAP_BPartnerImportSettings
-- 2023-04-11T12:42:55.343Z
UPDATE AD_Tab SET AD_Element_ID=582235, CommitWarning=NULL, Description=NULL, EntityType='de.metas.externalsystem', Help=NULL, Name='Geschäftspartner-Import-Einstellungen', TabLevel=1,Updated=TO_TIMESTAMP('2023-04-11 15:42:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546903
;

-- 2023-04-11T12:42:55.343Z
UPDATE AD_Tab_Trl trl SET Name='Geschäftspartner-Import-Einstellungen' WHERE AD_Tab_ID=546903 AND AD_Language='de_DE'
;

-- 2023-04-11T12:43:02.228Z
/* DDL */  select update_tab_translation_from_ad_element(582235) 
;

-- 2023-04-11T12:43:02.235Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546903)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen(546903,de.metas.externalsystem) -> Reihenfolge
-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T12:43:49.800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586478,713840,0,546903,0,TO_TIMESTAMP('2023-04-11 15:43:49','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,'de.metas.externalsystem','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','N','N','N','N','N','Reihenfolge',0,10,0,1,1,TO_TIMESTAMP('2023-04-11 15:43:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T12:43:49.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-11T12:43:49.803Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2023-04-11T12:43:49.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713840
;

-- 2023-04-11T12:43:49.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713840)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen(546903,de.metas.externalsystem) -> Partner-Code-Muster
-- Column: SAP_BPartnerImportSettings.PartnerCodePattern
-- 2023-04-11T12:44:35.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586477,713841,0,546903,0,TO_TIMESTAMP('2023-04-11 15:44:35','YYYY-MM-DD HH24:MI:SS'),100,'Regex-Muster für den Partnercode',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Partner-Code-Muster',0,20,0,1,1,TO_TIMESTAMP('2023-04-11 15:44:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T12:44:35.650Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-11T12:44:35.656Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582232) 
;

-- 2023-04-11T12:44:35.662Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713841
;

-- 2023-04-11T12:44:35.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713841)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen(546903,de.metas.externalsystem) -> Einzelner Geschäftspartner
-- Column: SAP_BPartnerImportSettings.isSingleBPartner
-- 2023-04-11T12:45:08.371Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586480,713842,0,546903,0,TO_TIMESTAMP('2023-04-11 15:45:08','YYYY-MM-DD HH24:MI:SS'),100,'Bei "true" werden die Geschäftspartner, deren Partnercode unter das Regex-Muster fällt, nicht auf der Grundlage des Codes aggregiert und es wird keine Abschnittsgruppe Geschäftspartner erstellt.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Einzelner Geschäftspartner',0,30,0,1,1,TO_TIMESTAMP('2023-04-11 15:45:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T12:45:08.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-11T12:45:08.374Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582234) 
;

-- 2023-04-11T12:45:08.376Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713842
;

-- 2023-04-11T12:45:08.376Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713842)
;

-- 2023-04-11T12:56:33.211Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713840
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen(546903,de.metas.externalsystem) -> Reihenfolge
-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T12:56:33.214Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=713840
;

-- 2023-04-11T12:56:33.230Z
DELETE FROM AD_Field WHERE AD_Field_ID=713840
;

-- Column: SAP_BPartnerImportSettings.ExternalSystem_Config_SAP_ID
-- 2023-04-11T13:25:52.026Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586481,581532,0,19,542325,'ExternalSystem_Config_SAP_ID',TO_TIMESTAMP('2023-04-11 16:25:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'ExternalSystem_Config_SAP',0,0,TO_TIMESTAMP('2023-04-11 16:25:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-11T13:25:52.029Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-11T13:25:53.298Z
/* DDL */  select update_Column_Translation_From_AD_Element(581532) 
;

-- 2023-04-11T13:26:02.326Z
/* DDL */ SELECT public.db_alter_table('SAP_BPartnerImportSettings','ALTER TABLE public.SAP_BPartnerImportSettings ADD COLUMN ExternalSystem_Config_SAP_ID NUMERIC(10) NOT NULL')
;

-- 2023-04-11T13:26:02.346Z
ALTER TABLE SAP_BPartnerImportSettings ADD CONSTRAINT ExternalSystemConfigSAP_SAPBPartnerImportSettings FOREIGN KEY (ExternalSystem_Config_SAP_ID) REFERENCES public.ExternalSystem_Config_SAP DEFERRABLE INITIALLY DEFERRED
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen
-- Table: SAP_BPartnerImportSettings
-- 2023-04-11T13:26:56.846Z
UPDATE AD_Tab SET AD_Column_ID=586481,Updated=TO_TIMESTAMP('2023-04-11 16:26:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546903
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen(546903,de.metas.externalsystem) -> Geschäftspartnergruppe
-- Column: SAP_BPartnerImportSettings.C_BP_Group_ID
-- 2023-04-11T16:12:16.725Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586479,713843,0,546903,0,TO_TIMESTAMP('2023-04-11 19:12:16','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe',0,'de.metas.externalsystem','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.',0,'Y','Y','N','N','N','N','N','N','Geschäftspartnergruppe',0,40,0,1,1,TO_TIMESTAMP('2023-04-11 19:12:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-11T16:12:16.737Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-11T16:12:16.753Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1383)
;

-- 2023-04-11T16:12:16.791Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713843
;

-- 2023-04-11T16:12:16.797Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713843)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen(546903,de.metas.externalsystem) -> Reihenfolge
-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T16:38:21.786Z
UPDATE AD_Field SET SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-04-11 19:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713844
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> Geschäftspartner-Import-Einstellungen(546903,de.metas.externalsystem) -> Geschäftspartnergruppe
-- Column: SAP_BPartnerImportSettings.C_BP_Group_ID
-- 2023-04-11T16:54:13.039Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-04-11 19:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=713843
;

-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-11T17:14:00.524Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+100 AS DefaultValue FROM SAP_BPartnerImportSettings',Updated=TO_TIMESTAMP('2023-04-11 20:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586478
;


CREATE UNIQUE INDEX partner_code_pattern
    ON sap_bpartnerimportsettings (partnercodepattern, issinglebpartner, ad_org_id)
;

-- Element: PartnerCodePattern
-- 2023-04-11T20:43:29.700Z
UPDATE AD_Element_Trl SET Name='Partnercode-Muster', PrintName='Partnercode-Muster',Updated=TO_TIMESTAMP('2023-04-11 23:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='de_CH'
;

-- 2023-04-11T20:43:29.738Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'de_CH')
;

-- Element: PartnerCodePattern
-- 2023-04-11T20:43:37.921Z
UPDATE AD_Element_Trl SET Name='Partnercode-Muster', PrintName='Partnercode-Muster',Updated=TO_TIMESTAMP('2023-04-11 23:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='de_DE'
;

-- 2023-04-11T20:43:37.921Z
UPDATE AD_Element SET Name='Partnercode-Muster', PrintName='Partnercode-Muster' WHERE AD_Element_ID=582232
;

-- 2023-04-11T20:43:39.155Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582232,'de_DE')
;

-- 2023-04-11T20:43:39.155Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'de_DE')
;

-- Element: isSingleBPartner
-- 2023-04-11T20:45:30.079Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, werden Geschäftspartner mit einem Partnercode nach dem Regex-Muster nicht auf Grundlage ihres Codes aggregiert und es wird kein Sektionsgruppen-Geschäftspartner erstellt.',Updated=TO_TIMESTAMP('2023-04-11 23:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='de_CH'
;

-- 2023-04-11T20:45:30.098Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'de_CH')
;

-- Element: isSingleBPartner
-- 2023-04-11T20:45:36.220Z
UPDATE AD_Element_Trl SET Description='Wenn angehakt, werden Geschäftspartner mit einem Partnercode nach dem Regex-Muster nicht auf Grundlage ihres Codes aggregiert und es wird kein Sektionsgruppen-Geschäftspartner erstellt.',Updated=TO_TIMESTAMP('2023-04-11 23:45:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='de_DE'
;

-- 2023-04-11T20:45:36.220Z
UPDATE AD_Element SET Description='Wenn angehakt, werden Geschäftspartner mit einem Partnercode nach dem Regex-Muster nicht auf Grundlage ihres Codes aggregiert und es wird kein Sektionsgruppen-Geschäftspartner erstellt.' WHERE AD_Element_ID=582234
;

-- 2023-04-11T20:45:37.115Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582234,'de_DE')
;

-- 2023-04-11T20:45:37.117Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'de_DE')
;

-- Element: isSingleBPartner
-- 2023-04-11T20:45:57.699Z
UPDATE AD_Element_Trl SET Description='If ticked, business partners with a partner code according to the regex pattern will not be aggregated based on their code and no section group business partner will be created.',Updated=TO_TIMESTAMP('2023-04-11 23:45:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='en_US'
;

-- 2023-04-11T20:45:57.701Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'en_US')
;

-- Element: isSingleBPartner
-- 2023-04-11T20:46:02.355Z
UPDATE AD_Element_Trl SET Description='If ticked, business partners with a partner code according to the regex pattern will not be aggregated based on their code and no section group business partner will be created.',Updated=TO_TIMESTAMP('2023-04-11 23:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='fr_CH'
;

-- 2023-04-11T20:46:02.371Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'fr_CH')
;

-- Element: isSingleBPartner
-- 2023-04-11T20:46:13.728Z
UPDATE AD_Element_Trl SET Description='If ticked, business partners with a partner code according to the regex pattern will not be aggregated based on their code and no section group business partner will be created.',Updated=TO_TIMESTAMP('2023-04-11 23:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582234 AND AD_Language='nl_NL'
;

-- 2023-04-11T20:46:13.728Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582234,'nl_NL')
;

-- 2023-04-11T20:46:30.018Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','isSingleBPartner','CHAR(1)',null,'N')
;

-- 2023-04-11T20:46:30.125Z
UPDATE SAP_BPartnerImportSettings SET isSingleBPartner='N' WHERE isSingleBPartner IS NULL
;

-- 2023-04-11T20:46:44.892Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','PartnerCodePattern','VARCHAR(255)',null,null)
;

-- 2023-04-11T20:46:49.865Z
INSERT INTO t_alter_column values('sap_bpartnerimportsettings','PartnerCodePattern','VARCHAR(255)',null,null)
;

-- Element: null
-- 2023-04-11T20:48:58.731Z
UPDATE AD_Element_Trl SET Name='Import Geschäftspartner - Einstellungen', PrintName='Import Geschäftspartner - Einstellungen',Updated=TO_TIMESTAMP('2023-04-11 23:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582235 AND AD_Language='de_CH'
;

-- 2023-04-11T20:48:58.731Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582235,'de_CH')
;

-- Element: null
-- 2023-04-11T20:49:05.807Z
UPDATE AD_Element_Trl SET Name='Import Geschäftspartner - Einstellungen', PrintName='Import Geschäftspartner - Einstellungen',Updated=TO_TIMESTAMP('2023-04-11 23:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582235 AND AD_Language='de_DE'
;

-- 2023-04-11T20:49:05.807Z
UPDATE AD_Element SET Name='Import Geschäftspartner - Einstellungen', PrintName='Import Geschäftspartner - Einstellungen' WHERE AD_Element_ID=582235
;

-- 2023-04-11T20:49:06.839Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582235,'de_DE')
;

-- 2023-04-11T20:49:06.839Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582235,'de_DE')
;

-- Element: null
-- 2023-04-11T20:49:23.461Z
UPDATE AD_Element_Trl SET Name='Import Business Partner - Settings', PrintName='Import Business Partner - Settings',Updated=TO_TIMESTAMP('2023-04-11 23:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582235 AND AD_Language='en_US'
;

-- 2023-04-11T20:49:23.462Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582235,'en_US')
;

-- Element: null
-- 2023-04-11T20:49:30.826Z
UPDATE AD_Element_Trl SET Name='Import Business Partner - Settings', PrintName='Import Business Partner - Settings',Updated=TO_TIMESTAMP('2023-04-11 23:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582235 AND AD_Language='fr_CH'
;

-- 2023-04-11T20:49:30.826Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582235,'fr_CH')
;

-- Element: null
-- 2023-04-11T20:49:37.905Z
UPDATE AD_Element_Trl SET Name='Import Business Partner - Settings', PrintName='Import Business Partner - Settings',Updated=TO_TIMESTAMP('2023-04-11 23:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582235 AND AD_Language='nl_NL'
;

-- 2023-04-11T20:49:37.905Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582235,'nl_NL')
;

-- Element: PartnerCodePattern
-- 2023-04-11T20:50:27.740Z
UPDATE AD_Element_Trl SET Name='Partner Code Pattern',Updated=TO_TIMESTAMP('2023-04-11 23:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582232 AND AD_Language='nl_NL'
;

-- 2023-04-11T20:50:27.740Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582232,'nl_NL')
;

-- Column: SAP_BPartnerImportSettings.SeqNo
-- 2023-04-13T17:17:32.963Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM SAP_BPartnerImportSettings',Updated=TO_TIMESTAMP('2023-04-13 20:17:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586478
;