-- German labels for the windows POS-Terminal (AD_Window 338) and POS Cash Journal (AD_Window 541819): several fields,
-- the journal window and tabs, and the values of the list C_POS_JournalLine_Type (AD_Reference 541892) were shown in English
-- in the German UI. The base language is de_DE, so the base records get the German text; en_US keeps the English text and
-- is marked as translated. Untranslated rows (IsTranslated='N', e.g. fr_CH) follow the German base text.
--
-- | Record                                                   | en_US                   | de_DE / de_CH                      |
-- |----------------------------------------------------------|-------------------------|------------------------------------|
-- | AD_Element 583272                                        | POS Cash Journal        | POS-Kassenjournal                  |
-- | AD_Element 583277                                        | POS Cash Journal Line   | POS-Kassenjournalzeile             |
-- | AD_Element 583278                                        | Cash Last Balance       | Letzter Kassenbestand              |
-- | AD_Element 583305                                        | Payment Processor       | Zahlungsdienstleister              |
-- | AD_Element 583297                                        | SumUp Configuration     | SumUp-Konfiguration                |
-- | AD_Element 54141                                         | Auto Logout Delay       | Verzögerung automatische Abmeldung |
-- | AD_Element 2584                                          | Modify Price            | Preis änderbar                     |
-- | AD_Ref_List 543721 (list 541892)                        | Cash Payment            | Barzahlung                         |
-- | AD_Ref_List 543722 (list 541892)                        | Card Payment            | Kartenzahlung                      |
-- | AD_Ref_List 543723 (list 541892)                        | Cash In/Out             | Bareinlage/-entnahme               |
-- | AD_Ref_List 543724 (list 541892)                        | Cash Closing Difference | Schlussdifferenz                   |
-- AD_Element 583272 also names window 541819, its tab 547594 and menu 542174; 583277 names tab 547595;
-- 583297 also names the window SumUp Configuration (AD_Window 541823). All follow via the propagation functions.

-- 2026-11-20T12:00:00
UPDATE AD_Element_Trl SET Name='POS Cash Journal',PrintName='POS Cash Journal',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583272 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:01
UPDATE AD_Element_Trl SET Name='POS-Kassenjournal',PrintName='POS-Kassenjournal',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583272 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:02
UPDATE AD_Element_Trl SET Name='POS-Kassenjournal',PrintName='POS-Kassenjournal',Updated=TO_TIMESTAMP('2026-11-20 12:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583272 AND IsTranslated='N'
;

-- 2026-11-20T12:00:03
/* DDL */  select update_ad_element_on_ad_element_trl_update(583272,'de_DE')
;

-- 2026-11-20T12:00:04
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583272)
;

-- 2026-11-20T12:00:05
UPDATE AD_Element_Trl SET Name='POS Cash Journal Line',PrintName='POS Cash Journal Line',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583277 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:06
UPDATE AD_Element_Trl SET Name='POS-Kassenjournalzeile',PrintName='POS-Kassenjournalzeile',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583277 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:07
UPDATE AD_Element_Trl SET Name='POS-Kassenjournalzeile',PrintName='POS-Kassenjournalzeile',Updated=TO_TIMESTAMP('2026-11-20 12:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583277 AND IsTranslated='N'
;

-- 2026-11-20T12:00:08
/* DDL */  select update_ad_element_on_ad_element_trl_update(583277,'de_DE')
;

-- 2026-11-20T12:00:09
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583277)
;

-- 2026-11-20T12:00:10
UPDATE AD_Element_Trl SET Name='Cash Last Balance',PrintName='Cash Last Balance',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583278 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:11
UPDATE AD_Element_Trl SET Name='Letzter Kassenbestand',PrintName='Letzter Kassenbestand',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583278 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:12
UPDATE AD_Element_Trl SET Name='Letzter Kassenbestand',PrintName='Letzter Kassenbestand',Updated=TO_TIMESTAMP('2026-11-20 12:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583278 AND IsTranslated='N'
;

-- 2026-11-20T12:00:13
/* DDL */  select update_ad_element_on_ad_element_trl_update(583278,'de_DE')
;

-- 2026-11-20T12:00:14
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583278)
;

