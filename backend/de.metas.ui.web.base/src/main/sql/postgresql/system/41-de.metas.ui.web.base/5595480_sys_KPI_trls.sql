-- 2021-06-29T04:36:19.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI_Field SET UOMSymbol='offen',Updated=TO_TIMESTAMP('2021-06-29 07:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540001
;

-- 2021-06-29T04:36:19.307Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI_Field_Trl trl SET Name='Requests', OffsetName=NULL, UOMSymbol='offen'  WHERE WEBUI_KPI_Field_ID=540001 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-29T04:36:28.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI_Field_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-29 07:36:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_Trl_ID=1000057
;

-- 2021-06-29T04:36:30.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI_Field_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-29 07:36:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_Trl_ID=1000060
;

-- 2021-06-29T04:36:35.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI_Field_Trl SET IsTranslated='Y', UOMSymbol='open',Updated=TO_TIMESTAMP('2021-06-29 07:36:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_Trl_ID=1000059
;

-- 2021-06-29T04:40:32.033Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET Name='Open invoices',Updated=TO_TIMESTAMP('2021-06-29 07:40:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

-- 2021-06-29T04:40:37.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET Name='Offene Rechnungen',Updated=TO_TIMESTAMP('2021-06-29 07:40:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

-- 2021-06-29T04:40:53.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET Name='Requests',Updated=TO_TIMESTAMP('2021-06-29 07:40:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540002
;

-- 2021-06-29T04:40:57.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET Name='Open Invoices',Updated=TO_TIMESTAMP('2021-06-29 07:40:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

