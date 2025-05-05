drop function if exists get_db_columns_sqlvalues();
create or replace function get_db_columns_sqlvalues()
    returns TABLE (
                      TableName varchar(2000),
                      Record_ID integer,
                      ColumnName varchar(2000),
                      sqltext varchar(2000)
                  )
AS
$$
    /**
     * Functions retrieves all columns from application dictionary which have columns containing sql code snippets.
     * (i.e. columns whose ColumnName is 'code' or contains the string 'sql' or 'clause').
     */
DECLARE
    v_sql		text := '';
    rec			record;
BEGIN
    for rec in (select t.tableName, c.columnName
                     , 'select '
                           -- TableName
                           || ''''||t.TableName||'''::varchar AS TableName'
                           -- Record_ID
                           || ', '|| COALESCE(
                               (select max(k.ColumnName) from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.IsKey='Y' and k.IsActive='Y' having count(1)=1)
                           , (select max(k.ColumnName) from AD_Column k where k.AD_Table_ID=t.AD_Table_ID and k.IsParent='Y' and k.IsActive='Y' having count(1)=1)
                           , 'NULL'
                           )
                           ||'::integer AS Record_ID'
                           -- ColumnName
                           || ', '''||c.ColumnName||'''::varchar AS ColumnName'
                           -- Column Value
                           || ', '||c.ColumnName||' AS sqltext'
                           --
                           || ' FROM '||t.TableName
            || '' AS sqltext
                from AD_Table t
                         inner join AD_Column c on (c.AD_Table_ID=t.AD_Table_ID)
                where
                    (
                                lower (c.ColumnName) like '%sql%'
                            or lower(c.ColumnName) like 'code'
                            or lower(c.ColumnName) like '%clause%'
                            or lower(c.ColumnName) like '%logic%'
                            or lower(c.ColumnName) like lower('%DefaultValue%')
                        )
                  and c.columnname not in ('AD_SQLColumn_SourceTableColumn_ID')
                order by t.tableName, c.ColumnName
    ) loop
            if (v_sql <> '') then
                v_sql := v_sql || ' union all ';
            end if;
            v_sql := v_sql || rec.sqltext;
        end loop;

    v_sql := 'SELECT * FROM ('||v_sql||') t';
    raise notice 'SQL: %', v_sql;

    return query execute v_sql;
END;
$$ LANGUAGE plpgsql;



