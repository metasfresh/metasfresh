-- 29.09.2015 11:25
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FormatPattern='###.#',Updated=TO_TIMESTAMP('2015-09-29 11:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552751
;

-- 29.09.2015 12:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,551130,0,540705,540611,540226,TO_TIMESTAMP('2015-09-29 12:14:01','YYYY-MM-DD HH24:MI:SS'),100,NULL,'U','N','N','Y','N','Y','N','N','N','N','Y','Y','N','Y','Y','N','N','N','Empfangen',551110,'N',20,1,TO_TIMESTAMP('2015-09-29 12:14:01','YYYY-MM-DD HH24:MI:SS'),100,'AD_Table_ID=320 ')
;

-- 29.09.2015 12:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540705 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 29.09.2015 12:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=25,Updated=TO_TIMESTAMP('2015-09-29 12:14:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540611
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551130,556331,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:44','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','N','N','N','N','N','N','N','Material-Vorgang-ID',0,0,TO_TIMESTAMP('2015-09-29 14:48:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556331 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551122,556332,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'U','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','N','N','N','N','N','N','N','Mandant',0,0,TO_TIMESTAMP('2015-09-29 14:48:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556332 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551123,556333,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'U','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','N','N','N','Sektion',0,0,TO_TIMESTAMP('2015-09-29 14:48:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556333 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551137,556334,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100,1,'U',0,'Y','Y','N','N','N','N','N','Y','N','Quality Inspection Document',0,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556334 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551126,556335,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'U','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','Y','N','N','N','N','N','Aktiv',1,1,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556335 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551135,556336,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'U','The Database Table provides the information of the table definition',0,'Y','Y','Y','Y','N','N','N','Y','N','DB-Tabelle',10,10,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556336 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551136,556337,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',10,'U','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','Y','Y','N','N','N','Y','Y','Datensatz-ID',20,20,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556337 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552753,556338,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',0,'U','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','Y','N','N','N','N','N','Beleg Nr.',30,30,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556338 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552754,556339,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde',0,'U','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.',0,'Y','Y','Y','Y','N','N','N','Y','N','Bewegungs-Datum',40,40,0,TO_TIMESTAMP('2015-09-29 14:48:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556339 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552755,556340,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:46','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','Y','N','N','N','Y','Y','Ausgelagerte Menge',50,50,0,TO_TIMESTAMP('2015-09-29 14:48:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556340 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNoGrid,Updated,UpdatedBy) VALUES (0,551129,556341,0,540705,0,TO_TIMESTAMP('2015-09-29 14:48:46','YYYY-MM-DD HH24:MI:SS'),100,10,'U',0,'Y','Y','N','N','N','N','N','N','N','Material Tracking Reference',0,TO_TIMESTAMP('2015-09-29 14:48:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 14:48
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556341 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 15:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2015-09-29 15:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556340
;

-- 29.09.2015 15:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2015-09-29 15:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556335
;

-- 29.09.2015 15:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2015-09-29 15:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556336
;

-- 29.09.2015 15:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2015-09-29 15:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556337
;

-- 29.09.2015 15:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2015-09-29 15:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556338
;

-- 29.09.2015 15:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2015-09-29 15:03:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556339
;

-- 29.09.2015 15:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540705
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556334
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556340
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556331
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556332
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556333
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556335
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556336
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556337
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556338
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556339
;

-- 29.09.2015 15:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.materialtracking',Updated=TO_TIMESTAMP('2015-09-29 15:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556341
;

-- 29.09.2015 15:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 		select pp.DocumentNo from PP_Order pp where pp.PP_Order_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 53027 	WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 		select io.DocumentNo from M_InOutLine iol  		join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 		where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 END CASE )',Updated=TO_TIMESTAMP('2015-09-29 15:17:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552753
;

-- 29.09.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 		select pp.DocumentNo from PP_Order pp where pp.PP_Order_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 53027 	WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 		select io.DocumentNo from M_InOutLine iol  		join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 		where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 		and io.DocStatus in (''CO'', ''CL'') END CASE )',Updated=TO_TIMESTAMP('2015-09-29 15:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552753
;

-- 29.09.2015 15:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 			elect cc.MovementDate from PP_Order pp  join PP_Cost_Collector cc on (pp.PP_Order_ID = cc.PP_Order_ID) where pp.PP_Order_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 53027  and cc.DocStatus in (''CO'', ''CL'') order by cc.MovementDate limit 1 		WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 			select io.MovementDate from M_InOutLine iol  			join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 			where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 			and io.DocStatus in (''CO'', ''CL'') END CASE )',Updated=TO_TIMESTAMP('2015-09-29 15:20:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552754
;

