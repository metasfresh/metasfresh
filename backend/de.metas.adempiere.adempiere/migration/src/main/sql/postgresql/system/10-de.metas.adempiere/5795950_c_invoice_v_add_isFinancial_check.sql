/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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


CREATE OR REPLACE VIEW c_invoice_v AS
SELECT i.c_invoice_id,
       i.ad_client_id,
       i.ad_org_id,
       i.isactive,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.issotrx,
       i.documentno,
       i.docstatus,
       i.docaction,
       i.processing,
       i.processed,
       i.c_doctype_id,
       i.c_doctypetarget_id,
       i.c_order_id,
       i.description,
       i.isapproved,
       i.istransferred,
       i.salesrep_id,
       i.dateinvoiced,
       i.dateprinted,
       i.dateacct,
       i.c_bpartner_id,
       i.c_bpartner_location_id,
       i.ad_user_id,
       i.poreference,
       i.dateordered,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.paymentrule,
       i.c_paymentterm_id,
       i.c_charge_id,
       i.m_pricelist_id,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id,
       i.isprinted,
       i.isdiscountprinted,
       i.ispaid,
       i.isindispute,
       i.ispayschedulevalid,
       NULL::numeric                                                                    AS c_invoicepayschedule_id,
       i.invoicecollectiontype,
       i.dunninggrace,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.chargeamt * (-1)::numeric
                                                                              ELSE i.chargeamt
       END                                                                              AS chargeamt,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.totallines * (-1)::numeric
                                                                              ELSE i.totallines
       END                                                                              AS totallines,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN i.grandtotal * (-1)::numeric
                                                                              ELSE i.grandtotal
       END                                                                              AS grandtotal,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN (-1.0)
                                                                              ELSE 1.0
       END                                                                              AS multiplier,
       CASE
           WHEN charat(d.docbasetype::character varying, 2)::text = ANY (ARRAY ['P'::text, 'E'::text]) THEN (-1.0)
                                                                                                       ELSE 1.0
       END                                                                              AS multiplierap,
       d.docbasetype,
       paymenttermduedate(i.c_paymentterm_id, i.dateinvoiced::timestamp with time zone) AS duedate
FROM c_invoice i
         JOIN c_doctype d ON i.c_doctype_id = d.c_doctype_id
WHERE i.ispayschedulevalid <> 'Y'::bpchar
  AND i.isFinancial = 'Y'::bpchar
UNION
SELECT i.c_invoice_id,
       i.ad_client_id,
       i.ad_org_id,
       i.isactive,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.issotrx,
       i.documentno,
       i.docstatus,
       i.docaction,
       i.processing,
       i.processed,
       i.c_doctype_id,
       i.c_doctypetarget_id,
       i.c_order_id,
       i.description,
       i.isapproved,
       i.istransferred,
       i.salesrep_id,
       i.dateinvoiced,
       i.dateprinted,
       i.dateacct,
       i.c_bpartner_id,
       i.c_bpartner_location_id,
       i.ad_user_id,
       i.poreference,
       i.dateordered,
       i.c_currency_id,
       i.c_conversiontype_id,
       i.paymentrule,
       i.c_paymentterm_id,
       i.c_charge_id,
       i.m_pricelist_id,
       i.c_campaign_id,
       i.c_project_id,
       i.c_activity_id,
       i.isprinted,
       i.isdiscountprinted,
       i.ispaid,
       i.isindispute,
       i.ispayschedulevalid,
       ips.c_invoicepayschedule_id,
       i.invoicecollectiontype,
       i.dunninggrace,
       NULL::numeric AS chargeamt,
       NULL::numeric AS totallines,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN ips.dueamt * (-1)::numeric
                                                                              ELSE ips.dueamt
       END           AS grandtotal,
       CASE
           WHEN charat(d.docbasetype::character varying, 3)::text = 'C'::text THEN (-1)
                                                                              ELSE 1
       END           AS multiplier,
       CASE
           WHEN charat(d.docbasetype::character varying, 2)::text = ANY (ARRAY ['P'::text, 'E'::text]) THEN (-1)
                                                                                                       ELSE 1
       END           AS multiplierap,
       d.docbasetype,
       ips.duedate
FROM c_invoice i
         JOIN c_doctype d ON i.c_doctype_id = d.c_doctype_id
         JOIN c_invoicepayschedule ips ON i.c_invoice_id = ips.c_invoice_id
WHERE i.ispayschedulevalid = 'Y'::bpchar
  AND ips.isvalid = 'Y'::bpchar
  AND i.isFinancial = 'Y'::bpchar
;
