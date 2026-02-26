DROP RULE IF EXISTS alter_column_rule ON t_alter_column
;

DROP FUNCTION IF EXISTS altercolumn(
    tablename     name,
    columnname    name,
    datatype      name,
    nullclause    character varying,
    defaultclause character varying
)
;

CREATE OR REPLACE FUNCTION altercolumn(
    tablename     name,
    columnname    name,
    datatype      name,
    nullclause    character varying,
    defaultclause character varying)
    RETURNS void
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE
AS
$BODY$
DECLARE
    command       text;
    viewtext      text[];
    viewname      text[];
    dropviews     text[];
    i             int;
    j             int;
    v             record;
    sqltype       text;
    sqltype_short text;
    typename      name;
BEGIN
    RAISE NOTICE 'altercolumn: tablename=%, columnname=%, datatype=%, nullclause=%, defaultclause=%', tablename, columnname, datatype, nullclause, defaultclause;
    IF datatype IS NOT NULL THEN
        SELECT pg_type.typname, FORMAT_TYPE(pg_type.oid, pg_attribute.atttypmod)
        INTO typename, sqltype
        FROM pg_class,
             pg_attribute,
             pg_type
        WHERE relname = LOWER(tablename)
          AND relkind = 'r'
          AND pg_class.oid = pg_attribute.attrelid
          AND attname = LOWER(columnname)
          AND atttypid = pg_type.oid;
        sqltype_short := sqltype;

        IF typename = 'numeric' THEN
            sqltype_short := REPLACE(sqltype, ',0', '');
        ELSIF STRPOS(sqltype, 'character varying') = 1 THEN
            sqltype_short := REPLACE(sqltype, 'character varying', 'varchar');
        ELSIF sqltype = 'timestamp without time zone' THEN
            sqltype_short := 'timestamp';
        END IF;

        IF LOWER(datatype) <> sqltype AND LOWER(datatype) <> sqltype_short THEN
            RAISE NOTICE 'Changing datatype for %.%: ''%'' to ''%''', 'public.' || tablename, columnname, sqltype, datatype;

            -- Fetch dependent views
            i := 0;

            -- use the "new" db_dependent_views view instead of the deprecated db_dependents view
            -- trigger for this change:
            --   db_dependents returns materialized views as if they were normal views; therefore this function fails when we try to drop them
            --   db_dependent_views does not return materialized views; 
            --   TODO (maybe, later, if needed): extend db_dependent_views to return materialized views, too. But make sure (=>new return-column) that the caller can identify them and deal with them property.
            FOR v IN (SELECT DISTINCT '"' || view_schema || '".' || view_name AS full_view_name, depth FROM db_dependent_views(tablename) ORDER BY depth DESC)
                LOOP
                    IF (viewname @> ARRAY [v.full_view_name::text]) THEN
                        -- raise notice '        skip view % because it was already detected as a dependency', v.relname;
                        CONTINUE;
                    END IF;
                    i := i + 1;
                    viewtext[i] := PG_GET_VIEWDEF(v.full_view_name);
                    viewname[i] := v.full_view_name::text;
                    RAISE NOTICE '    Found dependent: %', viewname[i];
                END LOOP;

            IF i > 0 THEN
                BEGIN
                    FOR j IN 1 .. i
                        LOOP
                            command := 'drop view if exists ' || viewname[j];
                            RAISE NOTICE 'Going to execute command %', command;
                            --raise notice 'View-DDL: %',viewtext[j];
                            EXECUTE command;
                            dropviews[j] := viewname[j];


                        END LOOP;
                EXCEPTION
                    WHEN OTHERS THEN
                        RAISE INFO 'Exception dropping view:';
                        RAISE INFO 'Error Name:%',SQLERRM;
                        RAISE INFO 'Error State:%', SQLSTATE;
                        RAISE INFO 'Will attempt to recover what we dropped so far';
                        i := ARRAY_UPPER(dropviews, 1);
                        IF i > 0 THEN
                            FOR j IN REVERSE i .. 1
                                LOOP
                                    RAISE NOTICE '    Recovery: creating view %', viewname[j];
                                    command := 'create or replace view ' || viewname[j] || ' as ' || viewtext[j];
                                    EXECUTE command;
                                END LOOP;
                        END IF;
                        RAISE EXCEPTION 'Failed to drop dependent view';
                END;
            END IF;

            RAISE NOTICE '    Actual datatype change: to ''%''', datatype;
            command := 'alter table public.' || LOWER(tablename) || ' alter column ' || LOWER(columnname) || ' type ' || LOWER(datatype);
            EXECUTE command;

            i := ARRAY_UPPER(dropviews, 1);
            IF i > 0 THEN
                FOR j IN REVERSE i .. 1
                    LOOP
                        RAISE NOTICE '    Creating view %', dropviews[j];
                        command := 'create or replace view ' || dropviews[j] || ' as ' || viewtext[j];
                        EXECUTE command;
                    END LOOP;
            END IF;
        END IF;
    END IF;

    IF defaultclause IS NOT NULL THEN
        RAISE NOTICE 'Changing default clause for %.%: ''%''', 'public.' || tablename, columnname, defaultclause;
        --
        IF LOWER(defaultclause) = 'null' THEN
            command := 'alter table public.' || LOWER(tablename) || ' alter column ' || LOWER(columnname) || ' drop default ';
        ELSE
            command := 'alter table public.' || LOWER(tablename) || ' alter column ' || LOWER(columnname) || ' set default ''' || defaultclause || '''';
        END IF;
        EXECUTE command;
    END IF;

    IF nullclause IS NOT NULL THEN
        RAISE NOTICE 'Changing null clause for %.%: ''%''', 'public.' || tablename, columnname, nullclause;
        --
        IF LOWER(nullclause) = 'not null' THEN
            command := 'alter table public.' || LOWER(tablename) || ' alter column ' || LOWER(columnname) || ' set not null';
            EXECUTE command;
        ELSIF LOWER(nullclause) = 'null' THEN
            command := 'alter table public.' || LOWER(tablename) || ' alter column ' || LOWER(columnname) || ' drop not null';
            EXECUTE command;
        END IF;
    END IF;
END;
$BODY$
;

COMMENT ON FUNCTION altercolumn(name, name, name, character varying, character varying)
    IS 'Performs DDL changes on tables that may also be part of a view, by dropping and re-creating those views. Assumes that the given table is always in the "public" schema'
;



CREATE RULE alter_column_rule AS
    ON INSERT
    TO t_alter_column
    DO INSTEAD
    SELECT altercolumn(
                   new.tablename,
                   new.columnname,
                   new.datatype,
                   new.nullclause,
                   new.defaultclause
           ) AS altercolumn
;