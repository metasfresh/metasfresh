-- Migration ID: 5806040
-- Task: EDI location routing — drop old C_BPartner EDI columns and window fields
-- The 9 EDI columns moved to C_BPartner_EDI_Setting (Task 11 copied the data).
-- SAFETY: shared AD_Element, AD_Reference 542047, and AD_Val_Rule 540768 are NOT deleted —
-- they are reused by the new C_BPartner_EDI_Setting columns.
--
-- This script also updates M_InOut_Export_EDI_DESADV_JSON_V to resolve EdiDesadvRecipientGLN
-- from C_BPartner_EDI_Setting instead of C_BPartner (which no longer has that column).
-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/desadv_json/M_InOut_Export_EDI_DESADV_JSON_V.sql

-- Step 0: Update M_InOut_Export_EDI_DESADV_JSON_V BEFORE dropping the C_BPartner columns.
-- The view previously read buyer.edidesadvrecipientgln from C_BPartner.
-- Now resolves via coalesce: exact-location C_BPartner_EDI_Setting row → partner-default row.
DROP VIEW IF EXISTS m_inout_export_edi_desadv_json_v$new;

CREATE OR REPLACE VIEW m_inout_export_edi_desadv_json_v$new AS
SELECT io.m_inout_id,
       d.edi_desadv_id AS edi_desadv_id,
       JSON_BUILD_OBJECT('metasfresh_DESADV', JSONB_BUILD_OBJECT(
               'Version', '0.2',
               'TechnicalRecipientGLN', COALESCE(edi_setting_loc.edidesadvrecipientgln, edi_setting_def.edidesadvrecipientgln),
               'TechnicalSenderGLN', (SELECT REGEXP_REPLACE(sl.gln::text, '\s+$'::text, ''::text)
                                      FROM c_bpartner_location sl
                                      WHERE sl.c_bpartner_id = org.org_bpartner_id
                                        AND sl.isremitto = 'Y'
                                        AND sl.gln IS NOT NULL
                                        AND sl.gln <> ''
                                        AND sl.isactive = 'Y'
                                      ORDER BY sl.c_bpartner_location_id
                                      LIMIT 1),
               'Parties', JSONB_BUILD_OBJECT(
                       'Supplier', COALESCE(bp_supplier.bpartner_json, '{}'::jsonb),
                       'Supplier_Location', COALESCE(bpl_supplier.bpartner_location_json, '{}'::jsonb),
                       'Buyer', COALESCE(bp_buyer.bpartner_json, '{}'::jsonb),
                       'Buyer_Location', COALESCE(bpl_buyer.bpartner_location_json, '{}'::jsonb),
                       'Invoicee', COALESCE(bp_bill.bpartner_json, '{}'::jsonb),
                       'Invoicee_Location', COALESCE(bpl_bill.bpartner_location_json, '{}'::jsonb),
                       'DeliveryParty', COALESCE(bp_handover.bpartner_json, '{}'::jsonb),
                       'DeliveryParty_Location', COALESCE(bpl_handover.bpartner_location_json, '{}'::jsonb),
                       'UltimateConsignee', COALESCE(bp_dropship.bpartner_json, '{}'::jsonb),
                       'UltimateConsignee_Location', COALESCE(bpl_dropship.bpartner_location_json, '{}'::jsonb)
               ),
               'DateOrdered', d.dateordered,
               'ShipmentDocumentNo', io.documentno,
               'EDI_Desadv_ID', d.edi_desadv_id,
               'MovementDate', io.movementdate,
               'POReference', COALESCE(d.poreference, io.poreference),
               'Packings', "de.metas.edi".get_desadv_packs_json_fn(d.edi_desadv_id, io.m_inout_id),
               'Currency', COALESCE(curr.currency_json, '{}'::jsonb),
               'InvoicableQtyBasedOn', (SELECT edl_ib.invoicableqtybasedon
                                FROM edi_desadvline edl_ib
                                WHERE edl_ib.edi_desadv_id = d.edi_desadv_id
                                  AND edl_ib.isactive = 'Y'
                                ORDER BY edl_ib.line
                                LIMIT 1),
               'DeliveryViaRule', COALESCE(d.deliveryviarule, io.deliveryviarule),
               'DesadvLineWithNoPacking', "de.metas.edi".get_desadv_lines_no_pack_json_fn(d.edi_desadv_id, io.m_inout_id),
               'M_InOut_ID', io.m_inout_id,
               'DatePromised', o.datepromised)
       ) AS embedded_json
