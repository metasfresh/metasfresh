
alter table GL_Journal DROP COLUMN M_Product_ID;

alter table GL_Journal DROP COLUMN C_Order_ID;


-- UI Element: Hauptbuch Journal(540356,D) -> Journal(540854,D) -> advanced edit -> 10 -> advanced edit.Sales order
-- Column: GL_Journal.C_Order_ID
-- 2022-12-19T13:30:54.879Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614543
;

-- 2022-12-19T13:30:54.892Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710002
;

-- Field: Hauptbuch Journal(540356,D) -> Journal(540854,D) -> Auftrag
-- Column: GL_Journal.C_Order_ID
-- 2022-12-19T13:30:54.897Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710002
;

-- 2022-12-19T13:30:54.902Z
DELETE FROM AD_Field WHERE AD_Field_ID=710002
;

-- UI Element: Hauptbuch Journal(540356,D) -> Journal(540854,D) -> advanced edit -> 10 -> advanced edit.Product
-- Column: GL_Journal.M_Product_ID
-- 2022-12-19T13:31:00.244Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614542
;

-- 2022-12-19T13:31:00.246Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710003
;

-- Field: Hauptbuch Journal(540356,D) -> Journal(540854,D) -> Produkt
-- Column: GL_Journal.M_Product_ID
-- 2022-12-19T13:31:00.250Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=710003
;

-- 2022-12-19T13:31:00.255Z
DELETE FROM AD_Field WHERE AD_Field_ID=710003
;

-- UI Element: Hauptbuch Journal(540356,D) -> Journal(540854,D) -> advanced edit -> 10 -> advanced edit.Section Code
-- Column: GL_Journal.M_SectionCode_ID
-- 2022-12-19T13:31:57.120Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614541
;

-- Column: GL_Journal.M_Product_ID
-- 2022-12-19T13:32:20.113Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585329
;

-- 2022-12-19T13:32:20.119Z
DELETE FROM AD_Column WHERE AD_Column_ID=585329
;

-- Column: GL_Journal.C_Order_ID
-- 2022-12-19T13:32:25.263Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585328
;

-- 2022-12-19T13:32:25.269Z
DELETE FROM AD_Column WHERE AD_Column_ID=585328
;

