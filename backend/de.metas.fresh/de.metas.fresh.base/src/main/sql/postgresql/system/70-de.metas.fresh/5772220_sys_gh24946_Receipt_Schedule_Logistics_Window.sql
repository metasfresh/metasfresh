-- Run mode: SWING_CLIENT



-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> ATA
-- Column: M_ReceiptSchedule.ATA
-- 2025-10-02T19:20:08.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591260,754676,0,548451,0,TO_TIMESTAMP('2025-10-02 19:20:07.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Ankunftsdatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ATA',0,0,250,0,1,1,TO_TIMESTAMP('2025-10-02 19:20:07.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:20:09.025Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754676 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:20:09.108Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584069)
;

-- 2025-10-02T19:20:09.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754676
;

-- 2025-10-02T19:20:09.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754676)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> ATD
-- Column: M_ReceiptSchedule.ATD
-- 2025-10-02T19:20:40.388Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591261,754678,0,548451,0,TO_TIMESTAMP('2025-10-02 19:20:38.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Versanddatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ATD',0,0,260,0,1,1,TO_TIMESTAMP('2025-10-02 19:20:38.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:20:40.465Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754678 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:20:40.542Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584068)
;

-- 2025-10-02T19:20:40.620Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754678
;

-- 2025-10-02T19:20:40.697Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754678)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> B/L Datum
-- Column: M_ReceiptSchedule.BLDate
-- 2025-10-02T19:21:18.468Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591262,754679,0,548451,0,TO_TIMESTAMP('2025-10-02 19:21:16.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'B/L Datum',0,0,270,0,1,1,TO_TIMESTAMP('2025-10-02 19:21:16.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:21:18.546Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754679 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:21:18.624Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584075)
;

-- 2025-10-02T19:21:18.706Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754679
;

-- 2025-10-02T19:21:18.784Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754679)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Container-Nr.
-- Column: M_ReceiptSchedule.ContainerNo
-- 2025-10-02T19:22:04.389Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591263,754680,0,548451,0,TO_TIMESTAMP('2025-10-02 19:22:02.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummer des Containers',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Container-Nr.',0,0,280,0,1,1,TO_TIMESTAMP('2025-10-02 19:22:02.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:22:04.466Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754680 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:22:04.544Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584072)
;

-- 2025-10-02T19:22:04.623Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754680
;

-- 2025-10-02T19:22:04.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754680)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> CRD
-- Column: M_ReceiptSchedule.CRD
-- 2025-10-02T19:22:50.469Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591264,754681,0,548451,0,TO_TIMESTAMP('2025-10-02 19:22:49.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gewünschte Lieferbereitschaft beim Lieferant',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'CRD',0,0,290,0,1,1,TO_TIMESTAMP('2025-10-02 19:22:49.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:22:50.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:22:50.628Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584070)
;

-- 2025-10-02T19:22:50.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754681
;

-- 2025-10-02T19:22:50.788Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754681)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> ETA
-- Column: M_ReceiptSchedule.ETA
-- 2025-10-02T19:23:29.303Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591265,754682,0,548451,0,TO_TIMESTAMP('2025-10-02 19:23:27.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ETA',0,0,300,0,1,1,TO_TIMESTAMP('2025-10-02 19:23:27.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:23:29.384Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:23:29.467Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584067)
;

-- 2025-10-02T19:23:29.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754682
;

-- 2025-10-02T19:23:29.627Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754682)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> ETD
-- Column: M_ReceiptSchedule.ETD
-- 2025-10-02T19:23:57.488Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591259,754683,0,548451,0,TO_TIMESTAMP('2025-10-02 19:23:56.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Versanddatum',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'ETD',0,0,310,0,1,1,TO_TIMESTAMP('2025-10-02 19:23:56.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:23:57.567Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754683 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:23:57.647Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584066)
;

-- 2025-10-02T19:23:57.725Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754683
;

-- 2025-10-02T19:23:57.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754683)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> B/L erhalten
-- Column: M_ReceiptSchedule.IsBLReceived
-- 2025-10-02T19:24:35.106Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591266,754684,0,548451,0,TO_TIMESTAMP('2025-10-02 19:24:33.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ist das Konnossement eingegangen?',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'B/L erhalten',0,0,320,0,1,1,TO_TIMESTAMP('2025-10-02 19:24:33.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:24:35.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:24:35.260Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584074)
;

-- 2025-10-02T19:24:35.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754684
;

-- 2025-10-02T19:24:35.417Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754684)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Buchung bestätigt
-- Column: M_ReceiptSchedule.IsBookingConfirmed
-- 2025-10-02T19:25:05.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591267,754685,0,548451,0,TO_TIMESTAMP('2025-10-02 19:25:04.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Verschiffungsbuchung bestätigt?',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Buchung bestätigt',0,0,330,0,1,1,TO_TIMESTAMP('2025-10-02 19:25:04.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:25:05.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:25:05.917Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584071)
;

-- 2025-10-02T19:25:05.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754685
;

-- 2025-10-02T19:25:06.075Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754685)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> WE Avis
-- Column: M_ReceiptSchedule.IsWENotice
-- 2025-10-02T19:25:42.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591268,754686,0,548451,0,TO_TIMESTAMP('2025-10-02 19:25:40.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Containerplanung abgeschlossen?',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'WE Avis',0,0,340,0,1,1,TO_TIMESTAMP('2025-10-02 19:25:40.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:25:42.138Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:25:42.215Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584076)
;

-- 2025-10-02T19:25:42.294Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754686
;

-- 2025-10-02T19:25:42.374Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754686)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> POD
-- Column: M_ReceiptSchedule.POD_ID
-- 2025-10-02T19:26:21.807Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591269,754687,0,548451,0,TO_TIMESTAMP('2025-10-02 19:26:20.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Entladehafen',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'POD',0,0,350,0,1,1,TO_TIMESTAMP('2025-10-02 19:26:20.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:26:21.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:26:21.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584079)
;

-- 2025-10-02T19:26:22.040Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754687
;

-- 2025-10-02T19:26:22.117Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754687)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> POL
-- Column: M_ReceiptSchedule.POL_ID
-- 2025-10-02T19:26:50.985Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591270,754688,0,548451,0,TO_TIMESTAMP('2025-10-02 19:26:49.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ladehafen',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'POL',0,0,360,0,1,1,TO_TIMESTAMP('2025-10-02 19:26:49.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:26:51.062Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754688 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:26:51.140Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584078)
;

-- 2025-10-02T19:26:51.217Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754688
;

-- 2025-10-02T19:26:51.295Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754688)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Spediteur
-- Column: M_ReceiptSchedule.Shipper_BPartner_ID
-- 2025-10-02T19:27:30.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591271,754689,0,548451,0,TO_TIMESTAMP('2025-10-02 19:27:29.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verweist auf den Spediteur mit dem der Transport abgewickelt wird',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Spediteur',0,0,370,0,1,1,TO_TIMESTAMP('2025-10-02 19:27:29.045000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:27:30.571Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:27:30.650Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540091)
;

-- 2025-10-02T19:27:30.728Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754689
;

-- 2025-10-02T19:27:30.803Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754689)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Tracking-Nr.
-- Column: M_ReceiptSchedule.TrackingID
-- 2025-10-02T19:28:02.957Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591272,754690,0,548451,0,TO_TIMESTAMP('2025-10-02 19:28:01.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tracking-ID der Sendung',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Tracking-Nr.',0,0,380,0,1,1,TO_TIMESTAMP('2025-10-02 19:28:01.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:28:03.036Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754690 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:28:03.115Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584073)
;

-- 2025-10-02T19:28:03.195Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754690
;

-- 2025-10-02T19:28:03.271Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754690)
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Vessel Name
-- Column: M_ReceiptSchedule.VesselName
-- 2025-10-02T19:28:42.701Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591273,754691,0,548451,0,TO_TIMESTAMP('2025-10-02 19:28:41.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Vessel Name',0,0,390,0,1,1,TO_TIMESTAMP('2025-10-02 19:28:41.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:28:42.780Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754691 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:28:42.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584077)
;

-- 2025-10-02T19:28:42.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754691
;

-- 2025-10-02T19:28:43.013Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754691)
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: logistics
-- 2025-10-02T19:30:30.388Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548500,553596,TO_TIMESTAMP('2025-10-02 19:30:29.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','logistics',30,TO_TIMESTAMP('2025-10-02 19:30:29.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.ETD
-- Column: M_ReceiptSchedule.ETD
-- 2025-10-02T19:31:07.685Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754683,0,548451,553596,637640,'F',TO_TIMESTAMP('2025-10-02 19:31:06.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Versanddatum','Y','N','N','Y','N','N','N',0,'ETD',10,0,0,TO_TIMESTAMP('2025-10-02 19:31:06.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.ETA
-- Column: M_ReceiptSchedule.ETA
-- 2025-10-02T19:31:29.659Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754682,0,548451,553596,637641,'F',TO_TIMESTAMP('2025-10-02 19:31:28.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum','Y','N','N','Y','N','N','N',0,'ETA',20,0,0,TO_TIMESTAMP('2025-10-02 19:31:28.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.ATD
-- Column: M_ReceiptSchedule.ATD
-- 2025-10-02T19:31:51.041Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754678,0,548451,553596,637642,'F',TO_TIMESTAMP('2025-10-02 19:31:50.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Versanddatum','Y','N','N','Y','N','N','N',0,'ATD',30,0,0,TO_TIMESTAMP('2025-10-02 19:31:50.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.ATA
-- Column: M_ReceiptSchedule.ATA
-- 2025-10-02T19:32:14.592Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754676,0,548451,553596,637643,'F',TO_TIMESTAMP('2025-10-02 19:32:13.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Ankunftsdatum','Y','N','N','Y','N','N','N',0,'ATA',40,0,0,TO_TIMESTAMP('2025-10-02 19:32:13.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.CRD
-- Column: M_ReceiptSchedule.CRD
-- 2025-10-02T19:32:36.270Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754681,0,548451,553596,637644,'F',TO_TIMESTAMP('2025-10-02 19:32:35.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gewünschte Lieferbereitschaft beim Lieferant','Y','N','N','Y','N','N','N',0,'CRD',50,0,0,TO_TIMESTAMP('2025-10-02 19:32:35.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Buchung bestätigt
-- Column: M_ReceiptSchedule.IsBookingConfirmed
-- 2025-10-02T19:32:59.517Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754685,0,548451,553596,637645,'F',TO_TIMESTAMP('2025-10-02 19:32:58.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Verschiffungsbuchung bestätigt?','Y','N','N','Y','N','N','N',0,'Buchung bestätigt',60,0,0,TO_TIMESTAMP('2025-10-02 19:32:58.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Container-Nr.
-- Column: M_ReceiptSchedule.ContainerNo
-- 2025-10-02T19:33:21.669Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754680,0,548451,553596,637646,'F',TO_TIMESTAMP('2025-10-02 19:33:20.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummer des Containers','Y','N','N','Y','N','N','N',0,'Container-Nr.',70,0,0,TO_TIMESTAMP('2025-10-02 19:33:20.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Tracking-Nr.
-- Column: M_ReceiptSchedule.TrackingID
-- 2025-10-02T19:33:46.339Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754690,0,548451,553596,637647,'F',TO_TIMESTAMP('2025-10-02 19:33:45.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tracking-ID der Sendung','Y','N','N','Y','N','N','N',0,'Tracking-Nr.',80,0,0,TO_TIMESTAMP('2025-10-02 19:33:45.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.B/L erhalten
-- Column: M_ReceiptSchedule.IsBLReceived
-- 2025-10-02T19:34:09.293Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754684,0,548451,553596,637648,'F',TO_TIMESTAMP('2025-10-02 19:34:08.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ist das Konnossement eingegangen?','Y','N','N','Y','N','N','N',0,'B/L erhalten',90,0,0,TO_TIMESTAMP('2025-10-02 19:34:08.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.B/L Datum
-- Column: M_ReceiptSchedule.BLDate
-- 2025-10-02T19:34:31.657Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754679,0,548451,553596,637649,'F',TO_TIMESTAMP('2025-10-02 19:34:30.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'B/L Datum',100,0,0,TO_TIMESTAMP('2025-10-02 19:34:30.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.WE Avis
-- Column: M_ReceiptSchedule.IsWENotice
-- 2025-10-02T19:34:53.470Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754686,0,548451,553596,637650,'F',TO_TIMESTAMP('2025-10-02 19:34:52.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wurde die Containerplanung abgeschlossen?','Y','N','N','Y','N','N','N',0,'WE Avis',110,0,0,TO_TIMESTAMP('2025-10-02 19:34:52.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Vessel Name
-- Column: M_ReceiptSchedule.VesselName
-- 2025-10-02T19:35:15.233Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754691,0,548451,553596,637651,'F',TO_TIMESTAMP('2025-10-02 19:35:14.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Vessel Name',120,0,0,TO_TIMESTAMP('2025-10-02 19:35:14.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.POL
-- Column: M_ReceiptSchedule.POL_ID
-- 2025-10-02T19:35:38.196Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754688,0,548451,553596,637652,'F',TO_TIMESTAMP('2025-10-02 19:35:37.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ladehafen','Y','N','N','Y','N','N','N',0,'POL',130,0,0,TO_TIMESTAMP('2025-10-02 19:35:37.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.POD
-- Column: M_ReceiptSchedule.POD_ID
-- 2025-10-02T19:35:59.552Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754687,0,548451,553596,637653,'F',TO_TIMESTAMP('2025-10-02 19:35:58.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Entladehafen','Y','N','N','Y','N','N','N',0,'POD',140,0,0,TO_TIMESTAMP('2025-10-02 19:35:58.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> logistics.Spediteur
-- Column: M_ReceiptSchedule.Shipper_BPartner_ID
-- 2025-10-02T19:37:16.354Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754689,0,548451,553596,637654,'F',TO_TIMESTAMP('2025-10-02 19:37:15.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verweist auf den Spediteur mit dem der Transport abgewickelt wird','Y','N','N','Y','N','N','N',0,'Spediteur',5,0,0,TO_TIMESTAMP('2025-10-02 19:37:15.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

