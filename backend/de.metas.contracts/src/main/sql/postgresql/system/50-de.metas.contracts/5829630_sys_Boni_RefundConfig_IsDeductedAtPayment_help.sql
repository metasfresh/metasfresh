-- Gate: sysconfig de.metas.invoice.paymentbonus.SettlementMode (pay-full grant per instance)
-- Help note on C_Flatrate_RefundConfig.IsDeductedAtPayment (AD_Element 585516): the automatic pay-full grant is additionally
-- controlled per instance by a sysconfig. Help only, Name and Description unchanged. The element is used by this one column only.

UPDATE AD_Element_Trl SET Help='Die automatische Gutschrift bei Vollzahlung (Bonus, der gewährt wird, wenn die Rechnung vollständig bezahlt wird) muss zusätzlich pro Instanz über eine Systemkonfiguration aktiviert sein.', Updated=TO_TIMESTAMP('2026-10-17 18:00:00.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585516 AND AD_Language='de_DE'
;

/* de_DE */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585516, 'de_DE')
;

UPDATE AD_Element_Trl SET Help='Die automatische Gutschrift bei Vollzahlung (Bonus, der gewährt wird, wenn die Rechnung vollständig bezahlt wird) muss zusätzlich pro Instanz über eine Systemkonfiguration aktiviert sein.', Updated=TO_TIMESTAMP('2026-10-17 18:00:01.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585516 AND AD_Language='de_CH'
;

/* de_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585516, 'de_CH')
;

UPDATE AD_Element_Trl SET Help='Die automatische Gutschrift bei Vollzahlung (Bonus, der gewährt wird, wenn die Rechnung vollständig bezahlt wird) muss zusätzlich pro Instanz über eine Systemkonfiguration aktiviert sein.', Updated=TO_TIMESTAMP('2026-10-17 18:00:02.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585516 AND AD_Language='fr_CH' AND IsTranslated='N'
;

/* fr_CH */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585516, 'fr_CH')
;

UPDATE AD_Element_Trl SET Help='The automatic pay-full grant (bonus granted when the invoice is paid in full) must additionally be enabled per instance by a system configuration.', Updated=TO_TIMESTAMP('2026-10-17 18:00:03.000', 'YYYY-MM-DD HH24:MI:SS.MS'), UpdatedBy=100 WHERE AD_Element_ID=585516 AND AD_Language='en_US'
;

/* en_US */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585516, 'en_US')
;
