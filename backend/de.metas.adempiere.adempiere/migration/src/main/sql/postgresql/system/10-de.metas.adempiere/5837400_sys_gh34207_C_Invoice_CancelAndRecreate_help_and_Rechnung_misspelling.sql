-- Reverse and reinvoice selection (C_Invoice_CancelAndRecreate): put the help text into Description and Help (DE for base/de_DE/de_CH, EN for en_US and untranslated rows).
-- Also fix the dropped-n misspelling of Rechnung (Rechung, Abrechung, ...) in the user-visible texts of the AD tables below.

UPDATE AD_Process SET Description='Die ausgewählten fertiggestellten Rechnungen werden storniert, und ihre Rechnungskandidaten werden neu fakturiert. Rechnungen ohne Rechnungskandidaten bleiben unverändert.

Wichtig: Die neuen Rechnungen erhalten die Rechnungsadresse und den Rechnungskontakt, die jetzt beim Rechnungspartner als Rechnungsadresse bzw. Rechnungskontakt markiert sind, nicht die bisherigen (außer am Rechnungskandidaten ist eine abweichende Adresse bzw. ein abweichender Kontakt fest eingetragen). Alle übrigen Daten kommen weiter aus den Rechnungskandidaten. Typischer Einsatz: Rechnungsadresse oder Rechnungskontakt im Geschäftspartner korrigieren und danach die betroffenen Rechnungen mit diesem Prozess ersetzen.

Achtung: Rechnungskandidaten mit Rechnungsstellung „Manuell“ oder mit leerem bzw. zukünftigem Datum „Abrechnung ab“ werden nicht neu fakturiert; die neue Rechnung entsteht ohne sie. Hat ein Rechnungskandidat einen Fehler oder wird keiner fakturiert, bleibt die Rechnung storniert, ohne dass eine neue Rechnung entsteht, und die Verarbeitung im Hintergrund bricht ab. Die Rechnungskandidaten daher vorher prüfen.

Rechnungen, die mit etwas anderem als einer Zahlung verrechnet sind (z. B. mit einer Gutschrift), und Rechnungen mit Provision werden übersprungen; dazu erscheint eine Meldung.', Help='Die ausgewählten fertiggestellten Rechnungen werden storniert, und ihre Rechnungskandidaten werden neu fakturiert. Rechnungen ohne Rechnungskandidaten bleiben unverändert.

Wichtig: Die neuen Rechnungen erhalten die Rechnungsadresse und den Rechnungskontakt, die jetzt beim Rechnungspartner als Rechnungsadresse bzw. Rechnungskontakt markiert sind, nicht die bisherigen (außer am Rechnungskandidaten ist eine abweichende Adresse bzw. ein abweichender Kontakt fest eingetragen). Alle übrigen Daten kommen weiter aus den Rechnungskandidaten. Typischer Einsatz: Rechnungsadresse oder Rechnungskontakt im Geschäftspartner korrigieren und danach die betroffenen Rechnungen mit diesem Prozess ersetzen.

Achtung: Rechnungskandidaten mit Rechnungsstellung „Manuell“ oder mit leerem bzw. zukünftigem Datum „Abrechnung ab“ werden nicht neu fakturiert; die neue Rechnung entsteht ohne sie. Hat ein Rechnungskandidat einen Fehler oder wird keiner fakturiert, bleibt die Rechnung storniert, ohne dass eine neue Rechnung entsteht, und die Verarbeitung im Hintergrund bricht ab. Die Rechnungskandidaten daher vorher prüfen.

Rechnungen, die mit etwas anderem als einer Zahlung verrechnet sind (z. B. mit einer Gutschrift), und Rechnungen mit Provision werden übersprungen; dazu erscheint eine Meldung.', Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_ID=584906;

UPDATE AD_Process_Trl SET Description='Die ausgewählten fertiggestellten Rechnungen werden storniert, und ihre Rechnungskandidaten werden neu fakturiert. Rechnungen ohne Rechnungskandidaten bleiben unverändert.

Wichtig: Die neuen Rechnungen erhalten die Rechnungsadresse und den Rechnungskontakt, die jetzt beim Rechnungspartner als Rechnungsadresse bzw. Rechnungskontakt markiert sind, nicht die bisherigen (außer am Rechnungskandidaten ist eine abweichende Adresse bzw. ein abweichender Kontakt fest eingetragen). Alle übrigen Daten kommen weiter aus den Rechnungskandidaten. Typischer Einsatz: Rechnungsadresse oder Rechnungskontakt im Geschäftspartner korrigieren und danach die betroffenen Rechnungen mit diesem Prozess ersetzen.

