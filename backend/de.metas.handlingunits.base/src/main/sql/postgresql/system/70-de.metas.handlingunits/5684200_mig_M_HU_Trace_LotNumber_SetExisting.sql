
CREATE TABLE backup.BKP_M_HU_Trace_05042023
AS
SELECT *
FROM m_hu_trace
;



WITH HUAttributes AS
         (SELECT hua.m_hu_id, hua.Value as LotNumber

          FROM m_hu_attribute hua
          WHERE m_attribute_id = 1000017 --Lot-Nummer
         )

UPDATE M_HU_Trace trace
SET LotNumber = x.LotNumber
from HUAttributes x WHERE trace.m_hu_id = x.m_hu_id ;