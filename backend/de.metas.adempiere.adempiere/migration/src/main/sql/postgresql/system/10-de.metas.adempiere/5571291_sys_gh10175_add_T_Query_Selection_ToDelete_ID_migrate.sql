
-- update existing values
-- the sequence was created in the predecessor script
UPDATE T_Query_Selection_ToDelete SET T_Query_Selection_ToDelete_ID=nextval('t_query_selection_todelete_seq') where T_Query_Selection_ToDelete_ID IS NULL;
