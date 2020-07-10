-- 2018-11-27T13:39:25.398
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-11-27 13:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53510
;

-- 2018-11-27T13:39:41.317
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-11-27 13:39:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53512
;

-- 2018-11-27T13:41:06.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53657
;

-- 2018-11-27T13:41:06.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53657
;

-- 2018-11-27T13:41:06.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607868
;

-- 2018-11-27T13:41:09.855
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53656
;

-- 2018-11-27T13:41:09.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53656
;

-- 2018-11-27T13:41:09.879
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607867
;

-- 2018-11-27T13:41:16.791
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53512
;

-- 2018-11-27T13:41:16.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53512
;

-- 2018-11-27T13:41:25.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53510
;

-- 2018-11-27T13:41:25.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53510
;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists XPosition');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists YPosition');

-- 2018-11-27T13:44:00.182
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53673
;

-- 2018-11-27T13:44:00.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53673
;

-- 2018-11-27T13:44:00.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607884
;

-- 2018-11-27T13:44:07.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53478
;

-- 2018-11-27T13:44:07.819
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53478
;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists IsCentrallyMaintained');

-- 2018-11-27T13:44:33.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53674
;

-- 2018-11-27T13:44:33.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53674
;

-- 2018-11-27T13:44:33.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607885
;

-- 2018-11-27T13:44:42.691
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53474
;

-- 2018-11-27T13:44:42.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53474
;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists EntityType');




-- 2018-11-27T13:46:23.327
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53683
;

-- 2018-11-27T13:46:23.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53683
;

-- 2018-11-27T13:46:23.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607894
;

-- 2018-11-27T13:46:29.733
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53457
;

-- 2018-11-27T13:46:29.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53457
;

-- 2018-11-27T13:47:12.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53686
;

-- 2018-11-27T13:47:12.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53686
;

-- 2018-11-27T13:47:12.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607897
;

-- 2018-11-27T13:47:12.368
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53684
;

-- 2018-11-27T13:47:12.371
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53684
;

-- 2018-11-27T13:47:12.378
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607895
;

-- 2018-11-27T13:47:12.443
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53690
;

-- 2018-11-27T13:47:12.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53690
;

-- 2018-11-27T13:47:12.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607901
;

-- 2018-11-27T13:47:12.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53687
;

-- 2018-11-27T13:47:12.512
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53687
;

-- 2018-11-27T13:47:12.516
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607898
;

-- 2018-11-27T13:47:12.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53691
;

-- 2018-11-27T13:47:12.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53691
;

-- 2018-11-27T13:47:12.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607902
;

-- 2018-11-27T13:47:25.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53447
;

-- 2018-11-27T13:47:25.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53447
;

-- 2018-11-27T13:47:25.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53448
;

-- 2018-11-27T13:47:25.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53448
;

-- 2018-11-27T13:47:25.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53450
;

-- 2018-11-27T13:47:25.546
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53450
;

-- 2018-11-27T13:47:25.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53451
;

-- 2018-11-27T13:47:25.633
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53451
;

-- 2018-11-27T13:47:25.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53452
;

-- 2018-11-27T13:47:25.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53452
;

-- 2018-11-27T13:47:30.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53692
;

-- 2018-11-27T13:47:30.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53692
;

-- 2018-11-27T13:47:30.168
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607903
;

-- 2018-11-27T13:47:40.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53446
;

-- 2018-11-27T13:47:40.522
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53446
;

-- 2018-11-27T13:48:17.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53685
;

-- 2018-11-27T13:48:17.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53685
;

-- 2018-11-27T13:48:17.324
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607896
;

-- 2018-11-27T13:48:26.538
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53455
;

-- 2018-11-27T13:48:26.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53455
;

-- 2018-11-27T13:48:33.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53693
;

-- 2018-11-27T13:48:33.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53693
;

-- 2018-11-27T13:48:33.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607904
;

-- 2018-11-27T13:48:37.807
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53694
;

-- 2018-11-27T13:48:37.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53694
;

-- 2018-11-27T13:48:37.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607905
;

-- 2018-11-27T13:48:45.778
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53458
;

-- 2018-11-27T13:48:45.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53458
;

-- 2018-11-27T13:48:45.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53459
;

-- 2018-11-27T13:48:45.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53459
;

-- 2018-11-27T13:51:45.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53695
;

-- 2018-11-27T13:51:45.871
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53695
;

-- 2018-11-27T13:51:45.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607906
;

-- 2018-11-27T13:51:52.243
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53469
;

-- 2018-11-27T13:51:52.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53469
;

-- 2018-11-27T13:54:07.586
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53703
;

-- 2018-11-27T13:54:07.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53703
;

-- 2018-11-27T13:54:07.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607914
;

-- 2018-11-27T13:54:13.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53482
;

-- 2018-11-27T13:54:13.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53482
;

-- 2018-11-27T13:54:49.498
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53681
;

-- 2018-11-27T13:54:49.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53681
;

-- 2018-11-27T13:54:49.505
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607892
;

-- 2018-11-27T13:54:58.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53682
;

-- 2018-11-27T13:54:58.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53682
;

-- 2018-11-27T13:54:58.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607893
;

-- 2018-11-27T13:55:04.829
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53481
;

-- 2018-11-27T13:55:04.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53481
;

-- 2018-11-27T13:55:09.363
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53498
;

