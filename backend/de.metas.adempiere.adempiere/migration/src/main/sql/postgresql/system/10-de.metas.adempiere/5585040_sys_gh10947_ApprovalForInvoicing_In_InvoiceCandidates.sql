-- 2021-04-07T15:00:19.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,554872,0,540279,540058,583208,'F',TO_TIMESTAMP('2021-04-07 18:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Freigabe zur Fakturierung',35,0,0,TO_TIMESTAMP('2021-04-07 18:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-07T16:30:32.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,622380,0,543052,544365,583339,'F',TO_TIMESTAMP('2021-04-07 19:30:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Freigabe zur Fakturierung',35,0,0,TO_TIMESTAMP('2021-04-07 19:30:32','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2021-04-09T17:09:23.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='',Updated=TO_TIMESTAMP('2021-04-09 20:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551293
;

-- 2021-04-09T17:10:22.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-04-09 20:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 

WHERE AD_Column_ID=551293
;

