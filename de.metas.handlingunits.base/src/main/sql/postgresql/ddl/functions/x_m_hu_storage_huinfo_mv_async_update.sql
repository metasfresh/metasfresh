
DROP FUNCTION if exists x_m_hu_storage_huinfo_mv_async_update();
CREATE OR REPLACE FUNCTION x_m_hu_storage_huinfo_mv_async_update()
  RETURNS integer AS
$BODY$
DECLARE
	v_Lock_ID integer;
BEGIN
	-- get our tag/next lock ID
	SELECT nextval('ad_pinstance_seq') INTO v_Lock_ID;

	-- tag/lock the *current* records in x_m_hu_storage_huinfo_mv_recompute, the rest of the function will only deal with those tagged records
	-- we do this so that we don't have to care about new recompute-records that show up while this function runs
	UPDATE x_m_hu_storage_huinfo_mv_recompute SET Lock_ID = v_Lock_ID;

	-- clear rv_m_hu_storage_huinfo_mv from all HUs that were scheduled for recomputation
	DELETE FROM x_m_hu_storage_huinfo_mv v
		USING x_m_hu_storage_huinfo_mv_recompute r -- "using" might be faster then IN (subquery) 
	WHERE v.M_HU_ID = r.M_HU_ID 
		AND r.Lock_ID = v_Lock_ID;
	
	-- create new rv_m_hu_storage_huinfo_mv records. Note that for e.g. destroyed HUs, the view rv_m_hu_storage_huinfo_v won't record new records*/
	-- note: the view already incorporates the recompute table, but we only want to add records we locked because we just deleted stale record that were locked, and if we now also add unlocked records, there might be doubles
	INSERT INTO x_m_hu_storage_huinfo_mv 
	SELECT v.* FROM x_m_hu_storage_huinfo_v v
		JOIN x_m_hu_storage_huinfo_mv_recompute r ON r.M_HU_ID = v.m_hu_id 
	WHERE r.Lock_ID = v_Lock_ID;
	
	/* finally, delete the x_m_hu_storage_huinfo_mv_recompute records that we just processed */
	DELETE FROM x_m_hu_storage_huinfo_mv_recompute WHERE Lock_ID=v_Lock_ID;

	RETURN 0;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

  
COMMENT ON FUNCTION x_m_hu_storage_huinfo_mv_async_update()
  IS 'See task 07919';
