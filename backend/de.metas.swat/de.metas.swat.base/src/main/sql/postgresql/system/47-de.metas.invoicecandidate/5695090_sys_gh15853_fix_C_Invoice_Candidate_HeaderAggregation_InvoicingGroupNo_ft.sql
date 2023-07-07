--
-- Align the procedure with src/main/sql/postgresql/ddl/functions/C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_ft.sql
-- On a legacy-DB we had generate_series(1,10000000) and there the numbers where exhausted
--
CREATE OR REPLACE FUNCTION C_Invoice_Candidate_HeaderAggregation_InvoicingGroupNo_ft()
    RETURNS trigger AS
$BODY$
DECLARE
    v_nextInvoicingGroupNo integer;
BEGIN
    select
        s_InvoicingGroupNo
    into v_nextInvoicingGroupNo
    from generate_series(1,9999999999) as s_InvoicingGroupNo -- the column C_Invoice_Candidate_HeaderAggregation.InvoicingGroupNo has type numeric(10)
             left outer join C_Invoice_Candidate_HeaderAggregation ha on (ha.InvoicingGroupNo=s_InvoicingGroupNo
        and ha.C_BPartner_ID=NEW.C_BPartner_ID and ha.IsSOTrx=NEW.IsSOTrx
        )
    where ha.HeaderAggregationKey is null
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
