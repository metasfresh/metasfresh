CREATE OR REPLACE FUNCTION m_inout_export_edi_desadv_xml_fn(p_m_inout_id NUMERIC)
    RETURNS xml
AS
$BODY$
DECLARE
    v_json_data jsonb;
BEGIN
    -- Get the JSON data from the view
    SELECT data
    INTO v_json_data
    FROM m_inout_export_edi_desadv_json_v
    WHERE m_inout_id = p_m_inout_id;

    -- Convert JSON to XML and return
    RETURN XMLELEMENT(
            NAME "DESADV",
            XMLATTRIBUTES('1.0' AS "version"),
            (SELECT XMLAGG(
                            XMLELEMENT(
                                    NAME key,
                                    CASE
                                        WHEN JSONB_TYPEOF(value) = 'object' THEN
                                            (SELECT XMLAGG(
                                                            XMLELEMENT(
                                                                    NAME nested_key,
                                                                    nested_value
                                                            )
                                                    )
                                             FROM JSONB_EACH_TEXT(value) AS n(nested_key, nested_value))
                                        WHEN JSONB_TYPEOF(value) = 'array'  THEN
                                            (SELECT XMLAGG(
                                                            XMLELEMENT(
                                                                    NAME "Item",
                                                                    XMLATTRIBUTES(ordinality AS "index"),
                                                                    CASE
                                                                        WHEN JSONB_TYPEOF(array_element) = 'object' THEN
                                                                            (SELECT XMLAGG(
                                                                                            XMLELEMENT(
                                                                                                    NAME array_key,
                                                                                                    array_value
                                                                                            )
                                                                                    )
                                                                             FROM JSONB_EACH_TEXT(array_element) AS a(array_key, array_value))
                                                                                                                    ELSE
                                                                            XMLELEMENT(NAME "Value", array_element::text) -- Wrap text in an XML element
                                                                    END
                                                            )
                                                    )
                                             FROM JSONB_ARRAY_ELEMENTS(value) WITH ORDINALITY AS arr(array_element, ordinality))
                                                                            ELSE
                                            XMLELEMENT(NAME "Value", value::text) -- Wrap text in an XML element
                                    END
                            )
                    )
             FROM JSONB_EACH(v_json_data) AS j(key, value))
           );
END;
$BODY$
    LANGUAGE plpgsql STABLE
;

COMMENT ON FUNCTION "de.metas.edi".fn_get_desadv_xml(NUMERIC) IS 'Generic function that converts JSON from m_inout_export_edi_desadv_json_v to XML'
;
