-- 2021-12-06T15:10:06.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ColumnCallout (AD_Client_ID,AD_ColumnCallout_ID,AD_Column_ID,AD_Org_ID,AD_Table_ID,Classname,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,541246,2197,0,259,'org.compiere.model.CalloutOrder.shipper',TO_TIMESTAMP('2021-12-06 16:10:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-12-06 16:10:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-06T15:47:43.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540010,547652,TO_TIMESTAMP('2021-12-06 16:47:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','Versandart',30,TO_TIMESTAMP('2021-12-06 16:47:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-06T15:49:30.860Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547652, SeqNo=10,Updated=TO_TIMESTAMP('2021-12-06 16:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560152
;

-- 2021-12-06T15:50:38.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=547652, SeqNo=20,Updated=TO_TIMESTAMP('2021-12-06 16:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544774
;

-- 2021-12-06T15:52:10.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2021-12-06 16:52:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544774
;

-- 2021-12-06T15:52:16.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2021-12-06 16:52:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560152
;

