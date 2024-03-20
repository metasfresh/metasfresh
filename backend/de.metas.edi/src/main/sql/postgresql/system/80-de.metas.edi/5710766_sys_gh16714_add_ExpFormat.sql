

-- 2023-11-16T00:35:08.894Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,587651,0,TO_TIMESTAMP('2023-11-16 01:35:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540418,550594,'N','N','Menge CU/LU (Preiseinh.)',250,'E',TO_TIMESTAMP('2023-11-16 01:35:08','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsPerLU_InInvoiceUOM')
;

-- 2023-11-16T00:35:13.072Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,587650,0,TO_TIMESTAMP('2023-11-16 01:35:12','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU) in der jew. Preiseinheit','de.metas.esb.edi',540418,550595,'N','N','Menge CU/TU (Preiseinh.)',260,'E',TO_TIMESTAMP('2023-11-16 01:35:12','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsPerTU_InInvoiceUOM')
;

-- 2023-11-16T00:36:25.657Z
UPDATE EXP_FormatLine SET Value='QtyCUsPerTU',Updated=TO_TIMESTAMP('2023-11-16 01:36:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550440
;

-- 2023-11-16T00:36:52.060Z
UPDATE EXP_FormatLine SET IsActive='Y', Position=163,Updated=TO_TIMESTAMP('2023-11-16 01:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550594
;

-- 2023-11-16T00:37:30.806Z
UPDATE EXP_FormatLine SET IsActive='Y', Position=165,Updated=TO_TIMESTAMP('2023-11-16 01:37:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550595
;

