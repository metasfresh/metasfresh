-- 14.06.2016 23:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540202,0,TO_TIMESTAMP('2016-06-14 23:45:43','YYYY-MM-DD HH24:MI:SS'),100,'Request for Quotation (RfQ) module','de.metas.rfq','Y','Y','de.metas.rfq.model','de.metas.rfq','N',TO_TIMESTAMP('2016-06-14 23:45:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.06.2016 23:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_ModelValidator (AD_Client_ID,AD_ModelValidator_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,ModelValidationClass,Name,SeqNo,Updated,UpdatedBy) VALUES (0,540110,0,TO_TIMESTAMP('2016-06-14 23:47:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.rfq','Y','de.metas.rfq.model.interceptor.RfQModuleActivator','de.metas.rfq',9999,TO_TIMESTAMP('2016-06-14 23:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;




















-- 14.06.2016 23:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQ_Close', EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=269
;

-- 14.06.2016 23:49
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQ_CopyLines', EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:49:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=268
;

-- 14.06.2016 23:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=478
;

-- 14.06.2016 23:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQResponse_CreateFromTopic', EntityType='de.metas.rfq', Value='C_RfQResponse_CreateFromTopic',Updated=TO_TIMESTAMP('2016-06-14 23:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=261
;

-- 14.06.2016 23:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:51:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=472
;

-- 14.06.2016 23:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQ_CreatePO', EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=266
;

-- 14.06.2016 23:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=527
;

-- 14.06.2016 23:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQ_CreateSO', EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:52:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=267
;

-- 14.06.2016 23:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:52:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=528
;

-- 14.06.2016 23:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQ_RankResponses', EntityType='de.metas.rfq', Value='C_RfQ_RankResponses',Updated=TO_TIMESTAMP('2016-06-14 23:52:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=265
;

-- 14.06.2016 23:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQResponse_Complete', EntityType='de.metas.rfq', Value='C_RfQResponse_Complete',Updated=TO_TIMESTAMP('2016-06-14 23:53:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=277
;

-- 14.06.2016 23:54
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.rfq.process.C_RfQResponse_Invite', EntityType='de.metas.rfq',Updated=TO_TIMESTAMP('2016-06-14 23:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=262
;































drop table if exists TMP_RFQ_Tables;
create temporary table TMP_RFQ_Tables as
select
	t.TableName, t.AD_Table_ID
	, tt.AD_Tab_ID
	, tt.AD_Window_ID
	, tt.SeqNo
from AD_Table t
left outer join AD_Tab tt on (tt.AD_Table_ID=t.AD_Table_ID)
where TableName in (
	'C_RfQ'
	, 'C_RfQ_Topic'
	, 'C_RfQ_TopicSubscriber'
	, 'C_RfQ_TopicSubscriberOnly'
	, 'C_RfQLine'
	, 'C_RfQLineQty'
	, 'C_RfQResponse'
	, 'C_RfQResponse_v'
	, 'C_RfQResponseLine'
	, 'C_RfQResponseLine_v'
	, 'C_RfQResponseLineQty'
	, 'C_RfQResponseLineQty_v'
	, 'RV_C_RfQ_UnAnswered'
	, 'RV_C_RfQResponse'
)
order by t.TableName
;
-- select * from TMP_RFQ_Tables;

update AD_Table t set EntityType='de.metas.rfq'
from TMP_RFQ_Tables z
where t.AD_Table_ID=z.AD_Table_ID;

update AD_Column c set EntityType='de.metas.rfq'
from TMP_RFQ_Tables z
where c.AD_Table_ID=z.AD_Table_ID
and c.EntityType='D'
;

update AD_Tab tt set EntityType='de.metas.rfq'
from TMP_RFQ_Tables z
where tt.AD_Tab_ID=z.AD_Tab_ID
and tt.EntityType='D'
;

update AD_Field f set EntityType='de.metas.rfq'
from TMP_RFQ_Tables z
where f.AD_Tab_ID=z.AD_Tab_ID
and f.EntityType='D'
;

update AD_Window w set EntityType='de.metas.rfq'
from TMP_RFQ_Tables z
where w.AD_Window_ID=z.AD_Window_ID
and w.EntityType='D'
and z.SeqNo=10
;

-- C_RfQ QuoteType
update AD_Reference set EntityType='de.metas.rfq' where AD_Reference_ID=314;
update AD_Ref_List set EntityType='de.metas.rfq' where AD_Reference_ID=314 and EntityType='D';

update AD_Element set EntityType='de.metas.rfq'
where ColumnName in (
'C_RfQ_ID'
,'C_RfQ_Topic_ID'
,'C_RfQ_TopicSubscriber_ID'
,'C_RfQ_TopicSubscriberOnly_ID'
,'C_RfQLine_ID'
,'C_RfQLineQty_ID'
,'C_RfQResponse_ID'
,'C_RfQResponseLine_ID'
,'C_RfQResponseLineQty_ID'
);




































