-- 2018-11-10T10:13:23.346
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575867,0,TO_TIMESTAMP('2018-11-10 10:13:23','YYYY-MM-DD HH24:MI:SS'),100,'Javaklasse, die das Interface IAttributeValueHandler implementiert. Falls leer wird eine Defaultimplementierung benutzt, die die dynamische Validierungsregel aufruft.','D','Y','Attribute-Handler Javaklasse','Attribute-Handler Javaklasse',TO_TIMESTAMP('2018-11-10 10:13:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-10T10:13:23.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=575867 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-11-10T10:13:44.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='AttributeValueHandler Javaklasse', PrintName='AttributeValueHandler Javaklasse',Updated=TO_TIMESTAMP('2018-11-10 10:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=575867
;

-- 2018-11-10T10:13:44.291
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AttributeValueHandler Javaklasse', Description='Javaklasse, die das Interface IAttributeValueHandler implementiert. Falls leer wird eine Defaultimplementierung benutzt, die die dynamische Validierungsregel aufruft.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=575867) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 575867)
;

-- 2018-11-10T10:13:44.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='AttributeValueHandler Javaklasse', Name='AttributeValueHandler Javaklasse' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=575867)
;

-- 2018-11-10T10:14:04.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-10 10:14:04','YYYY-MM-DD HH24:MI:SS'),Name='AttributeValueHandler Javaklasse',PrintName='AttributeValueHandler Javaklasse' WHERE AD_Element_ID=575867 AND AD_Language='de_CH'
;

-- 2018-11-10T10:14:04.974
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575867,'de_CH') 
;

-- 2018-11-10T10:16:21.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-10 10:16:21','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='AttributeValueHandler java class',PrintName='AttributeValueHandler java class',Description='Java class which implements the IAttributeValueHandler interface. If not set, then a default implemention is used that invokes the attribbute''s dynamic validation rule.' WHERE AD_Element_ID=575867 AND AD_Language='en_US'
;

-- 2018-11-10T10:16:21.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575867,'en_US') 
;

-- 2018-11-10T10:16:33.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-10 10:16:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575867 AND AD_Language='de_CH'
;

-- 2018-11-10T10:16:33.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575867,'de_CH') 
;

-- 2018-11-10T10:16:54.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=575867, Description='Javaklasse, die das Interface IAttributeValueHandler implementiert. Falls leer wird eine Defaultimplementierung benutzt, die die dynamische Validierungsregel aufruft.', Help=NULL, Name='AttributeValueHandler Javaklasse',Updated=TO_TIMESTAMP('2018-11-10 10:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552506
;

-- 2018-11-10T10:16:54.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575867) 
;

-- 2018-11-10T10:22:41.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,575868,0,TO_TIMESTAMP('2018-11-10 10:22:41','YYYY-MM-DD HH24:MI:SS'),100,'Validierungsregel für erlaubte Attributewerte, die von der Default-AttributValueHandler Implementierung aufgerufen wird. Der Kontext enthält nur Spalten des jeweiligen M_Attribute-Datensatzes.','D','Ob auch andere AttributValueHandler Implementierungen die dynamische Attributvalidierungsregel benutzen, hängt von deren Implementierung ab.','Y','Dynamische Attributvalidierungsregel','Dynamische Attributvalidierungsregel',TO_TIMESTAMP('2018-11-10 10:22:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-11-10T10:22:41.570
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=575868 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-11-10T10:22:49.985
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-10 10:22:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=575868 AND AD_Language='de_CH'
;

-- 2018-11-10T10:22:49.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575868,'de_CH') 
;

-- 2018-11-10T10:27:26.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-10 10:27:26','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Dynamic attribute validation rule',PrintName='Dynamic attribute validation rule',Description='Defines valid attribute values and is invoked by the default AttributValueHandler implementation. Its context contains only columns of the respective M_Attribute record.',Help='Whether other AttributValueHandler implementations use the dynamic attribute validation rule depends on their particular implementation.' WHERE AD_Element_ID=575868 AND AD_Language='en_US'
;

-- 2018-11-10T10:27:26.894
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575868,'en_US') 
;

-- 2018-11-10T10:27:47.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-10 10:27:47','YYYY-MM-DD HH24:MI:SS'),Description='Specifies valid attribute values and is invoked by the default AttributValueHandler implementation. Its context contains only columns of the respective M_Attribute record.' WHERE AD_Element_ID=575868 AND AD_Language='en_US'
;

-- 2018-11-10T10:27:47.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575868,'en_US') 
;

-- 2018-11-10T10:28:35.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-11-10 10:28:35','YYYY-MM-DD HH24:MI:SS'),Description='Spezifiziert mögliche Attributewerte und wird von der Default-AttributValueHandler Implementierung aufgerufen. Der Kontext enthält nur Spalten des jeweiligen M_Attribute-Datensatzes.' WHERE AD_Element_ID=575868 AND AD_Language='de_CH'
;

-- 2018-11-10T10:28:35.340
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(575868,'de_CH') 
;

-- 2018-11-10T10:29:19.379
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=575868, Description='Validierungsregel für erlaubte Attributewerte, die von der Default-AttributValueHandler Implementierung aufgerufen wird. Der Kontext enthält nur Spalten des jeweiligen M_Attribute-Datensatzes.', Help='Ob auch andere AttributValueHandler Implementierungen die dynamische Attributvalidierungsregel benutzen, hängt von deren Implementierung ab.', Name='Dynamische Attributvalidierungsregel',Updated=TO_TIMESTAMP('2018-11-10 10:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=73427
;

-- 2018-11-10T10:29:19.384
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575868) 
;

