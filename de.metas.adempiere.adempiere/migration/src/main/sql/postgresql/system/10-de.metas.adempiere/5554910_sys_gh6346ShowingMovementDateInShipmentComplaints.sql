-- 2020-03-19T15:49:52.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint'' | @M_InOut_ID@ ! 0',Updated=TO_TIMESTAMP('2020-03-19 17:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557234
;

-- 2020-03-19T15:50:14.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='(@R_RequestType_InternalName/-1@ =''A_CustomerComplaint'' | @R_RequestType_InternalName/-1@ = ''B_VendorComplaint'' | @R_RequestType_InternalName/-1@ = ''C_ConsumerComplaint'') & @M_InOut_ID@ ! 0',Updated=TO_TIMESTAMP('2020-03-19 17:50:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557234
;

-- 2020-03-19T15:50:40.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,557234,0,402,566603,540046,'F',TO_TIMESTAMP('2020-03-19 17:50:40','YYYY-MM-DD HH24:MI:SS'),100,'Datum, zu dem die Ware geliefert wurde','Y','N','N','Y','N','N','N',0,'Lieferdatum',60,0,0,TO_TIMESTAMP('2020-03-19 17:50:40','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

