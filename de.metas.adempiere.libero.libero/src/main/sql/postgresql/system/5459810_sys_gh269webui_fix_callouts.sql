-- 2017-04-08T15:12:45.240
-- Get rid of UOMConversion context variable
UPDATE AD_Field SET DisplayLogic='@Processed@=''Y''',Updated=TO_TIMESTAMP('2017-04-08 15:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=54153
;
/* query used to identify the fields which use UOMConversion in one of their expressions:

select ex.*, t.TableName, c.ColumnName
from public.get_db_columns_logicexpression() ex
left outer join AD_Field f on (ex.TableName='AD_Field' and f.AD_Field_ID=ex.Record_ID)
left outer join AD_Column c on (c.AD_Column_ID=f.AD_Column_ID)
left outer join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
where logicExpression ilike '%UOMConver%'
order by t.TableName
*/



--
-- Remove all AD_ColumnCalouts related to manufacturing tables. We will register those callouts programatically.
-- 


-- 2017-04-08T15:14:29.423
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50168
;

-- 2017-04-08T15:14:29.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50169
;

-- 2017-04-08T15:15:06.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50167
;

-- 2017-04-08T15:25:18.652
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50172
;

-- 2017-04-08T15:26:28.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50173
;

-- 2017-04-08T15:36:03.390
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50170
;

-- 2017-04-08T15:36:15.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50171
;

-- 2017-04-08T15:50:22.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50164
;

-- 2017-04-08T15:50:30.132
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50165
;

-- 2017-04-08T15:50:35.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_ColumnCallout WHERE AD_ColumnCallout_ID=50166
;









-- 2017-04-08T16:06:56.052
-- Fix PP_Order.C_UOM_ID's default value
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2017-04-08 16:06:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53632
;

