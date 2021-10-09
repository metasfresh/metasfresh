-- 2021-09-29T12:59:18.594Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540001,TO_TIMESTAMP('2021-09-29 14:59:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 14:59:18','YYYY-MM-DD HH24:MI:SS'),100,'TB01', 'EXW – Ex Works (Incoterms latest edition)')
;

-- 2021-09-29T12:59:35.437Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='EXW – Ex Works (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 14:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540001
;

-- 2021-09-29T13:09:14.732Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540001,540000,TO_TIMESTAMP('2021-09-29 15:09:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:09:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:10:11.613Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW – Ex Works (Incoterms latest edition',Updated=TO_TIMESTAMP('2021-09-29 15:10:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540000
;

-- 2021-09-29T13:10:15.622Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='EXW – Ex Works (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540000
;

-- 2021-09-29T13:10:19.317Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540001,540001,TO_TIMESTAMP('2021-09-29 15:10:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:10:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:10:56.506Z
-- URL zum Konzept
DELETE FROM C_Incoterms_Trl WHERE C_Incoterms_Trl_ID=540001
;

-- 2021-09-29T13:11:21.065Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540002,TO_TIMESTAMP('2021-09-29 15:11:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:11:21','YYYY-MM-DD HH24:MI:SS'),100,'EXW – Ex Works (Incoterms latest edition', 'EXW – Ex Works')
;

-- 2021-09-29T13:11:35.717Z
-- URL zum Konzept
UPDATE C_Incoterms SET Value='FCA – Free Carrier (Incoterms latest edi',Updated=TO_TIMESTAMP('2021-09-29 15:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-29T13:11:40.876Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FCA – Free Carrier',Updated=TO_TIMESTAMP('2021-09-29 15:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-29T13:11:45.886Z
-- URL zum Konzept
UPDATE C_Incoterms SET Value='TB02',Updated=TO_TIMESTAMP('2021-09-29 15:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-29T13:12:01.445Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FCA – Free Carrier (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-29T13:12:10.738Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FCA – Free Carrier (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:12:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-29T13:12:11.953Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540002,540002,TO_TIMESTAMP('2021-09-29 15:12:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:12:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:12:14.565Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA – Free Carrier (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:12:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540002
;

-- 2021-09-29T13:12:15.914Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA – Free Carrier (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540002
;

-- 2021-09-29T13:12:15.968Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540002
;

-- 2021-09-29T13:12:35.052Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540003,TO_TIMESTAMP('2021-09-29 15:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:12:35','YYYY-MM-DD HH24:MI:SS'),100,'TB03', 'FAS  (Incoterms last edition)')
;

-- 2021-09-29T13:12:57.333Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FAS  (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:12:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-29T13:13:06.454Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FAS  Free Alongside Ship (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-29T13:13:09.852Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FAS  Free Alongside Ship (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-29T13:13:09.962Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540003,540003,TO_TIMESTAMP('2021-09-29 15:13:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:13:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:13:12.589Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS  Free Alongside Ship (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540003
;

-- 2021-09-29T13:13:14.092Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS  Free Alongside Ship (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540003
;

-- 2021-09-29T13:13:14.160Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540003
;

-- 2021-09-29T13:14:46.326Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540004,TO_TIMESTAMP('2021-09-29 15:14:46','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:14:46','YYYY-MM-DD HH24:MI:SS'),100,'TB04', 'FAS  Free Alongside Ship')
;

-- 2021-09-29T13:14:49.349Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FAS  Free Alongside Ship (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2021-09-29T13:14:56.894Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FOB – Free on Board (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2021-09-29T13:14:58.085Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FOB – Free on Board (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:14:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2021-09-29T13:14:58.237Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540004,540004,TO_TIMESTAMP('2021-09-29 15:14:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:14:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:15:00.292Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB – Free on Board (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:15:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540004
;

-- 2021-09-29T13:15:01.422Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB – Free on Board (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:15:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540004
;

-- 2021-09-29T13:15:01.492Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:15:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540004
;

-- 2021-09-29T13:15:40.527Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540005,TO_TIMESTAMP('2021-09-29 15:15:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:15:40','YYYY-MM-DD HH24:MI:SS'),100,'TB05', '(Incoterms last edition)')
;

-- 2021-09-29T13:15:44.197Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name=' (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-29T13:15:50.159Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CFR – Cost and Freight  (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:15:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-29T13:15:51.378Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CFR – Cost and Freight  (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-29T13:15:51.513Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540005,540005,TO_TIMESTAMP('2021-09-29 15:15:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:15:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:15:53.335Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR – Cost and Freight  (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540005
;

-- 2021-09-29T13:15:54.648Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR – Cost and Freight  (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540005
;

-- 2021-09-29T13:15:54.712Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540005
;

-- 2021-09-29T13:16:10.778Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540006,TO_TIMESTAMP('2021-09-29 15:16:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:16:10','YYYY-MM-DD HH24:MI:SS'),100,'TB06', 'Cost and Freight')
;

-- 2021-09-29T13:16:19.652Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='Cost and Freight  (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2021-09-29T13:16:27.194Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CIF – Cost, Insurance & Freight (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2021-09-29T13:16:29.876Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CIF – Cost, Insurance & Freight (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2021-09-29T13:16:30.011Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540006,540006,TO_TIMESTAMP('2021-09-29 15:16:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:16:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:16:31.854Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF – Cost, Insurance & Freight (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540006
;

-- 2021-09-29T13:16:33.297Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF – Cost, Insurance & Freight (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540006
;

-- 2021-09-29T13:16:33.350Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:16:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540006
;

-- 2021-09-29T13:18:15.860Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value,name) VALUES (1000000,1000000,540007,TO_TIMESTAMP('2021-09-29 15:18:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:18:15','YYYY-MM-DD HH24:MI:SS'),100,'TB07', 'CPT – Carriage Paid To')
;

-- 2021-09-29T13:18:33.584Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CPT – Carriage Paid To',Updated=TO_TIMESTAMP('2021-09-29 15:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540007
;

-- 2021-09-29T13:19:07.946Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Name,Updated,UpdatedBy,Value) VALUES (1000000,1000000,540008,TO_TIMESTAMP('2021-09-29 15:19:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',' (Incoterms last edition)',TO_TIMESTAMP('2021-09-29 15:19:07','YYYY-MM-DD HH24:MI:SS'),100,'TB07')
;

-- 2021-09-29T13:19:13.884Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CPT – Carriage Paid To (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540008
;

-- 2021-09-29T13:19:15.418Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CPT – Carriage Paid To (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540008
;

-- 2021-09-29T13:19:17.566Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540008,540007,TO_TIMESTAMP('2021-09-29 15:19:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:19:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:19:19.557Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT – Carriage Paid To (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540007
;

-- 2021-09-29T13:19:21.433Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT – Carriage Paid To (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:19:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540007
;

-- 2021-09-29T13:19:21.514Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:19:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540007
;

-- 2021-09-29T13:19:39.068Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540009,TO_TIMESTAMP('2021-09-29 15:19:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:19:39','YYYY-MM-DD HH24:MI:SS'),100,'TB08', '(Incoterms last edition)')
;

-- 2021-09-29T13:19:53.088Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name=' (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2021-09-29T13:20:00.045Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CIP – Carriage and Insurance Paid to (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2021-09-29T13:20:02.324Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CIP – Carriage and Insurance Paid to (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2021-09-29T13:20:03.523Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540009,540008,TO_TIMESTAMP('2021-09-29 15:20:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:20:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:20:05.131Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP – Carriage and Insurance Paid to (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540008
;

-- 2021-09-29T13:20:06.068Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP – Carriage and Insurance Paid to (Incoterms last edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540008
;

-- 2021-09-29T13:20:06.119Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:20:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540008
;

-- 2021-09-29T13:20:38.534Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CIP – Carriage and Insurance Paid to (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2021-09-29T13:20:44.208Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CIP – Carriage and Insurance Paid to (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2021-09-29T13:20:55.714Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP – Carriage and Insurance Paid to (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540008
;

-- 2021-09-29T13:20:57.842Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP – Carriage and Insurance Paid to (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:20:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540008
;

-- 2021-09-29T13:21:20.030Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FCA – Free Carrier (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-29T13:21:27.907Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FCA – Free Carrier (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-29T13:21:34.453Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA – Free Carrier (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540002
;

-- 2021-09-29T13:21:36.474Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA – Free Carrier (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540002
;

-- 2021-09-29T13:21:45.671Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FAS  Free Alongside Ship (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-29T13:21:47.507Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FAS  Free Alongside Ship (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-29T13:21:52.624Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS  Free Alongside Ship (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540003
;

-- 2021-09-29T13:21:56.366Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS  Free Alongside Ship (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540003
;

-- 2021-09-29T13:22:06.594Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FOB – Free on Board (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2021-09-29T13:22:08.608Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FOB – Free on Board (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2021-09-29T13:22:16.527Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB – Free on Board (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540004
;

-- 2021-09-29T13:22:17.823Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB – Free on Board (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540004
;

-- 2021-09-29T13:22:36.217Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540008
;

-- 2021-09-29T13:22:39.715Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540008
;

-- 2021-09-29T13:22:47.967Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540007
;

-- 2021-09-29T13:22:50.219Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540007
;

-- 2021-09-29T13:23:04.027Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CIF – Cost, Insurance & Freight (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2021-09-29T13:23:06.290Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CIF – Cost, Insurance & Freight (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2021-09-29T13:23:15.239Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF – Cost, Insurance & Freight (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540006
;

-- 2021-09-29T13:23:20.109Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF – Cost, Insurance & Freight (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540006
;

-- 2021-09-29T13:23:31.033Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CFR – Cost and Freight (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-29T13:23:32.912Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CFR – Cost and Freight (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-29T13:23:35.690Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR – Cost and Freight  (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540005
;

-- 2021-09-29T13:23:41.526Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR – Cost and Freight  (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540005
;

-- 2021-09-29T13:23:57.790Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540007
;

-- 2021-09-29T13:23:59.493Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:23:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540007
;

-- 2021-09-29T13:24:00.302Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540007,540009,TO_TIMESTAMP('2021-09-29 15:24:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:24:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:24:01.859Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540009
;

-- 2021-09-29T13:24:02.700Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT – Carriage Paid To (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:24:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540009
;

-- 2021-09-29T13:24:02.776Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:24:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540009
;

-- 2021-09-29T13:24:20.128Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540010,TO_TIMESTAMP('2021-09-29 15:24:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:24:20','YYYY-MM-DD HH24:MI:SS'),100,'TB09', 'DAP (Incoterms latest edition)')
;

-- 2021-09-29T13:24:30.834Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='DAP (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540010
;

-- 2021-09-29T13:24:55.034Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='DAP – Delivered At Place (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:24:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540010
;

-- 2021-09-29T13:24:57.029Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='DAP – Delivered At Place (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:24:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540010
;

-- 2021-09-29T13:24:57.964Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540010,540010,TO_TIMESTAMP('2021-09-29 15:24:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:24:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:24:59.575Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DAP – Delivered At Place (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540010
;

-- 2021-09-29T13:25:01.133Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DAP – Delivered At Place (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540010
;

-- 2021-09-29T13:25:01.211Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540010
;

-- 2021-09-29T13:25:32.476Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540011,TO_TIMESTAMP('2021-09-29 15:25:32','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:25:32','YYYY-MM-DD HH24:MI:SS'),100,'TB', 'test')
;

-- 2021-09-29T13:25:39.182Z
-- URL zum Konzept
UPDATE C_Incoterms SET Value='TB11',Updated=TO_TIMESTAMP('2021-09-29 15:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540011
;

-- 2021-09-29T13:25:41.741Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='(Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540011
;

-- 2021-09-29T13:25:54.773Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='DDP – Delivered Duty Paid (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540011
;

-- 2021-09-29T13:25:56.475Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='DDP – Delivered Duty Paid (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:25:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540011
;

-- 2021-09-29T13:25:56.617Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540011,540011,TO_TIMESTAMP('2021-09-29 15:25:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:25:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:25:58.242Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP – Delivered Duty Paid (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:25:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540011
;

-- 2021-09-29T13:25:59.116Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP – Delivered Duty Paid (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540011
;

-- 2021-09-29T13:25:59.202Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540011
;

-- 2021-09-29T13:26:53.937Z
-- URL zum Konzept
INSERT INTO C_Incoterms (AD_Client_ID,AD_Org_ID,C_Incoterms_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,Value, name) VALUES (1000000,1000000,540012,TO_TIMESTAMP('2021-09-29 15:26:53','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2021-09-29 15:26:53','YYYY-MM-DD HH24:MI:SS'),100,'TB10', 'test')
;

-- 2021-09-29T13:26:56.671Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='(Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:26:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540012
;

-- 2021-09-29T13:27:02.694Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='DPU – Delivered At Place Unloaded (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:27:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540012
;

-- 2021-09-29T13:27:03.702Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='DPU – Delivered At Place Unloaded (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540012
;

-- 2021-09-29T13:27:03.851Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540012,540012,TO_TIMESTAMP('2021-09-29 15:27:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 15:27:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T13:27:06.236Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DPU – Delivered At Place Unloaded (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:27:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540012
;

-- 2021-09-29T13:27:07.339Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DPU – Delivered At Place Unloaded (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540012
;

-- 2021-09-29T13:27:10.444Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 15:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540012
;

-- 2021-09-29T13:27:25.163Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='EXW – Ex Works (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:27:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540001
;

-- 2021-09-29T13:27:29.022Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW – Ex Works (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 15:27:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540000
;

-- 2021-09-29T14:15:47.170Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540001,540015,TO_TIMESTAMP('2021-09-29 16:15:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:15:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:15:51.489Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW - Ab Werk',Updated=TO_TIMESTAMP('2021-09-29 16:15:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540015
;

-- 2021-09-29T14:15:55.109Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540015
;

-- 2021-09-29T14:15:55.830Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:15:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540015
;

-- 2021-09-29T14:17:37.149Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540001,540016,TO_TIMESTAMP('2021-09-29 16:17:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:17:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:17:42.179Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW - Ab Werk',Updated=TO_TIMESTAMP('2021-09-29 16:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540016
;

-- 2021-09-29T14:17:43.308Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='EXW - Ab Werk',Updated=TO_TIMESTAMP('2021-09-29 16:17:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540016
;

-- 2021-09-29T14:17:47.929Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540016
;

-- 2021-09-29T14:17:48.009Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540016
;

-- 2021-09-29T14:17:53.528Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540001,540017,TO_TIMESTAMP('2021-09-29 16:17:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:17:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:17:56.652Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW - Ab Werk',Updated=TO_TIMESTAMP('2021-09-29 16:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540017
;

-- 2021-09-29T14:17:57.921Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='EXW - Ab Werk',Updated=TO_TIMESTAMP('2021-09-29 16:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540017
;

-- 2021-09-29T14:18:02.137Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:18:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540017
;

-- 2021-09-29T14:18:02.204Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:18:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540017
;

-- 2021-09-29T14:18:57.259Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540002,540018,TO_TIMESTAMP('2021-09-29 16:18:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:18:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:19:06.493Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA - Frei Frachtführer',Updated=TO_TIMESTAMP('2021-09-29 16:19:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540018
;

-- 2021-09-29T14:19:07.887Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA - Frei Frachtführer',Updated=TO_TIMESTAMP('2021-09-29 16:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540018
;

-- 2021-09-29T14:19:11.018Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540018
;

-- 2021-09-29T14:19:11.117Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:19:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540018
;

-- 2021-09-29T14:19:22.993Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540002,540019,TO_TIMESTAMP('2021-09-29 16:19:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:19:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:19:25.388Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA - Frei Frachtführer',Updated=TO_TIMESTAMP('2021-09-29 16:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540019
;

-- 2021-09-29T14:19:26.452Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA - Frei Frachtführer',Updated=TO_TIMESTAMP('2021-09-29 16:19:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540019
;

-- 2021-09-29T14:19:29.632Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540019
;

-- 2021-09-29T14:19:29.679Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:19:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540019
;

-- 2021-09-29T14:19:35.478Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540002,540020,TO_TIMESTAMP('2021-09-29 16:19:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:19:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:19:47.352Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA - Frei Frachtführer',Updated=TO_TIMESTAMP('2021-09-29 16:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540020
;

-- 2021-09-29T14:19:48.551Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA - Frei Frachtführer',Updated=TO_TIMESTAMP('2021-09-29 16:19:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540020
;

-- 2021-09-29T14:19:53.227Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540020
;

-- 2021-09-29T14:19:53.347Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540020
;

-- 2021-09-29T14:20:26.436Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FAS  - Free Alongside Ship (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 16:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-29T14:20:32.947Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FAS - Free Alongside Ship (Incoterms latest edition)',Updated=TO_TIMESTAMP('2021-09-29 16:20:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-29T14:20:33.101Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540003,540021,TO_TIMESTAMP('2021-09-29 16:20:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:20:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:20:44.798Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS - Frei längsseits Schiff',Updated=TO_TIMESTAMP('2021-09-29 16:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540021
;

-- 2021-09-29T14:20:45.995Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS - Frei längsseits Schiff',Updated=TO_TIMESTAMP('2021-09-29 16:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540021
;

-- 2021-09-29T14:20:49.555Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540021
;

-- 2021-09-29T14:20:49.595Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540021
;

-- 2021-09-29T14:21:00.381Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540003,540022,TO_TIMESTAMP('2021-09-29 16:21:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:21:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:21:03.035Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS - Frei längsseits Schiff',Updated=TO_TIMESTAMP('2021-09-29 16:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540022
;

-- 2021-09-29T14:21:03.951Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS - Frei längsseits Schiff',Updated=TO_TIMESTAMP('2021-09-29 16:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540022
;

-- 2021-09-29T14:21:07.067Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540022
;

-- 2021-09-29T14:21:07.102Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:21:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540022
;

-- 2021-09-29T14:21:09.808Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540003,540023,TO_TIMESTAMP('2021-09-29 16:21:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:21:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:21:11.380Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS - Frei längsseits Schiff',Updated=TO_TIMESTAMP('2021-09-29 16:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540023
;

-- 2021-09-29T14:21:12.224Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS - Frei längsseits Schiff',Updated=TO_TIMESTAMP('2021-09-29 16:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540023
;

-- 2021-09-29T14:21:18.952Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540023
;

-- 2021-09-29T14:21:19.022Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540023
;

-- 2021-09-29T14:21:47.219Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540004,540024,TO_TIMESTAMP('2021-09-29 16:21:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:21:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:21:56.954Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB - Frei an Bord',Updated=TO_TIMESTAMP('2021-09-29 16:21:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540024
;

-- 2021-09-29T14:21:57.887Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB - Frei an Bord',Updated=TO_TIMESTAMP('2021-09-29 16:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540024
;

-- 2021-09-29T14:22:00.921Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:22:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540024
;

-- 2021-09-29T14:22:01.003Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:22:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540024
;

-- 2021-09-29T14:22:04.796Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540004,540025,TO_TIMESTAMP('2021-09-29 16:22:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:22:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:22:06.397Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB - Frei an Bord',Updated=TO_TIMESTAMP('2021-09-29 16:22:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540025
;

-- 2021-09-29T14:22:07.266Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB - Frei an Bord',Updated=TO_TIMESTAMP('2021-09-29 16:22:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540025
;

-- 2021-09-29T14:22:10.929Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:22:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540025
;

-- 2021-09-29T14:22:10.996Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:22:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540025
;

-- 2021-09-29T14:22:14.865Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540004,540026,TO_TIMESTAMP('2021-09-29 16:22:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:22:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:22:16.326Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB - Frei an Bord',Updated=TO_TIMESTAMP('2021-09-29 16:22:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540026
;

-- 2021-09-29T14:22:17.343Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB - Frei an Bord',Updated=TO_TIMESTAMP('2021-09-29 16:22:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540026
;

-- 2021-09-29T14:22:20.942Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540026
;

-- 2021-09-29T14:22:21.653Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540026
;

-- 2021-09-29T14:22:45.053Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540005,540027,TO_TIMESTAMP('2021-09-29 16:22:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:22:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:22:51.772Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR - Kosten und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:22:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540027
;

-- 2021-09-29T14:22:52.687Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR - Kosten und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540027
;

-- 2021-09-29T14:22:55.613Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540027
;

-- 2021-09-29T14:22:55.694Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:22:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540027
;

-- 2021-09-29T14:22:59.142Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540005,540028,TO_TIMESTAMP('2021-09-29 16:22:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:22:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:23:02.790Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR - Kosten und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540028
;

-- 2021-09-29T14:23:03.680Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR - Kosten und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540028
;

-- 2021-09-29T14:23:06.842Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540028
;

-- 2021-09-29T14:23:06.922Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540028
;

-- 2021-09-29T14:23:13.193Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540005,540029,TO_TIMESTAMP('2021-09-29 16:23:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:23:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:23:14.899Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR - Kosten und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540029
;

-- 2021-09-29T14:23:15.692Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR - Kosten und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540029
;

-- 2021-09-29T14:23:19.678Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:23:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540029
;

-- 2021-09-29T14:23:19.781Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:23:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540029
;

-- 2021-09-29T14:23:59.147Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540006,540030,TO_TIMESTAMP('2021-09-29 16:23:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:23:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:24:07.890Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF - Kosten, Versicherung und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540030
;

-- 2021-09-29T14:24:08.753Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF - Kosten, Versicherung und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540030
;

-- 2021-09-29T14:24:11.619Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540030
;

-- 2021-09-29T14:24:11.690Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:24:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540030
;

-- 2021-09-29T14:24:24.226Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540006,540031,TO_TIMESTAMP('2021-09-29 16:24:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:24:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:24:25.604Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF - Kosten, Versicherung und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:24:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540031
;

-- 2021-09-29T14:24:26.350Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF - Kosten, Versicherung und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:24:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540031
;

-- 2021-09-29T14:24:29.368Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:24:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540031
;

-- 2021-09-29T14:24:29.416Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:24:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540031
;

-- 2021-09-29T14:24:32.037Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540006,540032,TO_TIMESTAMP('2021-09-29 16:24:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:24:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:24:33.648Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF - Kosten, Versicherung und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540032
;

-- 2021-09-29T14:24:34.593Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF - Kosten, Versicherung und Fracht',Updated=TO_TIMESTAMP('2021-09-29 16:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540032
;

-- 2021-09-29T14:24:40.975Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:24:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540032
;

-- 2021-09-29T14:24:41.029Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540032
;

-- 2021-09-29T14:25:53.152Z
-- URL zum Konzept
DELETE FROM C_Incoterms_Trl WHERE C_Incoterms_Trl_ID=540009
;

-- 2021-09-29T14:25:58.320Z
-- URL zum Konzept
DELETE FROM C_Incoterms WHERE C_Incoterms_ID=540007
;

-- 2021-09-29T14:26:06.614Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540008,540033,TO_TIMESTAMP('2021-09-29 16:26:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:26:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:26:25.596Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT - Frachtfrei bis',Updated=TO_TIMESTAMP('2021-09-29 16:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540033
;

-- 2021-09-29T14:26:26.736Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT - Frachtfrei bis',Updated=TO_TIMESTAMP('2021-09-29 16:26:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540033
;

-- 2021-09-29T14:26:29.713Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540033
;

-- 2021-09-29T14:26:29.803Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:26:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540033
;

-- 2021-09-29T14:26:44.245Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540008,540034,TO_TIMESTAMP('2021-09-29 16:26:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:26:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:26:46.692Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT - Frachtfrei bis',Updated=TO_TIMESTAMP('2021-09-29 16:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540034
;

-- 2021-09-29T14:26:47.502Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT - Frachtfrei bis',Updated=TO_TIMESTAMP('2021-09-29 16:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540034
;

-- 2021-09-29T14:26:50.843Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540034
;

-- 2021-09-29T14:26:50.953Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540034
;

-- 2021-09-29T14:26:56.317Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540008,540035,TO_TIMESTAMP('2021-09-29 16:26:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:26:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:26:58.071Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT - Frachtfrei bis',Updated=TO_TIMESTAMP('2021-09-29 16:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540035
;

-- 2021-09-29T14:26:59.187Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT - Frachtfrei bis',Updated=TO_TIMESTAMP('2021-09-29 16:26:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540035
;

-- 2021-09-29T14:27:03.052Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540035
;

-- 2021-09-29T14:27:03.147Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540035
;

-- 2021-09-29T14:27:26.445Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540009,540036,TO_TIMESTAMP('2021-09-29 16:27:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:27:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:27:56.029Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP - Frachtfrei versichert bis',Updated=TO_TIMESTAMP('2021-09-29 16:27:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540036
;

-- 2021-09-29T14:27:57.089Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP - Frachtfrei versichert bis',Updated=TO_TIMESTAMP('2021-09-29 16:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540036
;

-- 2021-09-29T14:28:01.183Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:28:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540036
;

-- 2021-09-29T14:28:01.265Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:28:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540036
;

-- 2021-09-29T14:28:05.638Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540009,540037,TO_TIMESTAMP('2021-09-29 16:28:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:28:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:28:07.318Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP - Frachtfrei versichert bis',Updated=TO_TIMESTAMP('2021-09-29 16:28:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540037
;

-- 2021-09-29T14:28:08.086Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP - Frachtfrei versichert bis',Updated=TO_TIMESTAMP('2021-09-29 16:28:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540037
;

-- 2021-09-29T14:28:11.700Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540037
;

-- 2021-09-29T14:28:12.116Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:28:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540037
;

-- 2021-09-29T14:28:15.862Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540009,540038,TO_TIMESTAMP('2021-09-29 16:28:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:28:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:28:17.384Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP - Frachtfrei versichert bis',Updated=TO_TIMESTAMP('2021-09-29 16:28:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540038
;

-- 2021-09-29T14:28:18.467Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP - Frachtfrei versichert bis',Updated=TO_TIMESTAMP('2021-09-29 16:28:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540038
;

-- 2021-09-29T14:28:23.009Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540038
;

-- 2021-09-29T14:28:23.091Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540038
;

-- 2021-09-29T14:28:52.338Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540011,540039,TO_TIMESTAMP('2021-09-29 16:28:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:28:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:29:26.037Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540039
;

-- 2021-09-29T14:29:27.481Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540039
;

-- 2021-09-29T14:29:32.273Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540039
;

-- 2021-09-29T14:29:32.358Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540039
;

-- 2021-09-29T14:29:37.684Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540011,540040,TO_TIMESTAMP('2021-09-29 16:29:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:29:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:29:39.846Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540040
;

-- 2021-09-29T14:29:41.169Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540040
;

-- 2021-09-29T14:29:44.815Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:29:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540040
;

-- 2021-09-29T14:29:44.877Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:29:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540040
;

-- 2021-09-29T14:29:48.686Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540011,540041,TO_TIMESTAMP('2021-09-29 16:29:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:29:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:29:50.994Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-29T14:29:52.634Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-29T14:29:53.723Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP  - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-29T14:29:55.371Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP  - Geliefert verzollt',Updated=TO_TIMESTAMP('2021-09-29 16:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-29T14:29:58.994Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-29T14:29:59.066Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-29T14:32:05.989Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540012,540042,TO_TIMESTAMP('2021-09-29 16:32:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:32:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:32:20.725Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DPU - Geliefert benannter Ort entladen',Updated=TO_TIMESTAMP('2021-09-29 16:32:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540042
;

-- 2021-09-29T14:32:22.507Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DPU - Geliefert benannter Ort entladen',Updated=TO_TIMESTAMP('2021-09-29 16:32:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540042
;

-- 2021-09-29T14:32:25.710Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540042
;

-- 2021-09-29T14:32:25.807Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540042
;

-- 2021-09-29T14:32:34.801Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540012,540043,TO_TIMESTAMP('2021-09-29 16:32:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:32:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:32:36.437Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DPU - Geliefert benannter Ort entladen',Updated=TO_TIMESTAMP('2021-09-29 16:32:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540043
;

-- 2021-09-29T14:32:37.609Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DPU - Geliefert benannter Ort entladen',Updated=TO_TIMESTAMP('2021-09-29 16:32:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540043
;

-- 2021-09-29T14:32:40.689Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540043
;

-- 2021-09-29T14:32:40.694Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:32:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540043
;

-- 2021-09-29T14:32:44.290Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540012,540044,TO_TIMESTAMP('2021-09-29 16:32:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:32:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:32:45.881Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DPU - Geliefert benannter Ort entladen',Updated=TO_TIMESTAMP('2021-09-29 16:32:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540044
;

-- 2021-09-29T14:32:46.776Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DPU - Geliefert benannter Ort entladen',Updated=TO_TIMESTAMP('2021-09-29 16:32:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540044
;

-- 2021-09-29T14:32:50.538Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540044
;

-- 2021-09-29T14:32:50.603Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:32:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540044
;

-- 2021-09-29T14:33:11.568Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540010,540045,TO_TIMESTAMP('2021-09-29 16:33:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:33:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:33:20.503Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DAP - Geliefert benannter Ort',Updated=TO_TIMESTAMP('2021-09-29 16:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540045
;

-- 2021-09-29T14:33:21.688Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DAP - Geliefert benannter Ort',Updated=TO_TIMESTAMP('2021-09-29 16:33:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540045
;

-- 2021-09-29T14:33:26.581Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_DE',Updated=TO_TIMESTAMP('2021-09-29 16:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540045
;

-- 2021-09-29T14:33:26.655Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540045
;

-- 2021-09-29T14:33:31.769Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540010,540046,TO_TIMESTAMP('2021-09-29 16:33:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:33:33.647Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DAP - Geliefert benannter Ort',Updated=TO_TIMESTAMP('2021-09-29 16:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540046
;

-- 2021-09-29T14:33:34.853Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DAP - Geliefert benannter Ort',Updated=TO_TIMESTAMP('2021-09-29 16:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540046
;

-- 2021-09-29T14:33:39.370Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='de_CH',Updated=TO_TIMESTAMP('2021-09-29 16:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540046
;

-- 2021-09-29T14:33:39.844Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:33:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540046
;

-- 2021-09-29T14:33:57.379Z
-- URL zum Konzept
INSERT INTO C_Incoterms_Trl (AD_Client_ID,AD_Language,AD_Org_ID,C_Incoterms_ID,C_Incoterms_Trl_ID,Created,CreatedBy,IsActive,IsTranslated,Updated,UpdatedBy) VALUES (1000000,'en_US',1000000,540010,540047,TO_TIMESTAMP('2021-09-29 16:33:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',TO_TIMESTAMP('2021-09-29 16:33:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-29T14:33:59.530Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DAP - Geliefert benannter Ort',Updated=TO_TIMESTAMP('2021-09-29 16:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540047
;

-- 2021-09-29T14:34:00.971Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DAP - Geliefert benannter Ort',Updated=TO_TIMESTAMP('2021-09-29 16:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540047
;

-- 2021-09-29T14:34:04.744Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET AD_Language='nl_NL',Updated=TO_TIMESTAMP('2021-09-29 16:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540047
;

-- 2021-09-29T14:34:05.571Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-29 16:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540047
;

-- 2021-09-30T05:43:59.039Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540015
;

-- 2021-09-30T05:44:08.506Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540015
;

-- 2021-09-30T05:44:27Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:44:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540016
;

-- 2021-09-30T05:44:30.448Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540016
;

-- 2021-09-30T05:44:40.092Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:44:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540017
;

-- 2021-09-30T05:44:42.657Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:44:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540017
;

-- 2021-09-30T05:44:57.757Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540018
;

-- 2021-09-30T05:45:01.084Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540018
;

-- 2021-09-30T05:45:08.821Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540019
;

-- 2021-09-30T05:45:11.778Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540019
;

-- 2021-09-30T05:45:27.045Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540020
;

-- 2021-09-30T05:45:30.366Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540020
;

-- 2021-09-30T05:45:45.242Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540021
;

-- 2021-09-30T05:45:48.052Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540021
;

-- 2021-09-30T05:45:56.770Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540022
;

-- 2021-09-30T05:45:58.624Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:45:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540022
;

-- 2021-09-30T05:46:08.486Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540023
;

-- 2021-09-30T05:46:09.876Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540023
;

-- 2021-09-30T05:46:31.377Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540024
;

-- 2021-09-30T05:46:33.162Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540024
;

-- 2021-09-30T05:46:39.798Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540025
;

-- 2021-09-30T05:46:41.354Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540025
;

-- 2021-09-30T05:46:51.446Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540026
;

-- 2021-09-30T05:46:53.131Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:46:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540026
;

-- 2021-09-30T05:47:09.364Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540027
;

-- 2021-09-30T05:47:11.263Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540027
;

-- 2021-09-30T05:47:18.021Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540028
;

-- 2021-09-30T05:47:20.401Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540028
;

-- 2021-09-30T05:47:28.205Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540029
;

-- 2021-09-30T05:47:29.696Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540029
;

-- 2021-09-30T05:47:42.890Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540030
;

-- 2021-09-30T05:47:44.428Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540030
;

-- 2021-09-30T05:47:51.708Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540031
;

-- 2021-09-30T05:47:53.144Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:47:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540031
;

-- 2021-09-30T05:48:00.998Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540032
;

-- 2021-09-30T05:48:02.369Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540032
;

-- 2021-09-30T05:48:14.980Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540033
;

-- 2021-09-30T05:48:16.184Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540033
;

-- 2021-09-30T05:48:23.360Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540034
;

-- 2021-09-30T05:48:25.814Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540034
;

-- 2021-09-30T05:48:33.659Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540035
;

-- 2021-09-30T05:48:34.846Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540035
;

-- 2021-09-30T05:48:46.073Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540036
;

-- 2021-09-30T05:48:47.427Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540036
;

-- 2021-09-30T05:48:54.196Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540037
;

-- 2021-09-30T05:48:55.443Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:48:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540037
;

-- 2021-09-30T05:49:01.947Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540038
;

-- 2021-09-30T05:49:03.085Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540038
;

-- 2021-09-30T05:49:18.041Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540045
;

-- 2021-09-30T05:49:19.513Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540045
;

-- 2021-09-30T05:49:25.820Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540046
;

-- 2021-09-30T05:49:27.081Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540046
;

-- 2021-09-30T05:49:34.586Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540047
;

-- 2021-09-30T05:49:35.879Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540047
;

-- 2021-09-30T05:49:47.363Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540039
;

-- 2021-09-30T05:49:48.567Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540039
;

-- 2021-09-30T05:49:54.201Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540040
;

-- 2021-09-30T05:49:55.458Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540040
;

-- 2021-09-30T05:50:02.886Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DDP  - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-30T05:50:04.246Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DDP  - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540041
;

-- 2021-09-30T05:50:16.639Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540042
;

-- 2021-09-30T05:50:17.804Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540042
;

-- 2021-09-30T05:50:24.977Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540043
;

-- 2021-09-30T05:50:26.516Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540043
;

-- 2021-09-30T05:50:34.358Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Name='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540044
;

-- 2021-09-30T05:50:35.873Z
-- URL zum Konzept
UPDATE C_Incoterms_Trl SET Description='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 07:50:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_Trl_ID=540044
;

-- 2021-09-30T06:41:02.666Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 08:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-30T06:41:04.805Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 08:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-30T06:41:02.666Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 08:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-30T06:41:04.805Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CFR - Kosten und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 08:41:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540005
;

-- 2021-09-30T06:42:11.258Z
-- URL zum Konzept
UPDATE AD_User SET AD_Language='en_US',Updated=TO_TIMESTAMP('2021-09-30 08:42:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_User_ID=100
;

-- 2021-09-30T08:05:41.808Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540001
;

-- 2021-09-30T08:05:46.741Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='EXW - Ab Werk (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540001
;

-- 2021-09-30T08:06:03.751Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:06:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-30T08:06:06.133Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FCA - Frei Frachtführer (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:06:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540002
;

-- 2021-09-30T08:06:20.901Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:06:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-30T08:06:23.137Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FAS - Frei längsseits Schiff (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:06:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540003
;

-- 2021-09-30T08:06:52.520Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:06:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2021-09-30T08:06:54.001Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='FOB - Frei an Bord (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540004
;

-- 2021-09-30T08:07:23.046Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2021-09-30T08:07:24.793Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CIF - Kosten, Versicherung und Fracht (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:07:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540006
;

-- 2021-09-30T08:07:37.943Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540008
;

-- 2021-09-30T08:07:39.630Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CPT - Frachtfrei bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:07:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540008
;

-- 2021-09-30T08:08:02.275Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2021-09-30T08:08:04.116Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='CIP - Frachtfrei versichert bis (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540009
;

-- 2021-09-30T08:08:16.720Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540010
;

-- 2021-09-30T08:08:18.736Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='DAP - Geliefert benannter Ort (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540010
;

-- 2021-09-30T08:08:36.666Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='DDP - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540011
;

-- 2021-09-30T08:08:38.772Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='DDP - Geliefert verzollt (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540011
;

-- 2021-09-30T08:08:54.510Z
-- URL zum Konzept
UPDATE C_Incoterms SET Description='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540012
;

-- 2021-09-30T08:08:56.151Z
-- URL zum Konzept
UPDATE C_Incoterms SET Name='DPU - Geliefert benannter Ort entladen (Incoterms aktuellste Fassung)',Updated=TO_TIMESTAMP('2021-09-30 10:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Incoterms_ID=540012
;
