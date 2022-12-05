--- we disabled changelogging this column because, but we need it back now, to help with customer-support
UPDATE ad_column
SET isallowlogging='Y'
WHERE ad_column_id = 546339 and isallowlogging='N';
