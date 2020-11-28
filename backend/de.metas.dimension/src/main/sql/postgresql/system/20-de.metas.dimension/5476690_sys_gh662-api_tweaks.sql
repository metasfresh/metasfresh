
--
-- adding M_AttributeValue.Value to the result
--

select public.db_alter_table('DIM_Dimension_Spec_Attribute_AllValues','
CREATE OR REPLACE VIEW "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues AS
SELECT 
	ds.InternalName, ds.DIM_Dimension_Spec_ID, 
	GroupName,
	M_AttributeValue_ID,
	ValueName,
	Value
FROM DIM_Dimension_Spec ds
	JOIN (
		SELECT
			dsa.DIM_Dimension_Spec_ID,
			COALESCE(dsa.ValueAggregateName,av_i.Name,av_d.Name) AS GroupName,
			COALESCE(av_i.Name,av_d.Name) AS ValueName,
			COALESCE(av_i.Value,av_d.Value) AS Value,
			COALESCE(av_i.M_AttributeValue_ID,av_d.M_AttributeValue_ID) AS M_AttributeValue_ID,
			''N'' as IsEmpty
		FROM DIM_Dimension_Spec_Attribute dsa
			JOIN M_Attribute a ON a.M_Attribute_ID=dsa.M_Attribute_ID
			LEFT JOIN M_AttributeValue av_d ON av_d.M_Attribute_ID=a.M_Attribute_ID -- av_d = attribute value - direct
				AND dsa.IsInCludeAllAttributeValues=''Y''
			LEFT JOIN DIM_Dimension_Spec_AttributeValue dsav ON dsav.DIM_Dimension_Spec_Attribute_ID=dsa.DIM_Dimension_Spec_Attribute_ID
				AND dsa.IsInCludeAllAttributeValues=''N''
			LEFT JOIN M_AttributeValue av_i ON av_i.M_AttributeValue_ID=dsav.M_AttributeValue_ID -- av_i = attribute value - indirect
		WHERE true
			AND dsa.IsActive=''Y''
			AND a.IsActive=''Y'' AND COALESCE(av_d.IsActive,''Y'')=''Y'' AND COALESCE(dsav.IsActive,''Y'')=''Y'' AND COALESCE(av_i.IsActive,''Y'')=''Y''
		UNION
		SELECT 
			-1, /* DIM_Dimension_Spec_ID */
			(SELECT MsgText FROM AD_Message WHERE Value=''NoneOrEmpty''),
			''DIM_EMPTY'', /* ValueName */ 
			''DIM_EMPTY'', /* Value */ 
			-1, /* M_AttributeValue_ID */
			''Y'' /* IsEmpty */
	) data ON data.DIM_Dimension_Spec_ID=ds.DIM_Dimension_Spec_ID OR (data.IsEmpty=''Y'' AND ds.IsIncludeEmpty=''Y'')
WHERE true
	AND ds.IsActive=''Y'';'
);
COMMENT ON VIEW "de.metas.dimension".DIM_Dimension_Spec_Attribute_AllValues IS 
'Task 08681: lists all attribute values that are defined by a given DIM_Dimension_Spec. If the DIM_Dimension_Spec has IsIncludeEmpty=Y, then it selects an additional record for reprecent the empty value.';

--
-- just extend the documentation
--
COMMENT ON  FUNCTION  "de.metas.dimension".DIM_Get_GroupName(text, text) IS 'Returns the attribute-dimension-spec-lines'' GroupNames for the given ASI-key as array.
Its purpose is to join order lines and other "ASI-Aware" records to the "measures" (but note, this is not really olap!) of a given dimension. 
So if you e.g. have a bunch of order lines and a dimension, 
you can aggregate those lines and provide sums based on the attributes that are part of the dimension

To that end, it can be used in joins like this:
 dim.GroupName = ANY("de.metas.dimension".DIM_Get_GroupName(''MRP_Product_Info_ASI_Values'', v.ASIKey))

That way, you can join the given ASI-Key to its respective GroupName(s) as returned by the DIM_Dimension_Spec_Attribute_AllValues.

That groupname is some Dim_Dimension_Spec_Attribute.ValueAggregateName (i.e. a common name given to multiple attribute values) or M_AttributeValue.Name.
Check out the view DIM_Dimension_Spec_Attribute_AllValues for more infos.

Note: for better performance, consider not using the function in a join..rather write it''s results into a physical column.';
