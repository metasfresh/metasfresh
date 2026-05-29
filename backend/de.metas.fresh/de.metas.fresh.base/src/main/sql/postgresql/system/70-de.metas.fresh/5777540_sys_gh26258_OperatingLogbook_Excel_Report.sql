/*
 * #%L
 * de.metas.fresh.base
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

-- ========================================
-- Betriebstagebuch  Report
--
-- 1. "B + T Eingang" - Regular material receipts
-- 2. "B + T" - Dropship warehouse receipts
-- 3. "H + M" - Customer shipments
-- ========================================

-- ========================================
-- PART 1: Material Receipts (B + T Eingang) - Regular Warehouses
-- ========================================

DROP FUNCTION IF EXISTS report.OperatingLogbook_Excel_Report(date,
                                                             date)
;

CREATE OR REPLACE FUNCTION report.OperatingLogbook_Excel_Report(IN p_MovementDate_From date,
                                                                IN p_MovementDate_To   date)
    RETURNS TABLE
            (
                ServicePerformed        varchar,
                InvoiceDocumentNo       varchar,
                PurchaseOrderDocumentNo varchar,
                TakeoverDate            date,
                QuantityInTons          numeric,
                WasteCodeNumber         varchar,
                WasteProducerName       varchar,
                WasteDisposalName       varchar,
                WasteCodeNumber2        varchar,
                QuantityInTons2         numeric,
                SubmissionDate          date,
                Comments                varchar
            )
    LANGUAGE sql
AS
$$
SELECT 'B + T Eingang'                                                                       AS ServicePerformed,
       inv.DocumentNo                                                                        AS InvoiceDocumentNo,
       ord.DocumentNo                                                                        AS PurchaseOrderDocumentNo,
       io.MovementDate                                                                       AS TakeoverDate,
       iol.MovementQty                                                                       AS QuantityInTons,
       COALESCE(prod.AVV_No, prod.value)                                                     AS WasteCodeNumber,

       bp_supplier.Name || E'\n' || COALESCE(bp_supplier.WasteProducerNo, bp_supplier.Value) AS WasteProducerName,

       COALESCE(bp_dest_wh.Name, bp_dest_org.Name) || E'\n' || COALESCE(
               COALESCE(bp_dest_wh.WasteDisposerNo, bp_dest_wh.Value),
               COALESCE(bp_dest_org.WasteDisposerNo, bp_dest_org.Value)
                                                               )                             AS WasteDisposalName,


       prod.AVV_No                                                                           AS WasteCodeNumber2,
       iol.MovementQty                                                                       AS QuantityInTons2,
       io.MovementDate                                                                       AS SubmissionDate,
       NULL                                                                                  AS Comments -- empty for now

FROM M_InOut io
         INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
         INNER JOIN M_Product prod ON iol.M_Product_ID = prod.M_Product_ID
         INNER JOIN C_BPartner bp_supplier ON io.C_BPartner_ID = bp_supplier.C_BPartner_ID
         LEFT JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
         LEFT JOIN C_Order ord ON ol.C_Order_ID = ord.C_Order_ID
         LEFT JOIN C_InvoiceLine invl ON iol.M_InOutLine_ID = invl.M_InOutLine_ID
         LEFT JOIN C_Invoice inv ON invl.C_Invoice_ID = inv.C_Invoice_ID
         LEFT JOIN M_ReceiptSchedule_Alloc rsa ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID
         LEFT JOIN M_ReceiptSchedule rs ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
         LEFT JOIN M_Warehouse wh_dest ON wh_dest.M_Warehouse_ID = COALESCE(
        rs.M_Warehouse_Dest_ID,
        rs.M_Warehouse_Override_ID,
        rs.M_Warehouse_ID
                                                                   )

         LEFT JOIN C_BPartner bp_dest_wh ON wh_dest.C_BPartner_ID = bp_dest_wh.C_BPartner_ID
         LEFT JOIN AD_Org org_dest ON io.AD_Org_ID = org_dest.AD_Org_ID
         LEFT JOIN AD_OrgInfo org_info ON org_dest.AD_Org_ID = org_info.AD_Org_ID
         LEFT JOIN C_BPartner bp_dest_org ON org_info.Org_BPartner_ID = bp_dest_org.C_BPartner_ID
         LEFT JOIN M_Warehouse wh ON io.M_Warehouse_ID = wh.M_Warehouse_ID

WHERE io.IsSOTrx = 'N'
  AND io.MovementType IN ('V+', 'V-')
  AND io.MovementDate BETWEEN p_MovementDate_From AND p_MovementDate_To
  AND io.DocStatus IN ('CO', 'CL')
  AND io.IsActive = 'Y'
  AND iol.IsActive = 'Y'
  AND wh.Value != 'DropshipWarehouse'

UNION ALL

-- ========================================
-- PART 2: Material Receipts (B + T) - Dropship Warehouses
-- ========================================
SELECT 'B + T'                                                                               AS ServicePerformed,
       inv.DocumentNo                                                                        AS InvoiceDocumentNo,
       ord.DocumentNo                                                                        AS PurchaseOrderDocumentNo,
       io.MovementDate                                                                       AS TakeoverDate,
       iol.MovementQty                                                                       AS QuantityInTons,
       COALESCE(prod.AVV_No, prod.value)                                                     AS WasteCodeNumber,

       bp_supplier.Name || E'\n' || COALESCE(bp_supplier.WasteProducerNo, bp_supplier.Value) AS WasteProducerName,

       COALESCE(bp_dest_wh.Name, bp_dest_org.Name) || E'\n' || COALESCE(
               COALESCE(bp_dest_wh.WasteDisposerNo, bp_dest_wh.Value),
               COALESCE(bp_dest_org.WasteDisposerNo, bp_dest_org.Value)
                                                               )                             AS WasteDisposalName,

       prod.AVV_No                                                                           AS WasteCodeNumber2,
       iol.MovementQty                                                                       AS QuantityInTons2,
       io.MovementDate                                                                       AS SubmissionDate,
       NULL                                                                                  AS Comments

FROM M_InOut io
         INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
         INNER JOIN M_Product prod ON iol.M_Product_ID = prod.M_Product_ID
         INNER JOIN C_BPartner bp_supplier ON io.C_BPartner_ID = bp_supplier.C_BPartner_ID
         LEFT JOIN C_OrderLine ol ON iol.C_OrderLine_ID = ol.C_OrderLine_ID
         LEFT JOIN C_Order ord ON ol.C_Order_ID = ord.C_Order_ID
         LEFT JOIN C_InvoiceLine invl ON iol.M_InOutLine_ID = invl.M_InOutLine_ID
         LEFT JOIN C_Invoice inv ON invl.C_Invoice_ID = inv.C_Invoice_ID
         LEFT JOIN M_ReceiptSchedule_Alloc rsa ON rsa.M_InOutLine_ID = iol.M_InOutLine_ID
         LEFT JOIN M_ReceiptSchedule rs ON rs.M_ReceiptSchedule_ID = rsa.M_ReceiptSchedule_ID
         LEFT JOIN M_Warehouse wh_dest ON wh_dest.M_Warehouse_ID = COALESCE(
        rs.M_Warehouse_Dest_ID,
        rs.M_Warehouse_Override_ID,
        rs.M_Warehouse_ID
                                                                   )

         LEFT JOIN C_BPartner bp_dest_wh ON wh_dest.C_BPartner_ID = bp_dest_wh.C_BPartner_ID
         LEFT JOIN AD_Org org_dest ON io.AD_Org_ID = org_dest.AD_Org_ID
         LEFT JOIN AD_OrgInfo org_info ON org_dest.AD_Org_ID = org_info.AD_Org_ID
         LEFT JOIN C_BPartner bp_dest_org ON org_info.Org_BPartner_ID = bp_dest_org.C_BPartner_ID
         INNER JOIN M_Warehouse wh ON io.M_Warehouse_ID = wh.M_Warehouse_ID

WHERE io.IsSOTrx = 'N'
  AND io.MovementDate BETWEEN p_MovementDate_From AND p_MovementDate_To
  AND io.DocStatus IN ('CO', 'CL')
  AND io.IsActive = 'Y'
  AND iol.IsActive = 'Y'
  AND wh.Value = 'DropshipWarehouse'

UNION ALL

-- ========================================
-- PART 3: Customer Shipments (H + M)
-- ========================================
SELECT 'H + M'                                                                               AS ServicePerformed,
       inv_sales.DocumentNo                                                                  AS InvoiceDocumentNo,
       ord_sales.DocumentNo                                                                  AS PurchaseOrderDocumentNo,
       io.MovementDate                                                                       AS TakeoverDate,
       iol.MovementQty                                                                       AS QuantityInTons,
       COALESCE(prod.AVV_No, prod.value)                                                     AS WasteCodeNumber,

       COALESCE(bp_wh_source.Name, bp_org_source.Name) || E'\n' || COALESCE(
               COALESCE(bp_wh_source.WasteProducerNo, bp_wh_source.Value),
               COALESCE(bp_org_source.WasteProducerNo, bp_org_source.Value)
                                                                   )                         AS WasteProducerName,

       bp_customer.Name || E'\n' || COALESCE(bp_customer.WasteDisposerNo, bp_customer.Value) AS WasteDisposalName,

       prod.AVV_No                                                                           AS WasteCodeNumber2,
       iol.MovementQty                                                                       AS QuantityInTons2,
       io.MovementDate                                                                       AS SubmissionDate,
       NULL                                                                                  AS Comments

FROM M_InOut io
         INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID
         INNER JOIN M_Product prod ON iol.M_Product_ID = prod.M_Product_ID
         INNER JOIN C_BPartner bp_customer ON io.C_BPartner_ID = bp_customer.C_BPartner_ID
         LEFT JOIN C_OrderLine ol_sales ON iol.C_OrderLine_ID = ol_sales.C_OrderLine_ID
         LEFT JOIN C_Order ord_sales ON ol_sales.C_Order_ID = ord_sales.C_Order_ID AND ord_sales.IsSOTrx = 'Y'
         LEFT JOIN C_InvoiceLine invl_sales ON iol.M_InOutLine_ID = invl_sales.M_InOutLine_ID
         LEFT JOIN C_Invoice inv_sales ON invl_sales.C_Invoice_ID = inv_sales.C_Invoice_ID AND inv_sales.IsSOTrx = 'Y'
         LEFT JOIN M_Warehouse wh_source ON io.M_Warehouse_ID = wh_source.M_Warehouse_ID
         LEFT JOIN C_BPartner bp_wh_source ON wh_source.C_BPartner_ID = bp_wh_source.C_BPartner_ID
         LEFT JOIN AD_Org org_source ON wh_source.AD_Org_ID = org_source.AD_Org_ID
         LEFT JOIN AD_OrgInfo org_info_source ON org_source.AD_Org_ID = org_info_source.AD_Org_ID
         LEFT JOIN C_BPartner bp_org_source ON org_info_source.Org_BPartner_ID = bp_org_source.C_BPartner_ID

WHERE io.IsSOTrx = 'Y'
  AND io.MovementType IN ('C+', 'C-')
  AND io.MovementDate BETWEEN p_MovementDate_From AND p_MovementDate_To
  AND io.DocStatus IN ('CO', 'CL')
  AND io.IsActive = 'Y'
  AND iol.IsActive = 'Y'

ORDER BY ServicePerformed,
         TakeoverDate,
         InvoiceDocumentNo;
$$
;
