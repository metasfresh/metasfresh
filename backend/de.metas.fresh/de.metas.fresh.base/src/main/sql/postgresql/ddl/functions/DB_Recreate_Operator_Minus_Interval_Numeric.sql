/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

CREATE OR REPLACE FUNCTION public.DB_Recreate_Operator_Minus_Interval_Numeric()
    RETURNS void
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v                  RECORD;
    f                  RECORD;
    i                  INT := 0;
    j                  INT;
    k                  INT := 0;
    m                  INT;
    view_schemas       TEXT[];
    view_names         TEXT[];
    view_relkinds      CHAR[];
    view_defs          TEXT[];
    view_owners        TEXT[];
    view_owner_oids    OID[];
    view_options       TEXT[];
    view_grants        TEXT[][];
    view_indexes       TEXT[][];
    func_schemas       TEXT[];
    func_names         TEXT[];
    func_defs          TEXT[];
    func_owners        TEXT[];
    func_owner_oids    OID[];
    operator_oid       OID;
    operator_owner     NAME;
    operator_owner_oid OID;
    current_user_oid   OID;
    cmd                TEXT;
    grant_rec          RECORD;
    temp_owner         TEXT;
    temp_owner_oid     OID;
    temp_options       TEXT;
    temp_grants        TEXT[];
    temp_indexes       TEXT[];
    grant_idx          INT;
    index_idx          INT;
    index_rec          RECORD;
    temp_view_grants   TEXT[];
    temp_view_indexes  TEXT[];
