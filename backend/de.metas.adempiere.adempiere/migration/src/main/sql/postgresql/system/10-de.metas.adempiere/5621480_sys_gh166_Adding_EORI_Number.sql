DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.docs_purchase_inout_description(IN record_id   numeric,
                                                                                           IN ad_language Character Varying(6))
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_inout_description(record_id   numeric,
                                                                                              ad_language character varying)
    RETURNS TABLE
            (
                documentno   text,
                dateinvoiced timestamp WITHOUT TIME ZONE,
                bp_value     character varying,
                eori         character varying,
                printname    character varying,
                o_documentno character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT CASE
           WHEN io.DocNo_hi = io.DocNo_lo THEN io.DocNo_lo
                                          ELSE io.DocNo_lo || ' ff.'
       END                                   AS documentno,
       io.movementdate                       AS dateinvoiced,
       bp.value                              AS bp_value,
       bp.eori                               AS eori,
       COALESCE(dtt.PrintName, dt.PrintName) AS PrintName,
       o.documentno                          AS o_documentno
FROM C_OrderLine ol
         INNER JOIN (
    SELECT ol.C_Order_ID,
           MAX(io.movementdate) AS movementdate,
           MIN(io.Documentno)   AS Docno_lo,
           MAX(io.Documentno)   AS Docno_hi,
           MAX(io.C_DocType_ID) AS C_DocType_ID
    FROM C_OrderLine ol
             INNER JOIN M_InOutLine iol ON ol.C_OrderLine_ID = iol.C_OrderLine_ID AND iol.isActive = 'Y'
             INNER JOIN M_InOut io ON iol.M_InOut_ID = io.M_InOut_ID AND io.isActive = 'Y'
    WHERE ol.c_order_id = (SELECT c_order_id FROM c_orderline WHERE C_OrderLine_ID = $1)
      AND ol.isActive = 'Y'
    GROUP BY ol.C_Order_ID
) io ON ol.C_Order_ID = io.C_Order_ID
         INNER JOIN C_Order o ON ol.C_Order_ID = o.C_Order_ID AND o.isActive = 'Y'
         INNER JOIN C_BPartner bp ON o.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID AND dt.isActive = 'Y'
         LEFT OUTER JOIN C_DocType_Trl dtt ON io.C_DocType_ID = dtt.C_DocType_ID AND dtt.AD_Language = $2 AND dtt.isActive = 'Y'
WHERE ol.C_OrderLine_ID = $1
  AND ol.isActive = 'Y'
$$
;

ALTER FUNCTION de_metas_endcustomer_fresh_reports.docs_purchase_inout_description(numeric, varchar) OWNER TO metasfresh
;

