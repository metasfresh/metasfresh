
-- 2021-04-09T06:18:54.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=60, TechnicalNote='Setting the length to 60 because right now the value is set from AD_Archive.Name which can also be 60 chars long',Updated=TO_TIMESTAMP('2021-04-09 08:18:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548162
;

-- 2021-04-09T06:18:57.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_doc_outbound_log','DocumentNo','VARCHAR(60)',null,null)
;

-- 2021-04-09T06:25:00.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET InternalName='C_Doc_Outbound_Log',Updated=TO_TIMESTAMP('2021-04-09 08:25:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540170
;

-- 2021-04-09T06:25:35.040Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='',Updated=TO_TIMESTAMP('2021-04-09 08:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548015
;

