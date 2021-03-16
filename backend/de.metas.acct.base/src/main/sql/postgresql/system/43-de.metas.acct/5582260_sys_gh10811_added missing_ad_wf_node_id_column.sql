-- 2021-03-16T08:22:33.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2021-03-16 10:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53364
;

-- 2021-03-16T08:23:22.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,53364,0,53025,540370,580061,'F',TO_TIMESTAMP('2021-03-16 10:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Workflow Knoten (Tätigkeit), Schritt oder Prozess','"Knoten" bezeichnet einen einzelen Schritt oder Prozess in einem Workflow.','Y','N','N','Y','N','N','N',0,'Knoten',130,0,0,TO_TIMESTAMP('2021-03-16 10:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-16T08:23:39.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-03-16 10:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580061
;

-- 2021-03-16T08:23:54.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-03-16 10:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=580061
;

