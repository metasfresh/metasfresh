-- 2021-01-18T08:37:00.484Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO S_Resource (AD_Client_ID,AD_Org_ID,ChargeableQty,Created,CreatedBy,DailyCapacity,IsActive,IsAvailable,IsManufacturingResource,ManufacturingResourceType,Name,PercentUtilization,PlanningHorizon,QueuingTime,S_Resource_ID,S_ResourceType_ID,Updated,UpdatedBy,Value,WaitingTime) VALUES (1000000,1000000,0,TO_TIMESTAMP('2021-01-18 10:37:00','YYYY-MM-DD HH24:MI:SS'),100,0,'Y','Y','Y','WC','No resource',100,0,0,540011,1000000,TO_TIMESTAMP('2021-01-18 10:37:00','YYYY-MM-DD HH24:MI:SS'),100,'No resource',0)
;

update s_resource set ad_client_id=0, ad_org_id=0 where s_resource_id=540011;

update ad_wf_node set s_resource_id=540011 where AD_WF_Node_ID=540240;



