-- 2018-06-18T09:07:23.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCodeType,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540442,0,540649,'',TO_TIMESTAMP('2018-06-18 09:07:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y','Y','C_Aggregation_UC_Default','N',TO_TIMESTAMP('2018-06-18 09:07:23','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2018-06-18T09:07:23.509
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540442 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-06-18T09:07:50.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,552544,540867,540442,0,TO_TIMESTAMP('2018-06-18 09:07:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',10,TO_TIMESTAMP('2018-06-18 09:07:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T09:08:12.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551847,540868,540442,0,TO_TIMESTAMP('2018-06-18 09:08:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',20,TO_TIMESTAMP('2018-06-18 09:08:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T09:09:55.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551873,540869,540442,0,'(IsDefaultPO=''Y'' OR IsDefault=''Y'')',TO_TIMESTAMP('2018-06-18 09:09:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',30,TO_TIMESTAMP('2018-06-18 09:09:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T09:10:20.827
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551872,540870,540442,0,'(IsDefaultSO=''Y'' OR IsDefault=''Y'')',TO_TIMESTAMP('2018-06-18 09:10:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',40,TO_TIMESTAMP('2018-06-18 09:10:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T09:11:12.022
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Es darf pro Tabelle und Level nur einen Standard-Eintrag geben',Updated=TO_TIMESTAMP('2018-06-18 09:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540442
;

-- 2018-06-18T09:11:18.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 09:11:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',ErrorMsg='Es darf pro Tabelle und Level nur einen Standard-Eintrag geben' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540442
;

-- 2018-06-18T09:11:37.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 09:11:37','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',ErrorMsg='There may be just one default recrod per table and level' WHERE AD_Language='en_US' AND AD_Index_Table_ID=540442
;

-- 2018-06-18T09:12:08.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 09:12:08','YYYY-MM-DD HH24:MI:SS'),ErrorMsg='There may be just one default recrod per table and level for sales and purchase' WHERE AD_Language='en_US' AND AD_Index_Table_ID=540442
;

-- 2018-06-18T09:12:37.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 09:12:37','YYYY-MM-DD HH24:MI:SS'),ErrorMsg='Es darf je für Verkauf und Einkauf pro Tabelle und Level nur einen Standard-Eintrag geben' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540442
;

-- 2018-06-18T09:12:44.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Es darf je für Verkauf und Einkauf pro Tabelle und Level nur einen Standard-Eintrag geben',Updated=TO_TIMESTAMP('2018-06-18 09:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540442
;

-- 2018-06-18T09:13:06.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeCodeType='SQLS',Updated=TO_TIMESTAMP('2018-06-18 09:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540442
;

-- 2018-06-18T09:15:12.615
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND (IsDefaultPO=''Y'' OR IsDefaultSO=''Y'' OR IsDefault=''Y'')',Updated=TO_TIMESTAMP('2018-06-18 09:15:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540442
;


-- 2018-06-18T10:02:55.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540443,0,540649,'SQLS',TO_TIMESTAMP('2018-06-18 10:02:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Es darf pro Tabelle und Level nur einen Standard-Einkaufs-Eintrag geben','Y','Y','C_Aggregation_UC_PO_Default','N',TO_TIMESTAMP('2018-06-18 10:02:54','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y'' AND COALESCE(IsDefaultPO, IsDefault)=''Y''')
;

-- 2018-06-18T10:02:55.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540443 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-06-18T10:03:24.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551847,540871,540443,0,TO_TIMESTAMP('2018-06-18 10:03:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',10,TO_TIMESTAMP('2018-06-18 10:03:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T10:03:34.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,552544,540872,540443,0,TO_TIMESTAMP('2018-06-18 10:03:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',20,TO_TIMESTAMP('2018-06-18 10:03:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T10:04:17.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551873,540873,540443,0,'IsDefaultPO=''Y'' OR IsDefault=''Y''',TO_TIMESTAMP('2018-06-18 10:04:17','YYYY-MM-DD HH24:MI:SS'),100,'U','Y',30,TO_TIMESTAMP('2018-06-18 10:04:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T10:04:28.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 10:04:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540443
;

-- 2018-06-18T10:04:56.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 10:04:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',ErrorMsg='There may be just one purchase record per level and table' WHERE AD_Language='en_US' AND AD_Index_Table_ID=540443
;

-- 2018-06-18T10:05:41.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND (IsDefaultPO=''Y'' OR IsDefault=''Y'')',Updated=TO_TIMESTAMP('2018-06-18 10:05:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540443
;

-- 2018-06-18T10:05:49.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET EntityType='de.metas.aggregation',Updated=TO_TIMESTAMP('2018-06-18 10:05:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540873
;

-- 2018-06-18T10:06:12.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET ColumnSQL='(IsDefaultPO=''Y'' OR IsDefault=''Y'')',Updated=TO_TIMESTAMP('2018-06-18 10:06:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540873
;

-- 2018-06-18T10:06:15.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Aggregation_UC_PO_Default ON C_Aggregation (AD_Table_ID,AggregationUsageLevel,(IsDefaultPO='Y' OR IsDefault='Y')) WHERE IsActive='Y' AND (IsDefaultPO='Y' OR IsDefault='Y')
;

-- 2018-06-18T10:06:52.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCodeType,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540444,0,540649,'SQLS',TO_TIMESTAMP('2018-06-18 10:06:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Es darf pro Tabelle und Level nur einen Standard-Verkaufs-Eintrag geben','Y','Y','C_Aggregation_UC_SO_Default','N',TO_TIMESTAMP('2018-06-18 10:06:51','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y'' AND (IsDefaultSO=''Y'' OR IsDefault=''Y'')')
;

-- 2018-06-18T10:06:52.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540444 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2018-06-18T10:07:01.284
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 10:07:01','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540444
;

-- 2018-06-18T10:07:19.723
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-18 10:07:19','YYYY-MM-DD HH24:MI:SS'),ErrorMsg='There may be just one sales record per level and table' WHERE AD_Language='en_US' AND AD_Index_Table_ID=540444
;

-- 2018-06-18T10:07:37.431
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551847,540874,540444,0,TO_TIMESTAMP('2018-06-18 10:07:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',10,TO_TIMESTAMP('2018-06-18 10:07:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T10:07:55.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,552544,540875,540444,0,TO_TIMESTAMP('2018-06-18 10:07:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',20,TO_TIMESTAMP('2018-06-18 10:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T10:08:29.544
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551872,540876,540444,0,'(IsDefaultSO=''Y'' OR IsDefault=''Y'')',TO_TIMESTAMP('2018-06-18 10:08:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.aggregation','Y',30,TO_TIMESTAMP('2018-06-18 10:08:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-18T10:08:41.625
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_Aggregation_UC_SO_Default ON C_Aggregation (AD_Table_ID,AggregationUsageLevel,(IsDefaultSO='Y' OR IsDefault='Y')) WHERE IsActive='Y' AND (IsDefaultSO='Y' OR IsDefault='Y')
;

-- 2018-06-18T10:08:51.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540442
;

-- 2018-06-18T10:08:51.458
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540442
;

