-- 2017-05-06T13:21:48.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53015,540165,TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:48.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540229,540165,TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:48.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540230,540165,TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540229,540356,TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-05-06 13:21:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53289,0,53015,540356,543724,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53290,0,53015,540356,543725,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53296,0,53015,540356,543726,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Ressourcen-Nr.',30,30,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53293,0,53015,540356,543727,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Ressourcen Name',40,40,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.156
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53292,0,53015,540356,543728,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53291,0,53015,540356,543729,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',60,60,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53295,0,53015,540356,543730,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ressourcenart',70,70,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53297,0,53015,540356,543731,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','Y','N','Lager',80,80,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53294,0,53015,540356,543732,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','Y','N','Ressourcen Verantwortlicher',90,90,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53298,0,53015,540356,543733,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Ressource ist verfügbar','Ressource ist verfügbar für Zuordnungen','Y','N','Y','Y','N','Verfügbar',100,100,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53299,0,53015,540356,543734,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Produktionsressource',110,110,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53300,0,53015,540356,543735,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Produktions Ressourcen Art',120,120,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56280,0,53015,540356,543736,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.','The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.','Y','N','Y','Y','N','Planungshorizont',130,130,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-06T13:21:49.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,555415,0,53015,540356,543737,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100,'Exclude from MRP calculation','Y','N','Y','Y','N','Exclude from MRP',140,140,0,TO_TIMESTAMP('2017-05-06 13:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

