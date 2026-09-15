/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2026-02-04T10:22:03.034Z
-- de.metas.edi.model.validator.main
DELETE FROM AD_ModelValidator WHERE AD_ModelValidator_ID=540086
;


-- 2026-02-04T12:22:03.034Z
-- invoicing-agg-per-poreference
-- Column: isEdiEnabled
DELETE FROM c_aggregationitem WHERE c_aggregationitem_id=540021
;

-- 2026-02-04T12:22:03.034Z
-- invoicing-agg-std
-- Column: isEdiEnabled
DELETE FROM c_aggregationitem WHERE c_aggregationitem_id=540033
;




/*
  | tablename | ad_column_id | columnname |
  | :--- | :--- | :--- |
  | C_Order | 552598 | IsEdiEnabled |
  | C_Invoice | 548484 | IsEdiEnabled |
  | M_InOut | 549872 | IsEdiEnabled |
  | C_Invoice_Candidate | 552599 | IsEdiEnabled |
  | AD_InputDataSource | 554101 | IsEdiEnabled |
  | C_Doc_Outbound_Log | 551507 | IsEdiEnabled |
  | C_Invoice_Candidate | 552564 | IsEdiInvoicRecipient |
 */

CREATE TABLE fix.edi_columns_to_delete_20260204 AS
SELECT tablename, ad_column_id, columnname
FROM ad_table t
JOIN ad_column c ON t.ad_table_id = c.ad_table_id AND columnname = 'IsEdiEnabled'

UNION ALL

SELECT tablename, ad_column_id, columnname
FROM ad_table t
JOIN ad_column c ON t.ad_table_id = c.ad_table_id AND columnname = 'IsEdiInvoicRecipient'
WHERE c.ad_table_id = get_table_id('C_Invoice_Candidate')
;

SELECT backup_table('ad_ui_element');
DELETE FROM ad_ui_element
WHERE ad_field_id IN (SELECT ad_field_id
                      FROM ad_field
                      WHERE ad_column_id IN (SELECT ad_column_id FROM fix.edi_columns_to_delete_20260204))
;

SELECT backup_table('ad_field');
DELETE FROM ad_field
WHERE ad_column_id IN (SELECT ad_column_id FROM fix.edi_columns_to_delete_20260204)
;

SELECT backup_table('ad_column');
DELETE FROM ad_column
WHERE ad_column_id IN (SELECT ad_column_id FROM fix.edi_columns_to_delete_20260204)
;

ALTER TABLE C_Order DROP COLUMN IF EXISTS IsEdiEnabled
;


SELECT backup_table('C_Invoice');
UPDATE C_Invoice
SET edi_exportstatus = 'N',
    updated = '2026-02-04 17:30',
    updatedby = 99
WHERE IsEdiEnabled = 'N'
  AND edi_exportstatus <> 'N'
  AND docstatus IN ('CO', 'CL')
;

ALTER TABLE C_Invoice DROP COLUMN IF EXISTS IsEdiEnabled
;

SELECT backup_table('M_InOut');
UPDATE M_InOut
SET edi_exportstatus = 'N',
    updated = '2026-02-04 17:30',
    updatedby = 99
WHERE IsEdiEnabled = 'N'
  AND edi_exportstatus <> 'N'
  AND docstatus IN ('CO', 'CL')
;

ALTER TABLE M_InOut DROP COLUMN IF EXISTS IsEdiEnabled
;

ALTER TABLE C_Invoice_Candidate DROP COLUMN IF EXISTS IsEdiEnabled
;

ALTER TABLE AD_InputDataSource DROP COLUMN IF EXISTS IsEdiEnabled
;

SELECT backup_table('C_Doc_Outbound_Log');
UPDATE C_Doc_Outbound_Log dol
SET edi_exportstatus = 'N',
    updated = '2026-02-04 17:30',
    updatedby = 99
FROM c_invoice i
WHERE dol.ad_table_id = get_table_id('C_Invoice')
AND i.c_invoice_id = dol.record_id
AND i.edi_exportstatus <> dol.edi_exportstatus
  AND i.docstatus IN ('CO', 'CL')
;

ALTER TABLE C_Doc_Outbound_Log DROP COLUMN IF EXISTS IsEdiEnabled
;

ALTER TABLE C_Invoice_Candidate DROP COLUMN IF EXISTS IsEdiInvoicRecipient
;
