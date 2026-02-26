DROP INDEX IF EXISTS m_costdetail_m_matchinv_id;
CREATE INDEX IF NOT EXISTS m_costdetail_m_matchinv_id
    ON m_costdetail (M_MatchInv_ID)
;

DROP INDEX IF EXISTS m_costdetail_m_matchpo_id;
CREATE INDEX IF NOT EXISTS m_costdetail_m_matchpo_id
    ON m_costdetail (M_MatchPO_ID)
;

DROP INDEX IF EXISTS m_costdetail_pp_cost_collector_id;
CREATE INDEX IF NOT EXISTS m_costdetail_pp_cost_collector_id
    ON m_costdetail (pp_cost_collector_id)
;

