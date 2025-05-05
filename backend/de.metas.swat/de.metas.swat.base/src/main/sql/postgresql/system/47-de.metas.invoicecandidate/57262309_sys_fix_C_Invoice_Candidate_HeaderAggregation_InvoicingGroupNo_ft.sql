-- this DDL was not found in any migration-script
-- adding it now
-- the change is to extend the second generate_series parameter to 10000000 
CREATE OR REPLACE FUNCTION C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_ft()
    RETURNS trigger AS
$BODY$
DECLARE
    v_nextInvoicingGroupNo integer;
BEGIN
    select
        s_InvoicingGroupNo
    into v_nextInvoicingGroupNo
    from generate_series(1,10000000) as s_InvoicingGroupNo
             left outer join C_Invoice_Candidate_HeaderAggregation ha on (ha.InvoicingGroupNo=s_InvoicingGroupNo
        and ha.C_BPartner_ID=NEW.C_BPartner_ID and ha.IsSOTrx=NEW.IsSOTrx
        )
    where ha.HeaderAggregationKey is null -- look for the first number of the series that has **no** exising C_Invoice_Candidate_HeaderAggregation record
    order by s_InvoicingGroupNo
    limit 1;

    if (v_nextInvoicingGroupNo is null)
    then
        raise exception 'Got null next InvoicingGroupNo for C_BPartner_ID=%, IsSOTrx=%', NEW.C_BPartner_ID, NEW.IsSOTrx;
    end if;

    NEW.InvoicingGroupNo := v_nextInvoicingGroupNo;

    RETURN NEW;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;

COMMENT ON FUNCTION C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_ft() IS 'Sets C_Invoice_Candidate_HeaderAggregation.InvoicingGroupNo; TODO: come up with a more efficient way.';

drop trigger if exists C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_trigger on C_Invoice_Candidate_HeaderAggregation;
CREATE TRIGGER C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_trigger
    BEFORE INSERT
    ON C_Invoice_Candidate_HeaderAggregation
    FOR EACH ROW
    WHEN (NEW.InvoicingGroupNo IS NULL)
EXECUTE PROCEDURE C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_ft();
