
-- Mar 8, 2016 6:06 PM
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540271,541189,TO_TIMESTAMP('2016-03-08 18:06:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','Liefervereinbarung',TO_TIMESTAMP('2016-03-08 18:06:31','YYYY-MM-DD HH24:MI:SS'),100,'proc','Liefervereinbarung')
;

-- Mar 8, 2016 6:06 PM
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541189 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Mar 8, 2016 6:11 PM
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='Procurement',Updated=TO_TIMESTAMP('2016-03-08 18:11:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541189
;

-- Mar 8, 2016 6:12 PM
-- URL zum Konzept
UPDATE AD_Ref_List SET Value='Procuremnt',Updated=TO_TIMESTAMP('2016-03-08 18:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541189
;