-- 29.09.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552757,542547,0,29,540611,'N','QtyReceived',TO_TIMESTAMP('2015-09-29 15:21:38','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.materialtracking',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Empfangene Menge',0,TO_TIMESTAMP('2015-09-29 15:21:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 29.09.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552757 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 29.09.2015 15:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 			select cc.MovementDate from PP_Order pp  join PP_Cost_Collector cc on (pp.PP_Order_ID = cc.PP_Order_ID) where pp.PP_Order_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 53027  and cc.DocStatus in (''CO'', ''CL'') order by cc.MovementDate limit 1 		WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 			select io.MovementDate from M_InOutLine iol  			join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 			where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 			and io.DocStatus in (''CO'', ''CL'') END CASE ) ',Updated=TO_TIMESTAMP('2015-09-29 15:21:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552754
;

-- 29.09.2015 15:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 			0 		WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 			select iol.MovementQty from M_InOutLine iol  			join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 			where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 			and io.DocStatus in (''CO'', ''CL'') END CASE )', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-09-29 15:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552757
;

-- 29.09.2015 15:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552757,556342,0,540705,0,TO_TIMESTAMP('2015-09-29 15:23:10','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.materialtracking',0,'Y','Y','Y','Y','N','N','N','N','N','Empfangene Menge',60,60,0,TO_TIMESTAMP('2015-09-29 15:23:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 29.09.2015 15:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556342 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 29.09.2015 15:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='AD_Table_ID NOT IN (53027 , 320)',Updated=TO_TIMESTAMP('2015-09-29 15:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540613
;

-- 29.09.2015 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 		(select pp.DocumentNo from PP_Order pp where pp.PP_Order_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 53027) 	WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 		(select io.DocumentNo from M_InOutLine iol  		join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 		where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 		and io.DocStatus in (''CO'', ''CL'')) END  )',Updated=TO_TIMESTAMP('2015-09-29 15:59:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552753
;

-- 29.09.2015 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 			(select cc.MovementDate from PP_Order pp  join PP_Cost_Collector cc on (pp.PP_Order_ID = cc.PP_Order_ID) where pp.PP_Order_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 53027  and cc.DocStatus in (''CO'', ''CL'') order by cc.MovementDate limit 1) 		WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 			(select io.MovementDate from M_InOutLine iol  			join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 			where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 			and io.DocStatus in (''CO'', ''CL'')) 	END  ) ',Updated=TO_TIMESTAMP('2015-09-29 15:59:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552754
;

-- 29.09.2015 15:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='( CASE  	WHEN M_Material_Tracking_Ref.AD_Table_ID = 53027 THEN 			0 		WHEN M_Material_Tracking_Ref.AD_Table_ID = 320 THEN 			(select iol.MovementQty from M_InOutLine iol  			join M_InOut io on (io.M_InOut_ID = iol.M_InOut_ID) 			where iol.M_InOutLine_ID = M_Material_Tracking_Ref.Record_ID and M_Material_Tracking_Ref.AD_Table_ID = 320 			and io.DocStatus in (''CO'', ''CL'')) END  )',Updated=TO_TIMESTAMP('2015-09-29 15:59:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552757
;