FROM m_inout io
LEFT JOIN c_order o ON io.c_order_id = o.c_order_id
JOIN edi_desadv_m_inout link
     ON link.m_inout_id = io.m_inout_id AND link.isactive = 'Y'
JOIN edi_desadv d
     ON d.edi_desadv_id = link.edi_desadv_id
-- EDI setting: coalesce exact-location row then partner-default row for GLN resolution
LEFT JOIN c_bpartner_edi_setting edi_setting_loc
     ON edi_setting_loc.c_bpartner_id = d.c_bpartner_id
    AND edi_setting_loc.c_bpartner_location_id = d.c_bpartner_location_id
    AND edi_setting_loc.isactive = 'Y'
LEFT JOIN c_bpartner_edi_setting edi_setting_def
     ON edi_setting_def.c_bpartner_id = d.c_bpartner_id
    AND edi_setting_def.c_bpartner_location_id IS NULL
    AND edi_setting_def.isactive = 'Y'
LEFT JOIN "de.metas.edi".edi_currency_object_v curr ON curr.c_currency_id = d.c_currency_id
LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_buyer ON bp_buyer.c_bpartner_id = d.c_bpartner_id
LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_buyer ON bpl_buyer.c_bpartner_location_id = d.c_bpartner_location_id
LEFT JOIN c_bpartner_location bpl_bill_table ON bpl_bill_table.c_bpartner_location_id = d.bill_location_id
LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_bill ON bp_bill.c_bpartner_id = bpl_bill_table.c_bpartner_id
LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_bill ON bpl_bill.c_bpartner_location_id = d.bill_location_id
LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_handover ON bp_handover.c_bpartner_id = d.handover_partner_id
LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_handover ON bpl_handover.c_bpartner_location_id = d.handover_location_id
LEFT JOIN "de.metas.edi".edi_bpartner_object_v bp_dropship ON bp_dropship.c_bpartner_id = d.dropship_bpartner_id
LEFT JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_dropship ON bpl_dropship.c_bpartner_location_id = d.dropship_location_id
LEFT JOIN ad_orginfo org ON org.ad_org_id = io.ad_org_id
JOIN "de.metas.edi".edi_bpartner_object_v bp_supplier ON bp_supplier.c_bpartner_id = org.org_bpartner_id
JOIN "de.metas.edi".edi_bpartner_location_object_v bpl_supplier ON bpl_supplier.c_bpartner_location_id = org.orgbp_location_id
WHERE io.isactive = 'Y'
  AND io.docstatus IN ('CO', 'CL')
;

SELECT public.db_alter_view(
    'm_inout_export_edi_desadv_json_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('m_inout_export_edi_desadv_json_v$new'))
);

DROP VIEW IF EXISTS m_inout_export_edi_desadv_json_v$new;

-- Step 0b: Update edi_cctop_000_v — EdiInvoicRecipientGLN from C_BPartner_EDI_Setting.
-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_000_v_view.sql
DROP VIEW IF EXISTS edi_cctop_000_v$new;

CREATE OR REPLACE VIEW edi_cctop_000_v$new AS
SELECT
    l.c_bpartner_location_id AS edi_cctop_000_v_id,
    l.c_bpartner_location_id,
    REGEXP_REPLACE(
        COALESCE(edi_loc.EdiInvoicRecipientGLN, edi_def.EdiInvoicRecipientGLN),
        '\s+$', '') AS EdiInvoicRecipientGLN,
    l.ad_client_id,
    l.ad_org_id,
    l.created,
    l.createdby,
    l.updated,
    l.updatedby,
    l.isactive
