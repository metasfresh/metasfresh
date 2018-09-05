--
-- mimic the way DocAction and DocStatus AD_UI stuff is set up in the sames order window.
--
-- 2018-08-02T13:44:51.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540388,541697,TO_TIMESTAMP('2018-08-02 13:44:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','rest',30,TO_TIMESTAMP('2018-08-02 13:44:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-02T13:45:51.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,542193,0,540096,541697,552494,'F',TO_TIMESTAMP('2018-08-02 13:45:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Belegstatus',10,0,0,TO_TIMESTAMP('2018-08-02 13:45:51','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2018-08-02T13:49:27.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,542194,0,540096,541697,552495,'F',TO_TIMESTAMP('2018-08-02 13:49:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Document Action',20,0,0,TO_TIMESTAMP('2018-08-02 13:49:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-08-02T13:56:31.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545685
;

-- 2018-08-02T13:56:39.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545686
;

-- 2018-08-02T13:57:22.407
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2018-08-02 13:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=552494
;

