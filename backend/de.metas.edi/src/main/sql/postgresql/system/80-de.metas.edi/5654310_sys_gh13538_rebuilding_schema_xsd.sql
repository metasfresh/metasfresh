-- 2022-09-01T16:33:47.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version,WhereClause) VALUES (0,0,554218,540645,'N',TO_TIMESTAMP('2022-09-01 19:33:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540422,'Y','N','EDI_Exp_DesadvLineWithNoPack','N','N','N',TO_TIMESTAMP('2022-09-01 19:33:46','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_DesadvLineWithNoPack','*','EDI_DesadvLine_ID not in (select EDI_DesadvLine_ID from Edi_Desadv_Pack_Item)')
;

-- 2022-09-01T16:40:27.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2022-09-01 19:40:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,540422,550569,'Y','N','N','EDI_Exp_DesadvLine',10,'M',TO_TIMESTAMP('2022-09-01 19:40:27','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_DesadvLine')
;

-- 2022-09-01T16:47:58.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET AD_Column_ID=551740, IsMandatory='Y', Name='EDI_DesadvLine_ID', Type='R', Value='EDI_DesadvLine_ID',Updated=TO_TIMESTAMP('2022-09-01 19:47:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550569
;

-- 2022-09-01T16:50:33.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2022-09-01 19:50:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540422,540405,550570,'Y','N','N','EDI_Exp_DesadvLineWithNoPack',370,'M',TO_TIMESTAMP('2022-09-01 19:50:33','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_DesadvLineWithNoPack')
;

-- 2022-09-01T16:52:37.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550536
;

-- 2022-09-01T16:54:13.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550537
;

-- 2022-09-01T16:54:22.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550538
;

-- 2022-09-01T16:54:26.174Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550540
;

-- 2022-09-01T16:54:34.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550541
;

-- 2022-09-01T16:54:46.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550539
;

-- 2022-09-01T16:54:50.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550542
;

-- 2022-09-01T16:54:58.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550546
;

-- 2022-09-01T16:55:11.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550547
;

-- 2022-09-01T16:55:15.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550552
;

-- 2022-09-01T16:55:23.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550550
;

-- 2022-09-01T16:55:35.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550553
;

-- 2022-09-01T16:55:39.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550557
;

-- 2022-09-01T16:55:48.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550558
;

-- 2022-09-01T16:55:51.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550562
;

-- 2022-09-01T16:55:51.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550565
;

-- 2022-09-01T16:55:51.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550551
;

-- 2022-09-01T16:55:52.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550560
;

-- 2022-09-01T16:55:52.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550543
;

-- 2022-09-01T16:55:52.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550544
;

-- 2022-09-01T16:55:52.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550548
;

-- 2022-09-01T16:55:52.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550564
;

-- 2022-09-01T16:55:52.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550566
;

-- 2022-09-01T16:55:52.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550567
;

-- 2022-09-01T16:55:52.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550568
;

-- 2022-09-01T16:55:52.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550556
;

-- 2022-09-01T16:55:52.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550545
;

-- 2022-09-01T16:55:52.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550549
;

-- 2022-09-01T16:55:52.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550555
;

-- 2022-09-01T16:55:52.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550563
;

-- 2022-09-01T16:55:52.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550559
;

-- 2022-09-01T16:55:52.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550561
;

-- 2022-09-01T16:55:52.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550554
;

-- 2022-09-01T16:56:11.729Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_Format WHERE EXP_Format_ID=540420
;

-- 2022-09-01T16:58:53.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550434
;

-- 2022-09-01T16:59:13.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET AD_Column_ID=583443, Name='EDI_DesadvLine_ID', Type='R', Value='EDI_DesadvLine_ID',Updated=TO_TIMESTAMP('2022-09-01 19:59:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550465
;

-- 2022-09-01T21:34:00.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2022-09-02 00:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550030
;

-- 2022-09-03T07:55:20.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET AD_Reference_Override_ID=19,Updated=TO_TIMESTAMP('2022-09-03 10:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550569
;

