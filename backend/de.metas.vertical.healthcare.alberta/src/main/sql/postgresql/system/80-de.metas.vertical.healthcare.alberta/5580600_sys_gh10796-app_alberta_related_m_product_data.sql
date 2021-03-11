-- 2021-02-26T05:03:31.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_hierarchycommissionsettings','IsCreateShareForOwnRevenue','CHAR(1)',null,'N')
;

-- 2021-02-26T05:03:31.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_HierarchyCommissionSettings SET IsCreateShareForOwnRevenue='N' WHERE IsCreateShareForOwnRevenue IS NULL
;

-- 2021-03-10T18:00:43.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541597,'N',TO_TIMESTAMP('2021-03-10 19:00:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','N','Y','N','N','Y','N','N','N','N','N',0,'M_Product_AlbertaArticle','NP','L','M_Product_AlbertaArticle','DTI',TO_TIMESTAMP('2021-03-10 19:00:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T18:00:43.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541597 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-03-10T18:00:43.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555257,TO_TIMESTAMP('2021-03-10 19:00:43','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_AlbertaArticle',1,'Y','N','Y','Y','M_Product_AlbertaArticle','N',1000000,TO_TIMESTAMP('2021-03-10 19:00:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T18:00:43.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE M_PRODUCT_ALBERTAARTICLE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-03-10T18:01:46.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573079,102,0,19,541597,'AD_Client_ID',TO_TIMESTAMP('2021-03-10 19:01:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.vertical.healthcare.alberta',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-03-10 19:01:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:46.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573079 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:46.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-03-10T18:01:47.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573080,113,0,30,541597,'AD_Org_ID',TO_TIMESTAMP('2021-03-10 19:01:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.vertical.healthcare.alberta',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-03-10 19:01:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:47.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573080 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:47.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-03-10T18:01:47.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573081,245,0,16,541597,'Created',TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.vertical.healthcare.alberta',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:47.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573081 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:47.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-03-10T18:01:47.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573082,246,0,18,110,541597,'CreatedBy',TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.vertical.healthcare.alberta',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:47.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573082 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:47.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-03-10T18:01:47.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573083,348,0,20,541597,'IsActive',TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.vertical.healthcare.alberta',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:47.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573083 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:47.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-03-10T18:01:48.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573084,607,0,16,541597,'Updated',TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.vertical.healthcare.alberta',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-03-10 19:01:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:48.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573084 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:48.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-03-10T18:01:48.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573085,608,0,18,110,541597,'UpdatedBy',TO_TIMESTAMP('2021-03-10 19:01:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.vertical.healthcare.alberta',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-03-10 19:01:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:48.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573085 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:48.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-03-10T18:01:48.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578816,0,'M_Product_AlbertaArticle_ID',TO_TIMESTAMP('2021-03-10 19:01:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','M_Product_AlbertaArticle','M_Product_AlbertaArticle',TO_TIMESTAMP('2021-03-10 19:01:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T18:01:48.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578816 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T18:01:48.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573086,578816,0,13,541597,'M_Product_AlbertaArticle_ID',TO_TIMESTAMP('2021-03-10 19:01:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','M_Product_AlbertaArticle',0,TO_TIMESTAMP('2021-03-10 19:01:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:01:48.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573086 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:01:48.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578816) 
;

-- 2021-03-10T18:44:02.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578817,0,'AdditionalDescription',TO_TIMESTAMP('2021-03-10 19:44:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Zusatzbeschreibung','Zusatzbeschreibung',TO_TIMESTAMP('2021-03-10 19:44:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T18:44:02.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578817 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T18:44:05.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:44:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578817 AND AD_Language='de_CH'
;

-- 2021-03-10T18:44:05.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578817,'de_CH') 
;

-- 2021-03-10T18:44:07.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:44:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578817 AND AD_Language='de_DE'
;

-- 2021-03-10T18:44:07.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578817,'de_DE') 
;

-- 2021-03-10T18:44:07.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578817,'de_DE') 
;

-- 2021-03-10T18:44:21.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Additional description', PrintName='Additional description',Updated=TO_TIMESTAMP('2021-03-10 19:44:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578817 AND AD_Language='en_US'
;

-- 2021-03-10T18:44:21.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578817,'en_US') 
;

-- 2021-03-10T18:46:00.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573098,578817,0,10,541597,'AdditionalDescription',TO_TIMESTAMP('2021-03-10 19:46:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,1024,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zusatzbeschreibung',0,0,TO_TIMESTAMP('2021-03-10 19:46:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:46:00.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573098 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:46:00.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578817) 
;

-- 2021-03-10T18:47:57.726Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578818,0,'Size',TO_TIMESTAMP('2021-03-10 19:47:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Größe','Größe',TO_TIMESTAMP('2021-03-10 19:47:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T18:47:57.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578818 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T18:48:01.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:48:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578818 AND AD_Language='de_CH'
;

-- 2021-03-10T18:48:01.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578818,'de_CH') 
;

-- 2021-03-10T18:48:03.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578818 AND AD_Language='de_DE'
;

-- 2021-03-10T18:48:03.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578818,'de_DE') 
;

-- 2021-03-10T18:48:03.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578818,'de_DE') 
;

-- 2021-03-10T18:48:10.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Size', PrintName='Size',Updated=TO_TIMESTAMP('2021-03-10 19:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578818 AND AD_Language='en_US'
;

-- 2021-03-10T18:48:10.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578818,'en_US') 
;

-- 2021-03-10T18:48:25.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573099,578818,0,10,541597,'Size',TO_TIMESTAMP('2021-03-10 19:48:25','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.vertical.healthcare.alberta',0,255,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Größe',0,0,TO_TIMESTAMP('2021-03-10 19:48:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-03-10T18:48:25.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573099 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:48:25.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578818) 
;

-- 2021-03-10T18:51:57.444Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578819,0,'ArticleInventoryType',TO_TIMESTAMP('2021-03-10 19:51:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Artikeltyp','Artikeltyp',TO_TIMESTAMP('2021-03-10 19:51:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T18:51:57.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578819 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T18:52:06.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578819 AND AD_Language='de_CH'
;

-- 2021-03-10T18:52:06.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578819,'de_CH') 
;

-- 2021-03-10T18:52:10.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578819 AND AD_Language='de_DE'
;

-- 2021-03-10T18:52:10.057Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578819,'de_DE') 
;

-- 2021-03-10T18:52:10.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578819,'de_DE') 
;

-- 2021-03-10T18:52:27.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventory type', PrintName='Inventory type',Updated=TO_TIMESTAMP('2021-03-10 19:52:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578819 AND AD_Language='en_US'
;

-- 2021-03-10T18:52:27.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578819,'en_US') 
;

-- 2021-03-10T18:53:07.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541276,TO_TIMESTAMP('2021-03-10 19:53:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','N','ArticleInventoryType',TO_TIMESTAMP('2021-03-10 19:53:07','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-10T18:53:07.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541276 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-10T18:54:13.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541276,542301,TO_TIMESTAMP('2021-03-10 19:54:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Bestellartikel',TO_TIMESTAMP('2021-03-10 19:54:13','YYYY-MM-DD HH24:MI:SS'),100,'1','UnstockedArticle')
;

-- 2021-03-10T18:54:13.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542301 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T18:54:17.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:54:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542301
;

-- 2021-03-10T18:54:21.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542301
;

-- 2021-03-10T18:54:24.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:54:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542301
;

-- 2021-03-10T18:54:36.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Unstocked article',Updated=TO_TIMESTAMP('2021-03-10 19:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542301
;

-- 2021-03-10T18:55:03.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541276,542302,TO_TIMESTAMP('2021-03-10 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Lagerartikel',TO_TIMESTAMP('2021-03-10 19:55:03','YYYY-MM-DD HH24:MI:SS'),100,'2','StockedArticle')
;

-- 2021-03-10T18:55:03.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542302 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T18:55:32.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Stocker article',Updated=TO_TIMESTAMP('2021-03-10 19:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542302
;

-- 2021-03-10T18:55:36.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:55:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542302
;

-- 2021-03-10T18:55:41.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542302
;

-- 2021-03-10T18:56:10.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541276,542303,TO_TIMESTAMP('2021-03-10 19:56:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Unbekannt',TO_TIMESTAMP('2021-03-10 19:56:10','YYYY-MM-DD HH24:MI:SS'),100,'3','Unknown')
;

-- 2021-03-10T18:56:10.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542303 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T18:56:13.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:56:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542303
;

-- 2021-03-10T18:56:24.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Unknown',Updated=TO_TIMESTAMP('2021-03-10 19:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542303
;

-- 2021-03-10T18:56:26.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542303
;

-- 2021-03-10T18:57:07.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (17,'Unknown',20,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 19:57:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 19:57:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541597,'N',541276,573100,'N','Y','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','ArticleInventoryType','N','N',0,0,578819,'N','Artikeltyp',0)
;

-- 2021-03-10T18:57:07.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573100 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T18:57:07.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578819) 
;

-- 2021-03-10T18:58:14.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578820,0,'ArticleStatus',TO_TIMESTAMP('2021-03-10 19:58:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Artikelstatus','Artikelstatus',TO_TIMESTAMP('2021-03-10 19:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T18:58:14.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578820 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T18:58:17.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578820 AND AD_Language='de_CH'
;

-- 2021-03-10T18:58:17.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578820,'de_CH') 
;

-- 2021-03-10T18:58:20.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 19:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578820 AND AD_Language='de_DE'
;

-- 2021-03-10T18:58:20.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578820,'de_DE') 
;

-- 2021-03-10T18:58:20.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578820,'de_DE') 
;

-- 2021-03-10T18:58:28.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Article status', PrintName='Article status',Updated=TO_TIMESTAMP('2021-03-10 19:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578820 AND AD_Language='en_US'
;

-- 2021-03-10T18:58:28.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578820,'en_US') 
;

-- 2021-03-10T18:59:47.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541277,TO_TIMESTAMP('2021-03-10 19:59:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','N','ArticleStatus',TO_TIMESTAMP('2021-03-10 19:59:47','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-10T18:59:47.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541277 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-10T19:00:17.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541277,542304,TO_TIMESTAMP('2021-03-10 20:00:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Unbekannt',TO_TIMESTAMP('2021-03-10 20:00:17','YYYY-MM-DD HH24:MI:SS'),100,'0','Unknown')
;

-- 2021-03-10T19:00:17.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542304 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:00:32.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542304
;

-- 2021-03-10T19:00:40.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Unknown',Updated=TO_TIMESTAMP('2021-03-10 20:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542304
;

-- 2021-03-10T19:00:43.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542304
;

-- 2021-03-10T19:01:12.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541277,542305,TO_TIMESTAMP('2021-03-10 20:01:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Verfügbar',TO_TIMESTAMP('2021-03-10 20:01:12','YYYY-MM-DD HH24:MI:SS'),100,'1','Available')
;

-- 2021-03-10T19:01:12.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542305 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:01:16.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:01:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542305
;

-- 2021-03-10T19:01:24.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Available',Updated=TO_TIMESTAMP('2021-03-10 20:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542305
;

-- 2021-03-10T19:01:28.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:01:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542305
;

-- 2021-03-10T19:02:18.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541277,542306,TO_TIMESTAMP('2021-03-10 20:02:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Gesperrt',TO_TIMESTAMP('2021-03-10 20:02:18','YYYY-MM-DD HH24:MI:SS'),100,'2','Blocked')
;

-- 2021-03-10T19:02:18.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542306 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:02:23.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542306
;

-- 2021-03-10T19:02:32.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Blocked',Updated=TO_TIMESTAMP('2021-03-10 20:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542306
;

-- 2021-03-10T19:02:34.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:02:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542306
;

-- 2021-03-10T19:03:33.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541277,542307,TO_TIMESTAMP('2021-03-10 20:03:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Temporär nicht verfügbar',TO_TIMESTAMP('2021-03-10 20:03:33','YYYY-MM-DD HH24:MI:SS'),100,'3','TemporarilyUnavailable')
;

-- 2021-03-10T19:03:33.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542307 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:03:38.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:03:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542307
;

-- 2021-03-10T19:03:44.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Temporarily unavailable',Updated=TO_TIMESTAMP('2021-03-10 20:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542307
;

-- 2021-03-10T19:03:48.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542307
;

-- 2021-03-10T19:04:31.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541277,542308,TO_TIMESTAMP('2021-03-10 20:04:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Endgültig nicht verfügbar',TO_TIMESTAMP('2021-03-10 20:04:31','YYYY-MM-DD HH24:MI:SS'),100,'4','FinallyUnavailable')
;

-- 2021-03-10T19:04:31.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542308 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:04:34.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542308
;

-- 2021-03-10T19:04:43.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Finally unavailable',Updated=TO_TIMESTAMP('2021-03-10 20:04:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542308
;

-- 2021-03-10T19:04:47.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:04:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542308
;

-- 2021-03-10T19:05:19.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (17,'0',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:05:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:05:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541597,'N',541277,573101,'N','Y','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','ArticleStatus','N','N',0,0,578820,'N','Artikelstatus',0)
;

-- 2021-03-10T19:05:19.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573101 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:05:19.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578820) 
;

-- 2021-03-10T19:07:09.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578821,0,'MedicalAidPositionNumber',TO_TIMESTAMP('2021-03-10 20:07:09','YYYY-MM-DD HH24:MI:SS'),100,'Hilfsmittelpositionsnummer','de.metas.vertical.healthcare.alberta','Y','HiMiPo','HiMiPo',TO_TIMESTAMP('2021-03-10 20:07:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:07:09.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578821 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:07:13.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578821 AND AD_Language='de_CH'
;

-- 2021-03-10T19:07:13.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578821,'de_CH') 
;

-- 2021-03-10T19:07:17.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578821 AND AD_Language='de_DE'
;

-- 2021-03-10T19:07:17.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578821,'de_DE') 
;

-- 2021-03-10T19:07:17.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578821,'de_DE') 
;

