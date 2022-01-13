
-- Role WebUI
-- 2021-05-26T10:34:10.477Z
-- URL zum Konzept
UPDATE AD_Role SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2021-05-26 12:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540024
;

-- Role Admin
-- 2021-05-26T10:35:29.775Z
-- URL zum Konzept
UPDATE AD_Role SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2021-05-26 12:35:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=1000000
;

-- Role Quickstart
-- 2021-05-26T10:36:19.334Z
-- URL zum Konzept
UPDATE AD_Role SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2021-05-26 12:36:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Role_ID=540017
;

----

-- changing default settings to as talked with nw
-- 2021-06-16T04:53:20.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-06-16 06:53:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=13025
;


-- 2021-06-16T04:53:22.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Role SET IsChangeLog='Y' WHERE IsChangeLog IS NULL
;

-- 2021-06-16T04:57:12.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-06-16 06:57:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=9593
;

-- 2021-06-16T04:57:13.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Role SET IsManual='Y' WHERE IsManual IS NULL
;

--

--Additional tweaks to reduce useless AD_changeLog records bloating the DB
DELETE FROM AD_ChangeLog WHERE AD_Column_ID=556483;/*delete MD_Candidate.MD_Candidate_Parent_ID AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 556483; /* change AD_Column MD_Candidate.MD_Candidate_Parent_ID to not be AD_ChangeLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=570911;/*delete M_ShipmentSchedule.CanBeExportedFrom AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 570911; /* change AD_Column M_ShipmentSchedule.CanBeExportedFrom to not be AD_ChangeLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=557522;/*delete MD_Candidate_Transaction_Detail.Updated AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 557522; /* change AD_Column MD_Candidate_Transaction_Detail.Updated to not be AD_ChangeLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=551792;/*delete C_Invoice_Candidate.LineNetAmt AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 551792; /* change AD_Column C_Invoice_Candidate.LineNetAmt to not be AD_ChangeLog'ed anymore */


DELETE FROM AD_ChangeLog WHERE AD_Column_ID=544925;/*delete C_Invoice_Candidate.SchedulerResult AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 544925; /* change AD_Column C_Invoice_Candidate.SchedulerResult to not be AD_ChangeLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=2227;/*delete C_OrderLine.QtyInvoiced AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 2227; /* change AD_Column C_OrderLine.QtyInvoiced to not be AD_ChangeLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=552712;/*delete C_Order.QtyInvoiced AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 552712; /* change AD_Column C_Order.QtyInvoiced to not be AD_ChangeLog'ed anymore */


DELETE FROM AD_ChangeLog WHERE AD_Column_ID=571840;/*delete M_ShipmentSchedule.NrOfOLCandsWithSamePOReference AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 571840; /* change AD_Column M_ShipmentSchedule.NrOfOLCandsWithSamePOReference to not be AD_ChangeLog'ed anymore */

-- DDL

COMMIT;

-- 2021-06-16T04:53:22.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_role','IsChangeLog','CHAR(1)',null,'Y')
;

-- 2021-06-16T04:57:13.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_role','IsManual','CHAR(1)',null,'Y')
;
