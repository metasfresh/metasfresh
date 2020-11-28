-- moved to 5519609_sys_gh5139_add_important_AD_Elements
-- 2019-04-17T14:21:27.315
-- URL zum Konzept
--INSERT INTO AD_EntityType (Processing,AD_Client_ID,IsActive,Created,CreatedBy,ModelPackage,Updated,UpdatedBy,AD_EntityType_ID,IsDisplayed,AD_Org_ID,EntityType,Name) VALUES ('N',0,'Y',TO_TIMESTAMP('2019-04-17 14:21:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.securpharm.model',TO_TIMESTAMP('2019-04-17 14:21:26','YYYY-MM-DD HH24:MI:SS'),100,540248,'Y',0,'de.metas.vertical.pharma.securpharm','de.metas.vertical.pharma.securpharm')
--;

-- 2019-04-17T14:22:51.144
-- URL zum Konzept
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,AD_Org_ID,AD_Table_ID,EntityType,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation,Description,Name) VALUES (0,'3',0,100,'Y',TO_TIMESTAMP('2019-04-17 14:22:50','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2019-04-17 14:22:50','YYYY-MM-DD HH24:MI:SS'),'N','Y','N','N','N','N','L','N',0,100,'N','N',0,541348,'de.metas.vertical.pharma','M_Securpharm_Config','P','N','SecurPharm REST API client configuration','SecurPharm Configuration')
;

-- 2019-04-17T14:22:51.153
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541348 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-04-17T14:22:51.343
-- URL zum Konzept
INSERT INTO AD_Sequence (CurrentNext,IsAudited,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,AD_Client_ID,Name,AD_Org_ID,Description) VALUES (1000000,'N','N','Y','Y',TO_TIMESTAMP('2019-04-17 14:22:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2019-04-17 14:22:51','YYYY-MM-DD HH24:MI:SS'),100,555001,0,'M_Securpharm_Config',0,'Table M_Securpharm_Config')
;

-- 2019-04-17T14:23:16.485
-- URL zum Konzept
UPDATE AD_Table SET EntityType='de.metas.vertical.pharma.securpharm',Updated=TO_TIMESTAMP('2019-04-17 14:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541348
;

-- 2019-04-17T14:23:46.484
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:46','YYYY-MM-DD HH24:MI:SS'),100,541348,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',567718,'Y',0,102,'de.metas.vertical.pharma.securpharm','AD_Client_ID','Mandant für diese Installation.','Mandant')
;

-- 2019-04-17T14:23:46.491
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567718 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:46.515
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2019-04-17T14:23:47.548
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Y','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:47','YYYY-MM-DD HH24:MI:SS'),100,541348,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',567719,'Y',0,113,'de.metas.vertical.pharma.securpharm','AD_Org_ID','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-17T14:23:47.553
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567719 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:47.557
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2019-04-17T14:23:47.930
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:47','YYYY-MM-DD HH24:MI:SS'),100,541348,'The authorization ID required to access the creditpass/creditPass API',567720,'Y',0,576181,'de.metas.vertical.pharma.securpharm','AuthId','Berechtigungs ID')
;

-- 2019-04-17T14:23:47.935
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567720 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:47.939
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576181) 
;

-- 2019-04-17T14:23:48.146
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,541348,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',567721,'Y',0,245,'de.metas.vertical.pharma.securpharm','Created','Datum, an dem dieser Eintrag erstellt wurde','Erstellt')
;

-- 2019-04-17T14:23:48.150
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567721 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:48.154
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2019-04-17T14:23:48.571
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,541348,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,567722,'Y',0,246,'de.metas.vertical.pharma.securpharm','CreatedBy','Nutzer, der diesen Eintrag erstellt hat','Erstellt durch')
;

-- 2019-04-17T14:23:48.574
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567722 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:48.577
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2019-04-17T14:23:48.922
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,576637,0,'M_Securpharm_Config_ID','de.metas.vertical.pharma.securpharm','SecurPharm Configuration','SecurPharm Configuration')
;

-- 2019-04-17T14:23:48.932
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576637 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T14:23:49.071
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (13,10,0,'Y','N','N','Y',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N',TO_TIMESTAMP('2019-04-17 14:23:48','YYYY-MM-DD HH24:MI:SS'),100,541348,567723,'Y',0,576637,'de.metas.vertical.pharma.securpharm','M_Securpharm_Config_ID','SecurPharm Configuration')
;

-- 2019-04-17T14:23:49.075
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567723 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:49.089
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576637) 
;

-- 2019-04-17T14:23:49.321
-- URL zum Konzept
INSERT INTO AD_Column (AD_Val_Rule_ID,AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (540428,17,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:49','YYYY-MM-DD HH24:MI:SS'),100,541348,'Bestellungen wie ausgewählt abschließen, wenn credipass manuelle Prüfung empfiehlt oder wenn creditpass fehl schlägt',540961,567724,'Y',0,576229,'de.metas.vertical.pharma.securpharm','DefaultCheckResult','Bestellungen wie ausgewählt abschließen','Standard Ergebnis')
;

-- 2019-04-17T14:23:49.325
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567724 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:49.329
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576229) 
;

-- 2019-04-17T14:23:49.488
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:49','YYYY-MM-DD HH24:MI:SS'),100,541348,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',567725,'Y',0,348,'de.metas.vertical.pharma.securpharm','IsActive','Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-17T14:23:49.492
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567725 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:49.495
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2019-04-17T14:23:49.995
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (30,'',10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:49','YYYY-MM-DD HH24:MI:SS'),100,541348,'',110,567726,'Y',0,576230,'de.metas.vertical.pharma.securpharm','Manual_Check_User_ID','Benutzer für die Benachrichtigung zur manuellen Prüfung','Benutzer für manuelle Prüfung')
;

-- 2019-04-17T14:23:49.998
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567726 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:50.001
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576230) 
;

-- 2019-04-17T14:23:50.166
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,541348,'The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".',567727,'Y',0,498,'de.metas.vertical.pharma.securpharm','Password','','Kennwort')
;

-- 2019-04-17T14:23:50.169
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567727 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:50.172
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(498) 
;

-- 2019-04-17T14:23:50.346
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,'ABK',3,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,541348,567728,'Y',0,576184,'de.metas.vertical.pharma.securpharm','RequestReason','Grund der Anfrage')
;

-- 2019-04-17T14:23:50.350
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567728 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:50.355
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576184) 
;

-- 2019-04-17T14:23:50.507
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (40,1000,0,'N','N','N','Y',1,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,541348,567729,'Y',0,576182,'de.metas.vertical.pharma.securpharm','RestApiBaseURL','REST API URL')
;

-- 2019-04-17T14:23:50.510
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567729 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:50.513
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576182) 
;

-- 2019-04-17T14:23:50.694
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (22,'0',3,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,541348,567730,'Y',0,576228,'de.metas.vertical.pharma.securpharm','RetryAfterDays','Creditpass-Prüfung wiederholen ')
;

-- 2019-04-17T14:23:50.698
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567730 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:50.701
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576228) 
;

-- 2019-04-17T14:23:50.857
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (11,'@SQL=SELECT COALESCE(MAX(SeqNo),0) + 10 AS DefaultValue FROM CS_Creditpass_Config',10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,541348,'"Reihenfolge" bestimmt die Reihenfolge der Einträge',567731,'N',0,566,'de.metas.vertical.pharma.securpharm','SeqNo','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','Reihenfolge')
;

-- 2019-04-17T14:23:50.861
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567731 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:50.866
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2019-04-17T14:23:51.083
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-17 14:23:50','YYYY-MM-DD HH24:MI:SS'),100,541348,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',567732,'Y',0,607,'de.metas.vertical.pharma.securpharm','Updated','Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert')
;

-- 2019-04-17T14:23:51.086
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567732 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:51.088
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2019-04-17T14:23:51.487
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:23:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-17 14:23:51','YYYY-MM-DD HH24:MI:SS'),100,541348,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,567733,'Y',0,608,'de.metas.vertical.pharma.securpharm','UpdatedBy','Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch')
;

-- 2019-04-17T14:23:51.492
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567733 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:23:51.497
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2019-04-17T14:24:23.099
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567720
;

-- 2019-04-17T14:24:23.136
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567720
;

-- 2019-04-17T14:24:32.115
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567724
;

-- 2019-04-17T14:24:32.134
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567724
;

-- 2019-04-17T14:24:54.650
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567728
;

-- 2019-04-17T14:24:54.694
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567728
;

-- 2019-04-17T14:25:02.304
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567730
;

-- 2019-04-17T14:25:02.321
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567730
;

-- 2019-04-17T14:25:05.492
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567731
;

-- 2019-04-17T14:25:05.505
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567731
;

-- 2019-04-17T14:29:38.065
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 14:29:37','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 14:29:37','YYYY-MM-DD HH24:MI:SS'),100,576638,0,'PharmaRestApiBaseURL','de.metas.vertical.pharma.securpharm','Pharma REST API URL','Pharma REST API URL')
;

-- 2019-04-17T14:29:38.085
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576638 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T14:32:17.700
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='N', IsSelectionColumn='N', Help=NULL, AD_Element_ID=576638, ColumnName='PharmaRestApiBaseURL', Description=NULL, Name='Pharma REST API URL',Updated=TO_TIMESTAMP('2019-04-17 14:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567729
;

-- 2019-04-17T14:32:17.710
-- URL zum Konzept
UPDATE AD_Field SET Name='Pharma REST API URL', Description=NULL, Help=NULL WHERE AD_Column_ID=567729
;

-- 2019-04-17T14:32:17.717
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576638) 
;

-- 2019-04-17T14:33:58.142
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 14:33:57','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 14:33:57','YYYY-MM-DD HH24:MI:SS'),100,576641,0,'AuthPharmaRestApiBaseURL','de.metas.vertical.pharma.securpharm','Pharma Auth REST API URL','Pharma Auth REST API URL')
;

-- 2019-04-17T14:33:58.153
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576641 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T14:34:30.856
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (40,1000,0,'N','N','N','N',1,0,'Y',TO_TIMESTAMP('2019-04-17 14:34:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:34:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541348,'N',567736,'N','Y','N','N','N','N','N','N',0,0,576641,'de.metas.vertical.pharma.securpharm','N','N','AuthPharmaRestApiBaseURL','N','Pharma Auth REST API URL')
;

-- 2019-04-17T14:34:30.865
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567736 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:34:30.873
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576641) 
;

-- 2019-04-17T14:40:18.914
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 14:40:18','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 14:40:18','YYYY-MM-DD HH24:MI:SS'),100,576642,0,'ApplicationUUID','de.metas.vertical.pharma.securpharm','Application UUID','Application UUID')
;

-- 2019-04-17T14:40:18.920
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576642 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T14:41:19.745
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (10,36,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 14:41:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 14:41:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541348,'N',567737,'N','Y','N','N','N','N','N','N',0,0,576642,'de.metas.vertical.pharma.securpharm','N','N','ApplicationUUID','N','Application UUID')
;

-- 2019-04-17T14:41:19.757
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567737 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T14:41:19.768
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576642) 
;

-- 2019-04-17T14:45:35.485
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,Description,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 14:45:35','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 14:45:35','YYYY-MM-DD HH24:MI:SS'),100,576643,0,'TanPassword','de.metas.vertical.pharma.securpharm','TAN Passwort benutzt für Authentifizierung','TAN Passwort','TAN Passwort')
;

-- 2019-04-17T14:45:35.492
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576643 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T14:46:08.840
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='TAN Password', Description='TAN Password used for authentication', IsTranslated='Y', Name='TAN Password',Updated=TO_TIMESTAMP('2019-04-17 14:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576643
;

-- 2019-04-17T14:46:08.850
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576643,'en_US') 
;

-- 2019-04-17T16:49:44.387
-- URL zum Konzept
UPDATE AD_Column SET Help=NULL, AD_Element_ID=576643, ColumnName='TanPassword', Description='TAN Passwort benutzt für Authentifizierung', Name='TAN Passwort',Updated=TO_TIMESTAMP('2019-04-17 16:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567727
;

-- 2019-04-17T16:49:44.399
-- URL zum Konzept
UPDATE AD_Field SET Name='TAN Passwort', Description='TAN Passwort benutzt für Authentifizierung', Help=NULL WHERE AD_Column_ID=567727
;

-- 2019-04-17T16:49:44.410
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576643) 
;

-- 2019-04-17T16:54:29.060
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,Description,Help,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 16:54:28','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 16:54:28','YYYY-MM-DD HH24:MI:SS'),100,576645,0,'Support_User_ID','de.metas.vertical.pharma.securpharm','Benutzer für Benachrichtigungen ','','Support Benutzer','Support Benutzer')
;

-- 2019-04-17T16:54:29.068
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576645 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T16:55:32.501
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Support User', Description='User for notifications ', IsTranslated='Y', Name='Support User',Updated=TO_TIMESTAMP('2019-04-17 16:55:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576645
;

-- 2019-04-17T16:55:32.505
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576645,'en_US') 
;

-- 2019-04-17T16:56:08.298
-- URL zum Konzept
UPDATE AD_Column SET Help='', AD_Element_ID=576645, ColumnName='Support_User_ID', Description='Benutzer für Benachrichtigungen ', Name='Support Benutzer',Updated=TO_TIMESTAMP('2019-04-17 16:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567726
;

-- 2019-04-17T16:56:08.304
-- URL zum Konzept
UPDATE AD_Field SET Name='Support Benutzer', Description='Benutzer für Benachrichtigungen ', Help='' WHERE AD_Column_ID=567726
;

-- 2019-04-17T16:56:08.306
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576645) 
;

-- 2019-04-17T17:07:00.196
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,Description,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 17:07:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 17:07:00','YYYY-MM-DD HH24:MI:SS'),100,576646,0,'CertificatePath','de.metas.vertical.pharma.securpharm','','Zertifikatpfad','Zertifikatpfad')
;

-- 2019-04-17T17:07:00.207
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576646 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T17:08:23.982
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Certificate path', Name='Certificate path',Updated=TO_TIMESTAMP('2019-04-17 17:08:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576646
;

-- 2019-04-17T17:08:23.986
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576646,'en_US') 
;

-- 2019-04-17T17:08:33.359
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-17 17:08:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576646
;

-- 2019-04-17T17:08:33.362
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576646,'en_US') 
;

-- 2019-04-17T17:10:23.183
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name) VALUES (10,200,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-17 17:10:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-17 17:10:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541348,'N',567738,'N','Y','N','N','N','N','N','N',0,0,576646,'de.metas.vertical.pharma.securpharm','N','N','CertificatePath','','N','Zertifikatpfad')
;

-- 2019-04-17T17:10:23.226
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567738 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-17T17:10:23.251
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576646) 
;

