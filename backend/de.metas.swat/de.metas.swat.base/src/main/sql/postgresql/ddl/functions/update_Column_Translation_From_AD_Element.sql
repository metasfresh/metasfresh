DROP FUNCTION IF EXISTS update_Column_Translation_From_AD_Element(numeric, character varying);

CREATE OR REPLACE FUNCTION update_Column_Translation_From_AD_Element
(
	p_AD_Element_ID numeric,
	p_AD_Language character varying = null
)
RETURNS void
AS
$BODY$
DECLARE
	update_count integer;
BEGIN
	UPDATE AD_Column_Trl t
	SET
		IsTranslated = x.IsTranslated,
		Name = x.Name,
		Description = x.Description,
        Updated = x.Updated
	FROM
	(
		select
			c.AD_Column_ID,
			etrl.AD_Element_ID,
			etrl.AD_Language,
			etrl.IsTranslated,
			etrl.Name,
			etrl.Description,
            etrl.updated
		from AD_Element_Trl_Effective_v etrl
			join AD_Column c on c.AD_Element_ID = etrl.AD_Element_ID
		where
			etrl.AD_Element_ID = p_AD_Element_ID
			and (p_AD_Language is null OR etrl.AD_Language = p_AD_Language)
	) x
	WHERE
		t.AD_Column_ID = x.AD_Column_ID
		and t.AD_Language = x.AD_Language
        AND t.updated <> x.updated
	;
	--
	GET DIAGNOSTICS update_count = ROW_COUNT;
	raise notice 'Update % AD_Column_Trl rows using AD_Element_ID=%, AD_Language=%', update_count, p_AD_Element_ID, p_AD_Language;

END;
$BODY$
LANGUAGE plpgsql
VOLATILE
SECURITY DEFINER
COST 100;

COMMENT ON FUNCTION update_Column_Translation_From_AD_Element(numeric, character varying) IS
'Update AD_Column_Trl from AD_Column.AD_Element_ID.';
