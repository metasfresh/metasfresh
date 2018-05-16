-- 2018-05-16T06:48:23.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541090,540740,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-05-16T06:48:23.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540740 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-05-16T06:48:23.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540966,540740,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.530
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540967,540740,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540966,541590,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563986,0,541090,541590,551943,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563987,0,541090,541590,551944,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',10,20,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563988,0,541090,541590,551945,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',20,30,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563990,0,541090,541590,551946,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,40,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563992,0,541090,541590,551947,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'First effective day (inclusive)','The Start Date indicates the first or starting date','Y','N','Y','Y','N','Anfangsdatum',40,50,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563993,0,541090,541590,551948,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Last effective date (inclusive)','The End Date indicates the last date in this range.','Y','N','Y','Y','N','Enddatum',50,60,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563991,0,541090,541590,551949,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',60,70,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564161,0,541090,541590,551950,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','MKTG_Platform',70,80,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564179,0,541090,541590,551951,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Externe Datensatz-ID',80,90,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564180,0,541090,541590,551952,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Zuletzt auf Platform hochgeladen',90,100,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.933
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564181,0,541090,541590,551953,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Zuletzt von Platform runtergeladen',100,110,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:23.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564182,0,541090,541590,551954,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Letzter Synchronisierungsstatus',110,120,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564183,0,541090,541590,551955,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Synchronisierungstatus-Detail',120,130,0,TO_TIMESTAMP('2018-05-16 06:48:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,541102,540741,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-05-16T06:48:24.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540741 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-05-16T06:48:24.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540968,540741,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540968,541591,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564162,0,541102,541591,551956,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Zuletzt auf Platform hochgeladen',0,10,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564163,0,541102,541591,551957,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Zuletzt von Platform runtergeladen',0,20,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564164,0,541102,541591,551958,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Letzter Synchronisierungsstatus',0,30,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564165,0,541102,541591,551959,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Synchronisierungstatus-Detail',0,40,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564166,0,541102,541591,551960,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Platformseitig deaktiviert',0,50,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.347
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564167,0,541102,541591,551961,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Externe Datensatz-ID',0,60,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564168,0,541102,541591,551962,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','MKTG_Platform',0,70,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564088,0,541102,541591,551963,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','MKTG_Campaign',0,80,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564089,0,541102,541591,551964,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','MKTG_ContactPerson',0,90,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564086,0,541102,541591,551965,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,100,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564091,0,541102,541591,551966,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','N','Name',0,110,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564092,0,541102,541591,551967,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','N','eMail',0,120,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.604
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564090,0,541102,541591,551968,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Ansprechpartner',0,130,0,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-16T06:48:24.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,564093,0,540245,551968,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-05-16 06:48:24','YYYY-MM-DD HH24:MI:SS'),100)
;

