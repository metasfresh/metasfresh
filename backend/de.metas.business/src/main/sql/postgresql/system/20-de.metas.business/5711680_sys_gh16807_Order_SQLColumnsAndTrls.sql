-- Column: C_Order.net_in_base_currency
-- Source Table: C_OrderTax
-- 2023-11-23T15:25:49.452Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,544222,0,540150,259,TO_TIMESTAMP('2023-11-23 17:25:48','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3355,3766,314,TO_TIMESTAMP('2023-11-23 17:25:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Order.net_in_base_currency
-- Source Table: C_OrderTax
-- 2023-11-23T15:29:26.670Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=NULL,Updated=TO_TIMESTAMP('2023-11-23 17:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540150
;

-- Column: C_Order.net_in_base_currency
-- Source Table: C_OrderTax
-- 2023-11-23T15:31:56.678Z
DELETE FROM AD_SQLColumn_SourceTableColumn WHERE AD_SQLColumn_SourceTableColumn_ID=540150
;

-- Column: C_Order.netsum
-- Source Table: C_OrderTax
-- 2023-11-23T15:34:04.466Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,544448,0,540151,259,TO_TIMESTAMP('2023-11-23 17:34:04','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',3355,314,TO_TIMESTAMP('2023-11-23 17:34:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Order.C_BPartner_Memo
-- Source Table: C_BPartner
-- 2023-11-23T15:37:36.014Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,560875,0,540152,259,TO_TIMESTAMP('2023-11-23 17:37:35','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',2893,542502,291,TO_TIMESTAMP('2023-11-23 17:37:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Order.C_BPartner_Memo
-- Source Table: C_BPartner
-- 2023-11-23T15:38:40.729Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=NULL,Updated=TO_TIMESTAMP('2023-11-23 17:38:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540152
;

-- Column: C_Order.C_BPartner_Memo
-- Source Table: C_BPartner
-- 2023-11-23T15:40:36.471Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=2893,Updated=TO_TIMESTAMP('2023-11-23 17:40:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540152
;

-- Column: C_Order.C_BPartner_Memo
-- Source Table: C_BPartner
-- 2023-11-23T15:45:18.897Z
UPDATE AD_SQLColumn_SourceTableColumn SET Source_Column_ID=NULL,Updated=TO_TIMESTAMP('2023-11-23 17:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540152
;

-- Element: C_BPartner_Memo
-- 2023-11-23T12:38:28.733Z
UPDATE AD_Element_Trl SET Name='Memo',Updated=TO_TIMESTAMP('2023-11-23 14:38:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544250 AND AD_Language='de_CH'
;

-- 2023-11-23T12:38:28.736Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544250,'de_CH')
;

-- Element: C_BPartner_Memo
-- 2023-11-23T12:38:35.146Z
UPDATE AD_Element_Trl SET Name='Memo',Updated=TO_TIMESTAMP('2023-11-23 14:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544250 AND AD_Language='de_DE'
;

-- 2023-11-23T12:38:35.150Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(544250,'de_DE')
;

-- 2023-11-23T12:38:35.156Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544250,'de_DE')
;

-- Element: C_BPartner_Memo
-- 2023-11-23T12:38:37.155Z
UPDATE AD_Element_Trl SET Name='Memo',Updated=TO_TIMESTAMP('2023-11-23 14:38:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544250 AND AD_Language='fr_CH'
;

-- 2023-11-23T12:38:37.159Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544250,'fr_CH')
;
