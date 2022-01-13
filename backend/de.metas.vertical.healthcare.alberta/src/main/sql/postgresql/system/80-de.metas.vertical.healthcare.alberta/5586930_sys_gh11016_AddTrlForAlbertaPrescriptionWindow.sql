-- 2021-04-29T06:58:03.863Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anmerkung', PrintName='Anmerkung',Updated=TO_TIMESTAMP('2021-04-29 08:58:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578978 AND AD_Language='de_CH'
;

-- 2021-04-29T06:58:03.879Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578978,'de_CH') 
;

-- 2021-04-29T06:58:16.052Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anmerkung', PrintName='Anmerkung',Updated=TO_TIMESTAMP('2021-04-29 08:58:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578978 AND AD_Language='de_DE'
;

-- 2021-04-29T06:58:16.053Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578978,'de_DE') 
;

-- 2021-04-29T06:58:16.058Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578978,'de_DE') 
;

-- 2021-04-29T06:58:16.059Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Annotation', Name='Anmerkung', Description=NULL, Help=NULL WHERE AD_Element_ID=578978
;

-- 2021-04-29T06:58:16.060Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Annotation', Name='Anmerkung', Description=NULL, Help=NULL, AD_Element_ID=578978 WHERE UPPER(ColumnName)='ANNOTATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-29T06:58:16.060Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Annotation', Name='Anmerkung', Description=NULL, Help=NULL WHERE AD_Element_ID=578978 AND IsCentrallyMaintained='Y'
;

-- 2021-04-29T06:58:16.061Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Anmerkung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578978) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578978)
;

-- 2021-04-29T06:58:16.073Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Anmerkung', Name='Anmerkung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578978)
;

-- 2021-04-29T06:58:16.074Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Anmerkung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578978
;

-- 2021-04-29T06:58:16.074Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Anmerkung', Description=NULL, Help=NULL WHERE AD_Element_ID = 578978
;

-- 2021-04-29T06:58:16.075Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Anmerkung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578978
;

-- 2021-04-29T06:58:24.519Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-29 08:58:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578978 AND AD_Language='en_US'
;

-- 2021-04-29T06:58:24.520Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578978,'en_US') 
;

-- 2021-04-29T06:58:30.665Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Anmerkung', PrintName='Anmerkung',Updated=TO_TIMESTAMP('2021-04-29 08:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578978 AND AD_Language='nl_NL'
;

-- 2021-04-29T06:58:30.668Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578978,'nl_NL') 
;

-- 2021-04-29T07:02:37.695Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Doktor', PrintName='Doktor',Updated=TO_TIMESTAMP('2021-04-29 09:02:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579044 AND AD_Language='de_CH'
;

-- 2021-04-29T07:02:37.696Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579044,'de_CH') 
;

-- 2021-04-29T07:02:46.463Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Doktor', PrintName='Doktor',Updated=TO_TIMESTAMP('2021-04-29 09:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579044 AND AD_Language='de_DE'
;

-- 2021-04-29T07:02:46.466Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579044,'de_DE') 
;

-- 2021-04-29T07:02:46.492Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579044,'de_DE') 
;

-- 2021-04-29T07:02:46.495Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_BPartner_Doctor_ID', Name='Doktor', Description=NULL, Help=NULL WHERE AD_Element_ID=579044
;

-- 2021-04-29T07:02:46.499Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Doctor_ID', Name='Doktor', Description=NULL, Help=NULL, AD_Element_ID=579044 WHERE UPPER(ColumnName)='C_BPARTNER_DOCTOR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-29T07:02:46.501Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Doctor_ID', Name='Doktor', Description=NULL, Help=NULL WHERE AD_Element_ID=579044 AND IsCentrallyMaintained='Y'
;

-- 2021-04-29T07:02:46.502Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Doktor', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579044) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579044)
;

-- 2021-04-29T07:02:46.526Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Doktor', Name='Doktor' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579044)
;

-- 2021-04-29T07:02:46.527Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Doktor', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579044
;

-- 2021-04-29T07:02:46.529Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Doktor', Description=NULL, Help=NULL WHERE AD_Element_ID = 579044
;

-- 2021-04-29T07:02:46.529Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Doktor', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579044
;

-- 2021-04-29T07:02:55.037Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Doctor',Updated=TO_TIMESTAMP('2021-04-29 09:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579044 AND AD_Language='en_US'
;

-- 2021-04-29T07:02:55.037Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579044,'en_US') 
;

-- 2021-04-29T07:03:03.752Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Doktor', PrintName='Doktor',Updated=TO_TIMESTAMP('2021-04-29 09:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579044 AND AD_Language='nl_NL'
;

-- 2021-04-29T07:03:03.753Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579044,'nl_NL') 
;

-- 2021-04-29T07:04:04.597Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Apotheke', PrintName='Apotheke',Updated=TO_TIMESTAMP('2021-04-29 09:04:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579045 AND AD_Language='de_CH'
;

-- 2021-04-29T07:04:04.600Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579045,'de_CH') 
;

