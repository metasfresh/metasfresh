-- AD_Process: C_Order_Split
-- Base language is German per metasfresh convention; en_US translation in AD_Process_Trl.
-- Note: AD_Process does NOT have an AD_Table_ID column in this codebase.
-- Process-to-table binding is handled in the AD_Table_Process migration (5804940_*.sql).

INSERT INTO AD_Process (
    AD_Process_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Value, Name, Description, Help, EntityType, Classname, AccessLevel, IsReport,
    ShowHelp
) VALUES (
    585625 /*From ID Server*/, 0, 0, 'Y', NOW(), 100, NOW(), 100,
    'C_Order_Split',
    'Auftrag aufteilen (Folgeauftrag für offene Menge)',
    'Erstellt einen Folgeauftrag mit allen Positionen, deren Liefermenge noch offen ist. Anschließend werden die Lieferdispositionen und aktiven Reservierungen des ursprünglichen Auftrags geschlossen.',
    'Wird nach Teil-Lieferung verwendet, wenn die Restmenge in einem anderen Projektkontext reserviert / beschafft werden soll. Die Fakturierungs-Kandidaten des ursprünglichen Auftrags bleiben unverändert und können später mit jenen des Folgeauftrags zusammen fakturiert werden.',
    'de.metas.order',
    'de.metas.order.split.C_Order_Split',
    '7',
    'N',
    'Y'
);

-- English translation (en_US)
INSERT INTO AD_Process_Trl (
    AD_Process_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
    Name, Description, Help, IsTranslated
) VALUES (
    585625 /*From ID Server*/, 'en_US', 0, 0, 'Y', NOW(), 100, NOW(), 100,
    'Split order (continuation order for unshipped qty)',
    'Creates a continuation sales order containing all lines that still have unshipped quantity, then closes the shipment schedules and active reservations of the original order.',
    'Use after the original order has been partially shipped and the remaining quantity needs to be re-reserved / re-sourced in a different project context. Invoice candidates on the original order are left untouched and can be invoiced together with the continuation order''s invoice candidates.',
    'Y'
);
