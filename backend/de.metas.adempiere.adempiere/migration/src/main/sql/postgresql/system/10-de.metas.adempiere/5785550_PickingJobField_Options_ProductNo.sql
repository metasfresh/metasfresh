-- Reference: PickingJobField_Options
-- Value: Product
-- ValueName: Product
-- 2026-01-26T19:50:11.031Z
UPDATE AD_Ref_List SET Name='Produktname',Updated=TO_TIMESTAMP('2026-01-26 19:50:11.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543862
;

-- 2026-01-26T19:50:11.036Z
UPDATE AD_Ref_List_Trl trl SET Name='Produktname' WHERE AD_Ref_List_ID=543862 AND AD_Language='de_DE'
;

-- Reference Item: PickingJobField_Options -> Product_Product
-- 2026-01-26T19:50:15.542Z
UPDATE AD_Ref_List_Trl SET Name='Produktname',Updated=TO_TIMESTAMP('2026-01-26 19:50:15.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543862
;

-- 2026-01-26T19:50:15.543Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: PickingJobField_Options -> Product_Product
-- 2026-01-26T19:50:21.337Z
UPDATE AD_Ref_List_Trl SET Name='Product Name',Updated=TO_TIMESTAMP('2026-01-26 19:50:21.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543862
;

-- 2026-01-26T19:50:21.339Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: PickingJobField_Options
-- Value: ProductNo
-- ValueName: ProductNo
-- 2026-01-26T19:51:01.603Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541850,544109,TO_TIMESTAMP('2026-01-26 19:51:01.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking','Y','Produktnummer',TO_TIMESTAMP('2026-01-26 19:51:01.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ProductNo','ProductNo')
;

-- 2026-01-26T19:51:01.605Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544109 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: PickingJobField_Options -> ProductNo_ProductNo
-- 2026-01-26T19:51:05.650Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-26 19:51:05.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544109
;

-- Reference Item: PickingJobField_Options -> ProductNo_ProductNo
-- 2026-01-26T19:51:15.481Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-26 19:51:15.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544109
;

-- Reference Item: PickingJobField_Options -> ProductNo_ProductNo
-- 2026-01-26T19:51:30.671Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Product No',Updated=TO_TIMESTAMP('2026-01-26 19:51:30.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544109
;

-- 2026-01-26T19:51:30.672Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
















update AD_Ref_List set ValueName='ProductName' where AD_Ref_List_ID=543862;



















