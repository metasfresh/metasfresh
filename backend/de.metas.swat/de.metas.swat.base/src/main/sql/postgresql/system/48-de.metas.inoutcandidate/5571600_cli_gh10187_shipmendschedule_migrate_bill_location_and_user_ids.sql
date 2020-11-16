UPDATE m_shipmentschedule s
SET Bill_Location_ID = o.Bill_Location_ID,
    Updated = '2020-11-05 18:10:57.703232',
    Updatedby = 99
FROM C_Order o
WHERE s.C_Order_ID = o.C_Order_ID
  AND coalesce(s.Bill_Location_ID, 0) != coalesce(o.Bill_Location_ID, 0)
  AND (s.processed = 'N' OR s.updated >= now() - INTERVAL '3 months')
;

UPDATE m_shipmentschedule s
SET Bill_User_ID = o.Bill_User_ID,
    Updated = '2020-11-05 18:10:57.703232',
    Updatedby = 99
FROM C_Order o
WHERE s.C_Order_ID = o.C_Order_ID
  AND coalesce(s.Bill_User_ID, 0) != coalesce(o.Bill_User_ID, 0)
  AND (s.processed = 'N' OR s.updated >= now() - INTERVAL '3 months')
;