-- 2019-04-17T17:14:36.844
-- URL zum Konzept
UPDATE AD_Column SET PersonalDataCategory='P',Updated=TO_TIMESTAMP('2019-04-17 17:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567737
;

-- 2019-04-17T17:14:46.914
-- URL zum Konzept
UPDATE AD_Column SET PersonalDataCategory='SP',Updated=TO_TIMESTAMP('2019-04-17 17:14:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567738
;

-- 2019-04-17T17:14:54.289
-- URL zum Konzept
UPDATE AD_Column SET PersonalDataCategory='SP',Updated=TO_TIMESTAMP('2019-04-17 17:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567737
;

-- 2019-04-17T17:15:03.856
-- URL zum Konzept
UPDATE AD_Column SET PersonalDataCategory='P',Updated=TO_TIMESTAMP('2019-04-17 17:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567736
;

-- 2019-04-17T17:15:10.471
-- URL zum Konzept
UPDATE AD_Column SET PersonalDataCategory='P',Updated=TO_TIMESTAMP('2019-04-17 17:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567729
;

-- 2019-04-17T17:15:24.231
-- URL zum Konzept
UPDATE AD_Column SET PersonalDataCategory='SP',Updated=TO_TIMESTAMP('2019-04-17 17:15:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567727
;

-- 2019-04-17T18:49:15.764
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 18:49:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-17 18:49:15','YYYY-MM-DD HH24:MI:SS'),100,576647,0,'de.metas.vertical.pharma.securpharm','SecurPharm Einstellungen','SecurPharm Einstellungen')
;

-- 2019-04-17T18:49:15.790
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576647 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-17T18:49:39.149
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='SecurPharm Configuration', IsTranslated='Y', Name='SecurPharm Configuration',Updated=TO_TIMESTAMP('2019-04-17 18:49:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576647
;

-- 2019-04-17T18:49:39.153
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576647,'en_US') 
;

-- 2019-04-17T18:53:03.277
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,IsActive,Created,CreatedBy,WindowType,Processing,IsSOTrx,WinHeight,WinWidth,IsBetaFunctionality,IsDefault,Updated,UpdatedBy,IsOneInstanceOnly,AD_Window_ID,AD_Org_ID,Name,EntityType,IsEnableRemoteCacheInvalidation,AD_Element_ID) VALUES (0,'Y',TO_TIMESTAMP('2019-04-17 18:53:03','YYYY-MM-DD HH24:MI:SS'),100,'M','N','Y',0,0,'N','N',TO_TIMESTAMP('2019-04-17 18:53:03','YYYY-MM-DD HH24:MI:SS'),100,'N',540631,0,'SecurPharm Einstellungen','de.metas.vertical.pharma.securpharm','N',576647)
;

-- 2019-04-17T18:53:03.292
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540631 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-04-17T18:53:03.548
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(576647) 
;

-- 2019-04-17T18:53:03.576
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540631
;

-- 2019-04-17T18:53:03.603
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540631)
;

-- 2019-04-17T18:56:19.207
-- URL zum Konzept
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,AD_Org_ID,Name,EntityType,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Element_ID) VALUES (TO_TIMESTAMP('2019-04-17 18:56:19','YYYY-MM-DD HH24:MI:SS'),'N',540631,10,'N',0,TO_TIMESTAMP('2019-04-17 18:56:19','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N','N',0,'Y','N','N','Y','Y','Y','N',541348,541721,'N','Y',0,0,'SecurPharm Configuration','de.metas.vertical.pharma.securpharm','M_Securpharm_Config','Y','N',576637)
;

-- 2019-04-17T18:56:19.216
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541721 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-04-17T18:56:19.235
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(576637) 
;

-- 2019-04-17T18:56:19.270
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541721)
;

-- 2019-04-17T18:56:38.296
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541721,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:38','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:38','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',579193,'N',567718,0,'de.metas.vertical.pharma.securpharm','Mandant für diese Installation.','Mandant')
;

-- 2019-04-17T18:56:38.307
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579193 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:38.325
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-04-17T18:56:39.304
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579193
;

-- 2019-04-17T18:56:39.307
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579193)
;

-- 2019-04-17T18:56:39.434
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541721,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:39','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',579194,'N',567719,0,'de.metas.vertical.pharma.securpharm','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-17T18:56:39.438
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:39.455
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-04-17T18:56:39.816
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579194
;

-- 2019-04-17T18:56:39.818
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579194)
;

-- 2019-04-17T18:56:39.926
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541721,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:39','YYYY-MM-DD HH24:MI:SS'),100,579195,'N',567723,0,'de.metas.vertical.pharma.securpharm','SecurPharm Configuration')
;

-- 2019-04-17T18:56:39.929
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:39.940
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576637) 
;

-- 2019-04-17T18:56:39.946
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579195
;

-- 2019-04-17T18:56:39.948
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579195)
;

-- 2019-04-17T18:56:40.054
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541721,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:39','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:39','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',579196,'N',567725,0,'de.metas.vertical.pharma.securpharm','Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-17T18:56:40.061
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:40.085
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-04-17T18:56:41.020
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579196
;

-- 2019-04-17T18:56:41.022
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579196)
;

-- 2019-04-17T18:56:41.138
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541721,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,'',579197,'N',567726,0,'de.metas.vertical.pharma.securpharm','Benutzer für Benachrichtigungen ','Support Benutzer')
;

-- 2019-04-17T18:56:41.143
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:41.174
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576645) 
;

-- 2019-04-17T18:56:41.187
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579197
;

-- 2019-04-17T18:56:41.192
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579197)
;

-- 2019-04-17T18:56:41.318
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541721,'N',50,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,579198,'N',567727,0,'de.metas.vertical.pharma.securpharm','TAN Passwort benutzt für Authentifizierung','TAN Passwort')
;

-- 2019-04-17T18:56:41.322
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579198 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:41.339
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576643) 
;

-- 2019-04-17T18:56:41.348
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579198
;

-- 2019-04-17T18:56:41.351
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579198)
;

-- 2019-04-17T18:56:41.474
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541721,'N',1000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,579199,'N',567729,0,'de.metas.vertical.pharma.securpharm','Pharma REST API URL')
;

-- 2019-04-17T18:56:41.478
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579199 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:41.493
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576638) 
;

-- 2019-04-17T18:56:41.498
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579199
;

-- 2019-04-17T18:56:41.502
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579199)
;

-- 2019-04-17T18:56:41.703
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541721,'N',1000,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,579200,'N',567736,0,'de.metas.vertical.pharma.securpharm','Pharma Auth REST API URL')
;

-- 2019-04-17T18:56:41.708
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579200 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:41.727
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576641) 
;

-- 2019-04-17T18:56:41.732
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579200
;

-- 2019-04-17T18:56:41.735
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579200)
;

-- 2019-04-17T18:56:41.859
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541721,'N',36,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,579201,'N',567737,0,'de.metas.vertical.pharma.securpharm','Application UUID')
;

-- 2019-04-17T18:56:41.862
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:41.884
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576642) 
;

-- 2019-04-17T18:56:41.888
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579201
;

-- 2019-04-17T18:56:41.890
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579201)
;

-- 2019-04-17T18:56:42.022
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541721,'N',200,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-17 18:56:41','YYYY-MM-DD HH24:MI:SS'),100,579202,'N',567738,0,'de.metas.vertical.pharma.securpharm','','Zertifikatpfad')
;

-- 2019-04-17T18:56:42.026
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=579202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-17T18:56:42.038
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576646) 
;

-- 2019-04-17T18:56:42.042
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=579202
;

-- 2019-04-17T18:56:42.044
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(579202)
;

-- 2019-04-17T18:57:13.716
-- URL zum Konzept
INSERT INTO AD_UI_Section (Value,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Section_ID,Updated,UpdatedBy,AD_Tab_ID,SeqNo,AD_Org_ID) VALUES ('main',0,TO_TIMESTAMP('2019-04-17 18:57:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',541277,TO_TIMESTAMP('2019-04-17 18:57:13','YYYY-MM-DD HH24:MI:SS'),100,541721,10,0)
;

-- 2019-04-17T18:57:13.721
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541277 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-04-17T18:57:13.907
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-17 18:57:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',541635,TO_TIMESTAMP('2019-04-17 18:57:13','YYYY-MM-DD HH24:MI:SS'),541277,10,0)
;

-- 2019-04-17T18:57:14.025
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-17 18:57:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',541636,TO_TIMESTAMP('2019-04-17 18:57:13','YYYY-MM-DD HH24:MI:SS'),541277,20,0)
;

-- 2019-04-17T18:57:14.213
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,UIStyle,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-17 18:57:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',542475,'primary',10,TO_TIMESTAMP('2019-04-17 18:57:14','YYYY-MM-DD HH24:MI:SS'),541635,100,0,'default')
;

-- 2019-04-17T18:58:30.921
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=542475
;

-- 2019-04-17T18:58:30.936
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541635
;

-- 2019-04-17T18:58:30.960
-- URL zum Konzept
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=541636
;

-- 2019-04-17T18:58:30.965
-- URL zum Konzept
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=541277
;

-- 2019-04-17T18:58:30.978
-- URL zum Konzept
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=541277
;

-- 2019-04-17T18:58:50.457
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579193
;

-- 2019-04-17T18:58:50.974
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579194
;

-- 2019-04-17T18:59:02.427
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579196
;

-- 2019-04-17T18:59:03.352
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579197
;

-- 2019-04-17T18:59:04.539
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579198
;

-- 2019-04-17T18:59:06.258
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579199
;

-- 2019-04-17T18:59:11.138
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579200
;

-- 2019-04-17T18:59:12.017
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579201
;

-- 2019-04-17T18:59:29.788
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579202
;

-- 2019-04-17T18:59:30.648
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579193
;

-- 2019-04-17T18:59:31.563
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579194
;

-- 2019-04-17T18:59:32.230
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579196
;

-- 2019-04-17T18:59:32.921
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579197
;

-- 2019-04-17T18:59:33.506
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579198
;

-- 2019-04-17T18:59:33.869
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579199
;

-- 2019-04-17T18:59:34.337
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579200
;

-- 2019-04-17T18:59:34.864
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 18:59:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579201
;

-- 2019-04-17T19:00:20.950
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-17 19:00:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=579202
;

-- 2019-04-17T19:00:25.899
-- URL zum Konzept
INSERT INTO AD_UI_Section (Value,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Section_ID,Updated,UpdatedBy,AD_Tab_ID,SeqNo,AD_Org_ID) VALUES ('main',0,TO_TIMESTAMP('2019-04-17 19:00:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',541278,TO_TIMESTAMP('2019-04-17 19:00:25','YYYY-MM-DD HH24:MI:SS'),100,541721,10,0)
;

-- 2019-04-17T19:00:25.903
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541278 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-04-17T19:00:26.020
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-17 19:00:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',541637,TO_TIMESTAMP('2019-04-17 19:00:25','YYYY-MM-DD HH24:MI:SS'),541278,10,0)
;

-- 2019-04-17T19:00:26.127
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',541638,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),541278,20,0)
;

-- 2019-04-17T19:00:26.269
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,UIStyle,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',542476,'primary',10,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),541637,100,0,'default')
;

-- 2019-04-17T19:00:26.498
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558590,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',579193,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',10,'N',0,541721,'Mandant für diese Installation.','Mandant')
;

-- 2019-04-17T19:00:26.608
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558591,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',579194,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',20,'N',0,541721,'Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-17T19:00:26.716
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558592,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',579196,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',30,'N',0,541721,'Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-17T19:00:26.935
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558593,'',579197,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',40,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',40,'N',0,541721,'Benutzer für Benachrichtigungen ','Support Benutzer')
;

-- 2019-04-17T19:00:27.053
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558594,579198,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',50,TO_TIMESTAMP('2019-04-17 19:00:26','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',50,'N',0,541721,'TAN Passwort benutzt für Authentifizierung','TAN Passwort')
;

-- 2019-04-17T19:00:27.186
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558595,579199,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',60,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',60,'N',0,541721,'Pharma REST API URL')
;

-- 2019-04-17T19:00:27.310
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558596,579200,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',70,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',70,'N',0,541721,'Pharma Auth REST API URL')
;

-- 2019-04-17T19:00:27.419
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558597,579201,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',80,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',80,'N',0,541721,'Application UUID')
;

-- 2019-04-17T19:00:27.524
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558598,579202,'N',542476,0,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',90,TO_TIMESTAMP('2019-04-17 19:00:27','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',90,'N',0,541721,'','Zertifikatpfad')
;

-- 2019-04-17T19:01:41.547
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-17 19:01:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',542477,20,TO_TIMESTAMP('2019-04-17 19:01:41','YYYY-MM-DD HH24:MI:SS'),541637,100,0,'org')
;

-- 2019-04-17T19:01:46.156
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-17 19:01:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',542478,30,TO_TIMESTAMP('2019-04-17 19:01:46','YYYY-MM-DD HH24:MI:SS'),541637,100,0,'logic')
;

-- 2019-04-17T19:02:02.665
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542477, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-17 19:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558590
;

-- 2019-04-17T19:02:10.106
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542477, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-17 19:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558591
;

-- 2019-04-17T19:02:19.596
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542478, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-17 19:02:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558592
;

