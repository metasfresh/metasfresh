
CREATE OR REPLACE FUNCTION dlm.get_dlm_setting(IN p_setting text, IN p_default smallint)
  RETURNS smallint AS
$BODY$
DECLARE 
	v_result smallint;
BEGIN
	select current_setting(p_setting)::smallint INTO v_result;
	RETURN v_result;
EXCEPTION 
	WHEN undefined_object /* SQLSTATE '42704' */
	THEN 
		RAISE NOTICE '% is not yet set. Setting it to % for now', p_setting, p_default;
		PERFORM set_config(p_setting, p_default::text, false);
		RETURN p_default;
END;	
$BODY$
  LANGUAGE plpgsql STABLE;
COMMENT ON FUNCTION dlm.get_dlm_setting(text, smallint) IS 'gh #489: Gets the given setting using current_setting(). 
If that setting is not defined (SQLSTATE ''42704'') then it sets it using set_config() and returns the given default value.'
;