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

CREATE VIEW edi_cctop_invoic_v
            (edi_cctop_invoic_v_id, c_invoice_id, c_order_id, invoice_documentno, dateinvoiced, dateacct, poreference, dateordered, eancom_doctype, grandtotal, totallines, movementdate, shipment_documentno, totalvat, totaltaxbaseamt, receivergln, c_bpartner_location_id, sendergln, vataxid, iso_code, creditmemoreason, creditmemoreasontext, invoicableqtybasedon, countrycode, countrycode_3digit,
             ad_language, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, edidesadvdocumentno, surchargeamt, totallineswithsurchargeamt, totalvatwithsurchargeamt, grandtotalwithsurchargeamt)
AS
SELECT i.c_invoice_id                                                   AS edi_cctop_invoic_v_id,
       i.c_invoice_id,
       i.c_order_id,
       REGEXP_REPLACE(i.documentno::text, '\s+$'::text, ''::text)       AS invoice_documentno,
       i.dateinvoiced,
       i.dateacct,
       CASE
           WHEN REGEXP_REPLACE(i.poreference::text, '\s+$'::text, ''::text) <> ''::text AND i.poreference IS NOT NULL THEN REGEXP_REPLACE(i.poreference::text, '\s+$'::text, ''::text)::character varying
                                                                                                                      ELSE NULL::character varying
       END                                                              AS poreference,
       CASE
           WHEN COALESCE(i.dateordered, o.dateordered, ol.dateordered) IS NOT NULL THEN COALESCE(i.dateordered, o.dateordered, ol.dateordered)
                                                                                   ELSE NULL::timestamp without time zone
       END                                                              AS dateordered,
       CASE dt.docbasetype
           WHEN 'ARI'::bpchar THEN
               CASE
                   WHEN dt.docsubtype IS NULL OR TRIM(BOTH ' '::text FROM dt.docsubtype) = ''::text   THEN '380'::text
                   WHEN dt.docsubtype IS NULL OR TRIM(BOTH ' '::text FROM dt.docsubtype) = 'AQ'::text THEN '383'::text
                   WHEN dt.docsubtype IS NULL OR TRIM(BOTH ' '::text FROM dt.docsubtype) = 'AP'::text THEN '84'::text
                                                                                                      ELSE 'ERROR EAN_DocType'::text
               END
           WHEN 'ARC'::bpchar THEN
               CASE
                   WHEN dt.docsubtype IS NULL OR (TRIM(BOTH ' '::text FROM dt.docsubtype) = ANY (ARRAY ['CQ'::text, 'CS'::text])) THEN '381'::text
                   WHEN dt.docsubtype IS NULL OR TRIM(BOTH ' '::text FROM dt.docsubtype) = 'CR'::text                             THEN '83'::text
                                                                                                                                  ELSE 'ERROR EAN_DocType'::text
               END
                              ELSE 'ERROR EAN_DocType'::text
       END                                                              AS eancom_doctype,
       i.grandtotal,
       i.totallines,
       CASE
           WHEN dt.docsubtype::text = 'CS'::text THEN NULL::timestamp without time zone
                                                 ELSE COALESCE(shipment.movementdate, iomd.movementdate)
       END                                                              AS movementdate,
       ((SELECT STRING_AGG(DISTINCT edi.documentno::text, ','::text) AS string_agg
         FROM c_invoiceline icl
                  JOIN c_invoice_line_alloc inalloc ON icl.c_invoiceline_id = inalloc.c_invoiceline_id
                  JOIN c_invoicecandidate_inoutline candinout ON inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                  JOIN m_inoutline inoutline ON candinout.m_inoutline_id = inoutline.m_inoutline_id
                  JOIN m_inout "inout" ON inoutline.m_inout_id = "inout".m_inout_id
                  JOIN edi_desadv edi ON "inout".edi_desadv_id = edi.edi_desadv_id
         WHERE icl.c_invoice_id = i.c_invoice_id))::character varying   AS shipment_documentno,
       taxandsurchage.totalvat,
       taxandsurchage.totaltaxbaseamt,
       COALESCE(rbp.ediinvoicrecipientgln, rl.gln)                      AS receivergln,
       rl.c_bpartner_location_id,
       (SELECT DISTINCT ON ((REGEXP_REPLACE(sl.gln::text, '\s+$'::text, ''::text))) REGEXP_REPLACE(sl.gln::text, '\s+$'::text, ''::text) AS gln
        FROM c_bpartner_location sl
        WHERE TRUE
          AND sl.c_bpartner_id = sp.c_bpartner_id
          AND sl.isremitto = 'Y'::bpchar
          AND sl.gln IS NOT NULL
          AND sl.isactive = 'Y'::bpchar)                                AS sendergln,
       REGEXP_REPLACE(sp.vataxid::text, '\s+$'::text, ''::text)         AS vataxid,
       c.iso_code,
       REGEXP_REPLACE(i.creditmemoreason::text, '\s+$'::text, ''::text) AS creditmemoreason,
       (SELECT REGEXP_REPLACE(ad_ref_list.name::text, '\s+$'::text, ''::text) AS name
        FROM ad_ref_list
        WHERE ad_ref_list.ad_reference_id = 540014::numeric
          AND ad_ref_list.value::text = i.creditmemoreason::text)       AS creditmemoreasontext,
       (SELECT CASE
                   WHEN ARRAY_LENGTH(ARRAY_AGG(DISTINCT ol_1.invoicableqtybasedon), 1) = 1 THEN (ARRAY_AGG(DISTINCT ol_1.invoicableqtybasedon))[1]
                                                                                           ELSE NULL::character varying
               END AS "case"
        FROM c_orderline ol_1
        WHERE ol_1.c_order_id = o.c_order_id)                           AS invoicableqtybasedon,
       cc.countrycode,
       cc.countrycode_3digit,
       cc.countrycode                                                   AS ad_language,
       i.ad_client_id,
       i.ad_org_id,
       i.created,
       i.createdby,
       i.updated,
       i.updatedby,
       i.isactive,
       (SELECT STRING_AGG(DISTINCT edi.documentno::text, ','::text) AS string_agg
        FROM c_invoiceline icl
                 JOIN c_invoice_line_alloc inalloc ON icl.c_invoiceline_id = inalloc.c_invoiceline_id
                 JOIN c_invoicecandidate_inoutline candinout ON inalloc.c_invoice_candidate_id = candinout.c_invoice_candidate_id
                 JOIN m_inoutline inoutline ON candinout.m_inoutline_id = inoutline.m_inoutline_id
                 JOIN m_inout "inout" ON inoutline.m_inout_id = "inout".m_inout_id
                 JOIN edi_desadv edi ON "inout".edi_desadv_id = edi.edi_desadv_id
        WHERE icl.c_invoice_id = i.c_invoice_id)                        AS edidesadvdocumentno,
       taxandsurchage.surchargeamt,
       taxandsurchage.totallineswithsurchargeamt,
       taxandsurchage.totalvatwithsurchargeamt,
       taxandsurchage.grandtotalwithsurchargeamt
