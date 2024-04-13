DROP FUNCTION IF EXISTS userlevel_to_accesslevels(p_UserLevel varchar)
;

CREATE OR REPLACE FUNCTION userlevel_to_accesslevels(p_UserLevel varchar)
    RETURNS VARCHAR[]
AS
$BODY$
BEGIN
    IF p_UserLevel = 'S__' THEN -- system
        RETURN ARRAY ['4', '7', '6'];
    ELSIF p_UserLevel = '_C_' THEN -- client
        RETURN ARRAY ['7', '6', '3', '2'];
    ELSIF p_UserLevel = '_CO' THEN -- client + org
        RETURN ARRAY ['7', '6', '3', '2', '1'];
    ELSEIF p_UserLevel = '__O' THEN -- org
        RETURN ARRAY ['3', '1', '7'];
    ELSE
        RAISE EXCEPTION 'Unknown UserLevel: %', p_UserLevel;
    END IF;
END;
$BODY$
    LANGUAGE plpgsql IMMUTABLE
;


