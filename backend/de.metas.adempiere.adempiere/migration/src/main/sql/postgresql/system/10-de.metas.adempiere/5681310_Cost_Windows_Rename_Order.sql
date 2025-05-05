-- Field: Order Cost(541676,D) -> Order Cost(546808,D) -> Order
-- Column: C_Order_Cost.C_Order_ID
-- 2023-03-10T08:56:41.078Z
UPDATE AD_Field SET AD_Name_ID=582012, Description=NULL, Help=NULL, Name='Order',Updated=TO_TIMESTAMP('2023-03-10 10:56:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712198
;

-- 2023-03-10T08:56:41.109Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Order' WHERE AD_Field_ID=712198 AND AD_Language='en_US'
;

-- 2023-03-10T08:56:41.169Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582012) 
;

-- 2023-03-10T08:56:41.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712198
;

-- 2023-03-10T08:56:41.265Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712198)
;




-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Order
-- Column: M_InOut_Cost.C_Order_ID
-- 2023-03-10T09:33:00.541Z
UPDATE AD_Field SET AD_Name_ID=582012, Description=NULL, Help=NULL, Name='Order',Updated=TO_TIMESTAMP('2023-03-10 11:33:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712266
;

-- 2023-03-10T09:33:00.572Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Order' WHERE AD_Field_ID=712266 AND AD_Language='en_US'
;

-- 2023-03-10T09:33:00.604Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582012) 
;

-- 2023-03-10T09:33:00.642Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712266
;

-- 2023-03-10T09:33:00.673Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712266)
;

