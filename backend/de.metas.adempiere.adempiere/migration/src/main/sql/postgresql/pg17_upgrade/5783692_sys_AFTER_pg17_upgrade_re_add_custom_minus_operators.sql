/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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


-- ============================================================================
-- STEP 10: Create DEPRECATED operator functions with warning logging
-- ============================================================================
-- These functions replace the old operators but log a deprecation warning.
-- They log only ONCE per session to avoid performance impact.
-- This serves as a safety net to catch any usages we missed.

-- Function for: timestamptz - numeric (e.g., date - days)
CREATE OR REPLACE FUNCTION public.subtract_numeric_from_timestamptz_deprecated(
    p_timestamp timestamptz,
    p_days numeric
)
    RETURNS timestamptz
    LANGUAGE plpgsql
AS $func$
DECLARE
    v_query TEXT;
    v_warn_key TEXT;
BEGIN
    -- Get current query and create a unique key for this specific usage
    v_query := current_query();
    v_warn_key := 'metasfresh.warned_minus_ts_num_' || md5(v_query);

    -- Log warning once per unique query per session
    IF current_setting(v_warn_key, true) IS NULL THEN
        RAISE WARNING E'\n'
            '[DEPRECATED OPERATOR] timestamp - numeric\n'
            '================================================================================\n'
            'QUERY:\n'
            '%\n'
            '--------------------------------------------------------------------------------\n'
            'FIX: Replace (date - days) with subtractdays(date, days)\n'
            '     Example: TO_CHAR(subtractdays(o.DateOrdered, DiscountDays), ''dd.MM.YYYY'')\n'
            'DOCS: https://github.com/metasfresh/metasfresh/pull/21982\n'
            '================================================================================',
            v_query;
        PERFORM set_config(v_warn_key, 'true', false);
    END IF;

    RETURN subtractdays(p_timestamp, p_days);
END;
$func$;

-- Function for: interval - numeric
CREATE OR REPLACE FUNCTION public.subtract_numeric_from_interval_deprecated(
    p_interval interval,
    p_days numeric
)
    RETURNS interval
    LANGUAGE plpgsql
AS $func$
DECLARE
    v_query TEXT;
    v_warn_key TEXT;
BEGIN
    v_query := current_query();
    v_warn_key := 'metasfresh.warned_minus_int_num_' || md5(v_query);

    IF current_setting(v_warn_key, true) IS NULL THEN
        RAISE WARNING E'\n'
            '[DEPRECATED OPERATOR] interval - numeric\n'
            '================================================================================\n'
            'QUERY:\n'
            '%\n'
            '--------------------------------------------------------------------------------\n'
            'FIX: Replace (interval - days) with subtractdays(interval, days)\n'
            'DOCS: https://github.com/metasfresh/metasfresh/pull/21982\n'
            '================================================================================',
            v_query;
        PERFORM set_config(v_warn_key, 'true', false);
    END IF;

    RETURN subtractdays(p_interval, p_days);
END;
$func$;

-- Function for: numeric - timestamptz (reversed operands)
CREATE OR REPLACE FUNCTION public.subtract_timestamptz_from_numeric_deprecated(
    p_days numeric,
    p_timestamp timestamptz
)
    RETURNS timestamptz
    LANGUAGE plpgsql
AS $func$
DECLARE
    v_query TEXT;
    v_warn_key TEXT;
BEGIN
    v_query := current_query();
    v_warn_key := 'metasfresh.warned_minus_num_ts_' || md5(v_query);

    IF current_setting(v_warn_key, true) IS NULL THEN
        RAISE WARNING E'\n'
            '[DEPRECATED OPERATOR] numeric - timestamp (unusual reversed operands)\n'
            '================================================================================\n'
            'QUERY:\n'
            '%\n'
            '--------------------------------------------------------------------------------\n'
            'FIX: Review and rewrite this unusual pattern\n'
            'DOCS: https://github.com/metasfresh/metasfresh/pull/21982\n'
            '================================================================================',
            v_query;
        PERFORM set_config(v_warn_key, 'true', false);
    END IF;

    RETURN subtractdays(p_timestamp, p_days);
END;
$func$;

-- Function for: numeric - interval (reversed operands)
CREATE OR REPLACE FUNCTION public.subtract_interval_from_numeric_deprecated(
    p_days numeric,
    p_interval interval
)
    RETURNS interval
    LANGUAGE plpgsql
AS $func$
DECLARE
    v_query TEXT;
    v_warn_key TEXT;
BEGIN
    v_query := current_query();
    v_warn_key := 'metasfresh.warned_minus_num_int_' || md5(v_query);

    IF current_setting(v_warn_key, true) IS NULL THEN
        RAISE WARNING E'\n'
            '[DEPRECATED OPERATOR] numeric - interval (unusual reversed operands)\n'
            '================================================================================\n'
            'QUERY:\n'
            '%\n'
            '--------------------------------------------------------------------------------\n'
            'FIX: Review and rewrite this unusual pattern\n'
            'DOCS: https://github.com/metasfresh/metasfresh/pull/21982\n'
            '================================================================================',
            v_query;
        PERFORM set_config(v_warn_key, 'true', false);
    END IF;

    RETURN subtractdays(p_interval, p_days);
END;
$func$;


-- ============================================================================
-- STEP 11: Recreate operators using deprecated functions (SAFETY NET)
-- ============================================================================
-- These operators provide backwards compatibility while logging warnings.
-- They will be removed in a future release once all usages are fixed.
--
-- IMPORTANT: These operators are in the 'public' schema intentionally.
-- Before the NEXT pg_upgrade, they must be removed again and all usages fixed.

CREATE OPERATOR public.- (
    LEFTARG = timestamptz,
    RIGHTARG = numeric,
    FUNCTION = public.subtract_numeric_from_timestamptz_deprecated
    );

CREATE OPERATOR public.- (
    LEFTARG = interval,
    RIGHTARG = numeric,
    FUNCTION = public.subtract_numeric_from_interval_deprecated
    );

CREATE OPERATOR public.- (
    LEFTARG = numeric,
    RIGHTARG = timestamptz,
    FUNCTION = public.subtract_timestamptz_from_numeric_deprecated
    );

CREATE OPERATOR public.- (
    LEFTARG = numeric,
    RIGHTARG = interval,
    FUNCTION = public.subtract_interval_from_numeric_deprecated
    );


-- ============================================================================
-- STEP 12: Final status message
-- ============================================================================

DO $$
    BEGIN
        RAISE NOTICE '';
        RAISE NOTICE '=========================================================';
        RAISE NOTICE 'MIGRATION COMPLETE';
        RAISE NOTICE '=========================================================';
        RAISE NOTICE '';
        RAISE NOTICE 'Custom minus operators have been replaced with DEPRECATED';
        RAISE NOTICE 'versions that log warnings when used.';
        RAISE NOTICE '';
        RAISE NOTICE 'IMPORTANT: Before the next pg_upgrade, you must:';
        RAISE NOTICE '  1. Monitor logs for deprecation warnings';
        RAISE NOTICE '  2. Fix all remaining usages (replace - with subtractdays)';
        RAISE NOTICE '  3. Remove these deprecated operators';
        RAISE NOTICE '';
        RAISE NOTICE 'See: https://github.com/metasfresh/metasfresh/pull/21982';
        RAISE NOTICE '=========================================================';
    END $$;
