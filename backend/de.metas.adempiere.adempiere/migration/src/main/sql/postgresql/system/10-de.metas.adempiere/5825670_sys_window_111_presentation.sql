-- Roles window (AD_Window 111) presentation sweep.
-- Every element behind a field rendered in window 111 via the AD_UI_* chain gets its
-- de_DE / de_CH / en_US AD_Element_Trl rows completed: a Description in all three languages
-- and a German name where the base language still carried English text, so the en_US caption
-- no longer merely echoes the German base name. Help text is left untouched - the WebUI does
-- not render it. Text is authored per element, so every window referencing the same element
-- benefits; the propagation functions are called once per language per element.

-- AD_Element 128 (AD_Task_ID)
UPDATE AD_Element_Trl SET Description='Process to be started outside of metasfresh.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=128 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(128, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(128, 'en_US');

-- AD_Element 275 (Description)
UPDATE AD_Element_Trl SET Description='Optionale Kurzbeschreibung des Eintrags.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Optional short description of the record.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=275 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(275, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(275, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(275, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(275, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(275, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(275, 'en_US');

-- AD_Element 287 (DocAction)
UPDATE AD_Element_Trl SET Description='The targeted status of the document.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=287 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(287, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(287, 'en_US');

-- AD_Element 406 (IsReadWrite)
UPDATE AD_Element_Trl SET Description='Field / entry / area can be read and changed.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=406 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(406, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(406, 'en_US');

-- AD_Element 469 (Name)
UPDATE AD_Element_Trl SET Description='Alphanumerische Bezeichnung des Eintrags.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=469 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Alphanumeric identifier of the entity.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=469 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(469, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(469, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(469, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(469, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(469, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(469, 'en_US');

-- AD_Element 1298 (AD_Form_ID)
UPDATE AD_Element_Trl SET Name='Spezialformular', Description='Bildschirmmaske ausserhalb der Standard-Fensterlogik.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=1298 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Special Form', Description='Screen form outside the standard window logic.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=1298 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(1298, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1298, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(1298, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1298, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(1298, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(1298, 'en_US');

-- AD_Element 50045 (Allow_Info_Account)
UPDATE AD_Element_Trl SET Name='Info Konto erlaubt', Description='Die Rolle darf das Info-Fenster Konto öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50045 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Account', Description='The role may open the Account info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50045 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50045, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50045, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50045, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50045, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50045, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50045, 'en_US');

-- AD_Element 50046 (Allow_Info_Asset)
UPDATE AD_Element_Trl SET Name='Info Anlage erlaubt', Description='Die Rolle darf das Info-Fenster Anlage öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50046 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Asset', Description='The role may open the Asset info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50046 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50046, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50046, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50046, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50046, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50046, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50046, 'en_US');

-- AD_Element 50047 (Allow_Info_BPartner)
UPDATE AD_Element_Trl SET Name='Info Geschäftspartner erlaubt', Description='Die Rolle darf das Info-Fenster Geschäftspartner öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50047 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Business Partner', Description='The role may open the Business Partner info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50047 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50047, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50047, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50047, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50047, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50047, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50047, 'en_US');

-- AD_Element 50048 (Allow_Info_CashJournal)
UPDATE AD_Element_Trl SET Name='Info Kassenbuch erlaubt', Description='Die Rolle darf das Info-Fenster Kassenbuch öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50048 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Cash Journal', Description='The role may open the Cash Journal info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50048 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50048, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50048, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50048, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50048, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50048, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50048, 'en_US');

-- AD_Element 50049 (Allow_Info_InOut)
UPDATE AD_Element_Trl SET Name='Info Lieferung erlaubt', Description='Die Rolle darf das Info-Fenster Lieferung öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50049 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Shipment', Description='The role may open the Shipment info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50049 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50049, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50049, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50049, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50049, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50049, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50049, 'en_US');

-- AD_Element 50050 (Allow_Info_Invoice)
UPDATE AD_Element_Trl SET Name='Info Rechnung erlaubt', Description='Die Rolle darf das Info-Fenster Rechnung öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:19', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50050 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Invoice', Description='The role may open the Invoice info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50050 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50050, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50050, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50050, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50050, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50050, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50050, 'en_US');

-- AD_Element 50051 (Allow_Info_Order)
UPDATE AD_Element_Trl SET Name='Info Auftrag erlaubt', Description='Die Rolle darf das Info-Fenster Auftrag öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50051 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Order', Description='The role may open the Order info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50051 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50051, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50051, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50051, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50051, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50051, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50051, 'en_US');

