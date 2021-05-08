-- 2021-04-30T11:34:59.682Z
-- URL zum Konzept
UPDATE AD_Tab SET Parent_Column_ID=573555,Updated=TO_TIMESTAMP('2021-04-30 13:34:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543842
;

-- 2021-04-30T11:40:12.401Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2021-04-30 13:40:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573599
;

-- 2021-04-30T12:04:58.668Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_therapytype','TherapyType','VARCHAR(255)',null,null)
;

-- 2021-04-30T12:15:04.369Z
-- URL zum Konzept
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2021-04-30 14:15:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573599
;

-- 2021-04-30T12:15:07.235Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_therapytype','TherapyType','VARCHAR(255)',null,null)
;

-- 2021-04-30T12:20:02.136Z
-- URL zum Konzept
UPDATE AD_Tab SET Parent_Column_ID=573555,Updated=TO_TIMESTAMP('2021-04-30 14:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543844
;

-- 2021-04-30T12:21:01.336Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-04-30 14:21:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573635
;

-- 2021-04-30T12:21:07.072Z
-- URL zum Konzept
INSERT INTO t_alter_column values('alberta_prescriptionrequest_accounting','AccountingMonths','VARCHAR(2)',null,null)
;

-- 2021-04-30T12:23:31.159Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Therapieform ID', PrintName='Therapieform ID',Updated=TO_TIMESTAMP('2021-04-30 14:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579052 AND AD_Language='de_CH'
;

-- 2021-04-30T12:23:31.179Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579052,'de_CH') 
;

-- 2021-04-30T12:23:40.975Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Therapieform ID', PrintName='Therapieform ID',Updated=TO_TIMESTAMP('2021-04-30 14:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579052 AND AD_Language='de_DE'
;

-- 2021-04-30T12:23:40.979Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579052,'de_DE') 
;

-- 2021-04-30T12:23:40.988Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579052,'de_DE') 
;

-- 2021-04-30T12:23:40.990Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Alberta_PrescriptionRequest_TherapyType_ID', Name='Therapieform ID', Description=NULL, Help=NULL WHERE AD_Element_ID=579052
;

-- 2021-04-30T12:23:40.990Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Alberta_PrescriptionRequest_TherapyType_ID', Name='Therapieform ID', Description=NULL, Help=NULL, AD_Element_ID=579052 WHERE UPPER(ColumnName)='ALBERTA_PRESCRIPTIONREQUEST_THERAPYTYPE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-04-30T12:23:40.991Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Alberta_PrescriptionRequest_TherapyType_ID', Name='Therapieform ID', Description=NULL, Help=NULL WHERE AD_Element_ID=579052 AND IsCentrallyMaintained='Y'
;

-- 2021-04-30T12:23:40.991Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Therapieform ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579052) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579052)
;

-- 2021-04-30T12:23:41.002Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Therapieform ID', Name='Therapieform ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579052)
;

-- 2021-04-30T12:23:41.003Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Therapieform ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579052
;

-- 2021-04-30T12:23:41.004Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Therapieform ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 579052
;

-- 2021-04-30T12:23:41.005Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Therapieform ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579052
;

-- 2021-04-30T12:23:51.299Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Therapieform ID', PrintName='Therapieform ID',Updated=TO_TIMESTAMP('2021-04-30 14:23:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579052 AND AD_Language='nl_NL'
;

-- 2021-04-30T12:23:51.301Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579052,'nl_NL') 
;

-- 2021-04-30T12:26:23.911Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Therapieform ID',Updated=TO_TIMESTAMP('2021-04-30 14:26:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583802
;

-- 2021-04-30T12:27:51.255Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2021-04-30 14:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583816
;

-- 2021-04-30T12:27:51.707Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2021-04-30 14:27:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583817
;

-- 2021-04-30T12:27:53.356Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2021-04-30 14:27:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583818
;

-- 2021-04-30T12:27:56.242Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2021-04-30 14:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583819
;

-- 2021-04-30T12:41:50.941Z
-- URL zum Konzept
UPDATE AD_UI_Element SET Name='Therapieart',Updated=TO_TIMESTAMP('2021-04-30 14:41:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583802
;

-- 2021-04-30T12:45:11.817Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2021-04-30 14:45:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573600
;

-- 2021-04-30T12:45:31.733Z
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2021-04-30 14:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573638
;

