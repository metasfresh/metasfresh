-- 03.03.2017 13:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2017-03-03 13:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552285
;

-- all other found cases where flags have "wrong" default logic for webUI
update ad_column set defaultvalue = 'N'
where true
and ad_column_id in
(
56070,
56072,
56073,
56071,
52053,
546473,
546472,
548287
)
and defaultvalue = '''N'''
;

update ad_column set defaultvalue = 'Y'
where true
and ad_column_id in
(
54545,
54558,
54572,
54595,
54624,
54609,
54477,
54490,
54506,
54528,
54617,
55616
)
and defaultvalue = '''Y'''
;