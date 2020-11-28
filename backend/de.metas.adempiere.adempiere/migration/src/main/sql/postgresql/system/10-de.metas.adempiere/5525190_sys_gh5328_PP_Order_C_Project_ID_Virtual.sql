-- 2019-06-19T15:20:18.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT COALESCE(ol.C_Project_ID, o.C_Project_ID) from  C_OrderLine ol JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID where ol.C_OrderLine_ID = PP_Order.C_OrderLine_ID)', IsUpdateable='N',Updated=TO_TIMESTAMP('2019-06-19 15:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53631
;

-- 2019-06-19T15:42:59.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,54158,0,53054,560010,540186,'F',TO_TIMESTAMP('2019-06-19 15:42:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'project',70,0,0,TO_TIMESTAMP('2019-06-19 15:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-06-19T15:43:08.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2019-06-19 15:43:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54158
;

-- 2019-06-19T15:54:44.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@$Element_PJ@=''Y''',Updated=TO_TIMESTAMP('2019-06-19 15:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54158
;

-- 2019-06-19T16:09:36.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2019-06-19 16:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542327
;

-- 2019-06-19T16:09:36.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2019-06-19 16:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542369
;

-- -- 2019-06-19T16:10:31.644
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2019-06-19 16:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54158
-- ;

-- -- 2019-06-19T16:23:11.952
-- -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
-- UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2019-06-19 16:23:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2593
-- ;

-- 2019-06-19T16:25:24.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2019-06-19 16:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53631
;

-- 2019-06-19T16:26:29.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,2593,0,186,560011,1000026,'F',TO_TIMESTAMP('2019-06-19 16:26:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'project',70,0,0,TO_TIMESTAMP('2019-06-19 16:26:29','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2019-06-19T17:00:08.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2019-06-19 17:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53631
;

