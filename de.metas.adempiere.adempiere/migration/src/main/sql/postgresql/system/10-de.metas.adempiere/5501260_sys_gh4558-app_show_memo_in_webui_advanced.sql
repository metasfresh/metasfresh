-- 2018-09-12T08:17:43.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element 
(AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 

SELECT 0,545318,0,220,540671,553797,'F',TO_TIMESTAMP('2018-09-12 08:17:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Sonstiges',90,0,0,TO_TIMESTAMP('2018-09-12 08:17:42','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from AD_UI_Element where AD_Field_ID=545318)
;

