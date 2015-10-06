DROP VIEW IF EXISTS C_Element_Levels;

Create VIEW C_Element_Levels AS
(
SELECT
	lvl1_C_ElementValue_ID as lvl1_C_ElementValue_ID, e1.value as lvl1_value, e1.name as lvl1_name, ( e1.value || ' ' || e1.name ) as lvl1_label,
	lvl2_C_ElementValue_ID as lvl2_C_ElementValue_ID, e2.value as lvl2_value, e2.name as lvl2_name, ( e2.value || ' ' || e2.name ) as lvl2_label,
	lvl3_C_ElementValue_ID as lvl3_C_ElementValue_ID, e3.value as lvl3_value, e3.name as lvl3_name, ( e3.value || ' ' || e3.name ) as lvl3_label,
	lvl4_C_ElementValue_ID as lvl4_C_ElementValue_ID, e4.value as lvl4_value, e4.name as lvl4_name, ( e4.value || ' ' || e4.name ) as lvl4_label	
FROM
	(
		SELECT
			e1.C_ElementValue_ID as lvl1_C_ElementValue_ID, 		
			CASE WHEN e4.C_ElementValue_ID IS NULL AND e3.C_ElementValue_ID IS NULL 
				THEN NULL 
				ELSE e2.C_ElementValue_ID 
			END as lvl2_C_ElementValue_ID,
			CASE WHEN e4.C_ElementValue_ID IS NULL 
				THEN NULL 
				ELSE e3.C_ElementValue_ID 
			END as lvl3_C_ElementValue_ID,
			COALESCE( e4.C_ElementValue_ID, e3.C_ElementValue_ID, e2.C_ElementValue_ID, e1.C_ElementValue_ID ) as lvl4_C_ElementValue_ID
		FROM
			(
				SELECT	ev.C_ElementValue_ID
				FROM	C_ElementValue ev
					JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
					JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID 
					LEFT JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID 
				WHERE	evp.C_ElementValue_ID IS NULL
			) e1
			LEFT JOIN (
				SELECT	evp.C_ElementValue_ID AS Parent_ElementValue_ID, ev.C_ElementValue_ID
				FROM	C_ElementValue ev
					JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
					JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID 
					JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID 
			) e2 ON e1.C_ElementValue_ID = e2.Parent_ElementValue_ID
			LEFT JOIN (
				SELECT	evp.C_ElementValue_ID AS Parent_ElementValue_ID,ev.C_ElementValue_ID
				FROM	C_ElementValue ev
					JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
					JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID 
					JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID 
			) e3 ON e2.C_ElementValue_ID = e3.Parent_ElementValue_ID
			LEFT JOIN (
				SELECT	evp.C_ElementValue_ID AS Parent_ElementValue_ID, ev.C_ElementValue_ID
				FROM	C_ElementValue ev
					JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID
					JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID 
					JOIN C_ElementValue evp ON evp.C_ElementValue_ID = tn.Parent_ID 
			) e4 ON e3.C_ElementValue_ID = e4.Parent_ElementValue_ID
	) evl
	INNER JOIN C_ElementValue e1 ON lvl1_C_ElementValue_ID = e1.C_ElementValue_ID 
	LEFT OUTER JOIN C_ElementValue e2 ON lvl2_C_ElementValue_ID = e2.C_ElementValue_ID 
	LEFT OUTER JOIN C_ElementValue e3 ON lvl3_C_ElementValue_ID = e3.C_ElementValue_ID 
	INNER JOIN C_ElementValue e4 ON lvl4_C_ElementValue_ID = e4.C_ElementValue_ID 
ORDER BY  
	e1.value,
	e2.value,
	e3.value,
	e4.value
)
;