-- 2018-11-27T13:55:09.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53498
;

-- 2018-11-27T13:55:39.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53689
;

-- 2018-11-27T13:55:39.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53689
;

-- 2018-11-27T13:55:39.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607900
;

-- 2018-11-27T13:55:53.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53688
;

-- 2018-11-27T13:55:53.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53688
;

-- 2018-11-27T13:55:53.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607899
;

-- 2018-11-27T13:55:58.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53500
;

-- 2018-11-27T13:55:58.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53500
;

-- 2018-11-27T13:56:07.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53508
;

-- 2018-11-27T13:56:07.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53508
;

-- 2018-11-27T13:57:11.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53680
;

-- 2018-11-27T13:57:11.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53680
;

-- 2018-11-27T13:57:11.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607891
;

-- 2018-11-27T13:57:17.201
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53679
;

-- 2018-11-27T13:57:17.202
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53679
;

-- 2018-11-27T13:57:17.205
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607890
;

-- 2018-11-27T13:57:26.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53475
;

-- 2018-11-27T13:57:26.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53475
;

-- 2018-11-27T13:57:33.787
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53499
;

-- 2018-11-27T13:57:33.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53499
;

-- 2018-11-27T13:58:19.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53678
;

-- 2018-11-27T13:58:19.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53678
;

-- 2018-11-27T13:58:19.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607889
;

-- 2018-11-27T13:58:26.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53488
;

-- 2018-11-27T13:58:26.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53488
;

-- 2018-11-27T13:59:58.491
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53698
;

-- 2018-11-27T13:59:58.494
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53698
;

-- 2018-11-27T13:59:58.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607909
;

-- 2018-11-27T14:00:05.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53501
;

-- 2018-11-27T14:00:05.457
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53501
;

-- 2018-11-27T14:00:27.647
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53675
;

-- 2018-11-27T14:00:27.656
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53675
;

-- 2018-11-27T14:00:27.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607886
;

-- 2018-11-27T14:00:31.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53676
;

-- 2018-11-27T14:00:31.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53676
;

-- 2018-11-27T14:00:31.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607887
;

-- 2018-11-27T14:00:37.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53504
;

-- 2018-11-27T14:00:37.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53504
;

-- 2018-11-27T14:00:40.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53505
;

-- 2018-11-27T14:00:40.471
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53505
;

drop view if exists PP_Order_Node_v;
drop view if exists PP_Order_Node_vt;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists Action');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AD_Form_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AD_Task_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AD_WF_Block_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AD_Process_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AD_Column_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AD_Window_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AttributeName');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AttributeValue');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists AD_Image_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists DocAction');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists DurationLimit');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists JoinElement');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists SplitElement');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists SubflowExecution');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists Workflow_ID');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists Priority');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists StartMode');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists FinishMode');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists UnitsCycles');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists ValidFrom');
SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists ValidTo');

































-- 2018-11-27T14:02:54.292
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2018-11-27 14:02:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53461
;

-- 2018-11-27T14:02:58.160
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE PP_Order_Node SET Cost=0 WHERE Cost IS NULL
;

-- 2018-11-27T14:03:47.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53697
;

-- 2018-11-27T14:03:47.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53697
;

-- 2018-11-27T14:03:47.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607908
;

-- 2018-11-27T14:03:58.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53509
;

-- 2018-11-27T14:03:58.031
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53509
;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists WorkingTime');










-- 2018-11-27T14:06:52.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53696
;

-- 2018-11-27T14:06:52.447
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53696
;

-- 2018-11-27T14:06:52.453
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607907
;

-- 2018-11-27T14:06:58.938
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53461
;

-- 2018-11-27T14:06:58.940
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53461
;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists Cost');

-- 2018-11-27T14:09:04.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53665
;

-- 2018-11-27T14:09:04.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53665
;

-- 2018-11-27T14:09:04.761
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607876
;

-- 2018-11-27T14:09:15.504
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=53667
;

-- 2018-11-27T14:09:15.513
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=53667
;

-- 2018-11-27T14:09:15.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=607878
;

-- 2018-11-27T14:09:21.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53476
;

-- 2018-11-27T14:09:21.909
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53476
;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists Help');
alter table PP_Order_Node_Trl drop column if exists Help;

-- 2018-11-27T14:12:09.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2018-11-27 14:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=53232
;

-- 2018-11-27T14:12:16.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=53445
;

-- 2018-11-27T14:12:16.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=53445
;

drop view if exists rv_pp_order_workflow;

SELECT db_alter_table('PP_Order_Node', 'alter table PP_Order_Node drop column if exists Name');
alter table PP_Order_Node_Trl drop column if exists Name;

-- 2018-11-27T14:14:55.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=56965
;

-- 2018-11-27T14:14:55.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=56965
;

-- 2018-11-27T14:14:55.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=610601
;

-- 2018-11-27T14:14:58.757
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=56968
;

-- 2018-11-27T14:14:58.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=56968
;

-- 2018-11-27T14:14:58.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Element_Link_ID=610604
;

-- 2018-11-27T14:15:03.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=57208
;

-- 2018-11-27T14:15:03.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=57208
;

-- 2018-11-27T14:15:07.115
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=57211
;

-- 2018-11-27T14:15:07.119
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=57211
;

