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

DROP FUNCTION IF EXISTS "de.metas.edi".getediexportstatus(numeric, numeric)
;

-- View: de_metas_invoicecandidate.c_invoice_candidate_failed_to_update_v
-- DROP VIEW de_metas_invoicecandidate.c_invoice_candidate_failed_to_update_v;

DROP VIEW de_metas_invoicecandidate.c_invoice_candidate_failed_to_update_v
;


CREATE OR REPLACE VIEW de_metas_invoicecandidate.c_invoice_candidate_failed_to_update_v AS
SELECT now() AS found,
       NULL::timestamp with time zone AS reenqueued,
       'N'::character(1) AS iserroracknowledged,
       'C_Invoice_Candidate_Wrong_Qty_iol_v'::text AS problem_found_by,
       ic.ad_client_id,
       ic.ad_org_id,
       ic.c_invoice_candidate_id,
       ic.c_orderline_id,
       ic.created,
       ic.createdby,
       ic.isactive,
       ic.qtytoinvoice,
       ic.updated,
       ic.updatedby,
       ic.schedulerresult,
       ic.priceactual_override,
       ic.discount_override,
       ic.bill_bpartner_id,
       ic.bill_location_id,
       ic.bill_user_id,
       ic.invoicerule,
       ic.qtytoinvoicenetamt,
       ic.dateinvoiced,
       ic.istoclear,
       ic.m_product_id,
       ic.dateordered,
       ic.processed,
       ic.priceactual,
       ic.c_currency_id,
       ic.qtyordered,
       ic.qtydelivered,
       ic.qtyinvoiced,
       ic.qtytoinvoice_override,
       ic.qtytoinvoice_overridefulfilled,
       ic.c_charge_id,
       ic.bill_bpartner_override_id,
       ic.invoicerule_override,
       ic.m_pricingsystem_id,
       ic.discount,
       ic.netamttoinvoice,
       ic.netamtinvoiced,
       ic.c_invoice_candidate_agg_id,
       ic.lineaggregationkey,
       ic.lineaggregationkey_suffix,
       ic.c_ilcandhandler_id,
       ic.ad_table_id,
       ic.record_id,
       ic.iserror,
       ic.ad_note_id,
       ic.errormsg,
       ic.datetoinvoice,
       ic.datetoinvoice_override,
       ic.c_conversiontype_id,
       ic.invoicescheduleamtstatus,
       ic.ismanual,
       ic.description,
       ic.ad_user_incharge_id,
       ic.headeraggregationkey,
       ic.splitamt,
       ic.descriptionheader,
       ic.descriptionbottom,
       ic.priceentered,
       ic.priceentered_override,
       ic.issotrx,
       ic.qualitydiscountpercent_effective,
       ic.qualitynote_receiptschedule,
       ic.qualitydiscountpercent,
       ic.qualitydiscountpercent_override,
       ic.isindispute,
       ic.qtywithissues,
       ic.qtyorderedoverunder,
       ic.reasondiscount,
       ic.c_uom_id,
       ic.price_uom_id,
       ic.c_order_id,
       ic.c_activity_id,
       ic.c_tax_id,
       ic.qtytoinvoiceinpriceuom,
       ic.isprinted,
       ic.line,
       ic.c_doctypeinvoice_id,
       ic.m_material_tracking_id,
       ic.approvalforinvoicing,
       ic.c_tax_override_id,
       ic.poreference,
       ic.dateacct,
       ic.deliverydate,
       ic.m_inout_id,
       ic.priceactual_net_effective,
       ic.istaxincluded,
       ic.qtyenteredtu,
       ic.qtytoinvoicebeforediscount,
       ic.istaxincluded_override,
       ic.c_invoice_candidate_headeraggregation_id,
       ic.c_invoice_candidate_headeraggregation_override_id,
       ic.headeraggregationkey_calc,
       ic.c_invoice_candidate_headeraggregation_effective_id,
       ic.headeraggregationkeybuilder_id,
       ic.first_ship_bplocation_id,
       ic.isinoutapprovedforinvoicing,
       ic.qtywithissues_effective,
       ic.processed_override,
       ic.processed_calc,
       ic.task_08848_fixed,
       ic.lineaggregationkeybuilder_id,
       ic.ispackagingmaterial,
       ic.m_pricelist_version_id,
       ic.qualityinvoicelinegrouptype
