
delete from m_hu_attribute_snapshot where m_hu_pi_attribute_id =540048; -- HU_ExpiryDate

delete from m_hu_attribute where  m_hu_pi_attribute_id = 540048; -- HU_ExpiryDate

delete from m_hu_pi_attribute where m_attribute_id =540048; -- HU_ExpiryDate

delete from m_attributeinstance where m_attribute_id = 540048; -- HU_ExpiryDate

delete from m_attributeuse where  m_attribute_id = 540048; -- HU_ExpiryDate

delete from m_attribute where m_attribute_id = 540048; -- HU_ExpiryDate
