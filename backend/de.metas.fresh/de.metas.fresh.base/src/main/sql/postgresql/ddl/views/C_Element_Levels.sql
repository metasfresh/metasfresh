DROP VIEW IF EXISTS C_Element_levels;
CREATE OR REPLACE VIEW C_Element_levels AS
	SELECT
		ev1.C_ElementValue_ID AS lvl1_C_ElementValue_ID, ev1.value AS lvl1_value, ev1.name AS lvl1_name, (ev1.value::text || ' '::text) || ev1.name::text AS lvl1_label
		, ev2.C_ElementValue_ID AS lvl2_C_ElementValue_ID, ev2.value AS lvl2_value, ev2.name AS lvl2_name, (ev2.value::text || ' '::text) || ev2.name::text AS lvl2_label
		, ev3.C_ElementValue_ID AS lvl3_C_ElementValue_ID, ev3.value AS lvl3_value, ev3.name AS lvl3_name, (ev3.value::text || ' '::text) || ev3.name::text AS lvl3_label
		, ev4.C_ElementValue_ID AS lvl4_C_ElementValue_ID, ev4.value AS lvl4_value, ev4.name AS lvl4_name, (ev4.value::text || ' '::text) || ev4.name::text AS lvl4_label
		, ev5.C_ElementValue_ID AS lvl5_C_ElementValue_ID, ev5.value AS lvl5_value, ev5.name AS lvl5_name, (ev5.value::text || ' '::text) || ev5.name::text AS lvl5_label
		, ev6.C_ElementValue_ID AS lvl6_C_ElementValue_ID, ev6.value AS lvl6_value, ev6.name AS lvl6_name, (ev6.value::text || ' '::text) || ev6.name::text AS lvl6_label
		, ev7.C_ElementValue_ID AS lvl7_C_ElementValue_ID, ev7.value AS lvl7_value, ev7.name AS lvl7_name, (ev7.value::text || ' '::text) || ev7.name::text AS lvl7_label
		, ev8.C_ElementValue_ID AS lvl8_C_ElementValue_ID, ev8.value AS lvl8_value, ev8.name AS lvl8_name, (ev8.value::text || ' '::text) || ev8.name::text AS lvl8_label
		, ev9.C_ElementValue_ID AS lvl9_C_ElementValue_ID, ev9.value AS lvl9_value, ev9.name AS lvl9_name, (ev9.value::text || ' '::text) || ev9.name::text AS lvl9_label
		, ev10.C_ElementValue_ID AS lvl10_C_ElementValue_ID, ev10.value AS lvl10_value, ev10.name AS lvl10_name, (ev10.value::text || ' '::text) || ev10.name::text AS lvl10_label
		, COALESCE(ev10.C_ElementValue_ID, ev9.C_ElementValue_ID, ev8.C_ElementValue_ID, ev7.C_ElementValue_ID, ev6.C_ElementValue_ID, ev5.C_ElementValue_ID, ev4.C_ElementValue_ID, ev3.C_ElementValue_ID, ev2.C_ElementValue_ID, ev1.C_ElementValue_ID) AS C_ElementValue_ID
		, (CASE
			WHEN ev10.C_ElementValue_ID IS NOT NULL THEN ev10.value
			WHEN ev9.C_ElementValue_ID IS NOT NULL THEN ev9.value
			WHEN ev8.C_ElementValue_ID IS NOT NULL THEN ev8.value
			WHEN ev7.C_ElementValue_ID IS NOT NULL THEN ev7.value
			WHEN ev6.C_ElementValue_ID IS NOT NULL THEN ev6.value
			WHEN ev5.C_ElementValue_ID IS NOT NULL THEN ev5.value
			WHEN ev4.C_ElementValue_ID IS NOT NULL THEN ev4.value
			WHEN ev3.C_ElementValue_ID IS NOT NULL THEN ev3.value
			WHEN ev2.C_ElementValue_ID IS NOT NULL THEN ev2.value
			WHEN ev1.C_ElementValue_ID IS NOT NULL THEN ev1.value
			ELSE NULL::character varying
		END) AS Value
		, (CASE
			WHEN ev10.C_ElementValue_ID IS NOT NULL THEN ev10.name
			WHEN ev9.C_ElementValue_ID IS NOT NULL THEN ev9.name
			WHEN ev8.C_ElementValue_ID IS NOT NULL THEN ev8.name
			WHEN ev7.C_ElementValue_ID IS NOT NULL THEN ev7.name
			WHEN ev6.C_ElementValue_ID IS NOT NULL THEN ev6.name
			WHEN ev5.C_ElementValue_ID IS NOT NULL THEN ev5.name
			WHEN ev4.C_ElementValue_ID IS NOT NULL THEN ev4.name
			WHEN ev3.C_ElementValue_ID IS NOT NULL THEN ev3.name
			WHEN ev2.C_ElementValue_ID IS NOT NULL THEN ev2.name
			WHEN ev1.C_ElementValue_ID IS NOT NULL THEN ev1.name
			ELSE NULL::character varying
		END) AS Name
		, (CASE
			WHEN ev10.C_ElementValue_ID IS NOT NULL THEN (ev10.value::text || ' '::text) || ev10.name::text
			WHEN ev9.C_ElementValue_ID IS NOT NULL THEN (ev9.value::text || ' '::text) || ev9.name::text
			WHEN ev8.C_ElementValue_ID IS NOT NULL THEN (ev8.value::text || ' '::text) || ev8.name::text
			WHEN ev7.C_ElementValue_ID IS NOT NULL THEN (ev7.value::text || ' '::text) || ev7.name::text
			WHEN ev6.C_ElementValue_ID IS NOT NULL THEN (ev6.value::text || ' '::text) || ev6.name::text
			WHEN ev5.C_ElementValue_ID IS NOT NULL THEN (ev5.value::text || ' '::text) || ev5.name::text
			WHEN ev4.C_ElementValue_ID IS NOT NULL THEN (ev4.value::text || ' '::text) || ev4.name::text
			WHEN ev3.C_ElementValue_ID IS NOT NULL THEN (ev3.value::text || ' '::text) || ev3.name::text
			WHEN ev2.C_ElementValue_ID IS NOT NULL THEN (ev2.value::text || ' '::text) || ev2.name::text
			WHEN ev1.C_ElementValue_ID IS NOT NULL THEN (ev1.value::text || ' '::text) || ev1.name::text
			ELSE NULL::text
		END) AS Label
		, acctBalance(COALESCE(ev10.C_ElementValue_ID, ev9.C_ElementValue_ID, ev8.C_ElementValue_ID, ev7.C_ElementValue_ID, ev6.C_ElementValue_ID, ev5.C_ElementValue_ID, ev4.C_ElementValue_ID, ev3.C_ElementValue_ID, ev2.C_ElementValue_ID, ev1.C_ElementValue_ID), 1::numeric, 0::numeric) AS Multiplicator
