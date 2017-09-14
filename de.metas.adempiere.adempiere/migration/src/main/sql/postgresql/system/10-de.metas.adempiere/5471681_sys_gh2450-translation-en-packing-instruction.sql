-- 2017-09-14T16:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
update ad_field_trl
set name = 'Packing Instruction'
where true
and ad_language = 'en_US'
and ad_field_id in
(
553027
,555866
,553942
,553931
,552881
,558493
,553845
,553933
,553021
,552991
,553756
,554550
,554863
,555194
,556180
,556181
,556689
,556762
,556681
,556849
,556848
,557835
,557551
,558759
);