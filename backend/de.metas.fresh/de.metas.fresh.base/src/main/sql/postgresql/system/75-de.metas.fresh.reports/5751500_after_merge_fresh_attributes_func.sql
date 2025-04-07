/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

CREATE OR REPLACE FUNCTION Report.fresh_attributes(IN p_M_AttributeSetInstance_ID numeric)
    RETURNS TABLE (
                      ai_value character varying,
                      m_attributesetinstance_id numeric,
                      at_value character varying,
                      at_name character varying,
                      at_isattrdocumentrelevant char(1),
                      isprintedindocument char(1))
AS
$$
SELECT att.ai_value,
       att.m_attributesetinstance_id,
       att.at_value,
       att.at_name,
       att.at_isattrdocumentrelevant,
       att.isprintedindocument
FROM (
         SELECT CASE
                    WHEN a.Value = '1000015' AND av.value = '01'                                          THEN NULL -- ADR & Keine/Leer
                    WHEN a.Value = '1000001' AND (av.value IS NOT NULL AND av.value != '')                THEN av.value -- Herkunft
                    WHEN a.Value = '1000021' AND (ai.value IS NOT NULL AND ai.value != '')                THEN ai.Value -- MHD
                    WHEN a.Value = 'HU_BestBeforeDate' AND (ai.valuedate IS NOT NULL)                     THEN 'MHD: ' || TO_CHAR(ai.valuedate, 'DD.MM.YYYY') --Best Before Date
                    WHEN a.attributevaluetype = 'D' AND (ai.valuedate IS NOT NULL) THEN to_char(ai.valuedate, 'DD.MM.YYYY')
                    WHEN a.attributevaluetype = 'S' AND COALESCE(TRIM(ai.value), '') != ''                THEN ai.value
                    WHEN a.attributevaluetype = 'N' AND ai.valuenumber IS NOT NULL AND ai.valuenumber > 0 THEN ai.valuenumber::bpchar
                    WHEN a.Value = 'M_Material_Tracking_ID'
                                                                                                          THEN (SELECT mt.lot
                                                                                                                FROM m_material_tracking mt
                                                                                                                WHERE mt.m_material_tracking_id = ai.value::numeric)
                                                                                                          ELSE (COALESCE(NULLIF(TRIM(printvalue_override), ''), av.name, ai.value)::varchar)
                END                      AS ai_Value,
                M_AttributeSetInstance_ID,
                a.Value                  AS at_Value,
                a.Name                   AS at_Name,
                a.IsAttrDocumentRelevant AS at_IsAttrDocumentRelevant,
                a.IsPrintedInDocument
         FROM M_AttributeInstance ai
                  LEFT OUTER JOIN M_Attributevalue av ON ai.M_Attributevalue_ID = av.M_Attributevalue_ID AND av.isActive = 'Y'
                  LEFT OUTER JOIN M_Attribute a ON ai.M_Attribute_ID = a.M_Attribute_ID AND a.isActive = 'Y'
         WHERE
             /**
              * 08014 - There are/were orderlines, that had M_AttributeSetInstance_ID = 0 when they were intended to not have
              * Attributes set. Unfortunately there actually was an attribute set with ID = 0 which also had values set thus
              * The report displayed attribute values even though it should not display them. The Attribute with the ID = 0
              * Is invalid and therefore not returned by this view. That way, the Report will display nothing for ASI ID = 0
              */
             ai.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
           AND ai.isActive = 'Y'
     ) att
WHERE COALESCE(ai_value, '') != ''
$$
    LANGUAGE sql
    STABLE;

COMMENT ON FUNCTION Report.fresh_attributes(IN p_M_AttributeSetIntance_ID numeric)
    IS 'Consider using this function instead of the view Report.fresh_attributes, because using the view often causes the DB to do a sequential scan on m_attributeinstance.
    !! While we have both the view and the function, please keep them in sync !!'

