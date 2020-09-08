-- 2020-08-10T08:23:57.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Netto (Mit Freigabe)', PrintName='Netto (Mit Freigabe)',Updated=TO_TIMESTAMP('2020-08-10 11:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578019
;

-- 2020-08-10T08:23:57.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetIsApprovedForInvoicing', Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578019
;

-- 2020-08-10T08:23:57.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsApprovedForInvoicing', Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL, AD_Element_ID=578019 WHERE UPPER(ColumnName)='NETISAPPROVEDFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-08-10T08:23:57.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsApprovedForInvoicing', Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578019 AND IsCentrallyMaintained='Y'
;

-- 2020-08-10T08:23:57.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578019) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578019)
;

-- 2020-08-10T08:23:57.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Netto (Mit Freigabe)', Name='Netto (Mit Freigabe)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578019)
;

-- 2020-08-10T08:23:57.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578019
;

-- 2020-08-10T08:23:57.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Netto (Mit Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID = 578019
;

-- 2020-08-10T08:23:57.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Netto (Mit Freigabe)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578019
;

-- 2020-08-10T08:24:46.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='IsGoods', PrintName='IsGoods',Updated=TO_TIMESTAMP('2020-08-10 11:24:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578020
;

-- 2020-08-10T08:24:46.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsGoods', Name='IsGoods', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID=578020
;

-- 2020-08-10T08:24:46.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='IsGoods', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").', AD_Element_ID=578020 WHERE UPPER(ColumnName)='ISGOODS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-08-10T08:24:46.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='IsGoods', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID=578020 AND IsCentrallyMaintained='Y'
;

-- 2020-08-10T08:24:46.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='IsGoods', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578020) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578020)
;

-- 2020-08-10T08:24:46.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='IsGoods', Name='IsGoods' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578020)
;

-- 2020-08-10T08:24:46.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='IsGoods', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").', CommitWarning = NULL WHERE AD_Element_ID = 578020
;

-- 2020-08-10T08:24:46.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='IsGoods', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID = 578020
;

-- 2020-08-10T08:24:46.201Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'IsGoods', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578020
;

-- 2020-08-10T08:24:50.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Ware', PrintName='Ware',Updated=TO_TIMESTAMP('2020-08-10 11:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578020
;

-- 2020-08-10T08:24:50.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID=578020
;

-- 2020-08-10T08:24:50.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").', AD_Element_ID=578020 WHERE UPPER(ColumnName)='ISGOODS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-08-10T08:24:50.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsGoods', Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID=578020 AND IsCentrallyMaintained='Y'
;

-- 2020-08-10T08:24:50.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578020) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578020)
;

-- 2020-08-10T08:24:50.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Ware', Name='Ware' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578020)
;

-- 2020-08-10T08:24:50.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").', CommitWarning = NULL WHERE AD_Element_ID = 578020
;

-- 2020-08-10T08:24:50.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Ware', Description=NULL, Help='"IsGoods" is the opposite of "IsTradingUnit". If a ProductCategory is "Goods" ("Ware"), then it is not a Trading Unit ("Gebinde").' WHERE AD_Element_ID = 578020
;

-- 2020-08-10T08:24:50.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Ware', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578020
;

-- 2020-08-10T08:25:26.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='NetIsNotApprovedForInvoicing', PrintName='NetIsNotApprovedForInvoicing',Updated=TO_TIMESTAMP('2020-08-10 11:25:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018
;

-- 2020-08-10T08:25:26.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID=578018
;

-- 2020-08-10T08:25:26.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL, AD_Element_ID=578018 WHERE UPPER(ColumnName)='NETISNOTAPPROVEDFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-08-10T08:25:26.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID=578018 AND IsCentrallyMaintained='Y'
;

-- 2020-08-10T08:25:26.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578018) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578018)
;

-- 2020-08-10T08:25:26.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='NetIsNotApprovedForInvoicing', Name='NetIsNotApprovedForInvoicing' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578018)
;

-- 2020-08-10T08:25:26.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578018
;

-- 2020-08-10T08:25:26.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='NetIsNotApprovedForInvoicing', Description=NULL, Help=NULL WHERE AD_Element_ID = 578018
;

-- 2020-08-10T08:25:26.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'NetIsNotApprovedForInvoicing', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578018
;

-- 2020-08-10T08:25:30.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Netto (Ohne Freigabe)', PrintName='Netto (Ohne Freigabe)',Updated=TO_TIMESTAMP('2020-08-10 11:25:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578018
;

-- 2020-08-10T08:25:30.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='NetIsNotApprovedForInvoicing', Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578018
;

-- 2020-08-10T08:25:30.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL, AD_Element_ID=578018 WHERE UPPER(ColumnName)='NETISNOTAPPROVEDFORINVOICING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-08-10T08:25:30.895Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='NetIsNotApprovedForInvoicing', Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID=578018 AND IsCentrallyMaintained='Y'
;

-- 2020-08-10T08:25:30.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578018) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578018)
;

-- 2020-08-10T08:25:30.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Netto (Ohne Freigabe)', Name='Netto (Ohne Freigabe)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578018)
;

-- 2020-08-10T08:25:30.903Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578018
;

-- 2020-08-10T08:25:30.905Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Netto (Ohne Freigabe)', Description=NULL, Help=NULL WHERE AD_Element_ID = 578018
;

-- 2020-08-10T08:25:30.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Netto (Ohne Freigabe)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578018
;

