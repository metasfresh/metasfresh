-- Function: getDocumentPOReference()

-- DROP FUNCTION IF EXISTS getDocumentPOReference(numeric, numeric)
;

CREATE OR REPLACE FUNCTION getDocumentPOReference(ad_table_id numeric, record_id numeric)
    RETURNS character AS
$BODY$
SELECT (CASE
            WHEN $1 = 318 -- C_Invoice
                THEN (SELECT i.POReference
                      FROM C_Invoice i
                      WHERE i.C_Invoice_ID = $2)
            WHEN $1 = 319 -- M_InOut
                THEN (SELECT io.POReference
                      FROM M_InOut io
                      WHERE io.M_InOut_ID = $2)
            WHEN $1 = 540401 -- C_DunningDoc
                THEN (SELECT dd.POReference
                      FROM C_DunningDoc dd
                      WHERE dd.C_DunningDoc_ID = $2)
                ELSE NULL
        END) POReference
$BODY$
    LANGUAGE sql VOLATILE
                 COST 100;
COMMENT ON FUNCTION getDocumentPOReference(numeric, numeric) IS 'fresh 08456: Get POReference'
;