FROM c_invoice_candidate ic
WHERE (ic.c_invoice_candidate_id IN ( SELECT c_invoice_candidate_wrong_qty_iol_v.c_invoice_candidate_id
                                      FROM de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_iol_v))
UNION
SELECT now() AS found,
       NULL::timestamp with time zone AS reenqueued,
       'N'::bpchar AS iserroracknowledged,
       'C_Invoice_Candidate_Wrong_Qtytoinvoice_V'::text AS problem_found_by,
       ic.ad_client_id,
       ic.ad_org_id,
       ic.c_invoice_candidate_id,
       ic.c_orderline_id,
       ic.created,
       ic.createdby,
       ic.isactive,
       ic.qtytoinvoice,
       ic.updated,
       ic.updatedby,
       ic.schedulerresult,
       ic.priceactual_override,
       ic.discount_override,
       ic.bill_bpartner_id,
       ic.bill_location_id,
       ic.bill_user_id,
       ic.invoicerule,
       ic.qtytoinvoicenetamt,
       ic.dateinvoiced,
       ic.istoclear,
       ic.m_product_id,
       ic.dateordered,
       ic.processed,
       ic.priceactual,
       ic.c_currency_id,
       ic.qtyordered,
       ic.qtydelivered,
       ic.qtyinvoiced,
       ic.qtytoinvoice_override,
       ic.qtytoinvoice_overridefulfilled,
       ic.c_charge_id,
       ic.bill_bpartner_override_id,
       ic.invoicerule_override,
       ic.m_pricingsystem_id,
       ic.discount,
       ic.netamttoinvoice,
       ic.netamtinvoiced,
       ic.c_invoice_candidate_agg_id,
       ic.lineaggregationkey,
       ic.lineaggregationkey_suffix,
       ic.c_ilcandhandler_id,
       ic.ad_table_id,
       ic.record_id,
       ic.iserror,
       ic.ad_note_id,
       ic.errormsg,
       ic.datetoinvoice,
       ic.datetoinvoice_override,
       ic.c_conversiontype_id,
       ic.invoicescheduleamtstatus,
       ic.ismanual,
       ic.description,
       ic.ad_user_incharge_id,
       ic.headeraggregationkey,
       ic.splitamt,
       ic.descriptionheader,
       ic.descriptionbottom,
       ic.priceentered,
       ic.priceentered_override,
       ic.issotrx,
       ic.qualitydiscountpercent_effective,
       ic.qualitynote_receiptschedule,
       ic.qualitydiscountpercent,
       ic.qualitydiscountpercent_override,
       ic.isindispute,
       ic.qtywithissues,
       ic.qtyorderedoverunder,
       ic.reasondiscount,
       ic.c_uom_id,
       ic.price_uom_id,
       ic.c_order_id,
       ic.c_activity_id,
       ic.c_tax_id,
       ic.qtytoinvoiceinpriceuom,
       ic.isprinted,
       ic.line,
       ic.c_doctypeinvoice_id,
       ic.m_material_tracking_id,
       ic.approvalforinvoicing,
       ic.c_tax_override_id,
       ic.poreference,
       ic.dateacct,
       ic.deliverydate,
       ic.m_inout_id,
       ic.priceactual_net_effective,
       ic.istaxincluded,
       ic.qtyenteredtu,
       ic.qtytoinvoicebeforediscount,
       ic.istaxincluded_override,
       ic.c_invoice_candidate_headeraggregation_id,
       ic.c_invoice_candidate_headeraggregation_override_id,
       ic.headeraggregationkey_calc,
       ic.c_invoice_candidate_headeraggregation_effective_id,
       ic.headeraggregationkeybuilder_id,
       ic.first_ship_bplocation_id,
       ic.isinoutapprovedforinvoicing,
       ic.qtywithissues_effective,
       ic.processed_override,
       ic.processed_calc,
       ic.task_08848_fixed,
       ic.lineaggregationkeybuilder_id,
       ic.ispackagingmaterial,
       ic.m_pricelist_version_id,
       ic.qualityinvoicelinegrouptype
