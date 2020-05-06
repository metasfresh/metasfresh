-- 2018-02-02T17:49:27.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=561982
;

-- 2018-02-02T17:49:27.203
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=561982
;

-- 2018-02-02T17:50:27.425
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558983,561984,0,53032,0,TO_TIMESTAMP('2018-02-02 17:50:27','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.product',0,'Y','Y','Y','N','N','N','N','N','M_ProductPlanningSchema_Selector',230,230,0,1,1,TO_TIMESTAMP('2018-02-02 17:50:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-02T17:50:27.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=561984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-02T17:50:27.455
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543841,NULL) 
;

-- 2018-02-02T17:51:40.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNo=215,Updated=TO_TIMESTAMP('2018-02-02 17:51:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561984
;

