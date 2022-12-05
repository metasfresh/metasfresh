-- Element: M_Product_Value
-- 2022-09-07T09:42:40.048Z
UPDATE AD_Element_Trl SET Name='Produkt Suchschlüssel', PrintName='Produkt Suchschlüssel',Updated=TO_TIMESTAMP('2022-09-07 12:42:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577363 AND AD_Language='de_CH'
;

-- 2022-09-07T09:42:40.070Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577363,'de_CH') 
;

-- Element: M_Product_Value
-- 2022-09-07T09:42:47.001Z
UPDATE AD_Element_Trl SET Name='Produkt Suchschlüssel',Updated=TO_TIMESTAMP('2022-09-07 12:42:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577363 AND AD_Language='de_DE'
;

-- 2022-09-07T09:42:47.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577363,'de_DE') 
;

-- 2022-09-07T09:42:47.007Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577363,'de_DE') 
;

-- 2022-09-07T09:42:47.010Z
UPDATE AD_Column SET ColumnName='M_Product_Value', Name='Produkt Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=577363
;

-- 2022-09-07T09:42:47.012Z
UPDATE AD_Process_Para SET ColumnName='M_Product_Value', Name='Produkt Suchschlüssel', Description=NULL, Help=NULL, AD_Element_ID=577363 WHERE UPPER(ColumnName)='M_PRODUCT_VALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:42:47.012Z
UPDATE AD_Process_Para SET ColumnName='M_Product_Value', Name='Produkt Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=577363 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:42:47.013Z
UPDATE AD_Field SET Name='Produkt Suchschlüssel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577363) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577363)
;

-- 2022-09-07T09:42:47.023Z
UPDATE AD_PrintFormatItem pi SET PrintName='Product Value', Name='Produkt Suchschlüssel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577363)
;

-- 2022-09-07T09:42:47.025Z
UPDATE AD_Tab SET Name='Produkt Suchschlüssel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577363
;

-- 2022-09-07T09:42:47.026Z
UPDATE AD_WINDOW SET Name='Produkt Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID = 577363
;

-- 2022-09-07T09:42:47.026Z
UPDATE AD_Menu SET   Name = 'Produkt Suchschlüssel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577363
;

-- Element: M_Product_Value
-- 2022-09-07T09:43:02.796Z
UPDATE AD_Element_Trl SET PrintName='Produkt Suchschlüssel',Updated=TO_TIMESTAMP('2022-09-07 12:43:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577363 AND AD_Language='de_DE'
;

-- 2022-09-07T09:43:02.797Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577363,'de_DE') 
;

-- 2022-09-07T09:43:02.803Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577363,'de_DE') 
;

-- 2022-09-07T09:43:02.803Z
UPDATE AD_PrintFormatItem pi SET PrintName='Produkt Suchschlüssel', Name='Produkt Suchschlüssel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577363)
;

-- 2022-09-07T09:56:04.868Z
INSERT INTO t_alter_column values('i_invoice_candidate','M_Product_Value','VARCHAR(255)',null,null)
;

-- 2022-09-07T09:56:35.538Z
UPDATE AD_Element SET Name='Zeileninhalt', PrintName='Zeileninhalt',Updated=TO_TIMESTAMP('2022-09-07 12:56:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577115
;

-- 2022-09-07T09:56:35.582Z
UPDATE AD_Column SET ColumnName='I_LineContent', Name='Zeileninhalt', Description=NULL, Help=NULL WHERE AD_Element_ID=577115
;

-- 2022-09-07T09:56:35.586Z
UPDATE AD_Process_Para SET ColumnName='I_LineContent', Name='Zeileninhalt', Description=NULL, Help=NULL, AD_Element_ID=577115 WHERE UPPER(ColumnName)='I_LINECONTENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:56:35.586Z
UPDATE AD_Process_Para SET ColumnName='I_LineContent', Name='Zeileninhalt', Description=NULL, Help=NULL WHERE AD_Element_ID=577115 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:56:35.587Z
UPDATE AD_Field SET Name='Zeileninhalt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577115) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577115)
;

-- 2022-09-07T09:56:35.600Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zeileninhalt', Name='Zeileninhalt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577115)
;

