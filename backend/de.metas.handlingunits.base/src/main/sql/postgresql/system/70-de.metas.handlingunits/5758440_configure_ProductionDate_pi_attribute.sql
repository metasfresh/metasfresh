-- set splitter strategy as de.metas.handlingunits.attribute.strategy.impl.CopyAttributeSplitterStrategy,
-- same as we have for 'Lot-Nummer' and 'HU_BestBeforeDate'
UPDATE m_hu_pi_attribute
SET splitterstrategy_javaclass_id=540017, updated='2025-06-23', updatedby=0
WHERE m_hu_pi_attribute_id = 540044
;
