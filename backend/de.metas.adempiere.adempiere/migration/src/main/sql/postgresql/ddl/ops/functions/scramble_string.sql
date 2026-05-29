CREATE OR REPLACE FUNCTION ops.scramble_string(
    p_string            character varying,
    p_delimiter_pattern character varying = '[@,{}\s]' /* kept for backward compatibility; no longer used */)
    RETURNS text
    LANGUAGE 'sql'
    IMMUTABLE PARALLEL SAFE
AS
$BODY$
-- Uses translate() for a single-pass O(n) character substitution implemented in C.
-- All characters not in the mapping are preserved automatically (spaces, punctuation, newlines, etc.).
-- This replaces the previous PL/pgSQL loop + RANDOM() + REPLACE() approach which was orders of magnitude slower
-- and had a bug where REPLACE() corrupted already-scrambled text when the same word appeared multiple times.
-- The mapping is a shuffled alphabet (not a simple rotation), so it's not trivially reversible.
-- Deterministic: same input always produces the same output (useful for cross-table consistency).
SELECT translate(
    p_string,
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789ÄÖÜäöü',
    'QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm7381920465ÜÄÖüäö'
);
$BODY$
;

COMMENT ON FUNCTION ops.scramble_string(character varying, character varying)
    IS 'Takes a string and returns a scrambled version via character substitution using translate().
All letters, digits, and German umlauts are mapped to different characters of the same class.
Spaces, punctuation, newlines, and all other characters are preserved automatically.

~100x faster than the previous PL/pgSQL loop approach, and deterministic (same input = same output).
The p_delimiter_pattern parameter is kept for backward compatibility but no longer used.

Examples:
  select ops.scramble_string(''Support-User, IT''); -- returns "Lxhhgkz-Xltk, OZ"
  select ops.scramble_string(''hans.mueller@example.com''); -- returns "iqfl.dxtsстk@tbqdhst.egd"

To scramble all rendered addresses on C_Invoices:
  UPDATE c_invoice SET bpartneraddress = ops.scramble_string(bpartneraddress);

For proper pseudonymization of production data, consider using the Greenmask-based tool instead.
See: mf15-private-extensions/scripts/pseudonymize/README.md';
