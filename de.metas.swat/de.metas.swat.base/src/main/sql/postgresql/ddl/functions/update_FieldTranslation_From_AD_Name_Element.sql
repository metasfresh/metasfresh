-- Function: public.update_trl_tables_on_ad_element_trl_update(numeric, character varying)

-- DROP FUNCTION public.update_trl_tables_on_ad_element_trl_update(numeric, character varying);

CREATE OR REPLACE FUNCTION public.update_trl_tables_on_ad_element_trl_update(
    ad_element_id numeric,
    ad_language character varying)
  RETURNS void AS
$BODY$

BEGIN


	-- AD_Column_TRL

    UPDATE AD_Column_TRL ctrl
	SET name = x.name, isTranslated = x.isTranslated
	FROM
	(
		select e.AD_Element_ID,  c.AD_Column_ID, ct.ad_language, etrl.Name, etrl.IsTranslated
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Column c on e.AD_Element_ID = c.AD_Element_ID
		join AD_Column_TRL ct on c.AD_Column_ID = ct.AD_Column_ID
		
		where e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID and ct.ad_language = etrl.ad_language and etrl.ad_language =  update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language
			
	) x
WHERE ctrl.AD_Column_ID = x.AD_Column_ID and ctrl.ad_language = x.AD_Language ;



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
			
			e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID  
			and f.AD_Name_ID is NULL  
			and ft.ad_language = etrl.ad_language 
			and ((update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language is null) OR (etrl.ad_language =  update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language))
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
			e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID  
			and ft.ad_language = etrl.ad_language 
			and ((update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language is null) OR (etrl.ad_language =  update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language ))
	) x
WHERE ftrl.AD_Field_ID = x.AD_Field_ID and ftrl.ad_language = x.ad_language;


-- AD_Process_Para_TRL
    UPDATE  AD_Process_Para_TRL pptrl
	SET name = x.name, isTranslated = x.isTranslated, description = x.description, help = x.help
	FROM
	(
		select e.AD_Element_ID, pp.AD_Process_Para_ID,   ppt.ad_language, etrl.Name, etrl.IsTranslated, etrl.description, etrl.help
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Process_Para pp on e.AD_Element_ID = pp.AD_Element_ID
		join AD_Process_Para_Trl ppt on pp.AD_Process_Para_ID = ppt.AD_Process_Para_ID
		
		where e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID and pp.IsCentrallyMaintained = 'Y'  and ppt.ad_language = etrl.ad_language and etrl.ad_language =  update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language
	) x
WHERE pptrl.AD_Process_Para_ID = x.AD_Process_Para_ID and pptrl.ad_language = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language;


-- AD_PrintFormatItem_TRL

    UPDATE  AD_PrintFormatItem_TRL pfitrl
	SET printName = x.Printname, isTranslated = x.isTranslated
	FROM
	(
		select e.AD_Element_ID, pfi.AD_PrintFormatItem_ID,   pfit.ad_language, etrl.PrintName, etrl.IsTranslated
		from AD_Element e
		join AD_Element_Trl etrl on e.AD_Element_ID = etrl.AD_Element_ID
		join AD_Column c on e.AD_Element_ID = c.AD_Element_ID
		join AD_PrintFormatItem pfi on c.AD_Column_ID = pfi.AD_Column_ID
		join AD_PrintFormatItem_Trl pfit on pfi.AD_PrintFormatItem_ID = pfit.AD_PrintFormatItem_ID
		
		where e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID and pfi.IsCentrallyMaintained = 'Y'  and pfit.ad_language = etrl.ad_language and etrl.ad_language =  update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language
	) x
WHERE pfitrl.AD_PrintFormatItem_ID = x.AD_PrintFormatItem_ID and pfitrl.ad_language = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language;




END;
$BODY$
  LANGUAGE plpgsql VOLATILE SECURITY DEFINER
  COST 100;
ALTER FUNCTION public.update_trl_tables_on_ad_element_trl_update(numeric, character varying)
  OWNER TO metasfresh;
COMMENT ON FUNCTION public.update_trl_tables_on_ad_element_trl_update(numeric, character varying) IS 'When the AD_Element_trl has one of its values changed, the change shall also propagate to the linked table entries:
-AD_Column_TRL -- name, isTranslated
-AD_Process_Para_TRL -- name, description, help, isTranslated
-AD_Field_TRL --name, description, help, isTranslated
-AD_PrintFormatItem_TRL -- printname, isTranslated
';
