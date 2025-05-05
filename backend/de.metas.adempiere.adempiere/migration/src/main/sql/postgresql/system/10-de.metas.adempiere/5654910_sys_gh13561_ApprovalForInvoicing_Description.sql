-- 2022-09-07T09:22:16.116Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.',Updated=TO_TIMESTAMP('2022-09-07 12:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='en_US'
;

-- 2022-09-07T09:22:16.179Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'en_US') 
;

-- 2022-09-07T09:23:56.373Z
-- URL zum Konzept
UPDATE AD_Process SET Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.',Updated=TO_TIMESTAMP('2022-09-07 12:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540830
;

-- 2022-09-07T09:25:58.743Z
-- URL zum Konzept
UPDATE AD_Process SET TechnicalNote='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.',Updated=TO_TIMESTAMP('2022-09-07 12:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540830
;

-- 2022-09-07T09:29:31.485Z
-- URL zum Konzept
UPDATE AD_Process SET TechnicalNote='',Updated=TO_TIMESTAMP('2022-09-07 12:29:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540830
;

-- 2022-09-07T09:29:48.083Z
-- URL zum Konzept
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2022-09-07 12:29:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540830
;

-- 2022-09-07T09:31:02.172Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.',Updated=TO_TIMESTAMP('2022-09-07 12:31:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='de_DE'
;

-- 2022-09-07T09:31:02.206Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'de_DE') 
;

-- 2022-09-07T09:31:02.277Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(542572,'de_DE') 
;

-- 2022-09-07T09:31:02.307Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ApprovalForInvoicing', Name='Freigabe zur Fakturierung', Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.' WHERE AD_Element_ID=542572
;

-- 2022-09-07T09:31:02.341Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ApprovalForInvoicing', Name='Freigabe zur Fakturierung', Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', AD_Element_ID=542572 WHERE UPPER(ColumnName)='APPROVALFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:31:02.376Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ApprovalForInvoicing', Name='Freigabe zur Fakturierung', Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.' WHERE AD_Element_ID=542572 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:31:02.406Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Freigabe zur Fakturierung', Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542572) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542572)
;

-- 2022-09-07T09:31:02.488Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Freigabe zur Fakturierung', Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', CommitWarning = NULL WHERE AD_Element_ID = 542572
;

-- 2022-09-07T09:31:02.519Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Freigabe zur Fakturierung', Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.' WHERE AD_Element_ID = 542572
;

-- 2022-09-07T09:31:02.549Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Freigabe zur Fakturierung', Description = 'After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542572
;

-- 2022-09-07T09:31:48.637Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.',Updated=TO_TIMESTAMP('2022-09-07 12:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='de_CH'
;

-- 2022-09-07T09:31:48.667Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'de_CH') 
;

-- 2022-09-07T09:41:55.054Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.',Updated=TO_TIMESTAMP('2022-09-07 12:41:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='de_CH'
;

-- 2022-09-07T09:41:55.084Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'de_CH') 
;

-- 2022-09-07T09:42:06.102Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.',Updated=TO_TIMESTAMP('2022-09-07 12:42:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='de_DE'
;

-- 2022-09-07T09:42:06.133Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'de_DE') 
;

-- 2022-09-07T09:42:06.237Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(542572,'de_DE') 
;

-- 2022-09-07T09:42:06.266Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ApprovalForInvoicing', Name='Freigabe zur Fakturierung', Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.' WHERE AD_Element_ID=542572
;

-- 2022-09-07T09:42:06.297Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ApprovalForInvoicing', Name='Freigabe zur Fakturierung', Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', AD_Element_ID=542572 WHERE UPPER(ColumnName)='APPROVALFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:42:06.328Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ApprovalForInvoicing', Name='Freigabe zur Fakturierung', Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.' WHERE AD_Element_ID=542572 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:42:06.359Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Freigabe zur Fakturierung', Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542572) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542572)
;

-- 2022-09-07T09:42:06.404Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Freigabe zur Fakturierung', Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', CommitWarning = NULL WHERE AD_Element_ID = 542572
;

-- 2022-09-07T09:42:06.434Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Freigabe zur Fakturierung', Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.' WHERE AD_Element_ID = 542572
;

-- 2022-09-07T09:42:06.466Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Freigabe zur Fakturierung', Description = 'Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 542572
;

-- 2022-09-07T09:44:32.908Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.', Help='After an invoice candidate is approved for invoicing, its prices and taxes will not be updated from the corresponding order line.',Updated=TO_TIMESTAMP('2022-09-07 12:44:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540830
;

-- 2022-09-07T09:48:03.670Z
-- URL zum Konzept
UPDATE AD_Process SET Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.',Updated=TO_TIMESTAMP('2022-09-07 12:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540830
;

-- 2022-09-07T09:48:18.677Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.',Updated=TO_TIMESTAMP('2022-09-07 12:48:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540830
;

-- 2022-09-07T09:48:40.449Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.', Help='Nachdem ein Rechnungskandidat für die Rechnungsstellung freigegeben wurde, werden seine Preise und Steuern nicht mehr aus der entsprechenden Bestell- oder Auftragsposition aktualisiert.',Updated=TO_TIMESTAMP('2022-09-07 12:48:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=540830
;

-- 2022-09-07T10:37:54.426Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.', Help='After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.',Updated=TO_TIMESTAMP('2022-09-07 13:37:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='en_US'
;

-- 2022-09-07T10:37:54.460Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'en_US') 
;

-- 2022-09-07T10:38:06.342Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.', Help='After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.',Updated=TO_TIMESTAMP('2022-09-07 13:38:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542572 AND AD_Language='en_GB'
;

-- 2022-09-07T10:38:06.372Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542572,'en_GB') 
;

-- 2022-09-07T10:39:50.286Z
-- URL zum Konzept
UPDATE AD_Process_Trl SET Description='After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.', Help='After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.',Updated=TO_TIMESTAMP('2022-09-07 13:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=540830
;

