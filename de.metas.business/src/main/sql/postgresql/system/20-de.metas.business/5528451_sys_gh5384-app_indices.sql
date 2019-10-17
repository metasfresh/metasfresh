

-- slightly related: improve performance on T_Lock related queries by putting Record_ID first
DROP INDEX public.t_lock_reference_multipleowners;

CREATE UNIQUE INDEX t_lock_reference_multipleowners
    ON public.t_lock
    (record_id, owner, ad_table_id)
    WHERE isallowmultipleowners = 'Y';
COMMENT ON INDEX public.t_lock_reference_multipleowners
    IS 'task 09849: if a lock was created with IsAllowMultipleOwners=''Y'', then this index allows multipe references on the same record, as long as they have different owners';

DROP INDEX public.t_lock_reference_singleowner;

CREATE UNIQUE INDEX t_lock_reference_singleowner
    ON public.t_lock
    (record_id, ad_table_id)
WHERE isallowmultipleowners = 'N';
COMMENT ON INDEX public.t_lock_reference_singleowner
    IS 'task 09849: if a lock was created with IsAllowMultipleOwners=''N'', then this index makes sure that no other lock can reference the record in question';


-- there are two indices on Record_ID and AD_Table_ID
-- on is not unique, one is inefficient
DROP INDEX IF EXISTS public.m_shipmentschedule_unique_tableandrecord;
DROP INDEX IF EXISTS public.m_shipmentschedule_record_id_ad_table_id;

CREATE UNIQUE INDEX m_shipmentschedule_record_id_ad_table_id
    ON public.m_shipmentschedule
    (record_id, ad_table_id)
WHERE isactive = 'Y';
COMMENT ON INDEX public.m_shipmentschedule_record_id_ad_table_id
    IS 'putting record_id first because there are less records with the same record-id';

CREATE INDEX m_shipmentschedule_M_Product_ID_CatchUOM_ID
    ON public.m_shipmentschedule
    (M_Product_ID, Catch_UOM_ID)
WHERE isactive = 'Y' and Processed = 'N';
COMMENT ON INDEX public.m_shipmentschedule_M_Product_ID_CatchUOM_ID
    IS 'Supports direct updating of m_shipmentschedule.Catch_UOM_ID when the masterdata has changed';
