update c_elementvalue set Parent_id = tn.parent_id, seqno = tn.seqno
 from AD_treenode tn
where tn.node_id = c_elementvalue.c_elementvalue_id and tn.ad_org_id = c_elementvalue.ad_org_id
and exists ( select 1 from c_element e where e.c_element_id = c_elementvalue.c_element_id and e.ad_tree_id = tn.ad_tree_id);