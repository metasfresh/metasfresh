
DROP INDEX IF EXISTS exp_replicationtrxline_exp_replicationtrx_id;

CREATE INDEX exp_replicationtrxline_exp_replicationtrx_id
   ON exp_replicationtrxline (exp_replicationtrx_id ASC NULLS LAST);