FROM c_invoice_candidate ic
WHERE (ic.c_invoice_candidate_id IN ( SELECT c_invoice_candidate_wrong_qtytoinvoice_v.c_invoice_candidate_id
                                      FROM de_metas_invoicecandidate.c_invoice_candidate_wrong_qtytoinvoice_v))
UNION
SELECT now() AS found,
       NULL::timestamp with time zone AS reenqueued,
       'N'::bpchar AS iserroracknowledged,
       'C_Invoice_Candidate_Wrong_Qty_ol_v'::text AS problem_found_by,
       ic.ad_client_id,
       ic.ad_org_id,
       ic.c_invoice_candidate_id,
       ic.c_orderline_id,
       ic.created,
       ic.createdby,
       ic.isactive,
       ic.qtytoinvoice,
       ic.updated,
       ic.updatedby,
       ic.schedulerresult,
       ic.priceactual_override,
       ic.discount_override,
       ic.bill_bpartner_id,
       ic.bill_location_id,
       ic.bill_user_id,
       ic.invoicerule,
       ic.qtytoinvoicenetamt,
       ic.dateinvoiced,
       ic.istoclear,
       ic.m_product_id,
       ic.dateordered,
       ic.processed,
       ic.priceactual,
       ic.c_currency_id,
       ic.qtyordered,
       ic.qtydelivered,
       ic.qtyinvoiced,
       ic.qtytoinvoice_override,
       ic.qtytoinvoice_overridefulfilled,
       ic.c_charge_id,
       ic.bill_bpartner_override_id,
       ic.invoicerule_override,
       ic.m_pricingsystem_id,
       ic.discount,
       ic.netamttoinvoice,
       ic.netamtinvoiced,
       ic.c_invoice_candidate_agg_id,
       ic.lineaggregationkey,
       ic.lineaggregationkey_suffix,
       ic.c_ilcandhandler_id,
       ic.ad_table_id,
       ic.record_id,
       ic.iserror,
       ic.ad_note_id,
       ic.errormsg,
       ic.datetoinvoice,
       ic.datetoinvoice_override,
       ic.c_conversiontype_id,
       ic.invoicescheduleamtstatus,
       ic.ismanual,
       ic.description,
       ic.ad_user_incharge_id,
       ic.headeraggregationkey,
       ic.splitamt,
       ic.descriptionheader,
       ic.descriptionbottom,
       ic.priceentered,
       ic.priceentered_override,
       ic.issotrx,
       ic.qualitydiscountpercent_effective,
       ic.qualitynote_receiptschedule,
       ic.qualitydiscountpercent,
       ic.qualitydiscountpercent_override,
       ic.isindispute,
       ic.qtywithissues,
       ic.qtyorderedoverunder,
       ic.reasondiscount,
       ic.c_uom_id,
       ic.price_uom_id,
       ic.c_order_id,
       ic.c_activity_id,
       ic.c_tax_id,
       ic.qtytoinvoiceinpriceuom,
       ic.isprinted,
       ic.line,
       ic.c_doctypeinvoice_id,
       ic.m_material_tracking_id,
       ic.approvalforinvoicing,
       ic.c_tax_override_id,
       ic.poreference,
       ic.dateacct,
       ic.deliverydate,
       ic.m_inout_id,
       ic.priceactual_net_effective,
       ic.istaxincluded,
       ic.qtyenteredtu,
       ic.qtytoinvoicebeforediscount,
       ic.istaxincluded_override,
       ic.c_invoice_candidate_headeraggregation_id,
       ic.c_invoice_candidate_headeraggregation_override_id,
       ic.headeraggregationkey_calc,
       ic.c_invoice_candidate_headeraggregation_effective_id,
       ic.headeraggregationkeybuilder_id,
       ic.first_ship_bplocation_id,
       ic.isinoutapprovedforinvoicing,
       ic.qtywithissues_effective,
       ic.processed_override,
       ic.processed_calc,
       ic.task_08848_fixed,
       ic.lineaggregationkeybuilder_id,
       ic.ispackagingmaterial,
       ic.m_pricelist_version_id,
       ic.qualityinvoicelinegrouptype