-- 2022-09-07T09:56:35.601Z
UPDATE AD_Tab SET Name='Zeileninhalt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577115
;

-- 2022-09-07T09:56:35.602Z
UPDATE AD_WINDOW SET Name='Zeileninhalt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577115
;

-- 2022-09-07T09:56:35.603Z
UPDATE AD_Menu SET   Name = 'Zeileninhalt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577115
;

-- Element: I_LineContent
-- 2022-09-07T09:56:42.346Z
UPDATE AD_Element_Trl SET Name='Zeileninhalt', PrintName='Zeileninhalt',Updated=TO_TIMESTAMP('2022-09-07 12:56:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577115 AND AD_Language='de_CH'
;

-- 2022-09-07T09:56:42.349Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577115,'de_CH') 
;

-- Element: I_LineContent
-- 2022-09-07T09:56:47.356Z
UPDATE AD_Element_Trl SET Name='Zeileninhalt', PrintName='Zeileninhalt',Updated=TO_TIMESTAMP('2022-09-07 12:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577115 AND AD_Language='de_DE'
;

-- 2022-09-07T09:56:47.357Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577115,'de_DE') 
;

-- 2022-09-07T09:56:47.372Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577115,'de_DE') 
;

-- 2022-09-07T09:56:47.373Z
UPDATE AD_Column SET ColumnName='I_LineContent', Name='Zeileninhalt', Description=NULL, Help=NULL WHERE AD_Element_ID=577115
;

-- 2022-09-07T09:56:47.376Z
UPDATE AD_Process_Para SET ColumnName='I_LineContent', Name='Zeileninhalt', Description=NULL, Help=NULL, AD_Element_ID=577115 WHERE UPPER(ColumnName)='I_LINECONTENT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:56:47.377Z
UPDATE AD_Process_Para SET ColumnName='I_LineContent', Name='Zeileninhalt', Description=NULL, Help=NULL WHERE AD_Element_ID=577115 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:56:47.378Z
UPDATE AD_Field SET Name='Zeileninhalt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577115) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577115)
;

-- 2022-09-07T09:56:47.389Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zeileninhalt', Name='Zeileninhalt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577115)
;

-- 2022-09-07T09:56:47.390Z
UPDATE AD_Tab SET Name='Zeileninhalt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577115
;

-- 2022-09-07T09:56:47.391Z
UPDATE AD_WINDOW SET Name='Zeileninhalt', Description=NULL, Help=NULL WHERE AD_Element_ID = 577115
;

-- 2022-09-07T09:56:47.391Z
UPDATE AD_Menu SET   Name = 'Zeileninhalt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577115
;

-- 2022-09-07T09:57:31.746Z
UPDATE AD_Element SET Name='Zeile Nr.', PrintName='Zeile Nr.',Updated=TO_TIMESTAMP('2022-09-07 12:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577116
;

-- 2022-09-07T09:57:31.748Z
UPDATE AD_Column SET ColumnName='I_LineNo', Name='Zeile Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID=577116
;

-- 2022-09-07T09:57:31.750Z
UPDATE AD_Process_Para SET ColumnName='I_LineNo', Name='Zeile Nr.', Description=NULL, Help=NULL, AD_Element_ID=577116 WHERE UPPER(ColumnName)='I_LINENO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:57:31.750Z
UPDATE AD_Process_Para SET ColumnName='I_LineNo', Name='Zeile Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID=577116 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:57:31.751Z
UPDATE AD_Field SET Name='Zeile Nr.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577116) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577116)
;

-- 2022-09-07T09:57:31.763Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zeile Nr.', Name='Zeile Nr.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577116)
;

