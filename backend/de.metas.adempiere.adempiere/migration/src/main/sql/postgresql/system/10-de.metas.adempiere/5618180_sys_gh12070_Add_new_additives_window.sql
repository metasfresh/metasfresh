-- 2021-12-09T16:37:27.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:37:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',540017,'mit Farbstoff',TO_TIMESTAMP('2021-12-09 17:37:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:37:27.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540017 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:38:29.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='with colour',Updated=TO_TIMESTAMP('2021-12-09 17:38:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540017
;

-- 2021-12-09T16:39:38.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',540018,'mit Konservierungsstoff',TO_TIMESTAMP('2021-12-09 17:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:39:38.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540018 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:39:55.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:39:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',540019,'mit Antioxidationsmittel',TO_TIMESTAMP('2021-12-09 17:39:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:39:55.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540019 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:40:11.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:40:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',540020,'mit Geschmacksverstärker',TO_TIMESTAMP('2021-12-09 17:40:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:40:11.253Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540020 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:40:21.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:40:21','YYYY-MM-DD HH24:MI:SS'),100,'Y',540021,'geschwärzt',TO_TIMESTAMP('2021-12-09 17:40:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:40:21.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540021 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:40:31.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:40:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',540022,'mit Phosphat',TO_TIMESTAMP('2021-12-09 17:40:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:40:31.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540022 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:40:41.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:40:41','YYYY-MM-DD HH24:MI:SS'),100,'Y',540023,'mit Süßungsmittel',TO_TIMESTAMP('2021-12-09 17:40:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:40:41.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540023 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:40:55.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:40:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',540024,'enthält eine Phenylalaninquelle',TO_TIMESTAMP('2021-12-09 17:40:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:40:55.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540024 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:41:04.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Additive_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-12-09 17:41:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',540025,'gewachst',TO_TIMESTAMP('2021-12-09 17:41:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-09T16:41:04.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Additive_Trl (AD_Language,M_Additive_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.M_Additive_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, M_Additive t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.M_Additive_ID=540025 AND NOT EXISTS (SELECT 1 FROM M_Additive_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.M_Additive_ID=t.M_Additive_ID)
;

-- 2021-12-09T16:41:38.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='with preservative',Updated=TO_TIMESTAMP('2021-12-09 17:41:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540018
;

-- 2021-12-09T16:42:40.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='with antioxidant',Updated=TO_TIMESTAMP('2021-12-09 17:42:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540019
;

-- 2021-12-09T16:43:10.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='with flavour enhancer',Updated=TO_TIMESTAMP('2021-12-09 17:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540020
;

-- 2021-12-09T16:43:56.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='blackened',Updated=TO_TIMESTAMP('2021-12-09 17:43:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540021
;

-- 2021-12-09T16:44:28.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='with phosphate',Updated=TO_TIMESTAMP('2021-12-09 17:44:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540022
;

-- 2021-12-09T16:45:01.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='with sweetener',Updated=TO_TIMESTAMP('2021-12-09 17:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540023
;

-- 2021-12-09T16:45:35.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='contains a source of phenylalanine',Updated=TO_TIMESTAMP('2021-12-09 17:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540024
;

-- 2021-12-09T16:46:13.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Additive_Trl SET Name='waxed',Updated=TO_TIMESTAMP('2021-12-09 17:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND M_Additive_ID=540025
;