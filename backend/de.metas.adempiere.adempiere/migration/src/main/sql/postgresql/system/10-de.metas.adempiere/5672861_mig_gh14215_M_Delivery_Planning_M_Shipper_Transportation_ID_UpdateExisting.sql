UPDATE m_delivery_planning dp
SET m_shippertransportation_ID = x.m_shippertransportation_ID
FROM m_shippertransportation x
WHERE dp.releaseno = x.documentno
;

