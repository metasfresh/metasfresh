
ALTER TABLE AD_Element_Trl ADD COLUMN AD_Field_Saved_ID numeric(10,0);
ALTER TABLE AD_Field_Trl ADD COLUMN IsSaved character(1) DEFAULT 'N';

DROP VIEW IF EXISTS AD_Field_Trl_to_save_V CASCADE;
CREATE VIEW AD_Field_Trl_to_save_V AS
SELECT * FROM
(
	-- collect element and field via AD_Field.AD_Name_ID
	SELECT 
		et.AD_Element_ID, 
		ft.AD_Field_ID,
		et.AD_Language,
		'AD_Name_ID' AS IsLinkVia,
		ft.IsSaved AS ft_IsSaved,
		et.Name AS et_name, 
		ft.Name AS ft_name,
		et.Description AS et_Description,
		ft.Description AS ft_Description,
		et.help AS et_help,
		ft.help AS ft_help,
		et.IsTranslated AS et_IsTranslated, 
		ft.IsTranslated AS ft_IsTranslated
	FROM AD_Element_Trl et
		JOIN AD_Field f ON et.AD_Element_ID = f.AD_Name_ID
			JOIN AD_Field_Trl ft ON f.AD_Field_ID = ft.AD_Field_ID AND ft.AD_Language=et.AD_Language
	UNION
	-- collect element and field via AD_Column
	SELECT 
		et.AD_Element_ID, 
		ft.AD_Field_ID,
		et.AD_Language,
		'AD_Column_ID' AS IsLinkVia,
		ft.IsSaved AS ft_IsSaved,
		et.Name AS et_name, 
		ft.Name AS ft_name,
		et.Description AS et_Description,
		ft.Description AS ft_Description,
		et.help AS et_help,
		ft.help AS ft_help,
		et.IsTranslated AS et_IsTranslated, 
		ft.IsTranslated AS ft_IsTranslated
	FROM AD_Element_Trl et
		JOIN AD_Column c ON c.AD_Element_ID=et.AD_Element_ID
			JOIN AD_Field f ON f.AD_Column_ID = c.AD_Column_ID AND /*important:*/f.AD_Name_ID IS NULL
				JOIN AD_Field_Trl ft ON f.AD_Field_ID = ft.AD_Field_ID AND ft.AD_Language=et.AD_Language
) data
WHERE ft_IsTranslated='Y'
	AND ( /*the columns are non-empty, and have a different value than the respective AD_Element's columns */
		(TRIM(COALESCE(ft_Name,''))!='' AND ft_Name!=et_name) OR
		(TRIM(COALESCE(ft_Description,''))!='' AND ft_Description!=et_Description) OR
		(TRIM(COALESCE(ft_help,''))!='' AND ft_help!=ft_help OR et_IsTranslated != et_IsTranslated)
	)
;

--select count(*) from AD_Field_Trl_to_save_V;
--1406

--select count(distinct AD_Element_ID) from AD_Field_Trl_to_save_V
--1014

--- AD_Field_Trls to save where more than one AD_Field_Trls refer to the same AD_Element_Trl
-- this view is more for diagnostics, we won't really use it
DROP VIEW IF EXISTS AD_Field_Trl_with_missing_AD_Element_v;
CREATE VIEW AD_Field_Trl_with_missing_AD_Element_v AS
select 
	AD_Element_ID, AD_Language, et_Name, et_IsTranslated, 
	
	array_agg(distinct AD_Field_ID) AS AD_Field_IDs,
	Count(distinct AD_Field_ID) as AD_Field_ID_count,
	
	array_agg(distinct ft_name) AS ft_names, 
	Count(distinct ft_name) as ft_name_count,
	
	array_agg(distinct ft_description) AS ft_descriptions, 
	Count(distinct ft_description) as ft_description_count,
	
	array_agg(distinct ft_help) AS ft_helps, 
	Count(distinct ft_help) as ft_help_count
from AD_Field_Trl_to_save_V
GROUP BY AD_Element_ID, AD_Language, et_IsTranslated, et_Name
HAVING Count(distinct ft_name) > 1 OR Count(distinct ft_description) > 1 OR Count(distinct ft_help) > 1 OR Count(distinct AD_Field_ID) > 1
ORDER BY Count(distinct ft_name) desc;
--192 AD_Element_IDs are affected


--UPDATE AD_Element_Trl SET AD_Field_Saved_ID = null;
--UPDATE AD_Field_Trl SET IsSaved = 'N';

