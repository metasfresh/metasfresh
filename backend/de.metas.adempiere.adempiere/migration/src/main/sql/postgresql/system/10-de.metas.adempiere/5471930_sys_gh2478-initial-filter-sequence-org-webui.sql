-- 2017-09-16T16:28:28.375
-- Initializing all filter seqences to 10, becasue null and 0 does have the same effect and does not adjust the filter sequence
update ad_column
set selectioncolumnseqno = 10
where true
and isselectioncolumn = 'Y'
and selectioncolumnseqno is null
;

-- Initializing the filter sequence for ad_org_id (last)
update ad_column
set selectioncolumnseqno = 980
where true
and isselectioncolumn = 'Y'
and columnname = 'AD_Org_ID'
and selectioncolumnseqno is null
;