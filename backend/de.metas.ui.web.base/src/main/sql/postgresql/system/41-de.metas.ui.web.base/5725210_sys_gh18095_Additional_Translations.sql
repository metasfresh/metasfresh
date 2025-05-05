-- Run mode: SWING_CLIENT

-- Process: WEBUI_PP_Order_IssueReceipt_BarcodeLauncher(de.metas.ui.web.pporder.process.WEBUI_PP_Order_IssueReceipt_BarcodeLauncher)
-- 2024-05-31T08:14:55.808Z
UPDATE AD_Process_Trl SET Name='Produzieren (barcode)',Updated=TO_TIMESTAMP('2024-05-31 11:14:55.807','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540788
;

-- Process: WEBUI_PP_Order_IssueReceipt_Launcher(de.metas.ui.web.pporder.process.WEBUI_PP_Order_IssueReceipt_Launcher)
-- 2024-05-31T08:15:19.891Z
UPDATE AD_Process_Trl SET Name='Produzieren',Updated=TO_TIMESTAMP('2024-05-31 11:15:19.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540772
;

-- Run mode: SWING_CLIENT

-- Element: null
-- 2024-05-31T09:08:30.821Z
UPDATE AD_Element_Trl SET Description='Scrap Quantity for this component', Help='Scrap Quantity for this component',Updated=TO_TIMESTAMP('2024-05-31 12:08:30.821','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1000174 AND AD_Language='en_US'
;

-- 2024-05-31T09:08:30.863Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000174,'en_US')
;

-- Element: null
-- 2024-05-31T09:09:03.517Z
UPDATE AD_Element_Trl SET Description='Ausschußmenge für diese Komponente', Help='Ausschußmenge für diese Komponente', Name='Schrott %', PrintName='Schrott %',Updated=TO_TIMESTAMP('2024-05-31 12:09:03.517','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1000174 AND AD_Language='de_DE'
;

-- 2024-05-31T09:09:03.521Z
UPDATE AD_Element SET Description='Ausschußmenge für diese Komponente', Help='Ausschußmenge für diese Komponente', Name='Schrott %', PrintName='Schrott %' WHERE AD_Element_ID=1000174
;

-- 2024-05-31T09:09:04.165Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(1000174,'de_DE')
;

-- 2024-05-31T09:09:04.167Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000174,'de_DE')
;

-- Element: null
-- 2024-05-31T09:09:12.671Z
UPDATE AD_Element_Trl SET Description='Ausschußmenge für diese Komponente', Help='Ausschußmenge für diese Komponente', Name='Schrott %', PrintName='Schrott %',Updated=TO_TIMESTAMP('2024-05-31 12:09:12.671','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=1000174 AND AD_Language='de_CH'
;

-- 2024-05-31T09:09:12.673Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1000174,'de_CH')
;

-- Element: FloatBefored
-- 2024-05-31T09:10:06.458Z
UPDATE AD_Element_Trl SET Name='Float before', PrintName='Float before',Updated=TO_TIMESTAMP('2024-05-31 12:10:06.457','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53301 AND AD_Language='en_GB'
;

-- 2024-05-31T09:10:06.461Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53301,'en_GB')
;

-- Element: FloatBefored
-- 2024-05-31T09:10:13.859Z
UPDATE AD_Element_Trl SET Name='Vorgriffszeit', PrintName='Vorgriffszeit',Updated=TO_TIMESTAMP('2024-05-31 12:10:13.859','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53301 AND AD_Language='de_CH'
;

-- 2024-05-31T09:10:13.862Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53301,'de_CH')
;

-- Element: FloatBefored
-- 2024-05-31T09:10:20.695Z
UPDATE AD_Element_Trl SET Name='Float before', PrintName='Float before',Updated=TO_TIMESTAMP('2024-05-31 12:10:20.695','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53301 AND AD_Language='en_US'
;

-- 2024-05-31T09:10:20.697Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53301,'en_US')
;

-- Element: FloatBefored
-- 2024-05-31T09:10:26.384Z
UPDATE AD_Element_Trl SET Name='Vorgriffszeit', PrintName='Vorgriffszeit',Updated=TO_TIMESTAMP('2024-05-31 12:10:26.384','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53301 AND AD_Language='de_DE'
;

-- 2024-05-31T09:10:26.385Z
UPDATE AD_Element SET Name='Vorgriffszeit', PrintName='Vorgriffszeit' WHERE AD_Element_ID=53301
;

-- 2024-05-31T09:10:27.251Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53301,'de_DE')
;

-- 2024-05-31T09:10:27.253Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53301,'de_DE')
;

-- Element: FloatBefored
-- 2024-05-31T09:10:34.285Z
UPDATE AD_Element_Trl SET Name='Float before', PrintName='Float before',Updated=TO_TIMESTAMP('2024-05-31 12:10:34.285','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53301 AND AD_Language='it_IT'
;

-- 2024-05-31T09:10:34.289Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53301,'it_IT')
;

-- Element: FloatAfter
-- 2024-05-31T09:10:59.717Z
UPDATE AD_Element_Trl SET Name='Float after', PrintName='Float after',Updated=TO_TIMESTAMP('2024-05-31 12:10:59.717','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53300 AND AD_Language='en_GB'
;

-- 2024-05-31T09:10:59.720Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53300,'en_GB')
;

-- Element: FloatAfter
-- 2024-05-31T09:11:05.299Z
UPDATE AD_Element_Trl SET Name='Sicherheitszeit', PrintName='Sicherheitszeit',Updated=TO_TIMESTAMP('2024-05-31 12:11:05.299','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53300 AND AD_Language='de_CH'
;

-- 2024-05-31T09:11:05.305Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53300,'de_CH')
;

-- Element: FloatAfter
-- 2024-05-31T09:11:10.987Z
UPDATE AD_Element_Trl SET Name='Float after', PrintName='Float after',Updated=TO_TIMESTAMP('2024-05-31 12:11:10.987','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53300 AND AD_Language='en_US'
;

-- 2024-05-31T09:11:10.991Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53300,'en_US')
;

-- Element: FloatAfter
-- 2024-05-31T09:11:16.134Z
UPDATE AD_Element_Trl SET Name='Sicherheitszeit', PrintName='Sicherheitszeit',Updated=TO_TIMESTAMP('2024-05-31 12:11:16.134','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53300 AND AD_Language='de_DE'
;

-- 2024-05-31T09:11:16.135Z
UPDATE AD_Element SET Name='Sicherheitszeit', PrintName='Sicherheitszeit' WHERE AD_Element_ID=53300
;

-- 2024-05-31T09:11:16.823Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53300,'de_DE')
;

-- 2024-05-31T09:11:16.824Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53300,'de_DE')
;

