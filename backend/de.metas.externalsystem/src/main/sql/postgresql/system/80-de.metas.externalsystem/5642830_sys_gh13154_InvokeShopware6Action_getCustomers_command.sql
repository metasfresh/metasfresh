-- 2022-06-09T14:25:55.776Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543211,541262,TO_TIMESTAMP('2022-06-09 17:25:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kunden abrufen',TO_TIMESTAMP('2022-06-09 17:25:55','YYYY-MM-DD HH24:MI:SS'),100,'getCustomers','Kunden abrufen')
;

-- 2022-06-09T14:25:55.776Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543211 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-06-09T14:26:05.211Z
UPDATE AD_Ref_List SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2022-06-09 17:26:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543211
;

-- 2022-06-09T14:26:17.324Z
UPDATE AD_Ref_List_Trl SET Name='Retrieve customers',Updated=TO_TIMESTAMP('2022-06-09 17:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543211
;

