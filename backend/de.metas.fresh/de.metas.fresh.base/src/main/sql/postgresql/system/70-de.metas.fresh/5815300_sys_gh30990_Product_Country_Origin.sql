-- Run mode: SWING_CLIENT

-- UI Element: Produkt(541885,D) -> Produkt(547996,D) -> main -> 10 -> No.Ursprungsland
-- Column: M_Product.RawMaterialOrigin_ID
-- 2026-07-21T20:44:00.478Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742763,0,547996,552776,652695,'F',TO_TIMESTAMP('2026-07-21 20:43:59.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Ursprungsland',90,0,0,TO_TIMESTAMP('2026-07-21 20:43:59.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-21T20:46:01.496Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585121,0,TO_TIMESTAMP('2026-07-21 20:46:01.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Herstellerland','Herstellerland',TO_TIMESTAMP('2026-07-21 20:46:01.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-21T20:46:01.570Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585121 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-07-21T20:46:37.399Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Country of origin', PrintName='Country of origin',Updated=TO_TIMESTAMP('2026-07-21 20:46:37.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585121 AND AD_Language='en_US'
;

-- 2026-07-21T20:46:37.474Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-07-21T20:46:48.254Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585121,'en_US')
;

-- Field: Produkt(541885,D) -> Produkt(547996,D) -> Herstellerland
-- Column: M_Product.RawMaterialOrigin_ID
-- 2026-07-21T20:47:07.746Z
UPDATE AD_Field SET AD_Name_ID=585121, Description=NULL, Help=NULL, Name='Herstellerland',Updated=TO_TIMESTAMP('2026-07-21 20:47:07.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=742763
;

-- 2026-07-21T20:47:07.823Z
UPDATE AD_Field_Trl trl SET Name='Herstellerland' WHERE AD_Field_ID=742763 AND AD_Language='de_DE'
;

-- 2026-07-21T20:47:07.895Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585121)
;

-- 2026-07-21T20:47:08.016Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742763
;

-- 2026-07-21T20:47:08.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742763)
;

