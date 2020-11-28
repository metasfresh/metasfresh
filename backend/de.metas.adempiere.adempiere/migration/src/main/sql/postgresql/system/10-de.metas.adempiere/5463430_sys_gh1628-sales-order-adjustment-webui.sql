-- 2017-05-24T22:46:27.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,186,540216,TO_TIMESTAMP('2017-05-24 22:46:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',20,TO_TIMESTAMP('2017-05-24 22:46:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T22:46:33.521
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540216,TO_TIMESTAMP('2017-05-24 22:46:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-24 22:46:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T22:46:46.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540498,TO_TIMESTAMP('2017-05-24 22:46:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','advanced edit',10,TO_TIMESTAMP('2017-05-24 22:46:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T22:52:20.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1079,0,186,1000009,544766,TO_TIMESTAMP('2017-05-24 22:52:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',30,0,0,TO_TIMESTAMP('2017-05-24 22:52:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T22:52:22.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=540694
;

-- 2017-05-24T22:52:26.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2017-05-24 22:52:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000070
;

-- 2017-05-24T22:52:30.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-05-24 22:52:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544766
;

-- 2017-05-24T22:53:06
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1081,0,186,1000001,544767,TO_TIMESTAMP('2017-05-24 22:53:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Beleg Nr.',12,0,0,TO_TIMESTAMP('2017-05-24 22:53:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T22:53:26.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2111,0,186,1000001,544768,TO_TIMESTAMP('2017-05-24 22:53:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Referenz',920,0,0,TO_TIMESTAMP('2017-05-24 22:53:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T22:53:31.704
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2017-05-24 22:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544768
;

-- 2017-05-24T22:55:01.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000062
;

-- 2017-05-24T22:57:30.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544767
;

-- 2017-05-24T22:57:30.971
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000008
;

-- 2017-05-24T22:57:30.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000024
;

-- 2017-05-24T22:57:30.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000000
;

-- 2017-05-24T22:57:30.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541299
;

-- 2017-05-24T22:57:30.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000009
;

-- 2017-05-24T22:57:30.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000010
;

-- 2017-05-24T22:57:30.980
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-24 22:57:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 2017-05-24T22:58:24.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000023
;

-- 2017-05-24T22:58:35.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000071
;

-- 2017-05-24T22:59:16.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543170
;

-- 2017-05-24T23:01:32.843
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=NULL, IsAdvancedField='N', IsDisplayed='N',Updated=TO_TIMESTAMP('2017-05-24 23:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000021
;

-- 2017-05-24T23:02:00.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000021
;

-- 2017-05-24T23:03:44.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000010
;

-- 2017-05-24T23:03:44.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544767
;

-- 2017-05-24T23:03:44.607
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000000
;

-- 2017-05-24T23:03:44.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000009
;

-- 2017-05-24T23:03:44.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543210
;

-- 2017-05-24T23:03:44.612
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000024
;

-- 2017-05-24T23:03:44.613
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 2017-05-24T23:03:44.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-24 23:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544766
;

-- 2017-05-24T23:04:34.805
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-24 23:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543181
;

-- 2017-05-24T23:04:34.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-24 23:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543180
;

-- 2017-05-24T23:04:34.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-24 23:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541299
;

-- 2017-05-24T23:04:34.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-24 23:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000024
;

-- 2017-05-24T23:04:34.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-05-24 23:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 2017-05-24T23:04:34.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2017-05-24 23:04:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544766
;

-- 2017-05-24T23:06:05.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-24 23:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543210
;

-- 2017-05-24T23:06:05.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-05-24 23:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543181
;

-- 2017-05-24T23:06:05.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-05-24 23:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543180
;

-- 2017-05-24T23:06:05.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-05-24 23:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541299
;

-- 2017-05-24T23:06:05.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-05-24 23:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000024
;

-- 2017-05-24T23:06:05.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-05-24 23:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 2017-05-24T23:06:05.966
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-05-24 23:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544766
;

-- 2017-05-24T23:06:31.535
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543180
;

-- 2017-05-24T23:14:54.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='freight',Updated=TO_TIMESTAMP('2017-05-24 23:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540498
;

-- 2017-05-24T23:15:17.260
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56446,0,186,540498,544769,TO_TIMESTAMP('2017-05-24 23:15:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Fracht Kategorie',10,0,0,TO_TIMESTAMP('2017-05-24 23:15:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:15:30.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501620,0,186,540498,544770,TO_TIMESTAMP('2017-05-24 23:15:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Incoterm',20,0,0,TO_TIMESTAMP('2017-05-24 23:15:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:15:51.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501621,0,186,540498,544771,TO_TIMESTAMP('2017-05-24 23:15:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Incoterm Standort',30,0,0,TO_TIMESTAMP('2017-05-24 23:15:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:16:05.706
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,502201,0,186,540498,544772,TO_TIMESTAMP('2017-05-24 23:16:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Volumen',40,0,0,TO_TIMESTAMP('2017-05-24 23:16:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:16:22.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,502202,0,186,540498,544773,TO_TIMESTAMP('2017-05-24 23:16:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gewicht',50,0,0,TO_TIMESTAMP('2017-05-24 23:16:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:16:24.850
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544770
;

-- 2017-05-24T23:16:25.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544771
;

-- 2017-05-24T23:16:25.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544772
;

-- 2017-05-24T23:16:27.145
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:16:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544773
;

-- 2017-05-24T23:17:45.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2878,0,186,540498,544774,TO_TIMESTAMP('2017-05-24 23:17:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Versandkostenberechnung',60,0,0,TO_TIMESTAMP('2017-05-24 23:17:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:17:57.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1107,0,186,540498,544775,TO_TIMESTAMP('2017-05-24 23:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Versandkosten',70,0,0,TO_TIMESTAMP('2017-05-24 23:17:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:17:57.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544774
;

-- 2017-05-24T23:17:59.231
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544775
;

-- 2017-05-24T23:18:26.792
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543198
;

-- 2017-05-24T23:18:30.881
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543199
;

-- 2017-05-24T23:18:34.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543202
;

-- 2017-05-24T23:18:37.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543203
;

-- 2017-05-24T23:18:46.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543196
;

-- 2017-05-24T23:19:03.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543216
;

-- 2017-05-24T23:19:31.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543217
;

-- 2017-05-24T23:22:26.692
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540499,TO_TIMESTAMP('2017-05-24 23:22:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','sum',20,TO_TIMESTAMP('2017-05-24 23:22:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:22:39.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545267,0,186,540499,544776,TO_TIMESTAMP('2017-05-24 23:22:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anzahl Zeilen',10,0,0,TO_TIMESTAMP('2017-05-24 23:22:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:22:52.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,546256,0,186,540499,544777,TO_TIMESTAMP('2017-05-24 23:22:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Netto Summe',20,0,0,TO_TIMESTAMP('2017-05-24 23:22:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:23:06.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545269,0,186,540499,544778,TO_TIMESTAMP('2017-05-24 23:23:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Steuer Summe',30,0,0,TO_TIMESTAMP('2017-05-24 23:23:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:23:33.680
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1113,0,186,540499,544779,TO_TIMESTAMP('2017-05-24 23:23:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Gesamt Summe',40,0,0,TO_TIMESTAMP('2017-05-24 23:23:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:23:47.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1099,0,186,540499,544780,TO_TIMESTAMP('2017-05-24 23:23:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsbedingung',50,0,0,TO_TIMESTAMP('2017-05-24 23:23:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:24:06.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556289,0,186,540499,544781,TO_TIMESTAMP('2017-05-24 23:24:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Status Lieferung',60,0,0,TO_TIMESTAMP('2017-05-24 23:24:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:24:18.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,556278,0,186,540499,544782,TO_TIMESTAMP('2017-05-24 23:24:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Status Abrechnung',70,0,0,TO_TIMESTAMP('2017-05-24 23:24:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:24:40.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543206
;

-- 2017-05-24T23:24:49.053
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543210
;

-- 2017-05-24T23:24:55.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543181
;

-- 2017-05-24T23:24:59.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543208
;

-- 2017-05-24T23:25:06.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543212
;

-- 2017-05-24T23:25:09.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543211
;

-- 2017-05-24T23:25:17.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544776
;

-- 2017-05-24T23:25:18.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544777
;

-- 2017-05-24T23:25:18.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544778
;

-- 2017-05-24T23:25:19.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544779
;

-- 2017-05-24T23:25:19.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544780
;

-- 2017-05-24T23:25:19.859
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544781
;

-- 2017-05-24T23:25:21.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:25:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544782
;

-- 2017-05-24T23:31:52.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543179
;

-- 2017-05-24T23:36:31.508
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543204
;

-- 2017-05-24T23:36:35.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543205
;

-- 2017-05-24T23:39:02.479
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540500,TO_TIMESTAMP('2017-05-24 23:39:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','payment',30,TO_TIMESTAMP('2017-05-24 23:39:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:39:29.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4234,0,186,540500,544783,TO_TIMESTAMP('2017-05-24 23:39:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlung',10,0,0,TO_TIMESTAMP('2017-05-24 23:39:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:39:48.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9428,0,186,540500,544784,TO_TIMESTAMP('2017-05-24 23:39:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlung Partner',20,0,0,TO_TIMESTAMP('2017-05-24 23:39:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:40:01.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,9427,0,540127,544784,TO_TIMESTAMP('2017-05-24 23:40:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-24 23:40:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:40:04.891
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544784
;

-- 2017-05-24T23:40:08.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:40:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544783
;

-- 2017-05-24T23:40:26.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3113,0,186,540500,544785,TO_TIMESTAMP('2017-05-24 23:40:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zahlungsweise',30,0,0,TO_TIMESTAMP('2017-05-24 23:40:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:40:29.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-24 23:40:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544785
;

-- 2017-05-24T23:41:02.112
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543189
;

-- 2017-05-24T23:41:06.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543193
;

-- 2017-05-24T23:41:09.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543194
;

-- 2017-05-24T23:42:27.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9427,0,186,540500,544786,TO_TIMESTAMP('2017-05-24 23:42:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','Y','N','N','Zahlung Adresse',40,0,0,TO_TIMESTAMP('2017-05-24 23:42:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-24T23:42:33.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540127
;

-- 2017-05-24T23:43:18.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2017-05-24 23:43:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544786
;

-- 2017-05-25T07:48:08.785
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540501,TO_TIMESTAMP('2017-05-25 07:48:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','acct',40,TO_TIMESTAMP('2017-05-25 07:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:48:22.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1095,0,186,540501,544787,TO_TIMESTAMP('2017-05-25 07:48:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Buchungsdatum',10,0,0,TO_TIMESTAMP('2017-05-25 07:48:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:48:33.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3660,0,186,540501,544788,TO_TIMESTAMP('2017-05-25 07:48:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verbucht',20,0,0,TO_TIMESTAMP('2017-05-25 07:48:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:48:54.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540502,TO_TIMESTAMP('2017-05-25 07:48:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',50,TO_TIMESTAMP('2017-05-25 07:48:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:49:08.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1080,0,186,540502,544789,TO_TIMESTAMP('2017-05-25 07:49:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-05-25 07:49:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:49:25.148
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1087,0,186,540502,544790,TO_TIMESTAMP('2017-05-25 07:49:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Freigegeben',20,0,0,TO_TIMESTAMP('2017-05-25 07:49:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:49:38.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1088,0,186,540502,544791,TO_TIMESTAMP('2017-05-25 07:49:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kredit gebilligt',30,0,0,TO_TIMESTAMP('2017-05-25 07:49:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:49:48.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1089,0,186,540502,544792,TO_TIMESTAMP('2017-05-25 07:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zugestellt',40,0,0,TO_TIMESTAMP('2017-05-25 07:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:50:19.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1090,0,186,540502,544793,TO_TIMESTAMP('2017-05-25 07:50:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bereits abgerechnet',50,0,0,TO_TIMESTAMP('2017-05-25 07:50:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:50:38.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1091,0,186,540502,544794,TO_TIMESTAMP('2017-05-25 07:50:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bereits gedruckt',60,0,0,TO_TIMESTAMP('2017-05-25 07:50:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:51:00.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1092,0,186,540502,544795,TO_TIMESTAMP('2017-05-25 07:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','An Buchhaltung übergeben',70,0,0,TO_TIMESTAMP('2017-05-25 07:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:51:14.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2594,0,186,540502,544796,TO_TIMESTAMP('2017-05-25 07:51:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',80,0,0,TO_TIMESTAMP('2017-05-25 07:51:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:51:20.406
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Abgerechnet',Updated=TO_TIMESTAMP('2017-05-25 07:51:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544793
;

-- 2017-05-25T07:51:31.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Gedruckt',Updated=TO_TIMESTAMP('2017-05-25 07:51:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544794
;

-- 2017-05-25T07:51:34.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2017-05-25 07:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544796
;

-- 2017-05-25T07:51:44.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Buchhaltung',Updated=TO_TIMESTAMP('2017-05-25 07:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544795
;

-- 2017-05-25T07:52:01.695
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2879,0,186,540502,544797,TO_TIMESTAMP('2017-05-25 07:52:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verkaufs Transaktion',80,0,0,TO_TIMESTAMP('2017-05-25 07:52:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:52:27.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3661,0,186,540502,544798,TO_TIMESTAMP('2017-05-25 07:52:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preise inkl. Steuern',90,0,0,TO_TIMESTAMP('2017-05-25 07:52:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:52:47.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3704,0,186,540502,544799,TO_TIMESTAMP('2017-05-25 07:52:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Selektiert',100,0,0,TO_TIMESTAMP('2017-05-25 07:52:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:52:54.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544789
;

-- 2017-05-25T07:52:55.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544796
;

-- 2017-05-25T07:52:55.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544790
;

-- 2017-05-25T07:52:56.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544791
;

-- 2017-05-25T07:52:56.688
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544792
;

-- 2017-05-25T07:52:57.287
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544793
;

-- 2017-05-25T07:52:57.789
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544794
;

-- 2017-05-25T07:52:58.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544795
;

-- 2017-05-25T07:52:58.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544797
;

-- 2017-05-25T07:52:59.360
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544798
;

-- 2017-05-25T07:53:00.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:53:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544799
;

-- 2017-05-25T07:53:44.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=5,Updated=TO_TIMESTAMP('2017-05-25 07:53:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540501
;

-- 2017-05-25T07:55:40.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543171
;

-- 2017-05-25T07:55:40.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543172
;

-- 2017-05-25T07:55:40.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543173
;

-- 2017-05-25T07:55:40.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543174
;

-- 2017-05-25T07:55:40.981
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543175
;

-- 2017-05-25T07:55:40.987
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543176
;

-- 2017-05-25T07:55:40.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543177
;

-- 2017-05-25T07:55:40.996
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543178
;

-- 2017-05-25T07:55:41.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543182
;

-- 2017-05-25T07:55:41.008
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543184
;

-- 2017-05-25T07:55:41.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543185
;

-- 2017-05-25T07:55:41.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543186
;

-- 2017-05-25T07:55:41.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543187
;

-- 2017-05-25T07:55:41.051
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543192
;

-- 2017-05-25T07:57:46.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,502200,0,186,540500,544800,TO_TIMESTAMP('2017-05-25 07:57:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Bankverbindung',40,0,0,TO_TIMESTAMP('2017-05-25 07:57:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:58:15.601
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,4233,0,186,540500,544801,TO_TIMESTAMP('2017-05-25 07:58:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kassenbuch Zeile',50,0,0,TO_TIMESTAMP('2017-05-25 07:58:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T07:58:18.558
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:58:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544800
;

-- 2017-05-25T07:58:20.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:58:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544801
;

-- 2017-05-25T07:58:45.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543188
;

-- 2017-05-25T07:58:53.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543201
;

-- 2017-05-25T07:59:36.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544787
;

-- 2017-05-25T07:59:37.828
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 07:59:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544788
;

-- 2017-05-25T08:13:56.531
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2589,0,186,540501,544802,TO_TIMESTAMP('2017-05-25 08:13:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kostenstelle',30,0,0,TO_TIMESTAMP('2017-05-25 08:13:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:14:11.456
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2593,0,186,540501,544803,TO_TIMESTAMP('2017-05-25 08:14:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Projekt',40,0,0,TO_TIMESTAMP('2017-05-25 08:14:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:16:19.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6227,0,186,540502,544804,TO_TIMESTAMP('2017-05-25 08:16:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','eMail senden',110,0,0,TO_TIMESTAMP('2017-05-25 08:16:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:16:23.381
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 08:16:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544804
;

-- 2017-05-25T08:18:23.931
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543168
;

-- 2017-05-25T08:18:23.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543169
;

-- 2017-05-25T08:18:23.948
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543183
;

-- 2017-05-25T08:18:23.955
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543190
;

-- 2017-05-25T08:18:23.960
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543191
;

-- 2017-05-25T08:18:23.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543195
;

-- 2017-05-25T08:18:23.979
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543207
;

-- 2017-05-25T08:18:23.991
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543209
;

-- 2017-05-25T08:18:37.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543218
;

-- 2017-05-25T08:18:37.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543219
;

-- 2017-05-25T08:18:45.929
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543200
;

-- 2017-05-25T08:19:06.017
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540301,540503,TO_TIMESTAMP('2017-05-25 08:19:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','promotion',60,TO_TIMESTAMP('2017-05-25 08:19:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:19:25.574
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,543186,0,186,540503,544805,TO_TIMESTAMP('2017-05-25 08:19:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','EIngegangen via',10,0,0,TO_TIMESTAMP('2017-05-25 08:19:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:19:47.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56906,0,186,540503,544806,TO_TIMESTAMP('2017-05-25 08:19:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Promotioncode',20,0,0,TO_TIMESTAMP('2017-05-25 08:19:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:20:11.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547121,0,186,540503,544807,TO_TIMESTAMP('2017-05-25 08:20:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Angebot gültig bis',30,0,0,TO_TIMESTAMP('2017-05-25 08:20:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:20:30.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547120,0,186,540503,544808,TO_TIMESTAMP('2017-05-25 08:20:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Angebot gültig Tage',40,0,0,TO_TIMESTAMP('2017-05-25 08:20:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T08:20:52.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543197
;

-- 2017-05-25T08:20:52.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543213
;

-- 2017-05-25T08:20:52.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543214
;

-- 2017-05-25T08:20:52.537
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=543215
;

-- 2017-05-25T08:20:56.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540286
;

-- 2017-05-25T08:21:18.428
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 08:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544802
;

-- 2017-05-25T08:21:24.715
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 08:21:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544803
;

-- 2017-05-25T08:21:38.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 08:21:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544805
;

-- 2017-05-25T08:21:40.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 08:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544806
;

-- 2017-05-25T08:21:41.524
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 08:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544807
;

-- 2017-05-25T08:21:43.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 08:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544808
;

-- 2017-05-25T09:30:25.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10124,0,186,540005,544809,TO_TIMESTAMP('2017-05-25 09:30:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Abw. Lieferanschrift',30,0,0,TO_TIMESTAMP('2017-05-25 09:30:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T09:31:06.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,55410,0,186,540005,544810,TO_TIMESTAMP('2017-05-25 09:31:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Abw. Kunde',40,0,0,TO_TIMESTAMP('2017-05-25 09:31:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T09:31:22.149
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553694,0,186,540005,544811,TO_TIMESTAMP('2017-05-25 09:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Abw. Abladeort',50,0,0,TO_TIMESTAMP('2017-05-25 09:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T09:31:41.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,553693,0,186,540005,544812,TO_TIMESTAMP('2017-05-25 09:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Abladeort Partner',60,0,0,TO_TIMESTAMP('2017-05-25 09:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T09:32:49.764
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,55411,0,540128,544810,TO_TIMESTAMP('2017-05-25 09:32:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-25 09:32:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T09:33:02.773
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,55412,0,540129,544810,TO_TIMESTAMP('2017-05-25 09:33:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-25 09:33:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T09:33:42.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,553692,0,540130,544812,TO_TIMESTAMP('2017-05-25 09:33:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-25 09:33:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T09:33:58.714
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=1000012
;

-- 2017-05-25T09:34:02.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000214
;

-- 2017-05-25T09:34:12.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000213
;

-- 2017-05-25T09:34:31.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=1000013
;

-- 2017-05-25T09:34:31.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=1000014
;

-- 2017-05-25T09:34:35.338
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000212
;

-- 2017-05-25T09:34:39.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=1000211
;

-- 2017-05-25T09:41:30.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2017-05-25 09:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000026
;

-- 2017-05-25T10:22:35.683
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,186,540217,TO_TIMESTAMP('2017-05-25 10:22:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','adv edit: flags',30,TO_TIMESTAMP('2017-05-25 10:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:22:53.417
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='adv. edit: default',Updated=TO_TIMESTAMP('2017-05-25 10:22:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540216
;

-- 2017-05-25T10:23:03.559
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='main view',Updated=TO_TIMESTAMP('2017-05-25 10:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540008
;

-- 2017-05-25T10:23:08.951
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='adv. edit default',Updated=TO_TIMESTAMP('2017-05-25 10:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540216
;

-- 2017-05-25T10:23:15.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='adv edit flags',Updated=TO_TIMESTAMP('2017-05-25 10:23:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540217
;

-- 2017-05-25T10:23:44.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540302,540217,TO_TIMESTAMP('2017-05-25 10:23:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-05-25 10:23:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:23:51.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540302,540504,TO_TIMESTAMP('2017-05-25 10:23:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-05-25 10:23:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:23:56.781
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540303,540217,TO_TIMESTAMP('2017-05-25 10:23:56','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-05-25 10:23:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:24:03.869
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540303,540505,TO_TIMESTAMP('2017-05-25 10:24:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-05-25 10:24:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:26:51.702
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2594,0,186,540504,544813,TO_TIMESTAMP('2017-05-25 10:26:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2017-05-25 10:26:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:27:03.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2879,0,186,540504,544814,TO_TIMESTAMP('2017-05-25 10:27:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Verkaufs Transaktion',20,0,0,TO_TIMESTAMP('2017-05-25 10:27:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:27:23.972
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3661,0,186,540504,544815,TO_TIMESTAMP('2017-05-25 10:27:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Preis inkl. Steuern',30,0,0,TO_TIMESTAMP('2017-05-25 10:27:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:27:50.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3704,0,186,540504,544816,TO_TIMESTAMP('2017-05-25 10:27:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Selektiert',40,0,0,TO_TIMESTAMP('2017-05-25 10:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:28:17.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6227,0,186,540504,544817,TO_TIMESTAMP('2017-05-25 10:28:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','eMail senden',50,0,0,TO_TIMESTAMP('2017-05-25 10:28:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:28:41.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1087,0,186,540504,544818,TO_TIMESTAMP('2017-05-25 10:28:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Freigegeben',5,0,0,TO_TIMESTAMP('2017-05-25 10:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:30:13.415
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1088,0,186,540505,544819,TO_TIMESTAMP('2017-05-25 10:30:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kredit gebilligt',10,0,0,TO_TIMESTAMP('2017-05-25 10:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:30:24.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1089,0,186,540505,544820,TO_TIMESTAMP('2017-05-25 10:30:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zugestellt',20,0,0,TO_TIMESTAMP('2017-05-25 10:30:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:30:39.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1090,0,186,540505,544821,TO_TIMESTAMP('2017-05-25 10:30:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Berechnete Menge',30,0,0,TO_TIMESTAMP('2017-05-25 10:30:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:30:50.868
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1091,0,186,540505,544822,TO_TIMESTAMP('2017-05-25 10:30:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Andrucken',40,0,0,TO_TIMESTAMP('2017-05-25 10:30:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:31:11.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1092,0,186,540505,544823,TO_TIMESTAMP('2017-05-25 10:31:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Buchhaltung',50,0,0,TO_TIMESTAMP('2017-05-25 10:31:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:31:34.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544789
;

-- 2017-05-25T10:31:34.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544790
;

-- 2017-05-25T10:31:34.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544791
;

-- 2017-05-25T10:31:34.242
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544792
;

-- 2017-05-25T10:31:34.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544793
;

-- 2017-05-25T10:31:34.258
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544794
;

-- 2017-05-25T10:31:34.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544795
;

-- 2017-05-25T10:31:34.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544796
;

-- 2017-05-25T10:31:34.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544797
;

-- 2017-05-25T10:31:34.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544798
;

-- 2017-05-25T10:31:34.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544799
;

-- 2017-05-25T10:31:34.304
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544804
;

-- 2017-05-25T10:31:38.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540502
;

-- 2017-05-25T10:31:47.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544813
;

-- 2017-05-25T10:31:47.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544814
;

-- 2017-05-25T10:31:48.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544815
;

-- 2017-05-25T10:31:48.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544816
;

-- 2017-05-25T10:31:49.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544817
;

-- 2017-05-25T10:31:50.344
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544818
;

-- 2017-05-25T10:31:58.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544819
;

-- 2017-05-25T10:31:58.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544820
;

-- 2017-05-25T10:31:58.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544821
;

-- 2017-05-25T10:31:59.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544822
;

-- 2017-05-25T10:32:00.672
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:32:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544823
;

-- 2017-05-25T10:35:57.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544819
;

-- 2017-05-25T10:35:57.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544820
;

-- 2017-05-25T10:35:57.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544821
;

-- 2017-05-25T10:35:57.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544822
;

-- 2017-05-25T10:35:57.970
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544823
;

-- 2017-05-25T10:36:02.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540505
;

-- 2017-05-25T10:36:06.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540303
;

-- 2017-05-25T10:36:30.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1088,0,186,540504,544824,TO_TIMESTAMP('2017-05-25 10:36:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kredit gebilligt',60,0,0,TO_TIMESTAMP('2017-05-25 10:36:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:36:46.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1089,0,186,540504,544825,TO_TIMESTAMP('2017-05-25 10:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zugestellt',70,0,0,TO_TIMESTAMP('2017-05-25 10:36:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:36:59.865
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1090,0,186,540504,544826,TO_TIMESTAMP('2017-05-25 10:36:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Berechnete Menge',80,0,0,TO_TIMESTAMP('2017-05-25 10:36:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:37:17.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1091,0,186,540504,544827,TO_TIMESTAMP('2017-05-25 10:37:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Andrucken',90,0,0,TO_TIMESTAMP('2017-05-25 10:37:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:37:35.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1092,0,186,540504,544828,TO_TIMESTAMP('2017-05-25 10:37:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Buchhaltung',100,0,0,TO_TIMESTAMP('2017-05-25 10:37:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-25T10:37:55.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544824
;

-- 2017-05-25T10:37:55.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544825
;

-- 2017-05-25T10:37:56.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544826
;

-- 2017-05-25T10:37:56.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544827
;

-- 2017-05-25T10:37:57.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-05-25 10:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544828
;

