-- 2021-03-25T19:38:19.312759800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540581,0,114,TO_TIMESTAMP('2021-03-25 21:38:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_User_AD_Org_Mapping_ID','N',TO_TIMESTAMP('2021-03-25 21:38:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:38:19.338662800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540581 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-25T19:38:36.131676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573131,541078,540581,0,TO_TIMESTAMP('2021-03-25 21:38:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-25 21:38:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:38:36.789672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX AD_User_AD_Org_Mapping_ID ON AD_User (AD_Org_Mapping_ID)
;

-- 2021-03-25T19:39:03.369436900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540582,0,291,TO_TIMESTAMP('2021-03-25 21:39:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Bpartner_AD_Org_Mapping_ID','N',TO_TIMESTAMP('2021-03-25 21:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:39:03.372693300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540582 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-25T19:39:11.340872800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573097,541079,540582,0,TO_TIMESTAMP('2021-03-25 21:39:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-25 21:39:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:39:11.661707700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX C_Bpartner_AD_Org_Mapping_ID ON C_BPartner (AD_Org_Mapping_ID)
;

-- 2021-03-25T19:39:38.432840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540583,0,293,TO_TIMESTAMP('2021-03-25 21:39:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner_Location_AD_Org_Mapping_ID','N',TO_TIMESTAMP('2021-03-25 21:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:39:38.434445200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540583 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-25T19:39:46.443451400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573118,541080,540583,0,TO_TIMESTAMP('2021-03-25 21:39:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-25 21:39:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:39:46.770218200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX C_BPartner_Location_AD_Org_Mapping_ID ON C_BPartner_Location (AD_Org_Mapping_ID)
;

-- 2021-03-25T19:40:14.176130600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540584,0,298,TO_TIMESTAMP('2021-03-25 21:40:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BP_BankAccount_AD_Org_Mapping_ID','N',TO_TIMESTAMP('2021-03-25 21:40:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:40:14.180002400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540584 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-03-25T19:40:22.768288600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573227,541081,540584,0,TO_TIMESTAMP('2021-03-25 21:40:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-03-25 21:40:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-03-25T19:40:23.126028800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX C_BP_BankAccount_AD_Org_Mapping_ID ON C_BP_BankAccount (AD_Org_Mapping_ID)
;

