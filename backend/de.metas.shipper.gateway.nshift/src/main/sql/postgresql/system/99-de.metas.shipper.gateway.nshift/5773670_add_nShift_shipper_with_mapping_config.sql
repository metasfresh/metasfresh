-- Run mode: WEBUI

-- 2025-10-19T13:10:34.121Z
INSERT INTO M_Shipper (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,M_Shipper_ID,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:10:33.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','N',540019,'nShift',TO_TIMESTAMP('2025-10-19 13:10:33.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'nShift')
;

-- 2025-10-19T13:10:37.392Z
UPDATE M_Shipper SET Value='nShift',Updated=TO_TIMESTAMP('2025-10-19 13:10:37.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_ID=540019
;

-- 2025-10-19T13:10:48.027Z
UPDATE M_Shipper SET C_BPartner_ID=2155894,Updated=TO_TIMESTAMP('2025-10-19 13:10:48.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_ID=540019
;

-- 2025-10-19T13:10:58.301Z
UPDATE M_Shipper SET InternalName='nShift',Updated=TO_TIMESTAMP('2025-10-19 13:10:58.301000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_ID=540019
;

-- 2025-10-19T13:11:00.567Z
UPDATE M_Shipper SET ShipperGateway='nshift',Updated=TO_TIMESTAMP('2025-10-19 13:11:00.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_ID=540019
;

-- 2025-10-19T13:12:26.797Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:12:26.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','108','Reference','PickupDateAndTimeStart',540019,540000,10,TO_TIMESTAMP('2025-10-19 13:12:26.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:15:41.378Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:15:41.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','109','Reference','PickupDateAndTimeEnd',540019,540001,20,TO_TIMESTAMP('2025-10-19 13:15:41.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:16:25.351Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:16:25.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','9','Reference','DeliveryDate',540019,540002,30,TO_TIMESTAMP('2025-10-19 13:16:25.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:17:28.284Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:17:28.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','7','Reference','CustomerReference',540019,540003,40,TO_TIMESTAMP('2025-10-19 13:17:28.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:18:00.224Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:18:00.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','SenderAttention','SenderCompanyName',540019,540004,50,TO_TIMESTAMP('2025-10-19 13:18:00.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:18:31.443Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:18:31.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','SenderAttention','SenderDepartment',540019,540005,60,TO_TIMESTAMP('2025-10-19 13:18:31.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:19:19.224Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:19:19.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','ReceiverAttention','ReceiverCompanyName',540019,540006,70,TO_TIMESTAMP('2025-10-19 13:19:19.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:19:36.272Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:19:36.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','ReceiverAttention','ReceiverDepartment',540019,540007,80,TO_TIMESTAMP('2025-10-19 13:19:36.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:20:33.251Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:20:33.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','ReceiverAttention','ReceiverCompanyName',540019,540008,90,TO_TIMESTAMP('2025-10-19 13:20:33.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:24:36.409Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,MappingGroupKey,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:24:36.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','182','DetailGroup','ShipperEORI','2',540019,540009,100,TO_TIMESTAMP('2025-10-19 13:24:36.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:26:13.685Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,MappingGroupKey,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:26:13.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','5','LineDetailGroup','ShippedQuantity','1',540019,540010,110,TO_TIMESTAMP('2025-10-19 13:26:13.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:27:51.553Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,MappingGroupKey,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:27:51.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','8','LineDetailGroup','UomCode','1',540019,540011,120,TO_TIMESTAMP('2025-10-19 13:27:51.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:29:51.755Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,MappingGroupKey,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:29:51.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','7','LineDetailGroup','ProductName','1',540019,540012,130,TO_TIMESTAMP('2025-10-19 13:29:51.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:30:41.783Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,MappingGroupKey,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:30:41.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','2','LineDetailGroup','UnitPrice','1',540019,540013,140,TO_TIMESTAMP('2025-10-19 13:30:41.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:31:32.879Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,MappingGroupKey,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:31:32.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','10','LineDetailGroup','TotalValue','1',540019,540014,150,TO_TIMESTAMP('2025-10-19 13:31:32.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:32:49.475Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,MappingGroupKey,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:32:49.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','17','LineDetailGroup','CurrencyCode','1',540019,540015,160,TO_TIMESTAMP('2025-10-19 13:32:49.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:33:32.308Z
INSERT INTO M_Shipper_Mapping_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,MappingAttributeKey,MappingAttributeType,MappingAttributeValue,M_Shipper_ID,M_Shipper_Mapping_Config_ID,SeqNo,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2025-10-19 13:33:32.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317,'Y','4','LineDetailGroup','SenderCountryCode',540019,540016,170,TO_TIMESTAMP('2025-10-19 13:33:32.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',2191317)
;

-- 2025-10-19T13:33:35.307Z
UPDATE M_Shipper_Mapping_Config SET MappingGroupKey='1',Updated=TO_TIMESTAMP('2025-10-19 13:33:35.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540016
;

-- 2025-10-19T13:34:06.908Z
UPDATE M_Shipper_Mapping_Config SET Description='Ursprungsland',Updated=TO_TIMESTAMP('2025-10-19 13:34:06.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540016
;

-- 2025-10-19T13:40:06.746Z
UPDATE M_Shipper SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:06.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_ID=540019
;

-- 2025-10-19T13:40:19.045Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:19.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540000
;

-- 2025-10-19T13:40:24.327Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:24.327000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540001
;

-- 2025-10-19T13:40:29.439Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:29.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540002
;

-- 2025-10-19T13:40:33.405Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:33.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540003
;

-- 2025-10-19T13:40:37.318Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:37.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540004
;

-- 2025-10-19T13:40:41.577Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:41.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540005
;

-- 2025-10-19T13:40:45.235Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:45.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540006
;

-- 2025-10-19T13:40:50.193Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:50.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540007
;

-- 2025-10-19T13:40:55.048Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:55.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540008
;

-- 2025-10-19T13:40:58.848Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:40:58.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540009
;

-- 2025-10-19T13:41:02.564Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:41:02.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540010
;

-- 2025-10-19T13:41:06.109Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:41:06.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540011
;

-- 2025-10-19T13:41:10.176Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:41:10.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540012
;

-- 2025-10-19T13:41:13.739Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:41:13.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540013
;

-- 2025-10-19T13:41:17.050Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:41:17.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540014
;

-- 2025-10-19T13:41:21.990Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:41:21.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540015
;

-- 2025-10-19T13:41:25.656Z
UPDATE M_Shipper_Mapping_Config SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2025-10-19 13:41:25.656000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=2191317 WHERE M_Shipper_Mapping_Config_ID=540016
;