-- 2022-09-07T09:57:31.764Z
UPDATE AD_Tab SET Name='Zeile Nr.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577116
;

-- 2022-09-07T09:57:31.765Z
UPDATE AD_WINDOW SET Name='Zeile Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID = 577116
;

-- 2022-09-07T09:57:31.766Z
UPDATE AD_Menu SET   Name = 'Zeile Nr.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577116
;

-- Element: I_LineNo
-- 2022-09-07T09:57:37.621Z
UPDATE AD_Element_Trl SET Name='Zeile Nr.', PrintName='Zeile Nr.',Updated=TO_TIMESTAMP('2022-09-07 12:57:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577116 AND AD_Language='de_CH'
;

-- 2022-09-07T09:57:37.621Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577116,'de_CH') 
;

-- Element: I_LineNo
-- 2022-09-07T09:57:42.771Z
UPDATE AD_Element_Trl SET Name='Zeile Nr.', PrintName='Zeile Nr.',Updated=TO_TIMESTAMP('2022-09-07 12:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577116 AND AD_Language='de_DE'
;

-- 2022-09-07T09:57:42.772Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577116,'de_DE') 
;

-- 2022-09-07T09:57:42.778Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577116,'de_DE') 
;

-- 2022-09-07T09:57:42.779Z
UPDATE AD_Column SET ColumnName='I_LineNo', Name='Zeile Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID=577116
;

-- 2022-09-07T09:57:42.780Z
UPDATE AD_Process_Para SET ColumnName='I_LineNo', Name='Zeile Nr.', Description=NULL, Help=NULL, AD_Element_ID=577116 WHERE UPPER(ColumnName)='I_LINENO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:57:42.781Z
UPDATE AD_Process_Para SET ColumnName='I_LineNo', Name='Zeile Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID=577116 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:57:42.781Z
UPDATE AD_Field SET Name='Zeile Nr.', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577116) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577116)
;

-- 2022-09-07T09:57:42.790Z
UPDATE AD_PrintFormatItem pi SET PrintName='Zeile Nr.', Name='Zeile Nr.' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577116)
;

-- 2022-09-07T09:57:42.791Z
UPDATE AD_Tab SET Name='Zeile Nr.', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577116
;

-- 2022-09-07T09:57:42.792Z
UPDATE AD_WINDOW SET Name='Zeile Nr.', Description=NULL, Help=NULL WHERE AD_Element_ID = 577116
;

-- 2022-09-07T09:57:42.792Z
UPDATE AD_Menu SET   Name = 'Zeile Nr.', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577116
;

-- 2022-09-07T09:58:41.518Z
UPDATE AD_Element SET Name='Dokument-Subtyp', PrintName='Dokument-Subtyp',Updated=TO_TIMESTAMP('2022-09-07 12:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1018
;

-- 2022-09-07T09:58:41.556Z
UPDATE AD_Column SET ColumnName='DocSubType', Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE AD_Element_ID=1018
;

-- 2022-09-07T09:58:41.560Z
UPDATE AD_Process_Para SET ColumnName='DocSubType', Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document', AD_Element_ID=1018 WHERE UPPER(ColumnName)='DOCSUBTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:58:41.561Z
UPDATE AD_Process_Para SET ColumnName='DocSubType', Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE AD_Element_ID=1018 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:58:41.562Z
UPDATE AD_Field SET Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1018) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1018)
;

-- 2022-09-07T09:58:41.575Z
UPDATE AD_PrintFormatItem pi SET PrintName='Dokument-Subtyp', Name='Dokument-Subtyp' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1018)
;

-- 2022-09-07T09:58:41.577Z
UPDATE AD_Tab SET Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document', CommitWarning = NULL WHERE AD_Element_ID = 1018
;

-- 2022-09-07T09:58:41.578Z
UPDATE AD_WINDOW SET Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE AD_Element_ID = 1018
;

