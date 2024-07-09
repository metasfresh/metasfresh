-- Run mode: SWING_CLIENT

-- 2024-01-18T12:40:44.736Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582909,0,'PostFinance_Sender_BillerId',TO_TIMESTAMP('2024-01-18 14:40:44.488','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Rechnungssteller-ID','Rechnungssteller-ID',TO_TIMESTAMP('2024-01-18 14:40:44.488','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:40:44.769Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582909 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PostFinance_Sender_BillerId
-- 2024-01-18T12:40:56.533Z
UPDATE AD_Element_Trl SET Name='Biller ID', PrintName='Biller ID',Updated=TO_TIMESTAMP('2024-01-18 14:40:56.533','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582909 AND AD_Language='en_US'
;

-- 2024-01-18T12:40:56.595Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582909,'en_US')
;

-- 2024-01-18T12:43:28.735Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582910,0,'IsUsePaperBill',TO_TIMESTAMP('2024-01-18 14:43:28.61','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Papierrechnungsservice verwenden Paper Bill','Papierrechnungsservice verwenden Paper Bill',TO_TIMESTAMP('2024-01-18 14:43:28.61','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:43:28.738Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582910 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsUsePaperBill
-- 2024-01-18T12:43:42.824Z
UPDATE AD_Element_Trl SET Name='Use Paper Bill Service', PrintName='Use Paper Bill Service',Updated=TO_TIMESTAMP('2024-01-18 14:43:42.824','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582910 AND AD_Language='en_US'
;

-- 2024-01-18T12:43:42.828Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582910,'en_US')
;

-- 2024-01-18T12:44:18.491Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582911,0,'PostFinance_Receiver_eBillId',TO_TIMESTAMP('2024-01-18 14:44:18.343','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','eBill-ID des Kunden','eBill-ID des Kunden',TO_TIMESTAMP('2024-01-18 14:44:18.343','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:44:18.494Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582911 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PostFinance_Receiver_eBillId
-- 2024-01-18T12:44:28.565Z
UPDATE AD_Element_Trl SET Name='Customer eBill ID', PrintName='Customer eBill ID',Updated=TO_TIMESTAMP('2024-01-18 14:44:28.565','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582911 AND AD_Language='en_US'
;

-- 2024-01-18T12:44:28.568Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582911,'en_US')
;

-- Run mode: SWING_CLIENT

-- Table: PostFinance_Org_Config
-- 2024-01-18T12:46:25.292Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542386,'N',TO_TIMESTAMP('2024-01-18 14:46:25.16','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'PostFinance','NP','L','PostFinance_Org_Config','DTI',TO_TIMESTAMP('2024-01-18 14:46:25.16','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:25.296Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542386 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-01-18T12:46:25.408Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556322,TO_TIMESTAMP('2024-01-18 14:46:25.327','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table PostFinance_Org_Config',1,'Y','N','Y','Y','PostFinance_Org_Config','N',1000000,TO_TIMESTAMP('2024-01-18 14:46:25.327','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:46:25.434Z
CREATE SEQUENCE POSTFINANCE_ORG_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: PostFinance_Org_Config.AD_Client_ID
-- 2024-01-18T12:46:41.016Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587816,102,0,19,542386,'AD_Client_ID',TO_TIMESTAMP('2024-01-18 14:46:40.877','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-01-18 14:46:40.877','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:41.020Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:41.029Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: PostFinance_Org_Config.AD_Org_ID
-- 2024-01-18T12:46:42.140Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587817,113,0,30,542386,'AD_Org_ID',TO_TIMESTAMP('2024-01-18 14:46:41.768','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-01-18 14:46:41.768','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:42.141Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:42.144Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: PostFinance_Org_Config.Created
-- 2024-01-18T12:46:43.024Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587818,245,0,16,542386,'Created',TO_TIMESTAMP('2024-01-18 14:46:42.767','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-01-18 14:46:42.767','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:43.026Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:43.029Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: PostFinance_Org_Config.CreatedBy
-- 2024-01-18T12:46:43.979Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587819,246,0,18,110,542386,'CreatedBy',TO_TIMESTAMP('2024-01-18 14:46:43.688','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-01-18 14:46:43.688','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:43.982Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587819 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:43.985Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: PostFinance_Org_Config.IsActive
-- 2024-01-18T12:46:45.020Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587820,348,0,20,542386,'IsActive',TO_TIMESTAMP('2024-01-18 14:46:44.672','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-01-18 14:46:44.672','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:45.022Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:45.025Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: PostFinance_Org_Config.Updated
-- 2024-01-18T12:46:46.091Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587821,607,0,16,542386,'Updated',TO_TIMESTAMP('2024-01-18 14:46:45.732','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-01-18 14:46:45.732','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:46.093Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587821 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:46.096Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: PostFinance_Org_Config.UpdatedBy
-- 2024-01-18T12:46:47.514Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587822,608,0,18,110,542386,'UpdatedBy',TO_TIMESTAMP('2024-01-18 14:46:47.013','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-01-18 14:46:47.013','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:47.517Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587822 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:47.520Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2024-01-18T12:46:48.540Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582912,0,'PostFinance_Org_Config_ID',TO_TIMESTAMP('2024-01-18 14:46:48.448','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','PostFinance','PostFinance',TO_TIMESTAMP('2024-01-18 14:46:48.448','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:46:48.543Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582912 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PostFinance_Org_Config.PostFinance_Org_Config_ID
-- 2024-01-18T12:46:49.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587823,582912,0,13,542386,'PostFinance_Org_Config_ID',TO_TIMESTAMP('2024-01-18 14:46:48.446','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','PostFinance',0,0,TO_TIMESTAMP('2024-01-18 14:46:48.446','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:46:49.707Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587823 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:46:49.711Z
/* DDL */  select update_Column_Translation_From_AD_Element(582912)
;

-- 2024-01-18T12:46:50.865Z
/* DDL */ CREATE TABLE public.PostFinance_Org_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PostFinance_Org_Config_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT PostFinance_Org_Config_Key PRIMARY KEY (PostFinance_Org_Config_ID))
;

-- 2024-01-18T12:46:50.929Z
INSERT INTO t_alter_column values('postfinance_org_config','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-01-18T12:46:50.954Z
INSERT INTO t_alter_column values('postfinance_org_config','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-01-18T12:46:50.977Z
INSERT INTO t_alter_column values('postfinance_org_config','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-01-18T12:46:50.992Z
INSERT INTO t_alter_column values('postfinance_org_config','IsActive','CHAR(1)',null,null)
;

-- 2024-01-18T12:46:51.032Z
INSERT INTO t_alter_column values('postfinance_org_config','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-01-18T12:46:51.046Z
INSERT INTO t_alter_column values('postfinance_org_config','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-01-18T12:46:51.067Z
INSERT INTO t_alter_column values('postfinance_org_config','PostFinance_Org_Config_ID','NUMERIC(10)',null,null)
;

-- Column: PostFinance_Org_Config.PostFinance_Sender_BillerId
-- 2024-01-18T12:47:54.599Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587824,582909,0,10,542386,'PostFinance_Sender_BillerId',TO_TIMESTAMP('2024-01-18 14:47:54.469','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungssteller-ID',0,0,TO_TIMESTAMP('2024-01-18 14:47:54.469','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:47:54.603Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:47:54.607Z
/* DDL */  select update_Column_Translation_From_AD_Element(582909)
;

-- 2024-01-18T12:47:57.081Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Org_Config','ALTER TABLE public.PostFinance_Org_Config ADD COLUMN PostFinance_Sender_BillerId VARCHAR(255) NOT NULL')
;

-- Column: PostFinance_Org_Config.IsUsePaperBill
-- 2024-01-18T12:48:27.811Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587825,582910,0,20,542386,'IsUsePaperBill',TO_TIMESTAMP('2024-01-18 14:48:27.679','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verwenden Sie den Papierrechnungsservice',0,0,TO_TIMESTAMP('2024-01-18 14:48:27.679','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T12:48:27.812Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587825 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T12:48:27.814Z
/* DDL */  select update_Column_Translation_From_AD_Element(582910)
;

-- 2024-01-18T12:48:28.969Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Org_Config','ALTER TABLE public.PostFinance_Org_Config ADD COLUMN IsUsePaperBill CHAR(1) DEFAULT ''N'' CHECK (IsUsePaperBill IN (''Y'',''N'')) NOT NULL')
;

-- 2024-01-18T12:51:02.819Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540783,0,542386,TO_TIMESTAMP('2024-01-18 14:51:02.683','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','idx_unique_PostFinance_Org_Config','N',TO_TIMESTAMP('2024-01-18 14:51:02.683','YYYY-MM-DD HH24:MI:SS.US'),100,'isActive=''Y''')
;

-- 2024-01-18T12:51:02.823Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540783 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-01-18T14:34:13.749Z
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2024-01-18 16:34:13.748','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540783
;

-- Run mode: SWING_CLIENT

-- 2024-01-18T12:52:14.108Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587817,541386,540783,0,TO_TIMESTAMP('2024-01-18 14:52:13.945','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',10,TO_TIMESTAMP('2024-01-18 14:52:13.945','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:52:27.701Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587820,541387,540783,0,TO_TIMESTAMP('2024-01-18 14:52:27.587','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',20,TO_TIMESTAMP('2024-01-18 14:52:27.587','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:34:30.179Z
CREATE UNIQUE INDEX idx_unique_PostFinance_Org_Config ON PostFinance_Org_Config (AD_Org_ID,IsActive) WHERE isActive='Y'
;

-- Run mode: SWING_CLIENT

-- Tab: Organisation(110,D) -> PostFinance
-- Table: PostFinance_Org_Config
-- 2024-01-18T12:54:30.526Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582912,0,547355,542386,110,'Y',TO_TIMESTAMP('2024-01-18 14:54:30.352','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','PostFinance_Org_Config','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'PostFinance','N',60,0,TO_TIMESTAMP('2024-01-18 14:54:30.352','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:54:30.531Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547355 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-01-18T12:54:30.534Z
/* DDL */  select update_tab_translation_from_ad_element(582912)
;

-- 2024-01-18T12:54:30.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547355)
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> Mandant
-- Column: PostFinance_Org_Config.AD_Client_ID
-- 2024-01-18T12:54:37.668Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587816,723822,0,547355,TO_TIMESTAMP('2024-01-18 14:54:37.49','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-01-18 14:54:37.49','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:54:37.675Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T12:54:37.689Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-01-18T12:54:38.054Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723822
;

-- 2024-01-18T12:54:38.055Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723822)
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> Organisation
-- Column: PostFinance_Org_Config.AD_Org_ID
-- 2024-01-18T12:54:38.155Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587817,723823,0,547355,TO_TIMESTAMP('2024-01-18 14:54:38.058','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-01-18 14:54:38.058','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:54:38.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T12:54:38.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-01-18T12:54:38.393Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723823
;

-- 2024-01-18T12:54:38.394Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723823)
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> Aktiv
-- Column: PostFinance_Org_Config.IsActive
-- 2024-01-18T12:54:38.496Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587820,723824,0,547355,TO_TIMESTAMP('2024-01-18 14:54:38.398','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-01-18 14:54:38.398','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:54:38.497Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T12:54:38.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-01-18T12:54:38.646Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723824
;

-- 2024-01-18T12:54:38.647Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723824)
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> PostFinance
-- Column: PostFinance_Org_Config.PostFinance_Org_Config_ID
-- 2024-01-18T12:54:38.757Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587823,723825,0,547355,TO_TIMESTAMP('2024-01-18 14:54:38.65','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','PostFinance',TO_TIMESTAMP('2024-01-18 14:54:38.65','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:54:38.758Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T12:54:38.760Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582912)
;

-- 2024-01-18T12:54:38.763Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723825
;

-- 2024-01-18T12:54:38.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723825)
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> Rechnungssteller-ID
-- Column: PostFinance_Org_Config.PostFinance_Sender_BillerId
-- 2024-01-18T12:54:38.861Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587824,723826,0,547355,TO_TIMESTAMP('2024-01-18 14:54:38.766','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Rechnungssteller-ID',TO_TIMESTAMP('2024-01-18 14:54:38.766','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:54:38.863Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T12:54:38.865Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582909)
;

-- 2024-01-18T12:54:38.871Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723826
;

-- 2024-01-18T12:54:38.872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723826)
;

-- Field: Organisation(110,D) -> PostFinance(547355,D) -> Verwenden Sie den Papierrechnungsservice
-- Column: PostFinance_Org_Config.IsUsePaperBill
-- 2024-01-18T12:54:38.977Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587825,723827,0,547355,TO_TIMESTAMP('2024-01-18 14:54:38.875','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Verwenden Sie den Papierrechnungsservice',TO_TIMESTAMP('2024-01-18 14:54:38.875','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T12:54:38.980Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T12:54:38.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582910)
;

-- 2024-01-18T12:54:39.002Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723827
;

-- 2024-01-18T12:54:39.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723827)
;

-- Tab: Organisation(110,D) -> PostFinance
-- Table: PostFinance_Org_Config
-- 2024-01-18T12:54:58.752Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2024-01-18 14:54:58.752','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547355
;

-- Column: PostFinance_Org_Config.AD_Org_ID
-- 2024-01-18T12:57:47.195Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-01-18 14:57:47.195','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587817
;

-- Tab: Organisation(110,D) -> PostFinance(547355,D)
-- UI Section: main
-- 2024-01-18T12:58:10.006Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547355,545937,TO_TIMESTAMP('2024-01-18 14:58:09.795','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-01-18 14:58:09.795','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-01-18T12:58:10.009Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545937 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Organisation(110,D) -> PostFinance(547355,D) -> main
-- UI Column: 10
-- 2024-01-18T12:58:23.532Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547239,545937,TO_TIMESTAMP('2024-01-18 14:58:23.404','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-01-18 14:58:23.404','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10
-- UI Element Group: default
-- 2024-01-18T12:58:35.798Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547239,551424,TO_TIMESTAMP('2024-01-18 14:58:35.631','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,TO_TIMESTAMP('2024-01-18 14:58:35.631','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Rechnungssteller-ID
-- Column: PostFinance_Org_Config.PostFinance_Sender_BillerId
-- 2024-01-18T13:00:14.047Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723826,0,547355,622117,551424,'F',TO_TIMESTAMP('2024-01-18 15:00:13.874','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungssteller-ID',10,0,0,TO_TIMESTAMP('2024-01-18 15:00:13.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Verwenden Sie den Papierrechnungsservice
-- Column: PostFinance_Org_Config.IsUsePaperBill
-- 2024-01-18T13:00:47.258Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723827,0,547355,622118,551424,'F',TO_TIMESTAMP('2024-01-18 15:00:47.124','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Verwenden Sie den Papierrechnungsservice',20,0,0,TO_TIMESTAMP('2024-01-18 15:00:47.124','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Mandant
-- Column: PostFinance_Org_Config.AD_Client_ID
-- 2024-01-18T13:02:09.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723822,0,547355,622119,551424,'F',TO_TIMESTAMP('2024-01-18 15:02:09.137','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2024-01-18 15:02:09.137','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Organisation
-- Column: PostFinance_Org_Config.AD_Org_ID
-- 2024-01-18T13:08:13.605Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723823,0,547355,622120,551424,'F',TO_TIMESTAMP('2024-01-18 15:08:13.483','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',40,0,0,TO_TIMESTAMP('2024-01-18 15:08:13.483','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Rechnungssteller-ID
-- Column: PostFinance_Org_Config.PostFinance_Sender_BillerId
-- 2024-01-18T13:10:14.369Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-01-18 15:10:14.369','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622117
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Verwenden Sie den Papierrechnungsservice
-- Column: PostFinance_Org_Config.IsUsePaperBill
-- 2024-01-18T13:10:14.375Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-01-18 15:10:14.375','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622118
;

-- Run mode: SWING_CLIENT

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Aktiv
-- Column: PostFinance_Org_Config.IsActive
-- 2024-01-18T14:31:03.917Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723824,0,547355,622121,551424,'F',TO_TIMESTAMP('2024-01-18 16:31:03.703','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',25,0,0,TO_TIMESTAMP('2024-01-18 16:31:03.703','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Organisation(110,D) -> PostFinance(547355,D) -> main -> 10 -> default.Aktiv
-- Column: PostFinance_Org_Config.IsActive
-- 2024-01-18T14:31:48.578Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-01-18 16:31:48.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622121
;

-- Run mode: SWING_CLIENT

-- Table: PostFinance_BPartner_Config
-- 2024-01-18T14:41:49.843Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542387,'N',TO_TIMESTAMP('2024-01-18 16:41:49.714','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'PostFinance','NP','L','PostFinance_BPartner_Config','DTI',TO_TIMESTAMP('2024-01-18 16:41:49.714','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:41:49.846Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542387 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-01-18T14:41:49.941Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556323,TO_TIMESTAMP('2024-01-18 16:41:49.856','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table PostFinance_BPartner_Config',1,'Y','N','Y','Y','PostFinance_BPartner_Config','N',1000000,TO_TIMESTAMP('2024-01-18 16:41:49.856','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:41:49.953Z
CREATE SEQUENCE POSTFINANCE_BPARTNER_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: PostFinance_BPartner_Config.AD_Client_ID
-- 2024-01-18T14:41:58.462Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587826,102,0,19,542387,'AD_Client_ID',TO_TIMESTAMP('2024-01-18 16:41:58.237','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-01-18 16:41:58.237','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:41:58.466Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587826 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:41:58.471Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: PostFinance_BPartner_Config.AD_Org_ID
-- 2024-01-18T14:41:59.596Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587827,113,0,30,542387,'AD_Org_ID',TO_TIMESTAMP('2024-01-18 16:41:59.137','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-01-18 16:41:59.137','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:41:59.598Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:41:59.600Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: PostFinance_BPartner_Config.Created
-- 2024-01-18T14:42:00.525Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587828,245,0,16,542387,'Created',TO_TIMESTAMP('2024-01-18 16:42:00.232','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-01-18 16:42:00.232','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:42:00.527Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587828 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:42:00.529Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: PostFinance_BPartner_Config.CreatedBy
-- 2024-01-18T14:42:01.425Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587829,246,0,18,110,542387,'CreatedBy',TO_TIMESTAMP('2024-01-18 16:42:01.073','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-01-18 16:42:01.073','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:42:01.427Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587829 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:42:01.431Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: PostFinance_BPartner_Config.IsActive
-- 2024-01-18T14:42:02.235Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587830,348,0,20,542387,'IsActive',TO_TIMESTAMP('2024-01-18 16:42:01.972','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-01-18 16:42:01.972','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:42:02.236Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587830 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:42:02.238Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: PostFinance_BPartner_Config.Updated
-- 2024-01-18T14:42:03.173Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587831,607,0,16,542387,'Updated',TO_TIMESTAMP('2024-01-18 16:42:02.889','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-01-18 16:42:02.889','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:42:03.175Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587831 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:42:03.178Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: PostFinance_BPartner_Config.UpdatedBy
-- 2024-01-18T14:42:04.140Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587832,608,0,18,110,542387,'UpdatedBy',TO_TIMESTAMP('2024-01-18 16:42:03.819','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-01-18 16:42:03.819','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:42:04.142Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587832 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:42:04.144Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2024-01-18T14:42:04.845Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582913,0,'PostFinance_BPartner_Config_ID',TO_TIMESTAMP('2024-01-18 16:42:04.743','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','PostFinance','PostFinance',TO_TIMESTAMP('2024-01-18 16:42:04.743','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:42:04.848Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582913 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PostFinance_BPartner_Config.PostFinance_BPartner_Config_ID
-- 2024-01-18T14:42:05.771Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587833,582913,0,13,542387,'PostFinance_BPartner_Config_ID',TO_TIMESTAMP('2024-01-18 16:42:04.742','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','PostFinance',0,0,TO_TIMESTAMP('2024-01-18 16:42:04.742','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:42:05.772Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587833 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:42:05.774Z
/* DDL */  select update_Column_Translation_From_AD_Element(582913)
;

-- 2024-01-18T14:42:06.515Z
/* DDL */ CREATE TABLE public.PostFinance_BPartner_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PostFinance_BPartner_Config_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT PostFinance_BPartner_Config_Key PRIMARY KEY (PostFinance_BPartner_Config_ID))
;

-- 2024-01-18T14:42:07.380Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-01-18T14:42:07.413Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-01-18T14:42:07.431Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-01-18T14:42:07.439Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','IsActive','CHAR(1)',null,null)
;

-- 2024-01-18T14:42:07.498Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-01-18T14:42:07.518Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-01-18T14:42:07.527Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','PostFinance_BPartner_Config_ID','NUMERIC(10)',null,null)
;

-- Column: PostFinance_BPartner_Config.PostFinance_Receiver_eBillId
-- 2024-01-18T14:43:02.672Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587834,582911,0,10,542387,'PostFinance_Receiver_eBillId',TO_TIMESTAMP('2024-01-18 16:43:02.551','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'eBill-ID des Kunden',0,0,TO_TIMESTAMP('2024-01-18 16:43:02.551','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:43:02.676Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587834 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:43:02.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(582911)
;

-- 2024-01-18T14:43:03.821Z
/* DDL */ SELECT public.db_alter_table('PostFinance_BPartner_Config','ALTER TABLE public.PostFinance_BPartner_Config ADD COLUMN PostFinance_Receiver_eBillId VARCHAR(255) NOT NULL')
;

-- Column: PostFinance_BPartner_Config.C_BPartner_ID
-- 2024-01-18T14:44:40.542Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587835,187,0,30,542387,'C_BPartner_ID',TO_TIMESTAMP('2024-01-18 16:44:40.403','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnet einen Geschäftspartner','D',0,22,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2024-01-18 16:44:40.403','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-18T14:44:40.544Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587835 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-18T14:44:40.547Z
/* DDL */  select update_Column_Translation_From_AD_Element(187)
;

-- 2024-01-18T14:44:42.803Z
/* DDL */ SELECT public.db_alter_table('PostFinance_BPartner_Config','ALTER TABLE public.PostFinance_BPartner_Config ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2024-01-18T14:44:42.823Z
ALTER TABLE PostFinance_BPartner_Config ADD CONSTRAINT CBPartner_PostFinanceBPartnerConfig FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2024-01-18T14:45:41.199Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540784,0,542387,TO_TIMESTAMP('2024-01-18 16:45:41.067','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Y','idx_unique_postfinance_bpartner_config','N',TO_TIMESTAMP('2024-01-18 16:45:41.067','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:45:41.203Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540784 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2024-01-18T14:46:28.793Z
INSERT INTO t_alter_column values('postfinance_bpartner_config','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- Run mode: SWING_CLIENT

-- 2024-01-18T14:51:23.022Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587835,541388,540784,0,TO_TIMESTAMP('2024-01-18 16:51:22.782','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',10,TO_TIMESTAMP('2024-01-18 16:51:22.782','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:51:31.158Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587830,541389,540784,0,TO_TIMESTAMP('2024-01-18 16:51:31.039','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',20,TO_TIMESTAMP('2024-01-18 16:51:31.039','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:52:14.010Z
UPDATE AD_Index_Table SET WhereClause='isActive=''Y''',Updated=TO_TIMESTAMP('2024-01-18 16:52:14.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540784
;

-- 2024-01-18T14:52:17.595Z
CREATE UNIQUE INDEX idx_unique_postfinance_bpartner_config ON PostFinance_BPartner_Config (C_BPartner_ID,IsActive) WHERE isActive='Y'
;

-- Run mode: SWING_CLIENT

-- Tab: Geschäftspartner_OLD(123,D) -> PostFinance
-- Table: PostFinance_BPartner_Config
-- 2024-01-18T14:55:11.329Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582913,0,547356,542387,123,'Y',TO_TIMESTAMP('2024-01-18 16:55:11.134','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','PostFinance_BPartner_Config','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'PostFinance','N',290,1,TO_TIMESTAMP('2024-01-18 16:55:11.134','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:55:11.342Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547356 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-01-18T14:55:11.375Z
/* DDL */  select update_tab_translation_from_ad_element(582913)
;

-- 2024-01-18T14:55:11.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547356)
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> Mandant
-- Column: PostFinance_BPartner_Config.AD_Client_ID
-- 2024-01-18T14:55:17.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587826,723828,0,547356,TO_TIMESTAMP('2024-01-18 16:55:16.848','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-01-18 16:55:16.848','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:55:17.027Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T14:55:17.031Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-01-18T14:55:17.257Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723828
;

-- 2024-01-18T14:55:17.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723828)
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> Organisation
-- Column: PostFinance_BPartner_Config.AD_Org_ID
-- 2024-01-18T14:55:17.358Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587827,723829,0,547356,TO_TIMESTAMP('2024-01-18 16:55:17.269','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-01-18 16:55:17.269','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:55:17.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T14:55:17.361Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-01-18T14:55:17.501Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723829
;

-- 2024-01-18T14:55:17.502Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723829)
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> Aktiv
-- Column: PostFinance_BPartner_Config.IsActive
-- 2024-01-18T14:55:17.586Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587830,723830,0,547356,TO_TIMESTAMP('2024-01-18 16:55:17.504','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-01-18 16:55:17.504','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:55:17.587Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T14:55:17.589Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-01-18T14:55:17.739Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723830
;

-- 2024-01-18T14:55:17.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723830)
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> PostFinance
-- Column: PostFinance_BPartner_Config.PostFinance_BPartner_Config_ID
-- 2024-01-18T14:55:17.828Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587833,723831,0,547356,TO_TIMESTAMP('2024-01-18 16:55:17.742','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','PostFinance',TO_TIMESTAMP('2024-01-18 16:55:17.742','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:55:17.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T14:55:17.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582913)
;

-- 2024-01-18T14:55:17.836Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723831
;

-- 2024-01-18T14:55:17.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723831)
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> eBill-ID des Kunden
-- Column: PostFinance_BPartner_Config.PostFinance_Receiver_eBillId
-- 2024-01-18T14:55:17.922Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587834,723832,0,547356,TO_TIMESTAMP('2024-01-18 16:55:17.839','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','eBill-ID des Kunden',TO_TIMESTAMP('2024-01-18 16:55:17.839','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:55:17.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T14:55:17.924Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582911)
;

-- 2024-01-18T14:55:17.929Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723832
;

-- 2024-01-18T14:55:17.929Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723832)
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> Geschäftspartner
-- Column: PostFinance_BPartner_Config.C_BPartner_ID
-- 2024-01-18T14:55:18.016Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587835,723833,0,547356,TO_TIMESTAMP('2024-01-18 16:55:17.932','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet einen Geschäftspartner',22,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2024-01-18 16:55:17.932','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-18T14:55:18.017Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-18T14:55:18.019Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2024-01-18T14:55:18.101Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723833
;

-- 2024-01-18T14:55:18.101Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723833)
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> eBill-ID des Kunden
-- Column: PostFinance_BPartner_Config.PostFinance_Receiver_eBillId
-- 2024-01-18T15:02:44.796Z
UPDATE AD_Field SET DisplayLogic='@AD_OrgBP_ID/0@ = 0',Updated=TO_TIMESTAMP('2024-01-18 17:02:44.796','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723832
;

-- Tab: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D)
-- UI Section: main
-- 2024-01-18T15:02:58.270Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547356,545938,TO_TIMESTAMP('2024-01-18 17:02:58.137','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-01-18 17:02:58.137','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-01-18T15:02:58.272Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545938 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main
-- UI Column: 10
-- 2024-01-18T15:03:01.373Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547240,545938,TO_TIMESTAMP('2024-01-18 17:03:01.255','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-01-18 17:03:01.255','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10
-- UI Element Group: main
-- 2024-01-18T15:03:07.350Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547240,551425,TO_TIMESTAMP('2024-01-18 17:03:07.182','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2024-01-18 17:03:07.182','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10 -> main.eBill-ID des Kunden
-- Column: PostFinance_BPartner_Config.PostFinance_Receiver_eBillId
-- 2024-01-18T15:04:19.681Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723832,0,547356,622122,551425,'F',TO_TIMESTAMP('2024-01-18 17:04:19.506','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'eBill-ID des Kunden',10,0,0,TO_TIMESTAMP('2024-01-18 17:04:19.506','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10 -> main.Aktiv
-- Column: PostFinance_BPartner_Config.IsActive
-- 2024-01-18T15:04:45.281Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723830,0,547356,622123,551425,'F',TO_TIMESTAMP('2024-01-18 17:04:45.148','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2024-01-18 17:04:45.148','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10 -> main.Mandant
-- Column: PostFinance_BPartner_Config.AD_Client_ID
-- 2024-01-18T15:06:00.240Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723828,0,547356,622124,551425,'F',TO_TIMESTAMP('2024-01-18 17:06:00.104','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2024-01-18 17:06:00.104','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10 -> main.Organisation
-- Column: PostFinance_BPartner_Config.AD_Org_ID
-- 2024-01-18T15:06:37.967Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723829,0,547356,622125,551425,'F',TO_TIMESTAMP('2024-01-18 17:06:37.801','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',40,0,0,TO_TIMESTAMP('2024-01-18 17:06:37.801','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10 -> main.eBill-ID des Kunden
-- Column: PostFinance_BPartner_Config.PostFinance_Receiver_eBillId
-- 2024-01-18T15:07:01.030Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-01-18 17:07:01.03','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622122
;

-- UI Element: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10 -> main.Aktiv
-- Column: PostFinance_BPartner_Config.IsActive
-- 2024-01-18T15:07:01.035Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-01-18 17:07:01.035','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622123
;

-- UI Element: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> main -> 10 -> main.Organisation
-- Column: PostFinance_BPartner_Config.AD_Org_ID
-- 2024-01-18T15:07:01.039Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-01-18 17:07:01.039','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622125
;

-- Tab: Geschäftspartner_OLD(123,D) -> PostFinance
-- Table: PostFinance_BPartner_Config
-- 2024-01-18T15:09:53.657Z
UPDATE AD_Tab SET SeqNo=191,Updated=TO_TIMESTAMP('2024-01-18 17:09:53.657','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547356
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> eBill-ID des Kunden
-- Column: PostFinance_BPartner_Config.PostFinance_Receiver_eBillId
-- 2024-01-18T15:15:57.676Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2024-01-18 17:15:57.676','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723832
;

-- Tab: Geschäftspartner_OLD(123,D) -> PostFinance
-- Table: PostFinance_BPartner_Config
-- 2024-01-18T15:24:30.831Z
UPDATE AD_Tab SET DisplayLogic='@AD_OrgBP_ID/0@ = 0',Updated=TO_TIMESTAMP('2024-01-18 17:24:30.831','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547356
;

-- Tab: Geschäftspartner_OLD(123,D) -> PostFinance
-- Table: PostFinance_BPartner_Config
-- 2024-01-18T16:13:00.604Z
UPDATE AD_Tab SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-01-18 18:13:00.604','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547356
;

-- Field: Geschäftspartner_OLD(123,D) -> PostFinance(547356,D) -> eBill-ID des Kunden
-- Column: PostFinance_BPartner_Config.PostFinance_Receiver_eBillId
-- 2024-01-18T16:13:06.766Z
UPDATE AD_Field SET DisplayLogic='', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2024-01-18 18:13:06.766','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723832
;

-- 2024-01-18T16:14:52.119Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541387
;

-- 2024-01-18T16:14:57.202Z
DROP INDEX IF EXISTS idx_unique_PostFinance_Org_Config
;

-- 2024-01-18T16:14:57.209Z
CREATE UNIQUE INDEX idx_unique_PostFinance_Org_Config ON PostFinance_Org_Config (AD_Org_ID) WHERE isActive='Y'
;

-- 2024-01-18T16:15:33.009Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541389
;

-- 2024-01-18T16:15:36.574Z
DROP INDEX IF EXISTS idx_unique_postfinance_bpartner_config
;

-- 2024-01-18T16:15:36.576Z
CREATE UNIQUE INDEX idx_unique_postfinance_bpartner_config ON PostFinance_BPartner_Config (C_BPartner_ID) WHERE isActive='Y'
;

-- Run mode: SWING_CLIENT

-- Value: PostFinance_BPartner_Config.ERROR_ORG_BP_POSTFINANCE_CONFIG
-- 2024-01-18T16:39:17.950Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545368,0,TO_TIMESTAMP('2024-01-18 18:39:17.702','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Die PostFinance-Konfiguration kann nicht erstellt werden, da dies für die mit dem ausgewählten Geschäftspartner verbundene Sektion nicht zulässig ist. Bitte zuerst Sektion ändern und dann erneut versuchen.','E',TO_TIMESTAMP('2024-01-18 18:39:17.702','YYYY-MM-DD HH24:MI:SS.US'),100,'PostFinance_BPartner_Config.ERROR_ORG_BP_POSTFINANCE_CONFIG')
;

-- 2024-01-18T16:39:17.962Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545368 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: PostFinance_BPartner_Config.ERROR_ORG_BP_POSTFINANCE_CONFIG
-- 2024-01-18T16:39:30.288Z
UPDATE AD_Message_Trl SET MsgText='Cannot create PostFinance configuration as this is not permitted for the organization associated with the selected business partner. Please change the organization first and then try again.',Updated=TO_TIMESTAMP('2024-01-18 18:39:30.288','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545368
;

-- Run mode: SWING_CLIENT

-- Value: C_BPartner.ERROR_ORG_BP_POSTFINANCE_CONFIG
-- 2024-01-18T18:27:11.206Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545369,0,TO_TIMESTAMP('2024-01-18 20:27:10.979','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Die Sektion kann nicht geändert werden, da der ausgewählte Geschäftspartner eine PostFinance-Konfiguration verwendet, was für die neue Sektion nicht zulässig ist. Bitte PostFinance-Konfiguration löschen und erneut versuchen.','E',TO_TIMESTAMP('2024-01-18 20:27:10.979','YYYY-MM-DD HH24:MI:SS.US'),100,'C_BPartner.ERROR_ORG_BP_POSTFINANCE_CONFIG')
;

-- 2024-01-18T18:27:11.212Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545369 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_BPartner.ERROR_ORG_BP_POSTFINANCE_CONFIG
-- 2024-01-18T18:27:24.439Z
UPDATE AD_Message_Trl SET MsgText='Cannot change organization as the selected business partner uses a PostFinance configuration, which is not allowed for the new organization. Please delete the PostFinance configuration and try again.',Updated=TO_TIMESTAMP('2024-01-18 20:27:24.439','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545369
;
