-- Name: M_Delivery_Instruction via ReleaseNo
-- 2023-01-17T09:00:14.456Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541706,TO_TIMESTAMP('2023-01-17 11:00:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Delivery_Instruction via ReleaseNo',TO_TIMESTAMP('2023-01-17 11:00:14','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-01-17T09:00:14.459Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541706 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Delivery_Instruction via ReleaseNo
-- Table: M_ShipperTransportation
-- Key: M_ShipperTransportation.DocumentNo
-- 2023-01-17T09:01:13.337Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,540439,540439,0,541706,540030,541657,TO_TIMESTAMP('2023-01-17 11:01:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-01-17 11:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_ShipperTransportation via DocumentNo
-- 2023-01-17T09:01:33.984Z
UPDATE AD_Reference SET Name='M_ShipperTransportation via DocumentNo',Updated=TO_TIMESTAMP('2023-01-17 11:01:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541706
;

-- Field: Lieferplanung(541632,D) -> Lieferplanung(546674,D) -> Ausgabenummer
-- Column: M_Delivery_Planning.ReleaseNo
-- 2023-01-17T09:01:59.214Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=541706,Updated=TO_TIMESTAMP('2023-01-17 11:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708097
;