-- 2021-04-29T07:04:11.748Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Apotheke', PrintName='Apotheke',Updated=TO_TIMESTAMP('2021-04-29 09:04:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579045 AND AD_Language='de_DE'
;

-- 2021-04-29T07:04:11.751Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579045,'de_DE') 
;

-- 2021-04-29T07:04:11.760Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579045,'de_DE') 
;

-- 2021-04-29T07:04:11.760Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_BPartner_Pharmacy_ID', Name='Apotheke', Description=NULL, Help=NULL WHERE AD_Element_ID=579045
;

-- 2021-04-29T07:04:11.761Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Pharmacy_ID', Name='Apotheke', Description=NULL, Help=NULL, AD_Element_ID=579045 WHERE UPPER(ColumnName)='C_BPARTNER_PHARMACY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-29T07:04:11.761Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BPartner_Pharmacy_ID', Name='Apotheke', Description=NULL, Help=NULL WHERE AD_Element_ID=579045 AND IsCentrallyMaintained='Y'
;

-- 2021-04-29T07:04:11.762Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Apotheke', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579045) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579045)
;

-- 2021-04-29T07:04:11.771Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Apotheke', Name='Apotheke' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579045)
;

-- 2021-04-29T07:04:11.772Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Apotheke', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579045
;

-- 2021-04-29T07:04:11.773Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Apotheke', Description=NULL, Help=NULL WHERE AD_Element_ID = 579045
;

-- 2021-04-29T07:04:11.773Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Apotheke', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579045
;

-- 2021-04-29T07:04:16.609Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-29 09:04:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579045 AND AD_Language='en_US'
;

-- 2021-04-29T07:04:16.612Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579045,'en_US') 
;

-- 2021-04-29T07:04:22.166Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Apotheke', PrintName='Apotheke',Updated=TO_TIMESTAMP('2021-04-29 09:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579045 AND AD_Language='nl_NL'
;

-- 2021-04-29T07:04:22.167Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579045,'nl_NL') 
;

-- 2021-04-29T07:06:20.845Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579074,0,TO_TIMESTAMP('2021-04-29 09:06:20','YYYY-MM-DD HH24:MI:SS'),100,'External ID','D','Y','External ID','External ID',TO_TIMESTAMP('2021-04-29 09:06:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-29T07:06:20.846Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579074 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-04-29T07:07:02.844Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Externe ID', IsTranslated='Y', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-04-29 09:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579074 AND AD_Language='de_CH'
;

-- 2021-04-29T07:07:02.846Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579074,'de_CH') 
;

-- 2021-04-29T07:07:12.249Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Externe ID', IsTranslated='Y', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-04-29 09:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579074 AND AD_Language='de_DE'
;

-- 2021-04-29T07:07:12.250Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579074,'de_DE') 
;

-- 2021-04-29T07:07:12.255Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579074,'de_DE') 
;

-- 2021-04-29T07:07:12.255Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Externe ID', Description='Externe ID', Help=NULL WHERE AD_Element_ID=579074
;

-- 2021-04-29T07:07:12.256Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Externe ID', Description='Externe ID', Help=NULL WHERE AD_Element_ID=579074 AND IsCentrallyMaintained='Y'
;

-- 2021-04-29T07:07:12.256Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Externe ID', Description='Externe ID', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579074) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579074)
;

-- 2021-04-29T07:07:12.263Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Externe ID', Name='Externe ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579074)
;

-- 2021-04-29T07:07:12.264Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Externe ID', Description='Externe ID', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579074
;

-- 2021-04-29T07:07:12.265Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Externe ID', Description='Externe ID', Help=NULL WHERE AD_Element_ID = 579074
;

-- 2021-04-29T07:07:12.266Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Externe ID', Description = 'Externe ID', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579074
;

-- 2021-04-29T07:07:18.826Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-29 09:07:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579074 AND AD_Language='en_US'
;

-- 2021-04-29T07:07:18.828Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579074,'en_US') 
;

-- 2021-04-29T07:07:25.385Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Externe ID', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-04-29 09:07:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579074 AND AD_Language='nl_NL'
;

-- 2021-04-29T07:07:25.386Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579074,'nl_NL') 
;

-- 2021-04-29T07:08:23.546Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579074, Description='Externe ID', Help=NULL, Name='Externe ID',Updated=TO_TIMESTAMP('2021-04-29 09:08:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=644003
;

-- 2021-04-29T07:08:23.550Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579074) 
;

-- 2021-04-29T07:08:23.565Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644003
;

-- 2021-04-29T07:08:23.568Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644003)
;

-- 2021-04-29T07:10:19.849Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579074, Description='Externe ID', Help=NULL, Name='Externe ID',Updated=TO_TIMESTAMP('2021-04-29 09:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=644024
;

-- 2021-04-29T07:10:19.851Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579074) 
;

-- 2021-04-29T07:10:19.861Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=644024
;

-- 2021-04-29T07:10:19.863Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(644024)
;

