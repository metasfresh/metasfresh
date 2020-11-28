-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,540794,'N',TO_TIMESTAMP('2016-10-26 17:56:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','N','Y','N','N','Y','N','N','N',0,'Qualität-Notiz','L','M_QualityNote',TO_TIMESTAMP('2016-10-26 17:56:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540794 AND NOT EXISTS (SELECT * FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
CREATE SEQUENCE M_QUALITYNOTE_SEQ INCREMENT 1 MINVALUE 0 MAXVALUE 2147483647 START 1000000
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554375,TO_TIMESTAMP('2016-10-26 17:56:12','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_QualityNote',1,'Y','N','Y','Y','M_QualityNote','N',1000000,TO_TIMESTAMP('2016-10-26 17:56:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,Help,ColumnName,AD_Column_ID,Description,IsMandatory) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,102,'N','N','Y','N','N','de.metas.swat','Mandant',540794,0,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','AD_Client_ID',555277,'Mandant für diese Installation.','Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555277 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,Help,ColumnName,AD_Column_ID,Description,IsMandatory) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,113,'N','N','Y','N','N','de.metas.swat','Sektion',540794,0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','AD_Org_ID',555278,'Organisatorische Einheit des Mandanten','Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555278 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,Help,ColumnName,AD_Column_ID,Description,IsMandatory) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,245,'N','N','Y','N','N','de.metas.swat','Erstellt',540794,0,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Created',555279,'Datum, an dem dieser Eintrag erstellt wurde','Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555279 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,Description,IsMandatory) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,246,'N','N','Y','N','N','de.metas.swat','Erstellt durch',540794,0,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',110,'CreatedBy',555280,'Nutzer, der diesen Eintrag erstellt hat','Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555280 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,Help,ColumnName,AD_Column_ID,Description,IsMandatory) VALUES (20,1,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,348,'Y','N','Y','N','N','de.metas.swat','Aktiv',540794,0,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','IsActive',555281,'Der Eintrag ist im System aktiv','Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555281 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,Help,ColumnName,AD_Column_ID,Description,IsMandatory) VALUES (16,29,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,607,'N','N','Y','N','N','de.metas.swat','Aktualisiert',540794,0,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Updated',555282,'Datum, an dem dieser Eintrag aktualisiert wurde','Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555282 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,Help,AD_Reference_Value_ID,ColumnName,AD_Column_ID,Description,IsMandatory) VALUES (18,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,608,'N','N','Y','N','N','de.metas.swat','Aktualisiert durch',540794,0,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',110,'UpdatedBy',555283,'Nutzer, der diesen Eintrag aktualisiert hat','Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555283 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543213,0,'M_QualityNote_ID',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Qualität-Notiz','Qualität-Notiz',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543213 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsEncrypted,EntityType,Name,AD_Table_ID,AD_Org_ID,ColumnName,AD_Column_ID,IsMandatory) VALUES (13,10,0,'Y','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:56:45','YYYY-MM-DD HH24:MI:SS'),100,543213,'N','N','Y','N','N','de.metas.swat','Qualität-Notiz',540794,0,'M_QualityNote_ID',555284,'Y')
;

-- 26.10.2016 17:56
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555284 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,EntityType,Name,IsAdvancedText,AD_Table_ID,IsLazyLoading,IsCalculated,AD_Org_ID,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,IsGenericZoomKeyColumn,AD_Column_ID,Description,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence) VALUES (10,255,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:57:35','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:57:35','YYYY-MM-DD HH24:MI:SS'),100,620,'Y','N','Y','N','N','N','Y','N','de.metas.swat','Suchschlüssel','N',540794,'N','N',0,'N','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Value','N','N',555285,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','N','N','N','N')
;

-- 26.10.2016 17:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555285 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:57
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,EntityType,Name,IsAdvancedText,AD_Table_ID,IsLazyLoading,IsCalculated,AD_Org_ID,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,IsGenericZoomKeyColumn,AD_Column_ID,Description,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence) VALUES (10,40,0,'N','N','N','Y',1,0,'Y',TO_TIMESTAMP('2016-10-26 17:57:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:57:46','YYYY-MM-DD HH24:MI:SS'),100,469,'Y','N','Y','N','N','N','Y','N','de.metas.swat','Name','N',540794,'N','N',0,'N','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Name','N','N',555286,'Alphanumeric identifier of the entity','N','N','N','N')
;

-- 26.10.2016 17:57
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555286 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:57
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2016-10-26 17:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555286
;

-- 26.10.2016 17:58
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,EntityType,Name,IsAdvancedText,AD_Table_ID,IsLazyLoading,IsCalculated,AD_Org_ID,AllowZoomTo,AD_Reference_Value_ID,ColumnName,IsGenericZoomOrigin,IsGenericZoomKeyColumn,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence) VALUES (17,255,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2016-10-26 17:58:27','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2016-10-26 17:58:27','YYYY-MM-DD HH24:MI:SS'),100,543176,'Y','N','N','N','N','N','Y','N','de.metas.swat','PerformanceType','N',540794,'N','N',0,'N',540689,'PerformanceType','N','N',555287,'N','N','N','N')
;

-- 26.10.2016 17:58
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=555287 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 26.10.2016 17:58
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N', IsMandatory='Y',Updated=TO_TIMESTAMP('2016-10-26 17:58:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555285
;

-- 26.10.2016 17:59
-- URL zum Konzept
CREATE TABLE M_QualityNote (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_QualityNote_ID NUMERIC(10) NOT NULL, Name VARCHAR(255) DEFAULT NULL , PerformanceType VARCHAR(255) DEFAULT NULL , Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(255) NOT NULL, CONSTRAINT M_QualityNote_Key PRIMARY KEY (M_QualityNote_ID))
;



-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555277
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Table SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540794
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555278
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555279
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555280
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555281
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-10-27 11:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555284
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555286
;

-- 27.10.2016 11:47
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:47:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555287
;

-- 27.10.2016 11:48
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555282
;

-- 27.10.2016 11:48
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555283
;

-- 27.10.2016 11:48
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.inout',Updated=TO_TIMESTAMP('2016-10-27 11:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=555285
;


-- 27.10.2016 12:14
-- URL zum Konzept
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540392,0,540794,TO_TIMESTAMP('2016-10-27 12:14:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','Y','M_QualityNote_Name_Unique','N',TO_TIMESTAMP('2016-10-27 12:14:41','YYYY-MM-DD HH24:MI:SS'),100,'IsActive = ''Y''')
;

-- 27.10.2016 12:14
-- URL zum Konzept
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540392 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 27.10.2016 12:14
-- URL zum Konzept
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,555286,540785,540392,0,TO_TIMESTAMP('2016-10-27 12:14:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y',10,TO_TIMESTAMP('2016-10-27 12:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 27.10.2016 12:14
-- URL zum Konzept
CREATE UNIQUE INDEX M_QualityNote_Name_Unique ON M_QualityNote (Name) WHERE IsActive = 'Y'
;

