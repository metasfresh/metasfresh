INSERT INTO AD_Sequence (AD_Sequence_ID, AD_Client_ID, AD_Org_ID, isActive, created, createdBy, updated, updatedBy, name, description, vformat, isAutoSequence, incrementNo, startNo, currentNext, currentNextSys, isAudited, isTableId, prefix, suffix, startNewYear, dateColumn, decimalPattern, customSequenceNoProvider_JavaClass_ID) VALUES (555719, 1000000, 0, 'Y', '2022-01-26 17:41:34.000000 +00:00', 100, '2022-01-26 17:41:34.000000 +00:00', 100, 'AP Invoice', 'AP Invoice', NULL, 'Y', 1, 100000, 100000, 10000, 'N', 'N', NULL, NULL, 'N', NULL, NULL, NULL)
;

-- 2022-01-26T17:59:29.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=555719, IsDocNoControlled='Y',Updated=TO_TIMESTAMP('2022-01-26 19:59:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000005
;

