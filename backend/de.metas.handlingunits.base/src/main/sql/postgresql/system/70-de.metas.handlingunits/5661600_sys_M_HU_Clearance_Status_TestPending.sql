-- 2022-10-24T09:39:20.726Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541540,543314,TO_TIMESTAMP('2022-10-24 12:39:20','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Zur Prüfung',TO_TIMESTAMP('2022-10-24 12:39:20','YYYY-MM-DD HH24:MI:SS'),100,'P','Test Pending')
;

-- 2022-10-24T09:39:20.771Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543314 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-10-24T09:39:31.994Z
-- URL zum Konzept
UPDATE AD_Ref_List SET EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-10-24 12:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543314
;

-- 2022-10-24T10:11:30.797Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='Test Pending',Updated=TO_TIMESTAMP('2022-10-24 13:11:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543314
;

