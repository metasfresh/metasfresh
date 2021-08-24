-- 2021-04-26T12:30:30.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,571153,0,TO_TIMESTAMP('2021-04-26 14:30:29','YYYY-MM-DD HH24:MI:SS'),100,'Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes','de.metas.esb.edi',540406,550323,'N','N','Verpackungskapazität',440,'E',TO_TIMESTAMP('2021-04-26 14:30:29','YYYY-MM-DD HH24:MI:SS'),100,'QtyItemCapacity')
;

-- 2021-04-26T12:30:48.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2021-04-26 14:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550323
;

-- 2021-04-26T12:31:04.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Position=405,Updated=TO_TIMESTAMP('2021-04-26 14:31:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550323
;

