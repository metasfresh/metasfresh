-- 2019-04-29T08:00:44.172
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2019-04-29 08:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1509
;

-- 2019-04-29T08:00:44.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2019-04-29 08:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3024
;

-- 2019-04-29T08:00:44.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2019-04-29 08:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3392
;

-- unlreated cleanup
UPDATE c_doctype 
SET IsDefault='N', Updatedby=99, Updated='2019-04-29 13:49:58.483977+00'
WHERE IsDefault='Y' AND DocBaseType='ARI' and DocSubType IN ('KT'/*Kanton*/,'GM'/*Gemeinde*/,'KV'/*Krankenversicherung*/,'EA'/*Klientenantil*/);


-- 2019-04-29T10:56:14.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2019-04-29 10:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4204
;

-- 2019-04-29T11:45:10.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,580077,0,256,541513,559072,'F',TO_TIMESTAMP('2019-04-29 11:45:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'HUAggregationType',150,0,0,TO_TIMESTAMP('2019-04-29 11:45:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-29T11:48:58.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-04-29 11:48:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559072
;

