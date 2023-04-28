-- 2022-02-22T09:09:51.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='Waage scannen.',Updated=TO_TIMESTAMP('2022-02-22 11:09:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540263
;

-- 2022-02-22T09:09:51.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='Waage scannen.'  WHERE AD_WF_Node_ID=540263 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-02-22T09:09:54.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node SET Action='Z', Name='Waage scannen',Updated=TO_TIMESTAMP('2022-02-22 11:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=540116 WHERE AD_WF_Node_ID=540263
;

-- 2022-02-22T09:09:54.244Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WF_Node_Trl trl SET Description=NULL, Help=NULL, Name='Waage scannen'  WHERE AD_WF_Node_ID=540263 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

update pp_order_node ppon set name=n.name
from ad_wf_node n
where 
n.AD_WF_Node_ID=540263
and n.ad_wf_node_id = ppon.ad_wf_node_id
and ppon.name <> n.name;

