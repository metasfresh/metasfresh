-- Backup the table AD_Process_Para, Even though only the rows containing 'AD_Org_ID' will be modified
CREATE TABLE backup.ad_process_para_gh6298_ad_org_id_as_search AS
    (
        SELECT *
        FROM ad_process_para
    )
;


-- Unconditionally update the `ad_reference_id` to `Search`
UPDATE ad_process_para
SET --
    ad_reference_id = (SELECT ad_reference_id FROM ad_reference WHERE name = 'Search'),
    UpdatedBy=99,
    Updated='2020-03-11 05:54:10.748455'
WHERE TRUE
  AND ad_element_id = (SELECT ad_element_id FROM ad_element WHERE columnname = 'AD_Org_ID')
  AND ad_reference_id != (SELECT ad_reference_id FROM ad_reference WHERE name = 'Search')
;
