DROP FUNCTION IF EXISTS purchase_order_highestprice_per_day_mv$update(
    p_M_Product_IDs numeric[],
    p_IsRebuild     char(1))
;

CREATE OR REPLACE FUNCTION purchase_order_highestprice_per_day_mv$update(
    p_M_Product_IDs numeric[] = NULL,
    p_IsRebuild     char(1) = 'N'
)
    RETURNS void
    LANGUAGE plpgsql
    VOLATILE
AS
$BODY$
DECLARE
    v_tag    VARCHAR;
    v_count  integer;
    v_record record;
BEGIN
    --
    -- Fully rebuild purchase_order_highestprice_per_day_mv
    IF (p_IsRebuild = 'Y') THEN
        RAISE NOTICE 'Start fully rebuilding purchase_order_highestprice_per_day_mv...';

        DELETE FROM purchase_order_highestprice_per_day_mv WHERE 1 = 1;
        GET DIAGNOSTICS v_count = ROW_COUNT;
        RAISE NOTICE 'Removed % records from purchase_order_highestprice_per_day_mv', v_count;

        INSERT INTO purchase_order_highestprice_per_day_mv (m_product_id, date, max_price, c_uom_id, c_currency_id)
        SELECT m_product_id, date, max_price, c_uom_id, c_currency_id
        FROM purchase_order_highestprice_per_day_v;
        GET DIAGNOSTICS v_count = ROW_COUNT;
        RAISE NOTICE 'Inserted % records into purchase_order_highestprice_per_day_mv', v_count;

        RETURN;
    END IF;

    v_tag := uuid_generate_v4();

    UPDATE purchase_order_highestprice_per_day_mv$recompute t
    SET processing_tag=v_tag
    WHERE t.processing_tag IS NULL
      AND (p_M_Product_IDs IS NULL OR t.m_product_id = ANY (p_M_Product_IDs));
    GET DIAGNOSTICS v_count = ROW_COUNT;
    IF (v_count <= 0) THEN
        RAISE NOTICE 'No recompute records to process.';
        RETURN;
    END IF;
    RAISE NOTICE 'Tagged % recompute records with tag %', v_count, v_tag;

    FOR v_record IN (SELECT t.date, ARRAY_AGG(t.m_product_id) m_product_ids
                     FROM (SELECT m_product_id,
                                  MIN(date) date
                           FROM purchase_order_highestprice_per_day_mv$recompute
                           WHERE processing_tag = v_tag
                           GROUP BY m_product_id) t
                     GROUP BY t.date)
        LOOP
            RAISE NOTICE 'Processing request: %', v_record;

            DELETE FROM purchase_order_highestprice_per_day_mv mv WHERE mv.m_product_id = ANY (v_record.m_product_ids) AND mv.date >= v_record.date;
            GET DIAGNOSTICS v_count = ROW_COUNT;
            RAISE NOTICE 'Removed % rows from materialized view', v_count;

            INSERT INTO purchase_order_highestprice_per_day_mv (m_product_id, date, max_price, c_uom_id, c_currency_id)
            SELECT m_product_id, date, max_price, c_uom_id, c_currency_id
            FROM purchase_order_highestprice_per_day_v v
            WHERE v.m_product_id = ANY (v_record.m_product_ids)
              AND v.date >= v_record.date;
            GET DIAGNOSTICS v_count = ROW_COUNT;
            RAISE NOTICE 'Inserted % rows into materialized view', v_count;
        END LOOP;

    DELETE FROM purchase_order_highestprice_per_day_mv$recompute WHERE processing_tag = v_tag;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Removed % tagged recompute records', v_count;
END;
$BODY$
;

