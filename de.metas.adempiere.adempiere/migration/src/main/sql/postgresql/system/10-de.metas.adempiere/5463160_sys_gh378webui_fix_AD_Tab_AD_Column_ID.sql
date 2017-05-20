/* Fix AD_Tab.AD_Column_ID not matching AD_Tab.AD_Table_ID

select 
w.Name as WindowName
, tt.Name as TabName
, (select z.TableName from AD_Table z where z.AD_Table_ID=tt.AD_Table_ID) as Tab_TableName
, t.TableName, c.ColumnName
-- , tt.*
from AD_Tab tt
inner join AD_Window w on (w.AD_Window_ID=tt.AD_Window_ID)
inner join AD_Column c on (c.AD_Column_ID=tt.AD_Column_ID)
inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
where tt.AD_Table_ID<>c.AD_Table_ID
;

*/

-- 2017-05-20T11:47:05.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=4636,Updated=TO_TIMESTAMP('2017-05-20 11:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=313
;

-- 2017-05-20T11:47:52.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=265,Updated=TO_TIMESTAMP('2017-05-20 11:47:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=527
;

-- 2017-05-20T11:49:08.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=14373,Updated=TO_TIMESTAMP('2017-05-20 11:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=759
;

-- 2017-05-20T11:49:44.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=15671,Updated=TO_TIMESTAMP('2017-05-20 11:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=839
;

-- 2017-05-20T11:50:25.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=554949,Updated=TO_TIMESTAMP('2017-05-20 11:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540751
;


















