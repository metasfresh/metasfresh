-- 2023-12-21T08:54:08.212Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540667,'PP_Product_BOM.ValidTo IS NULL OR PP_Product_BOM.ValidTo >= (SELECT DateOrdered FROM PP_Order WHERE PP_Order_ID = @PP_Order_ID@)',TO_TIMESTAMP('2023-12-21 09:54:07.742','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','PP_Product_BOM_ValidTo','S',TO_TIMESTAMP('2023-12-21 09:54:07.742','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-21T16:45:50.507Z
UPDATE AD_Val_Rule SET Code='(PP_Product_BOM.ValidTo IS NULL OR PP_Product_BOM.ValidTo >= ''@DateOrdered@'') AND EXISTS (SELECT 1 FROM C_DocType dt WHERE dt.C_DocType_ID=@C_DocTypeTarget_ID/0@ AND ((dt.DocBaseType=''MOP'' AND PP_Product_BOM.BomType = ''A'') OR (dt.DocBaseType=''MRO'' AND PP_Product_BOM.BomType = ''S'') OR dt.DocBaseType NOT IN (''MOP'', ''MRO'')))',Updated=TO_TIMESTAMP('2023-12-21 17:45:50.317','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540667
;

-- 2023-12-21T12:46:12.945Z
UPDATE AD_Val_Rule SET Description='BOMs with Valid To < Production Date should no be displayed, Also If the PP_Order is (Manufacturing) only Manufacturing BOMs with (BOM Type=Current Active) are displayed, if the PP_Order is (Service/Repair Order) only Repair BOMs are displayed',Updated=TO_TIMESTAMP('2023-12-21 13:46:12.745','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540667
;

-- Column: PP_Order.PP_Product_BOM_ID
-- 2023-12-21T16:12:10.258Z
UPDATE AD_Column SET AD_Val_Rule_ID=540667,Updated=TO_TIMESTAMP('2023-12-21 17:12:10.079','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=53660
;