
-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- Column SQL (old): (SELECT ai.value::numeric  from qm_analysis_report qm           JOIN M_AttributeSetInstance asi on qm.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID           JOIN M_AttributeInstance ai on asi.m_attributesetinstance_id = ai.m_attributesetinstance_id  where ai.M_Attribute_ID = 540018    AND qm.qm_analysis_report_id = qm_analysis_report_id)
-- 2025-06-27T15:38:35.044Z
UPDATE AD_Column SET ColumnSQL='(SELECT ai.value::numeric  from qm_analysis_report qm           JOIN M_AttributeSetInstance asi on qm.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID           JOIN M_AttributeInstance ai on asi.m_attributesetinstance_id = ai.m_attributesetinstance_id  where ai.M_Attribute_ID = 540018    AND qm.qm_analysis_report_id = QM_Analysis_Report.qm_analysis_report_id)',Updated=TO_TIMESTAMP('2025-06-27 15:38:35.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590462
;

