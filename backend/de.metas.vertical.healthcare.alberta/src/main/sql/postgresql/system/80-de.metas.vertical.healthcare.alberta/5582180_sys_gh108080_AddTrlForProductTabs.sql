-- 2021-03-15T13:57:51.936Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Alberta-Artikeldaten',Updated=TO_TIMESTAMP('2021-03-15 14:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='de_CH'
;

-- 2021-03-15T13:57:51.957Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'de_CH')
;

-- 2021-03-15T13:58:06.259Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Alberta-Artikeldaten',Updated=TO_TIMESTAMP('2021-03-15 14:58:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='de_DE'
;

-- 2021-03-15T13:58:06.263Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'de_DE')
;

-- 2021-03-15T13:58:06.301Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578816,'de_DE')
;

-- 2021-03-15T13:58:06.302Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta-Artikeldaten', Name='M_Product_AlbertaArticle' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578816)
;

-- 2021-03-15T13:58:23.525Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Alberta article data',Updated=TO_TIMESTAMP('2021-03-15 14:58:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='en_US'
;

-- 2021-03-15T13:58:23.529Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'en_US')
;

-- 2021-03-15T13:58:26.428Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-15 14:58:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='en_US'
;

-- 2021-03-15T13:58:26.429Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'en_US')
;

-- 2021-03-15T13:58:43.779Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET PrintName='Alberta-Artikeldaten',Updated=TO_TIMESTAMP('2021-03-15 14:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='nl_NL'
;

-- 2021-03-15T13:58:43.783Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'nl_NL')
;

-- 2021-03-15T14:04:52.899Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta-Artikeldaten',Updated=TO_TIMESTAMP('2021-03-15 15:04:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='de_CH'
;

-- 2021-03-15T14:04:52.903Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'de_CH')
;

-- 2021-03-15T14:05:00.964Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta-Artikeldaten',Updated=TO_TIMESTAMP('2021-03-15 15:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='de_DE'
;

-- 2021-03-15T14:05:00.968Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'de_DE')
;

-- 2021-03-15T14:05:00.994Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578816,'de_DE')
;

-- 2021-03-15T14:05:00.996Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Product_AlbertaArticle_ID', Name='Alberta-Artikeldaten', Description=NULL, Help=NULL WHERE AD_Element_ID=578816
;

-- 2021-03-15T14:05:00.999Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaArticle_ID', Name='Alberta-Artikeldaten', Description=NULL, Help=NULL, AD_Element_ID=578816 WHERE UPPER(ColumnName)='M_PRODUCT_ALBERTAARTICLE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-15T14:05:01.001Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaArticle_ID', Name='Alberta-Artikeldaten', Description=NULL, Help=NULL WHERE AD_Element_ID=578816 AND IsCentrallyMaintained='Y'
;

-- 2021-03-15T14:05:01.002Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Alberta-Artikeldaten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578816) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578816)
;

-- 2021-03-15T14:05:01.028Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta-Artikeldaten', Name='Alberta-Artikeldaten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578816)
;

-- 2021-03-15T14:05:01.029Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Alberta-Artikeldaten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578816
;

-- 2021-03-15T14:05:01.031Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Alberta-Artikeldaten', Description=NULL, Help=NULL WHERE AD_Element_ID = 578816
;

-- 2021-03-15T14:05:01.031Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alberta-Artikeldaten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578816
;

-- 2021-03-15T14:05:09.094Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta article data',Updated=TO_TIMESTAMP('2021-03-15 15:05:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='en_US'
;

-- 2021-03-15T14:05:09.096Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'en_US')
;

-- 2021-03-15T14:05:21.238Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta-Artikeldaten',Updated=TO_TIMESTAMP('2021-03-15 15:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578816 AND AD_Language='nl_NL'
;

-- 2021-03-15T14:05:21.241Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578816,'nl_NL')
;

-- 2021-03-15T14:06:52.558Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta-Therapien', PrintName='Alberta-Therapien',Updated=TO_TIMESTAMP('2021-03-15 15:06:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578829 AND AD_Language='nl_NL'
;

-- 2021-03-15T14:06:52.561Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578829,'nl_NL')
;

-- 2021-03-15T14:07:03.508Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alberta-Therapien', PrintName='Alberta-Therapien',Updated=TO_TIMESTAMP('2021-03-15 15:07:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578829 AND AD_Language='de_DE'
;

-- 2021-03-15T14:07:03.511Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578829,'de_DE')
;

