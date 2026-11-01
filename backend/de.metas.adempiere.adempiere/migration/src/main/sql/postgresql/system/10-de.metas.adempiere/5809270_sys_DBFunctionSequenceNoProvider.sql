-- Register the generic DBFunctionSequenceNoProvider as a selectable AD_Sequence.CustomSequenceNoProvider_JavaClass_ID.
-- For a sequence whose Name is <seqName>, the provider reads the per-sequence SysConfig
-- "de.metas.document.seqNo.DBFunctionSequenceNoProvider.<seqName>.dbFunctionName" to get a PL/pgSQL function name,
-- then calls <fn>(Record_ID, generated_at) and returns its result as the full sequence string (no counter appended).
INSERT INTO AD_JavaClass (AD_Client_ID,AD_JavaClass_ID,AD_JavaClass_Type_ID,AD_Org_ID,Classname,Created,CreatedBy,Description,EntityType,IsActive,IsInterface,Name,Updated,UpdatedBy)
VALUES (0,540102 /*From ID Server*/,540040,0,'de.metas.document.sequenceno.DBFunctionSequenceNoProvider',TO_TIMESTAMP('2026-06-22 16:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Generic custom AD_Sequence number provider. For a sequence whose Name is <seqName>, reads the SysConfig ''de.metas.document.seqNo.DBFunctionSequenceNoProvider.<seqName>.dbFunctionName'' to get a PL/pgSQL function name, then calls <fn>(Record_ID, generated_at) and returns its result as the full sequence string (no incremental counter appended). Configure one SysConfig per sequence that should use it.','D','Y','N','DBFunctionSequenceNoProvider',TO_TIMESTAMP('2026-06-22 16:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