-- 2019-04-17T19:03:05.008
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='flag',Updated=TO_TIMESTAMP('2019-04-17 19:03:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542478
;

-- 2019-04-17T19:03:08.933
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-17 19:03:08','YYYY-MM-DD HH24:MI:SS'),100,'Y',542479,40,TO_TIMESTAMP('2019-04-17 19:03:08','YYYY-MM-DD HH24:MI:SS'),541637,100,0,'logic')
;

-- 2019-04-17T19:03:28.694
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542479, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-17 19:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558593
;

-- 2019-04-17T19:04:37.795
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542479, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-17 19:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558595
;

-- 2019-04-17T19:04:56.756
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542479, SeqNo=30,Updated=TO_TIMESTAMP('2019-04-17 19:04:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558597
;

-- 2019-04-17T19:05:10.297
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='auth',Updated=TO_TIMESTAMP('2019-04-17 19:05:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542476
;

-- 2019-04-17T19:05:23.875
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=10, AD_UI_Column_ID=541638,Updated=TO_TIMESTAMP('2019-04-17 19:05:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542478
;

-- 2019-04-17T19:05:32.326
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=20, AD_UI_Column_ID=541638,Updated=TO_TIMESTAMP('2019-04-17 19:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542477
;

-- 2019-04-17T19:06:00.161
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2019-04-17 19:06:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558594
;

-- 2019-04-17T19:06:15.628
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-04-17 19:06:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558598
;

-- 2019-04-17T19:06:57.459
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-04-17 19:06:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558595
;

-- 2019-04-17T19:07:02.446
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-04-17 19:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558597
;

-- 2019-04-17T19:07:07.999
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-04-17 19:07:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558593
;

-- 2019-04-17T19:08:01.089
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558596
;

-- 2019-04-17T19:08:01.098
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558598
;

-- 2019-04-17T19:08:01.109
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558594
;

-- 2019-04-17T19:08:01.119
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558595
;

-- 2019-04-17T19:08:01.129
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558597
;

-- 2019-04-17T19:08:01.138
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558593
;

-- 2019-04-17T19:08:01.146
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558592
;

-- 2019-04-17T19:08:01.155
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558590
;

-- 2019-04-17T19:08:01.164
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-04-17 19:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558591
;

-- 2019-04-17T19:11:36.253
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Window_ID,AD_Client_ID,IsActive,Created,CreatedBy,IsSummary,IsSOTrx,IsReadOnly,Updated,UpdatedBy,AD_Menu_ID,IsCreateNew,InternalName,AD_Org_ID,EntityType,Action,Name,AD_Element_ID) VALUES (540631,0,'Y',TO_TIMESTAMP('2019-04-17 19:11:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N',TO_TIMESTAMP('2019-04-17 19:11:36','YYYY-MM-DD HH24:MI:SS'),100,541246,'N','SecurPharm Einstellungen',0,'de.metas.vertical.pharma.securpharm','W','SecurPharm Einstellungen',576647)
;

-- 2019-04-17T19:11:36.258
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541246 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-04-17T19:11:36.264
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541246, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541246)
;

-- 2019-04-17T19:11:36.296
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(576647) 
;

-- 2019-04-17T19:11:37.084
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=583 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.087
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=578 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.090
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=570 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.091
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=579 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.093
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=576 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.096
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=587 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.098
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=588 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.100
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=584 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.101
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=574 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.104
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=573 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:37.106
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=566, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.755
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.758
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=167 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.760
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=357 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.763
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=229 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.765
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=256 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.767
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=477 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.770
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=197 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.772
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=179 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.775
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=503 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.777
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540510 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.779
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=196 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.782
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=479 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.784
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=482 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.787
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=481 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.790
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=411 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.792
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=537 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.794
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=311 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.798
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=292 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.801
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=545 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.804
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540289 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.807
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540560 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.809
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540569 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.812
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540801 AND AD_Tree_ID=10
;

-- 2019-04-17T19:11:55.814
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540669 AND AD_Tree_ID=10
;

-- 2019-04-17T19:13:02.202
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540631,Updated=TO_TIMESTAMP('2019-04-17 19:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541348
;

-- 2019-04-17T19:13:13.347
-- URL zum Konzept
/* DDL */ CREATE TABLE public.M_Securpharm_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, ApplicationUUID VARCHAR(36) NOT NULL, AuthPharmaRestApiBaseURL VARCHAR(1000) NOT NULL, CertificatePath VARCHAR(200) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Securpharm_Config_ID NUMERIC(10) NOT NULL, PharmaRestApiBaseURL VARCHAR(1000) NOT NULL, Support_User_ID NUMERIC(10) NOT NULL, TanPassword VARCHAR(50) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Securpharm_Config_Key PRIMARY KEY (M_Securpharm_Config_ID), CONSTRAINT SupportUser_MSecurpharmConfig FOREIGN KEY (Support_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED)
;

ALTER TABLE public.m_securpharm_config ADD CONSTRAINT m_securpharm_config_ad_client_id UNIQUE(ad_client_id)
;

-- 2019-04-18T12:45:46.629
-- URL zum Konzept
UPDATE AD_Menu SET IsCreateNew='Y', WEBUI_NameBrowse='SecurPharm Einstellungen', WEBUI_NameNew='Neue SecurPharm Einstellung', WEBUI_NameNewBreadcrumb='Neue SecurPharm Einstellung',Updated=TO_TIMESTAMP('2019-04-18 12:45:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541246
;

-- 2019-04-18T12:47:12.349
-- URL zum Konzept
UPDATE AD_Menu SET IsCreateNew='N',Updated=TO_TIMESTAMP('2019-04-18 12:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541246
;

-- 2019-04-18T12:50:13.803
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=167 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.814
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=357 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.816
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=229 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.820
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=256 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.825
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=477 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.831
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=197 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.834
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=179 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.840
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=503 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.844
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540510 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.849
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=196 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.852
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=479 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.856
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=482 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.860
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=481 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.862
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=411 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.865
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=537 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.868
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=311 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.871
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=292 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.873
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=545 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.876
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540289 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.878
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540560 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.880
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540569 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.883
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540801 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.886
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540669 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:13.888
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.744
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=167 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.747
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=357 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.749
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=229 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.751
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=256 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.754
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=477 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.756
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=197 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.758
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=179 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.761
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=503 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.763
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540510 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.766
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=196 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.768
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=479 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.773
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=482 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.775
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=481 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.777
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=411 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.780
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=537 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.782
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=311 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.784
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=292 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.786
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=545 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.788
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540289 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.790
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540560 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.793
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540569 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.795
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540801 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.797
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-18T12:50:16.799
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540669 AND AD_Tree_ID=10
;

-- 2019-04-18T13:02:43.856
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-18 13:02:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558594
;

-- 2019-04-18T13:02:55.671
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='',Updated=TO_TIMESTAMP('2019-04-18 13:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558594
;

-- 2019-04-18T13:03:03.104
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-18 13:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558596
;

-- 2019-04-18T13:03:23.217
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-18 13:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558595
;

-- 2019-04-18T13:03:46.035
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-18 13:03:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558597
;

-- 2019-04-18T13:13:54.013
-- URL zum Konzept
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,AD_Org_ID,AD_Table_ID,EntityType,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation,Description,Name) VALUES (0,'3',0,100,'Y',TO_TIMESTAMP('2019-04-18 13:13:53','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2019-04-18 13:13:53','YYYY-MM-DD HH24:MI:SS'),'N','Y','N','N','N','N','L','N',0,100,'N','N',0,541349,'de.metas.vertical.pharma.securpharm','M_Securpharm_Productdata_Result','NP','N','SecurPharm API response with product data and status','SecurPharm product data result')
;

-- 2019-04-18T13:13:54.019
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541349 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-04-18T13:13:54.205
-- URL zum Konzept
INSERT INTO AD_Sequence (CurrentNext,IsAudited,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,AD_Client_ID,Name,AD_Org_ID,Description) VALUES (1000000,'N','N','Y','Y',TO_TIMESTAMP('2019-04-18 13:13:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2019-04-18 13:13:54','YYYY-MM-DD HH24:MI:SS'),100,555002,0,'M_Securpharm_Productdata_Result',0,'Table M_Securpharm_Productdata_Result')
;

-- 2019-04-18T13:21:54.888
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 13:21:54','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 13:21:54','YYYY-MM-DD HH24:MI:SS'),100,576649,0,'ProductCode','de.metas.vertical.pharma.securpharm','Produktcode','Produktcode')
;

-- 2019-04-18T13:21:54.895
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576649 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T13:22:12.913
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Product code', IsTranslated='Y', Name='Product code',Updated=TO_TIMESTAMP('2019-04-18 13:22:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576649
;

-- 2019-04-18T13:22:12.916
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576649,'en_US') 
;

-- 2019-04-18T13:23:19.335
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:19','YYYY-MM-DD HH24:MI:SS'),100,541349,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',567740,'Y',0,102,'de.metas.vertical.pharma.securpharm','AD_Client_ID','Mandant für diese Installation.','Mandant')
;

-- 2019-04-18T13:23:19.341
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:19.345
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2019-04-18T13:23:20.017
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Y','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:19','YYYY-MM-DD HH24:MI:SS'),100,541349,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',567741,'Y',0,113,'de.metas.vertical.pharma.securpharm','AD_Org_ID','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-18T13:23:20.029
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:20.035
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2019-04-18T13:23:20.439
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,36,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,541349,567742,'Y',0,576642,'de.metas.vertical.pharma.securpharm','ApplicationUUID','Application UUID')
;

-- 2019-04-18T13:23:20.449
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:20.465
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576642) 
;

-- 2019-04-18T13:23:20.647
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (40,1000,0,'N','N','N','N',1,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,541349,567743,'Y',0,576641,'de.metas.vertical.pharma.securpharm','AuthPharmaRestApiBaseURL','Pharma Auth REST API URL')
;

-- 2019-04-18T13:23:20.649
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567743 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:20.652
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576641) 
;

-- 2019-04-18T13:23:20.825
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (10,200,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,541349,567744,'Y',0,576646,'de.metas.vertical.pharma.securpharm','CertificatePath','','Zertifikatpfad')
;

-- 2019-04-18T13:23:20.833
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:20.837
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576646) 
;

-- 2019-04-18T13:23:21.023
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 13:23:20','YYYY-MM-DD HH24:MI:SS'),100,541349,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',567745,'Y',0,245,'de.metas.vertical.pharma.securpharm','Created','Datum, an dem dieser Eintrag erstellt wurde','Erstellt')
;

-- 2019-04-18T13:23:21.035
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:21.040
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2019-04-18T13:23:21.522
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 13:23:21','YYYY-MM-DD HH24:MI:SS'),100,541349,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,567746,'Y',0,246,'de.metas.vertical.pharma.securpharm','CreatedBy','Nutzer, der diesen Eintrag erstellt hat','Erstellt durch')
;

-- 2019-04-18T13:23:21.524
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:21.527
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2019-04-18T13:23:21.864
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:21','YYYY-MM-DD HH24:MI:SS'),100,541349,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',567747,'Y',0,348,'de.metas.vertical.pharma.securpharm','IsActive','Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-18T13:23:21.874
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:21.878
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2019-04-18T13:23:22.323
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,576650,0,'M_Securpharm_Productdata_Result_ID','de.metas.vertical.pharma.securpharm','SecurPharm product data result','SecurPharm product data result')
;

-- 2019-04-18T13:23:22.335
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576650 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T13:23:22.457
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (13,10,0,'Y','N','N','Y',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,541349,567748,'Y',0,576650,'de.metas.vertical.pharma.securpharm','M_Securpharm_Productdata_Result_ID','SecurPharm product data result')
;

-- 2019-04-18T13:23:22.462
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:22.465
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576650) 
;

-- 2019-04-18T13:23:22.624
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (40,1000,0,'N','N','N','N',1,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,541349,567749,'Y',0,576638,'de.metas.vertical.pharma.securpharm','PharmaRestApiBaseURL','Pharma REST API URL')
;

-- 2019-04-18T13:23:22.646
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:22.649
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576638) 
;

-- 2019-04-18T13:23:22.839
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (30,'',10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,541349,'',110,567750,'Y',0,576645,'de.metas.vertical.pharma.securpharm','Support_User_ID','Benutzer für Benachrichtigungen ','Support Benutzer')
;

-- 2019-04-18T13:23:22.842
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567750 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:22.845
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576645) 
;

-- 2019-04-18T13:23:22.990
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (10,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,541349,567751,'Y',0,576643,'de.metas.vertical.pharma.securpharm','TanPassword','TAN Passwort benutzt für Authentifizierung','TAN Passwort')
;

-- 2019-04-18T13:23:22.993
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567751 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:22.996
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576643) 
;

-- 2019-04-18T13:23:23.165
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 13:23:23','YYYY-MM-DD HH24:MI:SS'),100,541349,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',567752,'Y',0,607,'de.metas.vertical.pharma.securpharm','Updated','Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert')
;

-- 2019-04-18T13:23:23.173
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:23.177
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2019-04-18T13:23:23.519
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 13:23:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 13:23:23','YYYY-MM-DD HH24:MI:SS'),100,541349,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,567753,'Y',0,608,'de.metas.vertical.pharma.securpharm','UpdatedBy','Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch')
;

-- 2019-04-18T13:23:23.532
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T13:23:23.543
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2019-04-18T14:35:54.683
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=20, Help=NULL, AD_Element_ID=576649, ColumnName='ProductCode', Description=NULL, Name='Produktcode',Updated=TO_TIMESTAMP('2019-04-18 14:35:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567742
;

-- 2019-04-18T14:35:54.694
-- URL zum Konzept
UPDATE AD_Field SET Name='Produktcode', Description=NULL, Help=NULL WHERE AD_Column_ID=567742
;

-- 2019-04-18T14:35:54.703
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576649) 
;

-- 2019-04-18T14:38:49.724
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 14:38:49','YYYY-MM-DD HH24:MI:SS'),100,576651,0,'ProductCodeType','de.metas.vertical.pharma.securpharm','Kodierungskennzeichen','Kodierungskennzeichen')
;

-- 2019-04-18T14:38:49.754
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576651 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T14:39:05.587
-- URL zum Konzept
UPDATE AD_Element SET Description='Kodierungskennzeichen',Updated=TO_TIMESTAMP('2019-04-18 14:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576651
;

-- 2019-04-18T14:39:05.601
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ProductCodeType', Name='Kodierungskennzeichen', Description='Kodierungskennzeichen', Help=NULL WHERE AD_Element_ID=576651
;

-- 2019-04-18T14:39:05.604
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ProductCodeType', Name='Kodierungskennzeichen', Description='Kodierungskennzeichen', Help=NULL, AD_Element_ID=576651 WHERE UPPER(ColumnName)='PRODUCTCODETYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-04-18T14:39:05.615
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ProductCodeType', Name='Kodierungskennzeichen', Description='Kodierungskennzeichen', Help=NULL WHERE AD_Element_ID=576651 AND IsCentrallyMaintained='Y'
;

-- 2019-04-18T14:39:05.617
-- URL zum Konzept
UPDATE AD_Field SET Name='Kodierungskennzeichen', Description='Kodierungskennzeichen', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576651) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576651)
;

-- 2019-04-18T14:39:05.655
-- URL zum Konzept
UPDATE AD_Tab SET Name='Kodierungskennzeichen', Description='Kodierungskennzeichen', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576651
;

-- 2019-04-18T14:39:05.660
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Kodierungskennzeichen', Description='Kodierungskennzeichen', Help=NULL WHERE AD_Element_ID = 576651
;

-- 2019-04-18T14:39:05.664
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Kodierungskennzeichen', Description = 'Kodierungskennzeichen', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576651
;

-- 2019-04-18T14:39:34.610
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Product code type', IsTranslated='Y', Name='Product code type',Updated=TO_TIMESTAMP('2019-04-18 14:39:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576651
;

-- 2019-04-18T14:39:34.615
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576651,'en_US') 
;

-- 2019-04-18T14:43:04.064
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=4, IsUpdateable='N', Help=NULL, AD_Element_ID=576651, ColumnName='ProductCodeType', Description='Kodierungskennzeichen', Name='Kodierungskennzeichen',Updated=TO_TIMESTAMP('2019-04-18 14:43:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567743
;

-- 2019-04-18T14:43:04.074
-- URL zum Konzept
UPDATE AD_Field SET Name='Kodierungskennzeichen', Description='Kodierungskennzeichen', Help=NULL WHERE AD_Column_ID=567743
;

-- 2019-04-18T14:43:04.078
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576651) 
;