Achtung: Rechnungskandidaten mit Rechnungsstellung „Manuell“ oder mit leerem bzw. zukünftigem Datum „Abrechnung ab“ werden nicht neu fakturiert; die neue Rechnung entsteht ohne sie. Hat ein Rechnungskandidat einen Fehler oder wird keiner fakturiert, bleibt die Rechnung storniert, ohne dass eine neue Rechnung entsteht, und die Verarbeitung im Hintergrund bricht ab. Die Rechnungskandidaten daher vorher prüfen.

Rechnungen, die mit etwas anderem als einer Zahlung verrechnet sind (z. B. mit einer Gutschrift), und Rechnungen mit Provision werden übersprungen; dazu erscheint eine Meldung.', Help='Die ausgewählten fertiggestellten Rechnungen werden storniert, und ihre Rechnungskandidaten werden neu fakturiert. Rechnungen ohne Rechnungskandidaten bleiben unverändert.

Wichtig: Die neuen Rechnungen erhalten die Rechnungsadresse und den Rechnungskontakt, die jetzt beim Rechnungspartner als Rechnungsadresse bzw. Rechnungskontakt markiert sind, nicht die bisherigen (außer am Rechnungskandidaten ist eine abweichende Adresse bzw. ein abweichender Kontakt fest eingetragen). Alle übrigen Daten kommen weiter aus den Rechnungskandidaten. Typischer Einsatz: Rechnungsadresse oder Rechnungskontakt im Geschäftspartner korrigieren und danach die betroffenen Rechnungen mit diesem Prozess ersetzen.

Achtung: Rechnungskandidaten mit Rechnungsstellung „Manuell“ oder mit leerem bzw. zukünftigem Datum „Abrechnung ab“ werden nicht neu fakturiert; die neue Rechnung entsteht ohne sie. Hat ein Rechnungskandidat einen Fehler oder wird keiner fakturiert, bleibt die Rechnung storniert, ohne dass eine neue Rechnung entsteht, und die Verarbeitung im Hintergrund bricht ab. Die Rechnungskandidaten daher vorher prüfen.

Rechnungen, die mit etwas anderem als einer Zahlung verrechnet sind (z. B. mit einer Gutschrift), und Rechnungen mit Provision werden übersprungen; dazu erscheint eine Meldung.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_ID=584906 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Process_Trl SET Description='The selected completed invoices are reversed, and their invoice candidates are invoiced again. Invoices without invoice candidates stay unchanged.

Important: the new invoices get the invoice address and invoice contact that are now flagged as such for the invoice partner, not the previous ones (unless a fixed address or contact is set on the invoice candidate). All other data still comes from the invoice candidates. Typical use: correct the invoice address or contact in the business partner, then replace the affected invoices with this process.

Caution: invoice candidates with invoice rule "Manual" or with an empty or future "Date Invoice from" are not invoiced again; the new invoice is created without them. If an invoice candidate has an error or none is invoiced, the invoice stays reversed without a new invoice, and the background processing stops. So check the invoice candidates beforehand.

Invoices allocated to anything other than a payment (e.g. a credit memo) and invoices with commission are skipped; a notification is shown for each.', Help='The selected completed invoices are reversed, and their invoice candidates are invoiced again. Invoices without invoice candidates stay unchanged.

Important: the new invoices get the invoice address and invoice contact that are now flagged as such for the invoice partner, not the previous ones (unless a fixed address or contact is set on the invoice candidate). All other data still comes from the invoice candidates. Typical use: correct the invoice address or contact in the business partner, then replace the affected invoices with this process.

Caution: invoice candidates with invoice rule "Manual" or with an empty or future "Date Invoice from" are not invoiced again; the new invoice is created without them. If an invoice candidate has an error or none is invoiced, the invoice stays reversed without a new invoice, and the background processing stops. So check the invoice candidates beforehand.

Invoices allocated to anything other than a payment (e.g. a credit memo) and invoices with commission are skipped; a notification is shown for each.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_ID=584906 AND AD_Language='en_US';

UPDATE AD_Process_Trl SET Description='The selected completed invoices are reversed, and their invoice candidates are invoiced again. Invoices without invoice candidates stay unchanged.

Important: the new invoices get the invoice address and invoice contact that are now flagged as such for the invoice partner, not the previous ones (unless a fixed address or contact is set on the invoice candidate). All other data still comes from the invoice candidates. Typical use: correct the invoice address or contact in the business partner, then replace the affected invoices with this process.

Caution: invoice candidates with invoice rule "Manual" or with an empty or future "Date Invoice from" are not invoiced again; the new invoice is created without them. If an invoice candidate has an error or none is invoiced, the invoice stays reversed without a new invoice, and the background processing stops. So check the invoice candidates beforehand.