FROM c_bpartner_location l
         LEFT JOIN c_bpartner_edi_setting edi_loc
              ON edi_loc.c_bpartner_id = l.c_bpartner_id
             AND edi_loc.c_bpartner_location_id = l.c_bpartner_location_id
             AND edi_loc.isactive = 'Y'
         LEFT JOIN c_bpartner_edi_setting edi_def
              ON edi_def.c_bpartner_id = l.c_bpartner_id
             AND edi_def.c_bpartner_location_id IS NULL
             AND edi_def.isactive = 'Y';

SELECT public.db_alter_view(
    'edi_cctop_000_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('edi_cctop_000_v$new'))
);

DROP VIEW IF EXISTS edi_cctop_000_v$new;

-- Step 0c: Update edi_cctop_invoic_v — EdiInvoicRecipientGLN from C_BPartner_EDI_Setting.
-- Source DDL: backend/de.metas.edi/src/main/sql/postgresql/ddl/views/edi_cctop_invoic_v_view.sql
DROP VIEW IF EXISTS edi_cctop_invoic_v$new;

CREATE OR REPLACE VIEW edi_cctop_invoic_v$new AS
SELECT i.C_Invoice_ID                                                                                       AS EDI_Cctop_INVOIC_v_ID
     , i.C_Invoice_ID
     , i.C_Order_ID
     , i.C_BPartner_ID
     , REGEXP_REPLACE(i.DocumentNo, '\s+$', '')                                                             AS Invoice_DocumentNo
     , i.DateInvoiced
     , i.DateAcct
     , (CASE
            WHEN REGEXP_REPLACE(i.POReference::TEXT, '\s+$', '') <> ''::TEXT AND i.POReference IS NOT NULL
                THEN REGEXP_REPLACE(i.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END)                                                                                                AS POReference
     , (CASE
            WHEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered) IS NOT NULL
                THEN COALESCE(i.DateOrdered, o.DateOrdered, ol.dateordered)
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                                                                                AS DateOrdered
     , dt.docbasetype
     , dt.docsubtype
     , (CASE dt.DocBaseType
            WHEN 'ARI'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = ''   THEN '380'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AQ' THEN '383'::TEXT
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'AP' THEN '84'::TEXT
                                                                                                                ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
            WHEN 'ARC'::BPChar THEN (CASE
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) IN ('CQ', 'CS') THEN '381'
                                         WHEN dt.DocSubType IS NULL OR TRIM(BOTH ' ' FROM dt.DocSubType) = 'CR'          THEN '83'
                                                                                                                         ELSE 'ERROR EAN_DocType'::TEXT
                                     END)
                               ELSE 'ERROR EAN_DocType'::TEXT
        END)                                                                                                AS EANCOM_DocType
     , i.GrandTotal
     , i.TotalLines
     , i.DocStatus
     , i.ExternalId
     , esystem.value                                                                                        AS ExternalSystemCode
     , (CASE
            WHEN dsource.internalname IS NOT NULL
                THEN 'int-' || dsource.internalname
        END)                                                                                                AS DataSource
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.MovementDate, iomd.movementdate) END AS MovementDate
     , CASE WHEN dt.DocSubType = 'CS' THEN NULL ELSE COALESCE(shipment.DocumentNo, iodn.documentno) END     AS Shipment_DocumentNo
     , taxAndSurchage.TotalVAT
     , taxAndSurchage.TotalTaxBaseAmt
     -- EdiInvoicRecipientGLN moved to C_BPartner_EDI_Setting; coalesce exact-location then partner-default
     , COALESCE(edi_setting_loc.EdiInvoicRecipientGLN, edi_setting_def.EdiInvoicRecipientGLN, rl.GLN)      AS ReceiverGLN
     , rl.C_BPartner_Location_ID
     , (SELECT DISTINCT ON (REGEXP_REPLACE(sl.GLN, '\s+$', '')) REGEXP_REPLACE(sl.GLN, '\s+$', '') AS GLN
        FROM C_BPartner_Location sl
        WHERE TRUE
          AND sl.C_BPartner_ID = sp.C_BPartner_ID
          AND sl.IsRemitTo = 'Y'::BPChar
          AND sl.GLN IS NOT NULL
          AND sl.IsActive = 'Y'::BPChar)                                                                    AS SenderGLN
     , REGEXP_REPLACE(sp.VATaxId, '\s+$', '')                                                               AS VATaxId
     , c.ISO_Code
     , REGEXP_REPLACE(i.CreditMemoReason, '\s+$', '')                                                       AS CreditMemoReason
     , (SELECT REGEXP_REPLACE(Name, '\s+$', '') AS Name
        FROM AD_Ref_List
        WHERE AD_Reference_ID = 540014
          AND Value = i.CreditMemoReason)                                                                   AS CreditMemoReasonText
     , (SELECT CASE
                   WHEN ARRAY_LENGTH(ARRAY_AGG(DISTINCT ol.invoicableqtybasedon), 1) = 1
                       THEN (ARRAY_AGG(DISTINCT ol.invoicableqtybasedon))[1]
                       ELSE NULL
               END
        FROM c_orderline ol
        WHERE ol.c_order_id = o.c_order_id)                                                                 AS invoicableqtybasedon
     , cc.CountryCode
     , cc.CountryCode_3Digit
     , cc.CountryCode                                                                                       AS AD_Language
     , i.AD_Client_ID
     , i.AD_Org_ID
     , i.Created
     , i.CreatedBy
     , i.Updated
     , i.UpdatedBy
     , i.IsActive
     , (SELECT STRING_AGG(DISTINCT edi.DocumentNo, ',')
        FROM c_invoiceline icl
                 INNER JOIN c_invoice_line_alloc inalloc ON icl.c_invoiceline_id = inalloc.c_invoiceline_id
                 INNER JOIN C_InvoiceCandidate_InOutLine candinout
                            ON inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                 INNER JOIN M_InOutLine inoutline ON candinout.m_inoutline_id = inoutline.m_inoutline_id
                 INNER JOIN m_inout inout ON inoutline.m_inout_id = inout.m_inout_id
                 INNER JOIN EDI_Desadv edi ON inout.EDI_Desadv_ID = edi.EDI_Desadv_ID
        WHERE icl.c_invoice_id = i.c_invoice_id)                                                            AS EDIDesadvDocumentNo
     , taxAndSurchage.SurchargeAmt
     , taxAndSurchage.TotalLinesWithSurchargeAmt
     , taxAndSurchage.TotalVatWithSurchargeAmt
     , taxAndSurchage.GrandTotalWithSurchargeAmt
