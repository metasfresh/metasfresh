CREATE OR REPLACE FUNCTION extractLocatorDimensions(textInput character varying)
RETURNS LocatorDimensions AS
$BODY$
DECLARE
	v_locatorDimensions LocatorDimensions;
	v_dimensions text[];
BEGIN
	SELECT regexp_split_to_array(textInput, '-') INTO v_dimensions;
		
	v_locatorDimensions.warehouseValue := v_dimensions[1];
	v_locatorDimensions.locatorValue := v_dimensions[2];
	v_locatorDimensions.locatorX := v_dimensions[3];
	v_locatorDimensions.locatorY := v_dimensions[4];
	v_locatorDimensions.locatorZ := v_dimensions[5];
	v_locatorDimensions.locatorX1 := v_dimensions[6];
	
	return v_locatorDimensions;
END;
$BODY$
LANGUAGE plpgsql STABLE
COST 100;