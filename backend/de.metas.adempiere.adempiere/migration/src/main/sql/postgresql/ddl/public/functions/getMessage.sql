CREATE FUNCTION getMessage(p_value character varying, p_ad_language  character varying DEFAULT 'de_DE'::character varying) RETURNS character varying
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_msg        varchar;
BEGIN
    SELECT coalesce(mt.msgtext, m.msgtext)
    INTO v_msg
    FROM ad_message m
    LEFT OUTER JOIN AD_Message_trl mt ON m.ad_message_id = mt.ad_message_id and mt.ad_language=p_ad_language
    WHERE m.value=p_value;

    RETURN v_msg;
END;
$$
;
