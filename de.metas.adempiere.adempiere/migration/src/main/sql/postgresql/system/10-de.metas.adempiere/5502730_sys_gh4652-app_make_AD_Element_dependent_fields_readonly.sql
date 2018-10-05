
-- AD_Field.Name
-- 2018-10-05T10:40:52.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:40:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=127
;

-- AD_Field.Description
-- 2018-10-05T10:41:38.569
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=128
;

-- AD_Field.Help
-- 2018-10-05T10:42:09.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=129
;

-- AD_Tab of AD_Field_Trl
-- 2018-10-05T10:43:44.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=115
;

-----------------
--same for AD_Column

-- 2018-10-05T10:45:29.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=152
;

-- 2018-10-05T10:45:35.551
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=153
;

-- 2018-10-05T10:45:48.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=154
;

-- 2018-10-05T10:46:01.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-10-05 10:46:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=681
;

-----------------
-- AD_Process_Para-- 2018-10-05T10:50:11.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@AD_Element_ID\0@>0', TechnicalNote='This field is only editable if no AD_Element_ID is referenced; Otherwise, the field''s terminology is maintained in the selected AD_Element',Updated=TO_TIMESTAMP('2018-10-05 10:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2822
;

-- 2018-10-05T10:50:42.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This field is only editable if no AD_Element_ID is referenced; Otherwise, the field''s terminology is maintained in the selected AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 10:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2823
;

-- 2018-10-05T10:50:52.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This field is only editable if no AD_Element_ID is referenced; Otherwise, the field''s terminology is maintained in the selected AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 10:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2822
;

-- 2018-10-05T10:50:57.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@AD_Element_ID\0@>0',Updated=TO_TIMESTAMP('2018-10-05 10:50:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2823
;

-- 2018-10-05T10:51:49.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@AD_Element_ID\0@>0', TechnicalNote='This field is only editable if no AD_Element_ID is referenced; Otherwise, the field''s terminology is maintained in the selected AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 10:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2824
;

-- 2018-10-05T10:52:48.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@AD_Element_ID\0@>0', TechnicalNote='This field is only editable if no AD_Element_ID is referenced; Otherwise, the field''s terminology is maintained in the selected AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 10:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2841
;

-- 2018-10-05T10:53:06.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@AD_Element_ID\0@>0', TechnicalNote='This field is only editable if no AD_Element_ID is referenced; Otherwise, the field''s terminology is maintained in the selected AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 10:53:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2840
;

-- 2018-10-05T10:53:36.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@AD_Element_ID\0@>0', TechnicalNote='This field is only editable if no AD_Element_ID is referenced; Otherwise, the field''s terminology is maintained in the selected AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 10:53:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3743
;




-- 2018-10-05T11:05:08.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='This column''s value is maintained via AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 11:05:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=111
;

-- 2018-10-05T11:06:29.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='This column''s value is maintained via AD_Element.',Updated=TO_TIMESTAMP('2018-10-05 11:06:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=116
;

-- 2018-10-05T11:07:25.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@AD_Element_ID\0@>0',Updated=TO_TIMESTAMP('2018-10-05 11:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=116
;



-- 2018-10-05T11:24:12.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@AD_Element_ID\0@>0',Updated=TO_TIMESTAMP('2018-10-05 11:24:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=111
;


-- 2018-10-05T13:51:09.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2018-10-05 13:51:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2604
;