FROM c_invoice_candidate ic
WHERE (ic.c_invoice_candidate_id IN ( SELECT c_invoice_candidate_wrong_qty_ol_v.c_invoice_candidate_id
                                      FROM de_metas_invoicecandidate.c_invoice_candidate_wrong_qty_ol_v))
UNION
SELECT now() AS found,
       NULL::timestamp with time zone AS reenqueued,
       'N'::bpchar AS iserroracknowledged,
       'C_Invoice_Candidate_Stale_QtyInvoiced_v'::text AS problem_found_by,
       ic.ad_client_id,
       ic.ad_org_id,
       ic.c_invoice_candidate_id,
       ic.c_orderline_id,
       ic.created,
       ic.createdby,
       ic.isactive,
       ic.qtytoinvoice,
       ic.updated,
       ic.updatedby,
       ic.schedulerresult,
       ic.priceactual_override,
       ic.discount_override,
       ic.bill_bpartner_id,
       ic.bill_location_id,
       ic.bill_user_id,
       ic.invoicerule,
       ic.qtytoinvoicenetamt,
       ic.dateinvoiced,
       ic.istoclear,
       ic.m_product_id,
       ic.dateordered,
       ic.processed,
       ic.priceactual,
       ic.c_currency_id,
       ic.qtyordered,
       ic.qtydelivered,
       ic.qtyinvoiced,
       ic.qtytoinvoice_override,
       ic.qtytoinvoice_overridefulfilled,
       ic.c_charge_id,
       ic.bill_bpartner_override_id,
       ic.invoicerule_override,
       ic.m_pricingsystem_id,
       ic.discount,
       ic.netamttoinvoice,
       ic.netamtinvoiced,
       ic.c_invoice_candidate_agg_id,
       ic.lineaggregationkey,
       ic.lineaggregationkey_suffix,
       ic.c_ilcandhandler_id,
       ic.ad_table_id,
       ic.record_id,
       ic.iserror,
       ic.ad_note_id,
       ic.errormsg,
       ic.datetoinvoice,
       ic.datetoinvoice_override,
       ic.c_conversiontype_id,
       ic.invoicescheduleamtstatus,
       ic.ismanual,
       ic.description,
       ic.ad_user_incharge_id,
       ic.headeraggregationkey,
       ic.splitamt,
       ic.descriptionheader,
       ic.descriptionbottom,
       ic.priceentered,
       ic.priceentered_override,
       ic.issotrx,
       ic.qualitydiscountpercent_effective,
       ic.qualitynote_receiptschedule,
       ic.qualitydiscountpercent,
       ic.qualitydiscountpercent_override,
       ic.isindispute,
       ic.qtywithissues,
       ic.qtyorderedoverunder,
       ic.reasondiscount,
       ic.c_uom_id,
       ic.price_uom_id,
       ic.c_order_id,
       ic.c_activity_id,
       ic.c_tax_id,
       ic.qtytoinvoiceinpriceuom,
       ic.isprinted,
       ic.line,
       ic.c_doctypeinvoice_id,
       ic.m_material_tracking_id,
       ic.approvalforinvoicing,
       ic.c_tax_override_id,
       ic.poreference,
       ic.dateacct,
       ic.deliverydate,
       ic.m_inout_id,
       ic.priceactual_net_effective,
       ic.istaxincluded,
       ic.qtyenteredtu,
       ic.qtytoinvoicebeforediscount,
       ic.istaxincluded_override,
       ic.c_invoice_candidate_headeraggregation_id,
       ic.c_invoice_candidate_headeraggregation_override_id,
       ic.headeraggregationkey_calc,
       ic.c_invoice_candidate_headeraggregation_effective_id,
       ic.headeraggregationkeybuilder_id,
       ic.first_ship_bplocation_id,
       ic.isinoutapprovedforinvoicing,
       ic.qtywithissues_effective,
       ic.processed_override,
       ic.processed_calc,
       ic.task_08848_fixed,
       ic.lineaggregationkeybuilder_id,
       ic.ispackagingmaterial,
       ic.m_pricelist_version_id,
       ic.qualityinvoicelinegrouptype
