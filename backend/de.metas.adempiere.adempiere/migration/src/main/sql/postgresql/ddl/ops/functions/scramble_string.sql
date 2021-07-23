CREATE OR REPLACE FUNCTION ops.scramble_string(
    p_string            character varying,
    p_delimiter_pattern character varying = '[@,{}\s]' /*by default, use comma and white-spaces as the delimiter */)
    RETURNS text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE
AS
$BODY$
DECLARE
    v_single_word text;
    v_result      text;
BEGIN
    v_result = p_string; /* init the result with the given input string */

    FOR v_single_word IN SELECT REGEXP_SPLIT_TO_TABLE(p_string, p_delimiter_pattern) /* split the input into single words around the given delimiter pattern */
        LOOP
            /* for each single word, replace it with a random string of the same length */
            v_result = REPLACE(v_result,
                               v_single_word,
                               ARRAY_TO_STRING( /*thx to https://stackoverflow.com/a/54103971/1012103 */
                                       ARRAY(
                                               SELECT SUBSTR('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789', ((RANDOM() * (62 - 1) + 1)::integer), 1)
                                               FROM GENERATE_SERIES(1, LENGTH(v_single_word))
                                           ), '')
                );
        END LOOP;
    RETURN v_result; /* return the result which*/
END;
$BODY$
;

COMMENT ON FUNCTION ops.scramble_string(character varying, character varying)
    IS 'Takes a string an returns a scrambled version of that string, leaving certain characters intact.
By default, both commas and whitespaces (incl line breaks) are left intact, but this can be varied with the p_delimiter_pattern parameter.
Examples: 
  select ops.scramble_string(''Support-User, IT''); might return the string "4anePaQlNpz0, 2k", preserving the command and the space.
  select ops.scramble_string(''Support-User, IT'', ''[-,\s]''); might return the string "mXUgmFl-0FZc, nb", addionally preserving the minus sign.

To scramble all rendered addresses on all C_Invoices while keeping only the formatting (spaces, tabs, newlines), you can do
  update c_invoice set bpartneraddress=ops.scramble_string(bpartneraddress, ''[\s]'');';
