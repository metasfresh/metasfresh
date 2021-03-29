
-- - currently noone we know about uses the feature
-- - there is proably a simple fix, but we get this in the log, i.e. something is wrong with what we have in the elastic server vs what we send to the elastic server
/*
app_1       | 2019-10-10T13:31:47.393300881Z de.metas.async.exceptions.WorkpackageSkipRequestException: failure in bulk execution:
app_1       | 2019-10-10T13:31:47.393350436Z [0]: index [orders], type [C_OrderLine], id [1000083], message [MapperParsingException[failed to parse]; nested: NullPointerException;]
app_1       | 2019-10-10T13:31:47.393370822Z [1]: index [orders], type [C_OrderLine], id [1000084], message [MapperParsingException[failed to parse]; nested: NullPointerException;]
app_1       | 2019-10-10T13:31:47.393384840Z Additional parameters:
app_1       | 2019-10-10T13:31:47.393398267Z  I_C_Queue_WorkPackage: X_C_Queue_WorkPackage[C_Queue_WorkPackage_ID=1000583, trxName=null]
app_1       | 2019-10-10T13:31:47.393411396Z  IQueueProcessor: ThreadPoolQueueProcessor{name=Elasticsearch model indexer, executor=BlockingThreadPoolExecutor [getPoolSize()=1]}
app_1       | 2019-10-10T13:31:47.393423989Z  trxName: <<ThreadInherited>>
app_1       | 2019-10-10T13:31:47.393432429Z    at de.metas.async.exceptions.WorkpackageSkipRequestException.createWithTimeoutAndThrowable(WorkpackageSkipRequestException.java:70)
app_1       | 2019-10-10T13:31:47.393440064Z    at de.metas.elasticsearch.scheduler.async.AsyncAddToIndexProcessor.processWorkPackage(AsyncAddToIndexProcessor.java:106)
app_1       | 2019-10-10T13:31:47.393447632Z    at de.metas.async.processor.impl.WorkpackageProcessorTask.invokeProcessorAndHandleException(WorkpackageProcessorTask.java:291)
app_1       | 2019-10-10T13:31:47.393455062Z    at de.metas.async.processor.impl.WorkpackageProcessorTask.processWorkpackage(WorkpackageProcessorTask.java:283)
app_1       | 2019-10-10T13:31:47.393462418Z    at de.metas.async.processor.impl.WorkpackageProcessorTask.lambda$run$0(WorkpackageProcessorTask.java:167)
app_1       | 2019-10-10T13:31:47.393469720Z    at org.adempiere.ad.trx.api.impl.TrxCallableWrappers$2.call(TrxCallableWrappers.java:103)
app_1       | 2019-10-10T13:31:47.393477105Z    at org.adempiere.ad.trx.api.impl.TrxCallableWrappers$2.call(TrxCallableWrappers.java:93)
app_1       | 2019-10-10T13:31:47.393484337Z    at org.adempiere.ad.trx.api.impl.AbstractTrxManager.call0(AbstractTrxManager.java:757)
app_1       | 2019-10-10T13:31:47.393492206Z    at org.adempiere.ad.trx.api.impl.AbstractTrxManager.call(AbstractTrxManager.java:670)
app_1       | 2019-10-10T13:31:47.393499729Z    at org.adempiere.ad.trx.api.impl.AbstractTrxManager.run(AbstractTrxManager.java:578)
app_1       | 2019-10-10T13:31:47.393531484Z    at de.metas.async.processor.impl.WorkpackageProcessorTask.run(WorkpackageProcessorTask.java:161)
app_1       | 2019-10-10T13:31:47.393546281Z    at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
app_1       | 2019-10-10T13:31:47.393558371Z    at java.util.concurrent.FutureTask.run(FutureTask.java:266)
app_1       | 2019-10-10T13:31:47.393569459Z    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
app_1       | 2019-10-10T13:31:47.393581177Z    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
app_1       | 2019-10-10T13:31:47.393592807Z    at java.lang.Thread.run(Thread.java:748)
app_1       | 2019-10-10T13:31:47.393604137Z Caused by: org.elasticsearch.ElasticsearchException: failure in bulk execution:
app_1       | 2019-10-10T13:31:47.393616832Z [0]: index [orders], type [C_OrderLine], id [1000083], message [MapperParsingException[failed to parse]; nested: NullPointerException;]
app_1       | 2019-10-10T13:31:47.393628879Z [1]: index [orders], type [C_OrderLine], id [1000084], message [MapperParsingException[failed to parse]; nested: NullPointerException;]
app_1       | 2019-10-10T13:31:47.393641374Z    at de.metas.elasticsearch.indexer.engine.ESIndexerResult.throwExceptionIfAnyFailure(ESIndexerResult.java:134)
app_1       | 2019-10-10T13:31:47.393649228Z    at de.metas.elasticsearch.scheduler.async.AsyncAddToIndexProcessor.processWorkPackage(AsyncAddToIndexProcessor.java:98)
app_1       | 2019-10-10T13:31:47.393656834Z    ... 14 common frames omitted
*/

update AD_SysConfig set Value='N', Updated='2019-10-10 13:30:42.15236+00', UpdatedBy=0 where Name='de.metas.elasticsearch.PostKpiEvents';