-- create a function to copy the AD_Field_Trls to existing AD_Element_Trls
-- The respecitve update also sets AD_Element_ID.AD_Field_Saved_ID
CREATE OR REPLACE FUNCTION migrate() RETURNS VOID AS
$$
DECLARE
    field_trl RECORD;
	updated_element_trl RECORD;
BEGIN
	FOR field_trl IN SELECT * FROM AD_Field_Trl_to_save_V WHERE ft_IsSaved='N' /*AND AD_Element_ID=348 LIMIT 2*/ LOOP

		RAISE NOTICE 'Processing field_trl with AD_Field_ID=% and lang=%', field_trl.AD_Field_ID, field_trl.AD_Language;
		
		UPDATE AD_Element_Trl et
		SET 
			Name = field_trl.ft_Name,
			Description = field_trl.ft_Description,
			Help = field_trl.ft_Help,
			AD_Field_Saved_ID = field_trl.AD_Field_ID,
			IsTranslated = field_trl.ft_IsTranslated,
			Updated=now(),
			UpdatedBy=99
		WHERE et.AD_Element_ID = field_trl.AD_Element_ID 
			AND et.AD_Language = field_trl.AD_Language 
			AND et.AD_Field_Saved_ID IS NULL
		RETURNING * INTO updated_element_trl;
		
		IF FOUND THEN
							
			UPDATE AD_Field_Trl ft 
			SET IsSaved='Y' 
			WHERE ft.AD_Field_ID=field_trl.AD_Field_ID AND ft.AD_Language = field_trl.AD_Language;
			
			RAISE NOTICE 'Successfully migrated field_trl=%', field_trl;
		ELSE
			-- Note: for many records this is not a problem; 
			-- if their name, description and help are equal to the one that AD_Element_Trl was updated from, then they are also fine now.
			RAISE NOTICE 'another AD_Field_Trl was already migrated to AD_Element_Trl; field_trl=%s', field_trl;
		END IF;
		
	END LOOP;
END;
$$ LANGUAGE plpgsql;

select migrate();

--
-- Now deal with the cases where AD_Field_Trls with different name, description or help refer to the same AD_Element.
-- For these AD_Field_Trls, we create new AD_Element records and link them with AD_Field.AD_Name_ID.
-- When that's done, we create AD_Element_Trl records for the respective preexisting AD_Field_Trls and newly added AD_Elements.

--drop table ad_element_migrate;
CREATE TEMP TABLE ad_element_migrate AS
SELECT 
	nextval('ad_element_seq') AS AD_Element_new_ID,
	ad_field_id,
	name,
	description,
	help,
	entitytype
FROM AD_Field f
WHERE EXISTS (select 1 from AD_Field_Trl_to_save_V v WHERE v.AD_Field_ID=f.AD_Field_ID)
;

INSERT INTO AD_Element (
	ad_element_id, -- numeric(10,0) NOT NULL,
    ad_client_id, -- numeric(10,0) NOT NULL,
    ad_org_id, -- numeric(10,0) NOT NULL,
    isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Y'::bpchar,
    created, -- timestamp with time zone NOT NULL,
    createdby, -- numeric(10,0) NOT NULL,
    updated, -- timestamp with time zone NOT NULL DEFAULT now(),
    updatedby, -- numeric(10,0) NOT NULL,
    columnname, -- character varying(60) COLLATE pg_catalog."default",
    entitytype, -- character varying(512) COLLATE pg_catalog."default" NOT NULL DEFAULT 'D'::character varying,
    name, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    printname, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    description, -- character varying(2000) COLLATE pg_catalog."default",
    help, -- character varying(2000) COLLATE pg_catalog."default",
    po_name, -- character varying(60) COLLATE pg_catalog."default",
    po_printname, -- character varying(60) COLLATE pg_catalog."default",
    po_description, -- character varying(255) COLLATE pg_catalog."default",
    po_help, -- character varying(2000) COLLATE pg_catalog."default",
    widgetsize -- character varying(50) COLLATE pg_catalog."default",
)
SELECT 
	AD_Element_new_ID AS ad_element_id, -- numeric(10,0) NOT NULL,
    0 AS ad_client_id, -- numeric(10,0) NOT NULL,
    0 AS ad_org_id, -- numeric(10,0) NOT NULL,
    'Y' AS isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Y'::bpchar,
    now() AS created, -- timestamp with time zone NOT NULL,
    99 AS createdby, -- numeric(10,0) NOT NULL,
    now() AS updated, -- timestamp with time zone NOT NULL DEFAULT now(),
    99 AS updatedby, -- numeric(10,0) NOT NULL,
    NULL AS columnname, -- character varying(60) COLLATE pg_catalog."default",
    f.entitytype, -- character varying(512) COLLATE pg_catalog."default" NOT NULL DEFAULT 'D'::character varying,
    f.name, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    f.Name AS printname, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    f.description, -- character varying(2000) COLLATE pg_catalog."default",
    f.help, -- character varying(2000) COLLATE pg_catalog."default",
    NULL AS po_name, -- character varying(60) COLLATE pg_catalog."default",
    NULL AS po_printname, -- character varying(60) COLLATE pg_catalog."default",
    NULL AS po_description, -- character varying(255) COLLATE pg_catalog."default",
    NULL AS po_help, -- character varying(2000) COLLATE pg_catalog."default",
    NULL AS widgetsize -- character varying(50) COLLATE pg_catalog."default",
