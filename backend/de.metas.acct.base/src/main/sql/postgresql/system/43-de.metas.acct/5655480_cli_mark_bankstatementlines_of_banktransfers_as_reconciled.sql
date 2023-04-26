SELECT backup_table('c_bankstatementline')
;

UPDATE c_bankstatementline
SET isreconciled='Y'
WHERE isreconciled = 'N'
  AND link_bankstatementline_id IS NOT NULL
;


