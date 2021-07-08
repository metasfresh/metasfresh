DROP FUNCTION IF EXISTS migrationscript_ignore(character varying)
;

CREATE FUNCTION migrationscript_ignore(projectAndFileName character varying)
    RETURNS character varying
AS
$BODY$
DECLARE
    msg_parameterFormat       text := 'Parameter format example: 99-de.metas.mf15/1234567_script.sql.';
    v_projectAndFileName_norm character varying;
    v_scriptName              character varying;
    v_projectName             character varying;
    v_name                    character varying;
BEGIN

    v_projectAndFileName_norm := trim(projectAndFileName);
    v_projectAndFileName_norm := replace(v_projectAndFileName_norm, '->', '/');
    IF (v_projectAndFileName_norm IS NULL OR length(v_projectAndFileName_norm) <= 0) THEN
        RAISE EXCEPTION 'Parameter shall not be null or blank. %', msg_parameterFormat;
    END IF;

    v_projectName := trim(split_part(v_projectAndFileName_norm, '/', 1));
    IF (v_projectName IS NULL OR length(v_projectName) <= 0) THEN
        RAISE EXCEPTION 'Cannot extract ProjectName from %. %', projectAndFileName, msg_parameterFormat;
    END IF;

    v_scriptName := trim(split_part(v_projectAndFileName_norm, '/', 2));
    IF (v_scriptName IS NULL OR length(v_scriptName) <= 0) THEN
        RAISE EXCEPTION 'Cannot extract ScriptName from %. %', projectAndFileName, msg_parameterFormat;
    END IF;

    v_name := v_projectName || '->' || v_scriptName;

    INSERT INTO AD_MigrationScript (ad_client_id,
                                    ad_migrationscript_id,
                                    ad_org_id,
                                    created,
                                    createdby,
                                    description,
                                    developername,
                                    isactive,
                                    name,
                                    projectname,
                                    reference,
                                    releaseno,
                                    scriptroll,
                                    status,
                                    url,
                                    updated,
                                    updatedby,
                                    isapply,
                                    filename,
                                    script)
    VALUES (0,
            nextval('ad_migrationscript_seq'),
            0,
            now(),
            100,
            'Inserted by migrationscript_ignore with parameter projectAndFileName=' || projectAndFileName,
            NULL,
            'Y',
            v_name, -- name
            v_projectName, -- projectname
            NULL,
            '1',
            NULL,
            'CO',
            NULL,
            now(),
            100,
            'N',
            v_scriptName, -- filename
            NULL)
    ON CONFLICT DO NOTHING /*if the file is already recorded in AD_MigrationScript, then do nothing */
    ;

    RETURN 'Ignored ' || v_name;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;

COMMENT ON FUNCTION migrationscript_ignore(character varying)
    IS 'Inserts the given script into the AD_MigrationScript table, so it will be ignored on future rollouts.
usage example:
select migrationscript_ignore(''configuration/changingpartnerwindow.sql'');

Please keep in sync with http://docs.metasfresh.org/sql_collection/migrationscript_helper_functions.html
'
;