FROM c_invoice_candidate ic
WHERE (ic.c_invoice_candidate_id IN ( SELECT c_invoice_candidate_stale_qtyinvoiced_v.c_invoice_candidate_id
                                      FROM de_metas_invoicecandidate.c_invoice_candidate_stale_qtyinvoiced_v))
UNION
SELECT now() AS found,
       NULL::timestamp with time zone AS reenqueued,
       'N'::bpchar AS iserroracknowledged,
       'C_Invoice_Candidate_Missing_HeaderAggregation_Effective_ID_v (was C_Invoice_Candidate_Missing_Aggregation_Group_v)'::text AS problem_found_by,
       ic.ad_client_id,
       ic.ad_org_id,
       ic.c_invoice_candidate_id,
       ic.c_orderline_id,
       ic.created,
       ic.createdby,
       ic.isactive,
       ic.qtytoinvoice,
       ic.updated,
       ic.updatedby,
       ic.schedulerresult,
       ic.priceactual_override,
       ic.discount_override,
       ic.bill_bpartner_id,
       ic.bill_location_id,
       ic.bill_user_id,
       ic.invoicerule,
       ic.qtytoinvoicenetamt,
       ic.dateinvoiced,
       ic.istoclear,
       ic.m_product_id,
       ic.dateordered,
       ic.processed,
       ic.priceactual,
       ic.c_currency_id,
       ic.qtyordered,
       ic.qtydelivered,
       ic.qtyinvoiced,
       ic.qtytoinvoice_override,
       ic.qtytoinvoice_overridefulfilled,
       ic.c_charge_id,
       ic.bill_bpartner_override_id,
       ic.invoicerule_override,
       ic.m_pricingsystem_id,
       ic.discount,
       ic.netamttoinvoice,
       ic.netamtinvoiced,
       ic.c_invoice_candidate_agg_id,
       ic.lineaggregationkey,
       ic.lineaggregationkey_suffix,
       ic.c_ilcandhandler_id,
       ic.ad_table_id,
       ic.record_id,
       ic.iserror,
       ic.ad_note_id,
       ic.errormsg,
       ic.datetoinvoice,
       ic.datetoinvoice_override,
       ic.c_conversiontype_id,
       ic.invoicescheduleamtstatus,
       ic.ismanual,
       ic.description,
       ic.ad_user_incharge_id,
       ic.headeraggregationkey,
       ic.splitamt,
       ic.descriptionheader,
       ic.descriptionbottom,
       ic.priceentered,
       ic.priceentered_override,
       ic.issotrx,
       ic.qualitydiscountpercent_effective,
       ic.qualitynote_receiptschedule,
       ic.qualitydiscountpercent,
       ic.qualitydiscountpercent_override,
       ic.isindispute,
       ic.qtywithissues,
       ic.qtyorderedoverunder,
       ic.reasondiscount,
       ic.c_uom_id,
       ic.price_uom_id,
       ic.c_order_id,
       ic.c_activity_id,
       ic.c_tax_id,
       ic.qtytoinvoiceinpriceuom,
       ic.isprinted,
       ic.line,
       ic.c_doctypeinvoice_id,
       ic.m_material_tracking_id,
       ic.approvalforinvoicing,
       ic.c_tax_override_id,
       ic.poreference,
       ic.dateacct,
       ic.deliverydate,
       ic.m_inout_id,
       ic.priceactual_net_effective,
       ic.istaxincluded,
       ic.qtyenteredtu,
       ic.qtytoinvoicebeforediscount,
       ic.istaxincluded_override,
       ic.c_invoice_candidate_headeraggregation_id,
       ic.c_invoice_candidate_headeraggregation_override_id,
       ic.headeraggregationkey_calc,
       ic.c_invoice_candidate_headeraggregation_effective_id,
       ic.headeraggregationkeybuilder_id,
       ic.first_ship_bplocation_id,
       ic.isinoutapprovedforinvoicing,
       ic.qtywithissues_effective,
       ic.processed_override,
       ic.processed_calc,
       ic.task_08848_fixed,
       ic.lineaggregationkeybuilder_id,
       ic.ispackagingmaterial,
       ic.m_pricelist_version_id,
       ic.qualityinvoicelinegrouptype
