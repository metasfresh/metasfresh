-- View: c_element_levels

DROP VIEW IF EXISTS c_element_levels;

CREATE OR REPLACE VIEW c_element_levels AS 
 SELECT ev1.c_elementvalue_id AS lvl1_c_elementvalue_id, ev1.value AS lvl1_value, ev1.name AS lvl1_name, (ev1.value::text || ' '::text) || ev1.name::text AS lvl1_label, ev2.c_elementvalue_id AS lvl2_c_elementvalue_id, ev2.value AS lvl2_value, ev2.name AS lvl2_name, (ev2.value::text || ' '::text) || ev2.name::text AS lvl2_label, ev3.c_elementvalue_id AS lvl3_c_elementvalue_id, ev3.value AS lvl3_value, ev3.name AS lvl3_name, (ev3.value::text || ' '::text) || ev3.name::text AS lvl3_label, ev4.c_elementvalue_id AS lvl4_c_elementvalue_id, ev4.value AS lvl4_value, ev4.name AS lvl4_name, (ev4.value::text || ' '::text) || ev4.name::text AS lvl4_label, ev5.c_elementvalue_id AS lvl5_c_elementvalue_id, ev5.value AS lvl5_value, ev5.name AS lvl5_name, (ev5.value::text || ' '::text) || ev5.name::text AS lvl5_label, COALESCE(ev5.c_elementvalue_id, ev4.c_elementvalue_id, ev3.c_elementvalue_id, ev2.c_elementvalue_id, ev1.c_elementvalue_id) AS c_elementvalue_id, 
        CASE
            WHEN ev5.c_elementvalue_id IS NOT NULL THEN ev5.value
            WHEN ev4.c_elementvalue_id IS NOT NULL THEN ev4.value
            WHEN ev3.c_elementvalue_id IS NOT NULL THEN ev3.value
            WHEN ev2.c_elementvalue_id IS NOT NULL THEN ev2.value
            WHEN ev1.c_elementvalue_id IS NOT NULL THEN ev1.value
            ELSE NULL::character varying
        END AS value, 
        CASE
            WHEN ev5.c_elementvalue_id IS NOT NULL THEN ev5.name
            WHEN ev4.c_elementvalue_id IS NOT NULL THEN ev4.name
            WHEN ev3.c_elementvalue_id IS NOT NULL THEN ev3.name
            WHEN ev2.c_elementvalue_id IS NOT NULL THEN ev2.name
            WHEN ev1.c_elementvalue_id IS NOT NULL THEN ev1.name
            ELSE NULL::character varying
        END AS name, 
        CASE
            WHEN ev5.c_elementvalue_id IS NOT NULL THEN (ev5.value::text || ' '::text) || ev5.name::text
            WHEN ev4.c_elementvalue_id IS NOT NULL THEN (ev4.value::text || ' '::text) || ev4.name::text
            WHEN ev3.c_elementvalue_id IS NOT NULL THEN (ev3.value::text || ' '::text) || ev3.name::text
            WHEN ev2.c_elementvalue_id IS NOT NULL THEN (ev2.value::text || ' '::text) || ev2.name::text
            WHEN ev1.c_elementvalue_id IS NOT NULL THEN (ev1.value::text || ' '::text) || ev1.name::text
            ELSE NULL::text
        END AS label, acctbalance(COALESCE(ev5.c_elementvalue_id, ev4.c_elementvalue_id, ev3.c_elementvalue_id, ev2.c_elementvalue_id, ev1.c_elementvalue_id), 1::numeric, 0::numeric) AS multiplicator
   FROM c_elementvalue ev1
   JOIN c_element e ON e.c_element_id = ev1.c_element_id
   JOIN ad_treenode tn1 ON tn1.ad_tree_id = e.ad_tree_id AND tn1.node_id = ev1.c_elementvalue_id AND (tn1.parent_id IS NULL OR tn1.parent_id = 0::numeric)
   LEFT JOIN ad_treenode tn2 ON tn2.ad_tree_id = e.ad_tree_id AND tn2.parent_id = tn1.node_id
   LEFT JOIN c_elementvalue ev2 ON ev2.c_elementvalue_id = tn2.node_id
   LEFT JOIN ad_treenode tn3 ON tn3.ad_tree_id = e.ad_tree_id AND tn3.parent_id = tn2.node_id
   LEFT JOIN c_elementvalue ev3 ON ev3.c_elementvalue_id = tn3.node_id
   LEFT JOIN ad_treenode tn4 ON tn4.ad_tree_id = e.ad_tree_id AND tn4.parent_id = tn3.node_id
   LEFT JOIN c_elementvalue ev4 ON ev4.c_elementvalue_id = tn4.node_id
   LEFT JOIN ad_treenode tn5 ON tn5.ad_tree_id = e.ad_tree_id AND tn5.parent_id = tn4.node_id
   LEFT JOIN c_elementvalue ev5 ON ev5.c_elementvalue_id = tn5.node_id;
