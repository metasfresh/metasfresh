
-- Function: get_table_id(character varying)

-- DROP FUNCTION get_table_id(character varying);

CREATE OR REPLACE FUNCTION get_table_id(tablename character varying)
    RETURNS numeric AS
$BODY$
SELECT t.AD_Table_ID FROM AD_Table t WHERE UPPER(t.TableName) = UPPER($1);
$BODY$
    LANGUAGE sql IMMUTABLE
                 COST 100;
COMMENT ON FUNCTION get_table_id(character varying) IS
    'Returns the Table_ID for the given name, case-insensitive.
    
    WARNING: This function is declared IMMUTABLE for performance reasons.
    If the contents of AD_Table.TableName are changed,
    you must restart the session or reconnect the client to ensure correct behavior.
    See PostgreSQL documentation on IMMUTABLE functions:
    https://www.postgresql.org/docs/current/xfunc-volatility.html';

COMMENT ON COLUMN AD_Table.TableName IS
    'The name of the table (case-insensitive). Must be unique.
    
    WARNING: Used by the IMMUTABLE function get_table_id().
    Changes to this field will not be visible to existing sessions unless they are restarted.
    Make sure to reconnect client sessions or re-plan queries after modifying this column.

    See PostgreSQL documentation on IMMUTABLE functions: https://www.postgresql.org/docs/current/xfunc-volatility.html'
;

UPDATE ad_field
SET description = 'The name of the table (case-insensitive). Must be unique.

WARNING: Used by the IMMUTABLE function get_table_id().
Changes to this field will not be visible to existing sessions unless they are restarted.
Make sure to reconnect client sessions or replan queries after modifying this column.

See PostgreSQL documentation on IMMUTABLE functions: https://www.postgresql.org/docs/current/xfunc-volatility.html',
    Updated=TO_TIMESTAMP('2025-06-18 16:34:39', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_field_id = 149
;

UPDATE ad_field_trl
SET description = 'Der Name der Tabelle (Groß-/Kleinschreibung wird ignoriert). Muss eindeutig sein.

WARNUNG: Wird von der als IMMUTABLE deklarierten Funktion get_table_id() verwendet.
Änderungen an diesem Feld sind in bestehenden Sitzungen nicht sichtbar, solange diese nicht neu gestartet wurden.
Stellen Sie sicher, dass Client-Sitzungen neu verbunden oder Abfragen neu geplant werden, nachdem diese Spalte geändert wurde.

Siehe PostgreSQL-Dokumentation zu IMMUTABLE-Funktionen:
https://www.postgresql.org/docs/current/xfunc-volatility.html', Updated=TO_TIMESTAMP('2025-06-18 16:34:39', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_field_id = 149
  and ad_language != 'en_US'
;

UPDATE ad_field_trl
SET description = 'The name of the table (case-insensitive). Must be unique.

WARNING: Used by the IMMUTABLE function get_table_id().
Changes to this field will not be visible to existing sessions unless they are restarted.
Make sure to reconnect client sessions or replan queries after modifying this column.

See PostgreSQL documentation on IMMUTABLE functions: https://www.postgresql.org/docs/current/xfunc-volatility.html',
    Updated=TO_TIMESTAMP('2025-06-18 16:34:39', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE ad_field_id = 149
  and ad_language = 'en_US'
;