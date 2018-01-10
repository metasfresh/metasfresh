-- 2018-01-10T06:30:07.222
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540981,540583,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-01-10T06:30:07.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540583 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-01-10T06:30:07.277
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540782,540583,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540783,540583,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540782,541341,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561310,0,540981,541341,550050,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561311,0,540981,541341,550051,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.427
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561314,0,540981,541341,550052,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','Y','N','Auftrag',30,30,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561315,0,540981,541341,550053,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','Y','N','Auftragsposition',40,40,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561317,0,540981,541341,550054,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Pauschale - Vertragsperiode',50,50,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.507
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561318,0,540981,541341,550055,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Abo-Verlauf',60,60,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561319,0,540981,541341,550056,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',70,70,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561321,0,540981,541341,550057,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Bestellte Menge','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','Y','N','Bestellte Menge',80,80,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-10T06:30:07.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561322,0,540981,541341,550058,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100,'Reservierte Menge','Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.','Y','N','Y','Y','N','Reservierte Menge',90,90,0,TO_TIMESTAMP('2018-01-10 06:30:07','YYYY-MM-DD HH24:MI:SS'),100)
;