-- 2021-03-10T19:07:42.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Medical aid position number', PrintName='Medical aid position number',Updated=TO_TIMESTAMP('2021-03-10 20:07:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578821 AND AD_Language='en_US'
;

-- 2021-03-10T19:07:42.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578821,'en_US') 
;

-- 2021-03-10T19:08:09.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (10,125,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:08:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:08:09','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541597,'N',573102,'N','N','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','MedicalAidPositionNumber','N','Hilfsmittelpositionsnummer','N',0,0,578821,'N','HiMiPo',0)
;

-- 2021-03-10T19:08:09.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573102 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:08:09.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578821) 
;

-- 2021-03-10T19:10:38.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578822,0,'ArticleStars',TO_TIMESTAMP('2021-03-10 20:10:38','YYYY-MM-DD HH24:MI:SS'),100,'Sterneranking (0-5) des einzelnen Artikels im Warenkorb, falls keine Wirtschaftlichkeitsberechnung gewünscht','de.metas.vertical.healthcare.alberta','Y','Sterne','Sterne',TO_TIMESTAMP('2021-03-10 20:10:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:10:38.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578822 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:10:41.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:10:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578822 AND AD_Language='de_CH'
;

-- 2021-03-10T19:10:41.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578822,'de_CH') 
;

-- 2021-03-10T19:10:43.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578822 AND AD_Language='de_DE'
;

-- 2021-03-10T19:10:43.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578822,'de_DE') 
;

