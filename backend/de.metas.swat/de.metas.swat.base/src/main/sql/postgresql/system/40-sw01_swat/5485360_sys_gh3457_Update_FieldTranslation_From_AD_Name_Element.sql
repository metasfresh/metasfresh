DROP FUNCTION  IF EXISTS public.update_FieldTranslation_From_AD_Name_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION public.update_FieldTranslation_From_AD_Name_Element(
    ad_element_id numeric
   )
  RETURNS void AS
$BODY$

BEGIN

-- AD_Field_TRL via AD_Column

 UPDATE  AD_Field_TRL ftrl
	SET name = x.name, isTranslated = x.isTranslated, description = x.description, help = x.help
	FROM
	(
		select e.AD_Element_ID, f.AD_Field_ID,   ft.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.help
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Column c on e.AD_Element_ID = c.AD_Element_ID
		join AD_Field f on c.AD_Column_ID = f.AD_Column_ID
		join AD_Field_Trl ft on f.AD_Field_ID = ft.AD_Field_ID
		
		where 
			
			e.AD_Element_ID = update_FieldTranslation_From_AD_Name_Element.AD_Element_ID  
			and f.AD_Name_ID is NULL  
			and ft.ad_language = etrl.ad_language 
			
	) x
WHERE ftrl.AD_Field_ID = x.AD_Field_ID and ftrl.ad_language = x.ad_language;


-- AD_Field_TRL via AD_Element -> AD_Name_ID

 UPDATE  AD_Field_TRL ftrl
	SET name = x.name, isTranslated = x.isTranslated, description = x.description, help = x.help
	FROM
	(
		select e.AD_Element_ID, f.AD_Field_ID,   ft.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.help
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Field f on e.AD_Element_ID = f.AD_Name_ID
		join AD_Field_Trl ft on f.AD_Field_ID = ft.AD_Field_ID
		where 
			e.AD_Element_ID = update_FieldTranslation_From_AD_Name_Element.AD_Element_ID  
			and ft.ad_language = etrl.ad_language 
			
	) x
WHERE ftrl.AD_Field_ID = x.AD_Field_ID and ftrl.ad_language = x.ad_language;





END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_FieldTranslation_From_AD_Name_Element(numeric)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_FieldTranslation_From_AD_Name_Element(numeric) IS 'When the AD_Field.AD_Name_ID is changed, uypdate all the AD_Field_Trl entries of the AD_Field, based on the AD_Element behind the AD_Name';
