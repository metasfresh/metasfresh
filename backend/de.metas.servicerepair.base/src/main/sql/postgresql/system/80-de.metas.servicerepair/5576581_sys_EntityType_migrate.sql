UPDATE ad_table
SET entitytype='de.metas.servicerepair'
WHERE ad_table_id IN (541563, 541564, 541565)
;

UPDATE ad_column
SET entitytype='de.metas.servicerepair'
WHERE ad_table_id IN (541563, 541564, 541565)
;

UPDATE ad_reference
SET entitytype='de.metas.servicerepair'
WHERE ad_reference_id IN (541245, 541243, 541242, 541246, 541244)
;

UPDATE ad_ref_table
SET entitytype='de.metas.servicerepair'
WHERE ad_reference_id IN (541245, 541243, 541242, 541246, 541244)
;

UPDATE ad_ref_list
SET entitytype='de.metas.servicerepair'
WHERE ad_reference_id IN (541245, 541243, 541242, 541246, 541244)
;



DROP TABLE IF EXISTS tmp_window_elements_to_update
;

CREATE TEMPORARY TABLE tmp_window_elements_to_update AS
SELECT m.ad_menu_id,
       w.ad_window_id,
       tt.ad_tab_id,
       f.ad_field_id
FROM ad_window w
         LEFT OUTER JOIN ad_menu m ON m.ad_window_id = w.ad_window_id
         LEFT OUTER JOIN ad_tab tt ON tt.ad_window_id = w.ad_window_id
         LEFT OUTER JOIN ad_field f ON f.ad_tab_id = tt.ad_tab_id
WHERE w.ad_window_id IN (
    SELECT tt.ad_window_id
    FROM ad_table t
             LEFT OUTER JOIN ad_tab tt ON tt.ad_table_id = t.ad_table_id
    WHERE t.entitytype = 'de.metas.servicerepair')
   OR w.ad_window_id IN (541014, 541011, 541015)
;



UPDATE ad_menu
SET entitytype='de.metas.servicerepair'
WHERE ad_menu_id IN (SELECT DISTINCT ad_menu_id FROM tmp_window_elements_to_update)
;

UPDATE ad_window
SET entitytype='de.metas.servicerepair'
WHERE ad_window_id IN (SELECT DISTINCT ad_window_id FROM tmp_window_elements_to_update)
;

UPDATE ad_tab
SET entitytype='de.metas.servicerepair'
WHERE ad_tab_id IN (SELECT DISTINCT ad_tab_id FROM tmp_window_elements_to_update)
;

UPDATE ad_field
SET entitytype='de.metas.servicerepair'
WHERE ad_field_id IN (SELECT DISTINCT ad_field_id FROM tmp_window_elements_to_update)
;



UPDATE ad_process
SET entitytype='de.metas.servicerepair'
WHERE classname LIKE 'de.metas.servicerepair%'
;

UPDATE ad_process_para
SET entitytype='de.metas.servicerepair'
WHERE ad_process_id IN (SELECT ad_process_id FROM ad_process WHERE entitytype = 'de.metas.servicerepair')
;

UPDATE ad_table_process
SET entitytype='de.metas.servicerepair'
WHERE ad_process_id IN (SELECT ad_process_id FROM ad_process WHERE entitytype = 'de.metas.servicerepair')
;


/*
SELECT DISTINCT e.ad_element_id, e.columnname, e.entitytype
from ad_column c
inner join ad_element e on e.ad_element_id=c.ad_element_id
where c.entitytype='de.metas.servicerepair'
order by e.columnname
 */
UPDATE ad_element
SET entitytype='de.metas.servicerepair'
WHERE ad_element_id IN (578635, 578631, 578628, 578630)
;