FROM C_Invoice i
         LEFT JOIN C_DocType dt ON dt.C_DocType_ID = i.C_DocTypetarget_ID
         LEFT JOIN C_Order o ON o.C_Order_ID = i.C_Order_ID
         LEFT JOIN LATERAL ( SELECT io.DocumentNo,
                                    io.MovementDate
                             FROM M_InOut io
                                      LEFT JOIN c_invoice inv ON io.m_inout_id = inv.m_inout_id AND inv.c_invoice_id = i.c_invoice_id
                             WHERE io.C_Order_ID = o.C_Order_ID
                               AND io.DocStatus IN ('CO', 'CL')
                             ORDER BY inv.c_invoice_id NULLS LAST, io.Created
                             LIMIT 1 ) shipment ON TRUE
         -- EDI setting: coalesce exact-location row then partner-default for EdiInvoicRecipientGLN
         LEFT JOIN c_bpartner_edi_setting edi_setting_loc
              ON edi_setting_loc.c_bpartner_id = i.C_BPartner_ID
             AND edi_setting_loc.c_bpartner_location_id = i.C_BPartner_Location_ID
             AND edi_setting_loc.isactive = 'Y'
         LEFT JOIN c_bpartner_edi_setting edi_setting_def
              ON edi_setting_def.c_bpartner_id = i.C_BPartner_ID
             AND edi_setting_def.c_bpartner_location_id IS NULL
             AND edi_setting_def.isactive = 'Y'
         LEFT JOIN C_BPartner_Location rl ON rl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
         LEFT JOIN C_Location l ON l.C_Location_ID = rl.C_Location_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = i.C_Currency_ID
         LEFT JOIN C_Country cc ON cc.C_Country_ID = l.C_Country_ID
         LEFT JOIN C_BPartner sp ON sp.AD_OrgBP_ID = i.AD_Org_ID
         LEFT JOIN AD_InputDataSource dsource ON dsource.AD_InputDataSource_ID = i.AD_InputDataSource_ID
         LEFT JOIN ExternalSystem esystem ON esystem.externalsystem_id = i.externalsystem_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(o.dateordered) AS dateordered
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT ol.dateordered) = 1 ) ol ON ol.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.movementdate) AS movementdate
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.movementdate) = 1 ) iomd ON iomd.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i.c_invoice_id, MIN(io.documentno) AS documentno
                             FROM c_invoice i
                                      INNER JOIN c_invoiceline il ON i.c_invoice_id = il.c_invoice_id
                                      INNER JOIN c_orderline ol ON il.c_orderline_id = ol.c_orderline_id
                                      INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                                      INNER JOIN M_InOut io ON o.c_order_id = io.c_order_id AND io.DocStatus IN ('CO', 'CL')
                             GROUP BY i.c_invoice_id
                             HAVING COUNT(DISTINCT io.documentno) = 1 ) iodn ON iodn.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT C_Invoice_ID
                                  , SUM(TaxAmt)                                              AS TotalVAT
                                  , SUM(TaxBaseAmt)                                          AS TotalTaxBaseAmt
                                  , SUM(SurchargeAmt)                                        AS SurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt)                          AS TotalLinesWithSurchargeAmt
                                  , SUM(TaxAmtWithSurchargeAmt)                              AS TotalVatWithSurchargeAmt
                                  , SUM(TaxBaseAmtWithSurchargeAmt + TaxAmtWithSurchargeAmt) AS GrandTotalWithSurchargeAmt
                             FROM edi_cctop_901_991_v
                             WHERE c_invoice_id = i.c_invoice_id
                             GROUP BY C_Invoice_ID) taxAndSurchage ON TRUE
