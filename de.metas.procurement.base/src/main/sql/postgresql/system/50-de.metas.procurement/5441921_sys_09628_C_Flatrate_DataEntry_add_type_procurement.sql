
-- Mar 9, 2016 9:05 AM
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540263,541190,TO_TIMESTAMP('2016-03-09 09:05:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','Liefervertragsdetail',TO_TIMESTAMP('2016-03-09 09:05:07','YYYY-MM-DD HH24:MI:SS'),100,'PC','ProcurementDetail')
;

-- Mar 9, 2016 9:05 AM
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541190 AND NOT EXISTS (SELECT * FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Mar 9, 2016 9:05 AM
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='Procurement_PeriodBased',Updated=TO_TIMESTAMP('2016-03-09 09:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541190
;

-- Mar 9, 2016 9:05 AM
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Lieferungsdetail',Updated=TO_TIMESTAMP('2016-03-09 09:05:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541190
;

-- Mar 9, 2016 9:05 AM
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541190
;
