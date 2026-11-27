-- Sales invoice window (AD_Window 167, header tab 263): show the stored buyer VAT ID, seller VAT ID and fiscal representation, read-only.
-- The three elements sit in one new element group in the advanced-edit section (after the existing advanced group), flagged as advanced fields (Alt+E only) and not shown in the grid.
-- IDs from the central ID server (2026-11-26): AD_Field 788201..788203; AD_UI_ElementGroup 556018; AD_UI_Element 656526..656528.

-- UI element group (in the advanced-edit UI column 540698 of tab 263, after the existing group (seq 10))
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540698,556018 /*From ID Server*/,TO_TIMESTAMP('2026-11-26 11:00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','vataxids',20,TO_TIMESTAMP('2026-11-26 11:00:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field + UI element: C_Invoice.BuyerVATaxID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsReadOnly,IsSameLine,Name,SeqNo,Updated,UpdatedBy) SELECT 0,c.AD_Column_ID,788201 /*From ID Server*/,0,263,TO_TIMESTAMP('2026-11-26 11:01:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,c.Description,'D','Y','Y','N','Y','N',c.Name,1010,TO_TIMESTAMP('2026-11-26 11:01:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100 FROM AD_Column c WHERE c.AD_Column_ID=593861
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=788201 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- field name / description follow the column (and its element) translations
UPDATE AD_Field_Trl ft SET Name=ct.Name, Description=ct.Description, IsTranslated=ct.IsTranslated FROM AD_Column_Trl ct WHERE ft.AD_Field_ID=788201 AND ct.AD_Column_ID=593861 AND ct.AD_Language=ft.AD_Language
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788201,0,263,656526 /*From ID Server*/,556018,'F',TO_TIMESTAMP('2026-11-26 11:02:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N',(SELECT Name FROM AD_Field WHERE AD_Field_ID=788201),10,0,0,TO_TIMESTAMP('2026-11-26 11:02:10:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field + UI element: C_Invoice.SellerVATaxID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsReadOnly,IsSameLine,Name,SeqNo,Updated,UpdatedBy) SELECT 0,c.AD_Column_ID,788202 /*From ID Server*/,0,263,TO_TIMESTAMP('2026-11-26 11:01:20:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,c.Description,'D','Y','Y','N','Y','N',c.Name,1020,TO_TIMESTAMP('2026-11-26 11:01:20:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100 FROM AD_Column c WHERE c.AD_Column_ID=593862
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=788202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- field name / description follow the column (and its element) translations
UPDATE AD_Field_Trl ft SET Name=ct.Name, Description=ct.Description, IsTranslated=ct.IsTranslated FROM AD_Column_Trl ct WHERE ft.AD_Field_ID=788202 AND ct.AD_Column_ID=593862 AND ct.AD_Language=ft.AD_Language
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788202,0,263,656527 /*From ID Server*/,556018,'F',TO_TIMESTAMP('2026-11-26 11:02:20:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N',(SELECT Name FROM AD_Field WHERE AD_Field_ID=788202),20,0,0,TO_TIMESTAMP('2026-11-26 11:02:20:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field + UI element: C_Invoice.C_Fiscal_Representation_ID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsReadOnly,IsSameLine,Name,SeqNo,Updated,UpdatedBy) SELECT 0,c.AD_Column_ID,788203 /*From ID Server*/,0,263,TO_TIMESTAMP('2026-11-26 11:01:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,c.Description,'D','Y','Y','N','Y','N',c.Name,1030,TO_TIMESTAMP('2026-11-26 11:01:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100 FROM AD_Column c WHERE c.AD_Column_ID=593863
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=788203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- field name / description follow the column (and its element) translations
UPDATE AD_Field_Trl ft SET Name=ct.Name, Description=ct.Description, IsTranslated=ct.IsTranslated FROM AD_Column_Trl ct WHERE ft.AD_Field_ID=788203 AND ct.AD_Column_ID=593863 AND ct.AD_Language=ft.AD_Language
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,788203,0,263,656528 /*From ID Server*/,556018,'F',TO_TIMESTAMP('2026-11-26 11:02:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N',(SELECT Name FROM AD_Field WHERE AD_Field_ID=788203),30,0,0,TO_TIMESTAMP('2026-11-26 11:02:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