-- Element: FloatAfter
-- 2024-05-31T09:11:21.570Z
UPDATE AD_Element_Trl SET Name='Float after', PrintName='Float after',Updated=TO_TIMESTAMP('2024-05-31 12:11:21.57','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53300 AND AD_Language='it_IT'
;

-- 2024-05-31T09:11:21.572Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53300,'it_IT')
;

-- Run mode: SWING_CLIENT

-- Element: CreditLimitIndicator
-- 2024-05-31T09:20:16.546Z
UPDATE AD_Element_Trl SET Description='Prozentuale Ausnutzung des Kredits aus dem Limit', Name='Kreditlimit-Indikator %', PrintName='Kreditlimit-Indikator %',Updated=TO_TIMESTAMP('2024-05-31 12:20:16.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543845 AND AD_Language='de_CH'
;

-- 2024-05-31T09:20:16.553Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543845,'de_CH')
;

-- Element: CreditLimitIndicator
-- 2024-05-31T09:20:26.890Z
UPDATE AD_Element_Trl SET Description='Prozentuale Ausnutzung des Kredits aus dem Limit', Name='Kreditlimit-Indikator %', PrintName='Kreditlimit-Indikator %',Updated=TO_TIMESTAMP('2024-05-31 12:20:26.89','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543845 AND AD_Language='de_DE'
;

-- 2024-05-31T09:20:26.891Z
UPDATE AD_Element SET Description='Prozentuale Ausnutzung des Kredits aus dem Limit', Name='Kreditlimit-Indikator %', PrintName='Kreditlimit-Indikator %' WHERE AD_Element_ID=543845
;

-- 2024-05-31T09:20:27.922Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543845,'de_DE')
;

