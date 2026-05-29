--drop function jsonb_to_xml
CREATE OR REPLACE FUNCTION jsonb_to_xml_fn(p_json jsonb)
    RETURNS xml
    LANGUAGE plpgsql
AS
$$
DECLARE
    result_xml text := '';
    key        text;
    value      jsonb;
    nested_xml xml;
BEGIN
    -- Handle null input
    IF p_json IS NULL THEN
        RETURN NULL;
    END IF;

    -- Handle different JSON types
    CASE JSONB_TYPEOF(p_json)
        WHEN 'object'            THEN -- Iterate through object properties
        FOR key, value IN SELECT * FROM JSONB_EACH(p_json)
            LOOP
                CASE JSONB_TYPEOF(value)
                    WHEN 'object', 'array'   THEN -- Recursively handle nested objects/arrays
                    nested_xml := jsonb_to_xml_fn(value);
                    result_xml := result_xml || '<' || key || '>' || nested_xml::text || '</' || key || '>';
                    WHEN 'string'            THEN result_xml := result_xml || '<' || key || '>' || XMLSERIALIZE(CONTENT value #>> '{}' AS text) || '</' || key || '>';
                    WHEN 'number', 'boolean' THEN result_xml := result_xml || '<' || key || '>' || (value #>> '{}') || '</' || key || '>';
                    WHEN 'null'              THEN result_xml := result_xml || '<' || key || '/>';
                END CASE;
            END LOOP;

        WHEN 'array'             THEN -- Handle arrays by creating numbered elements
        FOR i IN 0..JSONB_ARRAY_LENGTH(p_json) - 1
            LOOP
                value := p_json -> i;
                CASE JSONB_TYPEOF(value)
                    WHEN 'object', 'array'   THEN nested_xml := jsonb_to_xml_fn(value);
                                                  result_xml := result_xml || '<item>' || nested_xml::text || '</item>';
                    WHEN 'string'            THEN result_xml := result_xml || '<item>' || XMLSERIALIZE(CONTENT value #>> '{}' AS text) || '</item>';
                    WHEN 'number', 'boolean' THEN result_xml := result_xml || '<item>' || (value #>> '{}') || '</item>';
                    WHEN 'null'              THEN result_xml := result_xml || '<item/>';
                END CASE;
            END LOOP;

        WHEN 'string'            THEN result_xml := XMLSERIALIZE(CONTENT p_json #>> '{}'::xml AS text);
        WHEN 'number', 'boolean' THEN result_xml := p_json #>> '{}';
        WHEN 'null'              THEN result_xml := '';
    END CASE;

    RETURN result_xml::xml;
END;
$$
;

--SELECT jsonb_to_xml_fn('{"name": "John", "middle": null, "age": 30}'::jsonb);