-- 2021-03-10T19:10:43.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578822,'de_DE') 
;

-- 2021-03-10T19:10:58.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='(0-5)', IsTranslated='Y', Name='Stars', PrintName='Stars',Updated=TO_TIMESTAMP('2021-03-10 20:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578822 AND AD_Language='en_US'
;

-- 2021-03-10T19:10:58.395Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578822,'en_US') 
;

-- 2021-03-10T19:11:26.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,ValueMax,IsUpdateable,ValueMin,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (22,'0',14,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:11:26','YYYY-MM-DD HH24:MI:SS'),100,'5','Y','0','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:11:26','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541597,'N',573103,'N','Y','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','ArticleStars','N','Sterneranking (0-5) des einzelnen Artikels im Warenkorb, falls keine Wirtschaftlichkeitsberechnung gewünscht','N',0,0,578822,'N','Sterne',0)
;

-- 2021-03-10T19:11:26.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573103 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:11:26.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578822) 
;

-- 2021-03-10T19:13:05.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578823,0,'AssortmentType',TO_TIMESTAMP('2021-03-10 20:13:05','YYYY-MM-DD HH24:MI:SS'),100,'Beeinflusst die Darstellung im Alberta-Warenkorb','de.metas.vertical.healthcare.alberta','Y','Sortimentszuordnung','Sortimentszuordnung',TO_TIMESTAMP('2021-03-10 20:13:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:13:05.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578823 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:13:10.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:13:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578823 AND AD_Language='de_CH'
;

-- 2021-03-10T19:13:10.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578823,'de_CH') 
;

-- 2021-03-10T19:13:13.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:13:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578823 AND AD_Language='de_DE'
;

-- 2021-03-10T19:13:13.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578823,'de_DE') 
;

-- 2021-03-10T19:13:13.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578823,'de_DE') 
;

-- 2021-03-10T19:13:33.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='Assortment type', PrintName='Assortment type',Updated=TO_TIMESTAMP('2021-03-10 20:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578823 AND AD_Language='en_US'
;

-- 2021-03-10T19:13:33.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578823,'en_US') 
;

-- 2021-03-10T19:14:10.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541278,TO_TIMESTAMP('2021-03-10 20:14:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','N','AssortmentType',TO_TIMESTAMP('2021-03-10 20:14:10','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-10T19:14:10.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541278 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-10T19:14:44.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541278,542309,TO_TIMESTAMP('2021-03-10 20:14:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Unbekannt',TO_TIMESTAMP('2021-03-10 20:14:44','YYYY-MM-DD HH24:MI:SS'),100,'0','Unknown')
;

-- 2021-03-10T19:14:44.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542309 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:14:47.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542309
;

-- 2021-03-10T19:14:56.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Unknown',Updated=TO_TIMESTAMP('2021-03-10 20:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542309
;

-- 2021-03-10T19:14:59.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:14:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542309
;

-- 2021-03-10T19:15:42.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541278,542310,TO_TIMESTAMP('2021-03-10 20:15:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Fokusartikel',TO_TIMESTAMP('2021-03-10 20:15:42','YYYY-MM-DD HH24:MI:SS'),100,'1','FocusArticle')
;

-- 2021-03-10T19:15:42.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542310 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:15:48.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542310
;

-- 2021-03-10T19:15:55.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Focus article',Updated=TO_TIMESTAMP('2021-03-10 20:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542310
;

-- 2021-03-10T19:15:58.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542310
;

-- 2021-03-10T19:16:38.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541278,542311,TO_TIMESTAMP('2021-03-10 20:16:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Standardartikel',TO_TIMESTAMP('2021-03-10 20:16:38','YYYY-MM-DD HH24:MI:SS'),100,'2','StandardArticle')
;

-- 2021-03-10T19:16:38.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542311 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:16:41.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542311
;

-- 2021-03-10T19:16:48.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Standard article',Updated=TO_TIMESTAMP('2021-03-10 20:16:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542311
;

-- 2021-03-10T19:16:51.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:16:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542311
;

-- 2021-03-10T19:17:18.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (17,'0',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:17:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:17:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541597,'N',541278,573104,'N','Y','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','AssortmentType','N','Beeinflusst die Darstellung im Alberta-Warenkorb','N',0,0,578823,'N','Sortimentszuordnung',0)
;

-- 2021-03-10T19:17:19.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573104 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:17:19.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578823) 
;

-- 2021-03-10T19:19:34.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578824,0,'PurchaseRating',TO_TIMESTAMP('2021-03-10 20:19:34','YYYY-MM-DD HH24:MI:SS'),100,'Wirtschaftlichkeitsberechung - Werte von A bis F (A = sehr wirtschaftlich F = nicht wirtschaftlich)','de.metas.vertical.healthcare.alberta','Y','Preisrating','Preisrating',TO_TIMESTAMP('2021-03-10 20:19:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:19:34.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578824 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:19:40.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578824 AND AD_Language='de_CH'
;

-- 2021-03-10T19:19:40.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578824,'de_CH') 
;

-- 2021-03-10T19:19:42.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:19:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578824 AND AD_Language='de_DE'
;

-- 2021-03-10T19:19:42.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578824,'de_DE') 
;

-- 2021-03-10T19:19:42.751Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578824,'de_DE') 
;

-- 2021-03-10T19:20:06.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='(A - F)', IsTranslated='Y', Name='Purchase rating', PrintName='Purchase rating',Updated=TO_TIMESTAMP('2021-03-10 20:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578824 AND AD_Language='en_US'
;

-- 2021-03-10T19:20:06.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578824,'en_US') 
;

