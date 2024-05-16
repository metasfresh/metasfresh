-- 2021-12-14T13:19:02.949Z
-- URL zum Konzept
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540666,541177,540334,TO_TIMESTAMP('2021-12-14 14:19:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order(SO) -> C_Project (Service/ Reparatur Projekt)',TO_TIMESTAMP('2021-12-14 14:19:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-12-14T13:21:33.444Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541530,TO_TIMESTAMP('2021-12-14 14:21:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Project(Service/Reparatur Projekt)_Target_For_C_Order',TO_TIMESTAMP('2021-12-14 14:21:32','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-12-14T13:21:33.514Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541530 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-12-14T13:23:45.772Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1349,0,541530,203,541015,TO_TIMESTAMP('2021-12-14 14:23:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-12-14 14:23:45','YYYY-MM-DD HH24:MI:SS'),100,'exists (  select 1  from C_Project p  join C_OrderLine ol on ol.C_Project_ID = p.C_Project_ID  join C_Order o on ol.C_Order_ID = o.C_Order_ID where  o.C_Order_ID =@C_Order_ID/-1@ and C_Project.C_Project_ID = ol.C_Project_ID )')
;

-- 2021-12-14T13:24:20.136Z
-- URL zum Konzept
UPDATE AD_RelationType SET AD_Reference_Target_ID=541530,Updated=TO_TIMESTAMP('2021-12-14 14:24:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540334
;



-- 2021-12-14T14:01:37.060Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1             from C_Project p             join C_OrderLine ol on ol.C_Project_ID = p.C_Project_ID             join C_Order o on ol.C_Order_ID = o.C_Order_ID            where p.ProjectCategory = ''R''  and  o.C_Order_ID =@C_Order_ID/-1@ and C_Project.C_Project_ID = ol.C_Project_ID         )',Updated=TO_TIMESTAMP('2021-12-14 15:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541530
;

-- 2021-12-14T14:07:58.567Z
-- URL zum Konzept
UPDATE AD_Ref_Table SET WhereClause='exists (  select 1             from C_Project p             join C_OrderLine ol on ol.C_Project_ID = p.C_Project_ID             join C_Order o on ol.C_Order_ID = o.C_Order_ID            where p.ProjectCategory != ''R''  and  o.C_Order_ID =@C_Order_ID/-1@ and C_Project.C_Project_ID = ol.C_Project_ID         )',Updated=TO_TIMESTAMP('2021-12-14 15:07:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541177
;