FROM C_ElementValue ev1
JOIN C_Element e ON e.C_Element_ID = ev1.C_Element_ID AND e.isActive='Y'
JOIN AD_TreeNode tn1 ON tn1.AD_Tree_ID = e.AD_Tree_ID AND tn1.Node_ID = ev1.C_ElementValue_ID AND (tn1.Parent_ID IS NULL OR tn1.Parent_ID = 0::numeric)
LEFT JOIN AD_TreeNode tn2 ON tn2.AD_Tree_ID = e.AD_Tree_ID AND tn2.Parent_ID = tn1.Node_ID
LEFT JOIN C_ElementValue ev2 ON ev2.C_ElementValue_ID = tn2.Node_ID
LEFT JOIN AD_TreeNode tn3 ON tn3.AD_Tree_ID = e.AD_Tree_ID AND tn3.Parent_ID = tn2.Node_ID
LEFT JOIN C_ElementValue ev3 ON ev3.C_ElementValue_ID = tn3.Node_ID
LEFT JOIN AD_TreeNode tn4 ON tn4.AD_Tree_ID = e.AD_Tree_ID AND tn4.Parent_ID = tn3.Node_ID
LEFT JOIN C_ElementValue ev4 ON ev4.C_ElementValue_ID = tn4.Node_ID
LEFT JOIN AD_TreeNode tn5 ON tn5.AD_Tree_ID = e.AD_Tree_ID AND tn5.Parent_ID = tn4.Node_ID
LEFT JOIN C_ElementValue ev5 ON ev5.C_ElementValue_ID = tn5.Node_ID
LEFT JOIN AD_TreeNode tn6 ON tn6.AD_Tree_ID = e.AD_Tree_ID AND tn6.Parent_ID = tn5.Node_ID
LEFT JOIN C_ElementValue ev6 ON ev6.C_ElementValue_ID = tn6.Node_ID
LEFT JOIN AD_TreeNode tn7 ON tn7.AD_Tree_ID = e.AD_Tree_ID AND tn7.Parent_ID = tn6.Node_ID
LEFT JOIN C_ElementValue ev7 ON ev7.C_ElementValue_ID = tn7.Node_ID
LEFT JOIN AD_TreeNode tn8 ON tn8.AD_Tree_ID = e.AD_Tree_ID AND tn8.Parent_ID = tn7.Node_ID
LEFT JOIN C_ElementValue ev8 ON ev8.C_ElementValue_ID = tn8.Node_ID
LEFT JOIN AD_TreeNode tn9 ON tn9.AD_Tree_ID = e.AD_Tree_ID AND tn9.Parent_ID = tn8.Node_ID
LEFT JOIN C_ElementValue ev9 ON ev9.C_ElementValue_ID = tn9.Node_ID
LEFT JOIN AD_TreeNode tn10 ON tn10.AD_Tree_ID = e.AD_Tree_ID AND tn10.Parent_ID = tn9.Node_ID
LEFT JOIN C_ElementValue ev10 ON ev10.C_ElementValue_ID = tn10.Node_ID;