-- 2021-03-15T14:07:03.520Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578829,'de_DE')
;

-- 2021-03-15T14:07:03.520Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Product_AlbertaTherapy_ID', Name='Alberta-Therapien', Description=NULL, Help=NULL WHERE AD_Element_ID=578829
;

-- 2021-03-15T14:07:03.521Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaTherapy_ID', Name='Alberta-Therapien', Description=NULL, Help=NULL, AD_Element_ID=578829 WHERE UPPER(ColumnName)='M_PRODUCT_ALBERTATHERAPY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-15T14:07:03.521Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaTherapy_ID', Name='Alberta-Therapien', Description=NULL, Help=NULL WHERE AD_Element_ID=578829 AND IsCentrallyMaintained='Y'
;

-- 2021-03-15T14:07:03.521Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Alberta-Therapien', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578829) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578829)
;

-- 2021-03-15T14:07:03.531Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta-Therapien', Name='Alberta-Therapien' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578829)
;

-- 2021-03-15T14:07:03.532Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Alberta-Therapien', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578829
;

-- 2021-03-15T14:07:03.533Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Alberta-Therapien', Description=NULL, Help=NULL WHERE AD_Element_ID = 578829
;

-- 2021-03-15T14:07:03.533Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alberta-Therapien', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578829
;

-- 2021-03-15T14:07:10.635Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta-Therapien', PrintName='Alberta-Therapien',Updated=TO_TIMESTAMP('2021-03-15 15:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578829 AND AD_Language='de_CH'
;

-- 2021-03-15T14:07:10.638Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578829,'de_CH')
;

-- 2021-03-15T14:07:41.259Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alberta therapies', PrintName='Alberta therapies',Updated=TO_TIMESTAMP('2021-03-15 15:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578829 AND AD_Language='en_US'
;

-- 2021-03-15T14:07:41.260Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578829,'en_US')
;

-- 2021-03-15T14:08:22.671Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Billable Alberta therapies', PrintName='Billable Alberta therapies',Updated=TO_TIMESTAMP('2021-03-15 15:08:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578831 AND AD_Language='en_US'
;

-- 2021-03-15T14:08:22.674Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578831,'en_US')
;

-- 2021-03-15T14:09:00.580Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Abrechenbare Alberta-Therapien', PrintName='Abrechenbare Alberta-Therapien',Updated=TO_TIMESTAMP('2021-03-15 15:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578831 AND AD_Language='nl_NL'
;

-- 2021-03-15T14:09:00.580Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578831,'nl_NL')
;

-- 2021-03-15T14:09:12.367Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abrechenbare Alberta-Therapien', PrintName='Abrechenbare Alberta-Therapien',Updated=TO_TIMESTAMP('2021-03-15 15:09:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578831 AND AD_Language='de_DE'
;

-- 2021-03-15T14:09:12.368Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578831,'de_DE')
;

-- 2021-03-15T14:09:12.372Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578831,'de_DE')
;

-- 2021-03-15T14:09:12.373Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Product_AlbertaBillableTherapy_ID', Name='Abrechenbare Alberta-Therapien', Description=NULL, Help=NULL WHERE AD_Element_ID=578831
;

-- 2021-03-15T14:09:12.373Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaBillableTherapy_ID', Name='Abrechenbare Alberta-Therapien', Description=NULL, Help=NULL, AD_Element_ID=578831 WHERE UPPER(ColumnName)='M_PRODUCT_ALBERTABILLABLETHERAPY_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-15T14:09:12.374Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaBillableTherapy_ID', Name='Abrechenbare Alberta-Therapien', Description=NULL, Help=NULL WHERE AD_Element_ID=578831 AND IsCentrallyMaintained='Y'
;

-- 2021-03-15T14:09:12.374Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Abrechenbare Alberta-Therapien', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578831) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578831)
;

-- 2021-03-15T14:09:12.384Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Abrechenbare Alberta-Therapien', Name='Abrechenbare Alberta-Therapien' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578831)
;

-- 2021-03-15T14:09:12.384Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Abrechenbare Alberta-Therapien', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578831
;

-- 2021-03-15T14:09:12.385Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Abrechenbare Alberta-Therapien', Description=NULL, Help=NULL WHERE AD_Element_ID = 578831
;

-- 2021-03-15T14:09:12.386Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Abrechenbare Alberta-Therapien', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578831
;

