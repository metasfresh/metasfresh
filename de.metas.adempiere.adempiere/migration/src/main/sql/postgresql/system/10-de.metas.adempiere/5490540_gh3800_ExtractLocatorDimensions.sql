drop type if exists LocatorDimensions;
create type LocatorDimensions as (
	warehouseValue character varying
	, locatorValue character varying
	, locatorX character varying
	, locatorY character varying
	, locatorZ character varying
	, locatorX1 character varying
);


CREATE OR REPLACE FUNCTION extractLocatorDimensions(textInput character varying)
RETURNS LocatorDimensions AS
$BODY$
DECLARE
	v_locatorDimensions LocatorDimensions;
	v_dimensions text[];
	v_warehouseValue character varying;
BEGIN
	SELECT regexp_split_to_array(textInput, '-'), 
		CASE
			WHEN substring(textInput,1,2) = regexp_replace(regexp_replace(substring(textInput,1,2), '[-0-9]', 'A'), '[-0-9]', 'A') THEN substring(textInput,1,2)
			ELSE substring(textInput,1,1)
		END AS warehouseValue
		INTO v_dimensions, v_warehouseValue;
		
	v_locatorDimensions.warehouseValue := v_warehouseValue;
	v_locatorDimensions.locatorValue := textInput;
	v_locatorDimensions.locatorX := v_dimensions[2];
	v_locatorDimensions.locatorY := v_dimensions[3];
	v_locatorDimensions.locatorZ := v_dimensions[4];
	v_locatorDimensions.locatorX1 := v_dimensions[5];
	
	return v_locatorDimensions;
END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;




