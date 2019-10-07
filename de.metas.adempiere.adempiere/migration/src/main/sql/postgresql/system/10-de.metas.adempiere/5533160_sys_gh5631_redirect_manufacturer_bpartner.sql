-- 2019-10-07T09:11:17.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543662, AD_Reference_ID=19, AD_Val_Rule_ID=NULL, ColumnName='I_Pharma_Product_ID', Description=NULL, EntityType='de.metas.vertical.pharma', Help=NULL, Name='Pharma Product',Updated=TO_TIMESTAMP('2019-10-07 12:11:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558031
;

-- 2019-10-07T09:11:17.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pharma Product', Description=NULL, Help=NULL WHERE AD_Column_ID=558031
;

-- 2019-10-07T09:11:17.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(543662) 
;

-- 2019-10-07T09:19:10.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=544128, AD_Reference_ID=18, AD_Val_Rule_ID=540401, ColumnName='Manufacturer_ID', Description='Hersteller des Produktes', EntityType='D', Help='', Name='Hersteller',Updated=TO_TIMESTAMP('2019-10-07 12:19:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558031
;

-- 2019-10-07T09:19:10.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Hersteller', Description='Hersteller des Produktes', Help='' WHERE AD_Column_ID=558031
;

-- 2019-10-07T09:19:10.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(544128) 
;

-- 2019-10-07T09:42:31.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=123,Updated=TO_TIMESTAMP('2019-10-07 12:42:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=138
;

-- 2019-10-07T09:43:30.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138,Updated=TO_TIMESTAMP('2019-10-07 12:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558031
;

-- 2019-10-07T09:48:00.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541058,TO_TIMESTAMP('2019-10-07 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner_IsManufacturer',TO_TIMESTAMP('2019-10-07 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2019-10-07T09:48:00.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541058 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-10-07T09:49:50.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy,WhereClause) VALUES (0,2902,2893,0,541058,291,123,TO_TIMESTAMP('2019-10-07 12:49:50','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',TO_TIMESTAMP('2019-10-07 12:49:50','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y'' and isManufacturer=''Y''')
;

-- 2019-10-07T09:50:00.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='D',Updated=TO_TIMESTAMP('2019-10-07 12:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541058
;

-- 2019-10-07T09:50:45.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET IsValueDisplayed='Y', WhereClause='C_BPartner.isActive=''Y'' and C_BPartner.isManufacturer=''Y''',Updated=TO_TIMESTAMP('2019-10-07 12:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541058
;

-- 2019-10-07T10:03:22.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541058,Updated=TO_TIMESTAMP('2019-10-07 13:03:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558031
;

