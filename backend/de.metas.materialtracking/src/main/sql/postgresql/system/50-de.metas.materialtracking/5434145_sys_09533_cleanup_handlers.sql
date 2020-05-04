
-- 25.11.2015 16:55
-- URL zum Konzept
UPDATE C_ILCandHandler SET IsActive='N',Updated=TO_TIMESTAMP('2015-11-25 16:55:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ILCandHandler_ID=540015
;

-- 25.11.2015 16:55
-- URL zum Konzept
UPDATE C_ILCandHandler SET Classname='de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.PP_Order_MaterialTracking_Handler', Is_AD_User_InCharge_UI_Setting='Y', IsActive='Y', Name='Qualitätsinspektion/Probewaschung PP_Order', TableName='PP_Order',Updated=TO_TIMESTAMP('2015-11-25 16:55:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ILCandHandler_ID=540012
;


UPDATE C_Invoice_Candidate SET c_ilcandhandler_id=540012 WHERE c_ilcandhandler_id=540015;

DELETE FROM c_ilcandhandler WHERE c_ilcandhandler_id=540015;