-- AD_Element 50052 (Allow_Info_Payment)
UPDATE AD_Element_Trl SET Name='Info Zahlung erlaubt', Description='Die Rolle darf das Info-Fenster Zahlung öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50052 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Payment', Description='The role may open the Payment info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:24', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50052 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50052, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50052, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50052, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50052, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50052, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50052, 'en_US');

-- AD_Element 50053 (Allow_Info_Product)
UPDATE AD_Element_Trl SET Name='Info Produkt erlaubt', Description='Die Rolle darf das Info-Fenster Produkt öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:25', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50053 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Product', Description='The role may open the Product info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50053 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50053, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50053, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50053, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50053, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50053, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50053, 'en_US');

-- AD_Element 50054 (Allow_Info_Resource)
UPDATE AD_Element_Trl SET Name='Info Ressource erlaubt', Description='Die Rolle darf das Info-Fenster Ressource öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:27', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50054 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Resource', Description='The role may open the Resource info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50054 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50054, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50054, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50054, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50054, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50054, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50054, 'en_US');

-- AD_Element 50055 (Allow_Info_Schedule)
UPDATE AD_Element_Trl SET Name='Info Terminplan erlaubt', Description='Die Rolle darf das Info-Fenster Terminplan öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:29', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50055 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info Schedule', Description='The role may open the Schedule info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=50055 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50055, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50055, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50055, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50055, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(50055, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(50055, 'en_US');

-- AD_Element 52024 (UserDiscount)
UPDATE AD_Element_Trl SET Name='Benutzerrabatt', Description='Rabatt in Prozent, den diese Rolle auf Belegpositionen gewähren darf.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:31', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=52024 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='User Discount', Description='Discount in percent this role may grant on document lines.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=52024 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(52024, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(52024, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(52024, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(52024, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(52024, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(52024, 'en_US');

-- AD_Element 53468 (Allow_Info_MRP)
UPDATE AD_Element_Trl SET Name='Info Bedarfsplanung erlaubt', Description='Die Rolle darf das Info-Fenster Bedarfsplanung (MRP) öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53468 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info MRP', Description='The role may open the MRP info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53468 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53468, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53468, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53468, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53468, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53468, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53468, 'en_US');

-- AD_Element 53469 (Allow_Info_CRP)
UPDATE AD_Element_Trl SET Name='Info Kapazitätsplanung erlaubt', Description='Die Rolle darf das Info-Fenster Kapazitätsplanung (CRP) öffnen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53469 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow Info CRP', Description='The role may open the CRP info window.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:36', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53469 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53469, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53469, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53469, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53469, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53469, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53469, 'en_US');

-- AD_Element 53896 (Included_Role_ID)
UPDATE AD_Element_Trl SET Name='Enthaltene Rolle', Description='Rolle, deren Berechtigungen zusätzlich für diese Rolle gelten.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53896 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Included Role', Description='Role whose permissions additionally apply to this role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:38', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53896 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53896, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53896, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53896, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53896, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(53896, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53896, 'en_US');

-- AD_Element 54151 (IsAutoRoleLogin)
UPDATE AD_Element_Trl SET Name='Rollenauswahl überspringen', Description='Die Anmeldung überspringt die Rollenauswahl und verwendet die Vorgabewerte.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:39', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54151 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Skip role login page', Description='Login skips the role selection page and uses the defaults.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:40', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54151 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54151, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54151, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54151, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54151, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54151, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54151, 'en_US');

-- AD_Element 54160 (IsPermissionGranted)
UPDATE AD_Element_Trl SET Name='Berechtigung erteilt', Description='Die angefragte Berechtigung wurde erteilt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:41', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54160 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Permission Granted', Description='The requested permission has been granted.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:42', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54160 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54160, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54160, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54160, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54160, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54160, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54160, 'en_US');

-- AD_Element 54161 (GrantPermission)
UPDATE AD_Element_Trl SET Name='Berechtigung erteilen', Description='Erteilt der Rolle die angefragte Berechtigung.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:43', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54161 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Grant Permission', Description='Grants the requested permission to the role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:44', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54161 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54161, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54161, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54161, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54161, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54161, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54161, 'en_US');

