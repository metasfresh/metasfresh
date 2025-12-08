
-- Element: InvoiceDocumentNo
-- 2025-01-13T14:13:13.885479425Z
UPDATE AD_Element_Trl SET Name='Rechnungsnummer',Updated=TO_TIMESTAMP('2025-01-13 15:13:13.885','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=2106 AND AD_Language='de_DE'
;

-- 2025-01-13T14:13:13.886806668Z
UPDATE AD_Element SET Name='Rechnungsnummer', Updated=TO_TIMESTAMP('2025-01-13 15:13:13.886','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=2106
;

-- 2025-01-13T14:13:14.228307728Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2106,'de_DE') 
;

-- 2025-01-13T14:13:14.230400927Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2106,'de_DE') 
;

-- Element: InvoiceDocumentNo
-- 2025-01-13T14:13:15.706821065Z
UPDATE AD_Element_Trl SET PrintName='Rechnungsnummer',Updated=TO_TIMESTAMP('2025-01-13 15:13:15.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=2106 AND AD_Language='de_DE'
;

-- 2025-01-13T14:13:15.707332930Z
UPDATE AD_Element SET PrintName='Rechnungsnummer', Updated=TO_TIMESTAMP('2025-01-13 15:13:15.707','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=2106
;

-- 2025-01-13T14:13:15.982473384Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2106,'de_DE') 
;

-- 2025-01-13T14:13:15.983025389Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2106,'de_DE') 
;

-- Element: InvoiceDocumentNo
-- 2025-01-13T14:13:23.550947839Z
UPDATE AD_Element_Trl SET Name='Rechnungsnummer',Updated=TO_TIMESTAMP('2025-01-13 15:13:23.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=2106 AND AD_Language='de_CH'
;

-- 2025-01-13T14:13:23.559930301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2106,'de_CH') 
;

-- Element: InvoiceDocumentNo
-- 2025-01-13T14:13:24.141166480Z
UPDATE AD_Element_Trl SET PrintName='Rechnungsnummer',Updated=TO_TIMESTAMP('2025-01-13 15:13:24.141','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=2106 AND AD_Language='de_CH'
;

-- 2025-01-13T14:13:24.142260670Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2106,'de_CH') 
;









-- 2025-01-13T14:25:27.291287473Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583423,0,TO_TIMESTAMP('2025-01-13 15:25:27.29','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','LineGrossAmt','LineGrossAmt',TO_TIMESTAMP('2025-01-13 15:25:27.29','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-01-13T14:25:27.293954768Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583423 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-01-13T14:25:40.928062169Z
UPDATE AD_Element SET ColumnName='LineGrossAmt',Updated=TO_TIMESTAMP('2025-01-13 15:25:40.928','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:25:40.928878986Z
UPDATE AD_Column SET ColumnName='LineGrossAmt' WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:25:40.929091768Z
UPDATE AD_Process_Para SET ColumnName='LineGrossAmt' WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:25:40.929976386Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_DE') 
;

-- 2025-01-13T14:26:19.331245Z
UPDATE AD_Element SET Name='Line Gross Amount',Updated=TO_TIMESTAMP('2025-01-13 15:26:19.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:26:19.331703234Z
UPDATE AD_Element_Trl trl SET Name='Line Gross Amount' WHERE AD_Element_ID=583423 AND AD_Language='de_DE'
;

-- 2025-01-13T14:26:19.333247038Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_DE') 
;

-- 2025-01-13T14:26:23.644397851Z
UPDATE AD_Element SET PrintName='Line Gross Amount',Updated=TO_TIMESTAMP('2025-01-13 15:26:23.644','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:26:23.644703364Z
UPDATE AD_Element_Trl trl SET PrintName='Line Gross Amount' WHERE AD_Element_ID=583423 AND AD_Language='de_DE'
;

-- 2025-01-13T14:26:23.645400970Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_DE') 
;

-- 2025-01-13T14:26:31.015251982Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-01-13 15:26:31.015','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:26:31.016631174Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_DE') 
;

-- Element: LineGrossAmt
-- 2025-01-13T14:26:48.235051099Z
UPDATE AD_Element_Trl SET Name='Bruttowerte',Updated=TO_TIMESTAMP('2025-01-13 15:26:48.234','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423 AND AD_Language='de_CH'
;

-- 2025-01-13T14:26:48.235915937Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_CH') 
;

-- Element: LineGrossAmt
-- 2025-01-13T14:26:51.857253519Z
UPDATE AD_Element_Trl SET PrintName='Bruttowerte',Updated=TO_TIMESTAMP('2025-01-13 15:26:51.857','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423 AND AD_Language='de_CH'
;

-- 2025-01-13T14:26:51.858441540Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_CH') 
;

-- Element: LineGrossAmt
-- 2025-01-13T14:27:09.890185751Z
UPDATE AD_Element_Trl SET Name='Zeilenbrutto',Updated=TO_TIMESTAMP('2025-01-13 15:27:09.89','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423 AND AD_Language='de_CH'
;

-- 2025-01-13T14:27:09.894902073Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_CH') 
;

-- Element: LineGrossAmt
-- 2025-01-13T14:27:10.575643459Z
UPDATE AD_Element_Trl SET PrintName='Zeilenbrutto',Updated=TO_TIMESTAMP('2025-01-13 15:27:10.575','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423 AND AD_Language='de_CH'
;

-- 2025-01-13T14:27:10.576706129Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_CH') 
;

-- Element: LineGrossAmt
-- 2025-01-13T14:27:17.055580562Z
UPDATE AD_Element_Trl SET Name='Zeilenbrutto',Updated=TO_TIMESTAMP('2025-01-13 15:27:17.055','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423 AND AD_Language='de_DE'
;

-- 2025-01-13T14:27:17.056061056Z
UPDATE AD_Element SET Name='Zeilenbrutto', Updated=TO_TIMESTAMP('2025-01-13 15:27:17.056','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:27:17.429262734Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583423,'de_DE') 
;

-- 2025-01-13T14:27:17.430736077Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_DE') 
;

-- Element: LineGrossAmt
-- 2025-01-13T14:27:17.942168537Z
UPDATE AD_Element_Trl SET PrintName='Zeilenbrutto',Updated=TO_TIMESTAMP('2025-01-13 15:27:17.942','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583423 AND AD_Language='de_DE'
;

-- 2025-01-13T14:27:17.942639621Z
UPDATE AD_Element SET PrintName='Zeilenbrutto', Updated=TO_TIMESTAMP('2025-01-13 15:27:17.942','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=583423
;

-- 2025-01-13T14:27:18.202575984Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583423,'de_DE') 
;

-- 2025-01-13T14:27:18.203055759Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583423,'de_DE') 
;
