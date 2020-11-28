delete from AD_SysConfig where Name='de.metas.CheckForDoubleOrderLine';

delete from AD_Message where value='OrderLineDouble.DoubleExists';
delete from AD_Message where value='OrderLineDouble.DoubleLine';

delete from AD_ColumnCallout where AD_ColumnCallout_ID in (540794, 540797, 540798, 540805);
-- select * from AD_ColumnCallout where classname like 'de.metas.order.callout.CheckDouble%'



-- DateOrdered_Double
delete from EXP_FormatLine where AD_Column_ID=505131;
delete from AD_Field where AD_Column_ID=505131;
-- 2017-04-23T14:28:52.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=505131
;
-- 2017-04-23T14:28:52.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=505131
;
-- 2017-04-23T14:30:23.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=505131
;
-- 2017-04-23T14:30:23.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=505131
;

