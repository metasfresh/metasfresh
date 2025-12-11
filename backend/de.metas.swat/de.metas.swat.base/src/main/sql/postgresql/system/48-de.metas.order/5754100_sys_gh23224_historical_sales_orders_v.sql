/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

DROP VIEW IF EXISTS Historical_Sales_Orders_V
;

CREATE OR REPLACE VIEW Historical_Sales_Orders_V AS
SELECT o.C_Order_ID
     , REGEXP_REPLACE(o.DocumentNo, '\s+$', '') AS Order_DocumentNo
     , (CASE
            WHEN REGEXP_REPLACE(o.POReference::TEXT, '\s+$', '') <> ''::TEXT AND o.POReference IS NOT NULL
                THEN REGEXP_REPLACE(o.POReference, '\s+$', '')
                ELSE NULL::CHARACTER VARYING
        END)                                    AS POReference
     , (CASE
            WHEN o.DateOrdered IS NOT NULL
                THEN o.DateOrdered
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                    AS DateOrdered
     , (CASE
            WHEN o.DatePromised IS NOT NULL
                THEN o.DatePromised
                ELSE NULL::TIMESTAMP WITHOUT TIME ZONE
        END)                                    AS DatePromised
     , rbp.C_BPartner_ID
     , rbp.value                                AS BPartnerValue
     , rbp.name                                 AS BPartnerName
     , bbp.C_BPartner_ID                        AS BillBParterId
     , bbp.value                                AS BillBParterValue
     , bbp.name                                 AS BillBParterName
     , hbp.C_BPartner_ID                        AS HandoverBParterId
     , hbp.value                                AS HandoverBParterValue
     , hbp.name                                 AS HandoverBParterName
     , dbp.C_BPartner_ID                        AS DropshipBParterId
     , dbp.value                                AS DropshipBParterValue
     , dbp.name                                 AS DropshipBParterName
     , o.grandtotal                             AS GrandTotal
     , pterm.name                               AS PaymentTermName
     , c.iso_code                               AS Currency
FROM C_Order o
         INNER JOIN C_DocType dt ON dt.C_DocType_ID = o.C_DocTypetarget_ID
         LEFT JOIN C_BPartner rbp ON rbp.C_BPartner_ID = o.C_BPartner_ID
         LEFT JOIN C_BPartner bbp ON bbp.C_BPartner_ID = o.bill_bpartner_id
         LEFT JOIN C_BPartner hbp ON hbp.C_BPartner_ID = o.handover_partner_id
         LEFT JOIN C_BPartner dbp ON dbp.C_BPartner_ID = o.dropship_bpartner_id
         LEFT JOIN C_Currency c ON c.C_Currency_ID = o.C_Currency_ID
         LEFT JOIN C_PaymentTerm pterm ON pterm.c_paymentterm_id = o.c_paymentterm_id
WHERE dt.docbasetype = 'SOO'
  AND dt.docsubtype = 'SO'
  AND o.DocStatus IN ('CO')
  AND o.issotrx = 'Y'
;