-- 2021-03-10T19:20:47.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541279,TO_TIMESTAMP('2021-03-10 20:20:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','N','PurchaseRating',TO_TIMESTAMP('2021-03-10 20:20:46','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-10T19:20:47.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541279 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-10T19:21:03.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541279,542312,TO_TIMESTAMP('2021-03-10 20:21:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','A',TO_TIMESTAMP('2021-03-10 20:21:03','YYYY-MM-DD HH24:MI:SS'),100,'A','A')
;

-- 2021-03-10T19:21:03.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542312 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:21:06.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542312
;

-- 2021-03-10T19:21:09.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542312
;

-- 2021-03-10T19:21:12.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542312
;

-- 2021-03-10T19:21:24.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541279,542313,TO_TIMESTAMP('2021-03-10 20:21:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','B',TO_TIMESTAMP('2021-03-10 20:21:24','YYYY-MM-DD HH24:MI:SS'),100,'B','B')
;

-- 2021-03-10T19:21:24.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542313 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:21:26.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542313
;

-- 2021-03-10T19:21:29.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542313
;

-- 2021-03-10T19:21:31.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542313
;

-- 2021-03-10T19:21:47.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541279,542314,TO_TIMESTAMP('2021-03-10 20:21:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','C',TO_TIMESTAMP('2021-03-10 20:21:46','YYYY-MM-DD HH24:MI:SS'),100,'C','C')
;

-- 2021-03-10T19:21:47.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542314 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:21:49.866Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542314
;

-- 2021-03-10T19:21:54.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542314
;

-- 2021-03-10T19:21:55.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542314
;

-- 2021-03-10T19:22:09.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541279,542315,TO_TIMESTAMP('2021-03-10 20:22:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','D',TO_TIMESTAMP('2021-03-10 20:22:09','YYYY-MM-DD HH24:MI:SS'),100,'D','D')
;

-- 2021-03-10T19:22:09.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542315 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:22:12.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542315
;

-- 2021-03-10T19:22:15.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:22:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542315
;

-- 2021-03-10T19:22:19.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:22:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542315
;

-- 2021-03-10T19:22:35.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541279,542316,TO_TIMESTAMP('2021-03-10 20:22:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','E',TO_TIMESTAMP('2021-03-10 20:22:35','YYYY-MM-DD HH24:MI:SS'),100,'E','E')
;

-- 2021-03-10T19:22:35.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542316 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:22:38.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542316
;

-- 2021-03-10T19:22:41.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542316
;

-- 2021-03-10T19:22:45.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542316
;

-- 2021-03-10T19:23:01.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541279,542317,TO_TIMESTAMP('2021-03-10 20:23:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','F',TO_TIMESTAMP('2021-03-10 20:23:00','YYYY-MM-DD HH24:MI:SS'),100,'F','F')
;

-- 2021-03-10T19:23:01.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542317 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:23:03.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542317
;

-- 2021-03-10T19:23:05.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542317
;

-- 2021-03-10T19:23:09.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542317
;

-- 2021-03-10T19:23:39.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (17,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:23:39','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541597,'N',541279,573105,'N','N','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','PurchaseRating','N','Wirtschaftlichkeitsberechung - Werte von A bis F (A = sehr wirtschaftlich F = nicht wirtschaftlich)','N',0,0,578824,'N','Preisrating',0)
;

-- 2021-03-10T19:23:39.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573105 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:23:39.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578824) 
;

-- 2021-03-10T19:25:30.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Product_AlbertaArticle (AD_Client_ID NUMERIC(10) NOT NULL, AdditionalDescription VARCHAR(1024), AD_Org_ID NUMERIC(10) NOT NULL, ArticleInventoryType VARCHAR(20) DEFAULT 'Unknown' NOT NULL, ArticleStars NUMERIC DEFAULT 0 NOT NULL, ArticleStatus CHAR(1) DEFAULT '0' NOT NULL, AssortmentType CHAR(1) DEFAULT '0' NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MedicalAidPositionNumber VARCHAR(125), M_Product_AlbertaArticle_ID NUMERIC(10) NOT NULL, PurchaseRating CHAR(1), Size VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Product_AlbertaArticle_Key PRIMARY KEY (M_Product_AlbertaArticle_ID))
;

-- 2021-03-10T19:26:55.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('6',0,0,0,541598,'N',TO_TIMESTAMP('2021-03-10 20:26:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','N','Y','N','Y','Y','N','N','N','N','N',0,'M_Product_AlbertaPackagingUnit','NP','L','M_Product_AlbertaPackagingUnit','DTI',TO_TIMESTAMP('2021-03-10 20:26:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:26:55.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541598 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-03-10T19:26:55.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555258,TO_TIMESTAMP('2021-03-10 20:26:55','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_AlbertaPackagingUnit',1,'Y','N','Y','Y','M_Product_AlbertaPackagingUnit','N',1000000,TO_TIMESTAMP('2021-03-10 20:26:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:26:55.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE M_PRODUCT_ALBERTAPACKAGINGUNIT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-03-10T19:27:00.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2021-03-10 20:27:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541597
;

-- 2021-03-10T19:27:07.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','Y','N',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,541598,'N','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',573106,'Y','de.metas.vertical.healthcare.alberta','AD_Client_ID','N','Mandant für diese Installation.',102,'Mandant',0)
;

-- 2021-03-10T19:27:07.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573106 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:07.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-03-10T19:27:07.470Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','Y','N','Y','N',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,541598,'N','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',573107,'Y','de.metas.vertical.healthcare.alberta','AD_Org_ID','N','Organisatorische Einheit des Mandanten',113,'Sektion',0)
;

-- 2021-03-10T19:27:07.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573107 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:07.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-03-10T19:27:07.649Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,541598,'N','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',573108,'Y','de.metas.vertical.healthcare.alberta','Created','N','Datum, an dem dieser Eintrag erstellt wurde',245,'Erstellt',0)
;

-- 2021-03-10T19:27:07.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573108 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:07.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-03-10T19:27:07.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,541598,'N','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,573109,'Y','de.metas.vertical.healthcare.alberta','CreatedBy','N','Nutzer, der diesen Eintrag erstellt hat',246,'Erstellt durch',0)
;

-- 2021-03-10T19:27:07.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573109 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:07.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-03-10T19:27:08.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',TO_TIMESTAMP('2021-03-10 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,541598,'N','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',573110,'Y','de.metas.vertical.healthcare.alberta','IsActive','N','Der Eintrag ist im System aktiv',348,'Aktiv',0)
;

-- 2021-03-10T19:27:08.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573110 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:08.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-03-10T19:27:08.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100,541598,'N','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',573111,'Y','de.metas.vertical.healthcare.alberta','Updated','N','Datum, an dem dieser Eintrag aktualisiert wurde',607,'Aktualisiert',0)
;

-- 2021-03-10T19:27:08.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573111 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:08.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-03-10T19:27:08.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100,541598,'N','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,573112,'Y','de.metas.vertical.healthcare.alberta','UpdatedBy','N','Nutzer, der diesen Eintrag aktualisiert hat',608,'Aktualisiert durch',0)
;

-- 2021-03-10T19:27:08.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573112 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:08.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-03-10T19:27:08.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578825,0,'M_Product_AlbertaPackagingUnit_ID',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','M_Product_AlbertaPackagingUnit','M_Product_AlbertaPackagingUnit',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:27:08.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578825 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:27:08.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,AD_Element_ID,Name,AD_Org_ID) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N',TO_TIMESTAMP('2021-03-10 20:27:08','YYYY-MM-DD HH24:MI:SS'),100,541598,'N',573113,'Y','de.metas.vertical.healthcare.alberta','M_Product_AlbertaPackagingUnit_ID','N',578825,'M_Product_AlbertaPackagingUnit',0)
;

-- 2021-03-10T19:27:08.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573113 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:27:08.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578825) 
;

-- 2021-03-10T19:28:45.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578826,0,'ArticleUnit',TO_TIMESTAMP('2021-03-10 20:28:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Verpackungseinheit','Verpackungseinheit',TO_TIMESTAMP('2021-03-10 20:28:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:28:45.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578826 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:28:48.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578826 AND AD_Language='de_CH'
;

-- 2021-03-10T19:28:48.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578826,'de_CH') 
;

-- 2021-03-10T19:28:50.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:28:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578826 AND AD_Language='de_DE'
;

-- 2021-03-10T19:28:50.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578826,'de_DE') 
;

-- 2021-03-10T19:28:50.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578826,'de_DE') 
;

-- 2021-03-10T19:29:10.249Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Unit', PrintName='Unit',Updated=TO_TIMESTAMP('2021-03-10 20:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578826 AND AD_Language='en_US'
;

-- 2021-03-10T19:29:10.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578826,'en_US') 
;

