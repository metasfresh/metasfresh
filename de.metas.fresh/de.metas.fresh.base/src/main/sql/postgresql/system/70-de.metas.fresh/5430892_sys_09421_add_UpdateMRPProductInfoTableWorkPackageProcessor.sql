
-- 20.10.2015 12:12
-- URL zum Konzept
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540030,'de.metas.fresh.mrp_productinfo.async.spi.impl.UpdateMRPProductInfoTableWorkPackageProcessor',TO_TIMESTAMP('2015-10-20 12:12:04','YYYY-MM-DD HH24:MI:SS'),100,'See task http://dewiki908/mediawiki/index.php/09421_Performance_Update_MRP_Product_Info_%2830_Minuten%29._Incrementelles_Update_n%C3%B6tig/_Asynch._%28105656598895%29','de.metas.fresh','Y',TO_TIMESTAMP('2015-10-20 12:12:04','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE C_Queue_PackageProcessor SET InternalName='UpdateMRPProductInfoTableWorkPackageProcessor' WHERE C_Queue_PackageProcessor_ID=540030;

-- 20.10.2015 12:12
-- URL zum Konzept
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540030,TO_TIMESTAMP('2015-10-20 12:12:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,'UpdateMRPProductInfoTableWorkPackageProcessor',1,TO_TIMESTAMP('2015-10-20 12:12:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 20.10.2015 12:13
-- URL zum Konzept
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540030,540054,540030,TO_TIMESTAMP('2015-10-20 12:13:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2015-10-20 12:13:04','YYYY-MM-DD HH24:MI:SS'),100)
;

