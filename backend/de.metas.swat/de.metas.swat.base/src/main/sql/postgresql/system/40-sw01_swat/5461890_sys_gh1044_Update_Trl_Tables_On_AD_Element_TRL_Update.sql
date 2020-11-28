-- delete the separated functions if exist

-- this part can be commented up after we are sure the initial functions didn't get to be created in the db

DROP FUNCTION IF EXISTS update_AD_Column_TRL_On_ElementTrl(numeric, character varying);

DROP FUNCTION IF EXISTS update_AD_Field_TRL_On_ElementTrl(numeric, character varying);

DROP FUNCTION IF EXISTS update_AD_Process_Para_TRL_On_ElementTrl(numeric, character varying);

DROP FUNCTION IF EXISTS update_AD_PrintFormatItem_TRL_On_ElementTrl(numeric, character varying);



-- Update all the trl tables in only one functions


/*
	When the AD_Element_trl has one of its values changed, the change shall also propagate to the linked table entries:
 
	AD_Column_TRL -- name, isTranslated
	AD_Process_Para_TRL -- name, description, help, isTranslated
	AD_Field_TRL --name, description, help, isTranslated
	AD_PrintFormatItem_TRL -- printname, isTranslated
*/
DROP FUNCTION IF EXISTS update_TRL_Tables_On_AD_Element_TRL_Update(numeric, character varying);

CREATE OR REPLACE FUNCTION update_TRL_Tables_On_AD_Element_TRL_Update(
    AD_Element_ID numeric,
    AD_Language character varying
) RETURNS VOID LANGUAGE plpgsql SECURITY DEFINER AS $$

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
		
		where e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID
			
	) x
WHERE ctrl.AD_Column_ID = x.AD_Column_ID and ctrl.ad_language = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language;



-- AD_Field_TRL

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
		
		where e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID  and f.IsCentrallyMaintained = 'Y'
	) x
WHERE ftrl.AD_Field_ID = x.AD_Field_ID and ftrl.ad_language = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language;


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
		
		where e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID and pp.IsCentrallyMaintained = 'Y'
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
		
		where e.AD_Element_ID = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Element_ID and pfi.IsCentrallyMaintained = 'Y'
	) x
WHERE pfitrl.AD_PrintFormatItem_ID = x.AD_PrintFormatItem_ID and pfitrl.ad_language = update_TRL_Tables_On_AD_Element_TRL_Update.AD_Language;




END;
$$;
