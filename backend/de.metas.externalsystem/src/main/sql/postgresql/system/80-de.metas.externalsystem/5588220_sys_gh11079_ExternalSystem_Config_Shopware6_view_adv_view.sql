-- 2021-05-12T10:52:16.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=543771, SeqNo=30,Updated=TO_TIMESTAMP('2021-05-12 13:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545810
;

-- 2021-05-12T10:52:54.462Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-12 13:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545680
;

-- 2021-05-12T10:53:02.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2021-05-12 13:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545810
;

-- 2021-05-12T10:55:22.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543770,545816,TO_TIMESTAMP('2021-05-12 13:55:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','path',20,TO_TIMESTAMP('2021-05-12 13:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T10:56:00.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643995,0,543838,584741,545816,'F',TO_TIMESTAMP('2021-05-12 13:55:59','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order die permanente ID des Kunden ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Orders ohne einen entsprechenden Wert ignoriert!','Y','N','N','Y','N','N','N',0,'Kunden JSON-Path',10,0,0,TO_TIMESTAMP('2021-05-12 13:55:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T10:56:14.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,643996,0,543838,584742,545816,'F',TO_TIMESTAMP('2021-05-12 13:56:14','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Addresse die die permanente Address-ID ausgelesen werden kann. ACHTUNG: wenn gesetzt, dann werden Addressen ohne einen entsprechenden Wert ignoriert!','Y','N','N','Y','N','N','N',0,'Adress JSON-Path',20,0,0,TO_TIMESTAMP('2021-05-12 13:56:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T10:56:23.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645427,0,543838,584743,545816,'F',TO_TIMESTAMP('2021-05-12 13:56:23','YYYY-MM-DD HH24:MI:SS'),100,'JSON-Path, der angibt wo innerhalb einer kundenspezifisch Angepassten Shopware-Order der Suchschlüssel der Vertriebspartners zu ausgelesen werden kann.','Y','N','N','Y','N','N','N',0,'Vertriebpartner JSON-Path',30,0,0,TO_TIMESTAMP('2021-05-12 13:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T10:57:40.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584733
;

-- 2021-05-12T10:57:42.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584734
;

-- 2021-05-12T10:57:46.777Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584735
;

-- 2021-05-12T10:57:49.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584736
;

-- 2021-05-12T10:57:51.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584738
;

-- 2021-05-12T10:58:01.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=545815
;

-- 2021-05-12T10:58:12.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=543858
;

-- 2021-05-12T10:58:18.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=543067
;

-- 2021-05-12T10:58:18.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=543067
;

