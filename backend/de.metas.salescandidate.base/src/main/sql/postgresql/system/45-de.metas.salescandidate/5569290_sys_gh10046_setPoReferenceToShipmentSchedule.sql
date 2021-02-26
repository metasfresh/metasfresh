DO
$$
    DECLARE
        max_shipmentschedule_id numeric;
        rowcount                numeric;
    BEGIN
        --
        -- tmp_olcand_nrOfOLCandsWithSamePOReference
        --
        DROP TABLE IF EXISTS tmp_olcand_nrOfOLCandsWithSamePOReference;
        --
        CREATE TEMPORARY TABLE tmp_olcand_nrOfOLCandsWithSamePOReference AS
        SELECT poreference,
               count(1) AS nrOfOLCandsWithSamePOReference
        FROM c_olcand
        WHERE poreference IS NOT NULL
        GROUP BY poreference;
        --
        CREATE UNIQUE INDEX ON tmp_olcand_nrOfOLCandsWithSamePOReference (poreference);
        --
        RAISE NOTICE 'Created tmp_olcand_nrOfOLCandsWithSamePOReference';

        --
        -- tmp_shipmentschdule_poreference
        --
        DROP TABLE IF EXISTS tmp_shipmentschdule_poreference;
        --
        CREATE TEMPORARY TABLE tmp_shipmentschdule_poreference AS
        SELECT sched.m_shipmentschedule_id,
               o.poreference                          AS poreference,
               olcands.nrOfOLCandsWithSamePOReference AS nrOfOLCandsWithSamePOReference,
               FALSE                                  AS selected
        FROM m_shipmentschedule sched
                 LEFT OUTER JOIN c_order o ON o.c_order_id = sched.c_order_id
                 LEFT OUTER JOIN tmp_olcand_nrOfOLCandsWithSamePOReference olcands ON olcands.poreference = o.poreference;
        --
        CREATE UNIQUE INDEX ON tmp_shipmentschdule_poreference (m_shipmentschedule_id);
        CREATE INDEX ON tmp_shipmentschdule_poreference (selected);
        --
        RAISE NOTICE 'Created tmp_shipmentschdule_poreference';


        --
        --
        --
        LOOP
            SELECT max(m_shipmentschedule_id)
            INTO max_shipmentschedule_id
            FROM (SELECT m_shipmentschedule_id
                  FROM tmp_shipmentschdule_poreference
                  WHERE selected = FALSE
                  ORDER BY m_shipmentschedule_id
                  LIMIT 500000) t;

            IF (max_shipmentschedule_id IS NULL) THEN
                RAISE NOTICE 'No more records to update';
                EXIT;
            END IF;
            RAISE NOTICE 'Will update until m_shipmentschedule_id=%', max_shipmentschedule_id;

            UPDATE tmp_shipmentschdule_poreference
            SET selected= TRUE
            WHERE selected = FALSE
              AND m_shipmentschedule_id <= max_shipmentschedule_id;
            GET DIAGNOSTICS rowcount := ROW_COUNT;
            RAISE NOTICE 'Selected % rows from tmp_shipmentschdule_poreference', rowcount;

            UPDATE m_shipmentschedule sched
            SET poreference=tmp.poreference, nrofolcandswithsameporeference=tmp.nrOfOLCandsWithSamePOReference
            FROM tmp_shipmentschdule_poreference tmp
            WHERE tmp.m_shipmentschedule_id = sched.m_shipmentschedule_id
              AND tmp.selected = TRUE;
            GET DIAGNOSTICS rowcount := ROW_COUNT;
            RAISE NOTICE 'Updated % M_ShipmentSchedule records', rowcount;

            DELETE FROM tmp_shipmentschdule_poreference WHERE selected = TRUE;
            GET DIAGNOSTICS rowcount := ROW_COUNT;
            RAISE NOTICE 'Deleted % rows from tmp_shipmentschdule_poreference', rowcount;

            SELECT count(1) INTO rowcount FROM tmp_shipmentschdule_poreference;
            RAISE NOTICE ' % M_ShipmentSchedules remaining to update', rowcount;
        END LOOP;
    END;
$$
;


