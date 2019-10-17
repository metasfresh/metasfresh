-- 2019-03-26T13:06:57.995
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540852,541897,TO_TIMESTAMP('2019-03-26 13:06:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Type C: Can Receive Narcotics',TO_TIMESTAMP('2019-03-26 13:06:57','YYYY-MM-DD HH24:MI:SS'),100,'C','Type C: Can Receive Narcotics')
;

-- 2019-03-26T13:06:58.004
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541897 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-03-26T13:18:08.055
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Typ C: Betäubungsmittel Lieferberechtigung',Updated=TO_TIMESTAMP('2019-03-26 13:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=541897
;

