UPDATE m_hu_pi_attribute
SET isonlyifinproductattributeset = 'Y',
    Updated                       = '2021-03-10 09:53:58.228674',
    UpdatedBy                     = 100
WHERE m_hu_pi_version_id = 100
  AND m_attribute_id = 540092 -- Produktionsauftrag
;
