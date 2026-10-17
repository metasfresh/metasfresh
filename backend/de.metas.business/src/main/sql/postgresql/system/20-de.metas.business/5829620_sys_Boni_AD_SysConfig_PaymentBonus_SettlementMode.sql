-- Which way of settling an invoice with its payment bonus ("Abzug bei Zahlung") a payment may take.
-- Values: underpay = the customer deducts the bonus from what it pays (a short payment), a customer who pays in full is granted nothing;
--         fullpay  = a customer who pays in full is granted the bonus, a short payment that deducts the bonus is not honoured;
--         both     = both of the above.
-- Can be overridden per client and per organization. Default 'underpay' = the pay-full grant is switched off.

INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
VALUES (0, 0, 541860 /*From ID Server*/, 'O',
        TO_TIMESTAMP('2026-10-17 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-10-17 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y',
        'de.metas.invoice.paymentbonus.SettlementMode',
        'underpay',
        'Erlaubte Abwicklung des Abzugs bei Zahlung (Payment Bonus): underpay = der Kunde zieht den Bonus bei der Zahlung selbst ab (Unterzahlung), bei Zahlung in voller Höhe wird kein Bonus gutgeschrieben (Standard); fullpay = bei Zahlung in voller Höhe wird der volle Bonus gutgeschrieben, eine Unterzahlung mit Bonusabzug wird nicht akzeptiert; both = beides.')
;