-- 2026-11-20T12:00:15
UPDATE AD_Element_Trl SET Name='Payment Processor',PrintName='Payment Processor',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583305 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:16
UPDATE AD_Element_Trl SET Name='Zahlungsdienstleister',PrintName='Zahlungsdienstleister',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583305 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:17
UPDATE AD_Element_Trl SET Name='Zahlungsdienstleister',PrintName='Zahlungsdienstleister',Updated=TO_TIMESTAMP('2026-11-20 12:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583305 AND IsTranslated='N'
;

-- 2026-11-20T12:00:18
/* DDL */  select update_ad_element_on_ad_element_trl_update(583305,'de_DE')
;

-- 2026-11-20T12:00:19
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583305)
;

-- 2026-11-20T12:00:20
UPDATE AD_Element_Trl SET Name='SumUp Configuration',PrintName='SumUp Configuration',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583297 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:21
UPDATE AD_Element_Trl SET Name='SumUp-Konfiguration',PrintName='SumUp-Konfiguration',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583297 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:22
UPDATE AD_Element_Trl SET Name='SumUp-Konfiguration',PrintName='SumUp-Konfiguration',Updated=TO_TIMESTAMP('2026-11-20 12:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583297 AND IsTranslated='N'
;

-- 2026-11-20T12:00:23
/* DDL */  select update_ad_element_on_ad_element_trl_update(583297,'de_DE')
;

-- 2026-11-20T12:00:24
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583297)
;

-- 2026-11-20T12:00:25
UPDATE AD_Element_Trl SET Name='Auto Logout Delay',PrintName='Auto Logout Delay',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54141 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:26
UPDATE AD_Element_Trl SET Name='Verzögerung automatische Abmeldung',PrintName='Verzögerung automatische Abmeldung',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54141 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:27
UPDATE AD_Element_Trl SET Name='Verzögerung automatische Abmeldung',PrintName='Verzögerung automatische Abmeldung',Updated=TO_TIMESTAMP('2026-11-20 12:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54141 AND IsTranslated='N'
;

-- 2026-11-20T12:00:28
/* DDL */  select update_ad_element_on_ad_element_trl_update(54141,'de_DE')
;

-- 2026-11-20T12:00:29
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54141)
;

-- 2026-11-20T12:00:30
UPDATE AD_Element_Trl SET Name='Modify Price',PrintName='Modify Price',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2584 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:31
UPDATE AD_Element_Trl SET Name='Preis änderbar',PrintName='Preis änderbar',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2584 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:32
UPDATE AD_Element_Trl SET Name='Preis änderbar',PrintName='Preis änderbar',Updated=TO_TIMESTAMP('2026-11-20 12:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2584 AND IsTranslated='N'
;

-- 2026-11-20T12:00:33
/* DDL */  select update_ad_element_on_ad_element_trl_update(2584,'de_DE')
;

-- 2026-11-20T12:00:34
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2584)
;

