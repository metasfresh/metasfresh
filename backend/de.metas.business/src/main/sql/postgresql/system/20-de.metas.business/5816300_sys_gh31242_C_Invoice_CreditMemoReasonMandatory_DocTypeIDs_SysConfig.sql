-- SysConfig gate for the "credit-memo reason is mandatory to complete" validation
-- (de.metas.invoice.interceptor.C_Invoice). Holds a comma-separated list of C_DocType IDs; when a
-- credit memo whose (target) document type is in this list is completed, every C_InvoiceLine must
-- carry a Line_CreditMemoReason, otherwise completion is blocked.
-- This seeds the row with the safe default '' (empty = validation disabled, no behaviour change for
-- existing tenants); the per-instance value (the credit-memo doc-type IDs) is set in the customer repo
-- via set_sysconfig_value once this row exists.

INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel,
                          Created, CreatedBy, Updated, UpdatedBy,
                          EntityType, IsActive, Name, Value, Description)
VALUES (0, 0, 541839 /*From ID Server*/, 'S',
        TO_TIMESTAMP('2026-07-27 12:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-07-27 12:05:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'D', 'Y',
        'C_Invoice.CreditMemoReasonMandatory_DocTypeIDs',
        '',
        'Komma-getrennte Liste von C_DocType-IDs. Wird eine Gutschrift mit einem dieser (Ziel-)Belegtypen abgeschlossen, muss auf jeder Position ein Gutschriftsgrund (Line_CreditMemoReason) erfasst sein, sonst wird der Abschluss verhindert. Leer (Standard) = Prüfung deaktiviert.')
;
