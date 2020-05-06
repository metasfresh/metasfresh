---- new product salesgroups ------



-- 05.02.2016 12:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Blumenkohl offen',Updated=TO_TIMESTAMP('2016-02-05 12:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541147
;

-- 05.02.2016 12:07
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541147
;

-- 05.02.2016 12:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541169,TO_TIMESTAMP('2016-02-05 12:08:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Blumenkohl verpackt',TO_TIMESTAMP('2016-02-05 12:08:01','YYYY-MM-DD HH24:MI:SS'),100,'1011','Blumenkohl verpackt')
;

-- 05.02.2016 12:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541169 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:08
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET ValueName='Blumenkohl offen',Updated=TO_TIMESTAMP('2016-02-05 12:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541147
;

-- 05.02.2016 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Broccoli offen', ValueName='Broccoli offen',Updated=TO_TIMESTAMP('2016-02-05 12:09:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541148
;

-- 05.02.2016 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541148
;

-- 05.02.2016 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541170,TO_TIMESTAMP('2016-02-05 12:09:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Broccoli verpackt',TO_TIMESTAMP('2016-02-05 12:09:22','YYYY-MM-DD HH24:MI:SS'),100,'0021','Broccoli verpackt')
;

-- 05.02.2016 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541170 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Cherry-Tomaten Rispen verpackt unter 500 g', ValueName='Cherry-Tomaten Rispen verpackt unter 500 g',Updated=TO_TIMESTAMP('2016-02-05 12:10:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541149
;

-- 05.02.2016 12:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541149
;

-- 05.02.2016 12:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541171,TO_TIMESTAMP('2016-02-05 12:10:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Cherry-Tomaten Rispen verpackt ab 500 g',TO_TIMESTAMP('2016-02-05 12:10:38','YYYY-MM-DD HH24:MI:SS'),100,'0031','Cherry-Tomaten Rispen verpackt ab 500 g')
;

-- 05.02.2016 12:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541171 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Chicorée Witloof offen/verpackt', ValueName='Chicorée Witloof offen/verpackt',Updated=TO_TIMESTAMP('2016-02-05 12:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541150
;

-- 05.02.2016 12:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541150
;

-- 05.02.2016 12:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Cicorino rosso offen/verpackt', ValueName='Cicorino rosso offen/verpackt',Updated=TO_TIMESTAMP('2016-02-05 12:11:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541151
;

-- 05.02.2016 12:11
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541151
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Eichenlaubsalat grün und rot offen', ValueName='Eichenlaubsalat grün und rot offen',Updated=TO_TIMESTAMP('2016-02-05 12:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541152
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541152
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541172,TO_TIMESTAMP('2016-02-05 12:12:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Eichenlaubsalat grün und rot verpackt',TO_TIMESTAMP('2016-02-05 12:12:16','YYYY-MM-DD HH24:MI:SS'),100,'0061','Eichenlaubsalat grün und rot verpackt')
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541172 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Eisberg offen', ValueName='Eisberg offen',Updated=TO_TIMESTAMP('2016-02-05 12:12:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541153
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541153
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541173,TO_TIMESTAMP('2016-02-05 12:12:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Eisberg verpackt',TO_TIMESTAMP('2016-02-05 12:12:54','YYYY-MM-DD HH24:MI:SS'),100,'0071','Eisberg verpackt')
;

-- 05.02.2016 12:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541173 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Fenchel offen/verpackt', ValueName='Fenchel offen/verpackt',Updated=TO_TIMESTAMP('2016-02-05 12:13:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541154
;

-- 05.02.2016 12:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541154
;

-- 05.02.2016 12:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Karotten gewaschen offen', ValueName='Karotten gewaschen offen',Updated=TO_TIMESTAMP('2016-02-05 12:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541155
;

-- 05.02.2016 12:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541155
;

-- 05.02.2016 12:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541174,TO_TIMESTAMP('2016-02-05 12:14:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Karotten gewaschen verpackt unter 2.5 Kg',TO_TIMESTAMP('2016-02-05 12:14:18','YYYY-MM-DD HH24:MI:SS'),100,'0091','Karotten gewaschen verpackt unter 2.5 Kg')
;

-- 05.02.2016 12:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541174 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541175,TO_TIMESTAMP('2016-02-05 12:14:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Karotten gewaschen verpackt ab 2.5 Kg',TO_TIMESTAMP('2016-02-05 12:14:43','YYYY-MM-DD HH24:MI:SS'),100,'0092','Karotten gewaschen verpackt ab 2.5 Kg')
;

-- 05.02.2016 12:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541175 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Kohlrabi offen', ValueName='Kohlrabi offen',Updated=TO_TIMESTAMP('2016-02-05 12:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541157
;

-- 05.02.2016 12:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541157
;

-- 05.02.2016 12:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Kopfsalat grün und rot offen', ValueName='Kopfsalat grün und rot offen',Updated=TO_TIMESTAMP('2016-02-05 12:15:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541158
;

-- 05.02.2016 12:15
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541158
;

-- 05.02.2016 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541176,TO_TIMESTAMP('2016-02-05 12:16:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Kopfsalat grün und rot verpackt',TO_TIMESTAMP('2016-02-05 12:16:06','YYYY-MM-DD HH24:MI:SS'),100,'0121','Kopfsalat grün und rot verpackt')
;

-- 05.02.2016 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541176 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Lauchgrün offen', ValueName='Lauchgrün offen',Updated=TO_TIMESTAMP('2016-02-05 12:16:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541159
;

-- 05.02.2016 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541159
;

-- 05.02.2016 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Nüsslisalat verpackt unter 250 g', ValueName='Nüsslisalat verpackt unter 250 g',Updated=TO_TIMESTAMP('2016-02-05 12:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541160
;

-- 05.02.2016 12:16
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541160
;

-- 05.02.2016 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541177,TO_TIMESTAMP('2016-02-05 12:17:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Nüsslisalat verpackt ab 250 g',TO_TIMESTAMP('2016-02-05 12:17:20','YYYY-MM-DD HH24:MI:SS'),100,'0141','Nüsslisalat verpackt ab 250 g')
;

-- 05.02.2016 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541177 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Radieschen offen', ValueName='Radieschen offen',Updated=TO_TIMESTAMP('2016-02-05 12:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541161
;

-- 05.02.2016 12:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541161
;

-- 05.02.2016 12:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541178,TO_TIMESTAMP('2016-02-05 12:18:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Radieschen verpackt',TO_TIMESTAMP('2016-02-05 12:18:33','YYYY-MM-DD HH24:MI:SS'),100,'0151','Radieschen verpackt')
;

-- 05.02.2016 12:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541178 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Randengekocht vepackt', ValueName='Randengekocht vepackt',Updated=TO_TIMESTAMP('2016-02-05 12:18:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541162
;

-- 05.02.2016 12:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541162
;

-- 05.02.2016 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Salatgurken offen/verpackt', ValueName='Salatgurken offen/verpackt',Updated=TO_TIMESTAMP('2016-02-05 12:19:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541163
;

-- 05.02.2016 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541163
;

-- 05.02.2016 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Tomaten Rispen offen', ValueName='Tomaten Rispen offen',Updated=TO_TIMESTAMP('2016-02-05 12:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541164
;

-- 05.02.2016 12:19
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541164
;

-- 05.02.2016 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Tomaten rund offen', ValueName='Tomaten rund offen',Updated=TO_TIMESTAMP('2016-02-05 12:20:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541165
;

-- 05.02.2016 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541165
;

-- 05.02.2016 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Zuchetti grün und gelb offen', ValueName='Zuchetti grün und gelb offen',Updated=TO_TIMESTAMP('2016-02-05 12:20:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541166
;

-- 05.02.2016 12:20
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541166
;

-- 05.02.2016 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541179,TO_TIMESTAMP('2016-02-05 12:21:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Zuchetti grün und gelb verpackt',TO_TIMESTAMP('2016-02-05 12:21:01','YYYY-MM-DD HH24:MI:SS'),100,'0201','Zuchetti grün und gelb verpackt')
;

-- 05.02.2016 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541179 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Zuckerhut offen/verpackt', ValueName='Zuckerhut offen/verpackt',Updated=TO_TIMESTAMP('2016-02-05 12:21:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541167
;

-- 05.02.2016 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541167
;

-- 05.02.2016 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Zwiebeln offen', ValueName='Zwiebeln offen',Updated=TO_TIMESTAMP('2016-02-05 12:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541168
;

-- 05.02.2016 12:21
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541168
;

-- 05.02.2016 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541180,TO_TIMESTAMP('2016-02-05 12:22:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Zwiebeln verpacktunter 2.5kg',TO_TIMESTAMP('2016-02-05 12:22:08','YYYY-MM-DD HH24:MI:SS'),100,'0221','Zwiebeln verpacktunter 2.5kg')
;

-- 05.02.2016 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541180 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 05.02.2016 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540636,541181,TO_TIMESTAMP('2016-02-05 12:22:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Zwiebeln verpacktab 2.5kg',TO_TIMESTAMP('2016-02-05 12:22:45','YYYY-MM-DD HH24:MI:SS'),100,'0222','Zwiebeln verpacktab 2.5kg')
;

-- 05.02.2016 12:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541181 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;



-- 05.02.2016 12:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Randen gekocht vepackt', ValueName='Randen gekocht vepackt',Updated=TO_TIMESTAMP('2016-02-05 12:56:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541162
;

-- 05.02.2016 12:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541162
;

