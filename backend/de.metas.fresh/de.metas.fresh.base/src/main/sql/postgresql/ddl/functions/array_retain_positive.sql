CREATE OR REPLACE FUNCTION array_retain_positive(p_array numeric[])
    RETURNS numeric[] AS
$$
SELECT COALESCE(array_agg(x ORDER BY ordinality), ARRAY[]::numeric[])
FROM unnest(p_array) WITH ORDINALITY AS t(x, ordinality)
WHERE x IS NOT NULL AND x > 0;
$$ LANGUAGE sql IMMUTABLE;
