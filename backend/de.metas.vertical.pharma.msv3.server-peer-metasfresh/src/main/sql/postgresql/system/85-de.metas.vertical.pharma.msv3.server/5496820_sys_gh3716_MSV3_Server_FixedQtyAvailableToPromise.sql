-- 2018-06-28T17:10:56.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Konst. Zusagbar (ATP) Wert','de.metas.vertical.pharma.msv3.server','FixedQtyAvailableToPromise',544153,0,'Konst. Zusagbar (ATP) Wert',100,TO_TIMESTAMP('2018-06-28 17:10:55','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-06-28 17:10:55','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-06-28T17:10:56.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544153 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-06-28T17:11:36.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-28 17:11:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Fixed Qty Available to Promise',PrintName='Fixed Qty Available to Promise' WHERE AD_Element_ID=544153 AND AD_Language='en_US'
;

-- 2018-06-28T17:11:36.616
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544153,'en_US') 
;

-- 2018-06-28T17:11:55.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544153, Help=NULL, ColumnName='FixedQtyAvailableToPromise', Description=NULL, Name='Konst. Zusagbar (ATP) Wert',Updated=TO_TIMESTAMP('2018-06-28 17:11:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559608
;

-- 2018-06-28T17:11:55.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Konst. Zusagbar (ATP) Wert', Description=NULL, Help=NULL WHERE AD_Column_ID=559608
;

alter table msv3_server rename column qty_AvailableToPromise_Min to FixedQtyAvailableToPromise;
