DROP FUNCTION IF EXISTS update_FieldTranslation_From_AD_Name_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_FieldTranslation_From_AD_Name_Element
(
	p_AD_Element_ID numeric,
	p_AD_Language character varying = null
)
RETURNS void
AS
$BODY$
DECLARE
	update_count_via_AD_Column integer;
	update_count_via_AD_Name integer;
BEGIN
	--
	-- AD_Field_Trl via AD_Column
	UPDATE AD_Field_Trl t
	SET 
		IsTranslated = x.IsTranslated, 
		Name = x.Name, 
		Description = x.Description, 
		Help = x.Help,
        Updated = x.Updated
	FROM
	(
		select
			f.AD_Field_ID,
			etrl.AD_Element_ID,
			etrl.AD_Language,
			etrl.IsTranslated,
			etrl.Name,
			etrl.Description,
			etrl.Help,
		    etrl.Updated
		from AD_Element_Trl_Effective_v etrl
			join AD_Column c on c.AD_Element_ID = etrl.AD_Element_ID
			join AD_Field f on f.AD_Column_ID = c.AD_Column_ID
		where 
			etrl.AD_Element_ID = p_AD_Element_ID  
			and f.AD_Name_ID is NULL  
			and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
	) x
	WHERE
		t.AD_Field_ID = x.AD_Field_ID
		and t.AD_Language = x.AD_Language
		and t.Updated <> x.Updated
	;
	--
	GET DIAGNOSTICS update_count_via_AD_Column = ROW_COUNT;


	--
	-- AD_Field_Trl via AD_Element -> AD_Name_ID
	UPDATE AD_Field_Trl t
	SET
		IsTranslated = x.IsTranslated,
		Name = x.Name,
		Description = x.Description,
		Help = x.Help,
        Updated = x.Updated
	FROM
	(
		select
			f.AD_Field_ID,
			etrl.AD_Element_ID,
			etrl.AD_Language,
			etrl.IsTranslated,
			etrl.Name,
			etrl.Description,
			etrl.Help,
			etrl.Updated
		from AD_Element_Trl_Effective_v etrl
			join AD_Field f on f.AD_Name_ID = etrl.AD_Element_ID
		where 
			etrl.AD_Element_ID = p_AD_Element_ID
			and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
	) x
	WHERE
		t.AD_Field_ID = x.AD_Field_ID
		and t.AD_Language = x.AD_Language
		and t.Updated <> x.Updated
	;
	--
	GET DIAGNOSTICS update_count_via_AD_Name = ROW_COUNT;

	raise notice 'Updated AD_Field_Trl for AD_Element_ID=%, AD_Language=%: % rows via AD_Column, % rows via AD_Name',
		p_AD_Element_ID, p_AD_Language, update_count_via_AD_Column, update_count_via_AD_Name;
	
END;
$BODY$
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
COST 100;

COMMENT ON FUNCTION update_FieldTranslation_From_AD_Name_Element(numeric, character varying) IS 
'When the AD_Field.AD_Name_ID is changed, uypdate all the AD_Field_Trl entries of the AD_Field, based on the AD_Element behind the AD_Name';
