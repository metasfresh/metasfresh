-- 2021-04-06T08:56:25.009Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541292,TO_TIMESTAMP('2021-04-06 10:56:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Discount breaks',TO_TIMESTAMP('2021-04-06 10:56:19','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-04-06T08:56:25.013Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541292 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-04-06T08:58:25.945Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,6598,0,541292,476,540612,TO_TIMESTAMP('2021-04-06 10:58:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-04-06 10:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-06T08:59:52.190Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541292,540284,TO_TIMESTAMP('2021-04-06 10:59:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','N','M_DiscountSchemaBreak_V -> Discount breaks',TO_TIMESTAMP('2021-04-06 10:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-06T09:01:46.270Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Window_ID=233,Updated=TO_TIMESTAMP('2021-04-06 11:01:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541292
;

-- 2021-04-06T09:02:13.467Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET AD_Key=573021, AD_Table_ID=541586,Updated=TO_TIMESTAMP('2021-04-06 11:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541292
;

-- 2021-04-06T09:02:26.317Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='Discount Schema Break View',Updated=TO_TIMESTAMP('2021-04-06 11:02:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541292
;

-- 2021-04-06T09:02:59.343Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541293,TO_TIMESTAMP('2021-04-06 11:02:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Discount Schema Breaks',TO_TIMESTAMP('2021-04-06 11:02:54','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-04-06T09:02:59.345Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541293 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-04-06T09:03:15.019Z
-- URL zum Konzept
UPDATE AD_Reference SET Name='Discount Schema Breaks target for Discount Schema Break View',Updated=TO_TIMESTAMP('2021-04-06 11:03:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541293
;

-- 2021-04-06T09:03:58.993Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,6598,0,541293,476,540612,TO_TIMESTAMP('2021-04-06 11:03:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-04-06 11:03:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-06T09:11:12.036Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='(EXISTS(SELECT 1               from M_DiscountSchemaBreak db                        JOIN M_DiscountSchemaBreak_V dbv on dvb.M_product_ID = db.M_product_ID and dbv.m_discountschema_id=db.m_discountschema_id               where db.M_DiscountSchemaBreak_ID = M_DiscountSchemaBreak.M_DiscountSchemaBreak_ID                 AND dbv.M_product_ID = @M_Product_ID / -1@  AND dbv.m_discountschema_id = @M_DiscountSchema_ID / -1@ ))  ',Updated=TO_TIMESTAMP('2021-04-06 11:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541293
;

-- 2021-04-06T09:11:53.170Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541293, EntityType='D'  WHERE AD_RelationType_ID=540284
;





-- 2021-04-06T09:52:24.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET IsDirected='Y',Updated=TO_TIMESTAMP('2021-04-06 12:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540284
;

-- 2021-04-06T10:03:33.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-04-06 13:03:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573021
;

-- 2021-04-06T10:09:06.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='(EXISTS(SELECT 1               from M_DiscountSchemaBreak db                        JOIN M_DiscountSchemaBreak_V dbv on dbv.M_product_ID = db.M_product_ID and dbv.m_discountschema_id=db.m_discountschema_id               where db.M_DiscountSchemaBreak_ID = M_DiscountSchemaBreak.M_DiscountSchemaBreak_ID                 AND dbv.M_product_ID = @M_Product_ID / -1@  AND dbv.m_discountschema_id = @M_DiscountSchema_ID / -1@ ))  ',Updated=TO_TIMESTAMP('2021-04-06 13:09:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541293
;





------