-- 2019-04-18T14:43:23.491
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2019-04-18 14:43:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567742
;
--
-- moved to 5519609_sys_gh5139_add_important_AD_Elements.sql
-- -- 2019-04-18T14:46:19.197
-- -- URL zum Konzept
-- INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 14:46:19','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 14:46:19','YYYY-MM-DD HH24:MI:SS'),100,576652,0,'LotNumber','de.metas.vertical.pharma.securpharm','Chargennummer','Chargennummer')
-- ;
--
-- -- 2019-04-18T14:46:19.207
-- -- URL zum Konzept
-- INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576652 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
-- ;
--
-- -- 2019-04-18T14:46:41.766
-- -- URL zum Konzept
-- UPDATE AD_Element_Trl SET PrintName='Lot number', IsTranslated='Y', Name='Lot number',Updated=TO_TIMESTAMP('2019-04-18 14:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576652
-- ;
--
-- -- 2019-04-18T14:46:41.770
-- -- URL zum Konzept
-- /* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576652,'en_US')
-- ;

-- 2019-04-18T14:50:10.151
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=20, IsUpdateable='N', Help=NULL, AD_Element_ID=576652, ColumnName='LotNumber', Description=NULL, Name='Chargennummer',Updated=TO_TIMESTAMP('2019-04-18 14:50:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567744
;

-- 2019-04-18T14:50:10.174
-- URL zum Konzept
UPDATE AD_Field SET Name='Chargennummer', Description=NULL, Help=NULL WHERE AD_Column_ID=567744
;

-- 2019-04-18T14:50:10.180
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576652) 
;

-- 2019-04-18T14:53:16.539
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 14:53:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 14:53:16','YYYY-MM-DD HH24:MI:SS'),100,576653,0,'ExpirationDate','de.metas.vertical.pharma.securpharm','Mindesthaltbarkeit','Mindesthaltbarkeit')
;

-- 2019-04-18T14:53:16.553
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576653 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T14:53:36.798
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Expiration date', IsTranslated='Y', Name='Expiration date',Updated=TO_TIMESTAMP('2019-04-18 14:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576653
;

-- 2019-04-18T14:53:36.800
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576653,'en_US') 
;

-- 2019-04-18T14:55:24.122
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=15, FieldLength=7, IsUpdateable='N', Help=NULL, AD_Element_ID=576653, ColumnName='ExpirationDate', Description=NULL, Name='Mindesthaltbarkeit',Updated=TO_TIMESTAMP('2019-04-18 14:55:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567749
;

-- 2019-04-18T14:55:24.128
-- URL zum Konzept
UPDATE AD_Field SET Name='Mindesthaltbarkeit', Description=NULL, Help=NULL WHERE AD_Column_ID=567749
;

-- 2019-04-18T14:55:24.132
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576653) 
;

-- 2019-04-18T14:58:21.753
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 14:58:21','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 14:58:21','YYYY-MM-DD HH24:MI:SS'),100,576654,0,'SerialNumber','de.metas.vertical.pharma.securpharm','Seriennummer','Seriennummer')
;

-- 2019-04-18T14:58:21.760
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576654 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T14:58:40.535
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Serial number', IsTranslated='Y', Name='Serial number',Updated=TO_TIMESTAMP('2019-04-18 14:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576654
;

-- 2019-04-18T14:58:40.538
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576654,'en_US') 
;

-- 2019-04-18T14:59:36.563
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=200, IsUpdateable='N', Help=NULL, AD_Element_ID=576654, ColumnName='SerialNumber', Description=NULL, Name='Seriennummer',Updated=TO_TIMESTAMP('2019-04-18 14:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567750
;

-- 2019-04-18T14:59:36.567
-- URL zum Konzept
UPDATE AD_Field SET Name='Seriennummer', Description=NULL, Help=NULL WHERE AD_Column_ID=567750
;

-- 2019-04-18T14:59:36.573
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576654) 
;

-- 2019-04-18T14:59:44.573
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2019-04-18 14:59:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567744
;

-- 2019-04-18T14:59:52.290
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2019-04-18 14:59:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567742
;

-- 2019-04-18T15:00:01.233
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2019-04-18 15:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567743
;

-- 2019-04-18T15:02:35.896
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 15:02:35','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 15:02:35','YYYY-MM-DD HH24:MI:SS'),100,576655,0,'hasActiveStatus','de.metas.vertical.pharma.securpharm','Aktiv Status','Aktiv Status')
;

-- 2019-04-18T15:02:35.901
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576655 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T15:02:56.874
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Active status', IsTranslated='Y', Name='Active status',Updated=TO_TIMESTAMP('2019-04-18 15:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576655
;

-- 2019-04-18T15:02:56.877
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576655,'en_US') 
;

-- 2019-04-18T15:04:10.775
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N', FieldLength=1, IsUpdateable='N', Help=NULL, AD_Element_ID=576655, ColumnName='hasActiveStatus', Description=NULL, Name='Aktiv Status',Updated=TO_TIMESTAMP('2019-04-18 15:04:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567751
;

-- 2019-04-18T15:04:10.778
-- URL zum Konzept
UPDATE AD_Field SET Name='Aktiv Status', Description=NULL, Help=NULL WHERE AD_Column_ID=567751
;

-- 2019-04-18T15:04:10.780
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576655) 
;

-- 2019-04-18T15:07:03.630
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 15:07:03','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 15:07:03','YYYY-MM-DD HH24:MI:SS'),100,576656,0,'InactiveReason','de.metas.vertical.pharma.securpharm','Inaktiv Grund','Inaktiv Grund')
;

-- 2019-04-18T15:07:03.635
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576656 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T15:07:30.053
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Inactive reason', IsTranslated='Y', Name='Inactive reason',Updated=TO_TIMESTAMP('2019-04-18 15:07:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576656
;

-- 2019-04-18T15:07:30.058
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576656,'en_US') 
;

-- 2019-04-18T15:09:55.156
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (10,200,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:09:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:09:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541349,'N',567754,'N','N','N','N','N','N','N','N',0,0,576656,'de.metas.vertical.pharma.securpharm','N','N','InactiveReason','N','Inaktiv Grund')
;

-- 2019-04-18T15:09:55.169
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:09:55.177
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576656) 
;

-- 2019-04-18T15:11:22.220
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','Y','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:22','YYYY-MM-DD HH24:MI:SS'),100,541349,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',567755,'N',0,187,'de.metas.vertical.pharma.securpharm','C_BPartner_ID','Bezeichnet einen Geschäftspartner','Geschäftspartner')
;

-- 2019-04-18T15:11:22.228
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567755 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:22.272
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2019-04-18T15:11:23.002
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:22','YYYY-MM-DD HH24:MI:SS'),100,541349,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',567756,'N',0,193,'de.metas.vertical.pharma.securpharm','C_Currency_ID','Die Währung für diesen Eintrag','Währung')
;

-- 2019-04-18T15:11:23.007
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567756 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:23.011
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- 2019-04-18T15:11:23.396
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,541349,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',567757,'N',0,558,'de.metas.vertical.pharma.securpharm','C_Order_ID','Auftrag','Auftrag')
;

-- 2019-04-18T15:11:23.400
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567757 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:23.403
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- 2019-04-18T15:11:23.632
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (17,50,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,541349,'Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.',195,567758,'Y',0,1143,'de.metas.vertical.pharma.securpharm','PaymentRule','Wie die Rechnung bezahlt wird','Zahlungsweise')
;

-- 2019-04-18T15:11:23.639
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567758 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:23.642
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1143) 
;

-- 2019-04-18T15:11:23.819
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (16,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,541349,567759,'Y',0,576235,'de.metas.vertical.pharma.securpharm','RequestEndTime','Anfrage Ende')
;

-- 2019-04-18T15:11:23.822
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:23.825
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576235) 
;

-- 2019-04-18T15:11:23.994
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (37,14,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:23','YYYY-MM-DD HH24:MI:SS'),100,541349,567760,'N',0,576232,'de.metas.vertical.pharma.securpharm','RequestPrice','Preis per Überprüfung')
;

-- 2019-04-18T15:11:24.001
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567760 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:24.004
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576232) 
;

-- 2019-04-18T15:11:24.183
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (16,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,541349,567761,'Y',0,576234,'de.metas.vertical.pharma.securpharm','RequestStartTime','Anfrage Start ')
;

-- 2019-04-18T15:11:24.196
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567761 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:24.205
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576234) 
;

-- 2019-04-18T15:11:24.374
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (17,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,541349,540961,567762,'Y',0,576236,'de.metas.vertical.pharma.securpharm','ResponseCode','Antwort ')
;

-- 2019-04-18T15:11:24.378
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567762 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:24.382
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576236) 
;

-- 2019-04-18T15:11:24.586
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (17,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,541349,540961,567763,'N',0,576511,'de.metas.vertical.pharma.securpharm','ResponseCodeEffective','Antwort eff.')
;

-- 2019-04-18T15:11:24.589
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567763 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:24.592
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576511) 
;

-- 2019-04-18T15:11:24.770
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (17,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,541349,540961,567764,'N',0,576512,'de.metas.vertical.pharma.securpharm','ResponseCodeOverride','Antwort abw.')
;

-- 2019-04-18T15:11:24.775
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567764 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:24.779
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576512) 
;

-- 2019-04-18T15:11:24.933
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,128,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:24','YYYY-MM-DD HH24:MI:SS'),100,541349,567765,'N',0,576237,'de.metas.vertical.pharma.securpharm','ResponseCodeText','Antwort Text')
;

-- 2019-04-18T15:11:24.941
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567765 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:24.946
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576237) 
;

-- 2019-04-18T15:11:25.131
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,512,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:25','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:25','YYYY-MM-DD HH24:MI:SS'),100,541349,567766,'N',0,576238,'de.metas.vertical.pharma.securpharm','ResponseDetails','Antwort Details')
;

-- 2019-04-18T15:11:25.136
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567766 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:25.141
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576238) 
;

-- 2019-04-18T15:11:25.286
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,32,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:25','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:25','YYYY-MM-DD HH24:MI:SS'),100,541349,567767,'N',0,576239,'de.metas.vertical.pharma.securpharm','TransactionCustomerId','Transaktionsreferenz Kunde ')
;

-- 2019-04-18T15:11:25.289
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567767 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:25.291
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576239) 
;

-- 2019-04-18T15:11:25.478
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,32,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:11:25','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:11:25','YYYY-MM-DD HH24:MI:SS'),100,541349,567768,'N',0,576240,'de.metas.vertical.pharma.securpharm','TransactionIdAPI','Transaktionsreferenz API')
;

-- 2019-04-18T15:11:25.492
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567768 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:11:25.496
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576240) 
;

-- 2019-04-18T15:12:46.479
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567755
;

-- 2019-04-18T15:12:46.535
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567755
;

-- 2019-04-18T15:12:47.049
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567756
;

-- 2019-04-18T15:12:47.062
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567756
;

-- 2019-04-18T15:12:47.337
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567758
;

-- 2019-04-18T15:12:47.348
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567758
;

-- 2019-04-18T15:12:47.604
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567760
;

-- 2019-04-18T15:12:47.619
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567760
;

-- 2019-04-18T15:12:48.001
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567762
;

-- 2019-04-18T15:12:48.011
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567762
;

-- 2019-04-18T15:12:48.232
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567763
;

-- 2019-04-18T15:12:48.242
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567763
;

-- 2019-04-18T15:12:48.454
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567764
;

-- 2019-04-18T15:12:48.462
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567764
;

-- 2019-04-18T15:12:48.703
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567765
;

-- 2019-04-18T15:12:48.712
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567765
;

-- 2019-04-18T15:12:48.952
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567766
;

-- 2019-04-18T15:12:48.963
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567766
;

-- 2019-04-18T15:15:32.205
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 15:15:32','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 15:15:32','YYYY-MM-DD HH24:MI:SS'),100,576657,0,'TransactionIDServer','de.metas.vertical.pharma.securpharm','TransaktionsID Server','TransaktionsID Server')
;

-- 2019-04-18T15:15:32.220
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576657 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T15:15:50.682
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='TransactionID Server', IsTranslated='Y', Name='TransactionID Server',Updated=TO_TIMESTAMP('2019-04-18 15:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576657
;

-- 2019-04-18T15:15:50.684
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576657,'en_US') 
;

-- 2019-04-18T15:16:15.401
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 15:16:15','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 15:16:15','YYYY-MM-DD HH24:MI:SS'),100,576659,0,'TransactionIDClient','de.metas.vertical.pharma.securpharm','TransaktionsID Client','TransaktionsID Client')
;

-- 2019-04-18T15:16:15.404
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576659 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T15:16:31.760
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='TransactionID Client', IsTranslated='Y', Name='TransactionID Client',Updated=TO_TIMESTAMP('2019-04-18 15:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576659
;

-- 2019-04-18T15:16:31.763
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576659,'en_US') 
;

-- 2019-04-18T15:17:11.080
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=36, Help=NULL, AD_Element_ID=576659, ColumnName='TransactionIDClient', Description=NULL, Name='TransaktionsID Client',Updated=TO_TIMESTAMP('2019-04-18 15:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567767
;

-- 2019-04-18T15:17:11.086
-- URL zum Konzept
UPDATE AD_Field SET Name='TransaktionsID Client', Description=NULL, Help=NULL WHERE AD_Column_ID=567767
;

-- 2019-04-18T15:17:11.089
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576659) 
;

-- 2019-04-18T15:17:31.692
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=36, Help=NULL, AD_Element_ID=576657, ColumnName='TransactionIDServer', Description=NULL, Name='TransaktionsID Server',Updated=TO_TIMESTAMP('2019-04-18 15:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567768
;

-- 2019-04-18T15:17:31.694
-- URL zum Konzept
UPDATE AD_Field SET Name='TransaktionsID Server', Description=NULL, Help=NULL WHERE AD_Column_ID=567768
;

-- 2019-04-18T15:17:31.697
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576657) 
;

-- 2019-04-18T15:19:33.585
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 15:19:33','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 15:19:33','YYYY-MM-DD HH24:MI:SS'),100,576662,0,'RequestUrl','de.metas.vertical.pharma.securpharm','Abfrage','Abfrage')
;

-- 2019-04-18T15:19:33.601
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576662 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T15:20:03.597
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Request URL', IsTranslated='Y', Name='Request URL',Updated=TO_TIMESTAMP('2019-04-18 15:20:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576662
;

-- 2019-04-18T15:20:03.601
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576662,'en_US') 
;

-- 2019-04-18T15:20:49.706
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (10,200,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 15:20:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 15:20:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541349,'N',567769,'N','Y','N','N','N','N','N','N',0,0,576662,'de.metas.vertical.pharma.securpharm','N','N','RequestUrl','N','Abfrage')
;

-- 2019-04-18T15:20:49.732
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567769 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T15:20:49.743
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576662) 
;

-- 2019-04-18T15:21:11.926
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-04-18 15:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567767
;

-- 2019-04-18T16:03:10.353
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N', Help=NULL, AD_Element_ID=542140, ColumnName='M_HU_ID', Description=NULL, Name='Handling Unit',Updated=TO_TIMESTAMP('2019-04-18 16:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567757
;

-- 2019-04-18T16:03:10.397
-- URL zum Konzept
UPDATE AD_Field SET Name='Handling Unit', Description=NULL, Help=NULL WHERE AD_Column_ID=567757
;

-- 2019-04-18T16:03:10.401
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(542140) 
;

-- 2019-04-18T16:04:46.088
-- URL zum Konzept
INSERT INTO AD_Table (LoadSeq,AccessLevel,AD_Client_ID,CreatedBy,IsActive,Created,Updated,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,CopyColumnsFromTable,ACTriggerLength,UpdatedBy,IsAutocomplete,IsDLM,AD_Org_ID,AD_Table_ID,EntityType,TableName,PersonalDataCategory,IsEnableRemoteCacheInvalidation,Description,Name) VALUES (0,'3',0,100,'Y',TO_TIMESTAMP('2019-04-18 16:04:45','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2019-04-18 16:04:45','YYYY-MM-DD HH24:MI:SS'),'N','Y','N','N','N','N','L','N',0,100,'N','N',0,541350,'de.metas.vertical.pharma.securpharm','M_Securpharm_Action_Result','NP','N','SecurPharm API action result','SecurPharm action result')
;

-- 2019-04-18T16:04:46.094
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541350 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-04-18T16:04:46.251
-- URL zum Konzept
INSERT INTO AD_Sequence (CurrentNext,IsAudited,StartNewYear,IsActive,IsTableID,Created,CreatedBy,IsAutoSequence,StartNo,IncrementNo,CurrentNextSys,Updated,UpdatedBy,AD_Sequence_ID,AD_Client_ID,Name,AD_Org_ID,Description) VALUES (1000000,'N','N','Y','Y',TO_TIMESTAMP('2019-04-18 16:04:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000000,1,50000,TO_TIMESTAMP('2019-04-18 16:04:46','YYYY-MM-DD HH24:MI:SS'),100,555003,0,'M_Securpharm_Action_Result',0,'Table M_Securpharm_Action_Result')
;

-- 2019-04-18T16:05:14.371
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:14','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:14','YYYY-MM-DD HH24:MI:SS'),100,541350,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',567770,'Y',0,102,'de.metas.vertical.pharma.securpharm','AD_Client_ID','Mandant für diese Installation.','Mandant')
;

-- 2019-04-18T16:05:14.399
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567770 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:14.410
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2019-04-18T16:05:15.234
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Y','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,541350,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',567771,'Y',0,113,'de.metas.vertical.pharma.securpharm','AD_Org_ID','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-18T16:05:15.238
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567771 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:15.240
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2019-04-18T16:05:15.579
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,541350,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',567772,'Y',0,245,'de.metas.vertical.pharma.securpharm','Created','Datum, an dem dieser Eintrag erstellt wurde','Erstellt')
;

-- 2019-04-18T16:05:15.583
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567772 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:15.586
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2019-04-18T16:05:16.014
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 16:05:15','YYYY-MM-DD HH24:MI:SS'),100,541350,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,567773,'Y',0,246,'de.metas.vertical.pharma.securpharm','CreatedBy','Nutzer, der diesen Eintrag erstellt hat','Erstellt durch')
;

-- 2019-04-18T16:05:16.017
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567773 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:16.021
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2019-04-18T16:05:16.488
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (15,7,0,'N','N','N','N',1,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,541350,567774,'Y',0,576653,'de.metas.vertical.pharma.securpharm','ExpirationDate','Mindesthaltbarkeit')
;

-- 2019-04-18T16:05:16.494
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567774 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:16.496
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576653) 
;

-- 2019-04-18T16:05:16.657
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,541350,567775,'Y',0,576655,'de.metas.vertical.pharma.securpharm','hasActiveStatus','Aktiv Status')
;

-- 2019-04-18T16:05:16.666
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567775 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:16.672
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576655) 
;

-- 2019-04-18T16:05:16.827
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,200,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,541350,567776,'N',0,576656,'de.metas.vertical.pharma.securpharm','InactiveReason','Inaktiv Grund')
;

-- 2019-04-18T16:05:16.834
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567776 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:16.840
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576656) 
;

-- 2019-04-18T16:05:17.020
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:16','YYYY-MM-DD HH24:MI:SS'),100,541350,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',567777,'Y',0,348,'de.metas.vertical.pharma.securpharm','IsActive','Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-18T16:05:17.022
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567777 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:17.025
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2019-04-18T16:05:17.489
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,100,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,541350,567778,'Y',0,576652,'de.metas.vertical.pharma.securpharm','LotNumber','Chargennummer')
;

-- 2019-04-18T16:05:17.492
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567778 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:17.495
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576652) 
;

-- 2019-04-18T16:05:17.647
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,541350,567779,'N',0,542140,'de.metas.vertical.pharma.securpharm','M_HU_ID','Handling Unit')
;

-- 2019-04-18T16:05:17.653
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567779 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:17.657
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(542140) 
;

-- 2019-04-18T16:05:17.841
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,576663,0,'M_Securpharm_Action_Result_ID','de.metas.vertical.pharma.securpharm','SecurPharm action result','SecurPharm action result')
;

-- 2019-04-18T16:05:17.850
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576663 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T16:05:17.990
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (13,10,0,'Y','N','N','Y',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N',TO_TIMESTAMP('2019-04-18 16:05:17','YYYY-MM-DD HH24:MI:SS'),100,541350,567780,'Y',0,576663,'de.metas.vertical.pharma.securpharm','M_Securpharm_Action_Result_ID','SecurPharm action result')
;

-- 2019-04-18T16:05:17.994
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:17.998
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576663) 
;

-- 2019-04-18T16:05:18.157
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,100,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,541350,567781,'Y',0,576649,'de.metas.vertical.pharma.securpharm','ProductCode','Produktcode')
;

-- 2019-04-18T16:05:18.161
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567781 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:18.164
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576649) 
;

-- 2019-04-18T16:05:18.368
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (10,10,0,'N','N','N','N',1,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,541350,567782,'Y',0,576651,'de.metas.vertical.pharma.securpharm','ProductCodeType','Kodierungskennzeichen','Kodierungskennzeichen')
;

-- 2019-04-18T16:05:18.372
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:18.374
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576651) 
;

-- 2019-04-18T16:05:18.539
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (16,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,541350,567783,'Y',0,576235,'de.metas.vertical.pharma.securpharm','RequestEndTime','Anfrage Ende')
;

-- 2019-04-18T16:05:18.543
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:18.545
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576235) 
;

-- 2019-04-18T16:05:18.711
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (16,7,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,541350,567784,'Y',0,576234,'de.metas.vertical.pharma.securpharm','RequestStartTime','Anfrage Start ')
;

-- 2019-04-18T16:05:18.714
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:18.716
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576234) 
;

-- 2019-04-18T16:05:18.873
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,200,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,541350,567785,'Y',0,576662,'de.metas.vertical.pharma.securpharm','RequestUrl','Abfrage')
;

-- 2019-04-18T16:05:18.876
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:18.878
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576662) 
;

-- 2019-04-18T16:05:19.048
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,'',200,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,541350,110,567786,'Y',0,576654,'de.metas.vertical.pharma.securpharm','SerialNumber','Seriennummer')
;

-- 2019-04-18T16:05:19.053
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:19.056
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576654) 
;

-- 2019-04-18T16:05:19.238
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,36,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,541350,567787,'Y',0,576659,'de.metas.vertical.pharma.securpharm','TransactionIDClient','TransaktionsID Client')
;

-- 2019-04-18T16:05:19.241
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:19.244
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576659) 
;

-- 2019-04-18T16:05:19.415
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Name) VALUES (10,36,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,541350,567788,'N',0,576657,'de.metas.vertical.pharma.securpharm','TransactionIDServer','TransaktionsID Server')
;

-- 2019-04-18T16:05:19.420
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:19.423
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576657) 
;

-- 2019-04-18T16:05:19.607
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,541350,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',567789,'Y',0,607,'de.metas.vertical.pharma.securpharm','Updated','Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert')
;

-- 2019-04-18T16:05:19.609
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:19.612
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2019-04-18T16:05:19.956
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Y','N','N','N',TO_TIMESTAMP('2019-04-18 16:05:19','YYYY-MM-DD HH24:MI:SS'),100,541350,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,567790,'Y',0,608,'de.metas.vertical.pharma.securpharm','UpdatedBy','Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch')
;

-- 2019-04-18T16:05:19.963
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:05:19.970
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2019-04-18T16:05:31.080
-- URL zum Konzept
UPDATE AD_Table SET Name='SecurPharm Action Result',Updated=TO_TIMESTAMP('2019-04-18 16:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541350
;

-- 2019-04-18T16:10:08.165
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567774
;

-- 2019-04-18T16:10:08.222
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567774
;

-- 2019-04-18T16:10:08.651
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567775
;

-- 2019-04-18T16:10:08.663
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567775
;

-- 2019-04-18T16:10:08.953
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567776
;

-- 2019-04-18T16:10:08.964
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567776
;

-- 2019-04-18T16:10:09.288
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567778
;

-- 2019-04-18T16:10:09.300
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567778
;

-- 2019-04-18T16:10:09.625
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567781
;

-- 2019-04-18T16:10:09.641
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567781
;

-- 2019-04-18T16:10:09.948
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567782
;

-- 2019-04-18T16:10:09.962
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567782
;

-- 2019-04-18T16:13:48.059
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,ColumnName,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-18 16:13:47','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-18 16:13:47','YYYY-MM-DD HH24:MI:SS'),100,576664,0,'DisposalAction','de.metas.vertical.pharma.securpharm','Aktion','Aktion')
;

-- 2019-04-18T16:13:48.077
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576664 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-18T16:13:59.806
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Action', IsTranslated='Y', Name='Action',Updated=TO_TIMESTAMP('2019-04-18 16:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576664
;

-- 2019-04-18T16:13:59.810
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576664,'en_US') 
;

-- 2019-04-18T16:18:33.038
-- URL zum Konzept
UPDATE AD_Table SET Name='SecurPharm Product Data Result',Updated=TO_TIMESTAMP('2019-04-18 16:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541349
;

-- 2019-04-18T16:21:29.325
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567779
;

-- 2019-04-18T16:21:29.343
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567779
;

-- 2019-04-18T16:21:48.512
-- URL zum Konzept
UPDATE AD_Column SET Help='', AD_Element_ID=152, ColumnName='Action', Description='', Name='Aktion',Updated=TO_TIMESTAMP('2019-04-18 16:21:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567786
;

-- 2019-04-18T16:21:48.517
-- URL zum Konzept
UPDATE AD_Field SET Name='Aktion', Description='', Help='' WHERE AD_Column_ID=567786
;

-- 2019-04-18T16:21:48.519
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(152) 
;

-- 2019-04-18T16:21:54.795
-- URL zum Konzept
UPDATE AD_Element SET EntityType='de.metas.vertical.pharma.securpharm',Updated=TO_TIMESTAMP('2019-04-18 16:21:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=152
;

-- 2019-04-18T16:22:18.891
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2019-04-18 16:22:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567786
;

-- 2019-04-18T16:24:54.753
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-18 16:24:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-18 16:24:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541350,'N',567791,'N','Y','N','N','N','N','N','N',0,0,2395,'de.metas.vertical.pharma.securpharm','N','N','IsError','Ein Fehler ist bei der Durchführung aufgetreten','N','Fehler')
;

-- 2019-04-18T16:24:54.758
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-18T16:24:54.776
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2395) 
;

-- 2019-04-18T16:25:57.523
-- URL zum Konzept
/* DDL */ CREATE TABLE public.M_Securpharm_Productdata_Result (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExpirationDate TIMESTAMP WITHOUT TIME ZONE NOT NULL, hasActiveStatus CHAR(1) DEFAULT 'N' CHECK (hasActiveStatus IN ('Y','N')) NOT NULL, InactiveReason VARCHAR(200), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, LotNumber VARCHAR(100) NOT NULL, M_HU_ID NUMERIC(10), M_Securpharm_Productdata_Result_ID NUMERIC(10) NOT NULL, ProductCode VARCHAR(100) NOT NULL, ProductCodeType VARCHAR(10) NOT NULL, RequestEndTime TIMESTAMP WITH TIME ZONE NOT NULL, RequestStartTime TIMESTAMP WITH TIME ZONE NOT NULL, RequestUrl VARCHAR(200) NOT NULL, SerialNumber VARCHAR(200) NOT NULL, TransactionIDClient VARCHAR(36) NOT NULL, TransactionIDServer VARCHAR(36), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MHU_MSecurpharmProductdataResult FOREIGN KEY (M_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Securpharm_Productdata_Result_Key PRIMARY KEY (M_Securpharm_Productdata_Result_ID))
;

-- 2019-04-18T16:26:05.281
-- URL zum Konzept
/* DDL */ CREATE TABLE public.M_Securpharm_Action_Result (Action VARCHAR(100) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsError CHAR(1) DEFAULT 'N' CHECK (IsError IN ('Y','N')) NOT NULL, M_Securpharm_Action_Result_ID NUMERIC(10) NOT NULL, RequestEndTime TIMESTAMP WITH TIME ZONE NOT NULL, RequestStartTime TIMESTAMP WITH TIME ZONE NOT NULL, RequestUrl VARCHAR(200) NOT NULL, TransactionIDClient VARCHAR(36) NOT NULL, TransactionIDServer VARCHAR(36), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Securpharm_Action_Result_Key PRIMARY KEY (M_Securpharm_Action_Result_ID))
;

-- 2019-04-19T14:58:21.567
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-19 14:58:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-19 14:58:21','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541350,'N','Bezeichnet die eindeutigen Parameter für eine physische Inventur',567830,'N','N','N','N','N','N','N','N',0,0,1027,'de.metas.vertical.pharma.securpharm','N','N','M_Inventory_ID','Parameter für eine physische Inventur','N','Inventur')
;

-- 2019-04-19T14:58:21.577
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567830 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-19T14:58:21.594
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1027) 
;

