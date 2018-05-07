-- 2018-05-04T16:12:37.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('DerKurier_Shipper_Config','ALTER TABLE public.DerKurier_Shipper_Config ADD COLUMN AD_Sequence_ID NUMERIC(10) NOT NULL')
;

-- 2018-05-04T16:12:37.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE DerKurier_Shipper_Config ADD CONSTRAINT ADSequence_DerKurierShipperConfig FOREIGN KEY (AD_Sequence_ID) REFERENCES public.AD_Sequence DEFERRABLE INITIALLY DEFERRED
;

-- 2018-05-04T16:38:25.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@ShipperGateway@=go',Updated=TO_TIMESTAMP('2018-05-04 16:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540982
;

-- 2018-05-04T16:38:45.083
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@ShipperGateway@=''derKurier''',Updated=TO_TIMESTAMP('2018-05-04 16:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541084
;

-- 2018-05-04T16:38:50.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DisplayLogic='@ShipperGateway@=''go''',Updated=TO_TIMESTAMP('2018-05-04 16:38:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540982
;

-- 2018-05-04T16:39:31.341
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563873
;

-- 2018-05-04T16:39:31.345
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563872
;

-- 2018-05-04T16:39:31.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563869
;

-- 2018-05-04T16:39:31.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2018-05-04 16:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563870
;

-- 2018-05-04T16:39:42.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-05-04 16:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563873
;

-- 2018-05-04T16:39:44.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-05-04 16:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563872
;

-- 2018-05-04T16:39:45.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-05-04 16:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563869
;

-- 2018-05-04T16:39:49.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-05-04 16:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563870
;

-- 2018-05-04T16:39:54.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-05-04 16:39:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563872
;

-- 2018-05-04T16:40:13.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2018-05-04 16:40:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563870
;

