-- gh30630: Full-sync the Delivery Instruction window (541657) main tab (546732, M_ShipperTransportation)
-- with the Transport Order window (540020) tab (540096): add the 13 transport fields the
-- Delivery Instruction window was missing, so it shows the same field set.
--
-- Each field mirrors the existing ETD field on this tab (AD_Field 710113 / AD_UI_Element 614616):
--   AD_Field: AD_Name_ID left NULL so the label resolves from the column's element;
--   AD_Field_Trl seeded then resolved via update_FieldTranslation_From_AD_Name_Element(<column element>);
--   AD_UI_Element placed to mirror tab 540096's layout: the dates (ATD/ATA/CRD/BLDate) stay in the
--   "delivery dates" group 550206; the 4 flags go in a new "flags" group (555562, right column 546540);
--   the 5 logistics fields go in a new "logistics" group (555563, left column 546539). Type 'F'.
-- ReadOnly-ness mirrors the corresponding field on tab 540096 (all N except IsSOTrx = Y).

-- ============================================================================
-- New element groups mirroring the Transport Order tab (540096, groups 553415/553587):
-- "flags" in the right column (546540, near the top) and "logistics" in the left column
-- (546539), so the boolean flags and the container/tracking/vessel/port fields are not all
-- piled into the right-column "delivery dates" group.
-- ============================================================================
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546540,555562 /*From ID Server*/,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','flags',15,NULL,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546539,555563 /*From ID Server*/,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','logistics',30,NULL,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 0) IsActive -- first element of the flags group (design cornerstone: the flags group
--    must start with IsActive; mirrors the Delivery Planning window 541632 which shows it).
--    The IsActive AD_Field already exists on this tab (710072, auto-created with the tab,
--    AD_Column 540440); it was simply not displayed. Display it and give it a UI element.
-- ============================================================================
UPDATE AD_Field SET IsDisplayed='Y', Updated=TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'), UpdatedBy=100 WHERE AD_Field_ID=710072
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710072,0,546732,555562,653168 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'Active',5,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 1) ATD  (M_ShipperTransportation.ATD, AD_Column 591247, element 584068) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591247,782302 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','ATD',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584068)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782302
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782302)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782302,0,546732,550206,653155 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'ATD',50,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 2) ATA  (M_ShipperTransportation.ATA, AD_Column 591248, element 584069) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591248,782303 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','ATA',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584069)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782303
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782303)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782303,0,546732,550206,653156 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'ATA',60,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 3) CRD  (M_ShipperTransportation.CRD, AD_Column 591249, element 584070) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591249,782304 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','CRD',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584070)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782304
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782304)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782304,0,546732,550206,653157 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'CRD',70,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 4) IsBookingConfirmed (M_ShipperTransportation.IsBookingConfirmed, AD_Column 591250, element 584071) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591250,782305 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','IsBookingConfirmed',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584071)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782305
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782305)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782305,0,546732,555562,653158 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'IsBookingConfirmed',20,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 5) IsBLReceived (M_ShipperTransportation.IsBLReceived, AD_Column 591253, element 584074) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591253,782306 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','IsBLReceived',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584074)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782306
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782306)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782306,0,546732,555562,653159 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'IsBLReceived',30,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 6) BLDate (M_ShipperTransportation.BLDate, AD_Column 591254, element 584075) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591254,782307 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','BLDate',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584075)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782307
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782307)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782307,0,546732,550206,653160 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'BLDate',80,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 7) IsWENotice (M_ShipperTransportation.IsWENotice, AD_Column 591255, element 584076) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591255,782308 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','IsWENotice',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584076)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782308
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782308)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782308,0,546732,555562,653161 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'IsWENotice',40,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 8) ContainerNo (M_ShipperTransportation.ContainerNo, AD_Column 591251, element 584072) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591251,782309 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','ContainerNo',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584072)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782309
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782309)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782309,0,546732,555563,653162 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'ContainerNo',10,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 9) TrackingID (M_ShipperTransportation.TrackingID, AD_Column 591252, element 584073) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591252,782310 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','TrackingID',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584073)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782310
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782310)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782310,0,546732,555563,653163 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'TrackingID',20,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 10) VesselName (M_ShipperTransportation.VesselName, AD_Column 591256, element 584077) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591256,782311 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','VesselName',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584077)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782311
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782311)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782311,0,546732,555563,653164 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'VesselName',30,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 11) POL_ID (M_ShipperTransportation.POL_ID, AD_Column 591257, element 584078) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591257,782312 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','POL_ID',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584078)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782312
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782312)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782312,0,546732,555563,653165 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'POL_ID',40,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 12) POD_ID (M_ShipperTransportation.POD_ID, AD_Column 591258, element 584079) -- ReadOnly N
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591258,782313 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','N','N','POD_ID',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584079)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782313
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782313)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782313,0,546732,555563,653166 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'POD_ID',50,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- 13) IsSOTrx (M_ShipperTransportation.IsSOTrx, AD_Column 590639, element 1106) -- ReadOnly Y (mirrors tab 540096)
-- ============================================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590639,782314 /*From ID Server*/,0,546732,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,0,'D','Y','Y','N','N','N','Y','N','IsSOTrx',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=782314
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(782314)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,782314,0,546732,555562,653167 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100,'Y','N','N','Y','N','N','N',0,'IsSOTrx',10,0,0,TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'),100)
;

-- ============================================================================
-- STEP B: Fix the ETA field label -- remove the "Liefertermin" AD_Name override
-- so the field shows its column element label ("ETA"). ETD (710113) is already correct.
-- ============================================================================
UPDATE AD_Field SET AD_Name_ID=NULL, Updated=TO_TIMESTAMP('2026-08-16','YYYY-MM-DD'), UpdatedBy=99 WHERE AD_Field_ID=710116
;
-- Re-resolve the field translation from the ETA column's element (584067) so the stale
-- "Liefertermin" AD_Field_Trl (seeded from the removed AD_Name override) is replaced by "ETA".
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584067)
;