-- AD_Element 54162 (RevokePermission)
UPDATE AD_Element_Trl SET Name='Berechtigung entziehen', Description='Entzieht der Rolle die zuvor erteilte Berechtigung.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:45', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54162 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Revoke Permission', Description='Revokes the permission previously granted to the role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=54162 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54162, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54162, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54162, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54162, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(54162, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(54162, 'en_US');

-- AD_Element 55551 (IsMenuAvailable)
UPDATE AD_Element_Trl SET Name='Menü verfügbar', Description='Der Menüeintrag steht dieser Rolle zur Verfügung.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:47', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=55551 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Menu available', Description='The menu entry is available to this role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:48', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=55551 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(55551, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(55551, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(55551, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(55551, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(55551, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(55551, 'en_US');

-- AD_Element 541954 (IsOrgLoginMandatory)
UPDATE AD_Element_Trl SET Name='Organisation bei Anmeldung erforderlich', Description='Bei der Anmeldung muss zwingend eine Organisation gewählt werden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:49', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=541954 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Org Login Mandatory', Description='An organisation must be selected when logging in.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:50', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=541954 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(541954, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(541954, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(541954, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(541954, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(541954, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(541954, 'en_US');

-- AD_Element 542068 (Login_Org_ID)
UPDATE AD_Element_Trl SET Name='Anmelde-Organisation', Description='Organisation, die bei der Anmeldung mit dieser Rolle vorbelegt wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:51', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542068 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Login Org', Description='Organisation preselected when logging in with this role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:52', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542068 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542068, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542068, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542068, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542068, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542068, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542068, 'en_US');

-- AD_Element 542651 (IsAllowLoginDateOverride)
UPDATE AD_Element_Trl SET Name='Anmeldedatum änderbar', Description='Beim Anmelden darf ein vom Tagesdatum abweichendes Datum gesetzt werden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:53', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542651 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Allow changing login date', Description='A date other than the current date may be set when logging in.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:54', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542651 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542651, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542651, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542651, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542651, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542651, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542651, 'en_US');

-- AD_Element 542866 (IsRoleAlwaysUseBetaFunctions)
UPDATE AD_Element_Trl SET Description='This setting can override, for a specific role, the setting stored on the tenant.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:55', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542866 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542866, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542866, 'en_US');

-- AD_Element 542909 (IsShowAllEntityTypes)
UPDATE AD_Element_Trl SET Name='Alle Entitätstypen anzeigen', Description='Zeigt alle Entitätstypen an, auch solche, die als nicht anzuzeigen markiert sind.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542909 AND AD_Language IN ('de_DE','de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542909, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542909, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542909, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542909, 'de_CH');

-- AD_Element 543343 (Root_Menu_ID)
UPDATE AD_Element_Trl SET Name='Wurzel-Menüknoten', Description='Menüknoten, ab dem das Menü für diese Rolle aufgebaut wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:57', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=543343 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Root menu node', Description='Menu node from which the menu is built for this role.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:58', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=543343 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543343, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543343, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543343, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543343, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543343, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543343, 'en_US');

-- AD_Element 543357 (WEBUI_Role)
UPDATE AD_Element_Trl SET Name='WebUI-Rolle', Description='Die Rolle kann sich an der Weboberfläche anmelden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:00:59', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=543357 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Is webui role', Description='The role can log in to the web user interface.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=543357 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543357, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543357, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543357, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543357, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(543357, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(543357, 'en_US');

-- AD_Element 576249 (Access)
UPDATE AD_Element_Trl SET Name='Zugriff', Description='Art des gewährten Zugriffs auf den Datensatz.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=576249 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Access', Description='Type of access granted to the record.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=576249 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576249, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576249, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576249, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576249, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576249, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576249, 'en_US');

-- AD_Element 576782 (IsAttachmentDeletionAllowed)
UPDATE AD_Element_Trl SET Description='Die Rolle darf angehängte Dateien löschen.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=576782 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='The role may delete attached files.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=576782 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576782, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576782, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576782, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576782, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(576782, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(576782, 'en_US');

-- AD_Element 583308 (Mobile_Application_ID)
UPDATE AD_Element_Trl SET Name='Mobile Anwendung', Description='Anwendung der mobilen Oberfläche.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583308 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Mobile Application', Description='Application of the mobile user interface.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583308 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583308, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583308, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583308, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583308, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(583308, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583308, 'en_US');

-- AD_Element 584031 (Mobile_Application_Action_ID)
UPDATE AD_Element_Trl SET Name='Aktion der mobilen Anwendung', Description='Einzelne Aktion innerhalb einer mobilen Anwendung.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584031 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Mobile Application Action', Description='Single action within a mobile application.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584031 AND AD_Language='en_US';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584031, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584031, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584031, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584031, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(584031, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584031, 'en_US');

-- AD_Element 405 (IsReadOnly): correct the German spelling error 'Berecih' -> 'Bereich'.
UPDATE AD_Element_Trl SET Description='Feld / Eintrag / Bereich ist schreibgeschützt', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=405 AND AD_Language IN ('de_DE','de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(405, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(405, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(405, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(405, 'de_CH');

-- AD_Element 2079 (IsExclude): Swiss orthography drops the eszett - 'Ausschluß' -> 'Ausschluss' in de_CH only.
UPDATE AD_Element_Trl SET Name='Ausschluss', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 09:01:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2079 AND AD_Language='de_CH';
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(2079, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2079, 'de_CH');
