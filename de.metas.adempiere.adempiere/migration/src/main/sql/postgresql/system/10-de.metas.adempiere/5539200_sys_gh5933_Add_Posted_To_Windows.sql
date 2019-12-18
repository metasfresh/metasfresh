-- 2019-12-16T12:02:32.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540563,543259,TO_TIMESTAMP('2019-12-16 14:02:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',30,TO_TIMESTAMP('2019-12-16 14:02:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-16T12:02:35.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-16 14:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541000
;

-- 2019-12-16T12:03:34.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543259, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-16 14:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547485
;

-- 2019-12-16T12:07:21.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed@=Y ',Updated=TO_TIMESTAMP('2019-12-16 14:07:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559523
;

-- 2019-12-16T12:08:12.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-12-16 14:08:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547485
;

-- 2019-12-16T12:08:41.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed@=Y',Updated=TO_TIMESTAMP('2019-12-16 14:08:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559523
;

-- 2019-12-16T12:08:48.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed@=''Y''',Updated=TO_TIMESTAMP('2019-12-16 14:08:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559523
;

-- 2019-12-16T12:11:00.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540528,Updated=TO_TIMESTAMP('2019-12-16 14:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1629
;

-- 2019-12-16T12:14:19.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('gl_journal','Posted','CHAR(1)',null,'N')
;

-- 2019-12-16T12:14:20.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE GL_Journal SET Posted='N' WHERE Posted IS NULL
;

-- 2019-12-16T12:18:17.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=234,Updated=TO_TIMESTAMP('2019-12-16 14:18:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=1629
;

-- 2019-12-16T12:18:18.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('gl_journal','Posted','CHAR(1)',null,'N')
;

-- 2019-12-16T12:18:18.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE GL_Journal SET Posted='N' WHERE Posted IS NULL
;

-- 2019-12-16T12:41:18.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540087,543260,TO_TIMESTAMP('2019-12-16 14:41:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-16 14:41:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-16T12:41:22.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-16 14:41:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540121
;

-- 2019-12-16T12:48:31.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-12-16 14:48:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541704
;

-- 2019-12-16T12:48:42.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543260, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-16 14:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541704
;

-- 2019-12-16T12:49:05.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed@=''Y''',Updated=TO_TIMESTAMP('2019-12-16 14:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557731
;

-- 2019-12-16T12:49:18.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17,Updated=TO_TIMESTAMP('2019-12-16 14:49:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6534
;

-- 2019-12-16T12:49:23.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_inout','Posted','CHAR(1)',null,'N')
;

-- 2019-12-16T12:49:23.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_InOut SET Posted='N' WHERE Posted IS NULL
;

-- 2019-12-16T15:46:02.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540084,543261,TO_TIMESTAMP('2019-12-16 17:46:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-16 17:46:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-16T15:46:04.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-16 17:46:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540115
;

-- 2019-12-16T15:46:07.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-16 17:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540116
;

-- 2019-12-16T15:46:40.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543261, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-16 17:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541650
;

-- 2019-12-16T15:46:56.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed@=''Y''',Updated=TO_TIMESTAMP('2019-12-16 17:46:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557617
;

-- 2019-12-16T15:48:03.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2019-12-16 17:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541650
;






-- 2019-12-16T12:49:05.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Processed@=''Y''',Updated=TO_TIMESTAMP('2019-12-16 14:49:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID in (3999,
3671,
3670,
3660,
57752,
4044,
57891,
5144,
5142,
56370,
5359,
589287,
559523,
557731,
557617)

;



-- 2019-12-16T12:49:18.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=234, Updated=TO_TIMESTAMP('2019-12-16 14:49:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE columnname = 'Posted';
;






INSERT INTO t_alter_column values('C_BankStatement','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('C_Order','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('C_Invoice','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('C_Order','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('M_InOut','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('C_Payment','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('M_InOut','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('M_Movement','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('M_Inventory','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('RV_UnPosted','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('M_MatchInv','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('C_BankStatement','Posted','CHAR(1)',null,'N');
INSERT INTO t_alter_column values('GL_Journal','Posted','CHAR(1)',null,'N');


-- 2019-12-16T12:49:23.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_InOut SET Posted='N' WHERE Posted IS NULL
;



UPDATE  C_BankStatement SET Posted='N' WHERE Posted IS NULL
;
UPDATE  C_Order SET Posted='N' WHERE Posted IS NULL
;
UPDATE  C_Invoice SET Posted='N' WHERE Posted IS NULL
;
UPDATE  C_Order SET Posted='N' WHERE Posted IS NULL
;

UPDATE  C_Payment SET Posted='N' WHERE Posted IS NULL
;

UPDATE  M_Movement SET Posted='N' WHERE Posted IS NULL
;
UPDATE  M_Inventory SET Posted='N' WHERE Posted IS NULL
;

UPDATE  M_MatchInv SET Posted='N' WHERE Posted IS NULL
;
UPDATE  C_BankStatement SET Posted='N' WHERE Posted IS NULL
;
UPDATE  GL_Journal SET Posted='N' WHERE Posted IS NULL
;






update ad_ui_element
set isDisplayed = 'Y' where ad_ui_element_ID in (

543088,
541259,
542660,
544788,
544852,
541152,
541526,
542589,
551244,
561826,
561872,
563150,
547485,
541704,
541650);






update ad_ui_element
set isAdvancedField = 'N' where ad_ui_element_ID in (

543088,
541259,
542660,
544788,
544852,
541152,
541526,
542589,
551244,
561826,
561872,
563150,
547485,
541704,
541650);





-- 2019-12-17T08:01:38.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540184,543262,TO_TIMESTAMP('2019-12-17 10:01:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 10:01:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:01:41.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-17 10:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540281
;

-- 2019-12-17T08:01:44.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-17 10:01:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540285
;

-- 2019-12-17T08:07:08.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543262, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:07:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543088
;

-- 2019-12-17T08:07:19.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-17 10:07:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543088
;

-- 2019-12-17T08:07:19.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-17 10:07:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=543157
;

-- 2019-12-17T08:08:43.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540050,543263,TO_TIMESTAMP('2019-12-17 10:08:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',30,TO_TIMESTAMP('2019-12-17 10:08:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:08:46.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-17 10:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540957
;

-- 2019-12-17T08:09:26.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543263, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:09:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541152
;

-- 2019-12-17T08:09:42.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-12-17 10:09:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541152
;

-- 2019-12-17T08:09:42.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-12-17 10:09:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547168
;




-- 2019-12-17T08:10:38.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540910,543264,TO_TIMESTAMP('2019-12-17 10:10:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 10:10:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:10:42.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-17 10:10:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=541515
;

-- 2019-12-17T08:13:15.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543264, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551244
;

-- 2019-12-17T08:13:34.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-17 10:13:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551244
;

-- 2019-12-17T08:13:34.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-12-17 10:13:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551225
;

-- 2019-12-17T08:15:10.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=234,Updated=TO_TIMESTAMP('2019-12-17 10:15:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=56347
;

-- 2019-12-17T08:15:36.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541912,543265,TO_TIMESTAMP('2019-12-17 10:15:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 10:15:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:19:24.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543265, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561826
;

-- 2019-12-17T08:25:42.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=234,Updated=TO_TIMESTAMP('2019-12-17 10:25:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6512
;




-- 2019-12-17T08:28:22.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541920,543266,TO_TIMESTAMP('2019-12-17 10:28:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',30,TO_TIMESTAMP('2019-12-17 10:28:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:28:25.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-17 10:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542905
;

-- 2019-12-17T08:28:52.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543266, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561872
;

-- 2019-12-17T08:33:48.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-12-17 10:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561864
;

-- 2019-12-17T08:33:48.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-12-17 10:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561865
;

-- 2019-12-17T08:33:48.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-12-17 10:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561869
;

-- 2019-12-17T08:33:48.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-12-17 10:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561876
;

-- 2019-12-17T08:33:49.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-12-17 10:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561866
;

-- 2019-12-17T08:33:49.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-12-17 10:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561877
;

-- 2019-12-17T08:33:49.008Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-12-17 10:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561867
;

-- 2019-12-17T08:33:49.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-12-17 10:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561870
;

-- 2019-12-17T08:33:49.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-17 10:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561872
;

-- 2019-12-17T08:33:49.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-17 10:33:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561874
;

-- 2019-12-17T08:34:03.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561878
;

-- 2019-12-17T08:34:03.865Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561865
;

-- 2019-12-17T08:34:03.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561869
;

-- 2019-12-17T08:34:03.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561876
;

-- 2019-12-17T08:34:03.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561866
;

-- 2019-12-17T08:34:03.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561877
;

-- 2019-12-17T08:34:03.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561867
;

-- 2019-12-17T08:34:03.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561870
;

-- 2019-12-17T08:34:03.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561872
;

-- 2019-12-17T08:34:03.891Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-12-17 10:34:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561874
;










-- 2019-12-17T08:52:38.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542018,543267,TO_TIMESTAMP('2019-12-17 10:52:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 10:52:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:52:41.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-17 10:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543057
;

-- 2019-12-17T08:52:43.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-17 10:52:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543058
;

-- 2019-12-17T08:53:06.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543267, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:53:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563150
;

-- 2019-12-17T08:53:13.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-17 10:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563150
;

-- 2019-12-17T08:53:13.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-17 10:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563144
;

-- 2019-12-17T08:56:04.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540057,543268,TO_TIMESTAMP('2019-12-17 10:56:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',30,TO_TIMESTAMP('2019-12-17 10:56:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:56:08.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-17 10:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540074
;

-- 2019-12-17T08:57:18.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543268, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:57:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541259
;

-- 2019-12-17T08:57:24.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-12-17 10:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541259
;

-- 2019-12-17T08:57:24.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-12-17 10:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541286
;






-- 2019-12-17T08:58:48.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540078,543269,TO_TIMESTAMP('2019-12-17 10:58:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 10:58:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T08:59:18.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543269, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 10:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541526
;

-- 2019-12-17T08:59:24.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-17 10:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541526
;

-- 2019-12-17T08:59:24.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-12-17 10:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546033
;

-- 2019-12-17T09:00:12.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-17 11:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541650
;

-- 2019-12-17T09:00:12.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-12-17 11:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541746
;

-- 2019-12-17T09:01:22.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-12-17 11:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541704
;

-- 2019-12-17T09:01:22.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-17 11:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546040
;

-- 2019-12-17T09:02:14.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540146,543270,TO_TIMESTAMP('2019-12-17 11:02:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 11:02:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T09:02:17.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-17 11:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540217
;

-- 2019-12-17T09:02:46.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543270, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 11:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542589
;

-- 2019-12-17T09:02:52.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-12-17 11:02:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542589
;

-- 2019-12-17T09:02:52.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-12-17 11:02:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542625
;

-- 2019-12-17T09:05:14.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540149,543271,TO_TIMESTAMP('2019-12-17 11:05:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 11:05:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T09:05:17.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2019-12-17 11:05:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540227
;

-- 2019-12-17T09:05:20.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2019-12-17 11:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540228
;

-- 2019-12-17T09:05:46.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543271, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 11:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542660
;

-- 2019-12-17T09:05:55.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-12-17 11:05:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542660
;

-- 2019-12-17T09:05:55.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-12-17 11:05:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542725
;

-- 2019-12-17T09:06:50.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540011,543272,TO_TIMESTAMP('2019-12-17 11:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',50,TO_TIMESTAMP('2019-12-17 11:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T09:06:53.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=60,Updated=TO_TIMESTAMP('2019-12-17 11:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000009
;

-- 2019-12-17T09:07:23.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543272, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 11:07:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544788
;

-- 2019-12-17T09:07:33.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-12-17 11:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544788
;

-- 2019-12-17T09:07:33.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-12-17 11:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000069
;

-- 2019-12-17T09:11:03.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540081,543273,TO_TIMESTAMP('2019-12-17 11:11:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','posted',20,TO_TIMESTAMP('2019-12-17 11:11:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-12-17T09:11:28.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543273, SeqNo=10,Updated=TO_TIMESTAMP('2019-12-17 11:11:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544852
;

-- 2019-12-17T09:11:38.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-12-17 11:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544846
;

-- 2019-12-17T09:11:38.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-12-17 11:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544838
;

-- 2019-12-17T09:11:38.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-12-17 11:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547184
;

-- 2019-12-17T09:11:38.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-12-17 11:11:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544852
;

-- 2019-12-17T09:12:29.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-12-17 11:12:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547485
;

-- 2019-12-17T09:12:29.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-12-17 11:12:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547528
;

