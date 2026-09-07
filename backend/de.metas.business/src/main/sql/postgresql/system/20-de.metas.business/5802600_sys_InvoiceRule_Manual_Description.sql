-- 2026-05-13T20:00:00.000Z
-- InvoiceRule Manual — Add a meaningful Description on the ref-list entry so users see in the dropdown tooltip what 'Manuell' actually means (and how it differs from 'Nach Lieferung')
UPDATE AD_Ref_List SET Description='Wie ''Nach Lieferung'', erfordert aber die ausdrückliche Freigabe des Benutzers, bevor der Kandidat fakturiert wird.',Updated=TO_TIMESTAMP('2026-05-13 20:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544228
;

-- 2026-05-13T20:00:01.000Z
-- InvoiceRule Manual — Propagate the new Description into the seed _Trl rows (matches what AD_Ref_List_Trl was seeded as 'N' = not yet translated)
UPDATE AD_Ref_List_Trl SET Description='Wie ''Nach Lieferung'', erfordert aber die ausdrückliche Freigabe des Benutzers, bevor der Kandidat fakturiert wird.',Updated=TO_TIMESTAMP('2026-05-13 20:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544228 AND AD_Language IN ('de_DE','de_CH','nl_NL')
;

-- 2026-05-13T20:00:02.000Z
-- InvoiceRule Manual — English description
UPDATE AD_Ref_List_Trl SET Description='Like ''After Delivery'', but requires express user permission before the candidate is invoiced.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 20:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544228
;

-- 2026-05-13T20:00:03.000Z
-- InvoiceRule Manual — Backfill description for any other active languages (idempotent)
SELECT add_missing_translations()
;
