-- gh#30457 — fast filtered "Bestand pro Woche" / stock per week.
-- Partial index on MD_Candidate (M_Product_ID, M_Warehouse_ID) for active rows: lets the
-- planner satisfy MD_Stock_PerWeek_fn's single-product/warehouse filter with an
-- Index/Bitmap Index Scan instead of a full scan of MD_Candidate.

CREATE INDEX IF NOT EXISTS md_candidate_perweek_pw_idx
    ON MD_Candidate (M_Product_ID, M_Warehouse_ID)
    WHERE IsActive = 'Y';
