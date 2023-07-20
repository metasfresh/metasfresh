
UPDATE ad_window
SET name=elem.name,
    Updated=TO_TIMESTAMP('2022-10-04 10:13:49', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
FROM ad_element elem
WHERE elem.AD_Element_ID = 573947
  and elem.ad_element_id = ad_window.ad_element_id
;