BEGIN
    /*
     * 1. Validate that the subtractdays function exists with correct signature
     */
    IF NOT EXISTS (SELECT 1
                   FROM pg_proc p
                            JOIN pg_namespace n ON n.oid = p.pronamespace
                   WHERE n.nspname = 'public'
                     AND p.proname = 'subtractdays'
                     AND p.pronargs = 2
                     AND p.proargtypes[0] = 'interval'::regtype::oid
                     AND p.proargtypes[1] = 'numeric'::regtype::oid) THEN
        RAISE EXCEPTION 'Function public.subtractdays(interval, numeric) does not exist with correct signature. Cannot recreate operator.';
    END IF;

    /*
     * 2. Locate the operator OID and preserve ownership
     */
    SELECT o.oid, o.oprowner, PG_GET_USERBYID(o.oprowner)
    INTO operator_oid, operator_owner_oid, operator_owner
    FROM pg_operator o
             JOIN pg_namespace n ON n.oid = o.oprnamespace
    WHERE n.nspname = 'public'
      AND o.oprname = '-'
      AND o.oprleft = 'interval'::regtype
      AND o.oprright = 'numeric'::regtype;

    IF operator_oid IS NULL THEN
        RAISE EXCEPTION 'Operator public.- (interval, numeric) does not exist. Cannot recreate operator.';
    END IF;

    -- Get current user OID for ownership comparison
    SELECT oid INTO current_user_oid FROM pg_authid WHERE rolname = CURRENT_USER;

    /*
     * Note: PostgreSQL does not support grants on operators, so no grant preservation needed.
     * Operators inherit access from their underlying functions.
     */

    /*
     * 3. Find dependent views and materialized views (deepest first) with cycle prevention
     */
    i := 0;
    FOR v IN
        WITH RECURSIVE view_deps AS (
            -- Base case: direct dependencies on the operator via pg_rewrite
            SELECT DISTINCT ns.nspname      AS view_schema,
                            cls.relname     AS view_name,
                            cls.relkind     AS view_relkind,
                            1               AS depth,
                            ARRAY [cls.oid] AS path,
                            cls.oid         AS view_oid
            FROM pg_depend d
                     JOIN pg_rewrite r ON r.oid = d.objid
                     JOIN pg_class cls ON cls.oid = r.ev_class
                     JOIN pg_namespace ns ON ns.oid = cls.relnamespace
            WHERE d.refobjid = operator_oid
              AND cls.relkind IN ('v', 'm')
              AND d.deptype = 'n' -- normal dependency

            UNION ALL

            -- Recursive case: views depending on previously found views
            SELECT DISTINCT ns2.nspname,
                            cls2.relname,
                            cls2.relkind,
                            vd.depth + 1,
                            vd.path || cls2.oid,
                            cls2.oid
            FROM view_deps vd
                     JOIN pg_depend d ON d.refobjid = vd.view_oid
                     JOIN pg_rewrite r ON r.oid = d.objid
                     JOIN pg_class cls2 ON cls2.oid = r.ev_class
                     JOIN pg_namespace ns2 ON ns2.oid = cls2.relnamespace
            WHERE cls2.relkind IN ('v', 'm')
              AND d.deptype = 'n' -- normal dependency
              AND NOT (cls2.oid = ANY (vd.path)))
        SELECT view_schema, view_name, view_relkind, MAX(depth) AS depth
        FROM view_deps
        GROUP BY view_schema, view_name, view_relkind
        ORDER BY depth DESC
        LOOP
            i := i + 1;

            view_schemas[i] := v.view_schema;
            view_names[i] := v.view_name;
            view_relkinds[i] := v.view_relkind;

            -- Capture view definition
            view_defs[i] :=
                    PG_GET_VIEWDEF(
                            FORMAT('%I.%I', v.view_schema, v.view_name)::regclass,
                            TRUE
                    );

            -- Capture view owner and owner OID
            SELECT c.relowner, PG_GET_USERBYID(c.relowner)
            INTO temp_owner_oid, temp_owner
            FROM pg_class c
                     JOIN pg_namespace n ON n.oid = c.relnamespace
            WHERE n.nspname = v.view_schema
              AND c.relname = v.view_name;

            view_owners[i] := temp_owner;
            view_owner_oids[i] := temp_owner_oid;

            -- Capture view options (security_barrier, check_option)
            SELECT STRING_AGG(
                           CASE
                               WHEN option_name = 'security_barrier' AND LOWER(option_value) = 'true'
                                                                 THEN 'security_barrier=true'
                               WHEN option_name = 'check_option' THEN 'check_option=' || option_value
                           END,
                           ', '
                   )
            INTO temp_options
            FROM PG_OPTIONS_TO_TABLE(
                    (SELECT c2.reloptions
                     FROM pg_class c2
                              JOIN pg_namespace n2 ON n2.oid = c2.relnamespace
                     WHERE n2.nspname = v.view_schema
                       AND c2.relname = v.view_name)
                 );

            view_options[i] := temp_options;

            -- Capture view grants
            -- Note: Only standard table-level grants are captured. Default privileges,
            -- column-level grants, and complex grant scenarios may need manual intervention.
            grant_idx := 0;
            temp_grants := ARRAY []::TEXT[];
            FOR grant_rec IN
                SELECT grantee, privilege_type, is_grantable
                FROM information_schema.role_table_grants
                WHERE table_schema = v.view_schema
                  AND table_name = v.view_name
                LOOP
                    grant_idx := grant_idx + 1;
                    temp_grants[grant_idx] := FORMAT('GRANT %s ON %I.%I TO %I%s',
                                                     grant_rec.privilege_type,
                                                     view_schemas[i],
                                                     view_names[i],
                                                     grant_rec.grantee,
                                                     CASE
                                                         WHEN grant_rec.is_grantable = 'YES'
                                                             THEN ' WITH GRANT OPTION'
                                                             ELSE ''
                                                     END);
                END LOOP;
            view_grants[i] := temp_grants;

            -- Capture materialized view indexes
            index_idx := 0;
            temp_indexes := ARRAY []::TEXT[];
            IF v.view_relkind = 'm' THEN
                FOR index_rec IN
                    SELECT indexdef
                    FROM pg_indexes
                    WHERE schemaname = v.view_schema
                      AND tablename = v.view_name
                    LOOP
                        index_idx := index_idx + 1;
                        temp_indexes[index_idx] := index_rec.indexdef;
                    END LOOP;
            END IF;
            view_indexes[i] := temp_indexes;

            RAISE NOTICE 'Found dependent %: %.% (owner: %, options: %, grants: %, indexes: %)',
                CASE WHEN view_relkinds[i] = 'v' THEN 'view' ELSE 'materialized view' END,
                view_schemas[i], view_names[i], view_owners[i], COALESCE(view_options[i], 'none'),
                grant_idx,
                index_idx;
        END LOOP;

    /*
     * 3b. Find dependent functions
     */
    k := 0;
    FOR f IN
        SELECT DISTINCT n.nspname                                 AS func_schema,
                        p.proname                                 AS func_name,
                        PG_GET_FUNCTIONDEF(p.oid)                 AS func_def,
                        PG_GET_USERBYID(p.proowner)               AS func_owner,
                        p.proowner                                AS func_owner_oid,
                        PG_GET_FUNCTION_IDENTITY_ARGUMENTS(p.oid) AS func_args
        FROM pg_depend d
                 JOIN pg_proc p ON p.oid = d.objid
                 JOIN pg_namespace n ON n.oid = p.pronamespace
        WHERE d.refobjid = operator_oid
          AND d.classid = 'pg_proc'::regclass
          AND d.deptype = 'n'
        ORDER BY n.nspname, p.proname
        LOOP
            k := k + 1;
            func_schemas[k] := f.func_schema;
            func_names[k] := f.func_name || '(' || f.func_args || ')';
            func_defs[k] := f.func_def;
            func_owners[k] := f.func_owner;
            func_owner_oids[k] := f.func_owner_oid;

            RAISE NOTICE 'Found dependent function: %.% (owner: %)',
                func_schemas[k], func_names[k], func_owners[k];
        END LOOP;

    BEGIN
        /*
         * 4. Drop dependent views and materialized views (deepest first)
         */
        IF i > 0 THEN
            FOR j IN 1..i
                LOOP
                    IF view_relkinds[j] = 'v' THEN
                        cmd := FORMAT('DROP VIEW IF EXISTS %I.%I', view_schemas[j], view_names[j]);
                        RAISE NOTICE 'Dropping view %.%', view_schemas[j], view_names[j];
                    ELSE
                        cmd := FORMAT('DROP MATERIALIZED VIEW IF EXISTS %I.%I', view_schemas[j], view_names[j]);
                        RAISE NOTICE 'Dropping materialized view %.%', view_schemas[j], view_names[j];
                    END IF;
                    EXECUTE cmd;
                END LOOP;
        END IF;

        /*
         * 4b. Drop dependent functions
         */
        IF k > 0 THEN
            FOR m IN 1..k
                LOOP
                    cmd := FORMAT('DROP FUNCTION IF EXISTS %I.%s', func_schemas[m], func_names[m]);
                    RAISE NOTICE 'Dropping function %.%', func_schemas[m], func_names[m];
                    EXECUTE cmd;
                END LOOP;
        END IF;

        /*
         * 5. Drop & recreate the operator
         */
        RAISE NOTICE 'Dropping operator public.- (interval, numeric)';
        EXECUTE 'DROP OPERATOR IF EXISTS public.- (interval, numeric)';

        RAISE NOTICE 'Creating operator public.- (interval, numeric) with owner %', operator_owner;
        EXECUTE FORMAT($op$
            CREATE OPERATOR public.- (
                FUNCTION = public.subtractdays,
                LEFTARG  = interval,
                RIGHTARG = numeric
            )
        $op$);

        -- Restore operator ownership
        IF operator_owner_oid IS NOT NULL AND current_user_oid IS NOT NULL
            AND operator_owner_oid <> current_user_oid THEN
            EXECUTE FORMAT('ALTER OPERATOR public.- (interval, numeric) OWNER TO %I', operator_owner);
        END IF;

        /*
         * 5b. Recreate dependent functions
         */
        IF k > 0 THEN
            FOR m IN 1..k
                LOOP
                    RAISE NOTICE 'Recreating function %.%', func_schemas[m], func_names[m];
                    EXECUTE func_defs[m];

                    -- Restore function ownership
                    IF func_owner_oids[m] IS NOT NULL AND current_user_oid IS NOT NULL
                        AND func_owner_oids[m] <> current_user_oid THEN
                        EXECUTE FORMAT('ALTER FUNCTION %I.%s OWNER TO %I', func_schemas[m], func_names[m], func_owners[m]);
                    END IF;
                END LOOP;
        END IF;

        /*
         * 6. Recreate views and materialized views (shallowest first) with full metadata
         */
        IF i > 0 THEN
            FOR j IN REVERSE i..1
                LOOP
                    -- Recreate view or materialized view
                    IF view_relkinds[j] = 'v' THEN
                        cmd := FORMAT('CREATE OR REPLACE VIEW %I.%I AS ', view_schemas[j], view_names[j]) || view_defs[j];
                        RAISE NOTICE 'Recreating view %.%', view_schemas[j], view_names[j];
                        EXECUTE cmd;

                        -- Restore ownership
                        IF view_owner_oids[j] IS NOT NULL AND current_user_oid IS NOT NULL
                            AND view_owner_oids[j] <> current_user_oid THEN
                            EXECUTE FORMAT('ALTER VIEW %I.%I OWNER TO %I', view_schemas[j], view_names[j], view_owners[j]);
                        END IF;

                        -- Restore view options
                        IF view_options[j] IS NOT NULL THEN
                            EXECUTE FORMAT('ALTER VIEW %I.%I SET (%s)', view_schemas[j], view_names[j], view_options[j]);
                        END IF;
                    ELSE
                        cmd := FORMAT('CREATE MATERIALIZED VIEW %I.%I AS ', view_schemas[j], view_names[j]) || view_defs[j];
                        RAISE NOTICE 'Recreating materialized view %.%', view_schemas[j], view_names[j];
                        EXECUTE cmd;

                        -- Restore ownership
                        IF view_owner_oids[j] IS NOT NULL AND current_user_oid IS NOT NULL
                            AND view_owner_oids[j] <> current_user_oid THEN
                            EXECUTE FORMAT('ALTER MATERIALIZED VIEW %I.%I OWNER TO %I', view_schemas[j], view_names[j], view_owners[j]);
                        END IF;

                        -- Restore view options
                        IF view_options[j] IS NOT NULL THEN
                            EXECUTE FORMAT('ALTER MATERIALIZED VIEW %I.%I SET (%s)', view_schemas[j], view_names[j], view_options[j]);
                        END IF;

                        -- Restore materialized view indexes
                        temp_view_indexes := view_indexes[j];
                        IF temp_view_indexes IS NOT NULL THEN
                            FOREACH cmd IN ARRAY temp_view_indexes
                                LOOP
                                    RAISE NOTICE 'Restoring index: %', cmd;
                                    EXECUTE cmd;
                                END LOOP;
                        END IF;
                    END IF;

                    -- Restore grants (common for both views and materialized views)
                    temp_view_grants := view_grants[j];
                    IF temp_view_grants IS NOT NULL THEN
                        FOREACH cmd IN ARRAY temp_view_grants
                            LOOP
                                RAISE NOTICE 'Restoring grant: %', cmd;
                                EXECUTE cmd;
                            END LOOP;
                    END IF;
                END LOOP;
        END IF;

    EXCEPTION
        WHEN OTHERS THEN
            -- Attempt rollback by recreating objects that were dropped
            BEGIN
                RAISE WARNING 'Operator recreation failed: %. Attempting to restore objects...', SQLERRM;

                -- Restore functions first
                IF k > 0 THEN
                    FOR m IN 1..k
                        LOOP
                            BEGIN
                                EXECUTE func_defs[m];

                                IF func_owner_oids[m] IS NOT NULL AND current_user_oid IS NOT NULL
                                    AND func_owner_oids[m] <> current_user_oid THEN
                                    EXECUTE FORMAT('ALTER FUNCTION %I.%s OWNER TO %I', func_schemas[m], func_names[m], func_owners[m]);
                                END IF;

                                RAISE NOTICE 'Successfully restored function %.%', func_schemas[m], func_names[m];
                            EXCEPTION
                                WHEN OTHERS THEN
                                    RAISE WARNING 'Failed to restore function %.%: %', func_schemas[m], func_names[m], SQLERRM;
                            END;
                        END LOOP;
                END IF;

                -- Restore views
                IF i > 0 THEN
                    FOR j IN REVERSE i..1
                        LOOP
                            BEGIN
                                IF view_relkinds[j] = 'v' THEN
                                    cmd := FORMAT('CREATE OR REPLACE VIEW %I.%I AS ', view_schemas[j], view_names[j]) || view_defs[j];
                                    EXECUTE cmd;

                                    IF view_owner_oids[j] IS NOT NULL AND current_user_oid IS NOT NULL
                                        AND view_owner_oids[j] <> current_user_oid THEN
                                        EXECUTE FORMAT('ALTER VIEW %I.%I OWNER TO %I', view_schemas[j], view_names[j], view_owners[j]);
                                    END IF;

                                    IF view_options[j] IS NOT NULL THEN
                                        EXECUTE FORMAT('ALTER VIEW %I.%I SET (%s)', view_schemas[j], view_names[j], view_options[j]);
                                    END IF;
                                ELSE
                                    cmd := FORMAT('CREATE MATERIALIZED VIEW %I.%I AS ', view_schemas[j], view_names[j]) || view_defs[j];
                                    EXECUTE cmd;

                                    IF view_owner_oids[j] IS NOT NULL AND current_user_oid IS NOT NULL
                                        AND view_owner_oids[j] <> current_user_oid THEN
                                        EXECUTE FORMAT('ALTER MATERIALIZED VIEW %I.%I OWNER TO %I', view_schemas[j], view_names[j], view_owners[j]);
                                    END IF;

                                    IF view_options[j] IS NOT NULL THEN
                                        EXECUTE FORMAT('ALTER MATERIALIZED VIEW %I.%I SET (%s)', view_schemas[j], view_names[j], view_options[j]);
                                    END IF;

                                    -- Restore materialized view indexes in exception handler
                                    temp_view_indexes := view_indexes[j];
                                    IF temp_view_indexes IS NOT NULL THEN
                                        FOREACH cmd IN ARRAY temp_view_indexes
                                            LOOP
                                                EXECUTE cmd;
                                            END LOOP;
                                    END IF;
                                END IF;

                                -- Restore view grants in exception handler
                                temp_view_grants := view_grants[j];
                                IF temp_view_grants IS NOT NULL THEN
                                    FOREACH cmd IN ARRAY temp_view_grants
                                        LOOP
                                            EXECUTE cmd;
                                        END LOOP;
                                END IF;

                                RAISE NOTICE 'Successfully restored %.% %',
                                    CASE WHEN view_relkinds[j] = 'v' THEN 'view' ELSE 'materialized view' END,
                                    view_schemas[j], view_names[j];
                            EXCEPTION
                                WHEN OTHERS THEN
                                    RAISE WARNING 'Failed to restore %.% %: %',
                                        CASE WHEN view_relkinds[j] = 'v' THEN 'view' ELSE 'materialized view' END,
                                        view_schemas[j], view_names[j], SQLERRM;
                            END;
                        END LOOP;
                END IF;
            END;

            RAISE EXCEPTION 'Operator recreation failed: %. Some views may need manual restoration.', SQLERRM;
    END;

END;
$$
;

COMMENT ON FUNCTION public.DB_Recreate_Operator_Minus_Interval_Numeric() IS
    'Drops and recreates operator public.- (interval, numeric) by temporarily removing all dependent views and functions than restoring them afterwards.'
;
