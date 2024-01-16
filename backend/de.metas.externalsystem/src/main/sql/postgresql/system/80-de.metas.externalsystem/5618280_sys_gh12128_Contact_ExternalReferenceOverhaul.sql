-- shipTo contact external reference overhaul
update s_externalreference
set externalreference = replace(extRefWithSuffix.externalreference, '-shipTo', ''), updated = '2021-12-11 11:34', updatedby = 99
    from s_externalreference extRefWithSuffix
where extRefWithSuffix.externalreference like '%-shipTo'
  and extRefWithSuffix.externalsystem = 'Shopware6'
  and extRefWithSuffix.type = 'UserID'
  and extRefWithSuffix.s_externalreference_id = s_externalreference.s_externalreference_id
  and not exists(select 1
    from s_externalreference userRefWithNoSuffix
    where userRefWithNoSuffix.externalreference = replace(extRefWithSuffix.externalreference, '-shipTo', '')
  and userRefWithNoSuffix.externalsystem = 'Shopware6'
  and userRefWithNoSuffix.type = 'UserID')
;


-- billTo contact external reference overhaul
update s_externalreference
set externalreference = replace(extRefWithSuffix.externalreference, '-billTo', ''), updated = '2021-12-11 11:35', updatedby = 99
    from s_externalreference extRefWithSuffix
where extRefWithSuffix.externalreference like '%-billTo'
  and extRefWithSuffix.externalsystem = 'Shopware6'
  and extRefWithSuffix.type = 'UserID'
  and extRefWithSuffix.s_externalreference_id = s_externalreference.s_externalreference_id
  and not exists(select 1
    from s_externalreference userRefWithNoSuffix
    where userRefWithNoSuffix.externalreference = replace(extRefWithSuffix.externalreference, '-billTo', '')
  and userRefWithNoSuffix.externalsystem = 'Shopware6'
  and userRefWithNoSuffix.type = 'UserID')
;


