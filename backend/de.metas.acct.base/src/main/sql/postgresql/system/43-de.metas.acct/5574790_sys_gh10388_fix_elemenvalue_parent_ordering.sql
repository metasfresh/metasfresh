-- 2020-12-14T15:24:02.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='length(C_ElementValue.Value), C_ElementValue.Value',Updated=TO_TIMESTAMP('2020-12-14 16:24:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=182
;

-- 2020-12-14T15:25:51.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Element Values ordered by length and lexiographically', Help='Ordering by length to make sure that if you want to select just "9", then 9 is the first match, and not 290, 329, 59 etc',Updated=TO_TIMESTAMP('2020-12-14 16:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=182
;

-- 2020-12-14T15:29:16.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578614,0,'Parent_ElementValue_ID',TO_TIMESTAMP('2020-12-14 16:29:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','Übergeordnetes Konto','Übergeordnetes Konto',TO_TIMESTAMP('2020-12-14 16:29:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-12-14T15:29:16.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578614 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-12-14T15:29:21.420Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-12-14 16:29:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578614 AND AD_Language='de_CH'
;

-- 2020-12-14T15:29:21.446Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578614,'de_CH') 
;

-- 2020-12-14T15:29:24.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-12-14 16:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578614 AND AD_Language='de_DE'
;

-- 2020-12-14T15:29:24.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578614,'de_DE') 
;

-- 2020-12-14T15:29:24.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578614,'de_DE') 
;

-- 2020-12-14T15:29:34.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Parent account', PrintName='Parent account',Updated=TO_TIMESTAMP('2020-12-14 16:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578614 AND AD_Language='en_US'
;

-- 2020-12-14T15:29:34.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578614,'en_US') 
;

-- 2020-12-14T15:50:02.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=578614, Description=NULL, Help=NULL, Name='Übergeordnetes Konto',Updated=TO_TIMESTAMP('2020-12-14 16:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=598471
;

-- 2020-12-14T15:50:02.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578614) 
;

-- 2020-12-14T15:50:02.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598471
;

-- 2020-12-14T15:50:02.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598471)
;

-- 2020-12-14T15:52:25.961Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Help='Ordering by length to make sure that if you want to select just "9", then 9 is the first match, and not 290, 329, 59 etc',Updated=TO_TIMESTAMP('2020-12-14 16:52:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541113
;

-- 2020-12-14T15:52:39.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET OrderByClause='length(C_ElementValue.Value), C_ElementValue.Value',Updated=TO_TIMESTAMP('2020-12-14 16:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541113
;

