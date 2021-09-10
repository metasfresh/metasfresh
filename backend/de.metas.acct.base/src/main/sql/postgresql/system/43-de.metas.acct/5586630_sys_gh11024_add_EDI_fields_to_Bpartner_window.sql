-- 2021-04-27T10:19:56.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET DisplayLogic='@IsEdiInvoicRecipient/N@=Y', Updated=TO_TIMESTAMP('2021-04-27 13:19:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 616964
;

-- 2021-04-27T10:20:34.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 553178, 0, 220, 1000011, 583834, 'F', TO_TIMESTAMP('2021-04-27 13:20:34', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'EDI-ID des DESADV-Empfängers', 90, 0, 0, TO_TIMESTAMP('2021-04-27 13:20:34', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-27T10:20:44.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 616964, 0, 220, 1000011, 583835, 'F', TO_TIMESTAMP('2021-04-27 13:20:44', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'EDI-ID des INVOIC-Empfängers', 100, 0, 0, TO_TIMESTAMP('2021-04-27 13:20:44', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-04-27T10:21:34.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=20, Updated=TO_TIMESTAMP('2021-04-27 13:21:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 1000082
;

-- 2021-04-27T10:21:44.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=30, Updated=TO_TIMESTAMP('2021-04-27 13:21:44', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 564728
;

-- 2021-04-27T10:21:54.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=50, Updated=TO_TIMESTAMP('2021-04-27 13:21:54', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 1000083
;

-- 2021-04-27T10:21:58.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=40, Updated=TO_TIMESTAMP('2021-04-27 13:21:58', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 583834
;

-- 2021-04-27T10:22:01.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element
SET SeqNo=60, Updated=TO_TIMESTAMP('2021-04-27 13:22:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_UI_Element_ID = 583835
;

