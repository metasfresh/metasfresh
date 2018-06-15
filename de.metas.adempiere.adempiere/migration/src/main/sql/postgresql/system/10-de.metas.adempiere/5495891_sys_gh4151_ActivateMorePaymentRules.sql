-- 2018-06-15T16:44:59.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=200,Updated=TO_TIMESTAMP('2018-06-15 16:44:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560155
;

-- 2018-06-15T16:45:01.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner','AggregationName','VARCHAR(200)',null,null)
;

-- 2018-06-15T17:29:45.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2018-06-15 17:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=13789
;

-- 2018-06-15T17:29:47.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_job','Name','VARCHAR(100)',null,null)
;

-- 2018-06-15T17:36:31.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2018-06-15 17:36:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557896
;

-- 2018-06-15T17:36:33.308
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_bpartner','InvoiceSchedule','VARCHAR(255)',null,null)
;
-----

-- 2018-06-15T17:38:47.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_InvoiceSchedule (AD_Client_ID,AD_Org_ID,Amt,C_InvoiceSchedule_ID,Created,CreatedBy,EvenInvoiceWeek,InvoiceDay,InvoiceDayCutoff,InvoiceDistance,InvoiceFrequency,IsActive,IsAmount,IsDefault,Name,Updated,UpdatedBy) VALUES (1000000,0,0,540008,TO_TIMESTAMP('2018-06-15 17:38:46','YYYY-MM-DD HH24:MI:SS'),100,'N',30,1,6,'T','Y','N','N','zum 15. und Ultimo (Halbmonatsrechnung)',TO_TIMESTAMP('2018-06-15 17:38:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-15T17:38:59.049
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_InvoiceSchedule (AD_Client_ID,AD_Org_ID,Amt,C_InvoiceSchedule_ID,Created,CreatedBy,EvenInvoiceWeek,InvoiceDay,InvoiceDayCutoff,InvoiceDistance,InvoiceFrequency,IsActive,IsAmount,IsDefault,Name,Updated,UpdatedBy) VALUES (1000000,0,0,540009,TO_TIMESTAMP('2018-06-15 17:38:58','YYYY-MM-DD HH24:MI:SS'),100,'N',30,1,6,'M','Y','N','N','zum Ultimo (Monatsrechnung)',TO_TIMESTAMP('2018-06-15 17:38:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-15T17:49:40.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2018-06-15 17:49:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=652
;

-- 2018-06-15T17:50:04.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='Y',Updated=TO_TIMESTAMP('2018-06-15 17:50:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=334
;

