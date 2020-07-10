-- 2019-10-29T15:43:57.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM (AD_Client_ID,AD_Org_ID,CostingPrecision,Created,CreatedBy,C_UOM_ID,Description,IsActive,IsDefault,Name,StdPrecision,UOMSymbol,Updated,UpdatedBy,X12DE355) VALUES (1000000,0,0,TO_TIMESTAMP('2019-10-29 16:43:57','YYYY-MM-DD HH24:MI:SS'),100,540049,'Die Standardgenauigkeit bestimmt die Anzahl der Nachkommastellen auf der Provisionsabrechnung.','Y','N','Provisionspunkt',2,'Pkt',TO_TIMESTAMP('2019-10-29 16:43:57','YYYY-MM-DD HH24:MI:SS'),100,'PTS')
;

-- 2019-10-29T15:43:57.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Trl (AD_Language,C_UOM_ID, Description,Name,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_UOM_ID, t.Description,t.Name,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_UOM t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_UOM_ID=540049 AND NOT EXISTS (SELECT 1 FROM C_UOM_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_UOM_ID=t.C_UOM_ID)
;

-- 2019-10-29T15:44:00.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-10-29 16:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_UOM_ID=540049
;

-- 2019-10-29T15:44:08.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_UOM_Trl SET Description='', Name='Commission point',Updated=TO_TIMESTAMP('2019-10-29 16:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_UOM_ID=540049
;

-- 2019-10-29T15:44:23.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product SET C_UOM_ID=540049, IsStocked='N',Updated=TO_TIMESTAMP('2019-10-29 16:44:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Product_ID=540420
;

-- 2019-10-29T15:45:16.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Product SET IsStocked='N', M_Product_Category_ID=540037,Updated=TO_TIMESTAMP('2019-10-29 16:45:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Product_ID=540420
;

-- 2019-10-29T15:48:22.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_ProductPrice SET C_UOM_ID=540049,Updated=TO_TIMESTAMP('2019-10-29 16:48:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_ProductPrice_ID=2767028
;