-- 2019-04-19T14:58:36.200
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_Securpharm_Action_Result','ALTER TABLE public.M_Securpharm_Action_Result ADD COLUMN M_Inventory_ID NUMERIC(10)')
;

-- 2019-04-19T14:58:36.259
-- URL zum Konzept
ALTER TABLE M_Securpharm_Action_Result ADD CONSTRAINT MInventory_MSecurpharmActionResult FOREIGN KEY (M_Inventory_ID) REFERENCES public.M_Inventory DEFERRABLE INITIALLY DEFERRED
;

-- 2019-04-19T14:59:57.632
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Reference_Value_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (10,'',100,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-19 14:59:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-19 14:59:57','YYYY-MM-DD HH24:MI:SS'),100,541349,'',110,567831,'Y',0,152,'de.metas.vertical.pharma.securpharm','Action','','Aktion')
;

-- 2019-04-19T14:59:57.638
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567831 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-19T14:59:57.653
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(152) 
;

-- 2019-04-19T14:59:57.902
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (20,'N',1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-19 14:59:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-19 14:59:57','YYYY-MM-DD HH24:MI:SS'),100,541349,567832,'Y',0,2395,'de.metas.vertical.pharma.securpharm','IsError','Ein Fehler ist bei der Durchführung aufgetreten','Fehler')
;

-- 2019-04-19T14:59:57.911
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567832 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-19T14:59:57.917
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2395) 
;

-- 2019-04-19T14:59:58.126
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,AD_Table_ID,Help,AD_Column_ID,IsMandatory,AD_Org_ID,AD_Element_ID,EntityType,ColumnName,Description,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-19 14:59:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N',TO_TIMESTAMP('2019-04-19 14:59:58','YYYY-MM-DD HH24:MI:SS'),100,541349,'Bezeichnet die eindeutigen Parameter für eine physische Inventur',567833,'N',0,1027,'de.metas.vertical.pharma.securpharm','M_Inventory_ID','Parameter für eine physische Inventur','Inventur')
;

-- 2019-04-19T14:59:58.129
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567833 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-19T14:59:58.133
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1027) 
;

-- 2019-04-19T15:00:24.751
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_Securpharm_Productdata_Result','ALTER TABLE public.M_Securpharm_Productdata_Result ADD COLUMN IsError CHAR(1) DEFAULT ''N'' CHECK (IsError IN (''Y'',''N'')) NOT NULL')
;

-- 2019-04-19T16:30:16.912
-- URL zum Konzept
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,AD_Org_ID,EntityType,Value) VALUES ('I','Die DataMatrix konnte nicht dekodiert werden. Bitte überprüfe das Ergebnis.',0,'Y',TO_TIMESTAMP('2019-04-19 16:30:16','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-19 16:30:16','YYYY-MM-DD HH24:MI:SS'),100,544910,0,'U','SecurpharmScanResultErrorNotificationMessage')
;

-- 2019-04-19T16:30:16.922
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544910 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-04-19T16:31:01.496
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='Failed to decode the dataMatrix. Please check the result.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-19 16:31:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544910
;

-- 2019-04-19T16:39:40.655
-- URL zum Konzept
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,AD_Org_ID,EntityType,Value) VALUES ('I','Die Dekommissionierungsaktion ist fehlgeschlagen. Bitte überprüfe das Eigenverbrauch und versuche es erneut.',0,'Y',TO_TIMESTAMP('2019-04-19 16:39:40','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-19 16:39:40','YYYY-MM-DD HH24:MI:SS'),100,544911,0,'U','SecurpharmActionResultErrorNotificationMessage')
;

-- 2019-04-19T16:39:40.658
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544911 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-04-19T16:40:30.182
-- URL zum Konzept
UPDATE AD_Message_Trl SET MsgText='The decommission action failed. Please check the inventory and retry.', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-19 16:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544911
;

-- 2019-04-19T18:40:10.959
-- URL zum Konzept
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,Classname,CopyFromProcess,UpdatedBy,AD_Process_ID,IsApplySecuritySettings,AllowProcessReRun,IsUseBPartnerLanguage,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,EntityType,Type,IsTranslateExcelHeaders,Name,Value) VALUES (0,'Y',TO_TIMESTAMP('2019-04-19 18:40:10','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-19 18:40:10','YYYY-MM-DD HH24:MI:SS'),'N','N','3','N','N','N','de.metas.vertical.pharma.securpharm.process.M_HU_SecurpharmScan','N',100,541103,'N','Y','Y','N','N',0,0,'de.metas.vertical.pharma.securpharm','Java','Y','Datamatrix abscannen','M_HU_SecurpharmScan')
;

-- 2019-04-19T18:40:11.050
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541103 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-04-19T18:44:22.784
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,Created,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,FieldLength,IsCentrallyMaintained,IsEncrypted,Updated,UpdatedBy,ColumnName,IsMandatory,IsAutocomplete,SeqNo,AD_Org_ID,EntityType,Description,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-19 18:44:22','YYYY-MM-DD HH24:MI:SS'),100,541103,10,'N',541409,0,'Y','N',TO_TIMESTAMP('2019-04-19 18:44:22','YYYY-MM-DD HH24:MI:SS'),100,'dataMatrix','N','N',10,0,'de.metas.vertical.pharma.securpharm','Die abgescannte Datamatrix','Datamatrix')
;

-- 2019-04-19T18:44:22.794
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541409 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-04-19T18:44:27.251
-- URL zum Konzept
UPDATE AD_Process_Para_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-04-19 18:44:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541409 AND AD_Language='en_US'
;

-- 2019-04-19T18:46:38.030
-- URL zum Konzept
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,EntityType,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,AD_Org_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID) VALUES (TO_TIMESTAMP('2019-04-19 18:46:37','YYYY-MM-DD HH24:MI:SS'),100,'Y',540516,'de.metas.vertical.pharma.securpharm',TO_TIMESTAMP('2019-04-19 18:46:37','YYYY-MM-DD HH24:MI:SS'),100,0,'Y',541103,0,'Y','Y','N','N',540706)
;

-- 2019-04-19T19:05:27.699
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-19 19:05:27','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-19 19:05:27','YYYY-MM-DD HH24:MI:SS'),100,576671,0,'de.metas.vertical.pharma.securpharm','Securpharm Produktdaten Ergebnise','Securpharm Produktdaten Ergebnise')
;

-- 2019-04-19T19:05:27.708
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576671 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-19T19:05:49.314
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Securpharm product data results', IsTranslated='Y', Name='Securpharm product data results',Updated=TO_TIMESTAMP('2019-04-19 19:05:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576671
;

-- 2019-04-19T19:05:49.317
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576671,'en_US') 
;

-- 2019-04-19T19:06:22.390
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,IsActive,Created,CreatedBy,WindowType,Processing,IsSOTrx,WinHeight,WinWidth,IsBetaFunctionality,IsDefault,Updated,UpdatedBy,IsOneInstanceOnly,AD_Window_ID,AD_Org_ID,Name,EntityType,IsEnableRemoteCacheInvalidation,AD_Element_ID) VALUES (0,'Y',TO_TIMESTAMP('2019-04-19 19:06:22','YYYY-MM-DD HH24:MI:SS'),100,'M','N','Y',0,0,'N','N',TO_TIMESTAMP('2019-04-19 19:06:22','YYYY-MM-DD HH24:MI:SS'),100,'N',540634,0,'Securpharm Produktdaten Ergebnise','de.metas.vertical.pharma.securpharm','N',576671)
;

-- 2019-04-19T19:06:22.395
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540634 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-04-19T19:06:22.453
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(576671) 
;

-- 2019-04-19T19:06:22.460
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540634
;

-- 2019-04-19T19:06:22.471
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540634)
;

-- 2019-04-19T19:07:38.232
-- URL zum Konzept
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,AD_Org_ID,Name,EntityType,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Element_ID) VALUES (TO_TIMESTAMP('2019-04-19 19:07:38','YYYY-MM-DD HH24:MI:SS'),'N',540634,10,'N',0,TO_TIMESTAMP('2019-04-19 19:07:38','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','N','N','N','N',0,'N','N','N','Y','Y','Y','N',541349,541749,'N','Y',0,0,'SecurPharm product data result','de.metas.vertical.pharma.securpharm','M_Securpharm_Productdata_Result','Y','N',576650)
;

-- 2019-04-19T19:07:38.238
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541749 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-04-19T19:07:38.248
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(576650) 
;

-- 2019-04-19T19:07:38.257
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541749)
;

-- 2019-04-19T19:08:06.691
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541749,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:06','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:06','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',580018,567740,0,'de.metas.vertical.pharma.securpharm','Mandant für diese Installation.','Mandant')
;

-- 2019-04-19T19:08:06.707
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:06.725
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-04-19T19:08:07.655
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580018
;

-- 2019-04-19T19:08:07.657
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580018)
;

-- 2019-04-19T19:08:07.766
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541749,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:07','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:07','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',580019,567741,0,'de.metas.vertical.pharma.securpharm','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-19T19:08:07.769
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:07.780
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-04-19T19:08:08.078
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580019
;

-- 2019-04-19T19:08:08.080
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580019)
;

