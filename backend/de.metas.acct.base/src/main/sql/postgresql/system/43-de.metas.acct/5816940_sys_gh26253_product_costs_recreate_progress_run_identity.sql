-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/table/product_costs_recreate_progress.sql

-- Records, per commit-batch, the run parameters the batch was produced under, so that a resume can
-- refuse a call that does not continue the recorded run instead of skipping batches it never did.
-- Full rationale in the Source DDL above.
-- Nullable on purpose: rows a run started before this change left behind carry no identity, and the
-- resume has to refuse those too rather than trust them.
SELECT public.db_alter_table('product_costs_recreate_progress',
                             'ALTER TABLE "de_metas_acct".product_costs_recreate_progress
                                  ADD COLUMN c_acctschema_id numeric(10),
                                  ADD COLUMN m_costelement_id numeric(10),
                                  ADD COLUMN startdateacct timestamp WITH TIME ZONE,
                                  ADD COLUMN products_per_commit numeric(10)')
;
