
-- 2019-04-02T13:14:45.580
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,Updated,UpdatedBy) VALUES ('3',0,0,0,541342,'N',TO_TIMESTAMP('2019-04-02 13:14:45','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Import Postal Data','NP','L','I_Postal',TO_TIMESTAMP('2019-04-02 13:14:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-02T13:14:45.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541342 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2019-04-02T13:14:45.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,554995,TO_TIMESTAMP('2019-04-02 13:14:45','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table I_Postal',1,'Y','N','Y','Y','I_Postal','N',1000000,TO_TIMESTAMP('2019-04-02 13:14:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-02T13:16:08.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567546,102,0,19,541342,'AD_Client_ID',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:08.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567546 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:08.364
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567547,113,0,19,541342,'AD_Org_ID',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:08.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567547 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:08.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567548,543951,0,18,505274,541342,'Base_PricingSystem_ID',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Preissystem',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:08.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567548 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:08.591
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567549,543984,0,10,541342,'Base_PricingSystem_Value',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'D',60,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Base_PricingSystem_Value',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:08.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567549 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:08.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567550,1906,0,10,541342,'BPartner_Value',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Geschäftspartner','D',60,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Geschäftspartner-Schlüssel',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:08.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567550 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:08.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567551,1707,0,12,541342,'BreakDiscount',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Trade Discount in Percent for the break level','D',10,'Trade Discount in Percent for the break level','Y','Y','N','N','N','N','N','N','N','N','N','Y','Break Discount %',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:08.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567551 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:08.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567552,1708,0,29,541342,'BreakValue',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Mindestmenge ab der die Kondition gilt','D',10,'','Y','Y','N','N','N','N','N','N','N','N','N','Y','Mengenstufe',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:08.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567552 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567553,187,0,19,541342,'C_BPartner_ID',TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','D',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Geschäftspartner',0,TO_TIMESTAMP('2019-04-02 13:16:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567553 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567554,193,0,19,541342,'C_Currency_ID',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','D',10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','N','N','N','N','N','N','N','N','N','Y','Währung',0,TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567554 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567555,204,0,19,541342,'C_PaymentTerm_ID',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs','D',10,'Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Zahlungsbedingung',0,TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567555 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.397
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567556,245,0,16,541342,'Created',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.400
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567556 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567557,246,0,18,110,541342,'CreatedBy',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567557 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567558,280,0,12,541342,'Discount',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'Abschlag in Prozent','D',14,'"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Rabatt %',0,TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567558 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,576519,0,'I_Postal_ID',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Import Postal Data','Import Postal Data',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-02T13:16:09.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=576519 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-04-02T13:16:09.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567559,576519,0,13,541342,'I_Postal_ID',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','Y','Y','N','N','Y','N','N','Import Postal Data',0,TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567559 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:09.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567560,912,0,10,541342,'I_ErrorMsg',TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','D',2000,'"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Import-Fehlermeldung',0,TO_TIMESTAMP('2019-04-02 13:16:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:09.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567560 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567561,913,0,17,540745,541342,'I_IsImported',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Ist dieser Import verarbeitet worden?','D',1,'DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','Y','N','N','N','N','Y','N','N','N','N','Y','Importiert',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.131
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567561 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567562,348,0,20,541342,'IsActive',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.245
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567562 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567563,1715,0,19,541342,'M_DiscountSchemaBreak_ID',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Trade Discount Break','D',10,'Trade discount based on breaks (steps)','Y','Y','N','N','N','N','N','N','N','N','N','Y','Discount Schema Break',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567563 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567564,1714,0,19,541342,'M_DiscountSchema_ID',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Schema um den prozentualen Rabatt zu berechnen','D',10,'Nach Berechnung des (Standard-)Preises wird der prozntuale Rabatt berechnet und auf den Endpreis angewendet.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Rabatt Schema',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567564 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567565,454,0,19,541342,'M_Product_ID',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','D',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','N','N','N','N','Y','Produkt',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567565 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567566,2116,0,10,541342,'PaymentTermValue',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für die Zahlungskondition','D',60,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Zahlungskonditions-Schlüssel',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567566 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567567,543953,0,10,541342,'PriceBase',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Preisgrundlage',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567567 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:10.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567568,1746,0,37,541342,'PriceStdFixed',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Festpreis, ohne ggf. zusätzliche Rabatte','D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Festpreis',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:10.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567568 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:11.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567569,575939,0,12,541342,'PricingSystemSurchargeAmt',TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Aufschlag auf den Preis, der aus dem Preissystem resultieren würde','D',10,'Y','Y','N','N','N','N','Y','N','N','N','N','Y','Preisaufschlag',0,TO_TIMESTAMP('2019-04-02 13:16:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:11.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:11.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567570,1047,0,20,541342,'Processed',TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','D',1,'Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','Y','N','N','N','N','Y','Verarbeitet',0,TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:11.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:11.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567571,1675,0,10,541342,'ProductValue',TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Schlüssel des Produktes','D',60,'Y','Y','N','N','N','N','N','N','N','N','N','Y','Produktschlüssel',0,TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:11.286
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:11.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567572,607,0,16,541342,'Updated',TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:11.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567572 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:16:11.528
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567573,608,0,18,110,541342,'UpdatedBy',TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2019-04-02 13:16:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:16:11.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567573 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:17:19.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567548
;

-- 2019-04-02T13:17:19.808
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567548
;

-- 2019-04-02T13:17:20.033
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567549
;

-- 2019-04-02T13:17:20.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567549
;

-- 2019-04-02T13:17:20.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567550
;

-- 2019-04-02T13:17:20.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567550
;

-- 2019-04-02T13:17:20.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567551
;

-- 2019-04-02T13:17:20.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567551
;

-- 2019-04-02T13:17:20.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567552
;

-- 2019-04-02T13:17:20.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567552
;

-- 2019-04-02T13:17:20.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567553
;

-- 2019-04-02T13:17:20.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567553
;

-- 2019-04-02T13:17:20.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567554
;

-- 2019-04-02T13:17:20.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567554
;

-- 2019-04-02T13:17:20.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567555
;

-- 2019-04-02T13:17:20.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567555
;

-- 2019-04-02T13:17:20.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567558
;

-- 2019-04-02T13:17:20.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567558
;

-- 2019-04-02T13:17:21.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567563
;

-- 2019-04-02T13:17:21.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567563
;

-- 2019-04-02T13:17:21.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567564
;

-- 2019-04-02T13:17:21.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567564
;

-- 2019-04-02T13:17:21.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567565
;

-- 2019-04-02T13:17:21.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567565
;

-- 2019-04-02T13:17:21.472
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567566
;

-- 2019-04-02T13:17:21.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567566
;

-- 2019-04-02T13:17:21.628
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567567
;

-- 2019-04-02T13:17:21.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567567
;

-- 2019-04-02T13:17:21.752
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567568
;

-- 2019-04-02T13:17:21.758
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567568
;

-- 2019-04-02T13:17:21.872
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567569
;

-- 2019-04-02T13:17:21.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567569
;

-- 2019-04-02T13:17:22.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=567571
;

-- 2019-04-02T13:17:22.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=567571
;

-- 2019-04-02T13:18:14.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567574,541436,0,19,541342,'C_Postal_ID',TO_TIMESTAMP('2019-04-02 13:18:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Postal codes',0,0,TO_TIMESTAMP('2019-04-02 13:18:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:18:14.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:18:45.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2019-04-02 13:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567547
;

-- 2019-04-02T13:19:03.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-04-02 13:19:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567574
;

-- 2019-04-02T13:19:05.720
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2019-04-02 13:19:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567574
;

-- 2019-04-02T13:24:33.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567575,512,0,10,541342,'Postal',TO_TIMESTAMP('2019-04-02 13:24:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Postleitzahl','D',10,'"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','PLZ',0,0,TO_TIMESTAMP('2019-04-02 13:24:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:24:33.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567575 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T13:25:29.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-04-02 13:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567575
;

-- 2019-04-02T13:36:05.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567576,225,0,10,541342,'City',TO_TIMESTAMP('2019-04-02 13:36:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Name des Ortes','D',250,'Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ort',0,0,TO_TIMESTAMP('2019-04-02 13:36:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T13:36:05.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567576 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T15:56:32.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2019-04-02 15:56:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567576
;

-- 2019-04-02T15:59:17.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567585,617,0,15,541342,'ValidFrom',TO_TIMESTAMP('2019-04-02 15:59:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Gültig ab inklusiv (erster Tag)','D',7,'"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Gültig ab',0,0,TO_TIMESTAMP('2019-04-02 15:59:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T15:59:17.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567585 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T15:59:44.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2019-04-02 15:59:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567585
;

-- 2019-04-02T16:01:10.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567586,1355,0,10,541342,'A_State',TO_TIMESTAMP('2019-04-02 16:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Bundesstaat / -land des Eigentümers von Kreditkarte oder Konto','D',255,'Bundesstaat / -land bezeichnet den Bundesstaat oder das Bundesland des Eigentümers von Kreditkarte oder Konto.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Bundesstaat / -land',0,0,TO_TIMESTAMP('2019-04-02 16:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T16:01:10.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567586 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-02T16:01:44.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,567587,618,0,15,541342,'ValidTo',TO_TIMESTAMP('2019-04-02 16:01:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Gültig bis inklusiv (letzter Tag)','D',10,'"Gültig bis" bezeichnet den letzten Tag eines Gültigkeitzeitraumes.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Gültig bis',0,0,TO_TIMESTAMP('2019-04-02 16:01:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-04-02T16:01:44.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567587 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T07:52:34.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat (AD_Client_ID,AD_ImpFormat_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,FormatType,IsActive,IsManualImport,IsMultiLine,Name,Processing,Updated,UpdatedBy) VALUES (0,540034,0,541342,TO_TIMESTAMP('2019-04-03 07:52:33','YYYY-MM-DD HH24:MI:SS'),100,'Import Postal code data in the format: Austrian https://www.post.at/geschaeftlich_werben_produkte_und_services_adressen_postlexikon.php','C','Y','N','N','Import Postal Code Data','N',TO_TIMESTAMP('2019-04-03 07:52:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T07:56:55.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,567575,540034,541246,0,TO_TIMESTAMP('2019-04-03 07:56:55','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','Postal',10,0,TO_TIMESTAMP('2019-04-03 07:56:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T07:58:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,567576,540034,541247,0,TO_TIMESTAMP('2019-04-03 07:58:41','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','City',20,0,TO_TIMESTAMP('2019-04-03 07:58:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:19:10.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,567586,540034,541248,0,TO_TIMESTAMP('2019-04-03 08:19:10','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','A_State',40,0,TO_TIMESTAMP('2019-04-03 08:19:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:19:30.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,567585,540034,541249,0,TO_TIMESTAMP('2019-04-03 08:19:30','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','ValidFrom',50,0,TO_TIMESTAMP('2019-04-03 08:19:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:20:19.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,567587,540034,541250,0,TO_TIMESTAMP('2019-04-03 08:20:19','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','ValidTo',60,0,TO_TIMESTAMP('2019-04-03 08:20:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T08:20:42.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET IsMultiLine='Y',Updated=TO_TIMESTAMP('2019-04-03 08:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540034
;

-- 2019-04-03T08:20:52.089
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET IsMultiLine='N',Updated=TO_TIMESTAMP('2019-04-03 08:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540034
;

-- 2019-04-03T09:24:20.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help='"Region" definiert die Bezeichnung, die bei der Verwendung in einem Dokument gedruckt wird.', AD_Element_ID=541, ColumnName='RegionName', Description='Name der Region', Name='Region',Updated=TO_TIMESTAMP('2019-04-03 09:24:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=567586
;

-- 2019-04-03T09:24:20.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Region', Description='Name der Region', Help='"Region" definiert die Bezeichnung, die bei der Verwendung in einem Dokument gedruckt wird.' WHERE AD_Column_ID=567586
;

-- 2019-04-03T09:26:54.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 09:26:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 09:26:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541342,'N','"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.',567617,'N','N','N','N','N','N','N','N',0,0,209,'N','N','C_Region_ID','Identifiziert eine geographische Region','N','Region','D')
;

-- 2019-04-03T09:26:54.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567617 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T09:27:44.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name,EntityType) VALUES (10,2,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 09:27:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 09:27:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541342,'N','Für Details - http://www.din.de/gremien/nas/nabd/iso3166ma/codlstp1.html oder - http://www.unece.org/trade/rec/rec03en.htm',567618,'N','N','N','N','N','N','N','N',0,0,244,'N','N','CountryCode','Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html','N','ISO Ländercode','D')
;

-- 2019-04-03T09:27:44.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567618 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T09:28:02.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,AD_Org_ID,AD_Element_ID,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,Description,IsAutoApplyValidationRule,Name,EntityType) VALUES (19,10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2019-04-03 09:28:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2019-04-03 09:28:02','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541342,'N','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.',567619,'N','N','N','N','N','N','N','N',0,0,192,'N','N','C_Country_ID','Land','N','Land','D')
;

-- 2019-04-03T09:28:02.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=567619 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-04-03T09:31:06.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataFormat='dd.MM.yyyy', DataType='D',Updated=TO_TIMESTAMP('2019-04-03 09:31:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541250
;

-- 2019-04-03T09:31:17.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET DataFormat='dd.MM.yyyy', DataType='D',Updated=TO_TIMESTAMP('2019-04-03 09:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541249
;

-- 2019-04-03T09:31:56.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET Name='RegionName',Updated=TO_TIMESTAMP('2019-04-03 09:31:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541248
;

-- 2019-04-03T09:33:36.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,EndNo,IsActive,Name,SeqNo,StartNo,Updated,UpdatedBy) VALUES (0,567618,540034,541251,0,TO_TIMESTAMP('2019-04-03 09:33:36','YYYY-MM-DD HH24:MI:SS'),100,'S','.','N',0,'Y','CountryCode',30,0,TO_TIMESTAMP('2019-04-03 09:33:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T09:41:08.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=1,Updated=TO_TIMESTAMP('2019-04-03 09:41:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541246
;

-- 2019-04-03T09:41:09.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=2,Updated=TO_TIMESTAMP('2019-04-03 09:41:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541247
;

-- 2019-04-03T09:41:10.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=3,Updated=TO_TIMESTAMP('2019-04-03 09:41:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541248
;

-- 2019-04-03T09:41:11.413
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=4,Updated=TO_TIMESTAMP('2019-04-03 09:41:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541249
;

-- 2019-04-03T09:41:12.596
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=5,Updated=TO_TIMESTAMP('2019-04-03 09:41:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541250
;

-- 2019-04-03T09:41:14.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=6,Updated=TO_TIMESTAMP('2019-04-03 09:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541251
;

-- 2019-04-03T09:41:21.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=1,Updated=TO_TIMESTAMP('2019-04-03 09:41:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541246
;

-- 2019-04-03T09:41:22.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2019-04-03 09:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541247
;

-- 2019-04-03T09:41:22.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2019-04-03 09:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541248
;

-- 2019-04-03T09:41:22.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2019-04-03 09:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541249
;

-- 2019-04-03T09:41:23.142
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2019-04-03 09:41:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541250
;

-- 2019-04-03T09:41:24.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2019-04-03 09:41:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541251
;

-- 2019-04-03T09:43:17.765
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=3, StartNo=3,Updated=TO_TIMESTAMP('2019-04-03 09:43:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541251
;

-- 2019-04-03T09:43:40.329
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=4,Updated=TO_TIMESTAMP('2019-04-03 09:43:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541248
;

-- 2019-04-03T09:43:41.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=5,Updated=TO_TIMESTAMP('2019-04-03 09:43:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541249
;

-- 2019-04-03T09:43:46.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET SeqNo=6,Updated=TO_TIMESTAMP('2019-04-03 09:43:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541250
;

-- 2019-04-03T09:43:50.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=4,Updated=TO_TIMESTAMP('2019-04-03 09:43:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541248
;

-- 2019-04-03T09:43:51.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=5,Updated=TO_TIMESTAMP('2019-04-03 09:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541249
;

-- 2019-04-03T09:43:52.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET StartNo=6,Updated=TO_TIMESTAMP('2019-04-03 09:43:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541250
;

-- 2019-04-03T09:47:12.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat SET FormatType='T',Updated=TO_TIMESTAMP('2019-04-03 09:47:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_ID=540034
;

