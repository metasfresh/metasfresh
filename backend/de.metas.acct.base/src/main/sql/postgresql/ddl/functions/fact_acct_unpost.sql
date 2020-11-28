DROP FUNCTION IF EXISTS "de_metas_acct".fact_acct_unpost(character varying, numeric, char(1));

CREATE OR REPLACE FUNCTION "de_metas_acct".fact_acct_unpost(p_tablename character varying,
                                                            p_Record_ID numeric,
                                                            p_Force     char(1) = 'N')
    RETURNS text
AS
$BODY$
DECLARE
    v_lockSQL     text    := '';
    v_AD_Table_ID integer := -1;
    rowcount      integer;
    v_result      text    := '';
BEGIN
    -- Do nothing if there was no record ID provided
    IF (p_Record_ID IS NULL OR p_Record_ID <= 0) THEN
        RETURN 'Nothing to do because Record_ID is null or <= 0';
    END IF;
    --
    -- Get AD_Table_ID
    SELECT AD_Table_ID
    INTO v_AD_Table_ID
    FROM AD_Table
    WHERE UPPER(TableName) = UPPER(p_TableName);
    --
    IF (v_AD_Table_ID IS NULL OR v_AD_Table_ID <= 0) THEN
        RAISE EXCEPTION 'No AD_Table_ID was found for TableName=%', p_TableName;
    END IF;

    --
    -- Lock the record.
    -- Make sure it was not already locked.
    v_lockSQL := 'UPDATE ' || p_TableName || ' SET Processing=''Y'' WHERE ' || p_TableName || '_ID=' || p_Record_ID;
     IF (p_Force <> 'Y') THEN
             v_lockSQL := v_lockSQL || ' AND COALESCE(Processing,''N'')=''N''/*be lenient towards Processing IS NULL*/';
     END IF;
    EXECUTE v_lockSQL;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    IF (rowcount <> 1) THEN
        RAISE NOTICE 'Failed to lock record %, ID=%, Force=%  (affected rows count was %).', p_TableName, p_Record_ID, p_Force, rowcount;
        RAISE NOTICE 'HINT: Check the processing flag, if not force (select Processing from % where %_ID=%)', p_TableName, p_TableName, p_Record_ID;
        RAISE NOTICE 'HINT: Set processing flag to No or use p_Force=Y (update % set Processing=''N'' where %_ID=%)', p_TableName, p_TableName, p_Record_ID;
        RAISE EXCEPTION 'Cannot lock record TableName=%, ID=%. Check previous notices for more info', p_TableName, p_Record_ID;
    END IF;

    --
    -- Delete the Fact_Acct records
    DELETE FROM Fact_Acct fa WHERE fa.AD_Table_ID = v_AD_Table_ID AND fa.Record_ID = p_Record_ID;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := '' || rowcount || ' Fact_Acct records deleted';

    --
    -- Mark the record as not posted, unlock it.
    EXECUTE 'UPDATE ' || p_TableName || ' SET Posted=''N'', Processing=''N'' WHERE ' || p_TableName || '_ID=' || p_Record_ID;

    --
    -- Notify the accounting service
    INSERT INTO "de_metas_acct".accounting_docs_to_repost (tablename, record_id) VALUES (p_TableName, p_Record_ID);

    RETURN v_result;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;