-- 2024-05-31T09:20:27.924Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543845,'de_DE')
;

-- Element: CreditLimitIndicator
-- 2024-05-31T09:22:55.463Z
UPDATE AD_Element_Trl SET Name='Credit limit indicator %', PrintName='Credit limit indicator %',Updated=TO_TIMESTAMP('2024-05-31 12:22:55.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543845 AND AD_Language='en_US'
;

-- 2024-05-31T09:22:55.466Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543845,'en_US')
;

-- Run mode: WEBUI

-- 2024-05-31T10:07:12.293Z
UPDATE C_DocType_Trl SET PrintName='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:12.284','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541027
;

-- 2024-05-31T10:07:13.604Z
UPDATE C_DocType_Trl SET Name='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:13.604','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541027
;

-- 2024-05-31T10:07:23.135Z
UPDATE C_DocType_Trl SET PrintName='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:23.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541027
;

-- 2024-05-31T10:07:23.138Z
UPDATE C_DocType SET PrintName='Stückliste Version' WHERE C_DocType_ID=541027
;

-- 2024-05-31T10:07:23.889Z
UPDATE C_DocType_Trl SET Name='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:23.889','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541027
;

-- 2024-05-31T10:07:23.890Z
UPDATE C_DocType SET Name='Stückliste Version' WHERE C_DocType_ID=541027
;

-- 2024-05-31T10:07:29.089Z
UPDATE C_DocType_Trl SET PrintName='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:29.089','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541027
;

-- 2024-05-31T10:07:29.816Z
UPDATE C_DocType_Trl SET Name='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:29.816','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541027
;

-- 2024-05-31T10:07:34.238Z
UPDATE C_DocType_Trl SET PrintName='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:34.238','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND C_DocType_ID=541027
;

-- 2024-05-31T10:07:34.872Z
UPDATE C_DocType_Trl SET Name='Stückliste Version',Updated=TO_TIMESTAMP('2024-05-31 13:07:34.872','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND C_DocType_ID=541027
;

-- Run mode: SWING_CLIENT

-- Element: IssueMethod
-- 2024-05-31T10:13:00.178Z
UPDATE AD_Element_Trl SET Name='Zuteil Methode', PrintName='Zuteil Methode',Updated=TO_TIMESTAMP('2024-05-31 13:13:00.178','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=53253 AND AD_Language='de_CH'
;

-- 2024-05-31T10:13:00.186Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53253,'de_CH')
;

-- Run mode: SWING_CLIENT

-- Element: MatchSeqNo
-- 2024-05-31T10:16:36.829Z
UPDATE AD_Element_Trl SET Name='Reihenfolge bei Übereinstimmung', PrintName='Reihenfolge bei Übereinstimmung',Updated=TO_TIMESTAMP('2024-05-31 13:16:36.829','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='de_CH'
;

-- 2024-05-31T10:16:36.832Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'de_CH')
;

-- Element: MatchSeqNo
-- 2024-05-31T10:16:39.932Z
UPDATE AD_Element_Trl SET Name='Reihenfolge bei Übereinstimmung', PrintName='Reihenfolge bei Übereinstimmung',Updated=TO_TIMESTAMP('2024-05-31 13:16:39.932','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='de_DE'
;

-- 2024-05-31T10:16:39.932Z
UPDATE AD_Element SET Name='Reihenfolge bei Übereinstimmung', PrintName='Reihenfolge bei Übereinstimmung' WHERE AD_Element_ID=543277
;

-- 2024-05-31T10:16:40.478Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543277,'de_DE')
;

-- 2024-05-31T10:16:40.484Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'de_DE')
;