-- 2021-03-10T19:29:56.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541280,TO_TIMESTAMP('2021-03-10 20:29:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','N','ArticleUnit',TO_TIMESTAMP('2021-03-10 20:29:56','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-10T19:29:56.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541280 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-10T19:30:49.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541280,542318,TO_TIMESTAMP('2021-03-10 20:30:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Stück',TO_TIMESTAMP('2021-03-10 20:30:49','YYYY-MM-DD HH24:MI:SS'),100,'PCE','PCE')
;

-- 2021-03-10T19:30:49.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542318 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:30:53.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542318
;

-- 2021-03-10T19:31:03.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Piece',Updated=TO_TIMESTAMP('2021-03-10 20:31:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542318
;

-- 2021-03-10T19:31:05.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542318
;

-- 2021-03-10T19:31:45.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541280,542319,TO_TIMESTAMP('2021-03-10 20:31:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Karton',TO_TIMESTAMP('2021-03-10 20:31:45','YYYY-MM-DD HH24:MI:SS'),100,'Ktn','BOX')
;

-- 2021-03-10T19:31:45.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542319 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T19:31:53.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='Stk',Updated=TO_TIMESTAMP('2021-03-10 20:31:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542318
;

-- 2021-03-10T19:32:01.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:32:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542319
;

-- 2021-03-10T19:32:06.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Box',Updated=TO_TIMESTAMP('2021-03-10 20:32:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542319
;

-- 2021-03-10T19:32:11.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:32:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542319
;

-- 2021-03-10T19:32:56.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (17,3,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:32:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:32:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541598,'N',541280,573114,'N','Y','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','ArticleUnit','N','N',0,0,578826,'N','Verpackungseinheit',0)
;

-- 2021-03-10T19:32:56.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573114 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:32:56.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578826) 
;

-- 2021-03-10T19:34:31.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578827,0,'ArticleQuantity',TO_TIMESTAMP('2021-03-10 20:34:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Inhalt in Stück','Inhalt in Stück',TO_TIMESTAMP('2021-03-10 20:34:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:34:31.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578827 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:34:34.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578827 AND AD_Language='de_CH'
;

-- 2021-03-10T19:34:34.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578827,'de_CH') 
;

-- 2021-03-10T19:34:37.604Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:34:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578827 AND AD_Language='de_DE'
;

-- 2021-03-10T19:34:37.605Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578827,'de_DE') 
;

-- 2021-03-10T19:34:37.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578827,'de_DE') 
;

-- 2021-03-10T19:34:45.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Content', PrintName='Content',Updated=TO_TIMESTAMP('2021-03-10 20:34:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578827 AND AD_Language='en_US'
;

-- 2021-03-10T19:34:45.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578827,'en_US') 
;

-- 2021-03-10T19:35:51.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (29,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:35:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:35:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541598,'N','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.',573115,'N','N','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','Qty','N','Menge','N',0,0,526,'N','Menge',0)
;

-- 2021-03-10T19:35:51.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573115 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:35:51.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- 2021-03-10T19:35:52.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Product_AlbertaPackagingUnit (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, ArticleUnit VARCHAR(3) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_AlbertaPackagingUnit_ID NUMERIC(10) NOT NULL, Qty NUMERIC, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Product_AlbertaPackagingUnit_Key PRIMARY KEY (M_Product_AlbertaPackagingUnit_ID))
;