FROM c_invoice_candidate ic
WHERE (ic.c_invoice_candidate_id IN ( SELECT c_invoice_candidate_missing_headeraggregation_effective_id_v.c_invoice_candidate_id
                                      FROM de_metas_invoicecandidate.c_invoice_candidate_missing_headeraggregation_effective_id_v))
UNION
SELECT now() AS found,
       NULL::timestamp with time zone AS reenqueued,
       'N'::bpchar AS iserroracknowledged,
       'C_Invoice_Candidate_Missing_C_Invoice_Candidate_Agg_ID_v'::text AS problem_found_by,
       ic.ad_client_id,
       ic.ad_org_id,
       ic.c_invoice_candidate_id,
       ic.c_orderline_id,
       ic.created,
       ic.createdby,
       ic.isactive,
       ic.qtytoinvoice,
       ic.updated,
       ic.updatedby,
       ic.schedulerresult,
       ic.priceactual_override,
       ic.discount_override,
       ic.bill_bpartner_id,
       ic.bill_location_id,
       ic.bill_user_id,
       ic.invoicerule,
       ic.qtytoinvoicenetamt,
       ic.dateinvoiced,
       ic.istoclear,
       ic.m_product_id,
       ic.dateordered,
       ic.processed,
       ic.priceactual,
       ic.c_currency_id,
       ic.qtyordered,
       ic.qtydelivered,
       ic.qtyinvoiced,
       ic.qtytoinvoice_override,
       ic.qtytoinvoice_overridefulfilled,
       ic.c_charge_id,
       ic.bill_bpartner_override_id,
       ic.invoicerule_override,
       ic.m_pricingsystem_id,
       ic.discount,
       ic.netamttoinvoice,
       ic.netamtinvoiced,
       ic.c_invoice_candidate_agg_id,
       ic.lineaggregationkey,
       ic.lineaggregationkey_suffix,
       ic.c_ilcandhandler_id,
       ic.ad_table_id,
       ic.record_id,
       ic.iserror,
       ic.ad_note_id,
       ic.errormsg,
       ic.datetoinvoice,
       ic.datetoinvoice_override,
       ic.c_conversiontype_id,
       ic.invoicescheduleamtstatus,
       ic.ismanual,
       ic.description,
       ic.ad_user_incharge_id,
       ic.headeraggregationkey,
       ic.splitamt,
       ic.descriptionheader,
       ic.descriptionbottom,
       ic.priceentered,
       ic.priceentered_override,
       ic.issotrx,
       ic.qualitydiscountpercent_effective,
       ic.qualitynote_receiptschedule,
       ic.qualitydiscountpercent,
       ic.qualitydiscountpercent_override,
       ic.isindispute,
       ic.qtywithissues,
       ic.qtyorderedoverunder,
       ic.reasondiscount,
       ic.c_uom_id,
       ic.price_uom_id,
       ic.c_order_id,
       ic.c_activity_id,
       ic.c_tax_id,
       ic.qtytoinvoiceinpriceuom,
       ic.isprinted,
       ic.line,
       ic.c_doctypeinvoice_id,
       ic.m_material_tracking_id,
       ic.approvalforinvoicing,
       ic.c_tax_override_id,
       ic.poreference,
       ic.dateacct,
       ic.deliverydate,
       ic.m_inout_id,
       ic.priceactual_net_effective,
       ic.istaxincluded,
       ic.qtyenteredtu,
       ic.qtytoinvoicebeforediscount,
       ic.istaxincluded_override,
       ic.c_invoice_candidate_headeraggregation_id,
       ic.c_invoice_candidate_headeraggregation_override_id,
       ic.headeraggregationkey_calc,
       ic.c_invoice_candidate_headeraggregation_effective_id,
       ic.headeraggregationkeybuilder_id,
       ic.first_ship_bplocation_id,
       ic.isinoutapprovedforinvoicing,
       ic.qtywithissues_effective,
       ic.processed_override,
       ic.processed_calc,
       ic.task_08848_fixed,
       ic.lineaggregationkeybuilder_id,
       ic.ispackagingmaterial,
       ic.m_pricelist_version_id,
       ic.qualityinvoicelinegrouptype