FROM ad_element_migrate f;

UPDATE AD_Field f
SET AD_Name_ID=AD_Element_new_ID
FROM ad_element_migrate m
WHERE f.AD_Field_ID=m.AD_Field_ID;

INSERT INTO AD_Element_Trl (
	ad_element_id, -- numeric(10,0) NOT NULL,
    ad_language, -- character varying(6) COLLATE pg_catalog."default" NOT NULL,
    ad_client_id, -- numeric(10,0) NOT NULL,
    ad_org_id, -- numeric(10,0) NOT NULL,
    isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Y'::bpchar,
    created, -- timestamp with time zone NOT NULL DEFAULT now(),
    createdby, -- numeric(10,0) NOT NULL,
    updated, -- timestamp with time zone NOT NULL DEFAULT now(),
    updatedby, -- numeric(10,0) NOT NULL,
    name, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    printname, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    description, -- character varying(2000) COLLATE pg_catalog."default",
    help, -- character varying(2000) COLLATE pg_catalog."default",
    po_name, -- character varying(60) COLLATE pg_catalog."default",
    po_printname, -- character varying(60) COLLATE pg_catalog."default",
    po_description, -- character varying(255) COLLATE pg_catalog."default",
    po_help, -- character varying(2000) COLLATE pg_catalog."default",
    istranslated, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N'::bpchar,
    ad_field_saved_id -- numeric(10,0),
)
SELECT
	m.AD_Element_new_ID AS ad_element_id, -- numeric(10,0) NOT NULL,
    ft.ad_language, -- character varying(6) COLLATE pg_catalog."default" NOT NULL,
    0 AS ad_client_id, -- numeric(10,0) NOT NULL,
    0 AS ad_org_id, -- numeric(10,0) NOT NULL,
    ft.isactive, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'Y'::bpchar,
    now() AS created, -- timestamp with time zone NOT NULL DEFAULT now(),
    99 AS createdby, -- numeric(10,0) NOT NULL,
    now() AS updated, -- timestamp with time zone NOT NULL DEFAULT now(),
    99 AS updatedby, -- numeric(10,0) NOT NULL,
    ft.name, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    ft.name AS printname, -- character varying(60) COLLATE pg_catalog."default" NOT NULL,
    ft.description, -- character varying(2000) COLLATE pg_catalog."default",
    ft.help, -- character varying(2000) COLLATE pg_catalog."default",
    NULL AS po_name, -- character varying(60) COLLATE pg_catalog."default",
    NULL AS po_printname, -- character varying(60) COLLATE pg_catalog."default",
    NULL AS po_description, -- character varying(255) COLLATE pg_catalog."default",
    NULL AS po_help, -- character varying(2000) COLLATE pg_catalog."default",
    'Y' AS istranslated, -- character(1) COLLATE pg_catalog."default" NOT NULL DEFAULT 'N'::bpchar,
    ft.AD_Field_ID AS ad_field_saved_id -- numeric(10,0),
FROM ad_element_migrate m
	JOIN AD_Field_Trl ft ON ft.AD_Field_Id=m.AD_Field_ID AND ft.IsTranslated='Y'
;
-- with this, insert done
-- SELECT * FROM AD_Field_Trl_to_save_V
--should return no results anymore

--cleanup
COMMIT;
DROP VIEW AD_Field_Trl_to_save_V CASCADE;
ALTER TABLE AD_Element_Trl DROP COLUMN AD_Field_Saved_ID;
ALTER TABLE AD_Field_Trl DROP COLUMN IsSaved;