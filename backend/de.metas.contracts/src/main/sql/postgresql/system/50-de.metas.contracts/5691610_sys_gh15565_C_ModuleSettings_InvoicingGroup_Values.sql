-- Reference: InvoicingGroup
-- Value: Other
-- ValueName: Other
-- 2023-06-14T06:39:07.105578900Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543484
;

-- 2023-06-14T06:39:07.126271400Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543484
;

-- Reference: InvoicingGroup
-- Value: Leistung
-- ValueName: Leistung
-- 2023-06-14T06:39:27.013376900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543498,541742,TO_TIMESTAMP('2023-06-14 09:39:26.813','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Leistung',TO_TIMESTAMP('2023-06-14 09:39:26.813','YYYY-MM-DD HH24:MI:SS.US'),100,'Leistung','Leistung')
;

-- 2023-06-14T06:39:27.019459800Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543498 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: InvoicingGroup -> Leistung_Leistung
-- 2023-06-14T06:39:55.947236Z
UPDATE AD_Ref_List_Trl SET Name='Performance',Updated=TO_TIMESTAMP('2023-06-14 09:39:55.947','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543498
;

-- Reference: InvoicingGroup
-- Value: Kosten
-- ValueName: Kosten
-- 2023-06-14T06:40:25.071358100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543499,541742,TO_TIMESTAMP('2023-06-14 09:40:24.859','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Kosten',TO_TIMESTAMP('2023-06-14 09:40:24.859','YYYY-MM-DD HH24:MI:SS.US'),100,'Kosten','Kosten')
;

-- 2023-06-14T06:40:25.072976500Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543499 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: InvoicingGroup -> Kosten_Kosten
-- 2023-06-14T06:40:37.466335100Z
UPDATE AD_Ref_List_Trl SET Name='Costs',Updated=TO_TIMESTAMP('2023-06-14 09:40:37.466','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543499
;