-- 2019-04-19T19:08:08.197
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',100,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,580020,567742,0,'de.metas.vertical.pharma.securpharm','Produktcode')
;

-- 2019-04-19T19:08:08.200
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:08.218
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576649) 
;

-- 2019-04-19T19:08:08.241
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580020
;

-- 2019-04-19T19:08:08.247
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580020)
;

-- 2019-04-19T19:08:08.353
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541749,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,580021,567743,0,'de.metas.vertical.pharma.securpharm','Kodierungskennzeichen','Kodierungskennzeichen')
;

-- 2019-04-19T19:08:08.355
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:08.375
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576651) 
;

-- 2019-04-19T19:08:08.385
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580021
;

-- 2019-04-19T19:08:08.388
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580021)
;

-- 2019-04-19T19:08:08.493
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',100,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,580022,567744,0,'de.metas.vertical.pharma.securpharm','Chargennummer')
;

-- 2019-04-19T19:08:08.495
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:08.506
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576652) 
;

-- 2019-04-19T19:08:08.512
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580022
;

-- 2019-04-19T19:08:08.514
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580022)
;

-- 2019-04-19T19:08:08.624
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541749,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:08','YYYY-MM-DD HH24:MI:SS'),100,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',580023,567747,0,'de.metas.vertical.pharma.securpharm','Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-19T19:08:08.626
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:08.638
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2019-04-19T19:08:09.626
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580023
;

-- 2019-04-19T19:08:09.628
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580023)
;

-- 2019-04-19T19:08:09.745
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:09','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:09','YYYY-MM-DD HH24:MI:SS'),100,580024,'N',567748,0,'de.metas.vertical.pharma.securpharm','SecurPharm product data result')
;

-- 2019-04-19T19:08:09.748
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:09.760
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576650) 
;

-- 2019-04-19T19:08:09.765
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580024
;

-- 2019-04-19T19:08:09.767
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580024)
;

-- 2019-04-19T19:08:09.873
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:09','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:09','YYYY-MM-DD HH24:MI:SS'),100,580025,567749,0,'de.metas.vertical.pharma.securpharm','Mindesthaltbarkeit')
;

-- 2019-04-19T19:08:09.877
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:09.890
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576653) 
;

-- 2019-04-19T19:08:09.896
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580025
;

-- 2019-04-19T19:08:09.898
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580025)
;

-- 2019-04-19T19:08:10.002
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',200,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:09','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:09','YYYY-MM-DD HH24:MI:SS'),100,580026,567750,0,'de.metas.vertical.pharma.securpharm','Seriennummer')
;

-- 2019-04-19T19:08:10.005
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.015
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576654) 
;

-- 2019-04-19T19:08:10.019
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580026
;

-- 2019-04-19T19:08:10.021
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580026)
;

-- 2019-04-19T19:08:10.134
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,580027,567751,0,'de.metas.vertical.pharma.securpharm','Aktiv Status')
;

-- 2019-04-19T19:08:10.137
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.147
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576655) 
;

-- 2019-04-19T19:08:10.152
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580027
;

-- 2019-04-19T19:08:10.154
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580027)
;

-- 2019-04-19T19:08:10.257
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',200,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,580028,567754,0,'de.metas.vertical.pharma.securpharm','Inaktiv Grund')
;

-- 2019-04-19T19:08:10.260
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.289
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576656) 
;

-- 2019-04-19T19:08:10.308
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580028
;

-- 2019-04-19T19:08:10.311
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580028)
;

-- 2019-04-19T19:08:10.418
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,580029,567757,0,'de.metas.vertical.pharma.securpharm','Handling Unit')
;

-- 2019-04-19T19:08:10.422
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.433
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140) 
;

-- 2019-04-19T19:08:10.460
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580029
;

-- 2019-04-19T19:08:10.462
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580029)
;

-- 2019-04-19T19:08:10.568
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,580030,567759,0,'de.metas.vertical.pharma.securpharm','Anfrage Ende')
;

-- 2019-04-19T19:08:10.570
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.580
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576235) 
;

-- 2019-04-19T19:08:10.586
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580030
;

-- 2019-04-19T19:08:10.588
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580030)
;

-- 2019-04-19T19:08:10.697
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,580031,567761,0,'de.metas.vertical.pharma.securpharm','Anfrage Start ')
;

-- 2019-04-19T19:08:10.700
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.710
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576234) 
;

-- 2019-04-19T19:08:10.713
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580031
;

-- 2019-04-19T19:08:10.715
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580031)
;

-- 2019-04-19T19:08:10.843
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',36,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,580032,567767,0,'de.metas.vertical.pharma.securpharm','TransaktionsID Client')
;

-- 2019-04-19T19:08:10.849
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.866
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576659) 
;

-- 2019-04-19T19:08:10.869
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580032
;

-- 2019-04-19T19:08:10.871
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580032)
;

-- 2019-04-19T19:08:10.977
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',36,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:10','YYYY-MM-DD HH24:MI:SS'),100,580033,567768,0,'de.metas.vertical.pharma.securpharm','TransaktionsID Server')
;

-- 2019-04-19T19:08:10.979
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:10.994
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576657) 
;

-- 2019-04-19T19:08:11.002
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580033
;

-- 2019-04-19T19:08:11.004
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580033)
;

-- 2019-04-19T19:08:11.112
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541749,'Y',200,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,580034,567769,0,'de.metas.vertical.pharma.securpharm','Abfrage')
;

-- 2019-04-19T19:08:11.116
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:11.127
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576662) 
;

-- 2019-04-19T19:08:11.130
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580034
;

-- 2019-04-19T19:08:11.132
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580034)
;

-- 2019-04-19T19:08:11.237
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541749,'Y',100,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,'',580035,567831,0,'de.metas.vertical.pharma.securpharm','','Aktion')
;

-- 2019-04-19T19:08:11.240
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:11.249
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(152) 
;

-- 2019-04-19T19:08:11.270
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580035
;

-- 2019-04-19T19:08:11.273
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580035)
;

-- 2019-04-19T19:08:11.378
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541749,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,580036,567832,0,'de.metas.vertical.pharma.securpharm','Ein Fehler ist bei der Durchführung aufgetreten','Fehler')
;

-- 2019-04-19T19:08:11.382
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:11.395
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395) 
;

-- 2019-04-19T19:08:11.440
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580036
;

-- 2019-04-19T19:08:11.442
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580036)
;

-- 2019-04-19T19:08:11.553
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541749,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-19 19:08:11','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet die eindeutigen Parameter für eine physische Inventur',580037,567833,0,'de.metas.vertical.pharma.securpharm','Parameter für eine physische Inventur','Inventur')
;

-- 2019-04-19T19:08:11.556
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-19T19:08:11.569
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1027) 
;

-- 2019-04-19T19:08:11.584
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580037
;

-- 2019-04-19T19:08:11.586
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580037)
;

-- 2019-04-19T19:08:41.200
-- URL zum Konzept
INSERT INTO AD_UI_Section (Value,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Section_ID,Updated,UpdatedBy,AD_Tab_ID,SeqNo,AD_Org_ID) VALUES ('main',0,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',541304,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,541749,10,0)
;

-- 2019-04-19T19:08:41.211
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541304 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-04-19T19:08:41.337
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',541666,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),541304,10,0)
;

-- 2019-04-19T19:08:41.448
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',541667,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),541304,20,0)
;

-- 2019-04-19T19:08:41.555
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,UIStyle,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',542514,'primary',10,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),541666,100,0,'default')
;

-- 2019-04-19T19:08:41.756
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558928,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',580018,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',10,'N',0,541749,'Mandant für diese Installation.','Mandant')
;

-- 2019-04-19T19:08:41.952
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558929,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',580019,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',20,'N',0,541749,'Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-19T19:08:42.076
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558930,580020,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,TO_TIMESTAMP('2019-04-19 19:08:41','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',30,'N',0,541749,'Produktcode')
;

-- 2019-04-19T19:08:42.189
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558931,580021,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',40,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',40,'N',0,541749,'Kodierungskennzeichen','Kodierungskennzeichen')
;

-- 2019-04-19T19:08:42.306
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558932,580022,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',50,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',50,'N',0,541749,'Chargennummer')
;

-- 2019-04-19T19:08:42.412
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558933,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',580023,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',60,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',60,'N',0,541749,'Der Eintrag ist im System aktiv','Aktiv')
;

-- 2019-04-19T19:08:42.527
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558934,580025,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',70,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',70,'N',0,541749,'Mindesthaltbarkeit')
;

-- 2019-04-19T19:08:42.635
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558935,580026,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',80,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',80,'N',0,541749,'Seriennummer')
;

-- 2019-04-19T19:08:42.751
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558936,580027,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',90,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',90,'N',0,541749,'Aktiv Status')
;

-- 2019-04-19T19:08:42.855
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558937,580028,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',100,'N',0,541749,'Inaktiv Grund')
;

-- 2019-04-19T19:08:42.964
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558938,580029,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',110,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',110,'N',0,541749,'Handling Unit')
;

-- 2019-04-19T19:08:43.081
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558939,580030,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',120,TO_TIMESTAMP('2019-04-19 19:08:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',120,'N',0,541749,'Anfrage Ende')
;

-- 2019-04-19T19:08:43.187
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558940,580031,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',130,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',130,'N',0,541749,'Anfrage Start ')
;

-- 2019-04-19T19:08:43.306
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558941,580032,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',140,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',140,'N',0,541749,'TransaktionsID Client')
;

-- 2019-04-19T19:08:43.417
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558942,580033,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',150,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',150,'N',0,541749,'TransaktionsID Server')
;

-- 2019-04-19T19:08:43.529
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,558943,580034,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',160,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',160,'N',0,541749,'Abfrage')
;

-- 2019-04-19T19:08:43.640
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558944,'',580035,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',170,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',170,'N',0,541749,'','Aktion')
;

-- 2019-04-19T19:08:43.748
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558945,580036,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',180,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',180,'N',0,541749,'Ein Fehler ist bei der Durchführung aufgetreten','Fehler')
;

-- 2019-04-19T19:08:43.865
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,558946,'Bezeichnet die eindeutigen Parameter für eine physische Inventur',580037,'N',542514,0,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',190,TO_TIMESTAMP('2019-04-19 19:08:43','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',190,'N',0,541749,'Parameter für eine physische Inventur','Inventur')
;

-- 2019-04-19T19:09:25.988
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-19 19:09:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',542515,20,TO_TIMESTAMP('2019-04-19 19:09:25','YYYY-MM-DD HH24:MI:SS'),541666,100,0,'org')
;

-- 2019-04-19T19:09:31.852
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-19 19:09:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',542516,30,TO_TIMESTAMP('2019-04-19 19:09:31','YYYY-MM-DD HH24:MI:SS'),541666,100,0,'logic')
;

-- 2019-04-19T19:09:57.911
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-19 19:09:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',542517,40,TO_TIMESTAMP('2019-04-19 19:09:57','YYYY-MM-DD HH24:MI:SS'),541666,100,0,'log')
;

