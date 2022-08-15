UPDATE pp_order_node n
SET name=wfn.name
FROM ad_wf_node wfn
WHERE wfn.ad_wf_node_id = n.ad_wf_node_id
;