FROM c_invoice_candidate ic
WHERE (ic.c_invoice_candidate_id IN ( SELECT c_invoice_candidate_missing_c_invoice_candidate_agg_id_v.c_invoice_candidate_id
                                      FROM de_metas_invoicecandidate.c_invoice_candidate_missing_c_invoice_candidate_agg_id_v));

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


-- Field: Rechnung(167,D) -> Rechnung(263,D) -> EDI Fehlermeldung
-- Column: C_Invoice.EDIErrorMsg
-- 2026-02-04T17:18:19.357Z
UPDATE AD_Field SET DisplayLogic='(@EDI_ExportStatus/''X''@=''E'' | @EDI_ExportStatus/''X''@=''I'')',Updated=TO_TIMESTAMP('2026-02-04 17:18:19.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551574
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> DESADV
-- Column: M_InOut.EDI_Desadv_ID
-- 2026-02-04T17:22:35.409Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus@=CO | @DocStatus@=CL) & @EDI_Desadv_ID@>0',Updated=TO_TIMESTAMP('2026-02-04 17:22:35.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555713
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> EDI Fehlermeldung
-- Column: M_InOut.EDIErrorMsg
-- 2026-02-04T17:24:39.865Z
UPDATE AD_Field SET DisplayLogic='@EDI_ExportStatus@=''E'' | @EDI_ExportStatus@=''I''', IsActive='N',Updated=TO_TIMESTAMP('2026-02-04 17:24:39.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553215
;

-- Field: Lieferung(169,D) -> Lieferung(257,D) -> EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-04T17:25:48.292Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus@=CO | @DocStatus@=CL)',Updated=TO_TIMESTAMP('2026-02-04 17:25:48.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553214
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> EDI-Sendestatus
-- Column: C_Invoice.EDI_ExportStatus
-- 2026-02-04T17:27:10.866Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus/''X''@=''CO'' | @DocStatus/''X''@=''CL'' )',Updated=TO_TIMESTAMP('2026-02-04 17:27:10.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=551554
;

-- Field: Wareneingang(184,D) -> Wareneingang(296,D) -> DESADV
-- Column: M_InOut.EDI_Desadv_ID
-- 2026-02-04T17:28:07.991Z
UPDATE AD_Field SET DisplayLogic='@DocStatus@=CO | @DocStatus@=CL & @EDI_Desadv_ID@>0',Updated=TO_TIMESTAMP('2026-02-04 17:28:07.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555714
;

-- Field: Wareneingang(184,D) -> Wareneingang(296,D) -> EDI-Sendestatus
-- Column: M_InOut.EDI_ExportStatus
-- 2026-02-04T17:28:41.380Z
UPDATE AD_Field SET DisplayLogic='(@DocStatus@=CO | @DocStatus@=CL)', IsActive='N',Updated=TO_TIMESTAMP('2026-02-04 17:28:41.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553217
;

-- Field: Wareneingang(184,D) -> Wareneingang(296,D) -> EDI Fehlermeldung
-- Column: M_InOut.EDIErrorMsg
-- 2026-02-04T17:29:24.738Z
UPDATE AD_Field SET DisplayLogic='(@EDI_ExportStatus@=''E'' | @EDI_ExportStatus@=''I'')', IsActive='N',Updated=TO_TIMESTAMP('2026-02-04 17:29:24.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553218
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> EDI-Sendestatus
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- 2026-02-04T17:30:28.793Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2026-02-04 17:30:28.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=555162
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