;

SELECT public.db_alter_view(
    'edi_cctop_invoic_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('edi_cctop_invoic_v$new'))
);

DROP VIEW IF EXISTS edi_cctop_invoic_v$new;

-- Column set helper (used throughout via subquery on AD_Column):
--   AD_Table_ID=291 (C_BPartner)
--   ColumnNames: IsEdiDesadvRecipient, EdiDesadvRecipientGLN, EdiDESADVSendingMode,
--                EdiDESADV_ExternalSystem_Config_ID, EdiDESADVDefaultItemCapacity,
--                IsEdiInvoicRecipient, EdiInvoicRecipientGLN, EdiINVOICSendingMode,
--                EdiINVOIC_ExternalSystem_Config_ID

-- Step 1: AD_UI_ElementField (leaf — no dependents)
DELETE FROM AD_UI_ElementField
WHERE AD_UI_Element_ID IN (
    SELECT e.AD_UI_Element_ID
    FROM AD_UI_Element e
    JOIN AD_Field f ON f.AD_Field_ID = e.AD_Field_ID
    WHERE f.AD_Column_ID IN (
        SELECT AD_Column_ID FROM AD_Column
        WHERE AD_Table_ID = 291
          AND ColumnName IN (
              'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
              'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
              'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
              'EdiINVOIC_ExternalSystem_Config_ID'
          )
    )
);

-- Step 2: AD_UI_Element rows that reference the EDI fields via AD_Field_ID
DELETE FROM AD_UI_Element
WHERE AD_Field_ID IN (
    SELECT AD_Field_ID FROM AD_Field
    WHERE AD_Column_ID IN (
        SELECT AD_Column_ID FROM AD_Column
        WHERE AD_Table_ID = 291
          AND ColumnName IN (
              'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
              'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
              'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
              'EdiINVOIC_ExternalSystem_Config_ID'
          )
    )
);

