
CREATE TABLE backup.BKP_M_HU_Trace_10042023
AS
SELECT *
FROM m_hu_trace
;






UPDATE M_HU_Trace trace
SET C_UOM_ID = p.C_UOM_ID
from M_Product p WHERE trace.M_Product_ID = p.M_Product_ID ;