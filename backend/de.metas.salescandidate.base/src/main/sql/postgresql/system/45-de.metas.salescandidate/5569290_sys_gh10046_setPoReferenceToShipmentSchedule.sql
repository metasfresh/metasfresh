-- set m_shipmentschedule.poReference from the linked c_order
UPDATE m_shipmentschedule ss
SET poreference = cOrder.poreference
FROM c_order cOrder
WHERE ss.c_order_id = cOrder.c_order_Id
;

-- compute the m_shipmentschedule.nrOfOLCandsWithSamePOReference
UPDATE m_shipmentschedule ss
SET nrOfOLCandsWithSamePOReference =
            (SELECT count(*) FROM c_olcand WHERE poreference = ss.poReference)
WHERE poreference IS NOT NULL
;
