-- 2018-10-30T15:56:18.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_workflow','AD_User_InCharge_ID','NUMERIC(10)',null,null)
;

-- 2018-10-30T15:57:01.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2018-10-30 15:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563391
;

-- 2018-10-30T15:58:42.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575834,0,TO_TIMESTAMP('2018-10-30 15:58:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Produktionsverantwortlicher','Produktionsverantwortlicher',TO_TIMESTAMP('2018-10-30 15:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T15:58:42.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=575834 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-30T15:59:00.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 15:59:00','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575834 AND AD_Language='de_CH'
;

-- 2018-10-30T15:59:00.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575834,'de_CH') 
;

-- 2018-10-30T15:59:18.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-30 15:59:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Responsible for production',PrintName='Responsible for production' WHERE AD_Element_ID=575834 AND AD_Language='en_US'
;

-- 2018-10-30T15:59:18.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575834,'en_US') 
;

-- 2018-10-30T15:59:35.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563391,569780,575834,0,148,0,TO_TIMESTAMP('2018-10-30 15:59:34','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Produktionsverantwortlicher',320,320,0,1,1,TO_TIMESTAMP('2018-10-30 15:59:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-30T15:59:35.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-30T16:00:31.265
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y', SeqNo=45,Updated=TO_TIMESTAMP('2018-10-30 16:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569780
;

