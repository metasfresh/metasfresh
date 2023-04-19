

update ad_Column set iscalculated='Y', updatedby=99, updated='2022-11-07 12:04',
                     technicalnote=technicalnote||'Set to IsCalculated=N and Default=Y because when cloning a not-manual role, ' ||
                                   'then the generic clone support would collide with the DB-function role_access_update that is automatically run for every new not-manual role.'
WHERE ad_Table_id = get_table_id('AD_Role') and columnname='IsManual';

