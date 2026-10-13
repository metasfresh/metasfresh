-- Index the cost revaluation line's parent link. Every load of a revaluation's lines (the line tab grid, the
-- full document, Complete and posting) filters M_CostRevaluationLine by M_CostRevaluation_ID; with only the
-- primary key, each such load scans the whole table.
--
-- Plain CREATE INDEX (the migration runner applies scripts inside one transaction, so
-- CREATE INDEX CONCURRENTLY is not usable). IF NOT EXISTS keeps it a no-op on any instance that
-- already carries the index.

CREATE INDEX IF NOT EXISTS m_costrevaluationline_m_costrevaluation_id
    ON M_CostRevaluationLine (M_CostRevaluation_ID);
