/*
 * #%L
 * de.metas.fresh.base
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

CREATE INDEX IF NOT EXISTS m_hu_trx_line_parent_hu_trx_line_id ON M_HU_Trx_Line(parent_hu_trx_line_id)
;


DROP FUNCTION IF EXISTS "de.metas.handlingunits".recursive_hu_trace_sub(trxline integer[]);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".recursive_hu_trace_sub(trxline integer[]) RETURNS TABLE
                                                                                                      (
                                                                                                          M_hu_trx_line_id numeric,
                                                                                                          m_hu_id numeric

                                                                                                      )
AS $$

DECLARE

    new_trxline Integer[] := Array[0];
    r record;
    v_sql text := 'SELECT m_hu_trx_line_id,m_hu_id FROM M_HU_Trx_Line WHERE M_HU_Trx_Line_ID = ANY( ARRAY[';

BEGIN
    for r in (

        SELECT   COALESCE(trx_line3.M_HU_Trx_Line_id::integer, trx_line2.M_HU_Trx_Line_id::integer) AS M_HU_Trx_Line_id, trx_line2.M_HU_ID as M_HU_id FROM M_HU_Trx_Line trx_line --, trx_line2.M_HU_Trx_Line_id::integer AS passed_trx_id
                                                                                                                                                               LEFT OUTER JOIN M_HU_Trx_hdr trx_hdr ON trx_line.M_HU_Trx_hdr_ID = trx_hdr.M_HU_Trx_Hdr_ID
                                                                                                                                                               LEFT OUTER JOIN M_HU_Trx_Line trx_line2 ON trx_line2.parent_hu_trx_line_id = trx_line.M_HU_Trx_Line_ID

            --find another trxline/header for the same hu
                                                                                                                                                               LEFT OUTER JOIN M_HU_Trx_Line trx_line3 ON trx_line2.M_HU_ID = trx_line3.M_HU_ID   AND  trx_line2.M_HU_Trx_Line_ID != trx_line3.M_HU_Trx_Line_ID AND trx_line.M_HU_Trx_Line_ID != trx_line3.M_HU_Trx_Line_ID
            AND 	trx_line3.qty!=0

        WHERE trx_line.M_HU_Trx_Line_ID = ANY($1)
    ) loop
            new_trxline := new_trxline || r.M_HU_Trx_Line_id;

        END LOOP;
    RETURN query execute v_sql || array_to_string(new_trxline,',') || ']);' ;
END;
$$ LANGUAGE plpgsql;
COMMENT ON FUNCTION "de.metas.handlingunits".recursive_hu_trace_sub(integer[]) IS '
 this function is currently used in "de.metas.handlingunits".recursive_hu_trace function
 use: it takes an array of M_HU_Trx_Line(s) and returns for each M_HU_Trx_Line the directly related M_HU_Trx_Line(s) which come from splitting an HU.
 in case an HU was split, you will have at least 2 M_HU_Trx_Line(s) with qty!=0 (one with + and one with -) which have on parent_hu_trx_line_id the M_HU_Trx_Line_ID of each other. The new split HU lies in it''s parent.
 if the new HU is split again, then there is another M_HU_Trx_Line (a third one) with this HU.
 that''s why first we search for the "latest" M_HU_Trx_Line, and if there is none we stay with the parent.
 You can picture this function in layers:
 Layer 1 is the transaction you have + the parent. Parent is the split HU.
 Layer 2 is the third transaction, with the same HU from Layer 1, and it announces a second split. If there is no second split, then there is just 1 layer. This is where this function stops.
 To get to layer 3, you just have to call this function with the transaction(s) from Layer 2. And so on, until the HU is not split any more

SEE https://github.com/metasfresh/metasfresh/issues/262
';

DROP FUNCTION IF EXISTS "de.metas.handlingunits".recursive_hu_trace(trxline integer);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".recursive_hu_trace(trxline integer) RETURNS TABLE
                                                                                                (
                                                                                                    trx_id numeric,
                                                                                                    hu_id numeric
                                                                                                )

AS $$
DECLARE

    r record;
    rez integer[]:= Array[$1];
    all_trx integer[] := '{}';
    last_trx integer[] := '{}';
    v_sql text := 'SELECT m_hu_trx_line_id,m_hu_id FROM M_HU_Trx_Line WHERE M_HU_Trx_Line_ID = ANY( ARRAY[';

BEGIN
    while(0 != any(rez))
        LOOP
            for r in (SELECT m_hu_trx_line_id::integer, m_hu_id::integer FROM "de.metas.handlingunits".recursive_hu_trace_sub(rez)) loop
                    if (Array[r.m_hu_trx_line_id] <@ all_trx)
                    then last_trx := last_trx;
                        --RAISE NOTICE 'removed %',r.m_hu_trx_line_id;
                    else
                        last_trx := last_trx || r.m_hu_trx_line_id;
                    end if;
                    --	RAISE NOTICE 'last_trx %', last_trx;
                    all_trx := all_trx || r.m_hu_trx_line_id;
                    --	RAISE NOTICE 'all_trx %', all_trx;

                end loop;

            rez := last_trx;-----------------
            --RAISE NOTICE 'rez %', rez;
            last_trx := Array[0];

        END LOOP;
    RETURN query execute v_sql || array_to_string(all_trx,',') || ']::integer[]);' ;
END;
$$ LANGUAGE plpgsql;
COMMENT ON FUNCTION "de.metas.handlingunits".recursive_hu_trace(integer) IS '
-----
Usage
-----
 use: it takes an M_HU_Trx_Line and returns all HUs and transactions which come from splitting the HU from the given M_HU_Trx_Line. Note that it will return HU duplicates so you might want to select distinct if you only need HUs
 you can uncomment "RAISE NOTICE" to debug (but don''t forget to comment them back when done)
 this function calls recursively "de.metas.handlingunits".recursive_hu_trace_sub function
 first it calls the function with the given parameter, and then with the set of results (rez) of the same function (if function returns 001 and 002, we call the function with [001, 002])
 to avoid infinite loop, we save each transaction in a variable (all_trx) and on each loop we check if the transaction was already called. This way we call the function once per transaction.
 the function will return all hus and transaction_ids from all_trx

 Summary
 Parameter: trxline = the transaction from the HU you want to start with
 Variables:
			last_trx = list of transactions that result from calling recursive_hu_trace_sub function. When for loop ends it becomes 0
			rez =  It''s used to call recursive_hu_trace_sub function. First is trxline parameter and then takes the value of last_trx before it becomes 0.
			all_trx = list of all transactions returned by the recursive_hu_trace_sub function
			v_sql = a query which is used to return all hus and transaction_ids for all_trx

--------
Testcase
--------
Testcase to check the function. It also helps if we need to modify recursive_hu_trace or recursive_hu_trace_sub

I did a purchase order with 10 IFCOx5. Went to receipt pos and moved the HUs in an empty warehouse (for visibility purpose).
Went to Handling Units pos, opened my warehouse and split my HUs 3 times as following:

My HUs: (5 x 10 x 1)
LU 1000650 (active)
TU 1000651,1000653,1000655,1000657,1000659, (destroyed by split)
   1000661,1000663,1000665,1000667,1000669 (active)

1st Split (5 x 5 x 1 from 1000650)
LU 1000671 (active)
TU 1000672,1000674,1000676 (destroyed by split)
   1000678,1000680 (active)

2nd Split (5 x 3 x 1 from 1000671)
LU 1000682 (destroyed by split)
TU 1000683, 1000685, 1000687 (destroyed by split)

3rd Split (5 x 2 x 2 from 1000682)
LU 1000689 (active)
TU 1000690, 1000692 (active)

LU 1000694 (active)
TU 1000695 (active)
------------------------

Run the function for the M_HU_TRX_Line(s) of my HUs
find the trx for e.g. TU 1000651 : select m_hu_trx_line_id,* from m_hu_trx_line where m_hu_id = 1000651
select * from "de.metas.handlingunits".recursive_hu_trace(m_hu_trx_line_id)

LU 1000650: 1000650

TU:
1000651: 1000651, 1000672, 1000683, 1000690
1000653: 1000653, 1000674, 1000685, 1000692, 1000671
1000655: 1000655, 1000676, 1000687, 1000695
1000657: 1000657, 1000678, 1000671
1000659: 1000659, 1000680, 1000671
1000661: 1000661, 1000671
1000663: 1000663
1000665: 1000665
1000667: 1000667
1000669: 1000669

Run the function for the M_HU_TRX_Line(s) of last split HUs (the other way around)

LU 1000689: 1000655, 1000676, 1000687, 1000695, 1000653, 1000674, 1000685, 1000692, 1000651, 1000672, 1000683, 1000690

TU
1000690: 1000651, 1000672, 1000683, 1000690
1000692: 1000653, 1000674, 1000685, 1000692

(note: here i put only the distinct HUs. They can appear more because of multiple transaction lines)

See task https://github.com/metasfresh/metasfresh/issues/262
';

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.trace_report(numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        numeric,
                                                                        character varying,
                                                                        numeric,
                                                                        character varying(3))
;


DROP FUNCTION IF EXISTS "de.metas.handlingunits".hu_assigment_tracking(in m_hu_assignment_id numeric);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".hu_assigment_tracking(in m_hu_assignment_id numeric)
    RETURNS TABLE
            (m_hu_assignment_id numeric,
             record_id numeric)
AS
$$

SELECT  c_huas.m_hu_assignment_id as m_hu_assignment_id,
        c_huas.record_id as record_id

FROM M_HU_Assignment c_huas

         INNER JOIN M_HU_Assignment huas ON huas.m_hu_assignment_id = $1

WHERE c_huas.isActive = 'Y' and c_huas.m_hu_assignment_id != $1 AND c_huas.m_tu_hu_id = huas.m_tu_hu_id
  AND c_huas.ad_table_id = get_table_id('M_InOutLine')
  AND c_huas.M_TU_HU_ID is not null
UNION ALL

(

    SELECT  c_huas.m_hu_assignment_id as m_hu_assignment_id,
            c_huas.record_id as record_id

    FROM M_HU_Assignment c_huas
             INNER JOIN M_HU_Assignment huas ON huas.m_hu_assignment_id = $1
             INNER JOIN M_HU_Trx_Line trx ON huas.M_TU_HU_ID = trx.M_HU_ID AND trx.isActive = 'Y'

    WHERE c_huas.isActive = 'Y' and c_huas.m_hu_assignment_id != $1
      AND c_huas.m_tu_hu_id = ANY  (ARRAY( SELECT distinct hu_id from "de.metas.handlingunits".recursive_hu_trace(trx.M_HU_Trx_Line_ID::integer)))
      AND c_huas.ad_table_id = get_table_id('M_InOutLine')
      AND c_huas.M_TU_HU_ID is not null
)
$$
    LANGUAGE sql STABLE;



CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.trace_report(
    IN p_org_id                  numeric,
    IN p_period_st_id            numeric,
    IN p_period_end_id           numeric,
    IN p_activity_id             numeric,
    IN p_bpartner_id             numeric,
    IN p_product_id              numeric,
    IN p_issotrx                 character varying,
    IN p_attributesetinstance_id numeric,
    IN p_Language                Character Varying(6))
    RETURNS TABLE
            (
                dateordered     timestamp without time zone,
                documentno      character varying,
                o_bp_value      character varying,
                o_bp_name       character varying,
                o_p_value       character varying,
                o_p_name        character varying,
                attributes      text,
                price           numeric,
                total           numeric,
                currency        character,
                o_uom           character varying,
                o_qty           numeric,
                movementdate    timestamp without time zone,
                orderdocumentno character varying,
                io_bp_value     character varying,
                io_bp_name      character varying,
                io_qty          numeric,
                io_uom          character varying,
                isPOfromSO      character varying
            )
AS
$$

SELECT a.DateOrdered,
       a.oDocumentNo,
       a.bp_value,
       a.bp_name,
       a.p_value,
       a.p_name,
       a.attributes,
       a.price,
       a.total,
       a.currency,
       a.uomsymbol,
       a.qty,

       --inout part
       a.movementdate,
       a.orderdocumentno,
       a.io_bp_value,
       a.io_bp_name,
       SUM(a.io_qty),
       a.io_uom,
       'N' AS isPOfromSO
FROM (SELECT DISTINCT o.DateOrdered,
                      o.DocumentNo                                                                                    AS ODocumentNo,
                      bp.Value                                                                                        AS bp_value,
                      bp.Name                                                                                         AS bp_name,
                      p.Value                                                                                         AS p_value,
                      COALESCE(pt.Name, p.Name)                                                                       AS p_name,
                      (SELECT attributes_value
                       FROM de_metas_endcustomer_fresh_reports.get_attributes_value(o_iol.M_AttributeSetInstance_ID)) AS attributes,
                      ol.PriceEntered                                                                                 AS price,
                      ol.linenetamt                                                                                   AS total,
                      c.iso_code                                                                                      AS currency,
                      COALESCE(uomt.uomsymbol, uom.uomsymbol)                                                         AS uomsymbol,
                      ol.qtyEntered                                                                                   AS qty,

                      --inout part
                      c_io.movementdate,
                      c_o.documentno                                                                                  AS orderdocumentno,
                      c_bp.value                                                                                      AS io_bp_value,
                      c_bp.name                                                                                       AS io_bp_name,
                      c_iol.qtyentered                                                                                AS io_qty,
                      COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol)                                                     AS io_uom,

                      --order and sales inout docno (for tracing purpose). Not used in reports
                      c_io.DocumentNo                                                                                 AS counterdocno


      FROM C_Order o

               INNER JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
               INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
               INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
               LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_Language

               LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
               INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
               LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_Language
               INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID


               INNER JOIN C_Period per_st ON p_period_st_id = per_st.C_Period_ID
               INNER JOIN C_Period per_end ON p_period_end_id = per_end.C_Period_ID

          --order's inout and hus
               INNER JOIN M_InOutLine o_iol ON ol.C_OrderLine_ID = o_iol.C_OrderLine_ID
               INNER JOIN M_InOut o_io ON o_iol.M_InOut_ID = o_io.M_InOut_ID
               INNER JOIN M_HU_Assignment huas ON huas.ad_table_id = get_table_id('M_InOutLine') AND huas.Record_ID = o_iol.M_InOutLine_ID

          --counter inout's hus and inout

               INNER JOIN M_InOutLine c_iol ON c_iol.M_InOutLine_ID = ANY (ARRAY(SELECT Record_ID FROM "de.metas.handlingunits".hu_assigment_tracking(huas.m_hu_assignment_id)))
               INNER JOIN M_InOut c_io ON c_iol.M_InOut_ID = c_io.M_InOut_ID AND c_io.isSOTrx != o_io.isSOTrx
               INNER JOIN C_InvoiceCandidate_InOutLine iciol ON c_iol.M_InOutLine_ID = iciol.M_InOutLine_ID
               INNER JOIN C_Invoice_Candidate ic ON iciol.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID


          --data for inout
               INNER JOIN C_OrderLine c_ol ON c_iol.C_OrderLine_ID = c_ol.C_OrderLine_ID
               INNER JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID
               INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_io.C_BPartner_ID
               INNER JOIN C_UOM c_uom ON ic.price_UOM_ID = c_uom.C_UOM_ID
               LEFT OUTER JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = p_Language


      WHERE o.AD_Org_ID = COALESCE(p_org_id, o.AD_Org_ID)
        AND per_st.startdate::date <= o.DateOrdered::date
        AND per_end.enddate::date >= o.DateOrdered::date
        AND (ol.C_Activity_ID = p_Activity_ID OR p_Activity_ID IS NULL)
        AND (o.C_BPartner_ID = p_bpartner_id OR p_bpartner_id IS NULL)
        AND (ol.M_Product_ID = p_product_id OR p_product_id IS NULL)
        AND o.isSOTrx = p_issotrx
        AND o.docStatus IN ('CO', 'CL')
        AND o_io.docStatus IN ('CO', 'CL')
        AND c_o.docStatus IN ('CO', 'CL')
        AND c_io.docStatus IN ('CO', 'CL')
        AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)

        AND (CASE
                 WHEN EXISTS (SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = p_attributesetinstance_id)
                     -- ... then apply following filter:
                     THEN (
                     -- Take lines where the attributes of the current InoutLine's asi are in the parameter asi and their Values Match
                     EXISTS (SELECT 0
                             FROM report.fresh_Attributes_ConcreteADR a -- a = Attributes from inout line, pa = Parameter Attributes
                                      INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID = 6487321
                                 AND a.at_value = pa.at_value -- same attribute
                                 AND (CASE
                                          WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                      ELSE a.ai_value = pa.ai_value
                                      END) --same value
                             WHERE a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID)
                         -- Dismiss lines where the Attributes in the Parameter are not in the inoutline's asi
                         AND NOT EXISTS (SELECT 0
                                         FROM report.fresh_Attributes_ConcreteADR pa
                                                  LEFT OUTER JOIN report.fresh_Attributes_ConcreteADR a
                                                                  ON a.at_value = pa.at_value AND
                                                                     (CASE
                                                                          WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                                                      ELSE a.ai_value = pa.ai_value
                                                                      END)
                                                                      AND a.M_AttributeSetInstance_ID = o_iol.M_AttributeSetInstance_ID
                                         WHERE pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                                           AND a.M_AttributeSetInstance_ID IS NULL)
                     )
                     ELSE TRUE
             END)) a

GROUP BY a.DateOrdered,
         a.oDocumentNo,
         a.bp_value,
         a.bp_name,
         a.p_value,
         a.p_name,
         a.attributes,
         a.price,
         a.currency,
         a.uomsymbol,
         a.total,
         a.qty,
         --inout part
         a.movementdate,
         a.orderdocumentno,
         a.io_bp_value,
         a.io_bp_name,
         a.io_uom


UNION

(SELECT o.DateOrdered,
        o.DocumentNo                                                                                                         AS ODocumentNo,
        bp.Value                                                                                                             AS bp_value,
        bp.Name                                                                                                              AS bp_name,
        p.Value                                                                                                              AS p_value,
        COALESCE(pt.Name, p.Name)                                                                                            AS p_name,
        (SELECT attributes_value FROM de_metas_endcustomer_fresh_reports.get_attributes_value(ol.M_AttributeSetInstance_ID)) AS attributes,
        ol.PriceEntered                                                                                                      AS price,
        ol.linenetamt                                                                                                        AS total,
        c.iso_code                                                                                                           AS currency,
        COALESCE(uomt.uomsymbol, uom.uomsymbol)                                                                              AS uomsymbol,
        ol.qtyEntered                                                                                                        AS qty,

        --inout part
        c_o.dateordered                                                                                                      AS movementdate,
        c_o.documentno                                                                                                       AS orderdocumentno,
        c_bp.value                                                                                                           AS io_bp_value,
        c_bp.name                                                                                                            AS io_bp_name,
        c_ol.qtyentered                                                                                                      AS io_qty,
        COALESCE(c_uomt.uomsymbol, c_uom.uomsymbol)                                                                          AS io_uom,
        'Y'                                                                                                                  AS isPOfromSO


 FROM C_Order o
          JOIN C_OrderLine ol ON o.C_Order_ID = ol.C_Order_ID
          JOIN C_PO_OrderLine_Alloc ol_Alloc
               ON ((o.issotrx = 'Y' AND ol.C_OrderLine_ID = ol_Alloc.c_so_orderline_id)
                   OR (o.issotrx = 'N' AND ol.C_OrderLine_ID = ol_Alloc.c_po_orderline_id))
          JOIN C_OrderLine c_ol
               ON ((o.issotrx = 'Y' AND c_ol.C_OrderLine_ID = ol_Alloc.c_po_orderline_id)
                   OR (o.issotrx = 'N' AND c_ol.C_OrderLine_ID = ol_Alloc.c_so_orderline_id))
          JOIN C_Order c_o ON c_ol.C_Order_ID = c_o.C_Order_ID
          INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID
          INNER JOIN M_Product p ON ol.M_Product_ID = p.M_Product_ID
          LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = p_Language
          LEFT OUTER JOIN M_Product_Category pc ON p.M_Product_Category_ID = pc.M_Product_Category_ID
          INNER JOIN C_UOM uom ON ol.Price_UOM_ID = uom.C_UOM_ID
          LEFT OUTER JOIN C_UOM_Trl uomt ON uom.C_UOM_ID = uomt.C_UOM_ID AND uomt.AD_Language = p_Language
          INNER JOIN C_Currency c ON ol.C_Currency_ID = c.C_Currency_ID


          INNER JOIN C_Period per_st ON p_period_st_id = per_st.C_Period_ID
          INNER JOIN C_Period per_end ON p_period_end_id = per_end.C_Period_ID

          INNER JOIN C_BPartner c_bp ON c_bp.C_BPartner_ID = c_o.C_BPartner_ID
          INNER JOIN C_UOM c_uom ON c_ol.price_UOM_ID = c_uom.C_UOM_ID
          LEFT OUTER JOIN C_UOM_Trl c_uomt ON c_uom.C_UOM_ID = c_uomt.C_UOM_ID AND c_uomt.AD_Language = p_Language

 WHERE o.AD_Org_ID = COALESCE(p_org_id, o.AD_Org_ID)
   AND per_st.startdate::date <= o.DateOrdered::date
   AND per_end.enddate::date >= o.DateOrdered::date
   AND (ol.C_Activity_ID = p_Activity_ID OR p_Activity_ID IS NULL)
   AND (o.C_BPartner_ID = p_bpartner_id OR p_bpartner_id IS NULL)
   AND (ol.M_Product_ID = p_product_id OR p_product_id IS NULL)
   AND o.isSOTrx = p_issotrx
   AND o.docStatus IN ('CO', 'CL')
   AND c_o.docStatus IN ('CO', 'CL')
   AND COALESCE(pc.M_Product_Category_ID, -1) != getSysConfigAsNumeric('PackingMaterialProductCategoryID', ol.AD_Client_ID, ol.AD_Org_ID)

   AND (CASE
            WHEN EXISTS (SELECT ai_value FROM report.fresh_Attributes WHERE M_AttributeSetInstance_ID = p_attributesetinstance_id)
                -- ... then apply following filter:
                THEN (
                -- Take lines where the attributes of the current InoutLine's asi are in the parameter asi and their Values Match
                EXISTS (SELECT 0
                        FROM report.fresh_Attributes_ConcreteADR a -- a = Attributes from order line, pa = Parameter Attributes
                                 INNER JOIN report.fresh_Attributes_ConcreteADR pa ON pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                            AND a.at_value = pa.at_value -- same attribute
                            AND (CASE
                                     WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                 ELSE a.ai_value = pa.ai_value
                                 END) --same value
                        WHERE a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID)
                    -- Dismiss lines where the Attributes in the Parameter are not in the inoutline's asi
                    AND NOT EXISTS (SELECT 0
                                    FROM report.fresh_Attributes_ConcreteADR pa
                                             LEFT OUTER JOIN report.fresh_Attributes_ConcreteADR a
                                                             ON a.at_value = pa.at_value AND
                                                                (CASE
                                                                     WHEN a.at_value = '1000015' THEN ('%' || SUBSTRING(a.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(pa.ai_value FROM 5) || '%' OR '%' || SUBSTRING(pa.ai_value FROM 5) || '%' LIKE '%' || SUBSTRING(a.ai_value FROM 5) || '%') --case of adr containing similar value
                                                                                                 ELSE a.ai_value = pa.ai_value
                                                                 END)
                                                                 AND a.M_AttributeSetInstance_ID = ol.M_AttributeSetInstance_ID
                                    WHERE pa.M_AttributeSetInstance_ID = p_attributesetinstance_id
                                      AND a.M_AttributeSetInstance_ID IS NULL)
                )
                ELSE TRUE
        END)


 ORDER BY DateOrdered,
          oDocumentNo,
          bp_value,
          p_value,
          movementdate,
          orderdocumentno,
          io_bp_value)

$$
    LANGUAGE sql STABLE
;
