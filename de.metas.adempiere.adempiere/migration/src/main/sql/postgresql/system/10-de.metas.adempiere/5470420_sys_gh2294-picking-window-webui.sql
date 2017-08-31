-- 2017-08-31T11:28:05.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540830,540455,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-08-31T11:28:05.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540455 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-08-31T11:28:05.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540609,540455,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540610,540455,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540609,541070,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.784
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558672,0,540830,541070,548003,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','Y','N','Lager',10,10,0,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.814
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558683,0,540830,541070,548004,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','Y','N','Produkt',20,20,0,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558667,0,540830,541070,548005,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','Y','N','Auftrag',30,30,0,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558661,0,540830,541070,548006,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','Y','N','Geschäftspartner',40,40,0,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,558664,0,540194,548006,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558676,0,540830,541070,548007,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausliefermenge',50,50,0,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:05.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559574,0,540830,541070,548008,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Qty picked (planned)',60,60,0,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:06.019
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558679,0,540830,541070,548009,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bereitstellungsdatum',70,70,0,TO_TIMESTAMP('2017-08-31 11:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-08-31T11:28:06.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558678,0,540830,541070,548010,TO_TIMESTAMP('2017-08-31 11:28:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferdatum',80,80,0,TO_TIMESTAMP('2017-08-31 11:28:06','YYYY-MM-DD HH24:MI:SS'),100)
;

