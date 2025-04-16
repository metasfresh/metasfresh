

-- 2024-05-15T14:53:56.262Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abteilung', PrintName='Abteilung',Updated=TO_TIMESTAMP('2024-05-15 16:53:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583095 AND AD_Language='de_CH'
;

-- 2024-05-15T14:53:56.311Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583095,'de_CH') 
;

-- 2024-05-15T14:54:10.314Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abteilung', PrintName='Abteilung',Updated=TO_TIMESTAMP('2024-05-15 16:54:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583095 AND AD_Language='de_DE'
;

-- 2024-05-15T14:54:10.318Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583095,'de_DE') 
;

-- 2024-05-15T14:54:10.326Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583095,'de_DE') 
;

-- 2024-05-15T14:54:35.029Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Department', PrintName='Department',Updated=TO_TIMESTAMP('2024-05-15 16:54:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583095 AND AD_Language='en_US'
;

-- 2024-05-15T14:54:35.034Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583095,'en_US') 
;



-- Column: C_BPartner_Department.Name
-- 2024-05-31T14:14:31.196Z
UPDATE AD_Column SET FilterOperator='E', IsMandatory='Y', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-31 16:14:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588242
;

-- 2024-05-31T14:14:40.309Z
INSERT INTO t_alter_column values('c_bpartner_department','Name','VARCHAR(255)',null,null)
;

-- 2024-05-31T14:14:40.313Z
INSERT INTO t_alter_column values('c_bpartner_department','Name',null,'NOT NULL',null)
;

-- Column: C_BPartner_Department.Value
-- 2024-05-31T14:14:53.783Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-05-31 16:14:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588241
;

-- 2024-05-31T14:14:56.575Z
INSERT INTO t_alter_column values('c_bpartner_department','Value','VARCHAR(20)',null,null)
;

-- 2024-05-31T14:14:56.577Z
INSERT INTO t_alter_column values('c_bpartner_department','Value',null,'NOT NULL',null)
;

