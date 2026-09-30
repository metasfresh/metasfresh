-- gh31774: remove empty ProjectValue attribute instances that were incorrectly injected onto
-- project-less C_OrderLine / M_ShipmentSchedule attribute-set instances by
-- OrderBL.updateASIFromProjectId / ShipmentScheduleBL.updateASIFromProjectId before the fix.
--
-- Predicate (deliberately scoped and hard-DELETE -- see REQUIREMENTS.md ai-work/31774 for the full
-- safety argument):
--   - attribute is ProjectValue
--   - all four typed columns are NULL: Value, ValueNumber, ValueDate, M_AttributeValue_ID -- the same
--     four columns de_metas_attributes.clear_attributeinstance nulls, so this only removes rows that
--     never carried a real value
--   - the ASI is referenced by a C_OrderLine or an M_ShipmentSchedule
--   - IsActive is deliberately NOT part of the predicate: both active and inactive rows are removed,
--     because no reader feeding the attribute editor filters on IsActive
--
-- Out of scope on purpose: ASIs reachable only from M_InOutLine / C_InvoiceLine / M_MatchPO are not
-- touched by this script (narrower blast radius; signed off as a follow-up).
--
-- Preview -- run manually on a live instance BEFORE applying, to see the before-count:
--
-- SELECT count(*)
-- FROM M_AttributeInstance ai
-- JOIN M_Attribute a ON a.M_Attribute_ID = ai.M_Attribute_ID AND a.Value = 'ProjectValue'
-- WHERE ai.Value IS NULL
--   AND ai.ValueNumber IS NULL
--   AND ai.ValueDate IS NULL
--   AND ai.M_AttributeValue_ID IS NULL
--   AND (
--        EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.M_AttributeSetInstance_ID = ai.M_AttributeSetInstance_ID)
--     OR EXISTS (SELECT 1 FROM M_ShipmentSchedule ss WHERE ss.M_AttributeSetInstance_ID = ai.M_AttributeSetInstance_ID)
--   );

SELECT backup_table('m_attributeinstance', '_gh31774_EmptyProjectValueASI');

DELETE FROM M_AttributeInstance ai
USING M_Attribute a
WHERE a.M_Attribute_ID = ai.M_Attribute_ID
  AND a.Value = 'ProjectValue'
  AND ai.Value IS NULL
  AND ai.ValueNumber IS NULL
  AND ai.ValueDate IS NULL
  AND ai.M_AttributeValue_ID IS NULL
  AND (
       EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.M_AttributeSetInstance_ID = ai.M_AttributeSetInstance_ID)
    OR EXISTS (SELECT 1 FROM M_ShipmentSchedule ss WHERE ss.M_AttributeSetInstance_ID = ai.M_AttributeSetInstance_ID)
  );