-- Step 3: AD_Element_Link rows for the EDI fields (links element→field)
DELETE FROM AD_Element_Link
WHERE AD_Field_ID IN (
    SELECT AD_Field_ID FROM AD_Field
    WHERE AD_Column_ID IN (
        SELECT AD_Column_ID FROM AD_Column
        WHERE AD_Table_ID = 291
          AND ColumnName IN (
              'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
              'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
              'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
              'EdiINVOIC_ExternalSystem_Config_ID'
          )
    )
);

-- Step 4: AD_Field_Trl translations
DELETE FROM AD_Field_Trl
WHERE AD_Field_ID IN (
    SELECT AD_Field_ID FROM AD_Field
    WHERE AD_Column_ID IN (
        SELECT AD_Column_ID FROM AD_Column
        WHERE AD_Table_ID = 291
          AND ColumnName IN (
              'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
              'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
              'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
              'EdiINVOIC_ExternalSystem_Config_ID'
          )
    )
);

-- Step 5: AD_Field rows
DELETE FROM AD_Field
WHERE AD_Column_ID IN (
    SELECT AD_Column_ID FROM AD_Column
    WHERE AD_Table_ID = 291
      AND ColumnName IN (
          'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
          'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
          'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
          'EdiINVOIC_ExternalSystem_Config_ID'
      )
);

-- Step 6: Drop old index (covers IsEdiDesadvRecipient + IsEdiInvoicRecipient)
DROP INDEX IF EXISTS c_bpartner_isedidesadvrecipient_isediinvoicrecipient;

-- Step 7: Drop the two FK constraints before dropping columns
ALTER TABLE C_BPartner DROP CONSTRAINT IF EXISTS edidesadvexternalsystemconfig_cbpartner;
ALTER TABLE C_BPartner DROP CONSTRAINT IF EXISTS ediinvoicexternalsystemconfig_cbpartner;

-- Step 8: Drop the 9 physical columns (using db_alter_table for idempotent DDL)
-- Defensive backup before the destructive DROP COLUMN (REVIEW.md SQL-migrations rule):
-- on a customer DB that did not run the data-copy (5806030) cleanly, this preserves the values.
SELECT backup_table('C_BPartner', '_5806040_edi_cols');

SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS IsEdiDesadvRecipient');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS EdiDesadvRecipientGLN');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS EdiDESADVSendingMode');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS EdiDESADV_ExternalSystem_Config_ID');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS EdiDESADVDefaultItemCapacity');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS IsEdiInvoicRecipient');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS EdiInvoicRecipientGLN');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS EdiINVOICSendingMode');
SELECT public.db_alter_table('C_BPartner', 'ALTER TABLE C_BPartner DROP COLUMN IF EXISTS EdiINVOIC_ExternalSystem_Config_ID');

-- Step 8b: Delete EXP_FormatLine rows referencing the 9 columns (FK to AD_Column)
DELETE FROM EXP_FormatLine
WHERE AD_Column_ID IN (
    SELECT AD_Column_ID FROM AD_Column
    WHERE AD_Table_ID = 291
      AND ColumnName IN (
          'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
          'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
          'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
          'EdiINVOIC_ExternalSystem_Config_ID'
      )
);

-- Step 9: Delete AD_Column_Trl before AD_Column (FK order)
DELETE FROM AD_Column_Trl
WHERE AD_Column_ID IN (
    SELECT AD_Column_ID FROM AD_Column
    WHERE AD_Table_ID = 291
      AND ColumnName IN (
          'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
          'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
          'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
          'EdiINVOIC_ExternalSystem_Config_ID'
      )
);

-- Step 10: Delete AD_Column rows (AD_Element rows are NOT deleted — shared with C_BPartner_EDI_Setting)
DELETE FROM AD_Column
WHERE AD_Table_ID = 291
  AND ColumnName IN (
      'IsEdiDesadvRecipient', 'EdiDesadvRecipientGLN', 'EdiDESADVSendingMode',
      'EdiDESADV_ExternalSystem_Config_ID', 'EdiDESADVDefaultItemCapacity',
      'IsEdiInvoicRecipient', 'EdiInvoicRecipientGLN', 'EdiINVOICSendingMode',
      'EdiINVOIC_ExternalSystem_Config_ID'
  );
