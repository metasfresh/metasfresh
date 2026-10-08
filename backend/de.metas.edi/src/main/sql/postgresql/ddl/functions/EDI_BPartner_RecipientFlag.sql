-- Resolves the EDI recipient flag for a business partner / location / message-type combination.
-- Returns 'Y' or 'N' (character(1)) based on the C_BPartner_EDI_Setting row with the lowest SeqNo
-- (tie-broken by C_BPartner_EDI_Setting_ID) among rows where the location matches exactly or is NULL.
--
-- p_message_type: 'D' = DESADV (IsEdiDesadvRecipient), 'I' = INVOIC (IsEdiInvoicRecipient)
--
-- This function exists so the 4 virtual AD_Column.ColumnSQL expressions can be trivial
-- single-level subqueries (ParsedSql-safe) rather than doubly-nested ORDER BY/LIMIT subqueries
-- which break de.metas.security.impl.ParsedSql.extractAllSqlSelectStatements.
CREATE OR REPLACE FUNCTION EDI_BPartner_RecipientFlag(
    p_c_bpartner_id          numeric,
    p_c_bpartner_location_id numeric,
    p_message_type           character
) RETURNS character
    LANGUAGE sql
    STABLE
AS
$$
SELECT COALESCE(
    (SELECT CASE WHEN p_message_type = 'D' THEN s.IsEdiDesadvRecipient ELSE s.IsEdiInvoicRecipient END
       FROM C_BPartner_EDI_Setting s
      WHERE s.C_BPartner_ID = p_c_bpartner_id
        -- match the exact-location row OR the partner-default (NULL-location) row.
        -- A passed location of 0 ("no location" on some documents) matches no exact row
        -- (the FK never stores 0), so only the NULL-default branch applies — which is intended.
        AND (s.C_BPartner_Location_ID = p_c_bpartner_location_id OR s.C_BPartner_Location_ID IS NULL)
        AND s.IsActive = 'Y'
      ORDER BY s.SeqNo, s.C_BPartner_EDI_Setting_ID
      LIMIT 1),
    'N')::character(1)
$$;
