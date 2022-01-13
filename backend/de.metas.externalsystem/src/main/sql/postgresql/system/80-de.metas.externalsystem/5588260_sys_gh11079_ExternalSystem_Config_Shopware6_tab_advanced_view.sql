-- 2021-05-12T14:23:52.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=582405
;

-- 2021-05-12T14:23:55.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=582406
;

-- 2021-05-12T14:23:57.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584110
;

-- 2021-05-12T14:23:59.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584685
;

-- 2021-05-12T14:24:01.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584686
;

-- 2021-05-12T14:24:04.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584687
;

-- 2021-05-12T14:24:06.648Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584688
;

-- 2021-05-12T14:24:19.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543315,545817,TO_TIMESTAMP('2021-05-12 17:24:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','path',20,TO_TIMESTAMP('2021-05-12 17:24:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:24:38.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,640824,0,543435,584745,545817,'F',TO_TIMESTAMP('2021-05-12 17:24:38','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!','Y','N','N','Y','N','N','N',0,'Kunden JSON-Path',10,0,0,TO_TIMESTAMP('2021-05-12 17:24:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:24:50.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,641158,0,543435,584746,545817,'F',TO_TIMESTAMP('2021-05-12 17:24:50','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!','Y','N','N','Y','N','N','N',0,'Adress JSON-Path',20,0,0,TO_TIMESTAMP('2021-05-12 17:24:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:25:53.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,644698,0,543435,584747,545817,'F',TO_TIMESTAMP('2021-05-12 17:25:53','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.','Y','N','N','Y','N','N','N',0,'Vertriebpartner JSON-Path',30,0,0,TO_TIMESTAMP('2021-05-12 17:25:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:26:28.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543316,545818,TO_TIMESTAMP('2021-05-12 17:26:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','tax',30,TO_TIMESTAMP('2021-05-12 17:26:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:26:35.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-12 17:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=544977
;

-- 2021-05-12T14:26:40.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2021-05-12 17:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545818
;

-- 2021-05-12T14:28:17.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645423,0,543435,584748,545818,'F',TO_TIMESTAMP('2021-05-12 17:28:17','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Normale MWSt.)" benutzt werden soll. Beispiel: "0, 7.7, 19"','Y','N','N','Y','N','N','N',0,'Normale MWSt. Sätze',10,0,0,TO_TIMESTAMP('2021-05-12 17:28:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:28:43.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645424,0,543435,584749,545818,'F',TO_TIMESTAMP('2021-05-12 17:28:43','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit normalem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','Y','N','N','Y','N','N','N',0,'Versandkostenprodukt (Normale MWSt.)',20,0,0,TO_TIMESTAMP('2021-05-12 17:28:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:29:06.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645426,0,543435,584750,545818,'F',TO_TIMESTAMP('2021-05-12 17:29:06','YYYY-MM-DD HH24:MI:SS'),100,'Komma-Separierte Liste von Versandkosten-Steuersätzen (mit Dezimal-Punkt!), bei denen das "Versandkostenprodukt (Reduzierte MWSt.)" benutzt werden soll. Beispiel: "2.5, 7"','Y','N','N','Y','N','N','N',0,'Reduzierte MWSt. Sätze',30,0,0,TO_TIMESTAMP('2021-05-12 17:29:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:29:15.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645425,0,543435,584751,545818,'F',TO_TIMESTAMP('2021-05-12 17:29:15','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, dass bei Versandkosten mit reduziertem MWSt-Satz benutzt wird. Die Steuerkategorie des Produktes muss einen passenden Steuersatz enthalten.','Y','N','N','Y','N','N','N',0,'Versandkostenprodukt (Reduzierte MWSt.)',40,0,0,TO_TIMESTAMP('2021-05-12 17:29:15','YYYY-MM-DD HH24:MI:SS'),100)
;

