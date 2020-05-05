-- 2019-10-23T20:07:21.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=1000013, SeqNo=60,Updated=TO_TIMESTAMP('2019-10-23 22:07:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=562102
;

-- 2019-10-24T05:20:06.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540075
;

-- 2019-10-24T05:20:07.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540074
;

-- 2019-10-24T05:20:07.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540072
;

-- 2019-10-24T05:20:08.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540098
;

-- 2019-10-24T05:20:09.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540077
;

-- 2019-10-24T05:20:10.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540086
;

-- 2019-10-24T05:20:10.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540084
;

-- 2019-10-24T05:20:11.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540085
;

-- 2019-10-24T05:20:11.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540073
;

-- 2019-10-24T05:20:12.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540155
;

-- 2019-10-24T05:20:13.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540076
;

-- 2019-10-24T05:20:17.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540157
;

-- 2019-10-24T05:20:20.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540183
;

-- 2019-10-24T05:20:23.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsActive='N',Updated=TO_TIMESTAMP('2019-10-24 07:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540162
;

-- 2019-10-24T05:21:46.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541429,'N',TO_TIMESTAMP('2019-10-24 07:21:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','N','Y','N','N','Y','N','N','N','N','N',0,'C_CommissionSettingsLine','NP','L','C_CommissionSettingsLine',TO_TIMESTAMP('2019-10-24 07:21:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:21:46.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541429 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-10-24T05:21:46.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555073,TO_TIMESTAMP('2019-10-24 07:21:46','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_CommissionSettingsLine',1,'Y','N','Y','Y','C_CommissionSettingsLine','N',1000000,TO_TIMESTAMP('2019-10-24 07:21:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:22:00.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540189,Updated=TO_TIMESTAMP('2019-10-24 07:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541429
;

-- 2019-10-24T05:22:24.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540742,Updated=TO_TIMESTAMP('2019-10-24 07:22:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541429
;

-- 2019-10-24T05:22:33.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569259,102,0,19,541429,'AD_Client_ID',TO_TIMESTAMP('2019-10-24 07:22:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.contracts.commission',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2019-10-24 07:22:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:33.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569259 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:33.354Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2019-10-24T05:22:33.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569260,113,0,30,541429,'AD_Org_ID',TO_TIMESTAMP('2019-10-24 07:22:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.contracts.commission',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2019-10-24 07:22:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:33.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569260 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:33.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2019-10-24T05:22:33.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569261,245,0,16,541429,'Created',TO_TIMESTAMP('2019-10-24 07:22:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.contracts.commission',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2019-10-24 07:22:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:33.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569261 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:33.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2019-10-24T05:22:34.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569262,246,0,18,110,541429,'CreatedBy',TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.contracts.commission',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:34.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569262 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:34.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2019-10-24T05:22:34.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569263,348,0,20,541429,'IsActive',TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.contracts.commission',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:34.402Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569263 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:34.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2019-10-24T05:22:34.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569264,607,0,16,541429,'Updated',TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.contracts.commission',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:34.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569264 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:34.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2019-10-24T05:22:34.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569265,608,0,18,110,541429,'UpdatedBy',TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.contracts.commission',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2019-10-24 07:22:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:34.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569265 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:34.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2019-10-24T05:22:35.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577241,0,'C_CommissionSettingsLine_ID',TO_TIMESTAMP('2019-10-24 07:22:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','C_CommissionSettingsLine','C_CommissionSettingsLine',TO_TIMESTAMP('2019-10-24 07:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:22:35.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577241 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-10-24T05:22:35.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569266,577241,0,13,541429,'C_CommissionSettingsLine_ID',TO_TIMESTAMP('2019-10-24 07:22:35','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','C_CommissionSettingsLine',0,TO_TIMESTAMP('2019-10-24 07:22:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:22:35.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569266 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:22:35.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577241) 
;

-- 2019-10-24T05:23:32.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569267,577218,0,30,541429,'C_HierarchyCommissionSettings_ID',TO_TIMESTAMP('2019-10-24 07:23:32','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',10,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','Einstellungen für Hierachie-Provisionsverträge',0,0,TO_TIMESTAMP('2019-10-24 07:23:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:23:32.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569267 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:23:32.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577218) 
;

-- 2019-10-24T05:24:35.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569268,453,0,30,541429,'M_Product_Category_ID',TO_TIMESTAMP('2019-10-24 07:24:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Kategorie eines Produktes','de.metas.contracts.commission',10,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Produkt Kategorie',0,0,TO_TIMESTAMP('2019-10-24 07:24:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:24:35.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569268 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:24:35.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(453) 
;

-- 2019-10-24T05:24:42.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2019-10-24 07:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569268
;

-- 2019-10-24T05:24:59.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2019-10-24 07:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569267
;

-- 2019-10-24T05:26:06.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,569269,1383,0,30,541429,'C_BP_Group_ID',TO_TIMESTAMP('2019-10-24 07:26:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Geschäftspartnergruppe','de.metas.contracts.commission',10,'Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','Y','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','Geschäftspartnergruppe',0,30,TO_TIMESTAMP('2019-10-24 07:26:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-10-24T05:26:06.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569269 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:26:06.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1383) 
;

-- 2019-10-24T05:27:31.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMax,ValueMin,Version) VALUES (0,569270,577219,0,29,541429,'PercentOfBasePoints',TO_TIMESTAMP('2019-10-24 07:27:31','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','% der Basispunkte',0,0,TO_TIMESTAMP('2019-10-24 07:27:31','YYYY-MM-DD HH24:MI:SS'),100,'100','0',0)
;

-- 2019-10-24T05:27:31.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=569270 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-10-24T05:27:31.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577219) 
;

-- 2019-10-24T05:27:42.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_CommissionSettingsLine (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BP_Group_ID NUMERIC(10), C_CommissionSettingsLine_ID NUMERIC(10) NOT NULL, C_HierarchyCommissionSettings_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_Category_ID NUMERIC(10), PercentOfBasePoints NUMERIC, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CBPGroup_CCommissionSettingsLine FOREIGN KEY (C_BP_Group_ID) REFERENCES public.C_BP_Group DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_CommissionSettingsLine_Key PRIMARY KEY (C_CommissionSettingsLine_ID), CONSTRAINT CHierarchyCommissionSettings_CCommissionSettingsLine FOREIGN KEY (C_HierarchyCommissionSettings_ID) REFERENCES public.C_HierarchyCommissionSettings DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProductCategory_CCommissionSettingsLine FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED)
;

-- 2019-10-24T05:28:44.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540507,0,541429,TO_TIMESTAMP('2019-10-24 07:28:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y','Y','C_CommissionSettingsLine_UC','N',TO_TIMESTAMP('2019-10-24 07:28:44','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2019-10-24T05:28:44.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540507 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-10-24T05:29:19.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569267,540968,540507,0,TO_TIMESTAMP('2019-10-24 07:29:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',10,TO_TIMESTAMP('2019-10-24 07:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:29:53.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569269,540969,540507,0,'COALESCE(C_BP_Group_ID, 0)',TO_TIMESTAMP('2019-10-24 07:29:53','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',20,TO_TIMESTAMP('2019-10-24 07:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:30:30.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569268,540970,540507,0,'COALESCE(M_Product_Category_ID, 0)',TO_TIMESTAMP('2019-10-24 07:30:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts.commission','Y',30,TO_TIMESTAMP('2019-10-24 07:30:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:30:56.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_CommissionSettingsLine_UC ON C_CommissionSettingsLine (C_HierarchyCommissionSettings_ID,COALESCE(C_BP_Group_ID, 0),COALESCE(M_Product_Category_ID, 0)) WHERE IsActive='Y'
;

-- 2019-10-24T05:32:18.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-10-24 07:32:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541425
;

-- 2019-10-24T05:32:29.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2019-10-24 07:32:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541429
;

-- 2019-10-24T05:33:09.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563247
;

-- 2019-10-24T05:33:09.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589587
;

-- 2019-10-24T05:33:09.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=589587
;

-- 2019-10-24T05:33:09.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=589587
;

ALTER TABLE C_HierarchyCommissionSettings DROP COLUMN PercentOfBasePoints;

-- 2019-10-24T05:38:59.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,569267,577241,0,542093,541429,540742,'Y',TO_TIMESTAMP('2019-10-24 07:38:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','N','N','C_CommissionSettingsLine','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'C_CommissionSettingsLine','N',20,0,TO_TIMESTAMP('2019-10-24 07:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:38:59.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=542093 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-10-24T05:38:59.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(577241) 
;

-- 2019-10-24T05:38:59.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(542093)
;

-- 2019-10-24T05:41:18.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569259,590611,0,542093,TO_TIMESTAMP('2019-10-24 07:41:18','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2019-10-24 07:41:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:18.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590611 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:18.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-10-24T05:41:18.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590611
;

-- 2019-10-24T05:41:18.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590611)
;

-- 2019-10-24T05:41:18.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569260,590612,0,542093,TO_TIMESTAMP('2019-10-24 07:41:18','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-10-24 07:41:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:18.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:18.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-10-24T05:41:19.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590612
;

-- 2019-10-24T05:41:19.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590612)
;

-- 2019-10-24T05:41:19.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569263,590613,0,542093,TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:19.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:19.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-10-24T05:41:19.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590613
;

-- 2019-10-24T05:41:19.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590613)
;

-- 2019-10-24T05:41:19.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569266,590614,0,542093,TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','C_CommissionSettingsLine',TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:19.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:19.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577241) 
;

-- 2019-10-24T05:41:19.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590614
;

-- 2019-10-24T05:41:19.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590614)
;

-- 2019-10-24T05:41:19.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569267,590615,0,542093,TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.contracts','Y','Y','N','N','N','N','N','Einstellungen für Hierachie-Provisionsverträge',TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:19.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:19.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577218) 
;

-- 2019-10-24T05:41:19.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590615
;

-- 2019-10-24T05:41:19.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590615)
;

-- 2019-10-24T05:41:19.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569268,590616,0,542093,TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes',10,'de.metas.contracts','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','Y','N','N','N','N','N','Produkt Kategorie',TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:19.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590616 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:19.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453) 
;

-- 2019-10-24T05:41:19.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590616
;

-- 2019-10-24T05:41:19.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590616)
;

-- 2019-10-24T05:41:19.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569269,590617,0,542093,TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100,'Geschäftspartnergruppe',10,'de.metas.contracts','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','Y','N','N','N','N','N','Geschäftspartnergruppe',TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:19.854Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:19.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1383) 
;

-- 2019-10-24T05:41:19.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590617
;

-- 2019-10-24T05:41:19.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590617)
;

-- 2019-10-24T05:41:19.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569270,590618,0,542093,TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.contracts','Y','Y','N','N','N','N','N','% der Basispunkte',TO_TIMESTAMP('2019-10-24 07:41:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-10-24T05:41:19.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=590618 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-10-24T05:41:19.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577219) 
;

-- 2019-10-24T05:41:19.963Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590618
;

-- 2019-10-24T05:41:19.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590618)
;

-- 2019-10-24T05:42:23.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsQueryOnLoad='N',Updated=TO_TIMESTAMP('2019-10-24 07:42:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542093
;