-- 2019-04-19T19:10:03.246
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='data',Updated=TO_TIMESTAMP('2019-04-19 19:10:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542514
;

-- 2019-04-19T19:10:17.426
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='log',Updated=TO_TIMESTAMP('2019-04-19 19:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542514
;

-- 2019-04-19T19:10:20.856
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='data',Updated=TO_TIMESTAMP('2019-04-19 19:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542517
;

-- 2019-04-19T19:10:34.471
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542515, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-19 19:10:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558928
;

-- 2019-04-19T19:10:41.648
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542515, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-19 19:10:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558929
;

-- 2019-04-19T19:10:56.959
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542517, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-19 19:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558930
;

-- 2019-04-19T19:11:10.934
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542517, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-19 19:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558931
;

-- 2019-04-19T19:11:24.813
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542517, SeqNo=30,Updated=TO_TIMESTAMP('2019-04-19 19:11:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558932
;

-- 2019-04-19T19:11:36.187
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542516, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-19 19:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558933
;

-- 2019-04-19T19:11:46.584
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542517, SeqNo=40,Updated=TO_TIMESTAMP('2019-04-19 19:11:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558934
;

-- 2019-04-19T19:12:03.485
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542517, SeqNo=50,Updated=TO_TIMESTAMP('2019-04-19 19:12:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558935
;

-- 2019-04-19T19:12:42.587
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-19 19:12:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',542518,50,TO_TIMESTAMP('2019-04-19 19:12:42','YYYY-MM-DD HH24:MI:SS'),541666,100,0,'status')
;

-- 2019-04-19T19:12:58.181
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542518, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-19 19:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558936
;

-- 2019-04-19T19:13:05.339
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542518, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-19 19:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558937
;

-- 2019-04-19T19:15:37.649
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558946
;

-- 2019-04-19T19:15:48.367
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542516, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-19 19:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558945
;

-- 2019-04-19T19:16:26.246
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=558944
;

-- 2019-04-19T19:17:07.426
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580037
;

-- 2019-04-19T19:17:07.436
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=580037
;

-- 2019-04-19T19:17:07.450
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=580037
;

-- 2019-04-19T19:17:14.953
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580035
;

-- 2019-04-19T19:17:14.956
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=580035
;

-- 2019-04-19T19:17:14.970
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=580035
;

-- 2019-04-19T19:17:43.191
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567833
;

-- 2019-04-19T19:17:43.233
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567833
;

-- 2019-04-19T19:18:59.523
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_action_result','IsError','CHAR(1)',null,'N')
;

-- 2019-04-19T19:18:59.624
-- URL zum Konzept
UPDATE M_Securpharm_Action_Result SET IsError='N' WHERE IsError IS NULL
;

-- 2019-04-19T19:20:04.212
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567831
;

-- 2019-04-19T19:20:04.227
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=567831
;

-- 2019-04-19T19:21:28.326
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','TransactionIDServer','VARCHAR(36)',null,null)
;

-- 2019-04-19T19:21:50.637
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2019-04-19T19:24:04.419
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2019-04-19 19:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558940
;

-- 2019-04-19T19:24:10.074
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2019-04-19 19:24:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558939
;

-- 2019-04-19T19:24:43.952
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=170,Updated=TO_TIMESTAMP('2019-04-19 19:24:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558938
;

-- 2019-04-19T19:25:30.154
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=10, AD_UI_Column_ID=541667,Updated=TO_TIMESTAMP('2019-04-19 19:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542515
;

-- 2019-04-19T19:25:34.924
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=20, AD_UI_Column_ID=541667,Updated=TO_TIMESTAMP('2019-04-19 19:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542516
;

-- 2019-04-19T19:25:39.834
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=30, AD_UI_Column_ID=541667,Updated=TO_TIMESTAMP('2019-04-19 19:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542518
;

-- 2019-04-19T19:28:45.572
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558928
;

-- 2019-04-19T19:28:45.581
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558929
;

-- 2019-04-19T19:28:45.591
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558940
;

-- 2019-04-19T19:28:45.600
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558939
;

-- 2019-04-19T19:28:45.610
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558941
;

-- 2019-04-19T19:28:45.618
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558942
;

-- 2019-04-19T19:28:45.626
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558943
;

-- 2019-04-19T19:28:45.636
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558930
;

-- 2019-04-19T19:28:45.644
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558931
;

-- 2019-04-19T19:28:45.654
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558932
;

-- 2019-04-19T19:28:45.662
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558934
;

-- 2019-04-19T19:28:45.671
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558935
;

-- 2019-04-19T19:28:45.678
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558945
;

-- 2019-04-19T19:28:45.685
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558938
;

-- 2019-04-19T19:28:45.694
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558936
;

-- 2019-04-19T19:28:45.701
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558937
;

-- 2019-04-19T19:28:45.710
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2019-04-19 19:28:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558933
;

-- 2019-04-23T11:33:48.399
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-23T11:33:48.452
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000083 AND AD_Tree_ID=10
;

-- 2019-04-23T11:33:48.455
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540736 AND AD_Tree_ID=10
;

-- 2019-04-23T11:33:48.463
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540781 AND AD_Tree_ID=10
;

-- 2019-04-23T11:33:48.466
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540782 AND AD_Tree_ID=10
;

-- 2019-04-23T11:33:48.469
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000058 AND AD_Tree_ID=10
;

-- 2019-04-23T11:33:48.471
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000066 AND AD_Tree_ID=10
;

-- 2019-04-23T11:33:48.474
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000076 AND AD_Tree_ID=10
;

-- 2019-04-23T13:08:44.285
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-04-23 13:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567749
;

-- 2019-04-23T13:08:57.633
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','ExpirationDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2019-04-23T13:08:57.672
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','ExpirationDate',null,'NULL',null)
;

-- 2019-04-23T13:09:44.523
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-04-23 13:09:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567744
;

-- 2019-04-23T13:09:55.484
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-04-23 13:09:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567742
;

-- 2019-04-23T13:10:01.545
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-04-23 13:10:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567743
;

-- 2019-04-23T13:10:08.527
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-04-23 13:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567750
;

-- 2019-04-23T13:10:29.062
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','LotNumber','VARCHAR(100)',null,null)
;

-- 2019-04-23T13:10:29.070
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','LotNumber',null,'NULL',null)
;

-- 2019-04-23T13:10:43.670
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','SerialNumber','VARCHAR(200)',null,null)
;

-- 2019-04-23T13:10:43.677
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','SerialNumber',null,'NULL',null)
;

-- 2019-04-23T13:10:52.892
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','ProductCode','VARCHAR(100)',null,null)
;

-- 2019-04-23T13:10:52.899
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','ProductCode',null,'NULL',null)
;

-- 2019-04-23T13:10:55.218
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','ProductCodeType','VARCHAR(10)',null,null)
;

-- 2019-04-23T13:10:55.226
-- URL zum Konzept
INSERT INTO t_alter_column values('m_securpharm_productdata_result','ProductCodeType',null,'NULL',null)
;

-- 2019-04-23T13:13:08.948
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540634,Updated=TO_TIMESTAMP('2019-04-23 13:13:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541349
;

-- 2019-04-23T13:19:24.293
-- URL zum Konzept
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-04-23 13:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541749
;

-- 2019-04-23T13:22:11.895
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,IsActive,Created,CreatedBy,IsSummary,IsSOTrx,IsReadOnly,Updated,UpdatedBy,AD_Menu_ID,IsCreateNew,InternalName,WEBUI_NameBrowse,AD_Org_ID,EntityType,Name,AD_Element_ID) VALUES (0,'Y',TO_TIMESTAMP('2019-04-23 13:22:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N',TO_TIMESTAMP('2019-04-23 13:22:11','YYYY-MM-DD HH24:MI:SS'),100,541253,'N','Securpharm Produktdaten Ergebnise','Securpharm Produktdaten Ergebnise',0,'de.metas.vertical.pharma.securpharm','Securpharm Produktdaten Ergebnise',576671)
;

-- 2019-04-23T13:22:11.921
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541253 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-04-23T13:22:11.933
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541253, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541253)
;

-- 2019-04-23T13:22:12.043
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(576671) 
;

-- 2019-04-23T13:22:20.349
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.354
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000083 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.357
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540736 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.361
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540781 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.365
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540782 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.367
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000058 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.370
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000066 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.372
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000076 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:20.375
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541253 AND AD_Tree_ID=10
;

-- 2019-04-23T13:22:45.812
-- URL zum Konzept
UPDATE AD_Menu SET AD_Window_ID=540634, Action='W',Updated=TO_TIMESTAMP('2019-04-23 13:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541253
;

-- 2019-04-23T14:32:12.360
-- URL zum Konzept
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,Classname,CopyFromProcess,UpdatedBy,AD_Process_ID,IsApplySecuritySettings,AllowProcessReRun,IsUseBPartnerLanguage,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,EntityType,Type,IsTranslateExcelHeaders,Name,Value) VALUES (0,'Y',TO_TIMESTAMP('2019-04-23 14:32:12','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-23 14:32:12','YYYY-MM-DD HH24:MI:SS'),'N','N','3','N','N','N','de.metas.vertical.pharma.securpharm.process.M_Securpharm_Productdata_Result_Retry','N',100,541108,'N','Y','Y','N','N',0,0,'de.metas.vertical.pharma.securpharm','Java','Y','Datamatrix abscannen wiederholen','.M_Securpharm_Productdata_Result_Retry')
;

-- 2019-04-23T14:32:12.376
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541108 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-04-23T14:32:25.115
-- URL zum Konzept
UPDATE AD_Process SET Value='M_Securpharm_Productdata_Result_Retry',Updated=TO_TIMESTAMP('2019-04-23 14:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541108
;

-- 2019-04-23T14:33:27.043
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,Created,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,FieldLength,IsCentrallyMaintained,IsEncrypted,Updated,UpdatedBy,ColumnName,IsMandatory,IsAutocomplete,SeqNo,AD_Org_ID,EntityType,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-23 14:33:26','YYYY-MM-DD HH24:MI:SS'),100,541108,10,'N',541422,0,'Y','N',TO_TIMESTAMP('2019-04-23 14:33:26','YYYY-MM-DD HH24:MI:SS'),100,'datamatrix','Y','N',10,0,'U','Datamatrix')
;

-- 2019-04-23T14:33:27.048
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541422 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-04-23T14:33:37.104
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-04-23 14:33:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541409
;

-- 2019-04-23T14:35:05.903
-- URL zum Konzept
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,EntityType,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,AD_Org_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID) VALUES (TO_TIMESTAMP('2019-04-23 14:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',541349,'de.metas.vertical.pharma.securpharm',TO_TIMESTAMP('2019-04-23 14:35:05','YYYY-MM-DD HH24:MI:SS'),100,0,'N',541108,0,'Y','Y','Y','N',540707)
;

-- 2019-04-23T14:35:15.402
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_ViewQuickAction='Y', WEBUI_IncludedTabTopAction='N',Updated=TO_TIMESTAMP('2019-04-23 14:35:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540707
;

-- 2019-04-24T11:32:56.913
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-24 11:32:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-24 11:32:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541350,'N',567886,'N','Y','N','N','N','N','N','N',0,0,576650,'de.metas.vertical.pharma.securpharm','N','N','M_Securpharm_Productdata_Result_ID','N','SecurPharm product data result')
;

-- 2019-04-24T11:32:56.938
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567886 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-24T11:32:56.948
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(576650) 
;

-- 2019-04-24T11:33:13.782
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_Securpharm_Action_Result','ALTER TABLE public.M_Securpharm_Action_Result ADD COLUMN M_Securpharm_Productdata_Result_ID NUMERIC(10) NOT NULL')
;

-- 2019-04-24T11:33:13.816
-- URL zum Konzept
ALTER TABLE M_Securpharm_Action_Result ADD CONSTRAINT MSecurpharmProductdataResult_MSecurpharmActionResult FOREIGN KEY (M_Securpharm_Productdata_Result_ID) REFERENCES public.M_Securpharm_Productdata_Result DEFERRABLE INITIALLY DEFERRED
;

-- 2019-04-24T16:03:51.414
-- URL zum Konzept
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2019-04-24 16:03:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=580031
;

-- 2019-04-24T16:14:52.849
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,AD_Org_ID,EntityType,PrintName,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-04-24 16:14:52','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-24 16:14:52','YYYY-MM-DD HH24:MI:SS'),100,576684,0,'de.metas.vertical.pharma.securpharm','Securpharm Aktion Ergebnise','Securpharm Aktion Ergebnise')
;

-- 2019-04-24T16:14:52.859
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Description,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Description,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576684 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-24T16:15:21.699
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Securpharm action results', IsTranslated='Y', Name='Securpharm action results',Updated=TO_TIMESTAMP('2019-04-24 16:15:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576684
;

-- 2019-04-24T16:15:21.723
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576684,'en_US') 
;

-- 2019-04-24T16:16:34.208
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,IsActive,Created,CreatedBy,WindowType,Processing,IsSOTrx,WinHeight,WinWidth,IsBetaFunctionality,IsDefault,Updated,UpdatedBy,IsOneInstanceOnly,AD_Window_ID,AD_Org_ID,Name,EntityType,IsEnableRemoteCacheInvalidation,AD_Element_ID) VALUES (0,'Y',TO_TIMESTAMP('2019-04-24 16:16:34','YYYY-MM-DD HH24:MI:SS'),100,'M','N','Y',0,0,'N','N',TO_TIMESTAMP('2019-04-24 16:16:34','YYYY-MM-DD HH24:MI:SS'),100,'N',540637,0,'Securpharm Aktion Ergebnise','de.metas.vertical.pharma.securpharm','N',576684)
;

-- 2019-04-24T16:16:34.212
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540637 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-04-24T16:16:34.419
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(576684) 
;

-- 2019-04-24T16:16:34.454
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540637
;

-- 2019-04-24T16:16:34.460
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540637)
;

-- 2019-04-24T16:17:42.449
-- URL zum Konzept
INSERT INTO AD_Tab (Created,HasTree,AD_Window_ID,SeqNo,IsSingleRow,AD_Client_ID,Updated,IsActive,CreatedBy,UpdatedBy,IsInfoTab,IsTranslationTab,IsReadOnly,Processing,IsSortTab,ImportFields,TabLevel,IsInsertRecord,IsAdvancedTab,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsQueryOnLoad,IsGridModeOnly,AD_Table_ID,AD_Tab_ID,IsGenericZoomTarget,IsCheckParentsChanged,MaxQueryRecords,AD_Org_ID,Name,EntityType,InternalName,AllowQuickInput,IsRefreshViewOnChangeEvents,AD_Element_ID) VALUES (TO_TIMESTAMP('2019-04-24 16:17:42','YYYY-MM-DD HH24:MI:SS'),'N',540637,10,'N',0,TO_TIMESTAMP('2019-04-24 16:17:42','YYYY-MM-DD HH24:MI:SS'),'Y',100,100,'N','N','Y','N','N','N',0,'N','N','N','Y','Y','Y','N',541350,541753,'N','Y',0,0,'SecurPharm action result','de.metas.vertical.pharma.securpharm','M_Securpharm_Action_Result','Y','N',576663)
;

-- 2019-04-24T16:17:42.454
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541753 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-04-24T16:17:42.464
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(576663) 
;

-- 2019-04-24T16:17:42.469
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(541753)
;

-- 2019-04-24T16:17:57.668
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541753,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:57','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',580103,567770,0,'de.metas.vertical.pharma.securpharm','Mandant für diese Installation.','Mandant')
;

-- 2019-04-24T16:17:57.677
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:57.692
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2019-04-24T16:17:58.539
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580103
;

-- 2019-04-24T16:17:58.541
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580103)
;

-- 2019-04-24T16:17:58.676
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541753,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:58','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',580104,567771,0,'de.metas.vertical.pharma.securpharm','Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-24T16:17:58.685
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:58.697
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2019-04-24T16:17:58.998
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580104
;

-- 2019-04-24T16:17:59
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580104)
;

-- 2019-04-24T16:17:59.115
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541753,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,580105,'N',567780,0,'de.metas.vertical.pharma.securpharm','SecurPharm action result')
;

-- 2019-04-24T16:17:59.118
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:59.130
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576663) 
;

-- 2019-04-24T16:17:59.136
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580105
;

-- 2019-04-24T16:17:59.138
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580105)
;

-- 2019-04-24T16:17:59.257
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541753,'Y',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,580106,567783,0,'de.metas.vertical.pharma.securpharm','Anfrage Ende')
;

-- 2019-04-24T16:17:59.260
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:59.272
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576235) 
;

-- 2019-04-24T16:17:59.279
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580106
;

-- 2019-04-24T16:17:59.282
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580106)
;

-- 2019-04-24T16:17:59.395
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541753,'Y',7,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,580107,567784,0,'de.metas.vertical.pharma.securpharm','Anfrage Start ')
;

-- 2019-04-24T16:17:59.398
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:59.408
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576234) 
;

-- 2019-04-24T16:17:59.417
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580107
;

-- 2019-04-24T16:17:59.419
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580107)
;

-- 2019-04-24T16:17:59.546
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541753,'Y',200,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,580108,567785,0,'de.metas.vertical.pharma.securpharm','Abfrage')
;

-- 2019-04-24T16:17:59.549
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:59.559
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576662) 
;

-- 2019-04-24T16:17:59.572
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580108
;

-- 2019-04-24T16:17:59.575
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580108)
;

-- 2019-04-24T16:17:59.684
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541753,'Y',100,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'',580109,567786,0,'de.metas.vertical.pharma.securpharm','','Aktion')
;

-- 2019-04-24T16:17:59.692
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:59.713
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(152) 
;

-- 2019-04-24T16:17:59.726
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580109
;

-- 2019-04-24T16:17:59.728
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580109)
;

-- 2019-04-24T16:17:59.857
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541753,'Y',36,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,580110,567787,0,'de.metas.vertical.pharma.securpharm','TransaktionsID Client')
;

-- 2019-04-24T16:17:59.861
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:17:59.876
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576659) 
;

-- 2019-04-24T16:17:59.880
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580110
;

-- 2019-04-24T16:17:59.882
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580110)
;

-- 2019-04-24T16:17:59.999
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541753,'Y',36,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:17:59','YYYY-MM-DD HH24:MI:SS'),100,580111,567788,0,'de.metas.vertical.pharma.securpharm','TransaktionsID Server')
;

-- 2019-04-24T16:18:00.003
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:18:00.019
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576657) 
;

-- 2019-04-24T16:18:00.028
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580111
;

-- 2019-04-24T16:18:00.030
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580111)
;

-- 2019-04-24T16:18:00.133
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541753,'Y',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:18:00','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:18:00','YYYY-MM-DD HH24:MI:SS'),100,580112,567791,0,'de.metas.vertical.pharma.securpharm','Ein Fehler ist bei der Durchführung aufgetreten','Fehler')
;

-- 2019-04-24T16:18:00.135
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:18:00.145
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395) 
;

-- 2019-04-24T16:18:00.163
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580112
;

-- 2019-04-24T16:18:00.165
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580112)
;

-- 2019-04-24T16:18:00.279
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Description,Name) VALUES (541753,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:18:00','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:18:00','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet die eindeutigen Parameter für eine physische Inventur',580113,567830,0,'de.metas.vertical.pharma.securpharm','Parameter für eine physische Inventur','Inventur')
;

-- 2019-04-24T16:18:00.282
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:18:00.292
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1027) 
;

-- 2019-04-24T16:18:00.302
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580113
;

-- 2019-04-24T16:18:00.304
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580113)
;

-- 2019-04-24T16:18:00.422
-- URL zum Konzept
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,AD_Column_ID,AD_Org_ID,EntityType,Name) VALUES (541753,'Y',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-04-24 16:18:00','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-04-24 16:18:00','YYYY-MM-DD HH24:MI:SS'),100,580114,567886,0,'de.metas.vertical.pharma.securpharm','SecurPharm product data result')
;

-- 2019-04-24T16:18:00.425
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=580114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-04-24T16:18:00.435
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576650) 
;

-- 2019-04-24T16:18:00.441
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=580114
;

-- 2019-04-24T16:18:00.444
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(580114)
;

-- 2019-04-24T16:19:41.967
-- URL zum Konzept
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2019-04-24 16:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=580107
;

-- 2019-04-24T16:19:52.282
-- URL zum Konzept
INSERT INTO AD_UI_Section (Value,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Section_ID,Updated,UpdatedBy,AD_Tab_ID,SeqNo,AD_Org_ID) VALUES ('main',0,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',541311,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),100,541753,10,0)
;

-- 2019-04-24T16:19:52.293
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541311 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2019-04-24T16:19:52.503
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',541676,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),541311,10,0)
;

-- 2019-04-24T16:19:52.629
-- URL zum Konzept
INSERT INTO AD_UI_Column (UpdatedBy,AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_Column_ID,Updated,AD_UI_Section_ID,SeqNo,AD_Org_ID) VALUES (100,0,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',541677,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),541311,20,0)
;

-- 2019-04-24T16:19:52.862
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,UIStyle,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',542529,'primary',10,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),541676,100,0,'default')
;

-- 2019-04-24T16:19:53.055
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,559039,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',580103,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2019-04-24 16:19:52','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',10,'N',0,541753,'Mandant für diese Installation.','Mandant')
;

-- 2019-04-24T16:19:53.172
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,559040,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',580104,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',20,'N',0,541753,'Organisatorische Einheit des Mandanten','Sektion')
;

-- 2019-04-24T16:19:53.276
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,559041,580106,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',30,'N',0,541753,'Anfrage Ende')
;

-- 2019-04-24T16:19:53.375
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,559042,580107,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',40,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',40,'N',0,541753,'Anfrage Start ')
;

-- 2019-04-24T16:19:53.496
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,559043,580108,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',50,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',50,'N',0,541753,'Abfrage')
;

-- 2019-04-24T16:19:53.612
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,559044,'',580109,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',60,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',60,'N',0,541753,'','Aktion')
;

