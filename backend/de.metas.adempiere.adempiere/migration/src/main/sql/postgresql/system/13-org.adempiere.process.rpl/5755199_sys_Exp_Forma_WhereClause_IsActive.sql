
--
-- We remove the hardcoded "IsActive='Y'" from the export logic and add it to the whereclause - in most cases
-- Needs to be executed ahead of 5755200_sys_22804_EDI_Desadv_Exp_whereClauses.sql

CREATE TABLE fix.exp_format_whereclause_isactive_20250520 AS
SELECT exp_format_id,
       name,
       value,
       whereclause,
       CASE
           WHEN TRIM(UPPER(whereclause)) = UPPER('IsActive=''Y''')  THEN whereclause
           WHEN TRIM(UPPER(whereclause)) ILIKE ('% IsActive=''Y''') THEN whereclause
           WHEN TRIM(COALESCE(whereclause, '')) = ''                THEN 'IsActive=''Y'''
                                                                    ELSE '( ' || TRIM(whereclause) || ') AND IsActive=''Y'''
       END AS whereclause_new
FROM exp_format
WHERE
    -- this is the format where we actually want inactive lines to be exported
    exp_format_id NOT IN (540422/*EDI_Exp_DesadvLineWithNoPack*/ )
;
--select * from fix.exp_format_whereclause_isactive_20250520;
SELECT backup_table('exp_format')
;

UPDATE exp_format f
SET whereclause=fix.whereclause_new, updatedby=99, updated='2025-05-20 15:48:00'
FROM fix.exp_format_whereclause_isactive_20250520 fix
WHERE fix.exp_format_id = f.exp_format_id
;