-- 2026-11-20T12:00:35
UPDATE AD_Ref_List SET Name='Barzahlung',Updated=TO_TIMESTAMP('2026-11-20 12:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543721
;

-- 2026-11-20T12:00:36
UPDATE AD_Ref_List_Trl SET Name='Cash Payment',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543721 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:37
UPDATE AD_Ref_List_Trl SET Name='Barzahlung',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543721 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:38
UPDATE AD_Ref_List_Trl SET Name='Barzahlung',Updated=TO_TIMESTAMP('2026-11-20 12:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543721 AND IsTranslated='N'
;

-- 2026-11-20T12:00:39
UPDATE AD_Ref_List SET Name='Kartenzahlung',Updated=TO_TIMESTAMP('2026-11-20 12:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543722
;

-- 2026-11-20T12:00:40
UPDATE AD_Ref_List_Trl SET Name='Card Payment',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543722 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:41
UPDATE AD_Ref_List_Trl SET Name='Kartenzahlung',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543722 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:42
UPDATE AD_Ref_List_Trl SET Name='Kartenzahlung',Updated=TO_TIMESTAMP('2026-11-20 12:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543722 AND IsTranslated='N'
;

-- 2026-11-20T12:00:43
UPDATE AD_Ref_List SET Name='Bareinlage/-entnahme',Updated=TO_TIMESTAMP('2026-11-20 12:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543723
;

-- 2026-11-20T12:00:44
UPDATE AD_Ref_List_Trl SET Name='Cash In/Out',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543723 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:45
UPDATE AD_Ref_List_Trl SET Name='Bareinlage/-entnahme',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543723 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:46
UPDATE AD_Ref_List_Trl SET Name='Bareinlage/-entnahme',Updated=TO_TIMESTAMP('2026-11-20 12:00:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543723 AND IsTranslated='N'
;

-- 2026-11-20T12:00:47
UPDATE AD_Ref_List SET Name='Schlussdifferenz',Updated=TO_TIMESTAMP('2026-11-20 12:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543724
;

-- 2026-11-20T12:00:48
UPDATE AD_Ref_List_Trl SET Name='Cash Closing Difference',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543724 AND AD_Language='en_US'
;

-- 2026-11-20T12:00:49
UPDATE AD_Ref_List_Trl SET Name='Schlussdifferenz',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543724 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:00:50
UPDATE AD_Ref_List_Trl SET Name='Schlussdifferenz',Updated=TO_TIMESTAMP('2026-11-20 12:00:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543724 AND IsTranslated='N'
;

-- German tooltip (Description) and help of the POS-specific fields Modify Price, Auto Logout Delay and POS-Terminal, and of
-- the tab POS-Terminal (AD_Element 573227). en_US keeps its text (already IsTranslated='Y'). Description and help of the
-- general fields Nr., Drucker and Auftrags-Belegart are core texts used in many windows and are not changed here.

-- 2026-11-20T12:01:00
UPDATE AD_Element_Trl SET Description='Erlaubt, den Preis zu ändern',Help='Erlaubt, den Preis von Produkten mit einem Preis ungleich null zu ändern',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2584 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:01:01
UPDATE AD_Element_Trl SET Description='Erlaubt, den Preis zu ändern',Help='Erlaubt, den Preis von Produkten mit einem Preis ungleich null zu ändern',Updated=TO_TIMESTAMP('2026-11-20 12:01:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2584 AND IsTranslated='N'
;

-- 2026-11-20T12:01:02
/* DDL */  select update_ad_element_on_ad_element_trl_update(2584,'de_DE')
;

-- 2026-11-20T12:01:03
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2584)
;

-- 2026-11-20T12:01:04
UPDATE AD_Element_Trl SET Description='Automatische Abmeldung, wenn das Terminal so lange nicht benutzt wird',Help='In Sekunden; 0 bedeutet keine automatische Abmeldung',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54141 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:01:05
UPDATE AD_Element_Trl SET Description='Automatische Abmeldung, wenn das Terminal so lange nicht benutzt wird',Help='In Sekunden; 0 bedeutet keine automatische Abmeldung',Updated=TO_TIMESTAMP('2026-11-20 12:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=54141 AND IsTranslated='N'
;

-- 2026-11-20T12:01:06
/* DDL */  select update_ad_element_on_ad_element_trl_update(54141,'de_DE')
;

-- 2026-11-20T12:01:07
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54141)
;

-- 2026-11-20T12:01:08
UPDATE AD_Element_Trl SET Description='Kassenterminal (Point of Sale)',Help='"POS-Terminal" definiert Standardwerte und Funktionen für das POS-Fenster.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2581 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:01:09
UPDATE AD_Element_Trl SET Description='Kassenterminal (Point of Sale)',Help='"POS-Terminal" definiert Standardwerte und Funktionen für das POS-Fenster.',Updated=TO_TIMESTAMP('2026-11-20 12:01:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2581 AND IsTranslated='N'
;

-- 2026-11-20T12:01:10
/* DDL */  select update_ad_element_on_ad_element_trl_update(2581,'de_DE')
;

-- 2026-11-20T12:01:11
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2581)
;

-- 2026-11-20T12:01:12
UPDATE AD_Element_Trl SET Description='Kassenterminal (Point of Sale)',Help='"POS-Terminal" definiert Standardwerte und Funktionen für das POS-Fenster.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-20 12:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573227 AND AD_Language IN ('de_DE','de_CH')
;

-- 2026-11-20T12:01:13
UPDATE AD_Element_Trl SET Description='Kassenterminal (Point of Sale)',Help='"POS-Terminal" definiert Standardwerte und Funktionen für das POS-Fenster.',Updated=TO_TIMESTAMP('2026-11-20 12:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573227 AND IsTranslated='N'
;

-- 2026-11-20T12:01:14
/* DDL */  select update_ad_element_on_ad_element_trl_update(573227,'de_DE')
;

-- 2026-11-20T12:01:15
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(573227)
;
