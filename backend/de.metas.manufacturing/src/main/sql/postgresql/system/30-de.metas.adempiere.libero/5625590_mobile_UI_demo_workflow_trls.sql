-- 2022-02-11T17:14:58.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='QR Label drucken',Updated=TO_TIMESTAMP('2022-02-11 19:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540262
;

-- 2022-02-11T17:14:58.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='QR Label drucken'  WHERE AD_WF_Node_ID=540262 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-02-11T17:15:14.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='Maschine reinigen ',Updated=TO_TIMESTAMP('2022-02-11 19:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540257
;

-- 2022-02-11T17:15:14.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='Maschine reinigen '  WHERE AD_WF_Node_ID=540257 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-02-11T17:15:15.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='Maschine reinigen',Updated=TO_TIMESTAMP('2022-02-11 19:15:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540257
;

-- 2022-02-11T17:15:15.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='Maschine reinigen'  WHERE AD_WF_Node_ID=540257 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-02-11T17:15:58.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='Rohware einfüllen',Updated=TO_TIMESTAMP('2022-02-11 19:15:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540258
;

-- 2022-02-11T17:15:58.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='Rohware einfüllen'  WHERE AD_WF_Node_ID=540258 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-02-11T17:16:15.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='mischen',Updated=TO_TIMESTAMP('2022-02-11 19:16:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540259
;

-- 2022-02-11T17:16:15.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='mischen'  WHERE AD_WF_Node_ID=540259 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-02-11T17:16:26.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='Fertigprodukt entnehmen',Updated=TO_TIMESTAMP('2022-02-11 19:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540260
;

-- 2022-02-11T17:16:26.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='Fertigprodukt entnehmen'  WHERE AD_WF_Node_ID=540260 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-02-11T17:16:38.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='Produktion abschliessen',Updated=TO_TIMESTAMP('2022-02-11 19:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540261
;

-- 2022-02-11T17:16:38.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='Produktion abschliessen'  WHERE AD_WF_Node_ID=540261 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;





update pp_order_node ppon set name=n.name
from ad_wf_node n
where n.ad_wf_node_id = ppon.ad_wf_node_id
and ppon.name <> n.name;