-- 2022-09-07T09:58:41.579Z
UPDATE AD_Menu SET   Name = 'Dokument-Subtyp', Description = 'Document Sub Type', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1018
;

-- Element: DocSubType
-- 2022-09-07T09:58:55.894Z
UPDATE AD_Element_Trl SET Name='Dokument-Subtyp', PrintName='Dokument-Subtyp',Updated=TO_TIMESTAMP('2022-09-07 12:58:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1018 AND AD_Language='de_CH'
;

-- 2022-09-07T09:58:55.896Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1018,'de_CH') 
;

-- Element: DocSubType
-- 2022-09-07T09:59:02.628Z
UPDATE AD_Element_Trl SET Name='Dokument-Subtyp', PrintName='Dokument-Subtyp',Updated=TO_TIMESTAMP('2022-09-07 12:59:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1018 AND AD_Language='de_DE'
;

-- 2022-09-07T09:59:02.629Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1018,'de_DE') 
;

-- 2022-09-07T09:59:02.635Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(1018,'de_DE') 
;

-- 2022-09-07T09:59:02.636Z
UPDATE AD_Column SET ColumnName='DocSubType', Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE AD_Element_ID=1018
;

-- 2022-09-07T09:59:02.637Z
UPDATE AD_Process_Para SET ColumnName='DocSubType', Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document', AD_Element_ID=1018 WHERE UPPER(ColumnName)='DOCSUBTYPE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T09:59:02.637Z
UPDATE AD_Process_Para SET ColumnName='DocSubType', Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE AD_Element_ID=1018 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T09:59:02.638Z
UPDATE AD_Field SET Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1018) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1018)
;

-- 2022-09-07T09:59:02.647Z
UPDATE AD_PrintFormatItem pi SET PrintName='Dokument-Subtyp', Name='Dokument-Subtyp' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1018)
;

-- 2022-09-07T09:59:02.647Z
UPDATE AD_Tab SET Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document', CommitWarning = NULL WHERE AD_Element_ID = 1018
;

-- 2022-09-07T09:59:02.648Z
UPDATE AD_WINDOW SET Name='Dokument-Subtyp', Description='Document Sub Type', Help='The Doc Sub Type indicates the type of order this document refers to.  The selection made here will determine which documents will be generated when an order is processed and which documents must be generated manually or in batches.  <br>
The following outlines this process.<br>
Doc Sub Type of <b>Standard Order</b> will generate just the <b>Order</b> document when the order is processed.  <br>
The <b>Delivery Note</b>, <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.  <br>
Doc Sub Type of <b>Warehouse Order</b> will generate the <b>Order</b> and <b>Delivery Note</b>. <br> The <b>Invoice</b> and <b>Receipt</b> must be generated via other processes.<br>
Doc Sub Type of <b>Credit Order</b> will generate the <b>Order</b>, <b>Delivery Note</b> and <b>Invoice</b>. <br> The <b>Reciept</b> must be generated via other processes.<br>
Doc Sub Type of <b>POS</b> (Point of Sale) will generate all document' WHERE AD_Element_ID = 1018
;

-- 2022-09-07T09:59:02.648Z
UPDATE AD_Menu SET   Name = 'Dokument-Subtyp', Description = 'Document Sub Type', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1018
;

