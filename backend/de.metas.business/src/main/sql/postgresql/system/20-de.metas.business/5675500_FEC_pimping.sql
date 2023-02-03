-- Column: C_ForeignExchangeContract_Alloc.AllocatedAmt
-- 2023-02-03T14:02:55.937Z
UPDATE AD_Column SET AD_Element_ID=2677, ColumnName='AllocatedAmt', Description='Amount allocated to this document', Help=NULL, Name='Allocated Amountt',Updated=TO_TIMESTAMP('2023-02-03 16:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585560
;

-- 2023-02-03T14:02:55.940Z
UPDATE AD_Column_Trl trl SET Name='Allocated Amountt' WHERE AD_Column_ID=585560 AND AD_Language='en_US'
;

-- 2023-02-03T14:02:55.942Z
UPDATE AD_Field SET Name='Allocated Amountt', Description='Amount allocated to this document', Help=NULL WHERE AD_Column_ID=585560
;

-- 2023-02-03T14:02:55.970Z
/* DDL */  select update_Column_Translation_From_AD_Element(2677) 
;

-- Element: AllocatedAmt
-- 2023-02-03T14:03:11.819Z
UPDATE AD_Element_Trl SET Name='Allocated Amount',Updated=TO_TIMESTAMP('2023-02-03 16:03:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2677 AND AD_Language='en_US'
;

-- 2023-02-03T14:03:11.821Z
UPDATE AD_Element SET Name='Allocated Amount' WHERE AD_Element_ID=2677
;

-- 2023-02-03T14:03:12.217Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2677,'en_US') 
;

-- 2023-02-03T14:03:12.220Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2677,'en_US') 
;

alter table C_ForeignExchangeContract_Alloc rename column Amount to AllocatedAmt;

-- 2023-02-03T14:06:02.593Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582012,0,TO_TIMESTAMP('2023-02-03 16:06:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Order','Order',TO_TIMESTAMP('2023-02-03 16:06:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T14:06:02.595Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582012 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract Allocation(546746,D) -> Order
-- Column: C_ForeignExchangeContract_Alloc.C_Order_ID
-- 2023-02-03T14:07:01.232Z
UPDATE AD_Field SET AD_Name_ID=580716, Description=NULL, Help=NULL, Name='Order',Updated=TO_TIMESTAMP('2023-02-03 16:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710319
;

-- 2023-02-03T14:07:01.234Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Order' WHERE AD_Field_ID=710319 AND AD_Language='en_US'
;

-- 2023-02-03T14:07:01.236Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580716) 
;

-- 2023-02-03T14:07:01.248Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710319
;

-- 2023-02-03T14:07:01.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710319)
;

-- 2023-02-03T14:07:43.533Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582013,0,TO_TIMESTAMP('2023-02-03 16:07:43','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','FEC ID','FEC ID',TO_TIMESTAMP('2023-02-03 16:07:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T14:07:43.535Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582013 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-02-03T14:07:47.364Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-03 16:07:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582013
;

-- 2023-02-03T14:07:47.367Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582013,'en_US') 
;

-- Field: Foreign Exchange Contract(541664,D) -> Foreign Exchange Contract(546745,D) -> FEC ID
-- Column: C_ForeignExchangeContract.DocumentNo
-- 2023-02-03T14:08:04.425Z
UPDATE AD_Field SET AD_Name_ID=582013, Description=NULL, Help=NULL, Name='FEC ID',Updated=TO_TIMESTAMP('2023-02-03 16:08:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710303
;

-- 2023-02-03T14:08:04.426Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='FEC ID' WHERE AD_Field_ID=710303 AND AD_Language='en_US'
;

-- 2023-02-03T14:08:04.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582013) 
;

-- 2023-02-03T14:08:04.432Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710303
;

-- 2023-02-03T14:08:04.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710303)
;

-- Column: C_ForeignExchangeContract.FEC_CurrencyRate
-- 2023-02-03T14:09:15.661Z
UPDATE AD_Column SET AD_Element_ID=581963, ColumnName='FEC_CurrencyRate', Description=NULL, Help=NULL, Name='FEC Currency Rate',Updated=TO_TIMESTAMP('2023-02-03 16:09:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585541
;

-- 2023-02-03T14:09:15.663Z
UPDATE AD_Column_Trl trl SET Name='FEC Currency Rate' WHERE AD_Column_ID=585541 AND AD_Language='en_US'
;

-- 2023-02-03T14:09:15.664Z
UPDATE AD_Field SET Name='FEC Currency Rate', Description=NULL, Help=NULL WHERE AD_Column_ID=585541
;

-- 2023-02-03T14:09:15.665Z
/* DDL */  select update_Column_Translation_From_AD_Element(581963) 
;

-- Element: FEC_CurrencyRate
-- 2023-02-03T14:09:30.143Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='FEC Rate', PrintName='FEC Rate',Updated=TO_TIMESTAMP('2023-02-03 16:09:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581963 AND AD_Language='en_US'
;

-- 2023-02-03T14:09:30.145Z
UPDATE AD_Element SET Name='FEC Rate', PrintName='FEC Rate' WHERE AD_Element_ID=581963
;

-- 2023-02-03T14:09:30.488Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581963,'en_US') 
;

-- 2023-02-03T14:09:30.490Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581963,'en_US') 
;


alter table C_ForeignExchangeContract rename column CurrencyRate to FEC_CurrencyRate;

