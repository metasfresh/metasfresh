/*
 * #%L
 * de.metas.async
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

create or replace function "de.metas.async".ExecuteSqlAsync(
    p_workpackage_sql text,
    p_after_finish_sql text default '')
    returns void
as
$BODY$
declare
    p_AD_Client_ID numeric := 1000000;
    p_AD_Org_ID numeric := 0;
    p_CreatedBy numeric := 99; -- migration user
    p_AD_User_ID numeric := 100; -- SuperUser
    p_AD_Role_ID numeric := 1000000; -- Admin
    p_TS timestamp with time zone := now();
    --
    v_ProcessorClassname text := 'de.metas.migration.async.ExecuteSQLWorkpackageProcessor';
    v_C_Queue_PackageProcessor_ID numeric;
    --
    v_C_Queue_Workpackage_ID numeric;

begin
    --
    -- Get C_Queue_PackageProcessor_ID
    select c_queue_packageprocessor_id
    into v_C_Queue_PackageProcessor_ID
    from c_queue_packageprocessor p
    where p.Classname=v_ProcessorClassname;
    if (coalesce(v_C_Queue_PackageProcessor_ID, 0) <= 0) then
        raise exception 'No C_Queue_PackageProcessor_ID found for %', v_ProcessorClassname;
    end if;

    --
    -- Create workpackage
    v_C_Queue_Workpackage_ID := nextval('c_queue_workpackage_seq');
    INSERT INTO C_Queue_Workpackage
    (
        c_queue_packageprocessor_id
    , c_queue_workpackage_id
    , IsReadyForProcessing
    , ad_role_id, ad_user_id
        --
    , ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
    )
    values (
               v_C_Queue_PackageProcessor_ID
           , v_C_Queue_Workpackage_ID
           , 'N' -- IsReadyForProcessing
           , p_AD_Role_ID, p_AD_User_ID
               --
           , p_AD_Client_ID, p_AD_Org_ID, p_TS, p_CreatedBy, 'Y', p_TS, p_CreatedBy
           );

    --
    -- Workpackage parameter: Code
    INSERT INTO c_queue_workpackage_param(
                                           c_queue_workpackage_id
                                         , c_queue_workpackage_param_id
                                         , parametername
                                         , ad_reference_id
                                         , p_string
        --
                                         , ad_client_id, ad_org_id, created, createdby, isactive, updated, updatedby
    )
    VALUES (
               v_C_Queue_Workpackage_ID
           , nextval('c_queue_workpackage_param_seq')
           , 'WorkPackageSQL' -- parametername
           , 10 -- ad_reference_id: String
           , p_workpackage_sql
               --
           , p_AD_Client_ID, p_AD_Org_ID, p_TS, p_CreatedBy, 'Y', p_TS, p_CreatedBy
           ),
           (
               v_C_Queue_Workpackage_ID
           , nextval('c_queue_workpackage_param_seq')
           , 'AfterFinishSQL' -- parametername
           , 10 -- ad_reference_id: String
           , p_after_finish_sql
               --
           , p_AD_Client_ID, p_AD_Org_ID, p_TS, p_CreatedBy, 'Y', p_TS, p_CreatedBy
           );

    --
    -- Mark the workpackage as ready for processing
    update C_Queue_Workpackage set IsReadyForProcessing='Y' where C_Queue_Workpackage_ID=v_C_Queue_Workpackage_ID;

    raise notice 'Enqueued workpackage %', v_C_Queue_Workpackage_ID;
end;
$BODY$
    LANGUAGE plpgsql VOLATILE COST 100;

COMMENT ON FUNCTION "de.metas.async".executeSqlAsync(text, text)
    IS 'Enqueues the given SQL to metasfresh async executor which will execute the SQL given as p_workpackage_sql as many times until the SQL update count becomes ZERO.
If that is the case, then the optional p_after_finish_sql value is executed one time.
The following example updates the M_HU.IsReserved flag to N (100k records at a time) and when there is no null value left, it makes the column mandatory.

SELECT "de.metas.async".executeSqlAsync(
	''UPDATE M_HU SET IsReserved=''N'' WHERE M_HU_ID IN (select M_HU_ID from M_HU where IsReserved IS NULL ORDER BY M_HU_ID DESC LIMIT 100000)'',
	''ALTER TABLE public.m_hu ALTER COLUMN IsReserved SET NOT NULL''
);
';