-- 2021-03-15T14:09:23.127Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Abrechenbare Alberta-Therapien', PrintName='Abrechenbare Alberta-Therapien',Updated=TO_TIMESTAMP('2021-03-15 15:09:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578831 AND AD_Language='de_CH'
;

-- 2021-03-15T14:09:23.128Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578831,'de_CH')
;

-- 2021-03-15T14:11:34.615Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Abrechenbare Alberta-Therapien', PrintName='Abrechenbare Alberta-Therapien',Updated=TO_TIMESTAMP('2021-03-15 15:11:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578825 AND AD_Language='de_CH'
;

-- 2021-03-15T14:11:34.617Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578825,'de_CH')
;

-- 2021-03-15T14:12:24.152Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alberta-Verpackungseinheiten', PrintName='Alberta-Verpackungseinheiten',Updated=TO_TIMESTAMP('2021-03-15 15:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578825 AND AD_Language='de_DE'
;

-- 2021-03-15T14:12:24.154Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578825,'de_DE')
;

-- 2021-03-15T14:12:24.163Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578825,'de_DE')
;

-- 2021-03-15T14:12:24.164Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Product_AlbertaPackagingUnit_ID', Name='Alberta-Verpackungseinheiten', Description=NULL, Help=NULL WHERE AD_Element_ID=578825
;

-- 2021-03-15T14:12:24.164Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaPackagingUnit_ID', Name='Alberta-Verpackungseinheiten', Description=NULL, Help=NULL, AD_Element_ID=578825 WHERE UPPER(ColumnName)='M_PRODUCT_ALBERTAPACKAGINGUNIT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-03-15T14:12:24.165Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Product_AlbertaPackagingUnit_ID', Name='Alberta-Verpackungseinheiten', Description=NULL, Help=NULL WHERE AD_Element_ID=578825 AND IsCentrallyMaintained='Y'
;

-- 2021-03-15T14:12:24.165Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Alberta-Verpackungseinheiten', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578825) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578825)
;

-- 2021-03-15T14:12:24.175Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Alberta-Verpackungseinheiten', Name='Alberta-Verpackungseinheiten' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578825)
;

-- 2021-03-15T14:12:24.175Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Alberta-Verpackungseinheiten', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578825
;

-- 2021-03-15T14:12:24.176Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Alberta-Verpackungseinheiten', Description=NULL, Help=NULL WHERE AD_Element_ID = 578825
;

-- 2021-03-15T14:12:24.176Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Alberta-Verpackungseinheiten', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578825
;

-- 2021-03-15T14:12:31.448Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta-Verpackungseinheiten', PrintName='Alberta-Verpackungseinheiten',Updated=TO_TIMESTAMP('2021-03-15 15:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578825 AND AD_Language='de_CH'
;

-- 2021-03-15T14:12:31.448Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578825,'de_CH')
;

-- 2021-03-15T14:12:40.862Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Alberta-Verpackungseinheiten', PrintName='Alberta-Verpackungseinheiten',Updated=TO_TIMESTAMP('2021-03-15 15:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578825 AND AD_Language='nl_NL'
;

-- 2021-03-15T14:12:40.864Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578825,'nl_NL')
;

-- 2021-03-15T14:13:19.694Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Alberta packaging units', PrintName='Alberta packaging units',Updated=TO_TIMESTAMP('2021-03-15 15:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578825 AND AD_Language='en_US'
;

-- 2021-03-15T14:13:19.697Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578825,'en_US')
;

-- 2021-03-15T14:20:37.908Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Therapy1',Updated=TO_TIMESTAMP('2021-03-15 15:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579773
;

-- 2021-03-15T14:21:20.600Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Therapy',Updated=TO_TIMESTAMP('2021-03-15 15:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579773
;

-- 2021-03-15T14:36:56.505Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Therapie',Updated=TO_TIMESTAMP('2021-03-15 15:36:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541282
;

-- 2021-03-15T14:37:03.808Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='Therapie',Updated=TO_TIMESTAMP('2021-03-15 15:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541282
;

-- 2021-03-15T14:37:09.925Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET Name='Therapie',Updated=TO_TIMESTAMP('2021-03-15 15:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541282
;

-- 2021-03-15T14:44:57.375Z
-- URL zum Konzept
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-03-15 15:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541282
;

-- 2021-03-15T14:52:22.136Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='M_Product_AlbertaTherapy',Updated=TO_TIMESTAMP('2021-03-15 15:52:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579773
;

-- 2021-03-15T14:52:53.319Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Therapy',Updated=TO_TIMESTAMP('2021-03-15 15:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579773
;
