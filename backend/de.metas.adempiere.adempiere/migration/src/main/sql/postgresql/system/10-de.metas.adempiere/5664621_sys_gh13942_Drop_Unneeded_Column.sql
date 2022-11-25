
-- 2022-11-24T14:27:27.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708204
;

-- 2022-11-24T14:27:27.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708204)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> links.Land
-- Column: M_Delivery_Planning.C_Country_ID
-- 2022-11-24T14:27:39.627Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=613577
;

-- 2022-11-24T14:27:39.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708201
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Land
-- Column: M_Delivery_Planning.C_Country_ID
-- 2022-11-24T14:27:39.633Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708201
;

-- 2022-11-24T14:27:39.638Z
DELETE FROM AD_Field WHERE AD_Field_ID=708201
;






-- Column: M_Delivery_Planning.C_Country_ID
-- 2022-11-24T14:32:09.559Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585137
;

-- 2022-11-24T14:32:09.564Z
DELETE FROM AD_Column WHERE AD_Column_ID=585137
;