-- Element: MatchSeqNo
-- 2024-05-31T10:17:55.738Z
UPDATE AD_Element_Trl SET Name='Reihenfolge bei Über.', PrintName='Reihenfolge bei Über.',Updated=TO_TIMESTAMP('2024-05-31 13:17:55.738','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='de_CH'
;

-- 2024-05-31T10:17:55.741Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'de_CH')
;

-- Element: MatchSeqNo
-- 2024-05-31T10:17:58.642Z
UPDATE AD_Element_Trl SET Name='Reihenfolge bei Über.', PrintName='Reihenfolge bei Über.',Updated=TO_TIMESTAMP('2024-05-31 13:17:58.642','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='de_DE'
;

-- 2024-05-31T10:17:58.642Z
UPDATE AD_Element SET Name='Reihenfolge bei Über.', PrintName='Reihenfolge bei Über.' WHERE AD_Element_ID=543277
;

-- 2024-05-31T10:17:59.203Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543277,'de_DE')
;

-- 2024-05-31T10:17:59.204Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'de_DE')
;

-- Run mode: SWING_CLIENT

-- Reference Item: Scale price quantity from -> Q_Quantity
-- 2024-05-31T10:48:59.927Z
UPDATE AD_Ref_List_Trl SET Name='Menge',Updated=TO_TIMESTAMP('2024-05-31 13:48:59.927','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543686
;

-- Reference Item: Scale price quantity from -> Q_Quantity
-- 2024-05-31T10:49:02.149Z
UPDATE AD_Ref_List_Trl SET Name='Menge',Updated=TO_TIMESTAMP('2024-05-31 13:49:02.149','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543686
;

-- 2024-05-31T10:49:02.150Z
UPDATE AD_Ref_List SET Name='Menge' WHERE AD_Ref_List_ID=543686
;

-- Reference Item: Scale price quantity from -> Q_Quantity
-- 2024-05-31T10:49:07.274Z
UPDATE AD_Ref_List_Trl SET Name='Menge',Updated=TO_TIMESTAMP('2024-05-31 13:49:07.274','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Ref_List_ID=543686
;

-- Reference Item: Scale price quantity from -> Q_Quantity
-- 2024-05-31T10:49:09.011Z
UPDATE AD_Ref_List_Trl SET Name='Menge',Updated=TO_TIMESTAMP('2024-05-31 13:49:09.011','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543686
;

-- Run mode: SWING_CLIENT

-- Process: WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsFromCustomer(de.metas.ui.web.handlingunits.process.WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsFromCustomer)
-- 2024-05-31T11:31:21.843Z
UPDATE AD_Process_Trl SET Name='Leergut Rücknahme',Updated=TO_TIMESTAMP('2024-05-31 14:31:21.842','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540760
;

-- Process: WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsToVendor(de.metas.ui.web.handlingunits.process.WEBUI_M_ReceiptSchedule_CreateEmptiesReturnsToVendor)
-- 2024-05-31T11:32:06.517Z
UPDATE AD_Process_Trl SET Name='Leergut Ausgabe',Updated=TO_TIMESTAMP('2024-05-31 14:32:06.517','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540759
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam(de.metas.ui.web.handlingunits.process.WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam)
-- 2024-05-31T11:33:14.239Z
UPDATE AD_Process_Trl SET Name='CUs annehmen mit Menge',Updated=TO_TIMESTAMP('2024-05-31 14:33:14.239','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540765
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig)
-- 2024-05-31T11:34:02.244Z
UPDATE AD_Process_Trl SET Name='HUs annehmen',Updated=TO_TIMESTAMP('2024-05-31 14:34:02.244','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540753
;

-- Process: WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig(de.metas.ui.web.handlingunits.process.WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig)
-- 2024-05-31T11:34:05.907Z
UPDATE AD_Process_Trl SET Description='HUs annehmen mit veränderter Konfiguration.',Updated=TO_TIMESTAMP('2024-05-31 14:34:05.907','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=540753
;

