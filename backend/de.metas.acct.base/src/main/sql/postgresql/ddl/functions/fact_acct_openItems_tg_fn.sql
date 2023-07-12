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
