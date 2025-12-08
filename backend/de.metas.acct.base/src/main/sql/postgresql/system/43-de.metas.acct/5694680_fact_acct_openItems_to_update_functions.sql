--
-- DROP
DROP TRIGGER IF EXISTS fact_acct_openItems_insert_or_delete_tg ON fact_acct
;

DROP TRIGGER IF EXISTS fact_acct_openItems_update_tg ON fact_acct
;

DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_openItems_tg_fn()
;


--
-- Trigger function
CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_openItems_tg_fn()
    RETURNS trigger
AS
$BODY$
BEGIN
    -- NOTE to developer: if you are adding a new column here, please make sure you also check the fact_acct_openItems_update_tg's WHEN(...) clause

    IF ((TG_OP = 'UPDATE' OR TG_OP = 'DELETE') AND OLD.openitemkey IS NOT NULL) THEN
        INSERT INTO "de_metas_acct".fact_acct_openItems_to_update(openitemkey, c_acctschema_id, postingtype, account_id)
        VALUES (OLD.openitemkey, OLD.c_acctschema_id, OLD.postingtype, OLD.account_id);
    END IF;

    IF ((TG_OP = 'INSERT' OR TG_OP = 'UPDATE') AND NEW.openitemkey IS NOT NULL) THEN
        INSERT INTO "de_metas_acct".fact_acct_openItems_to_update(openitemkey, c_acctschema_id, postingtype, account_id)
        VALUES (NEW.openitemkey, NEW.c_acctschema_id, NEW.postingtype, NEW.account_id);
    END IF;

    RETURN NEW;
END;
$BODY$
    LANGUAGE plpgsql
;


--
-- Bind the trigger function to Fact_Acct table's events:
CREATE TRIGGER fact_acct_openItems_insert_or_delete_tg
    AFTER INSERT OR DELETE
    ON Fact_Acct
    FOR EACH ROW
EXECUTE PROCEDURE de_metas_acct.fact_acct_openItems_tg_fn()
;

CREATE TRIGGER fact_acct_openItems_update_tg
    AFTER UPDATE
    ON Fact_Acct
    FOR EACH ROW
    WHEN (
            OLD.OpenItemKey IS DISTINCT FROM NEW.OpenItemKey
            OR OLD.Account_ID IS DISTINCT FROM NEW.Account_ID
            OR OLD.C_AcctSchema_ID IS DISTINCT FROM NEW.C_AcctSchema_ID
            OR OLD.PostingType IS DISTINCT FROM NEW.PostingType
        )
EXECUTE PROCEDURE de_metas_acct.fact_acct_openItems_tg_fn()
;













DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_openItems_to_update_process(
    p_BatchSize integer
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_openItems_to_update_process(
    p_BatchSize integer = 999999999
)
    RETURNS integer
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    v_selectionId           varchar(60);
    v_count                 integer;
    v_factAcctUpdatedCount  integer;
    v_group                 record;
    v_isOpenItemsReconciled char(1);
BEGIN
    v_selectionId := gen_random_uuid();

    -- TODO
    UPDATE "de_metas_acct".fact_acct_openItems_to_update
    SET selection_id=v_selectionId
    WHERE seqno IN (SELECT seqno
                    FROM "de_metas_acct".fact_acct_openItems_to_update
                    WHERE selection_id IS NULL
                    ORDER BY seqno
                    LIMIT p_BatchSize);
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Selected % records to process (%)', v_count, v_selectionId;

    IF (v_count <= 0) THEN
        RETURN 0;
    END IF;

    v_factAcctUpdatedCount := 0;
    FOR v_group IN (WITH sel AS (SELECT DISTINCT openitemkey, c_acctschema_id, postingtype, account_id
                                 FROM "de_metas_acct".fact_acct_openItems_to_update
                                 WHERE selection_id = v_selectionId)
                    SELECT fa.openitemkey,
                           fa.c_acctschema_id,
                           fa.postingtype,
                           fa.account_id,
                           SUM(fa.amtacctdr - fa.amtacctcr) AS balance,
                           ARRAY_AGG(fa.fact_acct_id)       AS fact_acct_ids
                    FROM sel
                             INNER JOIN fact_acct fa ON (fa.openitemkey = sel.openitemkey
                        AND fa.c_acctschema_id = sel.c_acctschema_id
                        AND fa.postingtype = sel.postingtype
                        AND fa.account_id = sel.account_id)
                    GROUP BY fa.openitemkey, fa.c_acctschema_id, fa.postingtype, fa.account_id
                    HAVING COUNT(1) > 0)
        LOOP
            v_isOpenItemsReconciled := (CASE WHEN v_group.balance = 0 THEN 'Y' ELSE 'N' END);

            UPDATE fact_acct fa
            SET isOpenItemsReconciled=v_isOpenItemsReconciled
            WHERE fa.fact_acct_id = ANY (v_group.fact_acct_ids);
            GET DIAGNOSTICS v_count = ROW_COUNT;
            v_factAcctUpdatedCount := v_factAcctUpdatedCount + v_count;
            RAISE DEBUG 'Updated % Fact_acct records with isOpenItemsReconciled=% (%)', v_count, v_isOpenItemsReconciled, v_group;
        END LOOP;

    RAISE NOTICE 'Updated % Fact_Acct records', v_factAcctUpdatedCount;

    DELETE FROM "de_metas_acct".fact_acct_openItems_to_update WHERE selection_id = v_selectionId;
    GET DIAGNOSTICS v_count = ROW_COUNT;
    RAISE NOTICE 'Removed selection % (% rows)', v_selectionId, v_count;

    RETURN v_count; -- i.e. now many records were processed
END;
$BODY$
;
