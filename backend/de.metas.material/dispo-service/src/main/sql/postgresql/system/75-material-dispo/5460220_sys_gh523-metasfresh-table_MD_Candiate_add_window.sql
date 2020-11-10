
-- 2017-04-13T17:12:33.919
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth) VALUES (0,0,540334,TO_TIMESTAMP('2017-04-13 17:12:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','N','N','N','Y','Materialdisposition','N',TO_TIMESTAMP('2017-04-13 17:12:33','YYYY-MM-DD HH24:MI:SS'),100,'T',0,0)
;

-- 2017-04-13T17:12:33.925
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Window_ID=540334 AND NOT EXISTS (SELECT * FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2017-04-13T17:13:21.797
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,0,540802,540808,540334,TO_TIMESTAMP('2017-04-13 17:13:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','N','N','Y','N','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Materialdispo','N',10,0,TO_TIMESTAMP('2017-04-13 17:13:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:13:21.802
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Tab_ID=540802 AND NOT EXISTS (SELECT * FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2017-04-13T17:13:46.851
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540802
;

-- 2017-04-13T17:13:51.619
-- URL zum Konzept
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2017-04-13 17:13:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540802
;

-- 2017-04-13T17:14:01.399
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556466,558120,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.material.dispo','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','Y','N','N','N','N','N','Mandant',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.403
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558120 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:01.493
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556467,558121,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.material.dispo','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.494
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558121 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:01.572
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556470,558122,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.material.dispo','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.573
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558122 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:01.648
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556473,558123,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','Y','N','N','N','N','N','N','N','Dispositionskandidat',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.649
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558123 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:01.721
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556474,558124,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'de.metas.material.dispo','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.722
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558124 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:01.794
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556476,558125,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Menge',10,'de.metas.material.dispo','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','Y','Y','N','N','N','N','N','Menge',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.795
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558125 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:01.878
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556477,558126,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,7,'de.metas.material.dispo','Y','Y','Y','N','N','N','N','N','Plandatum',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.879
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558126 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:01.949
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556478,558127,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'de.metas.material.dispo','The Database Table provides the information of the table definition','Y','Y','Y','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:01.950
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558127 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:02.021
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556479,558128,0,540802,TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',22,'de.metas.material.dispo','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','Y','Y','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2017-04-13 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:02.022
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558128 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:02.094
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556481,558129,0,540802,TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','Y','Y','N','N','N','N','N','Typ',TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:02.095
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558129 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:02.165
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556482,558130,0,540802,TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',10,'de.metas.material.dispo','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','Y','N','N','N','N','N','Lager',TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:02.166
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558130 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:02.237
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556483,558131,0,540802,TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.material.dispo','Y','Y','Y','N','N','N','N','N','Elterndatensatz',TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:02.238
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558131 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:14:02.307
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556485,558132,0,540802,TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100,15,'de.metas.material.dispo','Y','Y','Y','N','N','N','N','N','Untertyp',TO_TIMESTAMP('2017-04-13 17:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-13T17:14:02.308
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558132 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-04-13T17:15:17.439
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558120
;

-- 2017-04-13T17:15:17.441
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558122
;

-- 2017-04-13T17:15:17.443
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558121
;

-- 2017-04-13T17:15:17.445
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558123
;

-- 2017-04-13T17:15:17.447
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558129
;

-- 2017-04-13T17:15:17.449
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558132
;

-- 2017-04-13T17:15:17.450
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558131
;

-- 2017-04-13T17:15:17.452
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558128
;

-- 2017-04-13T17:15:17.453
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558127
;

-- 2017-04-13T17:15:17.455
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558130
;

-- 2017-04-13T17:15:17.456
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558124
;

-- 2017-04-13T17:15:17.457
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558126
;

-- 2017-04-13T17:15:17.459
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2017-04-13 17:15:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558125
;

-- 2017-04-13T17:15:32.467
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-04-13 17:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558122
;

-- 2017-04-13T17:15:33.856
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2017-04-13 17:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558120
;

-- 2017-04-13T17:15:47.367
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558123
;

-- 2017-04-13T17:15:47.895
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558121
;

-- 2017-04-13T17:15:48.832
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558123
;

-- 2017-04-13T17:15:49.440
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558129
;

-- 2017-04-13T17:15:50.128
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558132
;

-- 2017-04-13T17:15:50.744
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558131
;

-- 2017-04-13T17:15:51.389
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558128
;

-- 2017-04-13T17:15:52.048
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558127
;

-- 2017-04-13T17:15:52.856
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558130
;

-- 2017-04-13T17:15:53.543
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558124
;

-- 2017-04-13T17:15:54.175
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558126
;

-- 2017-04-13T17:16:05.680
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558125
;

-- 2017-04-13T17:16:07.749
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-13 17:16:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558122
;

-- 2017-04-13T17:16:10.382
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-04-13 17:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558120
;

-- 2017-04-13T17:16:11.771
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-04-13 17:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558121
;

-- 2017-04-13T17:16:13.035
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-04-13 17:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558123
;

-- 2017-04-13T17:16:14.699
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-04-13 17:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558129
;

-- 2017-04-13T17:16:16.219
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-04-13 17:16:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558132
;

-- 2017-04-13T17:16:17.627
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-04-13 17:16:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558131
;

-- 2017-04-13T17:16:19.147
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-04-13 17:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558128
;

-- 2017-04-13T17:16:20.579
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-04-13 17:16:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558127
;

-- 2017-04-13T17:16:22.266
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-04-13 17:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558130
;

-- 2017-04-13T17:16:22.914
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-04-13 17:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558124
;

-- 2017-04-13T17:16:24.676
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-04-13 17:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558126
;

-- 2017-04-13T17:16:31.535
-- URL zum Konzept
UPDATE AD_Field SET SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-04-13 17:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558125
;

-- 2017-04-13T17:16:34.895
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558123
;

-- 2017-04-13T17:16:41.240
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558132
;

-- 2017-04-13T17:16:42.799
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558126
;

-- 2017-04-13T17:16:44.351
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558130
;

-- 2017-04-13T17:16:44.894
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558125
;

-- 2017-04-13T17:16:45.527
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2017-04-13 17:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558126
;

-- 2017-04-13T17:16:45.975
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558124
;

-- 2017-04-13T17:16:47.191
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2017-04-13 17:16:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558130
;

-- 2017-04-13T17:16:51.159
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558128
;

-- 2017-04-13T17:16:55.383
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2017-04-13 17:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558127
;

-- 2017-04-13T17:17:33.106
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y', IsHighVolume='Y',Updated=TO_TIMESTAMP('2017-04-13 17:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540808
;

-- 2017-04-13T17:17:41.095
-- URL zum Konzept
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-04-13 17:17:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556471
;

-- 2017-04-13T17:17:43.569
-- URL zum Konzept
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2017-04-13 17:17:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556472
;

-- 2017-04-13T17:17:50.095
-- URL zum Konzept
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2017-04-13 17:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540808
;

-- 2017-04-13T17:19:15.136
-- URL zum Konzept
UPDATE AD_Window SET InternalName='MD_Candidate',Updated=TO_TIMESTAMP('2017-04-13 17:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540334
;

-- 2017-04-13T17:19:38.835
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540801,0,540334,TO_TIMESTAMP('2017-04-13 17:19:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','MD_Candidate','Y','N','N','N','N','Materialdisposition',TO_TIMESTAMP('2017-04-13 17:19:38','YYYY-MM-DD HH24:MI:SS'),100,'Materialdisposition')
;

-- 2017-04-13T17:19:38.841
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540801 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-04-13T17:19:38.857
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540801, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540801)
;

-- 2017-04-13T17:20:38.765
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=167 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.768
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=357 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.768
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=229 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.769
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=412 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.770
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=256 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.770
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=477 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.771
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=197 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.772
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=179 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.773
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=503 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.774
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540510 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.775
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=196 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.776
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=228 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.776
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=479 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.777
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=482 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.778
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=481 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.779
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=411 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.786
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=537 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.786
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=311 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.787
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=292 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.788
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=504 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.789
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=515 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.790
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=545 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.792
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540289 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.793
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540560 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.793
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540569 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.794
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540801 AND AD_Tree_ID=10
;

-- 2017-04-13T17:20:38.795
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=183, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540669 AND AD_Tree_ID=10
;

