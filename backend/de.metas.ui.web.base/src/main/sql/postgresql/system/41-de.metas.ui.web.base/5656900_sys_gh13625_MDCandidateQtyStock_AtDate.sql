-- 2022-09-20T11:26:17.264Z
UPDATE AD_Element SET ColumnName='MDCandidateQtyStock_AtDate',Updated=TO_TIMESTAMP('2022-09-20 14:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579973
;

-- 2022-09-20T11:26:17.279Z
UPDATE AD_Column SET ColumnName='MDCandidateQtyStock_AtDate', Name='MDCandidateQtyStock', Description=NULL, Help=NULL WHERE AD_Element_ID=579973
;

-- 2022-09-20T11:26:17.281Z
UPDATE AD_Process_Para SET ColumnName='MDCandidateQtyStock_AtDate', Name='MDCandidateQtyStock', Description=NULL, Help=NULL, AD_Element_ID=579973 WHERE UPPER(ColumnName)='MDCANDIDATEQTYSTOCK_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-20T11:26:17.284Z
UPDATE AD_Process_Para SET ColumnName='MDCandidateQtyStock_AtDate', Name='MDCandidateQtyStock', Description=NULL, Help=NULL WHERE AD_Element_ID=579973 AND IsCentrallyMaintained='Y'
;

-- 2022-09-20T11:26:33.953Z
UPDATE AD_Element_Trl SET Name='📆 MDCandidateQtyStock', PrintName='📆 MDCandidateQtyStock',Updated=TO_TIMESTAMP('2022-09-20 14:26:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579973 AND AD_Language='de_CH'
;

-- 2022-09-20T11:26:33.992Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579973,'de_CH') 
;

-- 2022-09-20T11:26:37.136Z
UPDATE AD_Element_Trl SET Name='📆 MDCandidateQtyStock', PrintName='📆 MDCandidateQtyStock',Updated=TO_TIMESTAMP('2022-09-20 14:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579973 AND AD_Language='de_DE'
;

-- 2022-09-20T11:26:37.138Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579973,'de_DE') 
;

-- 2022-09-20T11:26:37.166Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(579973,'de_DE') 
;

-- 2022-09-20T11:26:37.171Z
UPDATE AD_Column SET ColumnName='MDCandidateQtyStock_AtDate', Name='📆 MDCandidateQtyStock', Description=NULL, Help=NULL WHERE AD_Element_ID=579973
;

-- 2022-09-20T11:26:37.174Z
UPDATE AD_Process_Para SET ColumnName='MDCandidateQtyStock_AtDate', Name='📆 MDCandidateQtyStock', Description=NULL, Help=NULL, AD_Element_ID=579973 WHERE UPPER(ColumnName)='MDCANDIDATEQTYSTOCK_ATDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-20T11:26:37.176Z
UPDATE AD_Process_Para SET ColumnName='MDCandidateQtyStock_AtDate', Name='📆 MDCandidateQtyStock', Description=NULL, Help=NULL WHERE AD_Element_ID=579973 AND IsCentrallyMaintained='Y'
;

-- 2022-09-20T11:26:37.176Z
UPDATE AD_Field SET Name='📆 MDCandidateQtyStock', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579973) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579973)
;

-- 2022-09-20T11:26:37.213Z
UPDATE AD_PrintFormatItem pi SET PrintName='📆 MDCandidateQtyStock', Name='📆 MDCandidateQtyStock' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579973)
;

-- 2022-09-20T11:26:37.214Z
UPDATE AD_Tab SET Name='📆 MDCandidateQtyStock', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579973
;

-- 2022-09-20T11:26:37.217Z
UPDATE AD_WINDOW SET Name='📆 MDCandidateQtyStock', Description=NULL, Help=NULL WHERE AD_Element_ID = 579973
;

-- 2022-09-20T11:26:37.218Z
UPDATE AD_Menu SET   Name = '📆 MDCandidateQtyStock', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579973
;

-- 2022-09-20T11:26:40.664Z
UPDATE AD_Element_Trl SET Name='📆 MDCandidateQtyStock', PrintName='📆 MDCandidateQtyStock',Updated=TO_TIMESTAMP('2022-09-20 14:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579973 AND AD_Language='en_US'
;

-- 2022-09-20T11:26:40.666Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579973,'en_US') 
;

-- 2022-09-20T11:26:43.145Z
UPDATE AD_Element_Trl SET Name='📆 MDCandidateQtyStock', PrintName='📆 MDCandidateQtyStock',Updated=TO_TIMESTAMP('2022-09-20 14:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579973 AND AD_Language='nl_NL'
;

-- 2022-09-20T11:26:43.147Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579973,'nl_NL') 
;

ALTER TABLE md_cockpit
    RENAME COLUMN MDCandidateQtyStock TO MDCandidateQtyStock_AtDate;