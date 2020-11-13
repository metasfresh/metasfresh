-- 2019-12-12T10:27:43.663Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,569747,0,TO_TIMESTAMP('2019-12-12 11:27:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540417,550290,'N','N','Chargennummer',270,'E',TO_TIMESTAMP('2019-12-12 11:27:43','YYYY-MM-DD HH24:MI:SS'),100,'LotNumber')
;

-- 2019-12-12T10:31:40.478Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y', Position=75,Updated=TO_TIMESTAMP('2019-12-12 11:31:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550290
;

-- 2019-12-12T10:31:52.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-12-12 11:31:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550264
;

-- 2019-12-12T11:04:22.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET Name='EanCom_UOM', Value='EanCom_UOM',Updated=TO_TIMESTAMP('2019-12-12 12:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545117
;

-- 2019-12-12T11:06:35.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2019-12-12 12:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569746
;

-- 2019-12-12T11:06:47.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2019-12-12 12:06:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548355
;

-- 2019-12-12T11:06:55.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=10,Updated=TO_TIMESTAMP('2019-12-12 12:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551317
;

