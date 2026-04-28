-- View: de_metas_purchasecandidate.t_lock_diag_c_purchasecandidate

-- DROP VIEW de_metas_purchasecandidate.t_lock_diag_c_purchasecandidate;

CREATE OR REPLACE VIEW "de_metas_purchasecandidate".t_lock_diag_c_purchasecandidate AS 
 SELECT ( SELECT t.tablename
           FROM ad_table t
          WHERE t.ad_table_id = l.ad_table_id) AS tablename,
    l.ad_table_id,
    l.record_id,
    l.created,
    l.owner,
    l.isautocleanup,
    l.t_lock_id,
    l.isallowmultipleowners
   FROM t_lock l
  WHERE l.ad_table_id = get_table_id('C_PurchaseCandidate'::character varying);

