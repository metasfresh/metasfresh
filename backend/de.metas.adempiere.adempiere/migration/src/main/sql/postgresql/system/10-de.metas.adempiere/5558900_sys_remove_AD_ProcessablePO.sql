delete from ad_menu where ad_window_id=540099;

-- 2020-05-08T14:43:42.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540099
;

-- 2020-05-08T14:43:42.810Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540099
;

-- 2020-05-08T14:43:42.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=540099
;

-- 2020-05-08T14:45:10.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table_Process WHERE AD_Table_Process_ID=540390
;

delete from ad_menu where ad_process_id=540170;
-- 2020-05-08T14:46:20.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=540170
;

-- 2020-05-08T14:46:20.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=540170
;

-- 2020-05-08T14:46:47.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540274
;

-- 2020-05-08T14:46:47.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540274
;

-- 2020-05-08T14:49:07.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=541292
;

-- 2020-05-08T14:49:07.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=541292
;

DELETE FROM AD_Menu WHERE ad_window_id=540108;
-- 2020-05-08T14:54:39.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540108
;

-- 2020-05-08T14:54:39.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540108
;

-- 2020-05-08T14:54:39.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=540108
;

-- 2020-05-08T14:54:53.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540293
;

-- 2020-05-08T14:54:53.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540293
;

-- 2020-05-08T14:55:18.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=541293
;

-- 2020-05-08T14:55:18.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=541293
;

-- 2020-05-08T14:55:22.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=1002291
--;

-- 2020-05-08T14:55:22.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--DELETE FROM AD_Element WHERE AD_Element_ID=1002291
--;

UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2020-05-13 16:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002291
;



-- 2020-05-08T14:56:15.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET EntityType='de.metas.dataentry',Updated=TO_TIMESTAMP('2020-05-08 16:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541293
;

-- correction by rc
-- select * from ad_element where entitytype = 'de.metas.processing';
-- ->
-- 
-- 541292	0	0	Y	2011-04-13 14:23:29.000000 +02:00	100	2011-04-13 14:23:29.000000 +02:00	100	AD_ProcessablePO_ID	de.metas.processing	AD_ProcessablePO	AD_ProcessablePO											
-- 541293	0	0	Y	2011-04-13 15:26:32.000000 +02:00	100	2011-04-13 15:26:32.000000 +02:00	100	IsProcessDirectly	de.metas.processing	Sofort verarbeiten	Sofort verarbeiten											
-- 1002290	0	0	Y	2018-11-06 22:53:08.554379 +01:00	99	2018-11-06 22:53:08.554379 +01:00	99		de.metas.processing	Verarbeitet	Verarbeitet	Checkbox sagt aus, ob der Beleg verarbeitet wurde. 	Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.									



UPDATE AD_Element set entityType = 'de.metas.dataentry' where ad_element_ID in (541292,
541293,
1002290);

-- end of correction

-- another correction
UPDATE AD_Element SET EntityType='D', Updated='2020-05-19 08:13:08.556862+02', UpdatedBy=99 WHERE EntityType='de.metas.processing';

-- 2020-05-08T14:56:21.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_EntityType WHERE AD_EntityType_ID=540027
;
