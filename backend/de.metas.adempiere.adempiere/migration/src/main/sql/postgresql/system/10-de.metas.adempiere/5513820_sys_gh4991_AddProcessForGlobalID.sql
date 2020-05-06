-- 2019-02-26T13:13:19.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558551
;

-- 2019-02-26T13:13:31.885
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560188
;

-- 2019-02-26T13:17:27.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541180,'N',TO_TIMESTAMP('2019-02-26 13:17:27','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'BPartne Global ID','NP','L','I_BPartner_GlobalID',TO_TIMESTAMP('2019-02-26 13:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:17:27.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541180 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-02-26T13:17:27.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554825,TO_TIMESTAMP('2019-02-26 13:17:27','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table I_BPartner_GlobalID',1,'Y','N','Y','Y','I_BPartner_GlobalID','N',1000000,TO_TIMESTAMP('2019-02-26 13:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:17:55.711
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564217,146,0,17,118,541180,'AccountSign',TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt das natürliche Vorzeichen des Kontos als Soll oder Haben an','D',1,'Zeigt an, ob der zu erwartende Saldo für dieses Konto Soll oder Haben sein soll. Wenn auf "natürlich" gesetzt, ist das Kontovorzeichen für ein Bestands- oder Aufwandskonto ein Belastungszeichen (d.h. negativ, wenn Habensaldo).','Y','Y','N','N','N','N','N','N','N','N','N','Y','Kontovorzeichen',0,TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:55.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564217 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:55.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564218,147,0,17,117,541180,'AccountType',TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,'Definiert die Kontenart','D',1,'Gültige Kontenarten sind: A - Anlagevermögen (Asset), E - Betriebsausgaben (Expense), L - Verbindlichkeiten (Liability), O - Kapital (Owner''s Equity), R - Einnahmen (Revenue) and M - Memo. Die Kontenart wird genutzt um zu bestimmen, welche Steuern - wenn überhaupt - anfallen sowie zur Validierung von Verbindlichkeiten und Forderungen gegenüber Geschäftspartnern. Anmerkung: Memokonten-Beträge werden bei der Saldierungsprüfung ignoriert.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Kontenart',0,TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:55.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564218 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:55.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564219,102,0,19,541180,'AD_Client_ID',TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,'@#AD_Client_ID@','Mandant für diese Installation.','D',22,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','N','N','N','Mandant',0,TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:55.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564219 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:55.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564220,104,0,18,272,541180,'AD_Column_ID',TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,'Spalte in der Tabelle','D',22,'Verbindung zur Spalte der Tabelle','Y','Y','N','N','N','N','N','N','N','N','N','Y','Spalte',0,TO_TIMESTAMP('2019-02-26 13:17:55','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:55.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564220 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564221,113,0,19,541180,104,'AD_Org_ID',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'@#AD_Org_ID@','Organisatorische Einheit des Mandanten','D',22,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','N','N','Sektion',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.093
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564221 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564222,199,0,19,541180,'C_Element_ID',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Element','D',22,'The Account Element uniquely identifies an Account Type.  These are commonly known as a Chart of Accounts.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Element',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564222 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564223,198,0,19,541180,'C_ElementValue_ID',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Kontenart','D',22,'Account Elements can be natural accounts or user defined values.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Kontenart',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564223 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564224,245,0,16,541180,'Created',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','N','N','N','Erstellt',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564224 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564225,246,0,18,110,541180,'CreatedBy',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',22,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','N','N','N','Erstellt durch',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564225 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564226,1908,0,10,541180,'Default_Account',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Standard-Kontos','D',30,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Standard-Konto',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564226 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564227,275,0,10,541180,'Description',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'D',255,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564227 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564228,1909,0,10,541180,'ElementName',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Name of the Element','D',60,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Element Name',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.700
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564228 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576148,0,'I_BPartner_GlobalID_ID',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','BPartne Global ID','BPartne Global ID',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:17:56.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576148 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-02-26T13:17:56.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564229,576148,0,13,541180,'I_BPartner_GlobalID_ID',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'D',22,'Y','N','N','N','Y','Y','N','N','N','N','N','BPartne Global ID',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564229 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:56.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564230,912,0,10,541180,'I_ErrorMsg',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','D',2000,'"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Import-Fehlermeldung',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:56.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564230 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564231,913,0,20,541180,'I_IsImported',TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?','D',1,'DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','Y','N','N','N','N','Y','N','N','N','N','Y','Importiert',0,TO_TIMESTAMP('2019-02-26 13:17:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564231 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564232,348,0,20,541180,'IsActive',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Aktiv',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564232 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564233,369,0,20,541180,'IsDocControlled',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Gesteuertes Konto - Wenn eine Konto über die Belegart gesteuert wird können Sie nicht manuell darauf buchen.','D',1,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Belegartgesteuert',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564233 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564234,416,0,20,541180,'IsSummary',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Dies ist ein Zusammenfassungseintrag','D',1,'A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Zusammenfassungseintrag',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564234 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564235,469,0,10,541180,'Name',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','D',60,'The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','Name',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564235 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564236,1924,0,18,182,541180,'ParentElementValue_ID',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Das übergeordnete (zusammenfassende) Konto','D',22,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Übergeordnetes Konto',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564236 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564237,1925,0,10,541180,'ParentValue',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Schlüssel des übergeordneten Kontos','D',40,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Schlüssel Übergeordnetes Konto ',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.573
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564237 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564238,508,0,20,541180,'PostActual',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Ist-Werte können verbucht werden','D',1,'"Buchen Ist" zeigt an, ob Ist-Werte auf dieses Kontenelement verbucht werden kann.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Buchen "Ist"',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564238 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564239,509,0,20,541180,'PostBudget',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Budget-Werte können verbucht werden','D',1,'"Buchen Budget" zeigt an, ob Budget-Werte auf dieses Kontenelement verbucht werden kann.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Buchen "Budget"',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564239 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564240,510,0,20,541180,'PostEncumbrance',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'"Buchen Reservierung" zeigt an, ob reservierte Beträge auf dieses Kontenelement verbucht werden können.','D',1,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Buchen "Reservierung"',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564240 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:57.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564241,511,0,20,541180,'PostStatistical',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Sind statistische Buchungen auf diesem Element möglich?','D',1,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Buchen "statistisch"',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:57.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564241 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:58.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564242,1047,0,20,541180,'Processed',TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','D',1,'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Verarbeitet',0,TO_TIMESTAMP('2019-02-26 13:17:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:58.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564242 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:58.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564243,524,0,197,28,541180,'Processing',TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Process Now',0,TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:58.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564243 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:58.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564244,607,0,16,541180,'Updated',TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','N','N','N','Aktualisiert',0,TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:58.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564244 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:58.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564245,608,0,18,110,541180,'UpdatedBy',TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',22,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:58.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564245 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:17:58.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564246,620,0,10,541180,'Value',TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',40,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','Y','N','N','N','Y','N','N','Y','Suchschlüssel',1,TO_TIMESTAMP('2019-02-26 13:17:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2019-02-26T13:17:58.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564246 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:18:30.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564217
;

-- 2019-02-26T13:18:30.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564217
;

-- 2019-02-26T13:18:30.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564218
;

-- 2019-02-26T13:18:30.606
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564218
;

-- 2019-02-26T13:18:30.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564220
;

-- 2019-02-26T13:18:30.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564220
;

-- 2019-02-26T13:18:30.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564222
;

-- 2019-02-26T13:18:30.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564222
;

-- 2019-02-26T13:18:31.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564223
;

-- 2019-02-26T13:18:31.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564223
;

-- 2019-02-26T13:18:31.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564226
;

-- 2019-02-26T13:18:31.302
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564226
;

-- 2019-02-26T13:18:31.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564227
;

-- 2019-02-26T13:18:31.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564227
;

-- 2019-02-26T13:18:31.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564228
;

-- 2019-02-26T13:18:31.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564228
;

-- 2019-02-26T13:18:31.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564233
;

-- 2019-02-26T13:18:31.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564233
;

-- 2019-02-26T13:18:31.841
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564234
;

-- 2019-02-26T13:18:31.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564234
;

-- 2019-02-26T13:18:31.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564235
;

-- 2019-02-26T13:18:31.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564235
;

-- 2019-02-26T13:18:32.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564236
;

-- 2019-02-26T13:18:32.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564236
;

-- 2019-02-26T13:18:32.162
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564237
;

-- 2019-02-26T13:18:32.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564237
;

-- 2019-02-26T13:18:32.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564238
;

-- 2019-02-26T13:18:32.283
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564238
;

-- 2019-02-26T13:18:32.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564239
;

-- 2019-02-26T13:18:32.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564239
;

-- 2019-02-26T13:18:32.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564240
;

-- 2019-02-26T13:18:32.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564240
;

-- 2019-02-26T13:18:32.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564241
;

-- 2019-02-26T13:18:32.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564241
;

-- 2019-02-26T13:18:32.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=564246
;

-- 2019-02-26T13:18:32.677
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=564246
;

-- 2019-02-26T13:19:46.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564247,543753,0,10,541180,'globalid','',TO_TIMESTAMP('2019-02-26 13:19:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',60,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Global ID',0,0,TO_TIMESTAMP('2019-02-26 13:19:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-26T13:19:46.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564247 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:20:18.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564248,544091,0,10,541180,'URL3',TO_TIMESTAMP('2019-02-26 13:20:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Full URL address - e.g. http://www.adempiere.org','U',400,'The URL defines an fully qualified web address like http://www.adempiere.org','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','URL3',0,0,TO_TIMESTAMP('2019-02-26 13:20:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-26T13:20:18.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564248 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:20:23.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564248
;

-- 2019-02-26T13:20:34.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,564249,187,0,19,541180,'C_BPartner_ID',TO_TIMESTAMP('2019-02-26 13:20:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Geschäftspartner',0,0,TO_TIMESTAMP('2019-02-26 13:20:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-02-26T13:20:34.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=564249 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-02-26T13:20:44.138
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2019-02-26 13:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564247
;

-- 2019-02-26T13:20:49.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.I_BPartner_GlobalID (AD_Client_ID NUMERIC(10), AD_Org_ID NUMERIC(10), C_BPartner_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE, CreatedBy NUMERIC(10), globalid VARCHAR(60) NOT NULL, I_BPartner_GlobalID_ID NUMERIC(10) NOT NULL, I_ErrorMsg VARCHAR(2000), I_IsImported CHAR(1) CHECK (I_IsImported IN ('Y','N')) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')), Processed CHAR(1) CHECK (Processed IN ('Y','N')), Processing CHAR(1), Updated TIMESTAMP WITH TIME ZONE, UpdatedBy NUMERIC(10), URL3 VARCHAR(400), CONSTRAINT CBPartner_IBPartnerGlobalID FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT I_BPartner_GlobalID_Key PRIMARY KEY (I_BPartner_GlobalID_ID))
;

-- 2019-02-26T13:20:55.648
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner_globalid','globalid','VARCHAR(60)',null,null)
;

-- 2019-02-26T13:21:29.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Import BPartner Global ID',Updated=TO_TIMESTAMP('2019-02-26 13:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541180
;

-- 2019-02-26T13:22:17.861
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Import BPartnerr Global ID', PrintName='Import BPartnerr Global ID',Updated=TO_TIMESTAMP('2019-02-26 13:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576148
;

-- 2019-02-26T13:22:17.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='I_BPartner_GlobalID_ID', Name='Import BPartnerr Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=576148
;

-- 2019-02-26T13:22:17.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='I_BPartner_GlobalID_ID', Name='Import BPartnerr Global ID', Description=NULL, Help=NULL, AD_Element_ID=576148 WHERE UPPER(ColumnName)='I_BPARTNER_GLOBALID_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-02-26T13:22:17.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='I_BPartner_GlobalID_ID', Name='Import BPartnerr Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID=576148 AND IsCentrallyMaintained='Y'
;

-- 2019-02-26T13:22:17.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Import BPartnerr Global ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=576148) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 576148)
;

-- 2019-02-26T13:22:17.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Import BPartnerr Global ID', Name='Import BPartnerr Global ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=576148)
;

-- 2019-02-26T13:22:17.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Import BPartnerr Global ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 576148
;

-- 2019-02-26T13:22:17.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Import BPartnerr Global ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 576148
;

-- 2019-02-26T13:22:17.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Import BPartnerr Global ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 576148
;

-- 2019-02-26T13:22:44.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,576148,0,540590,TO_TIMESTAMP('2019-02-26 13:22:43','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N','N','N','Y','Import BPartnerr Global ID','N',TO_TIMESTAMP('2019-02-26 13:22:43','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0)
;

-- 2019-02-26T13:22:44.096
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Window_ID=540590 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2019-02-26T13:22:44.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(576148) 
;

-- 2019-02-26T13:23:34.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,576148,0,541566,541180,540590,'Y',TO_TIMESTAMP('2019-02-26 13:23:34','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','I_BPartner_GlobalID','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Import BPartnerr Global ID','N',10,0,TO_TIMESTAMP('2019-02-26 13:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:34.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Tab_ID=541566 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2019-02-26T13:23:35.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(576148) 
;

-- 2019-02-26T13:23:39.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564219,575071,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564221,575072,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564229,575073,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,22,'U','Y','N','N','N','N','N','N','N','Import BPartnerr Global ID',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564230,575074,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'U','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564231,575075,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',1,'U','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','N','N','N','N','N','Importiert',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564232,575076,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564242,575077,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'U','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564243,575078,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,1,'U','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564247,575079,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,60,'U','Y','N','N','N','N','N','N','N','Global ID',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564248,575080,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org',400,'U','The URL defines an fully qualified web address like http://www.adempiere.org','Y','N','N','N','N','N','N','N','URL3',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.703
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:23:39.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564249,575081,0,541566,TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'U','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2019-02-26 13:23:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T13:23:39.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=575081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-02-26T13:24:14.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575071
;

-- 2019-02-26T13:24:14.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575072
;

-- 2019-02-26T13:24:14.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575079
;

-- 2019-02-26T13:24:14.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575081
;

-- 2019-02-26T13:24:14.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575080
;

-- 2019-02-26T13:24:14.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575074
;

-- 2019-02-26T13:24:14.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575075
;

-- 2019-02-26T13:24:14.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575076
;

-- 2019-02-26T13:24:14.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575077
;

-- 2019-02-26T13:24:14.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-02-26 13:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575078
;

-- 2019-02-26T13:24:36.905
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575071
;

-- 2019-02-26T13:24:36.913
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575072
;

-- 2019-02-26T13:24:36.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575079
;

-- 2019-02-26T13:24:36.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575081
;

-- 2019-02-26T13:24:36.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575080
;

-- 2019-02-26T13:24:36.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575074
;

-- 2019-02-26T13:24:36.946
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575075
;

-- 2019-02-26T13:24:36.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575076
;

-- 2019-02-26T13:24:36.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575077
;

-- 2019-02-26T13:24:36.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2019-02-26 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575078
;

-- 2019-02-26T13:24:54.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:24:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575071
;

-- 2019-02-26T13:24:59.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D', IsSameLine='Y',Updated=TO_TIMESTAMP('2019-02-26 13:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575072
;

-- 2019-02-26T13:25:02.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575079
;

-- 2019-02-26T13:25:07.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D', IsSameLine='Y',Updated=TO_TIMESTAMP('2019-02-26 13:25:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575081
;

-- 2019-02-26T13:25:19.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575074
;

-- 2019-02-26T13:25:24.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575080
;

-- 2019-02-26T13:25:32.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575075
;

-- 2019-02-26T13:25:37.355
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D', IsSameLine='Y',Updated=TO_TIMESTAMP('2019-02-26 13:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575076
;

-- 2019-02-26T13:25:41.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575077
;

-- 2019-02-26T13:25:45.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D', IsSameLine='Y',Updated=TO_TIMESTAMP('2019-02-26 13:25:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575078
;

-- 2019-02-26T13:25:51.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET EntityType='D',Updated=TO_TIMESTAMP('2019-02-26 13:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540590
;

-- 2019-02-26T13:26:45.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Name='Import BPartner Global ID',Updated=TO_TIMESTAMP('2019-02-26 13:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540590
;

-- 2019-02-26T13:27:19.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,576148,541200,0,540590,TO_TIMESTAMP('2019-02-26 13:27:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.dpd','Import BPartner Global ID','Y','N','N','N','N','Import BPartner Global ID',TO_TIMESTAMP('2019-02-26 13:27:19','YYYY-MM-DD HH24:MI:SS'),100,'Import BPartner Global ID')
;

-- 2019-02-26T13:27:19.577
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541200 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-02-26T13:27:19.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541200, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541200)
;

-- 2019-02-26T13:27:19.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(576148) 
;

-- 2019-02-26T13:27:53.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540590,Updated=TO_TIMESTAMP('2019-02-26 13:27:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541180
;

-- 2019-02-26T17:29:39.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,Description,EntityType,Help,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsTranslateExcelHeaders,IsUseBPartnerLanguage,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,541062,'Y','de.metas.globalid.impexp.ImportBPartnerGlobalID',TO_TIMESTAMP('2019-02-26 17:29:39','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','N','Y','N','N','N','N','Y','Y','Import Profil Links','N','Y','Java',TO_TIMESTAMP('2019-02-26 17:29:39','YYYY-MM-DD HH24:MI:SS'),100,'ImportBPartnerGlobalID')
;

-- 2019-02-26T17:29:39.844
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541062 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-02-26T17:30:16.331
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,541062,541180,540674,TO_TIMESTAMP('2019-02-26 17:30:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2019-02-26 17:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;



-----------------------------------------


-- 2019-02-26T17:48:32.373
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,FormatType,IsActive,IsMultiLine,Name,Processing,Updated,UpdatedBy) VALUES (0,540027,0,541180,TO_TIMESTAMP('2019-02-26 17:48:32','YYYY-MM-DD HH24:MI:SS'),100,'S','Y','N','Global ID','N',TO_TIMESTAMP('2019-02-26 17:48:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T17:48:56.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,564247,540027,541027,0,TO_TIMESTAMP('2019-02-26 17:48:56','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Global ID',10,0,TO_TIMESTAMP('2019-02-26 17:48:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T17:49:07.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,564248,540027,541028,0,TO_TIMESTAMP('2019-02-26 17:49:07','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','URL',20,0,TO_TIMESTAMP('2019-02-26 17:49:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-02-26T17:49:31.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.495
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.506
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.517
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.518
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.523
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.545
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.549
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:31.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.422
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.429
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.438
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.444
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.445
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.448
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.460
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.462
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.465
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.470
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.474
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:38.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.766
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.767
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.776
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.783
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.821
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:42.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.178
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.180
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.184
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540004 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.198
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540060 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.215
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.218
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540234 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.221
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540237 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540244 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.229
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540264 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540265 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.232
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540529 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.236
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=100, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=101, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=102, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=103, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=104, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- 2019-02-26T17:49:48.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=105, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541045 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:35.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541045 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:40.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.274
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541045 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- 2019-02-26T17:54:42.279
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- 2019-02-26T17:58:34.459
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-02-26 17:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564247
;

-- 2019-02-26T17:58:56.983
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner_globalid','globalid','VARCHAR(60)',null,null)
;

-- 2019-02-26T17:58:57.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner_globalid','globalid',null,'NULL',null)
;

-- 2019-02-26T17:59:50.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2019-02-26 17:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564231
;

-- 2019-02-26T17:59:53.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner_globalid','I_IsImported','CHAR(1)',null,'N')
;

-- 2019-02-26T17:59:53.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE I_BPartner_GlobalID SET I_IsImported='N' WHERE I_IsImported IS NULL
;




-- 2019-02-26T18:12:11.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=1,Updated=TO_TIMESTAMP('2019-02-26 18:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541027
;

-- 2019-02-26T18:12:15.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2019-02-26 18:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541028
;




--------------------------------------------------------------------------------------------

-- 2019-02-27T14:46:14.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-02-27 14:46:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564247
;

-- 2019-02-27T14:46:24.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterDefaultValue='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-02-27 14:46:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564231
;

-- 2019-02-27T14:46:48.774
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2019-02-27 14:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575078
;

-- 2019-02-27T14:46:52.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-02-27 14:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=575078
;

