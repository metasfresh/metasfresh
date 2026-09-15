CREATE OR REPLACE FUNCTION get_bill_to_email(
    p_c_bpartner_id numeric,
    p_c_invoice_id  numeric DEFAULT NULL
)
    RETURNS varchar
AS
$$
DECLARE
    v_invoice_email         varchar;
    v_location_email        varchar;
    v_bill_contact_id       numeric;
    v_bill_contact_email    varchar;
    v_default_contact_email varchar;
BEGIN
    -- If invoice ID provided, get invoice email and location email
    IF p_c_invoice_id IS NOT NULL THEN
        SELECT i.EMail, i.AD_User_ID, bpl.EMail
        INTO v_invoice_email, v_bill_contact_id, v_location_email
        FROM C_Invoice i
                 LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = i.C_BPartner_Location_ID
        WHERE i.C_Invoice_ID = p_c_invoice_id;

        -- If invoice has a bill contact (AD_User_ID), get that contact's email
        IF v_bill_contact_id IS NOT NULL THEN
            SELECT u.EMail
            INTO v_bill_contact_email
            FROM AD_User u
            WHERE u.AD_User_ID = v_bill_contact_id
              AND u.IsActive = 'Y';

            -- Return with priority: invoice email > bill contact email > location email
            RETURN COALESCE(v_invoice_email, v_bill_contact_email, v_location_email);
        END IF;
    END IF;

    -- Try to find default bill-to contact with invoice email enabled
    SELECT u.EMail
    INTO v_default_contact_email
    FROM AD_User u
    WHERE u.C_BPartner_ID = p_c_bpartner_id
      AND u.IsActive = 'Y'
      AND u.IsBillToContact_Default = 'Y'
      AND u.IsInvoiceEmailEnabled = 'Y'
    ORDER BY u.AD_User_ID
    LIMIT 1;

    -- If location email not yet retrieved, get it from business partner
    IF v_location_email IS NULL THEN
        SELECT bpl.EMail
        INTO v_location_email
        FROM C_BPartner_Location bpl
        WHERE bpl.C_BPartner_ID = p_c_bpartner_id
          AND bpl.IsBillTo = 'Y'
          AND bpl.EMail IS NOT NULL
          AND bpl.IsActive = 'Y'
        ORDER BY bpl.C_BPartner_Location_ID
        LIMIT 1;
    END IF;

    -- Return with priority: invoice email > default contact email > location email
    RETURN COALESCE(v_invoice_email, v_default_contact_email, v_location_email);
END;
$$
    LANGUAGE plpgsql
;

COMMENT ON FUNCTION get_bill_to_email(numeric, numeric) IS
    'Retrieves the bill-to email address for a business partner, optionally using invoice-specific information.

    Parameters:
      p_c_bpartner_id - Business partner ID (required)
      p_c_invoice_id  - Invoice ID (optional)

    Returns: Email address (varchar) or NULL if no valid email found

    Logic (matching InvoiceDocOutboundLogMailRecipientProvider.getMailTo):
    1. If invoice provided and has AD_User_ID:
       - Returns: invoice email > bill contact email > invoice location email
    2. Otherwise (no invoice or invoice has no AD_User_ID):
       - Returns: default bill-to contact email > bill-to location email

    Notes:
    - Default bill-to contact must have IsBillToContact_Default=Y and IsInvoiceEmailEnabled=Y
    - Location email comes from invoice''s specific location if invoice provided, otherwise from business partner''s bill-to location'
;