FROM c_invoice i
         LEFT JOIN c_doctype dt ON dt.c_doctype_id = i.c_doctypetarget_id
         LEFT JOIN c_order o ON o.c_order_id = i.c_order_id
         LEFT JOIN edi_desadv desadv ON desadv.edi_desadv_id = o.edi_desadv_id
         LEFT JOIN LATERAL ( SELECT io.documentno,
                                    io.movementdate
                             FROM m_inout io
                                      LEFT JOIN c_invoice inv ON io.m_inout_id = inv.m_inout_id AND inv.c_invoice_id = i.c_invoice_id
                             WHERE io.c_order_id = o.c_order_id
                               AND (io.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
                             ORDER BY inv.c_invoice_id, io.created
                             LIMIT 1) shipment ON TRUE
         LEFT JOIN c_bpartner rbp ON rbp.c_bpartner_id = i.c_bpartner_id
         LEFT JOIN c_bpartner_location rl ON rl.c_bpartner_location_id = i.c_bpartner_location_id
         LEFT JOIN c_location l ON l.c_location_id = rl.c_location_id
         LEFT JOIN c_currency c ON c.c_currency_id = i.c_currency_id
         LEFT JOIN c_country cc ON cc.c_country_id = l.c_country_id
         LEFT JOIN c_bpartner sp ON sp.ad_orgbp_id = i.ad_org_id
         LEFT JOIN LATERAL ( SELECT i_1.c_invoice_id,
                                    MIN(o_1.dateordered) AS dateordered
                             FROM c_invoice i_1
                                      JOIN c_invoiceline il ON i_1.c_invoice_id = il.c_invoice_id
                                      JOIN c_orderline ol_1 ON il.c_orderline_id = ol_1.c_orderline_id
                                      JOIN c_order o_1 ON ol_1.c_order_id = o_1.c_order_id
                             GROUP BY i_1.c_invoice_id
                             HAVING COUNT(DISTINCT ol_1.dateordered) = 1) ol ON ol.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i_1.c_invoice_id,
                                    MIN(io.movementdate) AS movementdate
                             FROM c_invoice i_1
                                      JOIN c_invoiceline il ON i_1.c_invoice_id = il.c_invoice_id
                                      JOIN c_orderline ol_1 ON il.c_orderline_id = ol_1.c_orderline_id
                                      JOIN c_order o_1 ON ol_1.c_order_id = o_1.c_order_id
                                      JOIN m_inout io ON o_1.c_order_id = io.c_order_id AND (io.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
                             GROUP BY i_1.c_invoice_id
                             HAVING COUNT(DISTINCT io.movementdate) = 1) iomd ON iomd.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT i_1.c_invoice_id,
                                    MIN(io.documentno::text) AS documentno
                             FROM c_invoice i_1
                                      JOIN c_invoiceline il ON i_1.c_invoice_id = il.c_invoice_id
                                      JOIN c_orderline ol_1 ON il.c_orderline_id = ol_1.c_orderline_id
                                      JOIN c_order o_1 ON ol_1.c_order_id = o_1.c_order_id
                                      JOIN m_inout io ON o_1.c_order_id = io.c_order_id AND (io.docstatus = ANY (ARRAY ['CO'::bpchar, 'CL'::bpchar]))
                             GROUP BY i_1.c_invoice_id
                             HAVING COUNT(DISTINCT io.documentno) = 1) iodn ON iodn.c_invoice_id = i.c_invoice_id
         LEFT JOIN LATERAL ( SELECT edi_cctop_901_991_v.c_invoice_id,
                                    SUM(edi_cctop_901_991_v.taxamt)                                                                  AS totalvat,
                                    SUM(edi_cctop_901_991_v.taxbaseamt)                                                              AS totaltaxbaseamt,
                                    SUM(edi_cctop_901_991_v.surchargeamt)                                                            AS surchargeamt,
                                    SUM(edi_cctop_901_991_v.taxbaseamtwithsurchargeamt)                                              AS totallineswithsurchargeamt,
                                    SUM(edi_cctop_901_991_v.taxamtwithsurchargeamt)                                                  AS totalvatwithsurchargeamt,
                                    SUM(edi_cctop_901_991_v.taxbaseamtwithsurchargeamt + edi_cctop_901_991_v.taxamtwithsurchargeamt) AS grandtotalwithsurchargeamt
                             FROM edi_cctop_901_991_v
                             WHERE edi_cctop_901_991_v.c_invoice_id = i.c_invoice_id
                             GROUP BY edi_cctop_901_991_v.c_invoice_id) taxandsurchage ON TRUE
;

ALTER TABLE edi_cctop_invoic_v
    OWNER TO metasfresh
;

