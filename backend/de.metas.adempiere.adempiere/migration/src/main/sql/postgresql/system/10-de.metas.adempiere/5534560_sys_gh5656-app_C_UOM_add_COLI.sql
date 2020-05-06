
-- 2019-10-17T18:14:47.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM (AD_Client_ID,AD_Org_ID,CostingPrecision,Created,CreatedBy,C_UOM_ID,IsActive,IsDefault,Name,StdPrecision,UOMSymbol,Updated,UpdatedBy,X12DE355) VALUES (0,0,0,TO_TIMESTAMP('2019-10-17 20:14:47','YYYY-MM-DD HH24:MI:SS'),100,540048,'Y','N','Kollo',2,'Kollo',TO_TIMESTAMP('2019-10-17 20:14:47','YYYY-MM-DD HH24:MI:SS'),100,'COLI')
;

-- 2019-10-17T18:14:47.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Trl (AD_Language,C_UOM_ID, Description,Name,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_UOM_ID, t.Description,t.Name,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_UOM t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.C_UOM_ID=540048 AND NOT EXISTS (SELECT 1 FROM C_UOM_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_UOM_ID=t.C_UOM_ID)
;

-- 2019-10-17T18:15:03.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Conversion (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_Conversion_ID,C_UOM_ID,C_UOM_To_ID,DivideRate,IsActive,IsCatchUOMForProduct,MultiplyRate,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2019-10-17 20:15:03','YYYY-MM-DD HH24:MI:SS'),100,540013,540048,100,1.000000000000,'Y','N',1.000000000000,TO_TIMESTAMP('2019-10-17 20:15:03','YYYY-MM-DD HH24:MI:SS'),100)
;

