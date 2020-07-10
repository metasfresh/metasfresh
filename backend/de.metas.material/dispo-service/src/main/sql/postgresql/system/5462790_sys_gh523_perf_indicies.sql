
CREATE INDEX IF NOT EXISTS md_candidate_demand_detail_md_candidate_id
   ON public.md_candidate_demand_detail (md_candidate_id ASC NULLS LAST);
CREATE INDEX IF NOT EXISTS md_candidate_demand_detail_c_orderline_id
   ON public.md_candidate_demand_detail (c_orderline_id ASC NULLS LAST);
   
CREATE INDEX IF NOT EXISTS md_candidate_dist_detail_md_candidate_id
   ON public.md_candidate_dist_detail (md_candidate_id ASC NULLS LAST);
CREATE INDEX IF NOT EXISTS md_candidate_dist_detail_dd_order_id
   ON public.md_candidate_dist_detail (dd_order_id ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS md_candidate_prod_detail_md_candidate_id
   ON public.md_candidate_prod_detail (md_candidate_id ASC NULLS LAST);
CREATE INDEX IF NOT EXISTS md_candidate_prod_detail_pp_order_id
   ON public.md_candidate_prod_detail (pp_order_id ASC NULLS LAST);

