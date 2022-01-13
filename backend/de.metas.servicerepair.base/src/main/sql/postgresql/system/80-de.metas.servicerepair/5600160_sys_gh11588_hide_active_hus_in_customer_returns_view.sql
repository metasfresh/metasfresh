-- 2021-08-06T10:55:45.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='HUStatus NOT IN (''D'', ''A'') and exists (     select 1     from m_hu_attribute hua     inner join m_attribute a on a.m_attribute_id=hua.m_attribute_id     where hua.m_HU_ID=M_HU.m_hu_id  and a.value = ''SerialNo'' and hua.value is not null)',Updated=TO_TIMESTAMP('2021-08-06 13:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543240
;

