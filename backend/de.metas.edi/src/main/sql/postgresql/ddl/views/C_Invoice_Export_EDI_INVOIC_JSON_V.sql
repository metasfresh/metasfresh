/*
 * #%L
 * de.metas.edi
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
 
DROP VIEW IF EXISTS C_Invoice_Export_EDI_INVOIC_JSON_V;

CREATE OR REPLACE VIEW C_Invoice_Export_EDI_INVOIC_JSON_V AS
SELECT invoic_v.c_invoice_id,
       JSON_BUILD_OBJECT(
               'metasfresh_INVOIC', JSON_AGG(
               JSON_BUILD_OBJECT(
                       'Invoice_ID', invoic_v.c_invoice_id,
                       'Invoice_Receiver_Tec_GLN', invoic_v.ReceiverGLN,
                       'Invoice_Sender_Tec_GLN', invoic_v.SenderGLN,
                       'Invoice_Sender_CountryCode', invoic_v.CountryCode,
                       'Invoice_Sender_VATaxId', invoic_v.VATaxId,
                       'Invoice_DocumentNo', invoic_v.Invoice_DocumentNo,
                       'Invoice_Date', invoic_v.DateInvoiced,
                       'Invoice_Acct_Date', invoic_v.DateAcct,
                   -- doctype-related
                       'DocType_Base', invoic_v.docbasetype,
                       'DocType_Sub', invoic_v.docsubtype,
                       'CreditMemo_Reason', invoic_v.CreditMemoReason,
                       'CreditMemo_ReasonText', invoic_v.CreditMemoReasonText,
                   -- order
                       'Order_POReference', invoic_v.POReference,
                       'Order_Date', invoic_v.DateOrdered,
                   -- shipment
                       'Shipment_Date', invoic_v.MovementDate,
                       'Shipment_DocumentNo', invoic_v.Shipment_DocumentNo,
                       'DESADV_DocumentNo', invoic_v.EDIDesadvDocumentNo,
                   -- money
                       'Invoice_Currency_Code', invoic_v.ISO_Code,
                       'Invoice_GrandTotal', invoic_v.GrandTotal,
                       'Invoice_TotalLines', invoic_v.TotalLines,
                   -- tax
                       'Invoice_TotalVAT', invoic_v.TotalVAT,
                       'Invoice_TotalVATBaseAmt', invoic_v.TotalTaxBaseAmt,
                   -- surcharge
                       'Invoice_SurchargeAmt', invoic_v.SurchargeAmt,
                       'Invoice_TotalLinesWithSurchargeAmt', invoic_v.TotalLinesWithSurchargeAmt,
                       'Invoice_TotalVATWithSurchargeAmt', invoic_v.TotalVatWithSurchargeAmt,
                       'Invoice_GrandTotalWithSurchargeAmt', invoic_v.GrandTotalWithSurchargeAmt,
                       'Partners', edi_119_v.json_data,
                       'PaymentTerms', edi_120_v.json_data,
                       'PaymentDiscounts', edi_140_v.json_data,
                       'Lines', edi_500_v.json_data,
                       'Sums', edi_901_991_v.json_data
               )
                                    )
       ) AS embedded_json
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
                                   'Fax', Fax
                                    )) AS json_data
                    FROM edi_cctop_119_v
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
                                        -- product
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
GROUP BY invoic_v.c_invoice_id
;
