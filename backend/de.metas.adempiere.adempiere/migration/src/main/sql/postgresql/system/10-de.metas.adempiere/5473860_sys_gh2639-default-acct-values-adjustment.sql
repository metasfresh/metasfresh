-- 2017-10-06T17:41:30.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator

-- Update elementvalue
update c_elementvalue
set value = '9' || substring(value from '....$')
where c_elementvalue_id in 
(
select c_elementvalue_id from c_elementvalue
where true
and lower(name) like '%acct'
and 'metasfresh' = (select name from ad_client where ad_client_id = c_elementvalue.ad_client_id)
);

-- Update Validcombination
update c_validcombination
set combination = '9' || substring(combination from '....$')
where c_validcombination_id in 
(
select c_validcombination_id from c_validcombination
where true
and lower(description) like '%acct'
and 'metasfresh' = (select name from ad_client where ad_client_id = c_validcombination.ad_client_id)
);

