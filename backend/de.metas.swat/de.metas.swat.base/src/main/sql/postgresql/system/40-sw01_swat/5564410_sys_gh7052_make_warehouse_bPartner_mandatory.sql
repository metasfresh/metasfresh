
-- fill in m_warehouse.c_bpartner_id based on c_bpartner_location_id

UPDATE 
    m_warehouse
SET 
    c_bpartner_id = b_loc.c_bpartner_id
FROM 
     m_warehouse w
         INNER JOIN c_bpartner_location b_loc on w.c_bpartner_location_id = b_loc.c_bpartner_location_id;
