DROP FUNCTION IF EXISTS make_unique(varchar,
                                    varchar,
                                    boolean,
                                    boolean)
;

CREATE OR REPLACE FUNCTION make_unique(IN p_tableName             varchar,
                                       IN p_columnName            varchar,
                                       IN p_uniqueForClient       boolean DEFAULT FALSE,
                                       IN p_uniqueForOrganization boolean DEFAULT FALSE) RETURNS void
    VOLATILE
AS
$body$
DECLARE
    _index                   int     := 0;
    _colLength               int;
    _suffixLengthMinusSuffixSize int;
    _group_record            record;
    _record                  record;
    _newValue                varchar;
    _idColumn                varchar := p_tableName || '_ID ';
    _QUERY_TEMPLATE          varchar := 'SELECT ' || p_tableName || '_ID ID FROM ' || p_tableName || ' tbl WHERE ';
    _query                   varchar;
    _groupByColumns          varchar := p_columnName;
BEGIN
    IF (p_uniqueForClient) THEN
        _groupByColumns := 'ad_client_id, ' || _groupByColumns;
    END IF;
    IF (p_uniqueForOrganization) THEN
        _groupByColumns := 'ad_org_id, ' || _groupByColumns;
    END IF;
    SELECT fieldlength
    INTO _colLength
    FROM ad_column c
             INNER JOIN ad_table t ON c.ad_table_id = t.ad_table_id
    WHERE columnname ILIKE p_columnName
      AND tablename ILIKE p_tableName;
    FOR _group_record IN EXECUTE 'SELECT * FROM (SELECT COUNT(1) AS cnt, ' || _groupByColumns || ' VAL FROM ' || p_tableName || ' GROUP BY ' || _groupByColumns || ') t WHERE cnt > 1;'
        LOOP
            _query := _QUERY_TEMPLATE || p_columnName || ' = ''' || _group_record.VAL || '''';
            IF (p_uniqueForClient) THEN
                _query := _query || ' AND AD_Client_ID= ' || _group_record.AD_Client_ID;
            END IF;
            IF (p_uniqueForOrganization) THEN
                _query := _query || ' AND AD_Org_ID= ' || _group_record.AD_Org_ID;
            END IF;
            _query := _query || ' ORDER BY CREATED;';
            _index := 1;
            FOR _record IN EXECUTE _query USING _index
                LOOP
                    _suffixLengthMinusSuffixSize := _colLength - LENGTH('' || _index);
                    _newValue := CASE WHEN LENGTH(_group_record.VAL) > _suffixLengthMinusSuffixSize THEN SUBSTRING(_group_record.VAL, _suffixLengthMinusSuffixSize) ELSE _group_record.VAL END || '-' || _index;
                    EXECUTE 'UPDATE ' || p_tableName || ' SET ' || p_columnName || ' = ''' || _newValue || ''', updatedBy=100, updated=TO_TIMESTAMP(''2022-04-20 12:00:00'', ''YYYY-MM-DD HH24:MI:SS'') WHERE ' || _idColumn || ' = ' || _record.ID;
                    _index := _index + 1;
                END LOOP;
        END LOOP;
END;
$body$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION make_unique(varchar,
    varchar,
    boolean,
    boolean)
    IS 'Used to remove duplicates from a table''s column. This is useful when needing to introduce a unique constraint on a column that exists and may not have unique values in the database.
    Limitations: Column needs to be varchar/text.
    Parameters:
        p_tableName             varchar, -- The table name
        p_columnName            varchar, -- The column name
        p_uniqueForClient       boolean DEFAULT FALSE, -- Whether the column should be unique for the same client (default false) aka unique constraint will also include ad_client_id
        p_uniqueForOrganization boolean DEFAULT FALSE -- Whether the column should be unique for the same organization (default false)  aka unique constraint will also include ad_org_id
    ';
;