-- Field: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> Org Code
-- Column: I_Invoice_Candidate.OrgCode
-- 2022-09-07T10:44:13.047Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584230,707009,0,546594,0,TO_TIMESTAMP('2022-09-07 13:44:11','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Org Code',0,10,0,1,1,TO_TIMESTAMP('2022-09-07 13:44:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-07T10:44:13.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-07T10:44:13.058Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581396) 
;

-- 2022-09-07T10:44:13.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707009
;

-- 2022-09-07T10:44:13.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707009)
;

-- UI Element: Import - Rechnungskandidaten(541605,D) -> Import - Invoice candiate(546594,D) -> main -> 10 -> default.Org Code
-- Column: I_Invoice_Candidate.OrgCode
-- 2022-09-07T10:44:30.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707009,0,546594,612917,549832,'F',TO_TIMESTAMP('2022-09-07 13:44:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Org Code',90,0,0,TO_TIMESTAMP('2022-09-07 13:44:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-07T10:47:25.404Z
UPDATE AD_Element SET Name='Org Suchschlüssel', PrintName='Org Suchschlüssel',Updated=TO_TIMESTAMP('2022-09-07 13:47:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581396
;

-- 2022-09-07T10:47:25.435Z
UPDATE AD_Column SET ColumnName='OrgCode', Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=581396
;

-- 2022-09-07T10:47:25.436Z
UPDATE AD_Process_Para SET ColumnName='OrgCode', Name='Org Suchschlüssel', Description=NULL, Help=NULL, AD_Element_ID=581396 WHERE UPPER(ColumnName)='ORGCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T10:47:25.440Z
UPDATE AD_Process_Para SET ColumnName='OrgCode', Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=581396 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T10:47:25.440Z
UPDATE AD_Field SET Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581396) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581396)
;

-- 2022-09-07T10:47:25.452Z
UPDATE AD_PrintFormatItem pi SET PrintName='Org Suchschlüssel', Name='Org Suchschlüssel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581396)
;

-- 2022-09-07T10:47:25.455Z
UPDATE AD_Tab SET Name='Org Suchschlüssel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581396
;

-- 2022-09-07T10:47:25.456Z
UPDATE AD_WINDOW SET Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID = 581396
;

-- 2022-09-07T10:47:25.456Z
UPDATE AD_Menu SET   Name = 'Org Suchschlüssel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581396
;

-- Element: OrgCode
-- 2022-09-07T10:47:33.180Z
UPDATE AD_Element_Trl SET Name='Org Suchschlüssel', PrintName='Org Suchschlüssel',Updated=TO_TIMESTAMP('2022-09-07 13:47:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581396 AND AD_Language='de_CH'
;

-- 2022-09-07T10:47:33.183Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581396,'de_CH') 
;

-- Element: OrgCode
-- 2022-09-07T10:47:37.566Z
UPDATE AD_Element_Trl SET Name='Org Suchschlüssel', PrintName='Org Suchschlüssel',Updated=TO_TIMESTAMP('2022-09-07 13:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581396 AND AD_Language='de_DE'
;

-- 2022-09-07T10:47:37.567Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581396,'de_DE') 
;

-- 2022-09-07T10:47:37.573Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581396,'de_DE') 
;

-- 2022-09-07T10:47:37.574Z
UPDATE AD_Column SET ColumnName='OrgCode', Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=581396
;

-- 2022-09-07T10:47:37.574Z
UPDATE AD_Process_Para SET ColumnName='OrgCode', Name='Org Suchschlüssel', Description=NULL, Help=NULL, AD_Element_ID=581396 WHERE UPPER(ColumnName)='ORGCODE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-09-07T10:47:37.575Z
UPDATE AD_Process_Para SET ColumnName='OrgCode', Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID=581396 AND IsCentrallyMaintained='Y'
;

-- 2022-09-07T10:47:37.575Z
UPDATE AD_Field SET Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581396) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581396)
;

-- 2022-09-07T10:47:37.584Z
UPDATE AD_PrintFormatItem pi SET PrintName='Org Suchschlüssel', Name='Org Suchschlüssel' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581396)
;

-- 2022-09-07T10:47:37.585Z
UPDATE AD_Tab SET Name='Org Suchschlüssel', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581396
;

-- 2022-09-07T10:47:37.585Z
UPDATE AD_WINDOW SET Name='Org Suchschlüssel', Description=NULL, Help=NULL WHERE AD_Element_ID = 581396
;

-- 2022-09-07T10:47:37.586Z
UPDATE AD_Menu SET   Name = 'Org Suchschlüssel', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581396
;
