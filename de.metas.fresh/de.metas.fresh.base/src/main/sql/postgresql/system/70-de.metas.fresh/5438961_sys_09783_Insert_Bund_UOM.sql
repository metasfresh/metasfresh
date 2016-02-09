
-- 04.02.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM (AD_Client_ID,AD_Org_ID,CostingPrecision,Created,CreatedBy,C_UOM_ID,IsActive,IsDefault,Name,StdPrecision,UOMSymbol,Updated,UpdatedBy,X12DE355) VALUES (1000000,0,4,TO_TIMESTAMP('2016-02-04 18:30:18','YYYY-MM-DD HH24:MI:SS'),100,540031,'Y','N','Bund',0,'Bund',TO_TIMESTAMP('2016-02-04 18:30:18','YYYY-MM-DD HH24:MI:SS'),100,'Bund')
;

-- 04.02.2016 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_UOM_Trl (AD_Language,C_UOM_ID, Description,Name,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_UOM_ID, t.Description,t.Name,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_UOM t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_UOM_ID=540031 AND NOT EXISTS (SELECT * FROM C_UOM_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_UOM_ID=t.C_UOM_ID)
;

