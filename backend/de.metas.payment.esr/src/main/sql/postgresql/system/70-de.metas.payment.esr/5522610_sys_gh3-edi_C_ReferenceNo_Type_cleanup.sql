
--
-- Display C_ReferenceNo_Type.Description field in webUI
-- 2019-05-27T13:30:19.853
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550703,0,540433,541408,559426,'F',TO_TIMESTAMP('2019-05-27 13:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Description',50,0,0,TO_TIMESTAMP('2019-05-27 13:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;
-- delete obsolete C_ReferenceNo_Type_Table records (the "why" is explained in the now-dispoayed description field)
DELETE FROM C_ReferenceNo_Type_Table WHERE C_ReferenceNo_Type_ID IN (540005/*ESRReferenceNumber*/,540006/*InvoiceReference*/);
