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

DROP VIEW IF EXISTS historical_invoices_json_v
;

CREATE OR REPLACE VIEW historical_invoices_json_v AS
SELECT invoic_v.c_invoice_id               AS "Invoice_ID",
       invoic_v.ReceiverGLN                AS "Invoice_Receiver_Tec_GLN",
       invoic_v.SenderGLN                  AS "Invoice_Sender_Tec_GLN",
       invoic_v.CountryCode                AS "Invoice_Sender_CountryCode",
       invoic_v.VATaxId                    AS "Invoice_Sender_VATaxId",
       invoic_v.Invoice_DocumentNo         AS "Invoice_DocumentNo",
       invoic_v.DateInvoiced               AS "Invoice_Date",
       invoic_v.DateAcct                   AS "Invoice_Acct_Date",
       invoic_v.docbasetype                AS "DocType_Base",
       invoic_v.docsubtype                 AS "DocType_Sub",
       invoic_v.CreditMemoReason           AS "CreditMemo_Reason",
       invoic_v.CreditMemoReasonText       AS "CreditMemo_ReasonText",
       invoic_v.POReference                AS "Order_POReference",
       invoic_v.DateOrdered                AS "Order_Date",
       invoic_v.MovementDate               AS "Shipment_Date",
       invoic_v.Shipment_DocumentNo        AS "Shipment_DocumentNo",
       invoic_v.EDIDesadvDocumentNo        AS "DESADV_DocumentNo",
       invoic_v.ISO_Code                   AS "Invoice_Currency_Code",
       invoic_v.GrandTotal                 AS "Invoice_GrandTotal",
       invoic_v.TotalLines                 AS "Invoice_TotalLines",
       invoic_v.TotalVAT                   AS "Invoice_TotalVAT",
       invoic_v.TotalTaxBaseAmt            AS "Invoice_TotalVATBaseAmt",
       invoic_v.SurchargeAmt               AS "Invoice_SurchargeAmt",
       invoic_v.TotalLinesWithSurchargeAmt AS "Invoice_TotalLinesWithSurchargeAmt",
       invoic_v.TotalVatWithSurchargeAmt   AS "Invoice_TotalVATWithSurchargeAmt",
       invoic_v.GrandTotalWithSurchargeAmt AS "Invoice_GrandTotalWithSurchargeAmt",
       invoic_v.updated::timestamp         AS "Updated",
       invoic_v.ExternalId                 AS "ExternalId",
       COALESCE(invoic_v.DataSource, '')   AS "DataSource",
       invoic_v.DocStatus                  AS "DocStatus",
       edi_119_v.json_data                 AS "Partners",
       edi_120_v.json_data                 AS "PaymentTerms",
       edi_140_v.json_data                 AS "PaymentDiscounts",
       edi_500_v.json_data                 AS "Lines",
       edi_901_991_v.json_data             AS "Sums"