Invoices allocated to anything other than a payment (e.g. a credit memo) and invoices with commission are skipped; a notification is shown for each.', Help='The selected completed invoices are reversed, and their invoice candidates are invoiced again. Invoices without invoice candidates stay unchanged.

Important: the new invoices get the invoice address and invoice contact that are now flagged as such for the invoice partner, not the previous ones (unless a fixed address or contact is set on the invoice candidate). All other data still comes from the invoice candidates. Typical use: correct the invoice address or contact in the business partner, then replace the affected invoices with this process.

Caution: invoice candidates with invoice rule "Manual" or with an empty or future "Date Invoice from" are not invoiced again; the new invoice is created without them. If an invoice candidate has an error or none is invoiced, the invoice stays reversed without a new invoice, and the background processing stops. So check the invoice candidates beforehand.

Invoices allocated to anything other than a payment (e.g. a credit memo) and invoices with commission are skipped; a notification is shown for each.', Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Process_ID=584906 AND IsTranslated='N' AND AD_Language NOT IN ('de_DE','de_CH','en_US');

UPDATE ad_column SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_column_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_element SET
    commitwarning=regexp_replace(commitwarning, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_description=regexp_replace(po_description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_help=regexp_replace(po_help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_name=regexp_replace(po_name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_printname=regexp_replace(po_printname, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    printname=regexp_replace(printname, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namebrowse=regexp_replace(webui_namebrowse, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenew=regexp_replace(webui_namenew, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenewbreadcrumb=regexp_replace(webui_namenewbreadcrumb, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE commitwarning ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_name ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_printname ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR printname ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namebrowse ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenew ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenewbreadcrumb ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_element_trl SET
    commitwarning=regexp_replace(commitwarning, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_description=regexp_replace(po_description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_help=regexp_replace(po_help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_name=regexp_replace(po_name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    po_printname=regexp_replace(po_printname, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    printname=regexp_replace(printname, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namebrowse=regexp_replace(webui_namebrowse, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenew=regexp_replace(webui_namenew, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenewbreadcrumb=regexp_replace(webui_namenewbreadcrumb, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE commitwarning ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_name ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR po_printname ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR printname ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namebrowse ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenew ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenewbreadcrumb ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_field SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_field_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_menu SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namebrowse=regexp_replace(webui_namebrowse, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenew=regexp_replace(webui_namenew, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenewbreadcrumb=regexp_replace(webui_namenewbreadcrumb, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namebrowse ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenew ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenewbreadcrumb ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_menu_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namebrowse=regexp_replace(webui_namebrowse, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenew=regexp_replace(webui_namenew, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    webui_namenewbreadcrumb=regexp_replace(webui_namenewbreadcrumb, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namebrowse ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenew ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR webui_namenewbreadcrumb ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_message SET
    msgtext=regexp_replace(msgtext, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    msgtip=regexp_replace(msgtip, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE msgtext ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR msgtip ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_message_trl SET
    msgtext=regexp_replace(msgtext, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    msgtip=regexp_replace(msgtip, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE msgtext ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR msgtip ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_process SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_process_para SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_process_para_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_process_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_ref_list SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_ref_list_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_reference SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_reference_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_tab SET
    quickinput_openbutton_caption=regexp_replace(quickinput_openbutton_caption, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    quickinput_closebutton_caption=regexp_replace(quickinput_closebutton_caption, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    notfound_message=regexp_replace(notfound_message, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    notfound_messagedetail=regexp_replace(notfound_messagedetail, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    commitwarning=regexp_replace(commitwarning, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE quickinput_openbutton_caption ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR quickinput_closebutton_caption ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR notfound_message ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR notfound_messagedetail ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR commitwarning ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_tab_trl SET
    quickinput_openbutton_caption=regexp_replace(quickinput_openbutton_caption, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    quickinput_closebutton_caption=regexp_replace(quickinput_closebutton_caption, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    notfound_message=regexp_replace(notfound_message, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    notfound_messagedetail=regexp_replace(notfound_messagedetail, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    commitwarning=regexp_replace(commitwarning, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE quickinput_openbutton_caption ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR quickinput_closebutton_caption ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR notfound_message ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR notfound_messagedetail ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR commitwarning ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_table SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_table_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_window SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';

UPDATE ad_window_trl SET
    description=regexp_replace(description, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    help=regexp_replace(help, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    name=regexp_replace(name, '(\m|ab|[^bp])(rech)ung', '\1\2nung', 'gi'),
    Updated=TO_TIMESTAMP('2026-11-28 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE description ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR help ~* '(\mrechung|abrechung|[^bp]rechung)'
    OR name ~* '(\mrechung|abrechung|[^bp]rechung)';
