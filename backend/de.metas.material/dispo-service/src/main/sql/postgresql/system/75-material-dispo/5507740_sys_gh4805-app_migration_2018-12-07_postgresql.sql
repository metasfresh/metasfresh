-- 2018-12-07T17:19:15.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2018-12-07 17:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549491
;

-- 2018-12-07T17:19:21.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_receiptschedule','MovementDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2018-12-07T17:20:54.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=615692
;

-- 2018-12-07T17:20:54.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,1001146,625050,552538,0,540196,TO_TIMESTAMP('2018-12-07 17:20:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-07 17:20:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-07T17:21:01.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=269, Description='Zugesagter Termin für diesen Auftrag', Help='Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.', Name='Zugesagter Termin',Updated=TO_TIMESTAMP('2018-12-07 17:21:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552538
;

-- 2018-12-07T17:21:01.643
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=625050
;

-- 2018-12-07T17:21:01.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Link (AD_Client_ID,AD_Element_ID,AD_Element_Link_ID,AD_Field_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,269,625051,552538,0,540196,TO_TIMESTAMP('2018-12-07 17:21:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-12-07 17:21:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-12-07T17:21:01.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269) 
;

-- 2018-12-07T17:26:05.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2018-12-07 17:26:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3669
;

-- 2018-12-07T17:26:07.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_transaction','MovementDate','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2018-12-07T18:52:35.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2018-12-07 18:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558282
;

SELECT db_alter_table('MD_Candidate', 'ALTER TABLE MD_Candidate RENAME COLUMN qtyfullfilled TO qtyfulfilled');-- 2018-12-07T18:53:03.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('md_cockpit','DateGeneral','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2018-12-07T18:54:40.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2018-12-07 18:54:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561297
;

-- 2018-12-07T18:55:00.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2018-12-07 18:55:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=561306
;