-- 2021-03-10T19:36:23.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (19,10,0,'N','Y','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:36:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:36:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541598,'N','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',573116,'N','N','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','M_Product_ID','N','Produkt, Leistung, Artikel','N',0,0,454,'N','Produkt',0)
;

-- 2021-03-10T19:36:23.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573116 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:36:23.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2021-03-10T19:36:24.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product_AlbertaPackagingUnit','ALTER TABLE public.M_Product_AlbertaPackagingUnit ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2021-03-10T19:36:24.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_AlbertaPackagingUnit ADD CONSTRAINT MProduct_MProductAlbertaPackagingUnit FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2021-03-10T19:36:44.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-03-10 20:36:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573115
;

-- 2021-03-10T19:36:52.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2021-03-10 20:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573114
;

-- 2021-03-10T19:37:24.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (19,10,0,'N','Y','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:37:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:37:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541597,'N','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',573117,'N','N','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','M_Product_ID','N','Produkt, Leistung, Artikel','N',0,0,454,'N','Produkt',0)
;

-- 2021-03-10T19:37:24.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573117 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:37:24.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2021-03-10T19:37:50.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-03-10 20:37:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573100
;

-- 2021-03-10T19:40:27.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541281,TO_TIMESTAMP('2021-03-10 20:40:27','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','N','C_BPartner Manufacturer',TO_TIMESTAMP('2021-03-10 20:40:27','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-03-10T19:40:27.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541281 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-10T19:41:07.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2893,0,541281,291,TO_TIMESTAMP('2021-03-10 20:41:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-03-10 20:41:07','YYYY-MM-DD HH24:MI:SS'),100,'IsManufacturer=''Y''')
;

-- 2021-03-10T19:41:20.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541281,Updated=TO_TIMESTAMP('2021-03-10 20:41:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558031
;

-- 2021-03-10T19:42:58.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578828,0,'ManufacturerArticleNumber',TO_TIMESTAMP('2021-03-10 20:42:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hersteller-Nr.','Hersteller-Nr.',TO_TIMESTAMP('2021-03-10 20:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:42:58.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578828 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:43:11.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:43:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578828 AND AD_Language='de_CH'
;

-- 2021-03-10T19:43:11.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578828,'de_CH') 
;

-- 2021-03-10T19:43:14.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 20:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578828 AND AD_Language='de_DE'
;

-- 2021-03-10T19:43:14.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578828,'de_DE') 
;

-- 2021-03-10T19:43:14.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578828,'de_DE') 
;

-- 2021-03-10T19:43:26.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Manufacturer number', PrintName='Manufacturer number',Updated=TO_TIMESTAMP('2021-03-10 20:43:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578828 AND AD_Language='en_US'
;

-- 2021-03-10T19:43:26.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578828,'en_US') 
;

-- 2021-03-10T19:43:48.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (10,125,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:43:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:43:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N',208,'N',573119,'N','N','N','N','N','N','N',0,'D','N','N','ManufacturerArticleNumber','N','N',0,0,578828,'N','Hersteller-Nr.',0)
;

-- 2021-03-10T19:43:48.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573119 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:43:48.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578828) 
;

-- 2021-03-10T19:44:13.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2021-03-10 20:44:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541597
;

-- 2021-03-10T19:44:19.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2021-03-10 20:44:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541598
;

-- 2021-03-10T19:45:10.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (180,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:45:09','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-03-10 20:45:09','YYYY-MM-DD HH24:MI:SS'),100,635142,'Y',460,490,1,1,573119,'D','Hersteller-Nr.',0)
;

-- 2021-03-10T19:45:10.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:45:10.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578828) 
;

-- 2021-03-10T19:45:10.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635142
;

-- 2021-03-10T19:45:10.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635142)
;

-- 2021-03-10T19:46:46.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Manufacturer_ID',Updated=TO_TIMESTAMP('2021-03-10 20:46:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549596
;

-- 2021-03-10T19:47:06.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635142,0,180,1000040,579299,'F',TO_TIMESTAMP('2021-03-10 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ManufacturerArticleNumber',135,0,0,TO_TIMESTAMP('2021-03-10 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:51:37.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541599,'N',TO_TIMESTAMP('2021-03-10 20:51:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','N','Y','N','Y','Y','N','N','N','N','N',0,'M_Product_AlbertaTherapy','NP','L','M_Product_AlbertaTherapy','DTI',TO_TIMESTAMP('2021-03-10 20:51:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:51:37.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541599 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-03-10T19:51:37.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555259,TO_TIMESTAMP('2021-03-10 20:51:37','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_AlbertaTherapy',1,'Y','N','Y','Y','M_Product_AlbertaTherapy','N',1000000,TO_TIMESTAMP('2021-03-10 20:51:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:51:37.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE M_PRODUCT_ALBERTATHERAPY_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-03-10T19:51:53.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2021-03-10 20:51:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541599
;

-- 2021-03-10T19:52:02.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','Y','N',TO_TIMESTAMP('2021-03-10 20:52:01','YYYY-MM-DD HH24:MI:SS'),100,541599,'N','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',573120,'Y','de.metas.vertical.healthcare.alberta','AD_Client_ID','N','Mandant für diese Installation.',102,'Mandant',0)
;

-- 2021-03-10T19:52:02.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573120 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:02.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-03-10T19:52:02.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','Y','N','Y','N',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,541599,'N','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',573121,'Y','de.metas.vertical.healthcare.alberta','AD_Org_ID','N','Organisatorische Einheit des Mandanten',113,'Sektion',0)
;

-- 2021-03-10T19:52:02.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573121 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:02.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-03-10T19:52:02.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,541599,'N','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',573122,'Y','de.metas.vertical.healthcare.alberta','Created','N','Datum, an dem dieser Eintrag erstellt wurde',245,'Erstellt',0)
;

-- 2021-03-10T19:52:02.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573122 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:02.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-03-10T19:52:02.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,541599,'N','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,573123,'Y','de.metas.vertical.healthcare.alberta','CreatedBy','N','Nutzer, der diesen Eintrag erstellt hat',246,'Erstellt durch',0)
;

-- 2021-03-10T19:52:02.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573123 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:02.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-03-10T19:52:02.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,541599,'N','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',573124,'Y','de.metas.vertical.healthcare.alberta','IsActive','N','Der Eintrag ist im System aktiv',348,'Aktiv',0)
;

-- 2021-03-10T19:52:02.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573124 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:02.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-03-10T19:52:02.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:52:02','YYYY-MM-DD HH24:MI:SS'),100,541599,'N','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',573125,'Y','de.metas.vertical.healthcare.alberta','Updated','N','Datum, an dem dieser Eintrag aktualisiert wurde',607,'Aktualisiert',0)
;

-- 2021-03-10T19:52:02.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:02.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-03-10T19:52:03.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-10 20:52:03','YYYY-MM-DD HH24:MI:SS'),100,541599,'N','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,573126,'Y','de.metas.vertical.healthcare.alberta','UpdatedBy','N','Nutzer, der diesen Eintrag aktualisiert hat',608,'Aktualisiert durch',0)
;

-- 2021-03-10T19:52:03.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573126 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:03.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-03-10T19:52:03.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578829,0,'M_Product_AlbertaTherapy_ID',TO_TIMESTAMP('2021-03-10 20:52:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','M_Product_AlbertaTherapy','M_Product_AlbertaTherapy',TO_TIMESTAMP('2021-03-10 20:52:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:52:03.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578829 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:52:03.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,AD_Element_ID,Name,AD_Org_ID) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N',TO_TIMESTAMP('2021-03-10 20:52:03','YYYY-MM-DD HH24:MI:SS'),100,541599,'N',573127,'Y','de.metas.vertical.healthcare.alberta','M_Product_AlbertaTherapy_ID','N',578829,'M_Product_AlbertaTherapy',0)
;

-- 2021-03-10T19:52:03.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573127 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:03.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578829) 
;

-- 2021-03-10T19:52:58.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (19,10,0,'N','Y','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:52:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:52:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541599,'N','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',573128,'N','N','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','M_Product_ID','N','Produkt, Leistung, Artikel','N',0,0,454,'N','Produkt',0)
;

-- 2021-03-10T19:52:58.617Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573128 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:52:58.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2021-03-10T19:54:25.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578830,0,'Therapy',TO_TIMESTAMP('2021-03-10 20:54:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Therapie','Therapy',TO_TIMESTAMP('2021-03-10 20:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T19:54:25.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578830 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-10T19:54:33.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Therapie',Updated=TO_TIMESTAMP('2021-03-10 20:54:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578830 AND AD_Language='de_CH'
;

-- 2021-03-10T19:54:33.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578830,'de_CH') 
;

-- 2021-03-10T19:54:38.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Therapie',Updated=TO_TIMESTAMP('2021-03-10 20:54:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578830 AND AD_Language='de_DE'
;

-- 2021-03-10T19:54:38.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578830,'de_DE') 
;

-- 2021-03-10T19:54:38.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578830,'de_DE') 
;

-- 2021-03-10T19:54:38.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Therapie', Name='Therapie' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578830)
;

-- 2021-03-10T19:54:43.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Therapy',Updated=TO_TIMESTAMP('2021-03-10 20:54:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578830 AND AD_Language='en_US'
;

-- 2021-03-10T19:54:43.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578830,'en_US') 
;

-- 2021-03-10T19:55:07.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:55:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:55:07','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541599,'N',573129,'N','N','N','N','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','Therapy','N','N',0,0,578830,'N','Therapie',0)
;

-- 2021-03-10T19:55:07.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573129 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:55:07.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578830) 
;

-- 2021-03-10T19:55:11.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-03-10 20:55:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573129
;

-- 2021-03-10T19:55:47.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID,IsShowFilterInline,Name,AD_Org_ID) VALUES (10,2,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-10 20:55:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-10 20:55:47','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541599,'Y','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',573130,'N','N','N','Y','N','N','N',0,'de.metas.vertical.healthcare.alberta','N','N','Value','N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','N',0,0,620,'N','Suchschlüssel',0)
;

-- 2021-03-10T19:55:47.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573130 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-10T19:55:47.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- 2021-03-10T19:56:18.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Product_AlbertaTherapy (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_AlbertaTherapy_ID NUMERIC(10) NOT NULL, M_Product_ID NUMERIC(10), Therapy VARCHAR(50) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(2), CONSTRAINT M_Product_AlbertaTherapy_Key PRIMARY KEY (M_Product_AlbertaTherapy_ID), CONSTRAINT MProduct_MProductAlbertaTherapy FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-03-10T19:58:31.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,EntityType,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Element_ID,Processing,IsAdvancedTab,Name,AD_Org_ID) VALUES (TO_TIMESTAMP('2021-03-10 20:58:31','YYYY-MM-DD HH24:MI:SS'),'N',140,200,'Y',0,TO_TIMESTAMP('2021-03-10 20:58:31','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N',0,'Y','N','Y','Y','Y','N',541597,543534,'N','Y',0,'de.metas.vertical.healthcare.alberta','M_Product_AlbertaArticle','Y','N',578816,'N','N','M_Product_AlbertaArticle',0)
;

-- 2021-03-10T19:58:31.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543534 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-03-10T19:58:31.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(578816) 
;

-- 2021-03-10T19:58:31.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543534)
;

-- 2021-03-10T19:58:37.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-03-10 20:58:37','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',635143,573079,'de.metas.vertical.healthcare.alberta','Mandant','Mandant für diese Installation.',0)
;

-- 2021-03-10T19:58:37.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:37.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-03-10T19:58:38.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635143
;

-- 2021-03-10T19:58:38.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635143)
;

-- 2021-03-10T19:58:38.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:38','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:38','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',635144,573080,'de.metas.vertical.healthcare.alberta','Sektion','Organisatorische Einheit des Mandanten',0)
;

-- 2021-03-10T19:58:38.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:38.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-03-10T19:58:38.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635144
;

-- 2021-03-10T19:58:38.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635144)
;

-- 2021-03-10T19:58:38.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:38','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:38','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',635145,573083,'de.metas.vertical.healthcare.alberta','Aktiv','Der Eintrag ist im System aktiv',0)
;

-- 2021-03-10T19:58:38.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:38.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-03-10T19:58:39.055Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635145
;

-- 2021-03-10T19:58:39.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635145)
;

-- 2021-03-10T19:58:39.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543534,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635146,'N',573086,'de.metas.vertical.healthcare.alberta','M_Product_AlbertaArticle',0)
;

-- 2021-03-10T19:58:39.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635146 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578816) 
;

-- 2021-03-10T19:58:39.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635146
;

-- 2021-03-10T19:58:39.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635146)
;

-- 2021-03-10T19:58:39.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543534,'Y',1024,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635147,573098,'de.metas.vertical.healthcare.alberta','Zusatzbeschreibung',0)
;

-- 2021-03-10T19:58:39.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635147 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578817) 
;

-- 2021-03-10T19:58:39.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635147
;

-- 2021-03-10T19:58:39.245Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635147)
;

-- 2021-03-10T19:58:39.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543534,'Y',255,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635148,573099,'de.metas.vertical.healthcare.alberta','Größe',0)
;

-- 2021-03-10T19:58:39.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578818) 
;

-- 2021-03-10T19:58:39.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635148
;

-- 2021-03-10T19:58:39.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635148)
;

-- 2021-03-10T19:58:39.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543534,'Y',20,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635149,573100,'de.metas.vertical.healthcare.alberta','Artikeltyp',0)
;

-- 2021-03-10T19:58:39.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578819) 
;

-- 2021-03-10T19:58:39.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635149
;

-- 2021-03-10T19:58:39.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635149)
;

-- 2021-03-10T19:58:39.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,AD_Org_ID) VALUES (543534,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635150,573101,'de.metas.vertical.healthcare.alberta','Artikelstatus',0)
;

-- 2021-03-10T19:58:39.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578820) 
;

-- 2021-03-10T19:58:39.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635150
;

-- 2021-03-10T19:58:39.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635150)
;

-- 2021-03-10T19:58:39.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',125,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635151,573102,'de.metas.vertical.healthcare.alberta','HiMiPo','Hilfsmittelpositionsnummer',0)
;

-- 2021-03-10T19:58:39.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578821) 
;

-- 2021-03-10T19:58:39.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635151
;

-- 2021-03-10T19:58:39.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635151)
;

-- 2021-03-10T19:58:39.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',14,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635152,573103,'de.metas.vertical.healthcare.alberta','Sterne','Sterneranking (0-5) des einzelnen Artikels im Warenkorb, falls keine Wirtschaftlichkeitsberechnung gewünscht',0)
;

-- 2021-03-10T19:58:39.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578822) 
;

-- 2021-03-10T19:58:39.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635152
;

-- 2021-03-10T19:58:39.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635152)
;

-- 2021-03-10T19:58:39.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635153,573104,'de.metas.vertical.healthcare.alberta','Sortimentszuordnung','Beeinflusst die Darstellung im Alberta-Warenkorb',0)
;

-- 2021-03-10T19:58:39.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578823) 
;

-- 2021-03-10T19:58:39.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635153
;

-- 2021-03-10T19:58:39.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635153)
;

-- 2021-03-10T19:58:39.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,635154,573105,'de.metas.vertical.healthcare.alberta','Preisrating','Wirtschaftlichkeitsberechung - Werte von A bis F (A = sehr wirtschaftlich F = nicht wirtschaftlich)',0)
;

-- 2021-03-10T19:58:39.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:39.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578824) 
;

-- 2021-03-10T19:58:39.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635154
;

-- 2021-03-10T19:58:39.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635154)
;

-- 2021-03-10T19:58:40.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,EntityType,Name,Description,AD_Org_ID) VALUES (543534,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2021-03-10 20:58:39','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',635155,573117,'de.metas.vertical.healthcare.alberta','Produkt','Produkt, Leistung, Artikel',0)
;

-- 2021-03-10T19:58:40.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=635155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-03-10T19:58:40.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2021-03-10T19:58:40.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=635155
;

-- 2021-03-10T19:58:40.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(635155)
;

-- 2021-03-10T20:00:02.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540575,0,541597,TO_TIMESTAMP('2021-03-10 21:00:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Pro Produkt kann nur ein Satz von Alberta-Daten erfasst werden','Y','Y','M_Product_AlbertaArticle_UC','N',TO_TIMESTAMP('2021-03-10 21:00:02','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2021-03-10T20:00:02.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540575 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-10T20:00:18.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573117,541068,540575,0,TO_TIMESTAMP('2021-03-10 21:00:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y',10,TO_TIMESTAMP('2021-03-10 21:00:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-10T20:00:34.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product_AlbertaArticle','ALTER TABLE public.M_Product_AlbertaArticle ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2021-03-10T20:00:34.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_AlbertaArticle ADD CONSTRAINT MProduct_MProductAlbertaArticle FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2021-03-10T20:00:46.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX M_Product_AlbertaArticle_UC ON M_Product_AlbertaArticle (M_Product_ID) WHERE IsActive='Y'
;

-- 2021-03-10T20:03:36.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541282,TO_TIMESTAMP('2021-03-10 21:03:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','N','Therapy',TO_TIMESTAMP('2021-03-10 21:03:35','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-03-10T20:03:36.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541282 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-03-10T20:04:03.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542320,TO_TIMESTAMP('2021-03-10 21:04:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Unbekannt',TO_TIMESTAMP('2021-03-10 21:04:03','YYYY-MM-DD HH24:MI:SS'),100,'0','Unknown')
;

-- 2021-03-10T20:04:03.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542320 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T20:04:07.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 21:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542320
;

-- 2021-03-10T20:04:14.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Unknown',Updated=TO_TIMESTAMP('2021-03-10 21:04:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542320
;

-- 2021-03-10T20:04:16.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-10 21:04:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542320
;

-- 2021-03-10T20:04:55.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542321,TO_TIMESTAMP('2021-03-10 21:04:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Parenterale Ernährung',TO_TIMESTAMP('2021-03-10 21:04:55','YYYY-MM-DD HH24:MI:SS'),100,'1','ParenteralNutrition')
;

-- 2021-03-10T20:04:55.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542321 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T20:05:31.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542322,TO_TIMESTAMP('2021-03-10 21:05:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Enterale Ernährung',TO_TIMESTAMP('2021-03-10 21:05:31','YYYY-MM-DD HH24:MI:SS'),100,'2','EnteralNutrition')
;

-- 2021-03-10T20:05:31.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542322 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T20:07:06.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542323,TO_TIMESTAMP('2021-03-10 21:07:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Stoma',TO_TIMESTAMP('2021-03-10 21:07:05','YYYY-MM-DD HH24:MI:SS'),100,'3','Stoma')
;

-- 2021-03-10T20:07:06.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542323 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-10T20:07:29.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542324,TO_TIMESTAMP('2021-03-10 21:07:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Tracheostoma',TO_TIMESTAMP('2021-03-10 21:07:28','YYYY-MM-DD HH24:MI:SS'),100,'4','Tracheostoma')
;

-- 2021-03-10T20:07:29.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542324 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:26:15.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542325,TO_TIMESTAMP('2021-03-11 06:26:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Inkontinenz ableitend',TO_TIMESTAMP('2021-03-11 06:26:14','YYYY-MM-DD HH24:MI:SS'),100,'5','Inkontinenz ableitend')
;

-- 2021-03-11T05:26:15.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542325 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:26:49.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542326,TO_TIMESTAMP('2021-03-11 06:26:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Wundversorgung',TO_TIMESTAMP('2021-03-11 06:26:49','YYYY-MM-DD HH24:MI:SS'),100,'6','Wundversorgung')
;

-- 2021-03-11T05:26:49.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542326 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:27:07.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542327,TO_TIMESTAMP('2021-03-11 06:27:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','IV-Therapien',TO_TIMESTAMP('2021-03-11 06:27:07','YYYY-MM-DD HH24:MI:SS'),100,'7','IV-Therapien')
;

-- 2021-03-11T05:27:07.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542327 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:27:27.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542328,TO_TIMESTAMP('2021-03-11 06:27:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Beatmung',TO_TIMESTAMP('2021-03-11 06:27:27','YYYY-MM-DD HH24:MI:SS'),100,'8','Beatmung')
;

-- 2021-03-11T05:27:27.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542328 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:27:44.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542329,TO_TIMESTAMP('2021-03-11 06:27:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Sonstiges',TO_TIMESTAMP('2021-03-11 06:27:44','YYYY-MM-DD HH24:MI:SS'),100,'9','Sonstiges')
;

-- 2021-03-11T05:27:44.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542329 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:28:02.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542330,TO_TIMESTAMP('2021-03-11 06:28:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','OSA',TO_TIMESTAMP('2021-03-11 06:28:02','YYYY-MM-DD HH24:MI:SS'),100,'10','OSA')
;

-- 2021-03-11T05:28:02.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542330 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:28:18.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542331,TO_TIMESTAMP('2021-03-11 06:28:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Hustenhilfen',TO_TIMESTAMP('2021-03-11 06:28:18','YYYY-MM-DD HH24:MI:SS'),100,'11','Hustenhilfen')
;

-- 2021-03-11T05:28:18.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542331 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:28:33.283Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542332,TO_TIMESTAMP('2021-03-11 06:28:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Absaugung',TO_TIMESTAMP('2021-03-11 06:28:33','YYYY-MM-DD HH24:MI:SS'),100,'12','Absaugung')
;

-- 2021-03-11T05:28:33.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542332 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:28:47.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542333,TO_TIMESTAMP('2021-03-11 06:28:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Patientenüberwachung',TO_TIMESTAMP('2021-03-11 06:28:47','YYYY-MM-DD HH24:MI:SS'),100,'13','Patientenüberwachung')
;

-- 2021-03-11T05:28:47.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542333 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:29:06.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542334,TO_TIMESTAMP('2021-03-11 06:29:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Sauerstoff',TO_TIMESTAMP('2021-03-11 06:29:06','YYYY-MM-DD HH24:MI:SS'),100,'14','Sauerstoff')
;

-- 2021-03-11T05:29:06.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542334 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:29:32.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542335,TO_TIMESTAMP('2021-03-11 06:29:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Inhalations- und Atemtherapie',TO_TIMESTAMP('2021-03-11 06:29:32','YYYY-MM-DD HH24:MI:SS'),100,'15','Inhalations- und Atemtherapie')
;

-- 2021-03-11T05:29:32.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542335 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:29:48.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542336,TO_TIMESTAMP('2021-03-11 06:29:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Lagerungshilfsmittel',TO_TIMESTAMP('2021-03-11 06:29:48','YYYY-MM-DD HH24:MI:SS'),100,'16','Lagerungshilfsmittel')
;

-- 2021-03-11T05:29:48.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542336 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:30:04.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541282,542337,TO_TIMESTAMP('2021-03-11 06:30:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','Schmerztherapie',TO_TIMESTAMP('2021-03-11 06:30:04','YYYY-MM-DD HH24:MI:SS'),100,'17','Schmerztherapie')
;

-- 2021-03-11T05:30:04.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542337 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-03-11T05:30:53.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540709,Updated=TO_TIMESTAMP('2021-03-11 06:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573129
;

-- 2021-03-11T05:31:31.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541600,'N',TO_TIMESTAMP('2021-03-11 06:31:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','N','Y','N','N','Y','N','N','N','N','N',0,'M_Product_AlbertaBillableTherapy','NP','L','M_Product_AlbertaBillableTherapy','DTI',TO_TIMESTAMP('2021-03-11 06:31:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-11T05:31:31.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541600 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-03-11T05:31:31.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555260,TO_TIMESTAMP('2021-03-11 06:31:31','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_AlbertaBillableTherapy',1,'Y','N','Y','Y','M_Product_AlbertaBillableTherapy','N',1000000,TO_TIMESTAMP('2021-03-11 06:31:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-11T05:31:31.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE M_PRODUCT_ALBERTABILLABLETHERAPY_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-03-11T05:31:40.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:40','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','Y','N',TO_TIMESTAMP('2021-03-11 06:31:40','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',573132,'Y','de.metas.vertical.healthcare.alberta','AD_Client_ID','N','Mandant für diese Installation.',102,'Mandant',0)
;

-- 2021-03-11T05:31:40.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573132 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:40.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-03-11T05:31:41.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (30,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','Y','N','Y','N',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',573133,'Y','de.metas.vertical.healthcare.alberta','AD_Org_ID','N','Organisatorische Einheit des Mandanten',113,'Sektion',0)
;

-- 2021-03-11T05:31:41.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573133 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:41.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-03-11T05:31:41.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',573134,'Y','de.metas.vertical.healthcare.alberta','Created','N','Datum, an dem dieser Eintrag erstellt wurde',245,'Erstellt',0)
;

-- 2021-03-11T05:31:41.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573134 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:41.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-03-11T05:31:41.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,573135,'Y','de.metas.vertical.healthcare.alberta','CreatedBy','N','Nutzer, der diesen Eintrag erstellt hat',246,'Erstellt durch',0)
;

-- 2021-03-11T05:31:41.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573135 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:41.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-03-11T05:31:41.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Y','N',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',573136,'Y','de.metas.vertical.healthcare.alberta','IsActive','N','Der Eintrag ist im System aktiv',348,'Aktiv',0)
;

-- 2021-03-11T05:31:41.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573136 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:41.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-03-11T05:31:41.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578831,0,'M_Product_AlbertaBillableTherapy_ID',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.healthcare.alberta','Y','M_Product_AlbertaBillableTherapy','M_Product_AlbertaBillableTherapy',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-11T05:31:41.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578831 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-03-11T05:31:41.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,AD_Element_ID,Name,AD_Org_ID) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,541600,'N',573137,'Y','de.metas.vertical.healthcare.alberta','M_Product_AlbertaBillableTherapy_ID','N',578831,'M_Product_AlbertaBillableTherapy',0)
;

-- 2021-03-11T05:31:41.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573137 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:41.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578831) 
;

-- 2021-03-11T05:31:42.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (19,10,0,'N','Y','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-11 06:31:41','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',573138,'N','de.metas.vertical.healthcare.alberta','M_Product_ID','N','Produkt, Leistung, Artikel',454,'Produkt',0)
;

-- 2021-03-11T05:31:42.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573138 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:42.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2021-03-11T05:31:42.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,AD_Element_ID,Name,AD_Org_ID) VALUES (17,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,541600,'N',540709,573139,'Y','de.metas.vertical.healthcare.alberta','Therapy','N',578830,'Therapie',0)
;

-- 2021-03-11T05:31:42.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573139 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:42.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578830) 
;

-- 2021-03-11T05:31:42.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',573140,'Y','de.metas.vertical.healthcare.alberta','Updated','N','Datum, an dem dieser Eintrag aktualisiert wurde',607,'Aktualisiert',0)
;

-- 2021-03-11T05:31:42.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573140 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:42.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-03-11T05:31:42.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','Y','N','N','N',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,541600,'N','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,573141,'Y','de.metas.vertical.healthcare.alberta','UpdatedBy','N','Nutzer, der diesen Eintrag aktualisiert hat',608,'Aktualisiert durch',0)
;

-- 2021-03-11T05:31:42.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573141 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:42.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-03-11T05:31:42.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsMandatory,EntityType,ColumnName,IsAutoApplyValidationRule,Description,AD_Element_ID,Name,AD_Org_ID) VALUES (10,2,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','Y','N',TO_TIMESTAMP('2021-03-11 06:31:42','YYYY-MM-DD HH24:MI:SS'),100,541600,'Y','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',573142,'N','de.metas.vertical.healthcare.alberta','Value','N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',620,'Suchschlüssel',0)
;

-- 2021-03-11T05:31:42.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573142 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-03-11T05:31:42.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- 2021-03-11T05:31:51.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_product_albertatherapy','Therapy','VARCHAR(50)',null,null)
;

-- 2021-03-11T05:32:02.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_Product_AlbertaBillableTherapy (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_AlbertaBillableTherapy_ID NUMERIC(10) NOT NULL, M_Product_ID NUMERIC(10), Therapy VARCHAR(50) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(2), CONSTRAINT M_Product_AlbertaBillableTherapy_Key PRIMARY KEY (M_Product_AlbertaBillableTherapy_ID), CONSTRAINT MProduct_MProductAlbertaBillableTherapy FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-03-11T06:06:31.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN ManufacturerArticleNumber VARCHAR(125)')
;

-- 2021-03-11T06:46:39.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2021-03-11 07:46:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570772
;

