CREATE OR REPLACE FUNCTION cleanWhiteSpaces(character varying) RETURNS character varying LANGUAGE plpgsql AS $$
DECLARE
  declare step1 character Varying;

BEGIN
  step1 = regexp_replace($1, E'[\\n\\r]+', ' ', 'g' );

  RETURN trim(regexp_replace(step1, '\s+', ' ', 'g'));
END
$$;

COMMENT ON FUNCTION cleanWhiteSpaces(character varying) IS 'Replaces new lines, tabs and multiple whitespaces with a single whitespace';