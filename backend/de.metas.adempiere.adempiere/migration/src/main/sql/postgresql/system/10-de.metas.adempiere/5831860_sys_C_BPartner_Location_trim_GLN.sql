-- C_BPartner_Location.GLN is now trimmed on save (interceptor C_BPartner_Location, blank -> null).
-- One-time cleanup of the existing rows that still carry leading/trailing whitespace; only those rows are written.
UPDATE C_BPartner_Location
SET GLN       = NULLIF(REGEXP_REPLACE(GLN, '^\s+|\s+$', '', 'g'), ''),
    Updated   = NOW(),
    UpdatedBy = 99
WHERE GLN ~ '^\s|\s$'
;
