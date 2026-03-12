UPDATE modcntr_type
SET modularcontracthandlertype = 'ImportLog'
WHERE modularcontracthandlertype NOT IN (SELECT value from ad_ref_list WHERE ad_ref_list.ad_reference_id = 541838)
;
