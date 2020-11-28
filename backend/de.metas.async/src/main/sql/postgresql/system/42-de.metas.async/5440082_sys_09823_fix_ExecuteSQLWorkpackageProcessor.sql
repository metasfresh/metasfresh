
--
-- setting the ad_client-id to 0
--
UPDATE C_Queue_Processor SET AD_Client_ID=0, Updated=now(), UpdatedBy=99 WHERE C_Queue_Processor_ID=540038;
UPDATE C_Queue_PackageProcessor SET AD_Client_ID=0, Updated=now(), UpdatedBy=99 WHERE C_Queue_PackageProcessor_ID=540038;
UPDATE C_Queue_Processor_Assign SET AD_Client_ID=0, Updated=now(), UpdatedBy=99 WHERE C_Queue_PackageProcessor_ID=540038;
