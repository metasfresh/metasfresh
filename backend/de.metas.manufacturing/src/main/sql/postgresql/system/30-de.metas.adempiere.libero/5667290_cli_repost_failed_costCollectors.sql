SELECT COUNT(1)
FROM (SELECT de_metas_acct.fact_acct_unpost('PP_Cost_Collector', pp_cost_collector_id)
      FROM pp_cost_collector

      WHERE docstatus IN ('CO', 'RE')
        AND posted != 'Y') t
;

