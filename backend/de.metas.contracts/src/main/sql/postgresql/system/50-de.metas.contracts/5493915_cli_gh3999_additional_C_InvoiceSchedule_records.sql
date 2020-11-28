-- 2018-05-18T06:13:40.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_InvoiceSchedule (AD_Client_ID,AD_Org_ID,Amt,C_InvoiceSchedule_ID,Created,CreatedBy,EvenInvoiceWeek,InvoiceDay,InvoiceDayCutoff,InvoiceDistance,InvoiceFrequency,IsActive,IsAmount,IsDefault,Name,Updated,UpdatedBy) VALUES (1000000,0,0,540005,TO_TIMESTAMP('2018-05-18 06:13:40','YYYY-MM-DD HH24:MI:SS'),100,'N',30,1,3,'M','Y','N','N','Einmal pro Quatal',TO_TIMESTAMP('2018-05-18 06:13:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-18T06:14:00.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_InvoiceSchedule SET InvoiceDistance=6, Name='Einmal pro Halbjahr',Updated=TO_TIMESTAMP('2018-05-18 06:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_InvoiceSchedule_ID=540005
;

-- 2018-05-18T06:14:39.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_InvoiceSchedule SET InvoiceDistance=12, Name='Jährlich',Updated=TO_TIMESTAMP('2018-05-18 06:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_InvoiceSchedule_ID=540005
;

-- 2018-05-18T06:15:12.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_InvoiceSchedule (AD_Client_ID,AD_Org_ID,Amt,C_InvoiceSchedule_ID,Created,CreatedBy,EvenInvoiceWeek,InvoiceDay,InvoiceDayCutoff,InvoiceDistance,InvoiceFrequency,IsActive,IsAmount,IsDefault,Name,Updated,UpdatedBy) VALUES (1000000,0,0,540006,TO_TIMESTAMP('2018-05-18 06:15:12','YYYY-MM-DD HH24:MI:SS'),100,'N',30,1,6,'M','Y','N','N','Halbjährlich',TO_TIMESTAMP('2018-05-18 06:15:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-18T06:15:42.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_InvoiceSchedule (AD_Client_ID,AD_Org_ID,Amt,C_InvoiceSchedule_ID,Created,CreatedBy,EvenInvoiceWeek,InvoiceDay,InvoiceDayCutoff,InvoiceDistance,InvoiceFrequency,IsActive,IsAmount,IsDefault,Name,Updated,UpdatedBy) VALUES (1000000,0,0,540007,TO_TIMESTAMP('2018-05-18 06:15:42','YYYY-MM-DD HH24:MI:SS'),100,'N',30,1,3,'M','Y','N','N','Quartalsweise',TO_TIMESTAMP('2018-05-18 06:15:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-18T06:16:09.435
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_InvoiceSchedule SET Name='jährlich',Updated=TO_TIMESTAMP('2018-05-18 06:16:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_InvoiceSchedule_ID=540005
;

-- 2018-05-18T06:16:12.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_InvoiceSchedule SET Name='halbjährlich',Updated=TO_TIMESTAMP('2018-05-18 06:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_InvoiceSchedule_ID=540006
;

-- 2018-05-18T06:16:22.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_InvoiceSchedule SET Name='quartalsweise',Updated=TO_TIMESTAMP('2018-05-18 06:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_InvoiceSchedule_ID=540007
;

-- 2018-05-18T06:16:47.225
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_InvoiceSchedule SET InvoiceDistance=1,Updated=TO_TIMESTAMP('2018-05-18 06:16:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_InvoiceSchedule_ID=2000087
;


