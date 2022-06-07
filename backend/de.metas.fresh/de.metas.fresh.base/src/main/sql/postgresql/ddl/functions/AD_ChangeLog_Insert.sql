/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

CREATE FUNCTION public.AD_ChangeLog_Insert(p_tablename       varchar,
                                           p_columnname      varchar,
                                           p_record_id       numeric,
                                           p_oldvalue        varchar,
                                           p_newvalue        varchar,
                                           p_AD_PInstance_ID numeric DEFAULT NULL,
                                           p_AD_Session_ID   numeric DEFAULT NULL,
                                           p_trxname         varchar DEFAULT NULL,
                                           p_description     varchar DEFAULT NULL) RETURNS void
    VOLATILE
    LANGUAGE plpgsql
AS
$$
DECLARE

    v_Table_ID       numeric;
    v_Table_Key_Name varchar;
    v_Column_ID      numeric;
    rec              record;

BEGIN

    v_Table_ID := get_table_id(p_tablename);
    v_Table_Key_Name := (SELECT columnname FROM ad_column WHERE iskey = 'Y' AND ad_table_id = v_Table_ID);
    v_Column_ID := (SELECT ad_column_id FROM ad_column WHERE ad_table_id = v_Table_ID AND columnname ILIKE p_columnname);

    EXECUTE FORMAT('SELECT ad_client_id, ad_org_id, created, createdby, updated, updatedby FROM %s where %s = %s', p_tablename, v_Table_Key_Name, p_record_id)
        INTO rec;

    INSERT INTO public.ad_changelog (ad_changelog_id,
                                     ad_session_id,
                                     ad_table_id,
                                     ad_column_id,
                                     ad_client_id,
                                     ad_org_id,
                                     isactive,
                                     created,
                                     createdby,
                                     updated,
                                     updatedby,
                                     record_id,
                                     oldvalue,
                                     newvalue,
                                     undo,
                                     redo,
                                     iscustomization,
                                     trxname,
                                     description,
                                     eventchangelog,
                                     ad_pinstance_id)

    VALUES (NEXTVAL('ad_changelog_seq'),
            p_AD_PInstance_ID,
            v_Table_ID,
            v_Column_ID,
            rec.ad_client_id,
            rec.ad_org_id,
            'Y',
            rec.created,
            rec.createdby,
            rec.updated,
            rec.updatedby,
            p_record_id,
            p_oldvalue,
            p_newvalue,
            NULL,
            NULL,
            'N',
            p_trxname,
            p_description,
            'U',
            p_AD_Session_ID);

END;
$$
;

COMMENT ON FUNCTION public.AD_ChangeLog_Insert(varchar, varchar, numeric, varchar, varchar, numeric, numeric, varchar, varchar) IS 'This function is used to insert records into AD_ChangeLog table, it will fail if the table defined as parameter has more than one ID column.'
;

ALTER FUNCTION public.AD_ChangeLog_Insert(varchar, varchar, numeric, varchar, varchar, numeric, numeric, varchar, varchar) OWNER TO metasfresh
;