-- 2019-04-24T16:19:53.721
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,559045,580110,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',70,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',70,'N',0,541753,'TransaktionsID Client')
;

-- 2019-04-24T16:19:53.820
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,559046,580111,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',80,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',80,'N',0,541753,'TransaktionsID Server')
;

-- 2019-04-24T16:19:53.944
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,559047,580112,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',90,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',90,'N',0,541753,'Ein Fehler ist bei der Durchführung aufgetreten','Fehler')
;

-- 2019-04-24T16:19:54.058
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Description,Name) VALUES (100,559048,'Bezeichnet die eindeutigen Parameter für eine physische Inventur',580113,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',100,TO_TIMESTAMP('2019-04-24 16:19:53','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',100,'N',0,541753,'Parameter für eine physische Inventur','Inventur')
;

-- 2019-04-24T16:19:54.159
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,Name) VALUES (100,559049,580114,'N',542529,0,TO_TIMESTAMP('2019-04-24 16:19:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',110,TO_TIMESTAMP('2019-04-24 16:19:54','YYYY-MM-DD HH24:MI:SS'),0,'Y','Y',110,'N',0,541753,'SecurPharm product data result')
;

-- 2019-04-24T16:20:44.409
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-24 16:20:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',542530,20,TO_TIMESTAMP('2019-04-24 16:20:44','YYYY-MM-DD HH24:MI:SS'),541676,100,0,'log')
;

-- 2019-04-24T16:21:03.964
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-24 16:21:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',542531,30,TO_TIMESTAMP('2019-04-24 16:21:03','YYYY-MM-DD HH24:MI:SS'),541676,100,0,'org')
;

-- 2019-04-24T16:21:13.769
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-24 16:21:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',542532,40,TO_TIMESTAMP('2019-04-24 16:21:13','YYYY-MM-DD HH24:MI:SS'),541676,100,0,'flags')
;

-- 2019-04-24T16:21:30.307
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542531, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-24 16:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559039
;

-- 2019-04-24T16:21:36.502
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542531, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-24 16:21:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559040
;

-- 2019-04-24T16:21:57.054
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,Created,CreatedBy,IsActive,AD_UI_ElementGroup_ID,SeqNo,Updated,AD_UI_Column_ID,UpdatedBy,AD_Org_ID,Name) VALUES (0,TO_TIMESTAMP('2019-04-24 16:21:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',542533,50,TO_TIMESTAMP('2019-04-24 16:21:56','YYYY-MM-DD HH24:MI:SS'),541676,100,0,'ref')
;

-- 2019-04-24T16:22:16.951
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542533, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-24 16:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559049
;

-- 2019-04-24T16:22:23.941
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542533, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-24 16:22:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559048
;

-- 2019-04-24T16:22:45.594
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542532, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-24 16:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559047
;

-- 2019-04-24T16:23:04.279
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542530, SeqNo=10,Updated=TO_TIMESTAMP('2019-04-24 16:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559041
;

-- 2019-04-24T16:23:13.568
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542530, SeqNo=20,Updated=TO_TIMESTAMP('2019-04-24 16:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559042
;

-- 2019-04-24T16:23:22.042
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542530, SeqNo=30,Updated=TO_TIMESTAMP('2019-04-24 16:23:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559045
;

-- 2019-04-24T16:23:29.300
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542530, SeqNo=40,Updated=TO_TIMESTAMP('2019-04-24 16:23:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559046
;

-- 2019-04-24T16:24:08.723
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2019-04-24 16:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559041
;

-- 2019-04-24T16:24:13.334
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2019-04-24 16:24:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559042
;

-- 2019-04-24T16:24:36.933
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-24 16:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559045
;

-- 2019-04-24T16:24:44.466
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-24 16:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559046
;

-- 2019-04-24T16:24:58.767
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-04-24 16:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559047
;

-- 2019-04-24T16:25:15.638
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-24 16:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559043
;

-- 2019-04-24T16:25:38.314
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2019-04-24 16:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559044
;

-- 2019-04-24T16:25:56.731
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=10, AD_UI_Column_ID=541677,Updated=TO_TIMESTAMP('2019-04-24 16:25:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542533
;

-- 2019-04-24T16:26:07.694
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=20, AD_UI_Column_ID=541677,Updated=TO_TIMESTAMP('2019-04-24 16:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542531
;

-- 2019-04-24T16:26:12.891
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=30, AD_UI_Column_ID=541677,Updated=TO_TIMESTAMP('2019-04-24 16:26:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542532
;

-- 2019-04-24T16:26:30.527
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=10,Updated=TO_TIMESTAMP('2019-04-24 16:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542532
;

-- 2019-04-24T16:26:35.635
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2019-04-24 16:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542533
;

-- 2019-04-24T16:26:40.606
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-04-24 16:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542531
;

-- 2019-04-24T16:27:08.441
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559042
;

-- 2019-04-24T16:27:08.451
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559041
;

-- 2019-04-24T16:27:08.461
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559043
;

-- 2019-04-24T16:27:08.470
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559044
;

-- 2019-04-24T16:27:08.479
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559045
;

-- 2019-04-24T16:27:08.487
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559046
;

-- 2019-04-24T16:27:08.494
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559047
;

-- 2019-04-24T16:27:08.502
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559049
;

-- 2019-04-24T16:27:08.510
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559048
;

-- 2019-04-24T16:27:08.518
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559039
;

-- 2019-04-24T16:27:08.526
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-04-24 16:27:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559040
;

-- 2019-04-24T16:27:15.326
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-04-24 16:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559047
;

-- 2019-04-24T16:27:15.336
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-04-24 16:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559045
;

-- 2019-04-24T16:27:15.344
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-04-24 16:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559046
;

-- 2019-04-24T16:28:51.559
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Window_ID,AD_Client_ID,IsActive,Created,CreatedBy,IsSummary,IsSOTrx,IsReadOnly,Updated,UpdatedBy,AD_Menu_ID,IsCreateNew,InternalName,AD_Org_ID,EntityType,Action,Name,AD_Element_ID) VALUES (540637,0,'Y',TO_TIMESTAMP('2019-04-24 16:28:51','YYYY-MM-DD HH24:MI:SS'),100,'N','N','N',TO_TIMESTAMP('2019-04-24 16:28:51','YYYY-MM-DD HH24:MI:SS'),100,541254,'N','Securpharm Aktion Ergebnise',0,'de.metas.vertical.pharma.securpharm','W','Securpharm Aktion Ergebnise',576684)
;

-- 2019-04-24T16:28:51.564
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541254 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-04-24T16:28:51.573
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541254, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541254)
;

-- 2019-04-24T16:28:51.621
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(576684) 
;

-- 2019-04-24T16:28:51.820
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.823
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000083 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.825
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540736 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.833
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540781 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.837
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540782 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.840
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000058 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.842
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000066 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.845
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000076 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.850
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541253 AND AD_Tree_ID=10
;

-- 2019-04-24T16:28:51.853
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541254 AND AD_Tree_ID=10
;

-- 2019-04-24T16:30:14.060
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540637,Updated=TO_TIMESTAMP('2019-04-24 16:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541350
;

-- 2019-04-24T16:32:59.018
-- URL zum Konzept
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,Classname,CopyFromProcess,UpdatedBy,AD_Process_ID,IsApplySecuritySettings,AllowProcessReRun,IsUseBPartnerLanguage,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,EntityType,Type,IsTranslateExcelHeaders,Name,Value) VALUES (0,'Y',TO_TIMESTAMP('2019-04-24 16:32:58','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-04-24 16:32:58','YYYY-MM-DD HH24:MI:SS'),'N','N','3','N','N','N','de.metas.vertical.pharma.securpharm.process.M_Inventory_SecurpharmActionRetry','N',100,541109,'N','Y','Y','N','N',0,0,'de.metas.vertical.pharma.securpharm','Java','Y','SecurPharm Aktion wiederholen','M_Inventory_SecurpharmActionRetry')
;

-- 2019-04-24T16:32:59.026
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541109 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-04-24T16:34:15.071
-- URL zum Konzept
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,EntityType,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,AD_Org_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID) VALUES (TO_TIMESTAMP('2019-04-24 16:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',321,'D',TO_TIMESTAMP('2019-04-24 16:34:14','YYYY-MM-DD HH24:MI:SS'),100,0,'N',541109,0,'Y','Y','N','N',540708)
;

CREATE INDEX m_securpharm_action_result_inventory_action
  ON public.m_securpharm_action_result
  USING btree
  (m_inventory_id, action)
;

CREATE INDEX m_securpharm_productdata_result_hu
  ON public.m_securpharm_productdata_result
  USING btree
  (m_hu_id)
;

-- 2019-04-24T17:42:23.808
-- URL zum Konzept
UPDATE M_AttributeSet mas SET IsInstanceAttribute='Y' WHERE IsInstanceAttribute='N' AND EXISTS (SELECT * FROM M_AttributeUse mau WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID AND mau.M_Attribute_ID=540040)
;

-- 2019-04-24T17:30:28.217
-- URL zum Konzept
INSERT INTO M_Attribute (CreatedBy,Created,IsActive,Updated,UpdatedBy,M_Attribute_ID,IsMandatory,AD_Client_ID,IsStorageRelevant,IsReadOnlyValues,IsTransferWhenNull,IsPricingRelevant,IsAttrDocumentRelevant,ValueMax,ValueMin,IsInstanceAttribute,AttributeValueType,Value,AD_Org_ID,IsHighVolume,Description,Name) VALUES (100,TO_TIMESTAMP('2019-04-24 17:30:27','YYYY-MM-DD HH24:MI:SS'),'Y',TO_TIMESTAMP('2019-04-24 17:30:27','YYYY-MM-DD HH24:MI:SS'),100,540040,'N',0,'N','Y','N','N','N',0,0,'N','L','HU_scanned',0,'N','Zeigt an ob die Datamatrix einer HU gescannt wurde','Gescannt')
;

-- 2019-04-24T17:41:29.367
-- URL zum Konzept
UPDATE M_Attribute SET Value='HU_Scanned',Updated=TO_TIMESTAMP('2019-04-24 17:41:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540040
;

-- 2019-04-24T17:42:23.806
-- URL zum Konzept
UPDATE M_Attribute SET IsInstanceAttribute='Y',Updated=TO_TIMESTAMP('2019-04-24 17:42:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540040
;

INSERT INTO public.m_attributevalue VALUES (540000, 0, 0, 'Y', '2018-09-11 10:29:42+03', 100, '2019-04-24 17:50:31+03', 100, 540040, 'Y', 'Ja', NULL, NULL, 'N')
;

INSERT INTO public.m_attributevalue VALUES (540001, 0, 0, 'Y', '2018-09-11 10:29:42+03', 100, '2019-04-24 17:50:31+03', 100, 540040, 'N', 'Nein', NULL, NULL, 'N')
;

INSERT INTO public.m_attributevalue VALUES (540002, 0, 0, 'Y', '2018-09-11 10:29:42+03', 100, '2019-04-24 17:50:31+03', 100, 540040, 'E', 'Fehler', NULL, NULL, 'N')
;

-- 2019-04-24T18:09:48.133
-- URL zum Konzept
UPDATE M_Attribute SET IsReadOnlyValues='N',Updated=TO_TIMESTAMP('2019-04-24 18:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Attribute_ID=540040
;

-- 2019-04-24T18:14:01.641
-- URL zum Konzept
INSERT INTO M_HU_PI_Attribute (Created,CreatedBy,IsActive,Updated,UpdatedBy,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,IsMandatory,AD_Client_ID,IsReadOnly,SeqNo,M_Attribute_ID,HU_TansferStrategy_JavaClass_ID,UseInASI,IsDisplayed,IsInstanceAttribute,AD_Org_ID,IsOnlyIfInProductAttributeSet) VALUES (TO_TIMESTAMP('2019-04-24 18:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-04-24 18:14:01','YYYY-MM-DD HH24:MI:SS'),100,540041,100,'NONE','N',1000000,'Y',9050,540040,540027,'Y','Y','N',1000000,'N')
;

-- 2019-04-24T18:43:06.249
-- URL zum Konzept
UPDATE M_AttributeValue SET IsNullFieldValue='Y',Updated=TO_TIMESTAMP('2019-04-24 18:43:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_AttributeValue_ID=540001
;

-- 2019-04-24T19:06:54.776
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541253 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.784
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541055 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.788
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540992 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.791
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540993 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.794
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540999 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.797
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.802
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541084 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.805
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541022 AND AD_Tree_ID=10
;

-- 2019-04-24T19:06:54.807
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541076 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.280
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541254 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.283
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541253 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.286
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541055 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.289
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540992 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.292
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540993 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.294
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540999 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.297
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.299
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541084 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.302
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541022 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:03.305
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541076 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.418
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541246 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.421
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541254 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.423
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541253 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.425
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541055 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.427
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540992 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.429
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540993 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.432
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540999 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.434
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541224 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.436
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541084 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.439
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541022 AND AD_Tree_ID=10
;

-- 2019-04-24T19:07:10.441
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541076 AND AD_Tree_ID=10
;

-- 2019-04-25T14:57:33.231
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2019-04-25 14:57:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558931
;

-- 2019-04-25T14:57:46.890
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-25 14:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558941
;

-- 2019-04-25T14:57:54.667
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-25 14:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558942
;

-- 2019-04-25T14:58:05.403
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2019-04-25 14:58:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558943
;

-- 2019-04-25T14:58:13.519
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2019-04-25 14:58:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558940
;

-- 2019-04-25T14:58:19.865
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2019-04-25 14:58:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558939
;

-- 2019-04-25T15:06:06.605
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='Y',Updated=TO_TIMESTAMP('2019-04-25 15:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540707
;

-- 2019-04-25T15:07:17.648
-- URL zum Konzept
UPDATE AD_Process_Para SET EntityType='de.metas.vertical.pharma.securpharm',Updated=TO_TIMESTAMP('2019-04-25 15:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541422
;

-- 2019-04-25T15:50:45.112
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='dataMatrix',Updated=TO_TIMESTAMP('2019-04-25 15:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541422
;

-- 2019-04-25T18:23:45.134
-- URL zum Konzept
UPDATE M_AttributeValue SET IsNullFieldValue='N',Updated=TO_TIMESTAMP('2019-04-25 18:23:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_AttributeValue_ID=540001
;

-- 2019-05-06T19:13:50.873
-- URL zum Konzept
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,Classname,CopyFromProcess,UpdatedBy,AD_Process_ID,IsApplySecuritySettings,AllowProcessReRun,IsUseBPartnerLanguage,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,AD_Org_ID,EntityType,Type,IsTranslateExcelHeaders,Name,Value) VALUES (0,'Y',TO_TIMESTAMP('2019-05-06 19:13:50','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-05-06 19:13:50','YYYY-MM-DD HH24:MI:SS'),'N','N','3','N','N','N','de.metas.ui.web.handlingunits.process.WEBUI_M_HU_ReturnFromCustomer_Pharma','N',100,541122,'N','N','Y','Y','N',0,0,'de.metas.ui.web','Java','Y','Kunden RÃ¼cklieferung (pharma)','WEBUI_M_HU_ReturnFromCustomer_Pharma')
;

-- 2019-05-06T19:13:50.901
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541122 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-05-06T19:14:47.869
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Return from customer (pharma)',Updated=TO_TIMESTAMP('2019-05-06 19:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541122 AND AD_Language='en_US'
;

-- 2019-05-06T19:21:01.609
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,Created,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,FieldLength,IsCentrallyMaintained,IsEncrypted,Updated,UpdatedBy,ColumnName,IsMandatory,IsAutocomplete,SeqNo,AD_Org_ID,EntityType,Description,Name) VALUES (0,'Y',TO_TIMESTAMP('2019-05-06 19:21:01','YYYY-MM-DD HH24:MI:SS'),100,541122,10,'N',541439,0,'Y','N',TO_TIMESTAMP('2019-05-06 19:21:01','YYYY-MM-DD HH24:MI:SS'),100,'dataMatrix','Y','N',10,0,'de.metas.vertical.pharma.securpharm','Die abgescannte Datamatrix','Datamatrix')
;

-- 2019-05-06T19:21:01.632
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541439 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-05-06T19:22:25.223
-- URL zum Konzept
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,EntityType,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,AD_Org_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID) VALUES (TO_TIMESTAMP('2019-05-06 19:22:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',540516,'de.metas.ui.web',TO_TIMESTAMP('2019-05-06 19:22:25','YYYY-MM-DD HH24:MI:SS'),100,0,'Y',541122,0,'Y','Y','N','N',540712)
;







