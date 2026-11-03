-- 2018-02-16T22:23:06.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,AD_Client_ID,CreatedBy,IsActive,IsSecurityEnabled,IsDeleteable,IsHighVolume,IsView,ImportTable,IsChangeLog,ReplicationType,EntityType,CopyColumnsFromTable,TableName,IsAutocomplete,IsDLM,AD_Org_ID,Name,UpdatedBy,AD_Table_ID,LoadSeq,Created,ACTriggerLength,Updated) VALUES ('3',0,100,'Y','N','N','Y','Y','N','N','L','de.metas.datev','N','RV_DATEV_Export_Fact_Acct_Invoice','N','N',0,'RV_DATEV_Export_Fact_Acct_Invoice',100,540936,0,TO_TIMESTAMP('2018-02-16 22:23:06','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-16 22:23:06','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:06.663
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Table_ID=540936 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2018-02-16T22:23:13.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (10,'N','N','N','N',0,'Y',100,543877,'N','N','N','Y','N',540936,'DR_Account',559159,'N',0,100,'Debit Account','de.metas.datev',40,0,TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:13.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559159 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:13.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (10,'N','N','N','N',0,'Y',100,543878,'N','N','N','Y','N',540936,'CR_Account',559160,'N',0,100,'Credit Account','de.metas.datev',40,0,TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:13.684
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559160 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:13.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (12,'N','N','N','N',0,'Y',100,160,'N','N','N','Y','N',540936,'Betrag','Amt',559161,'N','Betrag',0,100,'Betrag','de.metas.datev',131089,0,TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:13.756
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559161 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:13.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,1005,'N','N','N','Y','N',540936,'Erfassung der zugehörigen Kostenstelle','C_Activity_ID',559162,'N','Kostenstelle',0,100,'Kostenstelle','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:13.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559162 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:13.901
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (10,'N','N','N','N',0,'Y',100,290,'N','N','N','Y','N',540936,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','DocumentNo',559163,'N','Document sequence number of the document',0,100,'Nr.','de.metas.datev',40,0,TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:13.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559163 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:13.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (16,'N','N','N','N',0,'Y',100,263,'N','N','N','Y','N',540936,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','DateAcct',559164,'N','Accounting Date',0,100,'Buchungsdatum','de.metas.datev',29,0,TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:13.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (10,'N','N','N','N',0,'Y',100,1876,'N','N','N','Y','N',540936,'Suchschlüssel für den Geschäftspartner','BPValue',559165,'N','Sponsor-Nr.',0,100,'Nr.','de.metas.datev',40,0,TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (10,'N','N','N','N',0,'Y',100,2510,'N','N','N','Y','N',540936,'BPName',559166,'N','Name des Sponsors.',0,100,'Name','de.metas.datev',60,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.197
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,187,'N','N','N','Y','N',540936,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','C_BPartner_ID',559167,'N','Bezeichnet einen Geschäftspartner',0,100,'Geschäftspartner','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.199
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559167 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.271
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,126,'N','N','N','Y','N',540936,'The Database Table provides the information of the table definition','AD_Table_ID',559168,'N','Database Table information',0,100,'DB-Tabelle','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559168 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,538,'N','N','N','Y','N',540936,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Record_ID',559169,'N','Direct internal record ID',0,100,'Datensatz-ID','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559169 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (10,'N','N','N','N',0,'Y',100,865,'N','N','N','Y','N',540936,'','DocBaseType',559170,'N','',0,100,'Dokument Basis Typ','de.metas.datev',3,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.418
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559170 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,885,'N','N','N','Y','N',540936,'Fact_Acct_ID',559171,'N',0,100,'Accounting Fact','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559171 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'RV_DATEV_Export_Fact_Acct_Invoice','de.metas.datev','RV_DATEV_Export_Fact_Acct_Invoice_ID',543879,0,'RV_DATEV_Export_Fact_Acct_Invoice',100,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.567
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543879 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-02-16T22:23:14.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (13,'Y','N','N','N',0,'Y',100,543879,'N','N','N','Y','N',540936,'RV_DATEV_Export_Fact_Acct_Invoice_ID',559172,'N',0,100,'RV_DATEV_Export_Fact_Acct_Invoice','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559172 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,102,'N','N','N','Y','N',540936,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','AD_Client_ID',559173,'N','Mandant für diese Installation.',0,100,'Mandant','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559173 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-16T22:23:14.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,IsSelectionColumn,IsAlwaysUpdateable,IsAllowLogging,IsEncrypted,AD_Table_ID,Help,ColumnName,AD_Column_ID,IsMandatory,Description,AD_Org_ID,UpdatedBy,Name,EntityType,FieldLength,Version,Created,Updated) VALUES (19,'N','N','N','N',0,'Y',100,113,'N','N','N','Y','N',540936,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','AD_Org_ID',559174,'N','Organisatorische Einheit des Mandanten',0,100,'Sektion','de.metas.datev',10,0,TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-16 22:23:14','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-16T22:23:14.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559174 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

