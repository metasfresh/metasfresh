-- Backfill for compensation-group contracts that were completed before these values were set on completion.
-- Only empty values are filled; a script re-run changes nothing.
--   DateContracted  := the day the term was created
--   MasterStartDate := MasterStartDate of the first term of the extension chain, else that first term's StartDate
--   ContractStatus  := 'Wa' (not yet started) if the start date lies in the future,
--                      'Ec' (contract end) if the end date has passed and the term was not extended,
--                      else 'Ru' (running)
-- Existing contract statuses (e.g. 'Qu' quit, 'Vo' voided, 'Ec' contract end) are left untouched.
-- Voided terms ('Vo') get no MasterStartDate: voiding a single contract clears it on purpose (ContractChangeBL.setMasterDates).

SELECT backup_table('c_flatrate_term', '_CompGroup_Backfill');

UPDATE C_Flatrate_Term t
SET DateContracted = date_trunc('day', t.Created),
    Updated        = TO_TIMESTAMP('2026-10-09 10:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy      = 99
WHERE t.Type_Conditions = 'CompensationGroup'
  AND t.DocStatus IN ('CO', 'CL')
  AND t.DateContracted IS NULL
;

-- MasterStartDate is the FIRST term of the chain's COALESCE(MasterStartDate, StartDate), on purpose unlike the completion hook (direct predecessor's MasterStartDate): legacy chains have no MasterStartDate on any term yet, so the direct-predecessor rule would just copy each term's own StartDate.
WITH RECURSIVE chain (C_Flatrate_Term_ID, ChainMasterStartDate) AS
                   (SELECT first.C_Flatrate_Term_ID, COALESCE(first.MasterStartDate, first.StartDate)
                    FROM C_Flatrate_Term first
                    WHERE first.Type_Conditions = 'CompensationGroup'
                      AND NOT EXISTS (SELECT 1 FROM C_Flatrate_Term pred WHERE pred.C_FlatrateTerm_Next_ID = first.C_Flatrate_Term_ID)
                    UNION
                    SELECT next.C_Flatrate_Term_ID, chain.ChainMasterStartDate
                    FROM chain
                             JOIN C_Flatrate_Term current ON current.C_Flatrate_Term_ID = chain.C_Flatrate_Term_ID
                             JOIN C_Flatrate_Term next ON next.C_Flatrate_Term_ID = current.C_FlatrateTerm_Next_ID)
UPDATE C_Flatrate_Term t
SET MasterStartDate = chain.ChainMasterStartDate,
    Updated         = TO_TIMESTAMP('2026-10-09 10:10:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 99
FROM chain
WHERE chain.C_Flatrate_Term_ID = t.C_Flatrate_Term_ID
  AND t.Type_Conditions = 'CompensationGroup'
  AND t.DocStatus IN ('CO', 'CL')
  AND t.ContractStatus IS DISTINCT FROM 'Vo'
  AND t.MasterStartDate IS NULL
;

UPDATE C_Flatrate_Term t
SET ContractStatus = CASE
                         WHEN t.StartDate::date > now()::date                                                      THEN 'Wa'
                         WHEN t.EndDate::date < now()::date AND COALESCE(t.C_FlatrateTerm_Next_ID, 0) <= 0 THEN 'Ec'
                                                                                                                   ELSE 'Ru'
                     END,
    Updated        = TO_TIMESTAMP('2026-10-09 10:10:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy      = 99
WHERE t.Type_Conditions = 'CompensationGroup'
  AND t.DocStatus IN ('CO', 'CL')
  AND (t.ContractStatus IS NULL OR t.ContractStatus = '')
;
