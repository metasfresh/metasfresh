-- Run mode: SWING_CLIENT

-- Column: DD_Order.SeqNo
-- 2025-11-19T09:26:15.969Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591592,566,0,11,53037,'XX','SeqNo',TO_TIMESTAMP('2025-11-19 09:26:15.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','EE01',0,10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2025-11-19 09:26:15.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-19T09:26:15.973Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591592 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-19T09:26:15.977Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- 2025-11-19T09:26:17.688Z
/* DDL */ SELECT public.db_alter_table('DD_Order','ALTER TABLE public.DD_Order ADD COLUMN SeqNo NUMERIC(10)')
;

-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Produktplanung
-- Column: DD_Order.PP_Product_Planning_ID
-- 2025-11-19T09:26:31.751Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,556806,756208,0,53055,TO_TIMESTAMP('2025-11-19 09:26:31.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01','Y','N','N','N','N','N','N','N','Produktplanung',TO_TIMESTAMP('2025-11-19 09:26:31.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T09:26:31.756Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T09:26:31.761Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53268)
;

-- 2025-11-19T09:26:31.776Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756208
;

-- 2025-11-19T09:26:31.778Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756208)
;

-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Verbuchungsfehler
-- Column: DD_Order.PostingError_Issue_ID
-- 2025-11-19T09:26:31.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570866,756209,0,53055,TO_TIMESTAMP('2025-11-19 09:26:31.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01','Y','N','N','N','N','N','N','N','Verbuchungsfehler',TO_TIMESTAMP('2025-11-19 09:26:31.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T09:26:31.902Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T09:26:31.904Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755)
;

-- 2025-11-19T09:26:31.910Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756209
;

-- 2025-11-19T09:26:31.911Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756209)
;

-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Verantwortlicher Benutzer
-- Column: DD_Order.AD_User_Responsible_ID
-- 2025-11-19T09:26:32.032Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577794,756210,0,53055,TO_TIMESTAMP('2025-11-19 09:26:31.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01','Y','N','N','N','N','N','N','N','Verantwortlicher Benutzer',TO_TIMESTAMP('2025-11-19 09:26:31.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T09:26:32.036Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756210 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T09:26:32.038Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542007)
;

-- 2025-11-19T09:26:32.042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756210
;

-- 2025-11-19T09:26:32.042Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756210)
;

-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Simulated
-- Column: DD_Order.IsSimulated
-- 2025-11-19T09:26:32.154Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579356,756211,0,53055,TO_TIMESTAMP('2025-11-19 09:26:32.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01','Y','N','N','N','N','N','N','N','Simulated',TO_TIMESTAMP('2025-11-19 09:26:32.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T09:26:32.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T09:26:32.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580611)
;

-- 2025-11-19T09:26:32.160Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756211
;

-- 2025-11-19T09:26:32.161Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756211)
;

-- Field: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> Reihenfolge
-- Column: DD_Order.SeqNo
-- 2025-11-19T09:26:32.280Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591592,756212,0,53055,TO_TIMESTAMP('2025-11-19 09:26:32.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'EE01','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2025-11-19 09:26:32.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-19T09:26:32.283Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756212 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-19T09:26:32.285Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2025-11-19T09:26:32.304Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756212
;

-- 2025-11-19T09:26:32.305Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756212)
;

-- UI Element: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> advanced edit -> 10 -> advanced edit.Reihenfolge
-- Column: DD_Order.SeqNo
-- 2025-11-19T09:27:20.122Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756212,0,53055,540423,638758,'F',TO_TIMESTAMP('2025-11-19 09:27:19.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','Reihenfolge',420,0,0,TO_TIMESTAMP('2025-11-19 09:27:19.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(53012,EE01) -> Distributionsauftrag(53055,EE01) -> advanced edit -> 10 -> advanced edit.Reihenfolge
-- Column: DD_Order.SeqNo
-- 2025-11-19T09:27:30.313Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-11-19 09:27:30.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638758
;