FROM edi_cctop_invoic_v invoic_v
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'EANCOM_LocationType', eancom_locationtype,
                                   'GLN', GLN,
                                   'Name', Name,
                                   'Name2', Name2,
                                   'PartnerNo', Value,
                                   'VATaxID', VATaxID,
                                   'ReferenceNo', ReferenceNo,
                                   'SiteName', SiteName,
                                   'Setup_Place_No', Setup_Place_No,
                                   'Address1', Address1,
                                   'Address2', Address2,
                                   'Postal', Postal,
                                   'City', City,
                                   'CountryCode', CountryCode,
                                   'Phone', Phone,
                                   'Fax', Fax,
                                   'CustomEdiAttributes', attr_v.json_data
                               )) AS json_data
                    FROM edi_cctop_119_v cctop119
                             LEFT JOIN (SELECT bpartner_value,
                                               ad_client_id,
                                               ad_org_id,
                                               JSON_AGG(JSON_BUILD_OBJECT(
                                                       bpartner_attr_value.attr_value,
                                                       bpartner_attr_value.valuestring
                                                   )) AS json_data
                                        FROM edi_bpartner_attribute_valuestring_v bpartner_attr_value
                                        GROUP BY bpartner_value, ad_client_id, ad_org_id) attr_v
                                       ON attr_v.bpartner_value = cctop119.value
                                           AND attr_v.ad_client_id = cctop119.ad_client_id
                                           AND (cctop119.ad_org_id = 0 OR attr_v.ad_org_id = cctop119.ad_org_id)
                    GROUP BY c_invoice_id) edi_119_v ON edi_119_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'Net_Days', NetDays
                               )) AS json_data
                    FROM edi_cctop_120_v
                    GROUP BY c_invoice_id) edi_120_v ON edi_120_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                   'Discount_Name', Name,
                                   'Tax_Percent', Rate,
                                   'Discount_Days', discountdays,
                                   'Discount_Percent', Discount,
                                   'Discount_BaseAmt', DiscountBaseAmt,
                                   'Discount_Amt', DiscountAmt
                               )) AS json_data
                    FROM edi_cctop_140_v
                    GROUP BY c_invoice_id) edi_140_v ON edi_140_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'Invoice_Line', v.line,
                                            'Invoice_QtyInvoiced', v.QtyInvoiced,
                                            'Invoice_QtyInvoiced_UOM', v.eancom_uom,
                                            'ORDERS_Line', v.externalseqno,
                                            'ORDERS_QtyInvoiced', v.qtyEnteredInBPartnerUOM,
                                            'ORDERS_QtyInvoiced_UOM', uom.x12de355,
                                            'Order_POReference', v.orderporeference,
                                            'Order_Line', v.orderline,
                                            'Order_QtyInvoiced', v.QtyInvoicedInOrderedUOM,
                                            'Order_QtyInvoiced_UOM', v.eancom_ordered_uom,
                                            'Currency_Code', v.iso_code,
                                            'PricePerUnit', v.priceactual,
                                            'PriceUOM', v.eancom_price_uom,
                                            'Discount_Amt', v.discount,
                                            'QtyBasedOn', v.invoicableqtybasedon,
                                            'NetAmt', v.linenetamt,
                                            'Tax_Percent', v.rate,
                                            'Tax_Amount', v.taxamtinfo,
                                            'Product_Name', v.name || v.name2,
                                            'Product_Description', v.productdescription,
                                            'Product_Buyer_CU_GTIN', v.Buyer_GTIN_CU,
                                            'Product_Buyer_TU_GTIN', v.Buyer_GTIN_TU,
                                            'Product_Buyer_ProductNo', v.CustomerProductNo,
                                            'Product_Supplier_TU_GTIN', v.Supplier_GTIN_CU,
                                            'Product_Supplier_ProductNo', v.Value
                                        ) ORDER BY v.line) AS json_data
                    FROM edi_cctop_invoic_500_v v
                             LEFT JOIN c_uom uom ON uom.c_uom_id = v.C_UOM_BPartner_ID
                    GROUP BY c_invoice_id) edi_500_v ON edi_500_v.c_invoice_id = invoic_v.c_invoice_id
         LEFT JOIN (SELECT c_invoice_id,
                           JSON_AGG(JSON_BUILD_OBJECT(
                                            'TotalAmt', TotalAmt,
                                            'Tax_Amt', TaxAmt,
                                            'Tax_BaseAmt', TaxBaseAmt,
                                            'Tax_Percent', Rate,
                                            'Tax_Exempt', IsTaxExempt = 'Y',
                                            'SurchargeAmt', SurchargeAmt,
                                            'Tax_BaseAmtWithSurchargeAmt', TaxBaseAmtWithSurchargeAmt,
                                            'Tax_AmtWithSurchargeAmt', TaxAmtWithSurchargeAmt
                                        ) ORDER BY rate DESC) AS json_data
                    FROM edi_cctop_901_991_v
                    GROUP BY c_invoice_id) edi_901_991_v ON edi_901_991_v.c_invoice_id = invoic_v.c_invoice_id
ORDER BY invoic_v.updated, invoic_v.c_invoice_id
;
