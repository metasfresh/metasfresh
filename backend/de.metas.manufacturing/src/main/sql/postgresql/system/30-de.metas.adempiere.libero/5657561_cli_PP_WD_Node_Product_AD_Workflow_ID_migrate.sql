UPDATE pp_wf_node_product p
SET ad_workflow_id=(SELECT n.ad_workflow_id FROM ad_wf_node n WHERE n.ad_wf_node_id = p.ad_wf_node_id)
WHERE p.ad_workflow_id IS NULL
;

