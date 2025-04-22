-- Run mode: SWING_CLIENT

-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Demand Date
-- Column: DD_Order_Candidate.DemandDate
-- 2025-02-20T15:24:19.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588899,738685,0,547559,0,TO_TIMESTAMP('2025-02-20 15:24:18.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'EE01',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Demand Date',0,0,10,0,1,1,TO_TIMESTAMP('2025-02-20 15:24:18.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-20T15:24:19.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=738685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-02-20T15:24:19.453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583209)
;

-- 2025-02-20T15:24:19.486Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=738685
;

-- 2025-02-20T15:24:19.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(738685)
;

-- UI Element: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> main -> 10 -> dates.Demand Date
-- Column: DD_Order_Candidate.DemandDate
-- 2025-02-20T15:24:56.376Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,738685,0,547559,629841,551862,'F',TO_TIMESTAMP('2025-02-20 15:24:56.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Demand Date',30,0,0,TO_TIMESTAMP('2025-02-20 15:24:56.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: DemandDate
-- 2025-02-20T15:29:19.675Z
UPDATE AD_Element_Trl SET Name='Warenausgangsdatum', PrintName='Warenausgangsdatum',Updated=TO_TIMESTAMP('2025-02-20 15:29:19.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583209 AND AD_Language='de_CH'
;

-- 2025-02-20T15:29:19.678Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-20T15:29:20.259Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583209,'de_CH')
;

-- Element: DemandDate
-- 2025-02-20T15:29:22.873Z
UPDATE AD_Element_Trl SET Name='Warenausgangsdatum', PrintName='Warenausgangsdatum',Updated=TO_TIMESTAMP('2025-02-20 15:29:22.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583209 AND AD_Language='de_DE'
;

-- 2025-02-20T15:29:22.874Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-20T15:29:23.937Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583209,'de_DE')
;

-- 2025-02-20T15:29:23.939Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583209,'de_DE')
;

-- Element: DemandDate
-- 2025-02-20T15:29:38.655Z
UPDATE AD_Element_Trl SET Name='Warenausgangsdatum', PrintName='Warenausgangsdatum',Updated=TO_TIMESTAMP('2025-02-20 15:29:38.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583209 AND AD_Language='fr_CH'
;

-- 2025-02-20T15:29:38.656Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-02-20T15:29:39.092Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583209,'fr_CH')
;

