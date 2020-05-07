/* script used to identify

select t.TableName, c.ColumnName, c.MandatoryLogic, c.ReadonlyLogic
from AD_Column c 
inner join AD_Table t on (t.AD_Table_ID=c.AD_Table_ID)
where c.MandatoryLogic='N' or c.ReadonlyLogic='N'

result:
"C_DirectDebit";"IsRemittance";"N";""
"C_AdvComCorrection";"Processed";"";"N"
"AD_EventLog_Entry";"IsError";"";"N"
"M_ShipmentSchedule_AttributeConfig";"OnlyIfInReferencedASI";"";"N"


*/


-- 2018-10-23T16:27:20.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', MandatoryLogic='',Updated=TO_TIMESTAMP('2018-10-23 16:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=541903
;

-- 2018-10-23T16:28:05.424
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2018-10-23 16:28:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542977
;

-- 2018-10-23T16:28:41.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2018-10-23 16:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558428
;

-- 2018-10-23T16:29:13.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2018-10-23 16:29:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559505
;

