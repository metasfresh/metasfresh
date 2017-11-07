
--
-- with theses constraints, we run into trouble witht the normal workflow (e.g. when an inout is reactivated)
--
ALTER TABLE public.md_candidate_demand_detail DROP CONSTRAINT IF EXISTS mshipmentschedule_mdcandidated;

ALTER TABLE public.md_candidate_transaction_detail DROP CONSTRAINT IF EXISTS mtransaction_mdcandidatetransa;
